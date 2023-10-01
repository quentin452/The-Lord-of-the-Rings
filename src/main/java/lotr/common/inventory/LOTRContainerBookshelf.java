package lotr.common.inventory;

import lotr.common.tileentity.LOTRTileEntityBookshelf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRContainerBookshelf extends Container {
	public LOTRTileEntityBookshelf shelfInv;
	public int numRows;

	public LOTRContainerBookshelf(IInventory player, LOTRTileEntityBookshelf shelf) {
		int j;
		int i;
		shelfInv = shelf;
		numRows = shelfInv.getSizeInventory() / 9;
		shelfInv.openInventory();
		int playerSlotY = (numRows - 4) * 18;
		for (j = 0; j < numRows; ++j) {
			for (i = 0; i < 9; ++i) {
				addSlotToContainer(new Slot(shelfInv, i + j * 9, 8 + i * 18, 18 + j * 18) {

					@Override
					public boolean isItemValid(ItemStack itemstack) {
						return LOTRTileEntityBookshelf.isBookItem(itemstack);
					}
				});
			}
		}
		for (j = 0; j < 3; ++j) {
			for (i = 0; i < 9; ++i) {
				addSlotToContainer(new Slot(player, i + j * 9 + 9, 8 + i * 18, 103 + j * 18 + playerSlotY));
			}
		}
		for (int i2 = 0; i2 < 9; ++i2) {
			addSlotToContainer(new Slot(player, i2, 8 + i2 * 18, 161 + playerSlotY));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return shelfInv.isUseableByPlayer(entityplayer);
	}

	@Override
	public void onContainerClosed(EntityPlayer entityplayer) {
		boolean anyContents = false;
		for (int i = 0; i < shelfInv.getSizeInventory(); ++i) {
			if (shelfInv.getStackInSlot(i) == null) {
				continue;
			}
			anyContents = true;
			break;
		}
		super.onContainerClosed(entityplayer);
		shelfInv.closeInventory();
		if (!anyContents && shelfInv.numPlayersUsing <= 0) {
			World world = shelfInv.getWorldObj();
			if (!world.isRemote) {
				world.setBlock(shelfInv.xCoord, shelfInv.yCoord, shelfInv.zCoord, Blocks.bookshelf, 0, 3);
			}
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i < numRows * 9) {
				if (!mergeItemStack(itemstack1, numRows * 9, inventorySlots.size(), true)) {
					return null;
				}
			} else if (LOTRTileEntityBookshelf.isBookItem(itemstack)) {
				if (!mergeItemStack(itemstack1, 0, numRows * 9, false)) {
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
		}
		return itemstack;
	}

}
