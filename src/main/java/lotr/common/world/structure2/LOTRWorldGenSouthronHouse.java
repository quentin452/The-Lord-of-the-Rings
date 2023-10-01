package lotr.common.world.structure2;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenSouthronHouse extends LOTRWorldGenSouthronStructure {
	public LOTRWorldGenSouthronHouse(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int i1;
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 5);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i12 = -3; i12 <= 3; ++i12) {
				for (int k12 = -5; k12 <= 6; ++k12) {
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
					if (maxHeight - minHeight <= 6) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			for (k1 = -6; k1 <= 6; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				if (i2 <= 2 && k2 <= 4) {
					j1 = 0;
					while (!isOpaque(world, i1, j1, k1) && getY(j1) >= 0) {
						setBlockAndMetadata(world, i1, j1, k1, stoneBlock, stoneMeta);
						setGrassToDirt(world, i1, j1 - 1, k1);
						--j1;
					}
					for (j1 = 1; j1 <= 7; ++j1) {
						setAir(world, i1, j1, k1);
					}
				}
				if ((i2 > 2 || k1 != 5) && (i1 < -3 || i1 > 2 || k1 != 6)) {
					continue;
				}
				j1 = 0;
				while (!isOpaque(world, i1, j1, k1) && getY(j1) >= 0) {
					setBlockAndMetadata(world, i1, j1, k1, brickBlock, brickMeta);
					setGrassToDirt(world, i1, j1 - 1, k1);
					--j1;
				}
				for (j1 = 1; j1 <= 7; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("southron_house");
		associateBlockMetaAlias("STONE", stoneBlock, stoneMeta);
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("BRICK_SLAB", brickSlabBlock, brickSlabMeta);
		associateBlockMetaAlias("BRICK_SLAB_INV", brickSlabBlock, brickSlabMeta | 8);
		associateBlockAlias("BRICK_STAIR", brickStairBlock);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockMetaAlias("BEAM", woodBeamBlock, woodBeamMeta);
		associateBlockMetaAlias("BEAM|4", woodBeamBlock, woodBeamMeta4);
		associateBlockMetaAlias("BEAM|8", woodBeamBlock, woodBeamMeta8);
		associateBlockAlias("DOOR", doorBlock);
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		associateBlockMetaAlias("ROOF_SLAB", roofSlabBlock, roofSlabMeta);
		associateBlockMetaAlias("ROOF_SLAB_INV", roofSlabBlock, roofSlabMeta | 8);
		associateBlockAlias("ROOF_STAIR", roofStairBlock);
		associateBlockMetaAlias("TABLE", tableBlock, 0);
		generateStrScan(world, random, 0, 0, 0);
		if (!isOpaque(world, 0, 0, 7) || isOpaque(world, 0, 1, 7)) {
			for (i1 = -4; i1 <= 2; ++i1) {
				for (k1 = 6; k1 <= 7; ++k1) {
					int j12;
					if (k1 != 7 && (i1 != -4 || k1 != 6)) {
						continue;
					}
					for (j12 = 0; (j12 >= 0 || !isOpaque(world, i1, j12, k1)) && getY(j12) >= 0; --j12) {
						setBlockAndMetadata(world, i1, j12, k1, brickBlock, brickMeta);
						setGrassToDirt(world, i1, j12 - 1, k1);
					}
					for (j12 = 1; j12 <= 3; ++j12) {
						setAir(world, i1, j12, k1);
					}
				}
			}
		}
		placeWallBanner(world, -2, 3, 0, bannerType, 1);
		for (int k12 : new int[]{-2, 0, 2}) {
			int i13 = -1;
			int j13 = 2;
			if (random.nextBoolean()) {
				placePlate(world, random, i13, j13, k12, LOTRMod.woodPlateBlock, LOTRFoods.SOUTHRON);
				continue;
			}
			placeMug(world, random, i13, j13, k12, 1, LOTRFoods.SOUTHRON_DRINK);
		}
		setBlockAndMetadata(world, -1, 5, -2, bedBlock, 2);
		setBlockAndMetadata(world, -1, 5, -3, bedBlock, 10);
		setBlockAndMetadata(world, 1, 5, -2, bedBlock, 2);
		setBlockAndMetadata(world, 1, 5, -3, bedBlock, 10);
		placeFlowerPot(world, 0, 6, -3, getRandomFlower(world, random));
		placeChest(world, random, -1, 5, 3, LOTRMod.chestBasket, 2, LOTRChestContents.NEAR_HARAD_HOUSE);
		int men = 1 + random.nextInt(2);
		for (int l = 0; l < men; ++l) {
			LOTREntityNearHaradrimBase haradrim = createHaradrim(world);
			spawnNPCAndSetHome(haradrim, world, 0, 1, 0, 16);
		}
		return true;
	}
}
