package lotr.common.inventory;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;

public class LOTREntityInventory extends InventoryBasic {
	public EntityLivingBase theEntity;
	public String nbtName;

	public LOTREntityInventory(String s, EntityLivingBase npc, int i) {
		super(s, true, i);
		theEntity = npc;
		nbtName = s;
	}

	public boolean addItemToInventory(ItemStack itemstack) {
		int origStack = itemstack.stackSize;
		if (itemstack.stackSize > 0) {
			for (int i = 0; i < getSizeInventory() && itemstack.stackSize > 0; ++i) {
				ItemStack itemInSlot = getStackInSlot(i);
				if (itemInSlot != null && (itemInSlot.stackSize >= itemInSlot.getMaxStackSize() || !itemstack.isItemEqual(itemInSlot) || !ItemStack.areItemStackTagsEqual(itemInSlot, itemstack))) {
					continue;
				}
				if (itemInSlot == null) {
					ItemStack copy = itemstack.copy();
					copy.stackSize = Math.min(copy.stackSize, getInventoryStackLimit());
					setInventorySlotContents(i, copy);
					itemstack.stackSize -= copy.stackSize;
					continue;
				}
				int maxStackSize = itemInSlot.getMaxStackSize();
				maxStackSize = Math.min(maxStackSize, getInventoryStackLimit());
				int difference = maxStackSize - itemInSlot.stackSize;
				difference = Math.min(difference, itemstack.stackSize);
				itemstack.stackSize -= difference;
				itemInSlot.stackSize += difference;
				setInventorySlotContents(i, itemInSlot);
			}
		}
		return itemstack.stackSize < origStack;
	}

	public void dropAllItems() {
		for (int i = 0; i < getSizeInventory(); ++i) {
			ItemStack itemstack = getStackInSlot(i);
			if (itemstack == null) {
				continue;
			}
			dropItem(itemstack);
			setInventorySlotContents(i, null);
		}
	}

	public void dropItem(ItemStack itemstack) {
		theEntity.entityDropItem(itemstack, 0.0f);
	}

	public boolean isEmpty() {
		for (int i = 0; i < getSizeInventory(); ++i) {
			if (getStackInSlot(i) == null) {
				continue;
			}
			return false;
		}
		return true;
	}

	public boolean isFull() {
		for (int i = 0; i < getSizeInventory(); ++i) {
			if (getStackInSlot(i) != null) {
				continue;
			}
			return false;
		}
		return true;
	}

	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagList items = nbt.getTagList(nbtName, 10);
		for (int i = 0; i < items.tagCount(); ++i) {
			NBTTagCompound itemData = items.getCompoundTagAt(i);
			byte slot = itemData.getByte("Slot");
			if (slot < 0 || slot >= getSizeInventory()) {
				continue;
			}
			setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(itemData));
		}
	}

	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagList items = new NBTTagList();
		for (int i = 0; i < getSizeInventory(); ++i) {
			ItemStack itemstack = getStackInSlot(i);
			if (itemstack == null) {
				continue;
			}
			NBTTagCompound itemData = new NBTTagCompound();
			itemData.setByte("Slot", (byte) i);
			itemstack.writeToNBT(itemData);
			items.appendTag(itemData);
		}
		nbt.setTag(nbtName, items);
	}
}
