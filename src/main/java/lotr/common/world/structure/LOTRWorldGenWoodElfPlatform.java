package lotr.common.world.structure;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityWoodElf;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenWoodElfPlatform extends LOTRWorldGenStructureBase {
	public LOTRWorldGenWoodElfPlatform(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		int rotation = -1;
		if (restrictions) {
			rotation = random.nextInt(4);
			switch (rotation) {
				case 0: {
					k -= 3;
					break;
				}
				case 1: {
					i += 3;
					break;
				}
				case 2: {
					k += 3;
					break;
				}
				case 3: {
					i -= 3;
				}
			}
		} else if (usingPlayer != null) {
			rotation = usingPlayerRotation();
		}
		boolean flag = false;
		switch (rotation) {
			case 0: {
				flag = generateFacingSouth(world, random, i, j, k);
				break;
			}
			case 1: {
				flag = generateFacingWest(world, random, i, j, k);
				break;
			}
			case 2: {
				flag = generateFacingNorth(world, random, i, j, k);
				break;
			}
			case 3: {
				flag = generateFacingEast(world, random, i, j, k);
			}
		}
		if (flag) {
			LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
			respawner.setSpawnClass(LOTREntityWoodElf.class);
			respawner.setCheckRanges(8, -8, 8, 2);
			respawner.setSpawnRanges(3, -2, 2, 8);
			placeNPCRespawner(respawner, world, i, j + 1, k);
		}
		return false;
	}

	public boolean generateFacingEast(World world, Random random, int i, int j, int k) {
		int j1;
		int k1;
		int i1;
		if (restrictions) {
			for (k1 = k - 2; k1 <= k + 2; ++k1) {
				for (j1 = j; j1 <= j + 4; ++j1) {
					if (!world.getBlock(i + 1, j1, k1).isWood(world, i + 1, j1, k1)) {
						return false;
					}
					for (i1 = i; i1 >= i - 3; --i1) {
						if (world.isAirBlock(i1, j1, k1)) {
							continue;
						}
						return false;
					}
				}
			}
		} else {
			for (k1 = k - 2; k1 <= k + 2; ++k1) {
				for (j1 = j; j1 <= j + 4; ++j1) {
					for (i1 = i; i1 >= i - 3; --i1) {
						setBlockAndNotifyAdequately(world, i1, j1, k1, Blocks.air, 0);
					}
				}
			}
		}
		for (k1 = k - 1; k1 <= k + 1; ++k1) {
			for (int i12 = i; i12 >= i - 2; --i12) {
				setBlockAndNotifyAdequately(world, i12, j, k1, LOTRMod.planks2, 13);
			}
		}
		for (k1 = k - 2; k1 <= k + 2; ++k1) {
			setBlockAndNotifyAdequately(world, i - 3, j, k1, LOTRMod.stairsGreenOak, 4);
			setBlockAndNotifyAdequately(world, i - 3, j + 1, k1, LOTRMod.fence2, 13);
		}
		for (int i13 = i; i13 >= i - 2; --i13) {
			setBlockAndNotifyAdequately(world, i13, j, k - 2, LOTRMod.stairsGreenOak, 6);
			setBlockAndNotifyAdequately(world, i13, j, k + 2, LOTRMod.stairsGreenOak, 7);
			setBlockAndNotifyAdequately(world, i13, j + 1, k - 2, LOTRMod.fence2, 13);
			setBlockAndNotifyAdequately(world, i13, j + 1, k + 2, LOTRMod.fence2, 13);
		}
		setBlockAndNotifyAdequately(world, i, j + 2, k - 2, LOTRMod.fence2, 13);
		setBlockAndNotifyAdequately(world, i, j + 3, k - 2, LOTRMod.woodElvenTorch, 5);
		setBlockAndNotifyAdequately(world, i, j + 2, k + 2, LOTRMod.fence2, 13);
		setBlockAndNotifyAdequately(world, i, j + 3, k + 2, LOTRMod.woodElvenTorch, 5);
		setBlockAndNotifyAdequately(world, i - 3, j + 2, k - 2, LOTRMod.fence2, 13);
		setBlockAndNotifyAdequately(world, i - 3, j + 3, k - 2, LOTRMod.woodElvenTorch, 5);
		setBlockAndNotifyAdequately(world, i - 3, j + 2, k + 2, LOTRMod.fence2, 13);
		setBlockAndNotifyAdequately(world, i - 3, j + 3, k + 2, LOTRMod.woodElvenTorch, 5);
		for (int j12 = j; j12 >= 0 && LOTRMod.isOpaque(world, i + 1, j12, k) && (j12 >= j || !LOTRMod.isOpaque(world, i, j12, k)); --j12) {
			setBlockAndNotifyAdequately(world, i, j12, k, Blocks.ladder, 4);
		}
		return true;
	}

	public boolean generateFacingNorth(World world, Random random, int i, int j, int k) {
		int j1;
		int k1;
		int i1;
		if (restrictions) {
			for (i1 = i - 2; i1 <= i + 2; ++i1) {
				for (j1 = j; j1 <= j + 4; ++j1) {
					if (!world.getBlock(i1, j1, k - 1).isWood(world, i1, j1, k - 1)) {
						return false;
					}
					for (k1 = k; k1 <= k + 3; ++k1) {
						if (world.isAirBlock(i1, j1, k1)) {
							continue;
						}
						return false;
					}
				}
			}
		} else {
			for (i1 = i - 2; i1 <= i + 2; ++i1) {
				for (j1 = j; j1 <= j + 4; ++j1) {
					for (k1 = k; k1 <= k + 3; ++k1) {
						setBlockAndNotifyAdequately(world, i1, j1, k1, Blocks.air, 0);
					}
				}
			}
		}
		for (i1 = i - 1; i1 <= i + 1; ++i1) {
			for (int k12 = k; k12 <= k + 2; ++k12) {
				setBlockAndNotifyAdequately(world, i1, j, k12, LOTRMod.planks2, 13);
			}
		}
		for (i1 = i - 2; i1 <= i + 2; ++i1) {
			setBlockAndNotifyAdequately(world, i1, j, k + 3, LOTRMod.stairsGreenOak, 7);
			setBlockAndNotifyAdequately(world, i1, j + 1, k + 3, LOTRMod.fence2, 13);
		}
		for (int k13 = k; k13 <= k + 2; ++k13) {
			setBlockAndNotifyAdequately(world, i - 2, j, k13, LOTRMod.stairsGreenOak, 4);
			setBlockAndNotifyAdequately(world, i + 2, j, k13, LOTRMod.stairsGreenOak, 5);
			setBlockAndNotifyAdequately(world, i - 2, j + 1, k13, LOTRMod.fence2, 13);
			setBlockAndNotifyAdequately(world, i + 2, j + 1, k13, LOTRMod.fence2, 13);
		}
		setBlockAndNotifyAdequately(world, i - 2, j + 2, k, LOTRMod.fence2, 13);
		setBlockAndNotifyAdequately(world, i - 2, j + 3, k, LOTRMod.woodElvenTorch, 5);
		setBlockAndNotifyAdequately(world, i + 2, j + 2, k, LOTRMod.fence2, 13);
		setBlockAndNotifyAdequately(world, i + 2, j + 3, k, LOTRMod.woodElvenTorch, 5);
		setBlockAndNotifyAdequately(world, i - 2, j + 2, k + 3, LOTRMod.fence2, 13);
		setBlockAndNotifyAdequately(world, i - 2, j + 3, k + 3, LOTRMod.woodElvenTorch, 5);
		setBlockAndNotifyAdequately(world, i + 2, j + 2, k + 3, LOTRMod.fence2, 13);
		setBlockAndNotifyAdequately(world, i + 2, j + 3, k + 3, LOTRMod.woodElvenTorch, 5);
		for (int j12 = j; j12 >= 0 && LOTRMod.isOpaque(world, i, j12, k - 1) && (j12 >= j || !LOTRMod.isOpaque(world, i, j12, k)); --j12) {
			setBlockAndNotifyAdequately(world, i, j12, k, Blocks.ladder, 3);
		}
		return true;
	}

	public boolean generateFacingSouth(World world, Random random, int i, int j, int k) {
		int j1;
		int k1;
		int i1;
		if (restrictions) {
			for (i1 = i - 2; i1 <= i + 2; ++i1) {
				for (j1 = j; j1 <= j + 4; ++j1) {
					if (!world.getBlock(i1, j1, k + 1).isWood(world, i1, j1, k + 1)) {
						return false;
					}
					for (k1 = k; k1 >= k - 3; --k1) {
						if (world.isAirBlock(i1, j1, k1)) {
							continue;
						}
						return false;
					}
				}
			}
		} else {
			for (i1 = i - 2; i1 <= i + 2; ++i1) {
				for (j1 = j; j1 <= j + 4; ++j1) {
					for (k1 = k; k1 >= k - 3; --k1) {
						setBlockAndNotifyAdequately(world, i1, j1, k1, Blocks.air, 0);
					}
				}
			}
		}
		for (i1 = i - 1; i1 <= i + 1; ++i1) {
			for (int k12 = k; k12 >= k - 2; --k12) {
				setBlockAndNotifyAdequately(world, i1, j, k12, LOTRMod.planks2, 13);
			}
		}
		for (i1 = i - 2; i1 <= i + 2; ++i1) {
			setBlockAndNotifyAdequately(world, i1, j, k - 3, LOTRMod.stairsGreenOak, 6);
			setBlockAndNotifyAdequately(world, i1, j + 1, k - 3, LOTRMod.fence2, 13);
		}
		for (int k13 = k; k13 >= k - 2; --k13) {
			setBlockAndNotifyAdequately(world, i - 2, j, k13, LOTRMod.stairsGreenOak, 4);
			setBlockAndNotifyAdequately(world, i + 2, j, k13, LOTRMod.stairsGreenOak, 5);
			setBlockAndNotifyAdequately(world, i - 2, j + 1, k13, LOTRMod.fence2, 13);
			setBlockAndNotifyAdequately(world, i + 2, j + 1, k13, LOTRMod.fence2, 13);
		}
		setBlockAndNotifyAdequately(world, i - 2, j + 2, k, LOTRMod.fence2, 13);
		setBlockAndNotifyAdequately(world, i - 2, j + 3, k, LOTRMod.woodElvenTorch, 5);
		setBlockAndNotifyAdequately(world, i + 2, j + 2, k, LOTRMod.fence2, 13);
		setBlockAndNotifyAdequately(world, i + 2, j + 3, k, LOTRMod.woodElvenTorch, 5);
		setBlockAndNotifyAdequately(world, i - 2, j + 2, k - 3, LOTRMod.fence2, 13);
		setBlockAndNotifyAdequately(world, i - 2, j + 3, k - 3, LOTRMod.woodElvenTorch, 5);
		setBlockAndNotifyAdequately(world, i + 2, j + 2, k - 3, LOTRMod.fence2, 13);
		setBlockAndNotifyAdequately(world, i + 2, j + 3, k - 3, LOTRMod.woodElvenTorch, 5);
		for (int j12 = j; j12 >= 0 && LOTRMod.isOpaque(world, i, j12, k + 1) && (j12 >= j || !LOTRMod.isOpaque(world, i, j12, k)); --j12) {
			setBlockAndNotifyAdequately(world, i, j12, k, Blocks.ladder, 2);
		}
		return true;
	}

	public boolean generateFacingWest(World world, Random random, int i, int j, int k) {
		int j1;
		int k1;
		int i1;
		if (restrictions) {
			for (k1 = k - 2; k1 <= k + 2; ++k1) {
				for (j1 = j; j1 <= j + 4; ++j1) {
					if (!world.getBlock(i - 1, j1, k1).isWood(world, i - 1, j1, k1)) {
						return false;
					}
					for (i1 = i; i1 <= i + 3; ++i1) {
						if (world.isAirBlock(i1, j1, k1)) {
							continue;
						}
						return false;
					}
				}
			}
		} else {
			for (k1 = k - 2; k1 <= k + 2; ++k1) {
				for (j1 = j; j1 <= j + 4; ++j1) {
					for (i1 = i; i1 <= i + 3; ++i1) {
						setBlockAndNotifyAdequately(world, i1, j1, k1, Blocks.air, 0);
					}
				}
			}
		}
		for (k1 = k - 1; k1 <= k + 1; ++k1) {
			for (int i12 = i; i12 <= i + 2; ++i12) {
				setBlockAndNotifyAdequately(world, i12, j, k1, LOTRMod.planks2, 13);
			}
		}
		for (k1 = k - 2; k1 <= k + 2; ++k1) {
			setBlockAndNotifyAdequately(world, i + 3, j, k1, LOTRMod.stairsGreenOak, 5);
			setBlockAndNotifyAdequately(world, i + 3, j + 1, k1, LOTRMod.fence2, 13);
		}
		for (int i13 = i; i13 <= i + 2; ++i13) {
			setBlockAndNotifyAdequately(world, i13, j, k - 2, LOTRMod.stairsGreenOak, 6);
			setBlockAndNotifyAdequately(world, i13, j, k + 2, LOTRMod.stairsGreenOak, 7);
			setBlockAndNotifyAdequately(world, i13, j + 1, k - 2, LOTRMod.fence2, 13);
			setBlockAndNotifyAdequately(world, i13, j + 1, k + 2, LOTRMod.fence2, 13);
		}
		setBlockAndNotifyAdequately(world, i, j + 2, k - 2, LOTRMod.fence2, 13);
		setBlockAndNotifyAdequately(world, i, j + 3, k - 2, LOTRMod.woodElvenTorch, 5);
		setBlockAndNotifyAdequately(world, i, j + 2, k + 2, LOTRMod.fence2, 13);
		setBlockAndNotifyAdequately(world, i, j + 3, k + 2, LOTRMod.woodElvenTorch, 5);
		setBlockAndNotifyAdequately(world, i + 3, j + 2, k - 2, LOTRMod.fence2, 13);
		setBlockAndNotifyAdequately(world, i + 3, j + 3, k - 2, LOTRMod.woodElvenTorch, 5);
		setBlockAndNotifyAdequately(world, i + 3, j + 2, k + 2, LOTRMod.fence2, 13);
		setBlockAndNotifyAdequately(world, i + 3, j + 3, k + 2, LOTRMod.woodElvenTorch, 5);
		for (int j12 = j; j12 >= 0 && LOTRMod.isOpaque(world, i - 1, j12, k) && (j12 >= j || !LOTRMod.isOpaque(world, i, j12, k)); --j12) {
			setBlockAndNotifyAdequately(world, i, j12, k, Blocks.ladder, 5);
		}
		return true;
	}
}
