package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.*;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure.LOTRWorldGenRuinedDunedainTower;
import lotr.common.world.structure2.*;
import net.minecraft.block.BlockFalling;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class LOTRBiomeGenLoneLands extends LOTRBiome {
	public WorldGenerator boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 5);

	public LOTRBiomeGenLoneLands(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityHorse.class, 6, 2, 6));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityBear.class, 4, 1, 4));
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[1];
		arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_NORTH, 10).setSpawnChance(500);
		npcSpawnList.newFactionList(8, 0.0f).add(arrspawnListContainer);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[1];
		arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_NORTH, 10);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer2);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer3 = new LOTRBiomeSpawnList.SpawnListContainer[3];
		arrspawnListContainer3[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 10);
		arrspawnListContainer3[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 2);
		arrspawnListContainer3[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 1).setConquestThreshold(100.0f);
		npcSpawnList.newFactionList(100).add(arrspawnListContainer3);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer4 = new LOTRBiomeSpawnList.SpawnListContainer[1];
		arrspawnListContainer4[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_HILLMEN, 10).setSpawnChance(10000);
		npcSpawnList.newFactionList(50).add(arrspawnListContainer4);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer5 = new LOTRBiomeSpawnList.SpawnListContainer[3];
		arrspawnListContainer5[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_ORCS, 10);
		arrspawnListContainer5[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_WARGS, 2);
		arrspawnListContainer5[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_HILLMEN, 10);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer5);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer6 = new LOTRBiomeSpawnList.SpawnListContainer[1];
		arrspawnListContainer6[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RIVENDELL_WARRIORS, 10);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer6);
		addBiomeVariantSet(LOTRBiomeVariant.SET_NORMAL_OAK);
		addBiomeVariant(LOTRBiomeVariant.SCRUBLAND, 3.0f);
		addBiomeVariant(LOTRBiomeVariant.HILLS_SCRUBLAND);
		addBiomeVariant(LOTRBiomeVariant.WASTELAND);
		addBiomeVariant(LOTRBiomeVariant.MOUNTAIN);
		addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.2f);
		addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.2f);
		addBiomeVariant(LOTRBiomeVariant.FOREST_LARCH, 0.2f);
		addBiomeVariant(LOTRBiomeVariant.FOREST_PINE, 0.2f);
		addBiomeVariant(LOTRBiomeVariant.FOREST_ASPEN, 0.2f);
		addBiomeVariant(LOTRBiomeVariant.FOREST_MAPLE, 0.2f);
		decorator.treesPerChunk = 0;
		decorator.willowPerChunk = 1;
		decorator.flowersPerChunk = 3;
		decorator.grassPerChunk = 10;
		decorator.doubleGrassPerChunk = 6;
		decorator.generateAthelas = true;
		decorator.addTree(LOTRTreeType.OAK, 1000);
		decorator.addTree(LOTRTreeType.OAK_LARGE, 300);
		decorator.addTree(LOTRTreeType.SPRUCE, 300);
		decorator.addTree(LOTRTreeType.BEECH, 100);
		decorator.addTree(LOTRTreeType.BEECH_LARGE, 50);
		decorator.addTree(LOTRTreeType.BIRCH, 10);
		decorator.addTree(LOTRTreeType.BIRCH_LARGE, 5);
		decorator.addTree(LOTRTreeType.ASPEN, 50);
		decorator.addTree(LOTRTreeType.ASPEN_LARGE, 10);
		decorator.addTree(LOTRTreeType.APPLE, 1);
		decorator.addTree(LOTRTreeType.PEAR, 1);
		registerPlainsFlowers();
		biomeColors.setGrass(12892772);
		decorator.generateOrcDungeon = true;
		decorator.addRandomStructure(new LOTRWorldGenGundabadCamp(false), 1500);
		decorator.addRandomStructure(new LOTRWorldGenRuinedDunedainTower(false), 800);
		decorator.addRandomStructure(new LOTRWorldGenRuinedHouse(false), 1500);
		decorator.addRandomStructure(new LOTRWorldGenBurntHouse(false), 1500);
		decorator.addRandomStructure(new LOTRWorldGenRottenHouse(false), 2000);
		decorator.addRandomStructure(new LOTRWorldGenRangerCamp(false), 2000);
		decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 4), 400);
		decorator.addRandomStructure(new LOTRWorldGenStoneRuin.ARNOR(1, 4), 400);
		decorator.addRandomStructure(new LOTRWorldGenRangerWatchtower(false), 2000);
		decorator.addRandomStructure(new LOTRWorldGenAngmarHillmanVillage(false), 4000);
		decorator.addRandomStructure(new LOTRWorldGenAngmarHillmanHouse(false), 1000);
		decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 200);
		decorator.addRandomStructure(new LOTRWorldGenRhudaurCastle(false), 1000);
		registerTravellingTrader(LOTREntityGaladhrimTrader.class);
		registerTravellingTrader(LOTREntityBlueDwarfMerchant.class);
		registerTravellingTrader(LOTREntityIronHillsMerchant.class);
		registerTravellingTrader(LOTREntityScrapTrader.class);
		registerTravellingTrader(LOTREntityRivendellTrader.class);
		setBanditChance(LOTREventSpawner.EventChance.BANDIT_COMMON);
		invasionSpawns.addInvasion(LOTRInvasions.HIGH_ELF_RIVENDELL, LOTREventSpawner.EventChance.RARE);
		invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD, LOTREventSpawner.EventChance.COMMON);
		invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD_WARG, LOTREventSpawner.EventChance.COMMON);
		invasionSpawns.addInvasion(LOTRInvasions.RANGER_NORTH, LOTREventSpawner.EventChance.COMMON);
		invasionSpawns.addInvasion(LOTRInvasions.ANGMAR, LOTREventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_HILLMEN, LOTREventSpawner.EventChance.COMMON);
		invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_WARG, LOTREventSpawner.EventChance.RARE);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
        BlockFalling.fallInstantly = true;
		super.decorate(world, random, i, k);
		if (random.nextInt(32) == 0) {
			for (int l = 0; l < 3; ++l) {
				int i1 = i + random.nextInt(16) + 8;
				int k1 = k + random.nextInt(16) + 8;
				boulderGen.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
			}
		}
        BlockFalling.fallInstantly = false;
	}

	@Override
	public LOTRAchievement getBiomeAchievement() {
		return LOTRAchievement.enterLoneLands;
	}

	@Override
	public LOTRMusicRegion.Sub getBiomeMusic() {
		return LOTRMusicRegion.ERIADOR.getSubregion("loneLands");
	}

	@Override
	public LOTRWaypoint.Region getBiomeWaypoints() {
		return LOTRWaypoint.Region.LONE_LANDS;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.1f;
	}

	@Override
	public LOTRRoadType getRoadBlock() {
		return LOTRRoadType.ARNOR.setRepair(0.4f);
	}

	@Override
	public float getTreeIncreaseChance() {
		return 0.25f;
	}
}
