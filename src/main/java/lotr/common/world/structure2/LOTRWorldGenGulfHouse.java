package lotr.common.world.structure2;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityGulfHaradrim;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenGulfHouse extends LOTRWorldGenGulfStructure {
	public LOTRWorldGenGulfHouse(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 8);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -8; i1 <= 8; ++i1) {
				for (int k1 = -8; k1 <= 8; ++k1) {
					j1 = getTopBlock(world, i1, k1) - 1;
					if (!isSurface(world, i1, j1, k1)) {
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
		for (int i1 = -8; i1 <= 8; ++i1) {
			for (int k1 = -8; k1 <= 8; ++k1) {
				int k2;
				int i2 = Math.abs(i1);
				k2 = Math.abs(k1);
				if (i2 * i2 + k2 * k2 >= 64) {
					continue;
				}
				for (j1 = 1; j1 <= 5; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("gulf_house");
		associateBlockMetaAlias("WOOD", woodBlock, woodMeta);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("DOOR", doorBlock);
		associateBlockMetaAlias("BEAM", beamBlock, beamMeta);
		associateBlockMetaAlias("PLANK2", plank2Block, plank2Meta);
		associateBlockMetaAlias("PLANK2_SLAB", plank2SlabBlock, plank2SlabMeta);
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		associateBlockMetaAlias("ROOF_SLAB", roofSlabBlock, roofSlabMeta);
		associateBlockMetaAlias("ROOF_SLAB_INV", roofSlabBlock, roofSlabMeta | 8);
		associateBlockAlias("ROOF_STAIR", roofStairBlock);
		generateStrScan(world, random, 0, 0, 0);
		setBlockAndMetadata(world, 0, 1, 5, bedBlock, 0);
		setBlockAndMetadata(world, 0, 1, 6, bedBlock, 8);
		placeChest(world, random, 6, 1, 0, LOTRMod.chestBasket, 5, LOTRChestContents.GULF_HOUSE);
		for (int k1 : new int[]{-2, 0, 2}) {
			int i1 = -6;
			int j12 = 2;
			if (random.nextBoolean()) {
				placePlate(world, random, i1, j12, k1, LOTRMod.woodPlateBlock, LOTRFoods.GULF_HARAD);
				continue;
			}
			placeMug(world, random, i1, j12, k1, 3, LOTRFoods.GULF_HARAD_DRINK);
		}
		int men = 1 + random.nextInt(2);
		for (int l = 0; l < men; ++l) {
			LOTREntityGulfHaradrim gulfman = new LOTREntityGulfHaradrim(world);
			spawnNPCAndSetHome(gulfman, world, 0, 0, 0, 16);
		}
		return true;
	}
}
