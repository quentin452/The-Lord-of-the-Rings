package lotr.common.world.structure2;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenGulfTownWall extends LOTRWorldGenGulfStructure {
	public boolean isTall;

	public LOTRWorldGenGulfTownWall(boolean flag) {
		super(flag);
	}

	@Override
	public boolean canUseRedBrick() {
		return false;
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
		for (j1 = 1; (j1 >= 0 || !isOpaque(world, 0, j1, 0)) && getY(j1) >= 0; --j1) {
			if (random.nextBoolean()) {
				setBlockAndMetadata(world, 0, j1, 0, Blocks.sandstone, 0);
			} else {
				setBlockAndMetadata(world, 0, j1, 0, brickBlock, brickMeta);
			}
			setGrassToDirt(world, 0, j1 - 1, 0);
		}
		for (j1 = 2; j1 <= 4; ++j1) {
			if (random.nextBoolean()) {
				setBlockAndMetadata(world, 0, j1, 0, Blocks.sandstone, 0);
				continue;
			}
			setBlockAndMetadata(world, 0, j1, 0, brickBlock, brickMeta);
		}
		if (isTall) {
			for (j1 = 5; j1 <= 6; ++j1) {
				setBlockAndMetadata(world, 0, j1, 0, boneWallBlock, boneWallMeta);
			}
			setBlockAndMetadata(world, 0, 7, 0, boneBlock, boneMeta);
			placeWallBanner(world, 0, 7, 0, LOTRItemBanner.BannerType.HARAD_GULF, 2);
		} else {
			setBlockAndMetadata(world, 0, 5, 0, fenceBlock, fenceMeta);
		}
		return true;
	}

	public void setTall() {
		isTall = true;
	}
}
