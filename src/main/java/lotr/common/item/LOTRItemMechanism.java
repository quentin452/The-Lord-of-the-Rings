package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRItemMechanism extends Item {
	public LOTRItemMechanism() {
		setCreativeTab(LOTRCreativeTabs.tabMisc);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
		int meta;
		Block block = world.getBlock(i, j, k);
		if (block == Blocks.rail && (meta = world.getBlockMetadata(i, j, k)) >= 0 && meta <= 5) {
			Block setBlock = LOTRMod.mechanisedRailOn;
			world.setBlock(i, j, k, setBlock, meta | 8, 3);
			Block.SoundType sound = setBlock.stepSound;
			world.playSoundEffect(i + 0.5f, j + 0.5f, k + 0.5f, sound.func_150496_b(), (sound.getVolume() + 1.0f) / 2.0f, sound.getPitch() * 0.8f);
			--itemstack.stackSize;
			return true;
		}
		return false;
	}
}
