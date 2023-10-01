package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityDesertScorpion;
import lotr.common.entity.npc.LOTREntityHaradPyramidWraith;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenHaradPyramid extends LOTRWorldGenStructureBase2 {
	public LOTRWorldGenHaradPyramid(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int pyramidRadius = 27;
		setOriginAndRotation(world, i, j, k, rotation, usingPlayer != null ? pyramidRadius : 0);
		setupRandomBlocks(random);
		if (restrictions) {
			for (int i1 = -pyramidRadius; i1 <= pyramidRadius; ++i1) {
				for (int k1 = -pyramidRadius; k1 <= pyramidRadius; ++k1) {
					int j1 = getTopBlock(world, i1, k1) - 1;
					Block block = getBlock(world, i1, j1, k1);
					if (isSurface(world, i1, j1, k1) || block == Blocks.stone || block == Blocks.sandstone || block == LOTRMod.redSandstone) {
						continue;
					}
					return false;
				}
			}
		}
		originY += MathHelper.getRandomIntegerInRange(random, -2, 4);
		loadStrScan("harad_pyramid");
		addBlockMetaAliasOption("BRICK", 3, LOTRMod.brick, 15);
		addBlockMetaAliasOption("BRICK", 1, LOTRMod.brick3, 11);
		addBlockMetaAliasOption("BRICK_MAYBE", 4, Blocks.air, 0);
		addBlockMetaAliasOption("BRICK_MAYBE", 3, LOTRMod.brick, 15);
		addBlockMetaAliasOption("BRICK_MAYBE", 1, LOTRMod.brick3, 11);
		addBlockMetaAliasOption("BRICK_SLAB", 3, LOTRMod.slabSingle4, 0);
		addBlockMetaAliasOption("BRICK_SLAB", 1, LOTRMod.slabSingle7, 1);
		addBlockAliasOption("BRICK_STAIR", 3, LOTRMod.stairsNearHaradBrick);
		addBlockAliasOption("BRICK_STAIR", 1, LOTRMod.stairsNearHaradBrickCracked);
		addBlockMetaAliasOption("BRICK_WALL", 3, LOTRMod.wall, 15);
		addBlockMetaAliasOption("BRICK_WALL", 1, LOTRMod.wall3, 3);
		addBlockMetaAliasOption("PILLAR", 4, LOTRMod.pillar, 5);
		addBlockMetaAliasOption("PILLAR_SLAB", 4, LOTRMod.slabSingle4, 7);
		addBlockMetaAliasOption("BRICK2", 3, LOTRMod.brick3, 13);
		addBlockMetaAliasOption("BRICK2", 1, LOTRMod.brick3, 14);
		addBlockMetaAliasOption("BRICK2_SLAB", 3, LOTRMod.slabSingle7, 2);
		addBlockMetaAliasOption("BRICK2_SLAB", 1, LOTRMod.slabSingle7, 3);
		addBlockAliasOption("BRICK2_STAIR", 3, LOTRMod.stairsNearHaradBrickRed);
		addBlockAliasOption("BRICK2_STAIR", 1, LOTRMod.stairsNearHaradBrickRedCracked);
		addBlockMetaAliasOption("TUNNEL", 5, Blocks.sand, 0);
		addBlockMetaAliasOption("TUNNEL", 5, Blocks.air, 0);
		addBlockMetaAliasOption("ROOF", 4, Blocks.sand, 1);
		addBlockMetaAliasOption("ROOF", 4, LOTRMod.redSandstone, 0);
		addBlockMetaAliasOption("ROOF", 2, LOTRMod.brick3, 13);
		addBlockMetaAliasOption("ROOF", 2, LOTRMod.brick3, 14);
		generateStrScan(world, random, 0, 0, 0);
		placePyramidChest(world, random, -4, -6, 3, 2);
		placePyramidChest(world, random, 0, -6, 3, 2);
		placePyramidChest(world, random, 4, -6, 3, 2);
		placePyramidChest(world, random, -5, -5, -7, 4);
		placePyramidChest(world, random, -3, -5, -7, 5);
		placePyramidChest(world, random, 3, -5, -7, 4);
		placePyramidChest(world, random, 5, -5, -7, 5);
		placePyramidChest(world, random, -4, -5, -5, 2);
		placePyramidChest(world, random, 4, -5, -5, 2);
		placeSpawnerChest(world, random, 0, -6, 15, LOTRMod.spawnerChestAncientHarad, 2, LOTREntityHaradPyramidWraith.class, LOTRChestContents.NEAR_HARAD_PYRAMID, 12);
		placeMobSpawner(world, 0, -2, 15, LOTREntityDesertScorpion.class);
		placeMobSpawner(world, -12, -2, -12, LOTREntityDesertScorpion.class);
		placeMobSpawner(world, 12, -2, -12, LOTREntityDesertScorpion.class);
		placeMobSpawner(world, 0, 8, 0, LOTREntityDesertScorpion.class);
		placePyramidChest(world, random, -12, -1, -12, 2, true);
		placePyramidChest(world, random, 12, -1, -12, 2, true);
		placePyramidChest(world, random, 0, 9, 0, 2, true);
		return true;
	}

	public void placePyramidChest(World world, Random random, int i, int j, int k, int meta) {
		placePyramidChest(world, random, i, j, k, meta, random.nextBoolean());
	}

	public void placePyramidChest(World world, Random random, int i, int j, int k, int meta, boolean trap) {
		int amount = MathHelper.getRandomIntegerInRange(random, 3, 5);
		if (trap) {
			placeSpawnerChest(world, random, i, j, k, LOTRMod.spawnerChestStone, meta, LOTREntityHaradPyramidWraith.class, LOTRChestContents.NEAR_HARAD_PYRAMID, amount);
		} else {
			placeChest(world, random, i, j, k, LOTRMod.chestStone, meta, LOTRChestContents.NEAR_HARAD_PYRAMID, amount);
		}
	}
}
