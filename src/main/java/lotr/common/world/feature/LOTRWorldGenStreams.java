package lotr.common.world.feature;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class LOTRWorldGenStreams extends WorldGenerator {
	public Block liquidBlock;

	public LOTRWorldGenStreams(Block block) {
		liquidBlock = block;
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		if (!isRock(world, i, j + 1, k) || !isRock(world, i, j - 1, k) || !world.isAirBlock(i, j, k) && !isRock(world, i, j, k)) {
			return false;
		}
		int sides = 0;
		if (isRock(world, i - 1, j, k)) {
			++sides;
		}
		if (isRock(world, i + 1, j, k)) {
			++sides;
		}
		if (isRock(world, i, j, k - 1)) {
			++sides;
		}
		if (isRock(world, i, j, k + 1)) {
			++sides;
		}
		int openAir = 0;
		if (world.isAirBlock(i - 1, j, k)) {
			++openAir;
		}
		if (world.isAirBlock(i + 1, j, k)) {
			++openAir;
		}
		if (world.isAirBlock(i, j, k - 1)) {
			++openAir;
		}
		if (world.isAirBlock(i, j, k + 1)) {
			++openAir;
		}
		if (sides == 3 && openAir == 1) {
			world.setBlock(i, j, k, liquidBlock, 0, 2);
			world.scheduledUpdatesAreImmediate = true;
			liquidBlock.updateTick(world, i, j, k, random);
			world.scheduledUpdatesAreImmediate = false;
		}
		return true;
	}

	public boolean isRock(IBlockAccess world, int i, int j, int k) {
		Block block = world.getBlock(i, j, k);
		return block == Blocks.stone || block == Blocks.sandstone || block == LOTRMod.rock;
	}
}
