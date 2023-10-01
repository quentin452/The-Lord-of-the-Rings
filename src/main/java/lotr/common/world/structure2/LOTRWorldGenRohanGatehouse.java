package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityRohirrimWarrior;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenRohanGatehouse extends LOTRWorldGenRohanStructure {
	public LOTRWorldGenRohanGatehouse(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int i1;
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 2);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -9; i1 <= 9; ++i1) {
				for (k1 = -2; k1 <= 2; ++k1) {
					int j12 = getTopBlock(world, i1, k1) - 1;
					if (isSurface(world, i1, j12, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -9; i1 <= 9; ++i1) {
			for (k1 = -2; k1 <= 2; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				if (i2 >= 3 || k2 <= 1) {
					for (j1 = 0; (j1 >= 0 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
						setBlockAndMetadata(world, i1, j1, k1, rockSlabDoubleBlock, rockSlabDoubleMeta);
						setGrassToDirt(world, i1, j1 - 1, k1);
					}
					for (j1 = 1; j1 <= 12; ++j1) {
						setAir(world, i1, j1, k1);
					}
				}
				if (i2 == 2 && k2 == 0) {
					for (j1 = 1; j1 <= 4; ++j1) {
						setBlockAndMetadata(world, i1, j1, k1, woodBeamBlock, woodBeamMeta);
					}
				}
				if (i2 == 2 && k2 == 1) {
					for (j1 = 1; j1 <= 3; ++j1) {
						setBlockAndMetadata(world, i1, j1, k1, fenceBlock, fenceMeta);
					}
					setBlockAndMetadata(world, i1, 4, k1, plankBlock, plankMeta);
				}
				if (i2 == 1 && k2 == 1) {
					setBlockAndMetadata(world, i1, 4, k1, plankSlabBlock, plankSlabMeta | 8);
				}
				if (i2 >= 3 && i2 <= 9 && k2 <= 2) {
					setBlockAndMetadata(world, i1, 1, k1, rockSlabDoubleBlock, rockSlabDoubleMeta);
					for (j1 = 2; j1 <= 4; ++j1) {
						setBlockAndMetadata(world, i1, j1, k1, brickBlock, brickMeta);
					}
				}
				if ((i2 == 4 || i2 == 8) && k2 == 2) {
					for (j1 = 2; j1 <= 8; ++j1) {
						setBlockAndMetadata(world, i1, j1, k1, woodBeamBlock, woodBeamMeta);
					}
					setBlockAndMetadata(world, i1, 9, k1, plankBlock, plankMeta);
				}
				if (i2 >= 5 && i2 <= 7 && k2 == 2 || i2 == 4 || i2 == 8 && k2 <= 1) {
					setBlockAndMetadata(world, i1, 9, k1, plankSlabBlock, plankSlabMeta | 8);
				}
				if (i2 == 9 && k2 <= 1) {
					for (j1 = 2; j1 <= 5; ++j1) {
						setBlockAndMetadata(world, i1, j1, k1, woodBeamBlock, woodBeamMeta);
					}
					setBlockAndMetadata(world, i1, 6, k1, fenceBlock, fenceMeta);
				}
				if (i2 >= 5 && i2 <= 7 && k2 == 2) {
					setBlockAndMetadata(world, i1, 4, k1, woodBeamBlock, woodBeamMeta | 8);
					setBlockAndMetadata(world, i1, 5, k1, woodBeamBlock, woodBeamMeta);
					if (k1 < 0) {
						setBlockAndMetadata(world, i1, 6, k1, fenceBlock, fenceMeta);
					}
				}
				if (i2 == 3 && k2 == 2) {
					setBlockAndMetadata(world, i1, 2, k1, brickWallBlock, brickWallMeta);
					setBlockAndMetadata(world, i1, 3, k1, fenceBlock, fenceMeta);
					setBlockAndMetadata(world, i1, 4, k1, brickWallBlock, brickWallMeta);
				}
				if (i2 == 9 && k2 == 2) {
					setBlockAndMetadata(world, i1, 2, k1, brickWallBlock, brickWallMeta);
					for (j1 = 3; j1 <= 6; ++j1) {
						setBlockAndMetadata(world, i1, j1, k1, fenceBlock, fenceMeta);
					}
				}
				if (i2 <= 8 && k2 <= 1) {
					setBlockAndMetadata(world, i1, 5, k1, plankBlock, plankMeta);
				}
				if (i2 > 3 || k2 != 2) {
					continue;
				}
				if (i2 == 3) {
					setBlockAndMetadata(world, i1, 5, k1, plankBlock, plankMeta);
				} else {
					setBlockAndMetadata(world, i1, 5, k1, plankSlabBlock, plankSlabMeta | 8);
				}
				setBlockAndMetadata(world, i1, 6, k1, fenceBlock, fenceMeta);
			}
		}
		for (i1 = -1; i1 <= 1; ++i1) {
			for (int j13 = 1; j13 <= 5; ++j13) {
				setBlockAndMetadata(world, i1, j13, 0, gateBlock, 2);
			}
		}
		setBlockAndMetadata(world, 0, 6, 0, fenceBlock, fenceMeta);
		for (int i12 : new int[]{-1, 1}) {
			setBlockAndMetadata(world, i12, 6, 0, woodBeamBlock, woodBeamMeta);
			setBlockAndMetadata(world, i12, 7, 0, Blocks.lever, 13);
		}
		for (int i12 : new int[]{-3, 3}) {
			placeWallBanner(world, i12, 5, -2, bannerType, 2);
		}
		for (int i12 : new int[]{-2, 2}) {
			setBlockAndMetadata(world, i12, 4, -2, Blocks.torch, 4);
			setBlockAndMetadata(world, i12, 4, 2, Blocks.torch, 3);
		}
		for (int k12 : new int[]{-2, 2}) {
			setBlockAndMetadata(world, -9, 8, k12, Blocks.torch, 1);
			setBlockAndMetadata(world, -3, 8, k12, Blocks.torch, 2);
			setBlockAndMetadata(world, 3, 8, k12, Blocks.torch, 1);
			setBlockAndMetadata(world, 9, 8, k12, Blocks.torch, 2);
		}
		for (int i12 : new int[]{-6, 6}) {
			for (int k13 = -3; k13 <= 3; ++k13) {
				int k2 = Math.abs(k13);
				if (k2 == 3) {
					setBlockAndMetadata(world, i12 - 1, 10, k13, roofSlabBlock, roofSlabMeta);
					setBlockAndMetadata(world, i12, 10, k13, roofSlabBlock, roofSlabMeta);
					setBlockAndMetadata(world, i12 + 1, 10, k13, roofSlabBlock, roofSlabMeta);
				}
				if (k2 == 2) {
					setBlockAndMetadata(world, i12 - 2, 10, k13, roofSlabBlock, roofSlabMeta);
					setBlockAndMetadata(world, i12 - 1, 10, k13, roofBlock, roofMeta);
					setBlockAndMetadata(world, i12, 10, k13, roofBlock, roofMeta);
					setBlockAndMetadata(world, i12 + 1, 10, k13, roofBlock, roofMeta);
					setBlockAndMetadata(world, i12 + 2, 10, k13, roofSlabBlock, roofSlabMeta);
				}
				if (k2 > 1) {
					continue;
				}
				setBlockAndMetadata(world, i12 - 3, 10, k13, roofSlabBlock, roofSlabMeta);
				setBlockAndMetadata(world, i12 - 2, 10, k13, roofBlock, roofMeta);
				setBlockAndMetadata(world, i12 - 1, 10, k13, roofSlabBlock, roofSlabMeta | 8);
				setBlockAndMetadata(world, i12 - 1, 11, k13, roofSlabBlock, roofSlabMeta);
				setBlockAndMetadata(world, i12, 10, k13, roofSlabBlock, roofSlabMeta | 8);
				setBlockAndMetadata(world, i12, 11, k13, roofSlabBlock, roofSlabMeta);
				setBlockAndMetadata(world, i12 + 1, 10, k13, roofSlabBlock, roofSlabMeta | 8);
				setBlockAndMetadata(world, i12 + 1, 11, k13, roofSlabBlock, roofSlabMeta);
				setBlockAndMetadata(world, i12 + 2, 10, k13, roofBlock, roofMeta);
				setBlockAndMetadata(world, i12 + 3, 10, k13, roofSlabBlock, roofSlabMeta);
			}
		}
		for (int i12 : new int[]{-3, 3}) {
			j1 = 6;
			int k14 = 0;
			LOTREntityRohirrimWarrior guard = new LOTREntityRohirrimWarrior(world);
			spawnNPCAndSetHome(guard, world, i12, j1, k14, 8);
		}
		for (int k15 = 3; k15 <= 4; ++k15) {
			int i12;
			int step;
			int j2;
			int maxStep = 16;
			for (step = 0; step < maxStep && !isOpaque(world, i12 = -6 - step, j1 = 5 - (step <= 1 ? 0 : step - 2), k15); ++step) {
				if (step <= 1) {
					setBlockAndMetadata(world, i12, j1, k15, plankBlock, plankMeta);
				} else {
					setBlockAndMetadata(world, i12, j1, k15, plankStairBlock, 1);
				}
				setGrassToDirt(world, i12, j1 - 1, k15);
				j2 = j1 - 1;
				while (!isOpaque(world, i12, j2, k15) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i12, j2, k15, plankBlock, plankMeta);
					setGrassToDirt(world, i12, j2 - 1, k15);
					--j2;
				}
			}
			for (step = 0; step < maxStep && !isOpaque(world, i12 = 6 + step, j1 = 5 - (step <= 1 ? 0 : step - 2), k15); ++step) {
				if (step <= 1) {
					setBlockAndMetadata(world, i12, j1, k15, plankBlock, plankMeta);
				} else {
					setBlockAndMetadata(world, i12, j1, k15, plankStairBlock, 0);
				}
				setGrassToDirt(world, i12, j1 - 1, k15);
				j2 = j1 - 1;
				while (!isOpaque(world, i12, j2, k15) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i12, j2, k15, plankBlock, plankMeta);
					setGrassToDirt(world, i12, j2 - 1, k15);
					--j2;
				}
			}
		}
		return true;
	}

	@Override
	public boolean oneWoodType() {
		return true;
	}
}
