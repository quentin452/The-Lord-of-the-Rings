package lotr.common.world.feature;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenMangrove extends WorldGenAbstractTree {
	public Block woodID = LOTRMod.wood3;
	public int woodMeta = 3;
	public Block leafID = LOTRMod.leaves3;
	public int leafMeta = 3;

	public LOTRWorldGenMangrove(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		int height = 6 + random.nextInt(5);
		boolean flag = true;
		if (j >= 1 && j + height + 1 <= 256) {
			boolean canGrow;
			for (int j1 = j; j1 <= j + 1 + height; ++j1) {
				int range = 1;
				if (j1 == j) {
					range = 0;
				}
				if (j1 >= j + 1 + height - 2) {
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
			Block below = world.getBlock(i, j - 1, k);
			canGrow = below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable) Blocks.sapling) || below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, Blocks.deadbush);
			if (canGrow) {
				int j1;
				world.getBlock(i, j - 1, k).onPlantGrow(world, i, j - 1, k, i, j, k);
				int leafStart = 3;
				int leafRangeMin = 0;
				int leafRangeFactor = 2;
				for (j1 = j - leafStart + height; j1 <= j + height; ++j1) {
					int j2 = j1 - (j + height);
					int leafRange = leafRangeMin + 1 - j2 / leafRangeFactor;
					for (int i1 = i - leafRange; i1 <= i + leafRange; ++i1) {
						int i2 = i1 - i;
						for (int k1 = k - leafRange; k1 <= k + leafRange; ++k1) {
							int k2 = k1 - k;
							Block block = world.getBlock(i1, j1, k1);
							if (Math.abs(i2) == leafRange && Math.abs(k2) == leafRange && (random.nextInt(2) == 0 || j2 == 0) || !block.canBeReplacedByLeaves(world, i1, j1, k1)) {
								continue;
							}
							setBlockAndNotifyAdequately(world, i1, j1, k1, leafID, leafMeta);
							if (random.nextInt(8) == 0 && world.getBlock(i1 - 1, j1, k1).isAir(world, i1 - 1, j1, k1)) {
								growVines(world, random, i1 - 1, j1, k1, 8);
							}
							if (random.nextInt(8) == 0 && world.getBlock(i1 + 1, j1, k1).isAir(world, i1 + 1, j1, k1)) {
								growVines(world, random, i1 + 1, j1, k1, 2);
							}
							if (random.nextInt(8) == 0 && world.getBlock(i1, j1, k1 - 1).isAir(world, i1, j1, k1 - 1)) {
								growVines(world, random, i1, j1, k1 - 1, 1);
							}
							if (random.nextInt(8) != 0 || !world.getBlock(i1, j1, k1 + 1).isAir(world, i1, j1, k1 + 1)) {
								continue;
							}
							growVines(world, random, i1, j1, k1 + 1, 4);
						}
					}
				}
				for (j1 = 0; j1 < height; ++j1) {
					Block block = world.getBlock(i, j + j1, k);
					if (!block.isReplaceable(world, i, j + j1, k) && !block.isLeaves(world, i, j + j1, k)) {
						continue;
					}
					setBlockAndNotifyAdequately(world, i, j + j1, k, woodID, woodMeta);
				}
				for (int i1 = i - 1; i1 <= i + 1; ++i1) {
					for (int k1 = k - 1; k1 <= k + 1; ++k1) {
						int i2 = i1 - i;
						int k2 = k1 - k;
						if (Math.abs(i2) == Math.abs(k2)) {
							continue;
						}
						int rootX = i1;
						int rootY = j + 1 + random.nextInt(3);
						int rootZ = k1;
						int xWay = Integer.signum(i2);
						int zWay = Integer.signum(k2);
						int roots = 0;
						while (world.getBlock(rootX, rootY, k1).isReplaceable(world, rootX, rootY, rootZ)) {
							setBlockAndNotifyAdequately(world, rootX, rootY, rootZ, woodID, woodMeta | 0xC);
							world.getBlock(rootX, rootY - 1, rootZ).onPlantGrow(world, rootX, rootY - 1, rootZ, rootX, rootY, rootZ);
							--rootY;
							if (random.nextInt(3) > 0) {
								rootX += xWay;
								rootZ += zWay;
							}
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

	public void growVines(World world, Random random, int i, int j, int k, int meta) {
		setBlockAndNotifyAdequately(world, i, j, k, Blocks.vine, meta);
		int vines = 0;
		while (world.getBlock(i, --j, k).isAir(world, i, j, k) && vines < 2 + random.nextInt(3)) {
			setBlockAndNotifyAdequately(world, i, j, k, Blocks.vine, meta);
			++vines;
		}
	}
}
