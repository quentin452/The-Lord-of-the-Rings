package lotr.common.world.structure2;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenSouthronMansion extends LOTRWorldGenSouthronStructure {
	public LOTRWorldGenSouthronMansion(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		setOriginAndRotation(world, i, j, k, rotation, 9);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -5; i1 <= 11; ++i1) {
				for (int k1 = -9; k1 <= 5; ++k1) {
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
					if (maxHeight - minHeight <= 8) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i1 = -5; i1 <= 11; ++i1) {
			for (int k1 = -9; k1 <= 5; ++k1) {
				int j1;
				int i2 = Math.abs(i1);
				if (i1 >= -4 && i1 <= 10 && k1 >= -4 && k1 <= 4) {
					j1 = 0;
					while (!isOpaque(world, i1, j1, k1) && getY(j1) >= 0) {
						setBlockAndMetadata(world, i1, j1, k1, stoneBlock, stoneMeta);
						setGrassToDirt(world, i1, j1 - 1, k1);
						--j1;
					}
					for (j1 = 1; j1 <= 8; ++j1) {
						setAir(world, i1, j1, k1);
					}
				}
				if ((i2 > 2 || k1 != -9) && (i2 > 4 || k1 < -8 || k1 > -5)) {
					continue;
				}
				j1 = -1;
				while (!isOpaque(world, i1, j1, k1) && getY(j1) >= 0) {
					setBlockAndMetadata(world, i1, j1, k1, Blocks.dirt, 0);
					setGrassToDirt(world, i1, j1 - 1, k1);
					--j1;
				}
				for (j1 = 1; j1 <= 6; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("southron_mansion");
		associateBlockMetaAlias("STONE", stoneBlock, stoneMeta);
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("BRICK_SLAB", brickSlabBlock, brickSlabMeta);
		associateBlockMetaAlias("BRICK_SLAB_INV", brickSlabBlock, brickSlabMeta | 8);
		associateBlockAlias("BRICK_STAIR", brickStairBlock);
		associateBlockMetaAlias("PILLAR", pillarBlock, pillarMeta);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		associateBlockMetaAlias("BEAM", woodBeamBlock, woodBeamMeta);
		associateBlockMetaAlias("BEAM|4", woodBeamBlock, woodBeamMeta4);
		associateBlockMetaAlias("BEAM|8", woodBeamBlock, woodBeamMeta8);
		associateBlockAlias("DOOR", doorBlock);
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		associateBlockMetaAlias("ROOF_SLAB", roofSlabBlock, roofSlabMeta);
		associateBlockMetaAlias("ROOF_SLAB_INV", roofSlabBlock, roofSlabMeta | 8);
		associateBlockAlias("ROOF_STAIR", roofStairBlock);
		associateBlockMetaAlias("TABLE", tableBlock, 0);
		associateBlockAlias("CROP", cropBlock);
		generateStrScan(world, random, 0, 0, 0);
		plantFlower(world, random, -2, 1, -5);
		plantFlower(world, random, -1, 1, -5);
		plantFlower(world, random, 1, 1, -5);
		plantFlower(world, random, 2, 1, -5);
		placeWallBanner(world, 3, 3, -4, bannerType, 0);
		placeChest(world, random, -3, 1, 3, LOTRMod.chestBasket, 2, LOTRChestContents.NEAR_HARAD_HOUSE);
		placeBarrel(world, random, 6, 2, 2, 4, LOTRFoods.SOUTHRON_DRINK);
		placePlateWithCertainty(world, random, 6, 2, 1, LOTRMod.ceramicPlateBlock, LOTRFoods.SOUTHRON);
		placeMug(world, random, 6, 2, 3, 3, LOTRFoods.SOUTHRON_DRINK);
		placeWeaponRack(world, 10, 2, -2, 7, getRandomHaradWeapon(random));
		setBlockAndMetadata(world, 8, 5, -1, bedBlock, 1);
		setBlockAndMetadata(world, 9, 5, -1, bedBlock, 9);
		setBlockAndMetadata(world, 8, 5, 1, bedBlock, 1);
		setBlockAndMetadata(world, 9, 5, 1, bedBlock, 9);
		placeFlowerPot(world, 4, 2, 1, getRandomFlower(world, random));
		placeFlowerPot(world, 9, 6, -3, new ItemStack(LOTRMod.sapling3, 1, 2));
		placeFlowerPot(world, 9, 6, 3, new ItemStack(LOTRMod.sapling3, 1, 2));
		int numHaradrim = 2;
		for (int l = 0; l < numHaradrim; ++l) {
			LOTREntityNearHaradrimBase haradrim = createHaradrim(world);
			spawnNPCAndSetHome(haradrim, world, 0, 1, 0, 16);
		}
		return true;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		bedBlock = Blocks.bed;
	}
}
