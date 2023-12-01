package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityDeer;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityGaladhrimTrader;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure.LOTRWorldGenGondorRuins;
import lotr.common.world.structure.LOTRWorldGenRuinedGondorTower;
import lotr.common.world.structure2.LOTRWorldGenGondorObelisk;
import lotr.common.world.structure2.LOTRWorldGenIthilienHideout;
import lotr.common.world.structure2.LOTRWorldGenRuinedBeaconTower;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import net.minecraft.block.BlockFalling;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

public class LOTRBiomeGenIthilien extends LOTRBiome {
	public LOTRBiomeGenIthilien(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityHorse.class, 5, 2, 6));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityDeer.class, 10, 4, 6));
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[1];
		arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_ITHILIEN, 3).setSpawnChance(100);
		npcSpawnList.newFactionList(10).add(arrspawnListContainer);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[10];
		arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10);
		arrspawnListContainer2[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_ITHILIEN, 10);
		arrspawnListContainer2[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.LOSSARNACH_SOLDIERS, 4);
		arrspawnListContainer2[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.LEBENNIN_SOLDIERS, 4).setConquestThreshold(50.0f);
		arrspawnListContainer2[4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.LAMEDON_HILLMEN, 1).setConquestThreshold(50.0f);
		arrspawnListContainer2[5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.LAMEDON_SOLDIERS, 1).setConquestThreshold(50.0f);
		arrspawnListContainer2[6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACKROOT_SOLDIERS, 1).setConquestThreshold(50.0f);
		arrspawnListContainer2[7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.PINNATH_GELIN_SOLDIERS, 1).setConquestThreshold(50.0f);
		arrspawnListContainer2[8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DOL_AMROTH_SOLDIERS, 2).setConquestThreshold(100.0f);
		arrspawnListContainer2[9] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_MEN, 10).setConquestThreshold(500.0f);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer2);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer3 = new LOTRBiomeSpawnList.SpawnListContainer[6];
		arrspawnListContainer3[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
		arrspawnListContainer3[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_BOMBARDIERS, 1);
		arrspawnListContainer3[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 2);
		arrspawnListContainer3[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(50.0f);
		arrspawnListContainer3[4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_SPIDERS, 1).setConquestThreshold(100.0f);
		arrspawnListContainer3[5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.OLOG_HAI, 1).setConquestThreshold(150.0f);
		npcSpawnList.newFactionList(90).add(arrspawnListContainer3);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer4 = new LOTRBiomeSpawnList.SpawnListContainer[2];
		arrspawnListContainer4[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_HAI, 10);
		arrspawnListContainer4[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_WARGS, 2).setConquestThreshold(50.0f);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer4);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer5 = new LOTRBiomeSpawnList.SpawnListContainer[1];
		arrspawnListContainer5[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ROHIRRIM_WARRIORS, 10);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer5);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer6 = new LOTRBiomeSpawnList.SpawnListContainer[4];
		arrspawnListContainer6[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HARNEDHRIM, 2).setConquestThreshold(100.0f);
		arrspawnListContainer6[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HARNEDOR_WARRIORS, 10);
		arrspawnListContainer6[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.COAST_SOUTHRONS, 2).setConquestThreshold(100.0f);
		arrspawnListContainer6[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SOUTHRON_WARRIORS, 10);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer6);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer7 = new LOTRBiomeSpawnList.SpawnListContainer[1];
		arrspawnListContainer7[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH_WARRIORS, 10);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer7);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer8 = new LOTRBiomeSpawnList.SpawnListContainer[3];
		arrspawnListContainer8[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLINGS, 2).setConquestThreshold(100.0f);
		arrspawnListContainer8[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_WARRIORS, 10);
		arrspawnListContainer8[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_GOLD_WARRIORS, 2).setConquestThreshold(50.0f);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer8);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer9 = new LOTRBiomeSpawnList.SpawnListContainer[1];
		arrspawnListContainer9[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HALF_TROLLS, 10);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer9);
		variantChance = 0.7f;
		addBiomeVariantSet(LOTRBiomeVariant.SET_NORMAL_OAK_NOSTEPPE);
		addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT, 4.0f);
		addBiomeVariant(LOTRBiomeVariant.FOREST, 2.0f);
		addBiomeVariant(LOTRBiomeVariant.SHRUBLAND_OAK, 4.0f);
		addBiomeVariant(LOTRBiomeVariant.SCRUBLAND, 3.0f);
		addBiomeVariant(LOTRBiomeVariant.DENSEFOREST_LEBETHRON);
		addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.5f);
		addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.5f);
		addBiomeVariant(LOTRBiomeVariant.ORCHARD_APPLE_PEAR, 0.2f);
		addBiomeVariant(LOTRBiomeVariant.ORCHARD_PLUM, 0.2f);
		addBiomeVariant(LOTRBiomeVariant.ORCHARD_OLIVE, 0.1f);
		decorator.addSoil(new WorldGenMinable(LOTRMod.rock, 1, 60, Blocks.stone), 2.0f, 0, 64);
		decorator.treesPerChunk = 0;
		decorator.willowPerChunk = 2;
		decorator.logsPerChunk = 1;
		decorator.flowersPerChunk = 4;
		decorator.doubleFlowersPerChunk = 4;
		decorator.grassPerChunk = 10;
		decorator.doubleGrassPerChunk = 4;
		decorator.waterlilyPerChunk = 2;
		decorator.generateAthelas = true;
		decorator.addTree(LOTRTreeType.OAK, 500);
		decorator.addTree(LOTRTreeType.OAK_TALL, 100);
		decorator.addTree(LOTRTreeType.OAK_LARGE, 200);
		decorator.addTree(LOTRTreeType.LEBETHRON, 100);
		decorator.addTree(LOTRTreeType.LEBETHRON_LARGE, 50);
		decorator.addTree(LOTRTreeType.BIRCH, 150);
		decorator.addTree(LOTRTreeType.BIRCH_LARGE, 50);
		decorator.addTree(LOTRTreeType.CEDAR, 200);
		decorator.addTree(LOTRTreeType.CHESTNUT, 100);
		decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 50);
		decorator.addTree(LOTRTreeType.PINE, 100);
		decorator.addTree(LOTRTreeType.CYPRESS, 100);
		decorator.addTree(LOTRTreeType.CYPRESS_LARGE, 10);
		decorator.addTree(LOTRTreeType.APPLE, 5);
		decorator.addTree(LOTRTreeType.PEAR, 5);
		decorator.addTree(LOTRTreeType.OLIVE, 5);
		decorator.addTree(LOTRTreeType.OLIVE_LARGE, 5);
		decorator.addTree(LOTRTreeType.ALMOND, 15);
		registerForestFlowers();
		addFlower(LOTRMod.asphodel, 0, 10);
		addFlower(Blocks.red_flower, 2, 5);
		decorator.generateOrcDungeon = true;
		decorator.addRandomStructure(new LOTRWorldGenRuinedBeaconTower(false), 200);
		decorator.addRandomStructure(new LOTRWorldGenGondorRuins(), 500);
		decorator.addRandomStructure(new LOTRWorldGenRuinedGondorTower(false), 500);
		decorator.addRandomStructure(new LOTRWorldGenGondorObelisk(false), 800);
		decorator.addRandomStructure(new LOTRWorldGenIthilienHideout(false), 50);
		decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 400);
		registerTravellingTrader(LOTREntityGaladhrimTrader.class);
		registerTravellingTrader(LOTREntityScrapTrader.class);
		setBanditChance(LOTREventSpawner.EventChance.BANDIT_UNCOMMON);
		invasionSpawns.addInvasion(LOTRInvasions.GONDOR, LOTREventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(LOTRInvasions.GONDOR_ITHILIEN, LOTREventSpawner.EventChance.COMMON);
		invasionSpawns.addInvasion(LOTRInvasions.GONDOR_DOL_AMROTH, LOTREventSpawner.EventChance.RARE);
		invasionSpawns.addInvasion(LOTRInvasions.MORDOR, LOTREventSpawner.EventChance.COMMON);
		invasionSpawns.addInvasion(LOTRInvasions.MORDOR_BLACK_URUK, LOTREventSpawner.EventChance.RARE);
		invasionSpawns.addInvasion(LOTRInvasions.MORDOR_NAN_UNGOL, LOTREventSpawner.EventChance.RARE);
		invasionSpawns.addInvasion(LOTRInvasions.MORDOR_WARG, LOTREventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(LOTRInvasions.NEAR_HARAD_HARNEDOR, LOTREventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(LOTRInvasions.NEAR_HARAD_COAST, LOTREventSpawner.EventChance.RARE);
		invasionSpawns.addInvasion(LOTRInvasions.NEAR_HARAD_CORSAIR, LOTREventSpawner.EventChance.RARE);
		invasionSpawns.addInvasion(LOTRInvasions.RHUN, LOTREventSpawner.EventChance.UNCOMMON);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
        BlockFalling.fallInstantly = true;
		int k1;
		int j1;
		int i1;
		super.decorate(world, random, i, k);
		if (random.nextInt(3) == 0) {
			i1 = i + random.nextInt(16) + 8;
			j1 = random.nextInt(128);
			k1 = k + random.nextInt(16) + 8;
			WorldGenDoublePlant doubleFlowerGen = new WorldGenDoublePlant();
			doubleFlowerGen.func_150548_a(0);
			doubleFlowerGen.generate(world, random, i1, j1, k1);
		}
		if (random.nextInt(24) == 0) {
			i1 = i + random.nextInt(16) + 8;
			j1 = random.nextInt(128);
			k1 = k + random.nextInt(16) + 8;
			new WorldGenFlowers(LOTRMod.pipeweedPlant).generate(world, random, i1, j1, k1);
		}
        BlockFalling.fallInstantly = false;
	}

	@Override
	public LOTRAchievement getBiomeAchievement() {
		return LOTRAchievement.enterIthilien;
	}

	@Override
	public LOTRMusicRegion.Sub getBiomeMusic() {
		return LOTRMusicRegion.GONDOR.getSubregion("ithilien");
	}

	@Override
	public LOTRWaypoint.Region getBiomeWaypoints() {
		return LOTRWaypoint.Region.ITHILIEN;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.5f;
	}

	@Override
	public LOTRRoadType getRoadBlock() {
		return LOTRRoadType.GONDOR_MIX.setRepair(0.7f);
	}

	@Override
	public float getTreeIncreaseChance() {
		return 0.4f;
	}
}
