package lotr.common.inventory;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRSlotBonemeal extends Slot {
	public LOTRSlotBonemeal(IInventory inv, int i, int j, int k, World world) {
		super(inv, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return itemstack.getItem() == Items.dye && itemstack.getItemDamage() == 15;
	}
}
