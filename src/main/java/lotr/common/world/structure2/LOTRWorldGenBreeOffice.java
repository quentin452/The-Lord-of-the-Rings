package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityBreeCaptain;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenBreeOffice extends LOTRWorldGenBreeStructure {
	public LOTRWorldGenBreeOffice(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int i1;
		int k1;
		setOriginAndRotation(world, i, j, k, rotation, 8);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -9; i1 <= 9; ++i1) {
				for (k1 = -8; k1 <= 5; ++k1) {
					j1 = getTopBlock(world, i1, k1) - 1;
					if (isSurface(world, i1, j1, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -8; i1 <= 8; ++i1) {
			for (k1 = -4; k1 <= 4; ++k1) {
				for (j1 = 1; j1 <= 13; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		for (i1 = -2; i1 <= 2; ++i1) {
			for (k1 = -6; k1 <= -5; ++k1) {
				for (j1 = 1; j1 <= 11; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		for (i1 = -1; i1 <= 1; ++i1) {
			for (k1 = -8; k1 <= -7; ++k1) {
				for (j1 = 1; j1 <= 6; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("bree_office");
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("FLOOR", floorBlock, floorMeta);
		associateBlockMetaAlias("STONE_WALL", stoneWallBlock, stoneWallMeta);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("DOOR", doorBlock);
		associateBlockAlias("TRAPDOOR", trapdoorBlock);
		associateBlockMetaAlias("BEAM", beamBlock, beamMeta);
		associateBlockMetaAlias("BEAM|4", beamBlock, beamMeta | 4);
		associateBlockMetaAlias("BEAM|8", beamBlock, beamMeta | 8);
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		associateBlockMetaAlias("ROOF_SLAB", roofSlabBlock, roofSlabMeta);
		associateBlockMetaAlias("ROOF_SLAB_INV", roofSlabBlock, roofSlabMeta | 8);
		associateBlockAlias("ROOF_STAIR", roofStairBlock);
		associateBlockMetaAlias("TABLE", tableBlock, 0);
		associateBlockMetaAlias("CARPET", carpetBlock, carpetMeta);
		associateBlockMetaAlias("LEAF", Blocks.leaves, 4);
		generateStrScan(world, random, 0, 0, 0);
		placeRandomFlowerPot(world, random, 4, 2, -2);
		placeRandomFlowerPot(world, random, -2, 2, -2);
		placeRandomFlowerPot(world, random, 2, 2, -1);
		placeRandomFlowerPot(world, random, 5, 2, 2);
		placeRandomFlowerPot(world, random, 1, 6, -4);
		placeRandomFlowerPot(world, random, 6, 6, -2);
		placeRandomFlowerPot(world, random, -6, 6, 0);
		plantFlower(world, random, -4, 6, 4);
		plantFlower(world, random, 4, 6, 4);
		plantFlower(world, random, 0, 6, -6);
		setBlockAndMetadata(world, 1, 1, 1, doorBlock, 3);
		setBlockAndMetadata(world, 1, 2, 1, doorBlock, 9);
		setBlockAndMetadata(world, 2, 1, 1, doorBlock, 3);
		setBlockAndMetadata(world, 2, 2, 1, doorBlock, 8);
		placeChest(world, random, -4, 1, -2, 3, LOTRChestContents.BREE_HOUSE);
		placeChest(world, random, 6, 1, 0, 5, LOTRChestContents.BREE_HOUSE);
		placeChest(world, random, 1, 1, 2, 2, LOTRChestContents.BREE_OFFICE_ITEMS);
		placeChest(world, random, 2, 1, 2, 2, LOTRChestContents.BREE_OFFICE_ITEMS);
		if (random.nextInt(3) == 0) {
			placeChest(world, random, 0, 9, -3, 3, LOTRChestContents.BREE_OFFICE_ITEMS);
		}
		if (random.nextInt(3) == 0) {
			placeChest(world, random, 6, 9, 0, 5, LOTRChestContents.BREE_OFFICE_ITEMS);
		}
		if (random.nextInt(3) == 0) {
			placeChest(world, random, 5, 9, 1, 2, LOTRChestContents.BREE_OFFICE_ITEMS);
		}
		placeMug(world, random, -5, 2, -2, 0, LOTRFoods.BREE_DRINK);
		placeBarrel(world, random, -6, 2, -2, 3, LOTRFoods.BREE_DRINK);
		placeMug(world, random, 6, 6, 1, 3, LOTRFoods.BREE_DRINK);
		placeMug(world, random, 6, 6, 2, 3, LOTRFoods.BREE_DRINK);
		placePlate(world, random, -4, 2, 2, LOTRMod.ceramicPlateBlock, LOTRFoods.BREE);
		placePlate(world, random, -5, 2, 2, LOTRMod.plateBlock, LOTRFoods.BREE);
		placePlate(world, random, 0, 6, -4, LOTRMod.ceramicPlateBlock, LOTRFoods.BREE);
		placePlate(world, random, -6, 6, -2, LOTRMod.plateBlock, LOTRFoods.BREE);
		placePlate(world, random, 5, 6, 1, LOTRMod.ceramicPlateBlock, LOTRFoods.BREE);
		setBlockAndMetadata(world, 5, 6, 2, LOTRWorldGenBreeStructure.getRandomPieBlock(random), 0);
		setBlockAndMetadata(world, -3, 6, 2, LOTRWorldGenBreeStructure.getRandomPieBlock(random), 0);
		setBlockAndMetadata(world, 6, 1, -1, LOTRMod.strawBed, 2);
		setBlockAndMetadata(world, 6, 1, -2, LOTRMod.strawBed, 10);
		setBlockAndMetadata(world, 6, 1, 1, LOTRMod.strawBed, 0);
		setBlockAndMetadata(world, 6, 1, 2, LOTRMod.strawBed, 8);
		setBlockAndMetadata(world, -5, 5, 1, Blocks.bed, 3);
		setBlockAndMetadata(world, -6, 5, 1, Blocks.bed, 11);
		setBlockAndMetadata(world, -5, 5, 2, Blocks.bed, 3);
		setBlockAndMetadata(world, -6, 5, 2, Blocks.bed, 11);
		setBlockAndMetadata(world, -5, 9, 0, LOTRMod.strawBed, 3);
		setBlockAndMetadata(world, -6, 9, 0, LOTRMod.strawBed, 11);
		spawnItemFrame(world, 0, 2, 1, 2, new ItemStack(Items.clock));
		LOTREntityBreeCaptain sherriff = new LOTREntityBreeCaptain(world);
		spawnNPCAndSetHome(sherriff, world, 0, 4, 0, 12);
		String[] sherriffNameParts = sherriff.getNPCName().split(" ");
		if (sherriffNameParts.length < 2) {
			sherriffNameParts = new String[]{sherriffNameParts[0], ""};
		}
		placeSign(world, 0, 3, -8, Blocks.wall_sign, 2, new String[]{"Sherriff", sherriffNameParts[0], sherriffNameParts[1], ""});
		return true;
	}
}
