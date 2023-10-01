package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityTauredainChieftain;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenTauredainChieftainPyramid extends LOTRWorldGenTauredainHouse {
	public LOTRWorldGenTauredainChieftainPyramid(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int i1;
		int i12;
		int k1;
		int k2;
		int k12;
		int i2;
		if (!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
			return false;
		}
		for (i12 = -10; i12 <= 10; ++i12) {
			for (k1 = -10; k1 <= 10; ++k1) {
				layFoundation(world, i12, k1);
				for (int j1 = 1; j1 <= 14; ++j1) {
					setAir(world, i12, j1, k1);
				}
			}
		}
		for (i12 = -10; i12 <= 10; ++i12) {
			for (k1 = -10; k1 <= 10; ++k1) {
				i2 = Math.abs(i12);
				k2 = Math.abs(k1);
				if (i2 >= 8 || k2 >= 8) {
					setBlockAndMetadata(world, i12, 0, k1, brickBlock, brickMeta);
					if (k1 < 0 && i12 == 0) {
						setBlockAndMetadata(world, 0, 0, k1, LOTRMod.brick4, 4);
					}
					if (i2 > 9 || k2 > 9 || i2 != 9 && k2 != 9) {
						continue;
					}
					setBlockAndMetadata(world, i12, 0, k1, LOTRMod.brick4, 4);
					continue;
				}
				for (int j1 = 1; j1 <= 4; ++j1) {
					setBlockAndMetadata(world, i12, j1, k1, brickBlock, brickMeta);
				}
				if (i2 <= 1 && k1 <= 0) {
					int step = 4 - (-3 - k1);
					if (step < 0 || step > 4) {
						continue;
					}
					for (int j1 = step + 1; j1 <= 4; ++j1) {
						setAir(world, i12, j1, k1);
					}
					if (step == 0) {
						setBlockAndMetadata(world, -1, 0, k1, brickBlock, brickMeta);
						setBlockAndMetadata(world, 0, 0, k1, LOTRMod.brick4, 4);
						setBlockAndMetadata(world, 1, 0, k1, brickBlock, brickMeta);
						continue;
					}
					setBlockAndMetadata(world, -1, step, k1, brickStairBlock, 2);
					setBlockAndMetadata(world, 0, step, k1, LOTRMod.stairsTauredainBrickObsidian, 2);
					setBlockAndMetadata(world, 1, step, k1, brickStairBlock, 2);
					continue;
				}
				if ((i2 != 7 || k1 % 2 != 0) && (k2 != 7 || i12 % 2 != 0)) {
					continue;
				}
				setBlockAndMetadata(world, i12, 1, k1, brickWallBlock, brickWallMeta);
				placeTauredainTorch(world, i12, 2, k1);
			}
		}
		for (int k13 = -2; k13 <= 4; ++k13) {
			setBlockAndMetadata(world, 0, 4, k13, LOTRMod.brick4, 4);
		}
		for (i12 = -4; i12 <= 4; ++i12) {
			setBlockAndMetadata(world, i12, 4, 0, LOTRMod.brick4, 4);
		}
		for (int i13 : new int[]{-5, 5}) {
			for (int k14 : new int[]{-5, 5}) {
				for (int i22 = i13 - 1; i22 <= i13 + 1; ++i22) {
					for (int k22 = k14 - 1; k22 <= k14 + 1; ++k22) {
						int i3 = Math.abs(i22 - i13);
						int k3 = Math.abs(k22 - k14);
						if (i3 == 1 && k3 == 1) {
							setBlockAndMetadata(world, i22, 5, k22, brickSlabBlock, brickSlabMeta);
							continue;
						}
						if (i3 == 1 || k3 == 1) {
							setBlockAndMetadata(world, i22, 5, k22, brickBlock, brickMeta);
							continue;
						}
						setBlockAndMetadata(world, i22, 5, k22, LOTRMod.hearth, 0);
						setBlockAndMetadata(world, i22, 6, k22, Blocks.fire, 0);
					}
				}
			}
		}
		for (int i13 : new int[]{-3, 3}) {
			setBlockAndMetadata(world, i13, 5, -6, brickWallBlock, brickWallMeta);
			for (int j1 = 5; j1 <= 7; ++j1) {
				for (int k15 = -5; k15 <= -3; ++k15) {
					setBlockAndMetadata(world, i13, j1, k15, brickBlock, brickMeta);
				}
				setBlockAndMetadata(world, i13, j1, -1, brickBlock, brickMeta);
				setBlockAndMetadata(world, i13, j1, 1, brickBlock, brickMeta);
				setBlockAndMetadata(world, i13, j1, 3, brickBlock, brickMeta);
			}
			setBlockAndMetadata(world, i13, 5, 4, brickBlock, brickMeta);
			setBlockAndMetadata(world, i13, 7, 0, brickSlabBlock, brickSlabMeta | 8);
			setBlockAndMetadata(world, i13, 5, -2, brickStairBlock, 3);
			setBlockAndMetadata(world, i13, 7, -2, brickStairBlock, 7);
			setBlockAndMetadata(world, i13, 5, 2, brickStairBlock, 2);
			setBlockAndMetadata(world, i13, 7, 2, brickStairBlock, 6);
			for (int k16 = -4; k16 <= 4; ++k16) {
				if (k16 == 0 || Math.abs(k16) == 3) {
					setBlockAndMetadata(world, i13, 8, k16, brickBlock, brickMeta);
					continue;
				}
				setBlockAndMetadata(world, i13, 8, k16, brickSlabBlock, brickSlabMeta);
			}
		}
		for (int i13 : new int[]{-4, 4}) {
			for (int j1 = 5; j1 <= 7; ++j1) {
				setBlockAndMetadata(world, i13, j1, -4, brickBlock, brickMeta);
				setBlockAndMetadata(world, i13, j1, -2, brickBlock, brickMeta);
				setBlockAndMetadata(world, i13, j1, 2, brickBlock, brickMeta);
				setBlockAndMetadata(world, i13, j1, 4, brickBlock, brickMeta);
			}
			setBlockAndMetadata(world, i13, 5, -3, brickBlock, brickMeta);
			setBlockAndMetadata(world, i13, 5, 3, brickBlock, brickMeta);
			setBlockAndMetadata(world, i13, 7, -1, brickSlabBlock, brickSlabMeta | 8);
			setBlockAndMetadata(world, i13, 7, 1, brickSlabBlock, brickSlabMeta | 8);
			for (int k17 = -4; k17 <= 4; ++k17) {
				if (Math.abs(k17) == 4) {
					setBlockAndMetadata(world, i13, 8, k17, brickBlock, brickMeta);
					continue;
				}
				setBlockAndMetadata(world, i13, 8, k17, brickSlabBlock, brickSlabMeta);
			}
		}
		int[] i14 = {-2, 2};
		k1 = i14.length;
		for (i2 = 0; i2 < k1; ++i2) {
			int i13;
			i13 = i14[i2];
			setBlockAndMetadata(world, i13, 5, -6, brickWallBlock, brickWallMeta);
			placeTauredainTorch(world, i13, 6, -6);
			setBlockAndMetadata(world, i13, 5, -5, brickBlock, brickMeta);
			setBlockAndMetadata(world, i13, 6, -5, brickSlabBlock, brickSlabMeta);
			setBlockAndMetadata(world, i13, 8, -4, brickSlabBlock, brickSlabMeta);
			setBlockAndMetadata(world, i13, 8, -3, brickSlabBlock, brickSlabMeta);
			placeArmorStand(world, i13, 5, 2, 0, new ItemStack[]{new ItemStack(LOTRMod.helmetTauredain), new ItemStack(LOTRMod.bodyTauredain), new ItemStack(LOTRMod.legsTauredain), new ItemStack(LOTRMod.bootsTauredain)});
		}
		for (int j1 = 5; j1 <= 7; ++j1) {
			setBlockAndMetadata(world, -2, j1, 4, brickBlock, brickMeta);
			setBlockAndMetadata(world, 2, j1, 4, brickBlock, brickMeta);
			setBlockAndMetadata(world, -1, j1, 3, brickBlock, brickMeta);
			setBlockAndMetadata(world, 1, j1, 3, brickBlock, brickMeta);
		}
		setBlockAndMetadata(world, 0, 7, 3, brickSlabBlock, brickSlabMeta | 8);
		setBlockAndMetadata(world, -2, 5, 3, brickStairBlock, 0);
		setBlockAndMetadata(world, -2, 7, 3, brickStairBlock, 4);
		setBlockAndMetadata(world, 2, 5, 3, brickStairBlock, 1);
		setBlockAndMetadata(world, 2, 7, 3, brickStairBlock, 5);
		setBlockAndMetadata(world, -1, 7, 4, brickSlabBlock, brickSlabMeta | 8);
		setBlockAndMetadata(world, 1, 7, 4, brickSlabBlock, brickSlabMeta | 8);
		for (i1 = -2; i1 <= 2; ++i1) {
			setBlockAndMetadata(world, i1, 8, 3, brickSlabBlock, brickSlabMeta);
			setBlockAndMetadata(world, i1, 8, 4, brickSlabBlock, brickSlabMeta);
		}
		setBlockAndMetadata(world, 0, 8, 3, brickBlock, brickMeta);
		for (k12 = -3; k12 <= 3; ++k12) {
			setBlockAndMetadata(world, -7, 5, k12, brickWallBlock, brickWallMeta);
			setBlockAndMetadata(world, 7, 5, k12, brickWallBlock, brickWallMeta);
		}
		for (i1 = -6; i1 <= -5; ++i1) {
			setBlockAndMetadata(world, i1, 5, -3, brickWallBlock, brickWallMeta);
			setBlockAndMetadata(world, i1, 5, 3, brickWallBlock, brickWallMeta);
		}
		for (i1 = 5; i1 <= 6; ++i1) {
			setBlockAndMetadata(world, i1, 5, -3, brickWallBlock, brickWallMeta);
			setBlockAndMetadata(world, i1, 5, 3, brickWallBlock, brickWallMeta);
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			setBlockAndMetadata(world, i1, 5, 7, brickWallBlock, brickWallMeta);
		}
		for (k12 = 5; k12 <= 6; ++k12) {
			setBlockAndMetadata(world, -3, 5, k12, brickWallBlock, brickWallMeta);
			setBlockAndMetadata(world, 3, 5, k12, brickWallBlock, brickWallMeta);
		}
		for (i1 = -2; i1 <= 2; ++i1) {
			for (k1 = -2; k1 <= 2; ++k1) {
				int j1;
				i2 = Math.abs(i1);
				k2 = Math.abs(k1);
				for (j1 = 8; j1 <= 9; ++j1) {
					setBlockAndMetadata(world, i1, j1, k1, brickBlock, brickMeta);
				}
				if (i2 == 2 && k2 == 2) {
					setBlockAndMetadata(world, i1, 10, k1, brickSlabBlock, brickSlabMeta);
				} else {
					setBlockAndMetadata(world, i1, 10, k1, brickBlock, brickMeta);
				}
				if (i2 == 1 && k2 == 1) {
					for (j1 = 11; j1 <= 12; ++j1) {
						setBlockAndMetadata(world, i1, j1, k1, brickWallBlock, brickWallMeta);
					}
				}
				if (i2 == 0 && k2 == 0) {
					setBlockAndMetadata(world, i1, 10, k1, LOTRMod.hearth, 0);
					setBlockAndMetadata(world, i1, 11, k1, Blocks.fire, 0);
				}
				if (i2 > 1 || k2 > 1) {
					continue;
				}
				setBlockAndMetadata(world, i1, 13, k1, LOTRMod.brick4, 3);
				if (k1 == -1) {
					setBlockAndMetadata(world, i1, 14, -1, LOTRMod.stairsTauredainBrickGold, 2);
					continue;
				}
				if (k1 == 1) {
					setBlockAndMetadata(world, i1, 14, 1, LOTRMod.stairsTauredainBrickGold, 3);
					continue;
				}
				if (i1 == -1) {
					setBlockAndMetadata(world, -1, 14, k1, LOTRMod.stairsTauredainBrickGold, 1);
					continue;
				}
				if (i1 == 1) {
					setBlockAndMetadata(world, 1, 14, k1, LOTRMod.stairsTauredainBrickGold, 0);
					continue;
				}
				setBlockAndMetadata(world, i1, 14, k1, LOTRMod.brick4, 3);
				setBlockAndMetadata(world, i1, 15, k1, LOTRMod.wall4, 3);
				placeTauredainTorch(world, i1, 16, k1);
			}
		}
		setBlockAndMetadata(world, -1, 8, -3, brickBlock, brickMeta);
		setBlockAndMetadata(world, 0, 8, -3, brickSlabBlock, brickSlabMeta | 8);
		setBlockAndMetadata(world, 0, 9, -3, brickSlabBlock, brickSlabMeta);
		setBlockAndMetadata(world, 1, 8, -3, brickBlock, brickMeta);
		placeWallBanner(world, -1, 8, -3, LOTRItemBanner.BannerType.TAUREDAIN, 2);
		placeWallBanner(world, 1, 8, -3, LOTRItemBanner.BannerType.TAUREDAIN, 2);
		placeWallBanner(world, -3, 7, -3, LOTRItemBanner.BannerType.TAUREDAIN, 1);
		placeWallBanner(world, 3, 7, -3, LOTRItemBanner.BannerType.TAUREDAIN, 3);
		setBlockAndMetadata(world, -2, 6, -1, Blocks.torch, 2);
		setBlockAndMetadata(world, -2, 6, 1, Blocks.torch, 2);
		setBlockAndMetadata(world, 2, 6, -1, Blocks.torch, 1);
		setBlockAndMetadata(world, 2, 6, 1, Blocks.torch, 1);
		spawnItemFrame(world, -1, 6, 3, 2, new ItemStack(LOTRMod.tauredainDart));
		spawnItemFrame(world, 1, 6, 3, 2, new ItemStack(LOTRMod.daggerTauredain));
		LOTREntityTauredainChieftain chieftain = new LOTREntityTauredainChieftain(world);
		spawnNPCAndSetHome(chieftain, world, 0, 5, 0, 8);
		return true;
	}

	@Override
	public int getOffset() {
		return 11;
	}

	@Override
	public boolean useStoneBrick() {
		return true;
	}
}
