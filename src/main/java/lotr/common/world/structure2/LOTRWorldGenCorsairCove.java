package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRWorldGenCorsairCove extends LOTRWorldGenCorsairStructure {
	public LOTRWorldGenCorsairCove(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int i1;
		int j1;
		int k1;
		setOriginAndRotation(world, i, j, k, rotation, 0);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i12 = -15; i12 <= 9; ++i12) {
				for (int k12 = -1; k12 <= 12; ++k12) {
					int j12 = getTopBlock(world, i12, k12) - 1;
					Block block = getBlock(world, i12, j12, k12);
					if (!isSurface(world, i12, j12, k12) && block != Blocks.stone && block != Blocks.sandstone) {
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
		for (i1 = -14; i1 <= 4; ++i1) {
			for (k1 = 0; k1 <= 7; ++k1) {
				for (j1 = 1; j1 <= 9; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("corsair_cove");
		addBlockMetaAliasOption("STONE", 10, Blocks.stone, 0);
		addBlockMetaAliasOption("STONE", 3, Blocks.sandstone, 0);
		addBlockMetaAliasOption("STONE", 3, Blocks.dirt, 1);
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("BRICK_SLAB", brickSlabBlock, brickSlabMeta);
		associateBlockMetaAlias("BRICK_SLAB_INV", brickSlabBlock, brickSlabMeta | 8);
		associateBlockAlias("BRICK_STAIR", brickStairBlock);
		associateBlockMetaAlias("BRICK_WALL", brickWallBlock, brickWallMeta);
		associateBlockMetaAlias("PILLAR", pillarBlock, pillarMeta);
		associateBlockMetaAlias("PILLAR_SLAB", pillarSlabBlock, pillarSlabMeta);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		generateStrScan(world, random, 0, 0, 0);
		placeSkull(world, random, -3, 7, 3);
		placeBanner(world, 1, 5, 3, LOTRItemBanner.BannerType.UMBAR, 2);
		placeChest(world, random, -14, 4, 4, Blocks.chest, 4, LOTRChestContents.CORSAIR, MathHelper.getRandomIntegerInRange(random, 6, 12));
		placeBarrel(world, random, -14, 5, 6, 4, LOTRFoods.CORSAIR_DRINK);
		placeWallBanner(world, -12, 8, 3, LOTRItemBanner.BannerType.UMBAR, 2);
		placeWallBanner(world, -12, 8, 7, LOTRItemBanner.BannerType.UMBAR, 0);
		placeWallBanner(world, -14, 8, 5, LOTRItemBanner.BannerType.UMBAR, 3);
		placeWallBanner(world, -10, 8, 5, LOTRItemBanner.BannerType.UMBAR, 1);
		placeWeaponRack(world, -7, 5, 8, 6, getRandomCorsairWeapon(random));
		placeWeaponRack(world, -6, 5, 8, 6, getRandomCorsairWeapon(random));
		placeWeaponRack(world, -5, 5, 8, 6, getRandomCorsairWeapon(random));
		if (random.nextInt(3) == 0) {
			placeTreasure(world, random, -14, 4, 2);
			placeTreasure(world, random, -14, 4, 1);
			placeTreasure(world, random, -13, 4, 1);
			placeTreasure(world, random, -12, 4, 1);
			placeTreasure(world, random, -12, 4, 0);
			placeTreasure(world, random, -11, 4, 0);
		}
		if (random.nextInt(3) == 0) {
			placeTreasure(world, random, -4, 4, 0);
			placeTreasure(world, random, -3, 5, 0);
			placeTreasure(world, random, -3, 4, 1);
			placeTreasure(world, random, -3, 4, 2);
			placeTreasure(world, random, -2, 4, 1);
		}
		for (i1 = -14; i1 <= -5; ++i1) {
			for (k1 = 0; k1 <= 8; ++k1) {
				j1 = 4;
				if (!isAir(world, i1, j1, k1) || !isOpaque(world, i1, j1 - 1, k1) || random.nextInt(20) != 0) {
					continue;
				}
				placeFoodOrDrink(world, random, i1, j1, k1);
			}
		}
		int corsairs = 2 + random.nextInt(2);
		for (int l = 0; l < corsairs; ++l) {
			LOTREntityCorsair corsair = new LOTREntityCorsair(world);
			spawnNPCAndSetHome(corsair, world, -9, 4, 4, 16);
		}
		LOTREntityCorsair captain = random.nextBoolean() ? new LOTREntityCorsairCaptain(world) : new LOTREntityCorsairSlaver(world);
		spawnNPCAndSetHome(captain, world, -9, 4, 4, 4);
		LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
		respawner.setSpawnClass(LOTREntityCorsair.class);
		respawner.setCheckRanges(24, -16, 12, 8);
		respawner.setSpawnRanges(3, -2, 2, 16);
		placeNPCRespawner(respawner, world, -9, 4, 4);
		LOTREntityHaradSlave slave = new LOTREntityHaradSlave(world);
		spawnNPCAndSetHome(slave, world, -7, 7, 6, 8);
		for (int l = 0; l < 16; ++l) {
			LOTRTreeType tree = LOTRTreeType.PALM;
			WorldGenAbstractTree treeGen = tree.create(notifyChanges, random);
			if (treeGen == null) {
				continue;
			}
			int i13 = 2;
			int j13 = 6;
			int k13 = 7;
			if (treeGen.generate(world, random, getX(i13, k13), getY(j13), getZ(i13, k13))) {
				break;
			}
		}
		return true;
	}

	public void placeFoodOrDrink(World world, Random random, int i, int j, int k) {
		if (random.nextInt(3) == 0) {
			Block plateBlock = LOTRMod.woodPlateBlock;
			if (random.nextBoolean()) {
				setBlockAndMetadata(world, i, j, k, plateBlock, 0);
			} else {
				placePlateWithCertainty(world, random, i, j, k, plateBlock, LOTRFoods.CORSAIR);
			}
		} else {
			placeMug(world, random, i, j, k, random.nextInt(4), LOTRFoods.CORSAIR_DRINK);
		}
	}

	public void placeTreasure(World world, Random random, int i, int j, int k) {
		Block block = random.nextBoolean() ? LOTRMod.treasureGold : LOTRMod.treasureSilver;
		setBlockAndMetadata(world, i, j, k, block, random.nextInt(7));
	}
}
