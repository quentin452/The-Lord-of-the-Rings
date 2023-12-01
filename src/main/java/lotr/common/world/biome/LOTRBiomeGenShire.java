package lotr.common.world.biome;

import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityShirePony;
import lotr.common.entity.npc.*;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.biome.variant.LOTRBiomeVariantOrchard;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenClover;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure.LOTRWorldGenHobbitPicnicBench;
import lotr.common.world.structure2.*;
import net.minecraft.block.BlockFalling;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenFlowers;

import java.util.Random;

public class LOTRBiomeGenShire extends LOTRBiome {
	public LOTRBiomeSpawnList orcharderSpawnList = new LOTRBiomeSpawnList(this);

	public LOTRBiomeGenShire(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityShirePony.class, 12, 2, 6));
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[1];
		arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HOBBITS, 10);
		npcSpawnList.newFactionList(100).add(arrspawnListContainer);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[2];
		arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 10);
		arrspawnListContainer2[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 3).setConquestThreshold(100.0f);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer2);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer3 = new LOTRBiomeSpawnList.SpawnListContainer[2];
		arrspawnListContainer3[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_ORCS, 10);
		arrspawnListContainer3[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_WARGS, 3).setConquestThreshold(100.0f);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer3);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer4 = new LOTRBiomeSpawnList.SpawnListContainer[3];
		arrspawnListContainer4[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RUFFIANS, 10);
		arrspawnListContainer4[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ISENGARD_SNAGA, 10).setConquestThreshold(25.0f);
		arrspawnListContainer4[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_HAI, 5).setConquestThreshold(25.0f);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer4);
		npcSpawnList.conquestGainRate = 0.2f;
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer5 = new LOTRBiomeSpawnList.SpawnListContainer[1];
		arrspawnListContainer5[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HOBBITS_ORCHARD);
		orcharderSpawnList.newFactionList(100).add(arrspawnListContainer5);
		variantChance = 0.25f;
		addBiomeVariant(LOTRBiomeVariant.FLOWERS);
		addBiomeVariant(LOTRBiomeVariant.FOREST);
		addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
		addBiomeVariant(LOTRBiomeVariant.HILLS);
		addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
		addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.5f);
		addBiomeVariant(LOTRBiomeVariant.FOREST_ASPEN, 0.5f);
		addBiomeVariant(LOTRBiomeVariant.ORCHARD_SHIRE, 1.0f);
		addBiomeVariant(LOTRBiomeVariant.ORCHARD_PLUM, 0.3f);
		decorator.willowPerChunk = 1;
		decorator.flowersPerChunk = 3;
		decorator.doubleFlowersPerChunk = 1;
		decorator.grassPerChunk = 6;
		decorator.generateLava = false;
		decorator.addTree(LOTRTreeType.OAK, 1000);
		decorator.addTree(LOTRTreeType.OAK_LARGE, 400);
		decorator.addTree(LOTRTreeType.OAK_PARTY, 10);
		decorator.addTree(LOTRTreeType.CHESTNUT, 250);
		decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 100);
		decorator.addTree(LOTRTreeType.BIRCH, 25);
		decorator.addTree(LOTRTreeType.BIRCH_LARGE, 10);
		decorator.addTree(LOTRTreeType.ASPEN, 50);
		decorator.addTree(LOTRTreeType.ASPEN_LARGE, 10);
		decorator.addTree(LOTRTreeType.APPLE, 5);
		decorator.addTree(LOTRTreeType.PEAR, 5);
		decorator.addTree(LOTRTreeType.CHERRY, 2);
		decorator.addTree(LOTRTreeType.PLUM, 5);
		registerPlainsFlowers();
		biomeColors.setGrass(8111137);
		if (hasShireStructures()) {
			if (getClass() == LOTRBiomeGenShire.class) {
				decorator.addRandomStructure(new LOTRWorldGenHobbitHole(false), 90);
				decorator.addRandomStructure(new LOTRWorldGenHobbitBurrow(false), 45);
				decorator.addRandomStructure(new LOTRWorldGenHobbitHouse(false), 45);
				decorator.addRandomStructure(new LOTRWorldGenHobbitTavern(false), 70);
				decorator.addRandomStructure(new LOTRWorldGenHobbitWindmill(false), 600);
				decorator.addRandomStructure(new LOTRWorldGenHobbitFarm(false), 500);
			}
			decorator.addRandomStructure(new LOTRWorldGenHobbitPicnicBench(false), 40);
			decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 4), 1500);
			decorator.addRandomStructure(new LOTRWorldGenStoneRuin.ARNOR(1, 4), 1500);
		}
		registerTravellingTrader(LOTREntityGaladhrimTrader.class);
		registerTravellingTrader(LOTREntityBlueDwarfMerchant.class);
		registerTravellingTrader(LOTREntityIronHillsMerchant.class);
		registerTravellingTrader(LOTREntityScrapTrader.class);
		registerTravellingTrader(LOTREntityDaleMerchant.class);
		registerTravellingTrader(LOTREntityRivendellTrader.class);
		setBanditChance(LOTREventSpawner.EventChance.NEVER);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
        BlockFalling.fallInstantly = true;
		int k1;
		int j1;
		int i1;
		super.decorate(world, random, i, k);
		for (int l = 0; l < decorator.grassPerChunk / 2; ++l) {
			int i12 = i + random.nextInt(16) + 8;
			int j12 = random.nextInt(128);
			int k12 = k + random.nextInt(16) + 8;
			new LOTRWorldGenClover().generate(world, random, i12, j12, k12);
		}
		if (random.nextInt(6) == 0) {
			i1 = i + random.nextInt(16) + 8;
			j1 = random.nextInt(128);
			k1 = k + random.nextInt(16) + 8;
			new WorldGenFlowers(LOTRMod.pipeweedPlant).generate(world, random, i1, j1, k1);
		}
		if (decorator.doubleFlowersPerChunk > 0 && random.nextInt(6) == 0) {
			i1 = i + random.nextInt(16) + 8;
			j1 = random.nextInt(128);
			k1 = k + random.nextInt(16) + 8;
			WorldGenDoublePlant doubleFlowerGen = new WorldGenDoublePlant();
			doubleFlowerGen.func_150548_a(0);
			doubleFlowerGen.generate(world, random, i1, j1, k1);
		}
        BlockFalling.fallInstantly = false;
	}

	@Override
	public LOTRMusicRegion.Sub getBiomeMusic() {
		return LOTRMusicRegion.SHIRE.getSubregion("shire");
	}

	@Override
	public LOTRWaypoint.Region getBiomeWaypoints() {
		return LOTRWaypoint.Region.SHIRE;
	}

	@Override
	public LOTRBiomeSpawnList getNPCSpawnList(World world, Random random, int i, int j, int k, LOTRBiomeVariant variant) {
		if (variant instanceof LOTRBiomeVariantOrchard && random.nextFloat() < 0.3f) {
			return orcharderSpawnList;
		}
		return super.getNPCSpawnList(world, random, i, j, k, variant);
	}

	@Override
	public float getTreeIncreaseChance() {
		return 0.5f;
	}

	@Override
	public boolean hasDomesticAnimals() {
		return true;
	}

	public boolean hasShireStructures() {
		return true;
	}

	@Override
	public int spawnCountMultiplier() {
		return 3;
	}
}
