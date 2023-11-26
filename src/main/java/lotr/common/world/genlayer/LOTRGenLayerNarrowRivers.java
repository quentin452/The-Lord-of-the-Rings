package lotr.common.world.genlayer;

import net.minecraft.world.World;

public class LOTRGenLayerNarrowRivers extends LOTRGenLayer {
	public int maxRange;

	public LOTRGenLayerNarrowRivers(long l, LOTRGenLayer layer, int r) {
		super(l);
		lotrParent = layer;
		maxRange = r;
	}

	@Override
	public int[] getInts(World world, int i, int k, int xSize, int zSize) {
		int[] rivers = lotrParent.getInts(world, i - maxRange, k - maxRange, xSize + maxRange * 2, zSize + maxRange * 2);
		int[] ints = new int[xSize * zSize];
        for (int k1 = 0; k1 < zSize; ++k1) {
			for (int i1 = 0; i1 < xSize; ++i1) {
				initChunkSeed(i + i1, k + k1);
				int isRiver = rivers[i1 + maxRange + (k1 + maxRange) * (xSize + maxRange * 2)];
				if (isRiver > 0) {
					block2:
					for (int range = 1; range <= maxRange; ++range) {
						for (int k2 = k1 - range; k2 <= k1 + range; ++k2) {
							for (int i2 = i1 - range; i2 <= i1 + range; ++i2) {
								if (Math.abs(i2 - i1) != range && Math.abs(k2 - k1) != range || rivers[i2 + maxRange + (k2 + maxRange) * (xSize + maxRange * 2)] != 0) {
									continue;
								}
								isRiver = 0;
								break block2;
							}
						}
					}
				}
				ints[i1 + k1 * xSize] = isRiver;
			}
		}
		return ints;
	}
}
