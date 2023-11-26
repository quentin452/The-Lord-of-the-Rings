package lotr.common.world.genlayer;

import net.minecraft.world.World;

public class LOTRGenLayerMapRiverZoom extends LOTRGenLayer {
	public LOTRGenLayerMapRiverZoom(long l, LOTRGenLayer layer) {
		super(l);
		lotrParent = layer;
	}

	@Override
	public int[] getInts(World world, int i, int k, int xSize, int zSize) {
		int i1 = (i >> 1) - 1;
		int k1 = (k >> 1) - 1;
		int xSizeZoom = (xSize >> 1) + 4;
		int zSizeZoom = (zSize >> 1) + 4;
		int[] rivers = lotrParent.getInts(world, i1, k1, xSizeZoom, zSizeZoom);
		int i2 = xSizeZoom - 3 << 1;
		int k2 = zSizeZoom - 3 << 1;
		int[] ints = new int[i2 * k2];
		for (int k3 = 0; k3 < zSizeZoom - 3; ++k3) {
			for (int i3 = 0; i3 < xSizeZoom - 3; ++i3) {
				initChunkSeed((long) i3 + i1 + 1 << 1, (long) k3 + k1 + 1 << 1);
				for (int i4 = 0; i4 <= 1; ++i4) {
					for (int k4 = 0; k4 <= 1; ++k4) {
						int int00 = rivers[i3 + 1 + (k3 + 1) * xSizeZoom];
						int opp = int00 == 0 ? 2 : 0;
						boolean replaceCorner = false;
						if (i4 == 0 && k4 == 0) {
							replaceCorner = rivers[i3 + (k3) * xSizeZoom] == opp && rivers[i3 + 1 + (k3) * xSizeZoom] == opp && rivers[i3 + (k3 + 1) * xSizeZoom] == opp;
						}
						if (i4 == 1 && k4 == 0) {
							replaceCorner = rivers[i3 + 1 + (k3) * xSizeZoom] == opp && rivers[i3 + 2 + (k3) * xSizeZoom] == opp && rivers[i3 + 2 + (k3 + 1) * xSizeZoom] == opp;
						}
						if (i4 == 0 && k4 == 1) {
							replaceCorner = rivers[i3 + (k3 + 1) * xSizeZoom] == opp && rivers[i3 + (k3 + 2) * xSizeZoom] == opp && rivers[i3 + 1 + (k3 + 2) * xSizeZoom] == opp;
						}
						if (i4 == 1 && k4 == 1) {
							replaceCorner = rivers[i3 + 2 + (k3 + 1) * xSizeZoom] == opp && rivers[i3 + 1 + (k3 + 2) * xSizeZoom] == opp && rivers[i3 + 2 + (k3 + 2) * xSizeZoom] == opp;
						}
						if (replaceCorner) {
							int00 = opp;
						}
						int index = (i3 << 1) + i4 + ((k3 << 1) + k4) * i2;
						ints[index] = int00;
					}
				}
			}
		}
		int[] zoomedInts = new int[xSize * zSize];
        for (int k3 = 0; k3 < zSize; ++k3) {
			System.arraycopy(ints, (k3 + (k & 1)) * i2 + (i & 1), zoomedInts, k3 * xSize, xSize);
		}
		return zoomedInts;
	}
}
