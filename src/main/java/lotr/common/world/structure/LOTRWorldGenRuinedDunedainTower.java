package lotr.common.world.structure;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenRuinedDunedainTower extends LOTRWorldGenStructureBase {
	public LOTRWorldGenRuinedDunedainTower(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		int k1;
		int i1;
		if (restrictions && world.getBlock(i, j - 1, k) != Blocks.grass) {
			return false;
		}
		--j;
		int rotation = random.nextInt(4);
		int radius = 4 + random.nextInt(2);
		if (!restrictions && usingPlayer != null) {
			rotation = usingPlayerRotation();
			switch (rotation) {
				case 0: {
					k += radius;
					break;
				}
				case 1: {
					i -= radius;
					break;
				}
				case 2: {
					k -= radius;
					break;
				}
				case 3: {
					i += radius;
				}
			}
		}
		int sections = 4 + random.nextInt(3);
		int sectionHeight = 4 + random.nextInt(4);
		int maxHeight = (sections - 1) * sectionHeight;
		int wallThresholdMin = radius;
		wallThresholdMin *= wallThresholdMin;
		int wallThresholdMax = radius + 1;
		wallThresholdMax *= wallThresholdMax;
		for (i1 = i - radius; i1 <= i + radius; ++i1) {
			for (int k12 = k - radius; k12 <= k + radius; ++k12) {
				int j1;
				int i2 = i1 - i;
				int k2 = k12 - k;
				int distSq = i2 * i2 + k2 * k2;
				if (distSq >= wallThresholdMax) {
					continue;
				}
				if (distSq >= wallThresholdMin) {
					for (j1 = j - 1; j1 >= 0; --j1) {
						Block block = world.getBlock(i1, j1, k12);
						placeRandomBrick(world, random, i1, j1, k12);
						if (block == Blocks.grass || block == Blocks.dirt || block == Blocks.stone || !restrictions && block.isOpaqueCube()) {
							break;
						}
					}
					int j2 = j + maxHeight;
					for (int j12 = j; j12 <= j2; ++j12) {
						if (random.nextInt(20) == 0) {
							continue;
						}
						placeRandomBrick(world, random, i1, j12, k12);
					}
					int j3 = j2 + 1 + random.nextInt(3);
					for (int j13 = j2; j13 <= j3; ++j13) {
						placeRandomBrick(world, random, i1, j13, k12);
					}
					continue;
				}
				for (j1 = j + sectionHeight; j1 <= j + maxHeight; j1 += sectionHeight) {
					if (random.nextInt(6) == 0) {
						continue;
					}
					setBlockAndNotifyAdequately(world, i1, j1, k12, Blocks.stone_slab, 8);
				}
			}
		}
		for (int j1 = j + sectionHeight; j1 < j + maxHeight; j1 += sectionHeight) {
			for (int j2 = j1 + 2; j2 <= j1 + 3; ++j2) {
				for (int i12 = i - 1; i12 <= i + 1; ++i12) {
					placeIronBars(world, random, i12, j2, k - radius);
					placeIronBars(world, random, i12, j2, k + radius);
				}
				for (k1 = k - 1; k1 <= k + 1; ++k1) {
					placeIronBars(world, random, i - radius, j2, k1);
					placeIronBars(world, random, i + radius, j2, k1);
				}
			}
		}
		setBlockAndNotifyAdequately(world, i, j + maxHeight, k, Blocks.stone_slab, 8);
		setBlockAndNotifyAdequately(world, i, j + maxHeight + 1, k, LOTRMod.chestStone, rotation + 2);
		LOTRChestContents.fillChest(world, random, i, j + maxHeight + 1, k, LOTRChestContents.DUNEDAIN_TOWER);
		switch (rotation) {
			case 0: {
				int j1;
				int height;
				for (i1 = i - 1; i1 <= i + 1; ++i1) {
					height = j + 1 + random.nextInt(3);
					for (j1 = j; j1 <= height; ++j1) {
						setBlockAndNotifyAdequately(world, i1, j1, k - radius, Blocks.air, 0);
					}
				}
				break;
			}
			case 1: {
				int j1;
				int k13;
				int height;
				for (k13 = k - 1; k13 <= k + 1; ++k13) {
					height = j + 1 + random.nextInt(3);
					for (j1 = j; j1 <= height; ++j1) {
						setBlockAndNotifyAdequately(world, i + radius, j1, k13, Blocks.air, 0);
					}
				}
				break;
			}
			case 2: {
				int j1;
				int height;
				for (i1 = i - 1; i1 <= i + 1; ++i1) {
					height = j + 1 + random.nextInt(3);
					for (j1 = j; j1 <= height; ++j1) {
						setBlockAndNotifyAdequately(world, i1, j1, k + radius, Blocks.air, 0);
					}
				}
				break;
			}
			case 3: {
				int j1;
				int k13;
				int height;
				for (k13 = k - 1; k13 <= k + 1; ++k13) {
					height = j + 1 + random.nextInt(3);
					for (j1 = j; j1 <= height; ++j1) {
						setBlockAndNotifyAdequately(world, i - radius, j1, k13, Blocks.air, 0);
					}
				}
				break;
			}
		}
		for (int l = 0; l < 16; ++l) {
			int j1;
			int i13 = i - random.nextInt(radius * 2) + random.nextInt(radius * 2);
			if (world.getBlock(i13, (j1 = world.getHeightValue(i13, k1 = k - random.nextInt(radius * 2) + random.nextInt(radius * 2))) - 1, k1) != Blocks.grass) {
				continue;
			}
			int randomFeature = random.nextInt(4);
			boolean flag = true;
			if (randomFeature == 0) {
				if (!LOTRMod.isOpaque(world, i13, j1, k1)) {
					setBlockAndNotifyAdequately(world, i13, j1, k1, Blocks.stone_slab, random.nextBoolean() ? 0 : 5);
				}
			} else {
				int j2;
				for (j2 = j1; j2 < j1 + randomFeature && flag; ++j2) {
					flag = !LOTRMod.isOpaque(world, i13, j2, k1);
				}
				if (flag) {
					for (j2 = j1; j2 < j1 + randomFeature; ++j2) {
						placeRandomBrick(world, random, i13, j2, k1);
					}
				}
			}
			if (!flag) {
				continue;
			}
			world.getBlock(i13, j1 - 1, k1).onPlantGrow(world, i13, j1 - 1, k1, i13, j1, k1);
		}
		return true;
	}

	public void placeIronBars(World world, Random random, int i, int j, int k) {
		if (random.nextInt(4) == 0) {
			setBlockAndNotifyAdequately(world, i, j, k, Blocks.air, 0);
		} else {
			setBlockAndNotifyAdequately(world, i, j, k, Blocks.iron_bars, 0);
		}
	}

	public void placeRandomBrick(World world, Random random, int i, int j, int k) {
		if (random.nextInt(5) == 0) {
			setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.brick2, 4 + random.nextInt(2));
		} else {
			setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.brick2, 3);
		}
	}
}
