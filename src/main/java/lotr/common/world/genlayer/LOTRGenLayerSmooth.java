package lotr.common.world.genlayer;

import net.minecraft.world.World;

public class LOTRGenLayerSmooth extends LOTRGenLayer {
	public LOTRGenLayerSmooth(long l, LOTRGenLayer layer) {
		super(l);
		lotrParent = layer;
	}

    @Override
    public int[] getInts(World world, int i, int k, int xSize, int zSize) {
        int i1 = i - 1;
        int k1 = k - 1;
        int xSizeP = xSize + 2;
        int zSizeP = zSize + 2;

        int[] biomes = lotrParent.getInts(world, i1, k1, xSizeP, zSizeP);
        if (biomes == null || biomes.length < xSizeP * zSizeP) {
            return new int[xSize * zSize];
        }

        int[] ints = new int[xSize * zSize];

        for (int k2 = 0; k2 < zSize; ++k2) {
            for (int i2 = 0; i2 < xSize; ++i2) {
                int centre = biomes[i2 + 1 + (k2 + 1) * xSizeP];
                int xn = biomes[i2 + (k2 + 1) * xSizeP];
                int xp = biomes[i2 + 2 + (k2 + 1) * xSizeP];
                int zn = biomes[i2 + 1 + (k2) * xSizeP];
                int zp = biomes[i2 + 1 + (k2 + 2) * xSizeP];

                if (i2 + 2 < xSizeP && k2 + 2 < zSizeP) {
                    if (xn == xp && zn == zp) {
                        initChunkSeed(i2 + i, k2 + k);
                        centre = nextInt(2) == 0 ? xn : zn;
                    } else {
                        if (xn == xp) {
                            centre = xn;
                        }
                        if (zn == zp) {
                            centre = zn;
                        }
                    }
                }

                ints[i2 + k2 * xSize] = centre;
            }
        }
        return ints;
    }

}
