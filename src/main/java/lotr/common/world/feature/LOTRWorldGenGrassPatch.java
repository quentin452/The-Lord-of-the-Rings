package lotr.common.world.feature;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class LOTRWorldGenGrassPatch extends WorldGenerator {
	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		if (world.getBlock(i, j - 1, k) != Blocks.stone) {
			return false;
		}
		int radius = 3 + random.nextInt(3);
		int heightValue = world.getHeightValue(i, k);
		for (int i1 = i - radius; i1 <= i + radius; ++i1) {
			for (int k1 = k - radius; k1 <= k + radius; ++k1) {
				Block block;
				int i2 = i1 - i;
				int k2 = k1 - k;
				if (i2 * i2 + k2 * k2 >= radius * radius || world.getHeightValue(i1, k1) != heightValue) {
					continue;
				}
				for (int j1 = heightValue - 1; j1 > heightValue - 5 && ((block = world.getBlock(i1, j1, k1)) == Blocks.dirt || block == Blocks.stone); --j1) {
					if (j1 == heightValue - 1) {
						world.setBlock(i1, j1, k1, Blocks.grass, 0, 2);
						continue;
					}
					world.setBlock(i1, j1, k1, Blocks.dirt, 0, 2);
				}
			}
		}
		return true;
	}
}
