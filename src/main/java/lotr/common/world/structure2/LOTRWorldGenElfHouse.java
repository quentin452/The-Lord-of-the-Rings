package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityGaladhrimElf;
import lotr.common.world.feature.LOTRWorldGenMallornExtreme;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenElfHouse extends LOTRWorldGenStructureBase2 {
	public LOTRWorldGenElfHouse(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int j12;
		int i1;
		int k1;
		int k12;
		int k13;
		int k14;
		setOriginAndRotation(world, i, j, k, rotation, usingPlayer != null ? 2 : 0);
		if (usingPlayer != null) {
			LOTRWorldGenMallornExtreme treeGen = new LOTRWorldGenMallornExtreme(true);
			int i12 = 0;
			j12 = 0;
			k14 = 0;
			int height = treeGen.generateAndReturnHeight(world, random, getX(i12, k14), getY(j12), getZ(i12, k14), true);
			originY += MathHelper.floor_double(height * MathHelper.randomFloatClamp(random, LOTRWorldGenMallornExtreme.HOUSE_HEIGHT_MIN, LOTRWorldGenMallornExtreme.HOUSE_HEIGHT_MAX));
		}
		if (restrictions) {
			for (int i13 = -8; i13 <= 8; ++i13) {
				for (j1 = -3; j1 <= 6; ++j1) {
					for (k1 = -8; k1 <= 8; ++k1) {
						if (Math.abs(i13) <= 2 && Math.abs(k1) <= 2 || isAir(world, i13, j1, k1)) {
							continue;
						}
						return false;
					}
				}
			}
		} else if (usingPlayer != null) {
			for (int i14 = -2; i14 <= 2; ++i14) {
				for (k12 = -2; k12 <= 2; ++k12) {
					j12 = 0;
					while (!isOpaque(world, i14, j12, k12) && getY(j12) >= 0) {
						setBlockAndMetadata(world, i14, j12, k12, LOTRMod.wood, 1);
						--j12;
					}
				}
			}
		}
		for (i1 = -7; i1 <= 7; ++i1) {
			for (j1 = 1; j1 <= 4; ++j1) {
				for (k1 = -7; k1 <= 7; ++k1) {
					setBlockAndMetadata(world, i1, j1, k1, Blocks.air, 0);
				}
			}
		}
		for (i1 = -2; i1 <= 2; ++i1) {
			for (j1 = -1; j1 <= 5; ++j1) {
				for (k1 = -2; k1 <= 2; ++k1) {
					setBlockAndMetadata(world, i1, j1, k1, LOTRMod.wood, 1);
					if (j1 < 1 || j1 > 2 || Math.abs(i1) != 2 || Math.abs(k1) != 2) {
						continue;
					}
					setBlockAndMetadata(world, i1, j1, k1, LOTRMod.fence, 1);
				}
			}
		}
		for (i1 = -6; i1 <= 6; ++i1) {
			for (k12 = -6; k12 <= 6; ++k12) {
				if (Math.abs(i1) <= 2 && Math.abs(k12) <= 2 || Math.abs(i1) == 6 || Math.abs(k12) == 6) {
					continue;
				}
				setBlockAndMetadata(world, i1, 0, k12, LOTRMod.planks, 1);
			}
		}
		for (i1 = -5; i1 <= 5; ++i1) {
			setBlockAndMetadata(world, i1, 0, -6, LOTRMod.planks, 1);
			setBlockAndMetadata(world, i1, 0, 6, LOTRMod.planks, 1);
		}
		for (k13 = -5; k13 <= 5; ++k13) {
			setBlockAndMetadata(world, -6, 0, k13, LOTRMod.planks, 1);
			setBlockAndMetadata(world, 6, 0, k13, LOTRMod.planks, 1);
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			setBlockAndMetadata(world, i1, 0, -7, LOTRMod.planks, 1);
			setBlockAndMetadata(world, i1, 0, 7, LOTRMod.planks, 1);
		}
		for (k13 = -3; k13 <= 3; ++k13) {
			setBlockAndMetadata(world, -7, 0, k13, LOTRMod.planks, 1);
			setBlockAndMetadata(world, 7, 0, k13, LOTRMod.planks, 1);
		}
		for (int j13 = 1; j13 <= 4; ++j13) {
			setBlockAndMetadata(world, -5, j13, -5, LOTRMod.planks, 1);
			setBlockAndMetadata(world, 5, j13, -5, LOTRMod.planks, 1);
			setBlockAndMetadata(world, -5, j13, 5, LOTRMod.planks, 1);
			setBlockAndMetadata(world, 5, j13, 5, LOTRMod.planks, 1);
			setBlockAndMetadata(world, -6, j13, -3, LOTRMod.wood, 1);
			setBlockAndMetadata(world, -6, j13, 3, LOTRMod.wood, 1);
			setBlockAndMetadata(world, 6, j13, -3, LOTRMod.wood, 1);
			setBlockAndMetadata(world, 6, j13, 3, LOTRMod.wood, 1);
			setBlockAndMetadata(world, -3, j13, -6, LOTRMod.wood, 1);
			setBlockAndMetadata(world, -3, j13, 6, LOTRMod.wood, 1);
			setBlockAndMetadata(world, 3, j13, -6, LOTRMod.wood, 1);
			setBlockAndMetadata(world, 3, j13, 6, LOTRMod.wood, 1);
		}
		setBlockAndMetadata(world, -4, 2, -5, getRandomTorch(random), 2);
		setBlockAndMetadata(world, -5, 2, -4, getRandomTorch(random), 3);
		setBlockAndMetadata(world, 4, 2, -5, getRandomTorch(random), 1);
		setBlockAndMetadata(world, 5, 2, -4, getRandomTorch(random), 3);
		setBlockAndMetadata(world, -4, 2, 5, getRandomTorch(random), 2);
		setBlockAndMetadata(world, -5, 2, 4, getRandomTorch(random), 4);
		setBlockAndMetadata(world, 4, 2, 5, getRandomTorch(random), 1);
		setBlockAndMetadata(world, 5, 2, 4, getRandomTorch(random), 4);
		for (i1 = -3; i1 <= 3; ++i1) {
			setBlockAndMetadata(world, i1, 1, -7, LOTRMod.fence, 1);
			setBlockAndMetadata(world, i1, 1, 7, LOTRMod.fence, 1);
		}
		for (k13 = -3; k13 <= 3; ++k13) {
			setBlockAndMetadata(world, -7, 1, k13, LOTRMod.fence, 1);
			setBlockAndMetadata(world, 7, 1, k13, LOTRMod.fence, 1);
		}
		setBlockAndMetadata(world, -4, 1, -6, LOTRMod.fence, 1);
		setBlockAndMetadata(world, -5, 1, -6, LOTRMod.fence, 1);
		setBlockAndMetadata(world, -4, 1, 6, LOTRMod.fence, 1);
		setBlockAndMetadata(world, -5, 1, 6, LOTRMod.fence, 1);
		setBlockAndMetadata(world, 4, 1, -6, LOTRMod.fence, 1);
		setBlockAndMetadata(world, 5, 1, -6, LOTRMod.fence, 1);
		setBlockAndMetadata(world, 4, 1, 6, LOTRMod.fence, 1);
		setBlockAndMetadata(world, 5, 1, 6, LOTRMod.fence, 1);
		setBlockAndMetadata(world, -6, 1, -4, LOTRMod.fence, 1);
		setBlockAndMetadata(world, -6, 1, -5, LOTRMod.fence, 1);
		setBlockAndMetadata(world, 6, 1, -4, LOTRMod.fence, 1);
		setBlockAndMetadata(world, 6, 1, -5, LOTRMod.fence, 1);
		setBlockAndMetadata(world, -6, 1, 4, LOTRMod.fence, 1);
		setBlockAndMetadata(world, -6, 1, 5, LOTRMod.fence, 1);
		setBlockAndMetadata(world, 6, 1, 4, LOTRMod.fence, 1);
		setBlockAndMetadata(world, 6, 1, 5, LOTRMod.fence, 1);
		setBlockAndMetadata(world, -6, 4, -2, LOTRMod.stairsMallorn, 7);
		setBlockAndMetadata(world, -6, 4, -1, LOTRMod.woodSlabSingle, 9);
		setBlockAndMetadata(world, -6, 4, 0, LOTRMod.woodSlabSingle, 9);
		setBlockAndMetadata(world, -6, 4, 1, LOTRMod.woodSlabSingle, 9);
		setBlockAndMetadata(world, -6, 4, 2, LOTRMod.stairsMallorn, 6);
		setBlockAndMetadata(world, 6, 4, -2, LOTRMod.stairsMallorn, 7);
		setBlockAndMetadata(world, 6, 4, -1, LOTRMod.woodSlabSingle, 9);
		setBlockAndMetadata(world, 6, 4, 0, LOTRMod.woodSlabSingle, 9);
		setBlockAndMetadata(world, 6, 4, 1, LOTRMod.woodSlabSingle, 9);
		setBlockAndMetadata(world, 6, 4, 2, LOTRMod.stairsMallorn, 6);
		setBlockAndMetadata(world, -2, 4, -6, LOTRMod.stairsMallorn, 4);
		setBlockAndMetadata(world, -1, 4, -6, LOTRMod.woodSlabSingle, 9);
		setBlockAndMetadata(world, 0, 4, -6, LOTRMod.woodSlabSingle, 9);
		setBlockAndMetadata(world, 1, 4, -6, LOTRMod.woodSlabSingle, 9);
		setBlockAndMetadata(world, 2, 4, -6, LOTRMod.stairsMallorn, 5);
		setBlockAndMetadata(world, -2, 4, 6, LOTRMod.stairsMallorn, 4);
		setBlockAndMetadata(world, -1, 4, 6, LOTRMod.woodSlabSingle, 9);
		setBlockAndMetadata(world, 0, 4, 6, LOTRMod.woodSlabSingle, 9);
		setBlockAndMetadata(world, 1, 4, 6, LOTRMod.woodSlabSingle, 9);
		setBlockAndMetadata(world, 2, 4, 6, LOTRMod.stairsMallorn, 5);
		for (i1 = -6; i1 <= -4; ++i1) {
			setBlockAndMetadata(world, i1, 4, -6, LOTRMod.stairsMallorn, 6);
			setBlockAndMetadata(world, i1, 4, 6, LOTRMod.stairsMallorn, 7);
		}
		for (i1 = 4; i1 <= 6; ++i1) {
			setBlockAndMetadata(world, i1, 4, -6, LOTRMod.stairsMallorn, 6);
			setBlockAndMetadata(world, i1, 4, 6, LOTRMod.stairsMallorn, 7);
		}
		for (k13 = -6; k13 <= -4; ++k13) {
			setBlockAndMetadata(world, -6, 4, k13, LOTRMod.stairsMallorn, 5);
			setBlockAndMetadata(world, 6, 4, k13, LOTRMod.stairsMallorn, 4);
		}
		for (k13 = 4; k13 <= 6; ++k13) {
			setBlockAndMetadata(world, -6, 4, k13, LOTRMod.stairsMallorn, 5);
			setBlockAndMetadata(world, 6, 4, k13, LOTRMod.stairsMallorn, 4);
		}
		for (i1 = -4; i1 <= 4; ++i1) {
			if (Math.abs(i1) <= 1) {
				continue;
			}
			setBlockAndMetadata(world, i1, 4, -5, LOTRMod.stairsMallorn, 7);
			setBlockAndMetadata(world, i1, 4, 5, LOTRMod.stairsMallorn, 6);
		}
		for (k13 = -4; k13 <= 4; ++k13) {
			if (Math.abs(k13) <= 1) {
				continue;
			}
			setBlockAndMetadata(world, -5, 4, k13, LOTRMod.stairsMallorn, 4);
			setBlockAndMetadata(world, 5, 4, k13, LOTRMod.stairsMallorn, 5);
		}
		for (i1 = -6; i1 <= 6; ++i1) {
			for (k12 = -6; k12 <= 6; ++k12) {
				if (restrictions && i1 >= -2 && i1 <= 2 && k12 >= -2 && k12 <= 2 || (i1 == -6 || i1 == 6) && (k12 == -6 || k12 == 6)) {
					continue;
				}
				setBlockAndMetadata(world, i1, 5, k12, LOTRMod.planks, 1);
			}
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			setBlockAndMetadata(world, i1, 5, -7, LOTRMod.planks, 1);
			setBlockAndMetadata(world, i1, 5, 7, LOTRMod.planks, 1);
		}
		for (k13 = -3; k13 <= 3; ++k13) {
			setBlockAndMetadata(world, -7, 5, k13, LOTRMod.planks, 1);
			setBlockAndMetadata(world, 7, 5, k13, LOTRMod.planks, 1);
		}
		for (i1 = -5; i1 <= 5; ++i1) {
			for (k12 = -5; k12 <= 5; ++k12) {
				if (restrictions && i1 >= -2 && i1 <= 2 && k12 >= -2 && k12 <= 2 || (i1 == -5 || i1 == 5) && (k12 == -5 || k12 == 5)) {
					continue;
				}
				setBlockAndMetadata(world, i1, 6, k12, LOTRMod.planks, 1);
			}
		}
		for (i1 = -2; i1 <= 2; ++i1) {
			setBlockAndMetadata(world, i1, 6, -6, LOTRMod.planks, 1);
			setBlockAndMetadata(world, i1, 6, 6, LOTRMod.planks, 1);
		}
		for (k13 = -2; k13 <= 2; ++k13) {
			setBlockAndMetadata(world, -6, 6, k13, LOTRMod.planks, 1);
			setBlockAndMetadata(world, 6, 6, k13, LOTRMod.planks, 1);
		}
		for (i1 = -8; i1 <= 8; ++i1) {
			int stairZ;
			int stairX = i1;
			int i2 = Math.abs(i1);
			stairZ = i2 <= 3 ? 8 : i2 <= 5 ? 7 : i2 <= 7 ? 6 : 4;
			setBlockAndMetadata(world, stairX, 5, -stairZ, LOTRMod.stairsMallorn, 2);
			setBlockAndMetadata(world, stairX, 5, stairZ, LOTRMod.stairsMallorn, 3);
			stairX = Integer.signum(stairX) * (Math.abs(stairX) - 1);
			stairZ--;
			setBlockAndMetadata(world, stairX, 6, -stairZ, LOTRMod.stairsMallorn, 2);
			setBlockAndMetadata(world, stairX, 6, stairZ, LOTRMod.stairsMallorn, 3);
		}
		for (k13 = -8; k13 <= 8; ++k13) {
			int stairX;
			int stairZ = k13;
			int k2 = Math.abs(k13);
			stairX = k2 <= 3 ? 8 : k2 <= 5 ? 7 : k2 <= 7 ? 6 : 4;
			setBlockAndMetadata(world, -stairX, 5, stairZ, LOTRMod.stairsMallorn, 1);
			setBlockAndMetadata(world, stairX, 5, stairZ, LOTRMod.stairsMallorn, 0);
			stairZ = Integer.signum(stairZ) * (Math.abs(stairZ) - 1);
			stairX--;
			setBlockAndMetadata(world, -stairX, 6, stairZ, LOTRMod.stairsMallorn, 1);
			setBlockAndMetadata(world, stairX, 6, stairZ, LOTRMod.stairsMallorn, 0);
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			setBlockAndMetadata(world, i1, 4, -3, LOTRMod.stairsMallorn, 6);
			setBlockAndMetadata(world, i1, 4, 3, LOTRMod.stairsMallorn, 7);
		}
		for (k13 = -2; k13 <= 2; ++k13) {
			setBlockAndMetadata(world, -3, 4, k13, LOTRMod.stairsMallorn, 5);
			setBlockAndMetadata(world, 3, 4, k13, LOTRMod.stairsMallorn, 4);
		}
		for (int bough = 0; bough <= 2; ++bough) {
			j1 = -3 + bough;
			k14 = 3 + bough;
			for (int i2 = -bough; i2 <= bough; ++i2) {
				for (int k2 = -k14; k2 <= k14; ++k2) {
					setBlockAndMetadata(world, i2, j1, k2, LOTRMod.wood, 13);
					setBlockAndMetadata(world, k2, j1, i2, LOTRMod.wood, 13);
				}
			}
		}
		Block ladder = random.nextBoolean() ? LOTRMod.hithlainLadder : LOTRMod.mallornLadder;
		for (j1 = 3; j1 >= -3 || !isOpaque(world, 0, j1, -3) && getY(j1) >= 0; --j1) {
			setBlockAndMetadata(world, 0, j1, -3, ladder, 2);
		}
		setBlockAndMetadata(world, -2, 1, 0, LOTRMod.elvenTable, 0);
		setBlockAndMetadata(world, -2, 2, 0, Blocks.air, 0);
		setBlockAndMetadata(world, -2, 3, 0, Blocks.air, 0);
		setBlockAndMetadata(world, -2, 4, 0, LOTRMod.wood, 5);
		setBlockAndMetadata(world, 2, 1, 0, LOTRMod.elvenTable, 0);
		setBlockAndMetadata(world, 2, 2, 0, Blocks.air, 0);
		setBlockAndMetadata(world, 2, 3, 0, Blocks.air, 0);
		setBlockAndMetadata(world, 2, 4, 0, LOTRMod.wood, 5);
		placeChest(world, random, 0, 1, 2, LOTRMod.chestMallorn, 0, LOTRChestContents.ELF_HOUSE);
		setBlockAndMetadata(world, 0, 2, 2, Blocks.air, 0);
		setBlockAndMetadata(world, 0, 3, 2, Blocks.air, 0);
		setBlockAndMetadata(world, 0, 4, 2, LOTRMod.wood, 9);
		tryPlaceLight(world, -7, -1, -3, random);
		tryPlaceLight(world, -7, -1, 3, random);
		tryPlaceLight(world, 7, -1, -3, random);
		tryPlaceLight(world, 7, -1, 3, random);
		tryPlaceLight(world, -3, -1, -7, random);
		tryPlaceLight(world, 3, -1, -7, random);
		tryPlaceLight(world, -3, -1, 7, random);
		tryPlaceLight(world, 3, -1, 7, random);
		placeFlowerPot(world, -4, 1, -5, getRandomPlant(random));
		placeFlowerPot(world, -5, 1, -4, getRandomPlant(random));
		placeFlowerPot(world, -5, 1, 4, getRandomPlant(random));
		placeFlowerPot(world, -4, 1, 5, getRandomPlant(random));
		placeFlowerPot(world, 4, 1, -5, getRandomPlant(random));
		placeFlowerPot(world, 5, 1, -4, getRandomPlant(random));
		placeFlowerPot(world, 5, 1, 4, getRandomPlant(random));
		placeFlowerPot(world, 4, 1, 5, getRandomPlant(random));
		setBlockAndMetadata(world, -2, 1, 5, LOTRMod.elvenBed, 3);
		setBlockAndMetadata(world, -3, 1, 5, LOTRMod.elvenBed, 11);
		LOTREntityGaladhrimElf elf = new LOTREntityGaladhrimElf(world);
		elf.spawnRidingHorse = false;
		spawnNPCAndSetHome(elf, world, 0, 1, 4, 8);
		return true;
	}

	public void tryPlaceLight(World world, int i, int j, int k, Random random) {
		int j1;
		int height = 2 + random.nextInt(6);
		for (j1 = j; j1 >= -height; --j1) {
			if (!restrictions) {
				continue;
			}
			if (!isAir(world, i, j1, k)) {
				return;
			}
			if (j1 != -height || isAir(world, i, j1, k - 1) && isAir(world, i, j1, k + 1) && isAir(world, i - 1, j1, k) && isAir(world, i + 1, j1, k)) {
				continue;
			}
			return;
		}
		for (j1 = j; j1 >= j - height; --j1) {
			if (j1 == j - height) {
				setBlockAndMetadata(world, i, j1, k, LOTRMod.planks, 1);
				setBlockAndMetadata(world, i, j1, k - 1, getRandomTorch(random), 4);
				setBlockAndMetadata(world, i, j1, k + 1, getRandomTorch(random), 3);
				setBlockAndMetadata(world, i - 1, j1, k, getRandomTorch(random), 1);
				setBlockAndMetadata(world, i + 1, j1, k, getRandomTorch(random), 2);
				continue;
			}
			setBlockAndMetadata(world, i, j1, k, LOTRMod.fence, 1);
		}
	}

	public static ItemStack getRandomChandelier(Random random) {
		if (random.nextBoolean()) {
			int i = random.nextInt(3);
			switch (i) {
				case 0:
					return new ItemStack(LOTRMod.chandelier, 1, 13);
				case 1:
					return new ItemStack(LOTRMod.chandelier, 1, 14);
				case 2:
					return new ItemStack(LOTRMod.chandelier, 1, 15);
				default:
					break;
			}
		}
		return new ItemStack(LOTRMod.chandelier, 1, 5);
	}

	public static ItemStack getRandomPlant(Random random) {
		return random.nextBoolean() ? new ItemStack(LOTRMod.elanor) : new ItemStack(LOTRMod.niphredil);
	}

	public static Block getRandomTorch(Random random) {
		if (random.nextBoolean()) {
			int i = random.nextInt(3);
			switch (i) {
				case 0:
					return LOTRMod.mallornTorchBlue;
				case 1:
					return LOTRMod.mallornTorchGold;
				case 2:
					return LOTRMod.mallornTorchGreen;
				default:
					break;
			}
		}
		return LOTRMod.mallornTorchSilver;
	}
}
