package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingTower extends LOTRWorldGenEasterlingStructureTown {
	public boolean enableDoor = true;
	public boolean frontLadder;
	public boolean backLadder;
	public boolean leftLadder;
	public boolean rightLadder;

	public LOTRWorldGenEasterlingTower(boolean flag) {
		super(flag);
	}

	public LOTRWorldGenEasterlingTower disableDoor() {
		enableDoor = false;
		return this;
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int i1;
		int i12;
		int k2;
		int i2;
		setOriginAndRotation(world, i, j, k, rotation, 3);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i12 = -3; i12 <= 3; ++i12) {
				for (k1 = -3; k1 <= 3; ++k1) {
					int j1 = getTopBlock(world, i12, k1) - 1;
					if (isSurface(world, i12, j1, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i12 = -2; i12 <= 2; ++i12) {
			for (k1 = -2; k1 <= 2; ++k1) {
				int j1;
				i2 = Math.abs(i12);
				k2 = Math.abs(k1);
				for (j1 = 1; j1 <= 15; ++j1) {
					setAir(world, i12, j1, k1);
				}
				if (i2 == 2 && k2 == 2) {
					for (j1 = 13; (j1 >= 0 || !isOpaque(world, i12, j1, k1)) && getY(j1) >= 0; --j1) {
						setBlockAndMetadata(world, i12, j1, k1, woodBeamBlock, woodBeamMeta);
						setGrassToDirt(world, i12, j1 - 1, k1);
					}
					continue;
				}
				for (j1 = 0; (j1 >= 0 || !isOpaque(world, i12, j1, k1)) && getY(j1) >= 0; --j1) {
					setBlockAndMetadata(world, i12, j1, k1, brickBlock, brickMeta);
					setGrassToDirt(world, i12, j1 - 1, k1);
				}
				if (i2 == 2 || k2 == 2) {
					if (i2 == 2 && k2 == 0 || k2 == 2 && i2 == 0) {
						for (j1 = 1; j1 <= 9; ++j1) {
							setBlockAndMetadata(world, i12, j1, k1, pillarBlock, pillarMeta);
						}
					} else {
						for (j1 = 1; j1 <= 2; ++j1) {
							setBlockAndMetadata(world, i12, j1, k1, brickBlock, brickMeta);
						}
						int stairMeta = 0;
						if (i12 == -2) {
							stairMeta = 1;
						} else if (i12 == 2) {
							stairMeta = 0;
						} else if (k1 == -2) {
							stairMeta = 2;
						} else if (k1 == 2) {
							stairMeta = 3;
						}
						for (int j12 = 3; j12 <= 8; ++j12) {
							if (j12 == 4) {
								setBlockAndMetadata(world, i12, 4, k1, brickRedStairBlock, stairMeta);
								continue;
							}
							setBlockAndMetadata(world, i12, j12, k1, brickStairBlock, stairMeta);
						}
						setBlockAndMetadata(world, i12, 9, k1, brickBlock, brickMeta);
					}
					setBlockAndMetadata(world, i12, 10, k1, brickRedBlock, brickRedMeta);
					setBlockAndMetadata(world, i12, 11, k1, fenceBlock, fenceMeta);
					continue;
				}
				setBlockAndMetadata(world, i12, 4, k1, brickBlock, brickMeta);
				setBlockAndMetadata(world, i12, 10, k1, brickBlock, brickMeta);
			}
		}
		for (i12 = -1; i12 <= 1; ++i12) {
			for (k1 = -1; k1 <= 1; ++k1) {
				i2 = Math.abs(i12);
				k2 = Math.abs(k1);
				if (i2 == 0 || k2 == 0) {
					setBlockAndMetadata(world, i12, 0, k1, pillarBlock, pillarMeta);
					continue;
				}
				setBlockAndMetadata(world, i12, 0, k1, brickRedBlock, brickRedMeta);
			}
		}
		if (enableDoor) {
			setBlockAndMetadata(world, 0, 0, -2, pillarBlock, pillarMeta);
			setBlockAndMetadata(world, 0, 1, -2, doorBlock, 1);
			setBlockAndMetadata(world, 0, 2, -2, doorBlock, 8);
		}
		setBlockAndMetadata(world, -1, 3, -1, Blocks.torch, 3);
		setBlockAndMetadata(world, 1, 3, -1, Blocks.torch, 3);
		setBlockAndMetadata(world, -1, 3, 1, Blocks.torch, 4);
		setBlockAndMetadata(world, 1, 3, 1, Blocks.torch, 4);
		placeWeaponRack(world, -1, 2, 0, 5, getEasterlingWeaponItem(random));
		placeArmorStand(world, 1, 1, 0, 1, null);
		for (int j1 = 1; j1 <= 9; ++j1) {
			setBlockAndMetadata(world, 0, j1, 1, Blocks.ladder, 2);
		}
		setBlockAndMetadata(world, 0, 10, 1, trapdoorBlock, 9);
		setBlockAndMetadata(world, -1, 6, -1, plankSlabBlock, plankSlabMeta | 8);
		setBlockAndMetadata(world, 0, 6, -1, plankSlabBlock, plankSlabMeta | 8);
		int[] j1 = {5, 7};
		k1 = j1.length;
		for (i2 = 0; i2 < k1; ++i2) {
			int j13 = j1[i2];
			setBlockAndMetadata(world, 0, j13, -1, bedBlock, 3);
			setBlockAndMetadata(world, -1, j13, -1, bedBlock, 11);
		}
		for (int j14 = 6; j14 <= 9; ++j14) {
			setBlockAndMetadata(world, 1, j14, -1, Blocks.ladder, 3);
		}
		placeChest(world, random, 1, 5, -1, 3, LOTRChestContents.EASTERLING_TOWER);
		setBlockAndMetadata(world, -1, 8, 0, Blocks.torch, 2);
		setBlockAndMetadata(world, 1, 8, 0, Blocks.torch, 1);
		spawnItemFrame(world, -2, 7, 0, 1, getEasterlingFramedItem(random));
		spawnItemFrame(world, 2, 7, 0, 3, getEasterlingFramedItem(random));
		placeWallBanner(world, 0, 9, -2, bannerType, 2);
		setBlockAndMetadata(world, -3, 14, -3, roofSlabBlock, roofSlabMeta);
		setBlockAndMetadata(world, -2, 13, -3, roofStairBlock, 5);
		setBlockAndMetadata(world, -1, 13, -3, roofStairBlock, 2);
		setBlockAndMetadata(world, 0, 13, -3, roofSlabBlock, roofSlabMeta | 8);
		setBlockAndMetadata(world, 1, 13, -3, roofStairBlock, 2);
		setBlockAndMetadata(world, 2, 13, -3, roofStairBlock, 4);
		setBlockAndMetadata(world, 3, 14, -3, roofSlabBlock, roofSlabMeta);
		setBlockAndMetadata(world, 3, 13, -2, roofStairBlock, 6);
		setBlockAndMetadata(world, 3, 13, -1, roofStairBlock, 0);
		setBlockAndMetadata(world, 3, 13, 0, roofSlabBlock, roofSlabMeta | 8);
		setBlockAndMetadata(world, 3, 13, 1, roofStairBlock, 0);
		setBlockAndMetadata(world, 3, 13, 2, roofStairBlock, 7);
		setBlockAndMetadata(world, 3, 14, 3, roofSlabBlock, roofSlabMeta);
		setBlockAndMetadata(world, 2, 13, 3, roofStairBlock, 4);
		setBlockAndMetadata(world, 1, 13, 3, roofStairBlock, 3);
		setBlockAndMetadata(world, 0, 13, 3, roofSlabBlock, roofSlabMeta | 8);
		setBlockAndMetadata(world, -1, 13, 3, roofStairBlock, 3);
		setBlockAndMetadata(world, -2, 13, 3, roofStairBlock, 5);
		setBlockAndMetadata(world, -3, 14, 3, roofSlabBlock, roofSlabMeta);
		setBlockAndMetadata(world, -3, 13, 2, roofStairBlock, 7);
		setBlockAndMetadata(world, -3, 13, 1, roofStairBlock, 1);
		setBlockAndMetadata(world, -3, 13, 0, roofSlabBlock, roofSlabMeta | 8);
		setBlockAndMetadata(world, -3, 13, -1, roofStairBlock, 1);
		setBlockAndMetadata(world, -3, 13, -2, roofStairBlock, 6);
		for (i1 = -2; i1 <= 2; ++i1) {
			setBlockAndMetadata(world, i1, 14, -2, roofStairBlock, 2);
			setBlockAndMetadata(world, i1, 14, 2, roofStairBlock, 3);
		}
		for (int k12 = -2; k12 <= 2; ++k12) {
			setBlockAndMetadata(world, -2, 14, k12, roofStairBlock, 1);
			setBlockAndMetadata(world, 2, 14, k12, roofStairBlock, 0);
		}
		for (i1 = -1; i1 <= 1; ++i1) {
			for (k1 = -1; k1 <= 1; ++k1) {
				if (i1 != 0 || k1 != 0) {
					setBlockAndMetadata(world, i1, 14, k1, roofSlabBlock, roofSlabMeta | 8);
				}
				if (i1 == 0 || k1 == 0) {
					setBlockAndMetadata(world, i1, 15, k1, roofBlock, roofMeta);
					continue;
				}
				setBlockAndMetadata(world, i1, 15, k1, roofSlabBlock, roofSlabMeta);
			}
		}
		setBlockAndMetadata(world, 0, 16, 0, roofWallBlock, roofWallMeta);
		setBlockAndMetadata(world, 0, 17, 0, roofWallBlock, roofWallMeta);
		setBlockAndMetadata(world, -2, 12, -1, Blocks.torch, 3);
		setBlockAndMetadata(world, -1, 12, -2, Blocks.torch, 2);
		setBlockAndMetadata(world, 1, 12, -2, Blocks.torch, 1);
		setBlockAndMetadata(world, 2, 12, -1, Blocks.torch, 3);
		setBlockAndMetadata(world, -2, 12, 1, Blocks.torch, 4);
		setBlockAndMetadata(world, -1, 12, 2, Blocks.torch, 2);
		setBlockAndMetadata(world, 1, 12, 2, Blocks.torch, 1);
		setBlockAndMetadata(world, 2, 12, 1, Blocks.torch, 4);
		if (frontLadder) {
			setBlockAndMetadata(world, 0, 11, -2, fenceGateBlock, 0);
			placeSideLadder(world, 0, 10, -3, 2);
		}
		if (backLadder) {
			setBlockAndMetadata(world, 0, 11, 2, fenceGateBlock, 2);
			placeSideLadder(world, 0, 10, 3, 3);
		}
		if (leftLadder) {
			setBlockAndMetadata(world, -2, 11, 0, fenceGateBlock, 3);
			placeSideLadder(world, -3, 10, 0, 5);
		}
		if (rightLadder) {
			setBlockAndMetadata(world, 2, 11, 0, fenceGateBlock, 1);
			placeSideLadder(world, 3, 10, 0, 4);
		}
		int soldiers = 1 + random.nextInt(3);
		for (int l = 0; l < soldiers; ++l) {
			LOTREntityEasterlingWarrior soldier = random.nextInt(3) == 0 ? new LOTREntityEasterlingArcher(world) : new LOTREntityEasterlingWarrior(world);
			soldier.spawnRidingHorse = false;
			spawnNPCAndSetHome(soldier, world, 0, 1, 0, 16);
		}
		LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
		respawner.setSpawnClasses(LOTREntityEasterlingWarrior.class, LOTREntityEasterlingArcher.class);
		respawner.setCheckRanges(16, -8, 8, 6);
		respawner.setSpawnRanges(3, -6, 6, 16);
		placeNPCRespawner(respawner, world, 0, 6, 0);
		return true;
	}

	public void placeSideLadder(World world, int i, int j, int k, int meta) {
		int j1 = j;
		while (!isOpaque(world, i, j1, k) && getY(j1) >= 0) {
			setBlockAndMetadata(world, i, j1, k, Blocks.ladder, meta);
			--j1;
		}
	}

	public LOTRWorldGenEasterlingTower setBackLadder() {
		backLadder = true;
		return this;
	}

	public LOTRWorldGenEasterlingTower setFrontLadder() {
		frontLadder = true;
		return this;
	}

	public LOTRWorldGenEasterlingTower setLeftLadder() {
		leftLadder = true;
		return this;
	}

	public LOTRWorldGenEasterlingTower setRightLadder() {
		rightLadder = true;
		return this;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		bedBlock = LOTRMod.strawBed;
	}
}
