package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenHarnedorTavern extends LOTRWorldGenHarnedorStructure {
	public LOTRWorldGenHarnedorTavern(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int k1;
		int step;
		int j12;
		int i1;
		setOriginAndRotation(world, i, j, k, rotation, 7, -3);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i12 = -15; i12 <= 15; ++i12) {
				for (int k12 = -8; k12 <= 8; ++k12) {
					j12 = getTopBlock(world, i12, k12) - 1;
					if (!isSurface(world, i12, j12, k12)) {
						return false;
					}
					if (j12 < minHeight) {
						minHeight = j12;
					}
					if (j12 > maxHeight) {
						maxHeight = j12;
					}
					if (maxHeight - minHeight <= 12) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i13 = -13; i13 <= 13; ++i13) {
			for (int k13 = -6; k13 <= 6; ++k13) {
				int i2 = Math.abs(i13);
				int k2 = Math.abs(k13);
				if ((i2 > 8 || k2 != 6) && (i2 > 11 || k2 > 5) && k2 > 4) {
					continue;
				}
				for (j12 = 1; j12 <= 8; ++j12) {
					setAir(world, i13, j12, k13);
				}
				j12 = -1;
				while (!isOpaque(world, i13, j12, k13) && getY(j12) >= 0) {
					setBlockAndMetadata(world, i13, j12, k13, plank2Block, plank2Meta);
					setGrassToDirt(world, i13, j12 - 1, k13);
					--j12;
				}
			}
		}
		if (isRuined()) {
			loadStrScan("harnedor_tavern_ruined");
		} else {
			loadStrScan("harnedor_tavern");
		}
		associateBlockMetaAlias("WOOD", woodBlock, woodMeta);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		associateBlockMetaAlias("PLANK2", plank2Block, plank2Meta);
		if (isRuined()) {
			setBlockAliasChance("PLANK2", 0.8f);
		}
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		generateStrScan(world, random, 0, 1, 0);
		if (!isRuined()) {
			placeWeaponRack(world, -3, 3, -1, 6, getRandomHarnedorWeapon(random));
			spawnItemFrame(world, -3, 3, 0, 0, getHarnedorFramedItem(random));
			placeWeaponRack(world, 3, 3, 1, 4, getRandomHarnedorWeapon(random));
			spawnItemFrame(world, 3, 3, 0, 2, getHarnedorFramedItem(random));
			placeFoodOrDrink(world, random, -4, 2, -1);
			placeFoodOrDrink(world, random, -3, 2, -1);
			placeFoodOrDrink(world, random, -2, 2, -1);
			placeFoodOrDrink(world, random, -2, 2, 0);
			placeFoodOrDrink(world, random, -2, 2, 1);
			placeFoodOrDrink(world, random, -3, 2, 1);
			placeFoodOrDrink(world, random, -4, 2, 1);
			placeFoodOrDrink(world, random, -4, 2, 0);
			placeFoodOrDrink(world, random, 4, 2, -1);
			placeFoodOrDrink(world, random, 3, 2, -1);
			placeFoodOrDrink(world, random, 2, 2, -1);
			placeFoodOrDrink(world, random, 2, 2, 0);
			placeFoodOrDrink(world, random, 2, 2, 1);
			placeFoodOrDrink(world, random, 3, 2, 1);
			placeFoodOrDrink(world, random, 4, 2, 1);
			placeFoodOrDrink(world, random, 4, 2, 0);
			placeFoodOrDrink(world, random, -7, 2, -5);
			placeFoodOrDrink(world, random, -8, 2, 5);
			placeFoodOrDrink(world, random, -7, 2, 5);
			placeFoodOrDrink(world, random, -6, 2, 5);
			placeFoodOrDrink(world, random, 6, 2, -5);
			placeFoodOrDrink(world, random, 7, 2, -5);
			placeFoodOrDrink(world, random, 6, 2, 5);
			placeFoodOrDrink(world, random, 7, 2, 5);
			placeFoodOrDrink(world, random, -9, 2, -2);
			placeFoodOrDrink(world, random, -9, 2, -1);
			placeFoodOrDrink(world, random, -9, 2, 1);
			placeFoodOrDrink(world, random, -9, 2, 2);
			placeFlowerPot(world, -12, 2, -3, getRandomFlower(world, random));
			placeFoodOrDrink(world, random, -12, 2, -2);
			placeFoodOrDrink(world, random, -12, 2, 1);
			placeFoodOrDrink(world, random, -12, 2, 2);
			placeBarrel(world, random, -12, 2, 3, 4, LOTRFoods.HARNEDOR_DRINK);
			placeBarrel(world, random, -11, 2, 4, 2, LOTRFoods.HARNEDOR_DRINK);
			placeKebabStand(world, random, -10, 2, -4, LOTRMod.kebabStand, 3);
			setBlockAndMetadata(world, 11, 1, -3, bedBlock, 2);
			setBlockAndMetadata(world, 11, 1, -4, bedBlock, 10);
			setBlockAndMetadata(world, 11, 1, 3, bedBlock, 0);
			setBlockAndMetadata(world, 11, 1, 4, bedBlock, 8);
			placeChest(world, random, 12, 1, -3, LOTRMod.chestBasket, 3, LOTRChestContents.HARNENNOR_HOUSE);
			placeChest(world, random, 12, 1, 3, LOTRMod.chestBasket, 2, LOTRChestContents.HARNENNOR_HOUSE);
			placeFlowerPot(world, 12, 2, -1, getRandomFlower(world, random));
			placeFoodOrDrink(world, random, 11, 2, -1);
			placeFlowerPot(world, 11, 2, 1, getRandomFlower(world, random));
			placeFoodOrDrink(world, random, 12, 2, 1);
			String[] tavernName = LOTRNames.getHaradTavernName(random);
			String tavernNameNPC = tavernName[0] + " " + tavernName[1];
			placeSign(world, -1, 2, -6, Blocks.wall_sign, 5, new String[]{"", tavernName[0], tavernName[1], ""});
			placeSign(world, 1, 2, -6, Blocks.wall_sign, 4, new String[]{"", tavernName[0], tavernName[1], ""});
			placeSign(world, -1, 2, 6, Blocks.wall_sign, 5, new String[]{"", tavernName[0], tavernName[1], ""});
			placeSign(world, 1, 2, 6, Blocks.wall_sign, 4, new String[]{"", tavernName[0], tavernName[1], ""});
			placeWallBanner(world, -6, 4, -8, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
			placeWallBanner(world, 6, 4, -8, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
			placeWallBanner(world, -6, 4, 8, LOTRItemBanner.BannerType.NEAR_HARAD, 0);
			placeWallBanner(world, 6, 4, 8, LOTRItemBanner.BannerType.NEAR_HARAD, 0);
			placeWallBanner(world, 0, 6, -4, LOTRItemBanner.BannerType.NEAR_HARAD, 0);
			placeWallBanner(world, 0, 6, 4, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
			placeWallBanner(world, -9, 5, 0, LOTRItemBanner.BannerType.NEAR_HARAD, 1);
			placeWallBanner(world, 9, 5, 0, LOTRItemBanner.BannerType.NEAR_HARAD, 3);
			LOTREntityHarnedorBartender bartender = new LOTREntityHarnedorBartender(world);
			bartender.setSpecificLocationName(tavernNameNPC);
			spawnNPCAndSetHome(bartender, world, -10, 1, 0, 4);
			int numHaradrim = MathHelper.getRandomIntegerInRange(random, 3, 8);
			for (int l = 0; l < numHaradrim; ++l) {
				LOTREntityHarnedhrim haradrim = new LOTREntityHarnedhrim(world);
				spawnNPCAndSetHome(haradrim, world, 0, 1, 0, 16);
			}
		}
		for (i1 = -5; i1 <= -1; ++i1) {
			for (step = 0; step < 12 && !isOpaque(world, i1, j1 = -step, k1 = -7 - step); ++step) {
				setBlockAndMetadata(world, i1, j1, k1, plank2StairBlock, 2);
				setGrassToDirt(world, i1, j1 - 1, k1);
				int j2 = j1 - 1;
				while (!isOpaque(world, i1, j2, k1) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i1, j2, k1, plank2Block, plank2Meta);
					setGrassToDirt(world, i1, j2 - 1, k1);
					--j2;
				}
			}
		}
		for (i1 = 1; i1 <= 5; ++i1) {
			for (step = 0; step < 12 && !isOpaque(world, i1, j1 = -step, k1 = -7 - step); ++step) {
				setBlockAndMetadata(world, i1, j1, k1, plank2StairBlock, 2);
				setGrassToDirt(world, i1, j1 - 1, k1);
				int j2 = j1 - 1;
				while (!isOpaque(world, i1, j2, k1) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i1, j2, k1, plank2Block, plank2Meta);
					setGrassToDirt(world, i1, j2 - 1, k1);
					--j2;
				}
			}
		}
		for (i1 = -5; i1 <= -1; ++i1) {
			for (step = 0; step < 12 && !isOpaque(world, i1, j1 = -step, k1 = 7 + step); ++step) {
				setBlockAndMetadata(world, i1, j1, k1, plank2StairBlock, 3);
				setGrassToDirt(world, i1, j1 - 1, k1);
				int j2 = j1 - 1;
				while (!isOpaque(world, i1, j2, k1) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i1, j2, k1, plank2Block, plank2Meta);
					setGrassToDirt(world, i1, j2 - 1, k1);
					--j2;
				}
			}
		}
		for (i1 = 1; i1 <= 5; ++i1) {
			for (step = 0; step < 12 && !isOpaque(world, i1, j1 = -step, k1 = 7 + step); ++step) {
				setBlockAndMetadata(world, i1, j1, k1, plank2StairBlock, 3);
				setGrassToDirt(world, i1, j1 - 1, k1);
				int j2 = j1 - 1;
				while (!isOpaque(world, i1, j2, k1) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i1, j2, k1, plank2Block, plank2Meta);
					setGrassToDirt(world, i1, j2 - 1, k1);
					--j2;
				}
			}
		}
		return true;
	}

	public void placeFoodOrDrink(World world, Random random, int i, int j, int k) {
		if (random.nextBoolean()) {
			if (random.nextBoolean()) {
				placeMug(world, random, i, j, k, random.nextInt(4), LOTRFoods.HARNEDOR_DRINK);
			} else {
				Block plateBlock;
				plateBlock = random.nextBoolean() ? LOTRMod.woodPlateBlock : LOTRMod.ceramicPlateBlock;
				if (random.nextBoolean()) {
					setBlockAndMetadata(world, i, j, k, plateBlock, 0);
				} else {
					placePlateWithCertainty(world, random, i, j, k, plateBlock, LOTRFoods.HARNEDOR);
				}
			}
		}
	}
}
