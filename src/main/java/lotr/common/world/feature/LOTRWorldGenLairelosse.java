package lotr.common.world.feature;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class LOTRWorldGenLairelosse extends WorldGenAbstractTree {
	public int minHeight = 5;
	public int maxHeight = 8;
	public int extraTrunk;
	public Block woodBlock = LOTRMod.wood7;
	public int woodMeta = 2;
	public Block leafBlock = LOTRMod.leaves7;
	public int leafMeta = 2;

	public LOTRWorldGenLairelosse(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		int height = MathHelper.getRandomIntegerInRange(random, minHeight, maxHeight);
		int leafStart = j + 1 + extraTrunk + random.nextInt(3);
		int leafTop = j + height + 1;
		boolean flag = true;
		if (j >= 1 && j + height + 1 <= 256) {
			int k1;
			int i1;
			Block below;
			for (int j1 = j; j1 <= j + height + 1; ++j1) {
				int range = 1;
				if (j1 == j) {
					range = 0;
				}
				if (j1 >= leafStart) {
					range = 2;
				}
				for (int i12 = i - range; i12 <= i + extraTrunk + range && flag; ++i12) {
					for (int k12 = k - range; k12 <= k + extraTrunk + range && flag; ++k12) {
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
			for (i1 = i; i1 <= i + extraTrunk && canGrow; ++i1) {
				for (k1 = k; k1 <= k + extraTrunk && canGrow; ++k1) {
					below = world.getBlock(i1, j - 1, k1);
					if (below.canSustainPlant(world, i1, j - 1, k1, ForgeDirection.UP, (IPlantable) Blocks.sapling)) {
						continue;
					}
					canGrow = false;
				}
			}
			if (canGrow) {
				int k13;
				int j1;
				int i13;
				for (i1 = i; i1 <= i + extraTrunk; ++i1) {
					for (k1 = k; k1 <= k + extraTrunk; ++k1) {
						below = world.getBlock(i1, j - 1, k1);
						below.onPlantGrow(world, i1, j - 1, k1, i1, j, k1);
					}
				}
				int leafRange = 0;
				int maxRange = 2;
				for (j1 = leafTop; j1 >= leafStart; --j1) {
					if (j1 >= leafTop - 1) {
						leafRange = 0;
					} else if (++leafRange > 2) {
						leafRange = 1;
					}
					for (i13 = i - maxRange; i13 <= i + extraTrunk + maxRange; ++i13) {
						for (k13 = k - maxRange; k13 <= k + extraTrunk + maxRange; ++k13) {
							Block block;
							int i2 = Math.abs(i13 - i);
							int k2 = Math.abs(k13 - k);
							if (i13 > i) {
								i2 -= extraTrunk;
							}
							if (k13 > k) {
								k2 -= extraTrunk;
							}
							if (i2 + k2 > leafRange || !(block = world.getBlock(i13, j1, k13)).isReplaceable(world, i13, j1, k13) && !block.isLeaves(world, i13, j1, k13)) {
								continue;
							}
							setBlockAndNotifyAdequately(world, i13, j1, k13, leafBlock, leafMeta);
						}
					}
				}
				for (j1 = j; j1 < j + height; ++j1) {
					for (i13 = i; i13 <= i + extraTrunk; ++i13) {
						for (k13 = k; k13 <= k + extraTrunk; ++k13) {
							setBlockAndNotifyAdequately(world, i13, j1, k13, woodBlock, woodMeta);
						}
					}
				}
				return true;
			}
		}
		return false;
	}

	public LOTRWorldGenLairelosse setExtraTrunkWidth(int i) {
		extraTrunk = i;
		return this;
	}

	public LOTRWorldGenLairelosse setMinMaxHeight(int min, int max) {
		minHeight = min;
		maxHeight = max;
		return this;
	}
}
