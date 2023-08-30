package lotr.common.world.structure2;

import java.util.Random;

import com.google.common.math.IntMath;

import net.minecraft.world.World;

public abstract class LOTRWorldGenSouthronTownWall extends LOTRWorldGenSouthronStructure {
	public boolean centrePillar;
	public int leftExtent;
	public int rightExtent;

	protected LOTRWorldGenSouthronTownWall(boolean flag) {
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
		setOriginAndRotation(world, i, j, k, rotation, 0);
		setupRandomBlocks(random);
		for (int i1 = -leftExtent; i1 <= rightExtent; ++i1) {
			int k3;
			int j1;
			int pillarOffset;
			int k1 = 0;
			findSurface(world, i1, k1);
			for (int k32 = k1; k32 <= k1 + 1; ++k32) {
				int j12;
				for (j12 = 1; (j12 >= 1 || !isOpaque(world, i1, j12, k32)) && getY(j12) >= 0; --j12) {
					setBlockAndMetadata(world, i1, j12, k32, stoneBlock, stoneMeta);
					setGrassToDirt(world, i1, j12 - 1, k32);
				}
				for (j12 = 2; j12 <= 4; ++j12) {
					setBlockAndMetadata(world, i1, j12, k32, brickBlock, brickMeta);
				}
			}
			setBlockAndMetadata(world, i1, 5, k1, brickBlock, brickMeta);
			setBlockAndMetadata(world, i1, 5, k1 + 1, stoneBlock, stoneMeta);
			setBlockAndMetadata(world, i1, 5, k1 + 2, brickBlock, brickMeta);
			setBlockAndMetadata(world, i1, 6, k1 + 2, fenceBlock, fenceMeta);
			pillarOffset = centrePillar ? IntMath.mod(i1, 4) : IntMath.mod(i1 + 2, 4);
			switch (pillarOffset) {
				case 0:
					k3 = k1 - 1;
					for (j1 = 4; (j1 >= 1 || !isOpaque(world, i1, j1, k3)) && getY(j1) >= 0; --j1) {
						setBlockAndMetadata(world, i1, j1, k3, pillarBlock, pillarMeta);
						setGrassToDirt(world, i1, j1 - 1, k3);
					}
					setBlockAndMetadata(world, i1, 5, k3, brickBlock, brickMeta);
					setBlockAndMetadata(world, i1, 6, k3, brickWallBlock, brickWallMeta);
					break;
				case 1:
					setBlockAndMetadata(world, i1, 5, k1 - 1, brickStairBlock, 4);
					setBlockAndMetadata(world, i1, 6, k1 - 1, brickBlock, brickMeta);
					setBlockAndMetadata(world, i1, 7, k1 - 1, brickSlabBlock, brickSlabMeta);
					break;
				case 2:
					setBlockAndMetadata(world, i1, 5, k1 - 1, brickSlabBlock, brickSlabMeta | 8);
					setBlockAndMetadata(world, i1, 6, k1 - 1, brickWallBlock, brickWallMeta);
					break;
				case 3:
					setBlockAndMetadata(world, i1, 5, k1 - 1, brickStairBlock, 5);
					setBlockAndMetadata(world, i1, 6, k1 - 1, brickBlock, brickMeta);
					setBlockAndMetadata(world, i1, 7, k1 - 1, brickSlabBlock, brickSlabMeta);
					break;
				default:
					break;
			}
			if (pillarOffset % 2 == 0) {
				setBlockAndMetadata(world, i1, 4, k1 + 2, plankStairBlock, 7);
				continue;
			}
			k3 = k1 + 2;
			for (j1 = 4; (j1 >= 1 || !isOpaque(world, i1, j1, k3)) && getY(j1) >= 0; --j1) {
				setBlockAndMetadata(world, i1, j1, k3, woodBeamBlock, woodBeamMeta);
				setGrassToDirt(world, i1, j1 - 1, k3);
			}
		}
		return true;
	}

	public static class Extra extends LOTRWorldGenSouthronTownWall {
		public Extra(boolean flag) {
			super(flag);
			centrePillar = true;
			leftExtent = 1;
			rightExtent = 2;
		}
	}

	public static class Long extends LOTRWorldGenSouthronTownWall {
		public Long(boolean flag) {
			super(flag);
			centrePillar = true;
			leftExtent = 2;
			rightExtent = 2;
		}
	}

	public static class Short extends LOTRWorldGenSouthronTownWall {
		public Short(boolean flag) {
			super(flag);
			centrePillar = true;
			leftExtent = 1;
			rightExtent = 1;
		}
	}

	public static class SideMid extends LOTRWorldGenSouthronTownWall {
		public SideMid(boolean flag) {
			super(flag);
			centrePillar = false;
			leftExtent = 4;
			rightExtent = 4;
		}
	}

}
