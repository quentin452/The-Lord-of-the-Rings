package lotr.common.world.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenMountainsideBush extends WorldGenerator {
	public Block leafBlock;
	public int leafMeta;

	public LOTRWorldGenMountainsideBush(Block block, int meta) {
		leafBlock = block;
		leafMeta = meta;
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		for (int l = 0; l < 64; ++l) {
			int j1;
			int k1;
			int i1 = i + MathHelper.getRandomIntegerInRange(random, -2, 2);
			if (!world.isAirBlock(i1, j1 = j + MathHelper.getRandomIntegerInRange(random, -2, 2), k1 = k + MathHelper.getRandomIntegerInRange(random, -2, 2)) || !isStone(world, i1 - 1, j1, k1) && !isStone(world, i1 + 1, j1, k1) && !isStone(world, i1, j1, k1 - 1) && !isStone(world, i1, j1, k1 + 1)) {
				continue;
			}
			world.setBlock(i1, j1, k1, leafBlock, leafMeta | 4, 2);
		}
		return true;
	}

	public boolean isStone(IBlockAccess world, int i, int j, int k) {
		return world.getBlock(i, j, k).getMaterial() == Material.rock;
	}
}
