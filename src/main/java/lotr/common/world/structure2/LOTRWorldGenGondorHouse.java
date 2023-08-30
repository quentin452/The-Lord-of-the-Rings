package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityGondorMan;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGondorHouse extends LOTRWorldGenGondorStructure {
	public LOTRWorldGenGondorHouse(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int i1;
		int j1;
		int i2;
		setOriginAndRotation(world, i, j, k, rotation, 5);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i12 = -4; i12 <= 4; ++i12) {
				for (int k12 = -5; k12 <= 5; ++k12) {
					j1 = getTopBlock(world, i12, k12) - 1;
					if (!isSurface(world, i12, j1, k12)) {
						return false;
					}
					if (j1 < minHeight) {
						minHeight = j1;
					}
					if (j1 > maxHeight) {
						maxHeight = j1;
					}
					if (maxHeight - minHeight <= 5) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i13 = -4; i13 <= 4; ++i13) {
			for (k1 = -4; k1 <= 4; ++k1) {
				i2 = Math.abs(i13);
				int k2 = Math.abs(k1);
				if (i2 == 4 && k2 == 4) {
					continue;
				}
				if (i2 == 3 && k2 == 4 || k2 == 3 && i2 == 4) {
					for (j1 = 4; (j1 >= 0 || !isOpaque(world, i13, j1, k1)) && getY(j1) >= 0; --j1) {
						setBlockAndMetadata(world, i13, j1, k1, woodBeamBlock, woodBeamMeta);
						setGrassToDirt(world, i13, j1 - 1, k1);
					}
					continue;
				}
				if (i2 == 4 || k2 == 4) {
					for (j1 = 0; (j1 >= 0 || !isOpaque(world, i13, j1, k1)) && getY(j1) >= 0; --j1) {
						setBlockAndMetadata(world, i13, j1, k1, rockBlock, rockMeta);
						setGrassToDirt(world, i13, j1 - 1, k1);
					}
					for (j1 = 1; j1 <= 3; ++j1) {
						setBlockAndMetadata(world, i13, j1, k1, wallBlock, wallMeta);
					}
					if (k2 != 4) {
						continue;
					}
					setBlockAndMetadata(world, i13, 4, k1, woodBeamBlock, woodBeamMeta | 4);
					continue;
				}
				for (j1 = 0; (j1 >= 0 || !isOpaque(world, i13, j1, k1)) && getY(j1) >= 0; --j1) {
					setBlockAndMetadata(world, i13, j1, k1, rockBlock, rockMeta);
					setGrassToDirt(world, i13, j1 - 1, k1);
				}
				for (j1 = 1; j1 <= 5; ++j1) {
					setAir(world, i13, j1, k1);
				}
			}
		}
		int[] i13 = {-4, 4};
		k1 = i13.length;
		for (i2 = 0; i2 < k1; ++i2) {
			int i14 = i13[i2];
			for (j1 = 1; j1 <= 4; ++j1) {
				setBlockAndMetadata(world, i14, j1, 0, woodBeamBlock, woodBeamMeta);
			}
			setBlockAndMetadata(world, i14, 2, -2, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, i14, 2, 2, fenceBlock, fenceMeta);
		}
		setBlockAndMetadata(world, -2, 2, -4, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 2, 2, -4, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 0, 0, -4, rockBlock, rockMeta);
		setBlockAndMetadata(world, 0, 1, -4, doorBlock, 1);
		setBlockAndMetadata(world, 0, 2, -4, doorBlock, 8);
		for (int k13 = -5; k13 <= 5; ++k13) {
			setBlockAndMetadata(world, -4, 4, k13, roofStairBlock, 1);
			setBlockAndMetadata(world, -3, 5, k13, roofSlabBlock, roofSlabMeta);
			setBlockAndMetadata(world, -2, 5, k13, roofBlock, roofMeta);
			setBlockAndMetadata(world, -1, 6, k13, roofStairBlock, 1);
			setBlockAndMetadata(world, 0, 6, k13, woodBeamBlock, woodBeamMeta | 8);
			setBlockAndMetadata(world, 0, 7, k13, roofSlabBlock, roofSlabMeta);
			setBlockAndMetadata(world, 1, 6, k13, roofStairBlock, 0);
			setBlockAndMetadata(world, 2, 5, k13, roofBlock, roofMeta);
			setBlockAndMetadata(world, 3, 5, k13, roofSlabBlock, roofSlabMeta);
			setBlockAndMetadata(world, 4, 4, k13, roofStairBlock, 0);
			int k2 = Math.abs(k13);
			if (k2 == 5) {
				setBlockAndMetadata(world, -3, 4, k13, roofStairBlock, 4);
				setBlockAndMetadata(world, -1, 5, k13, roofStairBlock, 4);
				setBlockAndMetadata(world, 1, 5, k13, roofStairBlock, 5);
				setBlockAndMetadata(world, 3, 4, k13, roofStairBlock, 5);
				continue;
			}
			if (k2 != 4) {
				continue;
			}
			setBlockAndMetadata(world, -1, 5, k13, wallBlock, wallMeta);
			setBlockAndMetadata(world, 0, 5, k13, wallBlock, wallMeta);
			setBlockAndMetadata(world, 1, 5, k13, wallBlock, wallMeta);
		}
		for (i1 = -1; i1 <= 1; ++i1) {
			for (k1 = 3; k1 <= 5; ++k1) {
				for (int j12 = 7; (j12 >= 0 || !isOpaque(world, i1, j12, k1)) && getY(j12) >= 0; --j12) {
					setBlockAndMetadata(world, i1, j12, k1, brickBlock, brickMeta);
					setGrassToDirt(world, i1, j12 - 1, k1);
				}
			}
			setBlockAndMetadata(world, i1, 8, 3, brickStairBlock, 2);
			setBlockAndMetadata(world, i1, 8, 5, brickStairBlock, 3);
		}
		setBlockAndMetadata(world, -1, 8, 4, brickStairBlock, 1);
		setBlockAndMetadata(world, 0, 8, 4, brickBlock, brickMeta);
		setBlockAndMetadata(world, 1, 8, 4, brickStairBlock, 0);
		setBlockAndMetadata(world, 0, 9, 4, brickWallBlock, brickWallMeta);
		for (i1 = -3; i1 <= -2; ++i1) {
			setBlockAndMetadata(world, i1, 1, -3, plankStairBlock, 3);
			setBlockAndMetadata(world, i1, 1, -2, plankBlock, plankMeta);
			setBlockAndMetadata(world, i1, 1, -1, plankStairBlock, 2);
			if (random.nextBoolean()) {
				placePlateWithCertainty(world, random, i1, 2, -2, plateBlock, LOTRFoods.GONDOR);
				continue;
			}
			int drinkMeta = random.nextInt(4);
			placeMug(world, random, i1, 2, -2, drinkMeta, LOTRFoods.GONDOR_DRINK);
		}
		setBlockAndMetadata(world, 2, 1, -3, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, 3, 1, -3, plankBlock, plankMeta);
		placePlateWithCertainty(world, random, 3, 2, -3, plateBlock, LOTRFoods.GONDOR);
		setBlockAndMetadata(world, 3, 1, -2, Blocks.cauldron, 3);
		setBlockAndMetadata(world, 3, 1, -1, tableBlock, 0);
		setBlockAndMetadata(world, -2, 1, 1, bedBlock, 3);
		setBlockAndMetadata(world, -3, 1, 1, bedBlock, 11);
		setBlockAndMetadata(world, 2, 1, 1, bedBlock, 1);
		setBlockAndMetadata(world, 3, 1, 1, bedBlock, 9);
		setBlockAndMetadata(world, -3, 1, 3, plankBlock, plankMeta);
		setBlockAndMetadata(world, 3, 1, 3, plankBlock, plankMeta);
		placeChest(world, random, -2, 1, 3, 2, chestContents);
		placeChest(world, random, 2, 1, 3, 2, chestContents);
		setBlockAndMetadata(world, 0, 1, 3, barsBlock, 0);
		setBlockAndMetadata(world, 0, 2, 3, Blocks.furnace, 2);
		setBlockAndMetadata(world, 0, 0, 4, LOTRMod.hearth, 0);
		setBlockAndMetadata(world, 0, 1, 4, Blocks.fire, 0);
		for (int j13 = 2; j13 <= 7; ++j13) {
			setAir(world, 0, j13, 4);
		}
		spawnItemFrame(world, 0, 3, 3, 2, getGondorFramedItem(random));
		int men = 1 + random.nextInt(2);
		for (int l = 0; l < men; ++l) {
			LOTREntityGondorMan gondorMan = new LOTREntityGondorMan(world);
			spawnNPCAndSetHome(gondorMan, world, 0, 1, 0, 16);
		}
		return true;
	}
}
