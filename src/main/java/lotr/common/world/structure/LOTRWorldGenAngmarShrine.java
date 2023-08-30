package lotr.common.world.structure;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenAngmarShrine extends LOTRWorldGenStructureBase {
	public LOTRWorldGenAngmarShrine(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		int i1;
		int k1;
		if (restrictions && world.getBlock(i, j - 1, k) != Blocks.grass) {
			return false;
		}
		--j;
		int rotation = random.nextInt(4);
		if (!restrictions && usingPlayer != null) {
			rotation = usingPlayerRotation();
		}
		switch (rotation) {
			case 0: {
				k += 4;
				break;
			}
			case 1: {
				i -= 4;
				break;
			}
			case 2: {
				k -= 4;
				break;
			}
			case 3: {
				i += 4;
			}
		}
		if (restrictions) {
			int minHeight = j;
			int maxHeight = j;
			for (i1 = i - 3; i1 <= i + 3; ++i1) {
				for (k1 = k - 3; k1 <= k + 3; ++k1) {
					int j1 = world.getTopSolidOrLiquidBlock(i1, k1) - 1;
					Block l = world.getBlock(i1, j1, k1);
					if (l != Blocks.grass && l != Blocks.dirt && l != Blocks.stone) {
						return false;
					}
					if (j1 < minHeight) {
						minHeight = j1;
					}
					if (j1 <= maxHeight) {
						continue;
					}
					maxHeight = j1;
				}
			}
			if (maxHeight - minHeight > 3) {
				return false;
			}
		}
		for (int i12 = i - 3; i12 <= i + 3; ++i12) {
			for (int k12 = k - 3; k12 <= k + 3; ++k12) {
				for (int j1 = j; !LOTRMod.isOpaque(world, i12, j1, k12) && j1 >= 0; --j1) {
					placeRandomBrick(world, random, i12, j1, k12);
					setGrassToDirt(world, i12, j1 - 1, k12);
				}
			}
		}
		for (int l = 0; l <= 2; ++l) {
			for (int i13 = i - 3 + l; i13 <= i + 3 - l; ++i13) {
				for (int k13 = k - 3 + l; k13 <= k + 3 - l; ++k13) {
					placeRandomBrick(world, random, i13, j + 1 + l, k13);
				}
			}
			placeRandomStairs(world, random, i - 3 + l, j + 1 + l, k, 0);
			placeRandomStairs(world, random, i + 3 - l, j + 1 + l, k, 1);
			placeRandomStairs(world, random, i, j + 1 + l, k - 3 + l, 2);
			placeRandomStairs(world, random, i, j + 1 + l, k + 3 - l, 3);
		}
		setBlockAndNotifyAdequately(world, i, j + 4, k, LOTRMod.angmarTable, 0);
		setBlockAndNotifyAdequately(world, i - 2, j + 3, k - 2, LOTRMod.morgulTorch, 5);
		setBlockAndNotifyAdequately(world, i - 2, j + 3, k + 2, LOTRMod.morgulTorch, 5);
		setBlockAndNotifyAdequately(world, i + 2, j + 3, k - 2, LOTRMod.morgulTorch, 5);
		setBlockAndNotifyAdequately(world, i + 2, j + 3, k + 2, LOTRMod.morgulTorch, 5);
		int pillars = 4 + random.nextInt(5);
		for (int p = 0; p < pillars; ++p) {
			int j1;
			Block l;
			i1 = 4 + random.nextInt(4);
			k1 = 4 + random.nextInt(4);
			if (random.nextBoolean()) {
				i1 *= -1;
			}
			if (random.nextBoolean()) {
				k1 *= -1;
			}
			int height = 2 + random.nextInt(3);
			l = world.getBlock(i1 += i, j1 = world.getTopSolidOrLiquidBlock(i1, k1 += k) - 1, k1);
			if (l != Blocks.grass && l != Blocks.dirt && l != Blocks.stone) {
				continue;
			}
			setGrassToDirt(world, i1, j1, k1);
			for (int j2 = j1; j2 < j1 + height; ++j2) {
				placeRandomBrick(world, random, i1, j2, k1);
			}
			setBlockAndNotifyAdequately(world, i1, j1 + height, k1, LOTRMod.guldurilBrick, 2);
		}
		return true;
	}

	public void placeRandomBrick(World world, Random random, int i, int j, int k) {
		if (random.nextInt(4) == 0) {
			setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.brick2, 1);
		} else {
			setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.brick2, 0);
		}
	}

	public void placeRandomStairs(World world, Random random, int i, int j, int k, int meta) {
		if (random.nextInt(4) == 0) {
			setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.stairsAngmarBrickCracked, meta);
		} else {
			setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.stairsAngmarBrick, meta);
		}
	}
}
