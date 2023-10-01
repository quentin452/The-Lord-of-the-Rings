package lotr.common.inventory;

import lotr.common.item.LOTRItemPouch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class LOTRInventoryPouch extends InventoryBasic {
	public LOTRContainerPouch theContainer;
	public EntityPlayer thePlayer;
	public int playerSlot;
	public boolean isTemporary;
	public ItemStack tempPouchItem;

	public LOTRInventoryPouch(EntityPlayer entityplayer, LOTRContainerPouch container, int slot) {
		super(entityplayer.inventory.getStackInSlot(slot).getDisplayName(), true, LOTRItemPouch.getCapacity(entityplayer.inventory.getStackInSlot(slot)));
		isTemporary = false;
		thePlayer = entityplayer;
		theContainer = container;
		playerSlot = slot;
		if (!thePlayer.worldObj.isRemote) {
			loadPouchContents();
		}
	}

	public LOTRInventoryPouch(ItemStack itemstack) {
		super("tempPouch", true, LOTRItemPouch.getCapacity(itemstack));
		isTemporary = true;
		tempPouchItem = itemstack;
		loadPouchContents();
	}

	@Override
	public String getInventoryName() {
		return getPouchItem().getDisplayName();
	}

	public ItemStack getPouchItem() {
		if (isTemporary) {
			return tempPouchItem;
		}
		return thePlayer.inventory.getStackInSlot(playerSlot);
	}

	public void loadPouchContents() {
		if (getPouchItem().hasTagCompound() && getPouchItem().getTagCompound().hasKey("LOTRPouchData")) {
			NBTTagCompound nbt = getPouchItem().getTagCompound().getCompoundTag("LOTRPouchData");
			NBTTagList items = nbt.getTagList("Items", 10);
			for (int i = 0; i < items.tagCount(); ++i) {
				NBTTagCompound itemData = items.getCompoundTagAt(i);
				byte slot = itemData.getByte("Slot");
				if (slot < 0 || slot >= getSizeInventory()) {
					continue;
				}
				setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(itemData));
			}
		}
		if (!isTemporary) {
			theContainer.syncPouchItem(getPouchItem());
		}
	}

	@Override
	public void markDirty() {
		super.markDirty();
		if (isTemporary || !thePlayer.worldObj.isRemote) {
			savePouchContents();
		}
	}

	public void savePouchContents() {
		if (!getPouchItem().hasTagCompound()) {
			getPouchItem().setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound nbt = new NBTTagCompound();
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
		nbt.setTag("Items", items);
		getPouchItem().getTagCompound().setTag("LOTRPouchData", nbt);
		if (!isTemporary) {
			theContainer.syncPouchItem(getPouchItem());
		}
	}
}
