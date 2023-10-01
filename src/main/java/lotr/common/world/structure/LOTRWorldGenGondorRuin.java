package lotr.common.world.structure;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenGondorRuin extends LOTRWorldGenStructureBase {
	public LOTRWorldGenGondorRuin(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		int j1;
		int k1;
		int i1;
		int rotation = random.nextInt(4);
		if (!restrictions && usingPlayer != null) {
			rotation = usingPlayerRotation();
		}
		switch (rotation) {
			case 0: {
				k += 8;
				break;
			}
			case 1: {
				i -= 8;
				break;
			}
			case 2: {
				k -= 8;
				break;
			}
			case 3: {
				i += 8;
			}
		}
		j = world.getTopSolidOrLiquidBlock(i, k);
		if (restrictions && world.getBlock(i, j - 1, k) != Blocks.grass) {
			return false;
		}
		for (i1 = i - 7; i1 <= i + 7; ++i1) {
			for (k1 = k - 7; k1 <= k + 7; ++k1) {
				j1 = world.getTopSolidOrLiquidBlock(i1, k1);
				Block block = world.getBlock(i1, j1 - 1, k1);
				if (!block.isOpaqueCube()) {
					continue;
				}
				if (random.nextInt(3) == 0) {
					setBlockAndNotifyAdequately(world, i1, j1 - 1, k1, LOTRMod.rock, 1);
				}
				if (random.nextInt(3) == 0) {
					if (random.nextInt(3) == 0) {
						placeRandomSlab(world, random, i1, j1, k1);
					} else {
						placeRandomBrick(world, random, i1, j1, k1);
					}
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
				if (!LOTRMod.isOpaque(world, i1, j1, k1) || random.nextInt(4) != 0) {
					continue;
				}
				if (random.nextInt(5) == 0) {
					setBlockAndNotifyAdequately(world, i1, j1 + 1, k1, LOTRMod.wall, 3);
					placeSkull(world, random, i1, j1 + 2, k1);
					continue;
				}
				if (random.nextInt(3) == 0) {
					placeRandomSlab(world, random, i1, j1 + 1, k1);
					continue;
				}
				placeRandomBrick(world, random, i1, j1 + 1, k1);
			}
		}
		for (i1 = i - 7; i1 <= i + 7; i1 += 7) {
			block9:
			for (k1 = k - 7; k1 <= k + 7; k1 += 7) {
				j1 = world.getTopSolidOrLiquidBlock(i1, k1);
				setGrassToDirt(world, i1, j1 - 1, k1);
				int j2 = j1;
				do {
					placeRandomBrick(world, random, i1, j2, k1);
					if (random.nextInt(4) == 0 || j2 > j1 + 4) {
						if (i1 == i && k1 == k) {
							setBlockAndNotifyAdequately(world, i1, j2 + 1, k1, LOTRMod.beacon, 0);
							continue block9;
						}
						setBlockAndNotifyAdequately(world, i1, j2 + 1, k1, LOTRMod.brick, 5);
						continue block9;
					}
					++j2;
				} while (true);
			}
		}
		return true;
	}

	public void placeRandomBrick(World world, Random random, int i, int j, int k) {
		if (random.nextInt(20) == 0) {
			setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.brick, 5);
		} else if (random.nextInt(4) == 0) {
			setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.brick, 2 + random.nextInt(2));
		} else {
			setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.brick, 1);
		}
	}

	public void placeRandomSlab(World world, Random random, int i, int j, int k) {
		if (random.nextInt(5) == 0) {
			setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.slabSingle, 2);
		} else if (random.nextInt(4) == 0) {
			setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.slabSingle, 4 + random.nextInt(2));
		} else {
			setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.slabSingle, 3);
		}
	}
}
