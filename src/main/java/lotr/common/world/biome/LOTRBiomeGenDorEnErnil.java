package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.animal.LOTREntitySwan;
import lotr.common.entity.npc.*;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure.LOTRWorldGenGondorRuin;
import lotr.common.world.structure.LOTRWorldGenGondorRuins;
import lotr.common.world.structure2.LOTRWorldGenDolAmrothWatchfort;
import lotr.common.world.structure2.LOTRWorldGenGondorObelisk;
import lotr.common.world.structure2.LOTRWorldGenGondorStructure;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.village.LOTRVillageGenGondor;
import net.minecraft.block.BlockFalling;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

public class LOTRBiomeGenDorEnErnil extends LOTRBiome {
	public LOTRBiomeGenDorEnErnil(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityHorse.class, 30, 2, 6));
		spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntitySwan.class, 20, 4, 8));
		npcSpawnList.clear();
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[1];
		arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DOL_AMROTH_SOLDIERS, 10).setSpawnChance(30);
		npcSpawnList.newFactionList(100).add(arrspawnListContainer);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[6];
		arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 20);
		arrspawnListContainer2[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_BOMBARDIERS, 1);
		arrspawnListContainer2[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 4).setConquestThreshold(50.0f);
		arrspawnListContainer2[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 1).setConquestThreshold(50.0f);
		arrspawnListContainer2[4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(100.0f);
		arrspawnListContainer2[5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.OLOG_HAI, 1).setConquestThreshold(200.0f);
		npcSpawnList.newFactionList(1).add(arrspawnListContainer2);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer3 = new LOTRBiomeSpawnList.SpawnListContainer[2];
		arrspawnListContainer3[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_HAI, 10);
		arrspawnListContainer3[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_WARGS, 2).setConquestThreshold(50.0f);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer3);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer4 = new LOTRBiomeSpawnList.SpawnListContainer[8];
		arrspawnListContainer4[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HARNEDHRIM, 2).setConquestThreshold(100.0f);
		arrspawnListContainer4[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HARNEDOR_WARRIORS, 10);
		arrspawnListContainer4[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.COAST_SOUTHRONS, 2).setConquestThreshold(100.0f);
		arrspawnListContainer4[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SOUTHRON_WARRIORS, 10);
		arrspawnListContainer4[4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.UMBARIANS, 2).setConquestThreshold(100.0f);
		arrspawnListContainer4[5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.UMBAR_SOLDIERS, 10);
		arrspawnListContainer4[6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.CORSAIRS, 20);
		arrspawnListContainer4[7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_RENEGADES, 10);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer4);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer5 = new LOTRBiomeSpawnList.SpawnListContainer[3];
		arrspawnListContainer5[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLINGS, 2).setConquestThreshold(100.0f);
		arrspawnListContainer5[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_WARRIORS, 10);
		arrspawnListContainer5[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_GOLD_WARRIORS, 2).setConquestThreshold(50.0f);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer5);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer6 = new LOTRBiomeSpawnList.SpawnListContainer[1];
		arrspawnListContainer6[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HALF_TROLLS, 10);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer6);
		npcSpawnList.conquestGainRate = 0.5f;
		addBiomeVariantSet(LOTRBiomeVariant.SET_NORMAL);
		addBiomeVariant(LOTRBiomeVariant.SHRUBLAND_OAK);
		addBiomeVariant(LOTRBiomeVariant.DENSEFOREST_BIRCH);
		addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.5f);
		addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.5f);
		addBiomeVariant(LOTRBiomeVariant.ORCHARD_APPLE_PEAR, 0.5f);
		addBiomeVariant(LOTRBiomeVariant.ORCHARD_ORANGE, 0.1f);
		addBiomeVariant(LOTRBiomeVariant.ORCHARD_LEMON, 0.1f);
		addBiomeVariant(LOTRBiomeVariant.ORCHARD_LIME, 0.1f);
		addBiomeVariant(LOTRBiomeVariant.ORCHARD_OLIVE, 0.1f);
		addBiomeVariant(LOTRBiomeVariant.ORCHARD_ALMOND, 0.1f);
		addBiomeVariant(LOTRBiomeVariant.ORCHARD_PLUM, 0.1f);
		decorator.addSoil(new WorldGenMinable(LOTRMod.rock, 1, 60, Blocks.stone), 2.0f, 0, 64);
		decorator.treesPerChunk = 0;
		decorator.setTreeCluster(10, 30);
		decorator.willowPerChunk = 1;
		decorator.flowersPerChunk = 4;
		decorator.grassPerChunk = 8;
		decorator.doubleGrassPerChunk = 4;
		decorator.generateAthelas = true;
		decorator.whiteSand = true;
		decorator.addTree(LOTRTreeType.BIRCH, 200);
		decorator.addTree(LOTRTreeType.BIRCH_TALL, 200);
		decorator.addTree(LOTRTreeType.BIRCH_LARGE, 200);
		decorator.addTree(LOTRTreeType.OAK, 100);
		decorator.addTree(LOTRTreeType.OAK_TALL, 100);
		decorator.addTree(LOTRTreeType.OAK_LARGE, 100);
		decorator.addTree(LOTRTreeType.CEDAR, 100);
		decorator.addTree(LOTRTreeType.CYPRESS, 50);
		decorator.addTree(LOTRTreeType.CYPRESS_LARGE, 10);
		decorator.addTree(LOTRTreeType.APPLE, 5);
		decorator.addTree(LOTRTreeType.PEAR, 5);
		decorator.addTree(LOTRTreeType.LEMON, 5);
		decorator.addTree(LOTRTreeType.ORANGE, 5);
		decorator.addTree(LOTRTreeType.LIME, 5);
		decorator.addTree(LOTRTreeType.OLIVE, 5);
		decorator.addTree(LOTRTreeType.OLIVE_LARGE, 2);
		decorator.addTree(LOTRTreeType.ALMOND, 5);
		decorator.addTree(LOTRTreeType.PLUM, 5);
		registerPlainsFlowers();
		decorator.addVillage(new LOTRVillageGenGondor(this, LOTRWorldGenGondorStructure.GondorFiefdom.DOL_AMROTH, 0.75f));
		decorator.addRandomStructure(new LOTRWorldGenDolAmrothWatchfort(false), 800);
		decorator.addRandomStructure(new LOTRWorldGenGondorObelisk(false), 1000);
		decorator.addRandomStructure(new LOTRWorldGenGondorRuin(false), 1000);
		decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 600);
		decorator.addRandomStructure(new LOTRWorldGenGondorRuins(), 600);
		registerTravellingTrader(LOTREntityGaladhrimTrader.class);
		registerTravellingTrader(LOTREntityScrapTrader.class);
		registerTravellingTrader(LOTREntityDorwinionMerchantElf.class);
		registerTravellingTrader(LOTREntityDaleMerchant.class);
		registerTravellingTrader(LOTREntityDorwinionMerchantMan.class);
		setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
		invasionSpawns.addInvasion(LOTRInvasions.MORDOR, LOTREventSpawner.EventChance.RARE);
		invasionSpawns.addInvasion(LOTRInvasions.MORDOR_BLACK_URUK, LOTREventSpawner.EventChance.RARE);
		invasionSpawns.addInvasion(LOTRInvasions.NEAR_HARAD_HARNEDOR, LOTREventSpawner.EventChance.RARE);
		invasionSpawns.addInvasion(LOTRInvasions.NEAR_HARAD_COAST, LOTREventSpawner.EventChance.RARE);
		invasionSpawns.addInvasion(LOTRInvasions.NEAR_HARAD_UMBAR, LOTREventSpawner.EventChance.RARE);
		invasionSpawns.addInvasion(LOTRInvasions.NEAR_HARAD_CORSAIR, LOTREventSpawner.EventChance.UNCOMMON);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
        BlockFalling.fallInstantly = true;
		super.decorate(world, random, i, k);
		if (random.nextInt(24) == 0) {
			int i1 = i + random.nextInt(16) + 8;
			int j1 = random.nextInt(128);
			int k1 = k + random.nextInt(16) + 8;
			new WorldGenFlowers(LOTRMod.pipeweedPlant).generate(world, random, i1, j1, k1);
		}
        BlockFalling.fallInstantly = false;
	}

	@Override
	public LOTRAchievement getBiomeAchievement() {
		return LOTRAchievement.enterDorEnErnil;
	}

	@Override
	public LOTRMusicRegion.Sub getBiomeMusic() {
		return LOTRMusicRegion.GONDOR.getSubregion("dolAmroth");
	}

	@Override
	public LOTRWaypoint.Region getBiomeWaypoints() {
		return LOTRWaypoint.Region.DOR_EN_ERNIL;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.5f;
	}

	@Override
	public LOTRRoadType getRoadBlock() {
		return LOTRRoadType.DOL_AMROTH;
	}

	@Override
	public float getTreeIncreaseChance() {
		return 0.03f;
	}

	@Override
	public int spawnCountMultiplier() {
		return 4;
	}
}
