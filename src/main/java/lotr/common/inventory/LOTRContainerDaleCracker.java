package lotr.common.inventory;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.item.LOTRItemDaleCracker;
import lotr.common.network.*;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class LOTRContainerDaleCracker extends Container {
	public ItemStack theCrackerItem;
	public IInventory crackerInventory;
	public int capacity;

	public LOTRContainerDaleCracker(EntityPlayer entityplayer) {
		int i;
		theCrackerItem = entityplayer.inventory.getCurrentItem();
		capacity = 3;
		crackerInventory = new InventoryBasic("cracker", false, capacity);
		for (i = 0; i < capacity; ++i) {
			addSlotToContainer(new LOTRSlotDaleCracker(crackerInventory, i, 62 + i * 18, 24));
		}
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(entityplayer.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(entityplayer.inventory, i, 8 + i * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return ItemStack.areItemStacksEqual(theCrackerItem, entityplayer.inventory.getCurrentItem());
	}

	public boolean isCrackerInvEmpty() {
		for (int i = 0; i < crackerInventory.getSizeInventory(); ++i) {
			ItemStack itemstack = crackerInventory.getStackInSlot(i);
			if (itemstack == null) {
				continue;
			}
			return false;
		}
		return true;
	}

	@Override
	public void onContainerClosed(EntityPlayer entityplayer) {
		super.onContainerClosed(entityplayer);
		if (!entityplayer.worldObj.isRemote) {
			for (int i = 0; i < crackerInventory.getSizeInventory(); ++i) {
				ItemStack itemstack = crackerInventory.getStackInSlotOnClosing(i);
				if (itemstack == null) {
					continue;
				}
				entityplayer.dropPlayerItemWithRandomChoice(itemstack, false);
			}
		}
	}

	public void receiveSealingPacket(ICommandSender entityplayer) {
		if (!isCrackerInvEmpty()) {
			IInventory tempContents = new InventoryBasic("crackerTemp", false, crackerInventory.getSizeInventory());
			for (int i = 0; i < tempContents.getSizeInventory(); ++i) {
				tempContents.setInventorySlotContents(i, crackerInventory.getStackInSlot(i));
				crackerInventory.setInventorySlotContents(i, null);
			}
			LOTRItemDaleCracker.setEmpty(theCrackerItem, false);
			LOTRItemDaleCracker.setSealingPlayerName(theCrackerItem, entityplayer.getCommandSenderName());
			LOTRItemDaleCracker.setCustomCrackerContents(theCrackerItem, tempContents);
		}
	}

	public void sendSealingPacket(EntityPlayer entityplayer) {
		IMessage packet = new LOTRPacketSealCracker();
		LOTRPacketHandler.networkWrapper.sendToServer(packet);
	}

	@Override
	public ItemStack slotClick(int slotNo, int j, int k, EntityPlayer entityplayer) {
		if (slotNo >= 0 && slotNo < inventorySlots.size()) {
			Slot slot = (Slot) inventorySlots.get(slotNo);
			if (slot.inventory == entityplayer.inventory && slot.getSlotIndex() == entityplayer.inventory.currentItem) {
				return null;
			}
		}
		return super.slotClick(slotNo, j, k, entityplayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		Slot aCrackerSlot = getSlotFromInventory(crackerInventory, 0);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i < capacity ? !mergeItemStack(itemstack1, capacity, capacity + 36, true) : aCrackerSlot.isItemValid(itemstack1) && !mergeItemStack(itemstack1, 0, capacity, false)) {
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
