package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRItemSalt extends Item {
	public LOTRItemSalt() {
		setCreativeTab(LOTRCreativeTabs.tabFood);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
		if (!world.isRemote) {
			boolean changedAny = false;
			int range = 1 + world.rand.nextInt(3);
			int yRange = range / 2;
			for (int i1 = -range; i1 <= range; ++i1) {
				for (int j1 = -yRange; j1 <= yRange; ++j1) {
					for (int k1 = -range; k1 <= range; ++k1) {
						int i2 = i + i1;
						int j2 = j + j1;
						int k2 = k + k1;
						if (i1 * i1 + k1 * k1 > range * range) {
							continue;
						}
						Block block = world.getBlock(i2, j2, k2);
						int meta = world.getBlockMetadata(i2, j2, k2);
						Block newBlock = null;
						int newMeta = 0;
						if (block == Blocks.grass || block == Blocks.dirt && meta == 0 || block == Blocks.farmland) {
							newBlock = Blocks.dirt;
							newMeta = 1;
						} else if (block == LOTRMod.mudGrass || block == LOTRMod.mud && meta == 0 || block == LOTRMod.mudFarmland) {
							newBlock = LOTRMod.mud;
							newMeta = 1;
						}
						if (newBlock == null) {
							continue;
						}
						if (i1 == 0 && j1 == 0 && k1 == 0 || world.rand.nextInt(3) != 0) {
							world.setBlock(i2, j2, k2, newBlock, newMeta, 3);
						}
						changedAny = true;
					}
				}
			}
			if (changedAny) {
				--itemstack.stackSize;
			}
		}
		return true;
	}
}
