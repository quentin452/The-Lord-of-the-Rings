package lotr.common.inventory;

import lotr.common.item.LOTRItemPouch;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class LOTRSlotPouch extends Slot {
	public LOTRSlotPouch(IInventory inv, int i, int j, int k) {
		super(inv, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		if (itemstack.getItem() instanceof LOTRItemPouch) {
			return false;
		}
		return super.isItemValid(itemstack);
	}
}
