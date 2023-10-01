package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityHobbit;
import lotr.common.entity.npc.LOTREntityHobbitBartender;
import lotr.common.entity.npc.LOTREntityHobbitShirriff;
import lotr.common.entity.npc.LOTRNames;
import lotr.common.item.LOTRItemBanner;
import lotr.common.item.LOTRItemLeatherHat;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenHobbitTavern extends LOTRWorldGenHobbitStructure {
	public String[] tavernName;
	public String[] tavernNameSign;
	public String tavernNameNPC;

	public LOTRWorldGenHobbitTavern(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int i1;
		int j1;
		int k1;
		int i122;
		int k12;
		int j12;
		int k13;
		int room;
		int i13;
		int j13;
		int i14;
		int j14;
		int i2;
		int i152;
		int i16;
		int k14;
		int k15;
		int j15;
		int k16;
		setOriginAndRotation(world, i, j, k, rotation, 12);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i17 = -18; i17 <= 18; ++i17) {
				for (int k17 = -12; k17 <= 19; ++k17) {
					j13 = getTopBlock(world, i17, k17) - 1;
					if (!isSurface(world, i17, j13, k17)) {
						return false;
					}
					if (j13 < minHeight) {
						minHeight = j13;
					}
					if (j13 > maxHeight) {
						maxHeight = j13;
					}
					if (maxHeight - minHeight <= 8) {
						continue;
					}
					return false;
				}
			}
		}
		for (i14 = -16; i14 <= 16; ++i14) {
			for (k12 = -12; k12 <= 18; ++k12) {
				int j16;
				int i22 = Math.abs(i14);
				int grassHeight = -1;
				if (i22 <= 14) {
					if (k12 <= -6) {
						grassHeight = k12 == -7 && i22 <= 1 || k12 == -6 && i22 <= 3 ? 1 : 0;
					} else if (k12 <= -5 && (i22 == 11 || i22 <= 5)) {
						grassHeight = 1;
					} else if (k12 <= -4 && i22 <= 11 || k12 <= -3 && i22 <= 3) {
						grassHeight = 1;
					}
				}
				if (grassHeight >= 0) {
					for (j13 = grassHeight; (j13 >= -1 || !isOpaque(world, i14, j13, k12)) && getY(j13) >= 0; --j13) {
						if (j13 == grassHeight) {
							setBlockAndMetadata(world, i14, j13, k12, Blocks.grass, 0);
						} else {
							setBlockAndMetadata(world, i14, j13, k12, Blocks.dirt, 0);
						}
						setGrassToDirt(world, i14, j13 - 1, k12);
					}
					for (j13 = grassHeight + 1; j13 <= 12; ++j13) {
						setAir(world, i14, j13, k12);
					}
					continue;
				}
				boolean wood = false;
				boolean beam = false;
				if (k12 >= -5 && k12 <= 17) {
					wood = i22 < 15 || k12 > -4 && k12 < 16;
				}
				if (i22 == 15 && (k12 == -4 || k12 == 16)) {
					beam = true;
				}
				if (k12 == 18 && i22 <= 14 && IntMath.mod(i14, 5) == 0) {
					beam = true;
				}
				if (!beam && !wood) {
					continue;
				}
				for (j16 = 5; (j16 >= 0 || !isOpaque(world, i14, j16, k12)) && getY(j16) >= 0; --j16) {
					if (beam) {
						setBlockAndMetadata(world, i14, j16, k12, beamBlock, beamMeta);
					} else {
						setBlockAndMetadata(world, i14, j16, k12, plankBlock, plankMeta);
					}
					setGrassToDirt(world, i14, j16 - 1, k12);
				}
				setBlockAndMetadata(world, i14, 6, k12, plankBlock, plankMeta);
				for (j16 = 8; j16 <= 12; ++j16) {
					setAir(world, i14, j16, k12);
				}
			}
		}
		for (i14 = -16; i14 <= 16; ++i14) {
			i2 = Math.abs(i14);
			if (i2 <= 4) {
				setBlockAndMetadata(world, i14, 1, -10, outFenceBlock, outFenceMeta);
			}
			if (i2 >= 4 && i2 <= 11) {
				setBlockAndMetadata(world, i14, 1, -9, outFenceBlock, outFenceMeta);
			}
			if (i2 >= 11 && i2 <= 13) {
				setBlockAndMetadata(world, i14, 1, -8, outFenceBlock, outFenceMeta);
			}
			if (i2 == 13) {
				setBlockAndMetadata(world, i14, 1, -7, outFenceBlock, outFenceMeta);
				setBlockAndMetadata(world, i14, 1, -6, outFenceBlock, outFenceMeta);
			}
			if (i2 == 0) {
				setBlockAndMetadata(world, i14, 1, -10, outFenceGateBlock, 0);
			}
			if (i2 == 4) {
				setBlockAndMetadata(world, i14, 2, -10, Blocks.torch, 5);
			}
			if (i2 <= 1) {
				if (i14 == 0) {
					setBlockAndMetadata(world, 0, 0, -12, pathBlock, pathMeta);
					setBlockAndMetadata(world, 0, 0, -11, pathBlock, pathMeta);
					setBlockAndMetadata(world, 0, 0, -10, pathBlock, pathMeta);
				}
				setBlockAndMetadata(world, i14, 0, -9, pathBlock, pathMeta);
				setBlockAndMetadata(world, i14, 0, -8, pathBlock, pathMeta);
				setBlockAndMetadata(world, i14, 1, -7, pathSlabBlock, pathSlabMeta);
				setBlockAndMetadata(world, i14, 1, -6, pathSlabBlock, pathSlabMeta);
				for (k16 = -5; k16 <= -2; ++k16) {
					setBlockAndMetadata(world, i14, 1, k16, pathBlock, pathMeta);
				}
			}
			if (i2 == 5 || i2 == 11) {
				setGrassToDirt(world, i14, 0, -4);
				for (j12 = 1; j12 <= 5; ++j12) {
					setBlockAndMetadata(world, i14, j12, -4, beamBlock, beamMeta);
				}
			}
			if (i2 == 6 || i2 == 10) {
				setBlockAndMetadata(world, i14, 3, -4, hedgeBlock, hedgeMeta);
				setBlockAndMetadata(world, i14, 2, -4, hedgeBlock, hedgeMeta);
				setBlockAndMetadata(world, i14, 1, -5, hedgeBlock, hedgeMeta);
				setBlockAndMetadata(world, i14, 0, -5, Blocks.grass, 0);
			}
			if (i2 >= 7 && i2 <= 9) {
				setBlockAndMetadata(world, i14, 2, -5, hedgeBlock, hedgeMeta);
				setBlockAndMetadata(world, i14, 1, -5, hedgeBlock, hedgeMeta);
				setBlockAndMetadata(world, i14, 0, -5, Blocks.grass, 0);
				setBlockAndMetadata(world, i14, 1, -6, hedgeBlock, hedgeMeta);
				setBlockAndMetadata(world, i14, 2, -4, brickBlock, brickMeta);
				setGrassToDirt(world, i14, 1, -4);
				setBlockAndMetadata(world, i14, 3, -3, LOTRMod.glassPane, 0);
				setBlockAndMetadata(world, i14, 4, -3, LOTRMod.glassPane, 0);
				if (i2 == 7 || i2 == 9) {
					placeFlowerPot(world, i14, 3, -4, getRandomFlower(world, random));
				}
			}
			if (i2 >= 6 && i2 <= 10) {
				setBlockAndMetadata(world, i14, 5, -4, plankStairBlock, 6);
			}
			if (i2 >= 5 && i2 <= 11) {
				setBlockAndMetadata(world, i14, 6, -4, plankBlock, plankMeta);
			}
			if (i2 == 13) {
				setBlockAndMetadata(world, i14, 3, -6, fence2Block, fence2Meta);
				setBlockAndMetadata(world, i14, 4, -6, Blocks.torch, 5);
			}
			if (i2 == 4) {
				setBlockAndMetadata(world, i14, 2, -4, hedgeBlock, hedgeMeta);
			}
			if (i2 != 3) {
				continue;
			}
			setBlockAndMetadata(world, i14, 2, -4, hedgeBlock, hedgeMeta);
			setBlockAndMetadata(world, i14, 2, -3, hedgeBlock, hedgeMeta);
		}
		for (i14 = -12; i14 <= 12; ++i14) {
			for (k12 = -8; k12 <= -2; ++k12) {
				for (j12 = 0; j12 <= 1; ++j12) {
					if (getBlock(world, i14, j12, k12) != Blocks.grass || !isAir(world, i14, j12 + 1, k12) || random.nextInt(12) != 0) {
						continue;
					}
					plantFlower(world, random, i14, j12 + 1, k12);
				}
			}
		}
		for (i14 = -2; i14 <= 2; ++i14) {
			for (j1 = 2; j1 <= 4; ++j1) {
				setAir(world, i14, j1, -2);
			}
		}
		setBlockAndMetadata(world, -2, 2, -2, plankStairBlock, 0);
		setBlockAndMetadata(world, -2, 4, -2, plankStairBlock, 4);
		setBlockAndMetadata(world, 2, 2, -2, plankStairBlock, 1);
		setBlockAndMetadata(world, 2, 4, -2, plankStairBlock, 5);
		for (i14 = -1; i14 <= 1; ++i14) {
			for (j1 = 2; j1 <= 4; ++j1) {
				setAir(world, i14, j1, -1);
				setBlockAndMetadata(world, i14, j1, 0, brickBlock, brickMeta);
			}
		}
		setBlockAndMetadata(world, -1, 2, -1, plankStairBlock, 0);
		setBlockAndMetadata(world, -1, 4, -1, plankStairBlock, 4);
		setBlockAndMetadata(world, 1, 2, -1, plankStairBlock, 1);
		setBlockAndMetadata(world, 1, 4, -1, plankStairBlock, 5);
		setBlockAndMetadata(world, 0, 1, -1, pathBlock, pathMeta);
		setBlockAndMetadata(world, 0, 1, 0, brickBlock, brickMeta);
		setBlockAndMetadata(world, 0, 2, 0, doorBlock, 3);
		setBlockAndMetadata(world, 0, 3, 0, doorBlock, 8);
		placeSign(world, 0, 4, -1, Blocks.wall_sign, 2, tavernNameSign);
		setBlockAndMetadata(world, -2, 3, -2, Blocks.torch, 4);
		setBlockAndMetadata(world, 2, 3, -2, Blocks.torch, 4);
		setBlockAndMetadata(world, -3, 4, -3, tileSlabBlock, tileSlabMeta | 8);
		setBlockAndMetadata(world, -2, 5, -3, tileSlabBlock, tileSlabMeta);
		setBlockAndMetadata(world, -1, 5, -3, tileSlabBlock, tileSlabMeta | 8);
		setBlockAndMetadata(world, 0, 5, -3, tileSlabBlock, tileSlabMeta | 8);
		setBlockAndMetadata(world, 1, 5, -3, tileSlabBlock, tileSlabMeta | 8);
		setBlockAndMetadata(world, 2, 5, -3, tileSlabBlock, tileSlabMeta);
		setBlockAndMetadata(world, 3, 4, -3, tileSlabBlock, tileSlabMeta | 8);
		if (random.nextBoolean()) {
			placeSign(world, -2, 2, -10, Blocks.standing_sign, MathHelper.getRandomIntegerInRange(random, 7, 9), tavernNameSign);
		} else {
			placeSign(world, 2, 2, -10, Blocks.standing_sign, MathHelper.getRandomIntegerInRange(random, 7, 9), tavernNameSign);
		}
		for (i14 = -3; i14 <= 3; ++i14) {
			setBlockAndMetadata(world, i14, 6, -3, roofStairBlock, 2);
		}
		setBlockAndMetadata(world, -3, 6, -4, roofStairBlock, 0);
		setBlockAndMetadata(world, -4, 6, -4, roofStairBlock, 2);
		setBlockAndMetadata(world, -4, 6, -5, roofStairBlock, 0);
		setBlockAndMetadata(world, 3, 6, -4, roofStairBlock, 1);
		setBlockAndMetadata(world, 4, 6, -4, roofStairBlock, 2);
		setBlockAndMetadata(world, 4, 6, -5, roofStairBlock, 1);
		for (i14 = -11; i14 <= -5; ++i14) {
			setBlockAndMetadata(world, i14, 6, -5, roofStairBlock, 2);
		}
		for (i14 = 5; i14 <= 11; ++i14) {
			setBlockAndMetadata(world, i14, 6, -5, roofStairBlock, 2);
		}
		setBlockAndMetadata(world, -11, 6, -6, roofStairBlock, 0);
		setBlockAndMetadata(world, 11, 6, -6, roofStairBlock, 1);
		for (i14 = -14; i14 <= -12; ++i14) {
			setBlockAndMetadata(world, i14, 6, -6, roofStairBlock, 2);
		}
		for (i14 = 12; i14 <= 14; ++i14) {
			setBlockAndMetadata(world, i14, 6, -6, roofStairBlock, 2);
		}
		setBlockAndMetadata(world, -15, 6, -6, roofStairBlock, 1);
		setBlockAndMetadata(world, -15, 6, -5, roofStairBlock, 2);
		setBlockAndMetadata(world, -16, 6, -5, roofStairBlock, 1);
		setBlockAndMetadata(world, -16, 6, -4, roofStairBlock, 2);
		setBlockAndMetadata(world, 15, 6, -6, roofStairBlock, 0);
		setBlockAndMetadata(world, 15, 6, -5, roofStairBlock, 2);
		setBlockAndMetadata(world, 16, 6, -5, roofStairBlock, 0);
		setBlockAndMetadata(world, 16, 6, -4, roofStairBlock, 2);
		for (int k18 = -4; k18 <= 16; ++k18) {
			setBlockAndMetadata(world, -17, 6, k18, roofStairBlock, 1);
			setBlockAndMetadata(world, 17, 6, k18, roofStairBlock, 0);
		}
		setBlockAndMetadata(world, -16, 6, 16, roofStairBlock, 3);
		setBlockAndMetadata(world, -16, 6, 17, roofStairBlock, 1);
		setBlockAndMetadata(world, -15, 6, 17, roofStairBlock, 3);
		setBlockAndMetadata(world, -15, 6, 18, roofStairBlock, 1);
		setBlockAndMetadata(world, 16, 6, 16, roofStairBlock, 3);
		setBlockAndMetadata(world, 16, 6, 17, roofStairBlock, 0);
		setBlockAndMetadata(world, 15, 6, 17, roofStairBlock, 3);
		setBlockAndMetadata(world, 15, 6, 18, roofStairBlock, 0);
		for (i14 = -14; i14 <= -11; ++i14) {
			setBlockAndMetadata(world, i14, 6, 18, roofStairBlock, 3);
		}
		for (i14 = 11; i14 <= 14; ++i14) {
			setBlockAndMetadata(world, i14, 6, 18, roofStairBlock, 3);
		}
		setBlockAndMetadata(world, -11, 6, 19, roofStairBlock, 1);
		setBlockAndMetadata(world, 11, 6, 19, roofStairBlock, 0);
		for (i14 = -10; i14 <= 10; ++i14) {
			setBlockAndMetadata(world, i14, 6, 18, roofBlock, roofMeta);
			setBlockAndMetadata(world, i14, 6, 19, roofStairBlock, 3);
			i2 = IntMath.mod(i14, 5);
			if (IntMath.mod(i14, 5) == 0) {
				continue;
			}
			setBlockAndMetadata(world, i14, 5, 18, plankStairBlock, 7);
			setBlockAndMetadata(world, i14, 1, 18, brickBlock, brickMeta);
			setGrassToDirt(world, i14, 0, 18);
			if (i2 == 1 || i2 == 4) {
				setBlockAndMetadata(world, i14, 2, 18, hedgeBlock, hedgeMeta);
			} else {
				placeFlowerPot(world, i14, 2, 18, getRandomFlower(world, random));
			}
			if (isOpaque(world, i14, 0, 18)) {
				continue;
			}
			setBlockAndMetadata(world, i14, 0, 18, plankStairBlock, 7);
		}
		int[] i18 = {-15, 12};
		i2 = i18.length;
		for (j12 = 0; j12 < i2; ++j12) {
			int i23;
			for (i23 = i122 = i18[j12]; i23 <= i122 + 3; ++i23) {
				setBlockAndMetadata(world, i23, 11, 6, brickStairBlock, 2);
				setBlockAndMetadata(world, i23, 11, 8, brickStairBlock, 3);
			}
			setBlockAndMetadata(world, i122, 11, 7, brickStairBlock, 1);
			setBlockAndMetadata(world, i122 + 3, 11, 7, brickStairBlock, 0);
			for (i23 = i122 + 1; i23 <= i122 + 2; ++i23) {
				setBlockAndMetadata(world, i23, 11, 7, brickBlock, brickMeta);
				setBlockAndMetadata(world, i23, 12, 7, Blocks.flower_pot, 0);
			}
		}
		for (int i1221 : new int[]{-16, 16}) {
			for (k1 = 3; k1 <= 4; ++k1) {
				for (j15 = 2; j15 <= 3; ++j15) {
					setBlockAndMetadata(world, i1221, j15, k1, LOTRMod.glassPane, 0);
				}
			}
		}
		for (int i1221 : new int[]{-17, 17}) {
			for (k1 = 2; k1 <= 10; ++k1) {
				if (k1 == 6) {
					continue;
				}
				setBlockAndMetadata(world, i1221, 1, k1, brickBlock, brickMeta);
				setGrassToDirt(world, i1221, 0, k1);
				if (k1 == 2 || k1 == 5 || k1 == 7 || k1 == 10) {
					setBlockAndMetadata(world, i1221, 2, k1, hedgeBlock, hedgeMeta);
					continue;
				}
				placeFlowerPot(world, i1221, 2, k1, getRandomFlower(world, random));
			}
			for (int k19 : new int[]{1, 6, 11}) {
				for (int j17 = 5; (j17 >= 0 || !isOpaque(world, i1221, j17, k19)) && getY(j17) >= 0; --j17) {
					setBlockAndMetadata(world, i1221, j17, k19, beamBlock, beamMeta);
					setGrassToDirt(world, i1221, j17, k19);
				}
			}
			for (k1 = 1; k1 <= 11; ++k1) {
				setBlockAndMetadata(world, i1221, 6, k1, roofBlock, roofMeta);
			}
		}
		for (k14 = 2; k14 <= 10; ++k14) {
			if (k14 == 6) {
				continue;
			}
			if (!isOpaque(world, -17, 0, k14)) {
				setBlockAndMetadata(world, -17, 0, k14, plankStairBlock, 5);
			}
			setBlockAndMetadata(world, -17, 5, k14, plankStairBlock, 5);
			if (!isOpaque(world, 17, 0, k14)) {
				setBlockAndMetadata(world, 17, 0, k14, plankStairBlock, 4);
			}
			setBlockAndMetadata(world, 17, 5, k14, plankStairBlock, 4);
		}
		for (k14 = 7; k14 <= 10; ++k14) {
			setBlockAndMetadata(world, -17, 5, k14, plankStairBlock, 5);
			setBlockAndMetadata(world, 17, 5, k14, plankStairBlock, 4);
		}
		for (k14 = 1; k14 <= 11; ++k14) {
			setBlockAndMetadata(world, -18, 6, k14, roofStairBlock, 1);
			setBlockAndMetadata(world, 18, 6, k14, roofStairBlock, 0);
		}
		for (int i1221 : new int[]{-18, 18}) {
			setBlockAndMetadata(world, i1221, 6, 0, roofStairBlock, 2);
			setBlockAndMetadata(world, i1221, 6, 12, roofStairBlock, 3);
		}
		for (i13 = -4; i13 <= 4; ++i13) {
			setBlockAndMetadata(world, i13, 7, -2, roofStairBlock, 2);
		}
		setBlockAndMetadata(world, -4, 7, -3, roofStairBlock, 0);
		setBlockAndMetadata(world, -5, 7, -3, roofStairBlock, 2);
		setBlockAndMetadata(world, -5, 7, -4, roofStairBlock, 0);
		setBlockAndMetadata(world, 4, 7, -3, roofStairBlock, 1);
		setBlockAndMetadata(world, 5, 7, -3, roofStairBlock, 2);
		setBlockAndMetadata(world, 5, 7, -4, roofStairBlock, 1);
		for (i13 = -12; i13 <= -6; ++i13) {
			setBlockAndMetadata(world, i13, 7, -4, roofStairBlock, 2);
		}
		for (i13 = 6; i13 <= 12; ++i13) {
			setBlockAndMetadata(world, i13, 7, -4, roofStairBlock, 2);
		}
		setBlockAndMetadata(world, -12, 7, -5, roofStairBlock, 0);
		setBlockAndMetadata(world, -13, 7, -5, roofStairBlock, 2);
		setBlockAndMetadata(world, -14, 7, -5, roofStairBlock, 1);
		setBlockAndMetadata(world, -14, 7, -4, roofStairBlock, 2);
		setBlockAndMetadata(world, -15, 7, -4, roofStairBlock, 1);
		setBlockAndMetadata(world, -15, 7, -3, roofStairBlock, 2);
		setBlockAndMetadata(world, 12, 7, -5, roofStairBlock, 1);
		setBlockAndMetadata(world, 13, 7, -5, roofStairBlock, 2);
		setBlockAndMetadata(world, 14, 7, -5, roofStairBlock, 0);
		setBlockAndMetadata(world, 14, 7, -4, roofStairBlock, 2);
		setBlockAndMetadata(world, 15, 7, -4, roofStairBlock, 0);
		setBlockAndMetadata(world, 15, 7, -3, roofStairBlock, 2);
		for (k13 = -3; k13 <= 0; ++k13) {
			setBlockAndMetadata(world, -16, 7, k13, roofStairBlock, 1);
			setBlockAndMetadata(world, 16, 7, k13, roofStairBlock, 0);
		}
		setBlockAndMetadata(world, -16, 7, 1, roofStairBlock, 2);
		setBlockAndMetadata(world, 16, 7, 1, roofStairBlock, 2);
		for (k13 = 1; k13 <= 11; ++k13) {
			setBlockAndMetadata(world, -17, 7, k13, roofStairBlock, 1);
			setBlockAndMetadata(world, 17, 7, k13, roofStairBlock, 0);
		}
		setBlockAndMetadata(world, -16, 7, 11, roofStairBlock, 3);
		setBlockAndMetadata(world, 16, 7, 11, roofStairBlock, 3);
		for (k13 = 12; k13 <= 15; ++k13) {
			setBlockAndMetadata(world, -16, 7, k13, roofStairBlock, 1);
			setBlockAndMetadata(world, 16, 7, k13, roofStairBlock, 0);
		}
		setBlockAndMetadata(world, -15, 7, 15, roofStairBlock, 3);
		setBlockAndMetadata(world, -15, 7, 16, roofStairBlock, 1);
		setBlockAndMetadata(world, -14, 7, 16, roofStairBlock, 3);
		setBlockAndMetadata(world, -14, 7, 17, roofStairBlock, 1);
		setBlockAndMetadata(world, 15, 7, 15, roofStairBlock, 3);
		setBlockAndMetadata(world, 15, 7, 16, roofStairBlock, 0);
		setBlockAndMetadata(world, 14, 7, 16, roofStairBlock, 3);
		setBlockAndMetadata(world, 14, 7, 17, roofStairBlock, 0);
		for (i13 = -13; i13 <= -11; ++i13) {
			setBlockAndMetadata(world, i13, 7, 17, roofStairBlock, 3);
		}
		for (i13 = 11; i13 <= 13; ++i13) {
			setBlockAndMetadata(world, i13, 7, 17, roofStairBlock, 3);
		}
		setBlockAndMetadata(world, -10, 7, 17, roofStairBlock, 1);
		setBlockAndMetadata(world, 10, 7, 17, roofStairBlock, 0);
		for (i13 = -10; i13 <= 10; ++i13) {
			setBlockAndMetadata(world, i13, 7, 18, roofStairBlock, 3);
		}
		for (i13 = -5; i13 <= 5; ++i13) {
			setBlockAndMetadata(world, i13, 8, -1, roofStairBlock, 2);
		}
		setBlockAndMetadata(world, -5, 8, -2, roofStairBlock, 0);
		setBlockAndMetadata(world, -6, 8, -2, roofStairBlock, 2);
		setBlockAndMetadata(world, -6, 8, -3, roofStairBlock, 0);
		setBlockAndMetadata(world, 5, 8, -2, roofStairBlock, 1);
		setBlockAndMetadata(world, 6, 8, -2, roofStairBlock, 2);
		setBlockAndMetadata(world, 6, 8, -3, roofStairBlock, 1);
		for (i13 = -13; i13 <= -7; ++i13) {
			setBlockAndMetadata(world, i13, 8, -3, roofStairBlock, 2);
		}
		for (i13 = 7; i13 <= 13; ++i13) {
			setBlockAndMetadata(world, i13, 8, -3, roofStairBlock, 2);
		}
		setBlockAndMetadata(world, -13, 8, -4, roofSlabBlock, roofSlabMeta);
		setBlockAndMetadata(world, 13, 8, -4, roofSlabBlock, roofSlabMeta);
		setBlockAndMetadata(world, -14, 8, -3, roofStairBlock, 1);
		setBlockAndMetadata(world, -14, 8, -2, roofStairBlock, 2);
		setBlockAndMetadata(world, 14, 8, -3, roofStairBlock, 0);
		setBlockAndMetadata(world, 14, 8, -2, roofStairBlock, 2);
		for (k13 = -2; k13 <= 1; ++k13) {
			setBlockAndMetadata(world, -15, 8, k13, roofStairBlock, 1);
			setBlockAndMetadata(world, 15, 8, k13, roofStairBlock, 0);
		}
		setBlockAndMetadata(world, -15, 8, 2, roofStairBlock, 2);
		setBlockAndMetadata(world, 15, 8, 2, roofStairBlock, 2);
		for (k13 = 2; k13 <= 10; ++k13) {
			setBlockAndMetadata(world, -16, 8, k13, roofStairBlock, 1);
			setBlockAndMetadata(world, 16, 8, k13, roofStairBlock, 0);
		}
		setBlockAndMetadata(world, -15, 8, 10, roofStairBlock, 3);
		setBlockAndMetadata(world, 15, 8, 10, roofStairBlock, 3);
		for (k13 = 11; k13 <= 14; ++k13) {
			setBlockAndMetadata(world, -15, 8, k13, roofStairBlock, 1);
			setBlockAndMetadata(world, 15, 8, k13, roofStairBlock, 0);
		}
		setBlockAndMetadata(world, -14, 8, 14, roofStairBlock, 3);
		setBlockAndMetadata(world, -14, 8, 15, roofStairBlock, 1);
		setBlockAndMetadata(world, -13, 8, 15, roofStairBlock, 3);
		setBlockAndMetadata(world, -13, 8, 16, roofStairBlock, 1);
		setBlockAndMetadata(world, 14, 8, 14, roofStairBlock, 3);
		setBlockAndMetadata(world, 14, 8, 15, roofStairBlock, 0);
		setBlockAndMetadata(world, 13, 8, 15, roofStairBlock, 3);
		setBlockAndMetadata(world, 13, 8, 16, roofStairBlock, 0);
		for (i13 = -12; i13 <= -10; ++i13) {
			setBlockAndMetadata(world, i13, 8, 16, roofStairBlock, 3);
		}
		for (i13 = 10; i13 <= 12; ++i13) {
			setBlockAndMetadata(world, i13, 8, 16, roofStairBlock, 3);
		}
		setBlockAndMetadata(world, -9, 8, 16, roofStairBlock, 1);
		setBlockAndMetadata(world, 9, 8, 16, roofStairBlock, 0);
		for (i13 = -9; i13 <= 9; ++i13) {
			setBlockAndMetadata(world, i13, 8, 17, roofStairBlock, 3);
		}
		for (i13 = -16; i13 <= 16; ++i13) {
			boolean roof;
			i2 = Math.abs(i13);
			for (k16 = -4; k16 <= 17; ++k16) {
				roof = false;
				if (k16 == -4) {
					roof = i2 == 13;
				}
				if (k16 == -3) {
					roof = i2 >= 6 && i2 <= 14;
				}
				if (k16 == -2) {
					roof = i2 >= 5 && i2 <= 15;
				}
				if (k16 >= -1 && k16 <= 1) {
					roof = i2 <= 15;
				}
				if (k16 >= 2 && k16 <= 10) {
					roof = i2 <= 16;
				}
				if (k16 >= 11 && k16 <= 14) {
					roof = i2 <= 15;
				}
				if (k16 == 15) {
					roof = i2 <= 14;
				}
				if (k16 == 16) {
					roof = i2 <= 13;
				}
				if (k16 == 17) {
					roof = i2 <= 9;
				}
				if (!roof) {
					continue;
				}
				setBlockAndMetadata(world, i13, 7, k16, roofBlock, roofMeta);
			}
			for (k16 = -2; k16 <= 16; ++k16) {
				roof = false;
				if (k16 == -2) {
					roof = i2 >= 7 && i2 <= 13;
				}
				if (k16 == -1) {
					roof = i2 >= 6 && i2 <= 14;
				}
				if (k16 >= 0 && k16 <= 2) {
					roof = i2 <= 14;
				}
				if (k16 >= 3 && k16 <= 9) {
					roof = i2 <= 15;
				}
				if (k16 >= 10 && k16 <= 13) {
					roof = i2 <= 14;
				}
				if (k16 == 14) {
					roof = i2 <= 13;
				}
				if (k16 == 15) {
					roof = i2 <= 12;
				}
				if (k16 == 16) {
					roof = i2 <= 8;
				}
				if (!roof) {
					continue;
				}
				setBlockAndMetadata(world, i13, 8, k16, roofBlock, roofMeta);
				setBlockAndMetadata(world, i13, 9, k16, roofSlabBlock, roofSlabMeta);
			}
		}
		for (i13 = -6; i13 <= 6; ++i13) {
			i2 = Math.abs(i13);
			for (k16 = 1; k16 <= 15; ++k16) {
				room = 0;
				if (k16 == 1 && i2 <= 1) {
					room = 1;
				}
				if (k16 == 2 && i2 <= 2) {
					room = 1;
				}
				if (k16 == 3 && i2 <= 3) {
					room = 1;
				}
				if (k16 == 4 && i2 <= 4) {
					room = 1;
				}
				if (k16 == 5 && i2 <= 5) {
					room = 1;
				}
				if (k16 >= 6 && k16 <= 10 && i2 <= 6) {
					room = 1;
				}
				if (k16 >= 11 && k16 <= 12 && i2 <= 5) {
					room = 1;
				}
				if (k16 == 13 && i2 <= 4) {
					room = 1;
				}
				if (k16 >= 14 && k16 <= 15 && i2 <= 2) {
					room = 1;
				}
				if (room == 0) {
					continue;
				}
				setBlockAndMetadata(world, i13, 1, k16, floorBlock, floorMeta);
				for (j13 = 2; j13 <= 5; ++j13) {
					setAir(world, i13, j13, k16);
				}
			}
			for (j12 = 2; j12 <= 4; ++j12) {
				if (i2 == 2) {
					setBlockAndMetadata(world, i13, j12, 1, brickBlock, brickMeta);
				}
				if (i2 == 4) {
					setBlockAndMetadata(world, i13, j12, 3, beamBlock, beamMeta);
				}
				if (i2 == 6) {
					setBlockAndMetadata(world, i13, j12, 5, beamBlock, beamMeta);
					setBlockAndMetadata(world, i13, j12, 11, beamBlock, beamMeta);
				}
				if (i2 == 5) {
					setBlockAndMetadata(world, i13, j12, 13, beamBlock, beamMeta);
				}
				if (i2 != 3) {
					continue;
				}
				setBlockAndMetadata(world, i13, j12, 14, beamBlock, beamMeta);
			}
		}
		for (i13 = -5; i13 <= 5; ++i13) {
			for (k12 = 11; k12 <= 15; ++k12) {
				setBlockAndMetadata(world, i13, 5, k12, plankBlock, plankMeta);
			}
			setBlockAndMetadata(world, i13, 5, 10, plankStairBlock, 6);
		}
		for (i13 = -1; i13 <= 1; ++i13) {
			setBlockAndMetadata(world, i13, 5, 1, plankBlock, plankMeta);
		}
		for (i13 = -2; i13 <= 2; ++i13) {
			setBlockAndMetadata(world, i13, 5, 2, plankStairBlock, 7);
		}
		setBlockAndMetadata(world, -2, 5, 3, plankStairBlock, 4);
		setBlockAndMetadata(world, 2, 5, 3, plankStairBlock, 5);
		setBlockAndMetadata(world, -3, 5, 3, plankStairBlock, 7);
		setBlockAndMetadata(world, 3, 5, 3, plankStairBlock, 7);
		setBlockAndMetadata(world, -3, 5, 4, plankStairBlock, 4);
		setBlockAndMetadata(world, 3, 5, 4, plankStairBlock, 5);
		setBlockAndMetadata(world, -4, 5, 4, plankStairBlock, 7);
		setBlockAndMetadata(world, 4, 5, 4, plankStairBlock, 7);
		setBlockAndMetadata(world, -4, 5, 5, plankStairBlock, 4);
		setBlockAndMetadata(world, 4, 5, 5, plankStairBlock, 5);
		setBlockAndMetadata(world, -5, 5, 5, plankStairBlock, 7);
		setBlockAndMetadata(world, 5, 5, 5, plankStairBlock, 7);
		setBlockAndMetadata(world, -5, 5, 6, plankStairBlock, 4);
		setBlockAndMetadata(world, 5, 5, 6, plankStairBlock, 5);
		setBlockAndMetadata(world, -6, 5, 6, plankStairBlock, 7);
		setBlockAndMetadata(world, 6, 5, 6, plankStairBlock, 7);
		for (k13 = 7; k13 <= 10; ++k13) {
			setBlockAndMetadata(world, -6, 5, k13, plankStairBlock, 4);
			setBlockAndMetadata(world, 6, 5, k13, plankStairBlock, 5);
		}
		setBlockAndMetadata(world, 0, 4, 1, Blocks.torch, 3);
		setBlockAndMetadata(world, -6, 3, 6, Blocks.torch, 3);
		setBlockAndMetadata(world, 6, 3, 6, Blocks.torch, 3);
		setBlockAndMetadata(world, -6, 3, 10, Blocks.torch, 4);
		setBlockAndMetadata(world, 6, 3, 10, Blocks.torch, 4);
		setBlockAndMetadata(world, 0, 5, 5, chandelierBlock, chandelierMeta);
		setBlockAndMetadata(world, -4, 5, 8, chandelierBlock, chandelierMeta);
		setBlockAndMetadata(world, 4, 5, 8, chandelierBlock, chandelierMeta);
		setBlockAndMetadata(world, -2, 3, 2, Blocks.tripwire_hook, 0);
		setBlockAndMetadata(world, 2, 3, 2, Blocks.tripwire_hook, 0);
		setBlockAndMetadata(world, -3, 3, 3, Blocks.tripwire_hook, 0);
		setBlockAndMetadata(world, 3, 3, 3, Blocks.tripwire_hook, 0);
		setBlockAndMetadata(world, -4, 3, 4, Blocks.tripwire_hook, 1);
		setBlockAndMetadata(world, 4, 3, 4, Blocks.tripwire_hook, 3);
		for (i13 = -1; i13 <= 1; ++i13) {
			for (k12 = 1; k12 <= 2; ++k12) {
				setBlockAndMetadata(world, i13, 2, k12, carpetBlock, carpetMeta);
			}
		}
		for (i13 = -2; i13 <= 2; ++i13) {
			for (k12 = 5; k12 <= 7; ++k12) {
				setBlockAndMetadata(world, i13, 2, k12, carpetBlock, carpetMeta);
			}
		}
		for (i13 = -3; i13 <= 3; ++i13) {
			i2 = Math.abs(i13);
			setBlockAndMetadata(world, i13, 2, 11, plank2Block, plank2Meta);
			setBlockAndMetadata(world, i13, 4, 11, fence2Block, fence2Meta);
			if (IntMath.mod(i13, 2) == 1) {
				setBlockAndMetadata(world, i13, 2, 9, fence2Block, fence2Meta);
			}
			if (i2 == 2) {
				placeBarrel(world, random, i13, 3, 11, 3, LOTRFoods.HOBBIT_DRINK);
			}
			if (i2 != 1) {
				continue;
			}
			placeMug(world, random, i13, 3, 11, 0, LOTRFoods.HOBBIT_DRINK);
		}
		for (k13 = 12; k13 <= 13; ++k13) {
			int[] i24 = {-3, 3};
			j12 = i24.length;
			for (room = 0; room < j12; ++room) {
				i152 = i24[room];
				setBlockAndMetadata(world, i152, 2, k13, plank2Block, plank2Meta);
				setBlockAndMetadata(world, i152, 4, k13, fence2Block, fence2Meta);
			}
		}
		setBlockAndMetadata(world, 3, 2, 12, fenceGate2Block, 1);
		for (i13 = -2; i13 <= 2; ++i13) {
			setBlockAndMetadata(world, i13, 4, 15, plankStairBlock, 6);
		}
		setBlockAndMetadata(world, -2, 4, 14, Blocks.torch, 2);
		setBlockAndMetadata(world, 2, 4, 14, Blocks.torch, 1);
		setBlockAndMetadata(world, -2, 2, 15, Blocks.crafting_table, 0);
		placeChest(world, random, -1, 2, 15, 2, LOTRChestContents.HOBBIT_HOLE_LARDER);
		setBlockAndMetadata(world, 0, 2, 15, plankBlock, plankMeta);
		placeFlowerPot(world, 0, 3, 15, new ItemStack(LOTRMod.shireHeather));
		setBlockAndMetadata(world, 1, 2, 15, Blocks.cauldron, 3);
		setBlockAndMetadata(world, 2, 2, 15, LOTRMod.hobbitOven, 2);
		int[] i19 = {-7, 7};
		i2 = i19.length;
		for (j12 = 0; j12 < i2; ++j12) {
			i122 = i19[j12];
			setBlockAndMetadata(world, i122, 1, 8, floorBlock, floorMeta);
			setBlockAndMetadata(world, i122, 2, 8, carpetBlock, carpetMeta);
			setAir(world, i122, 3, 8);
			setBlockAndMetadata(world, i122, 2, 7, plankStairBlock, 3);
			setBlockAndMetadata(world, i122, 3, 7, plankStairBlock, 7);
			setBlockAndMetadata(world, i122, 2, 9, plankStairBlock, 2);
			setBlockAndMetadata(world, i122, 3, 9, plankStairBlock, 6);
		}
		for (k15 = 7; k15 <= 9; ++k15) {
			setBlockAndMetadata(world, -6, 2, k15, carpetBlock, carpetMeta);
			setBlockAndMetadata(world, -5, 2, k15, carpetBlock, carpetMeta);
			setBlockAndMetadata(world, 5, 2, k15, carpetBlock, carpetMeta);
			setBlockAndMetadata(world, 6, 2, k15, carpetBlock, carpetMeta);
		}
		for (i1 = -15; i1 <= -8; ++i1) {
			for (k12 = 3; k12 <= 14; ++k12) {
				setBlockAndMetadata(world, i1, 0, k12, floorBlock, floorMeta);
				for (j12 = 1; j12 <= 5; ++j12) {
					setAir(world, i1, j12, k12);
				}
			}
		}
		for (i1 = -15; i1 <= -11; ++i1) {
			for (k12 = -3; k12 <= 3; ++k12) {
				setBlockAndMetadata(world, i1, 0, k12, floorBlock, floorMeta);
				for (j12 = 1; j12 <= 5; ++j12) {
					setAir(world, i1, j12, k12);
				}
			}
		}
		for (i1 = -10; i1 <= -5; ++i1) {
			for (k12 = -2; k12 <= 3; ++k12) {
				setBlockAndMetadata(world, i1, 1, k12, floorBlock, floorMeta);
				for (j12 = 2; j12 <= 5; ++j12) {
					setAir(world, i1, j12, k12);
				}
			}
		}
		for (j14 = 1; j14 <= 5; ++j14) {
			setBlockAndMetadata(world, -15, j14, 14, beamBlock, beamMeta);
			setBlockAndMetadata(world, -9, j14, 14, beamBlock, beamMeta);
			setBlockAndMetadata(world, -8, j14, 14, plankBlock, plankMeta);
			setBlockAndMetadata(world, -8, j14, 11, beamBlock, beamMeta);
			setBlockAndMetadata(world, -8, j14, 5, beamBlock, beamMeta);
			setBlockAndMetadata(world, -8, j14, 4, plankBlock, plankMeta);
			setBlockAndMetadata(world, -9, j14, 3, beamBlock, beamMeta);
		}
		setBlockAndMetadata(world, -8, 3, 6, Blocks.torch, 3);
		setBlockAndMetadata(world, -8, 3, 10, Blocks.torch, 4);
		for (k15 = 6; k15 <= 10; ++k15) {
			setBlockAndMetadata(world, -8, 1, k15, floorStairBlock, 1);
			setBlockAndMetadata(world, -8, 5, k15, plankStairBlock, 5);
		}
		setBlockAndMetadata(world, -9, 1, 11, plank2Block, plank2Meta);
		for (j14 = 2; j14 <= 4; ++j14) {
			setBlockAndMetadata(world, -9, j14, 11, fence2Block, fence2Meta);
		}
		setBlockAndMetadata(world, -9, 5, 11, plank2Block, plank2Meta);
		for (k15 = 12; k15 <= 13; ++k15) {
			setBlockAndMetadata(world, -9, 1, k15, plank2StairBlock, 1);
			setBlockAndMetadata(world, -8, 1, k15, plankBlock, plankMeta);
			setBlockAndMetadata(world, -8, 2, k15, plankStairBlock, 5);
			placeFlowerPot(world, -8, 3, k15, getRandomFlower(world, random));
			setBlockAndMetadata(world, -8, 4, k15, plankStairBlock, 5);
			setBlockAndMetadata(world, -8, 5, k15, plankBlock, plankMeta);
			setBlockAndMetadata(world, -9, 5, k15, plank2StairBlock, 5);
		}
		for (i1 = -14; i1 <= -10; ++i1) {
			setBlockAndMetadata(world, i1, 1, 14, plank2StairBlock, 2);
			setBlockAndMetadata(world, i1, 5, 14, plank2StairBlock, 6);
		}
		for (i1 = -13; i1 <= -11; ++i1) {
			setBlockAndMetadata(world, i1, 2, 15, plankStairBlock, 6);
			setBlockAndMetadata(world, i1, 3, 15, LOTRMod.barrel, 2);
			setBlockAndMetadata(world, i1, 4, 15, plankStairBlock, 6);
		}
		for (k15 = 9; k15 <= 13; ++k15) {
			setBlockAndMetadata(world, -15, 1, k15, plank2StairBlock, 0);
			setBlockAndMetadata(world, -15, 5, k15, plank2StairBlock, 4);
		}
		for (k15 = 10; k15 <= 12; ++k15) {
			spawnItemFrame(world, -16, 3, k15, 1, getTavernFramedItem(random));
		}
		for (i1 = -13; i1 <= -11; ++i1) {
			for (k12 = 10; k12 <= 12; ++k12) {
				setBlockAndMetadata(world, i1, 1, k12, plank2Block, plank2Meta);
				placePlateOrMug(world, random, i1, 2, k12);
			}
		}
		setBlockAndMetadata(world, -12, 5, 11, fence2Block, fence2Meta);
		setBlockAndMetadata(world, -13, 5, 11, fence2Block, fence2Meta);
		setBlockAndMetadata(world, -11, 5, 11, fence2Block, fence2Meta);
		setBlockAndMetadata(world, -12, 5, 10, fence2Block, fence2Meta);
		setBlockAndMetadata(world, -12, 5, 12, fence2Block, fence2Meta);
		setBlockAndMetadata(world, -12, 4, 11, chandelierBlock, chandelierMeta);
		for (i1 = -15; i1 <= -12; ++i1) {
			for (k12 = 6; k12 <= 8; ++k12) {
				setBlockAndMetadata(world, i1, 1, k12, Blocks.stonebrick, 0);
				for (j12 = 2; j12 <= 4; ++j12) {
					setBlockAndMetadata(world, i1, j12, k12, brickBlock, brickMeta);
				}
				setBlockAndMetadata(world, i1, 5, k12, Blocks.stonebrick, 0);
				for (j12 = 6; j12 <= 10; ++j12) {
					setBlockAndMetadata(world, i1, j12, k12, brickBlock, brickMeta);
				}
			}
		}
		for (i1 = -14; i1 <= -13; ++i1) {
			setBlockAndMetadata(world, i1, 0, 7, LOTRMod.hearth, 0);
			setBlockAndMetadata(world, i1, 1, 7, Blocks.fire, 0);
			for (j1 = 2; j1 <= 10; ++j1) {
				setAir(world, i1, j1, 7);
			}
		}
		for (j14 = 1; j14 <= 3; ++j14) {
			setBlockAndMetadata(world, -12, j14, 7, barsBlock, 0);
		}
		setBlockAndMetadata(world, -10, 5, 7, chandelierBlock, chandelierMeta);
		for (k15 = 2; k15 <= 5; ++k15) {
			setBlockAndMetadata(world, -15, 1, k15, plank2StairBlock, 0);
			setBlockAndMetadata(world, -15, 5, k15, plank2StairBlock, 4);
		}
		for (j14 = 1; j14 <= 5; ++j14) {
			setBlockAndMetadata(world, -15, j14, 1, beamBlock, beamMeta);
		}
		setBlockAndMetadata(world, -14, 1, 1, plank2Block, plank2Meta);
		for (j14 = 2; j14 <= 4; ++j14) {
			setBlockAndMetadata(world, -14, j14, 1, fence2Block, fence2Meta);
		}
		setBlockAndMetadata(world, -14, 5, 1, plank2Block, plank2Meta);
		for (k15 = 3; k15 <= 4; ++k15) {
			setBlockAndMetadata(world, -13, 1, k15, plank2Block, plank2Meta);
			placePlateOrMug(world, random, -13, 2, k15);
		}
		setBlockAndMetadata(world, -13, 5, 4, chandelierBlock, chandelierMeta);
		for (k15 = -3; k15 <= 0; ++k15) {
			setBlockAndMetadata(world, -15, 1, k15, plank2StairBlock, 0);
			setBlockAndMetadata(world, -15, 5, k15, plank2StairBlock, 4);
		}
		for (k15 = -2; k15 <= -1; ++k15) {
			for (i16 = -13; i16 <= -12; ++i16) {
				setBlockAndMetadata(world, i16, 1, k15, plank2Block, plank2Meta);
				placePlateOrMug(world, random, i16, 2, k15);
			}
			spawnItemFrame(world, -16, 3, k15, 1, getTavernFramedItem(random));
		}
		for (i1 = -14; i1 <= -12; ++i1) {
			setBlockAndMetadata(world, i1, 1, -4, plank2StairBlock, 3);
			for (j1 = 2; j1 <= 4; ++j1) {
				setAir(world, i1, j1, -4);
			}
			setBlockAndMetadata(world, i1, 5, -4, plank2StairBlock, 7);
		}
		spawnItemFrame(world, -13, 3, -5, 0, getTavernFramedItem(random));
		setBlockAndMetadata(world, -12, 5, -1, chandelierBlock, chandelierMeta);
		for (k15 = -1; k15 <= 2; ++k15) {
			setBlockAndMetadata(world, -10, 1, k15, floorStairBlock, 1);
		}
		setBlockAndMetadata(world, -10, 1, 3, floorStairBlock, 3);
		for (j14 = 2; j14 <= 5; ++j14) {
			setBlockAndMetadata(world, -5, j14, 3, beamBlock, beamMeta);
			setBlockAndMetadata(world, -5, j14, -2, beamBlock, beamMeta);
		}
		for (i1 = -8; i1 <= -6; ++i1) {
			setBlockAndMetadata(world, i1, 2, 3, plank2StairBlock, 2);
			setBlockAndMetadata(world, i1, 5, 3, plank2StairBlock, 6);
		}
		setBlockAndMetadata(world, -7, 3, 4, LOTRMod.barrel, 2);
		setBlockAndMetadata(world, -7, 4, 4, plankStairBlock, 6);
		for (k15 = -1; k15 <= 2; ++k15) {
			setBlockAndMetadata(world, -5, 2, k15, plank2StairBlock, 1);
			setBlockAndMetadata(world, -5, 5, k15, plank2StairBlock, 5);
		}
		setBlockAndMetadata(world, -4, 3, 2, plankStairBlock, 2);
		setBlockAndMetadata(world, -4, 4, 2, plankStairBlock, 6);
		setBlockAndMetadata(world, -4, 3, -1, plankStairBlock, 3);
		setBlockAndMetadata(world, -4, 4, -1, plankStairBlock, 7);
		placeFlowerPot(world, -4, 3, 1, getRandomFlower(world, random));
		placeFlowerPot(world, -4, 3, 0, getRandomFlower(world, random));
		setBlockAndMetadata(world, -4, 4, 1, plankSlabBlock, plankSlabMeta | 8);
		setBlockAndMetadata(world, -4, 4, 0, plankSlabBlock, plankSlabMeta | 8);
		for (i1 = -9; i1 <= -6; ++i1) {
			setBlockAndMetadata(world, i1, 2, -2, plank2StairBlock, 3);
			setBlockAndMetadata(world, i1, 5, -2, plank2StairBlock, 7);
		}
		setBlockAndMetadata(world, -10, 1, -2, plank2Block, plank2Meta);
		setBlockAndMetadata(world, -10, 2, -2, plank2Block, plank2Meta);
		for (j14 = 3; j14 <= 4; ++j14) {
			setBlockAndMetadata(world, -10, j14, -2, fence2Block, fence2Meta);
		}
		setBlockAndMetadata(world, -10, 5, -2, plank2Block, plank2Meta);
		for (i1 = -8; i1 <= -7; ++i1) {
			for (k12 = 0; k12 <= 1; ++k12) {
				setBlockAndMetadata(world, i1, 2, k12, plank2Block, plank2Meta);
				placePlateOrMug(world, random, i1, 3, k12);
			}
		}
		setBlockAndMetadata(world, -8, 5, 1, chandelierBlock, chandelierMeta);
		for (i1 = 8; i1 <= 15; ++i1) {
			for (k12 = 3; k12 <= 14; ++k12) {
				setBlockAndMetadata(world, i1, 0, k12, floorBlock, floorMeta);
				for (j12 = 1; j12 <= 5; ++j12) {
					setAir(world, i1, j12, k12);
				}
			}
		}
		for (i1 = 11; i1 <= 15; ++i1) {
			for (k12 = -3; k12 <= 3; ++k12) {
				setBlockAndMetadata(world, i1, 0, k12, floorBlock, floorMeta);
				for (j12 = 1; j12 <= 5; ++j12) {
					setAir(world, i1, j12, k12);
				}
			}
		}
		for (i1 = 5; i1 <= 10; ++i1) {
			for (k12 = -2; k12 <= 3; ++k12) {
				setBlockAndMetadata(world, i1, 1, k12, floorBlock, floorMeta);
				for (j12 = 2; j12 <= 5; ++j12) {
					setAir(world, i1, j12, k12);
				}
			}
		}
		for (j14 = 1; j14 <= 5; ++j14) {
			setBlockAndMetadata(world, 15, j14, 14, beamBlock, beamMeta);
			setBlockAndMetadata(world, 9, j14, 14, beamBlock, beamMeta);
			setBlockAndMetadata(world, 8, j14, 14, plankBlock, plankMeta);
			setBlockAndMetadata(world, 8, j14, 11, beamBlock, beamMeta);
			setBlockAndMetadata(world, 8, j14, 5, beamBlock, beamMeta);
			setBlockAndMetadata(world, 8, j14, 4, plankBlock, plankMeta);
			setBlockAndMetadata(world, 9, j14, 3, beamBlock, beamMeta);
		}
		setBlockAndMetadata(world, 8, 3, 6, Blocks.torch, 3);
		setBlockAndMetadata(world, 8, 3, 10, Blocks.torch, 4);
		for (k15 = 6; k15 <= 10; ++k15) {
			setBlockAndMetadata(world, 8, 1, k15, floorStairBlock, 0);
			setBlockAndMetadata(world, 8, 5, k15, plankStairBlock, 4);
		}
		setBlockAndMetadata(world, 9, 1, 11, plank2Block, plank2Meta);
		for (j14 = 2; j14 <= 4; ++j14) {
			setBlockAndMetadata(world, 9, j14, 11, fence2Block, fence2Meta);
		}
		setBlockAndMetadata(world, 9, 5, 11, plank2Block, plank2Meta);
		for (k15 = 12; k15 <= 13; ++k15) {
			setBlockAndMetadata(world, 9, 1, k15, plank2StairBlock, 0);
			setBlockAndMetadata(world, 8, 1, k15, plankBlock, plankMeta);
			for (j1 = 2; j1 <= 4; ++j1) {
				setBlockAndMetadata(world, 8, j1, k15, Blocks.bookshelf, 0);
			}
			setBlockAndMetadata(world, 8, 5, k15, plankBlock, plankMeta);
			setBlockAndMetadata(world, 9, 5, k15, plank2StairBlock, 4);
		}
		for (i1 = 10; i1 <= 14; ++i1) {
			setBlockAndMetadata(world, i1, 1, 14, plank2StairBlock, 2);
			setBlockAndMetadata(world, i1, 5, 14, plank2StairBlock, 6);
		}
		for (i1 = 10; i1 <= 14; ++i1) {
			for (j1 = 2; j1 <= 4; ++j1) {
				setBlockAndMetadata(world, i1, j1, 15, Blocks.bookshelf, 0);
			}
		}
		for (k15 = 9; k15 <= 13; ++k15) {
			setBlockAndMetadata(world, 15, 1, k15, plank2StairBlock, 1);
			setBlockAndMetadata(world, 15, 5, k15, plank2StairBlock, 5);
		}
		placeWallBanner(world, 16, 4, 11, LOTRItemBanner.BannerType.HOBBIT, 3);
		for (i1 = 11; i1 <= 13; ++i1) {
			for (k12 = 10; k12 <= 12; ++k12) {
				setBlockAndMetadata(world, i1, 1, k12, plank2Block, plank2Meta);
				placePlateOrMug(world, random, i1, 2, k12);
			}
		}
		setBlockAndMetadata(world, 12, 5, 11, fence2Block, fence2Meta);
		setBlockAndMetadata(world, 13, 5, 11, fence2Block, fence2Meta);
		setBlockAndMetadata(world, 11, 5, 11, fence2Block, fence2Meta);
		setBlockAndMetadata(world, 12, 5, 10, fence2Block, fence2Meta);
		setBlockAndMetadata(world, 12, 5, 12, fence2Block, fence2Meta);
		setBlockAndMetadata(world, 12, 4, 11, chandelierBlock, chandelierMeta);
		for (i1 = 12; i1 <= 15; ++i1) {
			for (k12 = 6; k12 <= 8; ++k12) {
				setBlockAndMetadata(world, i1, 1, k12, Blocks.stonebrick, 0);
				for (j12 = 2; j12 <= 4; ++j12) {
					setBlockAndMetadata(world, i1, j12, k12, brickBlock, brickMeta);
				}
				setBlockAndMetadata(world, i1, 5, k12, Blocks.stonebrick, 0);
				for (j12 = 6; j12 <= 10; ++j12) {
					setBlockAndMetadata(world, i1, j12, k12, brickBlock, brickMeta);
				}
			}
		}
		for (i1 = 13; i1 <= 14; ++i1) {
			setBlockAndMetadata(world, i1, 0, 7, LOTRMod.hearth, 0);
			setBlockAndMetadata(world, i1, 1, 7, Blocks.fire, 0);
			for (j1 = 2; j1 <= 10; ++j1) {
				setAir(world, i1, j1, 7);
			}
		}
		for (j14 = 1; j14 <= 3; ++j14) {
			setBlockAndMetadata(world, 12, j14, 7, barsBlock, 0);
		}
		setBlockAndMetadata(world, 10, 5, 7, chandelierBlock, chandelierMeta);
		for (k15 = 2; k15 <= 5; ++k15) {
			setBlockAndMetadata(world, 15, 1, k15, plank2StairBlock, 1);
			setBlockAndMetadata(world, 15, 5, k15, plank2StairBlock, 5);
		}
		for (j14 = 1; j14 <= 5; ++j14) {
			setBlockAndMetadata(world, 15, j14, 1, beamBlock, beamMeta);
		}
		setBlockAndMetadata(world, 14, 1, 1, plank2Block, plank2Meta);
		for (j14 = 2; j14 <= 4; ++j14) {
			setBlockAndMetadata(world, 14, j14, 1, fence2Block, fence2Meta);
		}
		setBlockAndMetadata(world, 14, 5, 1, plank2Block, plank2Meta);
		for (k15 = 3; k15 <= 4; ++k15) {
			setBlockAndMetadata(world, 13, 1, k15, plank2Block, plank2Meta);
			placePlateOrMug(world, random, 13, 2, k15);
		}
		setBlockAndMetadata(world, 13, 5, 4, chandelierBlock, chandelierMeta);
		for (k15 = -3; k15 <= 0; ++k15) {
			setBlockAndMetadata(world, 15, 1, k15, plank2StairBlock, 1);
			setBlockAndMetadata(world, 15, 5, k15, plank2StairBlock, 5);
		}
		for (k15 = -2; k15 <= -1; ++k15) {
			for (i16 = 12; i16 <= 13; ++i16) {
				setBlockAndMetadata(world, i16, 1, k15, plank2Block, plank2Meta);
				placePlateOrMug(world, random, i16, 2, k15);
			}
		}
		placeWallBanner(world, 16, 4, -2, LOTRItemBanner.BannerType.HOBBIT, 3);
		for (i1 = 12; i1 <= 14; ++i1) {
			setBlockAndMetadata(world, i1, 1, -4, plank2StairBlock, 3);
			for (j1 = 2; j1 <= 4; ++j1) {
				setAir(world, i1, j1, -4);
			}
			setBlockAndMetadata(world, i1, 5, -4, plank2StairBlock, 7);
		}
		placeWallBanner(world, 13, 4, -5, LOTRItemBanner.BannerType.HOBBIT, 0);
		setBlockAndMetadata(world, 12, 5, -1, chandelierBlock, chandelierMeta);
		for (k15 = -1; k15 <= 2; ++k15) {
			setBlockAndMetadata(world, 10, 1, k15, floorStairBlock, 0);
		}
		setBlockAndMetadata(world, 10, 1, 3, floorStairBlock, 3);
		for (j14 = 2; j14 <= 5; ++j14) {
			setBlockAndMetadata(world, 5, j14, 3, beamBlock, beamMeta);
			setBlockAndMetadata(world, 5, j14, -2, beamBlock, beamMeta);
		}
		for (i1 = 6; i1 <= 8; ++i1) {
			setBlockAndMetadata(world, i1, 2, 3, plank2StairBlock, 2);
			setBlockAndMetadata(world, i1, 5, 3, plank2StairBlock, 6);
		}
		placeWallBanner(world, 7, 4, 4, LOTRItemBanner.BannerType.HOBBIT, 2);
		for (k15 = -1; k15 <= 2; ++k15) {
			setBlockAndMetadata(world, 5, 2, k15, plank2StairBlock, 0);
			setBlockAndMetadata(world, 4, 3, k15, Blocks.bookshelf, 0);
			setBlockAndMetadata(world, 4, 4, k15, Blocks.bookshelf, 0);
			setBlockAndMetadata(world, 5, 5, k15, plank2StairBlock, 4);
		}
		for (i1 = 6; i1 <= 9; ++i1) {
			setBlockAndMetadata(world, i1, 2, -2, plank2StairBlock, 3);
			setBlockAndMetadata(world, i1, 5, -2, plank2StairBlock, 7);
		}
		setBlockAndMetadata(world, 10, 1, -2, plank2Block, plank2Meta);
		setBlockAndMetadata(world, 10, 2, -2, plank2Block, plank2Meta);
		for (j14 = 3; j14 <= 4; ++j14) {
			setBlockAndMetadata(world, 10, j14, -2, fence2Block, fence2Meta);
		}
		setBlockAndMetadata(world, 10, 5, -2, plank2Block, plank2Meta);
		for (i1 = 7; i1 <= 8; ++i1) {
			for (k12 = 0; k12 <= 1; ++k12) {
				setBlockAndMetadata(world, i1, 2, k12, plank2Block, plank2Meta);
				placePlateOrMug(world, random, i1, 3, k12);
			}
		}
		setBlockAndMetadata(world, 8, 5, 1, chandelierBlock, chandelierMeta);
		for (i1 = -3; i1 <= 4; ++i1) {
			for (k12 = 11; k12 <= 15; ++k12) {
				setBlockAndMetadata(world, i1, -4, k12, floorBlock, floorMeta);
				for (j12 = -3; j12 <= 0; ++j12) {
					setAir(world, i1, j12, k12);
				}
			}
		}
		for (i1 = -3; i1 <= 4; ++i1) {
			int k110;
			int[] k111 = {10, 16};
			j12 = k111.length;
			for (i122 = 0; i122 < j12; ++i122) {
				k1 = k111[i122];
				setBlockAndMetadata(world, i1, -3, k1, plankBlock, plankMeta);
				setBlockAndMetadata(world, i1, -2, k1, beamBlock, beamMeta | 4);
				setBlockAndMetadata(world, i1, -1, k1, plankBlock, plankMeta);
			}
			for (k110 = 11; k110 <= 13; ++k110) {
				if (i1 < 0) {
					continue;
				}
				setBlockAndMetadata(world, i1, 0, k110, beamBlock, beamMeta | 4);
			}
			for (k110 = 14; k110 <= 15; ++k110) {
				setBlockAndMetadata(world, i1, 0, k110, beamBlock, beamMeta | 4);
			}
		}
		for (k15 = 11; k15 <= 15; ++k15) {
			int[] k112 = {-4, 5};
			j12 = k112.length;
			for (i122 = 0; i122 < j12; ++i122) {
				i152 = k112[i122];
				setBlockAndMetadata(world, i152, -3, k15, plankBlock, plankMeta);
				setBlockAndMetadata(world, i152, -2, k15, beamBlock, beamMeta | 8);
				setBlockAndMetadata(world, i152, -1, k15, plankBlock, plankMeta);
			}
		}
		for (j14 = -3; j14 <= -1; ++j14) {
			setBlockAndMetadata(world, -3, j14, 15, beamBlock, beamMeta);
			setBlockAndMetadata(world, 4, j14, 15, beamBlock, beamMeta);
			setBlockAndMetadata(world, 4, j14, 11, beamBlock, beamMeta);
			setBlockAndMetadata(world, 0, j14, 11, beamBlock, beamMeta);
		}
		placeBarrel(world, random, 4, -3, 14, 5, LOTRFoods.HOBBIT_DRINK);
		for (k15 = 12; k15 <= 13; ++k15) {
			placeChest(world, random, 4, -3, k15, 5, LOTRChestContents.HOBBIT_HOLE_LARDER);
		}
		for (k15 = 12; k15 <= 14; ++k15) {
			setBlockAndMetadata(world, 4, -2, k15, plankSlabBlock, plankSlabMeta | 8);
			placeBarrel(world, random, 4, -1, k15, 5, LOTRFoods.HOBBIT_DRINK);
		}
		placeBarrel(world, random, 1, -3, 11, 3, LOTRFoods.HOBBIT_DRINK);
		for (i1 = 2; i1 <= 3; ++i1) {
			placeChest(world, random, i1, -3, 11, 3, LOTRChestContents.HOBBIT_HOLE_LARDER);
		}
		for (i1 = 1; i1 <= 3; ++i1) {
			setBlockAndMetadata(world, i1, -2, 11, plankSlabBlock, plankSlabMeta | 8);
			Block cakeBlock = LOTRWorldGenHobbitStructure.getRandomCakeBlock(random);
			setBlockAndMetadata(world, i1, -1, 11, cakeBlock, 0);
		}
		for (k15 = 11; k15 <= 13; ++k15) {
			setAir(world, -2, 1, k15);
			setAir(world, -3, 1, k15);
			setAir(world, -3, 0, k15);
		}
		for (k15 = 10; k15 <= 12; ++k15) {
			setAir(world, -3, 0, k15);
		}
		setBlockAndMetadata(world, -3, 1, 14, floorBlock, floorMeta);
		for (i1 = -3; i1 <= -1; ++i1) {
			for (k12 = 11; k12 <= 12; ++k12) {
				for (j12 = -3; j12 <= -1; ++j12) {
					setBlockAndMetadata(world, i1, j12, k12, brickBlock, brickMeta);
				}
			}
		}
		for (int step = 0; step <= 2; ++step) {
			setBlockAndMetadata(world, -2, 1 - step, 14 - step, floorStairBlock, 2);
		}
		for (i1 = -3; i1 <= -2; ++i1) {
			setAir(world, i1, -1, 11);
			setBlockAndMetadata(world, i1, -2, 11, floorBlock, floorMeta);
		}
		setAir(world, -3, -1, 12);
		setBlockAndMetadata(world, -3, -2, 12, floorStairBlock, 3);
		for (i1 = -2; i1 <= -1; ++i1) {
			setBlockAndMetadata(world, i1, -1, 13, floorStairBlock, 7);
		}
		for (k15 = 13; k15 <= 14; ++k15) {
			setBlockAndMetadata(world, -3, -3, k15, floorBlock, floorMeta);
		}
		for (k15 = 13; k15 <= 15; ++k15) {
			setBlockAndMetadata(world, -2, -3, k15, floorStairBlock, 0);
		}
		setBlockAndMetadata(world, -2, -1, 15, Blocks.torch, 2);
		for (k15 = 11; k15 <= 13; ++k15) {
			setBlockAndMetadata(world, -4, 0, k15, beamBlock, beamMeta | 8);
			setBlockAndMetadata(world, -1, 0, k15, beamBlock, beamMeta | 8);
		}
		for (i1 = -3; i1 <= -2; ++i1) {
			setBlockAndMetadata(world, i1, 0, 10, beamBlock, beamMeta | 4);
		}
		LOTREntityHobbitBartender bartender = new LOTREntityHobbitBartender(world);
		bartender.setSpecificLocationName(tavernNameNPC);
		spawnNPCAndSetHome(bartender, world, 1, 2, 13, 2);
		for (int i1521 : new int[]{-10, 10}) {
			j15 = 1;
			int k113 = 7;
			int hobbits = 3 + random.nextInt(6);
			for (int l = 0; l < hobbits; ++l) {
				LOTREntityHobbit hobbit = new LOTREntityHobbit(world);
				spawnNPCAndSetHome(hobbit, world, i1521, j15, k113, 16);
			}
			if (random.nextInt(4) != 0) {
				continue;
			}
			LOTREntityHobbitShirriff shirriffChief = new LOTREntityHobbitShirriff(world);
			shirriffChief.spawnRidingHorse = false;
			spawnNPCAndSetHome(shirriffChief, world, i1521, j15, k113, 16);
		}
		placeSign(world, -8, 4, 8, Blocks.wall_sign, 5, LOTRNames.getHobbitTavernQuote(random));
		placeSign(world, 8, 4, 8, Blocks.wall_sign, 4, LOTRNames.getHobbitTavernQuote(random));
		return true;
	}

	public ItemStack getTavernFramedItem(Random random) {
		ItemStack[] items = {new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.leatherHat), LOTRItemLeatherHat.setFeatherColor(new ItemStack(LOTRMod.leatherHat), 16777215), LOTRItemLeatherHat.setHatColor(new ItemStack(LOTRMod.leatherHat), 2301981), LOTRItemLeatherHat.setFeatherColor(LOTRItemLeatherHat.setHatColor(new ItemStack(LOTRMod.leatherHat), 2301981), 3381529), new ItemStack(LOTRMod.hobbitPipe), new ItemStack(Items.book), new ItemStack(Items.feather), new ItemStack(Items.wooden_sword), new ItemStack(Items.bow), new ItemStack(LOTRMod.mug), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugCider), new ItemStack(LOTRMod.ceramicMug), new ItemStack(Items.glass_bottle), new ItemStack(Items.arrow), new ItemStack(LOTRMod.shireHeather), new ItemStack(LOTRMod.bluebell), new ItemStack(Blocks.yellow_flower, 1, 0), new ItemStack(Blocks.red_flower, 1, 0), new ItemStack(Blocks.red_flower, 1, 3)};
		return items[random.nextInt(items.length)].copy();
	}

	public void placePlateOrMug(World world, Random random, int i, int j, int k) {
		if (random.nextBoolean()) {
			placeMug(world, random, i, j, k, random.nextInt(4), LOTRFoods.HOBBIT_DRINK);
		} else {
			placePlate(world, random, i, j, k, plateBlock, LOTRFoods.HOBBIT);
		}
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		tavernName = LOTRNames.getHobbitTavernName(random);
		tavernNameSign = new String[]{"", tavernName[0], tavernName[1], ""};
		tavernNameNPC = tavernName[0] + " " + tavernName[1];
	}
}
