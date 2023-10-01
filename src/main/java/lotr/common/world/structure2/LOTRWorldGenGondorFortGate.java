package lotr.common.world.structure2;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenGondorFortGate extends LOTRWorldGenGondorStructure {
	public LOTRWorldGenGondorFortGate(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		setOriginAndRotation(world, i, j, k, rotation, 0);
		setupRandomBlocks(random);
		findSurface(world, 0, 0);
		int gateX = originX;
		int gateY = originY;
		int gateZ = originZ;
		for (int i1 = -4; i1 <= 4; ++i1) {
			int j1;
			int i2 = Math.abs(i1);
			int k1 = 0;
			if (i2 <= 1) {
				originX = gateX;
				originY = gateY;
				originZ = gateZ;
				for (j1 = 0; (j1 >= 0 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
					setBlockAndMetadata(world, i1, j1, k1, brickBlock, brickMeta);
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
				for (j1 = 1; j1 <= 4; ++j1) {
					setBlockAndMetadata(world, i1, j1, k1, gateBlock, 2);
				}
				if (i1 == -1) {
					setBlockAndMetadata(world, -1, 5, k1, brickStairBlock, 4);
				}
				if (i1 == 0) {
					setBlockAndMetadata(world, 0, 5, k1, brickStairBlock, 6);
				}
				if (i1 == 1) {
					setBlockAndMetadata(world, 1, 5, k1, brickStairBlock, 5);
				}
				setBlockAndMetadata(world, i1, 6, k1, brick2Block, brick2Meta);
				if (i2 == 0) {
					setBlockAndMetadata(world, i1, 7, k1, brick2SlabBlock, brick2SlabMeta);
				}
				setBlockAndMetadata(world, i1, 5, 1, rockSlabBlock, rockSlabMeta);
			}
			if (i2 == 2) {
				findSurface(world, i1, k1);
				for (j1 = 5; (j1 >= 1 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
					setBlockAndMetadata(world, i1, j1, k1, pillarBlock, pillarMeta);
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
				setBlockAndMetadata(world, i1, 6, k1, brick2SlabBlock, brick2SlabMeta);
				setBlockAndMetadata(world, i1, 4, 1, rockSlabBlock, rockSlabMeta | 8);
			}
			if (i2 < 3) {
				continue;
			}
			findSurface(world, i1, k1);
			for (j1 = 3; (j1 >= 1 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
				setBlockAndMetadata(world, i1, j1, k1, brickBlock, brickMeta);
				setGrassToDirt(world, i1, j1 - 1, k1);
			}
			setBlockAndMetadata(world, i1, 4, k1, brickStairBlock, 6);
			setBlockAndMetadata(world, i1, 5, k1, brick2WallBlock, brick2WallMeta);
			setBlockAndMetadata(world, i1, 4, 1, rockSlabBlock, rockSlabMeta | 8);
			if (i2 != 4) {
				continue;
			}
			for (j1 = 3; (j1 >= 1 || !isOpaque(world, i1, j1, 1)) && getY(j1) >= 0; --j1) {
				setBlockAndMetadata(world, i1, j1, 1, brickBlock, brickMeta);
				setGrassToDirt(world, i1, j1 - 1, 1);
			}
			setBlockAndMetadata(world, i1, 4, 1, rockSlabDoubleBlock, rockSlabDoubleMeta);
			j1 = 4;
			while (!isOpaque(world, i1, j1, 2) && getY(j1) >= 0) {
				setBlockAndMetadata(world, i1, j1, 2, Blocks.ladder, 3);
				--j1;
			}
		}
		return true;
	}
}
