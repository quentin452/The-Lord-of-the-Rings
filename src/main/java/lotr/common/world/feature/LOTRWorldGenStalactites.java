package lotr.common.world.feature;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class LOTRWorldGenStalactites extends WorldGenerator {
	public Block stalactiteBlock;

	public LOTRWorldGenStalactites() {
		this(LOTRMod.stalactite);
	}

	public LOTRWorldGenStalactites(Block block) {
		stalactiteBlock = block;
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		for (int l = 0; l < 64; ++l) {
			int j1;
			int k1;
			int i1 = i - random.nextInt(8) + random.nextInt(8);
			if (!world.isAirBlock(i1, j1 = j - random.nextInt(4) + random.nextInt(4), k1 = k - random.nextInt(8) + random.nextInt(8))) {
				continue;
			}
			Block above = world.getBlock(i1, j1 + 1, k1);
			Block below = world.getBlock(i1, j1 - 1, k1);
			if (above.isOpaqueCube() && above.getMaterial() == Material.rock) {
				world.setBlock(i1, j1, k1, stalactiteBlock, 0, 2);
				continue;
			}
			if (!below.isOpaqueCube() || below.getMaterial() != Material.rock) {
				continue;
			}
			world.setBlock(i1, j1, k1, stalactiteBlock, 1, 2);
		}
		return true;
	}
}
