package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.*;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenObsidianGravel;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import net.minecraft.block.BlockFalling;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenVines;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class LOTRBiomeGenFarHaradJungle extends LOTRBiomeGenFarHarad {
	public WorldGenerator obsidianGen = new LOTRWorldGenObsidianGravel();
	public int obsidianGravelRarity = 20;

	public LOTRBiomeGenFarHaradJungle(int i, boolean major) {
		super(i, major);
		if (isMuddy()) {
			topBlock = LOTRMod.mudGrass;
			fillerBlock = LOTRMod.mud;
		}
		spawnableCreatureList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityFlamingo.class, 10, 4, 4));
		spawnableLOTRAmbientList.clear();
		spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityBird.class, 10, 4, 4));
		spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityButterfly.class, 15, 4, 4));
		if (isMuddy()) {
			spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityMidges.class, 10, 4, 4));
		}
		spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(LOTREntityJungleScorpion.class, 30, 4, 4));
		npcSpawnList.clear();
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[2];
		arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TAURETHRIM, 10).setSpawnChance(5000);
		arrspawnListContainer[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TAURETHRIM_WARRIORS, 30).setSpawnChance(5000);
		npcSpawnList.newFactionList(100, 0.0f).add(arrspawnListContainer);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[2];
		arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TAURETHRIM_WARRIORS, 10);
		arrspawnListContainer2[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TAURETHRIM, 4);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer2);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer3 = new LOTRBiomeSpawnList.SpawnListContainer[2];
		arrspawnListContainer3[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
		arrspawnListContainer3[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(50.0f);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer3);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer4 = new LOTRBiomeSpawnList.SpawnListContainer[2];
		arrspawnListContainer4[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SOUTHRON_WARRIORS, 10);
		arrspawnListContainer4[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GULF_WARRIORS, 10);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer4);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer5 = new LOTRBiomeSpawnList.SpawnListContainer[2];
		arrspawnListContainer5[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH_WARRIORS, 10);
		arrspawnListContainer5[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH, 5);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer5);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer6 = new LOTRBiomeSpawnList.SpawnListContainer[1];
		arrspawnListContainer6[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HALF_TROLLS, 10);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer6);
		addBiomeVariant(LOTRBiomeVariant.FLOWERS);
		addBiomeVariant(LOTRBiomeVariant.HILLS);
		addBiomeVariant(LOTRBiomeVariant.MOUNTAIN);
		addBiomeVariant(LOTRBiomeVariant.JUNGLE_DENSE);
		if (isMuddy()) {
			decorator.addSoil(new WorldGenMinable(LOTRMod.mud, 32), 80.0f, 0, 256);
			decorator.addSoil(new WorldGenMinable(LOTRMod.mud, 32), 80.0f, 0, 64);
		}
		decorator.addOre(new WorldGenMinable(Blocks.gold_ore, 4), 3.0f, 0, 48);
		decorator.addGem(new WorldGenMinable(LOTRMod.oreGem, 4, 8, Blocks.stone), 3.0f, 0, 48);
		decorator.treesPerChunk = 40;
		decorator.vinesPerChunk = 50;
		decorator.flowersPerChunk = 4;
		decorator.doubleFlowersPerChunk = 4;
		decorator.grassPerChunk = 15;
		decorator.doubleGrassPerChunk = 10;
		decorator.enableFern = true;
		decorator.canePerChunk = 5;
		decorator.cornPerChunk = 10;
		decorator.melonPerChunk = 0.2f;
		decorator.clearTrees();
		decorator.addTree(LOTRTreeType.JUNGLE, 1000);
		decorator.addTree(LOTRTreeType.JUNGLE_LARGE, 500);
		decorator.addTree(LOTRTreeType.MAHOGANY, 500);
		decorator.addTree(LOTRTreeType.JUNGLE_SHRUB, 1000);
		decorator.addTree(LOTRTreeType.MANGO, 20);
		decorator.addTree(LOTRTreeType.BANANA, 50);
		registerJungleFlowers();
		biomeColors.setGrass(10607421);
		biomeColors.setFoliage(8376636);
		biomeColors.setSky(11977908);
		biomeColors.setFog(11254938);
		biomeColors.setWater(4104311);
		decorator.addRandomStructure(new LOTRWorldGenStoneRuin.TAUREDAIN(1, 4), 100);
		invasionSpawns.addInvasion(LOTRInvasions.MOREDAIN, LOTREventSpawner.EventChance.RARE);
		invasionSpawns.addInvasion(LOTRInvasions.TAUREDAIN, LOTREventSpawner.EventChance.RARE);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
        BlockFalling.fallInstantly = true;
		int j1;
		super.decorate(world, random, i, k);
		WorldGenVines vines = new WorldGenVines();
		for (int l = 0; l < 10; ++l) {
			int i1 = i + random.nextInt(16) + 8;
			j1 = 24;
			int k1 = k + random.nextInt(16) + 8;
			vines.generate(world, random, i1, j1, k1);
		}
		if (obsidianGravelRarity > 0 && random.nextInt(obsidianGravelRarity) == 0) {
			int i1 = i + random.nextInt(16) + 8;
			int k1 = k + random.nextInt(16) + 8;
			j1 = world.getTopSolidOrLiquidBlock(i1, k1);
			obsidianGen.generate(world, random, i1, j1, k1);
		}
        BlockFalling.fallInstantly = false;
	}

	@Override
	public LOTRAchievement getBiomeAchievement() {
		return LOTRAchievement.enterFarHaradJungle;
	}

	@Override
	public LOTRMusicRegion.Sub getBiomeMusic() {
		return LOTRMusicRegion.FAR_HARAD_JUNGLE.getSubregion("jungle");
	}

	@Override
	public LOTRWaypoint.Region getBiomeWaypoints() {
		return LOTRWaypoint.Region.FAR_HARAD_JUNGLE;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.25f;
	}

	@Override
	public LOTRBiome.GrassBlockAndMeta getRandomGrass(Random random) {
		if (random.nextInt(4) == 0) {
			return new LOTRBiome.GrassBlockAndMeta(LOTRMod.tallGrass, 5);
		}
		return super.getRandomGrass(random);
	}

	@Override
	public LOTRRoadType getRoadBlock() {
		return LOTRRoadType.TAUREDAIN.setRepair(0.8f);
	}

	public boolean hasJungleLakes() {
		return true;
	}

	public boolean isMuddy() {
		return true;
	}

	@Override
	public double modifyStoneNoiseForFiller(double stoneNoise) {
		if (isMuddy()) {
			stoneNoise += 40.0;
		}
		return stoneNoise;
	}
}
