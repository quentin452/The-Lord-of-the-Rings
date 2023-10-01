package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntitySeagull;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenSeaBlock;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.structure.LOTRWorldGenUnderwaterElvenRuin;
import lotr.common.world.structure2.LOTRWorldGenNumenorRuin;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class LOTRBiomeGenOcean extends LOTRBiome {
	public static Random iceRand = new Random();
	public WorldGenerator spongeGen = new LOTRWorldGenSeaBlock(Blocks.sponge, 0, 24);
	public WorldGenerator coralGen = new LOTRWorldGenSeaBlock(LOTRMod.coralReef, 0, 64);

	public LOTRBiomeGenOcean(int i, boolean major) {
		super(i, major);
		spawnableWaterCreatureList.add(new BiomeGenBase.SpawnListEntry(EntitySquid.class, 4, 4, 4));
		spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntitySeagull.class, 20, 4, 4));
		npcSpawnList.clear();
		decorator.addOre(new WorldGenMinable(LOTRMod.oreSalt, 8), 4.0f, 0, 64);
		decorator.addOre(new WorldGenMinable(LOTRMod.oreSalt, 8, Blocks.sand), 0.5f, 56, 80);
		decorator.addOre(new WorldGenMinable(LOTRMod.oreSalt, 8, LOTRMod.whiteSand), 0.5f, 56, 80);
		decorator.treesPerChunk = 1;
		decorator.willowPerChunk = 1;
		decorator.flowersPerChunk = 2;
		decorator.doubleFlowersPerChunk = 1;
		decorator.grassPerChunk = 8;
		decorator.doubleGrassPerChunk = 1;
		decorator.addTree(LOTRTreeType.OAK, 1000);
		decorator.addTree(LOTRTreeType.OAK_LARGE, 100);
		decorator.addTree(LOTRTreeType.BIRCH, 100);
		decorator.addTree(LOTRTreeType.BIRCH_LARGE, 10);
		decorator.addTree(LOTRTreeType.BEECH, 50);
		decorator.addTree(LOTRTreeType.BEECH_LARGE, 5);
		decorator.addTree(LOTRTreeType.APPLE, 3);
		decorator.addTree(LOTRTreeType.PEAR, 3);
		decorator.addRandomStructure(new LOTRWorldGenNumenorRuin(false), 500);
		decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 400);
		setBanditChance(LOTREventSpawner.EventChance.NEVER);
	}

	public static boolean isFrozen(int i, int k) {
		if (k > -30000) {
			return false;
		}
		int l = -1;
		return true;
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		int j1;
		int i1;
		int k1;
		super.decorate(world, random, i, k);
		if (i < LOTRWaypoint.MITHLOND_SOUTH.getXCoord() && k > LOTRWaypoint.SOUTH_FOROCHEL.getZCoord() && k < LOTRWaypoint.ERYN_VORN.getZCoord() && random.nextInt(200) == 0) {
			i1 = i + random.nextInt(16) + 8;
			k1 = k + random.nextInt(16) + 8;
			j1 = world.getTopSolidOrLiquidBlock(i1, k1);
			new LOTRWorldGenUnderwaterElvenRuin(false).generate(world, random, i1, j1, k1);
		}
		if (k > -30000) {
			if (random.nextInt(12) == 0 && ((j1 = world.getTopSolidOrLiquidBlock(i1 = i + random.nextInt(16) + 8, k1 = k + random.nextInt(16) + 8)) < 60 || random.nextBoolean())) {
				spongeGen.generate(world, random, i1, j1, k1);
			}
			if (random.nextInt(4) == 0 && ((j1 = world.getTopSolidOrLiquidBlock(i1 = i + random.nextInt(16) + 8, k1 = k + random.nextInt(16) + 8)) < 60 || random.nextBoolean())) {
				coralGen.generate(world, random, i1, j1, k1);
			}
		}
		if (k >= 64000) {
			float chance;
			chance = k >= 130000 ? 1.0f : (k - 64000) / 66000.0f;
			if (random.nextFloat() < chance && random.nextInt(6) == 0) {
				int palms = 1 + random.nextInt(2);
				if (random.nextInt(3) == 0) {
					++palms;
				}
				for (int l = 0; l < palms; ++l) {
					int j12;
					int k12;
					int i12 = i + random.nextInt(16) + 8;
					if (!world.getBlock(i12, j12 = world.getTopSolidOrLiquidBlock(i12, k12 = k + random.nextInt(16) + 8) - 1, k12).isNormalCube() || !LOTRWorldGenStructureBase2.isSurfaceStatic(world, i12, j12, k12)) {
						continue;
					}
					Block prevBlock = world.getBlock(i12, j12, k12);
					int prevMeta = world.getBlockMetadata(i12, j12, k12);
					world.setBlock(i12, j12, k12, Blocks.dirt, 0, 2);
					WorldGenAbstractTree palmGen = LOTRTreeType.PALM.create(false, random);
					if (palmGen.generate(world, random, i12, j12 + 1, k12)) {
						continue;
					}
					world.setBlock(i12, j12, k12, prevBlock, prevMeta, 2);
				}
			}
		}
	}

	@Override
	public LOTRAchievement getBiomeAchievement() {
		return LOTRAchievement.enterOcean;
	}

	@Override
	public LOTRMusicRegion.Sub getBiomeMusic() {
		return LOTRMusicRegion.SEA.getSubregion("sea");
	}

	@Override
	public LOTRWaypoint.Region getBiomeWaypoints() {
		return LOTRWaypoint.Region.OCEAN;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.25f;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}
}
