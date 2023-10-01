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

public class LOTRWorldGenFir extends WorldGenAbstractTree {
	public Block woodBlock = LOTRMod.wood4;
	public int woodMeta = 3;
	public Block leafBlock = LOTRMod.leaves4;
	public int leafMeta = 3;
	public int minHeight = 6;
	public int maxHeight = 13;

	public LOTRWorldGenFir(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		Block below;
		int height = MathHelper.getRandomIntegerInRange(random, minHeight, maxHeight);
		boolean flag = true;
		if (j >= 1 && height + 2 <= 256) {
			for (int j1 = j; j1 <= j + height + 2; ++j1) {
				int range = 1;
				if (j1 == j) {
					range = 0;
				}
				if (j1 >= j + height - 1) {
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
		int leafLevel = j + height + 2;
		int leafLayers = 3;
		for (int l = 0; l <= leafLayers * 2; ++l) {
			int leafRange = l / 2;
			for (int i1 = i - leafRange; i1 <= i + leafRange; ++i1) {
				for (int k1 = k - leafRange; k1 <= k + leafRange; ++k1) {
					Block block = world.getBlock(i1, leafLevel, k1);
					int i2 = Math.abs(i1 - i);
					if (i2 + Math.abs(k1 - k) > leafRange || !block.isReplaceable(world, i1, leafLevel, k1) && !block.isLeaves(world, i1, leafLevel, k1)) {
						continue;
					}
					setBlockAndNotifyAdequately(world, i1, leafLevel, k1, leafBlock, leafMeta);
				}
			}
			--leafLevel;
		}
		for (int j1 = 0; j1 < height; ++j1) {
			setBlockAndNotifyAdequately(world, i, j + j1, k, woodBlock, woodMeta);
		}
		return true;
	}

	public LOTRWorldGenFir setMinMaxHeight(int min, int max) {
		minHeight = min;
		maxHeight = max;
		return this;
	}
}
