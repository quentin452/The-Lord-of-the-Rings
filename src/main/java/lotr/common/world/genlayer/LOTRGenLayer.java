package lotr.common.world.genlayer;

import net.minecraft.world.World;
import net.minecraft.world.gen.layer.GenLayer;

public abstract class LOTRGenLayer extends GenLayer {
	public LOTRGenLayer lotrParent;

	protected LOTRGenLayer(long l) {
		super(l);
	}

	@Override
	public int[] getInts(int i, int k, int xSize, int zSize) {
		throw new RuntimeException("Do not use this method!");
	}

	public abstract int[] getInts(World var1, int var2, int var3, int var4, int var5);

	@Override
	public void initWorldGenSeed(long l) {
		super.initWorldGenSeed(l);
		if (lotrParent != null) {
			lotrParent.initWorldGenSeed(l);
		}
	}
}
