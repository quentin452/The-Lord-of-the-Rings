package lotr.common.world.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenReeds extends WorldGenerator {
	public Block reedBlock;

	public LOTRWorldGenReeds(Block block) {
		reedBlock = block;
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		block0:
		for (int l = 0; l < 16; ++l) {
			int i1 = i + random.nextInt(8) - random.nextInt(8);
			int j1 = j + random.nextInt(4) - random.nextInt(4);
			int k1 = k + random.nextInt(8) - random.nextInt(8);
			int maxDepth = 5;
			for (int j2 = j1 - 1; j2 > 0 && world.getBlock(i1, j2, k1).getMaterial() == Material.water; --j2) {
				if (j2 < j1 - maxDepth) {
					continue block0;
				}
			}
			if (world.isBlockFreezable(i1, j1 - 1, k1)) {
				continue;
			}
			int reedHeight = 1 + random.nextInt(3);
			for (int j2 = j1; j2 < j1 + reedHeight; ++j2) {
				if (!world.isAirBlock(i1, j2, k1) || !reedBlock.canBlockStay(world, i1, j2, k1)) {
					continue;
				}
				world.setBlock(i1, j2, k1, reedBlock, 0, 2);
			}
		}
		return true;
	}
}
