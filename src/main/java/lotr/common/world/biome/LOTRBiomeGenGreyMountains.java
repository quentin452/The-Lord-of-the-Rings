package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure2.LOTRWorldGenRuinedDwarvenTower;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRBiomeGenGreyMountains extends LOTRBiome {
	public LOTRBiomeGenGreyMountains(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[3];
		arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 30);
		arrspawnListContainer[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 20);
		arrspawnListContainer[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 7);
		npcSpawnList.newFactionList(100).add(arrspawnListContainer);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[4];
		arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SNOW_TROLLS, 1).setSpawnChance(5000);
		arrspawnListContainer2[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_ORCS, 30).setConquestOnly();
		arrspawnListContainer2[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_WARGS, 20).setConquestOnly();
		arrspawnListContainer2[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SNOW_TROLLS, 6).setConquestOnly();
		npcSpawnList.newFactionList(20).add(arrspawnListContainer2);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer3 = new LOTRBiomeSpawnList.SpawnListContainer[1];
		arrspawnListContainer3[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLUE_DWARVES, 10);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer3);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer4 = new LOTRBiomeSpawnList.SpawnListContainer[3];
		arrspawnListContainer4[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DOL_GULDUR_ORCS, 10);
		arrspawnListContainer4[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRKWOOD_SPIDERS, 2).setConquestThreshold(50.0f);
		arrspawnListContainer4[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRK_TROLLS, 1).setConquestThreshold(200.0f);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer4);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer5 = new LOTRBiomeSpawnList.SpawnListContainer[1];
		arrspawnListContainer5[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DWARVES, 10);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer5);
		addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
		addBiomeVariant(LOTRBiomeVariant.FOREST_LARCH, 0.2f);
		addBiomeVariant(LOTRBiomeVariant.FOREST_PINE, 0.2f);
		addBiomeVariant(LOTRBiomeVariant.FOREST_ASPEN, 0.2f);
		addBiomeVariant(LOTRBiomeVariant.FOREST_MAPLE, 0.2f);
		decorator.biomeGemFactor = 1.0f;
		decorator.flowersPerChunk = 0;
		decorator.grassPerChunk = 0;
		decorator.doubleGrassPerChunk = 0;
		decorator.addTree(LOTRTreeType.SPRUCE, 400);
		decorator.addTree(LOTRTreeType.SPRUCE_THIN, 400);
		decorator.addTree(LOTRTreeType.SPRUCE_MEGA, 50);
		decorator.addTree(LOTRTreeType.SPRUCE_MEGA_THIN, 10);
		decorator.addTree(LOTRTreeType.LARCH, 500);
		decorator.addTree(LOTRTreeType.FIR, 500);
		decorator.addTree(LOTRTreeType.PINE, 500);
		registerMountainsFlowers();
		biomeColors.setSky(10862798);
		decorator.generateOrcDungeon = true;
		decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 4), 500);
		decorator.addRandomStructure(new LOTRWorldGenStoneRuin.DWARVEN(1, 4), 1000);
		decorator.addRandomStructure(new LOTRWorldGenRuinedDwarvenTower(false), 1000);
		decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 500);
		setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
		invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD, LOTREventSpawner.EventChance.RARE);
		invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD_WARG, LOTREventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(LOTRInvasions.DWARF, LOTREventSpawner.EventChance.RARE);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
        BlockFalling.fallInstantly = true;
		int i1;
		int count;
		super.decorate(world, random, i, k);
		for (count = 0; count < 3; ++count) {
			int k1;
			i1 = i + random.nextInt(16) + 8;
			int j1 = world.getHeightValue(i1, k1 = k + random.nextInt(16) + 8);
			if (j1 >= 100) {
				continue;
			}
			decorator.genTree(world, random, i1, j1, k1);
		}
		for (count = 0; count < 2; ++count) {
			i1 = i + random.nextInt(16) + 8;
			int j1 = random.nextInt(128);
			int k1 = k + random.nextInt(16) + 8;
			if (j1 >= 100) {
				continue;
			}
			getRandomWorldGenForGrass(random).generate(world, random, i1, j1, k1);
		}
        BlockFalling.fallInstantly = false;
	}

	@Override
	public void generateMountainTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int xzIndex, int ySize, int height, int rockDepth, LOTRBiomeVariant variant) {
		int snowHeight = 150 - rockDepth;
		int stoneHeight = snowHeight - 40;
		for (int j = ySize - 1; j >= stoneHeight; --j) {
			int index = xzIndex * ySize + j;
			Block block = blocks[index];
			if (j >= snowHeight && block == topBlock) {
				blocks[index] = Blocks.snow;
				meta[index] = 0;
				continue;
			}
			if (block != topBlock && block != fillerBlock) {
				continue;
			}
			blocks[index] = Blocks.stone;
			meta[index] = 0;
		}
	}

	@Override
	public LOTRAchievement getBiomeAchievement() {
		return LOTRAchievement.enterGreyMountains;
	}

	@Override
	public LOTRMusicRegion.Sub getBiomeMusic() {
		return LOTRMusicRegion.GREY_MOUNTAINS.getSubregion("greyMountains");
	}

	@Override
	public LOTRWaypoint.Region getBiomeWaypoints() {
		return LOTRWaypoint.Region.GREY_MOUNTAINS;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.0f;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}

	@Override
	public float getTreeIncreaseChance() {
		return 0.0f;
	}
}
