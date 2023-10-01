package lotr.common.world.structure2;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenTauredainWatchtower extends LOTRWorldGenTauredainHouse {
	public LOTRWorldGenTauredainWatchtower(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int i1;
		if (!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
			return false;
		}
		for (i1 = -1; i1 <= 1; ++i1) {
			for (k1 = -1; k1 <= 1; ++k1) {
				for (int j1 = 7; j1 <= 13; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		placeWoodBase(world, -1, -1, false);
		placeWoodBase(world, 1, -1, true);
		placeWoodBase(world, -1, 1, false);
		placeWoodBase(world, 1, 1, false);
		for (i1 = -2; i1 <= 2; ++i1) {
			for (k1 = -2; k1 <= 2; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				if (i2 == 1 && k2 == 1) {
					setBlockAndMetadata(world, i1, 8, k1, woodBlock, woodMeta);
					setBlockAndMetadata(world, i1, 9, k1, fenceBlock, fenceMeta);
					setBlockAndMetadata(world, i1, 10, k1, fenceBlock, fenceMeta);
					setBlockAndMetadata(world, i1, 11, k1, woodBlock, woodMeta);
					setBlockAndMetadata(world, i1, 12, k1, woodBlock, woodMeta);
				}
				if (i2 == 1 && k2 % 2 == 0) {
					if (i1 != 1 || k1 != -2) {
						setBlockAndMetadata(world, i1, 7, k1, woodBlock, woodMeta | 8);
					}
					setBlockAndMetadata(world, i1, 11, k1, woodBlock, woodMeta | 8);
				}
				if (k2 == 1 && i2 % 2 == 0) {
					setBlockAndMetadata(world, i1, 7, k1, woodBlock, woodMeta | 4);
					setBlockAndMetadata(world, i1, 11, k1, woodBlock, woodMeta | 4);
				}
				if (i2 == 1 && k2 == 2 || k2 == 1 && i2 == 2) {
					setBlockAndMetadata(world, i1, 12, k1, thatchSlabBlock, thatchSlabMeta);
				}
				if (i2 == 0 && k2 == 2 || k2 == 0 && i2 == 2) {
					setBlockAndMetadata(world, i1, 12, k1, thatchBlock, thatchMeta);
				}
				if (i2 == 0 && k2 == 1 || k2 == 0 && i2 == 1) {
					setBlockAndMetadata(world, i1, 12, k1, thatchBlock, thatchMeta);
					setBlockAndMetadata(world, i1, 13, k1, thatchSlabBlock, thatchSlabMeta);
				}
				if (i2 != 0 || k2 != 0) {
					continue;
				}
				setBlockAndMetadata(world, i1, 12, k1, thatchBlock, thatchMeta);
				setBlockAndMetadata(world, i1, 13, k1, thatchBlock, thatchMeta);
			}
		}
		setBlockAndMetadata(world, 0, 7, 0, plankSlabBlock, plankSlabMeta | 8);
		setBlockAndMetadata(world, 0, 7, -2, plankSlabBlock, plankSlabMeta | 8);
		setBlockAndMetadata(world, 0, 11, 0, Blocks.torch, 4);
		setBlockAndMetadata(world, -1, 8, 0, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 1, 8, 0, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 0, 8, 1, fenceBlock, fenceMeta);
		return true;
	}

	@Override
	public int getOffset() {
		return 2;
	}

	public void placeWoodBase(World world, int i, int k, boolean ladder) {
		for (int j = 7; (j == 7 || !isOpaque(world, i, j, k)) && getY(j) >= 0; --j) {
			if (ladder) {
				setBlockAndMetadata(world, i, j, k, woodBlock, woodMeta);
				if (!isOpaque(world, i, j - 1, k)) {
					setBlockAndMetadata(world, i, j, k - 1, Blocks.ladder, 2);
				}
			} else if (j >= 6 || isOpaque(world, i, j - 1, k)) {
				setBlockAndMetadata(world, i, j, k, woodBlock, woodMeta);
			} else {
				setBlockAndMetadata(world, i, j, k, fenceBlock, fenceMeta);
			}
			setGrassToDirt(world, i, j - 1, k);
		}
	}
}
