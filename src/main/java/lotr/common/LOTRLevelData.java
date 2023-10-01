package lotr.common;

import com.google.common.base.Optional;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.fellowship.LOTRFellowshipData;
import lotr.common.network.*;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRTravellingTraderSpawner;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.StatCollector;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class LOTRLevelData {
	public static int madePortal;
	public static int madeMiddleEarthPortal;
	public static int overworldPortalX;
	public static int overworldPortalY;
	public static int overworldPortalZ;
	public static int middleEarthPortalX;
	public static int middleEarthPortalY;
	public static int middleEarthPortalZ;
	public static int structuresBanned;
	public static int waypointCooldownMax;
	public static int waypointCooldownMin;
	public static boolean gollumSpawned;
	public static boolean enableAlignmentZones;
	public static float conquestRate = 1.0f;
	public static boolean clientside_thisServer_feastMode;
	public static boolean clientside_thisServer_fellowshipCreation;
	public static int clientside_thisServer_fellowshipMaxSize;
	public static boolean clientside_thisServer_enchanting;
	public static boolean clientside_thisServer_enchantingLOTR;
	public static boolean clientside_thisServer_strictFactionTitleRequirements;
	public static int clientside_thisServer_customWaypointMinY;
	public static boolean commemorateEmpressShamiir;
	public static boolean clientside_thisServer_commemorateEmpressShamiir = true;
	public static EnumDifficulty difficulty;
	public static boolean difficultyLock;
	public static Map<UUID, LOTRPlayerData> playerDataMap = new HashMap<>();
	public static Map<UUID, Optional<LOTRTitle.PlayerTitle>> playerTitleOfflineCacheMap = new HashMap<>();
	public static boolean needsLoad = true;
	public static boolean needsSave;

	public static boolean anyDataNeedsSave() {
		if (needsSave || LOTRSpawnDamping.needsSave) {
			return true;
		}
		for (LOTRPlayerData pd : playerDataMap.values()) {
			if (!pd.needsSave()) {
				continue;
			}
			return true;
		}
		return false;
	}

	public static void destroyAllPlayerData() {
		playerDataMap.clear();
	}

	public static boolean enableAlignmentZones() {
		return enableAlignmentZones;
	}

	public static Set<String> getBannedStructurePlayersUsernames() {
		Set<String> players = new HashSet<>();
		for (UUID uuid : playerDataMap.keySet()) {
			String username;
			if (!getData(uuid).getStructuresBanned()) {
				continue;
			}
			GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152652_a(uuid);
			if (StringUtils.isBlank(profile.getName())) {
				MinecraftServer.getServer().func_147130_as().fillProfileProperties(profile, true);
			}
			if (StringUtils.isBlank(username = profile.getName())) {
				continue;
			}
			players.add(username);
		}
		return players;
	}

	public static float getConquestRate() {
		return conquestRate;
	}

	public static void setConquestRate(float f) {
		conquestRate = f;
		markDirty();
	}

	public static LOTRPlayerData getData(EntityPlayer entityplayer) {
		return getData(entityplayer.getUniqueID());
	}

	public static LOTRPlayerData getData(UUID player) {
		LOTRPlayerData pd = playerDataMap.get(player);
		if (pd == null) {
			pd = loadData(player);
			playerTitleOfflineCacheMap.remove(player);
			if (pd == null) {
				pd = new LOTRPlayerData(player);
			}
			playerDataMap.put(player, pd);
		}
		return pd;
	}

	public static String getHMSTime_Seconds(int secs) {
		return getHMSTime_Ticks(secs * 20);
	}

	public static String getHMSTime_Ticks(int ticks) {
		int hours = ticks / 72000;
		int minutes = ticks % 72000 / 1200;
		int seconds = ticks % 72000 % 1200 / 20;
		String sHours = StatCollector.translateToLocalFormatted("lotr.gui.time.hours", hours);
		String sMinutes = StatCollector.translateToLocalFormatted("lotr.gui.time.minutes", minutes);
		String sSeconds = StatCollector.translateToLocalFormatted("lotr.gui.time.seconds", seconds);
		if (hours > 0) {
			return StatCollector.translateToLocalFormatted("lotr.gui.time.format.hms", sHours, sMinutes, sSeconds);
		}
		if (minutes > 0) {
			return StatCollector.translateToLocalFormatted("lotr.gui.time.format.ms", sMinutes, sSeconds);
		}
		return StatCollector.translateToLocalFormatted("lotr.gui.time.format.s", sSeconds);
	}

	public static File getLOTRDat() {
		return new File(getOrCreateLOTRDir(), "LOTR.dat");
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	public static File getLOTRPlayerDat(UUID player) {
		File playerDir = new File(getOrCreateLOTRDir(), "players");
		if (!playerDir.exists()) {
			playerDir.mkdirs();
		}
		return new File(playerDir, player.toString() + ".dat");
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	public static File getOrCreateLOTRDir() {
		File file = new File(DimensionManager.getCurrentSaveRootDirectory(), "LOTR");
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	public static LOTRTitle.PlayerTitle getPlayerTitleWithOfflineCache(UUID player) {
		if (playerDataMap.containsKey(player)) {
			return playerDataMap.get(player).getPlayerTitle();
		}
		if (playerTitleOfflineCacheMap.containsKey(player)) {
			return playerTitleOfflineCacheMap.get(player).orNull();
		}
		LOTRPlayerData pd = loadData(player);
		LOTRTitle.PlayerTitle playerTitle = pd.getPlayerTitle();
		playerTitleOfflineCacheMap.put(player, Optional.fromNullable(playerTitle));
		return playerTitle;
	}

	public static EnumDifficulty getSavedDifficulty() {
		return difficulty;
	}

	public static void setSavedDifficulty(EnumDifficulty d) {
		difficulty = d;
		markDirty();
	}

	public static int getWaypointCooldownMax() {
		return waypointCooldownMax;
	}

	public static int getWaypointCooldownMin() {
		return waypointCooldownMin;
	}

	public static boolean gollumSpawned() {
		return gollumSpawned;
	}

	public static boolean isDifficultyLocked() {
		return difficultyLock;
	}

	public static void setDifficultyLocked(boolean flag) {
		difficultyLock = flag;
		markDirty();
	}

	public static boolean isPlayerBannedForStructures(EntityPlayer entityplayer) {
		return getData(entityplayer).getStructuresBanned();
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	public static void load() {
		try {
			NBTTagCompound levelData = loadNBTFromFile(getLOTRDat());
			File oldLOTRDat = new File(DimensionManager.getCurrentSaveRootDirectory(), "LOTR.dat");
			if (oldLOTRDat.exists()) {
				levelData = loadNBTFromFile(oldLOTRDat);
				oldLOTRDat.delete();
				if (levelData.hasKey("PlayerData")) {
					NBTTagList playerDataTags = levelData.getTagList("PlayerData", 10);
					for (int i = 0; i < playerDataTags.tagCount(); ++i) {
						NBTTagCompound nbt = playerDataTags.getCompoundTagAt(i);
						UUID player = UUID.fromString(nbt.getString("PlayerUUID"));
						saveNBTToFile(getLOTRPlayerDat(player), nbt);
					}
				}
			}
			madePortal = levelData.getInteger("MadePortal");
			madeMiddleEarthPortal = levelData.getInteger("MadeMiddlePortal");
			overworldPortalX = levelData.getInteger("OverworldX");
			overworldPortalY = levelData.getInteger("OverworldY");
			overworldPortalZ = levelData.getInteger("OverworldZ");
			middleEarthPortalX = levelData.getInteger("MiddleEarthX");
			middleEarthPortalY = levelData.getInteger("MiddleEarthY");
			middleEarthPortalZ = levelData.getInteger("MiddleEarthZ");
			structuresBanned = levelData.getInteger("StructuresBanned");
			waypointCooldownMax = levelData.hasKey("FastTravel") ? levelData.getInteger("FastTravel") / 20 : levelData.hasKey("WpCdMax") ? levelData.getInteger("WpCdMax") : 1800;
			waypointCooldownMin = levelData.hasKey("FastTravelMin") ? levelData.getInteger("FastTravelMin") / 20 : levelData.hasKey("WpCdMin") ? levelData.getInteger("WpCdMin") : 180;
			gollumSpawned = levelData.getBoolean("GollumSpawned");
			enableAlignmentZones = !levelData.hasKey("AlignmentZones") || levelData.getBoolean("AlignmentZones");
			conquestRate = levelData.hasKey("ConqRate") ? levelData.getFloat("ConqRate") : 1.0f;
			commemorateEmpressShamiir = levelData.getBoolean("CommemorateEmpressShamiir");
			if (levelData.hasKey("SavedDifficulty")) {
				int id = levelData.getInteger("SavedDifficulty");
				difficulty = EnumDifficulty.getDifficultyEnum(id);
				LOTRMod.proxy.setClientDifficulty(difficulty);
			} else {
				difficulty = null;
			}
			difficultyLock = levelData.getBoolean("DifficultyLock");
			NBTTagCompound travellingTraderData = levelData.getCompoundTag("TravellingTraders");
			for (LOTRTravellingTraderSpawner trader : LOTREventSpawner.travellingTraders) {
				NBTTagCompound nbt = travellingTraderData.getCompoundTag(trader.entityClassName);
				trader.readFromNBT(nbt);
			}
			LOTRGreyWandererTracker.load(levelData);
			destroyAllPlayerData();
			LOTRDate.loadDates(levelData);
			LOTRSpawnDamping.loadAll();
			needsLoad = false;
			needsSave = true;
			save();
		} catch (Exception e) {
			FMLLog.severe("Error loading LOTR data");
			e.printStackTrace();
		}
	}

	public static LOTRPlayerData loadData(UUID player) {
		try {
			NBTTagCompound nbt = loadNBTFromFile(getLOTRPlayerDat(player));
			LOTRPlayerData pd = new LOTRPlayerData(player);
			pd.load(nbt);
			return pd;
		} catch (Exception e) {
			FMLLog.severe("Error loading LOTR player data for %s", player);
			e.printStackTrace();
			return null;
		}
	}

	public static NBTTagCompound loadNBTFromFile(File file) throws FileNotFoundException, IOException {
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			NBTTagCompound nbt = CompressedStreamTools.readCompressed(fis);
			fis.close();
			return nbt;
		}
		return new NBTTagCompound();
	}

	public static void markDirty() {
		needsSave = true;
	}

	public static void markMiddleEarthPortalLocation(int i, int j, int k) {
		IMessage packet = new LOTRPacketPortalPos(i, j, k);
		LOTRPacketHandler.networkWrapper.sendToAll(packet);
		markDirty();
	}

	public static void markOverworldPortalLocation(int i, int j, int k) {
		overworldPortalX = i;
		overworldPortalY = j;
		overworldPortalZ = k;
		markDirty();
	}

	public static void save() {
		try {
			if (needsSave) {
				File LOTR_dat = getLOTRDat();
				if (!LOTR_dat.exists()) {
					saveNBTToFile(LOTR_dat, new NBTTagCompound());
				}
				NBTTagCompound levelData = new NBTTagCompound();
				levelData.setInteger("MadePortal", madePortal);
				levelData.setInteger("MadeMiddlePortal", madeMiddleEarthPortal);
				levelData.setInteger("OverworldX", overworldPortalX);
				levelData.setInteger("OverworldY", overworldPortalY);
				levelData.setInteger("OverworldZ", overworldPortalZ);
				levelData.setInteger("MiddleEarthX", middleEarthPortalX);
				levelData.setInteger("MiddleEarthY", middleEarthPortalY);
				levelData.setInteger("MiddleEarthZ", middleEarthPortalZ);
				levelData.setInteger("StructuresBanned", structuresBanned);
				levelData.setInteger("WpCdMax", waypointCooldownMax);
				levelData.setInteger("WpCdMin", waypointCooldownMin);
				levelData.setBoolean("GollumSpawned", gollumSpawned);
				levelData.setBoolean("AlignmentZones", enableAlignmentZones);
				levelData.setFloat("ConqRate", conquestRate);
				levelData.setBoolean("CommemorateEmpressShamiir", commemorateEmpressShamiir);
				if (difficulty != null) {
					levelData.setInteger("SavedDifficulty", difficulty.getDifficultyId());
				}
				levelData.setBoolean("DifficultyLock", difficultyLock);
				NBTTagCompound travellingTraderData = new NBTTagCompound();
				for (LOTRTravellingTraderSpawner trader : LOTREventSpawner.travellingTraders) {
					NBTTagCompound nbt = new NBTTagCompound();
					trader.writeToNBT(nbt);
					travellingTraderData.setTag(trader.entityClassName, nbt);
				}
				levelData.setTag("TravellingTraders", travellingTraderData);
				LOTRGreyWandererTracker.save(levelData);
				LOTRDate.saveDates(levelData);
				saveNBTToFile(LOTR_dat, levelData);
				needsSave = false;
			}
			for (Map.Entry<UUID, LOTRPlayerData> e : playerDataMap.entrySet()) {
				UUID player = e.getKey();
				LOTRPlayerData pd = e.getValue();
				if (pd.needsSave()) {
					saveData(player);
				}
			}
			if (LOTRSpawnDamping.needsSave) {
				LOTRSpawnDamping.saveAll();
			}
		} catch (Exception e) {
			FMLLog.severe("Error saving LOTR data");
			e.printStackTrace();
		}
	}

	public static boolean saveAndClearData(UUID player) {
		LOTRPlayerData pd = playerDataMap.get(player);
		if (pd != null) {
			boolean saved = false;
			if (pd.needsSave()) {
				saveData(player);
				saved = true;
			}
			playerTitleOfflineCacheMap.put(player, Optional.fromNullable(pd.getPlayerTitle()));
			playerDataMap.remove(player);
			return saved;
		}
		FMLLog.severe("Attempted to clear LOTR player data for %s; no data found", player);
		return false;
	}

	public static void saveAndClearUnusedPlayerData() {
		Collection<UUID> clearing = new ArrayList<>();
		for (UUID player : playerDataMap.keySet()) {
			boolean foundPlayer = false;
			for (WorldServer world : MinecraftServer.getServer().worldServers) {
				if (world.func_152378_a(player) == null) {
					continue;
				}
				foundPlayer = true;
				break;
			}
			if (foundPlayer) {
				continue;
			}
			clearing.add(player);
		}
		for (UUID player : clearing) {
			boolean saved = saveAndClearData(player);
		}
	}

	public static void saveData(UUID player) {
		try {
			NBTTagCompound nbt = new NBTTagCompound();
			LOTRPlayerData pd = playerDataMap.get(player);
			pd.save(nbt);
			saveNBTToFile(getLOTRPlayerDat(player), nbt);
		} catch (Exception e) {
			FMLLog.severe("Error saving LOTR player data for %s", player);
			e.printStackTrace();
		}
	}

	public static void saveNBTToFile(File file, NBTTagCompound nbt) throws FileNotFoundException, IOException {
		CompressedStreamTools.writeCompressed(nbt, Files.newOutputStream(file.toPath()));
	}

	public static void sendAlignmentToAllPlayersInWorld(EntityPlayer entityplayer, World world) {
		for (Object element : world.playerEntities) {
			EntityPlayer worldPlayer = (EntityPlayer) element;
			IMessage packet = new LOTRPacketAlignment(entityplayer.getUniqueID());
			LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP) worldPlayer);
		}
	}

	public static void sendAllAlignmentsInWorldToPlayer(EntityPlayer entityplayer, World world) {
		for (Object element : world.playerEntities) {
			EntityPlayer worldPlayer = (EntityPlayer) element;
			IMessage packet = new LOTRPacketAlignment(worldPlayer.getUniqueID());
			LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
	}

	public static void sendAllShieldsInWorldToPlayer(EntityPlayer entityplayer, World world) {
		for (Object element : world.playerEntities) {
			EntityPlayer worldPlayer = (EntityPlayer) element;
			IMessage packet = new LOTRPacketShield(worldPlayer.getUniqueID());
			LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
	}

	public static void sendLoginPacket(EntityPlayerMP entityplayer) {
		LOTRPacketLogin packet = new LOTRPacketLogin();
		packet.ringPortalX = middleEarthPortalX;
		packet.ringPortalY = middleEarthPortalY;
		packet.ringPortalZ = middleEarthPortalZ;
		packet.ftCooldownMax = waypointCooldownMax;
		packet.ftCooldownMin = waypointCooldownMin;
		packet.difficulty = difficulty;
		packet.difficultyLocked = difficultyLock;
		packet.alignmentZones = enableAlignmentZones;
		packet.feastMode = LOTRConfig.canAlwaysEat;
		packet.fellowshipCreation = LOTRConfig.enableFellowshipCreation;
		packet.fellowshipMaxSize = LOTRConfig.fellowshipMaxSize;
		packet.enchanting = LOTRConfig.enchantingVanilla;
		packet.enchantingLOTR = LOTRConfig.enchantingLOTR;
		packet.strictFactionTitleRequirements = LOTRConfig.strictFactionTitleRequirements;
		packet.customWaypointMinY = LOTRConfig.customWaypointMinY;
		packet.commemorateEmpressShamiir = commemorateEmpressShamiir;
		LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
	}

	public static void sendPlayerData(EntityPlayerMP entityplayer) {
		try {
			LOTRPlayerData pd = getData(entityplayer);
			pd.sendPlayerData(entityplayer);
		} catch (Exception e) {
			FMLLog.severe("Failed to send player data to player " + entityplayer.getCommandSenderName());
			e.printStackTrace();
		}
	}

	public static void sendPlayerLocationsToPlayer(EntityPlayer sendPlayer, World world) {
		LOTRPacketUpdatePlayerLocations packetLocations = new LOTRPacketUpdatePlayerLocations();
		boolean isOp = MinecraftServer.getServer().getConfigurationManager().func_152596_g(sendPlayer.getGameProfile());
		boolean creative = sendPlayer.capabilities.isCreativeMode;
		LOTRPlayerData playerData = getData(sendPlayer);
		Collection<LOTRFellowship> fellowshipsMapShow = new ArrayList<>();
		for (UUID fsID : playerData.getFellowshipIDs()) {
			LOTRFellowship fs = LOTRFellowshipData.getActiveFellowship(fsID);
			if (fs == null || !fs.getShowMapLocations()) {
				continue;
			}
			fellowshipsMapShow.add(fs);
		}
		for (Object element : world.playerEntities) {
			boolean show;
			EntityPlayer otherPlayer = (EntityPlayer) element;
			if (otherPlayer == sendPlayer) {
				continue;
			}
			show = !getData(otherPlayer).getHideMapLocation();
			if (!isOp && getData(otherPlayer).getAdminHideMap() || LOTRConfig.forceMapLocations == 1) {
				show = false;
			} else if (LOTRConfig.forceMapLocations == 2) {
				show = true;
			} else if (!show) {
				if (isOp && creative) {
					show = true;
				} else if (!playerData.isSiegeActive()) {
					for (LOTRFellowship fs : fellowshipsMapShow) {
						if (!fs.containsPlayer(otherPlayer.getUniqueID())) {
							continue;
						}
						show = true;
						break;
					}
				}
			}
			if (!show) {
				continue;
			}
			packetLocations.addPlayerLocation(otherPlayer.getGameProfile(), otherPlayer.posX, otherPlayer.posZ);
		}
		LOTRPacketHandler.networkWrapper.sendTo(packetLocations, (EntityPlayerMP) sendPlayer);
	}

	public static void sendShieldToAllPlayersInWorld(EntityPlayer entityplayer, World world) {
		for (Object element : world.playerEntities) {
			EntityPlayer worldPlayer = (EntityPlayer) element;
			IMessage packet = new LOTRPacketShield(entityplayer.getUniqueID());
			LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP) worldPlayer);
		}
	}

	public static void setEnableAlignmentZones(boolean flag) {
		enableAlignmentZones = flag;
		markDirty();
		if (!LOTRMod.proxy.isClient()) {
			List players = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
			for (Object player : players) {
				EntityPlayerMP entityplayer = (EntityPlayerMP) player;
				IMessage packet = new LOTRPacketEnableAlignmentZones(enableAlignmentZones);
				LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
			}
		}
	}

	public static void setGollumSpawned(boolean flag) {
		gollumSpawned = flag;
		markDirty();
	}

	public static void setMadeMiddleEarthPortal(int i) {
		madeMiddleEarthPortal = i;
		markDirty();
	}

	public static void setMadePortal(int i) {
		madePortal = i;
		markDirty();
	}

	public static void setPlayerBannedForStructures(String username, boolean flag) {
		UUID uuid = UUID.fromString(PreYggdrasilConverter.func_152719_a(username));
		getData(uuid).setStructuresBanned(flag);
	}

	public static void setStructuresBanned(boolean banned) {
		structuresBanned = banned ? 1 : 0;
		markDirty();
	}

	public static void setWaypointCooldown(int max, int min) {
		max = Math.max(0, max);
		min = Math.max(0, min);
		if (min > max) {
			min = max;
		}
		waypointCooldownMax = max;
		waypointCooldownMin = min;
		markDirty();
		if (!LOTRMod.proxy.isClient()) {
			List players = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
			for (Object player : players) {
				EntityPlayerMP entityplayer = (EntityPlayerMP) player;
				IMessage packet = new LOTRPacketFTCooldown(waypointCooldownMax, waypointCooldownMin);
				LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
			}
		}
	}

	public static boolean structuresBanned() {
		return structuresBanned == 1;
	}
}
