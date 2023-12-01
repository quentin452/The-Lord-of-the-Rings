package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTREventSpawner;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class LOTRBiomeGenMeneltarma extends LOTRBiomeGenOcean {
	public WorldGenerator boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 4);

	public LOTRBiomeGenMeneltarma(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableLOTRAmbientList.clear();
		decorator.setTreeCluster(8, 20);
		decorator.treesPerChunk = 0;
		decorator.flowersPerChunk = 5;
		decorator.doubleFlowersPerChunk = 1;
		decorator.grassPerChunk = 6;
		decorator.doubleGrassPerChunk = 2;
		decorator.generateAthelas = true;
		decorator.clearTrees();
		decorator.addTree(LOTRTreeType.CEDAR, 1000);
		decorator.addTree(LOTRTreeType.CEDAR_LARGE, 500);
		decorator.addTree(LOTRTreeType.OAK, 200);
		decorator.addTree(LOTRTreeType.OAK_TALL, 200);
		decorator.addTree(LOTRTreeType.OAK_LARGE, 400);
		decorator.addTree(LOTRTreeType.BIRCH, 200);
		decorator.addTree(LOTRTreeType.BIRCH_TALL, 200);
		decorator.addTree(LOTRTreeType.BIRCH_LARGE, 400);
		decorator.addTree(LOTRTreeType.BEECH, 200);
		decorator.addTree(LOTRTreeType.BEECH_LARGE, 400);
		Collection flowerDupes = new ArrayList();
		for (int l = 0; l < 10; ++l) {
			flowers.clear();
			registerPlainsFlowers();
			flowerDupes.addAll(flowers);
		}
		flowers.clear();
		flowers.addAll(flowerDupes);
		addFlower(LOTRMod.athelas, 0, 10);
		decorator.clearRandomStructures();
		setBanditChance(LOTREventSpawner.EventChance.NEVER);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
        BlockFalling.fallInstantly = true;
		int k1;
		super.decorate(world, random, i, k);
		if (random.nextInt(2) == 0) {
			for (int l = 0; l < 3; ++l) {
				int i1 = i + random.nextInt(16) + 8;
				k1 = k + random.nextInt(16) + 8;
				boulderGen.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
			}
		}
		if (random.nextInt(3) == 0) {
			int i1 = i + random.nextInt(16) + 8;
			int j1 = random.nextInt(128);
			k1 = k + random.nextInt(16) + 8;
			new WorldGenFlowers(LOTRMod.athelas).generate(world, random, i1, j1, k1);
		}
        BlockFalling.fallInstantly = false;
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
		Block topBlock_pre = topBlock;
		int topBlockMeta_pre = topBlockMeta;
		Block fillerBlock_pre = fillerBlock;
		int fillerBlockMeta_pre = fillerBlockMeta;
		double d1 = biomeTerrainNoise.func_151601_a(i * 0.1, k * 0.1);
		if (d1 > -0.1) {
			topBlock = Blocks.stone;
			topBlockMeta = 0;
			fillerBlock = topBlock;
			fillerBlockMeta = topBlockMeta;
		}
		super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		topBlock = topBlock_pre;
		topBlockMeta = topBlockMeta_pre;
		fillerBlock = fillerBlock_pre;
		fillerBlockMeta = fillerBlockMeta_pre;
	}

	@Override
	public LOTRAchievement getBiomeAchievement() {
		return LOTRAchievement.enterMeneltarma;
	}

	@Override
	public LOTRMusicRegion.Sub getBiomeMusic() {
		return LOTRMusicRegion.SEA.getSubregion("meneltarma");
	}

	@Override
	public LOTRWaypoint.Region getBiomeWaypoints() {
		return LOTRWaypoint.Region.MENELTARMA;
	}

	@Override
	public float getTreeIncreaseChance() {
		return 0.2f;
	}

	@Override
	public boolean isHiddenBiome() {
		return true;
	}
}
