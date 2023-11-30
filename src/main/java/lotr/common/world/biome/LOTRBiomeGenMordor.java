package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.feature.LOTRWorldGenMordorMoss;
import lotr.common.world.map.LOTRFixedStructures;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure.LOTRWorldGenMordorTower;
import lotr.common.world.structure2.LOTRWorldGenBlackUrukFort;
import lotr.common.world.structure2.LOTRWorldGenMordorCamp;
import lotr.common.world.structure2.LOTRWorldGenMordorWargPit;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class LOTRBiomeGenMordor extends LOTRBiome {
	public static NoiseGeneratorPerlin noiseDirt = new NoiseGeneratorPerlin(new Random(389502092662L), 1);
	public static NoiseGeneratorPerlin noiseGravel = new NoiseGeneratorPerlin(new Random(1379468206L), 1);
	public WorldGenerator boulderGen = new LOTRWorldGenBoulder(LOTRMod.rock, 0, 2, 8);
	public boolean enableMordorBoulders = true;

	public LOTRBiomeGenMordor(int i, boolean major) {
		super(i, major);
		topBlock = LOTRMod.rock;
		topBlockMeta = 0;
		fillerBlock = LOTRMod.rock;
		fillerBlockMeta = 0;
		if (isGorgoroth()) {
			setDisableRain();
		}
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableLOTRAmbientList.clear();
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[5];
		arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 30);
		arrspawnListContainer[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_BOMBARDIERS, 5);
		arrspawnListContainer[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 30);
		arrspawnListContainer[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.OLOG_HAI, 10);
		arrspawnListContainer[4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 7);
		npcSpawnList.newFactionList(100).add(arrspawnListContainer);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[1];
		arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WICKED_DWARVES, 10);
		npcSpawnList.newFactionList(1).add(arrspawnListContainer2);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer3 = new LOTRBiomeSpawnList.SpawnListContainer[1];
		arrspawnListContainer3[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ROHIRRIM_WARRIORS, 10);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer3);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer4 = new LOTRBiomeSpawnList.SpawnListContainer[2];
		arrspawnListContainer4[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10);
		arrspawnListContainer4[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_ITHILIEN, 3);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer4);
		npcSpawnList.conquestGainRate = 0.5f;
		decorator.clearOres();
		decorator.addSoil(new WorldGenMinable(LOTRMod.mordorDirt, 0, 60, LOTRMod.rock), 10.0f, 0, 60);
		decorator.addSoil(new WorldGenMinable(LOTRMod.mordorGravel, 0, 32, LOTRMod.rock), 10.0f, 0, 60);
		decorator.addOre(new WorldGenMinable(LOTRMod.oreNaurite, 12, LOTRMod.rock), 20.0f, 0, 64);
		decorator.addOre(new WorldGenMinable(LOTRMod.oreMorgulIron, 1, 8, LOTRMod.rock), 20.0f, 0, 64);
		decorator.addOre(new WorldGenMinable(LOTRMod.oreGulduril, 1, 8, LOTRMod.rock), 6.0f, 0, 32);
		decorator.flowersPerChunk = 0;
		decorator.grassPerChunk = 1;
		decorator.generateWater = false;
		if (isGorgoroth()) {
			decorator.sandPerChunk = 0;
			decorator.clayPerChunk = 0;
			decorator.dryReedChance = 1.0f;
			enableRocky = false;
		}
		decorator.addTree(LOTRTreeType.CHARRED, 1000);
		decorator.addRandomStructure(new LOTRWorldGenMordorCamp(false), 100);
		decorator.addRandomStructure(new LOTRWorldGenMordorWargPit(false), 300);
		decorator.addRandomStructure(new LOTRWorldGenMordorTower(false), 500);
		decorator.addRandomStructure(new LOTRWorldGenBlackUrukFort(false), 2000);
		biomeColors.setGrass(5980459);
		biomeColors.setFoliage(6508333);
		biomeColors.setSky(6700595);
		biomeColors.setClouds(4924185);
		biomeColors.setFog(3154711);
		biomeColors.setWater(2498845);
		setBanditChance(LOTREventSpawner.EventChance.NEVER);
	}

	public static boolean isSurfaceMordorBlock(IBlockAccess world, int i, int j, int k) {
		Block block = world.getBlock(i, j, k);
		int meta = world.getBlockMetadata(i, j, k);
		return block == LOTRMod.rock && meta == 0 || block == LOTRMod.mordorDirt || block == LOTRMod.mordorGravel;
	}

	@Override
	public boolean canSpawnHostilesInDay() {
		return true;
	}

    @Override
    public void decorate(World world, Random random, int i, int k) {
        super.decorate(world, random, i, k);
        if (isGorgoroth()) {
            if (enableMordorBoulders && random.nextInt(24) == 0) {
                for (int l = 0; l < 6; ++l) {
                    int i12 = i + random.nextInt(16) + 8;
                    int k12 = k + random.nextInt(16) + 8;
                    boulderGen.generate(world, random, i12, world.getHeight(), k12);
                }
            }

            if (random.nextInt(60) == 0) {
                for (int l = 0; l < 8; ++l) {
                    int i12 = i + random.nextInt(16) + 8;
                    int k12 = k + random.nextInt(16) + 8;
                    int j12 = world.getHeight();
                    decorator.genTree(world, random, i12, j12, k12);
                }
            }

            if (decorator.grassPerChunk > 0) {
                for (int l = 0; l < 6; ++l) {
                    int i12 = i + random.nextInt(6) + 8;
                    int k12 = k + random.nextInt(6) + 8;
                    int j12 = world.getHeight();
                    if (world.isAirBlock(i12, j12, k12) && LOTRMod.mordorThorn.canBlockStay(world, i12, j12, k12)) {
                        world.setBlock(i12, j12, k12, LOTRMod.mordorThorn, 0, 2);
                    }
                }

                int i1 = i + random.nextInt(16) + 8;
                int k1 = k + random.nextInt(16) + 8;
                int j1 = world.getHeight();
                if (random.nextInt(20) == 0 && world.isAirBlock(i1, j1, k1) && LOTRMod.mordorMoss.canBlockStay(world, i1, j1, k1)) {
                    new LOTRWorldGenMordorMoss().generate(world, random, i1, j1, k1);
                }
            }
        }

        if (LOTRFixedStructures.MORDOR_CHERRY_TREE.isAt(world, i, k)) {
            int i1 = i + 8;
            int k1 = k + 8;
            int j1 = world.getHeight();
            LOTRTreeType.CHERRY_MORDOR.create(false, random).generate(world, random, i1, j1, k1);
        }
    }


    @Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
		Block topBlock_pre = topBlock;
		int topBlockMeta_pre = topBlockMeta;
		Block fillerBlock_pre = fillerBlock;
		int fillerBlockMeta_pre = fillerBlockMeta;
		if (isGorgoroth() && hasMordorSoils()) {
			double d1 = noiseDirt.func_151601_a(i * 0.08, k * 0.08);
			double d2 = noiseDirt.func_151601_a(i * 0.4, k * 0.4);
			double d3 = noiseGravel.func_151601_a(i * 0.08, k * 0.08);
			if (d3 + noiseGravel.func_151601_a(i * 0.4, k * 0.4) > 0.8) {
				topBlock = LOTRMod.mordorGravel;
				topBlockMeta = 0;
				fillerBlock = topBlock;
				fillerBlockMeta = topBlockMeta;
			} else if (d1 + d2 > 0.5) {
				topBlock = LOTRMod.mordorDirt;
				topBlockMeta = 0;
				fillerBlock = topBlock;
				fillerBlockMeta = topBlockMeta;
			}
		}
		super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		topBlock = topBlock_pre;
		topBlockMeta = topBlockMeta_pre;
		fillerBlock = fillerBlock_pre;
		fillerBlockMeta = fillerBlockMeta_pre;
	}

	@Override
	public void generateMountainTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int xzIndex, int ySize, int height, int rockDepth, LOTRBiomeVariant variant) {
		for (int j = ySize - 1; j >= 0; --j) {
			int index = xzIndex * ySize + j;
			Block block = blocks[index];
			if (block != Blocks.stone) {
				continue;
			}
			blocks[index] = LOTRMod.rock;
			meta[index] = 0;
		}
	}

	@Override
	public LOTRAchievement getBiomeAchievement() {
		return LOTRAchievement.enterMordor;
	}

	@Override
	public LOTRMusicRegion.Sub getBiomeMusic() {
		return LOTRMusicRegion.MORDOR.getSubregion("mordor");
	}

	@Override
	public LOTRWaypoint.Region getBiomeWaypoints() {
		return LOTRWaypoint.Region.MORDOR;
	}

	@Override
	public LOTRRoadType.BridgeType getBridgeBlock() {
		return LOTRRoadType.BridgeType.CHARRED;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.0f;
	}

	@Override
	public LOTRBiome.GrassBlockAndMeta getRandomGrass(Random random) {
		if (isGorgoroth()) {
			return new LOTRBiome.GrassBlockAndMeta(LOTRMod.mordorGrass, 0);
		}
		return super.getRandomGrass(random);
	}

	@Override
	public LOTRRoadType getRoadBlock() {
		return LOTRRoadType.MORDOR;
	}

	@Override
	public float getTreeIncreaseChance() {
		return 0.0f;
	}

	public boolean hasMordorSoils() {
		return true;
	}

	@Override
	public boolean hasSky() {
		return !isGorgoroth();
	}

	public boolean isGorgoroth() {
		return true;
	}
}
