package lotr.common.world.structure2;

import java.util.Random;

import com.google.common.math.IntMath;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityTauredain;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenTauredainHouseLarge extends LOTRWorldGenTauredainHouse {
	public LOTRWorldGenTauredainHouseLarge(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int i1;
		int i12;
		int j1;
		int i13;
		int j12;
		int k1;
		int k12;
		if (!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
			return false;
		}
		for (i13 = -6; i13 <= 5; ++i13) {
			for (k1 = -4; k1 <= 4; ++k1) {
				layFoundation(world, i13, k1);
				for (j1 = 1; j1 <= 11; ++j1) {
					setAir(world, i13, j1, k1);
				}
			}
		}
		for (i13 = -6; i13 <= 5; ++i13) {
			for (k1 = -4; k1 <= 4; ++k1) {
				if (i13 >= -5 && i13 <= 4 && k1 >= -3 && k1 <= 3) {
					setBlockAndMetadata(world, i13, 0, k1, floorBlock, floorMeta);
				}
				if ((i13 == -5 || i13 == 4) && k1 >= -3 && k1 <= 3 || (k1 == -3 || k1 == 3) && i13 >= -5 && i13 <= 4) {
					setBlockAndMetadata(world, i13, 3, k1, brickSlabBlock, brickSlabMeta | 8);
					setBlockAndMetadata(world, i13, 4, k1, LOTRMod.mudGrass, 0);
					setBlockAndMetadata(world, i13, 5, k1, Blocks.tallgrass, 1);
				}
				if ((i13 != -4 && i13 != 3 || k1 < -2 || k1 > 2) && (k1 != -2 && k1 != 2 || i13 < -4 || i13 > 3)) {
					continue;
				}
				setBlockAndMetadata(world, i13, 4, k1, brickBlock, brickMeta);
				setBlockAndMetadata(world, i13, 5, k1, brickBlock, brickMeta);
				setBlockAndMetadata(world, i13, 8, k1, plankBlock, plankMeta);
			}
		}
		for (int i14 : new int[]{-6, 5}) {
			for (int k13 : new int[]{-4, 4}) {
				for (int j13 = 1; j13 <= 5; ++j13) {
					setBlockAndMetadata(world, i14, j13, k13, woodBlock, woodMeta);
				}
			}
		}
		for (int k14 : new int[]{-4, 4}) {
			for (int i15 = -5; i15 <= 4; ++i15) {
				setBlockAndMetadata(world, i15, 5, k14, brickSlabBlock, brickSlabMeta);
				if (IntMath.mod(i15, 3) == 1) {
					setBlockAndMetadata(world, i15, 4, k14, woodBlock, woodMeta | 8);
					continue;
				}
				setBlockAndMetadata(world, i15, 4, k14, plankBlock, plankMeta);
			}
			for (int j14 = 1; j14 <= 3; ++j14) {
				setBlockAndMetadata(world, -5, j14, k14, brickBlock, brickMeta);
				setBlockAndMetadata(world, 4, j14, k14, brickBlock, brickMeta);
			}
			for (int i16 : new int[]{-4, 2}) {
				setBlockAndMetadata(world, i16, 1, k14, brickBlock, brickMeta);
				setBlockAndMetadata(world, i16 + 1, 1, k14, brickBlock, brickMeta);
				if (random.nextInt(3) == 0) {
					placeTauredainFlowerPot(world, i16, 2, k14, random);
				}
				if (random.nextInt(3) == 0) {
					placeTauredainFlowerPot(world, i16 + 1, 2, k14, random);
				}
				setBlockAndMetadata(world, i16, 3, k14, brickStairBlock, 5);
				setBlockAndMetadata(world, i16 + 1, 3, k14, brickStairBlock, 4);
			}
		}
		setBlockAndMetadata(world, -1, 3, -4, brickBlock, brickMeta);
		setBlockAndMetadata(world, 0, 3, -4, brickBlock, brickMeta);
		for (int j15 = 1; j15 <= 3; ++j15) {
			setBlockAndMetadata(world, -1, j15, 4, brickBlock, brickMeta);
			setBlockAndMetadata(world, 0, j15, 4, brickBlock, brickMeta);
		}
		int[] j15 = {-6, 5};
		k1 = j15.length;
		for (j1 = 0; j1 < k1; ++j1) {
			int i14;
			i14 = j15[j1];
			for (int k15 = -3; k15 <= 3; ++k15) {
				setBlockAndMetadata(world, i14, 5, k15, brickSlabBlock, brickSlabMeta);
				if (k15 % 2 == 0) {
					setBlockAndMetadata(world, i14, 4, k15, woodBlock, woodMeta | 4);
					continue;
				}
				setBlockAndMetadata(world, i14, 4, k15, plankBlock, plankMeta);
			}
			for (int j16 = 1; j16 <= 3; ++j16) {
				setBlockAndMetadata(world, i14, j16, -3, brickBlock, brickMeta);
				setBlockAndMetadata(world, i14, j16, 3, brickBlock, brickMeta);
			}
			for (int k13 : new int[]{-2, 1}) {
				setBlockAndMetadata(world, i14, 1, k13, brickBlock, brickMeta);
				setBlockAndMetadata(world, i14, 1, k13 + 1, brickBlock, brickMeta);
				if (random.nextInt(3) == 0) {
					placeTauredainFlowerPot(world, i14, 2, k13, random);
				}
				if (random.nextInt(3) == 0) {
					placeTauredainFlowerPot(world, i14, 2, k13 + 1, random);
				}
				setBlockAndMetadata(world, i14, 3, k13, brickStairBlock, 6);
				setBlockAndMetadata(world, i14, 3, k13 + 1, brickStairBlock, 7);
			}
		}
		for (j12 = 1; j12 <= 3; ++j12) {
			setBlockAndMetadata(world, -2, j12, -4, woodBlock, woodMeta);
			setBlockAndMetadata(world, 1, j12, -4, woodBlock, woodMeta);
			setBlockAndMetadata(world, -2, j12, 4, woodBlock, woodMeta);
			setBlockAndMetadata(world, 1, j12, 4, woodBlock, woodMeta);
			setBlockAndMetadata(world, -6, j12, 0, woodBlock, woodMeta);
			setBlockAndMetadata(world, 5, j12, 0, woodBlock, woodMeta);
		}
		for (int i17 = -3; i17 <= 2; ++i17) {
			for (k1 = -1; k1 <= 1; ++k1) {
				setBlockAndMetadata(world, i17, 4, k1, plankSlabBlock, plankSlabMeta | 8);
			}
		}
		for (j12 = 5; j12 <= 9; ++j12) {
			setBlockAndMetadata(world, -4, j12, -2, woodBlock, woodMeta);
			setBlockAndMetadata(world, 3, j12, -2, woodBlock, woodMeta);
			setBlockAndMetadata(world, -4, j12, 2, woodBlock, woodMeta);
			setBlockAndMetadata(world, 3, j12, 2, woodBlock, woodMeta);
		}
		for (int k16 = -3; k16 <= 3; ++k16) {
			setBlockAndMetadata(world, -2, 8, k16, woodBlock, woodMeta | 8);
			setBlockAndMetadata(world, 1, 8, k16, woodBlock, woodMeta | 8);
		}
		setBlockAndMetadata(world, -5, 8, 0, woodBlock, woodMeta | 4);
		setBlockAndMetadata(world, -4, 8, 0, woodBlock, woodMeta | 4);
		setBlockAndMetadata(world, 3, 8, 0, woodBlock, woodMeta | 4);
		setBlockAndMetadata(world, 4, 8, 0, woodBlock, woodMeta | 4);
		for (int k14 : new int[]{-2, 2}) {
			setBlockAndMetadata(world, -3, 6, k14, brickStairBlock, 0);
			setBlockAndMetadata(world, -3, 7, k14, brickStairBlock, 4);
			setBlockAndMetadata(world, 2, 6, k14, brickStairBlock, 1);
			setBlockAndMetadata(world, 2, 7, k14, brickStairBlock, 5);
			for (int i18 = -2; i18 <= 1; ++i18) {
				if (random.nextInt(3) != 0) {
					continue;
				}
				placeTauredainFlowerPot(world, i18, 6, k14, random);
			}
		}
		for (int i14 : new int[]{-4, 3}) {
			setBlockAndMetadata(world, i14, 6, -1, brickStairBlock, 3);
			setBlockAndMetadata(world, i14, 7, -1, brickStairBlock, 7);
			setBlockAndMetadata(world, i14, 6, 1, brickStairBlock, 2);
			setBlockAndMetadata(world, i14, 7, 1, brickStairBlock, 6);
			if (random.nextInt(3) != 0) {
				continue;
			}
			placeTauredainFlowerPot(world, i14, 6, 0, random);
		}
		for (i1 = -3; i1 <= 2; ++i1) {
			setBlockAndMetadata(world, i1, 9, -2, LOTRMod.stairsTauredainBrickGold, 2);
			setBlockAndMetadata(world, i1, 10, -1, LOTRMod.stairsTauredainBrickGold, 2);
			setBlockAndMetadata(world, i1, 9, 2, LOTRMod.stairsTauredainBrickGold, 3);
			setBlockAndMetadata(world, i1, 10, 1, LOTRMod.stairsTauredainBrickGold, 3);
		}
		for (k12 = -1; k12 <= 1; ++k12) {
			setBlockAndMetadata(world, -4, 9, k12, LOTRMod.stairsTauredainBrickGold, 1);
			setBlockAndMetadata(world, -3, 10, k12, LOTRMod.stairsTauredainBrickGold, 1);
			setBlockAndMetadata(world, 3, 9, k12, LOTRMod.stairsTauredainBrickGold, 0);
			setBlockAndMetadata(world, 2, 10, k12, LOTRMod.stairsTauredainBrickGold, 0);
		}
		for (i1 = -2; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 10, 0, brickBlock, brickMeta);
			setBlockAndMetadata(world, i1, 11, 0, brickSlabBlock, brickSlabMeta);
		}
		for (k12 = 0; k12 <= 1; ++k12) {
			setBlockAndMetadata(world, -2, 5, k12, bedBlock, 3);
			setBlockAndMetadata(world, -3, 5, k12, bedBlock, 11);
		}
		setBlockAndMetadata(world, -3, 5, -1, woodBlock, woodMeta);
		placeTauredainFlowerPot(world, -3, 6, -1, random);
		placeSkull(world, random, -2, 9, 0);
		placeSkull(world, random, 1, 9, 0);
		int[] k17 = {-1, 1};
		k1 = k17.length;
		for (j1 = 0; j1 < k1; ++j1) {
			int k14;
			k14 = k17[j1];
			setBlockAndMetadata(world, -3, 8, k14, Blocks.torch, 2);
			setBlockAndMetadata(world, 2, 8, k14, Blocks.torch, 1);
		}
		for (int j17 = 1; j17 <= 5; ++j17) {
			if (j17 <= 3) {
				setBlockAndMetadata(world, 3, j17, 1, woodBlock, woodMeta);
			}
			setBlockAndMetadata(world, 2, j17, 1, Blocks.ladder, 5);
		}
		placeWallBanner(world, 3, 3, 1, LOTRItemBanner.BannerType.TAUREDAIN, 2);
		for (i12 = -1; i12 <= 0; ++i12) {
			for (k1 = 0; k1 <= 2; ++k1) {
				setBlockAndMetadata(world, i12, 3, k1, brickSlabBlock, brickSlabMeta | 8);
				setBlockAndMetadata(world, i12, 4, k1, plankBlock, plankMeta);
			}
			for (k1 = 0; k1 <= 3; ++k1) {
				setBlockAndMetadata(world, i12, 1, k1, plankBlock, plankMeta);
				if ((i12 + k1) % 2 == 0) {
					placePlateWithCertainty(world, random, i12, 2, k1, plateBlock, LOTRFoods.TAUREDAIN);
					continue;
				}
				placeMug(world, random, i12, 2, k1, random.nextInt(4), LOTRFoods.TAUREDAIN_DRINK);
			}
		}
		for (i12 = -5; i12 <= -4; ++i12) {
			setBlockAndMetadata(world, i12, 1, 3, Blocks.furnace, 2);
		}
		setBlockAndMetadata(world, -3, 1, 3, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, -2, 1, 3, LOTRMod.tauredainTable, 0);
		for (i12 = 1; i12 <= 2; ++i12) {
			placeChest(world, random, i12, 1, 3, 2, LOTRChestContents.TAUREDAIN_HOUSE);
		}
		for (i12 = 3; i12 <= 4; ++i12) {
			setBlockAndMetadata(world, i12, 1, 3, woodBlock, woodMeta);
		}
		setBlockAndMetadata(world, -5, 1, -3, woodBlock, woodMeta);
		setBlockAndMetadata(world, -5, 1, -2, plankBlock, plankMeta);
		setBlockAndMetadata(world, -5, 1, -1, woodBlock, woodMeta);
		placeBarrel(world, random, -5, 2, -3, 4, LOTRFoods.TAUREDAIN_DRINK);
		placeMug(world, random, -5, 2, -2, 3, LOTRFoods.TAUREDAIN_DRINK);
		placeMug(world, random, -5, 2, -1, 3, LOTRFoods.TAUREDAIN_DRINK);
		setBlockAndMetadata(world, 4, 1, -3, woodBlock, woodMeta);
		setBlockAndMetadata(world, 4, 1, -2, plankBlock, plankMeta);
		setBlockAndMetadata(world, 4, 1, -1, woodBlock, woodMeta);
		for (int k18 = -3; k18 <= -1; ++k18) {
			placePlate(world, random, 4, 2, k18, plateBlock, LOTRFoods.TAUREDAIN);
		}
		setBlockAndMetadata(world, -1, 0, -4, plankBlock, plankMeta);
		setBlockAndMetadata(world, 0, 0, -4, plankBlock, plankMeta);
		setBlockAndMetadata(world, -2, 2, -3, Blocks.torch, 3);
		setBlockAndMetadata(world, 1, 2, -3, Blocks.torch, 3);
		setBlockAndMetadata(world, -5, 2, 0, Blocks.torch, 2);
		setBlockAndMetadata(world, 4, 2, 0, Blocks.torch, 1);
		setBlockAndMetadata(world, -5, 2, 3, Blocks.torch, 4);
		setBlockAndMetadata(world, -2, 2, 3, Blocks.torch, 4);
		setBlockAndMetadata(world, 1, 2, 3, Blocks.torch, 4);
		setBlockAndMetadata(world, 4, 2, 3, Blocks.torch, 4);
		placeTauredainTorch(world, -6, 6, -4);
		placeTauredainTorch(world, 5, 6, -4);
		placeTauredainTorch(world, -6, 6, 4);
		placeTauredainTorch(world, 5, 6, 4);
		setBlockAndMetadata(world, -1, 1, -4, doorBlock, 1);
		setBlockAndMetadata(world, -1, 2, -4, doorBlock, 8);
		setBlockAndMetadata(world, 0, 1, -4, doorBlock, 1);
		setBlockAndMetadata(world, 0, 2, -4, doorBlock, 9);
		LOTREntityTauredain male = new LOTREntityTauredain(world);
		male.familyInfo.setMale(true);
		male.setupNPCName();
		LOTREntityTauredain female = new LOTREntityTauredain(world);
		female.familyInfo.setMale(false);
		female.setupNPCName();
		spawnNPCAndSetHome(male, world, 0, 1, -1, 16);
		spawnNPCAndSetHome(female, world, 0, 1, -1, 16);
		return true;
	}

	@Override
	public int getOffset() {
		return 5;
	}
}
