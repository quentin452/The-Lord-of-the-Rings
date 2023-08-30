package lotr.common.world.structure2;

import java.util.Random;

import com.google.common.math.IntMath;

import lotr.common.*;
import lotr.common.entity.npc.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGondorTavern extends LOTRWorldGenGondorStructure {
	public String[] tavernName;
	public String[] tavernNameSign;
	public String tavernNameNPC;

	public LOTRWorldGenGondorTavern(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int i1;
		int k15;
		int i12;
		int beam;
		int oppHeight;
		int k122;
		int i132;
		int j1;
		int i14;
		int k13;
		int k14;
		int step;
		int j12;
		setOriginAndRotation(world, i, j, k, rotation, 1);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (i1 = -9; i1 <= 13; ++i1) {
				for (k15 = -2; k15 <= 16; ++k15) {
					int j13 = getTopBlock(world, i1, k15) - 1;
					if (!isSurface(world, i1, j13, k15)) {
						return false;
					}
					if (j13 < minHeight) {
						minHeight = j13;
					}
					if (j13 > maxHeight) {
						maxHeight = j13;
					}
					if (maxHeight - minHeight <= 6) {
						continue;
					}
					return false;
				}
			}
		}
		if (restrictions && (oppHeight = getTopBlock(world, 0, 15) - 1) > 0) {
			originY = getY(oppHeight);
		}
		for (int i15 = -7; i15 <= 11; ++i15) {
			for (k13 = 0; k13 <= 14; ++k13) {
				if ((i15 == -7 || i15 == 11) && (k13 == 0 || k13 == 14)) {
					continue;
				}
				beam = 0;
				if (i15 == -7 || i15 == 11) {
					beam = IntMath.mod(k13, 4) == 1 ? 1 : 0;
				} else if (k13 == 0 || k13 == 14) {
					beam = IntMath.mod(i15, 4) == 2 ? 1 : 0;
				}
				if (beam != 0) {
					for (j12 = 4; (j12 >= 0 || !isOpaque(world, i15, j12, k13)) && getY(j12) >= 0; --j12) {
						setBlockAndMetadata(world, i15, j12, k13, woodBeamBlock, woodBeamMeta);
						setGrassToDirt(world, i15, j12 - 1, k13);
					}
					continue;
				}
				if (i15 == -7 || i15 == 11 || k13 == 0 || k13 == 14) {
					for (j12 = 0; (j12 >= 0 || !isOpaque(world, i15, j12, k13)) && getY(j12) >= 0; --j12) {
						setBlockAndMetadata(world, i15, j12, k13, rockBlock, rockMeta);
						setGrassToDirt(world, i15, j12 - 1, k13);
					}
					for (j12 = 1; j12 <= 4; ++j12) {
						setBlockAndMetadata(world, i15, j12, k13, wallBlock, wallMeta);
					}
					continue;
				}
				for (j12 = 0; (j12 >= 0 || !isOpaque(world, i15, j12, k13)) && getY(j12) >= 0; --j12) {
					setBlockAndMetadata(world, i15, j12, k13, plankBlock, plankMeta);
					setGrassToDirt(world, i15, j12 - 1, k13);
				}
				for (j12 = 1; j12 <= 4; ++j12) {
					setAir(world, i15, j12, k13);
				}
			}
		}
		for (int k151 : new int[]{0, 14}) {
			for (i14 = -4; i14 <= 8; ++i14) {
				if (IntMath.mod(i14, 4) != 0 || i14 == 0) {
					continue;
				}
				setBlockAndMetadata(world, i14, 2, k151, LOTRMod.glassPane, 0);
				setBlockAndMetadata(world, i14, 3, k151, LOTRMod.glassPane, 0);
			}
		}
		for (int i1321 : new int[]{-7, 11}) {
			for (k122 = 3; k122 <= 11; ++k122) {
				if (IntMath.mod(k122, 4) != 3 || i1321 == -7 && k122 == 7) {
					continue;
				}
				setBlockAndMetadata(world, i1321, 2, k122, LOTRMod.glassPane, 0);
				setBlockAndMetadata(world, i1321, 3, k122, LOTRMod.glassPane, 0);
			}
		}
		setBlockAndMetadata(world, 0, 0, 0, plankBlock, plankMeta);
		setBlockAndMetadata(world, 0, 1, 0, doorBlock, 1);
		setBlockAndMetadata(world, 0, 2, 0, doorBlock, 8);
		setBlockAndMetadata(world, 0, 0, 14, plankBlock, plankMeta);
		setBlockAndMetadata(world, 0, 1, 14, doorBlock, 3);
		setBlockAndMetadata(world, 0, 2, 14, doorBlock, 8);
		int[] i15 = {-1, 15};
		k13 = i15.length;
		block13:
		for (beam = 0; beam < k13; ++beam) {
			int j14;
			i14 = 0;
			k15 = i15[beam];
			int doorHeight = getTopBlock(world, i14, k15) - 1;
			if (doorHeight >= 0) {
				continue;
			}
			for (j14 = 0; (j14 == 0 || !isOpaque(world, i14, j14, k15)) && getY(j14) >= 0; --j14) {
				setBlockAndMetadata(world, i14, j14, k15, plankBlock, plankMeta);
				setGrassToDirt(world, i14, j14 - 1, k15);
			}
			++i14;
			j14 = 0;
			while (!isOpaque(world, i14, j14, k15) && getY(j14) >= 0) {
				setBlockAndMetadata(world, i14, j14, k15, plankStairBlock, 0);
				setGrassToDirt(world, i14, j14 - 1, k15);
				int j2 = j14 - 1;
				while (!isOpaque(world, i14, j2, k15) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i14, j2, k15, plankBlock, plankMeta);
					setGrassToDirt(world, i14, j2 - 1, k15);
					--j2;
				}
				i14++;
				if (i14 >= 15) {
					continue block13;
				}
				--j14;
			}
		}
		setBlockAndMetadata(world, -2, 3, -1, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, -2, 4, -1, Blocks.torch, 5);
		setBlockAndMetadata(world, 2, 3, -1, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 2, 4, -1, Blocks.torch, 5);
		setBlockAndMetadata(world, 0, 4, -1, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 0, 4, -2, plankBlock, plankMeta);
		placeSign(world, -1, 4, -2, Blocks.wall_sign, 5, tavernNameSign);
		placeSign(world, 1, 4, -2, Blocks.wall_sign, 4, tavernNameSign);
		placeSign(world, 0, 4, -3, Blocks.wall_sign, 2, tavernNameSign);
		setBlockAndMetadata(world, -2, 3, 15, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, -2, 4, 15, Blocks.torch, 5);
		setBlockAndMetadata(world, 2, 3, 15, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 2, 4, 15, Blocks.torch, 5);
		setBlockAndMetadata(world, 0, 4, 15, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 0, 4, 16, plankBlock, plankMeta);
		placeSign(world, -1, 4, 16, Blocks.wall_sign, 5, tavernNameSign);
		placeSign(world, 1, 4, 16, Blocks.wall_sign, 4, tavernNameSign);
		placeSign(world, 0, 4, 17, Blocks.wall_sign, 3, tavernNameSign);
		for (int i16 = -8; i16 <= 12; ++i16) {
			for (k13 = -1; k13 <= 15; ++k13) {
				if ((i16 <= -7 || i16 >= 11) && (k13 <= 0 || k13 >= 14)) {
					continue;
				}
				beam = 0;
				if (i16 == -8 || i16 == 12) {
					beam = IntMath.mod(k13, 4) == 1 ? 1 : 0;
				} else if (k13 == -1 || k13 == 15) {
					beam = IntMath.mod(i16, 4) == 2 ? 1 : 0;
				}
				if (beam != 0) {
					if (i16 == -8 || i16 == 12) {
						setBlockAndMetadata(world, i16, 5, k13, woodBeamBlock, woodBeamMeta | 4);
					}
					if (k13 == -1 || k13 == 15) {
						setBlockAndMetadata(world, i16, 5, k13, woodBeamBlock, woodBeamMeta | 8);
					}
					for (j12 = 6; j12 <= 8; ++j12) {
						setBlockAndMetadata(world, i16, j12, k13, woodBeamBlock, woodBeamMeta);
					}
					continue;
				}
				if (i16 == -8 || i16 == 12 || k13 == -1 || k13 == 15) {
					if (i16 == -8 || i16 == 12) {
						setBlockAndMetadata(world, i16, 5, k13, woodBeamBlock, woodBeamMeta | 8);
					}
					if (k13 == -1 || k13 == 15) {
						setBlockAndMetadata(world, i16, 5, k13, woodBeamBlock, woodBeamMeta | 4);
					}
					for (j12 = 6; j12 <= 8; ++j12) {
						setBlockAndMetadata(world, i16, j12, k13, wallBlock, wallMeta);
					}
					continue;
				}
				if ((i16 == -7 || i16 == 11) && (k13 == 1 || k13 == 13) || (i16 == -6 || i16 == 10) && (k13 == 0 || k13 == 14)) {
					if (i16 == -7 || i16 == 11) {
						setBlockAndMetadata(world, i16, 5, k13, woodBeamBlock, woodBeamMeta | 4);
					}
					if (k13 == 0 || k13 == 14) {
						setBlockAndMetadata(world, i16, 5, k13, woodBeamBlock, woodBeamMeta | 8);
					}
					for (j12 = 6; j12 <= 8; ++j12) {
						setBlockAndMetadata(world, i16, j12, k13, wallBlock, wallMeta);
					}
					continue;
				}
				setBlockAndMetadata(world, i16, 5, k13, plankBlock, plankMeta);
				for (j12 = 6; j12 <= 11; ++j12) {
					setAir(world, i16, j12, k13);
				}
			}
		}
		for (int k151 : new int[]{-1, 15}) {
			for (i14 = -4; i14 <= 8; ++i14) {
				if (IntMath.mod(i14, 4) != 0) {
					continue;
				}
				setBlockAndMetadata(world, i14, 7, k151, LOTRMod.glassPane, 0);
			}
		}
		int[] i16 = {-8, 12};
		k13 = i16.length;
		for (beam = 0; beam < k13; ++beam) {
			i132 = i16[beam];
			for (k122 = 3; k122 <= 11; ++k122) {
				if (IntMath.mod(k122, 4) != 3) {
					continue;
				}
				setBlockAndMetadata(world, i132, 7, k122, LOTRMod.glassPane, 0);
			}
		}
		for (int step2 = 0; step2 <= 2; ++step2) {
			for (int i17 = -9; i17 <= 13; ++i17) {
				if (i17 >= -7 + step2 && i17 <= 11 - step2) {
					setBlockAndMetadata(world, i17, 8 + step2, -2 + step2, roofStairBlock, 2);
					setBlockAndMetadata(world, i17, 8 + step2, 16 - step2, roofStairBlock, 3);
				}
				if (i17 > -7 + step2 && i17 < 11 - step2) {
					continue;
				}
				setBlockAndMetadata(world, i17, 8 + step2, step2, roofStairBlock, 2);
				setBlockAndMetadata(world, i17, 8 + step2, 14 - step2, roofStairBlock, 3);
			}
			setBlockAndMetadata(world, -7 + step2, 8 + step2, -1 + step2, roofStairBlock, 1);
			setBlockAndMetadata(world, 11 - step2, 8 + step2, -1 + step2, roofStairBlock, 0);
			setBlockAndMetadata(world, -7 + step2, 8 + step2, 15 - step2, roofStairBlock, 1);
			setBlockAndMetadata(world, 11 - step2, 8 + step2, 15 - step2, roofStairBlock, 0);
		}
		for (int i18 = -9; i18 <= 13; ++i18) {
			setBlockAndMetadata(world, i18, 11, 4, roofBlock, roofMeta);
			setBlockAndMetadata(world, i18, 12, 5, roofSlabBlock, roofSlabMeta);
			setBlockAndMetadata(world, i18, 12, 6, roofBlock, roofMeta);
			setBlockAndMetadata(world, i18, 12, 7, woodBeamBlock, woodBeamMeta | 4);
			setBlockAndMetadata(world, i18, 13, 7, roofSlabBlock, roofSlabMeta);
			setBlockAndMetadata(world, i18, 12, 8, roofBlock, roofMeta);
			setBlockAndMetadata(world, i18, 12, 9, roofSlabBlock, roofSlabMeta);
			setBlockAndMetadata(world, i18, 11, 10, roofBlock, roofMeta);
			if (i18 >= -3 && i18 <= 7) {
				setBlockAndMetadata(world, i18, 11, 1, roofSlabBlock, roofSlabMeta);
				setBlockAndMetadata(world, i18, 11, 2, roofBlock, roofMeta);
				setBlockAndMetadata(world, i18, 11, 3, roofBlock, roofMeta);
				setBlockAndMetadata(world, i18, 11, 11, roofBlock, roofMeta);
				setBlockAndMetadata(world, i18, 11, 12, roofBlock, roofMeta);
				setBlockAndMetadata(world, i18, 11, 13, roofSlabBlock, roofSlabMeta);
			} else {
				setBlockAndMetadata(world, i18, 11, 3, roofSlabBlock, roofSlabMeta);
				setBlockAndMetadata(world, i18, 11, 11, roofSlabBlock, roofSlabMeta);
			}
			if (i18 == -4 || i18 == 8) {
				setBlockAndMetadata(world, i18, 11, 1, roofSlabBlock, roofSlabMeta);
				setBlockAndMetadata(world, i18, 11, 2, roofSlabBlock, roofSlabMeta);
				setBlockAndMetadata(world, i18, 11, 12, roofSlabBlock, roofSlabMeta);
				setBlockAndMetadata(world, i18, 11, 13, roofSlabBlock, roofSlabMeta);
			}
			if (i18 != -9 && i18 != 13) {
				continue;
			}
			setBlockAndMetadata(world, i18, 8, 1, roofStairBlock, 7);
			setBlockAndMetadata(world, i18, 9, 2, roofStairBlock, 7);
			setBlockAndMetadata(world, i18, 10, 3, roofStairBlock, 7);
			setBlockAndMetadata(world, i18, 11, 5, roofSlabBlock, roofSlabMeta | 8);
			setBlockAndMetadata(world, i18, 11, 9, roofSlabBlock, roofSlabMeta | 8);
			setBlockAndMetadata(world, i18, 10, 11, roofStairBlock, 6);
			setBlockAndMetadata(world, i18, 9, 12, roofStairBlock, 6);
			setBlockAndMetadata(world, i18, 8, 13, roofStairBlock, 6);
		}
		for (int i1321 : new int[]{-8, 12}) {
			for (k122 = 2; k122 <= 12; ++k122) {
				setBlockAndMetadata(world, i1321, 9, k122, woodBeamBlock, woodBeamMeta | 8);
			}
			for (k122 = 3; k122 <= 11; ++k122) {
				setBlockAndMetadata(world, i1321, 10, k122, wallBlock, wallMeta);
			}
			for (k122 = 5; k122 <= 9; ++k122) {
				setBlockAndMetadata(world, i1321, 11, k122, wallBlock, wallMeta);
			}
		}
		for (int i19 = 3; i19 <= 5; ++i19) {
			for (k13 = 6; k13 <= 8; ++k13) {
				for (j1 = 0; j1 <= 13; ++j1) {
					if (i19 == 4 && k13 == 7) {
						setAir(world, 4, j1, 7);
						continue;
					}
					setBlockAndMetadata(world, i19, j1, k13, brickBlock, brickMeta);
				}
			}
			setBlockAndMetadata(world, i19, 14, 6, brickStairBlock, 2);
			setBlockAndMetadata(world, i19, 14, 8, brickStairBlock, 3);
		}
		setBlockAndMetadata(world, 3, 14, 7, brickStairBlock, 1);
		setBlockAndMetadata(world, 5, 14, 7, brickStairBlock, 0);
		setBlockAndMetadata(world, 4, 15, 7, brickBlock, brickMeta);
		setBlockAndMetadata(world, 4, 16, 7, brickBlock, brickMeta);
		setBlockAndMetadata(world, 4, 17, 7, brickWallBlock, brickWallMeta);
		setBlockAndMetadata(world, 4, 18, 7, brickWallBlock, brickWallMeta);
		setBlockAndMetadata(world, 4, 0, 7, LOTRMod.hearth, 0);
		setBlockAndMetadata(world, 4, 1, 7, Blocks.fire, 0);
		setBlockAndMetadata(world, 4, 1, 6, Blocks.iron_bars, 0);
		setBlockAndMetadata(world, 4, 1, 8, Blocks.iron_bars, 0);
		setBlockAndMetadata(world, 3, 1, 7, Blocks.iron_bars, 0);
		setBlockAndMetadata(world, 5, 1, 7, Blocks.iron_bars, 0);
		setBlockAndMetadata(world, 4, 2, 6, Blocks.furnace, 2);
		setBlockAndMetadata(world, 4, 2, 8, Blocks.furnace, 3);
		setBlockAndMetadata(world, 3, 2, 7, Blocks.furnace, 5);
		setBlockAndMetadata(world, 5, 2, 7, Blocks.furnace, 4);
		setBlockAndMetadata(world, 0, 4, 3, LOTRMod.chandelier, 1);
		setBlockAndMetadata(world, 0, 4, 11, LOTRMod.chandelier, 1);
		setBlockAndMetadata(world, 8, 4, 3, LOTRMod.chandelier, 1);
		setBlockAndMetadata(world, 8, 4, 11, LOTRMod.chandelier, 1);
		for (int k151 : new int[]{1, 2}) {
			setBlockAndMetadata(world, -4, 1, k151, plankBlock, plankMeta);
			placeMugOrPlate(world, random, -4, 2, k151);
			setBlockAndMetadata(world, -6, 1, k151, plankStairBlock, 0);
			setBlockAndMetadata(world, -2, 1, k151, plankStairBlock, 1);
		}
		int[] i19 = {1, 2, 12, 13};
		k13 = i19.length;
		for (j1 = 0; j1 < k13; ++j1) {
			k15 = i19[j1];
			setBlockAndMetadata(world, 2, 1, k15, plankBlock, plankMeta);
			placeMugOrPlate(world, random, 2, 2, k15);
			setBlockAndMetadata(world, 3, 1, k15, plankBlock, plankMeta);
			placeMugOrPlate(world, random, 3, 2, k15);
			setBlockAndMetadata(world, 5, 1, k15, plankStairBlock, 1);
		}
		for (k14 = 6; k14 <= 8; ++k14) {
			setBlockAndMetadata(world, 8, 1, k14, plankBlock, plankMeta);
			placeMugOrPlate(world, random, 8, 2, k14);
			setBlockAndMetadata(world, 10, 1, k14, plankStairBlock, 1);
		}
		for (i12 = 7; i12 <= 10; ++i12) {
			setBlockAndMetadata(world, i12, 1, 1, plankStairBlock, 3);
			setBlockAndMetadata(world, i12, 1, 13, plankStairBlock, 2);
		}
		for (k14 = 2; k14 <= 4; ++k14) {
			setBlockAndMetadata(world, 10, 1, k14, plankStairBlock, 1);
		}
		for (k14 = 10; k14 <= 12; ++k14) {
			setBlockAndMetadata(world, 10, 1, k14, plankStairBlock, 1);
		}
		for (i12 = 7; i12 <= 8; ++i12) {
			int[] k16 = {3, 4, 10, 11};
			j1 = k16.length;
			for (k15 = 0; k15 < j1; ++k15) {
				k122 = k16[k15];
				setBlockAndMetadata(world, i12, 1, k122, plankBlock, plankMeta);
				placeMugOrPlate(world, random, i12, 2, k122);
			}
		}
		for (int j15 = 1; j15 <= 4; ++j15) {
			setBlockAndMetadata(world, -2, j15, 5, woodBeamBlock, woodBeamMeta);
			setBlockAndMetadata(world, -2, j15, 9, woodBeamBlock, woodBeamMeta);
		}
		for (i12 = -6; i12 <= -3; ++i12) {
			setBlockAndMetadata(world, i12, 1, 5, plankBlock, plankMeta);
			setBlockAndMetadata(world, i12, 3, 5, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, i12, 4, 5, woodBeamBlock, woodBeamMeta | 4);
			setBlockAndMetadata(world, i12, 1, 9, plankBlock, plankMeta);
			setBlockAndMetadata(world, i12, 3, 9, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, i12, 4, 9, woodBeamBlock, woodBeamMeta | 4);
		}
		for (k14 = 6; k14 <= 8; ++k14) {
			setBlockAndMetadata(world, -2, 1, k14, plankBlock, plankMeta);
			setBlockAndMetadata(world, -2, 3, k14, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, -2, 4, k14, woodBeamBlock, woodBeamMeta | 8);
		}
		setBlockAndMetadata(world, -4, 1, 5, fenceGateBlock, 0);
		placeBarrel(world, random, -6, 2, 5, 3, LOTRFoods.GONDOR_DRINK);
		placeMug(world, random, -5, 2, 5, 2, LOTRFoods.GONDOR_DRINK);
		placeMug(world, random, -3, 2, 5, 2, LOTRFoods.GONDOR_DRINK);
		setBlockAndMetadata(world, -4, 1, 9, fenceGateBlock, 2);
		placeBarrel(world, random, -6, 2, 9, 2, LOTRFoods.GONDOR_DRINK);
		placeMug(world, random, -5, 2, 9, 0, LOTRFoods.GONDOR_DRINK);
		placeMug(world, random, -3, 2, 9, 0, LOTRFoods.GONDOR_DRINK);
		placeBarrel(world, random, -2, 2, 8, 5, LOTRFoods.GONDOR_DRINK);
		placeMug(world, random, -2, 2, 6, 1, LOTRFoods.GONDOR_DRINK);
		setBlockAndMetadata(world, -6, 1, 6, plankStairBlock, 4);
		placePlateWithCertainty(world, random, -6, 2, 6, plateBlock, LOTRFoods.GONDOR);
		setBlockAndMetadata(world, -6, 1, 7, Blocks.furnace, 4);
		setBlockAndMetadata(world, -6, 1, 8, Blocks.cauldron, 3);
		placeChest(world, random, -3, 0, 8, 5, LOTRChestContents.GONDOR_HOUSE);
		for (k14 = 6; k14 <= 8; ++k14) {
			setBlockAndMetadata(world, -6, 3, k14, plankStairBlock, 4);
			placeBarrel(world, random, -6, 4, k14, 4, LOTRFoods.GONDOR_DRINK);
		}
		setBlockAndMetadata(world, -4, 4, 7, LOTRMod.chandelier, 1);
		for (step = 0; step <= 2; ++step) {
			setBlockAndMetadata(world, -3 - step, 1 + step, 13, plankStairBlock, 0);
			setBlockAndMetadata(world, -4 - step, 1 + step, 13, plankStairBlock, 5);
		}
		setBlockAndMetadata(world, -6, 3, 13, plankBlock, plankMeta);
		for (step = 0; step <= 1; ++step) {
			setBlockAndMetadata(world, -6, 4 + step, 12 - step, plankStairBlock, 3);
			setBlockAndMetadata(world, -6, 3 + step, 12 - step, plankStairBlock, 6);
		}
		for (i12 = -6; i12 <= -4; ++i12) {
			setAir(world, i12, 5, 13);
		}
		setAir(world, -6, 5, 12);
		for (i12 = -5; i12 <= -3; ++i12) {
			setBlockAndMetadata(world, i12, 6, 14, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, i12, 6, 12, fenceBlock, fenceMeta);
		}
		setBlockAndMetadata(world, -3, 6, 13, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, -7, 6, 12, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, -7, 6, 11, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, -5, 6, 11, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, -5, 7, 12, Blocks.torch, 5);
		for (i12 = -7; i12 <= -3; ++i12) {
			for (k13 = 10; k13 <= 14; ++k13) {
				if (i12 == -3 && k13 == 10) {
					continue;
				}
				if ((i12 >= -4 || k13 <= 11) && k13 <= 13) {
					setBlockAndMetadata(world, i12, 10, k13, wallBlock, wallMeta);
				}
				if (i12 < -5 && k13 > 12) {
					continue;
				}
				setBlockAndMetadata(world, i12, 9, k13, roofSlabBlock, roofSlabMeta | 8);
			}
		}
		setBlockAndMetadata(world, 4, 7, 6, Blocks.iron_bars, 0);
		setBlockAndMetadata(world, 4, 7, 8, Blocks.iron_bars, 0);
		setBlockAndMetadata(world, 3, 7, 7, Blocks.iron_bars, 0);
		setBlockAndMetadata(world, 5, 7, 7, Blocks.iron_bars, 0);
		spawnItemFrame(world, 3, 10, 7, 3, getGondorFramedItem(random));
		for (i12 = -2; i12 <= 1; ++i12) {
			for (k13 = 5; k13 <= 9; ++k13) {
				setBlockAndMetadata(world, i12, 6, k13, Blocks.carpet, 12);
			}
		}
		for (i12 = -2; i12 <= 6; ++i12) {
			int k17;
			int i2 = IntMath.mod(i12, 4);
			if (i2 == 2) {
				for (j1 = 6; j1 <= 8; ++j1) {
					setBlockAndMetadata(world, i12, j1, 3, woodBeamBlock, woodBeamMeta);
					for (k15 = 0; k15 <= 2; ++k15) {
						setBlockAndMetadata(world, i12, j1, k15, wallBlock, wallMeta);
					}
					setBlockAndMetadata(world, i12, j1, 11, woodBeamBlock, woodBeamMeta);
					for (k15 = 12; k15 <= 14; ++k15) {
						setBlockAndMetadata(world, i12, j1, k15, wallBlock, wallMeta);
					}
				}
				for (k17 = 0; k17 <= 3; ++k17) {
					setBlockAndMetadata(world, i12, 9, k17, woodBeamBlock, woodBeamMeta | 8);
				}
				for (k17 = 11; k17 <= 14; ++k17) {
					setBlockAndMetadata(world, i12, 9, k17, woodBeamBlock, woodBeamMeta | 8);
				}
			} else {
				for (j1 = 6; j1 <= 8; ++j1) {
					setBlockAndMetadata(world, i12, j1, 3, wallBlock, wallMeta);
					setBlockAndMetadata(world, i12, j1, 11, wallBlock, wallMeta);
				}
				setBlockAndMetadata(world, i12, 9, 3, woodBeamBlock, woodBeamMeta | 4);
				setBlockAndMetadata(world, i12, 9, 11, woodBeamBlock, woodBeamMeta | 4);
				for (k17 = 0; k17 <= 2; ++k17) {
					setBlockAndMetadata(world, i12, 9, k17, roofSlabBlock, roofSlabMeta | 8);
				}
				for (k17 = 12; k17 <= 14; ++k17) {
					setBlockAndMetadata(world, i12, 9, k17, roofSlabBlock, roofSlabMeta | 8);
				}
			}
			if (i2 == 0) {
				setBlockAndMetadata(world, i12, 6, 3, doorBlock, 3);
				setBlockAndMetadata(world, i12, 7, 3, doorBlock, 8);
				setBlockAndMetadata(world, i12, 8, 2, Blocks.torch, 4);
				setBlockAndMetadata(world, i12, 6, 11, doorBlock, 1);
				setBlockAndMetadata(world, i12, 7, 11, doorBlock, 8);
				setBlockAndMetadata(world, i12, 8, 12, Blocks.torch, 3);
			}
			if (i2 == 3) {
				setBlockAndMetadata(world, i12, 6, 1, bedBlock, 0);
				setBlockAndMetadata(world, i12, 6, 2, bedBlock, 8);
				setBlockAndMetadata(world, i12, 6, 0, Blocks.chest, 4);
				setBlockAndMetadata(world, i12, 6, 13, bedBlock, 2);
				setBlockAndMetadata(world, i12, 6, 12, bedBlock, 10);
				setBlockAndMetadata(world, i12, 6, 14, Blocks.chest, 4);
			}
			if (i2 == 1) {
				setBlockAndMetadata(world, i12, 6, 2, plankStairBlock, 2);
				setBlockAndMetadata(world, i12, 6, 0, plankBlock, plankMeta);
				placeMug(world, random, i12, 7, 0, 2, LOTRFoods.GONDOR_DRINK);
				setBlockAndMetadata(world, i12, 6, 12, plankStairBlock, 3);
				setBlockAndMetadata(world, i12, 6, 14, plankBlock, plankMeta);
				placeMug(world, random, i12, 7, 14, 0, LOTRFoods.GONDOR_DRINK);
			}
			for (k17 = 1; k17 <= 3; ++k17) {
				setBlockAndMetadata(world, i12, 10, k17, wallBlock, wallMeta);
			}
			for (k17 = 11; k17 <= 13; ++k17) {
				setBlockAndMetadata(world, i12, 10, k17, wallBlock, wallMeta);
			}
		}
		for (k14 = 5; k14 <= 9; ++k14) {
			int k2 = IntMath.mod(k14, 4);
			if (k2 == 1) {
				for (j1 = 6; j1 <= 8; ++j1) {
					setBlockAndMetadata(world, -4, j1, k14, woodBeamBlock, woodBeamMeta);
					for (i132 = -7; i132 <= -5; ++i132) {
						setBlockAndMetadata(world, i132, j1, k14, wallBlock, wallMeta);
					}
					setBlockAndMetadata(world, 8, j1, k14, woodBeamBlock, woodBeamMeta);
					for (i132 = 9; i132 <= 11; ++i132) {
						setBlockAndMetadata(world, i132, j1, k14, wallBlock, wallMeta);
					}
				}
				for (i1 = -7; i1 <= -4; ++i1) {
					setBlockAndMetadata(world, i1, 9, k14, woodBeamBlock, woodBeamMeta | 4);
				}
				for (i1 = 8; i1 <= 11; ++i1) {
					setBlockAndMetadata(world, i1, 9, k14, woodBeamBlock, woodBeamMeta | 4);
				}
			} else {
				for (j1 = 6; j1 <= 8; ++j1) {
					setBlockAndMetadata(world, -4, j1, k14, wallBlock, wallMeta);
					setBlockAndMetadata(world, 8, j1, k14, wallBlock, wallMeta);
				}
				setBlockAndMetadata(world, -4, 9, k14, woodBeamBlock, woodBeamMeta | 8);
				setBlockAndMetadata(world, 8, 9, k14, woodBeamBlock, woodBeamMeta | 8);
				for (i1 = -7; i1 <= -5; ++i1) {
					setBlockAndMetadata(world, i1, 9, k14, roofSlabBlock, roofSlabMeta | 8);
				}
				for (i1 = 9; i1 <= 11; ++i1) {
					setBlockAndMetadata(world, i1, 9, k14, roofSlabBlock, roofSlabMeta | 8);
				}
			}
			if (k2 == 3) {
				setBlockAndMetadata(world, -4, 6, k14, doorBlock, 0);
				setBlockAndMetadata(world, -4, 7, k14, doorBlock, 8);
				setBlockAndMetadata(world, -5, 8, k14, Blocks.torch, 1);
				setBlockAndMetadata(world, 8, 6, k14, doorBlock, 2);
				setBlockAndMetadata(world, 8, 7, k14, doorBlock, 8);
				setBlockAndMetadata(world, 9, 8, k14, Blocks.torch, 2);
			}
			if (k2 == 0) {
				setBlockAndMetadata(world, -6, 6, k14, bedBlock, 1);
				setBlockAndMetadata(world, -5, 6, k14, bedBlock, 9);
				setBlockAndMetadata(world, -7, 6, k14, Blocks.chest, 2);
				setBlockAndMetadata(world, 10, 6, k14, bedBlock, 3);
				setBlockAndMetadata(world, 9, 6, k14, bedBlock, 11);
				setBlockAndMetadata(world, 11, 6, k14, Blocks.chest, 2);
			}
			if (k2 == 2) {
				setBlockAndMetadata(world, -5, 6, k14, plankStairBlock, 1);
				setBlockAndMetadata(world, -7, 6, k14, plankBlock, plankMeta);
				placeMug(world, random, -7, 7, k14, 3, LOTRFoods.GONDOR_DRINK);
				setBlockAndMetadata(world, 9, 6, k14, plankStairBlock, 0);
				setBlockAndMetadata(world, 11, 6, k14, plankBlock, plankMeta);
				placeMug(world, random, 11, 7, k14, 1, LOTRFoods.GONDOR_DRINK);
			}
			for (i1 = -7; i1 <= -4; ++i1) {
				setBlockAndMetadata(world, i1, 10, k14, wallBlock, wallMeta);
				setBlockAndMetadata(world, i1, 11, k14, wallBlock, wallMeta);
			}
			for (i1 = 8; i1 <= 11; ++i1) {
				setBlockAndMetadata(world, i1, 10, k14, wallBlock, wallMeta);
				setBlockAndMetadata(world, i1, 11, k14, wallBlock, wallMeta);
			}
		}
		for (i12 = 7; i12 <= 8; ++i12) {
			for (k13 = 10; k13 <= 11; ++k13) {
				if (i12 == 7 && k13 == 10) {
					continue;
				}
				for (j1 = 6; j1 <= 8; ++j1) {
					setBlockAndMetadata(world, i12, j1, k13, wallBlock, wallMeta);
				}
				setBlockAndMetadata(world, i12, 9, k13, woodBeamBlock, woodBeamMeta | (i12 == 8 ? 8 : 4));
				setBlockAndMetadata(world, i12, 10, k13, wallBlock, wallMeta);
			}
		}
		setBlockAndMetadata(world, 8, 6, 10, doorBlock, 2);
		setBlockAndMetadata(world, 8, 7, 10, doorBlock, 8);
		setBlockAndMetadata(world, 9, 8, 10, Blocks.torch, 2);
		setBlockAndMetadata(world, 7, 8, 13, Blocks.torch, 2);
		for (i12 = 7; i12 <= 8; ++i12) {
			for (k13 = 12; k13 <= 13; ++k13) {
				setBlockAndMetadata(world, i12, 10, k13, wallBlock, wallMeta);
			}
		}
		for (i12 = 9; i12 <= 11; ++i12) {
			for (k13 = 10; k13 <= 11; ++k13) {
				setBlockAndMetadata(world, i12, 10, k13, wallBlock, wallMeta);
			}
		}
		for (i12 = 7; i12 <= 9; ++i12) {
			for (k13 = 12; k13 <= 14; ++k13) {
				setBlockAndMetadata(world, i12, 9, k13, roofSlabBlock, roofSlabMeta | 8);
			}
		}
		for (i12 = 9; i12 <= 11; ++i12) {
			for (k13 = 10; k13 <= 12; ++k13) {
				setBlockAndMetadata(world, i12, 9, k13, roofSlabBlock, roofSlabMeta | 8);
			}
		}
		setBlockAndMetadata(world, 11, 6, 11, bedBlock, 0);
		setBlockAndMetadata(world, 11, 6, 12, bedBlock, 8);
		setBlockAndMetadata(world, 11, 6, 10, Blocks.chest, 5);
		setBlockAndMetadata(world, 7, 6, 13, bedBlock, 2);
		setBlockAndMetadata(world, 7, 6, 12, bedBlock, 10);
		setBlockAndMetadata(world, 7, 6, 14, Blocks.chest, 4);
		setBlockAndMetadata(world, 9, 6, 14, plankBlock, plankMeta);
		placeMug(world, random, 9, 7, 14, 0, LOTRFoods.GONDOR_DRINK);
		setBlockAndMetadata(world, 10, 6, 13, plankBlock, plankMeta);
		placeMug(world, random, 10, 7, 13, 1, LOTRFoods.GONDOR_DRINK);
		for (i12 = 7; i12 <= 8; ++i12) {
			for (k13 = 3; k13 <= 4; ++k13) {
				if (i12 == 7 && k13 == 4) {
					continue;
				}
				for (j1 = 6; j1 <= 8; ++j1) {
					setBlockAndMetadata(world, i12, j1, k13, wallBlock, wallMeta);
				}
				setBlockAndMetadata(world, i12, 9, k13, woodBeamBlock, woodBeamMeta | (i12 == 8 ? 8 : 4));
				setBlockAndMetadata(world, i12, 10, k13, wallBlock, wallMeta);
			}
		}
		setBlockAndMetadata(world, 8, 6, 4, doorBlock, 2);
		setBlockAndMetadata(world, 8, 7, 4, doorBlock, 8);
		setBlockAndMetadata(world, 9, 8, 4, Blocks.torch, 2);
		setBlockAndMetadata(world, 7, 8, 1, Blocks.torch, 2);
		for (i12 = 7; i12 <= 8; ++i12) {
			for (k13 = 1; k13 <= 2; ++k13) {
				setBlockAndMetadata(world, i12, 10, k13, wallBlock, wallMeta);
			}
		}
		for (i12 = 9; i12 <= 11; ++i12) {
			for (k13 = 3; k13 <= 4; ++k13) {
				setBlockAndMetadata(world, i12, 10, k13, wallBlock, wallMeta);
			}
		}
		for (i12 = 7; i12 <= 9; ++i12) {
			for (k13 = 0; k13 <= 2; ++k13) {
				setBlockAndMetadata(world, i12, 9, k13, roofSlabBlock, roofSlabMeta | 8);
			}
		}
		for (i12 = 9; i12 <= 11; ++i12) {
			for (k13 = 2; k13 <= 4; ++k13) {
				setBlockAndMetadata(world, i12, 9, k13, roofSlabBlock, roofSlabMeta | 8);
			}
		}
		setBlockAndMetadata(world, 11, 6, 3, bedBlock, 2);
		setBlockAndMetadata(world, 11, 6, 2, bedBlock, 10);
		setBlockAndMetadata(world, 11, 6, 4, Blocks.chest, 5);
		setBlockAndMetadata(world, 7, 6, 1, bedBlock, 0);
		setBlockAndMetadata(world, 7, 6, 2, bedBlock, 8);
		setBlockAndMetadata(world, 7, 6, 0, Blocks.chest, 4);
		setBlockAndMetadata(world, 9, 6, 0, plankBlock, plankMeta);
		placeMug(world, random, 9, 7, 0, 2, LOTRFoods.GONDOR_DRINK);
		setBlockAndMetadata(world, 10, 6, 1, plankBlock, plankMeta);
		placeMug(world, random, 10, 7, 1, 1, LOTRFoods.GONDOR_DRINK);
		for (i12 = -4; i12 <= -3; ++i12) {
			for (k13 = 3; k13 <= 4; ++k13) {
				if (i12 == -3 && k13 == 4) {
					continue;
				}
				for (j1 = 6; j1 <= 8; ++j1) {
					setBlockAndMetadata(world, i12, j1, k13, wallBlock, wallMeta);
				}
				setBlockAndMetadata(world, i12, 9, k13, woodBeamBlock, woodBeamMeta | (i12 == -4 ? 8 : 4));
				setBlockAndMetadata(world, i12, 10, k13, wallBlock, wallMeta);
			}
		}
		setBlockAndMetadata(world, -4, 6, 4, doorBlock, 0);
		setBlockAndMetadata(world, -4, 7, 4, doorBlock, 8);
		setBlockAndMetadata(world, -5, 8, 4, Blocks.torch, 1);
		setBlockAndMetadata(world, -3, 8, 1, Blocks.torch, 1);
		for (i12 = -4; i12 <= -3; ++i12) {
			for (k13 = 1; k13 <= 2; ++k13) {
				setBlockAndMetadata(world, i12, 10, k13, wallBlock, wallMeta);
			}
		}
		for (i12 = -7; i12 <= -5; ++i12) {
			for (k13 = 3; k13 <= 4; ++k13) {
				setBlockAndMetadata(world, i12, 10, k13, wallBlock, wallMeta);
			}
		}
		for (i12 = -5; i12 <= -3; ++i12) {
			for (k13 = 0; k13 <= 2; ++k13) {
				setBlockAndMetadata(world, i12, 9, k13, roofSlabBlock, roofSlabMeta | 8);
			}
		}
		for (i12 = -7; i12 <= -5; ++i12) {
			for (k13 = 2; k13 <= 4; ++k13) {
				setBlockAndMetadata(world, i12, 9, k13, roofSlabBlock, roofSlabMeta | 8);
			}
		}
		setBlockAndMetadata(world, -7, 6, 3, bedBlock, 2);
		setBlockAndMetadata(world, -7, 6, 2, bedBlock, 10);
		setBlockAndMetadata(world, -7, 6, 4, Blocks.chest, 4);
		setBlockAndMetadata(world, -3, 6, 1, bedBlock, 0);
		setBlockAndMetadata(world, -3, 6, 2, bedBlock, 8);
		setBlockAndMetadata(world, -3, 6, 0, Blocks.chest, 5);
		setBlockAndMetadata(world, -5, 6, 0, plankBlock, plankMeta);
		placeMug(world, random, -5, 7, 0, 2, LOTRFoods.GONDOR_DRINK);
		setBlockAndMetadata(world, -6, 6, 1, plankBlock, plankMeta);
		placeMug(world, random, -6, 7, 1, 3, LOTRFoods.GONDOR_DRINK);
		for (i12 = -3; i12 <= 7; ++i12) {
			for (int k1221 : new int[]{5, 9}) {
				setBlockAndMetadata(world, i12, 11, k1221, roofSlabBlock, roofSlabMeta | 8);
			}
		}
		setBlockAndMetadata(world, -1, 11, 7, LOTRMod.chandelier, 1);
		setBlockAndMetadata(world, 7, 11, 7, LOTRMod.chandelier, 1);
		LOTREntityGondorBartender bartender = new LOTREntityGondorBartender(world);
		bartender.setSpecificLocationName(tavernNameNPC);
		spawnNPCAndSetHome(bartender, world, -4, 1, 7, 2);
		int men = 6 + random.nextInt(7);
		for (int l = 0; l < men; ++l) {
			LOTREntityGondorMan gondorian = new LOTREntityGondorMan(world);
			spawnNPCAndSetHome(gondorian, world, 2, 1, 7, 16);
		}
		return true;
	}

	public void placeMugOrPlate(World world, Random random, int i, int j, int k) {
		if (random.nextBoolean()) {
			placeMug(world, random, i, j, k, random.nextInt(4), LOTRFoods.GONDOR_DRINK);
		} else {
			placePlate(world, random, i, j, k, plateBlock, LOTRFoods.GONDOR);
		}
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		bedBlock = Blocks.bed;
		tavernName = LOTRNames.getGondorTavernName(random);
		tavernNameSign = new String[]{"", tavernName[0], tavernName[1], ""};
		tavernNameNPC = tavernName[0] + " " + tavernName[1];
	}
}
