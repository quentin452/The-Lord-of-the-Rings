package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.passive.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGondorBarn extends LOTRWorldGenGondorStructure {
	public LOTRWorldGenGondorBarn(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int i1;
		int k1;
		int i12;
		int k122;
		int j1;
		int k13;
		setOriginAndRotation(world, i, j, k, rotation, 1);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i13 = -12; i13 <= 5; ++i13) {
				for (k122 = -2; k122 <= 15; ++k122) {
					int j12 = getTopBlock(world, i13, k122) - 1;
					if (!isSurface(world, i13, j12, k122)) {
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
		for (int i14 = -12; i14 <= 4; ++i14) {
			for (k1 = -2; k1 <= 15; ++k1) {
				setBlockAndMetadata(world, i14, 0, k1, Blocks.grass, 0);
				j1 = -1;
				while (!isOpaque(world, i14, j1, k1) && getY(j1) >= 0) {
					setBlockAndMetadata(world, i14, j1, k1, Blocks.dirt, 0);
					setGrassToDirt(world, i14, j1 - 1, k1);
					--j1;
				}
				for (j1 = 1; j1 <= 10; ++j1) {
					setAir(world, i14, j1, k1);
				}
			}
		}
		for (int i15 : new int[]{-4, 4}) {
			for (int k14 = 0; k14 <= 13; ++k14) {
				int j13;
				setBlockAndMetadata(world, i15, 1, k14, rockBlock, rockMeta);
				if (k14 == 0 || k14 == 4 || k14 == 9 || k14 == 13) {
					for (j13 = 2; j13 <= 5; ++j13) {
						setBlockAndMetadata(world, i15, j13, k14, woodBeamBlock, woodBeamMeta);
					}
					continue;
				}
				for (j13 = 2; j13 <= 5; ++j13) {
					setBlockAndMetadata(world, i15, j13, k14, plankBlock, plankMeta);
				}
			}
			setBlockAndMetadata(world, i15, 4, 1, plankStairBlock, 7);
			setBlockAndMetadata(world, i15, 4, 2, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, i15, 4, 3, plankStairBlock, 6);
			setBlockAndMetadata(world, i15, 4, 10, plankStairBlock, 7);
			setBlockAndMetadata(world, i15, 4, 11, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, i15, 4, 12, plankStairBlock, 6);
		}
		for (int k1221 : new int[]{0, 13}) {
			for (i1 = -3; i1 <= 3; ++i1) {
				int j14;
				int i2 = Math.abs(i1);
				if (i2 <= 1) {
					setBlockAndMetadata(world, i1, 1, k1221, fenceGateBlock, 0);
				} else {
					setBlockAndMetadata(world, i1, 1, k1221, rockBlock, rockMeta);
				}
				if (i2 == 2) {
					for (j14 = 2; j14 <= 7; ++j14) {
						setBlockAndMetadata(world, i1, j14, k1221, woodBeamBlock, woodBeamMeta);
					}
				}
				if (i2 != 3) {
					continue;
				}
				for (j14 = 2; j14 <= 5; ++j14) {
					setBlockAndMetadata(world, i1, j14, k1221, plankBlock, plankMeta);
				}
				for (j14 = 6; j14 <= 8; ++j14) {
					setBlockAndMetadata(world, i1, j14, k1221, wallBlock, wallMeta);
				}
			}
			setBlockAndMetadata(world, -1, 4, k1221, plankStairBlock, 4);
			setBlockAndMetadata(world, 1, 4, k1221, plankStairBlock, 5);
			for (i1 = -1; i1 <= 1; ++i1) {
				setBlockAndMetadata(world, i1, 5, k1221, plankBlock, plankMeta);
				setBlockAndMetadata(world, i1, 6, k1221, wallBlock, wallMeta);
				setBlockAndMetadata(world, i1, 8, k1221, wallBlock, wallMeta);
				setBlockAndMetadata(world, i1, 9, k1221, wallBlock, wallMeta);
			}
			setBlockAndMetadata(world, -1, 7, k1221, wallBlock, wallMeta);
			setBlockAndMetadata(world, 0, 7, k1221, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, 1, 7, k1221, wallBlock, wallMeta);
		}
		int[] i14 = {-1, 14};
		k1 = i14.length;
		for (j1 = 0; j1 < k1; ++j1) {
			k122 = i14[j1];
			for (i1 = -3; i1 <= 3; ++i1) {
				setBlockAndMetadata(world, i1, 6, k122, woodBeamBlock, woodBeamMeta | 4);
			}
		}
		setBlockAndMetadata(world, 0, 5, -1, plankStairBlock, 6);
		setBlockAndMetadata(world, 0, 5, 14, plankStairBlock, 7);
		for (k13 = -1; k13 <= 14; ++k13) {
			setBlockAndMetadata(world, -2, 8, k13, woodBeamBlock, woodBeamMeta | 8);
			setBlockAndMetadata(world, 2, 8, k13, woodBeamBlock, woodBeamMeta | 8);
			setBlockAndMetadata(world, 0, 10, k13, woodBeamBlock, woodBeamMeta | 8);
			setBlockAndMetadata(world, -5, 5, k13, roofStairBlock, 1);
			setBlockAndMetadata(world, -4, 6, k13, roofBlock, roofMeta);
			setBlockAndMetadata(world, -4, 7, k13, roofStairBlock, 1);
			setBlockAndMetadata(world, -3, 8, k13, roofStairBlock, 1);
			setBlockAndMetadata(world, -2, 9, k13, roofStairBlock, 1);
			setBlockAndMetadata(world, -1, 10, k13, roofStairBlock, 1);
			setBlockAndMetadata(world, 0, 11, k13, roofSlabBlock, roofSlabMeta);
			setBlockAndMetadata(world, 1, 10, k13, roofStairBlock, 0);
			setBlockAndMetadata(world, 2, 9, k13, roofStairBlock, 0);
			setBlockAndMetadata(world, 3, 8, k13, roofStairBlock, 0);
			setBlockAndMetadata(world, 4, 7, k13, roofStairBlock, 0);
			setBlockAndMetadata(world, 4, 6, k13, roofBlock, roofMeta);
			setBlockAndMetadata(world, 5, 5, k13, roofStairBlock, 0);
			if (k13 != -1 && k13 != 14) {
				continue;
			}
			setBlockAndMetadata(world, -4, 5, k13, roofStairBlock, 4);
			setBlockAndMetadata(world, -3, 7, k13, roofStairBlock, 4);
			setBlockAndMetadata(world, -1, 9, k13, roofStairBlock, 4);
			setBlockAndMetadata(world, 1, 9, k13, roofStairBlock, 5);
			setBlockAndMetadata(world, 3, 7, k13, roofStairBlock, 5);
			setBlockAndMetadata(world, 4, 5, k13, roofStairBlock, 5);
		}
		for (k13 = 1; k13 <= 3; ++k13) {
			setBlockAndMetadata(world, -3, 1, k13, Blocks.hay_block, 0);
		}
		for (k13 = 1; k13 <= 2; ++k13) {
			setBlockAndMetadata(world, -3, 2, k13, Blocks.hay_block, 0);
			setBlockAndMetadata(world, -2, 1, k13, Blocks.hay_block, 0);
		}
		for (k13 = 10; k13 <= 12; ++k13) {
			setBlockAndMetadata(world, -3, 1, k13, Blocks.hay_block, 0);
			setBlockAndMetadata(world, 3, 1, k13, Blocks.hay_block, 0);
		}
		for (k13 = 11; k13 <= 12; ++k13) {
			setBlockAndMetadata(world, -3, 2, k13, Blocks.hay_block, 0);
			setBlockAndMetadata(world, -2, 1, k13, Blocks.hay_block, 0);
			setBlockAndMetadata(world, 2, 1, k13, Blocks.hay_block, 0);
			setBlockAndMetadata(world, 3, 2, k13, Blocks.hay_block, 0);
		}
		setBlockAndMetadata(world, -3, 1, 4, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, -3, 1, 9, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, 3, 1, 9, Blocks.crafting_table, 0);
		for (int j15 = 2; j15 <= 4; ++j15) {
			setBlockAndMetadata(world, -3, j15, 4, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, -3, j15, 9, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, 3, j15, 9, fenceBlock, fenceMeta);
		}
		for (k13 = 1; k13 <= 12; ++k13) {
			for (int i16 = -3; i16 <= 3; ++i16) {
				setBlockAndMetadata(world, i16, 5, k13, plankBlock, plankMeta);
			}
			setBlockAndMetadata(world, -3, 7, k13, plankSlabBlock, plankSlabMeta | 8);
			setBlockAndMetadata(world, -1, 9, k13, plankSlabBlock, plankSlabMeta | 8);
			setBlockAndMetadata(world, 1, 9, k13, plankSlabBlock, plankSlabMeta | 8);
			setBlockAndMetadata(world, 3, 7, k13, plankSlabBlock, plankSlabMeta | 8);
		}
		setBlockAndMetadata(world, 0, 4, 4, LOTRMod.chandelier, 1);
		setBlockAndMetadata(world, 0, 4, 9, LOTRMod.chandelier, 1);
		for (int step = 0; step <= 3; ++step) {
			setBlockAndMetadata(world, 3, 1 + step, 2 + step, plankStairBlock, 2);
			setBlockAndMetadata(world, 3, 1 + step, 3 + step, plankStairBlock, 7);
		}
		setBlockAndMetadata(world, 2, 4, 6, plankStairBlock, 5);
		for (k13 = 3; k13 <= 5; ++k13) {
			setAir(world, 3, 5, k13);
		}
		setAir(world, 3, 5, 6);
		setAir(world, 3, 7, 6);
		setBlockAndMetadata(world, 2, 5, 6, plankStairBlock, 0);
		setBlockAndMetadata(world, 3, 6, 2, fenceBlock, fenceMeta);
		for (k13 = 2; k13 <= 5; ++k13) {
			setBlockAndMetadata(world, 2, 6, k13, fenceBlock, fenceMeta);
		}
		setBlockAndMetadata(world, 1, 6, 5, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 1, 6, 6, fenceGateBlock, 1);
		setBlockAndMetadata(world, 1, 6, 7, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 2, 6, 7, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 3, 6, 7, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 3, 6, 9, bedBlock, 2);
		setBlockAndMetadata(world, 3, 6, 8, bedBlock, 10);
		setBlockAndMetadata(world, 2, 6, 12, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, 3, 6, 12, tableBlock, 0);
		placeChest(world, random, 3, 6, 11, 5, LOTRChestContents.GONDOR_HOUSE);
		for (i12 = -3; i12 <= -2; ++i12) {
			for (k1 = 7; k1 <= 8; ++k1) {
				setBlockAndMetadata(world, i12, 6, k1, plankBlock, plankMeta);
			}
		}
		placeBarrel(world, random, -3, 6, 6, 4, LOTRFoods.GONDOR_DRINK);
		placeMug(world, random, -2, 7, 7, 3, LOTRFoods.GONDOR_DRINK);
		placePlateWithCertainty(world, random, -2, 7, 8, plateBlock, LOTRFoods.GONDOR);
		setBlockAndMetadata(world, 0, 9, 4, LOTRMod.chandelier, 1);
		setBlockAndMetadata(world, 0, 9, 9, LOTRMod.chandelier, 1);
		for (k13 = 1; k13 <= 5; ++k13) {
			setBlockAndMetadata(world, -3, 6, k13, Blocks.hay_block, 0);
		}
		for (k13 = 1; k13 <= 4; ++k13) {
			setBlockAndMetadata(world, -2, 6, k13, Blocks.hay_block, 0);
		}
		for (k13 = 2; k13 <= 3; ++k13) {
			setBlockAndMetadata(world, -2, 7, k13, Blocks.hay_block, 0);
			setBlockAndMetadata(world, -1, 6, k13, Blocks.hay_block, 0);
		}
		setBlockAndMetadata(world, -3, 6, 11, Blocks.hay_block, 0);
		setBlockAndMetadata(world, -3, 6, 12, Blocks.hay_block, 0);
		setBlockAndMetadata(world, -3, 7, 12, Blocks.hay_block, 0);
		for (k13 = 10; k13 <= 12; ++k13) {
			setBlockAndMetadata(world, -2, 6, k13, Blocks.hay_block, 0);
			setBlockAndMetadata(world, -1, 6, k13, Blocks.hay_block, 0);
		}
		setBlockAndMetadata(world, -2, 7, 11, Blocks.hay_block, 0);
		setBlockAndMetadata(world, -1, 7, 11, Blocks.hay_block, 0);
		setBlockAndMetadata(world, 0, 6, 11, Blocks.hay_block, 0);
		if (random.nextInt(3) == 0) {
			if (random.nextBoolean()) {
				placeChest(world, random, -2, 6, 3, 4, LOTRChestContents.GONDOR_HOUSE_TREASURE);
			} else {
				placeChest(world, random, -1, 6, 11, 4, LOTRChestContents.GONDOR_HOUSE_TREASURE);
			}
		}
		for (i12 = -4; i12 <= 4; ++i12) {
			for (k1 = 0; k1 <= 13; ++k1) {
				if (!isOpaque(world, i12, 1, k1)) {
					continue;
				}
				setGrassToDirt(world, i12, 0, k1);
			}
		}
		int animals = 3 + random.nextInt(6);
		for (int l = 0; l < animals; ++l) {
			EntityAnimal animal = getRandomAnimal(world, random);
			spawnNPCAndSetHome(animal, world, 0, 1, 6, 0);
			animal.detachHome();
		}
		for (k1 = 1; k1 <= 12; ++k1) {
			setBlockAndMetadata(world, -10, 1, k1, rockWallBlock, rockWallMeta);
		}
		for (int k14 : new int[]{0, 13}) {
			setBlockAndMetadata(world, -10, 1, k14, rockWallBlock, rockWallMeta);
			setBlockAndMetadata(world, -10, 2, k14, Blocks.torch, 5);
			setBlockAndMetadata(world, -9, 1, k14, fenceGateBlock, 0);
			for (int i17 = -8; i17 <= -5; ++i17) {
				setBlockAndMetadata(world, i17, 1, k14, rockWallBlock, rockWallMeta);
			}
			setBlockAndMetadata(world, -5, 2, k14, Blocks.torch, 5);
		}
		for (int i18 = -9; i18 <= -5; ++i18) {
			for (int k15 = 1; k15 <= 12; ++k15) {
				if (i18 == -5 && k15 >= 2 && k15 <= 11) {
					setBlockAndMetadata(world, -5, -1, k15, Blocks.dirt, 0);
					setBlockAndMetadata(world, -5, 0, k15, Blocks.water, 0);
					setBlockAndMetadata(world, -5, 1, k15, rockSlabBlock, rockSlabMeta);
					continue;
				}
				if (i18 >= -8 && k15 >= 2 && k15 <= 11) {
					setBlockAndMetadata(world, i18, 0, k15, Blocks.farmland, 7);
					setBlockAndMetadata(world, i18, 1, k15, cropBlock, cropMeta);
					continue;
				}
				setBlockAndMetadata(world, i18, 0, k15, LOTRMod.dirtPath, 0);
			}
		}
		setBlockAndMetadata(world, -10, 2, 6, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, -10, 3, 6, Blocks.hay_block, 0);
		setBlockAndMetadata(world, -10, 4, 6, Blocks.pumpkin, 3);
		LOTREntityGondorFarmer farmer = new LOTREntityGondorFarmer(world);
		spawnNPCAndSetHome(farmer, world, 0, 6, 8, 16);
		int farmhands = 1 + random.nextInt(3);
		for (int l = 0; l < farmhands; ++l) {
			LOTREntityGondorFarmhand farmhand = new LOTREntityGondorFarmhand(world);
			spawnNPCAndSetHome(farmhand, world, -7, 1, 6, 12);
			farmhand.seedsItem = seedItem;
		}
		return true;
	}

	public static EntityAnimal getRandomAnimal(World world, Random random) {
		int animal = random.nextInt(4);
		switch (animal) {
			case 0:
				return new EntityCow(world);
			case 1:
				return new EntityPig(world);
			case 2:
				return new EntitySheep(world);
			case 3:
				return new EntityChicken(world);
			default:
				break;
		}
		return null;
	}
}
