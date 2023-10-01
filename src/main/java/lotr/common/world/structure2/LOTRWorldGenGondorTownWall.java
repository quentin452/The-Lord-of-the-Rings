package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenGondorTownWall extends LOTRWorldGenGondorStructure {
	public int xMin;
	public int xMax;
	public int xMinInner;
	public int xMaxInner;

	public LOTRWorldGenGondorTownWall(boolean flag, int x0, int x1) {
		this(flag, x0, x1, x0, x1);
	}

	public LOTRWorldGenGondorTownWall(boolean flag, int x0, int x1, int xi0, int xi1) {
		super(flag);
		xMin = x0;
		xMax = x1;
		xMinInner = xi0;
		xMaxInner = xi1;
	}

	public static LOTRWorldGenGondorTownWall Centre(boolean flag) {
		return new LOTRWorldGenGondorTownWall(flag, -5, 5);
	}

	public static LOTRWorldGenGondorTownWall Left(boolean flag) {
		return new LOTRWorldGenGondorTownWall(flag, -9, 6);
	}

	public static LOTRWorldGenGondorTownWall LeftEnd(boolean flag) {
		return new LOTRWorldGenGondorTownWall(flag, -6, 6, -5, 6);
	}

	public static LOTRWorldGenGondorTownWall LeftEndShort(boolean flag) {
		return new LOTRWorldGenGondorTownWall(flag, -5, 6);
	}

	public static LOTRWorldGenGondorTownWall Right(boolean flag) {
		return new LOTRWorldGenGondorTownWall(flag, -6, 9);
	}

	public static LOTRWorldGenGondorTownWall RightEnd(boolean flag) {
		return new LOTRWorldGenGondorTownWall(flag, -6, 6, -6, 5);
	}

	public static LOTRWorldGenGondorTownWall RightEndShort(boolean flag) {
		return new LOTRWorldGenGondorTownWall(flag, -6, 5);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		setOriginAndRotation(world, i, j, k, rotation, 0);
		setupRandomBlocks(random);
		for (int i1 = xMin; i1 <= xMax; ++i1) {
			int j1;
			int k1 = 0;
			findSurface(world, i1, k1);
			for (j1 = 1; (j1 >= 0 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
				setBlockAndMetadata(world, i1, j1, k1, rockSlabDoubleBlock, rockSlabDoubleMeta);
				setGrassToDirt(world, i1, j1 - 1, k1);
			}
			for (j1 = 2; j1 <= 3; ++j1) {
				setBlockAndMetadata(world, i1, j1, k1, brickBlock, brickMeta);
			}
			setBlockAndMetadata(world, i1, 4, k1, brick2Block, brick2Meta);
			int i3 = IntMath.mod(i1, 4);
			if (i3 == 2) {
				setBlockAndMetadata(world, i1, 5, k1, rockWallBlock, rockWallMeta);
			} else {
				setBlockAndMetadata(world, i1, 5, k1, brickBlock, brickMeta);
				switch (i3) {
					case 3:
						setBlockAndMetadata(world, i1, 6, k1, brickStairBlock, 1);
						break;
					case 0:
						setBlockAndMetadata(world, i1, 6, k1, brickBlock, brickMeta);
						break;
					case 1:
						setBlockAndMetadata(world, i1, 6, k1, brickStairBlock, 0);
						break;
					default:
						break;
				}
			}
			if (i1 < xMinInner || i1 > xMaxInner) {
				continue;
			}
			for (k1 = 1; k1 <= 1; ++k1) {
				for (int j12 = 4; (j12 >= 0 || !isOpaque(world, i1, j12, k1)) && getY(j12) >= 0; --j12) {
					setBlockAndMetadata(world, i1, j12, k1, brickBlock, brickMeta);
					setGrassToDirt(world, i1, j12 - 1, k1);
				}
			}
		}
		return true;
	}
}
