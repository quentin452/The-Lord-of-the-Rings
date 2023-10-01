package lotr.common.world.genlayer;

import lotr.common.LOTRDimension;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.world.World;

import java.util.List;

public class LOTRGenLayerClassicBiomes extends LOTRGenLayer {
	public LOTRDimension dimension;

	public LOTRGenLayerClassicBiomes(long l, LOTRGenLayer layer, LOTRDimension dim) {
		super(l);
		lotrParent = layer;
		dimension = dim;
	}

	@Override
	public int[] getInts(World world, int i, int k, int xSize, int zSize) {
		int[] oceans = lotrParent.getInts(world, i, k, xSize, zSize);
		int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);
		for (int k1 = 0; k1 < zSize; ++k1) {
			for (int i1 = 0; i1 < xSize; ++i1) {
				int biomeID;
				initChunkSeed(i + i1, k + k1);
				int isOcean = oceans[i1 + k1 * xSize];
				if (isOcean == 1) {
					biomeID = LOTRBiome.ocean.biomeID;
				} else {
					List<LOTRBiome> biomeList = dimension.majorBiomes;
					int randIndex = nextInt(biomeList.size());
					LOTRBiome biome = biomeList.get(randIndex);
					biomeID = biome.biomeID;
				}
				ints[i1 + k1 * xSize] = biomeID;
			}
		}
		return ints;
	}
}
