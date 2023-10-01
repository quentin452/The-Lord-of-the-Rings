package lotr.common.world.biome;

import lotr.common.entity.animal.LOTREntityGorcrow;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTRBiomeGenMirkwoodNorth extends LOTRBiomeGenMirkwood {
	public LOTRBiomeGenMirkwoodNorth(int i, boolean major) {
		super(i, major);
		spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityGorcrow.class, 4, 4, 4));
		addBiomeVariantSet(LOTRBiomeVariant.SET_FOREST);
		decorator.treesPerChunk = 12;
		decorator.willowPerChunk = 1;
		decorator.logsPerChunk = 1;
		decorator.flowersPerChunk = 2;
		decorator.doubleFlowersPerChunk = 1;
		decorator.grassPerChunk = 8;
		decorator.doubleGrassPerChunk = 6;
		decorator.enableFern = true;
		decorator.addTree(LOTRTreeType.GREEN_OAK, 1000);
		decorator.addTree(LOTRTreeType.GREEN_OAK_LARGE, 50);
		decorator.addTree(LOTRTreeType.RED_OAK, 15);
		decorator.addTree(LOTRTreeType.RED_OAK_LARGE, 10);
		decorator.addTree(LOTRTreeType.OAK, 50);
		decorator.addTree(LOTRTreeType.OAK_LARGE, 100);
		decorator.addTree(LOTRTreeType.MIRK_OAK, 50);
		decorator.addTree(LOTRTreeType.SPRUCE, 100);
		decorator.addTree(LOTRTreeType.SPRUCE_THIN, 50);
		decorator.addTree(LOTRTreeType.SPRUCE_MEGA, 20);
		decorator.addTree(LOTRTreeType.SPRUCE_MEGA_THIN, 20);
		decorator.addTree(LOTRTreeType.CHESTNUT, 20);
		decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 50);
		decorator.addTree(LOTRTreeType.LARCH, 200);
		decorator.addTree(LOTRTreeType.FIR, 200);
		decorator.addTree(LOTRTreeType.PINE, 400);
		decorator.addTree(LOTRTreeType.ASPEN, 50);
		decorator.addTree(LOTRTreeType.ASPEN_LARGE, 10);
		setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
		invasionSpawns.addInvasion(LOTRInvasions.WOOD_ELF, LOTREventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(LOTRInvasions.DOL_GULDUR, LOTREventSpawner.EventChance.UNCOMMON);
	}

	@Override
	public LOTRMusicRegion.Sub getBiomeMusic() {
		return LOTRMusicRegion.MIRKWOOD.getSubregion("north");
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.2f;
	}

	@Override
	public int spawnCountMultiplier() {
		return 4;
	}
}
