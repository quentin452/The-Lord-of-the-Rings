package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.*;
import lotr.common.entity.npc.LOTREntityRivendellTrader;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure2.LOTRWorldGenElfHouse;
import lotr.common.world.structure2.LOTRWorldGenGaladhrimForge;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

public class LOTRBiomeGenLothlorien extends LOTRBiome {
	public LOTRBiomeGenLothlorien(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityHorse.class, 20, 4, 6));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityDeer.class, 30, 4, 6));
		spawnableWaterCreatureList.clear();
		spawnableCaveCreatureList.clear();
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[3];
		arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GALADHRIM, 10).setSpawnChance(50);
		arrspawnListContainer[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GALADHRIM_WARRIORS, 2).setSpawnChance(50);
		arrspawnListContainer[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GALADHRIM_WARDENS, 1).setSpawnChance(50);
		npcSpawnList.newFactionList(100).add(arrspawnListContainer);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[3];
		arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 10);
		arrspawnListContainer2[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 2);
		arrspawnListContainer2[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 2).setConquestThreshold(50.0f);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer2);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer3 = new LOTRBiomeSpawnList.SpawnListContainer[3];
		arrspawnListContainer3[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DOL_GULDUR_ORCS, 10);
		arrspawnListContainer3[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRKWOOD_SPIDERS, 2);
		arrspawnListContainer3[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRK_TROLLS, 1).setConquestThreshold(100.0f);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer3);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer4 = new LOTRBiomeSpawnList.SpawnListContainer[4];
		arrspawnListContainer4[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
		arrspawnListContainer4[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 2);
		arrspawnListContainer4[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 1).setConquestThreshold(50.0f);
		arrspawnListContainer4[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.OLOG_HAI, 1).setConquestThreshold(100.0f);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer4);
		npcSpawnList.conquestGainRate = 0.2f;
		spawnableLOTRAmbientList.clear();
		spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityButterfly.class, 10, 4, 4));
		spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityBird.class, 10, 4, 4));
		spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityRabbit.class, 6, 4, 4));
		spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntitySwan.class, 15, 4, 8));
		variantChance = 0.7f;
		addBiomeVariant(LOTRBiomeVariant.FLOWERS);
		addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT, 2.0f);
		addBiomeVariant(LOTRBiomeVariant.HILLS);
		addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
		addBiomeVariant(LOTRBiomeVariant.CLEARING, 0.5f);
		decorator.addOre(new WorldGenMinable(LOTRMod.oreQuendite, 6), 6.0f, 0, 48);
		enablePodzol = false;
		decorator.treesPerChunk = 3;
		decorator.willowPerChunk = 2;
		decorator.flowersPerChunk = 6;
		decorator.grassPerChunk = 8;
		decorator.doubleGrassPerChunk = 2;
		decorator.generateLava = false;
		decorator.generateCobwebs = false;
		decorator.whiteSand = true;
		decorator.addTree(LOTRTreeType.OAK, 300);
		decorator.addTree(LOTRTreeType.OAK_LARGE, 50);
		decorator.addTree(LOTRTreeType.LARCH, 200);
		decorator.addTree(LOTRTreeType.BEECH, 100);
		decorator.addTree(LOTRTreeType.BEECH_LARGE, 20);
		decorator.addTree(LOTRTreeType.MALLORN, 300);
		decorator.addTree(LOTRTreeType.MALLORN_BOUGHS, 600);
		decorator.addTree(LOTRTreeType.MALLORN_PARTY, 100);
		decorator.addTree(LOTRTreeType.MALLORN_EXTREME, 30);
		decorator.addTree(LOTRTreeType.ASPEN, 100);
		decorator.addTree(LOTRTreeType.ASPEN_LARGE, 20);
		decorator.addTree(LOTRTreeType.LAIRELOSSE, 50);
		registerForestFlowers();
		addFlower(LOTRMod.elanor, 0, 30);
		addFlower(LOTRMod.niphredil, 0, 20);
		biomeColors.setGrass(11527451);
		biomeColors.setFog(16770660);
		decorator.addRandomStructure(new LOTRWorldGenGaladhrimForge(false), 120);
		registerTravellingTrader(LOTREntityRivendellTrader.class);
		setBanditChance(LOTREventSpawner.EventChance.NEVER);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		super.decorate(world, random, i, k);
		for (int l = 0; l < 120; ++l) {
			int j1;
			int k1;
			int i1 = i + random.nextInt(16) + 8;
			if (!world.isAirBlock(i1, (j1 = 60 + random.nextInt(50)) - 1, k1 = k + random.nextInt(16) + 8) || !world.isAirBlock(i1, j1, k1) || !world.isAirBlock(i1, j1 + 1, k1)) {
				continue;
			}
			Block torchBlock = LOTRWorldGenElfHouse.getRandomTorch(random);
			if (world.getBlock(i1 - 1, j1, k1) == LOTRMod.wood && world.getBlockMetadata(i1 - 1, j1, k1) == 1 && world.isAirBlock(i1 - 1, j1, k1 - 1) && world.isAirBlock(i1 - 1, j1, k1 + 1)) {
				world.setBlock(i1, j1, k1, torchBlock, 1, 2);
				continue;
			}
			if (world.getBlock(i1 + 1, j1, k1) == LOTRMod.wood && world.getBlockMetadata(i1 + 1, j1, k1) == 1 && world.isAirBlock(i1 + 1, j1, k1 - 1) && world.isAirBlock(i1 + 1, j1, k1 + 1)) {
				world.setBlock(i1, j1, k1, torchBlock, 2, 2);
				continue;
			}
			if (world.getBlock(i1, j1, k1 - 1) == LOTRMod.wood && world.getBlockMetadata(i1, j1, k1 - 1) == 1 && world.isAirBlock(i1 - 1, j1, k1 - 1) && world.isAirBlock(i1 + 1, j1, k1 - 1)) {
				world.setBlock(i1, j1, k1, torchBlock, 3, 2);
				continue;
			}
			if (world.getBlock(i1, j1, k1 + 1) != LOTRMod.wood || world.getBlockMetadata(i1, j1, k1 + 1) != 1 || !world.isAirBlock(i1 - 1, j1, k1 + 1) || !world.isAirBlock(i1 + 1, j1, k1 + 1)) {
				continue;
			}
			world.setBlock(i1, j1, k1, torchBlock, 4, 2);
		}
	}

	@Override
	public LOTRAchievement getBiomeAchievement() {
		return LOTRAchievement.enterLothlorien;
	}

	@Override
	public LOTRMusicRegion.Sub getBiomeMusic() {
		return LOTRMusicRegion.LOTHLORIEN.getSubregion("lothlorien");
	}

	@Override
	public LOTRWaypoint.Region getBiomeWaypoints() {
		return LOTRWaypoint.Region.LOTHLORIEN;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.5f;
	}

	@Override
	public LOTRRoadType getRoadBlock() {
		return LOTRRoadType.GALADHRIM;
	}

	@Override
	public boolean hasSeasonalGrass() {
		return false;
	}
}
