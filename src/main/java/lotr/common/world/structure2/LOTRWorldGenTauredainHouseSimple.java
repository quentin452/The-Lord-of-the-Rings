package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityTauredain;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenTauredainHouseSimple extends LOTRWorldGenTauredainHouse {
	public LOTRWorldGenTauredainHouseSimple(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		setOriginAndRotation(world, i, j, k, rotation, 3);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			int range = 4;
			for (int i1 = -range; i1 <= range; ++i1) {
				for (int k1 = -range; k1 <= range; ++k1) {
					int j1 = getTopBlock(world, i1, k1) - 1;
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
		for (int i1 = -3; i1 <= 3; ++i1) {
			for (int k1 = -3; k1 <= 3; ++k1) {
				for (int j1 = 1; j1 <= 6; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("taurethrim_house");
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockAlias("BRICK_STAIR", brickStairBlock);
		associateBlockMetaAlias("BRICK_WALL", brickWallBlock, brickWallMeta);
		associateBlockMetaAlias("WOOD|4", woodBlock, woodMeta | 4);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("DOOR", doorBlock);
		associateBlockMetaAlias("ROOF", thatchBlock, thatchMeta);
		associateBlockMetaAlias("ROOF_SLAB", thatchSlabBlock, thatchSlabMeta);
		associateBlockMetaAlias("ROOF_SLAB_INV", thatchSlabBlock, thatchSlabMeta | 8);
		associateBlockAlias("ROOF_STAIR", thatchStairBlock);
		associateBlockMetaAlias("WALL", Blocks.stained_hardened_clay, 12);
		addBlockMetaAliasOption("GROUND", 10, floorBlock, floorMeta);
		addBlockMetaAliasOption("GROUND", 10, LOTRMod.mud, 0);
		generateStrScan(world, random, 0, 0, 0);
		setBlockAndMetadata(world, -2, 1, 1, bedBlock, 0);
		setBlockAndMetadata(world, -2, 1, 2, bedBlock, 8);
		setBlockAndMetadata(world, 2, 1, 1, bedBlock, 0);
		setBlockAndMetadata(world, 2, 1, 2, bedBlock, 8);
		placeChest(world, random, 0, 1, 2, LOTRMod.chestBasket, 2, LOTRChestContents.TAUREDAIN_HOUSE);
		placeTauredainFlowerPot(world, -2, 2, 0, random);
		placeTauredainFlowerPot(world, -1, 2, 2, random);
		placeTauredainFlowerPot(world, 0, 4, -2, random);
		placeTauredainFlowerPot(world, 0, 4, 2, random);
		placePlateWithCertainty(world, random, 1, 2, 2, LOTRMod.woodPlateBlock, LOTRFoods.TAUREDAIN);
		placeMug(world, random, 2, 2, 0, 3, LOTRFoods.TAUREDAIN_DRINK);
		int men = 1 + random.nextInt(2);
		for (int l = 0; l < men; ++l) {
			LOTREntityTauredain tauredain = new LOTREntityTauredain(world);
			spawnNPCAndSetHome(tauredain, world, 0, 1, 0, 8);
		}
		return true;
	}

	@Override
	public int getOffset() {
		return 4;
	}
}
