package lotr.common.world.feature;

import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockBerryBush;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class LOTRWorldGenBerryBush extends WorldGenerator {
	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		Block bush = LOTRMod.berryBush;
		LOTRBlockBerryBush.BushType bushType = LOTRBlockBerryBush.BushType.randomType(random);
		int bushMeta = bushType.bushMeta;
		bushMeta = LOTRBlockBerryBush.setHasBerries(bushMeta, true);
		if (bushType.poisonous && random.nextInt(2) != 0) {
			return false;
		}
		for (int l = 0; l < 12; ++l) {
			int i1 = i - random.nextInt(4) + random.nextInt(4);
			int j1 = j - random.nextInt(2) + random.nextInt(2);
			int k1 = k - random.nextInt(4) + random.nextInt(4);
			Block below = world.getBlock(i1, j1 - 1, k1);
			Block block = world.getBlock(i1, j1, k1);
			if (!below.canSustainPlant(world, i1, j1 - 1, k1, ForgeDirection.UP, (IPlantable) Blocks.sapling) || block.getMaterial().isLiquid() || !block.isReplaceable(world, i1, j1, k1)) {
				continue;
			}
			world.setBlock(i1, j1, k1, bush, bushMeta, 2);
		}
		return true;
	}
}
