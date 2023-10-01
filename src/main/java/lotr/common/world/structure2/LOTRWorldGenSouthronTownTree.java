package lotr.common.world.structure2;

import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.ArrayList;
import java.util.Random;

public class LOTRWorldGenSouthronTownTree extends LOTRWorldGenSouthronStructure {
	public LOTRWorldGenSouthronTownTree(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int k1;
		int i1;
		setOriginAndRotation(world, i, j, k, rotation, 3);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -2; i1 <= 2; ++i1) {
				for (k1 = -2; k1 <= 2; ++k1) {
					j1 = getTopBlock(world, i1, k1) - 1;
					if (isSurface(world, i1, j1, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -2; i1 <= 2; ++i1) {
			for (k1 = -2; k1 <= 2; ++k1) {
				int j12;
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				for (j12 = 1; j12 <= 12; ++j12) {
					setAir(world, i1, j12, k1);
				}
				for (j12 = 0; (j12 >= 0 || !isOpaque(world, i1, j12, k1)) && getY(j12) >= 0; --j12) {
					setBlockAndMetadata(world, i1, j12, k1, stoneBlock, stoneMeta);
					setGrassToDirt(world, i1, j12 - 1, k1);
				}
				if (i2 == 2 || k2 == 2) {
					setBlockAndMetadata(world, i1, 1, k1, stoneBlock, stoneMeta);
					if ((i2 + k2) % 2 != 0) {
						continue;
					}
					setBlockAndMetadata(world, i1, 2, k1, brickSlabBlock, brickSlabMeta);
					continue;
				}
				setBlockAndMetadata(world, i1, 1, k1, Blocks.grass, 0);
			}
		}
		for (int l = 0; l < 16; ++l) {
			int i12 = 0;
			j1 = 2;
			int k12 = 0;
			LOTRTreeType tree = getRandomTree(random);
			WorldGenAbstractTree treeGen = tree.create(notifyChanges, random);
			if (treeGen != null && treeGen.generate(world, random, getX(i12, k12), getY(j1), getZ(i12, k12))) {
				break;
			}
		}
		for (i1 = -1; i1 <= 1; ++i1) {
			for (k1 = -1; k1 <= 1; ++k1) {
				if (i1 == 0 && k1 == 0 || !isAir(world, i1, 2, k1)) {
					continue;
				}
				plantTallGrass(world, random, i1, 2, k1);
			}
		}
		return true;
	}

	public LOTRTreeType getRandomTree(Random random) {
		ArrayList<LOTRTreeType> treeList = new ArrayList<>();
		treeList.add(LOTRTreeType.CEDAR);
		treeList.add(LOTRTreeType.CYPRESS);
		treeList.add(LOTRTreeType.PALM);
		treeList.add(LOTRTreeType.DATE_PALM);
		treeList.add(LOTRTreeType.OLIVE);
		return treeList.get(random.nextInt(treeList.size()));
	}
}
