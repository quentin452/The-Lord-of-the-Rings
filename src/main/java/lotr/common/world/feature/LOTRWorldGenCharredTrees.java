package lotr.common.world.feature;

import lotr.common.LOTRMod;
import lotr.common.world.biome.LOTRBiomeGenMordor;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class LOTRWorldGenCharredTrees extends WorldGenAbstractTree {
	public LOTRWorldGenCharredTrees() {
		super(false);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		Block below = world.getBlock(i, j - 1, k);
		world.getBlockMetadata(i, j - 1, k);
		if (!LOTRBiomeGenMordor.isSurfaceMordorBlock(world, i, j - 1, k) && below != Blocks.stone && below != Blocks.sand && below != Blocks.gravel && !below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable) Blocks.sapling)) {
			return false;
		}
		below.onPlantGrow(world, i, j - 1, k, i, j, k);
		int height = 2 + random.nextInt(5);
		for (int j1 = j; j1 < j + height; ++j1) {
			world.setBlock(i, j1, k, LOTRMod.wood, 3, 2);
		}
		if (height >= 4) {
			for (int branch = 0; branch < 4; ++branch) {
				int branchLength = 2 + random.nextInt(4);
				int branchHorizontalPos = 0;
				int branchVerticalPos = j + height - random.nextInt(2);
				for (int l = 0; l < branchLength; ++l) {
					if (random.nextInt(4) == 0) {
						++branchHorizontalPos;
					}
					if (random.nextInt(3) != 0) {
						++branchVerticalPos;
					}
					switch (branch) {
						case 0: {
							world.setBlock(i - branchHorizontalPos, branchVerticalPos, k, LOTRMod.wood, 15, 2);
							continue;
						}
						case 1: {
							world.setBlock(i, branchVerticalPos, k + branchHorizontalPos, LOTRMod.wood, 15, 2);
							continue;
						}
						case 2: {
							world.setBlock(i + branchHorizontalPos, branchVerticalPos, k, LOTRMod.wood, 15, 2);
							continue;
						}
						case 3: {
							world.setBlock(i, branchVerticalPos, k - branchHorizontalPos, LOTRMod.wood, 15, 2);
						}
					}
				}
			}
		}
		return true;
	}
}
