package lotr.common.world.genlayer;

import net.minecraft.world.World;

public class LOTRGenLayerRiverInit extends LOTRGenLayer {
	public LOTRGenLayerRiverInit(long l) {
		super(l);
	}

	@Override
	public int[] getInts(World world, int i, int k, int xSize, int zSize) {
		int[] ints = new int[xSize * zSize];
		for (int k1 = 0; k1 < zSize; ++k1) {
			for (int i1 = 0; i1 < xSize; ++i1) {
				initChunkSeed(i + i1, k + k1);
				ints[i1 + k1 * xSize] = 2 + nextInt(299999);
			}
		}
		return ints;
	}
}
