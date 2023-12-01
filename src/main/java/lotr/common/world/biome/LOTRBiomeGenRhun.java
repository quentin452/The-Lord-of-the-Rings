package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityNearHaradMerchant;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.feature.LOTRWorldGenDoubleFlower;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import net.minecraft.block.BlockFalling;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class LOTRBiomeGenRhun extends LOTRBiome {
	public WorldGenerator boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 4);

	public LOTRBiomeGenRhun(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityHorse.class, 20, 2, 6));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityBear.class, 5, 1, 4));
		npcSpawnList.clear();
		variantChance = 0.3f;
		addBiomeVariantSet(LOTRBiomeVariant.SET_NORMAL_OAK_SPRUCE);
		addBiomeVariant(LOTRBiomeVariant.DENSEFOREST_SPRUCE, 3.0f);
		addBiomeVariant(LOTRBiomeVariant.SCRUBLAND);
		addBiomeVariant(LOTRBiomeVariant.HILLS_SCRUBLAND);
		addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.2f);
		addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.2f);
		addBiomeVariant(LOTRBiomeVariant.FOREST_PINE, 0.2f);
		addBiomeVariant(LOTRBiomeVariant.FOREST_ASPEN, 0.2f);
		addBiomeVariant(LOTRBiomeVariant.FOREST_MAPLE, 0.2f);
		decorator.resetTreeCluster();
		decorator.willowPerChunk = 1;
		decorator.flowersPerChunk = 1;
		decorator.grassPerChunk = 12;
		decorator.doubleGrassPerChunk = 8;
		decorator.addTree(LOTRTreeType.OAK, 100);
		decorator.addTree(LOTRTreeType.OAK_LARGE, 50);
		decorator.addTree(LOTRTreeType.SPRUCE, 200);
		decorator.addTree(LOTRTreeType.PINE, 500);
		decorator.addTree(LOTRTreeType.PINE_SHRUB, 4000);
		decorator.addTree(LOTRTreeType.CHESTNUT, 500);
		decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 20);
		decorator.addTree(LOTRTreeType.ASPEN, 100);
		decorator.addTree(LOTRTreeType.ASPEN_LARGE, 20);
		decorator.addTree(LOTRTreeType.MAPLE, 50);
		decorator.addTree(LOTRTreeType.MAPLE_LARGE, 20);
		registerRhunPlainsFlowers();
		biomeColors.setGrass(12504664);
		decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 1000);
		registerTravellingTrader(LOTREntityNearHaradMerchant.class);
		registerTravellingTrader(LOTREntityScrapTrader.class);
		setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
		invasionSpawns.addInvasion(LOTRInvasions.RHUN, LOTREventSpawner.EventChance.RARE);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
        BlockFalling.fallInstantly = true;
		super.decorate(world, random, i, k);
		if (random.nextInt(200) == 0) {
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
		return LOTRAchievement.enterRhun;
	}

	@Override
	public LOTRMusicRegion.Sub getBiomeMusic() {
		return LOTRMusicRegion.RHUN.getSubregion("rhun");
	}

	@Override
	public LOTRWaypoint.Region getBiomeWaypoints() {
		return LOTRWaypoint.Region.RHUN;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.25f;
	}

	@Override
	public WorldGenerator getRandomWorldGenForDoubleFlower(Random random) {
		if (random.nextInt(4) == 0) {
			LOTRWorldGenDoubleFlower doubleFlowerGen = new LOTRWorldGenDoubleFlower();
			doubleFlowerGen.setFlowerType(0);
			return doubleFlowerGen;
		}
		return super.getRandomWorldGenForDoubleFlower(random);
	}

	@Override
	public float getTreeIncreaseChance() {
		return 0.03f;
	}
}
