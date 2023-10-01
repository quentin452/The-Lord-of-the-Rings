package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenSouthronVillagePost extends LOTRWorldGenSouthronStructure {
	public LOTRWorldGenSouthronVillagePost(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int i1;
		setOriginAndRotation(world, i, j, k, rotation, 0);
		setupRandomBlocks(random);
		if (restrictions && !isSurface(world, i1 = 0, getTopBlock(world, i1, k1 = 0) - 1, k1)) {
			return false;
		}
		for (int j12 = 0; (j12 >= 0 || !isOpaque(world, 0, j12, 0)) && getY(j12) >= 0; --j12) {
			setBlockAndMetadata(world, 0, j12, 0, woodBeamBlock, woodBeamMeta);
			setGrassToDirt(world, 0, j12 - 1, 0);
		}
		setBlockAndMetadata(world, 0, 1, 0, woodBeamBlock, woodBeamMeta);
		setBlockAndMetadata(world, 0, 2, 0, woodBeamBlock, woodBeamMeta);
		setBlockAndMetadata(world, 0, 3, 0, plankSlabBlock, plankSlabMeta);
		return true;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		plankSlabBlock = LOTRMod.woodSlabSingle3;
		plankSlabMeta = 2;
		woodBeamBlock = LOTRMod.woodBeam4;
		woodBeamMeta = 2;
	}
}
