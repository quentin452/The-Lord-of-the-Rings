package lotr.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.StringUtils;

public class LOTRContainerPouch extends Container {
	public int thePouchSlot;
	public ItemStack thePouchItem;
	public LOTRInventoryPouch pouchInventory;
	public int capacity;

	public LOTRContainerPouch(EntityPlayer entityplayer, int slot) {
		int i;
		int j;
		thePouchSlot = slot;
		thePouchItem = entityplayer.inventory.getStackInSlot(thePouchSlot);
		pouchInventory = new LOTRInventoryPouch(entityplayer, this, slot);
		capacity = pouchInventory.getSizeInventory();
		int rows = capacity / 9;
		for (i = 0; i < rows; ++i) {
			for (j = 0; j < 9; ++j) {
				addSlotToContainer(new LOTRSlotPouch(pouchInventory, j + i * 9, 8 + j * 18, 30 + i * 18));
			}
		}
		for (i = 0; i < 3; ++i) {
			for (j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(entityplayer.inventory, j + i * 9 + 9, 8 + j * 18, 98 + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(entityplayer.inventory, i, 8 + i * 18, 156));
		}
	}

	public static boolean isPouchSlot(Container container, int slotNo, EntityPlayer entityplayer, int pouchSlotNo) {
		if (slotNo >= 0 && slotNo < container.inventorySlots.size()) {
			Slot slot = (Slot) container.inventorySlots.get(slotNo);
			return slot.inventory == entityplayer.inventory && slot.getSlotIndex() == pouchSlotNo;
		}
		return false;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return ItemStack.areItemStacksEqual(thePouchItem, pouchInventory.getPouchItem());
	}

	public String getDisplayName() {
		return pouchInventory.getInventoryName();
	}

	public void renamePouch(String name) {
		if (StringUtils.isBlank(name)) {
			pouchInventory.getPouchItem().func_135074_t();
		} else {
			pouchInventory.getPouchItem().setStackDisplayName(name);
		}
		syncPouchItem(pouchInventory.getPouchItem());
	}

	@Override
	public ItemStack slotClick(int slotNo, int subActionNo, int actionNo, EntityPlayer entityplayer) {
		if (isPouchSlot(this, slotNo, entityplayer, thePouchSlot) || actionNo == 2 && subActionNo == thePouchSlot) {
			return null;
		}
		return super.slotClick(slotNo, subActionNo, actionNo, entityplayer);
	}

	public void syncPouchItem(ItemStack itemstack) {
		thePouchItem = itemstack;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		Slot aPouchSlot = getSlotFromInventory(pouchInventory, 0);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i < capacity ? !mergeItemStack(itemstack1, capacity, capacity + 36, true) : aPouchSlot.isItemValid(itemstack1) && !mergeItemStack(itemstack1, 0, capacity, false)) {
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
