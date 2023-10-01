package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenBreeHedgePart extends LOTRWorldGenBreeStructure {
	public boolean grassOnly;

	public LOTRWorldGenBreeHedgePart(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int i1;
		int j1;
		int k1;
		setOriginAndRotation(world, i, j, k, rotation, 0);
		setupRandomBlocks(random);
		if (restrictions && (!isSurface(world, i1 = 0, j1 = getTopBlock(world, i1, k1 = 0) - 1, k1) || grassOnly && getBlock(world, i1, j1, k1) != Blocks.grass)) {
			return false;
		}
		int j12 = 0;
		while (!isOpaque(world, 0, j12, 0) && getY(j12) >= 0) {
			setBlockAndMetadata(world, 0, j12, 0, LOTRMod.dirtPath, 0);
			setGrassToDirt(world, 0, j12 - 1, 0);
			--j12;
		}
		boolean hasBeams = random.nextInt(4) == 0;
		int height = 3 + random.nextInt(2);
		for (j1 = 1; j1 <= height; ++j1) {
			if (hasBeams && j1 <= 2) {
				setBlockAndMetadata(world, 0, j1, 0, beamBlock, beamMeta);
				setGrassToDirt(world, 0, j1 - 1, 0);
				continue;
			}
			if (random.nextInt(4) == 0) {
				setBlockAndMetadata(world, 0, j1, 0, fenceBlock, fenceMeta);
				continue;
			}
			int randLeaf = random.nextInt(4);
			switch (randLeaf) {
				case 0:
					setBlockAndMetadata(world, 0, j1, 0, Blocks.leaves, 4);
					continue;
				case 1:
					setBlockAndMetadata(world, 0, j1, 0, LOTRMod.leaves2, 5);
					continue;
				case 2:
					setBlockAndMetadata(world, 0, j1, 0, LOTRMod.leaves4, 4);
					continue;
				default:
					break;
			}
			setBlockAndMetadata(world, 0, j1, 0, LOTRMod.leaves7, 4);
		}
		return true;
	}

	public LOTRWorldGenBreeHedgePart setGrassOnly() {
		grassOnly = true;
		return this;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		fenceBlock = Blocks.fence;
		fenceMeta = 0;
		beamBlock = LOTRMod.woodBeamV1;
		beamMeta = 0;
	}
}
