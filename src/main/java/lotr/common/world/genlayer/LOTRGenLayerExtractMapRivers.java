package lotr.common.world.genlayer;

import lotr.common.world.biome.LOTRBiome;
import net.minecraft.world.World;

public class LOTRGenLayerExtractMapRivers extends LOTRGenLayer {
	public LOTRGenLayerExtractMapRivers(long l, LOTRGenLayer layer) {
		super(l);
		lotrParent = layer;
	}

    @Override
    public int[] getInts(World world, int i, int k, int xSize, int zSize) {

        int[] biomes = lotrParent.getInts(world, i, k, xSize, zSize);
        int[] ints = new int[xSize * zSize];
        for (int k1 = 0; k1 < zSize; ++k1) {
            for (int i1 = 0; i1 < xSize; ++i1) {
                initChunkSeed(i + i1, k + k1);
                int biomeID = biomes[i1 + k1 * xSize];
                biomeID = Math.max(0, biomeID);
                biomeID = Math.min(255, biomeID);
                ints[i1 + k1 * xSize] = biomeID == LOTRBiome.river.biomeID ? 2 : 0;
            }
        }
        return ints;
    }
}
