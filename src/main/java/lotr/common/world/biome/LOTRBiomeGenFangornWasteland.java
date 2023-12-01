package lotr.common.world.biome;

import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBlastedLand;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import net.minecraft.block.BlockFalling;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRBiomeGenFangornWasteland extends LOTRBiome {
	public LOTRBiomeGenFangornWasteland(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		npcSpawnList.clear();
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[2];
		arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_HAI, 8);
		arrspawnListContainer[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_WARGS, 1);
		npcSpawnList.newFactionList(100).add(arrspawnListContainer);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[2];
		arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ENTS, 10);
		arrspawnListContainer2[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HUORNS, 20);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer2);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer3 = new LOTRBiomeSpawnList.SpawnListContainer[3];
		arrspawnListContainer3[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 8);
		arrspawnListContainer3[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 1);
		arrspawnListContainer3[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 1).setConquestThreshold(50.0f);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer3);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer4 = new LOTRBiomeSpawnList.SpawnListContainer[3];
		arrspawnListContainer4[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DOL_GULDUR_ORCS, 8);
		arrspawnListContainer4[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRKWOOD_SPIDERS, 1);
		arrspawnListContainer4[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRK_TROLLS, 1).setConquestThreshold(200.0f);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer4);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer5 = new LOTRBiomeSpawnList.SpawnListContainer[2];
		arrspawnListContainer5[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DUNLENDINGS, 10);
		arrspawnListContainer5[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DUNLENDING_WARRIORS, 10);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer5);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer6 = new LOTRBiomeSpawnList.SpawnListContainer[1];
		arrspawnListContainer6[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ROHIRRIM_WARRIORS, 10);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer6);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer7 = new LOTRBiomeSpawnList.SpawnListContainer[4];
		arrspawnListContainer7[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 8);
		arrspawnListContainer7[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 1);
		arrspawnListContainer7[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(50.0f);
		arrspawnListContainer7[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.OLOG_HAI, 1).setConquestThreshold(200.0f);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer7);
		decorator.treesPerChunk = 1;
		decorator.willowPerChunk = 0;
		decorator.logsPerChunk = 3;
		decorator.flowersPerChunk = 1;
		decorator.grassPerChunk = 5;
		decorator.doubleGrassPerChunk = 3;
		decorator.enableFern = true;
		decorator.addTree(LOTRTreeType.CHARRED, 500);
		decorator.addTree(LOTRTreeType.OAK_DEAD, 300);
		decorator.addTree(LOTRTreeType.BEECH_DEAD, 100);
		decorator.addTree(LOTRTreeType.BIRCH_DEAD, 20);
		decorator.addTree(LOTRTreeType.CHARRED_FANGORN, 50);
		decorator.addTree(LOTRTreeType.OAK_FANGORN_DEAD, 30);
		decorator.addTree(LOTRTreeType.BEECH_FANGORN_DEAD, 10);
		setBanditChance(LOTREventSpawner.EventChance.BANDIT_UNCOMMON);
		invasionSpawns.addInvasion(LOTRInvasions.URUK_HAI, LOTREventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(LOTRInvasions.ROHAN, LOTREventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(LOTRInvasions.FANGORN, LOTREventSpawner.EventChance.UNCOMMON);
	}

	@Override
	public boolean canSpawnHostilesInDay() {
		return true;
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
        BlockFalling.fallInstantly = true;
		super.decorate(world, random, i, k);
		if (random.nextInt(60) == 0) {
			int i1 = i + random.nextInt(16) + 8;
			int k1 = k + random.nextInt(16) + 8;
			new LOTRWorldGenBlastedLand(true).generate(world, random, i1, world.getHeightValue(i1, k1), k1);
		}
        BlockFalling.fallInstantly = false;
	}

	@Override
	public LOTRMusicRegion.Sub getBiomeMusic() {
		return LOTRMusicRegion.ISENGARD.getSubregion("fangorn");
	}

	@Override
	public float getTreeIncreaseChance() {
		return 0.25f;
	}

	@Override
	public int spawnCountMultiplier() {
		return 3;
	}
}
