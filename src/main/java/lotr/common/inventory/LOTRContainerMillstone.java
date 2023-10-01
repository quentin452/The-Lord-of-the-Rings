package lotr.common.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.recipe.LOTRMillstoneRecipes;
import lotr.common.tileentity.LOTRTileEntityMillstone;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class LOTRContainerMillstone extends Container {
	public LOTRTileEntityMillstone theMillstone;
	public int currentMillTime;
	public boolean isMilling;

	public LOTRContainerMillstone(InventoryPlayer inv, LOTRTileEntityMillstone millstone) {
		int i;
		theMillstone = millstone;
		addSlotToContainer(new Slot(millstone, 0, 84, 25));
		addSlotToContainer(new LOTRSlotMillstone(inv.player, millstone, 1, 84, 71));
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 100 + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(inv, i, 8 + i * 18, 158));
		}
	}

	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, theMillstone.currentMillTime);
		crafting.sendProgressBarUpdate(this, 1, theMillstone.isMilling ? 1 : 0);
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return theMillstone.isUseableByPlayer(entityplayer);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (Object element : crafters) {
			ICrafting crafting = (ICrafting) element;
			if (currentMillTime != theMillstone.currentMillTime) {
				crafting.sendProgressBarUpdate(this, 0, theMillstone.currentMillTime);
			}
			if (isMilling == theMillstone.isMilling) {
				continue;
			}
			crafting.sendProgressBarUpdate(this, 1, theMillstone.isMilling ? 1 : 0);
		}
		currentMillTime = theMillstone.currentMillTime;
		isMilling = theMillstone.isMilling;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i == 1) {
				if (!mergeItemStack(itemstack1, 2, 38, true)) {
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			} else if (i != 0 ? LOTRMillstoneRecipes.getMillingResult(itemstack1) != null ? !mergeItemStack(itemstack1, 0, 1, false) : i < 29 ? !mergeItemStack(itemstack1, 29, 38, false) : i < 38 && !mergeItemStack(itemstack1, 2, 29, false) : !mergeItemStack(itemstack1, 2, 38, false)) {
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
			theMillstone.currentMillTime = j;
		}
		if (i == 1) {
			theMillstone.isMilling = j == 1;
		}
	}
}
