package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.animal.*;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.*;

public class LOTRBiomeGenKanuka extends LOTRBiomeGenFarHarad {
	public static NoiseGeneratorPerlin noisePaths1 = new NoiseGeneratorPerlin(new Random(22L), 1);
	public static NoiseGeneratorPerlin noisePaths2 = new NoiseGeneratorPerlin(new Random(11L), 1);

	public LOTRBiomeGenKanuka(int i, boolean major) {
		super(i, major);
		spawnableLOTRAmbientList.clear();
		spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityBird.class, 10, 4, 4));
		spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityButterfly.class, 10, 4, 4));
		addBiomeVariant(LOTRBiomeVariant.FLOWERS);
		addBiomeVariant(LOTRBiomeVariant.HILLS);
		addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
		enablePodzol = false;
		decorator.treesPerChunk = 0;
		decorator.setTreeCluster(8, 3);
		decorator.vinesPerChunk = 0;
		decorator.flowersPerChunk = 3;
		decorator.doubleFlowersPerChunk = 1;
		decorator.grassPerChunk = 4;
		decorator.doubleGrassPerChunk = 1;
		decorator.enableFern = true;
		decorator.melonPerChunk = 0.0f;
		decorator.clearTrees();
		decorator.addTree(LOTRTreeType.KANUKA, 100);
		biomeColors.setGrass(11915563);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		super.decorate(world, random, i, k);
		for (int count = 0; count < 4; ++count) {
			int k1;
			int i1 = i + random.nextInt(16) + 8;
			int j1 = world.getHeightValue(i1, k1 = k + random.nextInt(16) + 8);
			if (j1 <= 75) {
				continue;
			}
			decorator.genTree(world, random, i1, j1, k1);
		}
		LOTRBiomeVariant variant = ((LOTRWorldChunkManager) world.getWorldChunkManager()).getBiomeVariantAt(i + 8, k + 8);
		int grasses = 12;
		grasses = Math.round(grasses * variant.grassFactor);
		for (int l = 0; l < grasses; ++l) {
			int i1 = i + random.nextInt(16) + 8;
			int j1 = random.nextInt(128);
			int k1 = k + random.nextInt(16) + 8;
			if (world.getHeightValue(i1, k1) <= 75) {
				continue;
			}
			WorldGenerator grassGen = getRandomWorldGenForGrass(random);
			grassGen.generate(world, random, i1, j1, k1);
		}
		int doubleGrasses = 4;
		doubleGrasses = Math.round(doubleGrasses * variant.grassFactor);
		for (int l = 0; l < doubleGrasses; ++l) {
			int i1 = i + random.nextInt(16) + 8;
			int j1 = random.nextInt(128);
			int k1 = k + random.nextInt(16) + 8;
			if (world.getHeightValue(i1, k1) <= 75) {
				continue;
			}
			WorldGenerator grassGen = getRandomWorldGenForDoubleGrass(random);
			grassGen.generate(world, random, i1, j1, k1);
		}
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
		Block topBlock_pre = topBlock;
		int topBlockMeta_pre = topBlockMeta;
		double d1 = noisePaths1.func_151601_a(i * 0.008, k * 0.008);
		double d2 = noisePaths2.func_151601_a(i * 0.008, k * 0.008);
		if (d1 > 0.0 && d1 < 0.1 || d2 > 0.0 && d2 < 0.1) {
			topBlock = LOTRMod.dirtPath;
			topBlockMeta = 1;
		}
		enablePodzol = height > 75;
		super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		enablePodzol = false;
		topBlock = topBlock_pre;
		topBlockMeta = topBlockMeta_pre;
	}

	@Override
	public LOTRMusicRegion.Sub getBiomeMusic() {
		return LOTRMusicRegion.FAR_HARAD.getSubregion("kanuka");
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return super.getChanceToSpawnAnimals() * 0.25f;
	}

	@Override
	public LOTRBiome.GrassBlockAndMeta getRandomGrass(Random random) {
		if (random.nextInt(5) != 0) {
			return new LOTRBiome.GrassBlockAndMeta(Blocks.tallgrass, 2);
		}
		return super.getRandomGrass(random);
	}

	@Override
	public WorldGenerator getRandomWorldGenForDoubleGrass(Random random) {
		WorldGenDoublePlant generator = new WorldGenDoublePlant();
		generator.func_150548_a(3);
		return generator;
	}
}
