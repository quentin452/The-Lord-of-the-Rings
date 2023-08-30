package lotr.common.world.biome;

import lotr.common.world.spawning.*;

public class LOTRBiomeGenFangornClearing extends LOTRBiomeGenFangorn {
	public LOTRBiomeGenFangornClearing(int i, boolean major) {
		super(i, major);
		npcSpawnList.clear();
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[1];
		arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ENTS, 10);
		npcSpawnList.newFactionList(100).add(arrspawnListContainer);
		clearBiomeVariants();
		decorator.treesPerChunk = 0;
		decorator.flowersPerChunk = 4;
		decorator.grassPerChunk = 10;
		decorator.doubleGrassPerChunk = 8;
	}

}
