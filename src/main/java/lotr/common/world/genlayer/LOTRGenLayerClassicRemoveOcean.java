package lotr.common.world.genlayer;

import net.minecraft.world.World;

public class LOTRGenLayerClassicRemoveOcean extends LOTRGenLayer {
	public LOTRGenLayerClassicRemoveOcean(long l, LOTRGenLayer layer) {
		super(l);
		lotrParent = layer;
	}

	@Override
	public int[] getInts(World world, int i, int k, int xSize, int zSize) {
		int[] oceans = lotrParent.getInts(world, i, k, xSize, zSize);
        int[] ints = new int[xSize * zSize];
		for (int k1 = 0; k1 < zSize; ++k1) {
			for (int i1 = 0; i1 < xSize; ++i1) {
				initChunkSeed(i + i1, k + k1);
				int isOcean = oceans[i1 + k1 * xSize];
				if (Math.abs(i + i1) <= 1 && Math.abs(k + k1) <= 1) {
					isOcean = 0;
				}
				ints[i1 + k1 * xSize] = isOcean;
			}
		}
		return ints;
	}
}
