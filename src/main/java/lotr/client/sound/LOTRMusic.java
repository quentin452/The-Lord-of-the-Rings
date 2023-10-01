package lotr.client.sound;

import com.google.common.base.Charsets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import lotr.common.LOTRDimension;
import lotr.common.LOTRReflection;
import lotr.common.util.LOTRLog;
import lotr.common.world.LOTRWorldProvider;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.LOTRMusicRegion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundRegistry;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.sound.PlaySoundEvent17;
import net.minecraftforge.common.MinecraftForge;
import org.apache.commons.io.input.BOMInputStream;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class LOTRMusic implements IResourceManagerReloadListener {
	public static File musicDir;
	public static String jsonFilename = "music.json";
	public static String musicResourcePath = "lotrmusic";
	public static LOTRMusicResourceManager trackResourceManager = new LOTRMusicResourceManager();
	public static List<LOTRMusicTrack> allTracks = new ArrayList<>();
	public static Map<LOTRMusicRegion.Sub, LOTRRegionTrackPool> regionTracks = new HashMap<>();
	public static boolean initSubregions;
	public static Random musicRand = new Random();

	public LOTRMusic() {
		((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(this);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static void addTrackToRegions(LOTRMusicTrack track) {
		allTracks.add(track);
		for (LOTRMusicRegion region : track.getAllRegions()) {
			if (region.hasNoSubregions()) {
				getTracksForRegion(region, null).addTrack(track);
				continue;
			}
			for (String sub : track.getRegionInfo(region).getSubregions()) {
				getTracksForRegion(region, sub).addTrack(track);
			}
		}
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	public static void generateReadme() throws IOException {
		File readme = new File(musicDir, "readme.txt");
		readme.createNewFile();
		PrintStream writer = new PrintStream(Files.newOutputStream(readme.toPath()), true, StandardCharsets.UTF_8.name());
		ResourceLocation template = new ResourceLocation("lotr:music/readme.txt");
		InputStream templateIn = Minecraft.getMinecraft().getResourceManager().getResource(template).getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(templateIn), Charsets.UTF_8));
		String line;
		while ((line = reader.readLine()) != null) {
			if ("#REGIONS#".equals(line)) {
				writer.println("all");
				for (Enum region : LOTRMusicRegion.values()) {
					StringBuilder regionString = new StringBuilder();
					regionString.append(((LOTRMusicRegion) region).regionName);
					List<String> subregions = ((LOTRMusicRegion) region).getAllSubregions();
					if (!subregions.isEmpty()) {
						StringBuilder subs = new StringBuilder();
						for (String s : subregions) {
							if (subs.length() > 0) {
								subs.append(", ");
							}
							subs.append(s);
						}
						regionString.append(": {").append(subs).append("}");
					}
					writer.println(regionString);
				}
				continue;
			}
			if ("#CATEGORIES#".equals(line)) {
				for (Enum category : LOTRMusicCategory.values()) {
					String catString = ((LOTRMusicCategory) category).categoryName;
					writer.println(catString);
				}
				continue;
			}
			writer.println(line);
		}
		writer.close();
		reader.close();
	}

	public static LOTRRegionTrackPool getTracksForRegion(LOTRMusicRegion region, String sub) {
		if (region.hasSubregion(sub) || region.hasNoSubregions() && sub == null) {
			LOTRMusicRegion.Sub key = region.getSubregion(sub);
			LOTRRegionTrackPool regionPool = regionTracks.get(key);
			if (regionPool == null) {
				regionPool = new LOTRRegionTrackPool(region, sub);
				regionTracks.put(key, regionPool);
			}
			return regionPool;
		}
		LOTRLog.logger.warn("LOTRMusic: No subregion " + sub + " for region " + region.regionName + "!");
		return null;
	}

	public static boolean isLOTRDimension() {
		Minecraft mc = Minecraft.getMinecraft();
		WorldClient world = mc.theWorld;
		EntityClientPlayerMP entityplayer = mc.thePlayer;
		return entityplayer != null && world != null && world.provider instanceof LOTRWorldProvider;
	}

	public static boolean isMenuMusic() {
		Minecraft mc = Minecraft.getMinecraft();
		return mc.func_147109_W() == MusicTicker.MusicType.MENU;
	}

	public static void loadMusicPack(ZipFile zip, SimpleReloadableResourceManager resourceMgr) throws IOException {
		ZipEntry entry = zip.getEntry(jsonFilename);
		if (entry != null) {
			InputStream stream = zip.getInputStream(entry);
			JsonReader reader = new JsonReader(new InputStreamReader(new BOMInputStream(stream), Charsets.UTF_8));
			JsonParser parser = new JsonParser();
			ArrayList<LOTRMusicTrack> packTracks = new ArrayList<>();
			JsonObject root = parser.parse(reader).getAsJsonObject();
			JsonArray rootArray = root.get("tracks").getAsJsonArray();
			for (JsonElement e : rootArray) {
				JsonObject trackData = e.getAsJsonObject();
				String filename = trackData.get("file").getAsString();
				ZipEntry trackEntry = zip.getEntry("assets/lotrmusic/" + filename);
				if (trackEntry == null) {
					LOTRLog.logger.warn("LOTRMusic: Track " + filename + " in pack " + zip.getName() + " does not exist!");
					continue;
				}
				InputStream trackStream = zip.getInputStream(trackEntry);
				LOTRMusicTrack track = new LOTRMusicTrack(filename);
				if (trackData.has("title")) {
					String title = trackData.get("title").getAsString();
					track.setTitle(title);
				}
				JsonArray regions = trackData.get("regions").getAsJsonArray();
				for (JsonElement r : regions) {
					LOTRMusicRegion region;
					JsonObject regionData = r.getAsJsonObject();
					String regionName = regionData.get("name").getAsString();
					boolean allRegions = false;
					if ("all".equalsIgnoreCase(regionName)) {
						region = null;
						allRegions = true;
					} else {
						region = LOTRMusicRegion.forName(regionName);
						if (region == null) {
							LOTRLog.logger.warn("LOTRMusic: No region named " + regionName + "!");
							continue;
						}
					}
					Collection<String> subregionNames = new ArrayList<>();
					if (region != null && regionData.has("sub")) {
						JsonArray subList = regionData.get("sub").getAsJsonArray();
						for (JsonElement s : subList) {
							String sub = s.getAsString();
							if (region.hasSubregion(sub)) {
								subregionNames.add(sub);
								continue;
							}
							LOTRLog.logger.warn("LOTRMusic: No subregion " + sub + " for region " + region.regionName + "!");
						}
					}
					Collection<LOTRMusicCategory> regionCategories = new ArrayList<>();
					if (region != null && regionData.has("categories")) {
						JsonArray catList = regionData.get("categories").getAsJsonArray();
						for (JsonElement cat : catList) {
							String categoryName = cat.getAsString();
							LOTRMusicCategory category = LOTRMusicCategory.forName(categoryName);
							if (category != null) {
								regionCategories.add(category);
								continue;
							}
							LOTRLog.logger.warn("LOTRMusic: No category named " + categoryName + "!");
						}
					}
					double weight = -1.0;
					if (regionData.has("weight")) {
						weight = regionData.get("weight").getAsDouble();
					}
					Collection<LOTRMusicRegion> regionsAdd = new ArrayList<>();
					if (allRegions) {
						regionsAdd.addAll(Arrays.asList(LOTRMusicRegion.values()));
					} else {
						regionsAdd.add(region);
					}
					for (LOTRMusicRegion reg : regionsAdd) {
						LOTRTrackRegionInfo regInfo = track.createRegionInfo(reg);
						if (weight >= 0.0) {
							regInfo.setWeight(weight);
						}
						if (subregionNames.isEmpty()) {
							regInfo.addAllSubregions();
						} else {
							for (String sub : subregionNames) {
								regInfo.addSubregion(sub);
							}
						}
						if (regionCategories.isEmpty()) {
							regInfo.addAllCategories();
							continue;
						}
						for (LOTRMusicCategory cat : regionCategories) {
							regInfo.addCategory(cat);
						}
					}
				}
				if (trackData.has("authors")) {
					JsonArray authorList = trackData.get("authors").getAsJsonArray();
					for (JsonElement a : authorList) {
						String author = a.getAsString();
						track.addAuthor(author);
					}
				}
				track.loadTrack(trackStream);
				packTracks.add(track);
			}
			reader.close();
			LOTRLog.logger.info("LOTRMusic: Successfully loaded music pack " + zip.getName() + " with " + packTracks.size() + " tracks");
		}
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	public static void loadMusicPacks(File mcDir, SimpleReloadableResourceManager resourceMgr) {
		musicDir = new File(mcDir, musicResourcePath);
		if (!musicDir.exists()) {
			musicDir.mkdirs();
		}
		allTracks.clear();
		regionTracks.clear();
		if (!initSubregions) {
			for (LOTRDimension dim : LOTRDimension.values()) {
				for (LOTRBiome biome : dim.biomeList) {
					if (biome == null) {
						continue;
					}
					biome.getBiomeMusic();
				}
			}
			initSubregions = true;
		}
		for (File file : musicDir.listFiles()) {
			if (!file.isFile() || !file.getName().endsWith(".zip")) {
				continue;
			}
			try {
				IResourcePack resourcePack = new FileResourcePack(file);
				resourceMgr.reloadResourcePack(resourcePack);
				ZipFile zipFile = new ZipFile(file);
				loadMusicPack(zipFile, resourceMgr);
			} catch (Exception e) {
				LOTRLog.logger.warn("LOTRMusic: Failed to load music pack " + file.getName() + "!");
				e.printStackTrace();
			}
		}
		try {
			generateReadme();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SubscribeEvent
	public void onPlaySound(PlaySoundEvent17 event) {
		if (!allTracks.isEmpty() && event.category == SoundCategory.MUSIC && !(event.sound instanceof LOTRMusicTrack)) {
			if (isLOTRDimension()) {
				event.result = null;
				return;
			}
			if (isMenuMusic() && !getTracksForRegion(LOTRMusicRegion.MENU, null).isEmpty()) {
				event.result = null;
			}
		}
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourcemanager) {
		loadMusicPacks(Minecraft.getMinecraft().mcDataDir, (SimpleReloadableResourceManager) resourcemanager);
	}

	public void update() {
		LOTRMusicTicker.update(musicRand);
	}

	public static class Reflect {
		public static SoundRegistry getSoundRegistry() {
			SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
			try {
				return ObfuscationReflectionHelper.getPrivateValue(SoundHandler.class, handler, "sndRegistry", "field_147697_e");
			} catch (Exception e) {
				LOTRReflection.logFailure(e);
				return null;
			}
		}

		public static void putDomainResourceManager(String domain, IResourceManager manager) {
			SimpleReloadableResourceManager masterManager = (SimpleReloadableResourceManager) Minecraft.getMinecraft().getResourceManager();
			try {
				Map map = ObfuscationReflectionHelper.getPrivateValue(SimpleReloadableResourceManager.class, masterManager, "domainResourceManagers", "field_110548_a");
				map.put(domain, manager);
			} catch (Exception e) {
				LOTRReflection.logFailure(e);
			}
		}
	}

}
