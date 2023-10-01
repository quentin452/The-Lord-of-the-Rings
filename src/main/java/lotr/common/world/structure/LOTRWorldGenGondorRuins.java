package lotr.common.world.structure;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityGondorRuinsWraith;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenGondorRuins extends LOTRWorldGenStructureBase {
	public LOTRWorldGenGondorRuins() {
		super(false);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		int j1;
		int k1;
		int k12;
		int i1;
		int k13;
		if (world.getBlock(i, j - 1, k) != Blocks.grass) {
			return false;
		}
		int slabRuinParts = 3 + random.nextInt(4);
		for (int l = 0; l < slabRuinParts; ++l) {
			int i12 = i - 5 + random.nextInt(10);
			k12 = k - 5 + random.nextInt(10);
			if (i12 == i && k12 == k) {
				continue;
			}
			j1 = world.getHeightValue(i12, k12);
			if (world.getBlock(i12, j1 - 1, k12).isOpaqueCube()) {
				placeRandomSlab(world, random, i12, j1, k12);
			}
			setGrassToDirt(world, i12, j1 - 1, k12);
		}
		int smallRuinParts = 3 + random.nextInt(4);
		for (int l = 0; l < smallRuinParts; ++l) {
			i1 = i - 5 + random.nextInt(10);
			k13 = k - 5 + random.nextInt(10);
			if (i1 == i && k13 == k) {
				continue;
			}
			int j12 = world.getHeightValue(i1, k13);
			if (world.getBlock(i1, j12 - 1, k13).isOpaqueCube()) {
				int height = 1 + random.nextInt(3);
				for (int j2 = 0; j2 < height; ++j2) {
					placeRandomBrick(world, random, i1, j12 + j2, k13);
				}
			}
			setGrassToDirt(world, i1, j12 - 1, k13);
		}
		int largeRuinParts = 3 + random.nextInt(5);
		for (int l = 0; l < largeRuinParts; ++l) {
			int i13 = i - 5 + random.nextInt(10);
			k1 = k - 5 + random.nextInt(10);
			if (i13 == i && k1 == k) {
				continue;
			}
			int j13 = world.getHeightValue(i13, k1);
			if (world.getBlock(i13, j13 - 1, k1).isOpaqueCube()) {
				int height = 4 + random.nextInt(7);
				for (int j2 = 0; j2 < height; ++j2) {
					placeRandomBrick(world, random, i13, j13 + j2, k1);
				}
			}
			setGrassToDirt(world, i13, j13 - 1, k1);
		}
		for (i1 = i - 1; i1 <= i + 1; ++i1) {
			for (j1 = j - 2; j1 >= j - 5; --j1) {
				for (k1 = k - 1; k1 <= k + 1; ++k1) {
					if (LOTRMod.isOpaque(world, i1, j1, k1)) {
						continue;
					}
					return true;
				}
			}
		}
		for (i1 = i - 1; i1 <= i + 8; ++i1) {
			for (j1 = j - 6; j1 >= j - 11; --j1) {
				for (k1 = k - 3; k1 <= k + 3; ++k1) {
					if (LOTRMod.isOpaque(world, i1, j1, k1)) {
						continue;
					}
					return true;
				}
			}
		}
		for (i1 = i - 1; i1 <= i + 8; ++i1) {
			for (j1 = j - 6; j1 >= j - 11; --j1) {
				for (k1 = k - 3; k1 <= k + 3; ++k1) {
					if (j1 == j - 6 || j1 < j - 9) {
						world.setBlock(i1, j1, k1, LOTRMod.rock, 1, 2);
						continue;
					}
					world.setBlock(i1, j1, k1, LOTRMod.brick, 1, 2);
				}
			}
		}
		for (i1 = i; i1 <= i + 7; ++i1) {
			for (j1 = j - 7; j1 >= j - 9; --j1) {
				for (k1 = k - 2; k1 <= k + 2; ++k1) {
					world.setBlock(i1, j1, k1, Blocks.air, 0, 2);
				}
			}
		}
		for (k12 = k - 2; k12 <= k + 2; ++k12) {
			world.setBlock(i + 7, j - 9, k12, LOTRMod.brick, 1, 2);
			world.setBlock(i + 7, j - 7, k12, LOTRMod.slabSingle, 11, 2);
			world.setBlock(i, j - 7, k12, LOTRMod.slabSingle, 11, 2);
		}
		for (i1 = i + 1; i1 <= i + 5; ++i1) {
			for (k13 = k - 1; k13 <= k + 1; ++k13) {
				world.setBlock(i1, j - 10, k13, LOTRMod.slabDouble, 2, 2);
			}
		}
		world.setBlock(i + 2, j - 9, k, LOTRMod.slabSingle, 2, 2);
		world.setBlock(i + 3, j - 9, k, LOTRMod.slabSingle, 2, 2);
		world.setBlock(i + 4, j - 9, k, LOTRMod.slabSingle, 2, 2);
		placeSpawnerChest(world, i + 4, j - 10, k, LOTRMod.spawnerChestStone, 4, LOTREntityGondorRuinsWraith.class);
		LOTRChestContents.fillChest(world, random, i + 4, j - 10, k, LOTRChestContents.GONDOR_RUINS_TREASURE);
		world.setBlock(i + 2, j - 10, k, LOTRMod.chestStone, 4, 2);
		LOTRChestContents.fillChest(world, random, i + 2, j - 10, k, LOTRChestContents.GONDOR_RUINS_BONES);
		for (int j14 = j - 2; j14 >= j - 9; --j14) {
			world.setBlock(i, j14, k, Blocks.ladder, 5, 2);
		}
		world.setBlock(i, j - 1, k, LOTRMod.brick, 5, 2);
		return true;
	}

	public void placeRandomBrick(World world, Random random, int i, int j, int k) {
		if (random.nextInt(4) == 0) {
			setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.brick, 2 + random.nextInt(2));
		} else {
			setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.brick, 1);
		}
	}

	public void placeRandomSlab(World world, Random random, int i, int j, int k) {
		if (random.nextInt(4) == 0) {
			setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.slabSingle, 4 + random.nextInt(2));
		} else {
			setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.slabSingle, 3);
		}
	}
}
