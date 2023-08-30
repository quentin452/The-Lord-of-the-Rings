package lotr.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

class LOTRSlotCoinResult extends Slot {
	private final LOTRContainerCoinExchange lotrContainerCoinExchange;

	LOTRSlotCoinResult(LOTRContainerCoinExchange lotrContainerCoinExchange, IInventory inv, int i, int j, int k) {
		super(inv, i, j, k);
		this.lotrContainerCoinExchange = lotrContainerCoinExchange;
	}

	@Override
	public boolean canTakeStack(EntityPlayer entityplayer) {
		return lotrContainerCoinExchange.exchanged;
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return false;
	}
}
