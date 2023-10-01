package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.*;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBiomeFlowers;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure.LOTRWorldGenRuinedDunedainTower;
import lotr.common.world.structure2.*;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

import java.util.Random;

public class LOTRBiomeGenEriador extends LOTRBiome {
	public static NoiseGeneratorPerlin lavenderNoise = new NoiseGeneratorPerlin(new Random(2571548905158015L), 1);

	public LOTRBiomeGenEriador(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityHorse.class, 6, 2, 6));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityBear.class, 4, 1, 4));
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[1];
		arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_NORTH, 10).setSpawnChance(200);
		npcSpawnList.newFactionList(10, 0.0f).add(arrspawnListContainer);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[1];
		arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_NORTH, 10);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer2);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer3 = new LOTRBiomeSpawnList.SpawnListContainer[3];
		arrspawnListContainer3[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 10);
		arrspawnListContainer3[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 2).setConquestThreshold(50.0f);
		arrspawnListContainer3[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 1).setConquestThreshold(100.0f);
		npcSpawnList.newFactionList(100).add(arrspawnListContainer3);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer4 = new LOTRBiomeSpawnList.SpawnListContainer[3];
		arrspawnListContainer4[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_ORCS, 10);
		arrspawnListContainer4[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_WARGS, 2).setConquestThreshold(50.0f);
		arrspawnListContainer4[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_HILLMEN, 4).setConquestThreshold(100.0f);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer4);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer5 = new LOTRBiomeSpawnList.SpawnListContainer[2];
		arrspawnListContainer5[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.LINDON_WARRIORS, 10);
		arrspawnListContainer5[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RIVENDELL_WARRIORS, 10);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer5);
		addBiomeVariantSet(LOTRBiomeVariant.SET_NORMAL_OAK);
		addBiomeVariant(LOTRBiomeVariant.SCRUBLAND, 1.0f);
		addBiomeVariant(LOTRBiomeVariant.HILLS_SCRUBLAND, 1.0f);
		addBiomeVariant(LOTRBiomeVariant.MOUNTAIN);
		addBiomeVariant(LOTRBiomeVariant.WASTELAND);
		addBiomeVariant(LOTRBiomeVariant.FOREST_ASPEN, 0.2f);
		addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.2f);
		addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.2f);
		addBiomeVariant(LOTRBiomeVariant.FOREST_MAPLE, 0.2f);
		decorator.setTreeCluster(8, 12);
		decorator.willowPerChunk = 1;
		decorator.grassPerChunk = 9;
		decorator.doubleGrassPerChunk = 4;
		decorator.generateAthelas = true;
		decorator.addTree(LOTRTreeType.OAK, 1000);
		decorator.addTree(LOTRTreeType.OAK_LARGE, 100);
		decorator.addTree(LOTRTreeType.BIRCH, 100);
		decorator.addTree(LOTRTreeType.BIRCH_LARGE, 10);
		decorator.addTree(LOTRTreeType.SPRUCE, 200);
		decorator.addTree(LOTRTreeType.BEECH, 20);
		decorator.addTree(LOTRTreeType.BEECH_LARGE, 2);
		decorator.addTree(LOTRTreeType.CHESTNUT, 100);
		decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 10);
		decorator.addTree(LOTRTreeType.ASPEN, 50);
		decorator.addTree(LOTRTreeType.ASPEN_LARGE, 5);
		decorator.addTree(LOTRTreeType.APPLE, 2);
		decorator.addTree(LOTRTreeType.PEAR, 2);
		registerPlainsFlowers();
		addFlower(LOTRMod.lavender, 0, 20);
		decorator.generateOrcDungeon = true;
		decorator.addRandomStructure(new LOTRWorldGenGundabadCamp(false), 2000);
		decorator.addRandomStructure(new LOTRWorldGenRuinedDunedainTower(false), 1000);
		decorator.addRandomStructure(new LOTRWorldGenRuinedHouse(false), 2000);
		decorator.addRandomStructure(new LOTRWorldGenBurntHouse(false), 3000);
		decorator.addRandomStructure(new LOTRWorldGenRottenHouse(false), 3000);
		decorator.addRandomStructure(new LOTRWorldGenRangerCamp(false), 1500);
		decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 3), 800);
		decorator.addRandomStructure(new LOTRWorldGenStoneRuin.ARNOR(1, 3), 800);
		decorator.addRandomStructure(new LOTRWorldGenRangerWatchtower(false), 2000);
		decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 400);
		registerTravellingTrader(LOTREntityGaladhrimTrader.class);
		registerTravellingTrader(LOTREntityBlueDwarfMerchant.class);
		registerTravellingTrader(LOTREntityIronHillsMerchant.class);
		registerTravellingTrader(LOTREntityScrapTrader.class);
		registerTravellingTrader(LOTREntityRivendellTrader.class);
		setBanditChance(LOTREventSpawner.EventChance.BANDIT_UNCOMMON);
		invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD, LOTREventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD_WARG, LOTREventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(LOTRInvasions.RANGER_NORTH, LOTREventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(LOTRInvasions.ANGMAR, LOTREventSpawner.EventChance.RARE);
		invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_HILLMEN, LOTREventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_WARG, LOTREventSpawner.EventChance.RARE);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		super.decorate(world, random, i, k);
		LOTRBiomeVariant biomeVariant = ((LOTRWorldChunkManager) world.getWorldChunkManager()).getBiomeVariantAt(i + 8, k + 8);
		if (biomeVariant.flowerFactor >= 1.0f) {
			double lavNoise = lavenderNoise.func_151601_a(i * 0.001, k * 0.001);
			lavNoise += lavenderNoise.func_151601_a(i * 0.03, k * 0.03);
			lavNoise /= 2.0;
			lavNoise -= 0.75;
			if (lavNoise >= 0.0) {
				int num = (int) Math.round(lavNoise * 16.0);
				num = Math.max(num, 4);
				LOTRWorldGenBiomeFlowers lavGen = new LOTRWorldGenBiomeFlowers(LOTRMod.lavender, 0);
				for (int l = 0; l < num; ++l) {
					int i1 = i + random.nextInt(16) + 8;
					int k1 = k + random.nextInt(16) + 8;
					int j1 = world.getTopSolidOrLiquidBlock(i1, k1);
					lavGen.generate(world, random, i1, j1, k1);
				}
			}
		}
	}

	@Override
	public LOTRAchievement getBiomeAchievement() {
		return LOTRAchievement.enterEriador;
	}

	@Override
	public LOTRMusicRegion.Sub getBiomeMusic() {
		return LOTRMusicRegion.ERIADOR.getSubregion("eriador");
	}

	@Override
	public LOTRWaypoint.Region getBiomeWaypoints() {
		return LOTRWaypoint.Region.ERIADOR;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.1f;
	}

	@Override
	public BiomeGenBase.FlowerEntry getRandomFlower(World world, Random rand, int i, int j, int k) {
		LOTRBiomeVariant variant = ((LOTRWorldChunkManager) world.getWorldChunkManager()).getBiomeVariantAt(i, k);
		if (variant.flowerFactor > 1.0f && rand.nextFloat() < 0.9f) {
			return new BiomeGenBase.FlowerEntry(LOTRMod.lavender, 0, 100);
		}
		return (BiomeGenBase.FlowerEntry) WeightedRandom.getRandomItem(rand, flowers);
	}

	@Override
	public LOTRRoadType getRoadBlock() {
		return LOTRRoadType.ARNOR.setRepair(0.92f);
	}

	@Override
	public float getTreeIncreaseChance() {
		return 0.05f;
	}

	@Override
	public int spawnCountMultiplier() {
		return 4;
	}
}
