package lotr.common.block;

import java.util.Random;

import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRBlockSapling2 extends LOTRBlockSaplingBase {
	public LOTRBlockSapling2() {
		setSaplingNames("lebethron", "beech", "holly", "banana");
	}

	@Override
	public void growTree(World world, int i, int j, int k, Random random) {
		int k1;
		int i1;
		int meta = world.getBlockMetadata(i, j, k) & 7;
		WorldGenAbstractTree treeGen = null;
		int trunkNeg = 0;
		int trunkPos = 0;
		int xOffset = 0;
		int zOffset = 0;
		switch (meta) {
			case 0: {
				int[] partyTree = LOTRBlockSaplingBase.findPartyTree(world, i, j, k, this, meta);
				if (partyTree != null) {
					treeGen = LOTRTreeType.LEBETHRON_PARTY.create(true, random);
					trunkPos = 1;
					trunkNeg = 1;
					xOffset = partyTree[0];
					zOffset = partyTree[1];
				}
				if (treeGen == null) {
					treeGen = random.nextInt(10) == 0 ? LOTRTreeType.LEBETHRON_LARGE.create(true, random) : LOTRTreeType.LEBETHRON.create(true, random);
					trunkPos = 0;
					trunkNeg = 0;
					xOffset = 0;
					zOffset = 0;
				}
				break;
			}
			case 1: {
				int[] partyTree = LOTRBlockSaplingBase.findPartyTree(world, i, j, k, this, meta);
				if (partyTree != null) {
					treeGen = LOTRTreeType.BEECH_PARTY.create(true, random);
					trunkPos = 1;
					trunkNeg = 1;
					xOffset = partyTree[0];
					zOffset = partyTree[1];
				}
				if (treeGen == null) {
					treeGen = random.nextInt(10) == 0 ? LOTRTreeType.BEECH_LARGE.create(true, random) : LOTRTreeType.BEECH.create(true, random);
					trunkPos = 0;
					trunkNeg = 0;
					xOffset = 0;
					zOffset = 0;
				}
				break;
			}
			case 2:
				for (int i12 = 0; i12 >= -1; --i12) {
					for (k1 = 0; k1 >= -1; --k1) {
						if (!isSameSapling(world, i + i12, j, k + k1, meta) || !isSameSapling(world, i + i12 + 1, j, k + k1, meta) || !isSameSapling(world, i + i12, j, k + k1 + 1, meta) || !isSameSapling(world, i + i12 + 1, j, k + k1 + 1, meta)) {
							continue;
						}
						treeGen = LOTRTreeType.HOLLY_LARGE.create(true, random);
						trunkNeg = 0;
						trunkPos = 1;
						xOffset = i12;
						zOffset = k1;
						break;
					}
					if (treeGen != null) {
						break;
					}
				}
				if (treeGen == null) {
					xOffset = 0;
					zOffset = 0;
					treeGen = LOTRTreeType.HOLLY.create(true, random);
				}
				break;
			case 3:
				treeGen = LOTRTreeType.BANANA.create(true, random);
				break;
			default:
				break;
		}
		for (i1 = -trunkNeg; i1 <= trunkPos; ++i1) {
			for (k1 = -trunkNeg; k1 <= trunkPos; ++k1) {
				world.setBlock(i + xOffset + i1, j, k + zOffset + k1, Blocks.air, 0, 4);
			}
		}
		if (treeGen != null && !treeGen.generate(world, random, i + xOffset, j, k + zOffset)) {
			for (i1 = -trunkNeg; i1 <= trunkPos; ++i1) {
				for (k1 = -trunkNeg; k1 <= trunkPos; ++k1) {
					world.setBlock(i + xOffset + i1, j, k + zOffset + k1, this, meta, 4);
				}
			}
		}
	}
}
