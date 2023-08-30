package lotr.common.world.structure2;

import java.util.*;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityRohanMan;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRohanHouse extends LOTRWorldGenRohanStructure {
	public boolean hasStoneBase;
	public boolean setHasBase;

	public LOTRWorldGenRohanHouse(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int i1;
		int roofEdge;
		int k16;
		int i12;
		int j1;
		int j12;
		int k12;
		int j13;
		int i13;
		int step;
		int k13;
		int k14;
		if (!setHasBase) {
			hasStoneBase = random.nextBoolean();
		}
		setOriginAndRotation(world, i, j, k, rotation, hasStoneBase ? 10 : 6);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i14 = -6; i14 <= 5; ++i14) {
				for (k16 = -10; k16 <= 10; ++k16) {
					int j14 = getTopBlock(world, i14, k16) - 1;
					if (!isSurface(world, i14, j14, k16)) {
						return false;
					}
					if (j14 < minHeight) {
						minHeight = j14;
					}
					if (j14 > maxHeight) {
						maxHeight = j14;
					}
					if (maxHeight - minHeight <= 5) {
						continue;
					}
					return false;
				}
			}
		}
		if (hasStoneBase) {
			for (i12 = -6; i12 <= 5; ++i12) {
				for (k12 = -9; k12 <= 8; ++k12) {
					boolean stair;
					int j15;
					boolean corner = (i12 == -6 || i12 == 5) && Math.abs(k12) == 8;
					boolean stairSide = (i12 == -2 || i12 == 1) && k12 == -9;
					stair = i12 >= -1 && i12 <= 0 && k12 == -9;
					if (corner || stairSide) {
						for (j15 = 1; (j15 >= 1 || !isOpaque(world, i12, j15, k12)) && getY(j15) >= 0; --j15) {
							setBlockAndMetadata(world, i12, j15, k12, rockSlabDoubleBlock, rockSlabDoubleMeta);
							setGrassToDirt(world, i12, j15 - 1, k12);
						}
						if (!corner) {
							continue;
						}
						setBlockAndMetadata(world, i12, 2, k12, rockSlabBlock, rockSlabMeta);
						continue;
					}
					if (!stair && k12 < -8) {
						continue;
					}
					for (j15 = 1; (j15 >= 1 || !isOpaque(world, i12, j15, k12)) && getY(j15) >= 0; --j15) {
						setBlockAndMetadata(world, i12, j15, k12, brickBlock, brickMeta);
						setGrassToDirt(world, i12, j15 - 1, k12);
					}
					if (!stair) {
						continue;
					}
					setBlockAndMetadata(world, i12, 1, k12, brickStairBlock, 2);
				}
			}
			++originY;
		} else {
			for (i12 = -3; i12 <= 2; ++i12) {
				for (k12 = -5; k12 <= 4; ++k12) {
					if (k12 < -4 && (i12 < -1 || i12 > 0)) {
						continue;
					}
					for (j1 = 0; (j1 >= 0 || !isOpaque(world, i12, j1, k12)) && getY(j1) >= 0; --j1) {
						setBlockAndMetadata(world, i12, j1, k12, plank2Block, plank2Meta);
						setGrassToDirt(world, i12, j1 - 1, k12);
					}
				}
			}
		}
		for (i12 = -5; i12 <= 4; ++i12) {
			for (k12 = -7; k12 <= 7; ++k12) {
				for (j1 = 1; j1 <= 8; ++j1) {
					setAir(world, i12, j1, k12);
				}
			}
		}
		for (i12 = -3; i12 <= 2; ++i12) {
			for (k12 = -4; k12 <= 4; ++k12) {
				if (!random.nextBoolean()) {
					continue;
				}
				setBlockAndMetadata(world, i12, 1, k12, LOTRMod.thatchFloor, 0);
			}
		}
		for (i12 = -4; i12 <= 3; ++i12) {
			for (k12 = -7; k12 <= 5; ++k12) {
				boolean beam = false;
				if (k12 == -7 && (i12 == -4 || i12 == -2 || i12 == 1 || i12 == 3)) {
					beam = true;
				} else if (Math.abs(k12) == 5 && (i12 == -4 || i12 == 3)) {
					beam = true;
				}
				if (beam) {
					for (j13 = 3; (j13 >= 1 || !isOpaque(world, i12, j13, k12)) && getY(j13) >= 0; --j13) {
						setBlockAndMetadata(world, i12, j13, k12, woodBeamBlock, woodBeamMeta);
						setGrassToDirt(world, i12, j13 - 1, k12);
					}
					continue;
				}
				if (k12 < -5) {
					continue;
				}
				if (i12 == -4 || i12 == 3) {
					setBlockAndMetadata(world, i12, 1, k12, plank2Block, plank2Meta);
					setGrassToDirt(world, i12, 0, k12);
					for (j13 = 2; j13 <= 3; ++j13) {
						setBlockAndMetadata(world, i12, j13, k12, plankBlock, plankMeta);
					}
					continue;
				}
				if (Math.abs(k12) != 5) {
					continue;
				}
				setBlockAndMetadata(world, i12, 1, k12, plank2Block, plank2Meta);
				setGrassToDirt(world, i12, 0, k12);
				for (j13 = 2; j13 <= 3; ++j13) {
					setBlockAndMetadata(world, i12, j13, k12, plankBlock, plankMeta);
				}
				setBlockAndMetadata(world, i12, 4, k12, woodBeamBlock, woodBeamMeta | 4);
			}
		}
		for (int k15 = -7; k15 <= 6; ++k15) {
			roofEdge = k15 == -7 || k15 == 6 ? 1 : 0;
			for (step = 0; step <= 4; ++step) {
				j13 = 3 + step;
				Block stairBlock = roofStairBlock;
				if (step == 4 || roofEdge != 0) {
					stairBlock = plank2StairBlock;
				}
				setBlockAndMetadata(world, -5 + step, j13, k15, stairBlock, 1);
				setBlockAndMetadata(world, 4 - step, j13, k15, stairBlock, 0);
				if (roofEdge != 0 && step <= 3) {
					setBlockAndMetadata(world, -4 + step, j13, k15, stairBlock, 4);
					setBlockAndMetadata(world, 3 - step, j13, k15, stairBlock, 5);
				}
				if (k15 < -4 || k15 > 4 || step < 1 || step > 3) {
					continue;
				}
				setBlockAndMetadata(world, -4 + step, j13, k15, stairBlock, 4);
				setBlockAndMetadata(world, 3 - step, j13, k15, stairBlock, 5);
			}
		}
		for (int k161 : new int[]{-6, -5, 5}) {
			setBlockAndMetadata(world, -2, 5, k161, plankBlock, plankMeta);
			setBlockAndMetadata(world, -1, 5, k161, plankStairBlock, 4);
			setBlockAndMetadata(world, 0, 5, k161, plankStairBlock, 5);
			setBlockAndMetadata(world, 1, 5, k161, plankBlock, plankMeta);
			setBlockAndMetadata(world, -1, 6, k161, plankBlock, plankMeta);
			setBlockAndMetadata(world, 0, 6, k161, plankBlock, plankMeta);
		}
		int[] k15 = {-7, 6};
		roofEdge = k15.length;
		for (step = 0; step < roofEdge; ++step) {
			k16 = k15[step];
			setBlockAndMetadata(world, -1, 8, k16, plank2StairBlock, 0);
			setBlockAndMetadata(world, 0, 8, k16, plank2StairBlock, 1);
		}
		for (i1 = -4; i1 <= 3; ++i1) {
			if (i1 == -4 || i1 == -2 || i1 == 1 || i1 == 3) {
				setBlockAndMetadata(world, i1, 3, -7, plank2Block, plank2Meta);
			} else {
				setBlockAndMetadata(world, i1, 3, -7, plank2SlabBlock, plank2SlabMeta | 8);
			}
			if (i1 < -3 || i1 > 2) {
				continue;
			}
			setBlockAndMetadata(world, i1, 3, 6, plank2SlabBlock, plank2SlabMeta | 8);
		}
		for (i1 = -3; i1 <= 2; ++i1) {
			setBlockAndMetadata(world, i1, 4, -6, plankBlock, plankMeta);
		}
		setBlockAndMetadata(world, -4, 3, -6, plank2SlabBlock, plank2SlabMeta | 8);
		setBlockAndMetadata(world, 3, 3, -6, plank2SlabBlock, plank2SlabMeta | 8);
		setBlockAndMetadata(world, -1, 4, -6, rockSlabBlock, rockSlabMeta);
		setBlockAndMetadata(world, 0, 4, -6, rockSlabBlock, rockSlabMeta);
		setBlockAndMetadata(world, -2, 4, -7, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 1, 4, -7, fenceBlock, fenceMeta);
		for (i1 = -1; i1 <= 0; ++i1) {
			for (int j16 = 1; j16 <= 2; ++j16) {
				setAir(world, i1, j16, -5);
			}
		}
		setBlockAndMetadata(world, -1, 3, -5, plankStairBlock, 4);
		setBlockAndMetadata(world, 0, 3, -5, plankStairBlock, 5);
		for (int i15 : new int[]{-5, 4}) {
			for (int k17 : new int[]{-7, 6}) {
				for (j12 = 2; (j12 >= 1 || !isOpaque(world, i15, j12, k17)) && getY(j12) >= 0; --j12) {
					setBlockAndMetadata(world, i15, j12, k17, fenceBlock, fenceMeta);
				}
			}
		}
		for (int i15 : new int[]{-4, 3}) {
			setAir(world, i15, 2, -2);
			setBlockAndMetadata(world, i15, 3, -2, plankSlabBlock, plankSlabMeta | 8);
			setBlockAndMetadata(world, i15, 4, -2, roofBlock, roofMeta);
			setBlockAndMetadata(world, i15, 2, -3, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, i15, 2, -1, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, i15, 3, -3, plankStairBlock, 7);
			setBlockAndMetadata(world, i15, 3, -1, plankStairBlock, 6);
		}
		for (int i15 : new int[]{-5, 4}) {
			setBlockAndMetadata(world, i15, 1, -3, plankStairBlock, 7);
			setBlockAndMetadata(world, i15, 1, -2, plankSlabBlock, plankSlabMeta | 8);
			setBlockAndMetadata(world, i15, 1, -1, plankStairBlock, 6);
			for (int k18 = -3; k18 <= -1; ++k18) {
				if (!random.nextBoolean()) {
					continue;
				}
				placeFlowerPot(world, i15, 2, k18, getRandomFlower(world, random));
			}
			setBlockAndMetadata(world, i15, 3, -4, roofBlock, roofMeta);
			setBlockAndMetadata(world, i15, 3, -3, roofSlabBlock, roofSlabMeta | 8);
			setBlockAndMetadata(world, i15, 4, -3, roofSlabBlock, roofSlabMeta);
			setBlockAndMetadata(world, i15, 4, -2, roofBlock, roofMeta);
			setAir(world, i15, 3, -2);
			setBlockAndMetadata(world, i15, 3, -1, roofSlabBlock, roofSlabMeta | 8);
			setBlockAndMetadata(world, i15, 4, -1, roofSlabBlock, roofSlabMeta);
			setBlockAndMetadata(world, i15, 3, 0, roofBlock, roofMeta);
			for (int k17 : new int[]{-4, 0}) {
				for (j12 = 2; (j12 >= 1 || !isOpaque(world, i15, j12, k17)) && getY(j12) >= 0; --j12) {
					setBlockAndMetadata(world, i15, j12, k17, fenceBlock, fenceMeta);
				}
			}
		}
		setBlockAndMetadata(world, -4, 2, 3, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, -2, 2, 5, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 1, 2, 5, fenceBlock, fenceMeta);
		for (k13 = 1; k13 <= 3; ++k13) {
			for (i13 = 2; i13 <= 3; ++i13) {
				for (j1 = 5; (j1 >= 0 || !isOpaque(world, i13, j1, k13)) && getY(j1) >= 0; --j1) {
					setBlockAndMetadata(world, i13, j1, k13, brickBlock, brickMeta);
				}
			}
		}
		setBlockAndMetadata(world, 3, 5, 1, brickStairBlock, 2);
		setBlockAndMetadata(world, 3, 5, 3, brickStairBlock, 3);
		setBlockAndMetadata(world, 2, 6, 1, brickStairBlock, 2);
		setBlockAndMetadata(world, 2, 6, 3, brickStairBlock, 3);
		setBlockAndMetadata(world, 3, 6, 2, brickStairBlock, 0);
		setBlockAndMetadata(world, 1, 6, 2, brickBlock, brickMeta);
		for (int j17 = 6; j17 <= 8; ++j17) {
			setBlockAndMetadata(world, 2, j17, 2, brickBlock, brickMeta);
		}
		setBlockAndMetadata(world, 2, 9, 2, rockSlabBlock, rockSlabMeta);
		for (k13 = 0; k13 <= 4; ++k13) {
			setBlockAndMetadata(world, 2, 4, k13, brickBlock, brickMeta);
			for (int step2 = 0; step2 <= 1; ++step2) {
				setBlockAndMetadata(world, 1 - step2, 5 + step2, k13, brickStairBlock, 5);
			}
		}
		for (int k161 : new int[]{0, 4}) {
			for (int j18 = 1; j18 <= 3; ++j18) {
				setBlockAndMetadata(world, 2, j18, k161, rockWallBlock, rockWallMeta);
			}
		}
		setBlockAndMetadata(world, 2, 0, 2, LOTRMod.hearth, 0);
		setBlockAndMetadata(world, 2, 1, 2, Blocks.fire, 0);
		setBlockAndMetadata(world, 2, 2, 2, Blocks.furnace, 5);
		setBlockAndMetadata(world, 2, 3, 2, brickCarvedBlock, brickCarvedMeta);
		setBlockAndMetadata(world, 1, 0, 2, rockSlabDoubleBlock, rockSlabDoubleMeta);
		setBlockAndMetadata(world, 1, 1, 1, brickBlock, brickMeta);
		setBlockAndMetadata(world, 1, 1, 2, barsBlock, 0);
		setBlockAndMetadata(world, 1, 1, 3, brickBlock, brickMeta);
		for (k14 = 1; k14 <= 3; ++k14) {
			setBlockAndMetadata(world, 1, 2, k14, rockSlabBlock, rockSlabMeta);
		}
		placeWeaponRack(world, 1, 3, 2, 7, getRandomRohanWeapon(random));
		spawnItemFrame(world, 2, 4, 2, 3, getRohanFramedItem(random));
		for (int i16 = -2; i16 <= 1; ++i16) {
			setBlockAndMetadata(world, i16, 5, -4, plank2SlabBlock, plank2SlabMeta);
		}
		setBlockAndMetadata(world, -3, 1, -4, plankStairBlock, 3);
		setBlockAndMetadata(world, -3, 1, -3, plankStairBlock, 2);
		setBlockAndMetadata(world, -3, 1, -2, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, -3, 1, -1, tableBlock, 0);
		placeChest(world, random, -3, 1, 0, 4, LOTRChestContents.ROHAN_HOUSE);
		setBlockAndMetadata(world, 2, 1, -4, plankStairBlock, 7);
		setBlockAndMetadata(world, 2, 1, -3, plankSlabBlock, plankSlabMeta | 8);
		setBlockAndMetadata(world, 2, 1, -2, plankStairBlock, 6);
		setBlockAndMetadata(world, 2, 1, -1, Blocks.cauldron, 3);
		placeBarrel(world, random, 2, 2, -4, 5, LOTRFoods.ROHAN_DRINK);
		placeMug(world, random, 2, 2, -3, 1, LOTRFoods.ROHAN_DRINK);
		if (random.nextBoolean()) {
			placePlateWithCertainty(world, random, 2, 2, -2, plateBlock, LOTRFoods.ROHAN);
		} else {
			setBlockAndMetadata(world, 2, 2, -2, plateBlock, 0);
		}
		if (random.nextBoolean()) {
			setBlockAndMetadata(world, 3, 2, -2, getRandomCakeBlock(random), 0);
		}
		for (k14 = 2; k14 <= 3; ++k14) {
			setBlockAndMetadata(world, -2, 1, k14, bedBlock, 3);
			setBlockAndMetadata(world, -3, 1, k14, bedBlock, 11);
			setBlockAndMetadata(world, -3, 3, k14, plank2SlabBlock, plank2SlabMeta | 8);
		}
		for (int k161 : new int[]{1, 4}) {
			for (int j19 = 1; j19 <= 2; ++j19) {
				setBlockAndMetadata(world, -3, j19, k161, fenceBlock, fenceMeta);
			}
			setBlockAndMetadata(world, -3, 3, k161, plank2Block, plank2Meta);
		}
		setBlockAndMetadata(world, -3, 3, -4, Blocks.torch, 2);
		setBlockAndMetadata(world, 2, 3, -4, Blocks.torch, 1);
		setBlockAndMetadata(world, -2, 4, 4, Blocks.torch, 4);
		setBlockAndMetadata(world, 1, 4, 4, Blocks.torch, 4);
		spawnItemFrame(world, -2, 3, -5, 0, getRohanFramedItem(random));
		spawnItemFrame(world, 1, 3, -5, 0, getRohanFramedItem(random));
		if (random.nextInt(3) != 0) {
			for (int i17 = -1; i17 <= 0; ++i17) {
				for (k12 = -3; k12 <= -1; ++k12) {
					setBlockAndMetadata(world, i17, 1, k12, carpetBlock, carpetMeta);
				}
			}
		}
		if (random.nextInt(3) != 0) {
			boolean hayOrWood = random.nextBoolean();
			for (i13 = -1; i13 <= 1; ++i13) {
				for (int k19 = 6; k19 <= 7; ++k19) {
					if (k19 != 6 && i13 != 0) {
						continue;
					}
					j13 = 1;
					while (!isOpaque(world, i13, j13 - 1, k19) && getY(j13) >= 0) {
						--j13;
					}
					int j2 = j13;
					if (i13 == 0 && k19 == 6) {
						++j2;
					}
					for (int j3 = j13; j3 <= j2; ++j3) {
						if (hayOrWood) {
							setBlockAndMetadata(world, i13, j3, k19, Blocks.hay_block, 0);
							continue;
						}
						setBlockAndMetadata(world, i13, j3, k19, woodBeamBlock, woodBeamMeta | 8);
					}
					setGrassToDirt(world, i13, j13 - 1, k19);
				}
			}
		}
		if (random.nextBoolean()) {
			int i15;
			int j110 = 2;
			k12 = 6;
			ArrayList<Integer> chestCoords = new ArrayList<>();
			for (i15 = -4; i15 <= 3; ++i15) {
				if (isOpaque(world, i15, j110, k12)) {
					continue;
				}
				chestCoords.add(i15);
			}
			if (!chestCoords.isEmpty()) {
				i15 = chestCoords.get(random.nextInt(chestCoords.size()));
				while (!isOpaque(world, i15, j110 - 1, k12) && getY(j110) >= 0) {
					--j110;
				}
				placeChest(world, random, i15, j110, k12, 3, LOTRChestContents.ROHAN_HOUSE);
			}
		}
		int men = 1 + random.nextInt(2);
		for (int l = 0; l < men; ++l) {
			LOTREntityRohanMan rohirrim = new LOTREntityRohanMan(world);
			spawnNPCAndSetHome(rohirrim, world, 0, 1, 0, 16);
		}
		return true;
	}

	public LOTRWorldGenRohanHouse setHasBase(boolean flag) {
		hasStoneBase = flag;
		setHasBase = true;
		return this;
	}
}
