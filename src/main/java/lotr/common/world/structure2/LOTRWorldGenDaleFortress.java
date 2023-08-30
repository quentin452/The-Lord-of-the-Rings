package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenDaleFortress extends LOTRWorldGenDaleStructure {
	public LOTRWorldGenDaleFortress(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int i1;
		int i12;
		int j1;
		int j12;
		int k2;
		Block block;
		int k1;
		int i132;
		int i2;
		int k12;
		int i14;
		setOriginAndRotation(world, i, j, k, rotation, 12);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			int maxEdgeHeight = 0;
			for (i132 = -12; i132 <= 12; ++i132) {
				for (int k13 = -12; k13 <= 12; ++k13) {
					j12 = getTopBlock(world, i132, k13) - 1;
					block = getBlock(world, i132, j12, k13);
					if (block != Blocks.grass) {
						return false;
					}
					if (j12 < minHeight) {
						minHeight = j12;
					}
					if (j12 > maxHeight) {
						maxHeight = j12;
					}
					if (maxHeight - minHeight > 8) {
						return false;
					}
					if (Math.abs(i132) != 12 && Math.abs(k13) != 12 || j12 <= maxEdgeHeight) {
						continue;
					}
					maxEdgeHeight = j12;
				}
			}
			originY = getY(maxEdgeHeight);
		}
		for (int i15 = -11; i15 <= 11; ++i15) {
			for (k1 = -11; k1 <= 11; ++k1) {
				int j13;
				i2 = Math.abs(i15);
				k2 = Math.abs(k1);
				layFoundation(world, i15, k1);
				if (i2 > 9 || k2 > 9) {
					continue;
				}
				for (j13 = 1; j13 <= 8; ++j13) {
					setAir(world, i15, j13, k1);
				}
				if (i2 <= 8 && k2 <= 8 && (i2 == 8 && k2 >= 4 || k2 == 8 && i2 >= 4)) {
					for (j13 = 1; j13 <= 3; ++j13) {
						setBlockAndMetadata(world, i15, j13, k1, brickBlock, brickMeta);
					}
				}
				if (i2 == 9 && k2 == 2 || k2 == 9 && i2 == 2) {
					for (j13 = 1; j13 <= 5; ++j13) {
						setBlockAndMetadata(world, i15, j13, k1, pillarBlock, pillarMeta);
					}
				} else if (i2 == 9 && (k2 == 3 || k2 == 5 || k2 == 9) || k2 == 9 && (i2 == 3 || i2 == 5)) {
					for (j13 = 1; j13 <= 5; ++j13) {
						setBlockAndMetadata(world, i15, j13, k1, brickBlock, brickMeta);
					}
				} else if (i2 == 9 || k2 == 9) {
					for (j13 = 4; j13 <= 5; ++j13) {
						setBlockAndMetadata(world, i15, j13, k1, brickBlock, brickMeta);
					}
				}
				if (i2 == 9) {
					setBlockAndMetadata(world, i15, 3, -8, brickStairBlock, 7);
					setBlockAndMetadata(world, i15, 3, -6, brickStairBlock, 6);
					setBlockAndMetadata(world, i15, 3, 6, brickStairBlock, 7);
					setBlockAndMetadata(world, i15, 3, 8, brickStairBlock, 6);
				}
				if (k2 == 9) {
					setBlockAndMetadata(world, -8, 3, k1, brickStairBlock, 4);
					setBlockAndMetadata(world, -6, 3, k1, brickStairBlock, 5);
					setBlockAndMetadata(world, 6, 3, k1, brickStairBlock, 4);
					setBlockAndMetadata(world, 8, 3, k1, brickStairBlock, 5);
				}
				if (i2 == 9 && k2 <= 5 || k2 == 9 && i2 <= 5) {
					setBlockAndMetadata(world, i15, 6, k1, brickWallBlock, brickWallMeta);
				}
				if (i2 == 6 && k2 <= 5 || k2 == 6 && i2 <= 5) {
					setBlockAndMetadata(world, i15, 6, k1, brickWallBlock, brickWallMeta);
					if (i2 == 6 && k2 <= 3 || k2 == 6 && i2 <= 3) {
						setBlockAndMetadata(world, i15, 5, k1, brickSlabBlock, brickSlabMeta | 8);
					} else {
						setBlockAndMetadata(world, i15, 5, k1, brickBlock, brickMeta);
					}
					if (i15 == -5) {
						setBlockAndMetadata(world, -5, 4, k1, brickStairBlock, 4);
					} else if (i15 == 5) {
						setBlockAndMetadata(world, 5, 4, k1, brickStairBlock, 5);
					} else if (k1 == -5) {
						setBlockAndMetadata(world, i15, 4, -5, brickStairBlock, 7);
					} else if (k1 == 5) {
						setBlockAndMetadata(world, i15, 4, 5, brickStairBlock, 6);
					}
				}
				if (i2 <= 8 && k2 <= 8 && (i2 >= 7 || k2 >= 7)) {
					if (i2 <= 2 || k2 <= 2) {
						setBlockAndMetadata(world, i15, 5, k1, plankBlock, plankMeta);
					} else if (i15 == -3) {
						setBlockAndMetadata(world, -3, 4, k1, plankStairBlock, 4);
						setBlockAndMetadata(world, -3, 5, k1, plankStairBlock, 1);
					} else if (i15 == 3) {
						setBlockAndMetadata(world, 3, 4, k1, plankStairBlock, 5);
						setBlockAndMetadata(world, 3, 5, k1, plankStairBlock, 0);
					} else if (k1 == -3) {
						setBlockAndMetadata(world, i15, 4, -3, plankStairBlock, 7);
						setBlockAndMetadata(world, i15, 5, -3, plankStairBlock, 2);
					} else if (k1 == 3) {
						setBlockAndMetadata(world, i15, 4, 3, plankStairBlock, 6);
						setBlockAndMetadata(world, i15, 5, 3, plankStairBlock, 3);
					} else {
						setBlockAndMetadata(world, i15, 4, k1, plankBlock, plankMeta);
					}
				}
				if (i2 == 6 && k2 == 6) {
					for (j13 = 1; j13 <= 5; ++j13) {
						setBlockAndMetadata(world, i15, j13, k1, brickBlock, brickMeta);
					}
				}
				if ((i2 == 6 || i2 == 9) && (k2 == 6 || k2 == 9)) {
					for (j13 = 6; j13 <= 7; ++j13) {
						setBlockAndMetadata(world, i15, j13, k1, brickBlock, brickMeta);
					}
				}
				if ((i2 != 9 || k2 != 7 && k2 != 8) && (k2 != 9 || i2 != 7 && i2 != 8)) {
					continue;
				}
				for (j13 = 6; j13 <= 7; ++j13) {
					setBlockAndMetadata(world, i15, j13, k1, barsBlock, 0);
				}
			}
		}
		for (int j14 = 1; j14 <= 3; ++j14) {
			for (i14 = -1; i14 <= 1; ++i14) {
				setBlockAndMetadata(world, i14, j14, -9, LOTRMod.gateWooden, 2);
				setBlockAndMetadata(world, i14, j14, 9, brickBlock, brickMeta);
			}
			for (k1 = -1; k1 <= 1; ++k1) {
				setBlockAndMetadata(world, -9, j14, k1, LOTRMod.gateWooden, 5);
				setBlockAndMetadata(world, 9, j14, k1, LOTRMod.gateWooden, 4);
			}
		}
		for (int i1321 : new int[]{-9, 6}) {
			for (int k14 : new int[]{-9, 6}) {
				int k22;
				int i22;
				for (i22 = i1321; i22 <= i1321 + 3; ++i22) {
					setBlockAndMetadata(world, i22, 8, k14, brickBlock, brickMeta);
					setBlockAndMetadata(world, i22, 8, k14 + 3, brickBlock, brickMeta);
				}
				for (k22 = k14 + 1; k22 <= k14 + 2; ++k22) {
					setBlockAndMetadata(world, i1321, 8, k22, brickBlock, brickMeta);
					setBlockAndMetadata(world, i1321 + 3, 8, k22, brickBlock, brickMeta);
				}
				for (i22 = i1321; i22 <= i1321 + 3; ++i22) {
					for (int k23 = k14; k23 <= k14 + 3; ++k23) {
						setBlockAndMetadata(world, i22, 9, k23, roofBlock, roofMeta);
						setBlockAndMetadata(world, i22, 10, k23, roofSlabBlock, roofSlabMeta);
					}
				}
				for (i22 = i1321 - 1; i22 <= i1321 + 4; ++i22) {
					setBlockAndMetadata(world, i22, 9, k14 - 1, roofStairBlock, 2);
					setBlockAndMetadata(world, i22, 9, k14 + 4, roofStairBlock, 3);
				}
				for (k22 = k14; k22 <= k14 + 3; ++k22) {
					setBlockAndMetadata(world, i1321 - 1, 9, k22, roofStairBlock, 1);
					setBlockAndMetadata(world, i1321 + 4, 9, k22, roofStairBlock, 0);
				}
				setBlockAndMetadata(world, i1321, 8, k14 - 1, roofStairBlock, 6);
				setBlockAndMetadata(world, i1321 + 3, 8, k14 - 1, roofStairBlock, 6);
				setBlockAndMetadata(world, i1321 + 4, 8, k14, roofStairBlock, 4);
				setBlockAndMetadata(world, i1321 + 4, 8, k14 + 3, roofStairBlock, 4);
				setBlockAndMetadata(world, i1321 + 3, 8, k14 + 4, roofStairBlock, 7);
				setBlockAndMetadata(world, i1321, 8, k14 + 4, roofStairBlock, 7);
				setBlockAndMetadata(world, i1321 - 1, 8, k14 + 3, roofStairBlock, 5);
				setBlockAndMetadata(world, i1321 - 1, 8, k14, roofStairBlock, 5);
			}
		}
		setBlockAndMetadata(world, -6, 8, -8, roofStairBlock, 7);
		setBlockAndMetadata(world, -6, 8, -7, roofStairBlock, 6);
		setBlockAndMetadata(world, -7, 8, -6, roofStairBlock, 5);
		setBlockAndMetadata(world, -8, 8, -6, roofStairBlock, 4);
		setBlockAndMetadata(world, 6, 8, -8, roofStairBlock, 7);
		setBlockAndMetadata(world, 6, 8, -7, roofStairBlock, 6);
		setBlockAndMetadata(world, 7, 8, -6, roofStairBlock, 4);
		setBlockAndMetadata(world, 8, 8, -6, roofStairBlock, 5);
		setBlockAndMetadata(world, -6, 8, 8, roofStairBlock, 6);
		setBlockAndMetadata(world, -6, 8, 7, roofStairBlock, 7);
		setBlockAndMetadata(world, -7, 8, 6, roofStairBlock, 5);
		setBlockAndMetadata(world, -8, 8, 6, roofStairBlock, 4);
		setBlockAndMetadata(world, 6, 8, 8, roofStairBlock, 6);
		setBlockAndMetadata(world, 6, 8, 7, roofStairBlock, 7);
		setBlockAndMetadata(world, 7, 8, 6, roofStairBlock, 4);
		setBlockAndMetadata(world, 8, 8, 6, roofStairBlock, 5);
		setBlockAndMetadata(world, -8, 8, -8, Blocks.torch, 2);
		setBlockAndMetadata(world, 8, 8, -8, Blocks.torch, 1);
		setBlockAndMetadata(world, -8, 8, 8, Blocks.torch, 2);
		setBlockAndMetadata(world, 8, 8, 8, Blocks.torch, 1);
		int[] j14 = {-2, 2};
		k1 = j14.length;
		for (i2 = 0; i2 < k1; ++i2) {
			i132 = j14[i2];
			for (int j16 = 6; j16 <= 8; ++j16) {
				setBlockAndMetadata(world, i132, j16, -9, pillarBlock, pillarMeta);
			}
			setBlockAndMetadata(world, i132, 9, -9, roofSlabBlock, roofSlabMeta);
			setBlockAndMetadata(world, i132, 5, -10, Blocks.torch, 4);
			placeWallBanner(world, i132, 8, -9, LOTRItemBanner.BannerType.DALE, 2);
		}
		for (j1 = 1; j1 <= 4; ++j1) {
			setBlockAndMetadata(world, -7, j1, -7, Blocks.ladder, 4);
			setBlockAndMetadata(world, 7, j1, -7, Blocks.ladder, 5);
		}
		setBlockAndMetadata(world, 0, 4, -8, Blocks.torch, 3);
		setBlockAndMetadata(world, 0, 4, 8, Blocks.torch, 4);
		setBlockAndMetadata(world, -8, 4, 0, Blocks.torch, 2);
		setBlockAndMetadata(world, 8, 4, 0, Blocks.torch, 1);
		setBlockAndMetadata(world, -6, 3, -5, Blocks.torch, 3);
		setBlockAndMetadata(world, -5, 3, -6, Blocks.torch, 2);
		setBlockAndMetadata(world, 6, 3, -5, Blocks.torch, 3);
		setBlockAndMetadata(world, 5, 3, -6, Blocks.torch, 1);
		setBlockAndMetadata(world, -6, 3, 5, Blocks.torch, 4);
		setBlockAndMetadata(world, -5, 3, 6, Blocks.torch, 2);
		setBlockAndMetadata(world, 6, 3, 5, Blocks.torch, 4);
		setBlockAndMetadata(world, 5, 3, 6, Blocks.torch, 1);
		for (k12 = -8; k12 <= -4; ++k12) {
			setBlockAndMetadata(world, -3, 0, k12, Blocks.grass, 0);
			setBlockAndMetadata(world, -2, 0, k12, Blocks.grass, 0);
			setBlockAndMetadata(world, 2, 0, k12, Blocks.grass, 0);
			setBlockAndMetadata(world, 3, 0, k12, Blocks.grass, 0);
		}
		for (k12 = 4; k12 <= 8; ++k12) {
			setBlockAndMetadata(world, -3, 0, k12, Blocks.grass, 0);
			setBlockAndMetadata(world, -2, 0, k12, Blocks.grass, 0);
			setBlockAndMetadata(world, 2, 0, k12, Blocks.grass, 0);
			setBlockAndMetadata(world, 3, 0, k12, Blocks.grass, 0);
		}
		for (i1 = -8; i1 <= -4; ++i1) {
			setBlockAndMetadata(world, i1, 0, -3, Blocks.grass, 0);
			setBlockAndMetadata(world, i1, 0, -2, Blocks.grass, 0);
			setBlockAndMetadata(world, i1, 0, 2, Blocks.grass, 0);
			setBlockAndMetadata(world, i1, 0, 3, Blocks.grass, 0);
		}
		for (i1 = 4; i1 <= 8; ++i1) {
			setBlockAndMetadata(world, i1, 0, -3, Blocks.grass, 0);
			setBlockAndMetadata(world, i1, 0, -2, Blocks.grass, 0);
			setBlockAndMetadata(world, i1, 0, 2, Blocks.grass, 0);
			setBlockAndMetadata(world, i1, 0, 3, Blocks.grass, 0);
		}
		for (i1 = -1; i1 <= 1; ++i1) {
			for (k1 = -1; k1 <= 1; ++k1) {
				i2 = Math.abs(i1);
				k2 = Math.abs(k1);
				setBlockAndMetadata(world, i1, 1, k1, brickBlock, brickMeta);
				setBlockAndMetadata(world, i1, 2, k1, brickBlock, brickMeta);
				if (i2 == 1 && k2 == 1) {
					setBlockAndMetadata(world, i1, 3, k1, brickWallBlock, brickWallMeta);
				} else if (i2 == 1 || k2 == 1) {
					setBlockAndMetadata(world, i1, 3, k1, brickBlock, brickMeta);
					setBlockAndMetadata(world, i1, 4, k1, brickBlock, brickMeta);
					setBlockAndMetadata(world, i1, 5, k1, brickWallBlock, brickWallMeta);
				}
				if (i1 != 0 || k1 != 0) {
					continue;
				}
				setBlockAndMetadata(world, i1, 3, k1, brickBlock, brickMeta);
				setBlockAndMetadata(world, i1, 4, k1, LOTRMod.hearth, 0);
				setBlockAndMetadata(world, i1, 5, k1, Blocks.fire, 0);
			}
		}
		setBlockAndMetadata(world, 0, 6, 0, roofBlock, roofMeta);
		setBlockAndMetadata(world, -1, 6, 0, roofStairBlock, 1);
		setBlockAndMetadata(world, 1, 6, 0, roofStairBlock, 0);
		setBlockAndMetadata(world, 0, 6, -1, roofStairBlock, 2);
		setBlockAndMetadata(world, 0, 6, 1, roofStairBlock, 3);
		for (k12 = -7; k12 <= -4; ++k12) {
			for (i14 = -7; i14 <= -4; ++i14) {
				setBlockAndMetadata(world, i14, 0, k12, LOTRMod.dirtPath, 0);
			}
			for (i14 = 4; i14 <= 7; ++i14) {
				setBlockAndMetadata(world, i14, 0, k12, LOTRMod.dirtPath, 0);
			}
		}
		setBlockAndMetadata(world, -4, 1, -5, brickWallBlock, brickWallMeta);
		setBlockAndMetadata(world, -5, 1, -4, brickWallBlock, brickWallMeta);
		setBlockAndMetadata(world, 5, 1, -4, brickWallBlock, brickWallMeta);
		setBlockAndMetadata(world, 4, 1, -5, brickWallBlock, brickWallMeta);
		for (k12 = 4; k12 <= 7; ++k12) {
			for (i14 = -7; i14 <= -4; ++i14) {
				setBlockAndMetadata(world, i14, 0, k12, plankBlock, plankMeta);
			}
			for (i14 = 4; i14 <= 7; ++i14) {
				setBlockAndMetadata(world, i14, 0, k12, plankBlock, plankMeta);
			}
		}
		for (k12 = 4; k12 <= 6; ++k12) {
			for (i14 = -6; i14 <= -4; ++i14) {
				setBlockAndMetadata(world, i14, 4, k12, Blocks.wool, 11);
			}
			for (i14 = 4; i14 <= 6; ++i14) {
				setBlockAndMetadata(world, i14, 4, k12, Blocks.wool, 11);
			}
		}
		for (j1 = 1; j1 <= 3; ++j1) {
			setBlockAndMetadata(world, -4, j1, 4, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, 4, j1, 4, fenceBlock, fenceMeta);
		}
		setBlockAndMetadata(world, -4, 0, -4, Blocks.grass, 0);
		setBlockAndMetadata(world, -3, 0, -3, Blocks.grass, 0);
		setBlockAndMetadata(world, 4, 0, -4, Blocks.grass, 0);
		setBlockAndMetadata(world, 3, 0, -3, Blocks.grass, 0);
		setBlockAndMetadata(world, -4, 0, 4, Blocks.grass, 0);
		setBlockAndMetadata(world, -3, 0, 3, Blocks.grass, 0);
		setBlockAndMetadata(world, 4, 0, 4, Blocks.grass, 0);
		setBlockAndMetadata(world, 3, 0, 3, Blocks.grass, 0);
		for (j1 = 1; j1 <= 3; ++j1) {
			setBlockAndMetadata(world, -7, j1, 7, plankBlock, plankMeta);
		}
		setBlockAndMetadata(world, -7, 3, 6, plankStairBlock, 6);
		setBlockAndMetadata(world, -6, 3, 7, plankStairBlock, 4);
		for (int j17 : new int[]{1, 2}) {
			setBlockAndMetadata(world, -7, j17, 5, LOTRMod.strawBed, 0);
			setBlockAndMetadata(world, -7, j17, 6, LOTRMod.strawBed, 8);
			setBlockAndMetadata(world, -5, j17, 7, LOTRMod.strawBed, 3);
			setBlockAndMetadata(world, -6, j17, 7, LOTRMod.strawBed, 11);
		}
		for (int j18 = 1; j18 <= 3; ++j18) {
			setBlockAndMetadata(world, 7, j18, 7, plankBlock, plankMeta);
		}
		setBlockAndMetadata(world, 7, 3, 6, plankStairBlock, 6);
		setBlockAndMetadata(world, 6, 3, 7, plankStairBlock, 5);
		for (i12 = 4; i12 <= 6; ++i12) {
			setBlockAndMetadata(world, i12, 1, 7, plankBlock, plankMeta);
			if (i12 > 5) {
				continue;
			}
			placeBarrel(world, random, i12, 2, 7, 2, LOTRFoods.DALE_DRINK);
		}
		for (int k15 = 4; k15 <= 6; ++k15) {
			setBlockAndMetadata(world, 7, 1, k15, plankBlock, plankMeta);
			if (k15 > 5) {
				continue;
			}
			placeBarrel(world, random, 7, 2, k15, 5, LOTRFoods.DALE_DRINK);
		}
		for (i12 = -3; i12 <= 3; ++i12) {
			setBlockAndMetadata(world, i12, 0, 8, floorBlock, floorMeta);
		}
		setBlockAndMetadata(world, -3, 1, 8, plankBlock, plankMeta);
		setBlockAndMetadata(world, -2, 1, 8, plankBlock, plankMeta);
		placeChest(world, random, -3, 2, 8, 2, LOTRChestContents.DALE_WATCHTOWER);
		placeChest(world, random, -2, 2, 8, 2, LOTRChestContents.DALE_WATCHTOWER);
		setBlockAndMetadata(world, 2, 1, 8, LOTRMod.daleTable, 0);
		setBlockAndMetadata(world, 3, 1, 8, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, 0, 1, 7, LOTRMod.commandTable, 0);
		setBlockAndMetadata(world, 6, 1, 6, Blocks.furnace, 2);
		LOTREntityDaleCaptain captain = new LOTREntityDaleCaptain(world);
		captain.spawnRidingHorse = false;
		spawnNPCAndSetHome(captain, world, 0, 1, 3, 16);
		int soldiers = 3 + random.nextInt(4);
		for (int l = 0; l < soldiers; ++l) {
			LOTREntityDaleSoldier soldier = random.nextInt(3) == 0 ? new LOTREntityDaleArcher(world) : new LOTREntityDaleSoldier(world);
			soldier.spawnRidingHorse = false;
			spawnNPCAndSetHome(soldier, world, 0, 1, 3, 16);
		}
		LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
		respawner.setSpawnClasses(LOTREntityDaleSoldier.class, LOTREntityDaleArcher.class);
		respawner.setCheckRanges(16, -6, 10, 12);
		respawner.setSpawnRanges(10, -2, 2, 16);
		placeNPCRespawner(respawner, world, 0, 0, 0);
		return true;
	}

	public void layFoundation(World world, int i, int k) {
		for (int j = 0; j == 0 || !isOpaque(world, i, j, k) && getY(j) >= 0; --j) {
			setBlockAndMetadata(world, i, j, k, floorBlock, floorMeta);
			setGrassToDirt(world, i, j - 1, k);
		}
	}
}
