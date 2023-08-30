package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityGondorMan;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGondorStables extends LOTRWorldGenGondorStructure {
	public LOTRWorldGenGondorStables(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int j1;
		int i2;
		int j12;
		int i1;
		int i12;
		int k12;
		int i132;
		int j13;
		setOriginAndRotation(world, i, j, k, rotation, 6);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i14 = -5; i14 <= 5; ++i14) {
				for (int k13 = -6; k13 <= 6; ++k13) {
					j12 = getTopBlock(world, i14, k13) - 1;
					if (!isSurface(world, i14, j12, k13)) {
						return false;
					}
					if (j12 < minHeight) {
						minHeight = j12;
					}
					if (j12 > maxHeight) {
						maxHeight = j12;
					}
					if (maxHeight - minHeight <= 5) {
						continue;
					}
					return false;
				}
			}
		}
		for (i12 = -4; i12 <= 4; ++i12) {
			for (k1 = -5; k1 <= 5; ++k1) {
				for (j12 = 0; (j12 == 0 || !isOpaque(world, i12, j12, k1)) && getY(j12) >= 0; --j12) {
					setBlockAndMetadata(world, i12, j12, k1, brickBlock, brickMeta);
					setGrassToDirt(world, i12, j12 - 1, k1);
				}
				for (j12 = 1; j12 <= 7; ++j12) {
					setAir(world, i12, j12, k1);
				}
			}
		}
		for (i12 = -5; i12 <= 5; ++i12) {
			setBlockAndMetadata(world, i12, 4, -6, roofSlabBlock, roofSlabMeta);
			setBlockAndMetadata(world, i12, 4, -5, roofSlabBlock, roofSlabMeta | 8);
			setBlockAndMetadata(world, i12, 5, -4, roofSlabBlock, roofSlabMeta);
			setBlockAndMetadata(world, i12, 5, -3, roofSlabBlock, roofSlabMeta | 8);
			setBlockAndMetadata(world, i12, 6, -2, roofSlabBlock, roofSlabMeta);
			setBlockAndMetadata(world, i12, 6, -1, roofSlabBlock, roofSlabMeta | 8);
		}
		for (i12 = -4; i12 <= 4; ++i12) {
			i2 = Math.abs(i12);
			if (i2 == 4 || i2 == 0) {
				for (int j14 = 1; j14 <= 3; ++j14) {
					setBlockAndMetadata(world, i12, j14, -5, woodBeamBlock, woodBeamMeta);
				}
				setBlockAndMetadata(world, i12, 3, -6, plankStairBlock, 6);
				for (k12 = -4; k12 <= -1; ++k12) {
					setBlockAndMetadata(world, i12, 1, k12, plankBlock, plankMeta);
					setBlockAndMetadata(world, i12, 2, k12, fenceBlock, fenceMeta);
					setBlockAndMetadata(world, i12, 3, k12, fenceBlock, fenceMeta);
				}
				for (k12 = -5; k12 <= -1; ++k12) {
					setBlockAndMetadata(world, i12, 4, k12, roofBlock, roofMeta);
					if (k12 >= -3) {
						setBlockAndMetadata(world, i12, 5, k12, roofBlock, roofMeta);
					}
					if (k12 < -1) {
						continue;
					}
					setBlockAndMetadata(world, i12, 6, k12, roofBlock, roofMeta);
				}
				continue;
			}
			setBlockAndMetadata(world, i12, 2, -5, fenceGateBlock, 2);
			for (k12 = -4; k12 <= -1; ++k12) {
				setBlockAndMetadata(world, i12, 0, k12, rockBlock, rockMeta);
				if (random.nextInt(3) == 0) {
					continue;
				}
				setBlockAndMetadata(world, i12, 1, k12, LOTRMod.thatchFloor, 0);
			}
		}
		int[] i15 = {-4, 4};
		i2 = i15.length;
		for (k12 = 0; k12 < i2; ++k12) {
			int k14;
			int j15;
			i132 = i15[k12];
			for (j12 = 1; j12 <= 6; ++j12) {
				setBlockAndMetadata(world, i132, j12, 0, pillarBlock, pillarMeta);
			}
			for (j12 = 1; j12 <= 5; ++j12) {
				setBlockAndMetadata(world, i132, j12, 5, pillarBlock, pillarMeta);
			}
			for (k14 = 1; k14 <= 4; ++k14) {
				for (j15 = 1; j15 <= 6; ++j15) {
					setBlockAndMetadata(world, i132, j15, k14, brickBlock, brickMeta);
				}
			}
			for (k14 = 2; k14 <= 3; ++k14) {
				for (j15 = 1; j15 <= 2; ++j15) {
					setAir(world, i132, j15, k14);
				}
			}
			setBlockAndMetadata(world, i132, 3, 2, brickStairBlock, 7);
			setBlockAndMetadata(world, i132, 3, 3, brickStairBlock, 6);
			for (k14 = 1; k14 <= 4; ++k14) {
				setBlockAndMetadata(world, i132, 4, k14, woodBeamBlock, woodBeamMeta | 8);
			}
		}
		for (int i16 = -3; i16 <= 3; ++i16) {
			for (j1 = 1; j1 <= 6; ++j1) {
				if (j1 >= 2 && j1 <= 4) {
					setBlockAndMetadata(world, i16, j1, 0, plankBlock, plankMeta);
					continue;
				}
				setBlockAndMetadata(world, i16, j1, 0, brickBlock, brickMeta);
			}
			for (j1 = 1; j1 <= 5; ++j1) {
				setBlockAndMetadata(world, i16, j1, 5, brickBlock, brickMeta);
			}
		}
		int[] i16 = {-2, 2};
		j1 = i16.length;
		for (k12 = 0; k12 < j1; ++k12) {
			i132 = i16[k12];
			setBlockAndMetadata(world, i132, 1, -1, Blocks.hay_block, 0);
			setBlockAndMetadata(world, i132, 4, -1, Blocks.torch, 4);
			LOTREntityHorse horse = new LOTREntityHorse(world);
			spawnNPCAndSetHome(horse, world, i132, 1, -3, 0);
			horse.setHorseType(0);
			horse.saddleMountForWorldGen();
			horse.detachHome();
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			for (k1 = 1; k1 <= 4; ++k1) {
				setBlockAndMetadata(world, i1, 4, k1, plankBlock, plankMeta);
			}
		}
		for (i1 = -5; i1 <= 5; ++i1) {
			setBlockAndMetadata(world, i1, 7, 0, brick2StairBlock, 2);
			for (k1 = 1; k1 <= 3; ++k1) {
				setBlockAndMetadata(world, i1, 7, k1, brick2Block, brick2Meta);
			}
			setBlockAndMetadata(world, i1, 7, 4, brick2StairBlock, 3);
			setBlockAndMetadata(world, i1, 6, 5, brick2StairBlock, 3);
			setBlockAndMetadata(world, i1, 5, 6, brick2StairBlock, 3);
			if (Math.abs(i1) != 5) {
				continue;
			}
			setBlockAndMetadata(world, i1, 6, 4, brick2StairBlock, 6);
			setBlockAndMetadata(world, i1, 5, 5, brick2StairBlock, 6);
		}
		for (int i1321 : new int[]{-3, 1}) {
			setBlockAndMetadata(world, i1321, 2, 5, brickStairBlock, 0);
			setBlockAndMetadata(world, i1321, 3, 5, brickStairBlock, 4);
			setBlockAndMetadata(world, i1321 + 1, 2, 5, brickWallBlock, brickWallMeta);
			setBlockAndMetadata(world, i1321 + 1, 3, 5, brickWallBlock, brickWallMeta);
			setBlockAndMetadata(world, i1321 + 2, 2, 5, brickStairBlock, 1);
			setBlockAndMetadata(world, i1321 + 2, 3, 5, brickStairBlock, 5);
		}
		setBlockAndMetadata(world, 0, 2, 5, LOTRMod.brick, 5);
		setBlockAndMetadata(world, -3, 1, 4, plankBlock, plankMeta);
		placeFlowerPot(world, -3, 2, 4, getRandomFlower(world, random));
		setBlockAndMetadata(world, -2, 1, 4, plankSlabBlock, plankSlabMeta | 8);
		placeMug(world, random, -2, 2, 4, 0, LOTRFoods.GONDOR_DRINK);
		setBlockAndMetadata(world, -1, 1, 4, Blocks.cauldron, 3);
		setBlockAndMetadata(world, 0, 1, 4, plankSlabBlock, plankSlabMeta | 8);
		placePlateWithCertainty(world, random, 0, 2, 4, plateBlock, LOTRFoods.GONDOR);
		setBlockAndMetadata(world, 1, 1, 4, Blocks.furnace, 2);
		setBlockAndMetadata(world, 2, 1, 4, plankSlabBlock, plankSlabMeta | 8);
		placeMug(world, random, 2, 2, 4, 0, LOTRFoods.GONDOR_DRINK);
		setBlockAndMetadata(world, 3, 1, 4, plankBlock, plankMeta);
		placeFlowerPot(world, 3, 2, 4, getRandomFlower(world, random));
		setBlockAndMetadata(world, -3, 1, 1, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, -2, 1, 1, tableBlock, 0);
		setBlockAndMetadata(world, 3, 1, 1, plankBlock, plankMeta);
		placeMug(world, random, 3, 2, 1, 2, LOTRFoods.GONDOR_DRINK);
		setBlockAndMetadata(world, 0, 3, 3, LOTRMod.chandelier, 1);
		for (j13 = 1; j13 <= 6; ++j13) {
			setBlockAndMetadata(world, 0, j13, 0, pillarBlock, pillarMeta);
		}
		for (j13 = 1; j13 <= 4; ++j13) {
			setBlockAndMetadata(world, 0, j13, 1, Blocks.ladder, 3);
		}
		for (int i17 = -3; i17 <= 3; ++i17) {
			setBlockAndMetadata(world, i17, 6, 4, brick2StairBlock, 6);
		}
		setBlockAndMetadata(world, 3, 5, 1, Blocks.hay_block, 0);
		setBlockAndMetadata(world, 3, 5, 2, Blocks.hay_block, 0);
		setBlockAndMetadata(world, 3, 5, 3, Blocks.hay_block, 0);
		setBlockAndMetadata(world, 3, 6, 1, Blocks.hay_block, 0);
		setBlockAndMetadata(world, 2, 5, 1, Blocks.hay_block, 0);
		setBlockAndMetadata(world, 2, 5, 2, Blocks.hay_block, 0);
		if (random.nextInt(3) == 0) {
			placeChest(world, random, 3, 5, 1, 5, LOTRChestContents.GONDOR_HOUSE_TREASURE);
		}
		setBlockAndMetadata(world, 3, 6, 2, Blocks.torch, 1);
		setBlockAndMetadata(world, -2, 5, 3, bedBlock, 3);
		setBlockAndMetadata(world, -3, 5, 3, bedBlock, 11);
		placeChest(world, random, -3, 5, 1, 3, LOTRChestContents.GONDOR_HOUSE);
		placeBarrel(world, random, -2, 5, 1, 3, LOTRFoods.GONDOR_DRINK);
		LOTREntityGondorMan gondorian = new LOTREntityGondorMan(world);
		spawnNPCAndSetHome(gondorian, world, 0, 1, 2, 8);
		return true;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		bedBlock = Blocks.bed;
	}
}
