package lotr.common.world.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenGnarledOak extends WorldGenAbstractTree {
	public Block woodBlock = Blocks.log;
	public int woodMeta;
	public Block leafBlock = Blocks.leaves;
	public int leafMeta;
	public int minHeight = 4;
	public int maxHeight = 9;

	public LOTRWorldGenGnarledOak(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		int height = MathHelper.getRandomIntegerInRange(random, minHeight, maxHeight);
		boolean flag = true;
		if (j >= 1 && height + 1 <= 256) {
			for (int j1 = j; j1 <= j + height + 1; ++j1) {
				int range = 1;
				if (j1 == j) {
					range = 0;
				}
				for (int i1 = i - range; i1 <= i + range && flag; ++i1) {
					for (int k1 = k - range; k1 <= k + range && flag; ++k1) {
						if (j1 >= 0 && j1 < 256 && isReplaceable(world, i1, j1, k1)) {
							continue;
						}
						flag = false;
					}
				}
			}
		} else {
			flag = false;
		}
		if (!flag) {
			return false;
		}
		boolean canGrow = true;
		Block below = world.getBlock(i, j - 1, k);
		if (!below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable) Blocks.sapling)) {
			canGrow = false;
		}
		if (!canGrow) {
			return false;
		}
		below = world.getBlock(i, j - 1, k);
		below.onPlantGrow(world, i, j - 1, k, i, j, k);
		for (int j1 = j; j1 < j + height; ++j1) {
			setBlockAndNotifyAdequately(world, i, j1, k, woodBlock, woodMeta);
		}
		generateLeaves(world, random, i, j + height, k);
		int branches = 2 + random.nextInt(3);
		for (int b = 0; b < branches; ++b) {
			float angle = random.nextFloat() * 3.1415927f * 2.0f;
			float cos = MathHelper.cos(angle);
			float sin = MathHelper.sin(angle);
			float angleY = random.nextFloat() * 0.6981317007977318f;
			float sinY = MathHelper.sin(angleY);
			int length = 2 + random.nextInt(3);
			int i1 = i;
			int k1 = k;
			int j1 = j + height - 1 - random.nextInt(3);
			if (j1 < j + 2) {
				j1 = j + 2;
			}
			for (int l = 0; l < length; ++l) {
				if (Math.floor(cos * l) != Math.floor(cos * (l - 1))) {
					i1 = (int) (i1 + Math.signum(cos));
				}
				if (Math.floor(sin * l) != Math.floor(sin * (l - 1))) {
					k1 = (int) (k1 + Math.signum(sin));
				}
				if (Math.floor(sinY * l) != Math.floor(sinY * (l - 1))) {
					j1 = (int) (j1 + Math.signum(sinY));
				}
				if (!isReplaceable(world, i1, j1, k1)) {
					break;
				}
				setBlockAndNotifyAdequately(world, i1, j1, k1, woodBlock, woodMeta | 0xC);
			}
			generateLeaves(world, random, i1, j1, k1);
		}
		int lastDir = -1;
		for (int j1 = j + 2; j1 < j + height; ++j1) {
			int dir;
			int k1;
			Block block;
			int i1;
			if (random.nextInt(3) != 0 || (dir = random.nextInt(4)) == lastDir) {
				continue;
			}
			lastDir = dir;
			int length = 1;
			for (int l = 1; l <= length && ((block = world.getBlock(i1 = i + Direction.offsetX[dir] * l, j1, k1 = k + Direction.offsetZ[dir] * l)).isReplaceable(world, i1, j1, k1) || block.isLeaves(world, i1, j1, k1)); ++l) {
				if (dir == 0 || dir == 2) {
					setBlockAndNotifyAdequately(world, i1, j1, k1, woodBlock, woodMeta | 8);
					continue;
				}
				setBlockAndNotifyAdequately(world, i1, j1, k1, woodBlock, woodMeta | 4);
			}
		}
		for (int i1 = i - 1; i1 <= i + 1; ++i1) {
			for (int k1 = k - 1; k1 <= k + 1; ++k1) {
				if (i1 == i && k1 == k || random.nextInt(4) > 0) {
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

	public void generateLeaves(World world, Random random, int i, int j, int k) {
		int leafRange = 3;
		int leafRangeSq = leafRange * leafRange;
		int leafRangeSqLess = (int) ((leafRange - 0.5) * (leafRange - 0.5));
		for (int i1 = i - leafRange; i1 <= i + leafRange; ++i1) {
			for (int j1 = j - leafRange + 1; j1 <= j + leafRange; ++j1) {
				for (int k1 = k - leafRange; k1 <= k + leafRange; ++k1) {
					Block block;
					int i2 = i1 - i;
					int j2 = j1 - j;
					int k2 = k1 - k;
					int dist = i2 * i2 + j2 * j2 + k2 * k2;
					if (dist >= leafRangeSqLess && (dist >= leafRangeSq || random.nextInt(3) != 0) || !(block = world.getBlock(i1, j1, k1)).isReplaceable(world, i1, j1, k1) && !block.isLeaves(world, i1, j1, k1)) {
						continue;
					}
					setBlockAndNotifyAdequately(world, i1, j1, k1, leafBlock, leafMeta);
				}
			}
		}
	}

	public LOTRWorldGenGnarledOak setBlocks(Block b1, int m1, Block b2, int m2) {
		woodBlock = b1;
		woodMeta = m1;
		leafBlock = b2;
		leafMeta = m2;
		return this;
	}

	public LOTRWorldGenGnarledOak setMinMaxHeight(int min, int max) {
		minHeight = min;
		maxHeight = max;
		return this;
	}
}
