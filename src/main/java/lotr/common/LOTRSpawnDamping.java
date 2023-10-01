package lotr.common;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class LOTRSpawnDamping {
	public static Map<String, Float> spawnDamping = new HashMap<>();
	public static String TYPE_NPC = "lotr_npc";
	public static boolean needsSave = true;

	public static int getBaseSpawnCapForInfo(String type, World world) {
		if (type.equals(TYPE_NPC)) {
			return LOTRDimension.getCurrentDimensionWithFallback(world).spawnCap;
		}
		EnumCreatureType creatureType = EnumCreatureType.valueOf(type);
		return creatureType.getMaxNumberOfCreature();
	}

	public static int getCreatureSpawnCap(EnumCreatureType type, World world) {
		return getSpawnCap(type.name(), type.getMaxNumberOfCreature(), world);
	}

	public static File getDataFile() {
		return new File(LOTRLevelData.getOrCreateLOTRDir(), "spawn_damping.dat");
	}

	public static int getNPCSpawnCap(World world) {
		return getSpawnCap(TYPE_NPC, LOTRDimension.getCurrentDimensionWithFallback(world).spawnCap, world);
	}

	public static int getSpawnCap(String type, int baseCap, int players) {
		float damp = getSpawnDamping(type);
		float dampFraction = (players - 1) * damp;
		dampFraction = MathHelper.clamp_float(dampFraction, 0.0F, 1.0F);
		float stationaryPointValue = 0.5F + damp / 2.0F;
		if (dampFraction > stationaryPointValue) {
			dampFraction = stationaryPointValue;
		}
		int capPerPlayer = Math.round(baseCap * (1.0F - dampFraction));
		return Math.max(capPerPlayer, 1);
	}

	public static int getSpawnCap(String type, int baseCap, World world) {
		int players = world.playerEntities.size();
		return getSpawnCap(type, baseCap, players);
	}

	public static float getSpawnDamping(String type) {
		float f = 0.0f;
		if (spawnDamping.containsKey(type)) {
			f = spawnDamping.get(type);
		}
		return f;
	}

	public static void loadAll() {
		try {
			File datFile = getDataFile();
			NBTTagCompound spawnData = LOTRLevelData.loadNBTFromFile(datFile);
			spawnDamping.clear();
			if (spawnData.hasKey("Damping")) {
				NBTTagList typeTags = spawnData.getTagList("Damping", 10);
				for (int i = 0; i < typeTags.tagCount(); ++i) {
					NBTTagCompound nbt = typeTags.getCompoundTagAt(i);
					String type = nbt.getString("Type");
					float damping = nbt.getFloat("Damp");
					if (StringUtils.isBlank(type)) {
						continue;
					}
					spawnDamping.put(type, damping);
				}
			}
			needsSave = true;
			saveAll();
		} catch (Exception e) {
			FMLLog.severe("Error loading LOTR spawn damping");
			e.printStackTrace();
		}
	}

	public static void markDirty() {
		needsSave = true;
	}

	public static void resetAll() {
		spawnDamping.clear();
		markDirty();
	}

	public static void saveAll() {
		try {
			File datFile = getDataFile();
			if (!datFile.exists()) {
				CompressedStreamTools.writeCompressed(new NBTTagCompound(), Files.newOutputStream(datFile.toPath()));
			}
			NBTTagCompound spawnData = new NBTTagCompound();
			NBTTagList typeTags = new NBTTagList();
			for (Map.Entry<String, Float> e : spawnDamping.entrySet()) {
				String type = e.getKey();
				float damping = e.getValue();
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setString("Type", type);
				nbt.setFloat("Damp", damping);
				typeTags.appendTag(nbt);
			}
			spawnData.setTag("Damping", typeTags);
			LOTRLevelData.saveNBTToFile(datFile, spawnData);
			needsSave = false;
		} catch (Exception e) {
			FMLLog.severe("Error saving LOTR spawn damping");
			e.printStackTrace();
		}
	}

	public static void setNPCSpawnDamping(float damping) {
		setSpawnDamping(TYPE_NPC, damping);
	}

	public static void setSpawnDamping(EnumCreatureType type, float damping) {
		setSpawnDamping(type.name(), damping);
	}

	public static void setSpawnDamping(String type, float damping) {
		spawnDamping.put(type, damping);
		markDirty();
	}
}
