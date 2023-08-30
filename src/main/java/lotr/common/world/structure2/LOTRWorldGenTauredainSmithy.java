package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityTauredainSmith;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenTauredainSmithy extends LOTRWorldGenTauredainHouse {
	public LOTRWorldGenTauredainSmithy(boolean flag) {
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
			for (int i1 = -5; i1 <= 5; ++i1) {
				for (int k1 = -5; k1 <= 7; ++k1) {
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
		for (int i1 = -5; i1 <= 5; ++i1) {
			for (int k1 = -5; k1 <= 7; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				if (i2 <= 4 && k1 >= -2 && k1 <= 5) {
					for (j1 = -4; j1 <= 0; ++j1) {
						setAir(world, i1, j1, k1);
					}
				}
				if (i2 <= 2 && k1 == 6) {
					for (j1 = -4; j1 <= -1; ++j1) {
						setAir(world, i1, j1, 6);
					}
				}
				if (k2 <= 5) {
					for (j1 = 1; j1 <= 8; ++j1) {
						setAir(world, i1, j1, k1);
					}
				}
				if (i2 > 3 || k1 < 1) {
					continue;
				}
				for (j1 = 1; j1 <= 8; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("taurethrim_smithy");
		associateBlockMetaAlias("STONEBRICK", LOTRMod.brick4, 0);
		associateBlockAlias("STONEBRICK_STAIR", LOTRMod.stairsTauredainBrick);
		associateBlockMetaAlias("STONEBRICK_WALL", LOTRMod.wall4, 0);
		associateBlockMetaAlias("OBBRICK", LOTRMod.brick4, 4);
		associateBlockMetaAlias("OBBRICK_SLAB", LOTRMod.slabSingle8, 4);
		associateBlockAlias("OBBRICK_STAIR", LOTRMod.stairsTauredainBrickObsidian);
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockAlias("BRICK_STAIR", brickStairBlock);
		associateBlockMetaAlias("BRICK_WALL", brickWallBlock, brickWallMeta);
		associateBlockMetaAlias("WOOD", woodBlock, woodMeta);
		associateBlockMetaAlias("WOOD|4", woodBlock, woodMeta | 4);
		associateBlockMetaAlias("WOOD|8", woodBlock, woodMeta | 8);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		addBlockMetaAliasOption("FLOOR", 10, Blocks.stained_hardened_clay, 7);
		addBlockMetaAliasOption("FLOOR", 10, LOTRMod.mud, 0);
		associateBlockMetaAlias("WALL", Blocks.stained_hardened_clay, 12);
		associateBlockMetaAlias("ROOF", thatchBlock, thatchMeta);
		associateBlockMetaAlias("ROOF_SLAB", thatchSlabBlock, thatchSlabMeta);
		associateBlockAlias("ROOF_STAIR", thatchStairBlock);
		generateStrScan(world, random, 0, 0, 0);
		setBlockAndMetadata(world, 0, 5, 5, bedBlock, 1);
		setBlockAndMetadata(world, 1, 5, 5, bedBlock, 9);
		placeChest(world, random, 2, 5, 4, LOTRMod.chestBasket, 5, LOTRChestContents.TAUREDAIN_HOUSE);
		placeTauredainFlowerPot(world, 2, 6, 5, random);
		placePlateWithCertainty(world, random, 2, 6, 3, LOTRMod.woodPlateBlock, LOTRFoods.TAUREDAIN);
		placeTauredainTorch(world, -4, 2, -4);
		placeTauredainTorch(world, 4, 2, -4);
		placeWeaponRack(world, -3, -2, 2, 5, getRandomTaurethrimWeapon(random));
		placeArmorStand(world, 3, -3, 2, 1, new ItemStack[]{null, new ItemStack(LOTRMod.bodyTauredain), null, null});
		LOTREntityTauredainSmith smith = new LOTREntityTauredainSmith(world);
		spawnNPCAndSetHome(smith, world, 0, -3, 3, 12);
		return true;
	}

	@Override
	public int getOffset() {
		return 6;
	}

	public ItemStack getRandomTaurethrimWeapon(Random random) {
		ItemStack[] items = {new ItemStack(LOTRMod.swordTauredain), new ItemStack(LOTRMod.daggerTauredain), new ItemStack(LOTRMod.spearTauredain), new ItemStack(LOTRMod.pikeTauredain), new ItemStack(LOTRMod.hammerTauredain), new ItemStack(LOTRMod.battleaxeTauredain)};
		return items[random.nextInt(items.length)].copy();
	}
}
