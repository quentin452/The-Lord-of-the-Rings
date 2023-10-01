package lotr.common.world.feature;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class LOTRWorldGenDesertTrees extends WorldGenAbstractTree {
	public boolean isNatural;
	public Block woodBlock;
	public int woodMeta;
	public Block leafBlock;
	public int leafMeta;

	public LOTRWorldGenDesertTrees(boolean flag, Block b1, int m1, Block b2, int m2) {
		super(flag);
		isNatural = !flag;
		woodBlock = b1;
		woodMeta = m1;
		leafBlock = b2;
		leafMeta = m2;
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		boolean isSoil;
		Block below;
		int height = 3 + random.nextInt(3);
		boolean flag = true;
		if (!isNatural) {
			if (j >= 1) {
				for (int j1 = j; j1 <= j + height + 1; ++j1) {
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
		}
		isSoil = (below = world.getBlock(i, j - 1, k)).canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable) Blocks.sapling) || isNatural && (below == Blocks.sand || below == Blocks.stone);
		if (!isSoil) {
			flag = false;
		}
		if (!flag) {
			return false;
		}
		below.onPlantGrow(world, i, j - 1, k, i, j, k);
		for (int branch = 0; branch < 4; ++branch) {
			int branchLength = 1 + random.nextInt(3);
			int i1 = i;
			int j1 = j + height - 1 - random.nextInt(2);
			int k1 = k;
			for (int l = 0; l < branchLength; ++l) {
				if (random.nextInt(3) != 0) {
					++j1;
				}
				if (random.nextInt(3) != 0) {
					switch (branch) {
						case 0: {
							--i1;
							break;
						}
						case 1: {
							++k1;
							break;
						}
						case 2: {
							++i1;
							break;
						}
						case 3: {
							--k1;
						}
					}
				}
				if (!isReplaceable(world, i1, j1, k1)) {
					break;
				}
				setBlockAndNotifyAdequately(world, i1, j1, k1, woodBlock, woodMeta);
			}
			int leafStart = 1;
			int leafRangeMin = 0;
			for (int j2 = j1 - leafStart; j2 <= j1 + 1; ++j2) {
				int j3 = j2 - j1;
				int leafRange = leafRangeMin + 1 - j3 / 2;
				for (int i2 = i1 - leafRange; i2 <= i1 + leafRange; ++i2) {
					int i3 = i2 - i1;
					for (int k2 = k1 - leafRange; k2 <= k1 + leafRange; ++k2) {
						Block block;
						int k3 = k2 - k1;
						if (Math.abs(i3) == leafRange && Math.abs(k3) == leafRange && (random.nextInt(2) == 0 || j3 == 0) || !(block = world.getBlock(i2, j2, k2)).isReplaceable(world, i2, j2, k2) && !block.isLeaves(world, i2, j2, k2)) {
							continue;
						}
						setBlockAndNotifyAdequately(world, i2, j2, k2, leafBlock, leafMeta);
					}
				}
			}
		}
		for (int j1 = j; j1 < j + height; ++j1) {
			setBlockAndNotifyAdequately(world, i, j1, k, woodBlock, woodMeta);
		}
		return true;
	}
}
