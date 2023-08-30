package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.entity.npc.LOTRNames;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronTownGate extends LOTRWorldGenSouthronStructure {
	public String[] signText = LOTRNames.getHaradVillageName(new Random());

	public LOTRWorldGenSouthronTownGate(boolean flag) {
		super(flag);
	}

	@Override
	public boolean canUseRedBricks() {
		return false;
	}

	@Override
	public boolean forceCedarWood() {
		return true;
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int j12;
		int j2;
		int i1;
		int k1;
		int k12;
		int step;
		int i12;
		setOriginAndRotation(world, i, j, k, rotation, 4);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i12 = -8; i12 <= 8; ++i12) {
				for (k12 = -3; k12 <= 3; ++k12) {
					j12 = getTopBlock(world, i12, k12) - 1;
					if (isSurface(world, i12, j12, k12)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i12 = -8; i12 <= 8; ++i12) {
			for (k12 = -3; k12 <= 3; ++k12) {
				for (j12 = 1; j12 <= 12; ++j12) {
					setAir(world, i12, j12, k12);
				}
			}
		}
		loadStrScan("southron_town_gate");
		associateBlockMetaAlias("STONE", stoneBlock, stoneMeta);
		associateBlockAlias("STONE_STAIR", stoneStairBlock);
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("BRICK_SLAB", brickSlabBlock, brickSlabMeta);
		associateBlockMetaAlias("BRICK_SLAB_INV", brickSlabBlock, brickSlabMeta | 8);
		associateBlockAlias("BRICK_STAIR", brickStairBlock);
		associateBlockMetaAlias("BRICK_WALL", brickWallBlock, brickWallMeta);
		associateBlockMetaAlias("PILLAR", pillarBlock, pillarMeta);
		associateBlockMetaAlias("BRICK2", brick2Block, brick2Meta);
		associateBlockMetaAlias("BRICK2_SLAB", brick2SlabBlock, brick2SlabMeta);
		associateBlockMetaAlias("BRICK2_SLAB_INV", brick2SlabBlock, brick2SlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockMetaAlias("BEAM", woodBeamBlock, woodBeamMeta);
		associateBlockMetaAlias("BEAM|4", woodBeamBlock, woodBeamMeta4);
		associateBlockAlias("GATE_METAL", gateMetalBlock);
		generateStrScan(world, random, 0, 0, 0);
		if (signText != null) {
			placeSign(world, -3, 3, -4, Blocks.wall_sign, 2, signText);
			placeSign(world, 3, 3, -4, Blocks.wall_sign, 2, signText);
		}
		placeWallBanner(world, -6, 4, -2, bannerType, 2);
		placeWallBanner(world, 6, 4, -2, bannerType, 2);
		for (step = 0; step < 12 && !isOpaque(world, i1 = -7 - step, j1 = 5 - step, k1 = 2); ++step) {
			if (j1 <= 1) {
				setBlockAndMetadata(world, i1, j1, k1, stoneStairBlock, 1);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, brickStairBlock, 1);
			}
			setGrassToDirt(world, i1, j1 - 1, k1);
			j2 = j1 - 1;
			while (!isOpaque(world, i1, j2, k1) && getY(j2) >= 0) {
				if (j2 <= 1) {
					setBlockAndMetadata(world, i1, j2, k1, stoneBlock, stoneMeta);
				} else {
					setBlockAndMetadata(world, i1, j2, k1, brickBlock, brickMeta);
				}
				setGrassToDirt(world, i1, j2 - 1, k1);
				--j2;
			}
		}
		for (step = 0; step < 12 && !isOpaque(world, i1 = 7 + step, j1 = 5 - step, k1 = 2); ++step) {
			if (j1 <= 1) {
				setBlockAndMetadata(world, i1, j1, k1, stoneStairBlock, 0);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, brickStairBlock, 0);
			}
			setGrassToDirt(world, i1, j1 - 1, k1);
			j2 = j1 - 1;
			while (!isOpaque(world, i1, j2, k1) && getY(j2) >= 0) {
				if (j2 <= 1) {
					setBlockAndMetadata(world, i1, j2, k1, stoneBlock, stoneMeta);
				} else {
					setBlockAndMetadata(world, i1, j2, k1, brickBlock, brickMeta);
				}
				setGrassToDirt(world, i1, j2 - 1, k1);
				--j2;
			}
		}
		return true;
	}

	public LOTRWorldGenSouthronTownGate setSignText(String[] s) {
		signText = s;
		return this;
	}
}
