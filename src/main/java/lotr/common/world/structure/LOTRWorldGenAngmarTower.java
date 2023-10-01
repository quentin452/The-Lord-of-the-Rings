package lotr.common.world.structure;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityAngmarOrcMercenaryCaptain;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.biome.LOTRBiomeGenAngmar;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenAngmarTower extends LOTRWorldGenStructureBase {
	public LOTRWorldGenAngmarTower(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		int j1;
		int k1;
		int i1;
		int k12;
		int j12;
		if (restrictions) {
			if (!(world.getBiomeGenForCoords(i, k) instanceof LOTRBiomeGenAngmar)) {
				return false;
			}
			Block l = world.getBlock(i, j - 1, k);
			if (l != Blocks.grass && l != Blocks.dirt && l != Blocks.stone) {
				return false;
			}
		}
		--j;
		int rotation = random.nextInt(4);
		if (!restrictions && usingPlayer != null) {
			rotation = usingPlayerRotation();
		}
		switch (rotation) {
			case 0: {
				k += 7;
				break;
			}
			case 1: {
				i -= 7;
				break;
			}
			case 2: {
				k -= 7;
				break;
			}
			case 3: {
				i += 7;
			}
		}
		int sections = 2 + random.nextInt(3);
		if (restrictions) {
			for (int i12 = i - 7; i12 <= i + 7; ++i12) {
				for (k12 = k - 7; k12 <= k + 7; ++k12) {
					j1 = world.getHeightValue(i12, k12) - 1;
					Block block = world.getBlock(i12, j1, k12);
					if (block == Blocks.grass || block == Blocks.stone || block == Blocks.dirt || block.isWood(world, i12, j1, k12) || block.isLeaves(world, i12, j1, k12)) {
						continue;
					}
					return false;
				}
			}
		}
		for (k1 = k - 2; k1 <= k + 2; ++k1) {
			for (j12 = j; !LOTRMod.isOpaque(world, i - 6, j12, k1) && j12 >= 0; --j12) {
				setBlockAndNotifyAdequately(world, i - 6, j12, k1, LOTRMod.brick2, 0);
				setGrassToDirt(world, i - 6, j12 - 1, k1);
			}
			for (j12 = j; !LOTRMod.isOpaque(world, i + 6, j12, k1) && j12 >= 0; --j12) {
				setBlockAndNotifyAdequately(world, i + 6, j12, k1, LOTRMod.brick2, 0);
				setGrassToDirt(world, i + 6, j12 - 1, k1);
			}
		}
		for (k1 = k - 4; k1 <= k + 4; ++k1) {
			for (j12 = j; !LOTRMod.isOpaque(world, i - 5, j12, k1) && j12 >= 0; --j12) {
				setBlockAndNotifyAdequately(world, i - 5, j12, k1, LOTRMod.brick2, 0);
				setGrassToDirt(world, i - 5, j12 - 1, k1);
			}
			for (j12 = j; !LOTRMod.isOpaque(world, i + 5, j12, k1) && j12 >= 0; --j12) {
				setBlockAndNotifyAdequately(world, i + 5, j12, k1, LOTRMod.brick2, 0);
				setGrassToDirt(world, i + 5, j12 - 1, k1);
			}
		}
		for (k1 = k - 5; k1 <= k + 5; ++k1) {
			for (i1 = i - 4; i1 <= i - 3; ++i1) {
				for (j1 = j; !LOTRMod.isOpaque(world, i1, j1, k1) && j1 >= 0; --j1) {
					setBlockAndNotifyAdequately(world, i1, j1, k1, LOTRMod.brick2, 0);
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
			}
			for (i1 = i + 3; i1 <= i + 4; ++i1) {
				for (j1 = j; !LOTRMod.isOpaque(world, i1, j1, k1) && j1 >= 0; --j1) {
					setBlockAndNotifyAdequately(world, i1, j1, k1, LOTRMod.brick2, 0);
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
			}
		}
		for (k1 = k - 6; k1 <= k + 6; ++k1) {
			for (i1 = i - 2; i1 <= i + 2; ++i1) {
				for (j1 = j; !LOTRMod.isOpaque(world, i1, j1, k1) && j1 >= 0; --j1) {
					setBlockAndNotifyAdequately(world, i1, j1, k1, LOTRMod.brick2, 0);
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
			}
		}
		for (int l = 0; l <= sections; ++l) {
			generateTowerSection(world, random, i, j, k, l, false);
		}
		generateTowerSection(world, random, i, j, k, sections + 1, true);
		LOTREntityAngmarOrcMercenaryCaptain trader = new LOTREntityAngmarOrcMercenaryCaptain(world);
		trader.setLocationAndAngles(i - 2 + 0.5, j + (sections + 1) * 8 + 1, k + 0.5, world.rand.nextFloat() * 360.0f, 0.0f);
		trader.onSpawnWithEgg(null);
		trader.setHomeArea(i, j + (sections + 1) * 8, k, 24);
		world.spawnEntityInWorld(trader);
		switch (rotation) {
			case 0: {
				for (i1 = i - 1; i1 <= i + 1; ++i1) {
					setBlockAndNotifyAdequately(world, i1, j, k - 6, Blocks.stonebrick, 0);
					for (j1 = j + 1; j1 <= j + 4; ++j1) {
						setBlockAndNotifyAdequately(world, i1, j1, k - 6, Blocks.air, 0);
					}
				}
				setBlockAndNotifyAdequately(world, i, j + 7, k - 6, LOTRMod.brick2, 0);
				placeWallBanner(world, i, j + 7, k - 6, 2, LOTRItemBanner.BannerType.ANGMAR);
				break;
			}
			case 1: {
				for (k12 = k - 1; k12 <= k + 1; ++k12) {
					setBlockAndNotifyAdequately(world, i + 6, j, k12, Blocks.stonebrick, 0);
					for (j1 = j + 1; j1 <= j + 4; ++j1) {
						setBlockAndNotifyAdequately(world, i + 6, j1, k12, Blocks.air, 0);
					}
				}
				setBlockAndNotifyAdequately(world, i + 6, j + 7, k, LOTRMod.brick2, 0);
				placeWallBanner(world, i + 6, j + 7, k, 3, LOTRItemBanner.BannerType.ANGMAR);
				break;
			}
			case 2: {
				for (i1 = i - 1; i1 <= i + 1; ++i1) {
					setBlockAndNotifyAdequately(world, i1, j, k + 6, Blocks.stonebrick, 0);
					for (j1 = j + 1; j1 <= j + 4; ++j1) {
						setBlockAndNotifyAdequately(world, i1, j1, k + 6, Blocks.air, 0);
					}
				}
				setBlockAndNotifyAdequately(world, i, j + 7, k + 6, LOTRMod.brick2, 0);
				placeWallBanner(world, i, j + 7, k + 6, 0, LOTRItemBanner.BannerType.ANGMAR);
				break;
			}
			case 3: {
				for (k12 = k - 1; k12 <= k + 1; ++k12) {
					setBlockAndNotifyAdequately(world, i - 6, j, k12, Blocks.stonebrick, 0);
					for (j1 = j + 1; j1 <= j + 4; ++j1) {
						setBlockAndNotifyAdequately(world, i - 6, j1, k12, Blocks.air, 0);
					}
				}
				setBlockAndNotifyAdequately(world, i - 6, j + 7, k, LOTRMod.brick2, 0);
				placeWallBanner(world, i - 6, j + 7, k, 1, LOTRItemBanner.BannerType.ANGMAR);
			}
		}
		int radius = 6;
		for (int l = 0; l < 16; ++l) {
			int k13;
			int j13;
			int i13 = i - random.nextInt(radius * 2) + random.nextInt(radius * 2);
			Block id = world.getBlock(i13, (j13 = world.getHeightValue(i13, k13 = k - random.nextInt(radius * 2) + random.nextInt(radius * 2))) - 1, k13);
			if (id != Blocks.grass && id != Blocks.dirt && id != Blocks.stone) {
				continue;
			}
			int randomFeature = random.nextInt(4);
			boolean flag = true;
			if (randomFeature == 0) {
				if (!LOTRMod.isOpaque(world, i13, j13, k13)) {
					if (random.nextInt(3) == 0) {
						setBlockAndNotifyAdequately(world, i13, j13, k13, LOTRMod.slabSingle3, 4);
					} else {
						setBlockAndNotifyAdequately(world, i13, j13, k13, LOTRMod.slabSingle3, 3);
					}
				}
			} else {
				int j2;
				for (j2 = j13; j2 < j13 + randomFeature && flag; ++j2) {
					flag = !LOTRMod.isOpaque(world, i13, j2, k13);
				}
				if (flag) {
					for (j2 = j13; j2 < j13 + randomFeature; ++j2) {
						if (random.nextBoolean()) {
							setBlockAndNotifyAdequately(world, i13, j2, k13, LOTRMod.brick2, 0);
							continue;
						}
						setBlockAndNotifyAdequately(world, i13, j2, k13, LOTRMod.brick2, 1);
					}
				}
			}
			if (world.getBlock(i13, j13 - 1, k13) != Blocks.dirt) {
				continue;
			}
			setBlockAndNotifyAdequately(world, i13, j13 - 1, k13, Blocks.dirt, 0);
		}
		return true;
	}

	public void generateTowerSection(World world, Random random, int i, int j, int k, int section, boolean isTop) {
		int j1;
		int i1;
		for (j1 = section == 0 ? j : (j += section * 8) + 1; j1 <= (isTop ? j + 10 : j + 8); ++j1) {
			int k1;
			int i12;
			Block fillBlock;
			int fillMeta = 0;
			if (j1 == j) {
				fillBlock = Blocks.stonebrick;
			} else {
				fillBlock = Blocks.air;
			}
			boolean hasCeiling = j1 == j + 8 && !isTop;
			for (k1 = k - 2; k1 <= k + 2; ++k1) {
				setBlockAndNotifyAdequately(world, i - 5, j1, k1, fillBlock, fillMeta);
				setBlockAndNotifyAdequately(world, i + 5, j1, k1, fillBlock, fillMeta);
				if (hasCeiling && random.nextInt(20) != 0) {
					setBlockAndNotifyAdequately(world, i - 5, j1, k1, LOTRMod.slabSingle3, 11);
				}
				if (!hasCeiling || random.nextInt(20) == 0) {
					continue;
				}
				setBlockAndNotifyAdequately(world, i + 5, j1, k1, LOTRMod.slabSingle3, 11);
			}
			for (k1 = k - 4; k1 <= k + 4; ++k1) {
				for (i12 = i - 4; i12 <= i - 3; ++i12) {
					setBlockAndNotifyAdequately(world, i12, j1, k1, fillBlock, fillMeta);
					if (!hasCeiling || random.nextInt(20) == 0) {
						continue;
					}
					setBlockAndNotifyAdequately(world, i12, j1, k1, LOTRMod.slabSingle3, 11);
				}
				for (i12 = i + 3; i12 <= i + 4; ++i12) {
					setBlockAndNotifyAdequately(world, i12, j1, k1, fillBlock, fillMeta);
					if (!hasCeiling || random.nextInt(20) == 0) {
						continue;
					}
					setBlockAndNotifyAdequately(world, i12, j1, k1, LOTRMod.slabSingle3, 11);
				}
			}
			for (k1 = k - 5; k1 <= k + 5; ++k1) {
				for (i12 = i - 2; i12 <= i + 2; ++i12) {
					setBlockAndNotifyAdequately(world, i12, j1, k1, fillBlock, fillMeta);
					if (!hasCeiling || random.nextInt(20) == 0) {
						continue;
					}
					setBlockAndNotifyAdequately(world, i12, j1, k1, LOTRMod.slabSingle3, 11);
				}
			}
		}
		for (j1 = j + 1; j1 <= (isTop ? j + 1 : j + 8); ++j1) {
			for (int k1 = k - 2; k1 <= k + 2; ++k1) {
				placeRandomBrick(world, random, i - 6, j1, k1);
				placeRandomBrick(world, random, i + 6, j1, k1);
			}
			for (int i13 = i - 2; i13 <= i + 2; ++i13) {
				placeRandomBrick(world, random, i13, j1, k - 6);
				placeRandomBrick(world, random, i13, j1, k + 6);
			}
			placeRandomBrick(world, random, i - 5, j1, k - 4);
			placeRandomBrick(world, random, i - 5, j1, k - 3);
			placeRandomBrick(world, random, i - 5, j1, k + 3);
			placeRandomBrick(world, random, i - 5, j1, k + 4);
			placeRandomBrick(world, random, i - 4, j1, k - 5);
			placeRandomBrick(world, random, i - 4, j1, k + 5);
			placeRandomBrick(world, random, i - 3, j1, k - 5);
			placeRandomBrick(world, random, i - 3, j1, k + 5);
			placeRandomBrick(world, random, i + 3, j1, k - 5);
			placeRandomBrick(world, random, i + 3, j1, k + 5);
			placeRandomBrick(world, random, i + 4, j1, k - 5);
			placeRandomBrick(world, random, i + 4, j1, k + 5);
			placeRandomBrick(world, random, i + 5, j1, k - 4);
			placeRandomBrick(world, random, i + 5, j1, k - 3);
			placeRandomBrick(world, random, i + 5, j1, k + 3);
			placeRandomBrick(world, random, i + 5, j1, k + 4);
		}
		if (!isTop) {
			for (j1 = j + 2; j1 <= j + 4; ++j1) {
				for (int k1 = k - 1; k1 <= k + 1; ++k1) {
					if (random.nextInt(3) == 0) {
						setBlockAndNotifyAdequately(world, i - 6, j1, k1, Blocks.air, 0);
					} else {
						setBlockAndNotifyAdequately(world, i - 6, j1, k1, LOTRMod.orcSteelBars, 0);
					}
					if (random.nextInt(3) != 0) {
						setBlockAndNotifyAdequately(world, i + 6, j1, k1, LOTRMod.orcSteelBars, 0);
						continue;
					}
					setBlockAndNotifyAdequately(world, i + 6, j1, k1, Blocks.air, 0);
				}
				for (int i14 = i - 1; i14 <= i + 1; ++i14) {
					if (random.nextInt(3) == 0) {
						setBlockAndNotifyAdequately(world, i14, j1, k - 6, Blocks.air, 0);
					} else {
						setBlockAndNotifyAdequately(world, i14, j1, k - 6, LOTRMod.orcSteelBars, 0);
					}
					if (random.nextInt(3) != 0) {
						setBlockAndNotifyAdequately(world, i14, j1, k + 6, LOTRMod.orcSteelBars, 0);
						continue;
					}
					setBlockAndNotifyAdequately(world, i14, j1, k + 6, Blocks.air, 0);
				}
			}
			for (i1 = i - 2; i1 <= i + 2; ++i1) {
				for (int k1 = k - 2; k1 <= k + 2; ++k1) {
					setBlockAndNotifyAdequately(world, i1, j + 8, k1, Blocks.air, 0);
				}
			}
			setBlockAndNotifyAdequately(world, i - 2, j + 1, k + 1, LOTRMod.slabSingle3, 3);
			setBlockAndNotifyAdequately(world, i - 2, j + 1, k + 2, LOTRMod.slabSingle3, 11);
			setBlockAndNotifyAdequately(world, i - 1, j + 2, k + 2, LOTRMod.slabSingle3, 3);
			setBlockAndNotifyAdequately(world, i, j + 2, k + 2, LOTRMod.slabSingle3, 11);
			setBlockAndNotifyAdequately(world, i + 1, j + 3, k + 2, LOTRMod.slabSingle3, 3);
			setBlockAndNotifyAdequately(world, i + 2, j + 3, k + 2, LOTRMod.slabSingle3, 11);
			setBlockAndNotifyAdequately(world, i + 2, j + 4, k + 1, LOTRMod.slabSingle3, 3);
			setBlockAndNotifyAdequately(world, i + 2, j + 4, k, LOTRMod.slabSingle3, 11);
			setBlockAndNotifyAdequately(world, i + 2, j + 5, k - 1, LOTRMod.slabSingle3, 3);
			setBlockAndNotifyAdequately(world, i + 2, j + 5, k - 2, LOTRMod.slabSingle3, 11);
			setBlockAndNotifyAdequately(world, i + 1, j + 6, k - 2, LOTRMod.slabSingle3, 3);
			setBlockAndNotifyAdequately(world, i, j + 6, k - 2, LOTRMod.slabSingle3, 11);
			setBlockAndNotifyAdequately(world, i - 1, j + 7, k - 2, LOTRMod.slabSingle3, 3);
			setBlockAndNotifyAdequately(world, i - 2, j + 7, k - 2, LOTRMod.slabSingle3, 11);
			setBlockAndNotifyAdequately(world, i - 2, j + 8, k - 1, LOTRMod.slabSingle3, 3);
			setBlockAndNotifyAdequately(world, i - 2, j + 8, k, LOTRMod.slabSingle3, 11);
		}
		for (i1 = i - 1; i1 <= i + 1; ++i1) {
			for (int k1 = k - 1; k1 <= k + 1; ++k1) {
				for (int j12 = j + 1; j12 <= (isTop ? j + 3 : j + 8); ++j12) {
					placeRandomBrick(world, random, i1, j12, k1);
				}
			}
		}
		if (isTop) {
			int j13;
			int top = 4 + random.nextInt(5);
			for (j13 = j + 1; j13 <= j + top; ++j13) {
				for (int k1 = k - 1; k1 <= k + 1; ++k1) {
					placeRandomBrick(world, random, i - 7, j13, k1);
					placeRandomBrick(world, random, i + 7, j13, k1);
				}
				for (int i15 = i - 1; i15 <= i + 1; ++i15) {
					placeRandomBrick(world, random, i15, j13, k - 7);
					placeRandomBrick(world, random, i15, j13, k + 7);
				}
			}
			for (int k1 = k - 1; k1 <= k + 1; ++k1) {
				placeRandomStairs(world, random, i - 7, j, k1, 4);
				placeRandomStairs(world, random, i - 6, j + 2, k1, 1);
				placeRandomStairs(world, random, i + 7, j, k1, 5);
				placeRandomStairs(world, random, i + 6, j + 2, k1, 0);
			}
			for (int i16 = i - 1; i16 <= i + 1; ++i16) {
				placeRandomStairs(world, random, i16, j, k - 7, 6);
				placeRandomStairs(world, random, i16, j + 2, k - 6, 3);
				placeRandomStairs(world, random, i16, j, k + 7, 7);
				placeRandomStairs(world, random, i16, j + 2, k + 6, 2);
			}
			for (j13 = j; j13 <= j + 4; ++j13) {
				setBlockAndNotifyAdequately(world, i - 5, j13, k - 5, LOTRMod.brick2, 0);
				setBlockAndNotifyAdequately(world, i - 5, j13, k + 5, LOTRMod.brick2, 0);
				setBlockAndNotifyAdequately(world, i + 5, j13, k - 5, LOTRMod.brick2, 0);
				setBlockAndNotifyAdequately(world, i + 5, j13, k + 5, LOTRMod.brick2, 0);
			}
			placeBanner(world, i - 5, j + 5, k - 5, 0, LOTRItemBanner.BannerType.ANGMAR);
			placeBanner(world, i - 5, j + 5, k + 5, 0, LOTRItemBanner.BannerType.ANGMAR);
			placeBanner(world, i + 5, j + 5, k - 5, 0, LOTRItemBanner.BannerType.ANGMAR);
			placeBanner(world, i + 5, j + 5, k + 5, 0, LOTRItemBanner.BannerType.ANGMAR);
			placeRandomStairs(world, random, i - 5, j + 2, k - 4, 3);
			placeRandomStairs(world, random, i - 4, j + 2, k - 5, 1);
			placeRandomStairs(world, random, i - 5, j + 2, k + 4, 2);
			placeRandomStairs(world, random, i - 4, j + 2, k + 5, 1);
			placeRandomStairs(world, random, i + 5, j + 2, k - 4, 3);
			placeRandomStairs(world, random, i + 4, j + 2, k - 5, 0);
			placeRandomStairs(world, random, i + 5, j + 2, k + 4, 2);
			placeRandomStairs(world, random, i + 4, j + 2, k + 5, 0);
		}
	}

	public void placeRandomBrick(World world, Random random, int i, int j, int k) {
		if (random.nextInt(20) == 0) {
			return;
		}
		if (random.nextInt(3) == 0) {
			setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.brick2, 1);
		} else {
			setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.brick2, 0);
		}
	}

	public void placeRandomStairs(World world, Random random, int i, int j, int k, int meta) {
		if (random.nextInt(10) == 0) {
			return;
		}
		if (random.nextInt(3) == 0) {
			setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.stairsAngmarBrickCracked, meta);
		} else {
			setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.stairsAngmarBrick, meta);
		}
	}
}
