package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityHarnedhrim;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.world.World;

public class LOTRWorldGenHarnedorHouse extends LOTRWorldGenHarnedorStructure {
	public LOTRWorldGenHarnedorHouse(boolean flag) {
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
			for (int i1 = -7; i1 <= 7; ++i1) {
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
		for (int i1 = -6; i1 <= 6; ++i1) {
			for (int k1 = -6; k1 <= 6; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				if (i2 > 2 && k2 > 2) {
					continue;
				}
				for (j1 = 1; j1 <= 6; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		if (isRuined()) {
			loadStrScan("harnedor_house_ruined");
		} else {
			loadStrScan("harnedor_house");
		}
		associateBlockMetaAlias("WOOD", woodBlock, woodMeta);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("DOOR", doorBlock);
		associateBlockMetaAlias("PLANK2", plank2Block, plank2Meta);
		if (isRuined()) {
			setBlockAliasChance("PLANK2", 0.8f);
		}
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		generateStrScan(world, random, 0, 1, 0);
		if (!isRuined()) {
			setBlockAndMetadata(world, 0, 1, 3, bedBlock, 0);
			setBlockAndMetadata(world, 0, 1, 4, bedBlock, 8);
			placeWeaponRack(world, 0, 3, -4, 4, getRandomHarnedorWeapon(random));
			placeWeaponRack(world, 0, 3, 4, 6, getRandomHarnedorWeapon(random));
			placeChest(world, random, -4, 1, 0, LOTRMod.chestBasket, 4, LOTRChestContents.HARNENNOR_HOUSE);
			placePlate(world, random, 4, 2, 0, LOTRMod.ceramicPlateBlock, LOTRFoods.HARNEDOR);
			placePlate(world, random, -1, 2, 4, LOTRMod.ceramicPlateBlock, LOTRFoods.HARNEDOR);
			placeMug(world, random, 1, 2, 4, 0, LOTRFoods.HARNEDOR_DRINK);
			int men = 1 + random.nextInt(2);
			for (int l = 0; l < men; ++l) {
				LOTREntityHarnedhrim haradrim = new LOTREntityHarnedhrim(world);
				spawnNPCAndSetHome(haradrim, world, 0, 1, -1, 16);
			}
		}
		return true;
	}
}
