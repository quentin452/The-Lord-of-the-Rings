package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityGorcrow;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure2.*;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class LOTRBiomeGenDolGuldur extends LOTRBiomeGenMirkwoodCorrupted {
	public LOTRBiomeGenDolGuldur(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityGorcrow.class, 8, 4, 4));
		npcSpawnList.clear();
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[3];
		arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRKWOOD_SPIDERS, 20);
		arrspawnListContainer[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DOL_GULDUR_ORCS, 30);
		arrspawnListContainer[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRK_TROLLS, 5);
		npcSpawnList.newFactionList(100).add(arrspawnListContainer);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[4];
		arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WOOD_ELF_WARRIORS, 10);
		arrspawnListContainer2[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WOOD_ELVES, 5).setConquestThreshold(50.0f);
		arrspawnListContainer2[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WOOD_ELVES, 5).setConquestThreshold(200.0f);
		arrspawnListContainer2[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WOOD_ELVES, 5).setConquestThreshold(400.0f);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer2);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer3 = new LOTRBiomeSpawnList.SpawnListContainer[2];
		arrspawnListContainer3[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GALADHRIM_WARRIORS, 10);
		arrspawnListContainer3[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GALADHRIM_WARDENS, 3);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer3);
		npcSpawnList.conquestGainRate = 0.2f;
		decorator.addOre(new WorldGenMinable(LOTRMod.oreMorgulIron, 8), 20.0f, 0, 64);
		decorator.addOre(new WorldGenMinable(LOTRMod.oreGulduril, 8), 8.0f, 0, 32);
		decorator.treesPerChunk = 1;
		decorator.vinesPerChunk = 2;
		decorator.flowersPerChunk = 0;
		decorator.grassPerChunk = 6;
		decorator.doubleGrassPerChunk = 1;
		decorator.clearTrees();
		decorator.addTree(LOTRTreeType.MIRK_OAK, 200);
		decorator.addTree(LOTRTreeType.MIRK_OAK_DEAD, 1000);
		biomeColors.setGrass(3032113);
		biomeColors.setFoliage(3032113);
		biomeColors.setSky(4343633);
		biomeColors.setClouds(2632757);
		biomeColors.setFoggy(true);
		decorator.clearRandomStructures();
		decorator.addRandomStructure(new LOTRWorldGenStoneRuin.DOL_GULDUR(1, 4), 5);
		decorator.addRandomStructure(new LOTRWorldGenDolGuldurAltar(false), 160);
		decorator.addRandomStructure(new LOTRWorldGenDolGuldurTower(false), 80);
		decorator.addRandomStructure(new LOTRWorldGenDolGuldurCamp(false), 50);
		decorator.addRandomStructure(new LOTRWorldGenDolGuldurSpiderPit(false), 50);
		decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 400);
	}

	@Override
	public boolean canSpawnHostilesInDay() {
		return true;
	}

	@Override
	public LOTRAchievement getBiomeAchievement() {
		return LOTRAchievement.enterDolGuldur;
	}

	@Override
	public LOTRMusicRegion.Sub getBiomeMusic() {
		return LOTRMusicRegion.MIRKWOOD.getSubregion("dolGuldur");
	}

	@Override
	public float getTreeIncreaseChance() {
		return 0.25f;
	}
}
