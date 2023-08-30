package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public abstract class LOTRWorldGenMoredainHut extends LOTRWorldGenStructureBase2 {
	public Block clayBlock = Blocks.hardened_clay;
	public int clayMeta;
	public Block stainedClayBlock = Blocks.stained_hardened_clay;
	public int stainedClayMeta = 1;
	public Block brickBlock = LOTRMod.brick3;
	public int brickMeta = 10;
	public Block brickSlabBlock = LOTRMod.slabSingle7;
	public int brickSlabMeta;
	public Block plankBlock = Blocks.planks;
	public int plankMeta = 4;
	public Block plankSlabBlock = Blocks.wooden_slab;
	public int plankSlabMeta = 4;
	public Block fenceBlock = Blocks.fence;
	public int fenceMeta = 4;
	public Block thatchBlock = LOTRMod.thatch;
	public int thatchMeta;
	public Block thatchSlabBlock = LOTRMod.slabSingleThatch;
	public int thatchSlabMeta;

	protected LOTRWorldGenMoredainHut(boolean flag) {
		super(flag);
	}

	public void dropFence(World world, int i, int j, int k) {
		do {
			setBlockAndMetadata(world, i, j, k, fenceBlock, fenceMeta);
			if (isOpaque(world, i, j - 1, k)) {
				break;
			}
			--j;
		} while (true);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		setOriginAndRotation(world, i, j, k, rotation, getOffset());
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			int range = getOffset();
			for (int i1 = -range; i1 <= range; ++i1) {
				for (int k1 = -range; k1 <= range; ++k1) {
					int j1 = getTopBlock(world, i1, k1);
					Block block = getBlock(world, i1, j1 - 1, k1);
					if (block != Blocks.grass && block != Blocks.dirt && block != Blocks.sand && block != Blocks.stone) {
						return false;
					}
					if (j1 < minHeight) {
						minHeight = j1;
					}
					if (j1 > maxHeight) {
						maxHeight = j1;
					}
					if (maxHeight - minHeight <= 5) {
						continue;
					}
					return false;
				}
			}
		}
		return true;
	}

	public abstract int getOffset();

	public void layFoundation(World world, int i, int k) {
		for (int j = 0; (j == 0 || !isOpaque(world, i, j, k)) && getY(j) >= 0; --j) {
			setBlockAndMetadata(world, i, j, k, clayBlock, clayMeta);
			setGrassToDirt(world, i, j - 1, k);
		}
	}
}
