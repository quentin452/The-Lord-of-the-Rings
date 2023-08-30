package lotr.common.world.structure2;

import java.util.Random;

import net.minecraft.world.World;

public class LOTRWorldGenRohanFortCorner extends LOTRWorldGenRohanStructure {
	public LOTRWorldGenRohanFortCorner(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		setOriginAndRotation(world, i, j, k, rotation, 0);
		setupRandomBlocks(random);
		int width = 1;
		for (int i1 = -width; i1 <= width; ++i1) {
			for (int k1 = -width; k1 <= width; ++k1) {
				int j1;
				for (j1 = 1; (j1 >= 0 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
					setBlockAndMetadata(world, i1, j1, k1, rockSlabDoubleBlock, rockSlabDoubleMeta);
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
				for (j1 = 2; j1 <= 3; ++j1) {
					setBlockAndMetadata(world, i1, j1, k1, brickBlock, brickMeta);
				}
				for (j1 = 4; j1 <= 7; ++j1) {
					setBlockAndMetadata(world, i1, j1, k1, woodBeamBlock, woodBeamMeta);
				}
				setBlockAndMetadata(world, i1, 8, k1, plankSlabBlock, plankSlabMeta);
			}
		}
		return true;
	}

	@Override
	public boolean oneWoodType() {
		return true;
	}
}
