package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.entity.npc.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingGatehouse extends LOTRWorldGenEasterlingStructureTown {
	public String[] signText = LOTRNames.getRhunVillageName(new Random());
	public boolean enableSigns = true;

	public LOTRWorldGenEasterlingGatehouse(boolean flag) {
		super(flag);
	}

	public LOTRWorldGenEasterlingGatehouse disableSigns() {
		enableSigns = false;
		return this;
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int k16;
		int k12;
		int i1;
		int i12;
		int i13;
		int k13;
		int i2;
		int j1;
		int k2;
		int k14;
		setOriginAndRotation(world, i, j, k, rotation, 4);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i12 = -7; i12 <= 7; ++i12) {
				for (k12 = -4; k12 <= 4; ++k12) {
					int j12 = getTopBlock(world, i12, k12) - 1;
					if (isSurface(world, i12, j12, k12)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i12 = -7; i12 <= 7; ++i12) {
			for (k12 = -3; k12 <= 3; ++k12) {
				int j13;
				i2 = Math.abs(i12);
				k2 = Math.abs(k12);
				for (j13 = 1; j13 <= 12; ++j13) {
					setAir(world, i12, j13, k12);
				}
				if (i2 <= 3 && k2 <= 3 || i2 <= 6 && k2 <= 2 || i2 <= 7 && k2 <= 1) {
					for (j13 = 0; (j13 >= 0 || !isOpaque(world, i12, j13, k12)) && getY(j13) >= 0; --j13) {
						setBlockAndMetadata(world, i12, j13, k12, brickBlock, brickMeta);
						setGrassToDirt(world, i12, j13 - 1, k12);
					}
				}
				if (i2 != 3 || k2 > 2) {
					continue;
				}
				if (k2 == 0) {
					for (j13 = 1; j13 <= 6; ++j13) {
						setBlockAndMetadata(world, i12, j13, k12, pillarBlock, pillarMeta);
					}
					continue;
				}
				for (j13 = 1; j13 <= 6; ++j13) {
					setBlockAndMetadata(world, i12, j13, k12, brickBlock, brickMeta);
				}
			}
		}
		for (i12 = -2; i12 <= 2; ++i12) {
			setBlockAndMetadata(world, i12, 0, 0, pillarBlock, pillarMeta);
			setBlockAndMetadata(world, i12, 0, -2, brickStairBlock, 3);
			setBlockAndMetadata(world, i12, 0, 2, brickStairBlock, 2);
		}
		for (int k15 = -1; k15 <= 1; ++k15) {
			setBlockAndMetadata(world, -2, 6, k15, brickStairBlock, 4);
			setBlockAndMetadata(world, 2, 6, k15, brickStairBlock, 5);
		}
		int[] k15 = {-3, 3};
		k12 = k15.length;
		for (i2 = 0; i2 < k12; ++i2) {
			k16 = k15[i2];
			for (int i14 : new int[]{-3, 3}) {
				for (int j14 = 1; j14 <= 6; ++j14) {
					setBlockAndMetadata(world, i14, j14, k16, brickRedBlock, brickRedMeta);
				}
			}
			setBlockAndMetadata(world, -2, 6, k16, brickRedStairBlock, 4);
			setBlockAndMetadata(world, 2, 6, k16, brickRedStairBlock, 5);
			setBlockAndMetadata(world, -3, 7, k16, brickRedStairBlock, 1);
			setBlockAndMetadata(world, -2, 7, k16, brickRedBlock, brickRedMeta);
			setBlockAndMetadata(world, -1, 7, k16, brickRedBlock, brickRedMeta);
			setBlockAndMetadata(world, 0, 7, k16, brickRedStairBlock, k16 > 0 ? 7 : 6);
			setBlockAndMetadata(world, 1, 7, k16, brickRedBlock, brickRedMeta);
			setBlockAndMetadata(world, 2, 7, k16, brickRedBlock, brickRedMeta);
			setBlockAndMetadata(world, 3, 7, k16, brickRedStairBlock, 0);
		}
		for (int i15 = -2; i15 <= 2; ++i15) {
			for (k12 = -2; k12 <= 2; ++k12) {
				if (k12 == 0) {
					setBlockAndMetadata(world, i15, 7, 0, brickRedBlock, brickRedMeta);
					continue;
				}
				setBlockAndMetadata(world, i15, 7, k12, pillarBlock, pillarMeta);
			}
		}
		for (int k161 : new int[]{-2, 2}) {
			for (int i16 = -2; i16 <= 2; ++i16) {
				for (int j15 = 1; j15 <= (Math.abs(i16) <= 1 ? 7 : 6); ++j15) {
					setBlockAndMetadata(world, i16, j15, k161, gateBlock, k161 > 0 ? 2 : 3);
				}
			}
			setBlockAndMetadata(world, -1, 8, k161, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, 0, 8, k161, brickBlock, brickMeta);
			setBlockAndMetadata(world, 1, 8, k161, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, 0, 9, k161, Blocks.lever, 14);
		}
		for (int j16 = 7; j16 <= 10; ++j16) {
			setBlockAndMetadata(world, -3, j16, -2, brickBlock, brickMeta);
			setBlockAndMetadata(world, 3, j16, -2, brickBlock, brickMeta);
			setBlockAndMetadata(world, -3, j16, 2, brickBlock, brickMeta);
			setBlockAndMetadata(world, 3, j16, 2, brickBlock, brickMeta);
		}
		int[] j16 = {-3, 3};
		k12 = j16.length;
		for (i2 = 0; i2 < k12; ++i2) {
			k16 = j16[i2];
			setBlockAndMetadata(world, -3, 8, k16, brickStairBlock, 5);
			setBlockAndMetadata(world, -2, 8, k16, brickStairBlock, k16 > 0 ? 3 : 2);
			setBlockAndMetadata(world, -1, 8, k16, brickBlock, brickMeta);
			setBlockAndMetadata(world, 0, 8, k16, brickStairBlock, k16 > 0 ? 3 : 2);
			setBlockAndMetadata(world, 1, 8, k16, brickBlock, brickMeta);
			setBlockAndMetadata(world, 2, 8, k16, brickStairBlock, k16 > 0 ? 3 : 2);
			setBlockAndMetadata(world, 3, 8, k16, brickStairBlock, 4);
			setBlockAndMetadata(world, -3, 9, k16, brickWallBlock, brickWallMeta);
			setBlockAndMetadata(world, -1, 9, k16, brickWallBlock, brickWallMeta);
			setBlockAndMetadata(world, 1, 9, k16, brickWallBlock, brickWallMeta);
			setBlockAndMetadata(world, 3, 9, k16, brickWallBlock, brickWallMeta);
			setBlockAndMetadata(world, -3, 10, k16, brickBlock, brickMeta);
			setBlockAndMetadata(world, -2, 10, k16, brickStairBlock, k16 > 0 ? 7 : 6);
			setBlockAndMetadata(world, -1, 10, k16, brickBlock, brickMeta);
			setBlockAndMetadata(world, 0, 10, k16, brickStairBlock, k16 > 0 ? 7 : 6);
			setBlockAndMetadata(world, 1, 10, k16, brickBlock, brickMeta);
			setBlockAndMetadata(world, 2, 10, k16, brickStairBlock, k16 > 0 ? 7 : 6);
			setBlockAndMetadata(world, 3, 10, k16, brickBlock, brickMeta);
			for (int i17 = -3; i17 <= 3; ++i17) {
				setBlockAndMetadata(world, i17, 11, k16, brickBlock, brickMeta);
			}
		}
		for (k14 = -2; k14 <= 2; ++k14) {
			setBlockAndMetadata(world, -3, 11, k14, brickBlock, brickMeta);
			setBlockAndMetadata(world, 3, 11, k14, brickBlock, brickMeta);
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			for (k12 = -3; k12 <= 3; ++k12) {
				i2 = Math.abs(i1);
				k2 = Math.abs(k12);
				if (i2 + k2 <= 1) {
					setBlockAndMetadata(world, i1, 12, k12, brickSlabBlock, brickSlabMeta | 8);
				} else {
					setBlockAndMetadata(world, i1, 12, k12, brickBlock, brickMeta);
				}
				if (i2 != 2 || k2 != 2) {
					continue;
				}
				setBlockAndMetadata(world, i1, 11, k12, brickSlabBlock, brickSlabMeta | 8);
			}
		}
		setBlockAndMetadata(world, -2, 10, -2, Blocks.torch, 2);
		setBlockAndMetadata(world, 2, 10, -2, Blocks.torch, 1);
		setBlockAndMetadata(world, -2, 10, 2, Blocks.torch, 2);
		setBlockAndMetadata(world, 2, 10, 2, Blocks.torch, 1);
		for (i1 = -4; i1 <= 4; ++i1) {
			for (k12 = -4; k12 <= 4; ++k12) {
				i2 = Math.abs(i1);
				k2 = Math.abs(k12);
				if (i2 != 4 && k2 != 4) {
					continue;
				}
				if ((i2 + k2) % 2 == 0) {
					setBlockAndMetadata(world, i1, 13, k12, roofSlabBlock, roofSlabMeta);
					if (i2 > 2 && k2 > 2) {
						continue;
					}
					setBlockAndMetadata(world, i1, 12, k12, fenceBlock, fenceMeta);
					continue;
				}
				if (k2 == 4 && i2 == 3) {
					setBlockAndMetadata(world, i1, 12, k12, roofStairBlock, k12 > 0 ? 7 : 6);
					continue;
				}
				if (i2 == 4 && k2 == 3) {
					setBlockAndMetadata(world, i1, 12, k12, roofStairBlock, i1 > 0 ? 4 : 5);
					continue;
				}
				setBlockAndMetadata(world, i1, 12, k12, roofSlabBlock, roofSlabMeta | 8);
			}
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			setBlockAndMetadata(world, i1, 13, -3, roofStairBlock, 2);
			setBlockAndMetadata(world, i1, 13, 3, roofStairBlock, 3);
		}
		for (k14 = -3; k14 <= 3; ++k14) {
			setBlockAndMetadata(world, -3, 13, k14, roofStairBlock, 1);
			setBlockAndMetadata(world, 3, 13, k14, roofStairBlock, 0);
		}
		for (i1 = -2; i1 <= 2; ++i1) {
			for (k12 = -2; k12 <= 2; ++k12) {
				i2 = Math.abs(i1);
				k2 = Math.abs(k12);
				setBlockAndMetadata(world, i1, 13, k12, roofBlock, roofMeta);
				if (i2 == 2 && k2 == 2) {
					setBlockAndMetadata(world, i1, 14, k12, roofSlabBlock, roofSlabMeta);
				} else {
					setBlockAndMetadata(world, i1, 14, k12, roofBlock, roofMeta);
				}
				if (i2 == 2 && k2 == 0 || k2 == 2 && i2 == 0) {
					setBlockAndMetadata(world, i1, 15, k12, roofSlabBlock, roofSlabMeta);
				}
				if (i2 > 1 || k2 > 1) {
					continue;
				}
				setBlockAndMetadata(world, i1, 15, k12, roofBlock, roofMeta);
			}
		}
		setBlockAndMetadata(world, 0, 16, 0, roofBlock, roofMeta);
		setBlockAndMetadata(world, 0, 17, 0, roofBlock, roofMeta);
		setBlockAndMetadata(world, 0, 18, 0, roofWallBlock, roofWallMeta);
		setBlockAndMetadata(world, 0, 19, 0, roofWallBlock, roofWallMeta);
		setBlockAndMetadata(world, 0, 16, -1, roofStairBlock, 2);
		setBlockAndMetadata(world, 0, 16, 1, roofStairBlock, 3);
		setBlockAndMetadata(world, -1, 16, 0, roofStairBlock, 1);
		setBlockAndMetadata(world, 1, 16, 0, roofStairBlock, 0);
		for (i1 = -7; i1 <= 7; ++i1) {
			int i22 = Math.abs(i1);
			if (i22 >= 4 && i22 <= 6) {
				for (int k17 : new int[]{-2, 2}) {
					for (int j17 = 1; j17 <= 8; ++j17) {
						if (j17 == 1) {
							setBlockAndMetadata(world, i1, 1, k17, brickRedBlock, brickRedMeta);
						} else {
							setBlockAndMetadata(world, i1, j17, k17, brickBlock, brickMeta);
						}
						if (j17 != 3 || i22 != 5) {
							continue;
						}
						setBlockAndMetadata(world, i1, j17, k17, brickCarvedBlock, brickCarvedMeta);
					}
					setBlockAndMetadata(world, i1, 9, k17, brickWallBlock, brickWallMeta);
				}
			}
			if (i22 < 4 || i22 > 7) {
				continue;
			}
			for (k13 = -1; k13 <= 2; ++k13) {
				for (int j18 = 1; j18 <= 5; ++j18) {
					if (k13 == 0 || j18 == 1) {
						setBlockAndMetadata(world, i1, j18, k13, brickRedBlock, brickRedMeta);
						continue;
					}
					setBlockAndMetadata(world, i1, j18, k13, brickBlock, brickMeta);
				}
			}
		}
		for (int step = 0; step <= 1; ++step) {
			int j19 = 6 + step;
			for (k13 = -1; k13 <= 1; ++k13) {
				setBlockAndMetadata(world, -4 + step, j19, k13, k13 == 0 ? brickRedStairBlock : brickStairBlock, 1);
				setBlockAndMetadata(world, 4 - step, j19, k13, k13 == 0 ? brickRedStairBlock : brickStairBlock, 0);
			}
		}
		setBlockAndMetadata(world, -7, 5, -2, brickStairBlock, 5);
		setBlockAndMetadata(world, -7, 6, -2, brickBlock, brickMeta);
		setBlockAndMetadata(world, -7, 7, -2, brickStairBlock, 3);
		setBlockAndMetadata(world, 7, 5, -2, brickStairBlock, 4);
		setBlockAndMetadata(world, 7, 6, -2, brickBlock, brickMeta);
		setBlockAndMetadata(world, 7, 7, -2, brickStairBlock, 3);
		placeWallBanner(world, -5, 6, -2, bannerType, 2);
		placeWallBanner(world, 5, 6, -2, bannerType, 2);
		placeWallBanner(world, -5, 6, 2, bannerType, 0);
		placeWallBanner(world, 5, 6, 2, bannerType, 0);
		if (enableSigns && signText != null) {
			placeSign(world, -3, 3, -4, Blocks.wall_sign, 2, signText);
			placeSign(world, 3, 3, -4, Blocks.wall_sign, 2, signText);
		}
		int maxStep = 12;
		for (k12 = 2; k12 <= 2; ++k12) {
			int j2;
			int step;
			int j110;
			for (step = 0; step < maxStep && !isOpaque(world, i13 = -8 - step, j110 = 5 - step, k12); ++step) {
				setBlockAndMetadata(world, i13, j110, k12, brickStairBlock, 1);
				setGrassToDirt(world, i13, j110 - 1, k12);
				j2 = j110 - 1;
				while (!isOpaque(world, i13, j2, k12) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i13, j2, k12, brickBlock, brickMeta);
					setGrassToDirt(world, i13, j2 - 1, k12);
					--j2;
				}
			}
			for (step = 0; step < maxStep && !isOpaque(world, i13 = 8 + step, j1 = 5 - step, k12); ++step) {
				setBlockAndMetadata(world, i13, j1, k12, brickStairBlock, 0);
				setGrassToDirt(world, i13, j1 - 1, k12);
				j2 = j1 - 1;
				while (!isOpaque(world, i13, j2, k12) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i13, j2, k12, brickBlock, brickMeta);
					setGrassToDirt(world, i13, j2 - 1, k12);
					--j2;
				}
			}
		}
		int men = 2;
		for (int l = 0; l < men; ++l) {
			int k17;
			i13 = 0;
			int j111 = 8;
			k17 = 0;
			LOTREntityEasterlingWarrior guard = new LOTREntityEasterlingWarrior(world);
			guard.spawnRidingHorse = false;
			spawnNPCAndSetHome(guard, world, i13, j111, k17, 8);
		}
		return true;
	}

	public LOTRWorldGenEasterlingGatehouse setSignText(String[] s) {
		signText = s;
		return this;
	}
}
