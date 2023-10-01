package lotr.common.world.map;

import com.google.common.math.IntMath;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.*;
import lotr.common.fac.LOTRFaction;
import lotr.common.fac.LOTRFactionRank;
import lotr.common.network.LOTRPacketConquestGrid;
import lotr.common.network.LOTRPacketConquestNotification;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.genlayer.LOTRGenLayerWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.io.File;
import java.util.*;

public class LOTRConquestGrid {
	public static int MAP_GRID_SCALE = IntMath.pow(2, 3);
	public static Map<GridCoordPair, LOTRConquestZone> zoneMap = new HashMap<>();
	public static LOTRConquestZone dummyZone = new LOTRConquestZone(-999, -999).setDummyZone();
	public static boolean needsLoad = true;
	public static Collection<GridCoordPair> dirtyZones = new HashSet<>();
	public static float MIN_CONQUEST;
	public static float MAX_CONQUEST_SET = 100000.0f;
	public static Map<GridCoordPair, List<LOTRFaction>> cachedZoneFactions = new HashMap<>();

	public static boolean anyChangedZones() {
		return !dirtyZones.isEmpty();
	}

	public static ConquestViewableQuery canPlayerViewConquest(EntityPlayer entityplayer, LOTRFaction fac) {
		LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
		LOTRFaction pledged = pd.getPledgeFaction();
		if (pledged != null) {
			if (fac == pledged) {
				return ConquestViewableQuery.canView();
			}
			float align = pd.getAlignment(pledged);
			LOTRFactionRank pledgeRank = pledged.getPledgeRank();
			if (fac.isAlly(pledged) || fac.isBadRelation(pledged)) {
				return ConquestViewableQuery.canView();
			}
			LOTRFactionRank higherRank = pledged.getRankNAbove(pledgeRank, 2);
			if (align >= higherRank.alignment) {
				return ConquestViewableQuery.canView();
			}
			return new ConquestViewableQuery(ConquestViewable.NEED_RANK, higherRank);
		}
		return new ConquestViewableQuery(ConquestViewable.UNPLEDGED, null);
	}

	public static void checkNotifyConquest(LOTRConquestZone zone, EntityPlayer originPlayer, LOTRFaction faction, float newConq, float prevConq, boolean isCleansing) {
		if (MathHelper.floor_double(newConq / 50.0f) != MathHelper.floor_double(prevConq / 50.0f) || newConq == 0.0f && prevConq != newConq) {
			World world = originPlayer.worldObj;
			List playerEntities = world.playerEntities;
			for (Object obj : playerEntities) {
				LOTRFaction pledgeFac;
				EntityPlayerMP player = (EntityPlayerMP) obj;
				LOTRPlayerData pd = LOTRLevelData.getData(player);
				if (player.getDistanceSqToEntity(originPlayer) > 40000.0 || getZoneByEntityCoords(player) != zone) {
					continue;
				}
				boolean playerApplicable;
				playerApplicable = isCleansing ? (pledgeFac = pd.getPledgeFaction()) != null && pledgeFac.isBadRelation(faction) : pd.isPledgedTo(faction);
				if (!playerApplicable) {
					continue;
				}
				IMessage pkt = new LOTRPacketConquestNotification(faction, newConq, isCleansing);
				LOTRPacketHandler.networkWrapper.sendTo(pkt, player);
			}
		}
	}

	public static boolean conquestEnabled(World world) {
		return LOTRConfig.enableConquest && world.getWorldInfo().getTerrainType() != LOTRMod.worldTypeMiddleEarthClassic;
	}

	public static float doRadialConquest(World world, LOTRConquestZone centralZone, EntityPlayer killingPlayer, LOTRFaction pledgeFaction, LOTRFaction enemyFaction, float conqGain, float conqCleanse) {
		if (!centralZone.isDummyZone) {
			float centralConqBonus = 0.0f;
			for (int i1 = -3; i1 <= 3; ++i1) {
				for (int k1 = -3; k1 <= 3; ++k1) {
					float enemyConq;
					int distSq = i1 * i1 + k1 * k1;
					if (distSq > 12.25f) {
						continue;
					}
					int zoneX = centralZone.gridX + i1;
					int zoneZ = centralZone.gridZ + k1;
					float dist = MathHelper.sqrt_float(distSq);
					float frac = 1.0f - dist / 3.5f;
					float conqGainHere = frac * conqGain;
					float conqCleanseHere = frac * conqCleanse;
					LOTRConquestZone zone = getZoneByGridCoords(zoneX, zoneZ);
					if (zone.isDummyZone) {
						continue;
					}
					boolean doneEnemyCleansing = false;
					if (enemyFaction != null && (enemyConq = zone.getConquestStrength(enemyFaction, world)) > 0.0f) {
						zone.addConquestStrength(enemyFaction, -conqCleanseHere, world);
						float newEnemyConq = zone.getConquestStrength(enemyFaction, world);
						if (zone == centralZone) {
							centralConqBonus = newEnemyConq - enemyConq;
						}
						if (killingPlayer != null) {
							checkNotifyConquest(zone, killingPlayer, enemyFaction, newEnemyConq, enemyConq, true);
						}
						doneEnemyCleansing = true;
					}
					if (doneEnemyCleansing || pledgeFaction == null) {
						continue;
					}
					float prevZoneConq = zone.getConquestStrength(pledgeFaction, world);
					zone.addConquestStrength(pledgeFaction, conqGainHere, world);
					float newZoneConq = zone.getConquestStrength(pledgeFaction, world);
					if (zone == centralZone) {
						centralConqBonus = newZoneConq - prevZoneConq;
					}
					if (killingPlayer == null) {
						continue;
					}
					checkNotifyConquest(zone, killingPlayer, pledgeFaction, newZoneConq, prevZoneConq, false);
				}
			}
			return centralConqBonus;
		}
		return 0.0f;
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	public static File getConquestDir() {
		File dir = new File(LOTRLevelData.getOrCreateLOTRDir(), "conquest_zones");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}

	public static ConquestEffective getConquestEffectIn(World world, LOTRConquestZone zone, LOTRFaction theFaction) {
		List<LOTRFaction> cachedFacs;
		GridCoordPair gridCoords;
		if (!LOTRGenLayerWorld.loadedBiomeImage()) {
			new LOTRGenLayerWorld();
		}
		cachedFacs = cachedZoneFactions.get(gridCoords = GridCoordPair.forZone(zone));
		if (cachedFacs == null) {
			Object biome;
			cachedFacs = new ArrayList<>();
			Collection includedBiomes = new ArrayList();
			int[] mapMin = getMinCoordsOnMap(zone);
			int[] mapMax = getMaxCoordsOnMap(zone);
			int mapXMin = mapMin[0];
			int mapXMax = mapMax[0];
			int mapZMin = mapMin[1];
			int mapZMax = mapMax[1];
			for (int i = mapXMin; i < mapXMax; ++i) {
				for (int k = mapZMin; k < mapZMax; ++k) {
					biome = LOTRGenLayerWorld.getBiomeOrOcean(i, k);
					if (includedBiomes.contains(biome)) {
						continue;
					}
					includedBiomes.add(biome);
				}
			}
			block2:
			for (LOTRFaction fac : LOTRFaction.getPlayableAlignmentFactions()) {
				for (LOTRBiome biome2 : (Iterable<LOTRBiome>) includedBiomes) {
					if (!biome2.npcSpawnList.isFactionPresent(world, fac)) {
						continue;
					}
					cachedFacs.add(fac);
					continue block2;
				}
			}
			cachedZoneFactions.put(gridCoords, cachedFacs);
		}
		if (cachedFacs.contains(theFaction)) {
			return ConquestEffective.EFFECTIVE;
		}
		for (LOTRFaction allyFac : theFaction.getConquestBoostRelations()) {
			if (!cachedFacs.contains(allyFac)) {
				continue;
			}
			return ConquestEffective.ALLY_BOOST;
		}
		return ConquestEffective.NO_EFFECT;
	}

	public static float getConquestGainRate(EntityPlayer entityplayer) {
		int i = MathHelper.floor_double(entityplayer.posX);
		BiomeGenBase bgb = entityplayer.worldObj.getBiomeGenForCoords(i, MathHelper.floor_double(entityplayer.posZ));
		if (bgb instanceof LOTRBiome) {
			LOTRBiome biome = (LOTRBiome) bgb;
			return biome.npcSpawnList.conquestGainRate;
		}
		return 1.0f;
	}

	public static int[] getMaxCoordsOnMap(LOTRConquestZone zone) {
		return new int[]{gridToMapCoord(zone.gridX + 1), gridToMapCoord(zone.gridZ + 1)};
	}

	public static int[] getMinCoordsOnMap(LOTRConquestZone zone) {
		return new int[]{gridToMapCoord(zone.gridX), gridToMapCoord(zone.gridZ)};
	}

	public static LOTRConquestZone getZoneByEntityCoords(Entity entity) {
		int i = MathHelper.floor_double(entity.posX);
		int k = MathHelper.floor_double(entity.posZ);
		return getZoneByWorldCoords(i, k);
	}

	public static LOTRConquestZone getZoneByGridCoords(int i, int k) {
		if (i < 0 || i >= MathHelper.ceiling_float_int((float) LOTRGenLayerWorld.imageWidth / MAP_GRID_SCALE) || k < 0 || k >= MathHelper.ceiling_float_int((float) LOTRGenLayerWorld.imageHeight / MAP_GRID_SCALE)) {
			return dummyZone;
		}
		GridCoordPair key = new GridCoordPair(i, k);
		LOTRConquestZone zone = zoneMap.get(key);
		if (zone == null) {
			File zoneDat = getZoneDat(key);
			zone = loadZoneFromFile(zoneDat);
			if (zone == null) {
				zone = new LOTRConquestZone(i, k);
			}
			zoneMap.put(key, zone);
		}
		return zone;
	}

	public static LOTRConquestZone getZoneByWorldCoords(int i, int k) {
		int x = worldToGridX(i);
		int z = worldToGridZ(k);
		return getZoneByGridCoords(x, z);
	}

	public static File getZoneDat(GridCoordPair key) {
		return new File(getConquestDir(), key.gridX + "." + key.gridZ + ".dat");
	}

	public static File getZoneDat(LOTRConquestZone zone) {
		GridCoordPair key = GridCoordPair.forZone(zone);
		return getZoneDat(key);
	}

	public static int gridToMapCoord(int i) {
		return i << 3;
	}

	public static void loadAllZones() {
		try {
			zoneMap.clear();
			dirtyZones.clear();
			File dir = getConquestDir();
			if (dir.exists()) {
				for (File zoneDat : dir.listFiles()) {
					LOTRConquestZone zone;
					if (zoneDat.isDirectory() || !zoneDat.getName().endsWith(".dat") || (zone = loadZoneFromFile(zoneDat)) == null) {
						continue;
					}
					GridCoordPair key = GridCoordPair.forZone(zone);
					zoneMap.put(key, zone);
				}
			}
			needsLoad = false;
			FMLLog.info("LOTR: Loaded %s conquest zones", zoneMap.size());
		} catch (Exception e) {
			FMLLog.severe("Error loading LOTR conquest zones");
			e.printStackTrace();
		}
	}

	public static LOTRConquestZone loadZoneFromFile(File zoneDat) {
		try {
			NBTTagCompound nbt = LOTRLevelData.loadNBTFromFile(zoneDat);
			if (nbt.hasNoTags()) {
				return null;
			}
			LOTRConquestZone zone = LOTRConquestZone.readFromNBT(nbt);
			if (zone.isEmpty()) {
				return null;
			}
			return zone;
		} catch (Exception e) {
			FMLLog.severe("Error loading LOTR conquest zone from %s", zoneDat.getName());
			e.printStackTrace();
			return null;
		}
	}

	public static void markZoneDirty(LOTRConquestZone zone) {
		GridCoordPair key = GridCoordPair.forZone(zone);
		if (zoneMap.containsKey(key)) {
			dirtyZones.add(key);
		}
	}

	public static float onConquestKill(EntityPlayer entityplayer, LOTRFaction pledgeFaction, LOTRFaction enemyFaction, float alignBonus) {
		World world = entityplayer.worldObj;
		if (!world.isRemote && conquestEnabled(world) && LOTRLevelData.getData(entityplayer).getEnableConquestKills() && entityplayer.dimension == LOTRDimension.MIDDLE_EARTH.dimensionID) {
			LOTRConquestZone centralZone = getZoneByEntityCoords(entityplayer);
			float conqAmount = alignBonus * LOTRLevelData.getConquestRate();
			float conqGain = conqAmount * getConquestGainRate(entityplayer);
			float conqCleanse = conqAmount * getConquestGainRate(entityplayer);
			return doRadialConquest(world, centralZone, entityplayer, pledgeFaction, enemyFaction, conqGain, conqCleanse);
		}
		return 0.0f;
	}

	public static void saveChangedZones() {
		try {
			Collection<GridCoordPair> removes = new HashSet<>();
			for (GridCoordPair key : dirtyZones) {
				LOTRConquestZone zone = zoneMap.get(key);
				if (zone == null) {
					continue;
				}
				saveZoneToFile(zone);
				if (!zone.isEmpty()) {
					continue;
				}
				removes.add(key);
			}
			dirtyZones.clear();
			for (GridCoordPair key : removes) {
				zoneMap.remove(key);
			}
		} catch (Exception e) {
			FMLLog.severe("Error saving LOTR conquest zones");
			e.printStackTrace();
		}
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	public static void saveZoneToFile(LOTRConquestZone zone) {
		File zoneDat = getZoneDat(zone);
		try {
			if (zone.isEmpty()) {
				zoneDat.delete();
			} else {
				NBTTagCompound nbt = new NBTTagCompound();
				zone.writeToNBT(nbt);
				LOTRLevelData.saveNBTToFile(zoneDat, nbt);
			}
		} catch (Exception e) {
			FMLLog.severe("Error saving LOTR conquest zone to %s", zoneDat.getName());
			e.printStackTrace();
		}
	}

	public static void sendConquestGridTo(EntityPlayerMP entityplayer, LOTRFaction fac) {
		IMessage pkt = new LOTRPacketConquestGrid(fac, zoneMap.values(), entityplayer.worldObj);
		LOTRPacketHandler.networkWrapper.sendTo(pkt, entityplayer);
		LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
		if (fac == pd.getPledgeFaction()) {
			pd.addAchievement(LOTRAchievement.factionConquest);
		}
	}

	public static void updateZones(World world) {
		MinecraftServer srv;
		if (conquestEnabled(world) && (srv = MinecraftServer.getServer()) != null) {
			int tick = srv.getTickCounter();
			int interval = 6000;
			for (LOTRConquestZone zone : zoneMap.values()) {
				if (zone.isEmpty() || IntMath.mod(tick, interval) != IntMath.mod(zone.hashCode(), interval)) {
					continue;
				}
				zone.checkForEmptiness(world);
			}
		}
	}

	public static int worldToGridX(int i) {
		int mapX = i >> 7;
		return mapX + 810 >> 3;
	}

	public static int worldToGridZ(int k) {
		int mapZ = k >> 7;
		return mapZ + 730 >> 3;
	}

	public enum ConquestEffective {
		EFFECTIVE, ALLY_BOOST, NO_EFFECT

	}

	public enum ConquestViewable {
		UNPLEDGED, CAN_VIEW, NEED_RANK

	}

	public static class ConquestViewableQuery {
		public ConquestViewable result;
		public LOTRFactionRank needRank;

		public ConquestViewableQuery(ConquestViewable res, LOTRFactionRank rank) {
			result = res;
			needRank = rank;
		}

		public static ConquestViewableQuery canView() {
			return new ConquestViewableQuery(ConquestViewable.CAN_VIEW, null);
		}
	}

	public static class GridCoordPair {
		public int gridX;
		public int gridZ;

		public GridCoordPair(int i, int k) {
			gridX = i;
			gridZ = k;
		}

		public static GridCoordPair forZone(LOTRConquestZone zone) {
			return new GridCoordPair(zone.gridX, zone.gridZ);
		}

		@Override
		public boolean equals(Object other) {
			if (this == other) {
				return true;
			}
			if (!(other instanceof GridCoordPair)) {
				return false;
			}
			GridCoordPair otherPair = (GridCoordPair) other;
			return gridX == otherPair.gridX && gridZ == otherPair.gridZ;
		}

		@Override
		public int hashCode() {
			int i = 1664525 * gridX + 1013904223;
			int j = 1664525 * (gridZ ^ 0xDEADBEEF) + 1013904223;
			return i ^ j;
		}
	}

}
