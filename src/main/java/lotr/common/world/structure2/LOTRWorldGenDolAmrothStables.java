package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityDolAmrothCaptain;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenDolAmrothStables extends LOTRWorldGenStructureBase2 {
	public Block brickBlock = LOTRMod.brick3;
	public int brickMeta = 9;
	public Block slabBlock = LOTRMod.slabSingle6;
	public int slabMeta = 7;
	public Block stairBlock = LOTRMod.stairsDolAmrothBrick;
	public Block wallBlock = LOTRMod.wall2;
	public int wallMeta = 14;
	public Block rockSlabBlock = LOTRMod.slabSingle;
	public Block doubleRockSlabBlock = LOTRMod.slabDouble;
	public int rockSlabMeta = 2;
	public Block pillarBlock = LOTRMod.pillar;
	public int pillarMeta = 6;
	public Block logBlock = Blocks.log;
	public int logMeta;
	public Block plankBlock = Blocks.planks;
	public int plankMeta;
	public Block plankSlabBlock = Blocks.wooden_slab;
	public int plankSlabMeta;
	public Block fenceBlock = Blocks.fence;
	public int fenceMeta;
	public Block gateBlock;
	public Block woodBeamBlock = LOTRMod.woodBeamV1;
	public int woodBeamMeta;
	public Block roofBlock;
	public int roofMeta = 11;
	public Block roofSlabBlock;
	public int roofSlabMeta = 3;
	public Block roofStairBlock;
	public Block leafBlock;
	public int leafMeta = 6;

	public LOTRWorldGenDolAmrothStables(boolean flag) {
		super(flag);
		gateBlock = Blocks.fence_gate;
		roofBlock = LOTRMod.clayTileDyed;
		roofSlabBlock = LOTRMod.slabClayTileDyedSingle2;
		roofStairBlock = LOTRMod.stairsClayTileDyedBlue;
		leafBlock = LOTRMod.leaves4;
	}

	public void generateWindow(World world, int i, int j, int k) {
		setBlockAndMetadata(world, i - 1, j, k, stairBlock, 0);
		setBlockAndMetadata(world, i, j, k, slabBlock, slabMeta);
		setBlockAndMetadata(world, i + 1, j, k, stairBlock, 1);
		for (int i1 = i - 1; i1 <= i + 1; ++i1) {
			setAir(world, i1, j + 1, k);
			setAir(world, i1, j + 2, k);
		}
		setBlockAndMetadata(world, i - 1, j + 3, k, stairBlock, 4);
		setBlockAndMetadata(world, i, j + 3, k, slabBlock, slabMeta | 8);
		setBlockAndMetadata(world, i + 1, j + 3, k, stairBlock, 5);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int i1;
		int i12;
		int j1;
		int i13;
		int k1;
		int i142;
		int i15;
		int k122;
		int j12;
		int k13;
		int j13;
		int i16;
		int i17;
		int j14;
		int i22;
		int j15;
		int i18;
		int k142;
		int k15;
		int k16;
		int k17;
		setOriginAndRotation(world, i, j, k, rotation, 2);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (i15 = -9; i15 <= 9; ++i15) {
				for (k142 = -1; k142 <= 19; ++k142) {
					j12 = getTopBlock(world, i15, k142);
					Block block = getBlock(world, i15, j12 - 1, k142);
					if (block != Blocks.grass) {
						return false;
					}
					if (j12 < minHeight) {
						minHeight = j12;
					}
					if (j12 > maxHeight) {
						maxHeight = j12;
					}
					if (maxHeight - minHeight <= 7) {
						continue;
					}
					return false;
				}
			}
		}
		for (int segment = 0; segment <= 2; ++segment) {
			int i192;
			int i110;
			int k18;
			int kz = segment * 4;
			for (i15 = -8; i15 <= 8; ++i15) {
				for (k142 = kz; k142 <= kz + 3; ++k142) {
					for (j12 = 0; (j12 == 0 || !isOpaque(world, i15, j12, k142)) && getY(j12) >= 0; --j12) {
						setBlockAndMetadata(world, i15, j12, k142, brickBlock, brickMeta);
						setGrassToDirt(world, i15, j12 - 1, k142);
					}
					for (j12 = 1; j12 <= 7; ++j12) {
						setAir(world, i15, j12, k142);
					}
				}
			}
			placeWoodPillar(world, -8, kz);
			placeWoodPillar(world, 8, kz);
			int[] i111 = {-3, 3};
			k142 = i111.length;
			for (j12 = 0; j12 < k142; ++j12) {
				i192 = i111[j12];
				if (segment == 0) {
					placeWoodPillar(world, i192, kz);
					continue;
				}
				for (j14 = 1; j14 <= 3; ++j14) {
					setBlockAndMetadata(world, i192, j14, kz, logBlock, logMeta);
				}
			}
			for (i110 = -7; i110 <= -4; ++i110) {
				setBlockAndMetadata(world, i110, 1, kz, brickBlock, brickMeta);
				setBlockAndMetadata(world, i110, 2, kz, wallBlock, wallMeta);
				setBlockAndMetadata(world, i110, 4, kz, brickBlock, brickMeta);
			}
			for (i110 = 4; i110 <= 7; ++i110) {
				setBlockAndMetadata(world, i110, 1, kz, brickBlock, brickMeta);
				setBlockAndMetadata(world, i110, 2, kz, wallBlock, wallMeta);
				setBlockAndMetadata(world, i110, 4, kz, brickBlock, brickMeta);
			}
			setBlockAndMetadata(world, -4, 2, kz, brickBlock, brickMeta);
			setBlockAndMetadata(world, -4, 3, kz, wallBlock, wallMeta);
			setBlockAndMetadata(world, 4, 2, kz, brickBlock, brickMeta);
			setBlockAndMetadata(world, 4, 3, kz, wallBlock, wallMeta);
			for (k18 = kz + 1; k18 <= kz + 3; ++k18) {
				setBlockAndMetadata(world, -8, 1, k18, doubleRockSlabBlock, rockSlabMeta);
				setBlockAndMetadata(world, -8, 2, k18, brickBlock, brickMeta);
				setBlockAndMetadata(world, -8, 3, k18, rockSlabBlock, rockSlabMeta);
				setBlockAndMetadata(world, -8, 4, k18, brickBlock, brickMeta);
				setBlockAndMetadata(world, 8, 1, k18, doubleRockSlabBlock, rockSlabMeta);
				setBlockAndMetadata(world, 8, 2, k18, brickBlock, brickMeta);
				setBlockAndMetadata(world, 8, 3, k18, rockSlabBlock, rockSlabMeta);
				setBlockAndMetadata(world, 8, 4, k18, brickBlock, brickMeta);
			}
			for (int j16 = 1; j16 <= 3; ++j16) {
				setBlockAndMetadata(world, -2, j16, kz, fenceBlock, brickMeta);
				setBlockAndMetadata(world, 2, j16, kz, fenceBlock, brickMeta);
			}
			for (k18 = kz + 1; k18 <= kz + 3; ++k18) {
				for (i142 = -7; i142 <= -3; ++i142) {
					setBlockAndMetadata(world, i142, 0, k18, doubleRockSlabBlock, rockSlabMeta);
					if (random.nextInt(3) == 0) {
						continue;
					}
					setBlockAndMetadata(world, i142, 1, k18, LOTRMod.thatchFloor, 0);
				}
				for (i142 = 3; i142 <= 7; ++i142) {
					setBlockAndMetadata(world, i142, 0, k18, doubleRockSlabBlock, rockSlabMeta);
					if (random.nextInt(3) == 0) {
						continue;
					}
					setBlockAndMetadata(world, i142, 1, k18, LOTRMod.thatchFloor, 0);
				}
			}
			for (int i1921 : new int[]{-7, 7}) {
				setBlockAndMetadata(world, i1921, 1, kz + 1, Blocks.hay_block, 0);
				setBlockAndMetadata(world, i1921, 1, kz + 2, Blocks.hay_block, 0);
				setBlockAndMetadata(world, i1921, 1, kz + 3, fenceBlock, fenceMeta);
			}
			for (int i1921 : new int[]{-3, 3}) {
				setBlockAndMetadata(world, i1921, 1, kz + 1, gateBlock, 1);
				setBlockAndMetadata(world, i1921, 1, kz + 2, gateBlock, 1);
				setBlockAndMetadata(world, i1921, 1, kz + 3, fenceBlock, fenceMeta);
				setBlockAndMetadata(world, i1921, 2, kz + 3, Blocks.torch, 5);
			}
			int[] k19 = {-1, 1};
			i142 = k19.length;
			for (j12 = 0; j12 < i142; ++j12) {
				int f = k19[j12];
				LOTREntityHorse horse = new LOTREntityHorse(world);
				spawnNPCAndSetHome(horse, world, 5 * f, 1, kz + 2, 0);
				horse.setHorseType(0);
				horse.saddleMountForWorldGen();
				horse.detachHome();
			}
			for (int i112 = -7; i112 <= 7; ++i112) {
				for (k142 = kz + 1; k142 <= kz + 3; ++k142) {
					setBlockAndMetadata(world, i112, 4, k142, rockSlabBlock, rockSlabMeta | 8);
				}
				if (segment <= 0) {
					continue;
				}
				if (Math.abs(i112) <= 1) {
					setBlockAndMetadata(world, i112, 4, kz, rockSlabBlock, rockSlabMeta | 8);
					continue;
				}
				setBlockAndMetadata(world, i112, 4, kz, brickBlock, brickMeta);
			}
			for (int i1921 : new int[]{-3, 3}) {
				setBlockAndMetadata(world, i1921, 4, kz + 1, doubleRockSlabBlock, rockSlabMeta);
				setBlockAndMetadata(world, i1921, 4, kz + 3, doubleRockSlabBlock, rockSlabMeta);
			}
			for (int i113 = -8; i113 <= 8; ++i113) {
				setBlockAndMetadata(world, i113, 5, kz, brickBlock, brickMeta);
			}
			for (int i1921 : new int[]{-7, 4}) {
				setBlockAndMetadata(world, i1921, 6, kz, stairBlock, 1);
				setBlockAndMetadata(world, i1921 + 1, 6, kz, brickBlock, brickMeta);
				setBlockAndMetadata(world, i1921 + 1, 7, kz, stairBlock, 1);
				setBlockAndMetadata(world, i1921 + 2, 6, kz, brickBlock, brickMeta);
				setBlockAndMetadata(world, i1921 + 2, 7, kz, stairBlock, 0);
				setBlockAndMetadata(world, i1921 + 3, 6, kz, stairBlock, 0);
				for (k16 = kz + 1; k16 <= kz + 3; ++k16) {
					setBlockAndMetadata(world, i1921, 5, k16, roofStairBlock, 1);
					setBlockAndMetadata(world, i1921 + 1, 5, k16, brickBlock, brickMeta);
					setBlockAndMetadata(world, i1921 + 1, 6, k16, roofStairBlock, 1);
					setBlockAndMetadata(world, i1921 + 2, 5, k16, brickBlock, brickMeta);
					setBlockAndMetadata(world, i1921 + 2, 6, k16, roofStairBlock, 0);
					setBlockAndMetadata(world, i1921 + 3, 5, k16, roofStairBlock, 0);
				}
			}
			setBlockAndMetadata(world, -2, 6, kz, stairBlock, 1);
			setBlockAndMetadata(world, -1, 6, kz, brickBlock, brickMeta);
			setBlockAndMetadata(world, -1, 7, kz, stairBlock, 1);
			setBlockAndMetadata(world, 0, 6, kz, brickBlock, brickMeta);
			setBlockAndMetadata(world, 0, 7, kz, brickBlock, brickMeta);
			setBlockAndMetadata(world, 0, 8, kz, slabBlock, slabMeta);
			setBlockAndMetadata(world, 1, 6, kz, brickBlock, brickMeta);
			setBlockAndMetadata(world, 1, 7, kz, stairBlock, 0);
			setBlockAndMetadata(world, 2, 6, kz, stairBlock, 0);
			for (int k110 = kz + 1; k110 <= kz + 3; ++k110) {
				setBlockAndMetadata(world, -2, 5, k110, roofStairBlock, 1);
				setBlockAndMetadata(world, -1, 5, k110, brickBlock, brickMeta);
				setBlockAndMetadata(world, -1, 6, k110, roofStairBlock, 1);
				setBlockAndMetadata(world, 0, 5, k110, brickBlock, brickMeta);
				setBlockAndMetadata(world, 0, 6, k110, roofBlock, roofMeta);
				setBlockAndMetadata(world, 0, 7, k110, roofSlabBlock, roofSlabMeta);
				setBlockAndMetadata(world, 1, 5, k110, brickBlock, brickMeta);
				setBlockAndMetadata(world, 1, 6, k110, roofStairBlock, 0);
				setBlockAndMetadata(world, 2, 5, k110, roofStairBlock, 0);
			}
			int[] k110 = {-8, -3, 3, 8};
			k142 = k110.length;
			for (j12 = 0; j12 < k142; ++j12) {
				i192 = k110[j12];
				for (k16 = kz + 1; k16 <= kz + 3; ++k16) {
					setBlockAndMetadata(world, i192, 5, k16, wallBlock, wallMeta);
				}
			}
		}
		for (i17 = -9; i17 <= 9; ++i17) {
			i22 = Math.abs(i17);
			if (i22 <= 2) {
				setBlockAndMetadata(world, i17, 0, -1, doubleRockSlabBlock, rockSlabMeta);
				setBlockAndMetadata(world, i17, 0, -2, doubleRockSlabBlock, rockSlabMeta);
				continue;
			}
			placeGrassFoundation(world, i17, -1);
			if (i22 == 3 || i22 == 8) {
				for (j13 = 1; j13 <= 4; ++j13) {
					setBlockAndMetadata(world, i17, j13, -1, wallBlock, wallMeta);
				}
				continue;
			}
			setBlockAndMetadata(world, i17, 1, -1, leafBlock, leafMeta);
		}
		for (int k111 = 0; k111 <= 11; ++k111) {
			int[] i221 = {-9, 9};
			j13 = i221.length;
			for (k142 = 0; k142 < j13; ++k142) {
				i13 = i221[k142];
				placeGrassFoundation(world, i13, k111);
				if (k111 % 4 == 0) {
					for (j15 = 1; j15 <= 4; ++j15) {
						setBlockAndMetadata(world, i13, j15, k111, wallBlock, wallMeta);
					}
					continue;
				}
				setBlockAndMetadata(world, i13, 1, k111, leafBlock, leafMeta);
			}
			if (k111 % 4 != 0) {
				continue;
			}
			setBlockAndMetadata(world, -9, 5, k111, stairBlock, 1);
			setBlockAndMetadata(world, 9, 5, k111, stairBlock, 0);
		}
		setBlockAndMetadata(world, -1, 5, 0, stairBlock, 4);
		setBlockAndMetadata(world, 0, 5, 0, stairBlock, 6);
		setBlockAndMetadata(world, 1, 5, 0, stairBlock, 5);
		setBlockAndMetadata(world, 0, 6, 0, LOTRMod.brick, 5);
		for (i17 = -1; i17 <= 1; ++i17) {
			setBlockAndMetadata(world, i17, 0, 0, doubleRockSlabBlock, rockSlabMeta);
			for (j1 = 1; j1 <= 4; ++j1) {
				setBlockAndMetadata(world, i17, j1, 0, LOTRMod.gateDolAmroth, 2);
			}
		}
		for (int i1421 : new int[]{-2, 2}) {
			for (j12 = 1; j12 <= 4; ++j12) {
				setBlockAndMetadata(world, i1421, j12, 0, brickBlock, brickMeta);
			}
			placeWallBanner(world, i1421, 4, 0, LOTRItemBanner.BannerType.DOL_AMROTH, 2);
		}
		setBlockAndMetadata(world, -8, 5, -1, stairBlock, 1);
		setBlockAndMetadata(world, 8, 5, -1, stairBlock, 0);
		int[] i114 = {-7, 4};
		j1 = i114.length;
		for (j13 = 0; j13 < j1; ++j13) {
			i142 = i114[j13];
			setBlockAndMetadata(world, i142, 5, -1, stairBlock, 4);
			setBlockAndMetadata(world, i142, 6, -1, stairBlock, 1);
			setBlockAndMetadata(world, i142 + 1, 6, -1, stairBlock, 4);
			setBlockAndMetadata(world, i142 + 1, 7, -1, stairBlock, 1);
			setBlockAndMetadata(world, i142 + 2, 6, -1, stairBlock, 5);
			setBlockAndMetadata(world, i142 + 2, 7, -1, stairBlock, 0);
			setBlockAndMetadata(world, i142 + 3, 5, -1, stairBlock, 5);
			setBlockAndMetadata(world, i142 + 3, 6, -1, stairBlock, 0);
			setBlockAndMetadata(world, i142 + 1, 5, 0, stairBlock, 4);
			setBlockAndMetadata(world, i142 + 2, 5, 0, stairBlock, 5);
		}
		setBlockAndMetadata(world, -3, 5, -1, brickBlock, brickMeta);
		setBlockAndMetadata(world, -2, 5, -1, stairBlock, 4);
		setBlockAndMetadata(world, -2, 6, -1, stairBlock, 1);
		setBlockAndMetadata(world, -1, 6, -1, stairBlock, 4);
		setBlockAndMetadata(world, -1, 7, -1, stairBlock, 1);
		setBlockAndMetadata(world, 0, 7, -1, brickBlock, brickMeta);
		setBlockAndMetadata(world, 0, 8, -1, slabBlock, slabMeta);
		setBlockAndMetadata(world, 1, 6, -1, stairBlock, 5);
		setBlockAndMetadata(world, 1, 7, -1, stairBlock, 0);
		setBlockAndMetadata(world, 2, 5, -1, stairBlock, 5);
		setBlockAndMetadata(world, 2, 6, -1, stairBlock, 0);
		setBlockAndMetadata(world, 3, 5, -1, brickBlock, brickMeta);
		for (k15 = 1; k15 <= 3; ++k15) {
			for (i18 = -1; i18 <= 1; ++i18) {
				if (k15 == 3 && Math.abs(i18) >= 1) {
					continue;
				}
				setAir(world, i18, 4, k15);
			}
		}
		for (k15 = 1; k15 <= 11; ++k15) {
			setBlockAndMetadata(world, 0, 0, k15, doubleRockSlabBlock, rockSlabMeta);
		}
		for (int i115 = -9; i115 <= 9; ++i115) {
			for (k122 = 12; k122 <= 18; ++k122) {
				for (j13 = 0; (j13 == 0 || !isOpaque(world, i115, j13, k122)) && getY(j13) >= 0; --j13) {
					setBlockAndMetadata(world, i115, j13, k122, brickBlock, brickMeta);
					setGrassToDirt(world, i115, j13 - 1, k122);
				}
				for (j13 = 1; j13 <= 9; ++j13) {
					setAir(world, i115, j13, k122);
				}
			}
		}
		int[] i115 = {12, 18};
		k122 = i115.length;
		for (j13 = 0; j13 < k122; ++j13) {
			k142 = i115[j13];
			for (i13 = -9; i13 <= 9; ++i13) {
				setBlockAndMetadata(world, i13, 1, k142, doubleRockSlabBlock, rockSlabMeta);
				for (j15 = 2; j15 <= 6; ++j15) {
					setBlockAndMetadata(world, i13, j15, k142, brickBlock, brickMeta);
				}
			}
			int[] i116 = {-9, 9};
			j15 = i116.length;
			for (k16 = 0; k16 < j15; ++k16) {
				int i117 = i116[k16];
				placeWoodPillar(world, i117, k142);
				setBlockAndMetadata(world, i117, 4, k142, doubleRockSlabBlock, rockSlabMeta);
				for (int j17 = 5; j17 <= 7; ++j17) {
					setBlockAndMetadata(world, i117, j17, k142, pillarBlock, pillarMeta);
				}
				setBlockAndMetadata(world, i117, 8, k142, rockSlabBlock, rockSlabMeta);
			}
			for (i13 = -8; i13 <= 8; ++i13) {
				int i23 = Math.abs(i13);
				if (i23 >= 5) {
					if (i23 % 2 == 0) {
						setBlockAndMetadata(world, i13, 7, k142, slabBlock, slabMeta);
					} else {
						setBlockAndMetadata(world, i13, 7, k142, brickBlock, brickMeta);
					}
				}
				if (i23 == 4) {
					for (j14 = 5; j14 <= 10; ++j14) {
						setBlockAndMetadata(world, i13, j14, k142, pillarBlock, pillarMeta);
					}
					setBlockAndMetadata(world, i13, 11, k142, slabBlock, slabMeta);
				}
				if (i23 > 3) {
					continue;
				}
				for (j14 = 7; j14 <= 8; ++j14) {
					setBlockAndMetadata(world, i13, j14, k142, brickBlock, brickMeta);
				}
				setBlockAndMetadata(world, i13, 9, k142, doubleRockSlabBlock, rockSlabMeta);
				if (i23 >= 1) {
					setBlockAndMetadata(world, i13, 10, k142, brickBlock, brickMeta);
					if (i23 % 2 == 0) {
						setBlockAndMetadata(world, i13, 11, k142, slabBlock, slabMeta);
					}
				}
				if (i23 != 0) {
					continue;
				}
				setBlockAndMetadata(world, i13, 10, k142, slabBlock, slabMeta);
			}
		}
		for (k13 = 13; k13 <= 17; ++k13) {
			int[] k1221 = {-4, 4};
			j13 = k1221.length;
			for (k142 = 0; k142 < j13; ++k142) {
				i13 = k1221[k142];
				setBlockAndMetadata(world, i13, 9, k13, doubleRockSlabBlock, rockSlabMeta);
				setBlockAndMetadata(world, i13, 10, k13, brickBlock, brickMeta);
				if (k13 % 2 != 0) {
					continue;
				}
				setBlockAndMetadata(world, i13, 11, k13, slabBlock, slabMeta);
			}
		}
		for (int i118 = -3; i118 <= 3; ++i118) {
			i22 = Math.abs(i118);
			for (k1 = 13; k1 <= 17; ++k1) {
				setBlockAndMetadata(world, i118, 9, k1, doubleRockSlabBlock, rockSlabMeta);
			}
			for (k1 = 14; k1 <= 16; ++k1) {
				setBlockAndMetadata(world, i118, 10, k1, doubleRockSlabBlock, rockSlabMeta);
				if (i22 <= 2) {
					setBlockAndMetadata(world, i118, 11, k1, brickBlock, brickMeta);
				}
				if (i22 > 1) {
					continue;
				}
				setBlockAndMetadata(world, i118, 12, k1, brickBlock, brickMeta);
			}
		}
		for (k13 = 13; k13 <= 17; ++k13) {
			setBlockAndMetadata(world, -3, 11, k13, roofStairBlock, 1);
			setBlockAndMetadata(world, -2, 12, k13, roofStairBlock, 1);
			setBlockAndMetadata(world, -1, 13, k13, roofStairBlock, 1);
			setBlockAndMetadata(world, 0, 13, k13, roofBlock, roofMeta);
			setBlockAndMetadata(world, 0, 14, k13, roofSlabBlock, roofSlabMeta);
			setBlockAndMetadata(world, 1, 13, k13, roofStairBlock, 0);
			setBlockAndMetadata(world, 2, 12, k13, roofStairBlock, 0);
			setBlockAndMetadata(world, 3, 11, k13, roofStairBlock, 0);
		}
		int[] k112 = {13, 17};
		i22 = k112.length;
		for (k1 = 0; k1 < i22; ++k1) {
			k142 = k112[k1];
			setBlockAndMetadata(world, -3, 10, k142, roofStairBlock, 4);
			setBlockAndMetadata(world, -2, 11, k142, roofStairBlock, 4);
			setBlockAndMetadata(world, -1, 12, k142, roofStairBlock, 4);
			setBlockAndMetadata(world, 1, 12, k142, roofStairBlock, 5);
			setBlockAndMetadata(world, 2, 11, k142, roofStairBlock, 5);
			setBlockAndMetadata(world, 3, 10, k142, roofStairBlock, 5);
		}
		placeWallBanner(world, 0, 12, 14, LOTRItemBanner.BannerType.DOL_AMROTH, 2);
		placeWallBanner(world, 0, 12, 16, LOTRItemBanner.BannerType.DOL_AMROTH, 0);
		placeWallBanner(world, -4, 9, 12, LOTRItemBanner.BannerType.DOL_AMROTH, 2);
		setBlockAndMetadata(world, -3, 9, 12, doubleRockSlabBlock, rockSlabMeta);
		setBlockAndMetadata(world, -2, 9, 12, stairBlock, 6);
		setBlockAndMetadata(world, -2, 8, 12, LOTRMod.stainedGlassPane, 11);
		setBlockAndMetadata(world, -1, 9, 12, doubleRockSlabBlock, rockSlabMeta);
		setBlockAndMetadata(world, 0, 9, 12, stairBlock, 6);
		setBlockAndMetadata(world, 0, 8, 12, LOTRMod.stainedGlassPane, 0);
		setBlockAndMetadata(world, 1, 9, 12, doubleRockSlabBlock, rockSlabMeta);
		setBlockAndMetadata(world, 2, 9, 12, stairBlock, 6);
		setBlockAndMetadata(world, 2, 8, 12, LOTRMod.stainedGlassPane, 11);
		setBlockAndMetadata(world, 3, 9, 12, doubleRockSlabBlock, rockSlabMeta);
		placeWallBanner(world, 4, 9, 12, LOTRItemBanner.BannerType.DOL_AMROTH, 2);
		placeWoodPillar(world, -3, 12);
		placeWoodPillar(world, 3, 12);
		placeWoodPillar(world, -6, 18);
		placeWoodPillar(world, -2, 18);
		placeWoodPillar(world, 2, 18);
		placeWoodPillar(world, 6, 18);
		for (k17 = 13; k17 <= 17; ++k17) {
			int[] i24 = {-9, 9};
			k1 = i24.length;
			for (k142 = 0; k142 < k1; ++k142) {
				i13 = i24[k142];
				setBlockAndMetadata(world, i13, 1, k17, doubleRockSlabBlock, rockSlabMeta);
				for (j15 = 2; j15 <= 3; ++j15) {
					setBlockAndMetadata(world, i13, j15, k17, brickBlock, brickMeta);
				}
				setBlockAndMetadata(world, i13, 4, k17, doubleRockSlabBlock, rockSlabMeta);
				for (j15 = 5; j15 <= 6; ++j15) {
					setBlockAndMetadata(world, i13, j15, k17, brickBlock, brickMeta);
				}
				if (k17 % 2 == 1) {
					setBlockAndMetadata(world, i13, 7, k17, slabBlock, slabMeta);
					continue;
				}
				setBlockAndMetadata(world, i13, 7, k17, brickBlock, brickMeta);
			}
		}
		for (k17 = 13; k17 <= 17; ++k17) {
			for (int step = 0; step <= 4; ++step) {
				setBlockAndMetadata(world, -8 + step, 4 + step, k17, stairBlock, 4);
				setBlockAndMetadata(world, 8 - step, 4 + step, k17, stairBlock, 5);
			}
			setBlockAndMetadata(world, -8, 5, k17, brickBlock, brickMeta);
			setBlockAndMetadata(world, -8, 6, k17, Blocks.grass, 0);
			setBlockAndMetadata(world, -7, 6, k17, Blocks.grass, 0);
			setBlockAndMetadata(world, 8, 5, k17, brickBlock, brickMeta);
			setBlockAndMetadata(world, 8, 6, k17, Blocks.grass, 0);
			setBlockAndMetadata(world, 7, 6, k17, Blocks.grass, 0);
		}
		for (i12 = -8; i12 <= 8; ++i12) {
			i22 = Math.abs(i12);
			switch (i22) {
				case 8:
					for (k1 = 13; k1 <= 17; ++k1) {
						setBlockAndMetadata(world, i12, 7, k1, leafBlock, leafMeta);
					}
					for (k1 = 14; k1 <= 16; ++k1) {
						setBlockAndMetadata(world, i12, 8, k1, leafBlock, leafMeta);
					}
					continue;
				case 7:
					for (k1 = 13; k1 <= 17; ++k1) {
						setBlockAndMetadata(world, i12, 7, k1, leafBlock, leafMeta);
					}
					setBlockAndMetadata(world, i12, 8, 13, leafBlock, leafMeta);
					setBlockAndMetadata(world, i12, 8, 17, leafBlock, leafMeta);
					setBlockAndMetadata(world, i12, 7, 15, rockSlabBlock, rockSlabMeta);
					setBlockAndMetadata(world, i12, 6, 15, brickBlock, brickMeta);
					continue;
				case 6:
					setBlockAndMetadata(world, i12, 7, 13, leafBlock, leafMeta);
					setBlockAndMetadata(world, i12, 7, 17, leafBlock, leafMeta);
					setBlockAndMetadata(world, i12, 8, 13, leafBlock, leafMeta);
					setBlockAndMetadata(world, i12, 8, 17, leafBlock, leafMeta);
					setBlockAndMetadata(world, i12, 7, 14, rockSlabBlock, rockSlabMeta);
					setBlockAndMetadata(world, i12, 7, 15, rockSlabBlock, rockSlabMeta);
					setBlockAndMetadata(world, i12, 7, 16, rockSlabBlock, rockSlabMeta);
					continue;
				default:
					break;
			}
			if (i22 != 5) {
				continue;
			}
			setBlockAndMetadata(world, i12, 8, 13, leafBlock, leafMeta);
			setBlockAndMetadata(world, i12, 8, 14, leafBlock, leafMeta);
			setBlockAndMetadata(world, i12, 8, 16, leafBlock, leafMeta);
			setBlockAndMetadata(world, i12, 8, 17, leafBlock, leafMeta);
		}
		for (i12 = -8; i12 <= 8; ++i12) {
			for (k122 = 13; k122 <= 17; ++k122) {
				setBlockAndMetadata(world, i12, 0, k122, doubleRockSlabBlock, rockSlabMeta);
			}
		}
		for (i12 = -2; i12 <= 2; ++i12) {
			setBlockAndMetadata(world, i12, 0, 12, doubleRockSlabBlock, rockSlabMeta);
			for (j1 = 1; j1 <= 3; ++j1) {
				setAir(world, i12, j1, 12);
			}
		}
		for (int j18 = 1; j18 <= 3; ++j18) {
			int[] j19 = {-2, 2};
			k1 = j19.length;
			for (k142 = 0; k142 < k1; ++k142) {
				i13 = j19[k142];
				setBlockAndMetadata(world, i13, j18, 12, brickBlock, brickMeta);
			}
			for (int i119 = -1; i119 <= 1; ++i119) {
				setBlockAndMetadata(world, i119, j18, 12, LOTRMod.gateDolAmroth, 2);
			}
		}
		for (int f : new int[]{-1, 1}) {
			setBlockAndMetadata(world, 4 * f, 4, 13, slabBlock, slabMeta | 8);
			setBlockAndMetadata(world, 3 * f, 5, 13, slabBlock, slabMeta);
			setBlockAndMetadata(world, 2 * f, 5, 13, slabBlock, slabMeta | 8);
			setBlockAndMetadata(world, f, 6, 13, slabBlock, slabMeta);
			setBlockAndMetadata(world, 0, 6, 13, slabBlock, slabMeta);
			placeWallBanner(world, 3 * f, 4, 12, LOTRItemBanner.BannerType.DOL_AMROTH, 0);
			setBlockAndMetadata(world, 6 * f, 3, 13, Blocks.torch, 3);
			for (j12 = 5; j12 <= 6; ++j12) {
				setBlockAndMetadata(world, 6 * f, j12, 18, woodBeamBlock, woodBeamMeta);
			}
			for (j12 = 5; j12 <= 7; ++j12) {
				setBlockAndMetadata(world, 2 * f, j12, 18, woodBeamBlock, woodBeamMeta);
			}
			placeWallBanner(world, 6 * f, 5, 18, LOTRItemBanner.BannerType.DOL_AMROTH, 0);
			placeWallBanner(world, 6 * f, 5, 18, LOTRItemBanner.BannerType.DOL_AMROTH, 2);
			placeWallBanner(world, 2 * f, 6, 18, LOTRItemBanner.BannerType.DOL_AMROTH, 0);
			placeWallBanner(world, 2 * f, 6, 18, LOTRItemBanner.BannerType.DOL_AMROTH, 2);
			setBlockAndMetadata(world, 6 * f, 3, 17, Blocks.torch, 4);
			for (int k113 : new int[]{13, 17}) {
				setBlockAndMetadata(world, 4 * f, 1, k113, plankBlock, plankMeta);
				setBlockAndMetadata(world, 5 * f, 1, k113, plankSlabBlock, plankSlabMeta | 8);
				setBlockAndMetadata(world, 7 * f, 1, k113, plankSlabBlock, plankSlabMeta | 8);
				placeChest(world, random, 6 * f, 1, k113, 0, LOTRChestContents.DOL_AMROTH_STABLES);
			}
		}
		setBlockAndMetadata(world, -8, 1, 13, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, -8, 1, 17, Blocks.furnace, 2);
		setBlockAndMetadata(world, 8, 1, 13, LOTRMod.gondorianTable, 0);
		setBlockAndMetadata(world, 8, 1, 17, LOTRMod.dolAmrothTable, 0);
		for (int i1421 : new int[]{-9, 9}) {
			for (int k114 = 14; k114 <= 16; ++k114) {
				setBlockAndMetadata(world, i1421, 1, k114, doubleRockSlabBlock, rockSlabMeta);
				setAir(world, i1421, 2, k114);
			}
			setBlockAndMetadata(world, i1421, 3, 14, stairBlock, 7);
			setBlockAndMetadata(world, i1421, 3, 15, slabBlock, slabMeta | 8);
			setBlockAndMetadata(world, i1421, 3, 16, stairBlock, 6);
		}
		int[] j18 = {-8, 7};
		i18 = j18.length;
		for (k1 = 0; k1 < i18; ++k1) {
			for (int i25 = i142 = j18[k1]; i25 <= i142 + 1; ++i25) {
				setBlockAndMetadata(world, i25, 1, 18, doubleRockSlabBlock, rockSlabMeta);
				setAir(world, i25, 2, 18);
			}
			setBlockAndMetadata(world, i142, 3, 18, stairBlock, 4);
			setBlockAndMetadata(world, i142 + 1, 3, 18, stairBlock, 5);
		}
		for (i16 = -8; i16 <= 8; ++i16) {
			setBlockAndMetadata(world, i16, 1, 15, Blocks.carpet, 11);
		}
		for (i16 = -2; i16 <= 2; ++i16) {
			setBlockAndMetadata(world, i16, 1, 14, Blocks.carpet, 11);
			setBlockAndMetadata(world, i16, 1, 16, Blocks.carpet, 11);
		}
		generateWindow(world, -4, 3, 18);
		generateWindow(world, 4, 3, 18);
		for (int k1421 : new int[]{14, 16}) {
			setBlockAndMetadata(world, -1, 9, k1421, Blocks.stained_hardened_clay, 3);
			setBlockAndMetadata(world, 0, 9, k1421, Blocks.stained_hardened_clay, 11);
			setBlockAndMetadata(world, 1, 9, k1421, Blocks.stained_hardened_clay, 3);
		}
		setBlockAndMetadata(world, -2, 9, 15, Blocks.stained_hardened_clay, 3);
		setBlockAndMetadata(world, -1, 9, 15, Blocks.stained_hardened_clay, 11);
		setBlockAndMetadata(world, 0, 9, 15, Blocks.stained_hardened_clay, 11);
		setBlockAndMetadata(world, 1, 9, 15, Blocks.stained_hardened_clay, 11);
		setBlockAndMetadata(world, 2, 9, 15, Blocks.stained_hardened_clay, 3);
		setBlockAndMetadata(world, 0, 8, 15, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 0, 7, 15, LOTRMod.chandelier, 2);
		for (i1 = -1; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 3, 18, doubleRockSlabBlock, rockSlabMeta);
			setBlockAndMetadata(world, i1, 7, 18, doubleRockSlabBlock, rockSlabMeta);
			for (j1 = 4; j1 <= 6; ++j1) {
				if (IntMath.mod(i1 + j1, 2) == 0) {
					setBlockAndMetadata(world, i1, j1, 18, LOTRMod.stainedGlassPane, 0);
					continue;
				}
				setBlockAndMetadata(world, i1, j1, 18, LOTRMod.stainedGlassPane, 11);
			}
		}
		setBlockAndMetadata(world, 0, 8, 18, doubleRockSlabBlock, rockSlabMeta);
		for (i1 = -6; i1 <= 6; ++i1) {
			i22 = Math.abs(i1);
			placeGrassFoundation(world, i1, 19);
			if (i22 % 4 == 2) {
				setBlockAndMetadata(world, i1, 1, 19, stairBlock, 3);
				setGrassToDirt(world, i1, 0, 19);
			} else {
				setBlockAndMetadata(world, i1, 1, 19, leafBlock, leafMeta);
			}
			if (i22 >= 6) {
				setBlockAndMetadata(world, i1, 6, 19, slabBlock, slabMeta | 8);
				continue;
			}
			if (i22 >= 3) {
				setBlockAndMetadata(world, i1, 7, 19, slabBlock, slabMeta);
				continue;
			}
			if (i22 == 2) {
				setBlockAndMetadata(world, i1, 7, 19, slabBlock, slabMeta | 8);
				continue;
			}
			setBlockAndMetadata(world, i1, 8, 19, slabBlock, slabMeta);
		}
		LOTREntityDolAmrothCaptain captain = new LOTREntityDolAmrothCaptain(world);
		captain.spawnRidingHorse = false;
		spawnNPCAndSetHome(captain, world, 0, 1, 15, 8);
		return true;
	}

	public void placeGrassFoundation(World world, int i, int k) {
		for (int j1 = 6; (j1 >= 0 || !isOpaque(world, i, j1, k)) && getY(j1) >= 0; --j1) {
			if (j1 > 0) {
				setAir(world, i, j1, k);
				continue;
			}
			if (j1 == 0) {
				setBlockAndMetadata(world, i, 0, k, Blocks.grass, 0);
				setGrassToDirt(world, i, -1, k);
				continue;
			}
			setBlockAndMetadata(world, i, j1, k, Blocks.dirt, 0);
			setGrassToDirt(world, i, j1 - 1, k);
		}
	}

	public void placeWoodPillar(World world, int i, int k) {
		int j = 0;
		while ((!isOpaque(world, i, j, k) || getBlock(world, i, j, k) == brickBlock && getMeta(world, i, j, k) == brickMeta) && getY(j) >= 0) {
			setBlockAndMetadata(world, i, j, k, woodBeamBlock, woodBeamMeta);
			setGrassToDirt(world, i, j - 1, k);
			--j;
		}
		for (j = 1; j <= 4; ++j) {
			setBlockAndMetadata(world, i, j, k, woodBeamBlock, woodBeamMeta);
		}
	}
}
