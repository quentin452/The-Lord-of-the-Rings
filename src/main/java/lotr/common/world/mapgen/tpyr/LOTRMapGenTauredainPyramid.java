package lotr.common.world.mapgen.tpyr;

import lotr.common.LOTRDimension;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.LOTRBiomeGenFarHaradJungle;
import lotr.common.world.biome.LOTRBiomeGenTauredainClearing;
import lotr.common.world.village.LOTRVillagePositionCache;
import lotr.common.world.village.LocationInfo;
import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureStart;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LOTRMapGenTauredainPyramid extends MapGenStructure {
	public static List spawnBiomes;
	public static int minDist = 12;
	public static int separation = 24;

	public int spawnChance = 10;

	@Override
	public boolean canSpawnStructureAtCoords(int i, int k) {
		LOTRWorldChunkManager worldChunkMgr = (LOTRWorldChunkManager) worldObj.getWorldChunkManager();
		LOTRVillagePositionCache cache = worldChunkMgr.getStructureCache(this);
		LocationInfo cacheLocation = cache.getLocationAt(i, k);
		if (cacheLocation != null) {
			return cacheLocation.isPresent();
		}
		setupSpawnBiomes();
		int i2 = MathHelper.floor_double((double) i / separation);
		int k2 = MathHelper.floor_double((double) k / separation);
		Random dRand = worldObj.setRandomSeed(i2, k2, 190169976);
		i2 *= separation;
		k2 *= separation;
		i2 += dRand.nextInt(separation - minDist + 1);
		if (i == i2 && k == k2 + dRand.nextInt(separation - minDist + 1)) {
			int i1 = i * 16 + 8;
			int k1 = k * 16 + 8;
			if (worldObj.getWorldChunkManager().areBiomesViable(i1, k1, 0, spawnBiomes) && rand.nextInt(spawnChance) == 0) {
				return cache.markResult(i, k, LocationInfo.RANDOM_GEN_HERE).isPresent();
			}
		}
		return cache.markResult(i, k, LocationInfo.NONE_HERE).isPresent();
	}

	@Override
	public String func_143025_a() {
		return "LOTR.TPyr";
	}

	@Override
	public StructureStart getStructureStart(int i, int j) {
		return new LOTRStructureTPyrStart(worldObj, rand, i, j);
	}

	public static void register() {
		MapGenStructureIO.registerStructure(LOTRStructureTPyrStart.class, "LOTR.TPyr");
		MapGenStructureIO.func_143031_a(LOTRComponentTauredainPyramid.class, "LOTR.TPyr.Pyramid");
	}

	public static void setupSpawnBiomes() {
		if (spawnBiomes == null) {
			spawnBiomes = new ArrayList();
			for (LOTRBiome biome : LOTRDimension.MIDDLE_EARTH.biomeList) {
				boolean flag = biome instanceof LOTRBiomeGenFarHaradJungle && !(biome instanceof LOTRBiomeGenTauredainClearing);
				if (!flag) {
					continue;
				}
				spawnBiomes.add(biome);
			}
		}
	}
}
