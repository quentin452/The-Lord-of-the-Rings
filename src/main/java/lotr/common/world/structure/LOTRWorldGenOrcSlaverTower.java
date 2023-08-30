package lotr.common.world.structure;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import lotr.common.world.biome.LOTRBiomeGenNurn;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenOrcSlaverTower extends LOTRWorldGenStructureBase {
	public LOTRWorldGenOrcSlaverTower(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		int j1;
		int j12;
		int k1;
		int i1;
		if (restrictions && (world.getBlock(i, j - 1, k) != Blocks.grass || !(world.getBiomeGenForCoords(i, k) instanceof LOTRBiomeGenNurn))) {
			return false;
		}
		int height = 5 + random.nextInt(4);
		j += height;
		int rotation = random.nextInt(4);
		if (!restrictions && usingPlayer != null) {
			rotation = usingPlayerRotation();
		}
		switch (rotation) {
			case 0: {
				++k;
				break;
			}
			case 1: {
				--i;
				break;
			}
			case 2: {
				--k;
				break;
			}
			case 3: {
				++i;
			}
		}
		if (restrictions) {
			for (i1 = i - 3; i1 <= i + 3; ++i1) {
				for (k1 = k - 3; k1 <= k + 3; ++k1) {
					j1 = world.getHeightValue(i1, k1) - 1;
					Block l = world.getBlock(i1, j1, k1);
					if (l == Blocks.grass) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = i - 3; i1 <= i + 3; ++i1) {
			for (k1 = k - 3; k1 <= k + 3; ++k1) {
				setBlockAndNotifyAdequately(world, i1, j, k1, LOTRMod.planks, 3);
				setBlockAndNotifyAdequately(world, i1, j + 6, k1, LOTRMod.planks, 3);
				if (Math.abs(i1 - i) != 3 && Math.abs(k1 - k) != 3) {
					continue;
				}
				setBlockAndNotifyAdequately(world, i1, j + 1, k1, LOTRMod.fence, 3);
				setBlockAndNotifyAdequately(world, i1, j + 5, k1, LOTRMod.fence, 3);
				setBlockAndNotifyAdequately(world, i1, j + 7, k1, LOTRMod.fence, 3);
			}
		}
		for (i1 = i - 3; i1 <= i + 3; i1 += 6) {
			for (k1 = k - 3; k1 <= k + 3; k1 += 6) {
				for (j1 = j + 5; (j1 >= j || !LOTRMod.isOpaque(world, i1, j1, k1)) && j1 >= 0; --j1) {
					setBlockAndNotifyAdequately(world, i1, j1, k1, LOTRMod.wood, 3);
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
			}
		}
		for (j12 = j + 2; j12 <= j + 4; ++j12) {
			setBlockAndNotifyAdequately(world, i - 2, j12, k - 3, LOTRMod.fence, 3);
			setBlockAndNotifyAdequately(world, i - 2, j12, k + 3, LOTRMod.fence, 3);
			setBlockAndNotifyAdequately(world, i + 2, j12, k - 3, LOTRMod.fence, 3);
			setBlockAndNotifyAdequately(world, i + 2, j12, k + 3, LOTRMod.fence, 3);
			setBlockAndNotifyAdequately(world, i - 3, j12, k - 2, LOTRMod.fence, 3);
			setBlockAndNotifyAdequately(world, i + 3, j12, k - 2, LOTRMod.fence, 3);
			setBlockAndNotifyAdequately(world, i - 3, j12, k + 2, LOTRMod.fence, 3);
			setBlockAndNotifyAdequately(world, i + 3, j12, k + 2, LOTRMod.fence, 3);
		}
		for (j12 = j + 11; (j12 >= j || !LOTRMod.isOpaque(world, i, j12, k)) && j12 >= 0; --j12) {
			setBlockAndNotifyAdequately(world, i, j12, k, LOTRMod.wood, 3);
			setGrassToDirt(world, i, j12 - 1, k);
			if (j12 > j + 6) {
				continue;
			}
			setBlockAndNotifyAdequately(world, i, j12, k - 1, Blocks.ladder, 2);
		}
		setBlockAndNotifyAdequately(world, i, j + 1, k - 1, Blocks.trapdoor, 0);
		setBlockAndNotifyAdequately(world, i, j + 7, k - 1, Blocks.trapdoor, 0);
		placeOrcTorch(world, i - 3, j + 8, k - 3);
		placeOrcTorch(world, i - 3, j + 8, k + 3);
		placeOrcTorch(world, i + 3, j + 8, k - 3);
		placeOrcTorch(world, i + 3, j + 8, k + 3);
		setBlockAndNotifyAdequately(world, i, j + 12, k, LOTRMod.fence, 3);
		setBlockAndNotifyAdequately(world, i, j + 13, k, LOTRMod.fence, 3);
		setBlockAndNotifyAdequately(world, i, j + 12, k - 1, LOTRMod.fence, 3);
		setBlockAndNotifyAdequately(world, i, j + 12, k + 1, LOTRMod.fence, 3);
		setBlockAndNotifyAdequately(world, i - 1, j + 12, k, LOTRMod.fence, 3);
		setBlockAndNotifyAdequately(world, i + 1, j + 12, k, LOTRMod.fence, 3);
		placeOrcTorch(world, i, j + 14, k);
		placeOrcTorch(world, i, j + 13, k - 1);
		placeOrcTorch(world, i, j + 13, k + 1);
		placeOrcTorch(world, i - 1, j + 13, k);
		placeOrcTorch(world, i + 1, j + 13, k);
		LOTREntityMordorOrcSlaver slaver = new LOTREntityMordorOrcSlaver(world);
		slaver.setLocationAndAngles(i + 1.5, j + 7, k + 1.5, 0.0f, 0.0f);
		slaver.onSpawnWithEgg(null);
		world.spawnEntityInWorld(slaver);
		slaver.setHomeArea(i, j + 6, k, 12);
		int orcs = 2 + random.nextInt(3);
		for (int l = 0; l < orcs; ++l) {
			LOTREntityMordorOrc orc = new LOTREntityMordorOrc(world);
			orc.setLocationAndAngles(i + 1.5, j + 1, k + 1.5, 0.0f, 0.0f);
			((LOTREntityOrc) orc).onSpawnWithEgg(null);
			orc.isNPCPersistent = true;
			world.spawnEntityInWorld(orc);
			orc.setHomeArea(i, j + 1, k, 8);
		}
		return true;
	}
}
