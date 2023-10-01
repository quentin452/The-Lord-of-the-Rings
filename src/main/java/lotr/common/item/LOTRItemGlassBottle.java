package lotr.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRItemGlassBottle extends ItemGlassBottle {
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
		return LOTRItemMug.tryPlaceMug(itemstack, entityplayer, world, i, j, k, side);
	}
}
