package lotr.common.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.tileentity.LOTRTileEntityAlloyForgeBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class LOTRContainerAlloyForge extends Container {
	public LOTRTileEntityAlloyForgeBase theForge;
	public int currentSmeltTime;
	public int forgeSmeltTime;
	public int currentItemFuelValue;

	public LOTRContainerAlloyForge(InventoryPlayer inv, LOTRTileEntityAlloyForgeBase forge) {
		int i;
		theForge = forge;
		for (i = 0; i < 4; ++i) {
			addSlotToContainer(new Slot(forge, i, 53 + i * 18, 21));
		}
		for (i = 0; i < 4; ++i) {
			addSlotToContainer(new Slot(forge, i + 4, 53 + i * 18, 39));
		}
		for (i = 0; i < 4; ++i) {
			addSlotToContainer(new SlotFurnace(inv.player, forge, i + 8, 53 + i * 18, 85));
		}
		addSlotToContainer(new Slot(forge, 12, 80, 129));
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 151 + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(inv, i, 8 + i * 18, 209));
		}
	}

	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, theForge.currentSmeltTime);
		crafting.sendProgressBarUpdate(this, 1, theForge.forgeSmeltTime);
		crafting.sendProgressBarUpdate(this, 2, theForge.currentItemFuelValue);
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return theForge.isUseableByPlayer(entityplayer);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (Object element : crafters) {
			ICrafting crafting = (ICrafting) element;
			if (currentSmeltTime != theForge.currentSmeltTime) {
				crafting.sendProgressBarUpdate(this, 0, theForge.currentSmeltTime);
			}
			if (forgeSmeltTime != theForge.forgeSmeltTime) {
				crafting.sendProgressBarUpdate(this, 1, theForge.forgeSmeltTime);
			}
			if (currentItemFuelValue == theForge.currentItemFuelValue) {
				continue;
			}
			crafting.sendProgressBarUpdate(this, 2, theForge.currentItemFuelValue);
		}
		currentSmeltTime = theForge.currentSmeltTime;
		forgeSmeltTime = theForge.forgeSmeltTime;
		currentItemFuelValue = theForge.currentItemFuelValue;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i >= 8 && i < 12) {
				if (!mergeItemStack(itemstack1, 13, 49, true)) {
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			} else if (i >= 8 && i != 12 ? theForge.getSmeltingResult(itemstack1) != null ? !mergeItemStack(itemstack1, 4, 8, false) : TileEntityFurnace.isItemFuel(itemstack1) ? !mergeItemStack(itemstack1, 12, 13, false) : i < 40 ? !mergeItemStack(itemstack1, 40, 49, false) : i < 49 && !mergeItemStack(itemstack1, 13, 40, false) : !mergeItemStack(itemstack1, 13, 49, false)) {
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

	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int i, int j) {
		if (i == 0) {
			theForge.currentSmeltTime = j;
		}
		if (i == 1) {
			theForge.forgeSmeltTime = j;
		}
		if (i == 2) {
			theForge.currentItemFuelValue = j;
		}
	}
}
