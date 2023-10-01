package lotr.common.world.biome;

import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.animal.LOTREntityFlamingo;
import lotr.common.entity.animal.LOTREntityJungleScorpion;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTRBiomeGenFarHaradCloudForest extends LOTRBiomeGenFarHarad {
	public LOTRBiomeGenFarHaradCloudForest(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityFlamingo.class, 10, 4, 4));
		spawnableLOTRAmbientList.clear();
		spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityBird.class, 10, 4, 4));
		spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityButterfly.class, 10, 4, 4));
		spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(LOTREntityJungleScorpion.class, 30, 4, 4));
		addBiomeVariantSet(LOTRBiomeVariant.SET_FOREST);
		decorator.treesPerChunk = 10;
		decorator.vinesPerChunk = 50;
		decorator.flowersPerChunk = 4;
		decorator.doubleFlowersPerChunk = 4;
		decorator.grassPerChunk = 15;
		decorator.doubleGrassPerChunk = 10;
		decorator.enableFern = true;
		decorator.melonPerChunk = 0.1f;
		decorator.clearTrees();
		decorator.addTree(LOTRTreeType.JUNGLE_CLOUD, 4000);
		decorator.addTree(LOTRTreeType.JUNGLE, 500);
		decorator.addTree(LOTRTreeType.JUNGLE_SHRUB, 1000);
		decorator.addTree(LOTRTreeType.MANGO, 20);
		registerJungleFlowers();
		biomeColors.setGrass(2007124);
		biomeColors.setFoliage(428338);
		biomeColors.setSky(11452859);
		biomeColors.setFoggy(true);
	}

	@Override
	public LOTRMusicRegion.Sub getBiomeMusic() {
		return LOTRMusicRegion.FAR_HARAD_JUNGLE.getSubregion("cloudForest");
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return super.getChanceToSpawnAnimals() * 0.5f;
	}
}
