package lotr.common.world.feature;

import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class LOTRWorldGenSkullPile extends WorldGenerator {
	public LOTRWorldGenSkullPile() {
		super(false);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		for (int l = 0; l < 4; ++l) {
			int j1;
			int k1;
			int i1 = i - 4 + random.nextInt(9);
			if (!world.getBlock(i1, (j1 = world.getHeightValue(i1, k1 = k - 4 + random.nextInt(9))) - 1, k1).isOpaqueCube() || !world.getBlock(i1, j1, k1).isReplaceable(world, i1, j1, k1)) {
				continue;
			}
			world.setBlock(i1, j1, k1, Blocks.skull, 1, 2);
			TileEntity tileentity = world.getTileEntity(i1, j1, k1);
			if (!(tileentity instanceof TileEntitySkull)) {
				continue;
			}
			TileEntitySkull skull = (TileEntitySkull) tileentity;
			skull.func_145903_a(random.nextInt(16));
		}
		return true;
	}
}
