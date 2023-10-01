package lotr.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class LOTRContainerChestWithPouch extends ContainerChest {
	public IInventory chestInv;
	public LOTRContainerPouch pouchContainer;
	public int thePouchSlot;
	public int numChestRows;
	public int numPouchRows;

	public LOTRContainerChestWithPouch(EntityPlayer entityplayer, int pouchSlot, IInventory chest) {
		super(entityplayer.inventory, chest);
		int i;
		inventorySlots.clear();
		inventoryItemStacks.clear();
		chestInv = chest;
		numChestRows = chest.getSizeInventory() / 9;
		thePouchSlot = pouchSlot;
		pouchContainer = new LOTRContainerPouch(entityplayer, thePouchSlot);
		numPouchRows = pouchContainer.capacity / 9;
		for (int j = 0; j < numChestRows; ++j) {
			for (int i2 = 0; i2 < 9; ++i2) {
				addSlotToContainer(new Slot(chest, i2 + j * 9, 8 + i2 * 18, 18 + j * 18));
			}
		}
		int pouchSlotsY = 103 + (numChestRows - 4) * 18;
		for (int j = 0; j < numPouchRows; ++j) {
			for (i = 0; i < 9; ++i) {
				int pouchSlotID = i + j * 9;
				Slot slot = pouchContainer.getSlotFromInventory(pouchContainer.pouchInventory, pouchSlotID);
				slot.xDisplayPosition = 8 + i * 18;
				slot.yDisplayPosition = pouchSlotsY + j * 18;
				addSlotToContainer(slot);
			}
		}
		int playerSlotsY = pouchSlotsY + 67;
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(entityplayer.inventory, j + i * 9 + 9, 8 + j * 18, playerSlotsY + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(entityplayer.inventory, i, 8 + i * 18, playerSlotsY + 58));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return chestInv.isUseableByPlayer(entityplayer) && pouchContainer.canInteractWith(entityplayer);
	}

	@Override
	public void onContainerClosed(EntityPlayer entityplayer) {
		super.onContainerClosed(entityplayer);
	}

	@Override
	public ItemStack slotClick(int slotNo, int subActionNo, int actionNo, EntityPlayer entityplayer) {
		if (LOTRContainerPouch.isPouchSlot(this, slotNo, entityplayer, thePouchSlot) || actionNo == 2 && subActionNo == thePouchSlot) {
			return null;
		}
		return super.slotClick(slotNo, subActionNo, actionNo, entityplayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		Slot aPouchSlot = getSlotFromInventory(pouchContainer.pouchInventory, 0);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i < numChestRows * 9 ? aPouchSlot.isItemValid(itemstack1) && !mergeItemStack(itemstack1, numChestRows * 9, (numChestRows + numPouchRows) * 9, true) : !mergeItemStack(itemstack1, 0, numChestRows * 9, false)) {
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
