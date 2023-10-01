package lotr.common.world.structure;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.feature.LOTRWorldGenMirkOak;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class LOTRWorldGenWoodElfTower extends LOTRWorldGenStructureBase {
	public WorldGenerator treeGen = new LOTRWorldGenMirkOak(true, 6, 6, 0, false).setGreenOak().disableRestrictions().disableRoots();
	public Block plateBlock = LOTRMod.woodPlateBlock;

	public LOTRWorldGenWoodElfTower(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		int j1;
		int i1;
		int k1;
		int i12;
		int k12;
		int k13;
		if (restrictions && world.getBlock(i, j - 1, k) != Blocks.grass) {
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
			int minHeight = j;
			int maxHeight = j;
			for (int i13 = i - radiusPlusOne; i13 <= i + radiusPlusOne; ++i13) {
				for (int k14 = k - radiusPlusOne; k14 <= k + radiusPlusOne; ++k14) {
					int i2 = i13 - i;
					int k2 = k14 - k;
					if (i2 * i2 + k2 * k2 > radiusPlusOne * radiusPlusOne) {
						continue;
					}
					int j12 = world.getTopSolidOrLiquidBlock(i13, k14) - 1;
					Block block = world.getBlock(i13, j12, k14);
					if (block != Blocks.grass && block != Blocks.dirt && block != Blocks.stone && !block.isWood(world, i13, j12, k14) && !block.isLeaves(world, i13, j12, k14)) {
						return false;
					}
					if (j12 < minHeight) {
						minHeight = j12;
					}
					if (j12 > maxHeight) {
						maxHeight = j12;
					}
					if (maxHeight - minHeight <= 8) {
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
		for (int i14 = i - radius; i14 <= i + radius; ++i14) {
			for (int k15 = k - radius; k15 <= k + radius; ++k15) {
				int start;
				int i2 = i14 - i;
				int k2 = k15 - k;
				int distSq = i2 * i2 + k2 * k2;
				if (distSq >= wallThresholdMax) {
					continue;
				}
				for (int j13 = start = j - sectionHeight; (j13 == start || !LOTRMod.isOpaque(world, i14, j13, k15)) && j13 >= 0; --j13) {
					if (j13 != start || distSq >= wallThresholdMin) {
						setBlockAndNotifyAdequately(world, i14, j13, k15, LOTRMod.brick3, 5);
					} else {
						setBlockAndNotifyAdequately(world, i14, j13, k15, LOTRMod.planks2, 13);
					}
					setGrassToDirt(world, i14, j13 - 1, k15);
				}
			}
		}
		for (int l = -1; l < sections; ++l) {
			int j14;
			int sectionBase = j + l * sectionHeight;
			for (j14 = sectionBase + 1; j14 <= sectionBase + sectionHeight; ++j14) {
				for (i12 = i - radius; i12 <= i + radius; ++i12) {
					for (k1 = k - radius; k1 <= k + radius; ++k1) {
						int i2 = i12 - i;
						int k2 = k1 - k;
						int distSq = i2 * i2 + k2 * k2;
						if (distSq >= wallThresholdMax) {
							continue;
						}
						if (distSq >= wallThresholdMin) {
							setBlockAndNotifyAdequately(world, i12, j14, k1, LOTRMod.brick3, 5);
							if (l == sections - 1 && j14 == sectionBase + sectionHeight) {
								setBlockAndNotifyAdequately(world, i12, j14 + 1, k1, LOTRMod.brick3, 5);
								setBlockAndNotifyAdequately(world, i12, j14 + 2, k1, LOTRMod.slabSingle6, 2);
							}
						} else if (j14 == sectionBase + sectionHeight && (Math.abs(i2) > 2 || Math.abs(k2) > 2)) {
							setBlockAndNotifyAdequately(world, i12, j14, k1, LOTRMod.planks2, 13);
						} else {
							setBlockAndNotifyAdequately(world, i12, j14, k1, Blocks.air, 0);
						}
						setGrassToDirt(world, i12, j14 - 1, k1);
					}
				}
				setBlockAndNotifyAdequately(world, i, j14, k, LOTRMod.wood7, 1);
			}
			for (int l1 = 0; l1 < 2; ++l1) {
				int stairBase = sectionBase + l1 * 4;
				setBlockAndNotifyAdequately(world, i - 4, sectionBase + 2, k - 4, LOTRMod.fence2, 13);
				setBlockAndNotifyAdequately(world, i, stairBase + 1, k + 1, LOTRMod.slabSingle6, 2);
				setBlockAndNotifyAdequately(world, i, stairBase + 1, k + 2, LOTRMod.slabSingle6, 2);
				setBlockAndNotifyAdequately(world, i + 1, stairBase + 2, k, LOTRMod.slabSingle6, 2);
				setBlockAndNotifyAdequately(world, i + 2, stairBase + 2, k, LOTRMod.slabSingle6, 2);
				setBlockAndNotifyAdequately(world, i, stairBase + 3, k - 1, LOTRMod.slabSingle6, 2);
				setBlockAndNotifyAdequately(world, i, stairBase + 3, k - 2, LOTRMod.slabSingle6, 2);
				setBlockAndNotifyAdequately(world, i - 1, stairBase + 4, k, LOTRMod.slabSingle6, 2);
				setBlockAndNotifyAdequately(world, i - 2, stairBase + 4, k, LOTRMod.slabSingle6, 2);
				for (i1 = 0; i1 <= 1; ++i1) {
					for (k12 = 0; k12 <= 1; ++k12) {
						setBlockAndNotifyAdequately(world, i + 1 + i1, stairBase + 1, k + 1 + k12, LOTRMod.slabSingle6, 10);
						setBlockAndNotifyAdequately(world, i + 1 + i1, stairBase + 2, k - 2 + k12, LOTRMod.slabSingle6, 10);
						setBlockAndNotifyAdequately(world, i - 2 + i1, stairBase + 3, k - 2 + k12, LOTRMod.slabSingle6, 10);
						setBlockAndNotifyAdequately(world, i - 2 + i1, stairBase + 4, k + 1 + k12, LOTRMod.slabSingle6, 10);
					}
				}
				setBlockAndNotifyAdequately(world, i - 1, stairBase + 2, k, LOTRMod.woodElvenTorch, 2);
				setBlockAndNotifyAdequately(world, i + 1, stairBase + 4, k, LOTRMod.woodElvenTorch, 1);
			}
			setBlockAndNotifyAdequately(world, i - 4, sectionBase + 2, k - 4, LOTRMod.fence2, 13);
			setBlockAndNotifyAdequately(world, i - 4, sectionBase + 3, k - 4, LOTRMod.woodElvenTorch, 5);
			setBlockAndNotifyAdequately(world, i - 4, sectionBase + 2, k + 4, LOTRMod.fence2, 13);
			setBlockAndNotifyAdequately(world, i - 4, sectionBase + 3, k + 4, LOTRMod.woodElvenTorch, 5);
			setBlockAndNotifyAdequately(world, i + 4, sectionBase + 2, k - 4, LOTRMod.fence2, 13);
			setBlockAndNotifyAdequately(world, i + 4, sectionBase + 3, k - 4, LOTRMod.woodElvenTorch, 5);
			setBlockAndNotifyAdequately(world, i + 4, sectionBase + 2, k + 4, LOTRMod.fence2, 13);
			setBlockAndNotifyAdequately(world, i + 4, sectionBase + 3, k + 4, LOTRMod.woodElvenTorch, 5);
			if (l > 0) {
				int i15;
				int k16;
				for (j14 = sectionBase + 1; j14 <= sectionBase + 4; ++j14) {
					for (i12 = i - 1; i12 <= i + 1; ++i12) {
						setBlockAndNotifyAdequately(world, i12, j14, k - 6, Blocks.air, 0);
						setBlockAndNotifyAdequately(world, i12, j14, k + 6, Blocks.air, 0);
					}
					for (k13 = k - 1; k13 <= k + 1; ++k13) {
						setBlockAndNotifyAdequately(world, i - 6, j14, k13, Blocks.air, 0);
						setBlockAndNotifyAdequately(world, i + 6, j14, k13, Blocks.air, 0);
					}
				}
				setBlockAndNotifyAdequately(world, i - 1, sectionBase + 4, k - 6, LOTRMod.stairsWoodElvenBrick, 5);
				setBlockAndNotifyAdequately(world, i + 1, sectionBase + 4, k - 6, LOTRMod.stairsWoodElvenBrick, 4);
				setBlockAndNotifyAdequately(world, i - 1, sectionBase + 4, k + 6, LOTRMod.stairsWoodElvenBrick, 5);
				setBlockAndNotifyAdequately(world, i + 1, sectionBase + 4, k + 6, LOTRMod.stairsWoodElvenBrick, 4);
				setBlockAndNotifyAdequately(world, i - 6, sectionBase + 4, k - 1, LOTRMod.stairsWoodElvenBrick, 7);
				setBlockAndNotifyAdequately(world, i - 6, sectionBase + 4, k + 1, LOTRMod.stairsWoodElvenBrick, 6);
				setBlockAndNotifyAdequately(world, i + 6, sectionBase + 4, k - 1, LOTRMod.stairsWoodElvenBrick, 7);
				setBlockAndNotifyAdequately(world, i + 6, sectionBase + 4, k + 1, LOTRMod.stairsWoodElvenBrick, 6);
				for (i15 = i - 2; i15 <= i + 2; ++i15) {
					setBlockAndNotifyAdequately(world, i15, sectionBase, k - 8, LOTRMod.stairsGreenOak, 6);
					setBlockAndNotifyAdequately(world, i15, sectionBase + 1, k - 8, LOTRMod.fence2, 13);
					setBlockAndNotifyAdequately(world, i15, sectionBase, k + 8, LOTRMod.stairsGreenOak, 7);
					setBlockAndNotifyAdequately(world, i15, sectionBase + 1, k + 8, LOTRMod.fence2, 13);
				}
				for (k16 = k - 2; k16 <= k + 2; ++k16) {
					setBlockAndNotifyAdequately(world, i - 8, sectionBase, k16, LOTRMod.stairsGreenOak, 4);
					setBlockAndNotifyAdequately(world, i - 8, sectionBase + 1, k16, LOTRMod.fence2, 13);
					setBlockAndNotifyAdequately(world, i + 8, sectionBase, k16, LOTRMod.stairsGreenOak, 5);
					setBlockAndNotifyAdequately(world, i + 8, sectionBase + 1, k16, LOTRMod.fence2, 13);
				}
				for (i15 = i - 1; i15 <= i + 1; ++i15) {
					setBlockAndNotifyAdequately(world, i15, sectionBase, k - 7, LOTRMod.planks2, 13);
					setBlockAndNotifyAdequately(world, i15, sectionBase, k + 7, LOTRMod.planks2, 13);
				}
				for (k16 = k - 1; k16 <= k + 1; ++k16) {
					setBlockAndNotifyAdequately(world, i - 7, sectionBase, k16, LOTRMod.planks2, 13);
					setBlockAndNotifyAdequately(world, i + 7, sectionBase, k16, LOTRMod.planks2, 13);
				}
				setBlockAndNotifyAdequately(world, i - 7, sectionBase, k - 2, LOTRMod.stairsGreenOak, 6);
				setBlockAndNotifyAdequately(world, i - 7, sectionBase + 1, k - 2, LOTRMod.fence2, 13);
				setBlockAndNotifyAdequately(world, i - 8, sectionBase + 2, k - 2, LOTRMod.woodElvenTorch, 5);
				setBlockAndNotifyAdequately(world, i - 7, sectionBase, k + 2, LOTRMod.stairsGreenOak, 7);
				setBlockAndNotifyAdequately(world, i - 7, sectionBase + 1, k + 2, LOTRMod.fence2, 13);
				setBlockAndNotifyAdequately(world, i - 8, sectionBase + 2, k + 2, LOTRMod.woodElvenTorch, 5);
				setBlockAndNotifyAdequately(world, i + 7, sectionBase, k - 2, LOTRMod.stairsGreenOak, 6);
				setBlockAndNotifyAdequately(world, i + 7, sectionBase + 1, k - 2, LOTRMod.fence2, 13);
				setBlockAndNotifyAdequately(world, i + 8, sectionBase + 2, k - 2, LOTRMod.woodElvenTorch, 5);
				setBlockAndNotifyAdequately(world, i + 7, sectionBase, k + 2, LOTRMod.stairsGreenOak, 7);
				setBlockAndNotifyAdequately(world, i + 7, sectionBase + 1, k + 2, LOTRMod.fence2, 13);
				setBlockAndNotifyAdequately(world, i + 8, sectionBase + 2, k + 2, LOTRMod.woodElvenTorch, 5);
				setBlockAndNotifyAdequately(world, i - 2, sectionBase, k - 7, LOTRMod.stairsGreenOak, 4);
				setBlockAndNotifyAdequately(world, i - 2, sectionBase + 1, k - 7, LOTRMod.fence2, 13);
				setBlockAndNotifyAdequately(world, i - 2, sectionBase + 2, k - 8, LOTRMod.woodElvenTorch, 5);
				setBlockAndNotifyAdequately(world, i + 2, sectionBase, k - 7, LOTRMod.stairsGreenOak, 5);
				setBlockAndNotifyAdequately(world, i + 2, sectionBase + 1, k - 7, LOTRMod.fence2, 13);
				setBlockAndNotifyAdequately(world, i + 2, sectionBase + 2, k - 8, LOTRMod.woodElvenTorch, 5);
				setBlockAndNotifyAdequately(world, i - 2, sectionBase, k + 7, LOTRMod.stairsGreenOak, 4);
				setBlockAndNotifyAdequately(world, i - 2, sectionBase + 1, k + 7, LOTRMod.fence2, 13);
				setBlockAndNotifyAdequately(world, i - 2, sectionBase + 2, k + 8, LOTRMod.woodElvenTorch, 5);
				setBlockAndNotifyAdequately(world, i + 2, sectionBase, k + 7, LOTRMod.stairsGreenOak, 5);
				setBlockAndNotifyAdequately(world, i + 2, sectionBase + 1, k + 7, LOTRMod.fence2, 13);
				setBlockAndNotifyAdequately(world, i + 2, sectionBase + 2, k + 8, LOTRMod.woodElvenTorch, 5);
			}
			LOTREntityWoodElf woodElf = random.nextInt(3) == 0 ? new LOTREntityWoodElfScout(world) : new LOTREntityWoodElfWarrior(world);
			woodElf.setLocationAndAngles(i - 3 + 0.5, sectionBase + 1, k - 3 + 0.5, world.rand.nextFloat() * 360.0f, 0.0f);
			woodElf.spawnRidingHorse = false;
			woodElf.onSpawnWithEgg(null);
			woodElf.setHomeArea(i, sectionBase + 1, k, 12);
			woodElf.isNPCPersistent = true;
			world.spawnEntityInWorld(woodElf);
		}
		treeGen.generate(world, random, i, topHeight, k);
		for (int j15 = topHeight + 2; j15 <= topHeight + 3; ++j15) {
			setBlockAndNotifyAdequately(world, i + 6, j15, k - 3, LOTRMod.brick3, 5);
			setBlockAndNotifyAdequately(world, i + 6, j15, k, LOTRMod.brick3, 5);
			setBlockAndNotifyAdequately(world, i + 6, j15, k + 3, LOTRMod.brick3, 5);
			setBlockAndNotifyAdequately(world, i - 3, j15, k + 6, LOTRMod.brick3, 5);
			setBlockAndNotifyAdequately(world, i, j15, k + 6, LOTRMod.brick3, 5);
			setBlockAndNotifyAdequately(world, i + 3, j15, k + 6, LOTRMod.brick3, 5);
			setBlockAndNotifyAdequately(world, i - 6, j15, k - 3, LOTRMod.brick3, 5);
			setBlockAndNotifyAdequately(world, i - 6, j15, k, LOTRMod.brick3, 5);
			setBlockAndNotifyAdequately(world, i - 6, j15, k + 3, LOTRMod.brick3, 5);
			setBlockAndNotifyAdequately(world, i - 3, j15, k - 6, LOTRMod.brick3, 5);
			setBlockAndNotifyAdequately(world, i, j15, k - 6, LOTRMod.brick3, 5);
			setBlockAndNotifyAdequately(world, i + 3, j15, k - 6, LOTRMod.brick3, 5);
		}
		setBlockAndNotifyAdequately(world, i + 6, topHeight + 2, k - 2, LOTRMod.brick3, 5);
		setBlockAndNotifyAdequately(world, i + 6, topHeight + 2, k + 2, LOTRMod.brick3, 5);
		setBlockAndNotifyAdequately(world, i - 2, topHeight + 2, k + 6, LOTRMod.brick3, 5);
		setBlockAndNotifyAdequately(world, i + 2, topHeight + 2, k + 6, LOTRMod.brick3, 5);
		setBlockAndNotifyAdequately(world, i - 6, topHeight + 2, k - 2, LOTRMod.brick3, 5);
		setBlockAndNotifyAdequately(world, i - 6, topHeight + 2, k + 2, LOTRMod.brick3, 5);
		setBlockAndNotifyAdequately(world, i - 2, topHeight + 2, k - 6, LOTRMod.brick3, 5);
		setBlockAndNotifyAdequately(world, i + 2, topHeight + 2, k - 6, LOTRMod.brick3, 5);
		ItemStack bow1 = new ItemStack(LOTRMod.mirkwoodBow);
		ItemStack bow2 = new ItemStack(LOTRMod.mirkwoodBow);
		ItemStack[] armor = {new ItemStack(LOTRMod.helmetWoodElvenScout), new ItemStack(LOTRMod.bodyWoodElvenScout), new ItemStack(LOTRMod.legsWoodElvenScout), new ItemStack(LOTRMod.bootsWoodElvenScout)};
		switch (rotation) {
			case 0: {
				placeArmorStand(world, i, topHeight + 1, k + 5, 0, armor);
				spawnItemFrame(world, i + 6, topHeight + 2, k, 1, bow1);
				spawnItemFrame(world, i - 6, topHeight + 2, k, 3, bow2);
				setBlockAndNotifyAdequately(world, i, topHeight + 1, k - 4, LOTRMod.commandTable, 0);
				break;
			}
			case 1: {
				spawnItemFrame(world, i, topHeight + 2, k + 6, 2, bow1);
				spawnItemFrame(world, i, topHeight + 2, k - 6, 0, bow2);
				placeArmorStand(world, i - 5, topHeight + 1, k, 1, armor);
				setBlockAndNotifyAdequately(world, i + 4, topHeight + 1, k, LOTRMod.commandTable, 0);
				break;
			}
			case 2: {
				spawnItemFrame(world, i + 6, topHeight + 2, k, 1, bow1);
				placeArmorStand(world, i, topHeight + 1, k - 5, 2, armor);
				spawnItemFrame(world, i - 6, topHeight + 2, k, 3, bow2);
				setBlockAndNotifyAdequately(world, i, topHeight + 1, k + 4, LOTRMod.commandTable, 0);
				break;
			}
			case 3: {
				spawnItemFrame(world, i, topHeight + 2, k + 6, 2, bow1);
				placeArmorStand(world, i + 5, topHeight + 1, k, 3, armor);
				spawnItemFrame(world, i, topHeight + 2, k - 6, 0, bow2);
				setBlockAndNotifyAdequately(world, i - 4, topHeight + 1, k, LOTRMod.commandTable, 0);
			}
		}
		placeWallBanner(world, i, topHeight + 1, k + 6, 0, LOTRItemBanner.BannerType.WOOD_ELF);
		placeWallBanner(world, i - 6, topHeight + 1, k, 1, LOTRItemBanner.BannerType.WOOD_ELF);
		placeWallBanner(world, i, topHeight + 1, k - 6, 2, LOTRItemBanner.BannerType.WOOD_ELF);
		placeWallBanner(world, i + 6, topHeight + 1, k, 3, LOTRItemBanner.BannerType.WOOD_ELF);
		for (i12 = i - 3; i12 <= i + 3; ++i12) {
			setBlockAndNotifyAdequately(world, i12, j - sectionHeight + 1, k - 5, Blocks.wooden_slab, 8);
			setBlockAndNotifyAdequately(world, i12, j - sectionHeight + 1, k + 5, Blocks.wooden_slab, 8);
			if (random.nextBoolean()) {
				placeMug(world, random, i12, j - sectionHeight + 2, k + 5, 0, LOTRFoods.WOOD_ELF_DRINK);
			}
			if (Math.abs(i12 - i) <= 1) {
				continue;
			}
			placeBarrel(world, random, i12, j - sectionHeight + 2, k - 5, 3, LOTRFoods.WOOD_ELF_DRINK);
		}
		setBlockAndNotifyAdequately(world, i - 1, j - sectionHeight + 1, k - 5, LOTRMod.woodElvenTable, 0);
		setBlockAndNotifyAdequately(world, i + 1, j - sectionHeight + 1, k - 5, LOTRMod.woodElvenTable, 0);
		setBlockAndNotifyAdequately(world, i, j - sectionHeight + 1, k - 5, Blocks.chest, 0);
		LOTRChestContents.fillChest(world, random, i, j - sectionHeight + 1, k - 5, LOTRChestContents.WOOD_ELF_HOUSE);
		for (i12 = i + 4; i12 <= i + 5; ++i12) {
			setBlockAndNotifyAdequately(world, i12, j - sectionHeight + 1, k - 3, Blocks.oak_stairs, 3);
			setBlockAndNotifyAdequately(world, i12, j - sectionHeight + 1, k - 1, Blocks.planks, 0);
			placeMug(world, random, i12, j - sectionHeight + 2, k - 1, 0, LOTRFoods.WOOD_ELF_DRINK);
			setBlockAndNotifyAdequately(world, i12, j - sectionHeight + 1, k, Blocks.wooden_slab, 8);
			placePlateWithCertainty(world, random, i12, j - sectionHeight + 2, k, plateBlock, LOTRFoods.ELF);
			setBlockAndNotifyAdequately(world, i12, j - sectionHeight + 1, k + 1, Blocks.planks, 0);
			placeMug(world, random, i12, j - sectionHeight + 2, k + 1, 2, LOTRFoods.WOOD_ELF_DRINK);
			setBlockAndNotifyAdequately(world, i12, j - sectionHeight + 1, k + 3, Blocks.oak_stairs, 2);
		}
		setBlockAndNotifyAdequately(world, i + 4, j - sectionHeight + 1, k - 4, Blocks.planks, 0);
		setBlockAndNotifyAdequately(world, i + 4, j - sectionHeight + 1, k + 4, Blocks.planks, 0);
		for (j1 = j - sectionHeight - 6; j1 <= j - sectionHeight - 1; ++j1) {
			placeDungeonBlock(world, random, i - 6, j1, k);
			placeDungeonBlock(world, random, i - 5, j1, k - 2);
			placeDungeonBlock(world, random, i - 5, j1, k - 1);
			placeDungeonBlock(world, random, i - 5, j1, k + 1);
			placeDungeonBlock(world, random, i - 5, j1, k + 2);
			placeDungeonBlock(world, random, i - 4, j1, k - 3);
			placeDungeonBlock(world, random, i - 4, j1, k + 3);
			placeDungeonBlock(world, random, i - 3, j1, k - 5);
			placeDungeonBlock(world, random, i - 3, j1, k - 4);
			placeDungeonBlock(world, random, i - 3, j1, k + 4);
			placeDungeonBlock(world, random, i - 3, j1, k + 5);
			placeDungeonBlock(world, random, i - 2, j1, k - 6);
			placeDungeonBlock(world, random, i - 2, j1, k + 6);
			placeDungeonBlock(world, random, i - 1, j1, k - 6);
			placeDungeonBlock(world, random, i - 1, j1, k + 6);
			placeDungeonBlock(world, random, i, j1, k - 6);
			placeDungeonBlock(world, random, i, j1, k + 6);
			placeDungeonBlock(world, random, i + 1, j1, k - 5);
			placeDungeonBlock(world, random, i + 1, j1, k - 4);
			placeDungeonBlock(world, random, i + 1, j1, k + 4);
			placeDungeonBlock(world, random, i + 1, j1, k + 5);
			placeDungeonBlock(world, random, i + 2, j1, k - 3);
			placeDungeonBlock(world, random, i + 2, j1, k + 3);
			placeDungeonBlock(world, random, i + 3, j1, k - 2);
			placeDungeonBlock(world, random, i + 3, j1, k + 2);
			placeDungeonBlock(world, random, i + 4, j1, k - 2);
			placeDungeonBlock(world, random, i + 4, j1, k + 2);
			placeDungeonBlock(world, random, i + 5, j1, k - 1);
			placeDungeonBlock(world, random, i + 5, j1, k);
			placeDungeonBlock(world, random, i + 5, j1, k + 1);
			if (j1 == j - sectionHeight - 6 || j1 == j - sectionHeight - 1) {
				placeDungeonBlock(world, random, i - 5, j1, k);
				for (k1 = k - 2; k1 <= k + 2; ++k1) {
					placeDungeonBlock(world, random, i - 4, j1, k1);
				}
				for (k1 = k - 3; k1 <= k + 3; ++k1) {
					placeDungeonBlock(world, random, i - 3, j1, k1);
				}
				for (k1 = k - 5; k1 <= k + 5; ++k1) {
					placeDungeonBlock(world, random, i - 2, j1, k1);
					placeDungeonBlock(world, random, i - 1, j1, k1);
					placeDungeonBlock(world, random, i, j1, k1);
				}
				for (k1 = k - 3; k1 <= k + 3; ++k1) {
					placeDungeonBlock(world, random, i + 1, j1, k1);
				}
				for (k1 = k - 2; k1 <= k + 2; ++k1) {
					placeDungeonBlock(world, random, i + 2, j1, k1);
				}
				for (k1 = k - 1; k1 <= k + 1; ++k1) {
					placeDungeonBlock(world, random, i + 3, j1, k1);
					placeDungeonBlock(world, random, i + 4, j1, k1);
				}
				continue;
			}
			setBlockAndNotifyAdequately(world, i - 5, j1, k, Blocks.air, 0);
			for (k1 = k - 2; k1 <= k + 2; ++k1) {
				setBlockAndNotifyAdequately(world, i - 4, j1, k1, Blocks.air, 0);
			}
			for (k1 = k - 3; k1 <= k + 3; ++k1) {
				setBlockAndNotifyAdequately(world, i - 3, j1, k1, Blocks.air, 0);
			}
			for (k1 = k - 5; k1 <= k + 5; ++k1) {
				setBlockAndNotifyAdequately(world, i - 2, j1, k1, Blocks.air, 0);
				setBlockAndNotifyAdequately(world, i - 1, j1, k1, Blocks.air, 0);
				setBlockAndNotifyAdequately(world, i, j1, k1, Blocks.air, 0);
			}
			for (k1 = k - 3; k1 <= k + 3; ++k1) {
				setBlockAndNotifyAdequately(world, i + 1, j1, k1, Blocks.air, 0);
			}
			for (k1 = k - 2; k1 <= k + 2; ++k1) {
				setBlockAndNotifyAdequately(world, i + 2, j1, k1, Blocks.air, 0);
			}
			for (k1 = k - 1; k1 <= k + 1; ++k1) {
				setBlockAndNotifyAdequately(world, i + 3, j1, k1, Blocks.air, 0);
				setBlockAndNotifyAdequately(world, i + 4, j1, k1, Blocks.air, 0);
			}
		}
		for (i12 = i - 2; i12 <= i; ++i12) {
			placeDungeonBlock(world, random, i12, j - sectionHeight - 2, k - 5);
			placeDungeonBlock(world, random, i12, j - sectionHeight - 2, k - 4);
			placeDungeonBlock(world, random, i12, j - sectionHeight - 2, k + 4);
			placeDungeonBlock(world, random, i12, j - sectionHeight - 2, k + 5);
		}
		for (k13 = k - 1; k13 <= k + 1; ++k13) {
			placeDungeonBlock(world, random, i + 3, j - sectionHeight - 2, k13);
			placeDungeonBlock(world, random, i + 4, j - sectionHeight - 2, k13);
		}
		for (j1 = j - sectionHeight - 5; j1 <= j - sectionHeight - 3; ++j1) {
			for (i1 = i - 2; i1 <= i; ++i1) {
				setBlockAndNotifyAdequately(world, i1, j1, k - 4, LOTRMod.woodElfBars, 0);
				setBlockAndNotifyAdequately(world, i1, j1, k + 4, LOTRMod.woodElfBars, 0);
			}
			for (k1 = k - 1; k1 <= k + 1; ++k1) {
				setBlockAndNotifyAdequately(world, i + 3, j1, k1, LOTRMod.woodElfBars, 0);
			}
		}
		placePrisoner(world, random, i - 2, j - sectionHeight - 5, k - 5, 3, 1);
		placePrisoner(world, random, i - 2, j - sectionHeight - 5, k + 5, 3, 1);
		placePrisoner(world, random, i + 4, j - sectionHeight - 5, k - 1, 1, 3);
		setBlockAndNotifyAdequately(world, i - 4, j - sectionHeight - 3, k - 1, LOTRMod.woodElvenTorch, 1);
		setBlockAndNotifyAdequately(world, i - 4, j - sectionHeight - 3, k + 1, LOTRMod.woodElvenTorch, 1);
		for (j1 = j - sectionHeight - 5; j1 <= j - sectionHeight; ++j1) {
			setBlockAndNotifyAdequately(world, i - 5, j1, k, Blocks.ladder, 5);
		}
		setBlockAndNotifyAdequately(world, i - 5, j - sectionHeight + 1, k, Blocks.trapdoor, 3);
		switch (rotation) {
			case 0: {
				int j16;
				k12 = k - radius;
				for (i12 = i - 1; i12 <= i + 1; ++i12) {
					for (j16 = j + 1; j16 <= j + 3; ++j16) {
						setBlockAndNotifyAdequately(world, i12, j16, k12, LOTRMod.gateWoodElven, 2);
					}
				}
				placeWallBanner(world, i, j + 6, k12, 2, LOTRItemBanner.BannerType.WOOD_ELF);
				break;
			}
			case 1: {
				int j16;
				i12 = i + radius;
				for (k12 = k - 1; k12 <= k + 1; ++k12) {
					for (j16 = j + 1; j16 <= j + 3; ++j16) {
						setBlockAndNotifyAdequately(world, i12, j16, k12, LOTRMod.gateWoodElven, 5);
					}
				}
				placeWallBanner(world, i12, j + 6, k, 3, LOTRItemBanner.BannerType.WOOD_ELF);
				break;
			}
			case 2: {
				int j16;
				k12 = k + radius;
				for (i12 = i - 1; i12 <= i + 1; ++i12) {
					for (j16 = j + 1; j16 <= j + 3; ++j16) {
						setBlockAndNotifyAdequately(world, i12, j16, k12, LOTRMod.gateWoodElven, 3);
					}
				}
				placeWallBanner(world, i, j + 6, k12, 0, LOTRItemBanner.BannerType.WOOD_ELF);
				break;
			}
			case 3: {
				int j16;
				i12 = i - radius;
				for (k12 = k - 1; k12 <= k + 1; ++k12) {
					for (j16 = j + 1; j16 <= j + 3; ++j16) {
						setBlockAndNotifyAdequately(world, i12, j16, k12, LOTRMod.gateWoodElven, 4);
					}
				}
				placeWallBanner(world, i12, j + 6, k, 1, LOTRItemBanner.BannerType.WOOD_ELF);
			}
		}
		LOTREntityWoodElfCaptain woodElfCaptain = new LOTREntityWoodElfCaptain(world);
		woodElfCaptain.setLocationAndAngles(i - 3 + 0.5, topHeight + 1, k - 3 + 0.5, world.rand.nextFloat() * 360.0f, 0.0f);
		woodElfCaptain.spawnRidingHorse = false;
		woodElfCaptain.onSpawnWithEgg(null);
		woodElfCaptain.setHomeArea(i, topHeight, k, 16);
		world.spawnEntityInWorld(woodElfCaptain);
		LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
		respawner.setSpawnClasses(LOTREntityWoodElfWarrior.class, LOTREntityWoodElfScout.class);
		respawner.setCheckRanges(12, -16, 40, 12);
		respawner.setSpawnRanges(5, 1, 40, 12);
		placeNPCRespawner(respawner, world, i, j, k);
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

	public void placePrisoner(World world, Random random, int i, int j, int k, int xRange, int zRange) {
		i += random.nextInt(xRange);
		k += random.nextInt(zRange);
		if (random.nextInt(3) == 0) {
			LOTREntityNPC npc = random.nextInt(10) == 0 ? new LOTREntityDwarf(world) : random.nextBoolean() ? new LOTREntityGundabadOrc(world) : new LOTREntityDolGuldurOrc(world);
			npc.setLocationAndAngles(i + 0.5, j, k + 0.5, 0.0f, 0.0f);
			npc.spawnRidingHorse = false;
			npc.onSpawnWithEgg(null);
			for (int l = 0; l < 5; ++l) {
				npc.setCurrentItemOrArmor(l, null);
			}
			npc.npcItemsInv.setMeleeWeapon(null);
			npc.npcItemsInv.setMeleeWeaponMounted(null);
			npc.npcItemsInv.setRangedWeapon(null);
			npc.npcItemsInv.setSpearBackup(null);
			npc.npcItemsInv.setIdleItem(null);
			npc.npcItemsInv.setIdleItemMounted(null);
			npc.isNPCPersistent = true;
			world.spawnEntityInWorld(npc);
		} else {
			placeSkull(world, random, i, j, k);
		}
	}
}
