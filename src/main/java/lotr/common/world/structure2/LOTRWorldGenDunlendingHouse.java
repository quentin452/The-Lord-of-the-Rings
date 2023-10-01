package lotr.common.world.structure2;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityDunlending;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenDunlendingHouse extends LOTRWorldGenDunlandStructure {
	public LOTRWorldGenDunlendingHouse(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 6);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -6; i1 <= 6; ++i1) {
				for (int k1 = -7; k1 <= 7; ++k1) {
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
					if (maxHeight - minHeight <= 8) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i1 = -4; i1 <= 4; ++i1) {
			for (int k1 = -6; k1 <= 5; ++k1) {
				if (k1 >= -5) {
					j1 = -1;
					while (!isOpaque(world, i1, j1, k1) && getY(j1) >= 0) {
						setBlockAndMetadata(world, i1, j1, k1, floorBlock, floorMeta);
						setGrassToDirt(world, i1, j1 - 1, k1);
						--j1;
					}
				}
				for (j1 = 1; j1 <= 6; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("dunland_house");
		associateBlockMetaAlias("FLOOR", floorBlock, floorMeta);
		associateBlockMetaAlias("WOOD", woodBlock, woodMeta);
		associateBlockMetaAlias("WOOD|8", woodBlock, woodMeta | 8);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("DOOR", doorBlock);
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		associateBlockMetaAlias("ROOF_SLAB", roofSlabBlock, roofSlabMeta);
		associateBlockMetaAlias("ROOF_SLAB_INV", roofSlabBlock, roofSlabMeta | 8);
		associateBlockAlias("ROOF_STAIR", roofStairBlock);
		associateBlockMetaAlias("BARS", barsBlock, barsMeta);
		generateStrScan(world, random, 0, 1, 0);
		setBlockAndMetadata(world, 0, 1, 3, bedBlock, 0);
		setBlockAndMetadata(world, 0, 1, 4, bedBlock, 8);
		placeChest(world, random, -2, 1, 4, LOTRMod.chestBasket, 2, LOTRChestContents.DUNLENDING_HOUSE);
		placeChest(world, random, 2, 1, 4, LOTRMod.chestBasket, 2, LOTRChestContents.DUNLENDING_HOUSE);
		placeBarrel(world, random, -3, 2, -3, 4, LOTRFoods.DUNLENDING_DRINK);
		placePlate(world, random, -3, 2, -2, LOTRMod.woodPlateBlock, LOTRFoods.DUNLENDING);
		placePlate(world, random, -3, 2, -1, LOTRMod.woodPlateBlock, LOTRFoods.DUNLENDING);
		placeMug(world, random, 3, 2, -3, 1, LOTRFoods.DUNLENDING_DRINK);
		placePlate(world, random, 3, 2, -2, LOTRMod.woodPlateBlock, LOTRFoods.DUNLENDING);
		placePlate(world, random, 3, 2, -1, LOTRMod.woodPlateBlock, LOTRFoods.DUNLENDING);
		placeFlowerPot(world, -3, 2, 1, getRandomFlower(world, random));
		placeWeaponRack(world, 0, 3, -4, 4, getRandomDunlandWeapon(random));
		placeDunlandItemFrame(world, random, -2, 2, -5, 0);
		placeDunlandItemFrame(world, random, 2, 2, -5, 0);
		placeDunlandItemFrame(world, random, -2, 2, 5, 2);
		placeDunlandItemFrame(world, random, 2, 2, 5, 2);
		placeWallBanner(world, -2, 4, -6, LOTRItemBanner.BannerType.DUNLAND, 2);
		placeWallBanner(world, 2, 4, -6, LOTRItemBanner.BannerType.DUNLAND, 2);
		LOTREntityDunlending dunlending = new LOTREntityDunlending(world);
		spawnNPCAndSetHome(dunlending, world, 0, 1, 0, 16);
		return true;
	}
}
