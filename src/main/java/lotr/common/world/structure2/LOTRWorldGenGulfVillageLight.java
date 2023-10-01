package lotr.common.world.structure2;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenGulfVillageLight extends LOTRWorldGenGulfStructure {
	public LOTRWorldGenGulfVillageLight(boolean flag) {
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
		for (int j12 = 0; (j12 >= 0 || !isOpaque(world, 0, j12, 0)) && getY(j12) >= 0; --j12) {
			setBlockAndMetadata(world, 0, j12, 0, woodBlock, woodMeta);
			setGrassToDirt(world, 0, j12 - 1, 0);
		}
		setBlockAndMetadata(world, 0, 1, 0, woodBlock, woodMeta);
		setBlockAndMetadata(world, 0, 2, 0, boneWallBlock, boneWallMeta);
		setBlockAndMetadata(world, 0, 3, 0, boneWallBlock, boneWallMeta);
		setBlockAndMetadata(world, 0, 4, 0, boneWallBlock, boneWallMeta);
		placeSkull(world, 0, 5, 0, 0);
		setBlockAndMetadata(world, 1, 4, 0, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 1, 5, 0, Blocks.torch, 5);
		setBlockAndMetadata(world, -1, 4, 0, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, -1, 5, 0, Blocks.torch, 5);
		setBlockAndMetadata(world, 0, 4, -1, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 0, 5, -1, Blocks.torch, 5);
		setBlockAndMetadata(world, 0, 4, 1, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 0, 5, 1, Blocks.torch, 5);
		return true;
	}
}
