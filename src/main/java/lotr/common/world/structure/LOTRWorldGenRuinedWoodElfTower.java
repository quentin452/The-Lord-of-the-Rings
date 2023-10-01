package lotr.common.world.structure;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityMirkwoodSpider;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.feature.LOTRWorldGenWebOfUngoliant;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenRuinedWoodElfTower extends LOTRWorldGenStructureBase {
	public LOTRWorldGenRuinedWoodElfTower(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		int j1;
		int k1;
		int j12;
		int k12;
		int i1;
		if (restrictions && (world.getBlock(i, j - 1, k) != Blocks.grass || world.getBiomeGenForCoords(i, k) != LOTRBiome.mirkwoodCorrupted)) {
			return false;
		}
		--j;
		int rotation = random.nextInt(4);
		int radius = 6;
		int radiusPlusOne = radius + 1;
		if (!restrictions && usingPlayer != null) {
			rotation = usingPlayerRotation();
		}
		switch (rotation) {
			case 0: {
				k += radiusPlusOne;
				break;
			}
			case 1: {
				i -= radiusPlusOne;
				break;
			}
			case 2: {
				k -= radiusPlusOne;
				break;
			}
			case 3: {
				i += radiusPlusOne;
			}
		}
		if (restrictions) {
			for (int i12 = i - radiusPlusOne; i12 <= i + radiusPlusOne; ++i12) {
				for (int k13 = k - radiusPlusOne; k13 <= k + radiusPlusOne; ++k13) {
					Block block;
					int j13;
					int i2 = i12 - i;
					int k2 = k13 - k;
					if (i2 * i2 + k2 * k2 > radiusPlusOne * radiusPlusOne || (block = world.getBlock(i12, j13 = world.getTopSolidOrLiquidBlock(i12, k13) - 1, k13)) == Blocks.grass || block == Blocks.dirt || block == Blocks.stone || block.isWood(world, i12, j13, k13) || block.isLeaves(world, i12, j13, k13)) {
						continue;
					}
					return false;
				}
			}
		}
		int sections = 3 + random.nextInt(3);
		int sectionHeight = 8;
		int topHeight = j + sections * sectionHeight;
		int wallThresholdMin = radius * radius;
		int wallThresholdMax = radiusPlusOne * radiusPlusOne;
		for (i1 = i - radius; i1 <= i + radius; ++i1) {
			for (k12 = k - radius; k12 <= k + radius; ++k12) {
				int start;
				int i2 = i1 - i;
				int k2 = k12 - k;
				int distSq = i2 * i2 + k2 * k2;
				if (distSq >= wallThresholdMax) {
					continue;
				}
				for (int j14 = start = j - sectionHeight; (j14 == start || !LOTRMod.isOpaque(world, i1, j14, k12)) && j14 >= 0; --j14) {
					if (j14 != start || distSq >= wallThresholdMin) {
						placeRandomStoneBrick(world, random, i1, j14, k12);
					} else {
						placeDungeonBlock(world, random, i1, j14, k12);
					}
					setGrassToDirt(world, i1, j14 - 1, k12);
				}
			}
		}
		for (int l = -1; l < sections; ++l) {
			int i13;
			int sectionBase = j + l * sectionHeight;
			for (j1 = sectionBase + 1; j1 <= sectionBase + sectionHeight; ++j1) {
				for (i13 = i - radius; i13 <= i + radius; ++i13) {
					for (int k14 = k - radius; k14 <= k + radius; ++k14) {
						int i2 = i13 - i;
						int k2 = k14 - k;
						int distSq = i2 * i2 + k2 * k2;
						if (distSq >= wallThresholdMax) {
							continue;
						}
						if (distSq >= wallThresholdMin) {
							placeRandomStoneBrick(world, random, i13, j1, k14);
							if (l == sections - 1 && j1 == sectionBase + sectionHeight) {
								placeRandomStoneBrick(world, random, i13, j1 + 1, k14);
								placeRandomStoneSlab(world, random, i13, j1 + 2, k14, false);
							}
						} else if (j1 == sectionBase + sectionHeight && (Math.abs(i2) > 2 || Math.abs(k2) > 2)) {
							placeDungeonBlock(world, random, i13, j1, k14);
						} else {
							setBlockAndNotifyAdequately(world, i13, j1, k14, Blocks.air, 0);
						}
						setGrassToDirt(world, i13, j1 - 1, k14);
					}
				}
				placeDungeonBlock(world, random, i, j1, k);
			}
			for (int l1 = 0; l1 < 2; ++l1) {
				int stairBase = sectionBase + l1 * 4;
				placeRandomStoneSlab(world, random, i, stairBase + 1, k + 1, false);
				placeRandomStoneSlab(world, random, i, stairBase + 1, k + 2, false);
				placeRandomStoneSlab(world, random, i + 1, stairBase + 2, k, false);
				placeRandomStoneSlab(world, random, i + 2, stairBase + 2, k, false);
				placeRandomStoneSlab(world, random, i, stairBase + 3, k - 1, false);
				placeRandomStoneSlab(world, random, i, stairBase + 3, k - 2, false);
				placeRandomStoneSlab(world, random, i - 1, stairBase + 4, k, false);
				placeRandomStoneSlab(world, random, i - 2, stairBase + 4, k, false);
				for (int i14 = 0; i14 <= 1; ++i14) {
					for (int k15 = 0; k15 <= 1; ++k15) {
						placeRandomStoneSlab(world, random, i + 1 + i14, stairBase + 1, k + 1 + k15, true);
						placeRandomStoneSlab(world, random, i + 1 + i14, stairBase + 2, k - 2 + k15, true);
						placeRandomStoneSlab(world, random, i - 2 + i14, stairBase + 3, k - 2 + k15, true);
						placeRandomStoneSlab(world, random, i - 2 + i14, stairBase + 4, k + 1 + k15, true);
					}
				}
			}
			if (l <= 0) {
				continue;
			}
			for (j1 = sectionBase + 1; j1 <= sectionBase + 4; ++j1) {
				for (i13 = i - 1; i13 <= i + 1; ++i13) {
					setBlockAndNotifyAdequately(world, i13, j1, k - 6, Blocks.air, 0);
					setBlockAndNotifyAdequately(world, i13, j1, k + 6, Blocks.air, 0);
				}
				for (k1 = k - 1; k1 <= k + 1; ++k1) {
					setBlockAndNotifyAdequately(world, i - 6, j1, k1, Blocks.air, 0);
					setBlockAndNotifyAdequately(world, i + 6, j1, k1, Blocks.air, 0);
				}
			}
			placeRandomStoneStairs(world, random, i - 1, sectionBase + 4, k - 6, 5);
			placeRandomStoneStairs(world, random, i + 1, sectionBase + 4, k - 6, 4);
			placeRandomStoneStairs(world, random, i - 1, sectionBase + 4, k + 6, 5);
			placeRandomStoneStairs(world, random, i + 1, sectionBase + 4, k + 6, 4);
			placeRandomStoneStairs(world, random, i - 6, sectionBase + 4, k - 1, 7);
			placeRandomStoneStairs(world, random, i - 6, sectionBase + 4, k + 1, 6);
			placeRandomStoneStairs(world, random, i + 6, sectionBase + 4, k - 1, 7);
			placeRandomStoneStairs(world, random, i + 6, sectionBase + 4, k + 1, 6);
		}
		for (j12 = topHeight + 2; j12 <= topHeight + 3; ++j12) {
			placeRandomStoneBrick(world, random, i + 6, j12, k - 3);
			placeRandomStoneBrick(world, random, i + 6, j12, k);
			placeRandomStoneBrick(world, random, i + 6, j12, k + 3);
			placeRandomStoneBrick(world, random, i - 3, j12, k + 6);
			placeRandomStoneBrick(world, random, i, j12, k + 6);
			placeRandomStoneBrick(world, random, i + 3, j12, k + 6);
			placeRandomStoneBrick(world, random, i - 6, j12, k - 3);
			placeRandomStoneBrick(world, random, i - 6, j12, k);
			placeRandomStoneBrick(world, random, i - 6, j12, k + 3);
			placeRandomStoneBrick(world, random, i - 3, j12, k - 6);
			placeRandomStoneBrick(world, random, i, j12, k - 6);
			placeRandomStoneBrick(world, random, i + 3, j12, k - 6);
		}
		placeRandomStoneBrick(world, random, i + 6, topHeight + 2, k - 2);
		placeRandomStoneBrick(world, random, i + 6, topHeight + 2, k + 2);
		placeRandomStoneBrick(world, random, i - 2, topHeight + 2, k + 6);
		placeRandomStoneBrick(world, random, i + 2, topHeight + 2, k + 6);
		placeRandomStoneBrick(world, random, i - 6, topHeight + 2, k - 2);
		placeRandomStoneBrick(world, random, i - 6, topHeight + 2, k + 2);
		placeRandomStoneBrick(world, random, i - 2, topHeight + 2, k - 6);
		placeRandomStoneBrick(world, random, i + 2, topHeight + 2, k - 6);
		for (j12 = j - sectionHeight - 6; j12 <= j - sectionHeight - 1; ++j12) {
			placeDungeonBlock(world, random, i - 6, j12, k);
			placeDungeonBlock(world, random, i - 5, j12, k - 2);
			placeDungeonBlock(world, random, i - 5, j12, k - 1);
			placeDungeonBlock(world, random, i - 5, j12, k + 1);
			placeDungeonBlock(world, random, i - 5, j12, k + 2);
			placeDungeonBlock(world, random, i - 4, j12, k - 3);
			placeDungeonBlock(world, random, i - 4, j12, k + 3);
			placeDungeonBlock(world, random, i - 3, j12, k - 5);
			placeDungeonBlock(world, random, i - 3, j12, k - 4);
			placeDungeonBlock(world, random, i - 3, j12, k + 4);
			placeDungeonBlock(world, random, i - 3, j12, k + 5);
			placeDungeonBlock(world, random, i - 2, j12, k - 6);
			placeDungeonBlock(world, random, i - 2, j12, k + 6);
			placeDungeonBlock(world, random, i - 1, j12, k - 6);
			placeDungeonBlock(world, random, i - 1, j12, k + 6);
			placeDungeonBlock(world, random, i, j12, k - 6);
			placeDungeonBlock(world, random, i, j12, k + 6);
			placeDungeonBlock(world, random, i + 1, j12, k - 5);
			placeDungeonBlock(world, random, i + 1, j12, k - 4);
			placeDungeonBlock(world, random, i + 1, j12, k + 4);
			placeDungeonBlock(world, random, i + 1, j12, k + 5);
			placeDungeonBlock(world, random, i + 2, j12, k - 3);
			placeDungeonBlock(world, random, i + 2, j12, k + 3);
			placeDungeonBlock(world, random, i + 3, j12, k - 2);
			placeDungeonBlock(world, random, i + 3, j12, k + 2);
			placeDungeonBlock(world, random, i + 4, j12, k - 2);
			placeDungeonBlock(world, random, i + 4, j12, k + 2);
			placeDungeonBlock(world, random, i + 5, j12, k - 1);
			placeDungeonBlock(world, random, i + 5, j12, k);
			placeDungeonBlock(world, random, i + 5, j12, k + 1);
			if (j12 == j - sectionHeight - 6 || j12 == j - sectionHeight - 1) {
				placeDungeonBlock(world, random, i - 5, j12, k);
				for (k12 = k - 2; k12 <= k + 2; ++k12) {
					placeDungeonBlock(world, random, i - 4, j12, k12);
				}
				for (k12 = k - 3; k12 <= k + 3; ++k12) {
					placeDungeonBlock(world, random, i - 3, j12, k12);
				}
				for (k12 = k - 5; k12 <= k + 5; ++k12) {
					placeDungeonBlock(world, random, i - 2, j12, k12);
					placeDungeonBlock(world, random, i - 1, j12, k12);
					placeDungeonBlock(world, random, i, j12, k12);
				}
				for (k12 = k - 3; k12 <= k + 3; ++k12) {
					placeDungeonBlock(world, random, i + 1, j12, k12);
				}
				for (k12 = k - 2; k12 <= k + 2; ++k12) {
					placeDungeonBlock(world, random, i + 2, j12, k12);
				}
				for (k12 = k - 1; k12 <= k + 1; ++k12) {
					placeDungeonBlock(world, random, i + 3, j12, k12);
					placeDungeonBlock(world, random, i + 4, j12, k12);
				}
				continue;
			}
			setBlockAndNotifyAdequately(world, i - 5, j12, k, Blocks.air, 0);
			for (k12 = k - 2; k12 <= k + 2; ++k12) {
				setBlockAndNotifyAdequately(world, i - 4, j12, k12, Blocks.air, 0);
			}
			for (k12 = k - 3; k12 <= k + 3; ++k12) {
				setBlockAndNotifyAdequately(world, i - 3, j12, k12, Blocks.air, 0);
			}
			for (k12 = k - 5; k12 <= k + 5; ++k12) {
				setBlockAndNotifyAdequately(world, i - 2, j12, k12, Blocks.air, 0);
				setBlockAndNotifyAdequately(world, i - 1, j12, k12, Blocks.air, 0);
				setBlockAndNotifyAdequately(world, i, j12, k12, Blocks.air, 0);
			}
			for (k12 = k - 3; k12 <= k + 3; ++k12) {
				setBlockAndNotifyAdequately(world, i + 1, j12, k12, Blocks.air, 0);
			}
			for (k12 = k - 2; k12 <= k + 2; ++k12) {
				setBlockAndNotifyAdequately(world, i + 2, j12, k12, Blocks.air, 0);
			}
			for (k12 = k - 1; k12 <= k + 1; ++k12) {
				setBlockAndNotifyAdequately(world, i + 3, j12, k12, Blocks.air, 0);
				setBlockAndNotifyAdequately(world, i + 4, j12, k12, Blocks.air, 0);
			}
		}
		for (i1 = i - 2; i1 <= i; ++i1) {
			placeDungeonBlock(world, random, i1, j - sectionHeight - 2, k - 5);
			placeDungeonBlock(world, random, i1, j - sectionHeight - 2, k - 4);
			placeDungeonBlock(world, random, i1, j - sectionHeight - 2, k + 4);
			placeDungeonBlock(world, random, i1, j - sectionHeight - 2, k + 5);
		}
		for (int k16 = k - 1; k16 <= k + 1; ++k16) {
			placeDungeonBlock(world, random, i + 3, j - sectionHeight - 2, k16);
			placeDungeonBlock(world, random, i + 4, j - sectionHeight - 2, k16);
		}
		for (j12 = j - sectionHeight - 5; j12 <= j - sectionHeight - 3; ++j12) {
			for (int i15 = i - 2; i15 <= i; ++i15) {
				if (random.nextInt(4) == 0) {
					setBlockAndNotifyAdequately(world, i15, j12, k - 4, LOTRMod.woodElfBars, 0);
				}
				if (random.nextInt(4) != 0) {
					continue;
				}
				setBlockAndNotifyAdequately(world, i15, j12, k + 4, LOTRMod.woodElfBars, 0);
			}
			for (k12 = k - 1; k12 <= k + 1; ++k12) {
				if (random.nextInt(4) != 0) {
					continue;
				}
				setBlockAndNotifyAdequately(world, i + 3, j12, k12, LOTRMod.woodElfBars, 0);
			}
		}
		placeSkull(world, random, i - 2, j - sectionHeight - 5, k - 5, 3, 1);
		placeSkull(world, random, i + 2, j - sectionHeight - 5, k + 5, 3, 1);
		placeSkull(world, random, i + 4, j - sectionHeight - 5, k - 1, 1, 3);
		for (int spiders = net.minecraft.util.MathHelper.getRandomIntegerInRange(random, 4, 6); spiders > 0; --spiders) {
			LOTREntityMirkwoodSpider spider = new LOTREntityMirkwoodSpider(world);
			spider.setLocationAndAngles(i - 1 + 0.5, j - sectionHeight - 5, k + 0.5, 0.0f, 0.0f);
			spider.setHomeArea(i, j + 1, k, 8);
			spider.onSpawnWithEgg(null);
			spider.isNPCPersistent = true;
			world.spawnEntityInWorld(spider);
		}
		new LOTRWorldGenWebOfUngoliant(notifyChanges, 24).generate(world, random, i - 1, j - sectionHeight - 5, k);
		placeDungeonBlock(world, random, i + 4, j - sectionHeight - 5, k);
		setBlockAndNotifyAdequately(world, i + 4, j - sectionHeight - 5, k - 1, Blocks.chest, 0);
		LOTRChestContents.fillChest(world, random, i + 4, j - sectionHeight - 5, k - 1, LOTRChestContents.MIRKWOOD_LOOT);
		setBlockAndNotifyAdequately(world, i + 4, j - sectionHeight - 5, k + 1, Blocks.chest, 0);
		LOTRChestContents.fillChest(world, random, i + 4, j - sectionHeight - 5, k + 1, LOTRChestContents.MIRKWOOD_LOOT);
		setBlockAndNotifyAdequately(world, i - 5, j - sectionHeight - 1, k, Blocks.air, 0);
		setBlockAndNotifyAdequately(world, i - 5, j - sectionHeight, k, Blocks.air, 0);
		switch (rotation) {
			case 0: {
				int i16;
				k1 = k - radius;
				for (i16 = i - 1; i16 <= i + 1; ++i16) {
					for (j1 = j + 1; j1 <= j + 3; ++j1) {
						setAir(world, i16, j1, k1);
					}
				}
				break;
			}
			case 1: {
				int i16 = i + radius;
				for (k1 = k - 1; k1 <= k + 1; ++k1) {
					for (j1 = j + 1; j1 <= j + 3; ++j1) {
						setAir(world, i16, j1, k1);
					}
				}
				break;
			}
			case 2: {
				int i16;
				k1 = k + radius;
				for (i16 = i - 1; i16 <= i + 1; ++i16) {
					for (j1 = j + 1; j1 <= j + 3; ++j1) {
						setAir(world, i16, j1, k1);
					}
				}
				break;
			}
			case 3: {
				int i16 = i - radius;
				for (k1 = k - 1; k1 <= k + 1; ++k1) {
					for (j1 = j + 1; j1 <= j + 3; ++j1) {
						setAir(world, i16, j1, k1);
					}
				}
				break;
			}
		}
		return true;
	}

	public void placeDungeonBlock(World world, Random random, int i, int j, int k) {
		int l = random.nextInt(3);
		switch (l) {
			case 0: {
				setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.brick3, 5);
				break;
			}
			case 1: {
				setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.brick3, 6);
				break;
			}
			case 2: {
				setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.brick3, 7);
			}
		}
	}

	public void placeRandomStoneBrick(World world, Random random, int i, int j, int k) {
		if (random.nextInt(20) == 0) {
			return;
		}
		int l = random.nextInt(3);
		switch (l) {
			case 0: {
				setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.brick3, 5);
				break;
			}
			case 1: {
				setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.brick3, 6);
				break;
			}
			case 2: {
				setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.brick3, 7);
			}
		}
	}

	public void placeRandomStoneSlab(World world, Random random, int i, int j, int k, boolean inverted) {
		if (random.nextInt(8) == 0) {
			return;
		}
		setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.slabSingle6, 2 + random.nextInt(3) | (inverted ? 8 : 0));
	}

	public void placeRandomStoneStairs(World world, Random random, int i, int j, int k, int meta) {
		if (random.nextInt(8) == 0) {
			return;
		}
		int l = random.nextInt(3);
		switch (l) {
			case 0: {
				setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.stairsWoodElvenBrick, meta);
				break;
			}
			case 1: {
				setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.stairsWoodElvenBrickMossy, meta);
				break;
			}
			case 2: {
				setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.stairsWoodElvenBrickCracked, meta);
			}
		}
	}

	public void placeSkull(World world, Random random, int i, int j, int k, int xRange, int zRange) {
		i += random.nextInt(xRange);
		k += random.nextInt(zRange);
		if (random.nextBoolean()) {
			placeSkull(world, random, i, j, k);
		}
	}
}
