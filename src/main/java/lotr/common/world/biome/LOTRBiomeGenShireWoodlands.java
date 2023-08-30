package lotr.common.world.biome;

import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;

public class LOTRBiomeGenShireWoodlands extends LOTRBiomeGenShire {
	public LOTRBiomeGenShireWoodlands(int i, boolean major) {
		super(i, major);
		variantChance = 0.2f;
		clearBiomeVariants();
		addBiomeVariant(LOTRBiomeVariant.FLOWERS);
		addBiomeVariant(LOTRBiomeVariant.HILLS);
		decorator.treesPerChunk = 9;
		decorator.flowersPerChunk = 6;
		decorator.doubleFlowersPerChunk = 2;
		decorator.grassPerChunk = 10;
		decorator.doubleGrassPerChunk = 2;
		decorator.enableFern = true;
		decorator.addTree(LOTRTreeType.BIRCH, 250);
		decorator.addTree(LOTRTreeType.SHIRE_PINE, 2500);
		decorator.addTree(LOTRTreeType.ASPEN, 300);
		decorator.addTree(LOTRTreeType.ASPEN_LARGE, 100);
		addFlower(LOTRMod.shireHeather, 0, 20);
		biomeColors.resetGrass();
	}

	@Override
	public LOTRMusicRegion.Sub getBiomeMusic() {
		return LOTRMusicRegion.SHIRE.getSubregion("woodland");
	}

	@Override
	public boolean hasDomesticAnimals() {
		return false;
	}

	@Override
	public int spawnCountMultiplier() {
		return super.spawnCountMultiplier() * 2;
	}
}
