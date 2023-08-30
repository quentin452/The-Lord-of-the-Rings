package lotr.common.world.structure;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRuinedGondorTower extends LOTRWorldGenStructureBase {
	public LOTRWorldGenRuinedGondorTower(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		Block id;
		int i1;
		int j1;
		int j12;
		int k1;
		int k12;
		int i12;
		if (restrictions && world.getBlock(i, j - 1, k) != Blocks.grass) {
			return false;
		}
		--j;
		int rotation = random.nextInt(4);
		if (!restrictions && usingPlayer != null) {
			rotation = usingPlayerRotation();
		}
		switch (rotation) {
			case 0: {
				k += 4;
				break;
			}
			case 1: {
				i -= 4;
				break;
			}
			case 2: {
				k -= 4;
				break;
			}
			case 3: {
				i += 4;
			}
		}
		if (restrictions) {
			for (i1 = i - 3; i1 <= i + 3; ++i1) {
				for (k12 = k + 3; k12 <= k + 3; ++k12) {
					j12 = world.getHeightValue(i1, k12);
					Block block = world.getBlock(i1, j12 - 1, k12);
					if (block == Blocks.grass || block.isWood(world, i1, j12 - 1, k12) || block.isLeaves(world, i1, j12 - 1, k12)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = i - 3; i1 <= i + 3; ++i1) {
			for (k12 = k - 3; k12 <= k + 3; ++k12) {
				if (Math.abs(i1 - i) == 3 || Math.abs(k12 - k) == 3) {
					for (j12 = j + 8; (j12 >= j || !LOTRMod.isOpaque(world, i1, j12, k12)) && j12 >= 0; --j12) {
						placeRandomBrick(world, random, i1, j12, k12);
						setGrassToDirt(world, i1, j12 - 1, k12);
					}
				} else {
					for (j12 = j; !LOTRMod.isOpaque(world, i1, j12, k12) && j12 >= 0; --j12) {
						placeRandomBrick(world, random, i1, j12, k12);
						setGrassToDirt(world, i1, j12 - 1, k12);
					}
					for (j12 = j + 1; j12 <= j + 8; ++j12) {
						setBlockAndNotifyAdequately(world, i1, j12, k12, Blocks.air, 0);
					}
				}
				if (Math.abs(i1 - i) >= 3 || Math.abs(k12 - k) >= 3 || random.nextInt(20) == 0) {
					continue;
				}
				setBlockAndNotifyAdequately(world, i1, j + 5, k12, Blocks.planks, 0);
			}
		}
		setBlockAndNotifyAdequately(world, i - 2, j + 1, k - 2, LOTRMod.chestLebethron, 0);
		if (random.nextInt(3) == 0) {
			LOTRChestContents.fillChest(world, random, i - 2, j + 1, k - 2, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES);
		}
		setBlockAndNotifyAdequately(world, i + 2, j + 1, k - 2, LOTRMod.gondorianTable, 0);
		if (random.nextInt(3) != 0) {
			setBlockAndNotifyAdequately(world, i + 2, j + 6, k - 2, LOTRMod.strawBed, 10);
			setBlockAndNotifyAdequately(world, i + 2, j + 6, k - 1, LOTRMod.strawBed, 2);
		}
		if (random.nextBoolean()) {
			setBlockAndNotifyAdequately(world, i + 2, j + 6, k + 2, Blocks.anvil, 8);
		}
		for (k1 = k; k1 <= k + 2; ++k1) {
			setBlockAndNotifyAdequately(world, i - 2, j + 6, k1, LOTRMod.slabSingle, 10);
		}
		if (random.nextBoolean()) {
			setBlockAndNotifyAdequately(world, i - 2, j + 7, k, LOTRMod.mugBlock, 1);
		}
		if (random.nextBoolean()) {
			setBlockAndNotifyAdequately(world, i - 2, j + 7, k + 1, LOTRMod.plateBlock, 0);
		}
		if (random.nextBoolean()) {
			setBlockAndNotifyAdequately(world, i - 2, j + 7, k + 2, LOTRMod.barrel, 5);
		}
		for (i1 = i - 4; i1 <= i + 4; ++i1) {
			for (k12 = k - 4; k12 <= k + 4; ++k12) {
				placeRandomBrick(world, random, i1, j + 9, k12);
				if ((Math.abs(i1 - i) != 4 || Math.abs(k12 - k) % 2 != 0) && (Math.abs(k12 - k) != 4 || Math.abs(i1 - i) % 2 != 0) || !LOTRMod.isOpaque(world, i1, j + 9, k12)) {
					continue;
				}
				placeRandomBrick(world, random, i1, j + 10, k12);
			}
		}
		for (j1 = j + 1; j1 <= j + 9; ++j1) {
			if (rotation == 2) {
				if (!LOTRMod.isOpaque(world, i + 3, j1, k)) {
					continue;
				}
				setBlockAndNotifyAdequately(world, i + 2, j1, k, Blocks.ladder, 4);
				continue;
			}
			if (!LOTRMod.isOpaque(world, i, j1, k + 3)) {
				continue;
			}
			setBlockAndNotifyAdequately(world, i, j1, k + 2, Blocks.ladder, 2);
		}
		if (rotation == 0) {
			for (j1 = j + 1; j1 <= j + 2; ++j1) {
				setBlockAndNotifyAdequately(world, i - 1, j1, k - 3, LOTRMod.brick, 5);
				setBlockAndNotifyAdequately(world, i, j1, k - 3, Blocks.air, 0);
				setBlockAndNotifyAdequately(world, i + 1, j1, k - 3, LOTRMod.brick, 5);
			}
			for (k1 = k - 4; k1 >= k - 7; --k1) {
				for (i12 = i - 2; i12 <= i + 2; i12 += 4) {
					j12 = world.getHeightValue(i12, k1);
					id = world.getBlock(i12, j12 - 1, k1);
					if (id != Blocks.grass || random.nextInt(4) == 0) {
						continue;
					}
					setBlockAndNotifyAdequately(world, i12, j12, k1, LOTRMod.wall, 3 + random.nextInt(3));
				}
			}
		}
		if (rotation == 1) {
			for (j1 = j + 1; j1 <= j + 2; ++j1) {
				setBlockAndNotifyAdequately(world, i + 3, j1, k - 1, LOTRMod.brick, 5);
				setBlockAndNotifyAdequately(world, i + 3, j1, k, Blocks.air, 0);
				setBlockAndNotifyAdequately(world, i + 3, j1, k + 1, LOTRMod.brick, 5);
			}
			for (i1 = i + 4; i1 <= i + 7; ++i1) {
				for (k12 = k - 2; k12 <= k + 2; k12 += 4) {
					j12 = world.getHeightValue(i1, k12);
					id = world.getBlock(i1, j12 - 1, k12);
					if (id != Blocks.grass || random.nextInt(4) == 0) {
						continue;
					}
					setBlockAndNotifyAdequately(world, i1, j12, k12, LOTRMod.wall, 3 + random.nextInt(3));
				}
			}
		}
		if (rotation == 2) {
			for (j1 = j + 1; j1 <= j + 2; ++j1) {
				setBlockAndNotifyAdequately(world, i - 1, j1, k + 3, LOTRMod.brick, 5);
				setBlockAndNotifyAdequately(world, i, j1, k + 3, Blocks.air, 0);
				setBlockAndNotifyAdequately(world, i + 1, j1, k + 3, LOTRMod.brick, 5);
			}
			for (k1 = k + 4; k1 <= k + 7; ++k1) {
				for (i12 = i - 2; i12 <= i + 2; i12 += 4) {
					j12 = world.getHeightValue(i12, k1);
					id = world.getBlock(i12, j12 - 1, k1);
					if (id != Blocks.grass || random.nextInt(4) == 0) {
						continue;
					}
					setBlockAndNotifyAdequately(world, i12, j12, k1, LOTRMod.wall, 3 + random.nextInt(3));
				}
			}
		}
		if (rotation == 3) {
			for (j1 = j + 1; j1 <= j + 2; ++j1) {
				setBlockAndNotifyAdequately(world, i - 3, j1, k - 1, LOTRMod.brick, 5);
				setBlockAndNotifyAdequately(world, i - 3, j1, k, Blocks.air, 0);
				setBlockAndNotifyAdequately(world, i - 3, j1, k + 1, LOTRMod.brick, 5);
			}
			for (i1 = i - 4; i1 >= i - 7; --i1) {
				for (k12 = k - 2; k12 <= k + 2; k12 += 4) {
					j12 = world.getHeightValue(i1, k12);
					id = world.getBlock(i1, j12 - 1, k12);
					if (id != Blocks.grass || random.nextInt(4) == 0) {
						continue;
					}
					setBlockAndNotifyAdequately(world, i1, j12, k12, LOTRMod.wall, 3 + random.nextInt(3));
				}
			}
		}
		int radius = 8;
		int ruinParts = 2 + random.nextInt(6);
		for (int l = 0; l < ruinParts; ++l) {
			int j13;
			int k13;
			int j2;
			int i13 = i - random.nextInt(radius * 2) + random.nextInt(radius * 2);
			Block id2 = world.getBlock(i13, (j13 = world.getHeightValue(i13, k13 = k - random.nextInt(radius * 2) + random.nextInt(radius * 2))) - 1, k13);
			if (id2 != Blocks.grass) {
				continue;
			}
			int height = 1 + random.nextInt(3);
			boolean flag = true;
			for (j2 = j13; j2 < j13 + height && flag; ++j2) {
				flag = !LOTRMod.isOpaque(world, i13, j2, k13);
			}
			if (!flag) {
				continue;
			}
			for (j2 = j13; j2 < j13 + height; ++j2) {
				setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.brick, 2 + random.nextInt(2));
			}
			setGrassToDirt(world, i13, j13 - 1, k13);
		}
		return true;
	}

	public void placeRandomBrick(World world, Random random, int i, int j, int k) {
		if (random.nextInt(16) == 0) {
			return;
		}
		if (random.nextInt(4) == 0) {
			setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.brick, 2 + random.nextInt(2));
		} else {
			setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.brick, 1);
		}
	}
}
