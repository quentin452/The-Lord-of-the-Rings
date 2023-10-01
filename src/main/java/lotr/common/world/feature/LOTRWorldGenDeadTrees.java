package lotr.common.world.feature;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class LOTRWorldGenDeadTrees extends WorldGenAbstractTree {
	public Block woodBlock;
	public int woodMeta;

	public LOTRWorldGenDeadTrees(Block block, int i) {
		super(false);
		woodBlock = block;
		woodMeta = i;
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		Block below = world.getBlock(i, j - 1, k);
		if (!below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable) Blocks.sapling) && below != Blocks.stone && below != Blocks.sand && below != Blocks.gravel) {
			return false;
		}
		below.onPlantGrow(world, i, j - 1, k, i, j, k);
		int height = 3 + random.nextInt(4);
		for (int j1 = j; j1 < j + height; ++j1) {
			setBlockAndNotifyAdequately(world, i, j1, k, woodBlock, woodMeta);
		}
		for (int branch = 0; branch < 4; ++branch) {
			int branchLength = 3 + random.nextInt(5);
			int branchHorizontalPos = 0;
			int branchVerticalPos = j + height - 1 - random.nextInt(2);
			for (int l = 0; l < branchLength; ++l) {
				if (random.nextInt(4) == 0) {
					++branchHorizontalPos;
				}
				if (random.nextInt(3) != 0) {
					++branchVerticalPos;
				}
				switch (branch) {
					case 0: {
						setBlockAndNotifyAdequately(world, i - branchHorizontalPos, branchVerticalPos, k, woodBlock, woodMeta | 0xC);
						continue;
					}
					case 1: {
						setBlockAndNotifyAdequately(world, i, branchVerticalPos, k + branchHorizontalPos, woodBlock, woodMeta | 0xC);
						continue;
					}
					case 2: {
						setBlockAndNotifyAdequately(world, i + branchHorizontalPos, branchVerticalPos, k, woodBlock, woodMeta | 0xC);
						continue;
					}
					case 3: {
						setBlockAndNotifyAdequately(world, i, branchVerticalPos, k - branchHorizontalPos, woodBlock, woodMeta | 0xC);
					}
				}
			}
		}
		return true;
	}
}
