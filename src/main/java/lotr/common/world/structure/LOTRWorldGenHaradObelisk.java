package lotr.common.world.structure;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenHaradObelisk extends LOTRWorldGenStructureBase {
	public LOTRWorldGenHaradObelisk(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		int j1;
		int k1;
		int i1;
		if (restrictions && world.getBlock(i, j - 1, k) != Blocks.sand && world.getBlock(i, j - 1, k) != Blocks.dirt && world.getBlock(i, j - 1, k) != Blocks.grass) {
			return false;
		}
		--j;
		int rotation = random.nextInt(4);
		if (!restrictions && usingPlayer != null) {
			rotation = usingPlayerRotation();
		}
		switch (rotation) {
			case 0: {
				k += 8;
				break;
			}
			case 1: {
				i -= 8;
				break;
			}
			case 2: {
				k -= 8;
				break;
			}
			case 3: {
				i += 8;
			}
		}
		if (restrictions) {
			for (i1 = i - 7; i1 <= i + 7; ++i1) {
				for (k1 = k - 7; k1 <= k + 7; ++k1) {
					j1 = world.getTopSolidOrLiquidBlock(i1, k1);
					Block block = world.getBlock(i1, j1 - 1, k1);
					if (block == Blocks.sand || block == Blocks.dirt || block == Blocks.stone || block == Blocks.grass) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = i - 7; i1 <= i + 7; ++i1) {
			for (k1 = k - 7; k1 <= k + 7; ++k1) {
				for (j1 = j; (j1 == j || !LOTRMod.isOpaque(world, i1, j1, k1)) && j1 >= 0; --j1) {
					setBlockAndNotifyAdequately(world, i1, j1, k1, Blocks.sandstone, 0);
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
			}
		}
		for (i1 = i - 7; i1 <= i + 7; ++i1) {
			for (k1 = k - 7; k1 <= k + 7; ++k1) {
				int i2 = Math.abs(i1 - i);
				int k2 = Math.abs(k1 - k);
				if (i2 == 7 || k2 == 7) {
					setBlockAndNotifyAdequately(world, i1, j + 1, k1, Blocks.sandstone, 0);
				}
				if (i2 == 5 && k2 <= 5 || k2 == 5 && i2 <= 5) {
					setBlockAndNotifyAdequately(world, i1, j + 1, k1, Blocks.sandstone, 0);
					setBlockAndNotifyAdequately(world, i1, j + 2, k1, Blocks.sandstone, 2);
				}
				if (i2 == 3 && k2 <= 3 || k2 == 3 && i2 <= 3) {
					setBlockAndNotifyAdequately(world, i1, j + 1, k1, Blocks.sandstone, 0);
					setBlockAndNotifyAdequately(world, i1, j + 2, k1, Blocks.sandstone, 2);
					placeHaradBrick(world, random, i1, j + 3, k1);
				}
				if (i2 <= 1 && k2 <= 1) {
					setBlockAndNotifyAdequately(world, i1, j + 1, k1, Blocks.sandstone, 0);
					setBlockAndNotifyAdequately(world, i1, j + 2, k1, Blocks.sandstone, 2);
					placeHaradBrick(world, random, i1, j + 3, k1);
					setBlockAndNotifyAdequately(world, i1, j + 4, k1, Blocks.sandstone, 2);
					placeHaradBrick(world, random, i1, j + 5, k1);
				}
				for (int l = 0; l <= 2; ++l) {
					int l1 = 8 - (l * 2 + 1);
					if (i2 != l1 || k2 != l1) {
						continue;
					}
					placeHaradBrick(world, random, i1, j + l + 2, k1);
					placeHaradWall(world, random, i1, j + l + 3, k1);
					placeHaradWall(world, random, i1, j + l + 4, k1);
				}
			}
		}
		placeHaradBrick(world, random, i - 1, j + 6, k);
		placeHaradBrick(world, random, i + 1, j + 6, k);
		placeHaradBrick(world, random, i, j + 6, k - 1);
		placeHaradBrick(world, random, i, j + 6, k + 1);
		for (int j12 = j + 6; j12 <= j + 9; ++j12) {
			setBlockAndNotifyAdequately(world, i, j12, k, Blocks.sandstone, 2);
		}
		setBlockAndNotifyAdequately(world, i - 1, j + 10, k, Blocks.sandstone, 2);
		setBlockAndNotifyAdequately(world, i + 1, j + 10, k, Blocks.sandstone, 2);
		setBlockAndNotifyAdequately(world, i, j + 10, k - 1, Blocks.sandstone, 2);
		setBlockAndNotifyAdequately(world, i, j + 10, k + 1, Blocks.sandstone, 2);
		setBlockAndNotifyAdequately(world, i, j + 10, k, LOTRMod.hearth, 0);
		setBlockAndNotifyAdequately(world, i, j + 11, k, Blocks.fire, 0);
		return true;
	}

	public void placeHaradBrick(World world, Random random, int i, int j, int k) {
		if (random.nextInt(3) == 0) {
			setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.brick3, 11);
		} else {
			setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.brick, 15);
		}
	}

	public void placeHaradWall(World world, Random random, int i, int j, int k) {
		if (random.nextInt(3) == 0) {
			setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.wall3, 3);
		} else {
			setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.wall, 15);
		}
	}
}
