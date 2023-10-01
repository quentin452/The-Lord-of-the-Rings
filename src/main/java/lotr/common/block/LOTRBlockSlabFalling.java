package lotr.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.world.World;

import java.util.Random;

public abstract class LOTRBlockSlabFalling extends LOTRBlockSlabBase {
	protected LOTRBlockSlabFalling(boolean flag, Material material, int n) {
		super(flag, material, n);
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
		world.scheduleBlockUpdate(i, j, k, this, tickRate(world));
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
		world.scheduleBlockUpdate(i, j, k, this, tickRate(world));
	}

	@Override
	public int tickRate(World world) {
		return 2;
	}

	public void tryBlockFall(World world, int i, int j, int k) {
		if (BlockFalling.func_149831_e(world, i, j - 1, k) && j >= 0) {
			int range = 32;
			if (!BlockFalling.fallInstantly && world.checkChunksExist(i - range, j - range, k - range, i + range, j + range, k + range)) {
				if (!world.isRemote) {
					EntityFallingBlock fallingBlock = new EntityFallingBlock(world, i + 0.5f, j + 0.5f, k + 0.5f, this, world.getBlockMetadata(i, j, k) & 7);
					world.spawnEntityInWorld(fallingBlock);
				}
			} else {
				world.setBlockToAir(i, j, k);
				while (BlockFalling.func_149831_e(world, i, j - 1, k) && j > 0) {
					--j;
				}
				if (j > 0) {
					world.setBlock(i, j, k, this);
				}
			}
		}
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		if (!world.isRemote) {
			tryBlockFall(world, i, j, k);
		}
	}
}
