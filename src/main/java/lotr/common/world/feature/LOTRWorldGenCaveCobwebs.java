package lotr.common.world.feature;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenCaveCobwebs extends WorldGenerator {
	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		for (int l = 0; l < 64; ++l) {
			int j1;
			int k1;
			int i1 = i - random.nextInt(6) + random.nextInt(6);
			if (!world.isAirBlock(i1, j1 = j - random.nextInt(4) + random.nextInt(4), k1 = k - random.nextInt(6) + random.nextInt(6))) {
				continue;
			}
			boolean flag = isStoneBlock(world, i1 - 1, j1, k1);
			if (isStoneBlock(world, i1 + 1, j1, k1)) {
				flag = true;
			}
			if (isStoneBlock(world, i1, j1 - 1, k1)) {
				flag = true;
			}
			if (isStoneBlock(world, i1, j1 + 1, k1)) {
				flag = true;
			}
			if (isStoneBlock(world, i1, j1, k1 - 1)) {
				flag = true;
			}
			if (isStoneBlock(world, i1, j1, k1 + 1)) {
				flag = true;
			}
			if (!flag) {
				continue;
			}
			world.setBlock(i1, j1, k1, Blocks.web, 0, 2);
		}
		return true;
	}

	public boolean isStoneBlock(IBlockAccess world, int i, int j, int k) {
		Block block = world.getBlock(i, j, k);
		return block == Blocks.stone || block == LOTRMod.rock;
	}
}
