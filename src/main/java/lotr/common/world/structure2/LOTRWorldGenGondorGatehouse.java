package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.npc.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGondorGatehouse extends LOTRWorldGenGondorStructure {
	public String[] signText = LOTRNames.getGondorVillageName(new Random());

	public LOTRWorldGenGondorGatehouse(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int i1;
		int i12;
		int i13;
		int j12;
		int i142;
		int k1;
		int i2;
		int k12;
		int i22;
		int k2;
		int k13;
		int j13;
		setOriginAndRotation(world, i, j, k, rotation, 4);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i15 = -6; i15 <= 6; ++i15) {
				for (int k14 = -5; k14 <= 5; ++k14) {
					j12 = getTopBlock(world, i15, k14) - 1;
					if (!isSurface(world, i15, j12, k14)) {
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
		for (i13 = -3; i13 <= 3; ++i13) {
			for (k1 = -3; k1 <= 3; ++k1) {
				i22 = Math.abs(i13);
				k2 = Math.abs(k1);
				for (j12 = 0; (j12 >= 0 || !isOpaque(world, i13, j12, k1)) && getY(j12) >= 0; --j12) {
					setBlockAndMetadata(world, i13, j12, k1, cobbleBlock, cobbleMeta);
					setGrassToDirt(world, i13, j12 - 1, k1);
				}
				for (j12 = 1; j12 <= 14; ++j12) {
					setAir(world, i13, j12, k1);
				}
				if (i22 == 3 && k2 == 3) {
					for (j12 = 1; j12 <= 10; ++j12) {
						setBlockAndMetadata(world, i13, j12, k1, pillarBlock, pillarMeta);
					}
				} else {
					if (i22 == 3) {
						for (j12 = 1; j12 <= 6; ++j12) {
							setBlockAndMetadata(world, i13, j12, k1, brickBlock, brickMeta);
						}
					}
					setBlockAndMetadata(world, i13, 7, k1, brickBlock, brickMeta);
				}
				if (i22 > 3 || k2 > 3) {
					continue;
				}
				if (i22 == 3 || k2 == 3) {
					setBlockAndMetadata(world, i13, 11, k1, rockSlabDoubleBlock, rockSlabDoubleMeta);
					continue;
				}
				setBlockAndMetadata(world, i13, 11, k1, rockSlabBlock, rockSlabMeta | 8);
			}
		}
		for (i13 = -2; i13 <= 2; ++i13) {
			setBlockAndMetadata(world, i13, 0, -1, cobbleStairBlock, 3);
			setBlockAndMetadata(world, i13, 0, 1, cobbleStairBlock, 2);
			setBlockAndMetadata(world, i13, -1, 0, cobbleBlock, cobbleMeta);
			setGrassToDirt(world, i13, -2, 0);
			setAir(world, i13, 0, 0);
		}
		for (i13 = -2; i13 <= 2; ++i13) {
			for (j1 = 1; j1 <= 7; ++j1) {
				if (j1 > 6 && i13 != 0) {
					continue;
				}
				setBlockAndMetadata(world, i13, j1, -1, gateBlock, 10);
				setBlockAndMetadata(world, i13, j1, 1, LOTRMod.gateIronBars, 10);
			}
		}
		for (int k14 : new int[]{-3, 3}) {
			setBlockAndMetadata(world, -2, 6, k14, brickStairBlock, 4);
			setBlockAndMetadata(world, 2, 6, k14, brickStairBlock, 5);
			setBlockAndMetadata(world, -2, 5, k14, Blocks.torch, 2);
			setBlockAndMetadata(world, 2, 5, k14, Blocks.torch, 1);
			for (int i16 = -2; i16 <= 2; ++i16) {
				int i23 = Math.abs(i16);
				setBlockAndMetadata(world, i16, 8, k14, rockSlabDoubleBlock, rockSlabDoubleMeta);
				if (i23 % 2 == 0) {
					setBlockAndMetadata(world, i16, 9, k14, LOTRMod.gateIronBars, 2);
				} else {
					setBlockAndMetadata(world, i16, 9, k14, brickBlock, brickMeta);
				}
				if (i23 == 0) {
					setBlockAndMetadata(world, i16, 10, k14, LOTRMod.brick, 5);
					continue;
				}
				setBlockAndMetadata(world, i16, 10, k14, brickBlock, brickMeta);
			}
		}
		int[] i17 = {-3, 3};
		j1 = i17.length;
		for (i22 = 0; i22 < j1; ++i22) {
			i142 = i17[i22];
			for (int k15 : new int[]{-2, 2}) {
				setBlockAndMetadata(world, i142, 8, k15, rockSlabDoubleBlock, rockSlabDoubleMeta);
				setBlockAndMetadata(world, i142, 9, k15, brickBlock, brickMeta);
				setBlockAndMetadata(world, i142, 10, k15, brickBlock, brickMeta);
			}
			setBlockAndMetadata(world, i142, 10, -1, brickStairBlock, 7);
			setBlockAndMetadata(world, i142, 10, 1, brickStairBlock, 6);
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			setBlockAndMetadata(world, i1, 11, -4, brickStairBlock, 6);
			setBlockAndMetadata(world, i1, 11, 4, brickStairBlock, 7);
		}
		for (int k16 = -3; k16 <= 3; ++k16) {
			setBlockAndMetadata(world, -4, 11, k16, brickStairBlock, 5);
			setBlockAndMetadata(world, 4, 11, k16, brickStairBlock, 4);
		}
		for (i1 = -4; i1 <= 4; ++i1) {
			for (k1 = -4; k1 <= 4; ++k1) {
				i22 = Math.abs(i1);
				k2 = Math.abs(k1);
				if (i22 > 3 && k2 > 3 || i22 != 4 && k2 != 4) {
					continue;
				}
				//noinspection BadOddness
				if ((i22 + k2) % 2 == 1) {
					setBlockAndMetadata(world, i1, 12, k1, brickBlock, brickMeta);
					setBlockAndMetadata(world, i1, 13, k1, brickSlabBlock, brickSlabMeta);
					continue;
				}
				setBlockAndMetadata(world, i1, 12, k1, brickWallBlock, brickWallMeta);
			}
		}
		setBlockAndMetadata(world, 0, 8, -1, plankBlock, plankMeta);
		setBlockAndMetadata(world, 0, 8, 0, fenceBlock, fenceMeta);
		setAir(world, 0, 7, 0);
		setBlockAndMetadata(world, 0, 8, 1, plankBlock, plankMeta);
		setBlockAndMetadata(world, 0, 9, -1, Blocks.lever, 14);
		setBlockAndMetadata(world, 0, 9, 1, Blocks.lever, 14);
		for (int i1421 : new int[]{-1, 1}) {
			for (j12 = 8; j12 <= 11; ++j12) {
				setBlockAndMetadata(world, i1421, j12, 2, Blocks.ladder, 2);
			}
		}
		setBlockAndMetadata(world, -2, 10, -2, Blocks.torch, 2);
		setBlockAndMetadata(world, 2, 10, -2, Blocks.torch, 1);
		setBlockAndMetadata(world, -2, 10, 2, Blocks.torch, 2);
		setBlockAndMetadata(world, 2, 10, 2, Blocks.torch, 1);
		placeWallBanner(world, 1, 10, -3, bannerType2, 0);
		placeWallBanner(world, -1, 10, -3, bannerType, 0);
		setBlockAndMetadata(world, -3, 12, -3, Blocks.torch, 3);
		setBlockAndMetadata(world, 3, 12, -3, Blocks.torch, 3);
		setBlockAndMetadata(world, -3, 12, 3, Blocks.torch, 4);
		setBlockAndMetadata(world, 3, 12, 3, Blocks.torch, 4);
		placeWallBanner(world, -3, 7, -3, bannerType2, 2);
		placeWallBanner(world, 3, 7, -3, bannerType, 2);
		placeWallBanner(world, 3, 7, 3, bannerType2, 0);
		placeWallBanner(world, -3, 7, 3, bannerType, 0);
		if (signText != null) {
			placeSign(world, -3, 3, -4, Blocks.wall_sign, 2, signText);
			placeSign(world, 3, 3, -4, Blocks.wall_sign, 2, signText);
		}
		for (i12 = -5; i12 <= 5; ++i12) {
			i2 = Math.abs(i12);
			if (i2 >= 4) {
				for (k12 = -1; k12 <= 1; ++k12) {
					for (j13 = 4; (j13 >= 0 || !isOpaque(world, i12, j13, k12)) && getY(j13) >= 0; --j13) {
						setBlockAndMetadata(world, i12, j13, k12, brickBlock, brickMeta);
						setGrassToDirt(world, i12, j13 - 1, k12);
					}
				}
				k12 = -2;
				for (j13 = 7; (j13 >= 0 || !isOpaque(world, i12, j13, k12)) && getY(j13) >= 0; --j13) {
					setBlockAndMetadata(world, i12, j13, k12, brickBlock, brickMeta);
					setGrassToDirt(world, i12, j13 - 1, k12);
				}
				setBlockAndMetadata(world, i12, 4, k12, brick2Block, brick2Meta);
				setBlockAndMetadata(world, i12, 8, k12, rockWallBlock, rockWallMeta);
			}
			if (i2 == 5) {
				k12 = -3;
				for (j13 = 7; (j13 >= 0 || !isOpaque(world, i12, j13, k12)) && getY(j13) >= 0; --j13) {
					setBlockAndMetadata(world, i12, j13, k12, pillarBlock, pillarMeta);
					setGrassToDirt(world, i12, j13 - 1, k12);
				}
				setBlockAndMetadata(world, i12, 8, k12, rockWallBlock, rockWallMeta);
			}
			if (i2 != 4) {
				continue;
			}
			k12 = -3;
			setBlockAndMetadata(world, i12, 5, k12, brickStairBlock, 6);
			setBlockAndMetadata(world, i12, 6, k12, rockWallBlock, rockWallMeta);
		}
		for (k13 = -1; k13 <= 1; ++k13) {
			setBlockAndMetadata(world, -3, 7, k13, brickStairBlock, 1);
			setBlockAndMetadata(world, -4, 6, k13, brickStairBlock, 1);
			setBlockAndMetadata(world, -5, 5, k13, brickStairBlock, 1);
			setBlockAndMetadata(world, -4, 5, k13, brickBlock, brickMeta);
			setBlockAndMetadata(world, 3, 7, k13, brickStairBlock, 0);
			setBlockAndMetadata(world, 4, 6, k13, brickStairBlock, 0);
			setBlockAndMetadata(world, 5, 5, k13, brickStairBlock, 0);
			setBlockAndMetadata(world, 4, 5, k13, brickBlock, brickMeta);
		}
		for (i12 = -8; i12 <= 8; ++i12) {
			i2 = Math.abs(i12);
			if (i2 < 6) {
				continue;
			}
			for (k12 = 0; k12 <= 1; ++k12) {
				for (j13 = 4; (j13 >= 0 || !isOpaque(world, i12, j13, k12)) && getY(j13) >= 0; --j13) {
					setBlockAndMetadata(world, i12, j13, k12, brickBlock, brickMeta);
					setGrassToDirt(world, i12, j13 - 1, k12);
				}
			}
		}
		for (k13 = 0; k13 <= 1; ++k13) {
			int j2;
			int step;
			int maxStep = 12;
			for (step = 0; step < maxStep && !isOpaque(world, i142 = -9 - step, j12 = 4 - step, k13); ++step) {
				setBlockAndMetadata(world, i142, j12, k13, brickStairBlock, 1);
				setGrassToDirt(world, i142, j12 - 1, k13);
				j2 = j12 - 1;
				while (!isOpaque(world, i142, j2, k13) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i142, j2, k13, brickBlock, brickMeta);
					setGrassToDirt(world, i142, j2 - 1, k13);
					--j2;
				}
			}
			for (step = 0; step < maxStep && !isOpaque(world, i142 = 9 + step, j12 = 4 - step, k13); ++step) {
				setBlockAndMetadata(world, i142, j12, k13, brickStairBlock, 0);
				setGrassToDirt(world, i142, j12 - 1, k13);
				j2 = j12 - 1;
				while (!isOpaque(world, i142, j2, k13) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i142, j2, k13, brickBlock, brickMeta);
					setGrassToDirt(world, i142, j2 - 1, k13);
					--j2;
				}
			}
		}
		for (i12 = -9; i12 <= 9; ++i12) {
			i2 = Math.abs(i12);
			if (i2 == 5 || i2 == 8) {
				setBlockAndMetadata(world, i12, 3, 1, LOTRMod.brick, 5);
				continue;
			}
			if (i2 < 4) {
				continue;
			}
			setBlockAndMetadata(world, i12, 3, 1, brickStairBlock, 7);
		}
		for (int i1421 : new int[]{-1, 1}) {
			j12 = 8;
			int k17 = 0;
			LOTREntityNPC levyman = (LOTREntityNPC) LOTREntities.createEntityByClass(strFief.getLevyClasses()[0], world);
			spawnNPCAndSetHome(levyman, world, i1421, j12, k17, 8);
		}
		return true;
	}

	public LOTRWorldGenGondorGatehouse setSignText(String[] s) {
		signText = s;
		return this;
	}
}
