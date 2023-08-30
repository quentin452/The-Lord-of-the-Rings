package lotr.common.world.structure2;

import java.util.Random;

import com.google.common.math.IntMath;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenDaleVillageTower extends LOTRWorldGenDaleStructure {
	public LOTRWorldGenDaleVillageTower(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int i1;
		int i12;
		setOriginAndRotation(world, i, j, k, rotation, 3);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i12 = -3; i12 <= 3; ++i12) {
				for (k1 = -3; k1 <= 3; ++k1) {
					int j1 = getTopBlock(world, i12, k1) - 1;
					Block block = getBlock(world, i12, j1, k1);
					if (block == Blocks.grass) {
						continue;
					}
					return false;
				}
			}
		}
		for (i12 = -3; i12 <= 3; ++i12) {
			for (k1 = -3; k1 <= 3; ++k1) {
				int j1;
				int i2 = Math.abs(i12);
				int k2 = Math.abs(k1);
				if (i2 <= 2 && k2 <= 2) {
					for (j1 = 0; (j1 == 0 || !isOpaque(world, i12, j1, k1)) && getY(j1) >= 0; --j1) {
						setBlockAndMetadata(world, i12, j1, k1, floorBlock, floorMeta);
						setGrassToDirt(world, i12, j1 - 1, k1);
					}
					for (j1 = 1; j1 <= 10; ++j1) {
						setAir(world, i12, j1, k1);
					}
				}
				if (i2 == 2 && k2 == 3 || k2 == 2 && i2 == 3) {
					j1 = 1;
					while (!isOpaque(world, i12, j1, k1) && getY(j1) >= 0) {
						setBlockAndMetadata(world, i12, j1, k1, brickWallBlock, brickWallMeta);
						--j1;
					}
				}
				if (i2 > 2 || k2 > 2) {
					continue;
				}
				if (i2 == 2 && k2 == 2) {
					for (j1 = 1; j1 <= 6; ++j1) {
						setBlockAndMetadata(world, i12, j1, k1, brickBlock, brickMeta);
					}
				} else if (i2 == 2 || k2 == 2) {
					for (j1 = 4; j1 <= 6; ++j1) {
						setBlockAndMetadata(world, i12, j1, k1, brickBlock, brickMeta);
					}
				}
				if (i2 == 2 && k2 == 2) {
					setBlockAndMetadata(world, i12, 7, k1, brickWallBlock, brickWallMeta);
				}
				if (i2 == 2 && k2 == 1 || k2 == 2 && i2 == 1) {
					setBlockAndMetadata(world, i12, 7, k1, brickBlock, brickMeta);
				}
				if (i2 == 2 && k2 == 0 || k2 == 2 && i2 == 0) {
					setBlockAndMetadata(world, i12, 7, k1, barsBlock, 0);
					setBlockAndMetadata(world, i12, 8, k1, brickBlock, brickMeta);
					setBlockAndMetadata(world, i12, 9, k1, brickSlabBlock, brickSlabMeta);
				}
				if (i2 > 1 || k2 > 1) {
					continue;
				}
				if (i2 == 1 && k2 == 1) {
					setBlockAndMetadata(world, i12, 6, k1, brickSlabBlock, brickSlabMeta | 8);
					setBlockAndMetadata(world, i12, 7, k1, brickWallBlock, brickWallMeta);
					for (j1 = 8; j1 <= 11; ++j1) {
						setBlockAndMetadata(world, i12, j1, k1, brickBlock, brickMeta);
					}
					setBlockAndMetadata(world, i12, 12, k1, brickWallBlock, brickWallMeta);
				}
				if (i2 == 1 && k2 == 0 || k2 == 1 && i2 == 0) {
					setBlockAndMetadata(world, i12, 9, k1, brickBlock, brickMeta);
					setBlockAndMetadata(world, i12, 10, k1, brickBlock, brickMeta);
				}
				if (i2 != 0 || k2 != 0) {
					continue;
				}
				setBlockAndMetadata(world, i12, 9, k1, brickBlock, brickMeta);
				setBlockAndMetadata(world, i12, 10, k1, brickBlock, brickMeta);
				setBlockAndMetadata(world, i12, 11, k1, LOTRMod.hearth, 0);
				setBlockAndMetadata(world, i12, 12, k1, Blocks.fire, 0);
				setBlockAndMetadata(world, i12, 13, k1, roofBlock, roofMeta);
				setBlockAndMetadata(world, i12, 14, k1, roofSlabBlock, roofSlabMeta);
			}
		}
		for (int i13 : new int[]{-2, 2}) {
			setBlockAndMetadata(world, i13, 3, -1, brickStairBlock, 7);
			setBlockAndMetadata(world, i13, 3, 1, brickStairBlock, 6);
		}
		for (int k12 : new int[]{-2, 2}) {
			setBlockAndMetadata(world, -1, 3, k12, brickStairBlock, 4);
			setBlockAndMetadata(world, 1, 3, k12, brickStairBlock, 5);
		}
		for (int i14 = -3; i14 <= 3; ++i14) {
			setBlockAndMetadata(world, i14, 5, -3, brickStairBlock, 6);
			setBlockAndMetadata(world, i14, 5, 3, brickStairBlock, 7);
			if (IntMath.mod(i14, 2) != 1) {
				continue;
			}
			setBlockAndMetadata(world, i14, 6, -3, brickWallBlock, brickWallMeta);
			setBlockAndMetadata(world, i14, 6, 3, brickWallBlock, brickWallMeta);
		}
		for (int k13 = -2; k13 <= 2; ++k13) {
			setBlockAndMetadata(world, -3, 5, k13, brickStairBlock, 5);
			setBlockAndMetadata(world, 3, 5, k13, brickStairBlock, 4);
			if (IntMath.mod(k13, 2) != 1) {
				continue;
			}
			setBlockAndMetadata(world, -3, 6, k13, brickWallBlock, brickWallMeta);
			setBlockAndMetadata(world, 3, 6, k13, brickWallBlock, brickWallMeta);
		}
		for (int i13 : new int[]{-2, 2}) {
			setBlockAndMetadata(world, i13, 8, -1, brickStairBlock, 2);
			setBlockAndMetadata(world, i13, 8, 1, brickStairBlock, 3);
		}
		for (int k12 : new int[]{-2, 2}) {
			setBlockAndMetadata(world, -1, 8, k12, brickStairBlock, 1);
			setBlockAndMetadata(world, 1, 8, k12, brickStairBlock, 0);
		}
		setBlockAndMetadata(world, -1, 8, 0, brickStairBlock, 4);
		setBlockAndMetadata(world, 1, 8, 0, brickStairBlock, 5);
		setBlockAndMetadata(world, 0, 8, -1, brickStairBlock, 7);
		setBlockAndMetadata(world, 0, 8, 1, brickStairBlock, 6);
		for (i1 = -2; i1 <= 2; ++i1) {
			setBlockAndMetadata(world, i1, 10, -2, roofStairBlock, 2);
			setBlockAndMetadata(world, i1, 10, 2, roofStairBlock, 3);
		}
		for (int k14 = -1; k14 <= 1; ++k14) {
			setBlockAndMetadata(world, -2, 10, k14, roofStairBlock, 1);
			setBlockAndMetadata(world, 2, 10, k14, roofStairBlock, 0);
		}
		setBlockAndMetadata(world, -1, 11, 0, brickStairBlock, 1);
		setBlockAndMetadata(world, 1, 11, 0, brickStairBlock, 0);
		setBlockAndMetadata(world, 0, 11, -1, brickStairBlock, 2);
		setBlockAndMetadata(world, 0, 11, 1, brickStairBlock, 3);
		for (i1 = -1; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 13, -1, roofStairBlock, 2);
			setBlockAndMetadata(world, i1, 13, 1, roofStairBlock, 3);
		}
		setBlockAndMetadata(world, -1, 13, 0, roofStairBlock, 1);
		setBlockAndMetadata(world, 1, 13, 0, roofStairBlock, 0);
		placeWallBanner(world, 0, 4, -2, LOTRItemBanner.BannerType.DALE, 2);
		placeWallBanner(world, -2, 4, 0, LOTRItemBanner.BannerType.DALE, 3);
		placeWallBanner(world, 0, 4, 2, LOTRItemBanner.BannerType.DALE, 0);
		placeWallBanner(world, 2, 4, 0, LOTRItemBanner.BannerType.DALE, 1);
		setBlockAndMetadata(world, -1, 5, 0, Blocks.torch, 2);
		setBlockAndMetadata(world, 1, 5, 0, Blocks.torch, 1);
		setBlockAndMetadata(world, 0, 5, -1, Blocks.torch, 3);
		setBlockAndMetadata(world, 0, 5, 1, Blocks.torch, 4);
		return true;
	}
}
