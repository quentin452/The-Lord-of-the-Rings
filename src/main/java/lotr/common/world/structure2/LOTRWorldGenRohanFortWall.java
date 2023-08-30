package lotr.common.world.structure2;

import java.util.Random;

import net.minecraft.world.World;

public class LOTRWorldGenRohanFortWall extends LOTRWorldGenRohanStructure {
	public int xMin;
	public int xMax;

	public LOTRWorldGenRohanFortWall(boolean flag) {
		this(flag, -4, 4);
	}

	public LOTRWorldGenRohanFortWall(boolean flag, int x0, int x1) {
		super(flag);
		xMin = x0;
		xMax = x1;
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		setOriginAndRotation(world, i, j, k, rotation, 0);
		setupRandomBlocks(random);
		for (int i1 = xMin; i1 <= xMax; ++i1) {
			int j1;
			int k1 = 0;
			findSurface(world, i1, k1);
			setupRandomBlocks(random);
			for (j1 = 1; (j1 >= 0 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
				setBlockAndMetadata(world, i1, j1, k1, rockSlabDoubleBlock, rockSlabDoubleMeta);
				setGrassToDirt(world, i1, j1 - 1, k1);
			}
			for (j1 = 2; j1 <= 2; ++j1) {
				setBlockAndMetadata(world, i1, j1, k1, brickBlock, brickMeta);
			}
			int h = 5 + random.nextInt(2);
			for (int j12 = 3; j12 <= h; ++j12) {
				setBlockAndMetadata(world, i1, j12, k1, woodBeamBlock, woodBeamMeta);
			}
			if (!random.nextBoolean()) {
				continue;
			}
			setBlockAndMetadata(world, i1, h + 1, k1, plankSlabBlock, plankSlabMeta);
		}
		return true;
	}

	@Override
	public boolean oneWoodType() {
		return true;
	}
}
