package lotr.common.world.biome.variant;

import lotr.common.LOTRMod;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenerator;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class LOTRBiomeVariant {
	public static LOTRBiomeVariant[] allVariants = new LOTRBiomeVariant[256];
	public static LOTRBiomeVariant STANDARD = new LOTRBiomeVariant(0, "standard", VariantScale.ALL);
	public static LOTRBiomeVariant FLOWERS = new LOTRBiomeVariant(1, "flowers", VariantScale.SMALL).setFlowers(10.0f);
	public static LOTRBiomeVariant FOREST = new LOTRBiomeVariantForest(2, "forest");
	public static LOTRBiomeVariant FOREST_LIGHT = new LOTRBiomeVariant(3, "forest_light", VariantScale.ALL).setTemperatureRainfall(0.0f, 0.2f).setTrees(3.0f).setGrass(2.0f);
	public static LOTRBiomeVariant STEPPE = new LOTRBiomeVariant(4, "steppe", VariantScale.LARGE).setTemperatureRainfall(0.0f, -0.1f).setHeight(0.0f, 0.1f).setTrees(0.01f).setGrass(3.0f).setFlowers(0.8f);
	public static LOTRBiomeVariant STEPPE_BARREN = new LOTRBiomeVariant(5, "steppe_barren", VariantScale.LARGE).setTemperatureRainfall(0.1f, -0.2f).setHeight(0.0f, 0.1f).setTrees(0.01f).setGrass(0.2f).setFlowers(0.4f);
	public static LOTRBiomeVariant HILLS = new LOTRBiomeVariant(6, "hills", VariantScale.ALL).setTemperatureRainfall(-0.1f, -0.1f).setHeight(0.5f, 1.5f).setGrass(0.5f);
	public static LOTRBiomeVariant HILLS_FOREST = new LOTRBiomeVariant(7, "hills_forest", VariantScale.ALL).setTemperatureRainfall(-0.1f, 0.0f).setHeight(0.5f, 1.5f).setTrees(3.0f);
	public static LOTRBiomeVariant MOUNTAIN = new LOTRBiomeVariant(8, "mountain", VariantScale.ALL).setTemperatureRainfall(-0.1f, -0.2f).setHeight(1.2f, 3.0f).setFlowers(0.8f);
	public static LOTRBiomeVariant CLEARING = new LOTRBiomeVariant(9, "clearing", VariantScale.SMALL).setHeight(0.0f, 0.5f).setTrees(0.0f).setGrass(2.0f).setFlowers(3.0f);
	public static LOTRBiomeVariant DENSEFOREST_OAK = new LOTRBiomeVariantDenseForest(10, "denseForest_oak").addTreeTypes(0.5f, LOTRTreeType.OAK_LARGE, 600, LOTRTreeType.OAK_PARTY, 100);
	public static LOTRBiomeVariant DENSEFOREST_SPRUCE = new LOTRBiomeVariantDenseForest(11, "denseForest_spruce").addTreeTypes(0.5f, LOTRTreeType.SPRUCE_MEGA, 100);
	public static LOTRBiomeVariant DENSEFOREST_OAK_SPRUCE = new LOTRBiomeVariantDenseForest(12, "denseForest_oak_spruce").addTreeTypes(0.5f, LOTRTreeType.OAK_LARGE, 600, LOTRTreeType.OAK_PARTY, 200, LOTRTreeType.SPRUCE_MEGA, 200);
	public static LOTRBiomeVariant DEADFOREST_OAK = new LOTRBiomeVariantDeadForest(13, "deadForest_oak").addTreeTypes(0.5f, LOTRTreeType.OAK_DEAD, 100);
	public static LOTRBiomeVariant DEADFOREST_SPRUCE = new LOTRBiomeVariantDeadForest(14, "deadForest_spruce").addTreeTypes(0.5f, LOTRTreeType.SPRUCE_DEAD, 100);
	public static LOTRBiomeVariant DEADFOREST_OAK_SPRUCE = new LOTRBiomeVariantDeadForest(15, "deadForest_oak_spruce").addTreeTypes(0.5f, LOTRTreeType.OAK_DEAD, 100, LOTRTreeType.SPRUCE_DEAD, 100);
	public static LOTRBiomeVariant SHRUBLAND_OAK = new LOTRBiomeVariant(16, "shrubland_oak", VariantScale.ALL).setTemperatureRainfall(0.0f, 0.3f).setTrees(6.0f).addTreeTypes(0.7f, LOTRTreeType.OAK_SHRUB, 100);
	public static LOTRBiomeVariant DENSEFOREST_BIRCH = new LOTRBiomeVariantDenseForest(17, "denseForest_birch").addTreeTypes(0.5f, LOTRTreeType.BIRCH_LARGE, 600, LOTRTreeType.BIRCH_PARTY, 100);
	public static LOTRBiomeVariant SWAMP_LOWLAND = new LOTRBiomeVariant(18, "swampLowland", VariantScale.SMALL).setHeight(-0.12f, 0.2f).setTrees(0.5f).setGrass(5.0f).setMarsh();
	public static LOTRBiomeVariant SWAMP_UPLAND = new LOTRBiomeVariant(19, "swampUpland", VariantScale.SMALL).setHeight(0.12f, 1.0f).setTrees(6.0f).setGrass(5.0f);
	public static LOTRBiomeVariant SAVANNAH_BAOBAB = new LOTRBiomeVariant(20, "savannahBaobab", VariantScale.LARGE).setHeight(0.0f, 0.5f).setTemperatureRainfall(0.0f, 0.2f).setTrees(1.5f).setGrass(0.5f).addTreeTypes(0.6f, LOTRTreeType.BAOBAB, 100);
	public static LOTRBiomeVariant LAKE = new LOTRBiomeVariant(21, "lake", VariantScale.NONE).setAbsoluteHeight(-0.5f, 0.05f);
	public static LOTRBiomeVariant DENSEFOREST_LEBETHRON = new LOTRBiomeVariantDenseForest(22, "denseForest_lebethron").addTreeTypes(0.5f, LOTRTreeType.LEBETHRON_LARGE, 600, LOTRTreeType.LEBETHRON_PARTY, 100);
	public static LOTRBiomeVariant BOULDERS_RED = new LOTRBiomeVariant(23, "boulders_red", VariantScale.LARGE).setBoulders(new LOTRWorldGenBoulder(LOTRMod.redSandstone, 1, 1, 3), 2, 4);
	public static LOTRBiomeVariant BOULDERS_ROHAN = new LOTRBiomeVariant(24, "boulders_rohan", VariantScale.LARGE).setBoulders(new LOTRWorldGenBoulder(LOTRMod.rock, 2, 1, 3), 2, 4);
	public static LOTRBiomeVariant JUNGLE_DENSE = new LOTRBiomeVariant(25, "jungle_dense", VariantScale.LARGE).setTemperatureRainfall(0.1f, 0.1f).setTrees(2.0f).addTreeTypes(0.6f, LOTRTreeType.JUNGLE_FANGORN, 1000, LOTRTreeType.MAHOGANY_FANGORN, 500);
	public static LOTRBiomeVariant VINEYARD = new LOTRBiomeVariant(26, "vineyard", VariantScale.SMALL).setHeight(0.0f, 0.5f).setTrees(0.0f).setGrass(0.5f).setFlowers(0.0f).disableStructuresVillages();
	public static LOTRBiomeVariant FOREST_ASPEN = new LOTRBiomeVariantForest(27, "forest_aspen").addTreeTypes(0.8f, LOTRTreeType.ASPEN, 1000, LOTRTreeType.ASPEN_LARGE, 50);
	public static LOTRBiomeVariant FOREST_BIRCH = new LOTRBiomeVariantForest(28, "forest_birch").addTreeTypes(0.8f, LOTRTreeType.BIRCH, 1000, LOTRTreeType.BIRCH_LARGE, 150);
	public static LOTRBiomeVariant FOREST_BEECH = new LOTRBiomeVariantForest(29, "forest_beech").addTreeTypes(0.8f, LOTRTreeType.BEECH, 1000, LOTRTreeType.BEECH_LARGE, 150);
	public static LOTRBiomeVariant FOREST_MAPLE = new LOTRBiomeVariantForest(30, "forest_maple").addTreeTypes(0.8f, LOTRTreeType.MAPLE, 1000, LOTRTreeType.MAPLE_LARGE, 150);
	public static LOTRBiomeVariant FOREST_LARCH = new LOTRBiomeVariantForest(31, "forest_larch").addTreeTypes(0.8f, LOTRTreeType.LARCH, 1000);
	public static LOTRBiomeVariant FOREST_PINE = new LOTRBiomeVariantForest(32, "forest_pine").addTreeTypes(0.8f, LOTRTreeType.PINE, 1000);
	public static LOTRBiomeVariant ORCHARD_SHIRE = new LOTRBiomeVariantOrchard(33, "orchard_shire").addTreeTypes(1.0f, LOTRTreeType.APPLE, 100, LOTRTreeType.PEAR, 100, LOTRTreeType.CHERRY, 10);
	public static LOTRBiomeVariant ORCHARD_APPLE_PEAR = new LOTRBiomeVariantOrchard(34, "orchard_apple_pear").addTreeTypes(1.0f, LOTRTreeType.APPLE, 100, LOTRTreeType.PEAR, 100);
	public static LOTRBiomeVariant ORCHARD_ORANGE = new LOTRBiomeVariantOrchard(35, "orchard_orange").addTreeTypes(1.0f, LOTRTreeType.ORANGE, 100);
	public static LOTRBiomeVariant ORCHARD_LEMON = new LOTRBiomeVariantOrchard(36, "orchard_lemon").addTreeTypes(1.0f, LOTRTreeType.LEMON, 100);
	public static LOTRBiomeVariant ORCHARD_LIME = new LOTRBiomeVariantOrchard(37, "orchard_lime").addTreeTypes(1.0f, LOTRTreeType.LIME, 100);
	public static LOTRBiomeVariant ORCHARD_ALMOND = new LOTRBiomeVariantOrchard(38, "orchard_almond").addTreeTypes(1.0f, LOTRTreeType.ALMOND, 100);
	public static LOTRBiomeVariant ORCHARD_OLIVE = new LOTRBiomeVariantOrchard(39, "orchard_olive").addTreeTypes(1.0f, LOTRTreeType.OLIVE, 100);
	public static LOTRBiomeVariant ORCHARD_PLUM = new LOTRBiomeVariantOrchard(40, "orchard_plum").addTreeTypes(1.0f, LOTRTreeType.PLUM, 100);
	public static LOTRBiomeVariant RIVER = new LOTRBiomeVariant(41, "river", VariantScale.NONE).setAbsoluteHeight(-0.5f, 0.05f).setTemperatureRainfall(0.0f, 0.3f);
	public static LOTRBiomeVariant SCRUBLAND = new LOTRBiomeVariantScrubland(42, "scrubland", Blocks.stone).setHeight(0.0f, 0.8f);
	public static LOTRBiomeVariant HILLS_SCRUBLAND = new LOTRBiomeVariantScrubland(43, "hills_scrubland", Blocks.stone).setHeight(0.5f, 2.0f);
	public static LOTRBiomeVariant WASTELAND = new LOTRBiomeVariantWasteland(44, "wasteland", Blocks.stone).setHeight(0.0f, 0.5f);
	public static LOTRBiomeVariant ORCHARD_DATE = new LOTRBiomeVariantOrchard(45, "orchard_date").addTreeTypes(1.0f, LOTRTreeType.DATE_PALM, 100);
	public static LOTRBiomeVariant DENSEFOREST_DARK_OAK = new LOTRBiomeVariantDenseForest(46, "denseForest_darkOak").addTreeTypes(0.5f, LOTRTreeType.DARK_OAK, 600, LOTRTreeType.DARK_OAK_PARTY, 100);
	public static LOTRBiomeVariant ORCHARD_POMEGRANATE = new LOTRBiomeVariantOrchard(47, "orchard_pomegranate").addTreeTypes(1.0f, LOTRTreeType.POMEGRANATE, 100);
	public static LOTRBiomeVariant DUNES = new LOTRBiomeVariantDunes(48, "dunes");
	public static LOTRBiomeVariant SCRUBLAND_SAND = new LOTRBiomeVariantScrubland(49, "scrubland_sand", Blocks.sandstone).setHeight(0.0f, 0.8f);
	public static LOTRBiomeVariant HILLS_SCRUBLAND_SAND = new LOTRBiomeVariantScrubland(50, "hills_scrubland_sand", Blocks.sandstone).setHeight(0.5f, 2.0f);
	public static LOTRBiomeVariant WASTELAND_SAND = new LOTRBiomeVariantWasteland(51, "wasteland_sand", Blocks.sandstone).setHeight(0.0f, 0.5f);
	public static LOTRBiomeVariant[] SET_NORMAL = {FLOWERS, FOREST, FOREST_LIGHT, STEPPE, STEPPE_BARREN, HILLS, HILLS_FOREST};
	public static LOTRBiomeVariant[] SET_NORMAL_OAK = (LOTRBiomeVariant[]) ArrayUtils.addAll(SET_NORMAL, (Object[]) new LOTRBiomeVariant[]{DENSEFOREST_OAK, DEADFOREST_OAK, SHRUBLAND_OAK});
	public static LOTRBiomeVariant[] SET_NORMAL_OAK_NOSTEPPE = (LOTRBiomeVariant[]) ArrayUtils.removeElements(SET_NORMAL_OAK, (Object[]) new LOTRBiomeVariant[]{STEPPE, STEPPE_BARREN});
	public static LOTRBiomeVariant[] SET_NORMAL_SPRUCE = (LOTRBiomeVariant[]) ArrayUtils.addAll(SET_NORMAL, (Object[]) new LOTRBiomeVariant[]{DENSEFOREST_SPRUCE, DEADFOREST_SPRUCE});
	public static LOTRBiomeVariant[] SET_NORMAL_OAK_SPRUCE = (LOTRBiomeVariant[]) ArrayUtils.addAll(SET_NORMAL, (Object[]) new LOTRBiomeVariant[]{DENSEFOREST_OAK, DEADFOREST_OAK, SHRUBLAND_OAK, DENSEFOREST_SPRUCE, DEADFOREST_SPRUCE, DENSEFOREST_OAK_SPRUCE, DEADFOREST_OAK_SPRUCE});
	public static LOTRBiomeVariant[] SET_NORMAL_NOSTEPPE = (LOTRBiomeVariant[]) ArrayUtils.removeElements(SET_NORMAL, (Object[]) new LOTRBiomeVariant[]{STEPPE, STEPPE_BARREN});
	public static LOTRBiomeVariant[] SET_FOREST = {FLOWERS, HILLS, CLEARING};
	public static LOTRBiomeVariant[] SET_MOUNTAINS = {FOREST, FOREST_LIGHT};
	public static LOTRBiomeVariant[] SET_SWAMP = {SWAMP_LOWLAND, SWAMP_LOWLAND, SWAMP_LOWLAND, SWAMP_UPLAND};
	public static NoiseGeneratorPerlin marshNoise = new NoiseGeneratorPerlin(new Random(444L), 1);
	public static NoiseGeneratorPerlin podzolNoise = new NoiseGeneratorPerlin(new Random(58052L), 1);
	public int variantID;
	public String variantName;
	public VariantScale variantScale;
	public float tempBoost;
	public float rainBoost;
	public boolean absoluteHeight;
	public float absoluteHeightLevel;
	public float heightBoost;
	public float hillFactor = 1.0f;
	public float treeFactor = 1.0f;
	public float grassFactor = 1.0f;
	public float flowerFactor = 1.0f;
	public boolean hasMarsh;
	public boolean disableStructures;
	public boolean disableVillages;
	public Collection<LOTRTreeType.WeightedTreeType> treeTypes = new ArrayList<>();
	public float variantTreeChance;
	public WorldGenerator boulderGen;
	public int boulderChance;
	public int boulderMax = 1;

	public LOTRBiomeVariant(int i, String s, VariantScale scale) {
		if (allVariants[i] != null) {
			throw new IllegalArgumentException("LOTR Biome variant already exists at index " + i);
		}
		variantID = i;
		allVariants[i] = this;
		variantName = s;
		variantScale = scale;
	}

	public static LOTRBiomeVariant getVariantForID(int i) {
		LOTRBiomeVariant variant = allVariants[i];
		if (variant == null) {
			return STANDARD;
		}
		return variant;
	}

	public LOTRBiomeVariant addTreeTypes(float f, Object... trees) {
		variantTreeChance = f;
		for (int i = 0; i < trees.length / 2; ++i) {
			Object obj1 = trees[i * 2];
			Object obj2 = trees[i * 2 + 1];
			treeTypes.add(new LOTRTreeType.WeightedTreeType((LOTRTreeType) obj1, (Integer) obj2));
		}
		return this;
	}

	public void decorateVariant(World world, Random random, int i, int k, LOTRBiome biome) {
	}

	public LOTRBiomeVariant disableStructuresVillages() {
		disableStructures = true;
		disableVillages = true;
		return this;
	}

	public LOTRBiomeVariant disableVillages() {
		disableVillages = true;
		return this;
	}

	public void generateVariantTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int height, LOTRBiome biome) {
	}

	public float getHeightBoostAt(int i, int k) {
		return heightBoost;
	}

	public LOTRTreeType getRandomTree(Random random) {
		WeightedRandom.Item item = WeightedRandom.getRandomItem(random, treeTypes);
		return ((LOTRTreeType.WeightedTreeType) item).treeType;
	}

	public LOTRBiomeVariant setAbsoluteHeight(float height, float hills) {
		absoluteHeight = true;
		absoluteHeightLevel = height;
		float f = height;
		f -= 2.0f;
		heightBoost = f + 0.2f;
		hillFactor = hills;
		return this;
	}

	public LOTRBiomeVariant setBoulders(WorldGenerator boulder, int chance, int num) {
		if (num < 1) {
			throw new IllegalArgumentException("n must be > 1");
		}
		boulderGen = boulder;
		boulderChance = chance;
		boulderMax = num;
		return this;
	}

	public LOTRBiomeVariant setFlowers(float f) {
		flowerFactor = f;
		return this;
	}

	public LOTRBiomeVariant setGrass(float f) {
		grassFactor = f;
		return this;
	}

	public LOTRBiomeVariant setHeight(float height, float hills) {
		heightBoost = height;
		hillFactor = hills;
		return this;
	}

	public LOTRBiomeVariant setMarsh() {
		hasMarsh = true;
		return this;
	}

	public LOTRBiomeVariant setTemperatureRainfall(float temp, float rain) {
		tempBoost = temp;
		rainBoost = rain;
		return this;
	}

	public LOTRBiomeVariant setTrees(float f) {
		treeFactor = f;
		return this;
	}

	public enum VariantScale {
		LARGE, SMALL, ALL, NONE

	}

}
