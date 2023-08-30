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

public class LOTRWorldGenOlive extends WorldGenAbstractTree {
	public int minHeight = 4;
	public int maxHeight = 5;
	public Block woodBlock = LOTRMod.wood6;
	public int woodMeta = 3;
	public Block leafBlock = LOTRMod.leaves6;
	public int leafMeta = 3;
	public int extraTrunk;

	public LOTRWorldGenOlive(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		int height = MathHelper.getRandomIntegerInRange(random, minHeight, maxHeight);
		int leafStart = j + height - 3 + random.nextInt(2);
		int leafTop = j + height;
		boolean flag = true;
		if (j >= 1 && j + height + 1 <= 256) {
			int i1;
			int k1;
			int i12;
			Block below;
			for (int j1 = j; j1 <= j + height + 1; ++j1) {
				int range = 1;
				if (j1 == j) {
					range = 0;
				}
				if (j1 >= leafStart) {
					range = 2;
				}
				for (i1 = i - range; i1 <= i + extraTrunk + range && flag; ++i1) {
					for (int k12 = k - range; k12 <= k + extraTrunk + range && flag; ++k12) {
						if (j1 >= 0 && j1 < 256 && isReplaceable(world, i1, j1, k12)) {
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
			for (i12 = i; i12 <= i + extraTrunk && canGrow; ++i12) {
				for (k1 = k; k1 <= k + extraTrunk && canGrow; ++k1) {
					below = world.getBlock(i12, j - 1, k1);
					if (below.canSustainPlant(world, i12, j - 1, k1, ForgeDirection.UP, (IPlantable) Blocks.sapling)) {
						continue;
					}
					canGrow = false;
				}
			}
			if (canGrow) {
				int j1;
				for (i12 = i; i12 <= i + extraTrunk; ++i12) {
					for (k1 = k; k1 <= k + extraTrunk; ++k1) {
						below = world.getBlock(i12, j - 1, k1);
						below.onPlantGrow(world, i12, j - 1, k1, i12, j, k1);
					}
				}
				for (j1 = leafStart; j1 <= leafTop; ++j1) {
					int leafRange;
					leafRange = j1 == leafTop ? 2 : j1 == leafStart ? 1 : 3;
					for (int i13 = i - leafRange; i13 <= i + extraTrunk + leafRange; ++i13) {
						for (int k13 = k - leafRange; k13 <= k + extraTrunk + leafRange; ++k13) {
							Block block;
							int i2 = Math.abs(i13 - i);
							int k2 = Math.abs(k13 - k);
							if (extraTrunk > 0) {
								if (i13 > i) {
									i2 -= extraTrunk;
								}
								if (k13 > k) {
									k2 -= extraTrunk;
								}
							}
							if (i2 + k2 > 4 || (i2 >= leafRange || k2 >= leafRange) && random.nextInt(3) == 0 || !(block = world.getBlock(i13, j1, k13)).isReplaceable(world, i13, j1, k13) && !block.isLeaves(world, i13, j1, k13)) {
								continue;
							}
							setBlockAndNotifyAdequately(world, i13, j1, k13, leafBlock, leafMeta);
						}
					}
				}
				for (j1 = j; j1 < j + height; ++j1) {
					for (i1 = i; i1 <= i + extraTrunk; ++i1) {
						for (int k14 = k; k14 <= k + extraTrunk; ++k14) {
							setBlockAndNotifyAdequately(world, i1, j1, k14, woodBlock, woodMeta);
						}
					}
				}
				if (extraTrunk > 0) {
					for (i12 = i - 1; i12 <= i + extraTrunk + 1; ++i12) {
						for (k1 = k - 1; k1 <= k + extraTrunk + 1; ++k1) {
							int j12;
							Block block;
							int i2 = Math.abs(i12 - i);
							int k2 = Math.abs(k1 - k);
							if (extraTrunk > 0) {
								if (i12 > i) {
									i2 -= extraTrunk;
								}
								if (k1 > k) {
									k2 -= extraTrunk;
								}
							}
							if (random.nextInt(4) == 0) {
								int rootY = j + random.nextInt(2);
								int roots = 0;
								while (world.getBlock(i12, rootY, k1).isReplaceable(world, i12, rootY, k1)) {
									setBlockAndNotifyAdequately(world, i12, rootY, k1, woodBlock, woodMeta | 0xC);
									world.getBlock(i12, rootY - 1, k1).onPlantGrow(world, i12, rootY - 1, k1, i12, rootY, k1);
									--rootY;
									roots++;
									random.nextInt(3);
								}
							}
							if (random.nextInt(4) != 0 || i2 != 0 && k2 != 0 || !(block = world.getBlock(i12, j12 = leafStart, k1)).isReplaceable(world, i12, j12, k1) && !block.isLeaves(world, i12, j12, k1)) {
								continue;
							}
							setBlockAndNotifyAdequately(world, i12, j12, k1, woodBlock, woodMeta);
						}
					}
				}
				return true;
			}
		}
		return false;
	}

	public LOTRWorldGenOlive setBlocks(Block b1, int m1, Block b2, int m2) {
		woodBlock = b1;
		woodMeta = m1;
		leafBlock = b2;
		leafMeta = m2;
		return this;
	}

	public LOTRWorldGenOlive setExtraTrunkWidth(int w) {
		extraTrunk = w;
		return this;
	}

	public LOTRWorldGenOlive setMinMaxHeight(int min, int max) {
		minHeight = min;
		maxHeight = max;
		return this;
	}
}
