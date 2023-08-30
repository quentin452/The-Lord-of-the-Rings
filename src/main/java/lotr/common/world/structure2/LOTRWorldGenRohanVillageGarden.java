package lotr.common.world.structure2;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRohanVillageGarden extends LOTRWorldGenRohanStructure {
	public Block leafBlock;
	public int leafMeta;

	public LOTRWorldGenRohanVillageGarden(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int i1;
		setOriginAndRotation(world, i, j, k, rotation, 2);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -3; i1 <= 3; ++i1) {
				for (k1 = -1; k1 <= 1; ++k1) {
					int j1 = getTopBlock(world, i1, k1) - 1;
					if (isSurface(world, i1, j1, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			for (k1 = -1; k1 <= 1; ++k1) {
				int j1;
				int i2 = Math.abs(i1);
				boolean foundSurface = false;
				for (j1 = 5; j1 >= -5; --j1) {
					if (!isSurface(world, i1, j1 - 1, k1)) {
						continue;
					}
					foundSurface = true;
					break;
				}
				if (!foundSurface) {
					continue;
				}
				if (i2 <= 2) {
					if (random.nextInt(3) == 0) {
						plantFlower(world, random, i1, j1, k1);
					} else {
						int j2 = j1;
						if (random.nextInt(5) == 0) {
							++j2;
						}
						for (int j3 = j1; j3 <= j2; ++j3) {
							setBlockAndMetadata(world, i1, j3, k1, leafBlock, leafMeta);
						}
					}
				}
				if (i2 != 3) {
					continue;
				}
				setBlockAndMetadata(world, i1, j1, k1, fenceBlock, fenceMeta);
			}
		}
		return true;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		leafBlock = Blocks.leaves;
		leafMeta = 4;
	}
}
