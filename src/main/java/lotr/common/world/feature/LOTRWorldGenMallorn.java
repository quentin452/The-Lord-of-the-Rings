package lotr.common.world.feature;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenMallorn extends WorldGenAbstractTree {
	public int minHeight = 10;
	public int maxHeight = 14;
	public Block woodBlock = LOTRMod.wood;
	public int woodMeta = 1;
	public Block leafBlock = LOTRMod.leaves;
	public int leafMeta = 1;

	public LOTRWorldGenMallorn(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		int height = MathHelper.getRandomIntegerInRange(random, minHeight, maxHeight);
		int leafMin = j + (int) (height * 0.6f);
		boolean flag = true;
		if (j >= 1 && j + height + 1 <= 256) {
			for (int j1 = j; j1 <= j + height + 1; ++j1) {
				int range = 1;
				if (j1 == j) {
					range = 0;
				}
				if (j1 >= leafMin) {
					range = 2;
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
			if (!flag) {
				return false;
			}
			boolean canGrow = true;
			Block below = world.getBlock(i, j - 1, k);
			if (!below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable) Blocks.sapling)) {
				canGrow = false;
			}
			if (canGrow) {
				int j1;
				below = world.getBlock(i, j - 1, k);
				below.onPlantGrow(world, i, j - 1, k, i, j, k);
				int deg = 0;
				for (j1 = j + height; j1 >= leafMin; --j1) {
					int branches = 1 + random.nextInt(2);
					for (int b = 0; b < branches; ++b) {
						float angle = (float) Math.toRadians(deg += 50 + random.nextInt(70));
						float cos = MathHelper.cos(angle);
						float sin = MathHelper.sin(angle);
						float angleY = random.nextFloat() * 0.8726646259971648f;
						float sinY = MathHelper.sin(angleY);
						int length = 4 + random.nextInt(6);
						int i1 = i;
						int k1 = k;
						int j2 = j1;
						for (int l = 0; l < length; ++l) {
							Block block;
							if (Math.floor(cos * l) != Math.floor(cos * (l - 1))) {
								i1 = (int) (i1 + Math.signum(cos));
							}
							if (Math.floor(sin * l) != Math.floor(sin * (l - 1))) {
								k1 = (int) (k1 + Math.signum(sin));
							}
							if (Math.floor(sinY * l) != Math.floor(sinY * (l - 1))) {
								j2 = (int) (j2 + Math.signum(sinY));
							}
							if (!(block = world.getBlock(i1, j2, k1)).isReplaceable(world, i1, j2, k1) && !block.isWood(world, i1, j2, k1) && !block.isLeaves(world, i1, j2, k1)) {
								break;
							}
							setBlockAndNotifyAdequately(world, i1, j2, k1, woodBlock, woodMeta | 0xC);
						}
						growLeafCanopy(world, random, i1, j2, k1);
					}
				}
				for (j1 = j; j1 < j + height; ++j1) {
					setBlockAndNotifyAdequately(world, i, j1, k, woodBlock, woodMeta);
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
		}
		return false;
	}

	public void growLeafCanopy(World world, Random random, int i, int j, int k) {
		int leafStart = j - 1;
		int leafTop = j + 2;
		int maxRange = 3 + random.nextInt(2);
		int[] ranges = {-2, 0, -1, -2};
		for (int j1 = leafStart; j1 <= leafTop; ++j1) {
			int leafRange = maxRange + ranges[j1 - leafStart];
			int leafRangeSq = leafRange * leafRange;
			for (int i1 = i - leafRange; i1 <= i + leafRange; ++i1) {
				for (int k1 = k - leafRange; k1 <= k + leafRange; ++k1) {
					boolean grow;
					Block block;
					int i2 = Math.abs(i1 - i);
					int k2 = Math.abs(k1 - k);
					int j2 = Math.abs(j1 - j);
					int dSq = i2 * i2 + k2 * k2;
					int dCh = i2 + j2 + k2;
					grow = dSq < leafRangeSq && dCh <= 4;
					if (i2 == leafRange - 1 || k2 == leafRange - 1) {
						grow = grow && random.nextInt(4) != 0;
					}
					if (!grow || !(block = world.getBlock(i1, j1, k1)).isReplaceable(world, i1, j1, k1) && !block.isLeaves(world, i1, j1, k1)) {
						continue;
					}
					setBlockAndNotifyAdequately(world, i1, j1, k1, leafBlock, leafMeta);
				}
			}
		}
	}

	public LOTRWorldGenMallorn setMinMaxHeight(int min, int max) {
		minHeight = min;
		maxHeight = max;
		return this;
	}
}
