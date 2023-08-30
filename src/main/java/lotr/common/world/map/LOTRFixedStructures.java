package lotr.common.world.map;

import lotr.common.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public enum LOTRFixedStructures {
	SPAWN(809.5, 729.5), UTUMNO_ENTRANCE(1139.0, 394.0), MORDOR_CHERRY_TREE(1630.0, 1170.0);

	public static long nanoTimeElapsed;
	public int xCoord;
	public int zCoord;

	LOTRFixedStructures(double x, double z) {
		xCoord = LOTRWaypoint.mapToWorldX(x);
		zCoord = LOTRWaypoint.mapToWorldZ(z);
	}

	public double distanceSqTo(EntityLivingBase entity) {
		double dx = entity.posX - (xCoord + 0.5);
		double dz = entity.posZ - (zCoord + 0.5);
		return dx * dx + dz * dz;
	}

	public boolean isAt(World world, int x, int z) {
		if (!hasMapFeatures(world)) {
			return false;
		}
		return xCoord == x && zCoord == z;
	}

	public static boolean[] _mountainNear_structureNear(World world, int x, int z) {
		long l = System.nanoTime();
		boolean mountainNear = false;
		boolean structureNear = false;
		if (hasMapFeatures(world)) {
			if (LOTRMountains.mountainAt(x, z)) {
				mountainNear = true;
			}
			structureNear = structureNear(world, x, z, 256);
			if (!structureNear) {
				for (LOTRWaypoint wp : LOTRWaypoint.values()) {
					double dz;
					double range;
					double dx = x - wp.getXCoord();
					double distSq = dx * dx + (dz = z - wp.getZCoord()) * dz;
					range = 256.0;
					if (distSq >= range * range) {
						continue;
					}
					structureNear = true;
					break;
				}
			}
			if (!structureNear && LOTRRoads.isRoadNear(x, z, 32) >= 0.0f) {
				structureNear = true;
			}
		}
		nanoTimeElapsed += System.nanoTime() - l;
		return new boolean[]{mountainNear, structureNear};
	}

	public static boolean generatesAt(int i, int k, int x, int z) {
		return i >> 4 == x >> 4 && k >> 4 == z >> 4;
	}

	public static boolean generatesAtMapImageCoords(int i, int k, int x, int z) {
		x = LOTRWaypoint.mapToWorldX(x);
		z = LOTRWaypoint.mapToWorldZ(z);
		return generatesAt(i, k, x, z);
	}

	public static boolean hasMapFeatures(World world) {
		if (!LOTRConfig.generateMapFeatures) {
			return false;
		}
		return world.getWorldInfo().getTerrainType() != LOTRMod.worldTypeMiddleEarthClassic;
	}

	public static boolean structureNear(World world, int x, int z, int range) {
		for (LOTRFixedStructures str : values()) {
			double dx = x - str.xCoord;
			double dz = z - str.zCoord;
			double distSq = dx * dx + dz * dz;
			double rangeSq = range * range;
			if (distSq >= rangeSq) {
				continue;
			}
			return true;
		}
		return false;
	}
}
