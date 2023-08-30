package lotr.common.world.genlayer;

import lotr.common.world.biome.LOTRBiome;
import net.minecraft.world.World;

public class LOTRGenLayerBiomeSubtypes extends LOTRGenLayer {
	public LOTRGenLayer biomeLayer;
	public LOTRGenLayer variantsLayer;

	public LOTRGenLayerBiomeSubtypes(long l, LOTRGenLayer biomes, LOTRGenLayer subtypes) {
		super(l);
		biomeLayer = biomes;
		variantsLayer = subtypes;
	}

	@Override
	public int[] getInts(World world, int i, int k, int xSize, int zSize) {
		int[] biomes = biomeLayer.getInts(world, i, k, xSize, zSize);
		int[] variants = variantsLayer.getInts(world, i, k, xSize, zSize);
		int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);
		for (int k1 = 0; k1 < zSize; ++k1) {
			for (int i1 = 0; i1 < xSize; ++i1) {
				initChunkSeed(i + i1, k + k1);
				int biome = biomes[i1 + k1 * xSize];
				int variant = variants[i1 + k1 * xSize];
				int newBiome = biome;
				if (biome == LOTRBiome.shire.biomeID && variant < 15 && variant != 0) {
					newBiome = LOTRBiome.shireWoodlands.biomeID;
				} else if (biome == LOTRBiome.forodwaithMountains.biomeID && variant < 5) {
					newBiome = LOTRBiome.forodwaithGlacier.biomeID;
				} else if (biome == LOTRBiome.farHarad.biomeID && variant < 20) {
					newBiome = LOTRBiome.farHaradForest.biomeID;
				} else if (biome == LOTRBiome.farHaradJungle.biomeID && variant < 15) {
					newBiome = LOTRBiome.tauredainClearing.biomeID;
				} else if (biome == LOTRBiome.pertorogwaith.biomeID && variant < 15) {
					newBiome = LOTRBiome.farHaradVolcano.biomeID;
				} else if (biome == LOTRBiome.ocean.biomeID && variant < 2) {
					newBiome = LOTRBiome.island.biomeID;
				}
				ints[i1 + k1 * xSize] = newBiome;
			}
		}
		return ints;
	}

	@Override
	public void initWorldGenSeed(long l) {
		biomeLayer.initWorldGenSeed(l);
		variantsLayer.initWorldGenSeed(l);
		super.initWorldGenSeed(l);
	}
}
