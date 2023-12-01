package lotr.common.world.biome;

import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.animal.LOTREntityGorcrow;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenWebOfUngoliant;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.block.BlockFalling;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Random;

public class LOTRBiomeGenMirkwoodCorrupted extends LOTRBiomeGenMirkwood {
	public LOTRBiomeGenMirkwoodCorrupted(int i, boolean major) {
		super(i, major);
		spawnableWaterCreatureList.clear();
		spawnableLOTRAmbientList.clear();
		spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityButterfly.class, 10, 4, 4));
		spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityGorcrow.class, 6, 4, 4));
		variantChance = 0.2f;
		addBiomeVariant(LOTRBiomeVariant.HILLS);
		decorator.treesPerChunk = 8;
		decorator.willowPerChunk = 1;
		decorator.vinesPerChunk = 20;
		decorator.logsPerChunk = 3;
		decorator.flowersPerChunk = 0;
		decorator.grassPerChunk = 12;
		decorator.doubleGrassPerChunk = 6;
		decorator.enableFern = true;
		decorator.mushroomsPerChunk = 4;
		decorator.generateCobwebs = false;
		decorator.addTree(LOTRTreeType.MIRK_OAK_LARGE, 1000);
		decorator.addTree(LOTRTreeType.OAK_LARGE, 300);
		decorator.addTree(LOTRTreeType.SPRUCE, 200);
		decorator.addTree(LOTRTreeType.FIR, 200);
		decorator.addTree(LOTRTreeType.PINE, 400);
		biomeColors.setGrass(2841381);
		biomeColors.setFoliage(2503461);
		biomeColors.setFog(3302525);
		biomeColors.setFoggy(true);
		biomeColors.setWater(1708838);
		setBanditChance(LOTREventSpawner.EventChance.NEVER);
		invasionSpawns.addInvasion(LOTRInvasions.WOOD_ELF, LOTREventSpawner.EventChance.UNCOMMON);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
        BlockFalling.fallInstantly = true;
		int i1;
		int l;
		super.decorate(world, random, i, k);
		if (decorator.treesPerChunk > 2) {
			for (l = 0; l < decorator.treesPerChunk / 2; ++l) {
				i1 = i + random.nextInt(16) + 8;
				int k1 = k + random.nextInt(16) + 8;
				int j1 = world.getTopSolidOrLiquidBlock(i1, k1);
				LOTRTreeType.MIRK_OAK.create(false, random).generate(world, random, i1, j1, k1);
			}
		}
		for (l = 0; l < 6; ++l) {
			i1 = i + random.nextInt(16) + 8;
			int j1 = random.nextInt(128);
			int k1 = k + random.nextInt(16) + 8;
			new LOTRWorldGenWebOfUngoliant(false, 64).generate(world, random, i1, j1, k1);
		}
        BlockFalling.fallInstantly = false;
	}

	@Override
	public LOTRMusicRegion.Sub getBiomeMusic() {
		return LOTRMusicRegion.MIRKWOOD.getSubregion("mirkwood");
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.1f;
	}
}
