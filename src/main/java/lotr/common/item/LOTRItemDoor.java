package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRItemDoor extends ItemBlock {
	public LOTRItemDoor(Block block) {
		super(block);
		maxStackSize = 1;
		setCreativeTab(LOTRCreativeTabs.tabDeco);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
		if (side != 1) {
			return false;
		}
		Block doorBlock = field_150939_a;
		if (entityplayer.canPlayerEdit(i, ++j, k, side, itemstack) && entityplayer.canPlayerEdit(i, j + 1, k, side, itemstack)) {
			if (!doorBlock.canPlaceBlockAt(world, i, j, k)) {
				return false;
			}
			int doorMeta = MathHelper.floor_double((entityplayer.rotationYaw + 180.0f) * 4.0f / 360.0f - 0.5) & 3;
			ItemDoor.placeDoorBlock(world, i, j, k, doorMeta, doorBlock);
			--itemstack.stackSize;
			return true;
		}
		return false;
	}
}
