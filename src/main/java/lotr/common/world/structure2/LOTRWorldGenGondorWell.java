package lotr.common.world.structure2;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenGondorWell extends LOTRWorldGenGondorStructure {
	public LOTRWorldGenGondorWell(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int j12;
		setOriginAndRotation(world, i, j, k, rotation, 4);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -3; i1 <= 3; ++i1) {
				for (int k1 = -3; k1 <= 3; ++k1) {
					j12 = getTopBlock(world, i1, k1) - 1;
					if (!isSurface(world, i1, j12, k1)) {
						return false;
					}
					if (j12 < minHeight) {
						minHeight = j12;
					}
					if (j12 > maxHeight) {
						maxHeight = j12;
					}
					if (maxHeight - minHeight <= 8) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i1 = -3; i1 <= 3; ++i1) {
			for (int k1 = -3; k1 <= 3; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				for (j12 = 0; (j12 >= 0 || !isOpaque(world, i1, j12, k1)) && getY(j12) >= 0; --j12) {
					setBlockAndMetadata(world, i1, j12, k1, rockBlock, rockMeta);
					setGrassToDirt(world, i1, j12 - 1, k1);
				}
				for (j12 = 1; j12 <= 6; ++j12) {
					setAir(world, i1, j12, k1);
				}
				if (i2 == 2 && k2 == 2) {
					setBlockAndMetadata(world, i1, 1, k1, rockBlock, rockMeta);
					setBlockAndMetadata(world, i1, 2, k1, rockBlock, rockMeta);
					setBlockAndMetadata(world, i1, 3, k1, rockWallBlock, rockWallMeta);
					setBlockAndMetadata(world, i1, 4, k1, rockBlock, rockMeta);
					setBlockAndMetadata(world, i1, 5, k1, rockSlabBlock, rockSlabMeta);
				}
				if (i2 <= 2 && k2 <= 2) {
					int d = i2 + k2;
					if (d == 3) {
						setBlockAndMetadata(world, i1, 4, k1, rockSlabBlock, rockSlabMeta | 8);
						setBlockAndMetadata(world, i1, 5, k1, rockSlabDoubleBlock, rockSlabDoubleMeta);
					}
					if (d == 2) {
						setBlockAndMetadata(world, i1, 5, k1, rockSlabDoubleBlock, rockSlabDoubleMeta);
						setBlockAndMetadata(world, i1, 6, k1, rockSlabBlock, rockSlabMeta);
					}
					if (d == 1) {
						setBlockAndMetadata(world, i1, 5, k1, rockSlabBlock, rockSlabMeta | 8);
						setBlockAndMetadata(world, i1, 6, k1, rockSlabDoubleBlock, rockSlabDoubleMeta);
					}
					if (d == 0) {
						setBlockAndMetadata(world, i1, 6, k1, rockSlabDoubleBlock, rockSlabDoubleMeta);
						setBlockAndMetadata(world, i1, 7, k1, rockSlabBlock, rockSlabMeta);
					}
				}
				if ((i2 != 2 || k2 > 1) && (k2 != 2 || i2 > 1)) {
					continue;
				}
				setBlockAndMetadata(world, i1, 1, k1, fenceBlock, fenceMeta);
				setBlockAndMetadata(world, i1, 0, k1, rockSlabDoubleBlock, rockSlabDoubleMeta);
			}
		}
		int waterDepth = 1 + random.nextInt(4);
		int depth = waterDepth + 1 + random.nextInt(3);
		for (j1 = 0; j1 < depth; ++j1) {
			int j2 = -j1;
			boolean watery = j1 >= depth - waterDepth;
			for (int i1 = -1; i1 <= 1; ++i1) {
				for (int k1 = -1; k1 <= 1; ++k1) {
					if (watery) {
						setBlockAndMetadata(world, i1, j2, k1, Blocks.water, 0);
						continue;
					}
					setAir(world, i1, j2, k1);
				}
			}
			if (watery) {
				continue;
			}
			setBlockAndMetadata(world, 0, j2, -1, Blocks.ladder, 3);
			setBlockAndMetadata(world, 0, j2, 1, Blocks.ladder, 2);
			setBlockAndMetadata(world, -1, j2, 0, Blocks.ladder, 4);
			setBlockAndMetadata(world, 1, j2, 0, Blocks.ladder, 5);
		}
		setBlockAndMetadata(world, 0, 1, -2, fenceGateBlock, 0);
		setBlockAndMetadata(world, 0, 1, 2, fenceGateBlock, 2);
		setBlockAndMetadata(world, -2, 1, 0, fenceGateBlock, 1);
		setBlockAndMetadata(world, 2, 1, 0, fenceGateBlock, 3);
		for (j1 = 4; j1 <= 5; ++j1) {
			setBlockAndMetadata(world, 0, j1, 0, fenceBlock, fenceMeta);
		}
		setBlockAndMetadata(world, -3, 5, 0, Blocks.torch, 1);
		setBlockAndMetadata(world, 3, 5, 0, Blocks.torch, 2);
		setBlockAndMetadata(world, 0, 5, -3, Blocks.torch, 4);
		setBlockAndMetadata(world, 0, 5, 3, Blocks.torch, 3);
		return true;
	}
}
