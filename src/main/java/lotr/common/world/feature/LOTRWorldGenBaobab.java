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

public class LOTRWorldGenBaobab extends WorldGenAbstractTree {
	public Block woodBlock = LOTRMod.wood4;
	public int woodMeta = 1;
	public Block leafBlock = LOTRMod.leaves4;
	public int leafMeta = 1;

	public LOTRWorldGenBaobab(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		int j1;
		int j12;
		int k2;
		int i2;
		int k1;
		int i1;
		int trunkCircleWidth = 4;
		int height = 16 + random.nextInt(10);
		int xSlope = 5 + random.nextInt(10);
		if (random.nextBoolean()) {
			xSlope *= -1;
		}
		int zSlope = 5 + random.nextInt(10);
		if (random.nextBoolean()) {
			zSlope *= -1;
		}
		boolean flag = true;
		if (j >= 1 && j + height + 5 <= 256) {
			for (i1 = i - trunkCircleWidth - 1; i1 <= i + trunkCircleWidth + 1 && flag; ++i1) {
				for (k1 = k - trunkCircleWidth - 1; k1 <= k + trunkCircleWidth + 1 && flag; ++k1) {
					i2 = Math.abs(i1 - i);
					k2 = Math.abs(k1 - k);
					if (i2 * i2 + k2 * k2 > trunkCircleWidth * trunkCircleWidth) {
						continue;
					}
					for (j1 = j; j1 <= j + 1 + height; ++j1) {
						if (j1 >= 0 && j1 < 256) {
							Block block = world.getBlock(i1, j1, k1);
							if (isReplaceable(world, i1, j1, k1) || block.isReplaceable(world, i1, j1, k1)) {
								continue;
							}
						}
						flag = false;
					}
					Block below = world.getBlock(i1, j - 1, k1);
					boolean isSoil = below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable) Blocks.sapling);
					if (isSoil) {
						continue;
					}
					flag = false;
				}
			}
		} else {
			flag = false;
		}
		if (!flag) {
			return false;
		}
		for (j12 = 0; j12 < height; ++j12) {
			for (int i12 = i - trunkCircleWidth - 1; i12 <= i + trunkCircleWidth + 1; ++i12) {
				for (int k12 = k - trunkCircleWidth - 1; k12 <= k + trunkCircleWidth + 1; ++k12) {
					int k22;
					int i22 = Math.abs(i12 - i);
					k22 = Math.abs(k12 - k);
					if (i22 * i22 + k22 * k22 > trunkCircleWidth * trunkCircleWidth) {
						continue;
					}
					if (j12 == 0) {
						world.getBlock(i12, j - 1, k12).onPlantGrow(world, i12, j - 1, k12, i12, j, k12);
					}
					setBlockAndNotifyAdequately(world, i12, j + j12, k12, woodBlock, woodMeta);
				}
			}
			if (j12 % xSlope == 0) {
				if (xSlope > 0) {
					++i;
				} else {
					--i;
				}
			}
			if (j12 % zSlope != 0) {
				continue;
			}
			if (zSlope > 0) {
				++k;
				continue;
			}
			--k;
		}
		for (j12 = j + height - 1; j12 > j + (int) (height * 0.75f); --j12) {
			int branches = 2 + random.nextInt(3);
			for (int l = 0; l < branches; ++l) {
				float angle = random.nextFloat() * 3.1415927f * 2.0f;
				int i13 = i;
				int k13 = k;
				int j2 = j12;
				int length = MathHelper.getRandomIntegerInRange(random, 4, 6);
				for (int l1 = trunkCircleWidth; l1 < trunkCircleWidth + length && isReplaceable(world, i13 = i + (int) (1.5f + MathHelper.cos(angle) * l1), j2 = j12 - 3 + l1 / 2, k13 = k + (int) (1.5f + MathHelper.sin(angle) * l1)); ++l1) {
					setBlockAndNotifyAdequately(world, i13, j2, k13, woodBlock, woodMeta);
				}
				int leafMin = 1 + random.nextInt(2);
				for (int j3 = j2 - leafMin; j3 <= j2; ++j3) {
					int leafRange = 1 - (j3 - j2);
					spawnLeaves(world, i13, j3, k13, leafRange);
				}
			}
		}
		for (i1 = i - trunkCircleWidth - 1; i1 <= i + trunkCircleWidth + 1; ++i1) {
			for (k1 = k - trunkCircleWidth - 1; k1 <= k + trunkCircleWidth + 1; ++k1) {
				i2 = Math.abs(i1 - i);
				k2 = Math.abs(k1 - k);
				if (i2 * i2 + k2 * k2 > trunkCircleWidth * trunkCircleWidth || random.nextInt(5) != 0) {
					continue;
				}
				j1 = j + height;
				int topHeight = 2 + random.nextInt(3);
				for (int l = 0; l < topHeight; ++l) {
					setBlockAndNotifyAdequately(world, i1, j1, k1, woodBlock, woodMeta);
					++j1;
				}
				int leafMin = 2;
				for (int j2 = j1 - leafMin; j2 <= j1; ++j2) {
					int leafRange = 1 - (j2 - j1);
					spawnLeaves(world, i1, j2, k1, leafRange);
				}
			}
		}
		return true;
	}

	public void spawnLeaves(World world, int i, int j, int k, int leafRange) {
		int leafRangeSq = leafRange * leafRange;
		for (int i1 = i - leafRange; i1 <= i + leafRange; ++i1) {
			for (int k1 = k - leafRange; k1 <= k + leafRange; ++k1) {
				Block block;
				int i2 = i1 - i;
				int k2 = k1 - k;
				if (i2 * i2 + k2 * k2 > leafRangeSq || !(block = world.getBlock(i1, j, k1)).isReplaceable(world, i1, j, k1) && !block.isLeaves(world, i1, j, k1)) {
					continue;
				}
				setBlockAndNotifyAdequately(world, i1, j, k1, leafBlock, leafMeta);
			}
		}
	}
}
