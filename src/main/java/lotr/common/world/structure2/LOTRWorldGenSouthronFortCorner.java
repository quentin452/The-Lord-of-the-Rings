package lotr.common.world.structure2;

import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenSouthronFortCorner extends LOTRWorldGenSouthronStructure {
	public LOTRWorldGenSouthronFortCorner(boolean flag) {
		super(flag);
	}

	@Override
	public boolean canUseRedBricks() {
		return false;
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		boolean beam;
		int j12;
		setOriginAndRotation(world, i, j, k, rotation, 0);
		setupRandomBlocks(random);
		for (int i1 = -4; i1 <= 1; ++i1) {
			int i2 = Math.abs(i1);
			int k1 = 0;
			findSurface(world, i1, k1);
			if (i1 <= 0) {
				beam = i2 % 4 == 0;
				if (beam) {
					for (j12 = 6; (j12 >= 1 || !isOpaque(world, i1, j12, k1)) && getY(j12) >= 0; --j12) {
						setBlockAndMetadata(world, i1, j12, k1, woodBeamBlock, woodBeamMeta);
						setGrassToDirt(world, i1, j12 - 1, k1);
					}
				} else {
					for (j12 = 5; (j12 >= 1 || !isOpaque(world, i1, j12, k1)) && getY(j12) >= 0; --j12) {
						setBlockAndMetadata(world, i1, j12, k1, plankBlock, plankMeta);
						setGrassToDirt(world, i1, j12 - 1, k1);
					}
					if (i2 % 2 == 1) {
						setBlockAndMetadata(world, i1, 5, k1, plankStairBlock, 2);
					} else {
						setBlockAndMetadata(world, i1, 6, k1, fenceBlock, fenceMeta);
					}
				}
				if (i1 <= -1) {
					int k3 = k1 + 1;
					setBlockAndMetadata(world, i1, 2, k3, brickStairBlock, 3);
					for (j1 = 1; (j1 >= 1 || !isOpaque(world, i1, j1, k3)) && getY(j1) >= 0; --j1) {
						setBlockAndMetadata(world, i1, j1, k3, brickBlock, brickMeta);
						setGrassToDirt(world, i1, j1 - 1, k3);
					}
				}
			}
			int k3 = k1 - 1;
			setBlockAndMetadata(world, i1, 2, k3, brickStairBlock, 2);
			for (j12 = 1; (j12 >= 1 || !isOpaque(world, i1, j12, k3)) && getY(j12) >= 0; --j12) {
				setBlockAndMetadata(world, i1, j12, k3, brickBlock, brickMeta);
				setGrassToDirt(world, i1, j12 - 1, k3);
			}
		}
		for (int k1 = 0; k1 <= 4; ++k1) {
			int i1 = 0;
			int k2 = Math.abs(k1);
			findSurface(world, i1, k1);
			if (k1 >= 1) {
				beam = k2 % 4 == 0;
				if (beam) {
					for (j12 = 6; (j12 >= 1 || !isOpaque(world, i1, j12, k1)) && getY(j12) >= 0; --j12) {
						setBlockAndMetadata(world, i1, j12, k1, woodBeamBlock, woodBeamMeta);
						setGrassToDirt(world, i1, j12 - 1, k1);
					}
				} else {
					for (j12 = 5; (j12 >= 1 || !isOpaque(world, i1, j12, k1)) && getY(j12) >= 0; --j12) {
						setBlockAndMetadata(world, i1, j12, k1, plankBlock, plankMeta);
						setGrassToDirt(world, i1, j12 - 1, k1);
					}
					if (k2 % 2 == 1) {
						setBlockAndMetadata(world, i1, 5, k1, plankStairBlock, 0);
					} else {
						setBlockAndMetadata(world, i1, 6, k1, fenceBlock, fenceMeta);
					}
				}
				if (k1 >= 2) {
					int i3 = i1 - 1;
					setBlockAndMetadata(world, i3, 2, k1, brickStairBlock, 1);
					for (j1 = 1; (j1 >= 1 || !isOpaque(world, i3, j1, k1)) && getY(j1) >= 0; --j1) {
						setBlockAndMetadata(world, i3, j1, k1, brickBlock, brickMeta);
						setGrassToDirt(world, i3, j1 - 1, k1);
					}
				}
			}
			int i3 = i1 + 1;
			setBlockAndMetadata(world, i3, 2, k1, brickStairBlock, 0);
			for (j12 = 1; (j12 >= 1 || !isOpaque(world, i3, j12, k1)) && getY(j12) >= 0; --j12) {
				setBlockAndMetadata(world, i3, j12, k1, brickBlock, brickMeta);
				setGrassToDirt(world, i3, j12 - 1, k1);
			}
		}
		return true;
	}
}
