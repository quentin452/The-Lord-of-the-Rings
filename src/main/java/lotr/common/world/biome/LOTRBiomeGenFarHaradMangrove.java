package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class LOTRBiomeGenFarHaradMangrove extends LOTRBiomeGenFarHarad {
	public LOTRBiomeGenFarHaradMangrove(int i, boolean major) {
		super(i, major);
		topBlock = Blocks.sand;
		spawnableWaterCreatureList.clear();
		spawnableLOTRAmbientList.clear();
		decorator.addSoil(new WorldGenMinable(Blocks.dirt, 1, 60, Blocks.sand), 12.0f, 60, 90);
		decorator.quagmirePerChunk = 1;
		decorator.treesPerChunk = 5;
		decorator.vinesPerChunk = 20;
		decorator.grassPerChunk = 8;
		decorator.enableFern = true;
		decorator.waterlilyPerChunk = 3;
		decorator.addTree(LOTRTreeType.MANGROVE, 1000);
		decorator.addTree(LOTRTreeType.ACACIA, 10);
		decorator.addTree(LOTRTreeType.OAK_DESERT, 5);
		registerSwampFlowers();
		biomeColors.setGrass(10466679);
		biomeColors.setFoliage(6715206);
		biomeColors.setWater(5985085);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		int i1;
		int l;
		super.decorate(world, random, i, k);
		for (l = 0; l < 2; ++l) {
			int k1;
			int j1;
			i1 = i + random.nextInt(16);
			if (!world.getBlock(i1, (j1 = world.getTopSolidOrLiquidBlock(i1, k1 = k + random.nextInt(16))) - 1, k1).isOpaqueCube() || world.getBlock(i1, j1, k1).getMaterial() != Material.water) {
				continue;
			}
			decorator.genTree(world, random, i1, j1, k1);
		}
		for (l = 0; l < 20; ++l) {
			int j1;
			int k1;
			i1 = i + random.nextInt(16);
			if (!world.getBlock(i1, j1 = 50 + random.nextInt(15), k1 = k + random.nextInt(16)).isOpaqueCube() || world.getBlock(i1, j1 + 1, k1).getMaterial() != Material.water) {
				continue;
			}
			int height = 2 + random.nextInt(3);
			for (int j2 = j1; j2 <= j1 + height; ++j2) {
				world.setBlock(i1, j2, k1, LOTRMod.wood3, 3, 2);
			}
		}
	}

	@Override
	public LOTRMusicRegion.Sub getBiomeMusic() {
		return LOTRMusicRegion.FAR_HARAD.getSubregion("mangrove");
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.4f;
	}

	@Override
	public float getTreeIncreaseChance() {
		return 0.15f;
	}
}
