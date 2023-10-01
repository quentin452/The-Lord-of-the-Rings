package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityDorwinionElfVintner;
import lotr.common.item.LOTRItemBanner;
import lotr.common.item.LOTRItemMug;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenDorwinionBrewery extends LOTRWorldGenDorwinionHouse {
	public LOTRWorldGenDorwinionBrewery(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int j1;
		int i1;
		int i12;
		int k12;
		int k13;
		int i13;
		setOriginAndRotation(world, i, j, k, rotation, 1);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i12 = -6; i12 <= 6; ++i12) {
				for (k1 = 0; k1 <= 19; ++k1) {
					j1 = getTopBlock(world, i12, k1) - 1;
					Block block = getBlock(world, i12, j1, k1);
					if (block == Blocks.grass) {
						continue;
					}
					return false;
				}
			}
		}
		for (i12 = -6; i12 <= 6; ++i12) {
			for (k1 = 0; k1 <= 19; ++k1) {
				setBlockAndMetadata(world, i12, 0, k1, Blocks.grass, 0);
				j1 = -1;
				while (!isOpaque(world, i12, j1, k1) && getY(j1) >= 0) {
					setBlockAndMetadata(world, i12, j1, k1, Blocks.dirt, 0);
					setGrassToDirt(world, i12, j1 - 1, k1);
					--j1;
				}
				for (j1 = 1; j1 <= 10; ++j1) {
					setAir(world, i12, j1, k1);
				}
				if (i12 < -5 || i12 > 5 || k1 < 1 || k1 > 18) {
					continue;
				}
				if ((i12 == -5 || i12 == 5) && (k1 == 1 || k1 == 18)) {
					for (j1 = 0; j1 <= 5; ++j1) {
						setBlockAndMetadata(world, i12, j1, k1, woodBeamBlock, woodBeamMeta);
					}
					continue;
				}
				if (i12 == -5 || i12 == 5 || k1 == 1 || k1 == 18) {
					for (j1 = 0; j1 <= 5; ++j1) {
						setBlockAndMetadata(world, i12, j1, k1, brickBlock, brickMeta);
					}
					continue;
				}
				if (i12 >= -2 && i12 <= 2) {
					setBlockAndMetadata(world, i12, 0, k1, floorBlock, floorMeta);
					continue;
				}
				setBlockAndMetadata(world, i12, 0, k1, plankBlock, plankMeta);
			}
		}
		for (i12 = -1; i12 <= 1; ++i12) {
			setBlockAndMetadata(world, i12, 0, 1, floorBlock, floorMeta);
			for (int j12 = 1; j12 <= 3; ++j12) {
				setBlockAndMetadata(world, i12, j12, 1, LOTRMod.gateWooden, 2);
			}
		}
		for (k12 = 2; k12 <= 17; ++k12) {
			if (k12 % 3 == 2) {
				setBlockAndMetadata(world, -6, 1, k12, brickStairBlock, 1);
				setGrassToDirt(world, -6, 0, k12);
				setBlockAndMetadata(world, 6, 1, k12, brickStairBlock, 0);
				setGrassToDirt(world, 6, 0, k12);
				continue;
			}
			setBlockAndMetadata(world, -6, 1, k12, leafBlock, leafMeta);
			setBlockAndMetadata(world, 6, 1, k12, leafBlock, leafMeta);
		}
		for (i12 = -4; i12 <= 4; ++i12) {
			if (Math.abs(i12) == 4) {
				setBlockAndMetadata(world, i12, 1, 19, brickStairBlock, 3);
				setGrassToDirt(world, i12, 0, 19);
				continue;
			}
			setBlockAndMetadata(world, i12, 1, 19, leafBlock, leafMeta);
		}
		for (i12 = -4; i12 <= 4; ++i12) {
			if (Math.abs(i12) == 4 || Math.abs(i12) == 2) {
				setBlockAndMetadata(world, i12, 1, 0, brickStairBlock, 2);
				setGrassToDirt(world, i12, 0, 0);
				continue;
			}
			if (Math.abs(i12) != 3) {
				continue;
			}
			setBlockAndMetadata(world, i12, 1, 0, leafBlock, leafMeta);
		}
		for (i12 = -5; i12 <= 5; ++i12) {
			setBlockAndMetadata(world, i12, 5, 0, brickStairBlock, 6);
			setBlockAndMetadata(world, i12, 5, 19, brickStairBlock, 7);
		}
		for (k12 = 0; k12 <= 19; ++k12) {
			if (k12 >= 3 && k12 <= 16) {
				if (k12 % 3 == 0) {
					setAir(world, -5, 3, k12);
					setBlockAndMetadata(world, -5, 4, k12, brickStairBlock, 7);
					setAir(world, 5, 3, k12);
					setBlockAndMetadata(world, 5, 4, k12, brickStairBlock, 7);
				} else if (k12 % 3 == 1) {
					setAir(world, -5, 3, k12);
					setBlockAndMetadata(world, -5, 4, k12, brickStairBlock, 6);
					setAir(world, 5, 3, k12);
					setBlockAndMetadata(world, 5, 4, k12, brickStairBlock, 6);
				}
			}
			setBlockAndMetadata(world, -6, 5, k12, brickStairBlock, 5);
			setBlockAndMetadata(world, 6, 5, k12, brickStairBlock, 4);
			if (k12 <= 7 && k12 % 2 == 0 || k12 >= 12 && k12 % 2 == 1) {
				setBlockAndMetadata(world, -6, 6, k12, brickSlabBlock, brickSlabMeta);
				setBlockAndMetadata(world, 6, 6, k12, brickSlabBlock, brickSlabMeta);
			}
			if (k12 == 8 || k12 == 11) {
				setBlockAndMetadata(world, -6, 4, k12, brickSlabBlock, brickSlabMeta | 8);
				setBlockAndMetadata(world, -6, 5, k12, brickBlock, brickMeta);
				setBlockAndMetadata(world, -6, 6, k12, brickSlabBlock, brickSlabMeta);
				setBlockAndMetadata(world, 6, 4, k12, brickSlabBlock, brickSlabMeta | 8);
				setBlockAndMetadata(world, 6, 5, k12, brickBlock, brickMeta);
				setBlockAndMetadata(world, 6, 6, k12, brickSlabBlock, brickSlabMeta);
				placeWallBanner(world, -5, 3, k12, LOTRItemBanner.BannerType.DORWINION, 3);
				placeWallBanner(world, 5, 3, k12, LOTRItemBanner.BannerType.DORWINION, 1);
			}
			if (k12 != 9 && k12 != 10) {
				continue;
			}
			setBlockAndMetadata(world, -6, 6, k12, brickBlock, brickMeta);
			setBlockAndMetadata(world, 6, 6, k12, brickBlock, brickMeta);
		}
		for (i12 = -3; i12 <= 3; ++i12) {
			if (Math.abs(i12) == 3) {
				setBlockAndMetadata(world, i12, 2, 1, brickSlabBlock, brickSlabMeta);
				setAir(world, i12, 3, 1);
			}
			if (Math.abs(i12) == 2) {
				placeWallBanner(world, i12, 4, 1, LOTRItemBanner.BannerType.DORWINION, 2);
			}
			if (IntMath.mod(i12, 2) != 1) {
				continue;
			}
			setBlockAndMetadata(world, i12, 2, 18, brickSlabBlock, brickSlabMeta);
			setAir(world, i12, 3, 18);
		}
		for (int k14 : new int[]{1, 18}) {
			int i14;
			setBlockAndMetadata(world, -4, 6, k14, brickBlock, brickMeta);
			setBlockAndMetadata(world, -3, 6, k14, brickBlock, brickMeta);
			setBlockAndMetadata(world, -2, 6, k14, brickSlabBlock, brickSlabMeta);
			setBlockAndMetadata(world, -1, 6, k14, brickBlock, brickMeta);
			setBlockAndMetadata(world, 0, 6, k14, brickSlabBlock, brickSlabMeta);
			setBlockAndMetadata(world, 1, 6, k14, brickBlock, brickMeta);
			setBlockAndMetadata(world, 2, 6, k14, brickSlabBlock, brickSlabMeta);
			setBlockAndMetadata(world, 3, 6, k14, brickBlock, brickMeta);
			setBlockAndMetadata(world, 4, 6, k14, brickBlock, brickMeta);
			setBlockAndMetadata(world, -3, 7, k14, brickBlock, brickMeta);
			setAir(world, -2, 7, k14);
			setBlockAndMetadata(world, -1, 7, k14, brickBlock, brickMeta);
			setAir(world, 0, 7, k14);
			setBlockAndMetadata(world, 1, 7, k14, brickBlock, brickMeta);
			setAir(world, 2, 7, k14);
			setBlockAndMetadata(world, 3, 7, k14, brickBlock, brickMeta);
			for (i14 = -2; i14 <= 2; ++i14) {
				setBlockAndMetadata(world, i14, 8, k14, brickBlock, brickMeta);
			}
			for (i14 = -1; i14 <= 1; ++i14) {
				setBlockAndMetadata(world, i14, 9, k14, brickBlock, brickMeta);
			}
			setBlockAndMetadata(world, 0, 10, k14, brickBlock, brickMeta);
		}
		for (k13 = 2; k13 <= 17; ++k13) {
			setBlockAndMetadata(world, -4, 6, k13, plankStairBlock, 4);
			setBlockAndMetadata(world, -3, 7, k13, plankStairBlock, 4);
			setBlockAndMetadata(world, -2, 8, k13, plankStairBlock, 4);
			setBlockAndMetadata(world, -1, 9, k13, plankStairBlock, 4);
			setBlockAndMetadata(world, 0, 10, k13, plankBlock, plankMeta);
			setBlockAndMetadata(world, 1, 9, k13, plankStairBlock, 5);
			setBlockAndMetadata(world, 2, 8, k13, plankStairBlock, 5);
			setBlockAndMetadata(world, 3, 7, k13, plankStairBlock, 5);
			setBlockAndMetadata(world, 4, 6, k13, plankStairBlock, 5);
		}
		for (k13 = 0; k13 <= 19; ++k13) {
			setBlockAndMetadata(world, -5, 6, k13, clayStairBlock, 1);
			setBlockAndMetadata(world, -4, 7, k13, clayStairBlock, 1);
			setBlockAndMetadata(world, -3, 8, k13, clayStairBlock, 1);
			setBlockAndMetadata(world, -2, 9, k13, clayStairBlock, 1);
			setBlockAndMetadata(world, -1, 10, k13, clayStairBlock, 1);
			setBlockAndMetadata(world, 0, 11, k13, claySlabBlock, claySlabMeta);
			setBlockAndMetadata(world, 1, 10, k13, clayStairBlock, 0);
			setBlockAndMetadata(world, 2, 9, k13, clayStairBlock, 0);
			setBlockAndMetadata(world, 3, 8, k13, clayStairBlock, 0);
			setBlockAndMetadata(world, 4, 7, k13, clayStairBlock, 0);
			setBlockAndMetadata(world, 5, 6, k13, clayStairBlock, 0);
		}
		for (int k14 : new int[]{0, 19}) {
			setBlockAndMetadata(world, -4, 6, k14, clayStairBlock, 4);
			setBlockAndMetadata(world, -3, 7, k14, clayStairBlock, 4);
			setBlockAndMetadata(world, -2, 8, k14, clayStairBlock, 4);
			setBlockAndMetadata(world, -1, 9, k14, clayStairBlock, 4);
			setBlockAndMetadata(world, 0, 10, k14, clayBlock, clayMeta);
			setBlockAndMetadata(world, 1, 9, k14, clayStairBlock, 5);
			setBlockAndMetadata(world, 2, 8, k14, clayStairBlock, 5);
			setBlockAndMetadata(world, 3, 7, k14, clayStairBlock, 5);
			setBlockAndMetadata(world, 4, 6, k14, clayStairBlock, 5);
		}
		for (int k15 = 2; k15 <= 17; ++k15) {
			if (k15 % 3 != 2) {
				continue;
			}
			for (i13 = -4; i13 <= 4; ++i13) {
				setBlockAndMetadata(world, i13, 6, k15, woodBeamBlock, woodBeamMeta | 4);
			}
			setBlockAndMetadata(world, -4, 5, k15, Blocks.torch, 2);
			setBlockAndMetadata(world, 4, 5, k15, Blocks.torch, 1);
		}
		setBlockAndMetadata(world, -2, 5, 2, Blocks.torch, 3);
		setBlockAndMetadata(world, 2, 5, 2, Blocks.torch, 3);
		setBlockAndMetadata(world, -2, 5, 17, Blocks.torch, 4);
		setBlockAndMetadata(world, 2, 5, 17, Blocks.torch, 4);
		placeWallBanner(world, 0, 5, 1, LOTRItemBanner.BannerType.DORWINION, 0);
		placeWallBanner(world, 0, 5, 18, LOTRItemBanner.BannerType.DORWINION, 2);
		ItemStack drink = LOTRFoods.DORWINION_DRINK.getRandomBrewableDrink(random);
		for (k1 = 2; k1 <= 17; ++k1) {
			for (i1 = -4; i1 <= 4; ++i1) {
				if (Math.abs(i1) < 3) {
					continue;
				}
				if (k1 == 2 || k1 == 17) {
					setBlockAndMetadata(world, i1, 1, k1, plankBlock, plankMeta);
					continue;
				}
				if (k1 % 3 == 0) {
					setBlockAndMetadata(world, i1, 1, k1, Blocks.spruce_stairs, 6);
					setBlockAndMetadata(world, i1, 2, k1, Blocks.spruce_stairs, 2);
					continue;
				}
				if (k1 % 3 != 1) {
					continue;
				}
				setBlockAndMetadata(world, i1, 1, k1, Blocks.spruce_stairs, 7);
				setBlockAndMetadata(world, i1, 2, k1, Blocks.spruce_stairs, 3);
			}
			if (k1 < 5 || k1 > 15 || k1 % 3 != 2) {
				continue;
			}
			setBlockAndMetadata(world, -4, 1, k1, plankBlock, plankMeta);
			placeBarrel(world, random, -4, 2, k1, 4, drink);
			setBlockAndMetadata(world, 4, 1, k1, plankBlock, plankMeta);
			placeBarrel(world, random, 4, 2, k1, 5, drink);
		}
		for (k1 = 8; k1 <= 11; ++k1) {
			for (i1 = -1; i1 <= 1; ++i1) {
				if (Math.abs(i1) == 1 && (k1 == 8 || k1 == 11)) {
					setBlockAndMetadata(world, i1, 1, k1, plankBlock, plankMeta);
					continue;
				}
				setBlockAndMetadata(world, i1, 1, k1, plankSlabBlock, plankSlabMeta | 8);
			}
			placeMug(world, random, -1, 2, k1, 1, drink, LOTRFoods.DORWINION_DRINK);
			placeMug(world, random, 1, 2, k1, 3, drink, LOTRFoods.DORWINION_DRINK);
		}
		setBlockAndMetadata(world, 0, 1, 17, Blocks.crafting_table, 0);
		for (i13 = -2; i13 <= 2; ++i13) {
			if (Math.abs(i13) < 1) {
				continue;
			}
			setBlockAndMetadata(world, i13, 1, 17, Blocks.chest, 2);
			TileEntity tileentity = getTileEntity(world, i13, 1, 17);
			if (!(tileentity instanceof TileEntityChest)) {
				continue;
			}
			IInventory chest = (IInventory) tileentity;
			int wines = MathHelper.getRandomIntegerInRange(random, 3, 6);
			for (int l = 0; l < wines; ++l) {
				ItemStack chestDrinkItem = drink.copy();
				chestDrinkItem.stackSize = 1;
				LOTRItemMug.setStrengthMeta(chestDrinkItem, MathHelper.getRandomIntegerInRange(random, 1, 4));
				LOTRItemMug.Vessel[] chestVessels = LOTRFoods.DORWINION_DRINK.getDrinkVessels();
				LOTRItemMug.Vessel v = chestVessels[random.nextInt(chestVessels.length)];
				LOTRItemMug.setVessel(chestDrinkItem, v, true);
				chest.setInventorySlotContents(random.nextInt(chest.getSizeInventory()), chestDrinkItem);
			}
		}
		LOTREntityDorwinionElfVintner vintner = new LOTREntityDorwinionElfVintner(world);
		spawnNPCAndSetHome(vintner, world, 0, 1, 13, 16);
		return true;
	}
}
