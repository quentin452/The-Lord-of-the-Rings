package lotr.common.inventory;

import lotr.common.tileentity.LOTRTileEntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class LOTRContainerArmorStand extends Container {
	public LOTRTileEntityArmorStand theArmorStand;

	public LOTRContainerArmorStand(InventoryPlayer inv, LOTRTileEntityArmorStand armorStand) {
		int i;
		theArmorStand = armorStand;
		for (i = 0; i < 4; ++i) {
			addSlotToContainer(new LOTRSlotArmorStand(armorStand, i, 80, 21 + i * 18, i, inv.player));
		}
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 107 + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(inv, i, 8 + i * 18, 165));
		}
		for (i = 0; i < 4; ++i) {
			addSlotToContainer(new LOTRSlotArmorStand(inv, 36 + 3 - i, 8, 21 + i * 18, i, inv.player));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return theArmorStand.isUseableByPlayer(entityplayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i < 4) {
				if (!mergeItemStack(itemstack1, 4, 40, true)) {
					return null;
				}
			} else if (itemstack.getItem() instanceof ItemArmor && !getSlotFromInventory(theArmorStand, ((ItemArmor) itemstack.getItem()).armorType).getHasStack()) {
				int j = ((ItemArmor) itemstack.getItem()).armorType;
				if (!mergeItemStack(itemstack1, j, j + 1, false)) {
					return null;
				}
			} else {
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
