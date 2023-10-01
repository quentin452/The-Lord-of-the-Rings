package lotr.common.world.structure2;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityGondorMan;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenGondorWatchfort extends LOTRWorldGenGondorStructure {
	public LOTRWorldGenGondorWatchfort(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int i1;
		int j1;
		int i12;
		int i13;
		int i14;
		int j12;
		int i15;
		int i16;
		int step;
		int k1;
		int j13;
		int i172;
		int j2;
		int j14;
		int k12;
		int k13;
		int j15;
		int k14;
		setOriginAndRotation(world, i, j, k, rotation, 9);
		setupRandomBlocks(random);
		if (restrictions) {
			int x1 = -6;
			int x2 = 6;
			int z1 = -6;
			int z2 = 34;
			for (int i18 = x1; i18 <= x2; ++i18) {
				for (k1 = z1; k1 <= z2; ++k1) {
					j1 = getTopBlock(world, i18, k1) - 1;
					if (isSurface(world, i18, j1, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i16 = -5; i16 <= 5; ++i16) {
			for (j12 = 1; j12 <= 11; ++j12) {
				for (k14 = -5; k14 <= 5; ++k14) {
					if (Math.abs(i16) == 5 && Math.abs(k14) == 5) {
						setBlockAndMetadata(world, i16, j12, k14, pillar2Block, pillar2Meta);
						continue;
					}
					placeRandomBrick(world, random, i16, j12, k14);
				}
			}
		}
		for (i16 = -6; i16 <= 6; ++i16) {
			setBlockAndMetadata(world, i16, 1, -6, brick2StairBlock, 2);
			setBlockAndMetadata(world, i16, 1, 6, brick2StairBlock, 3);
		}
		for (int k15 = -5; k15 <= 5; ++k15) {
			setBlockAndMetadata(world, -6, 1, k15, brick2StairBlock, 1);
			setBlockAndMetadata(world, 6, 1, k15, brick2StairBlock, 0);
		}
		for (i16 = -6; i16 <= 6; ++i16) {
			for (k12 = -6; k12 <= 6; ++k12) {
				j14 = 0;
				while (!isOpaque(world, i16, j14, k12) && getY(j14) >= 0) {
					placeRandomBrick(world, random, i16, j14, k12);
					setGrassToDirt(world, i16, j14 - 1, k12);
					--j14;
				}
			}
		}
		for (i16 = -4; i16 <= 4; ++i16) {
			for (k12 = -4; k12 <= 4; ++k12) {
				for (j14 = 2; j14 <= 5; ++j14) {
					setAir(world, i16, j14, k12);
				}
				for (j14 = 7; j14 <= 10; ++j14) {
					setAir(world, i16, j14, k12);
				}
			}
		}
		int[] i19 = {4, 9};
		k12 = i19.length;
		for (j14 = 0; j14 < k12; ++j14) {
			j15 = i19[j14];
			setBlockAndMetadata(world, -4, j15, -2, Blocks.torch, 2);
			setBlockAndMetadata(world, -4, j15, 2, Blocks.torch, 2);
			setBlockAndMetadata(world, 4, j15, -2, Blocks.torch, 1);
			setBlockAndMetadata(world, 4, j15, 2, Blocks.torch, 1);
			setBlockAndMetadata(world, -2, j15, -4, Blocks.torch, 3);
			setBlockAndMetadata(world, 2, j15, -4, Blocks.torch, 3);
			setBlockAndMetadata(world, -2, j15, 4, Blocks.torch, 4);
			setBlockAndMetadata(world, 2, j15, 4, Blocks.torch, 4);
		}
		for (i14 = -4; i14 <= 4; ++i14) {
			for (j12 = 12; j12 <= 16; ++j12) {
				for (k14 = -4; k14 <= 4; ++k14) {
					if (Math.abs(i14) == 4 && Math.abs(k14) == 4) {
						setBlockAndMetadata(world, i14, j12, k14, pillar2Block, pillar2Meta);
						continue;
					}
					placeRandomBrick(world, random, i14, j12, k14);
				}
			}
		}
		for (i14 = -5; i14 <= 5; ++i14) {
			setBlockAndMetadata(world, i14, 12, -5, brick2StairBlock, 2);
			setBlockAndMetadata(world, i14, 12, 5, brick2StairBlock, 3);
		}
		for (k13 = -4; k13 <= 4; ++k13) {
			setBlockAndMetadata(world, -5, 12, k13, brick2StairBlock, 1);
			setBlockAndMetadata(world, 5, 12, k13, brick2StairBlock, 0);
		}
		for (i14 = -3; i14 <= 3; ++i14) {
			for (k12 = -3; k12 <= 3; ++k12) {
				for (j14 = 12; j14 <= 15; ++j14) {
					setAir(world, i14, j14, k12);
				}
			}
		}
		setBlockAndMetadata(world, -3, 14, -2, Blocks.torch, 2);
		setBlockAndMetadata(world, -3, 14, 2, Blocks.torch, 2);
		setBlockAndMetadata(world, 3, 14, -2, Blocks.torch, 1);
		setBlockAndMetadata(world, 3, 14, 2, Blocks.torch, 1);
		setBlockAndMetadata(world, -2, 14, -3, Blocks.torch, 3);
		setBlockAndMetadata(world, 2, 14, -3, Blocks.torch, 3);
		setBlockAndMetadata(world, -2, 14, 3, Blocks.torch, 4);
		setBlockAndMetadata(world, 2, 14, 3, Blocks.torch, 4);
		for (i14 = -4; i14 <= 4; ++i14) {
			placeRandomWall(world, random, i14, 17, -4);
			placeRandomWall(world, random, i14, 17, 4);
		}
		for (k13 = -4; k13 <= 4; ++k13) {
			placeRandomWall(world, random, -4, 17, k13);
			placeRandomWall(world, random, 4, 17, k13);
		}
		int[] k16 = {-4, 4};
		k12 = k16.length;
		for (j14 = 0; j14 < k12; ++j14) {
			i172 = k16[j14];
			for (int k17 : new int[]{-4, 4}) {
				for (int j16 = 17; j16 <= 19; ++j16) {
					setBlockAndMetadata(world, i172, j16, k17, pillar2Block, pillar2Meta);
				}
				placeRandomBrick(world, random, i172, 20, k17);
			}
		}
		setBlockAndMetadata(world, -4, 19, -3, Blocks.torch, 3);
		setBlockAndMetadata(world, 4, 19, -3, Blocks.torch, 3);
		setBlockAndMetadata(world, -4, 19, 3, Blocks.torch, 4);
		setBlockAndMetadata(world, 4, 19, 3, Blocks.torch, 4);
		for (i15 = -2; i15 <= 2; ++i15) {
			for (k12 = -2; k12 <= 2; ++k12) {
				setBlockAndMetadata(world, i15, 21, k12, brick2Block, brick2Meta);
				if (Math.abs(i15) <= 1 && Math.abs(k12) <= 1) {
					setBlockAndMetadata(world, i15, 22, k12, brick2Block, brick2Meta);
					continue;
				}
				setBlockAndMetadata(world, i15, 22, k12, brick2SlabBlock, brick2SlabMeta);
			}
		}
		for (i15 = -5; i15 <= 5; ++i15) {
			for (k12 = -5; k12 <= 5; ++k12) {
				setBlockAndMetadata(world, i15, 21, k12, brick2SlabBlock, brick2SlabMeta);
			}
		}
		int[] i110 = {-4, 4};
		k12 = i110.length;
		for (j14 = 0; j14 < k12; ++j14) {
			i172 = i110[j14];
			for (int k17 : new int[]{-4, 4}) {
				setBlockAndMetadata(world, i172, 20, k17 - 1, brick2StairBlock, 6);
				setBlockAndMetadata(world, i172, 20, k17 + 1, brick2StairBlock, 7);
				for (int k2 = k17 - 1; k2 <= k17 + 1; ++k2) {
					setBlockAndMetadata(world, i172 - 1, 20, k2, brick2StairBlock, 5);
					setBlockAndMetadata(world, i172 + 1, 20, k2, brick2StairBlock, 4);
				}
			}
		}
		setBlockAndMetadata(world, -4, 21, -4, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, -4, 21, -3, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, -4, 21, -2, brick2StairBlock, 1);
		setBlockAndMetadata(world, -4, 21, -1, brick2StairBlock, 3);
		setBlockAndMetadata(world, -4, 21, 0, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, -4, 21, 1, brick2StairBlock, 2);
		setBlockAndMetadata(world, -4, 21, 2, brick2StairBlock, 1);
		setBlockAndMetadata(world, -4, 21, 3, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, -4, 21, 4, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, -3, 21, -4, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, -3, 21, -3, brick2Block, brick2Meta);
		setBlockAndMetadata(world, -3, 21, -2, brick2Block, brick2Meta);
		setBlockAndMetadata(world, -3, 21, -1, brick2StairBlock, 1);
		setBlockAndMetadata(world, -3, 21, 0, brick2StairBlock, 1);
		setBlockAndMetadata(world, -3, 21, 1, brick2StairBlock, 1);
		setBlockAndMetadata(world, -3, 21, 2, brick2Block, brick2Meta);
		setBlockAndMetadata(world, -3, 21, 3, brick2Block, brick2Meta);
		setBlockAndMetadata(world, -3, 21, 4, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, 4, 21, -4, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, 4, 21, -3, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, 4, 21, -2, brick2StairBlock, 0);
		setBlockAndMetadata(world, 4, 21, -1, brick2StairBlock, 3);
		setBlockAndMetadata(world, 4, 21, 0, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, 4, 21, 1, brick2StairBlock, 2);
		setBlockAndMetadata(world, 4, 21, 2, brick2StairBlock, 0);
		setBlockAndMetadata(world, 4, 21, 3, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, 4, 21, 4, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, 3, 21, -4, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, 3, 21, -3, brick2Block, brick2Meta);
		setBlockAndMetadata(world, 3, 21, -2, brick2Block, brick2Meta);
		setBlockAndMetadata(world, 3, 21, -1, brick2StairBlock, 0);
		setBlockAndMetadata(world, 3, 21, 0, brick2StairBlock, 0);
		setBlockAndMetadata(world, 3, 21, 1, brick2StairBlock, 0);
		setBlockAndMetadata(world, 3, 21, 2, brick2Block, brick2Meta);
		setBlockAndMetadata(world, 3, 21, 3, brick2Block, brick2Meta);
		setBlockAndMetadata(world, 3, 21, 4, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, -2, 21, 4, brick2StairBlock, 3);
		setBlockAndMetadata(world, -1, 21, 4, brick2StairBlock, 0);
		setBlockAndMetadata(world, 0, 21, 4, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, 1, 21, 4, brick2StairBlock, 1);
		setBlockAndMetadata(world, 2, 21, 4, brick2StairBlock, 3);
		setBlockAndMetadata(world, -2, 21, 3, brick2Block, brick2Meta);
		setBlockAndMetadata(world, -1, 21, 3, brick2StairBlock, 3);
		setBlockAndMetadata(world, 0, 21, 3, brick2StairBlock, 3);
		setBlockAndMetadata(world, 1, 21, 3, brick2StairBlock, 3);
		setBlockAndMetadata(world, 2, 21, 3, brick2Block, brick2Meta);
		setBlockAndMetadata(world, -2, 21, -4, brick2StairBlock, 2);
		setBlockAndMetadata(world, -1, 21, -4, brick2StairBlock, 0);
		setBlockAndMetadata(world, 0, 21, -4, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, 1, 21, -4, brick2StairBlock, 1);
		setBlockAndMetadata(world, 2, 21, -4, brick2StairBlock, 2);
		setBlockAndMetadata(world, -2, 21, -3, brick2Block, brick2Meta);
		setBlockAndMetadata(world, -1, 21, -3, brick2StairBlock, 2);
		setBlockAndMetadata(world, 0, 21, -3, brick2StairBlock, 2);
		setBlockAndMetadata(world, 1, 21, -3, brick2StairBlock, 2);
		setBlockAndMetadata(world, 2, 21, -3, brick2Block, brick2Meta);
		placeBarredWindowOnZ(world, -5, 3, 0);
		placeBarredWindowOnZ(world, 5, 3, 0);
		placeBarredWindowOnX(world, 0, 3, -5);
		placeBarredWindowOnX(world, 0, 3, 5);
		placeBarredWindowOnZ(world, -5, 8, 0);
		placeBarredWindowOnZ(world, 5, 8, 0);
		placeBarredWindowOnX(world, 0, 8, -5);
		placeBarredWindowOnX(world, 0, 8, 5);
		placeBarredWindowOnZ(world, -4, 13, 0);
		placeBarredWindowOnZ(world, 4, 13, 0);
		placeBarredWindowOnX(world, 0, 13, -4);
		placeBarredWindowOnX(world, 0, 13, 4);
		for (i12 = -2; i12 <= 2; ++i12) {
			for (k12 = -8; k12 <= -7; ++k12) {
				j14 = 0;
				while (!isOpaque(world, i12, j14, k12) && getY(j14) >= 0) {
					placeRandomBrick(world, random, i12, j14, k12);
					setGrassToDirt(world, i12, j14 - 1, k12);
					--j14;
				}
			}
		}
		for (i12 = -2; i12 <= 2; ++i12) {
			if (Math.abs(i12) == 2) {
				for (j12 = 1; j12 <= 4; ++j12) {
					setBlockAndMetadata(world, i12, j12, -6, pillarBlock, pillarMeta);
				}
			}
			setBlockAndMetadata(world, i12, 5, -6, brick2SlabBlock, brick2SlabMeta);
			placeRandomStairs(world, random, i12, 1, -8, 2);
		}
		placeWallBanner(world, 0, 7, -5, bannerType, 2);
		for (i12 = -1; i12 <= 1; ++i12) {
			for (k12 = -7; k12 <= -6; ++k12) {
				placeRandomBrick(world, random, i12, 1, k12);
			}
			placeRandomBrick(world, random, i12, 1, -5);
			setAir(world, i12, 2, -5);
			setAir(world, i12, 3, -5);
			setAir(world, i12, 4, -5);
		}
		placeRandomStairs(world, random, -2, 1, -7, 1);
		placeRandomStairs(world, random, 2, 1, -7, 0);
		for (i12 = -1; i12 <= 1; ++i12) {
			for (j12 = 2; j12 <= 4; ++j12) {
				setBlockAndMetadata(world, i12, j12, -6, gateBlock, 3);
			}
		}
		placeRandomSlab(world, random, -4, 2, -4, true);
		placeBarrel(world, random, -4, 3, -4, 4, LOTRFoods.GONDOR_DRINK);
		placeRandomSlab(world, random, -4, 2, -3, true);
		placeBarrel(world, random, -4, 3, -3, 4, LOTRFoods.GONDOR_DRINK);
		placeChest(world, random, -4, 2, -2, LOTRMod.chestLebethron, 4, LOTRChestContents.GONDOR_FORTRESS_DRINKS);
		placeRandomSlab(world, random, 4, 2, -4, true);
		placeBarrel(world, random, 4, 3, -4, 5, LOTRFoods.GONDOR_DRINK);
		placeRandomSlab(world, random, 4, 2, -3, true);
		placeBarrel(world, random, 4, 3, -3, 5, LOTRFoods.GONDOR_DRINK);
		placeChest(world, random, 4, 2, -2, LOTRMod.chestLebethron, 5, LOTRChestContents.GONDOR_FORTRESS_DRINKS);
		setBlockAndMetadata(world, -4, 2, 4, tableBlock, 0);
		setBlockAndMetadata(world, 4, 2, 4, tableBlock, 0);
		for (i12 = -1; i12 <= 1; ++i12) {
			for (step = 0; step <= 3; ++step) {
				k14 = -1 + step;
				j15 = 2 + step;
				setAir(world, i12, 6, k14);
				for (j2 = 2; j2 < j15; ++j2) {
					placeRandomBrick(world, random, i12, j2, k14);
				}
				placeRandomStairs(world, random, i12, j15, k14, 2);
			}
			placeRandomStairs(world, random, i12, 6, 3, 2);
		}
		placeChest(world, random, 0, 2, 2, LOTRMod.chestLebethron, 3, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES);
		setAir(world, 0, 3, 2);
		setBlockAndMetadata(world, 0, 7, -4, LOTRMod.commandTable, 0);
		int[] i111 = {-3, 3};
		step = i111.length;
		for (k14 = 0; k14 < step; ++k14) {
			i172 = i111[k14];
			for (int step2 = 0; step2 <= 4; ++step2) {
				k1 = 2 - step2;
				j1 = 7 + step2;
				setAir(world, i172, 11, k1);
				for (int j22 = 7; j22 < j1; ++j22) {
					placeRandomBrick(world, random, i172, j22, k1);
				}
				placeRandomStairs(world, random, i172, j1, k1, 3);
			}
		}
		for (i13 = -1; i13 <= 1; ++i13) {
			for (step = 0; step <= 3; ++step) {
				k14 = -2 + step;
				j15 = 12 + step;
				setAir(world, i13, 16, k14);
				for (j2 = 12; j2 < j15; ++j2) {
					placeRandomBrick(world, random, i13, j2, k14);
				}
				placeRandomStairs(world, random, i13, j15, k14, 2);
			}
			placeRandomStairs(world, random, i13, 16, 2, 2);
		}
		for (int k18 = 5; k18 <= 28; ++k18) {
			for (j12 = 12; j12 <= 15; ++j12) {
				for (int i112 = -2; i112 <= 2; ++i112) {
					setAir(world, i112, j12, k18);
				}
			}
		}
		for (i13 = -1; i13 <= 1; ++i13) {
			placeRandomBrick(world, random, i13, 13, 4);
			placeRandomBrick(world, random, i13, 14, 4);
		}
		for (int i1721 : new int[]{-2, 2}) {
			placeRandomBrick(world, random, i1721, 12, 5);
			placeRandomBrick(world, random, i1721, 13, 5);
			setBlockAndMetadata(world, i1721, 14, 5, brick2WallBlock, brick2WallMeta);
			setBlockAndMetadata(world, i1721, 15, 5, Blocks.torch, 5);
		}
		setBlockAndMetadata(world, 0, 12, 4, doorBlock, 3);
		setBlockAndMetadata(world, 0, 13, 4, doorBlock, 8);
		for (int k19 = 6; k19 <= 28; ++k19) {
			for (int i113 = -1; i113 <= 1; ++i113) {
				placeRandomBrick(world, random, i113, 11, k19);
			}
			placeRandomWall(world, random, -2, 12, k19);
			placeRandomWall(world, random, 2, 12, k19);
			placeRandomStairs(world, random, -2, 11, k19, 5);
			placeRandomStairs(world, random, 2, 11, k19, 4);
		}
		for (i1 = -1; i1 <= 1; ++i1) {
			placeRandomStairs(world, random, i1, 10, 6, 7);
			placeRandomStairs(world, random, i1, 10, 16, 6);
			placeRandomStairs(world, random, i1, 10, 18, 7);
			placeRandomStairs(world, random, i1, 10, 28, 6);
			j12 = 10;
			while (!isOpaque(world, i1, j12, 17) && getY(j12) >= 0) {
				placeRandomBrick(world, random, i1, j12, 17);
				setGrassToDirt(world, i1, j12 - 1, 17);
				--j12;
			}
		}
		for (j13 = 12; j13 <= 13; ++j13) {
			placeRandomBrick(world, random, -2, j13, 11);
			placeRandomBrick(world, random, 2, j13, 11);
			placeRandomBrick(world, random, -2, j13, 23);
			placeRandomBrick(world, random, 2, j13, 23);
		}
		setBlockAndMetadata(world, -1, 13, 11, Blocks.torch, 2);
		setBlockAndMetadata(world, 1, 13, 11, Blocks.torch, 1);
		setBlockAndMetadata(world, -1, 13, 23, Blocks.torch, 2);
		setBlockAndMetadata(world, 1, 13, 23, Blocks.torch, 1);
		placeBanner(world, -2, 14, 11, bannerType, 3);
		placeBanner(world, 2, 14, 11, bannerType, 1);
		placeBanner(world, -2, 14, 23, bannerType, 3);
		placeBanner(world, 2, 14, 23, bannerType, 1);
		for (j13 = 12; j13 <= 14; ++j13) {
			placeRandomBrick(world, random, -2, j13, 17);
			placeRandomBrick(world, random, 2, j13, 17);
		}
		placeRandomBrick(world, random, -2, 15, 17);
		placeRandomBrick(world, random, 2, 15, 17);
		placeRandomStairs(world, random, -1, 15, 17, 4);
		placeRandomStairs(world, random, 1, 15, 17, 5);
		placeRandomSlab(world, random, 0, 15, 17, true);
		for (i1 = -1; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 16, 17, brick2Block, brick2Meta);
		}
		setBlockAndMetadata(world, -2, 16, 17, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, 2, 16, 17, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, 0, 17, 17, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, -2, 14, 16, Blocks.torch, 4);
		setBlockAndMetadata(world, 2, 14, 16, Blocks.torch, 4);
		setBlockAndMetadata(world, -2, 14, 18, Blocks.torch, 3);
		setBlockAndMetadata(world, 2, 14, 18, Blocks.torch, 3);
		LOTRWorldGenBeaconTower beaconTower = new LOTRWorldGenBeaconTower(notifyChanges);
		beaconTower.restrictions = false;
		beaconTower.generateRoom = false;
		beaconTower.strFief = strFief;
		int beaconX = 0;
		int beaconY = 12;
		int beaconZ = 34;
		beaconTower.generateWithSetRotationAndHeight(world, random, getX(beaconX, beaconZ), getY(beaconY), getZ(beaconX, beaconZ), (getRotationMode() + 2) % 4, -8);
		setAir(world, -1, 12, 29);
		setAir(world, 0, 12, 29);
		setAir(world, 1, 12, 29);
		Class[] soldierClasses = strFief.getSoldierClasses();
		int soldiers = 4 + random.nextInt(4);
		for (int l = 0; l < soldiers; ++l) {
			Class entityClass = soldierClasses[0];
			if (random.nextInt(3) == 0) {
				entityClass = soldierClasses[1];
			}
			LOTREntityGondorMan soldier = (LOTREntityGondorMan) LOTREntities.createEntityByClass(entityClass, world);
			soldier.spawnRidingHorse = false;
			spawnNPCAndSetHome(soldier, world, 0, 2, -3, 32);
		}
		LOTREntityGondorMan captain = strFief.createCaptain(world);
		captain.spawnRidingHorse = false;
		spawnNPCAndSetHome(captain, world, 0, 15, 0, 8);
		LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
		respawner.setSpawnClasses(soldierClasses[0], soldierClasses[1]);
		respawner.setCheckRanges(24, -8, 18, 12);
		respawner.setSpawnRanges(4, 2, 17, 32);
		placeNPCRespawner(respawner, world, 0, 2, 0);
		return true;
	}

	public void placeBarredWindowOnX(World world, int i, int j, int k) {
		for (int i1 = -1; i1 <= 1; ++i1) {
			for (int j1 = 0; j1 <= 1; ++j1) {
				setBlockAndMetadata(world, i + i1, j + j1, k, barsBlock, 0);
			}
		}
	}

	public void placeBarredWindowOnZ(World world, int i, int j, int k) {
		for (int k1 = -1; k1 <= 1; ++k1) {
			for (int j1 = 0; j1 <= 1; ++j1) {
				setBlockAndMetadata(world, i, j + j1, k + k1, barsBlock, 0);
			}
		}
	}

	public void placeRandomBrick(World world, Random random, int i, int j, int k) {
		if (random.nextInt(10) == 0) {
			if (random.nextBoolean()) {
				setBlockAndMetadata(world, i, j, k, brickMossyBlock, brickMossyMeta);
			} else {
				setBlockAndMetadata(world, i, j, k, brickCrackedBlock, brickCrackedMeta);
			}
		} else {
			setBlockAndMetadata(world, i, j, k, brickBlock, brickMeta);
		}
	}

	public void placeRandomSlab(World world, Random random, int i, int j, int k, boolean inverted) {
		int flag;
		flag = inverted ? 8 : 0;
		if (random.nextInt(10) == 0) {
			if (random.nextBoolean()) {
				setBlockAndMetadata(world, i, j, k, brickMossySlabBlock, brickMossySlabMeta | flag);
			} else {
				setBlockAndMetadata(world, i, j, k, brickCrackedSlabBlock, brickCrackedSlabMeta | flag);
			}
		} else {
			setBlockAndMetadata(world, i, j, k, brickSlabBlock, brickSlabMeta | flag);
		}
	}

	public void placeRandomStairs(World world, Random random, int i, int j, int k, int meta) {
		if (random.nextInt(10) == 0) {
			if (random.nextBoolean()) {
				setBlockAndMetadata(world, i, j, k, brickMossyStairBlock, meta);
			} else {
				setBlockAndMetadata(world, i, j, k, brickCrackedStairBlock, meta);
			}
		} else {
			setBlockAndMetadata(world, i, j, k, brickStairBlock, meta);
		}
	}

	public void placeRandomWall(World world, Random random, int i, int j, int k) {
		if (random.nextInt(10) == 0) {
			if (random.nextBoolean()) {
				setBlockAndMetadata(world, i, j, k, brickMossyWallBlock, brickMossyWallMeta);
			} else {
				setBlockAndMetadata(world, i, j, k, brickCrackedWallBlock, brickCrackedWallMeta);
			}
		} else {
			setBlockAndMetadata(world, i, j, k, brickWallBlock, brickWallMeta);
		}
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		barsBlock = Blocks.iron_bars;
	}
}
