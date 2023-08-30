package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.world.World;

public class LOTRWorldGenGondorObelisk extends LOTRWorldGenGondorStructure {
	public LOTRWorldGenGondorObelisk(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int k1;
		int k12;
		int i1;
		setOriginAndRotation(world, i, j, k, rotation, 4);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -3; i1 <= 3; ++i1) {
				for (k12 = -3; k12 <= 3; ++k12) {
					j1 = getTopBlock(world, i1, k12) - 1;
					if (isSurface(world, i1, j1, k12)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			for (k12 = -3; k12 <= 3; ++k12) {
				for (j1 = 3; (j1 >= 0 || !isOpaque(world, i1, j1, k12)) && getY(j1) >= 0; --j1) {
					placeRandomBrick(world, random, i1, j1, k12);
					setGrassToDirt(world, i1, j1 - 1, k12);
				}
			}
		}
		for (i1 = -2; i1 <= 2; ++i1) {
			for (k12 = -2; k12 <= 2; ++k12) {
				for (j1 = 4; j1 <= 8; ++j1) {
					setBlockAndMetadata(world, i1, j1, k12, rockBlock, rockMeta);
				}
			}
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			placeRandomStairs(world, random, i1, 4, -3, 2);
			placeRandomStairs(world, random, i1, 4, 3, 3);
		}
		for (k1 = -2; k1 <= 2; ++k1) {
			placeRandomStairs(world, random, -3, 4, k1, 1);
			placeRandomStairs(world, random, 3, 4, k1, 0);
		}
		for (i1 = -1; i1 <= 1; ++i1) {
			for (k12 = -1; k12 <= 1; ++k12) {
				for (j1 = 9; j1 <= 14; ++j1) {
					placeRandomBrick(world, random, i1, j1, k12);
				}
			}
		}
		for (i1 = -2; i1 <= 2; ++i1) {
			placeRandomStairs(world, random, i1, 9, -2, 2);
			placeRandomStairs(world, random, i1, 9, 2, 3);
		}
		for (k1 = -1; k1 <= 1; ++k1) {
			placeRandomStairs(world, random, -2, 9, k1, 1);
			placeRandomStairs(world, random, 2, 9, k1, 0);
		}
		for (int j12 = 15; j12 <= 18; ++j12) {
			placeRandomBrick(world, random, 0, j12, 0);
			placeRandomBrick(world, random, -1, j12, 0);
			placeRandomBrick(world, random, 1, j12, 0);
			placeRandomBrick(world, random, 0, j12, -1);
			placeRandomBrick(world, random, 0, j12, 1);
		}
		placeRandomStairs(world, random, -1, 19, 0, 1);
		placeRandomStairs(world, random, 1, 19, 0, 0);
		placeRandomStairs(world, random, 0, 19, -1, 2);
		placeRandomStairs(world, random, 0, 19, 1, 3);
		placeRandomBrick(world, random, 0, 19, 0);
		setBlockAndMetadata(world, 0, 20, 0, LOTRMod.beacon, 0);
		return true;
	}

	public void placeRandomBrick(World world, Random random, int i, int j, int k) {
		if (random.nextInt(4) == 0) {
			if (random.nextBoolean()) {
				setBlockAndMetadata(world, i, j, k, brickMossyBlock, brickMossyMeta);
			} else {
				setBlockAndMetadata(world, i, j, k, brickCrackedBlock, brickCrackedMeta);
			}
		} else {
			setBlockAndMetadata(world, i, j, k, brickBlock, brickMeta);
		}
	}

	public void placeRandomStairs(World world, Random random, int i, int j, int k, int meta) {
		if (random.nextInt(4) == 0) {
			if (random.nextBoolean()) {
				setBlockAndMetadata(world, i, j, k, brickMossyStairBlock, meta);
			} else {
				setBlockAndMetadata(world, i, j, k, brickCrackedStairBlock, meta);
			}
		} else {
			setBlockAndMetadata(world, i, j, k, brickStairBlock, meta);
		}
	}
}
