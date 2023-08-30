package lotr.common.world.feature;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenCorn extends WorldGenerator {
	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		for (int l = 0; l < 20; ++l) {
			int j1;
			int k1;
			int i1 = i + random.nextInt(4) - random.nextInt(4);
			Block replace = world.getBlock(i1, j1 = j, k1 = k + random.nextInt(4) - random.nextInt(4));
			if (!replace.isReplaceable(world, i1, j1, k1) || replace.getMaterial().isLiquid()) {
				continue;
			}
			boolean adjWater = false;
			block1:
			for (int i2 = -1; i2 <= 1; ++i2) {
				for (int k2 = -1; k2 <= 1; ++k2) {
					if (Math.abs(i2) + Math.abs(k2) != 1 || world.getBlock(i1 + i2, j - 1, k1 + k2).getMaterial() != Material.water) {
						continue;
					}
					adjWater = true;
					break block1;
				}
			}
			if (!adjWater) {
				continue;
			}
			int cornHeight = 2 + random.nextInt(2);
			for (int j2 = 0; j2 < cornHeight; ++j2) {
				if (!LOTRMod.cornStalk.canBlockStay(world, i1, j1 + j2, k1)) {
					continue;
				}
				world.setBlock(i1, j1 + j2, k1, LOTRMod.cornStalk, 0, 2);
			}
		}
		return true;
	}
}
