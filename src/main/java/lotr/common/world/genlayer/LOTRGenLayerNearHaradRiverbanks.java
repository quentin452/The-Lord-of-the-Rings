package lotr.common.world.genlayer;

import lotr.common.LOTRDimension;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.LOTRBiomeGenNearHarad;
import net.minecraft.world.World;

public class LOTRGenLayerNearHaradRiverbanks extends LOTRGenLayer {
	public LOTRGenLayer biomeLayer;
	public LOTRGenLayer mapRiverLayer;
	public LOTRDimension dimension;

	public LOTRGenLayerNearHaradRiverbanks(long l, LOTRGenLayer biomes, LOTRGenLayer rivers, LOTRDimension dim) {
		super(l);
		biomeLayer = biomes;
		mapRiverLayer = rivers;
		dimension = dim;
	}

    @Override
    public int[] getInts(World world, int i, int k, int xSize, int zSize) {

        int[] biomes = biomeLayer.getInts(world, i - 2, k - 2, xSize + 3, zSize + 3);
        int[] mapRivers = mapRiverLayer.getInts(world, i - 2, k - 2, xSize + 3, zSize + 3);

        int[] ints = new int[xSize * zSize];

        for (int k1 = 0; k1 < zSize; ++k1) {
            for (int i1 = 0; i1 < xSize; ++i1) {

                initChunkSeed(i + i1, k + k1);

                int biomeID = biomes[i1 + 2 + (k1 + 2) * (xSize + 3)];

                biomeID = Math.max(0, biomeID);
                biomeID = Math.min(255, biomeID);

                LOTRBiome biome = dimension.biomeList[biomeID];
				int newBiomeID = biomeID;
				if (biome instanceof LOTRBiomeGenNearHarad) {
					boolean adjRiver = false;
					for (int i2 = -2; i2 <= 1; ++i2) {
						for (int k2 = -2; k2 <= 1; ++k2) {
							if (mapRivers[i1 + 2 + i2 + (k1 + 2 + k2) * (xSize + 3)] != 2) {
								continue;
							}
							adjRiver = true;
						}
					}
					if (adjRiver) {
						newBiomeID = LOTRBiome.nearHaradRiverbank.biomeID;
					}
				}
				ints[i1 + k1 * xSize] = newBiomeID;
			}
		}
		return ints;
	}

	@Override
	public void initWorldGenSeed(long l) {
		super.initWorldGenSeed(l);
		biomeLayer.initWorldGenSeed(l);
		mapRiverLayer.initWorldGenSeed(l);
	}
}
