package lotr.common.world.feature;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.*;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenRedwood extends WorldGenAbstractTree {
	public int trunkWidth;
	public int extraTrunkWidth;
	public Block woodBlock = LOTRMod.wood8;
	public int woodMeta = 1;
	public Block leafBlock = LOTRMod.leaves8;
	public int leafMeta = 1;

	public LOTRWorldGenRedwood(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		int fullWidth = 1 + extraTrunkWidth + trunkWidth * 2;
		int height = fullWidth * MathHelper.getRandomIntegerInRange(random, 15, 20);
		if (fullWidth > 1) {
			height += (fullWidth - 1) * MathHelper.getRandomIntegerInRange(random, 0, 8);
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
				for (int i12 = i - trunkWidth - range; i12 <= i + trunkWidth + extraTrunkWidth + range && flag; ++i12) {
					for (int k12 = k - trunkWidth - range; k12 <= k + trunkWidth + extraTrunkWidth + range && flag; ++k12) {
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
			boolean canGrow = true;
			for (i1 = i - trunkWidth; i1 <= i + trunkWidth + extraTrunkWidth && canGrow; ++i1) {
				for (k1 = k - trunkWidth; k1 <= k + trunkWidth + extraTrunkWidth && canGrow; ++k1) {
					Block block = world.getBlock(i1, j - 1, k1);
					if (block.canSustainPlant(world, i1, j - 1, k1, ForgeDirection.UP, (IPlantable) Blocks.sapling)) {
						continue;
					}
					canGrow = false;
				}
			}
			if (canGrow) {
				int k13;
				int trunkWidthHere;
				int i13;
				int k2;
				int i2;
				int j1;
				for (i1 = i - trunkWidth; i1 <= i + trunkWidth + extraTrunkWidth; ++i1) {
					for (k1 = k - trunkWidth; k1 <= k + trunkWidth + extraTrunkWidth; ++k1) {
						world.getBlock(i1, j - 1, k1).onPlantGrow(world, i1, j - 1, k1, i1, j, k1);
					}
				}
				int narrowHeight = -1;
				if (fullWidth > 3) {
					narrowHeight = j + (int) (height * MathHelper.randomFloatClamp(random, 0.3f, 0.4f));
				}
				int leafStart = j + (int) (height * MathHelper.randomFloatClamp(random, 0.45f, 0.6f));
				int leafTop = j + height + 1;
				int leafRange = 0;
				int maxRange = 2;
				boolean increasing = true;
				for (j1 = leafTop; j1 >= leafStart; --j1) {
					if (j1 >= leafTop - 1) {
						leafRange = 0;
					} else if (increasing) {
						leafRange++;
						if (leafRange >= 3) {
							increasing = false;
						}
					} else if (--leafRange <= 1) {
						increasing = true;
					}
					leafRange = Math.min(leafRange, 4);
					trunkWidthHere = trunkWidth;
					if (narrowHeight > -1 && j1 >= narrowHeight) {
						--trunkWidthHere;
					}
					for (i13 = i - trunkWidthHere - maxRange; i13 <= i + trunkWidthHere + extraTrunkWidth + maxRange; ++i13) {
						for (k13 = k - trunkWidthHere - maxRange; k13 <= k + trunkWidthHere + extraTrunkWidth + maxRange; ++k13) {
							Block block;
							i2 = Math.abs(i13 - i);
							k2 = Math.abs(k13 - k);
							i2 -= trunkWidthHere;
							k2 -= trunkWidthHere;
							if (i13 > i) {
								i2 -= extraTrunkWidth;
							}
							if (k13 > k) {
								k2 -= extraTrunkWidth;
							}
							int d = i2 + k2;
							if (j1 < leafTop - 2) {
								d += random.nextInt(2);
							}
							if (d > leafRange || !(block = world.getBlock(i13, j1, k13)).isReplaceable(world, i13, j1, k13) && !block.isLeaves(world, i13, j1, k13)) {
								continue;
							}
							setBlockAndNotifyAdequately(world, i13, j1, k13, leafBlock, leafMeta);
						}
					}
				}
				for (j1 = 0; j1 < height; ++j1) {
					trunkWidthHere = trunkWidth;
					if (narrowHeight > -1 && j + j1 >= narrowHeight) {
						--trunkWidthHere;
					}
					for (i13 = -trunkWidthHere; i13 <= trunkWidthHere + extraTrunkWidth; ++i13) {
						for (k13 = -trunkWidthHere; k13 <= trunkWidthHere + extraTrunkWidth; ++k13) {
							i2 = Math.abs(i13);
							k2 = Math.abs(k13);
							if (i13 > 0) {
								i2 -= extraTrunkWidth;
							}
							if (k13 > 0) {
								k2 -= extraTrunkWidth;
							}
							int i3 = i + i13;
							int j3 = j + j1;
							int k3 = k + k13;
							if (narrowHeight > -1 && j3 < narrowHeight && j3 > j + 15 && j3 < leafStart && i2 == trunkWidthHere && k2 == trunkWidthHere) {
								continue;
							}
							world.getBlock(i3, j3, k3);
							if (!isReplaceable(world, i3, j3, k3)) {
								continue;
							}
							setBlockAndNotifyAdequately(world, i3, j3, k3, woodBlock, woodMeta);
						}
					}
				}
				for (int i14 = i - trunkWidth - 1; i14 <= i + trunkWidth + extraTrunkWidth + 1; ++i14) {
					for (int k14 = k - trunkWidth - 1; k14 <= k + trunkWidth + extraTrunkWidth + 1; ++k14) {
						int i22 = Math.abs(i14 - i);
						int k22 = Math.abs(k14 - k);
						i22 -= trunkWidth;
						k22 -= trunkWidth;
						if (i14 > i) {
							i22 -= extraTrunkWidth;
						}
						if (k14 > k) {
							k22 -= extraTrunkWidth;
						}
						if (i22 != 1 && k22 != 1 || i22 == k22) {
							continue;
						}
						int rootY = j + fullWidth / 2 + random.nextInt(2 + fullWidth / 2);
						while (world.getBlock(i14, rootY, k14).isReplaceable(world, i14, rootY, k14)) {
							setBlockAndNotifyAdequately(world, i14, rootY, k14, woodBlock, woodMeta | 0xC);
							world.getBlock(i14, rootY - 1, k14).onPlantGrow(world, i14, rootY - 1, k14, i14, rootY, k14);
							rootY--;
							random.nextInt(3);
						}
					}
				}
				return true;
			}
		}
		return false;
	}

	public LOTRWorldGenRedwood setExtraTrunkWidth(int i) {
		extraTrunkWidth = i;
		return this;
	}

	public LOTRWorldGenRedwood setTrunkWidth(int i) {
		trunkWidth = i;
		return this;
	}
}
