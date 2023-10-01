package lotr.common.world.structure2;

import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenRohanVillagePalisade extends LOTRWorldGenRohanStructure {
	public LOTRWorldGenRohanVillagePalisade(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int i1;
		setOriginAndRotation(world, i, j, k, rotation, 0);
		setupRandomBlocks(random);
		if (restrictions && !isSurface(world, i1 = 0, getTopBlock(world, i1, k1 = 0) - 1, k1)) {
			return false;
		}
		for (int j12 = 1; (j12 >= 0 || !isOpaque(world, 0, j12, 0)) && getY(j12) >= 0; --j12) {
			setBlockAndMetadata(world, 0, j12, 0, cobbleBlock, cobbleMeta);
			setGrassToDirt(world, 0, j12 - 1, 0);
		}
		int height = 5 + random.nextInt(2);
		for (int j13 = 2; j13 <= height; ++j13) {
			setBlockAndMetadata(world, 0, j13, 0, logBlock, logMeta);
		}
		return true;
	}

	@Override
	public boolean oneWoodType() {
		return true;
	}
}
