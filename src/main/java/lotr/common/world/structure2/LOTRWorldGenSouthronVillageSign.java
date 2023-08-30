package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.entity.npc.LOTRNames;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronVillageSign extends LOTRWorldGenSouthronStructure {
	public String[] signText = LOTRNames.getHaradVillageName(new Random());

	public LOTRWorldGenSouthronVillageSign(boolean flag) {
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
			setBlockAndMetadata(world, 0, j12, 0, brickBlock, brickMeta);
			setGrassToDirt(world, 0, j12 - 1, 0);
		}
		setBlockAndMetadata(world, 0, 1, 0, brickBlock, brickMeta);
		setBlockAndMetadata(world, 0, 2, 0, woodBeamBlock, woodBeamMeta);
		setBlockAndMetadata(world, 0, 3, 0, woodBeamBlock, woodBeamMeta);
		setBlockAndMetadata(world, 0, 4, 0, brickSlabBlock, brickSlabMeta);
		setBlockAndMetadata(world, -1, 3, 0, Blocks.torch, 1);
		setBlockAndMetadata(world, 1, 3, 0, Blocks.torch, 2);
		setBlockAndMetadata(world, 0, 3, -1, Blocks.torch, 4);
		setBlockAndMetadata(world, 0, 3, 1, Blocks.torch, 3);
		if (signText != null) {
			placeSign(world, -1, 2, 0, Blocks.wall_sign, 5, signText);
			placeSign(world, 1, 2, 0, Blocks.wall_sign, 4, signText);
			placeSign(world, 0, 2, -1, Blocks.wall_sign, 2, signText);
			placeSign(world, 0, 2, 1, Blocks.wall_sign, 3, signText);
		}
		return true;
	}

	public LOTRWorldGenSouthronVillageSign setSignText(String[] s) {
		signText = s;
		return this;
	}
}
