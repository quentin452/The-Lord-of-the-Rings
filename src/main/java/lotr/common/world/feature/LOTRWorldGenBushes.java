package lotr.common.world.feature;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class LOTRWorldGenBushes extends WorldGenerator {
	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		int k1;
		Block leafBlock = null;
		int leafMeta = 0;
		for (int l = 0; l < 40; ++l) {
			int j1;
			int i1 = i - random.nextInt(6) + random.nextInt(6);
			Block block = world.getBlock(i1, j1 = j + random.nextInt(12), k1 = k - random.nextInt(6) + random.nextInt(6));
			if (!(block instanceof BlockLeavesBase)) {
				continue;
			}
			int meta = world.getBlockMetadata(i1, j1, k1);
			leafBlock = block;
			leafMeta = meta;
			break;
		}
		if (leafBlock == null) {
			return false;
		}
		Block below = world.getBlock(i, j - 1, k);
		if (below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable) Blocks.sapling)) {
			int size = 0;
			if (random.nextInt(3) == 0) {
				++size;
			}
			for (int i1 = -size; i1 <= size; ++i1) {
				for (k1 = -size; k1 <= size; ++k1) {
					Block block;
					int i2 = i + i1;
					int k2 = k + k1;
					if (size != 0 && Math.abs(i1) == size && Math.abs(k1) == size && random.nextInt(3) != 0 || (block = world.getBlock(i2, j, k2)).getMaterial().isLiquid() || !block.isReplaceable(world, i2, j, k2)) {
						continue;
					}
					world.setBlock(i2, j, k2, leafBlock, leafMeta | 4, 2);
				}
			}
		}
		return true;
	}
}
