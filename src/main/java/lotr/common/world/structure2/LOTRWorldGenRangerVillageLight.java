package lotr.common.world.structure2;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRangerVillageLight extends LOTRWorldGenRangerStructure {
	public LOTRWorldGenRangerVillageLight(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int k1;
		int i1;
		setOriginAndRotation(world, i, j, k, rotation, 0);
		setupRandomBlocks(random);
		if (restrictions && !isSurface(world, i1 = 0, getTopBlock(world, i1, k1 = 0) - 1, k1)) {
			return false;
		}
		for (j1 = 0; (j1 >= 0 || !isOpaque(world, 0, j1, 0)) && getY(j1) >= 0; --j1) {
			setBlockAndMetadata(world, 0, j1, 0, logBlock, logMeta);
			setGrassToDirt(world, 0, j1 - 1, 0);
		}
		for (j1 = 1; j1 <= 2; ++j1) {
			setBlockAndMetadata(world, 0, j1, 0, logBlock, logMeta);
		}
		setBlockAndMetadata(world, 0, 3, 0, plankBlock, plankMeta);
		setBlockAndMetadata(world, 0, 4, 0, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 0, 5, 0, Blocks.torch, 5);
		return true;
	}
}
