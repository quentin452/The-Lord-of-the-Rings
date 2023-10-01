package lotr.common.world.structure2;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityTauredainShaman;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenTauredainHouseStilts extends LOTRWorldGenTauredainHouse {
	public LOTRWorldGenTauredainHouseStilts(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int k2;
		int i1;
		int i2;
		int i12;
		if (!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
			return false;
		}
		bedBlock = LOTRMod.strawBed;
		for (i12 = -3; i12 <= 3; ++i12) {
			for (k1 = -3; k1 <= 3; ++k1) {
				for (int j1 = 3; j1 <= 7; ++j1) {
					setAir(world, i12, j1, k1);
				}
			}
		}
		placeStilt(world, -3, -3, false);
		placeStilt(world, 3, -3, false);
		placeStilt(world, -3, 3, false);
		placeStilt(world, 3, 3, false);
		placeStilt(world, 0, -4, true);
		for (i12 = -3; i12 <= 3; ++i12) {
			for (k1 = -3; k1 <= 3; ++k1) {
				i2 = Math.abs(i12);
				k2 = Math.abs(k1);
				if (i2 == 3 && k2 == 3) {
					for (int j1 = 3; j1 <= 7; ++j1) {
						setBlockAndMetadata(world, i12, j1, k1, woodBlock, woodMeta);
					}
				}
				if (i2 == 3 && k2 <= 2) {
					setBlockAndMetadata(world, i12, 3, k1, woodBlock, woodMeta | 8);
					setBlockAndMetadata(world, i12, 4, k1, plankBlock, plankMeta);
					setBlockAndMetadata(world, i12, 5, k1, plankBlock, plankMeta);
					setBlockAndMetadata(world, i12, 6, k1, woodBlock, woodMeta | 8);
				}
				if (k2 == 3 && i2 <= 2) {
					setBlockAndMetadata(world, i12, 3, k1, woodBlock, woodMeta | 4);
					setBlockAndMetadata(world, i12, 4, k1, plankBlock, plankMeta);
					setBlockAndMetadata(world, i12, 5, k1, plankBlock, plankMeta);
					setBlockAndMetadata(world, i12, 6, k1, woodBlock, woodMeta | 4);
				}
				if (i2 <= 2 && k2 <= 2) {
					setBlockAndMetadata(world, i12, 3, k1, plankBlock, plankMeta);
					if (random.nextInt(3) == 0) {
						setBlockAndMetadata(world, i12, 4, k1, LOTRMod.thatchFloor, 0);
					}
				}
				if (i12 == -3 && k2 == 1) {
					setBlockAndMetadata(world, -3, 4, k1, plankStairBlock, 1);
					setBlockAndMetadata(world, -3, 5, k1, fenceBlock, fenceMeta);
				}
				if (i12 == 3 && k2 == 1) {
					setBlockAndMetadata(world, 3, 4, k1, plankStairBlock, 0);
					setBlockAndMetadata(world, 3, 5, k1, fenceBlock, fenceMeta);
				}
				if (k1 != 3 || i2 != 1) {
					continue;
				}
				setBlockAndMetadata(world, i12, 4, k1, plankStairBlock, 3);
				setBlockAndMetadata(world, i12, 5, k1, fenceBlock, fenceMeta);
			}
		}
		int[] i13 = {3, 6};
		k1 = i13.length;
		for (i2 = 0; i2 < k1; ++i2) {
			int j1 = i13[i2];
			setBlockAndMetadata(world, -4, j1, -3, woodBlock, woodMeta | 4);
			setBlockAndMetadata(world, 4, j1, -3, woodBlock, woodMeta | 4);
			setBlockAndMetadata(world, -4, j1, 3, woodBlock, woodMeta | 4);
			setBlockAndMetadata(world, 4, j1, 3, woodBlock, woodMeta | 4);
			setBlockAndMetadata(world, -3, j1, -4, woodBlock, woodMeta | 8);
			setBlockAndMetadata(world, -3, j1, 4, woodBlock, woodMeta | 8);
			setBlockAndMetadata(world, 3, j1, -4, woodBlock, woodMeta | 8);
			setBlockAndMetadata(world, 3, j1, 4, woodBlock, woodMeta | 8);
		}
		for (i1 = -4; i1 <= 4; ++i1) {
			for (k1 = -4; k1 <= 4; ++k1) {
				i2 = Math.abs(i1);
				k2 = Math.abs(k1);
				if (i2 == 4 && k2 == 2 || k2 == 4 && i2 == 2) {
					setBlockAndMetadata(world, i1, 6, k1, fenceBlock, fenceMeta);
				}
				if (i2 == 4 && k2 <= 3 || k2 == 4 && i2 <= 3) {
					setBlockAndMetadata(world, i1, 7, k1, thatchSlabBlock, thatchSlabMeta);
				}
				if (i2 == 3 && k2 <= 2 || k2 == 3 && i2 <= 2) {
					setBlockAndMetadata(world, i1, 7, k1, thatchBlock, thatchMeta);
				}
				if (i2 == 2 && k2 <= 2 || k2 == 2 && i2 <= 2) {
					setBlockAndMetadata(world, i1, 8, k1, thatchSlabBlock, thatchSlabMeta);
					if (i2 == 2 && k2 == 2) {
						setBlockAndMetadata(world, i1, 7, k1, thatchSlabBlock, thatchSlabMeta | 8);
					} else {
						setBlockAndMetadata(world, i1, 7, k1, fenceBlock, fenceMeta);
					}
				}
				if (i2 == 1 && k2 <= 1 || k2 == 1 && i2 <= 1) {
					setBlockAndMetadata(world, i1, 8, k1, thatchSlabBlock, thatchSlabMeta | 8);
				}
				if (i2 != 0 || k2 != 0) {
					continue;
				}
				setBlockAndMetadata(world, i1, 9, k1, thatchSlabBlock, thatchSlabMeta);
			}
		}
		for (i1 = -2; i1 <= 2; ++i1) {
			setBlockAndMetadata(world, i1, 3, -4, plankBlock, plankMeta);
			if (i1 == 0) {
				continue;
			}
			setBlockAndMetadata(world, i1, 3, -5, plankSlabBlock, plankSlabMeta | 8);
			setBlockAndMetadata(world, i1, 4, -5, fenceBlock, fenceMeta);
		}
		setBlockAndMetadata(world, -2, 4, -4, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 2, 4, -4, fenceBlock, fenceMeta);
		setAir(world, 0, 4, -3);
		setAir(world, 0, 5, -3);
		setBlockAndMetadata(world, 0, 6, -2, Blocks.torch, 3);
		setBlockAndMetadata(world, -2, 6, 0, Blocks.torch, 2);
		setBlockAndMetadata(world, 0, 6, 2, Blocks.torch, 4);
		setBlockAndMetadata(world, 2, 6, 0, Blocks.torch, 1);
		setBlockAndMetadata(world, -2, 4, -2, woodBlock, woodMeta);
		placeChest(world, random, -2, 5, -2, 3, LOTRChestContents.TAUREDAIN_HOUSE);
		setBlockAndMetadata(world, 2, 4, -2, woodBlock, woodMeta);
		placeBarrel(world, random, 2, 5, -2, 3, LOTRFoods.TAUREDAIN_DRINK);
		for (int i14 : new int[]{-2, 2}) {
			setBlockAndMetadata(world, i14, 4, 1, bedBlock, 0);
			setBlockAndMetadata(world, i14, 4, 2, bedBlock, 8);
		}
		setBlockAndMetadata(world, -1, 4, 2, LOTRMod.tauredainTable, 0);
		setBlockAndMetadata(world, 0, 4, 2, woodBlock, woodMeta);
		placeMug(world, random, 0, 5, 2, 0, LOTRFoods.TAUREDAIN_DRINK);
		setBlockAndMetadata(world, 1, 4, 2, Blocks.crafting_table, 0);
		placeTauredainTorch(world, -1, 5, -5);
		placeTauredainTorch(world, 1, 5, -5);
		LOTREntityTauredainShaman shaman = new LOTREntityTauredainShaman(world);
		spawnNPCAndSetHome(shaman, world, 0, 4, 0, 2);
		return true;
	}

	@Override
	public int getOffset() {
		return 5;
	}

	public void placeStilt(World world, int i, int k, boolean ladder) {
		for (int j = 3; (j == 3 || !isOpaque(world, i, j, k)) && getY(j) >= 0; --j) {
			setBlockAndMetadata(world, i, j, k, woodBlock, woodMeta);
			setGrassToDirt(world, i, j - 1, k);
			if (!ladder) {
				continue;
			}
			setBlockAndMetadata(world, i, j, k - 1, Blocks.ladder, 2);
		}
	}
}
