package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronBarracks extends LOTRWorldGenSouthronStructure {
	public LOTRWorldGenSouthronBarracks(boolean flag) {
		super(flag);
	}

	public LOTREntityNearHaradrimBase createWarrior(World world, Random random) {
		return random.nextInt(3) == 0 ? new LOTREntityNearHaradrimArcher(world) : new LOTREntityNearHaradrimWarrior(world);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		setOriginAndRotation(world, i, j, k, rotation, 8);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -6; i1 <= 5; ++i1) {
				for (int k12 = -8; k12 <= 8; ++k12) {
					int j1 = getTopBlock(world, i1, k12) - 1;
					if (!isSurface(world, i1, j1, k12)) {
						return false;
					}
					if (j1 < minHeight) {
						minHeight = j1;
					}
					if (j1 > maxHeight) {
						maxHeight = j1;
					}
					if (maxHeight - minHeight <= 8) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i1 = -5; i1 <= 4; ++i1) {
			for (int k13 = -7; k13 <= 7; ++k13) {
				for (int j1 = 1; j1 <= 5; ++j1) {
					setAir(world, i1, j1, k13);
				}
			}
		}
		loadStrScan("southron_barracks");
		associateBlockMetaAlias("STONE", stoneBlock, stoneMeta);
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockAlias("BRICK_STAIR", brickStairBlock);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockMetaAlias("BEAM", woodBeamBlock, woodBeamMeta);
		associateBlockMetaAlias("BEAM|4", woodBeamBlock, woodBeamMeta4);
		associateBlockMetaAlias("BEAM|8", woodBeamBlock, woodBeamMeta8);
		associateBlockMetaAlias("ROOF_SLAB", roofSlabBlock, roofSlabMeta);
		associateBlockMetaAlias("ROOF_SLAB_INV", roofSlabBlock, roofSlabMeta | 8);
		generateStrScan(world, random, 0, 0, 0);
		for (k1 = -4; k1 <= 4; k1 += 2) {
			if (random.nextBoolean()) {
				placeChest(world, random, -4, 1, k1, LOTRMod.chestBasket, 4, LOTRChestContents.NEAR_HARAD_TOWER, 1 + random.nextInt(2));
			} else {
				setBlockAndMetadata(world, -4, 1, k1, LOTRMod.chestBasket, 4);
			}
			if (random.nextBoolean()) {
				placeChest(world, random, 3, 1, k1, LOTRMod.chestBasket, 5, LOTRChestContents.NEAR_HARAD_TOWER, 1 + random.nextInt(2));
				continue;
			}
			setBlockAndMetadata(world, 3, 1, k1, LOTRMod.chestBasket, 5);
		}
		for (k1 = -5; k1 <= 5; k1 += 2) {
			for (int j1 = 1; j1 <= 2; ++j1) {
				setBlockAndMetadata(world, -3, j1, k1, bedBlock, 3);
				setBlockAndMetadata(world, -4, j1, k1, bedBlock, 11);
				setBlockAndMetadata(world, 2, j1, k1, bedBlock, 1);
				setBlockAndMetadata(world, 3, j1, k1, bedBlock, 9);
			}
		}
		int warriors = 2 + random.nextInt(3);
		for (int l = 0; l < warriors; ++l) {
			LOTREntityNearHaradrimBase haradrim = createWarrior(world, random);
			spawnNPCAndSetHome(haradrim, world, random.nextBoolean() ? -1 : 0, 1, 0, 16);
		}
		return true;
	}
}
