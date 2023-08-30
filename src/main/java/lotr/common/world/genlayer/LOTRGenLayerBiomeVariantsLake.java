package lotr.common.world.genlayer;

import com.google.common.math.IntMath;

import net.minecraft.world.World;

public class LOTRGenLayerBiomeVariantsLake extends LOTRGenLayer {
	public static int FLAG_LAKE = 1;
	public static int FLAG_JUNGLE = 2;
	public static int FLAG_MANGROVE = 4;
	public int zoomScale;
	public int lakeFlags;

	public LOTRGenLayerBiomeVariantsLake(long l, LOTRGenLayer layer, int i) {
		super(l);
		lotrParent = layer;
		zoomScale = IntMath.pow(2, i);
	}

	@Override
	public int[] getInts(World world, int i, int k, int xSize, int zSize) {
		int[] baseInts = lotrParent == null ? null : lotrParent.getInts(world, i, k, xSize, zSize);
		int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);
		for (int k1 = 0; k1 < zSize; ++k1) {
			for (int i1 = 0; i1 < xSize; ++i1) {
				int baseInt;
				initChunkSeed(i + i1, k + k1);
				baseInt = baseInts == null ? 0 : baseInts[i1 + k1 * xSize];
				if (getFlag(lakeFlags, 1) && nextInt(30 * zoomScale * zoomScale * zoomScale) == 2) {
					baseInt = setFlag(baseInt, 1);
				}
				if (getFlag(lakeFlags, 2) && nextInt(12) == 3) {
					baseInt = setFlag(baseInt, 2);
				}
				if (getFlag(lakeFlags, 4) && nextInt(10) == 1) {
					baseInt = setFlag(baseInt, 4);
				}
				ints[i1 + k1 * xSize] = baseInt;
			}
		}
		return ints;
	}

	public LOTRGenLayerBiomeVariantsLake setLakeFlags(int... flags) {
		for (int f : flags) {
			lakeFlags = setFlag(lakeFlags, f);
		}
		return this;
	}

	public static boolean getFlag(int param, int flag) {
		return (param & flag) == flag;
	}

	public static int setFlag(int param, int flag) {
		return param | flag;
	}
}
