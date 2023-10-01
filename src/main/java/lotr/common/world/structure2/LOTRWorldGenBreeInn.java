package lotr.common.world.structure2;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenBreeInn extends LOTRWorldGenBreeStructure {
	public boolean hasPresets;
	public String[] presetInnName;
	public String presetInnkeeperName;
	public boolean presetIsMaleKeeper;
	public boolean presetIsHobbitKeeper;

	public LOTRWorldGenBreeInn(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int i1;
		LOTREntityMan innkeeper;
		int k1;
		setOriginAndRotation(world, i, j, k, rotation, 5, -2);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -9; i1 <= 9; ++i1) {
				for (k1 = -7; k1 <= 7; ++k1) {
					j1 = getTopBlock(world, i1, k1) - 1;
					if (isSurface(world, i1, j1, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -8; i1 <= 8; ++i1) {
			for (k1 = -5; k1 <= 6; ++k1) {
				for (j1 = 1; j1 <= 12; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("bree_inn");
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("BRICK2", brick2Block, brick2Meta);
		associateBlockAlias("BRICK2_STAIR", brick2StairBlock);
		associateBlockMetaAlias("BRICK2_WALL", brick2WallBlock, brick2WallMeta);
		associateBlockMetaAlias("FLOOR", floorBlock, floorMeta);
		associateBlockMetaAlias("STONE_WALL", stoneWallBlock, stoneWallMeta);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		associateBlockAlias("DOOR", doorBlock);
		associateBlockAlias("TRAPDOOR", trapdoorBlock);
		associateBlockMetaAlias("BEAM", beamBlock, beamMeta);
		associateBlockMetaAlias("BEAM|4", beamBlock, beamMeta | 4);
		associateBlockMetaAlias("BEAM|8", beamBlock, beamMeta | 8);
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		associateBlockMetaAlias("ROOF_SLAB", roofSlabBlock, roofSlabMeta);
		associateBlockMetaAlias("ROOF_SLAB_INV", roofSlabBlock, roofSlabMeta | 8);
		associateBlockAlias("ROOF_STAIR", roofStairBlock);
		associateBlockMetaAlias("LEAF", Blocks.leaves, 4);
		setBlockAliasChance("LEAF", 0.6f);
		generateStrScan(world, random, 0, 0, 0);
		placeRandomFlowerPot(world, random, -4, 2, -3);
		placeRandomFlowerPot(world, random, -3, 6, 0);
		plantFlower(world, random, -8, 6, 0);
		plantFlower(world, random, 8, 6, 0);
		plantFlower(world, random, -8, 6, 1);
		plantFlower(world, random, 8, 6, 1);
		placeChest(world, random, -5, 1, -3, 3, LOTRChestContents.BREE_HOUSE);
		setBlockAndMetadata(world, -6, 2, -3, LOTRWorldGenBreeStructure.getRandomPieBlock(random), 0);
		placeBarrel(world, random, -6, 2, 1, 4, LOTRFoods.BREE_DRINK);
		placeBarrel(world, random, -4, 2, 4, 2, LOTRFoods.BREE_DRINK);
		placeFoodOrDrink(world, random, 6, 2, -3);
		placeFoodOrDrink(world, random, 5, 2, -3);
		placeFoodOrDrink(world, random, 1, 2, -3);
		placeFoodOrDrink(world, random, 6, 2, -2);
		placeFoodOrDrink(world, random, 5, 2, -2);
		placeFoodOrDrink(world, random, 0, 2, 0);
		placeFoodOrDrink(world, random, -6, 2, 0);
		placeFoodOrDrink(world, random, -4, 2, 0);
		placeFoodOrDrink(world, random, -4, 2, 1);
		placeFoodOrDrink(world, random, 0, 2, 1);
		placeFoodOrDrink(world, random, -4, 2, 3);
		placeFoodOrDrink(world, random, 6, 2, 4);
		placeFoodOrDrink(world, random, 2, 2, 4);
		placeFoodOrDrink(world, random, 6, 6, -3);
		placeFoodOrDrink(world, random, 5, 6, -3);
		placeFoodOrDrink(world, random, 0, 6, -3);
		placeFoodOrDrink(world, random, -5, 6, -3);
		placeFoodOrDrink(world, random, -6, 6, -3);
		placeFoodOrDrink(world, random, 5, 6, 4);
		placeFoodOrDrink(world, random, -5, 6, 4);
		placeWeaponRack(world, -3, 7, -1, 5, getRandomBreeWeapon(random));
		placeWeaponRack(world, 3, 7, -1, 7, getRandomBreeWeapon(random));
		placeWeaponRack(world, -3, 7, 2, 5, getRandomBreeWeapon(random));
		placeWeaponRack(world, 3, 7, 2, 7, getRandomBreeWeapon(random));
		setBlockAndMetadata(world, 5, 5, 0, bedBlock, 1);
		setBlockAndMetadata(world, 6, 5, 0, bedBlock, 9);
		setBlockAndMetadata(world, -5, 5, 0, bedBlock, 3);
		setBlockAndMetadata(world, -6, 5, 0, bedBlock, 11);
		setBlockAndMetadata(world, 5, 5, 2, bedBlock, 1);
		setBlockAndMetadata(world, 6, 5, 2, bedBlock, 9);
		setBlockAndMetadata(world, -5, 5, 2, bedBlock, 3);
		setBlockAndMetadata(world, -6, 5, 2, bedBlock, 11);
		setBlockAndMetadata(world, 5, 8, -2, bedBlock, 2);
		setBlockAndMetadata(world, 5, 8, -3, bedBlock, 10);
		setBlockAndMetadata(world, -5, 8, -2, bedBlock, 2);
		setBlockAndMetadata(world, -5, 8, -3, bedBlock, 10);
		String[] innName = LOTRNames.getBreeInnName(random);
		if (hasPresets) {
			innName = presetInnName;
		}
		String innNameNPC = innName[0] + " " + innName[1];
		placeSign(world, -2, 4, -7, Blocks.wall_sign, 2, new String[]{"", innName[0], innName[1], ""});
		placeSign(world, -1, 4, -6, Blocks.wall_sign, 4, new String[]{"", innName[0], innName[1], ""});
		placeSign(world, -3, 4, -6, Blocks.wall_sign, 5, new String[]{"", innName[0], innName[1], ""});
		if (hasPresets) {
			innkeeper = presetIsHobbitKeeper ? new LOTREntityBreeHobbitInnkeeper(world) : new LOTREntityBreeInnkeeper(world);
		} else {
			innkeeper = random.nextInt(3) == 0 ? new LOTREntityBreeHobbitInnkeeper(world) : new LOTREntityBreeInnkeeper(world);
		}
		if (hasPresets) {
			innkeeper.familyInfo.setMale(presetIsMaleKeeper);
			innkeeper.familyInfo.setName(presetInnkeeperName);
		}
		innkeeper.setSpecificLocationName(innNameNPC);
		spawnNPCAndSetHome(innkeeper, world, -5, 1, 0, 4);
		String[] innkeeperNameParts = innkeeper.getNPCName().split(" ");
		if (innkeeperNameParts.length < 2) {
			innkeeperNameParts = new String[]{innkeeperNameParts[0], ""};
		}
		placeSign(world, -2, 3, -5, Blocks.wall_sign, 2, new String[]{"", "by " + innkeeperNameParts[0], innkeeperNameParts[1], ""});
		int men = 8 + random.nextInt(6);
		for (int l = 0; l < men; ++l) {
			LOTREntityMan breelander;
			breelander = random.nextInt(3) == 0 ? new LOTREntityBreeHobbit(world) : new LOTREntityBreeMan(world);
			if (random.nextInt(10) == 0) {
				breelander = new LOTREntityRuffianSpy(world);
			}
			spawnNPCAndSetHome(breelander, world, -2, 1, 0, 16);
		}
		return true;
	}

	public void placeFoodOrDrink(World world, Random random, int i, int j, int k) {
		if (random.nextBoolean()) {
			if (random.nextBoolean()) {
				placeMug(world, random, i, j, k, random.nextInt(4), LOTRFoods.BREE_DRINK);
			} else {
				Block[] plates = {LOTRMod.plateBlock, LOTRMod.ceramicPlateBlock, LOTRMod.woodPlateBlock};
				Block plateBlock = plates[random.nextInt(plates.length)];
				if (random.nextBoolean()) {
					setBlockAndMetadata(world, i, j, k, plateBlock, 0);
				} else {
					placePlateWithCertainty(world, random, i, j, k, plateBlock, LOTRFoods.BREE);
				}
			}
		}
	}

	public void setPresets(String[] innName, String innkeeperName, boolean innkeeperMale, boolean hobbit) {
		hasPresets = true;
		presetInnName = innName;
		presetInnkeeperName = innkeeperName;
		presetIsMaleKeeper = innkeeperMale;
		presetIsHobbitKeeper = hobbit;
	}
}
