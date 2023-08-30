package lotr.common.world.biome;

import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.spawning.*;

public class LOTRBiomeGenFarHaradJungleEdge extends LOTRBiomeGenFarHaradJungle {
	public LOTRBiomeGenFarHaradJungleEdge(int i, boolean major) {
		super(i, major);
		obsidianGravelRarity = 200;
		clearBiomeVariants();
		addBiomeVariant(LOTRBiomeVariant.FLOWERS);
		addBiomeVariant(LOTRBiomeVariant.HILLS);
		decorator.treesPerChunk = 4;
		decorator.vinesPerChunk = 10;
		decorator.melonPerChunk = 0.03f;
		decorator.clearTrees();
		decorator.addTree(LOTRTreeType.JUNGLE, 200);
		decorator.addTree(LOTRTreeType.JUNGLE_LARGE, 50);
		decorator.addTree(LOTRTreeType.MAHOGANY, 50);
		decorator.addTree(LOTRTreeType.JUNGLE_SHRUB, 1000);
		decorator.addTree(LOTRTreeType.MANGO, 5);
		biomeColors.resetSky();
		biomeColors.resetFog();
		invasionSpawns.clearInvasions();
		invasionSpawns.addInvasion(LOTRInvasions.MOREDAIN, LOTREventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(LOTRInvasions.TAUREDAIN, LOTREventSpawner.EventChance.UNCOMMON);
	}

	@Override
	public LOTRMusicRegion.Sub getBiomeMusic() {
		return LOTRMusicRegion.FAR_HARAD_JUNGLE.getSubregion("edge");
	}

	@Override
	public LOTRRoadType getRoadBlock() {
		return LOTRRoadType.TAUREDAIN.setRepair(0.5f);
	}

	@Override
	public boolean hasJungleLakes() {
		return false;
	}

	@Override
	public boolean isMuddy() {
		return false;
	}
}
