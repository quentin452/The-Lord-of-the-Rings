package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenEasterlingStatue extends LOTRWorldGenEasterlingStructure {
	public LOTRWorldGenEasterlingStatue(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int k1;
		int k12;
		int i1;
		setOriginAndRotation(world, i, j, k, rotation, 6);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -6; i1 <= 6; ++i1) {
				for (k12 = -5; k12 <= 5; ++k12) {
					int j12 = getTopBlock(world, i1, k12) - 1;
					if (isSurface(world, i1, j12, k12)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -6; i1 <= 6; ++i1) {
			for (k12 = -5; k12 <= 5; ++k12) {
				int j13;
				for (j13 = 1; (j13 >= 0 || !isOpaque(world, i1, j13, k12)) && getY(j13) >= 0; --j13) {
					setBlockAndMetadata(world, i1, j13, k12, LOTRMod.rock, 4);
					setGrassToDirt(world, i1, j13 - 1, k12);
				}
				for (j13 = 2; j13 <= 25; ++j13) {
					setAir(world, i1, j13, k12);
				}
				if (i1 == -6) {
					setBlockAndMetadata(world, -6, 1, k12, LOTRMod.stairsRedRock, 1);
					continue;
				}
				if (i1 == 6) {
					setBlockAndMetadata(world, 6, 1, k12, LOTRMod.stairsRedRock, 0);
					continue;
				}
				if (k12 == -5) {
					setBlockAndMetadata(world, i1, 1, -5, LOTRMod.stairsRedRock, 2);
					continue;
				}
				if (k12 != 5) {
					continue;
				}
				setBlockAndMetadata(world, i1, 1, k12, LOTRMod.stairsRedRock, 3);
			}
		}
		for (k1 = 0; k1 <= 1; ++k1) {
			setBlockAndMetadata(world, -2, 2, k1, brickStairBlock, 1);
			setBlockAndMetadata(world, -1, 2, k1, brickStairBlock, 0);
		}
		setBlockAndMetadata(world, -2, 2, 2, brickBlock, brickMeta);
		setBlockAndMetadata(world, -1, 2, 2, brickBlock, brickMeta);
		setBlockAndMetadata(world, -2, 3, 1, brickStairBlock, 5);
		setBlockAndMetadata(world, -1, 3, 1, brickStairBlock, 4);
		setBlockAndMetadata(world, -2, 3, 2, brickStairBlock, 1);
		setBlockAndMetadata(world, -1, 3, 2, brickStairBlock, 0);
		setBlockAndMetadata(world, -2, 4, 1, brickStairBlock, 1);
		setBlockAndMetadata(world, -1, 4, 1, brickStairBlock, 0);
		setBlockAndMetadata(world, -2, 4, 2, brickStairBlock, 5);
		setBlockAndMetadata(world, -1, 4, 2, brickStairBlock, 4);
		setBlockAndMetadata(world, -2, 5, 1, brickStairBlock, 5);
		setBlockAndMetadata(world, -1, 5, 1, brickStairBlock, 4);
		setBlockAndMetadata(world, -3, 5, 2, brickStairBlock, 5);
		setBlockAndMetadata(world, -2, 5, 2, brickBlock, brickMeta);
		setBlockAndMetadata(world, -1, 5, 2, brickBlock, brickMeta);
		setBlockAndMetadata(world, -2, 6, 1, brickStairBlock, 5);
		setBlockAndMetadata(world, -1, 6, 1, brickStairBlock, 4);
		setBlockAndMetadata(world, -2, 6, 2, brickBlock, brickMeta);
		setBlockAndMetadata(world, -1, 6, 2, brickBlock, brickMeta);
		for (k1 = -1; k1 <= 0; ++k1) {
			setBlockAndMetadata(world, 1, 2, k1, brickStairBlock, 1);
			setBlockAndMetadata(world, 2, 2, k1, brickStairBlock, 0);
		}
		setBlockAndMetadata(world, 1, 2, 1, brickBlock, brickMeta);
		setBlockAndMetadata(world, 2, 2, 1, brickBlock, brickMeta);
		setBlockAndMetadata(world, 1, 3, 0, brickStairBlock, 5);
		setBlockAndMetadata(world, 2, 3, 0, brickStairBlock, 4);
		setBlockAndMetadata(world, 1, 3, 1, brickStairBlock, 1);
		setBlockAndMetadata(world, 2, 3, 1, brickStairBlock, 0);
		setBlockAndMetadata(world, 1, 4, 0, brickStairBlock, 1);
		setBlockAndMetadata(world, 2, 4, 0, brickStairBlock, 0);
		setBlockAndMetadata(world, 1, 4, 1, brickStairBlock, 5);
		setBlockAndMetadata(world, 2, 4, 1, brickStairBlock, 4);
		setBlockAndMetadata(world, 1, 5, 0, brickStairBlock, 5);
		setBlockAndMetadata(world, 2, 5, 0, brickStairBlock, 4);
		setBlockAndMetadata(world, 1, 5, 1, brickBlock, brickMeta);
		setBlockAndMetadata(world, 2, 5, 1, brickBlock, brickMeta);
		setBlockAndMetadata(world, 3, 5, 1, brickStairBlock, 4);
		setBlockAndMetadata(world, 1, 6, 0, brickStairBlock, 5);
		setBlockAndMetadata(world, 2, 6, 0, brickStairBlock, 4);
		setBlockAndMetadata(world, 1, 6, 1, brickBlock, brickMeta);
		setBlockAndMetadata(world, 2, 6, 1, brickBlock, brickMeta);
		setBlockAndMetadata(world, 1, 6, 2, brickStairBlock, 5);
		setBlockAndMetadata(world, 2, 6, 2, brickStairBlock, 4);
		setBlockAndMetadata(world, -1, 7, 0, brickStairBlock, 5);
		setBlockAndMetadata(world, 0, 7, 0, brickStairBlock, 6);
		setBlockAndMetadata(world, 1, 7, 0, brickBlock, brickMeta);
		setBlockAndMetadata(world, 2, 7, 0, brickStairBlock, 4);
		setBlockAndMetadata(world, -2, 7, 1, brickStairBlock, 5);
		for (i1 = -1; i1 <= 2; ++i1) {
			setBlockAndMetadata(world, i1, 7, 1, brickBlock, brickMeta);
		}
		setBlockAndMetadata(world, 3, 7, 1, brickStairBlock, 4);
		setBlockAndMetadata(world, -3, 7, 2, brickStairBlock, 5);
		setBlockAndMetadata(world, -2, 7, 2, brickBlock, brickMeta);
		setBlockAndMetadata(world, -1, 7, 2, brickStairBlock, 4);
		setBlockAndMetadata(world, 0, 7, 2, brickStairBlock, 5);
		setBlockAndMetadata(world, 1, 7, 2, brickBlock, brickMeta);
		setBlockAndMetadata(world, 2, 7, 2, brickBlock, brickMeta);
		for (i1 = -1; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 8, 0, brickBlock, brickMeta);
		}
		setBlockAndMetadata(world, 2, 8, 0, brickStairBlock, 4);
		setBlockAndMetadata(world, -2, 8, 1, brickStairBlock, 5);
		for (i1 = -1; i1 <= 2; ++i1) {
			setBlockAndMetadata(world, i1, 8, 1, brickBlock, brickMeta);
		}
		for (i1 = -2; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 8, 2, brickBlock, brickMeta);
		}
		setBlockAndMetadata(world, 2, 8, 2, brickStairBlock, 4);
		for (i1 = -1; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 9, 0, brickWallBlock, brickWallMeta);
		}
		setBlockAndMetadata(world, 2, 9, 0, brickStairBlock, 1);
		setBlockAndMetadata(world, -2, 9, 1, brickStairBlock, 0);
		setBlockAndMetadata(world, -1, 9, 1, brickWallBlock, brickWallMeta);
		for (i1 = 0; i1 <= 2; ++i1) {
			setBlockAndMetadata(world, i1, 9, 1, brickBlock, brickMeta);
		}
		setBlockAndMetadata(world, 3, 9, 1, brickStairBlock, 4);
		setBlockAndMetadata(world, -3, 9, 2, brickStairBlock, 5);
		for (i1 = -2; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 9, 2, brickBlock, brickMeta);
		}
		setBlockAndMetadata(world, 2, 9, 2, brickStairBlock, 4);
		setBlockAndMetadata(world, -2, 10, 0, brickWallBlock, brickWallMeta);
		for (i1 = -1; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 10, 0, pillarBlock, pillarMeta);
		}
		setBlockAndMetadata(world, 2, 10, 0, brickWallBlock, brickWallMeta);
		setBlockAndMetadata(world, -2, 10, 1, pillarBlock, pillarMeta);
		setBlockAndMetadata(world, 2, 10, 1, pillarBlock, pillarMeta);
		setBlockAndMetadata(world, -2, 10, 2, brickWallBlock, brickWallMeta);
		for (i1 = -1; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 10, 2, brickStairBlock, 3);
		}
		setBlockAndMetadata(world, 2, 10, 2, brickWallBlock, brickWallMeta);
		setBlockAndMetadata(world, -2, 11, 0, brickWallBlock, brickWallMeta);
		for (i1 = -1; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 11, 0, pillarBlock, pillarMeta);
		}
		setBlockAndMetadata(world, 2, 11, 0, brickWallBlock, brickWallMeta);
		setBlockAndMetadata(world, -2, 11, 1, pillarBlock, pillarMeta);
		setBlockAndMetadata(world, 2, 11, 1, pillarBlock, pillarMeta);
		setBlockAndMetadata(world, -2, 11, 2, brickWallBlock, brickWallMeta);
		for (i1 = -1; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 11, 2, pillarBlock, pillarMeta);
		}
		setBlockAndMetadata(world, 2, 11, 2, brickWallBlock, brickWallMeta);
		for (i1 = -1; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 12, 0, brickWallBlock, brickWallMeta);
		}
		setBlockAndMetadata(world, -2, 12, 1, brickStairBlock, 5);
		for (i1 = -1; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 12, 1, brickBlock, brickMeta);
		}
		setBlockAndMetadata(world, 2, 12, 1, brickStairBlock, 4);
		for (i1 = -1; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 12, 2, brickBlock, brickMeta);
		}
		setBlockAndMetadata(world, -1, 13, 0, brickStairBlock, 5);
		setBlockAndMetadata(world, 0, 13, 0, brickBlock, brickMeta);
		setBlockAndMetadata(world, 1, 13, 0, brickStairBlock, 4);
		for (i1 = -2; i1 <= 2; ++i1) {
			setBlockAndMetadata(world, i1, 13, 1, brickBlock, brickMeta);
		}
		setBlockAndMetadata(world, -2, 13, 2, brickStairBlock, 5);
		for (i1 = -1; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 13, 2, brickBlock, brickMeta);
		}
		setBlockAndMetadata(world, 2, 13, 2, brickStairBlock, 4);
		setBlockAndMetadata(world, -2, 14, 0, brickStairBlock, 1);
		for (i1 = -1; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 14, 0, brickBlock, brickMeta);
		}
		setBlockAndMetadata(world, 2, 14, 0, brickStairBlock, 0);
		for (i1 = -2; i1 <= 2; ++i1) {
			setBlockAndMetadata(world, i1, 14, 1, brickBlock, brickMeta);
		}
		setBlockAndMetadata(world, -2, 14, 2, brickStairBlock, 1);
		for (i1 = -1; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 14, 2, brickBlock, brickMeta);
		}
		setBlockAndMetadata(world, 2, 14, 2, brickStairBlock, 0);
		setBlockAndMetadata(world, -1, 15, 0, brickStairBlock, 1);
		setBlockAndMetadata(world, 0, 15, 0, brickStairBlock, 2);
		setBlockAndMetadata(world, 1, 15, 0, brickStairBlock, 0);
		setBlockAndMetadata(world, -2, 15, 1, brickStairBlock, 1);
		for (i1 = -1; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 15, 1, brickBlock, brickMeta);
		}
		setBlockAndMetadata(world, 2, 15, 1, brickStairBlock, 0);
		setBlockAndMetadata(world, -1, 15, 2, brickStairBlock, 1);
		setBlockAndMetadata(world, 0, 15, 2, brickStairBlock, 3);
		setBlockAndMetadata(world, 1, 15, 2, brickStairBlock, 0);
		setBlockAndMetadata(world, -4, 2, -1, brickBlock, brickMeta);
		setBlockAndMetadata(world, -4, 2, 0, brickStairBlock, 7);
		setBlockAndMetadata(world, -4, 2, 2, brickStairBlock, 6);
		setBlockAndMetadata(world, -4, 2, 3, brickBlock, brickMeta);
		for (k1 = -1; k1 <= 3; ++k1) {
			setBlockAndMetadata(world, -4, 3, k1, brickBlock, brickMeta);
		}
		setBlockAndMetadata(world, -4, 4, -1, brickStairBlock, 2);
		setBlockAndMetadata(world, -4, 4, 0, brickBlock, brickMeta);
		setBlockAndMetadata(world, -4, 4, 1, pillarBlock, pillarMeta);
		setBlockAndMetadata(world, -4, 4, 2, brickBlock, brickMeta);
		setBlockAndMetadata(world, -4, 4, 3, brickStairBlock, 3);
		setBlockAndMetadata(world, -4, 5, 0, brickBlock, brickMeta);
		setBlockAndMetadata(world, -4, 5, 1, pillarBlock, pillarMeta);
		setBlockAndMetadata(world, -4, 5, 2, brickBlock, brickMeta);
		setBlockAndMetadata(world, -4, 6, -1, brickStairBlock, 6);
		setBlockAndMetadata(world, -4, 6, 0, brickBlock, brickMeta);
		setBlockAndMetadata(world, -4, 6, 1, pillarBlock, pillarMeta);
		setBlockAndMetadata(world, -4, 6, 2, brickBlock, brickMeta);
		setBlockAndMetadata(world, -4, 6, 3, brickStairBlock, 7);
		for (k1 = -1; k1 <= 3; ++k1) {
			setBlockAndMetadata(world, -4, 7, k1, brickBlock, brickMeta);
		}
		setBlockAndMetadata(world, -5, 8, -1, brickStairBlock, 5);
		setBlockAndMetadata(world, -4, 8, -1, brickBlock, brickMeta);
		setBlockAndMetadata(world, -4, 8, 0, brickStairBlock, 3);
		setBlockAndMetadata(world, -4, 8, 2, brickStairBlock, 2);
		setBlockAndMetadata(world, -4, 8, 3, brickBlock, brickMeta);
		setBlockAndMetadata(world, -5, 9, -1, brickStairBlock, 1);
		setBlockAndMetadata(world, -4, 9, -1, brickStairBlock, 4);
		setBlockAndMetadata(world, -4, 9, 0, brickStairBlock, 7);
		setBlockAndMetadata(world, -4, 10, -1, brickStairBlock, 2);
		setBlockAndMetadata(world, -4, 10, 0, brickBlock, brickMeta);
		setBlockAndMetadata(world, -4, 10, 1, brickStairBlock, 7);
		setBlockAndMetadata(world, -4, 11, 0, brickBlock, brickMeta);
		setBlockAndMetadata(world, -3, 11, 0, brickStairBlock, 4);
		setBlockAndMetadata(world, -4, 11, 1, brickStairBlock, 7);
		setBlockAndMetadata(world, -3, 11, 1, brickBlock, brickMeta);
		setBlockAndMetadata(world, -3, 11, 2, brickStairBlock, 7);
		setBlockAndMetadata(world, -4, 12, 0, brickStairBlock, 6);
		setBlockAndMetadata(world, -3, 12, 0, brickStairBlock, 2);
		setBlockAndMetadata(world, -4, 12, 1, brickStairBlock, 7);
		setBlockAndMetadata(world, -3, 12, 1, brickBlock, brickMeta);
		setBlockAndMetadata(world, -3, 12, 2, brickStairBlock, 7);
		setBlockAndMetadata(world, -4, 13, 0, brickStairBlock, 6);
		setBlockAndMetadata(world, -3, 13, 0, brickStairBlock, 2);
		setBlockAndMetadata(world, -4, 13, 1, brickStairBlock, 7);
		setBlockAndMetadata(world, -3, 13, 1, brickBlock, brickMeta);
		setBlockAndMetadata(world, -3, 13, 2, brickStairBlock, 7);
		setBlockAndMetadata(world, -4, 14, 0, brickStairBlock, 1);
		setBlockAndMetadata(world, -3, 14, 0, brickBlock, brickMeta);
		setBlockAndMetadata(world, -4, 14, 1, brickBlock, brickMeta);
		setBlockAndMetadata(world, -3, 14, 1, brickBlock, brickMeta);
		setBlockAndMetadata(world, -4, 14, 2, brickStairBlock, 7);
		setBlockAndMetadata(world, -3, 14, 2, brickStairBlock, 3);
		setBlockAndMetadata(world, -3, 15, 0, brickStairBlock, 1);
		setBlockAndMetadata(world, -4, 15, 1, brickStairBlock, 1);
		setBlockAndMetadata(world, -3, 15, 1, brickBlock, brickMeta);
		setBlockAndMetadata(world, -3, 15, 2, brickStairBlock, 1);
		setBlockAndMetadata(world, -3, 16, 1, brickStairBlock, 1);
		setBlockAndMetadata(world, 3, 16, 1, brickStairBlock, 0);
		setBlockAndMetadata(world, 3, 15, 0, brickStairBlock, 0);
		setBlockAndMetadata(world, 4, 15, 1, brickStairBlock, 0);
		setBlockAndMetadata(world, 3, 15, 1, brickBlock, brickMeta);
		setBlockAndMetadata(world, 3, 15, 2, brickStairBlock, 0);
		setBlockAndMetadata(world, 3, 14, -1, brickStairBlock, 0);
		setBlockAndMetadata(world, 3, 14, 0, brickBlock, brickMeta);
		setBlockAndMetadata(world, 4, 14, 0, brickStairBlock, 0);
		setBlockAndMetadata(world, 3, 14, 1, brickBlock, brickMeta);
		setBlockAndMetadata(world, 4, 14, 1, brickStairBlock, 4);
		setBlockAndMetadata(world, 3, 14, 2, brickStairBlock, 7);
		setBlockAndMetadata(world, 1, 13, -3, brickBlock, brickMeta);
		setBlockAndMetadata(world, 2, 13, -3, brickStairBlock, 0);
		setBlockAndMetadata(world, 2, 13, -2, brickStairBlock, 1);
		setBlockAndMetadata(world, 3, 13, -2, brickStairBlock, 0);
		setBlockAndMetadata(world, 2, 13, -1, brickBlock, brickMeta);
		setBlockAndMetadata(world, 3, 13, -1, brickBlock, brickMeta);
		setBlockAndMetadata(world, 4, 13, -1, brickStairBlock, 0);
		setBlockAndMetadata(world, 3, 13, 0, brickBlock, brickMeta);
		setBlockAndMetadata(world, 4, 13, 0, brickStairBlock, 4);
		setBlockAndMetadata(world, 3, 13, 1, brickStairBlock, 7);
		setBlockAndMetadata(world, 1, 12, -3, brickBlock, brickMeta);
		setBlockAndMetadata(world, 2, 12, -3, brickStairBlock, 4);
		setBlockAndMetadata(world, 2, 12, -2, brickStairBlock, 5);
		setBlockAndMetadata(world, 3, 12, -2, brickStairBlock, 4);
		setBlockAndMetadata(world, 3, 12, -1, brickBlock, brickMeta);
		setBlockAndMetadata(world, 4, 12, -1, brickStairBlock, 4);
		setBlockAndMetadata(world, 4, 12, 0, brickStairBlock, 7);
		setBlockAndMetadata(world, 1, 2, -3, brickBlock, brickMeta);
		for (j1 = 3; j1 <= 11; ++j1) {
			setBlockAndMetadata(world, 1, j1, -3, brickWallBlock, brickWallMeta);
		}
		for (j1 = 14; j1 <= 18; ++j1) {
			setBlockAndMetadata(world, 1, j1, -3, brickWallBlock, brickWallMeta);
		}
		for (j1 = 19; j1 <= 27; ++j1) {
			setBlockAndMetadata(world, 1, j1, -3, brickBlock, brickMeta);
		}
		setBlockAndMetadata(world, 1, 28, -3, brickStairBlock, 3);
		setBlockAndMetadata(world, 1, 21, -4, brickStairBlock, 2);
		setBlockAndMetadata(world, 1, 21, -2, brickStairBlock, 3);
		setBlockAndMetadata(world, 1, 23, -4, brickStairBlock, 6);
		setBlockAndMetadata(world, 1, 24, -5, brickStairBlock, 6);
		setBlockAndMetadata(world, 1, 24, -4, brickStairBlock, 3);
		setBlockAndMetadata(world, 1, 25, -5, brickStairBlock, 2);
		setBlockAndMetadata(world, 1, 25, -4, brickStairBlock, 7);
		setBlockAndMetadata(world, 1, 26, -4, brickStairBlock, 2);
		setBlockAndMetadata(world, -1, 16, 0, brickStairBlock, 5);
		setBlockAndMetadata(world, 0, 16, 0, brickBlock, brickMeta);
		setBlockAndMetadata(world, 1, 16, 0, brickStairBlock, 4);
		setBlockAndMetadata(world, -1, 16, 1, brickStairBlock, 5);
		setBlockAndMetadata(world, 0, 16, 1, brickBlock, brickMeta);
		setBlockAndMetadata(world, 1, 16, 1, brickStairBlock, 4);
		setBlockAndMetadata(world, 0, 16, 2, brickStairBlock, 7);
		setBlockAndMetadata(world, -1, 17, 0, brickStairBlock, 0);
		setBlockAndMetadata(world, 0, 17, 0, brickWallBlock, brickWallMeta);
		setBlockAndMetadata(world, 1, 17, 0, brickStairBlock, 1);
		setBlockAndMetadata(world, -1, 17, 1, brickStairBlock, 0);
		setBlockAndMetadata(world, 1, 17, 1, brickStairBlock, 1);
		setBlockAndMetadata(world, -1, 17, 2, brickStairBlock, 5);
		setBlockAndMetadata(world, 0, 17, 2, brickBlock, brickMeta);
		setBlockAndMetadata(world, 1, 17, 2, brickStairBlock, 4);
		for (i1 = -1; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 18, -1, brickStairBlock, 6);
		}
		for (k1 = 0; k1 <= 2; ++k1) {
			setBlockAndMetadata(world, -2, 18, k1, brickStairBlock, 5);
			for (int i12 = -1; i12 <= 1; ++i12) {
				setBlockAndMetadata(world, i12, 18, k1, brickBlock, brickMeta);
			}
			setBlockAndMetadata(world, 2, 18, k1, brickStairBlock, 4);
		}
		for (i1 = -1; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 18, 3, brickStairBlock, 7);
		}
		for (i1 = -1; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 19, 0, brickStairBlock, 2);
		}
		setBlockAndMetadata(world, -1, 19, 1, brickStairBlock, 1);
		setBlockAndMetadata(world, 0, 19, 1, brickBlock, brickMeta);
		setBlockAndMetadata(world, 1, 19, 1, brickStairBlock, 0);
		for (i1 = -1; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 19, 2, brickStairBlock, 3);
		}
		setBlockAndMetadata(world, -2, 20, 2, brickStairBlock, 5);
		setBlockAndMetadata(world, -1, 20, 2, brickStairBlock, 0);
		setBlockAndMetadata(world, 1, 20, 2, brickStairBlock, 1);
		setBlockAndMetadata(world, 2, 20, 2, brickStairBlock, 4);
		setBlockAndMetadata(world, -2, 21, 2, brickStairBlock, 0);
		setBlockAndMetadata(world, 2, 21, 2, brickStairBlock, 1);
		return true;
	}
}
