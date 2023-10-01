package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityGulfHaradrim;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenGulfAltar extends LOTRWorldGenGulfStructure {
	public LOTRWorldGenGulfAltar(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int j1;
		int step;
		setOriginAndRotation(world, i, j, k, rotation, 13);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -12; i1 <= 12; ++i1) {
				for (int k12 = -12; k12 <= 8; ++k12) {
					int j12 = getTopBlock(world, i1, k12) - 1;
					if (!isSurface(world, i1, j12, k12)) {
						return false;
					}
					if (j12 < minHeight) {
						minHeight = j12;
					}
					if (j12 > maxHeight) {
						maxHeight = j12;
					}
					if (maxHeight - minHeight <= 16) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i1 = -3; i1 <= 3; ++i1) {
			for (int k13 = -3; k13 <= 3; ++k13) {
				for (int j13 = 5; j13 <= 10; ++j13) {
					setAir(world, i1, j13, k13);
				}
			}
		}
		loadStrScan("gulf_altar");
		associateBlockMetaAlias("WOOD", woodBlock, woodMeta);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockMetaAlias("FLAG", flagBlock, flagMeta);
		associateBlockMetaAlias("BONE", boneBlock, boneMeta);
		generateStrScan(world, random, 0, 0, 0);
		placeSkull(world, 0, 7, 0, 0);
		int holeX = 0;
		int holeZ = 6;
		int holeR = 3;
		if (getTopBlock(world, holeX, holeZ) >= -8) {
			for (int i1 = -holeR; i1 <= holeR; ++i1) {
				for (k1 = -holeR; k1 <= holeR; ++k1) {
					int holeY;
					int i2 = holeX + i1;
					int k2 = holeZ + k1;
					int dSq = i1 * i1 + k1 * k1;
					if (dSq >= holeR * holeR || !isSurface(world, i2, holeY = getTopBlock(world, i2, k2) - 1, k2)) {
						continue;
					}
					int holeDHere = (int) Math.round(Math.sqrt(Math.max(0, holeR * holeR - dSq)));
					for (int j14 = 3; j14 >= -holeDHere; --j14) {
						int j2 = holeY + j14;
						if (j14 > 0 || j14 > -holeDHere) {
							setAir(world, i2, j2, k2);
							continue;
						}
						if (j14 != -holeDHere) {
							continue;
						}
						if (random.nextBoolean()) {
							setBlockAndMetadata(world, i2, j2, k2, Blocks.dirt, 1);
						} else {
							setBlockAndMetadata(world, i2, j2, k2, LOTRMod.wasteBlock, 0);
						}
						if (random.nextInt(6) != 0) {
							continue;
						}
						placeSkull(world, random, i2, j2 + 1, k2);
					}
				}
			}
		}
		int maxSteps = 12;
		for (int i1 = -1; i1 <= 1; ++i1) {
			int k14;
			for (step = 0; step < maxSteps && !isOpaque(world, i1, j1 = -step / 2, k14 = -13 - step); ++step) {
				if (step % 2 == 0) {
					setBlockAndMetadata(world, i1, j1, k14, plankBlock, plankMeta);
					setGrassToDirt(world, i1, j1 - 1, k14);
					continue;
				}
				setBlockAndMetadata(world, i1, j1, k14, plankSlabBlock, plankSlabMeta);
				setBlockAndMetadata(world, i1, j1 - 1, k14, plankSlabBlock, plankSlabMeta | 8);
			}
		}
		for (k1 = -1; k1 <= 1; ++k1) {
			int i1;
			for (step = 0; step < maxSteps && !isOpaque(world, i1 = -13 - step, j1 = -step / 2, k1); ++step) {
				if (step % 2 == 0) {
					setBlockAndMetadata(world, i1, j1, k1, plankBlock, plankMeta);
					setGrassToDirt(world, i1, j1 - 1, k1);
					continue;
				}
				setBlockAndMetadata(world, i1, j1, k1, plankSlabBlock, plankSlabMeta);
				setBlockAndMetadata(world, i1, j1 - 1, k1, plankSlabBlock, plankSlabMeta | 8);
			}
			for (step = 0; step < maxSteps && !isOpaque(world, i1 = 13 + step, j1 = -step / 2, k1); ++step) {
				if (step % 2 == 0) {
					setBlockAndMetadata(world, i1, j1, k1, plankBlock, plankMeta);
					setGrassToDirt(world, i1, j1 - 1, k1);
					continue;
				}
				setBlockAndMetadata(world, i1, j1, k1, plankSlabBlock, plankSlabMeta);
				setBlockAndMetadata(world, i1, j1 - 1, k1, plankSlabBlock, plankSlabMeta | 8);
			}
		}
		LOTREntityGulfHaradrim gulfman = new LOTREntityGulfHaradrim(world);
		spawnNPCAndSetHome(gulfman, world, 0, 7, -1, 4);
		return true;
	}
}
