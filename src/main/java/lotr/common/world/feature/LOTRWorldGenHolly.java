package lotr.common.world.feature;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class LOTRWorldGenHolly extends WorldGenAbstractTree {
	public int extraTrunkWidth;
	public Block woodBlock = LOTRMod.wood2;
	public int woodMeta = 2;
	public Block leafBlock = LOTRMod.leaves2;
	public int leafMeta = 2;

	public LOTRWorldGenHolly(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		int height = 9 + random.nextInt(6);
		if (extraTrunkWidth > 0) {
			height += 10 + random.nextInt(4);
		}
		boolean flag = true;
		if (j >= 1 && j + height + 1 <= 256) {
			int i1;
			int k1;
			for (int j1 = j; j1 <= j + 1 + height; ++j1) {
				int range = 1;
				if (j1 == j) {
					range = 0;
				}
				if (j1 > j + 2 && j1 < j + height - 2) {
					range = 2;
					if (extraTrunkWidth > 0) {
						++range;
					}
				}
				for (int i12 = i - range; i12 <= i + range + extraTrunkWidth && flag; ++i12) {
					for (int k12 = k - range; k12 <= k + range + extraTrunkWidth && flag; ++k12) {
						if (j1 >= 0 && j1 < 256 && isReplaceable(world, i12, j1, k12)) {
							continue;
						}
						flag = false;
					}
				}
			}
			if (!flag) {
				return false;
			}
			boolean flag1 = true;
			for (i1 = i; i1 <= i + extraTrunkWidth && flag1; ++i1) {
				for (k1 = k; k1 <= k + extraTrunkWidth && flag1; ++k1) {
					Block block = world.getBlock(i1, j - 1, k1);
					if (block.canSustainPlant(world, i1, j - 1, k1, ForgeDirection.UP, (IPlantable) LOTRMod.sapling2)) {
						continue;
					}
					flag1 = false;
				}
			}
			if (flag1) {
				int j1;
				int k13;
				int i13;
				for (i1 = i; i1 <= i + extraTrunkWidth; ++i1) {
					for (k1 = k; k1 <= k + extraTrunkWidth; ++k1) {
						world.getBlock(i1, j - 1, k1).onPlantGrow(world, i1, j - 1, k1, i1, j, k1);
					}
				}
				int leafStop = 2 + random.nextInt(2);
				for (j1 = height; j1 > leafStop; --j1) {
					int i2;
					int k2;
					if (j1 == height) {
						for (i13 = 0; i13 <= extraTrunkWidth; ++i13) {
							for (k13 = 0; k13 <= extraTrunkWidth; ++k13) {
								growLeaves(world, i + i13, j + j1, k + k13);
							}
						}
						continue;
					}
					if (j1 > height - 3 || j1 == leafStop + 1) {
						for (i13 = -1; i13 <= 1 + extraTrunkWidth; ++i13) {
							for (k13 = -1; k13 <= 1 + extraTrunkWidth; ++k13) {
								i2 = i13;
								if (i2 > 0) {
									i2 -= extraTrunkWidth;
								}
								k2 = k13;
								if (k2 > 0) {
									k2 -= extraTrunkWidth;
								}
								if (j1 == height - 1 && Math.abs(i2) == 1 && Math.abs(k2) == 1) {
									continue;
								}
								growLeaves(world, i + i13, j + j1, k + k13);
							}
						}
						continue;
					}
					for (i13 = -3; i13 <= 3 + extraTrunkWidth; ++i13) {
						for (k13 = -3; k13 <= 3 + extraTrunkWidth; ++k13) {
							i2 = i13;
							if (i2 > 0) {
								i2 -= extraTrunkWidth;
							}
							k2 = k13;
							if (k2 > 0) {
								k2 -= extraTrunkWidth;
							}
							if (j1 % 2 != 0 && Math.abs(i2) == 2 && Math.abs(k2) == 2 || (Math.abs(i2) >= 3 || Math.abs(k2) >= 3) && (extraTrunkWidth <= 0 || j1 % 2 != 0 || i2 != 0 && k2 != 0)) {
								continue;
							}
							growLeaves(world, i + i13, j + j1, k + k13);
						}
					}
				}
				for (j1 = 0; j1 < height; ++j1) {
					for (i13 = 0; i13 <= extraTrunkWidth; ++i13) {
						for (k13 = 0; k13 <= extraTrunkWidth; ++k13) {
							world.getBlock(i + i13, j + j1, k + k13);
							if (!isReplaceable(world, i + i13, j + j1, k + k13)) {
								continue;
							}
							setBlockAndNotifyAdequately(world, i + i13, j + j1, k + k13, woodBlock, woodMeta);
						}
					}
				}
				return true;
			}
		}
		return false;
	}

	public void growLeaves(World world, int i, int j, int k) {
		Block block = world.getBlock(i, j, k);
		if (block.isReplaceable(world, i, j, k) || block.isLeaves(world, i, j, k)) {
			setBlockAndNotifyAdequately(world, i, j, k, leafBlock, leafMeta);
		}
	}

	public LOTRWorldGenHolly setLarge() {
		extraTrunkWidth = 1;
		return this;
	}
}
