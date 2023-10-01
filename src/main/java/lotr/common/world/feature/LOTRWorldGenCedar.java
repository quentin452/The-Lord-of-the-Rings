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

public class LOTRWorldGenCedar extends WorldGenAbstractTree {
	public Block woodBlock = LOTRMod.wood4;
	public int woodMeta = 2;
	public Block leafBlock = LOTRMod.leaves4;
	public int leafMeta = 2;
	public int minHeight = 10;
	public int maxHeight = 16;
	public boolean hangingLeaves;

	public LOTRWorldGenCedar(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		Block below;
		int canopyMin;
		int j1;
		int height = MathHelper.getRandomIntegerInRange(random, minHeight, maxHeight);
		boolean flag = true;
		if (j >= 1 && height + 1 <= 256) {
			for (int j12 = j; j12 <= j + height + 1; ++j12) {
				int range = 1;
				if (j12 == j) {
					range = 0;
				}
				if (j12 >= j + height - 1) {
					range = 2;
				}
				for (int i1 = i - range; i1 <= i + range && flag; ++i1) {
					for (int k1 = k - range; k1 <= k + range && flag; ++k1) {
						if (j12 >= 0 && j12 < 256 && isReplaceable(world, i1, j12, k1)) {
							continue;
						}
						flag = false;
					}
				}
			}
		} else {
			flag = false;
		}
		if (!(below = world.getBlock(i, j - 1, k)).canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable) Blocks.sapling)) {
			flag = false;
		}
		if (!flag) {
			return false;
		}
		below.onPlantGrow(world, i, j - 1, k, i, j, k);
		for (j1 = canopyMin = j + height - 2; j1 <= j + height; ++j1) {
			int leafRange = 2 - (j1 - (j + height));
			spawnLeaves(world, random, i, j1, k, leafRange);
			if (j1 != canopyMin) {
				continue;
			}
			for (int i1 = i - 1; i1 <= i + 1; ++i1) {
				for (int k1 = k - 1; k1 <= k + 1; ++k1) {
					Block block;
					if (i1 != i && k1 != k || !(block = world.getBlock(i1, j1, k1)).isReplaceable(world, i1, j1, k1) && !block.isLeaves(world, i1, j1, k1)) {
						continue;
					}
					setBlockAndNotifyAdequately(world, i1, j1, k1, woodBlock, woodMeta);
				}
			}
		}
		for (j1 = j + height - 1; j1 > j + height / 2; j1 -= 1 + random.nextInt(3)) {
			int branches = 1 + random.nextInt(3);
			block7:
			for (int l = 0; l < branches; ++l) {
				float angle = random.nextFloat() * 3.1415927f * 2.0f;
				int i1 = i;
				int k1 = k;
				int j2 = j1;
				int length = MathHelper.getRandomIntegerInRange(random, 4, 7);
				int leafMin = 1 + random.nextInt(2);
				for (int l1 = 0; l1 < length; ++l1) {
					i1 = i + (int) (0.5f + MathHelper.cos(angle) * (l1 + 1));
					Block block = world.getBlock(i1, j2 = j1 - 3 + l1 / 2, k1 = k + (int) (0.5f + MathHelper.sin(angle) * (l1 + 1)));
					if (!block.isReplaceable(world, i1, j2, k1) && !block.isWood(world, i1, j2, k1) && !block.isLeaves(world, i1, j2, k1)) {
						continue block7;
					}
					setBlockAndNotifyAdequately(world, i1, j2, k1, woodBlock, woodMeta);
					if (l1 != length - 1 || leafMin < 2) {
						continue;
					}
					for (int i2 = i1 - 1; i2 <= i1 + 1; ++i2) {
						for (int k2 = k1 - 1; k2 <= k1 + 1; ++k2) {
							Block block1;
							int j3;
							if (i2 != i1 && k2 != k1 || !(block1 = world.getBlock(i2, j3 = j2 - 1, k2)).isReplaceable(world, i2, j3, k2) && !block1.isLeaves(world, i2, j3, k2)) {
								continue;
							}
							setBlockAndNotifyAdequately(world, i2, j3, k2, woodBlock, woodMeta);
						}
					}
				}
				for (int j3 = j2 - leafMin; j3 <= j2; ++j3) {
					int leafRange = 1 - (j3 - j2);
					spawnLeaves(world, random, i1, j3, k1, leafRange);
				}
			}
		}
		for (j1 = 0; j1 < height; ++j1) {
			setBlockAndNotifyAdequately(world, i, j + j1, k, woodBlock, woodMeta);
		}
		for (int i1 = i - 1; i1 <= i + 1; ++i1) {
			for (int k1 = k - 1; k1 <= k + 1; ++k1) {
				int i2 = i1 - i;
				int k2 = k1 - k;
				if (Math.abs(i2) == Math.abs(k2)) {
					continue;
				}
				int rootY = j + random.nextInt(2);
				int roots = 0;
				while (world.getBlock(i1, rootY, k1).isReplaceable(world, i1, rootY, k1)) {
					setBlockAndNotifyAdequately(world, i1, rootY, k1, woodBlock, woodMeta | 0xC);
					world.getBlock(i1, rootY - 1, k1).onPlantGrow(world, i1, rootY - 1, k1, i1, rootY, k1);
					--rootY;
					roots++;
					random.nextInt(3);
				}
			}
		}
		return true;
	}

	public LOTRWorldGenCedar setBlocks(Block b1, int m1, Block b2, int m2) {
		woodBlock = b1;
		woodMeta = m1;
		leafBlock = b2;
		leafMeta = m2;
		return this;
	}

	public LOTRWorldGenCedar setHangingLeaves() {
		hangingLeaves = true;
		return this;
	}

	public LOTRWorldGenCedar setMinMaxHeight(int min, int max) {
		minHeight = min;
		maxHeight = max;
		return this;
	}

	public void spawnLeaves(World world, Random random, int i, int j, int k, int leafRange) {
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
				if (!hangingLeaves || random.nextInt(10) != 0) {
					continue;
				}
				setBlockAndNotifyAdequately(world, i1, j, k1, woodBlock, woodMeta);
				Block block1 = world.getBlock(i1, j + 1, k1);
				if (block1.isReplaceable(world, i1, j + 1, k1) || block1.isLeaves(world, i1, j + 1, k1)) {
					setBlockAndNotifyAdequately(world, i1, j + 1, k1, leafBlock, leafMeta);
				}
				int hang = 2 + random.nextInt(3);
				for (int j1 = j - 1; j1 >= j - hang; --j1) {
					Block block2 = world.getBlock(i1, j1, k1);
					if (!block2.isReplaceable(world, i1, j1, k1) && !block2.isLeaves(world, i1, j1, k1)) {
						continue;
					}
					setBlockAndNotifyAdequately(world, i1, j1, k1, leafBlock, leafMeta);
				}
			}
		}
	}
}
