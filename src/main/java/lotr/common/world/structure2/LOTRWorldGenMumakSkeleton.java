package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenMumakSkeleton extends LOTRWorldGenStructureBase2 {
	public Block boneBlock;
	public int boneMeta;

	public LOTRWorldGenMumakSkeleton(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		setOriginAndRotation(world, i, j, k, rotation, 0);
		setupRandomBlocks(random);
		if (restrictions) {
			for (int i1 = -3; i1 <= 3; ++i1) {
				for (int k1 = -3; k1 <= 17; ++k1) {
					int j1 = getTopBlock(world, i1, k1) - 1;
					if (j1 >= -2) {
						continue;
					}
					return false;
				}
			}
		}
		if (usingPlayer == null) {
			originY -= random.nextInt(6);
		}
		loadStrScan("mumak_skeleton");
		associateBlockMetaAlias("BONE", boneBlock, boneMeta);
		generateStrScan(world, random, 0, 1, 0);
		return true;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		boneBlock = LOTRMod.boneBlock;
		boneMeta = 0;
	}
}
