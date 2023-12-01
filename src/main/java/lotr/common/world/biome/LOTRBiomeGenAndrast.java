package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.structure.LOTRWorldGenGondorRuin;
import lotr.common.world.structure.LOTRWorldGenGondorRuins;
import lotr.common.world.structure.LOTRWorldGenRuinedGondorTower;
import lotr.common.world.structure2.LOTRWorldGenGondorObelisk;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class LOTRBiomeGenAndrast extends LOTRBiomeGenGondor {
	public static NoiseGeneratorPerlin noiseDirt = new NoiseGeneratorPerlin(new Random(285939985023633003L), 1);
	public static NoiseGeneratorPerlin noiseStone = new NoiseGeneratorPerlin(new Random(4148990259960304L), 1);
	public WorldGenerator boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 4);
	public WorldGenerator boulderGenGondor = new LOTRWorldGenBoulder(LOTRMod.rock, 1, 1, 4);

	public LOTRBiomeGenAndrast(int i, boolean major) {
		super(i, major);
		npcSpawnList.clear();
		clearBiomeVariants();
		variantChance = 0.5f;
		addBiomeVariant(LOTRBiomeVariant.FLOWERS);
		addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
		addBiomeVariant(LOTRBiomeVariant.FOREST);
		addBiomeVariant(LOTRBiomeVariant.HILLS);
		addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
		addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
		addBiomeVariant(LOTRBiomeVariant.SHRUBLAND_OAK);
		addBiomeVariant(LOTRBiomeVariant.SCRUBLAND, 3.0f);
		addBiomeVariant(LOTRBiomeVariant.HILLS_SCRUBLAND);
		addBiomeVariant(LOTRBiomeVariant.WASTELAND, 3.0f);
		decorator.setTreeCluster(10, 30);
		decorator.treesPerChunk = 0;
		decorator.flowersPerChunk = 3;
		decorator.doubleFlowersPerChunk = 1;
		decorator.grassPerChunk = 12;
		decorator.doubleGrassPerChunk = 4;
		decorator.addTree(LOTRTreeType.OAK, 400);
		decorator.addTree(LOTRTreeType.OAK_LARGE, 200);
		decorator.addTree(LOTRTreeType.BIRCH, 50);
		decorator.addTree(LOTRTreeType.BIRCH_LARGE, 20);
		decorator.addTree(LOTRTreeType.BEECH, 50);
		decorator.addTree(LOTRTreeType.BEECH_LARGE, 20);
		decorator.addTree(LOTRTreeType.DARK_OAK, 500);
		decorator.addTree(LOTRTreeType.FIR, 200);
		decorator.addTree(LOTRTreeType.PINE, 200);
		decorator.addTree(LOTRTreeType.SPRUCE, 200);
		decorator.addTree(LOTRTreeType.LARCH, 200);
		decorator.addTree(LOTRTreeType.APPLE, 5);
		decorator.addTree(LOTRTreeType.PEAR, 5);
		decorator.addTree(LOTRTreeType.PLUM, 5);
		decorator.addTree(LOTRTreeType.OAK_SHRUB, 1500);
		registerPlainsFlowers();
		biomeColors.setGrass(10202470);
		decorator.clearVillages();
		decorator.clearRandomStructures();
		decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 400);
		decorator.addRandomStructure(new LOTRWorldGenGondorRuins(), 2000);
		decorator.addRandomStructure(new LOTRWorldGenRuinedGondorTower(false), 2000);
		decorator.addRandomStructure(new LOTRWorldGenGondorObelisk(false), 2000);
		decorator.addRandomStructure(new LOTRWorldGenGondorRuin(false), 1500);
		clearTravellingTraders();
		setBanditChance(LOTREventSpawner.EventChance.BANDIT_UNCOMMON);
		invasionSpawns.clearInvasions();
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
        BlockFalling.fallInstantly = true;
		super.decorate(world, random, i, k);
		if (random.nextInt(5) == 0) {
			for (int l = 0; l < 4; ++l) {
				int i1 = i + random.nextInt(16) + 8;
				int k1 = k + random.nextInt(16) + 8;
				if (random.nextBoolean()) {
					boulderGen.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
					continue;
				}
				boulderGenGondor.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
			}
		}
        BlockFalling.fallInstantly = false;
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
		Block topBlock_pre = topBlock;
		int topBlockMeta_pre = topBlockMeta;
		Block fillerBlock_pre = fillerBlock;
		int fillerBlockMeta_pre = fillerBlockMeta;
		double d1 = noiseDirt.func_151601_a(i * 0.07, k * 0.07);
		double d2 = noiseDirt.func_151601_a(i * 0.3, k * 0.3);
		double d3 = noiseStone.func_151601_a(i * 0.07, k * 0.07);
		if (d3 + noiseStone.func_151601_a(i * 0.3, k * 0.3) > 1.1) {
			topBlock = Blocks.stone;
			topBlockMeta = 0;
			fillerBlock = topBlock;
			fillerBlockMeta = topBlockMeta;
		} else if (d1 + d2 > 0.6) {
			topBlock = Blocks.dirt;
			topBlockMeta = 1;
		}
		super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		topBlock = topBlock_pre;
		topBlockMeta = topBlockMeta_pre;
		fillerBlock = fillerBlock_pre;
		fillerBlockMeta = fillerBlockMeta_pre;
	}

	@Override
	public LOTRAchievement getBiomeAchievement() {
		return LOTRAchievement.enterAndrast;
	}

	@Override
	public LOTRMusicRegion.Sub getBiomeMusic() {
		return LOTRMusicRegion.PUKEL.getSubregion("andrast");
	}

	@Override
	public float getTreeIncreaseChance() {
		return 0.5f;
	}

	@Override
	public int spawnCountMultiplier() {
		return 1;
	}
}
