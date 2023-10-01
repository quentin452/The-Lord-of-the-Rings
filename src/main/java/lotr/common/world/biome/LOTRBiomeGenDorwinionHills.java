package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;

public class LOTRBiomeGenDorwinionHills extends LOTRBiomeGenDorwinion {
	public LOTRBiomeGenDorwinionHills(int i, boolean major) {
		super(i, major);
		fillerBlock = LOTRMod.rock;
		fillerBlockMeta = 5;
		biomeTerrain.setXZScale(50.0);
		clearBiomeVariants();
		addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
		decorator.flowersPerChunk = 3;
		decorator.grassPerChunk = 10;
		decorator.doubleGrassPerChunk = 5;
		decorator.clearRandomStructures();
		decorator.addRandomStructure(new LOTRWorldGenStoneRuin.DORWINION(1, 4), 800);
	}

	@Override
	public LOTRAchievement getBiomeAchievement() {
		return LOTRAchievement.enterDorwinionHills;
	}

	@Override
	public float getTreeIncreaseChance() {
		return 0.25f;
	}

	@Override
	public boolean hasDomesticAnimals() {
		return false;
	}

	@Override
	public int spawnCountMultiplier() {
		return super.spawnCountMultiplier() * 3;
	}
}
