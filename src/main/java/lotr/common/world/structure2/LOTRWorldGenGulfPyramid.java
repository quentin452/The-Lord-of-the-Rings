package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenGulfPyramid extends LOTRWorldGenGulfStructure {
	public LOTRWorldGenGulfPyramid(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int j2;
		int i1;
		int j12;
		int k1;
		int step;
		setOriginAndRotation(world, i, j, k, rotation, 11);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -11; i1 <= 11; ++i1) {
				for (k1 = -11; k1 <= 11; ++k1) {
					j12 = getTopBlock(world, i1, k1) - 1;
					if (isSurface(world, i1, j12, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -10; i1 <= 10; ++i1) {
			for (k1 = -10; k1 <= 10; ++k1) {
				for (j12 = 1; j12 <= 20; ++j12) {
					setAir(world, i1, j12, k1);
				}
			}
		}
		loadStrScan("gulf_pyramid");
		associateBlockMetaAlias("STONE", Blocks.sandstone, 0);
		associateBlockAlias("STONE_STAIR", Blocks.sandstone_stairs);
		associateBlockMetaAlias("STONE2", LOTRMod.redSandstone, 0);
		associateBlockAlias("STONE2_STAIR", LOTRMod.stairsRedSandstone);
		addBlockMetaAliasOption("BRICK", 8, LOTRMod.brick, 15);
		addBlockMetaAliasOption("BRICK", 2, LOTRMod.brick3, 11);
		addBlockAliasOption("BRICK_STAIR", 8, LOTRMod.stairsNearHaradBrick);
		addBlockAliasOption("BRICK_STAIR", 2, LOTRMod.stairsNearHaradBrickCracked);
		addBlockMetaAliasOption("BRICK_WALL", 8, LOTRMod.wall, 15);
		addBlockMetaAliasOption("BRICK_WALL", 2, LOTRMod.wall3, 3);
		addBlockMetaAliasOption("PILLAR", 10, LOTRMod.pillar, 5);
		addBlockMetaAliasOption("BRICK2", 8, LOTRMod.brick3, 13);
		addBlockMetaAliasOption("BRICK2", 2, LOTRMod.brick3, 14);
		associateBlockMetaAlias("BRICK2_CARVED", LOTRMod.brick3, 15);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockMetaAlias("ROOF_SLAB", roofSlabBlock, roofSlabMeta);
		associateBlockMetaAlias("ROOF_SLAB_INV", roofSlabBlock, roofSlabMeta | 8);
		associateBlockAlias("ROOF_STAIR", roofStairBlock);
		generateStrScan(world, random, 0, 1, 0);
		for (i1 = -5; i1 <= 5; ++i1) {
			for (k1 = -5; k1 <= 5; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				int j13 = 11;
				if (i2 <= 2 && k2 <= 2 || !isOpaque(world, i1, j13 - 1, k1) || !isAir(world, i1, j13, k1) || random.nextInt(12) != 0) {
					continue;
				}
				placeChest(world, random, i1, j13, k1, LOTRMod.chestBasket, MathHelper.getRandomIntegerInRange(random, 2, 5), LOTRChestContents.GULF_PYRAMID);
			}
		}
		int maxStep = 4;
		for (int k12 : new int[]{-11, 11}) {
			int i12;
			for (step = 0; step < maxStep && !isOpaque(world, i12 = -7 - step, j1 = -step, k12); ++step) {
				setBlockAndMetadata(world, i12, j1, k12, Blocks.sandstone_stairs, 1);
				setGrassToDirt(world, i12, j1 - 1, k12);
				j2 = j1 - 1;
				while (!isOpaque(world, i12, j2, k12) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i12, j2, k12, Blocks.sandstone, 0);
					setGrassToDirt(world, i12, j2 - 1, k12);
					--j2;
				}
			}
			for (step = 0; step < maxStep && !isOpaque(world, i12 = 7 + step, j1 = -step, k12); ++step) {
				setBlockAndMetadata(world, i12, j1, k12, Blocks.sandstone_stairs, 0);
				setGrassToDirt(world, i12, j1 - 1, k12);
				j2 = j1 - 1;
				while (!isOpaque(world, i12, j2, k12) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i12, j2, k12, Blocks.sandstone, 0);
					setGrassToDirt(world, i12, j2 - 1, k12);
					--j2;
				}
			}
		}
		for (int i13 : new int[]{-11, 11}) {
			int k13;
			for (step = 0; step < maxStep && !isOpaque(world, i13, j1 = -step, k13 = -7 - step); ++step) {
				setBlockAndMetadata(world, i13, j1, k13, Blocks.sandstone_stairs, 2);
				setGrassToDirt(world, i13, j1 - 1, k13);
				j2 = j1 - 1;
				while (!isOpaque(world, i13, j2, k13) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i13, j2, k13, Blocks.sandstone, 0);
					setGrassToDirt(world, i13, j2 - 1, k13);
					--j2;
				}
			}
			for (step = 0; step < maxStep && !isOpaque(world, i13, j1 = -step, k13 = 7 + step); ++step) {
				setBlockAndMetadata(world, i13, j1, k13, Blocks.sandstone_stairs, 3);
				setGrassToDirt(world, i13, j1 - 1, k13);
				j2 = j1 - 1;
				while (!isOpaque(world, i13, j2, k13) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i13, j2, k13, Blocks.sandstone, 0);
					setGrassToDirt(world, i13, j2 - 1, k13);
					--j2;
				}
			}
		}
		return true;
	}
}
