package lotr.common.world.structure2;

import java.util.Random;

import com.google.common.math.IntMath;

import lotr.common.*;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityDunedain;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRangerStables extends LOTRWorldGenRangerStructure {
	public LOTRWorldGenRangerStables(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int i1;
		int i12;
		int k1;
		int j1;
		int j12;
		int k12;
		int i2;
		int i132;
		setOriginAndRotation(world, i, j, k, rotation, 1, -2);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i14 = -5; i14 <= 5; ++i14) {
				for (int k13 = -1; k13 <= 10; ++k13) {
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
		for (int i15 = -4; i15 <= 4; ++i15) {
			for (k1 = 0; k1 <= 9; ++k1) {
				i2 = Math.abs(i15);
				for (j1 = 0; (j1 >= 0 || !isOpaque(world, i15, j1, k1)) && getY(j1) >= 0; --j1) {
					setBlockAndMetadata(world, i15, j1, k1, brickBlock, brickMeta);
					setGrassToDirt(world, i15, j1 - 1, k1);
				}
				for (j1 = 1; j1 <= 8; ++j1) {
					setAir(world, i15, j1, k1);
				}
				if (k1 > 4 || k1 != 0 && k1 != 4 && i2 != 4) {
					continue;
				}
				boolean beam = (k1 == 0 || k1 == 4) && (i2 == 0 || i2 == 4);
				if (beam) {
					for (j12 = 1; j12 <= 3; ++j12) {
						setBlockAndMetadata(world, i15, j12, k1, woodBeamBlock, woodBeamMeta);
					}
					continue;
				}
				if (k1 == 4) {
					for (j12 = 1; j12 <= 3; ++j12) {
						setBlockAndMetadata(world, i15, j12, 4, plankBlock, plankMeta);
					}
					continue;
				}
				for (j12 = 1; j12 <= 3; ++j12) {
					setBlockAndMetadata(world, i15, j12, k1, wallBlock, wallMeta);
				}
			}
		}
		for (int k13 : new int[]{0, 4}) {
			for (int i16 = -3; i16 <= 3; ++i16) {
				if (i16 == 0) {
					continue;
				}
				setBlockAndMetadata(world, i16, 3, k13, woodBeamBlock, woodBeamMeta | 4);
			}
		}
		for (int i1321 : new int[]{-4, 0, 4}) {
			for (k12 = 0; k12 <= 3; ++k12) {
				setBlockAndMetadata(world, i1321, 4, k12, woodBeamBlock, woodBeamMeta | 8);
			}
			for (j12 = 4; j12 <= 6; ++j12) {
				setBlockAndMetadata(world, i1321, j12, 4, woodBeamBlock, woodBeamMeta);
			}
		}
		for (int i17 = -4; i17 <= 4; ++i17) {
			setBlockAndMetadata(world, i17, 7, 4, woodBeamBlock, woodBeamMeta | 4);
		}
		int[] i17 = {-4, 4};
		k1 = i17.length;
		for (i2 = 0; i2 < k1; ++i2) {
			i132 = i17[i2];
			setBlockAndMetadata(world, i132, 5, 2, wallBlock, wallMeta);
			setBlockAndMetadata(world, i132, 5, 3, wallBlock, wallMeta);
			setBlockAndMetadata(world, i132, 6, 3, wallBlock, wallMeta);
		}
		setBlockAndMetadata(world, -2, 1, 0, doorBlock, 1);
		setBlockAndMetadata(world, -2, 2, 0, doorBlock, 8);
		setBlockAndMetadata(world, 2, 2, 0, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, -4, 2, 2, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 4, 2, 2, fenceBlock, fenceMeta);
		for (int i18 = -3; i18 <= 3; ++i18) {
			for (k1 = 1; k1 <= 3; ++k1) {
				setBlockAndMetadata(world, i18, 0, k1, plankBlock, plankMeta);
			}
			if (i18 == 0) {
				continue;
			}
			for (k1 = 1; k1 <= 3; ++k1) {
				setBlockAndMetadata(world, i18, 4, k1, plankSlabBlock, plankSlabMeta | 8);
			}
			setBlockAndMetadata(world, i18, 4, 4, plankBlock, plankMeta);
		}
		for (int i1321 : new int[]{-4, 0, 4}) {
			for (j12 = 1; j12 <= 3; ++j12) {
				setBlockAndMetadata(world, i1321, j12, 9, woodBeamBlock, woodBeamMeta);
			}
			for (k12 = 5; k12 <= 9; ++k12) {
				setBlockAndMetadata(world, i1321, 4, k12, woodBeamBlock, woodBeamMeta | 8);
			}
			for (k12 = 5; k12 <= 8; ++k12) {
				setBlockAndMetadata(world, i1321, 1, k12, fenceBlock, fenceMeta);
				setBlockAndMetadata(world, i1321, 3, k12, fenceBlock, fenceMeta);
			}
			setBlockAndMetadata(world, i1321, 2, 5, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, i1321, 2, 8, fenceBlock, fenceMeta);
		}
		int[] i18 = {-4, 4};
		k1 = i18.length;
		for (i2 = 0; i2 < k1; ++i2) {
			i132 = i18[i2];
			setBlockAndMetadata(world, i132, 5, 5, wallBlock, wallMeta);
			setBlockAndMetadata(world, i132, 5, 6, wallBlock, wallMeta);
			setBlockAndMetadata(world, i132, 5, 7, wallBlock, wallMeta);
			setBlockAndMetadata(world, i132, 6, 5, wallBlock, wallMeta);
			setBlockAndMetadata(world, i132, 6, 6, wallBlock, wallMeta);
			setBlockAndMetadata(world, i132, 7, 5, wallBlock, wallMeta);
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			if (i1 == 0) {
				continue;
			}
			setBlockAndMetadata(world, i1, 1, 9, fenceGateBlock, 2);
			for (k1 = 5; k1 <= 8; ++k1) {
				int randomFloor = random.nextInt(3);
				switch (randomFloor) {
					case 0:
						setBlockAndMetadata(world, i1, 0, k1, Blocks.grass, 0);
						break;
					case 1:
						setBlockAndMetadata(world, i1, 0, k1, Blocks.dirt, 1);
						break;
					case 2:
						setBlockAndMetadata(world, i1, 0, k1, LOTRMod.dirtPath, 0);
						break;
					default:
						break;
				}
				if (!random.nextBoolean()) {
					continue;
				}
				setBlockAndMetadata(world, i1, 1, k1, LOTRMod.thatchFloor, 0);
			}
			setBlockAndMetadata(world, i1, 4, 5, plankStairBlock, 7);
			setBlockAndMetadata(world, i1, 4, 6, plankSlabBlock, plankSlabMeta | 8);
		}
		setBlockAndMetadata(world, -3, 3, 9, plankStairBlock, 4);
		setBlockAndMetadata(world, -1, 3, 9, plankStairBlock, 5);
		setBlockAndMetadata(world, 1, 3, 9, plankStairBlock, 4);
		setBlockAndMetadata(world, 3, 3, 9, plankStairBlock, 5);
		for (i1 = -5; i1 <= 5; ++i1) {
			int l;
			int avoidBeam;
			avoidBeam = IntMath.mod(i1, 4) == 0 ? 1 : 0;
			if (avoidBeam == 0) {
				setBlockAndMetadata(world, i1, 4, 0, roofStairBlock, 2);
				setBlockAndMetadata(world, i1, 4, 9, roofStairBlock, 3);
			}
			for (l = 0; l <= 2; ++l) {
				setBlockAndMetadata(world, i1, 5 + l, 1 + l, roofStairBlock, 2);
				setBlockAndMetadata(world, i1, 5 + l, 8 - l, roofStairBlock, 3);
			}
			for (int k14 = 4; k14 <= 5; ++k14) {
				setBlockAndMetadata(world, i1, 8, k14, roofSlabBlock, roofSlabMeta);
			}
			if (Math.abs(i1) != 5) {
				continue;
			}
			for (l = 0; l <= 3; ++l) {
				setBlockAndMetadata(world, i1, 4 + l, 1 + l, roofStairBlock, 7);
				setBlockAndMetadata(world, i1, 4 + l, 8 - l, roofStairBlock, 6);
			}
		}
		for (int i1321 : new int[]{-4, 0, 4}) {
			setBlockAndMetadata(world, i1321, 3, -1, Blocks.torch, 4);
			setBlockAndMetadata(world, i1321, 3, 10, Blocks.torch, 3);
		}
		setBlockAndMetadata(world, -5, 3, 4, Blocks.torch, 1);
		setBlockAndMetadata(world, 5, 3, 4, Blocks.torch, 2);
		setBlockAndMetadata(world, -3, 1, 3, Blocks.crafting_table, 0);
		placeChest(world, random, -2, 1, 3, 2, chestContentsHouse);
		setBlockAndMetadata(world, -1, 1, 3, plankBlock, plankMeta);
		placePlateWithCertainty(world, random, -1, 2, 3, plateBlock, LOTRFoods.RANGER);
		setBlockAndMetadata(world, 0, 1, 3, plankBlock, plankMeta);
		placeBarrel(world, random, 0, 2, 3, 2, LOTRFoods.RANGER_DRINK);
		setBlockAndMetadata(world, 1, 1, 3, plankBlock, plankMeta);
		placeMug(world, random, 1, 2, 3, 0, LOTRFoods.RANGER_DRINK);
		setBlockAndMetadata(world, 3, 1, 3, plankBlock, plankMeta);
		placeMug(world, random, 3, 2, 3, 1, LOTRFoods.RANGER_DRINK);
		setBlockAndMetadata(world, 2, 1, 1, bedBlock, 1);
		setBlockAndMetadata(world, 3, 1, 1, bedBlock, 9);
		setBlockAndMetadata(world, -3, 3, 2, Blocks.torch, 2);
		setBlockAndMetadata(world, 3, 3, 2, Blocks.torch, 1);
		for (int j13 = 1; j13 <= 4; ++j13) {
			setBlockAndMetadata(world, 2, j13, 3, Blocks.ladder, 2);
		}
		setBlockAndMetadata(world, 0, 7, 5, plankStairBlock, 7);
		setBlockAndMetadata(world, -3, 7, 5, plankStairBlock, 4);
		setBlockAndMetadata(world, 3, 7, 5, plankStairBlock, 5);
		for (i12 = -3; i12 <= 3; ++i12) {
			if (random.nextInt(3) == 0) {
				continue;
			}
			setBlockAndMetadata(world, i12, 5, 2, Blocks.hay_block, 0);
		}
		for (i12 = -3; i12 <= 3; ++i12) {
			if (random.nextInt(3) == 0) {
				continue;
			}
			int h = 5;
			int h1 = h + random.nextInt(2);
			for (j1 = h; j1 <= h1; ++j1) {
				setBlockAndMetadata(world, i12, j1, 6, Blocks.hay_block, 0);
			}
		}
		for (int i1321 : new int[]{-3, 3}) {
			for (k12 = 3; k12 <= 5; ++k12) {
				if (random.nextInt(3) == 0) {
					continue;
				}
				int h = 5;
				int h1 = h + random.nextInt(2);
				for (int j14 = h; j14 <= h1; ++j14) {
					setBlockAndMetadata(world, i1321, j14, k12, Blocks.hay_block, 0);
				}
			}
		}
		setBlockAndMetadata(world, -2, 5, 3, bedBlock, 3);
		setBlockAndMetadata(world, -3, 5, 3, bedBlock, 11);
		placeChest(world, random, -3, 5, 5, 4, chestContentsHouse);
		setBlockAndMetadata(world, -3, 6, 4, Blocks.torch, 2);
		setBlockAndMetadata(world, 3, 6, 4, Blocks.torch, 1);
		int men = 1;
		for (int l = 0; l < men; ++l) {
			LOTREntityDunedain dunedain = new LOTREntityDunedain(world);
			spawnNPCAndSetHome(dunedain, world, 0, 1, 2, 8);
		}
		for (int i16 : new int[]{-2, 2}) {
			LOTREntityHorse horse = new LOTREntityHorse(world);
			spawnNPCAndSetHome(horse, world, i16, 1, 7, 0);
			horse.setHorseType(0);
			horse.saddleMountForWorldGen();
			horse.detachHome();
		}
		return true;
	}
}
