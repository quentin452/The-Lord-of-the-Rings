package lotr.common.world.structure2;

import java.util.Random;

import com.google.common.math.IntMath;

import lotr.common.*;
import lotr.common.entity.*;
import lotr.common.entity.npc.LOTREntityGondorMan;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGondorWatchtower extends LOTRWorldGenGondorStructure {
	public LOTRWorldGenGondorWatchtower(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int k2;
		int k1;
		int i1;
		int j1;
		int j12;
		int k12;
		int i2;
		setOriginAndRotation(world, i, j, k, rotation, 4);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i12 = -4; i12 <= 4; ++i12) {
				for (int k13 = -4; k13 <= 4; ++k13) {
					j12 = getTopBlock(world, i12, k13) - 1;
					if (!isSurface(world, i12, j12, k13)) {
						return false;
					}
					if (j12 < minHeight) {
						minHeight = j12;
					}
					if (j12 > maxHeight) {
						maxHeight = j12;
					}
					if (maxHeight - minHeight <= 8) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			for (k1 = -3; k1 <= 3; ++k1) {
				i2 = Math.abs(i1);
				k2 = Math.abs(k1);
				if (i2 == 3 && k2 == 3) {
					continue;
				}
				j12 = 0;
				while (!isOpaque(world, i1, j12, k1) && getY(j12) >= 0) {
					setBlockAndMetadata(world, i1, j12, k1, brickBlock, brickMeta);
					setGrassToDirt(world, i1, j12 - 1, k1);
					--j12;
				}
				if (i2 == 3 && k2 == 2 || k2 == 3 && i2 == 2) {
					for (j12 = 1; j12 <= 9; ++j12) {
						setBlockAndMetadata(world, i1, j12, k1, pillarBlock, pillarMeta);
					}
					continue;
				}
				if (i2 == 3 || k2 == 3) {
					for (j12 = 1; j12 <= 9; ++j12) {
						setBlockAndMetadata(world, i1, j12, k1, brickBlock, brickMeta);
					}
					continue;
				}
				setBlockAndMetadata(world, i1, 0, k1, brickBlock, brickMeta);
				for (j12 = 1; j12 <= 5; ++j12) {
					setAir(world, i1, j12, k1);
				}
				setBlockAndMetadata(world, i1, 6, k1, plankBlock, plankMeta);
				for (j12 = 7; j12 <= 9; ++j12) {
					setAir(world, i1, j12, k1);
				}
			}
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			for (k1 = -3; k1 <= 3; ++k1) {
				setBlockAndMetadata(world, i1, 10, k1, brickBlock, brickMeta);
			}
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			setBlockAndMetadata(world, i1, 10, -4, brickStairBlock, 6);
			setBlockAndMetadata(world, i1, 10, 4, brickStairBlock, 7);
		}
		for (k12 = -2; k12 <= 2; ++k12) {
			setBlockAndMetadata(world, -4, 10, k12, brickStairBlock, 5);
			setBlockAndMetadata(world, 4, 10, k12, brickStairBlock, 4);
		}
		setBlockAndMetadata(world, -3, 10, -3, brickStairBlock, 5);
		setBlockAndMetadata(world, -4, 10, -3, brickStairBlock, 6);
		setBlockAndMetadata(world, 4, 10, -3, brickStairBlock, 6);
		setBlockAndMetadata(world, 3, 10, -3, brickStairBlock, 4);
		setBlockAndMetadata(world, -3, 10, 3, brickStairBlock, 5);
		setBlockAndMetadata(world, -4, 10, 3, brickStairBlock, 7);
		setBlockAndMetadata(world, 4, 10, 3, brickStairBlock, 7);
		setBlockAndMetadata(world, 3, 10, 3, brickStairBlock, 4);
		setBlockAndMetadata(world, 0, 0, -3, brickBlock, brickMeta);
		setBlockAndMetadata(world, 0, 1, -3, doorBlock, 1);
		setBlockAndMetadata(world, 0, 2, -3, doorBlock, 8);
		for (j1 = 1; j1 <= 2; ++j1) {
			setBlockAndMetadata(world, -1, j1, -3, LOTRMod.brick, 5);
			setBlockAndMetadata(world, 1, j1, -3, LOTRMod.brick, 5);
		}
		setBlockAndMetadata(world, -1, 3, -4, Blocks.torch, 4);
		setBlockAndMetadata(world, 1, 3, -4, Blocks.torch, 4);
		setBlockAndMetadata(world, 0, 6, -3, LOTRMod.brick, 5);
		setBlockAndMetadata(world, 0, 6, 3, LOTRMod.brick, 5);
		setBlockAndMetadata(world, -3, 6, 0, LOTRMod.brick, 5);
		setBlockAndMetadata(world, 3, 6, 0, LOTRMod.brick, 5);
		placeWallBanner(world, 0, 5, -3, bannerType, 2);
		for (j1 = 1; j1 <= 9; ++j1) {
			setBlockAndMetadata(world, 0, j1, 2, Blocks.ladder, 2);
		}
		setBlockAndMetadata(world, 0, 10, 2, trapdoorBlock, 9);
		for (k12 = -2; k12 <= 2; ++k12) {
			if (IntMath.mod(k12, 2) == 0) {
				placeChest(world, random, -2, 1, k12, 4, LOTRChestContents.GONDOR_FORTRESS_DRINKS);
				placeChest(world, random, 2, 1, k12, 5, LOTRChestContents.GONDOR_FORTRESS_DRINKS);
				continue;
			}
			setBlockAndMetadata(world, -1, 1, k12, bedBlock, 3);
			setBlockAndMetadata(world, -2, 1, k12, bedBlock, 11);
			setBlockAndMetadata(world, 1, 1, k12, bedBlock, 1);
			setBlockAndMetadata(world, 2, 1, k12, bedBlock, 9);
		}
		setBlockAndMetadata(world, -2, 3, 0, Blocks.torch, 2);
		setBlockAndMetadata(world, 2, 3, 0, Blocks.torch, 1);
		setBlockAndMetadata(world, 0, 5, 0, LOTRMod.chandelier, 1);
		placeChest(world, random, -2, 7, -2, LOTRMod.chestLebethron, 4, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES);
		setBlockAndMetadata(world, -2, 7, 0, LOTRMod.armorStand, 3);
		setBlockAndMetadata(world, -2, 8, 0, LOTRMod.armorStand, 7);
		setBlockAndMetadata(world, -2, 7, 2, Blocks.anvil, 0);
		spawnItemFrame(world, -3, 8, -1, 1, getGondorFramedItem(random));
		spawnItemFrame(world, -3, 8, 1, 1, getGondorFramedItem(random));
		setBlockAndMetadata(world, 2, 7, -2, tableBlock, 0);
		setBlockAndMetadata(world, 2, 7, -1, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, 2, 7, 0, Blocks.stone_slab, 8);
		setBlockAndMetadata(world, 2, 7, 1, Blocks.stone_slab, 8);
		setBlockAndMetadata(world, 2, 7, 2, Blocks.stone_slab, 8);
		placeMug(world, random, 2, 8, 0, 1, LOTRFoods.GONDOR_DRINK);
		placePlateWithCertainty(world, random, 2, 8, 1, plateBlock, LOTRFoods.GONDOR);
		placeBarrel(world, random, 2, 8, 2, 5, LOTRFoods.GONDOR_DRINK);
		setBlockAndMetadata(world, 0, 9, 0, LOTRMod.chandelier, 1);
		for (i1 = -4; i1 <= 4; ++i1) {
			for (k1 = -4; k1 <= 4; ++k1) {
				i2 = Math.abs(i1);
				k2 = Math.abs(k1);
				if (i2 == 4 && k2 == 4) {
					setBlockAndMetadata(world, i1, 11, k1, brickBlock, brickMeta);
					setBlockAndMetadata(world, i1, 12, k1, brickBlock, brickMeta);
					continue;
				}
				if (i2 != 4 && k2 != 4) {
					continue;
				}
				if (IntMath.mod(i1 + k1, 2) == 1) {
					setBlockAndMetadata(world, i1, 11, k1, brickWallBlock, brickWallMeta);
					continue;
				}
				setBlockAndMetadata(world, i1, 11, k1, brickBlock, brickMeta);
				setBlockAndMetadata(world, i1, 12, k1, brickSlabBlock, brickSlabMeta);
			}
		}
		setBlockAndMetadata(world, 0, 11, 0, pillarBlock, pillarMeta);
		setBlockAndMetadata(world, 0, 12, 0, pillarBlock, pillarMeta);
		setBlockAndMetadata(world, 0, 13, 0, LOTRMod.brick, 5);
		placeBanner(world, 0, 14, 0, bannerType, 2);
		setBlockAndMetadata(world, 0, 11, -3, Blocks.torch, 3);
		setBlockAndMetadata(world, 0, 11, 3, Blocks.torch, 4);
		setBlockAndMetadata(world, -3, 11, 0, Blocks.torch, 2);
		setBlockAndMetadata(world, 3, 11, 0, Blocks.torch, 1);
		Class[] levyClasses = strFief.getLevyClasses();
		LOTREntityGondorMan soldier = (LOTREntityGondorMan) LOTREntities.createEntityByClass(levyClasses[0], world);
		soldier.spawnRidingHorse = false;
		spawnNPCAndSetHome(soldier, world, 0, 7, 0, 16);
		soldier = (LOTREntityGondorMan) LOTREntities.createEntityByClass(levyClasses[0], world);
		soldier.spawnRidingHorse = false;
		spawnNPCAndSetHome(soldier, world, 0, 1, 0, 16);
		int levymen = 1 + random.nextInt(3);
		for (int l = 0; l < levymen; ++l) {
			Class entityClass = levyClasses[0];
			if (random.nextInt(3) == 0) {
				entityClass = levyClasses[1];
			}
			LOTREntityGondorMan levyman = (LOTREntityGondorMan) LOTREntities.createEntityByClass(entityClass, world);
			levyman.spawnRidingHorse = false;
			spawnNPCAndSetHome(levyman, world, 0, 11, 1, 16);
		}
		LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
		respawner.setSpawnClasses(levyClasses[0], levyClasses[1]);
		respawner.setCheckRanges(16, -12, 8, 6);
		respawner.setSpawnRanges(3, -6, 6, 16);
		placeNPCRespawner(respawner, world, 0, 6, 0);
		return true;
	}
}
