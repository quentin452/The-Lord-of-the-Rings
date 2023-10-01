package lotr.common.inventory;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class LOTRSlotAnvilOutput extends Slot {
	public LOTRContainerAnvil theAnvil;

	public LOTRSlotAnvilOutput(LOTRContainerAnvil container, IInventory inv, int id, int i, int j) {
		super(inv, id, i, j);
		theAnvil = container;
	}

	@Override
	public boolean canTakeStack(EntityPlayer entityplayer) {
		if (getHasStack()) {
			if (theAnvil.materialCost > 0) {
				return theAnvil.hasMaterialOrCoinAmount(theAnvil.materialCost);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return false;
	}

	@Override
	public void onPickupFromSlot(EntityPlayer entityplayer, ItemStack itemstack) {
		int materials = theAnvil.materialCost;
		boolean wasSmithCombine = theAnvil.isSmithScrollCombine;
		theAnvil.invInput.setInventorySlotContents(0, null);
		ItemStack combinerItem = theAnvil.invInput.getStackInSlot(1);
		if (combinerItem != null) {
			--combinerItem.stackSize;
			if (combinerItem.stackSize <= 0) {
				theAnvil.invInput.setInventorySlotContents(1, null);
			} else {
				theAnvil.invInput.setInventorySlotContents(1, combinerItem);
			}
		}
		if (materials > 0) {
			theAnvil.takeMaterialOrCoinAmount(materials);
		}
		if (!entityplayer.worldObj.isRemote && wasSmithCombine) {
			LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.combineSmithScrolls);
		}
		theAnvil.materialCost = 0;
		theAnvil.isSmithScrollCombine = false;
		theAnvil.playAnvilSound();
	}
}
