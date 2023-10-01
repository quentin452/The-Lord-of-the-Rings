package lotr.common.world.structure;

import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenNurnWheatFarm extends LOTRWorldGenNurnFarmBase {
	public LOTRWorldGenNurnWheatFarm(boolean flag) {
		super(flag);
	}

	@Override
	public void generateCrops(World world, Random random, int i, int j, int k) {
		for (int i1 = i - 4; i1 <= i + 4; ++i1) {
			for (int k1 = k - 4; k1 <= k + 4; ++k1) {
				if (Math.abs(i1 - i) == 4 && Math.abs(k1 - k) == 4) {
					setBlockAndNotifyAdequately(world, i1, j + 1, k1, LOTRMod.brick, 0);
					setBlockAndNotifyAdequately(world, i1, j + 2, k1, LOTRMod.brick, 0);
					setBlockAndNotifyAdequately(world, i1, j + 3, k1, LOTRMod.fence, 3);
					setBlockAndNotifyAdequately(world, i1, j + 4, k1, Blocks.wool, 12);
					placeSkull(world, random, i1, j + 5, k1);
					continue;
				}
				if (Math.abs(i1 - i) <= 1 && Math.abs(k1 - k) <= 1) {
					setBlockAndNotifyAdequately(world, i1, j + 1, k1, LOTRMod.brick, 0);
					if (Math.abs(i1 - i) == 0 || Math.abs(k1 - k) == 0) {
						continue;
					}
					placeOrcTorch(world, i1, j + 2, k1);
					continue;
				}
				if (i1 == i || k1 == k) {
					if (Math.abs(i1 - i) > 3 || Math.abs(k1 - k) > 3) {
						continue;
					}
					setBlockAndNotifyAdequately(world, i1, j, k1, Blocks.water, 0);
					continue;
				}
				setBlockAndNotifyAdequately(world, i1, j, k1, Blocks.farmland, 7);
				setBlockAndNotifyAdequately(world, i1, j + 1, k1, Blocks.wheat, 7);
			}
		}
		setBlockAndNotifyAdequately(world, i, j + 1, k, LOTRMod.morgulTable, 0);
	}
}
