package lotr.common.world.biome;

import lotr.common.world.structure2.LOTRWorldGenMordorCamp;
import lotr.common.world.structure2.LOTRWorldGenMordorWargPit;

public class LOTRBiomeGenUdun extends LOTRBiomeGenMordor {
	public LOTRBiomeGenUdun(int i, boolean major) {
		super(i, major);
		biomeColors.setSky(6837327);
		biomeColors.setClouds(4797229);
		biomeColors.setFog(4996410);
		decorator.addRandomStructure(new LOTRWorldGenMordorCamp(false), 20);
		decorator.addRandomStructure(new LOTRWorldGenMordorWargPit(false), 100);
	}
}
