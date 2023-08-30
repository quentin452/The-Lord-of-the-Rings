package lotr.common.world.biome;

import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;

public class LOTRBiomeGenTaiga extends LOTRBiomeGenTundra {
	public LOTRBiomeGenTaiga(int i, boolean major) {
		super(i, major);
		clearBiomeVariants();
		variantChance = 0.75f;
		addBiomeVariantSet(LOTRBiomeVariant.SET_FOREST);
		addBiomeVariant(LOTRBiomeVariant.FOREST);
		addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
		addBiomeVariant(LOTRBiomeVariant.DEADFOREST_SPRUCE);
		addBiomeVariant(LOTRBiomeVariant.FOREST_PINE);
		decorator.treesPerChunk = 2;
		decorator.flowersPerChunk = 2;
		decorator.grassPerChunk = 8;
		decorator.doubleGrassPerChunk = 2;
		decorator.clearTrees();
		decorator.addTree(LOTRTreeType.SPRUCE, 200);
		decorator.addTree(LOTRTreeType.SPRUCE_THIN, 100);
		decorator.addTree(LOTRTreeType.SPRUCE_DEAD, 50);
		decorator.addTree(LOTRTreeType.FIR, 200);
		decorator.addTree(LOTRTreeType.PINE, 200);
		registerTaigaFlowers();
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.5f;
	}
}
