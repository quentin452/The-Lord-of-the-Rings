package lotr.common.world.structure;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenHighElvenTurret extends LOTRWorldGenStructureBase {
	public LOTRWorldGenHighElvenTurret(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		int j1;
		int j12;
		int j13;
		int k1;
		Block block;
		int i1;
		int k12;
		if (restrictions && (block = world.getBlock(i, j - 1, k)) != Blocks.grass && block != Blocks.dirt && block != Blocks.stone) {
			return false;
		}
		--j;
		int rotation = random.nextInt(4);
		if (!restrictions && usingPlayer != null) {
			rotation = usingPlayerRotation();
		}
		switch (rotation) {
			case 0: {
				k += 6;
				break;
			}
			case 1: {
				i -= 6;
				break;
			}
			case 2: {
				k -= 6;
				break;
			}
			case 3: {
				i += 6;
			}
		}
		for (i1 = i - 4; i1 <= i + 4; ++i1) {
			for (k1 = k - 4; k1 <= k + 4; ++k1) {
				for (j12 = j; (j12 == j || !LOTRMod.isOpaque(world, i1, j12, k1)) && j12 >= 0; --j12) {
					setBlockAndNotifyAdequately(world, i1, j12, k1, LOTRMod.brick3, 2);
					setGrassToDirt(world, i1, j12 - 1, k1);
				}
				for (j12 = j + 1; j12 <= j + 7; ++j12) {
					if (Math.abs(i1 - i) == 4 || Math.abs(k1 - k) == 4) {
						setBlockAndNotifyAdequately(world, i1, j12, k1, LOTRMod.brick3, 2);
						continue;
					}
					setBlockAndNotifyAdequately(world, i1, j12, k1, Blocks.air, 0);
				}
			}
		}
		for (i1 = i - 3; i1 <= i + 3; ++i1) {
			for (k1 = k - 3; k1 <= k + 3; ++k1) {
				if (Math.abs(i1 - i) % 2 == Math.abs(k1 - k) % 2) {
					setBlockAndNotifyAdequately(world, i1, j, k1, LOTRMod.pillar, 10);
					continue;
				}
				setBlockAndNotifyAdequately(world, i1, j, k1, Blocks.double_stone_slab, 0);
			}
		}
		for (j13 = j + 1; j13 <= j + 7; ++j13) {
			setBlockAndNotifyAdequately(world, i - 3, j13, k - 3, LOTRMod.pillar, 10);
			setBlockAndNotifyAdequately(world, i - 3, j13, k + 3, LOTRMod.pillar, 10);
			setBlockAndNotifyAdequately(world, i + 3, j13, k - 3, LOTRMod.pillar, 10);
			setBlockAndNotifyAdequately(world, i + 3, j13, k + 3, LOTRMod.pillar, 10);
		}
		for (i1 = i - 4; i1 <= i + 4; ++i1) {
			setBlockAndNotifyAdequately(world, i1, j + 7, k - 4, LOTRMod.stairsHighElvenBrick, 2);
			setBlockAndNotifyAdequately(world, i1, j + 7, k + 4, LOTRMod.stairsHighElvenBrick, 3);
		}
		for (k12 = k - 3; k12 <= k + 3; ++k12) {
			setBlockAndNotifyAdequately(world, i - 4, j + 7, k12, LOTRMod.stairsHighElvenBrick, 0);
			setBlockAndNotifyAdequately(world, i + 4, j + 7, k12, LOTRMod.stairsHighElvenBrick, 1);
		}
		for (i1 = i - 3; i1 <= i + 3; ++i1) {
			for (k1 = k - 3; k1 <= k + 3; ++k1) {
				for (j12 = j + 7; j12 <= j + 15; ++j12) {
					if (Math.abs(i1 - i) == 3 || Math.abs(k1 - k) == 3) {
						if (j12 - j >= 10 && j12 - j <= 14 && Math.abs(i1 - i) >= 3 && Math.abs(k1 - k) >= 3) {
							continue;
						}
						setBlockAndNotifyAdequately(world, i1, j12, k1, LOTRMod.brick3, 2);
						continue;
					}
					setBlockAndNotifyAdequately(world, i1, j12, k1, Blocks.air, 0);
				}
			}
		}
		for (i1 = i - 4; i1 <= i + 4; ++i1) {
			for (k1 = k - 4; k1 <= k + 4; ++k1) {
				for (j12 = j + 16; j12 <= j + 18; ++j12) {
					if (j12 - j == 16 || Math.abs(i1 - i) == 4 || Math.abs(k1 - k) == 4) {
						//noinspection BadOddness
						if (j12 - j == 18 && (Math.abs(i1 - i) % 2 == 1 || Math.abs(k1 - k) % 2 == 1)) {
							setBlockAndNotifyAdequately(world, i1, j12, k1, Blocks.air, 0);
							continue;
						}
						setBlockAndNotifyAdequately(world, i1, j12, k1, LOTRMod.brick3, 2);
						continue;
					}
					setBlockAndNotifyAdequately(world, i1, j12, k1, Blocks.air, 0);
				}
			}
		}
		for (i1 = i - 4; i1 <= i + 4; ++i1) {
			setBlockAndNotifyAdequately(world, i1, j + 16, k - 4, LOTRMod.stairsHighElvenBrick, 6);
			setBlockAndNotifyAdequately(world, i1, j + 16, k + 4, LOTRMod.stairsHighElvenBrick, 7);
		}
		for (k12 = k - 3; k12 <= k + 3; ++k12) {
			setBlockAndNotifyAdequately(world, i - 4, j + 16, k12, LOTRMod.stairsHighElvenBrick, 4);
			setBlockAndNotifyAdequately(world, i + 4, j + 16, k12, LOTRMod.stairsHighElvenBrick, 5);
		}
		switch (rotation) {
			case 0:
				for (i1 = i - 1; i1 <= i + 1; ++i1) {
					setBlockAndNotifyAdequately(world, i1, j, k - 5, Blocks.double_stone_slab, 0);
					setBlockAndNotifyAdequately(world, i1, j, k - 4, Blocks.double_stone_slab, 0);
				}
				for (j13 = j + 1; j13 <= j + 2; ++j13) {
					setBlockAndNotifyAdequately(world, i - 1, j13, k - 5, LOTRMod.brick3, 2);
					setBlockAndNotifyAdequately(world, i, j13, k - 5, Blocks.air, 0);
					setBlockAndNotifyAdequately(world, i, j13, k - 4, Blocks.air, 0);
					setBlockAndNotifyAdequately(world, i + 1, j13, k - 5, LOTRMod.brick3, 2);
				}
				setBlockAndNotifyAdequately(world, i - 1, j + 3, k - 5, LOTRMod.stairsHighElvenBrick, 0);
				setBlockAndNotifyAdequately(world, i, j + 3, k - 5, LOTRMod.brick3, 2);
				setBlockAndNotifyAdequately(world, i + 1, j + 3, k - 5, LOTRMod.stairsHighElvenBrick, 1);
				for (i1 = i + 1; i1 <= i + 2; ++i1) {
					for (j1 = j + 1; j1 <= j + 7; ++j1) {
						setBlockAndNotifyAdequately(world, i1, j1, k + 3, LOTRMod.brick3, 2);
					}
					for (j1 = j + 1; j1 <= j + 16; ++j1) {
						setBlockAndNotifyAdequately(world, i1, j1, k + 2, Blocks.ladder, 2);
					}
				}
				break;
			case 1:
				for (k12 = k - 1; k12 <= k + 1; ++k12) {
					setBlockAndNotifyAdequately(world, i + 5, j, k12, Blocks.double_stone_slab, 0);
					setBlockAndNotifyAdequately(world, i + 4, j, k12, Blocks.double_stone_slab, 0);
				}
				for (j13 = j + 1; j13 <= j + 2; ++j13) {
					setBlockAndNotifyAdequately(world, i + 5, j13, k - 1, LOTRMod.brick3, 2);
					setBlockAndNotifyAdequately(world, i + 5, j13, k, Blocks.air, 0);
					setBlockAndNotifyAdequately(world, i + 4, j13, k, Blocks.air, 0);
					setBlockAndNotifyAdequately(world, i + 5, j13, k + 1, LOTRMod.brick3, 2);
				}
				setBlockAndNotifyAdequately(world, i + 5, j + 3, k - 1, LOTRMod.stairsHighElvenBrick, 2);
				setBlockAndNotifyAdequately(world, i + 5, j + 3, k, LOTRMod.brick3, 2);
				setBlockAndNotifyAdequately(world, i + 5, j + 3, k + 1, LOTRMod.stairsHighElvenBrick, 3);
				for (k12 = k - 1; k12 >= k - 2; --k12) {
					for (j1 = j + 1; j1 <= j + 7; ++j1) {
						setBlockAndNotifyAdequately(world, i - 3, j1, k12, LOTRMod.brick3, 2);
					}
					for (j1 = j + 1; j1 <= j + 16; ++j1) {
						setBlockAndNotifyAdequately(world, i - 2, j1, k12, Blocks.ladder, 5);
					}
				}
				break;
			case 2:
				for (i1 = i - 1; i1 <= i + 1; ++i1) {
					setBlockAndNotifyAdequately(world, i1, j, k + 5, Blocks.double_stone_slab, 0);
					setBlockAndNotifyAdequately(world, i1, j, k + 4, Blocks.double_stone_slab, 0);
				}
				for (j13 = j + 1; j13 <= j + 2; ++j13) {
					setBlockAndNotifyAdequately(world, i - 1, j13, k + 5, LOTRMod.brick3, 2);
					setBlockAndNotifyAdequately(world, i, j13, k + 5, Blocks.air, 0);
					setBlockAndNotifyAdequately(world, i, j13, k + 4, Blocks.air, 0);
					setBlockAndNotifyAdequately(world, i + 1, j13, k + 5, LOTRMod.brick3, 2);
				}
				setBlockAndNotifyAdequately(world, i - 1, j + 3, k + 5, LOTRMod.stairsHighElvenBrick, 0);
				setBlockAndNotifyAdequately(world, i, j + 3, k + 5, LOTRMod.brick3, 2);
				setBlockAndNotifyAdequately(world, i + 1, j + 3, k + 5, LOTRMod.stairsHighElvenBrick, 1);
				for (i1 = i - 1; i1 >= i - 2; --i1) {
					for (j1 = j + 1; j1 <= j + 7; ++j1) {
						setBlockAndNotifyAdequately(world, i1, j1, k - 3, LOTRMod.brick3, 2);
					}
					for (j1 = j + 1; j1 <= j + 16; ++j1) {
						setBlockAndNotifyAdequately(world, i1, j1, k - 2, Blocks.ladder, 3);
					}
				}
				break;
			case 3:
				for (k12 = k - 1; k12 <= k + 1; ++k12) {
					setBlockAndNotifyAdequately(world, i - 5, j, k12, Blocks.double_stone_slab, 0);
					setBlockAndNotifyAdequately(world, i - 4, j, k12, Blocks.double_stone_slab, 0);
				}
				for (j13 = j + 1; j13 <= j + 2; ++j13) {
					setBlockAndNotifyAdequately(world, i - 5, j13, k - 1, LOTRMod.brick3, 2);
					setBlockAndNotifyAdequately(world, i - 5, j13, k, Blocks.air, 0);
					setBlockAndNotifyAdequately(world, i - 4, j13, k, Blocks.air, 0);
					setBlockAndNotifyAdequately(world, i - 5, j13, k + 1, LOTRMod.brick3, 2);
				}
				setBlockAndNotifyAdequately(world, i - 5, j + 3, k - 1, LOTRMod.stairsHighElvenBrick, 2);
				setBlockAndNotifyAdequately(world, i - 5, j + 3, k, LOTRMod.brick3, 2);
				setBlockAndNotifyAdequately(world, i - 5, j + 3, k + 1, LOTRMod.stairsHighElvenBrick, 3);
				for (k12 = k + 1; k12 <= k + 2; ++k12) {
					for (j1 = j + 1; j1 <= j + 7; ++j1) {
						setBlockAndNotifyAdequately(world, i + 3, j1, k12, LOTRMod.brick3, 2);
					}
					for (j1 = j + 1; j1 <= j + 16; ++j1) {
						setBlockAndNotifyAdequately(world, i + 2, j1, k12, Blocks.ladder, 4);
					}
				}
				break;
			default:
				break;
		}
		setBlockAndNotifyAdequately(world, i - 3, j + 3, k, LOTRMod.highElvenTorch, 1);
		setBlockAndNotifyAdequately(world, i + 3, j + 3, k, LOTRMod.highElvenTorch, 2);
		setBlockAndNotifyAdequately(world, i, j + 3, k - 3, LOTRMod.highElvenTorch, 3);
		setBlockAndNotifyAdequately(world, i, j + 3, k + 3, LOTRMod.highElvenTorch, 4);
		setBlockAndNotifyAdequately(world, i - 3, j + 18, k, LOTRMod.highElvenTorch, 1);
		setBlockAndNotifyAdequately(world, i + 3, j + 18, k, LOTRMod.highElvenTorch, 2);
		setBlockAndNotifyAdequately(world, i, j + 18, k - 3, LOTRMod.highElvenTorch, 3);
		setBlockAndNotifyAdequately(world, i, j + 18, k + 3, LOTRMod.highElvenTorch, 4);
		return true;
	}
}
