package lotr.common.world.feature;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class LOTRWorldGenSurfaceGravel extends WorldGenerator {
	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		Block surfBlock;
		int surfMeta;
		int r = MathHelper.getRandomIntegerInRange(random, 2, 8);
		int chance = MathHelper.getRandomIntegerInRange(random, 3, 9);
		if (random.nextBoolean()) {
			surfBlock = Blocks.gravel;
			surfMeta = 0;
		} else {
			surfBlock = Blocks.dirt;
			surfMeta = 1;
		}
		for (int i1 = -r; i1 <= r; ++i1) {
			for (int k1 = -r; k1 <= r; ++k1) {
				int i2 = i + i1;
				int k2 = k + k1;
				int d = i1 * i1 + k1 * k1;
				if (d >= r * r || random.nextInt(chance) != 0) {
					continue;
				}
				int j1 = world.getTopSolidOrLiquidBlock(i2, k2) - 1;
				Block block = world.getBlock(i2, j1, k2);
				Material mt = block.getMaterial();
				if (!block.isOpaqueCube() || mt != Material.ground && mt != Material.grass) {
					continue;
				}
				world.setBlock(i2, j1, k2, surfBlock, surfMeta, 2);
			}
		}
		return true;
	}
}
