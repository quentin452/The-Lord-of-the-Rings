package lotr.common.world.feature;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class LOTRWorldGenObsidianGravel extends WorldGenerator {
	public Block genBlock = LOTRMod.obsidianGravel;
	public int genMeta;

	public boolean canReplace(IBlockAccess world, int i, int j, int k) {
		Block block = world.getBlock(i, j, k);
		BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
		return block == biome.topBlock || block == biome.fillerBlock || block == Blocks.stone || block.isReplaceable(world, i, j, k);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
		Block below = world.getBlock(i, j - 1, k);
		if (below != biome.topBlock && below != biome.fillerBlock && below != Blocks.stone) {
			return false;
		}
		int numBlocks = MathHelper.getRandomIntegerInRange(random, 6, 16);
		float angle = random.nextFloat() * 3.1415927f;
		float sin = MathHelper.sin(angle);
		float cos = MathHelper.sin(angle);
		float div = 8.0f;
		double xMin = i - sin * numBlocks / div;
		double xMax = i + sin * numBlocks / div;
		double zMin = k - cos * numBlocks / div;
		double zMax = k + cos * numBlocks / div;
		double yMin = j + random.nextInt(3) - 2;
		double yMax = j + random.nextInt(3) - 2;
		for (int l = 0; l <= numBlocks; ++l) {
			float lerp = (float) l / numBlocks;
			double xLerp = xMin + (xMax - xMin) * lerp;
			double yLerp = yMin + (yMax - yMin) * lerp;
			double zLerp = zMin + (zMax - zMin) * lerp;
			double d9 = random.nextDouble() * numBlocks / 16.0;
			double d10 = (MathHelper.sin(l * 3.1415927f / numBlocks) + 1.0f) * d9 + 1.0;
			double d11 = (MathHelper.sin(l * 3.1415927f / numBlocks) + 1.0f) * d9 + 1.0;
			int i1 = MathHelper.floor_double(xLerp - d10 / 2.0);
			int j1 = MathHelper.floor_double(yLerp - d11 / 2.0);
			int k1 = MathHelper.floor_double(zLerp - d10 / 2.0);
			int l1 = MathHelper.floor_double(xLerp + d10 / 2.0);
			int i2 = MathHelper.floor_double(yLerp + d11 / 2.0);
			int j2 = MathHelper.floor_double(zLerp + d10 / 2.0);
			for (int k2 = i1; k2 <= l1; ++k2) {
				double d12 = (k2 + 0.5 - xLerp) / (d10 / 2.0);
				if (d12 * d12 >= 1.0) {
					continue;
				}
				for (int l2 = j1; l2 <= i2; ++l2) {
					double d13 = (l2 + 0.5 - yLerp) / (d11 / 2.0);
					if (d12 * d12 + d13 * d13 >= 1.0) {
						continue;
					}
					for (int i3 = k1; i3 <= j2; ++i3) {
						double d14 = (i3 + 0.5 - zLerp) / (d10 / 2.0);
						if (d12 * d12 + d13 * d13 + d14 * d14 >= 1.0 || !canReplace(world, k2, l2, i3)) {
							continue;
						}
						world.setBlock(k2, l2, i3, genBlock, genMeta, 2);
					}
				}
			}
		}
		return true;
	}
}
