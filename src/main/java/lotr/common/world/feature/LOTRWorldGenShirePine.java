package lotr.common.world.feature;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class LOTRWorldGenShirePine extends WorldGenAbstractTree {
	public Block woodBlock = LOTRMod.wood;
	public int woodMeta;
	public Block leafBlock = LOTRMod.leaves;
	public int leafMeta;
	public int minHeight = 10;
	public int maxHeight = 20;

	public LOTRWorldGenShirePine(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		int height = MathHelper.getRandomIntegerInRange(random, minHeight, maxHeight);
		int leafHeight = 6 + random.nextInt(4);
		int minLeafHeight = j + height - leafHeight;
		int maxLeafWidth = 2 + random.nextInt(2);
		boolean flag = true;
		if (j >= 1 && j + height + 1 <= 256) {
			for (int j1 = j; j1 <= j + 1 + height && flag; ++j1) {
				int checkRange;
				checkRange = j1 < minLeafHeight ? 0 : maxLeafWidth;
				for (int i1 = i - checkRange; i1 <= i + checkRange && flag; ++i1) {
					for (int k1 = k - checkRange; k1 <= k + checkRange && flag; ++k1) {
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
			Block below = world.getBlock(i, j - 1, k);
			if (below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable) Blocks.sapling)) {
				below.onPlantGrow(world, i, j - 1, k, i, j, k);
				int leafWidth = random.nextInt(2);
				int leafWidthLimit = 1;
				int nextLeafWidth = 0;
				for (int j1 = j + height; j1 >= minLeafHeight; --j1) {
					for (int i1 = i - leafWidth; i1 <= i + leafWidth; ++i1) {
						for (int k1 = k - leafWidth; k1 <= k + leafWidth; ++k1) {
							Block block;
							int i2 = i1 - i;
							int k2 = k1 - k;
							if (leafWidth > 0 && Math.abs(i2) == leafWidth && Math.abs(k2) == leafWidth || !(block = world.getBlock(i1, j1, k1)).isReplaceable(world, i1, j1, k1) && !block.isLeaves(world, i1, j1, k1)) {
								continue;
							}
							setBlockAndNotifyAdequately(world, i1, j1, k1, leafBlock, leafMeta);
						}
					}
					if (leafWidth >= leafWidthLimit) {
						leafWidth = nextLeafWidth;
						nextLeafWidth = 1;
						leafWidthLimit++;
						if (leafWidthLimit <= maxLeafWidth) {
							continue;
						}
						leafWidthLimit = maxLeafWidth;
						continue;
					}
					++leafWidth;
				}
				int lastDir = -1;
				for (int j1 = j; j1 < j + height; ++j1) {
					int i1;
					int k1;
					int dir;
					setBlockAndNotifyAdequately(world, i, j1, k, woodBlock, woodMeta);
					if (j1 < j + 3 || j1 >= minLeafHeight || random.nextInt(3) != 0 || (dir = random.nextInt(4)) == lastDir) {
						continue;
					}
					lastDir = dir;
					int length = 1;
					for (int l = 1; l <= length && isReplaceable(world, i1 = i + Direction.offsetX[dir] * l, j1, k1 = k + Direction.offsetZ[dir] * l); ++l) {
						if (dir == 0 || dir == 2) {
							setBlockAndNotifyAdequately(world, i1, j1, k1, woodBlock, woodMeta | 8);
							continue;
						}
						setBlockAndNotifyAdequately(world, i1, j1, k1, woodBlock, woodMeta | 4);
					}
				}
				return true;
			}
		}
		return false;
	}
}
