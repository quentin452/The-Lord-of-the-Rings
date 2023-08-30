package lotr.common.world.feature;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenClover extends WorldGenerator {
	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		Block block;
		//noinspection StatementWithEmptyBody
		while (((block = world.getBlock(i, j, k)).isAir(world, i, j, k) || block.isLeaves(world, i, j, k)) && --j > 0) {
		}
		for (int l = 0; l < 128; ++l) {
			int j1;
			int k1;
			int i1 = i + random.nextInt(8) - random.nextInt(8);
			if (!world.isAirBlock(i1, j1 = j + random.nextInt(4) - random.nextInt(4), k1 = k + random.nextInt(8) - random.nextInt(8)) || !LOTRMod.clover.canBlockStay(world, i1, j1, k1)) {
				continue;
			}
			if (random.nextInt(500) == 0) {
				world.setBlock(i1, j1, k1, LOTRMod.clover, 1, 2);
				continue;
			}
			world.setBlock(i1, j1, k1, LOTRMod.clover, 0, 2);
		}
		return true;
	}
}
