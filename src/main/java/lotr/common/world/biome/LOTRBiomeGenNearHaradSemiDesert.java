package lotr.common.world.biome;

import java.util.Random;

import lotr.common.entity.npc.LOTREntityNomadMerchant;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.spawning.*;
import lotr.common.world.structure.LOTRWorldGenHaradObelisk;
import lotr.common.world.structure2.*;
import lotr.common.world.village.LOTRVillageGenHaradNomad;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRBiomeGenNearHaradSemiDesert extends LOTRBiomeGenNearHarad {
	public LOTRBiomeGenNearHaradSemiDesert(int i, boolean major) {
		super(i, major);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[2];
		arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.NOMADS, 20).setSpawnChance(500);
		arrspawnListContainer[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.NOMAD_WARRIORS, 15).setSpawnChance(500);
		npcSpawnList.newFactionList(100, 0.0f).add(arrspawnListContainer);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[1];
		arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.NOMAD_WARRIORS, 10);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer2);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer3 = new LOTRBiomeSpawnList.SpawnListContainer[1];
		arrspawnListContainer3[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer3);
		clearBiomeVariants();
		addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
		addBiomeVariant(LOTRBiomeVariant.STEPPE);
		addBiomeVariant(LOTRBiomeVariant.STEPPE_BARREN);
		addBiomeVariant(LOTRBiomeVariant.HILLS);
		addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
		addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
		addBiomeVariant(LOTRBiomeVariant.SHRUBLAND_OAK);
		decorator.clearTrees();
		decorator.addTree(LOTRTreeType.OAK_DEAD, 500);
		decorator.addTree(LOTRTreeType.OAK_DESERT, 500);
		decorator.grassPerChunk = 5;
		decorator.doubleGrassPerChunk = 0;
		decorator.cactiPerChunk = 1;
		decorator.deadBushPerChunk = 1;
		decorator.clearRandomStructures();
		decorator.addRandomStructure(new LOTRWorldGenHaradObelisk(false), 2000);
		decorator.addRandomStructure(new LOTRWorldGenHaradPyramid(false), 4000);
		decorator.addRandomStructure(new LOTRWorldGenStoneRuin.NEAR_HARAD(1, 4), 1000);
		decorator.addRandomStructure(new LOTRWorldGenMoredainMercCamp(false), 2000);
		decorator.addRandomStructure(new LOTRWorldGenMumakSkeleton(false), 2000);
		decorator.addRandomStructure(new LOTRWorldGenHaradRuinedFort(false), 3000);
		decorator.clearVillages();
		decorator.addVillage(new LOTRVillageGenHaradNomad(this, 0.5f));
		registerTravellingTrader(LOTREntityNomadMerchant.class);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		int j1;
		int i1;
		int k1;
		super.decorate(world, random, i, k);
		if (random.nextInt(20) == 0 && world.getBlock(i1 = i + random.nextInt(16) + 8, j1 = world.getHeightValue(i1, k1 = k + random.nextInt(16) + 8) - 1, k1) == Blocks.sand) {
			world.setBlock(i1, j1, k1, Blocks.dirt);
			LOTRTreeType treeType = LOTRTreeType.OAK_DESERT;
			WorldGenAbstractTree tree = treeType.create(false, random);
			if (!tree.generate(world, random, i1, j1 + 1, k1)) {
				world.setBlock(i1, j1, k1, Blocks.sand);
			}
		}
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
		Block topBlock_pre = topBlock;
		int topBlockMeta_pre = topBlockMeta;
		Block fillerBlock_pre = fillerBlock;
		int fillerBlockMeta_pre = fillerBlockMeta;
		double d1 = biomeTerrainNoise.func_151601_a(i * 0.08, k * 0.08);
		if (d1 + biomeTerrainNoise.func_151601_a(i * 0.3, k * 0.3) > 0.3) {
			topBlock = Blocks.dirt;
			topBlockMeta = 1;
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
	public float getTreeIncreaseChance() {
		return 0.05f;
	}
}
