package lotr.common.inventory;

import lotr.common.entity.npc.LOTREntityGollum;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class LOTRContainerGollum extends Container {
	public LOTREntityGollum theGollum;

	public LOTRContainerGollum(InventoryPlayer inv, LOTREntityGollum gollum) {
		int j;
		int i;
		theGollum = gollum;
		for (i = 0; i < LOTREntityGollum.INV_ROWS; ++i) {
			for (j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(gollum.inventory, j + i * 9, 8 + j * 18, 18 + i * 18));
			}
		}
		for (i = 0; i < 3; ++i) {
			for (j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 86 + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(inv, i, 8 + i * 18, 144));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return theGollum != null && theGollum.getGollumOwner() == entityplayer && entityplayer.getDistanceSqToEntity(theGollum) <= 144.0 && theGollum.isEntityAlive();
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i < LOTREntityGollum.INV_ROWS * 9 ? !mergeItemStack(itemstack1, LOTREntityGollum.INV_ROWS * 9, inventorySlots.size(), true) : !mergeItemStack(itemstack1, 0, LOTREntityGollum.INV_ROWS * 9, false)) {
				return null;
			}
			if (itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}
			slot.onPickupFromSlot(entityplayer, itemstack1);
		}
		return itemstack;
	}
}
