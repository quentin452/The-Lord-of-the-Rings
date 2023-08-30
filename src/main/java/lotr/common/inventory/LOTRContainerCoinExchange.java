package lotr.common.inventory;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.item.LOTRItemCoin;
import lotr.common.quest.IPickpocketable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class LOTRContainerCoinExchange extends Container {
	public IInventory coinInputInv = new InventoryCoinExchangeSlot(1);
	public IInventory exchangeInv = new InventoryCoinExchangeSlot(2);
	public LOTREntityNPC theTraderNPC;
	public boolean exchanged;

	public LOTRContainerCoinExchange(EntityPlayer entityplayer, LOTREntityNPC npc) {
		int i;
		theTraderNPC = npc;
		addSlotToContainer(new Slot(coinInputInv, 0, 80, 46) {

			@Override
			public boolean isItemValid(ItemStack itemstack) {
				return super.isItemValid(itemstack) && itemstack != null && isValidCoin(itemstack);
			}
		});
		addSlotToContainer(new LOTRSlotCoinResult(this, exchangeInv, 0, 26, 46));
		addSlotToContainer(new LOTRSlotCoinResult(this, exchangeInv, 1, 134, 46));
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(entityplayer.inventory, j + i * 9 + 9, 8 + j * 18, 106 + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(entityplayer.inventory, i, 8 + i * 18, 164));
		}
		onCraftMatrixChanged(coinInputInv);
	}

	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		sendClientExchangedData(crafting);
		super.addCraftingToCrafters(crafting);
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void detectAndSendChanges() {
		for (Object crafter : crafters) {
			ICrafting crafting = (ICrafting) crafter;
			sendClientExchangedData(crafting);
		}
		super.detectAndSendChanges();
	}

	public void handleExchangePacket(int slot) {
		if (!exchanged && coinInputInv.getStackInSlot(0) != null && slot >= 0 && slot < exchangeInv.getSizeInventory() && exchangeInv.getStackInSlot(slot) != null) {
			exchanged = true;
			int coins = exchangeInv.getStackInSlot(slot).stackSize;
			int coinsTaken = 0;
			if (slot == 0) {
				coinsTaken = coins / 10;
			} else if (slot == 1) {
				coinsTaken = coins * 10;
			}
			coinInputInv.decrStackSize(0, coinsTaken);
			for (int i = 0; i < exchangeInv.getSizeInventory(); ++i) {
				if (i == slot) {
					continue;
				}
				exchangeInv.setInventorySlotContents(i, null);
			}
			detectAndSendChanges();
			theTraderNPC.playTradeSound();
		}
	}

	@Override
	public void onContainerClosed(EntityPlayer entityplayer) {
		super.onContainerClosed(entityplayer);
		if (!entityplayer.worldObj.isRemote) {
			int i;
			ItemStack itemstack;
			for (i = 0; i < coinInputInv.getSizeInventory(); ++i) {
				itemstack = coinInputInv.getStackInSlotOnClosing(i);
				if (itemstack == null) {
					continue;
				}
				entityplayer.dropPlayerItemWithRandomChoice(itemstack, false);
			}
			if (exchanged) {
				for (i = 0; i < exchangeInv.getSizeInventory(); ++i) {
					itemstack = exchangeInv.getStackInSlotOnClosing(i);
					if (itemstack == null) {
						continue;
					}
					entityplayer.dropPlayerItemWithRandomChoice(itemstack, false);
				}
			}
		}
	}

	@Override
	public void onCraftMatrixChanged(IInventory inv) {
		if (inv == coinInputInv) {
			if (!exchanged) {
				ItemStack coin = coinInputInv.getStackInSlot(0);
				if (coin != null && coin.stackSize > 0 && isValidCoin(coin)) {
					int coins = coin.stackSize;
					int coinType = coin.getItemDamage();
					if (coinType > 0) {
						int coinsFloor = coins;
						while (coinsFloor * 10 > exchangeInv.getInventoryStackLimit()) {
							--coinsFloor;
						}
						exchangeInv.setInventorySlotContents(0, new ItemStack(LOTRMod.silverCoin, coinsFloor * 10, coinType - 1));
					} else {
						exchangeInv.setInventorySlotContents(0, null);
					}
					if (coinType < LOTRItemCoin.values.length - 1 && coins >= 10) {
						exchangeInv.setInventorySlotContents(1, new ItemStack(LOTRMod.silverCoin, coins / 10, coinType + 1));
					} else {
						exchangeInv.setInventorySlotContents(1, null);
					}
				} else {
					exchangeInv.setInventorySlotContents(0, null);
					exchangeInv.setInventorySlotContents(1, null);
				}
			}
		} else if (inv == exchangeInv && exchanged) {
			boolean anyItems = false;
			for (int i = 0; i < exchangeInv.getSizeInventory(); ++i) {
				if (exchangeInv.getStackInSlot(i) == null) {
					continue;
				}
				anyItems = true;
			}
			if (!anyItems) {
				exchanged = false;
				onCraftMatrixChanged(coinInputInv);
			}
		}
		super.onCraftMatrixChanged(inv);
	}

	@Override
	public void retrySlotClick(int i, int j, boolean flag, EntityPlayer entityplayer) {
	}

	public void sendClientExchangedData(ICrafting crafting) {
		crafting.sendProgressBarUpdate(this, 0, exchanged ? 1 : 0);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i < 3) {
				if (!mergeItemStack(itemstack1, 3, 39, true)) {
					return null;
				}
				onCraftMatrixChanged(slot.inventory);
			} else {
				boolean flag = false;
				Slot coinSlot = (Slot) inventorySlots.get(0);
				coinSlot.getStack();
				if (coinSlot.isItemValid(itemstack1) && mergeItemStack(itemstack1, 0, 1, true)) {
					flag = true;
				}
				if (!flag && (i < 30 ? !mergeItemStack(itemstack1, 30, 39, false) : !mergeItemStack(itemstack1, 3, 30, false))) {
					return null;
				}
			}
			if (itemstack1.stackSize == 0) {
				slot.putStack(null);
				detectAndSendChanges();
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

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int i, int j) {
		if (i == 0) {
			exchanged = j == 1;
		}
	}

	public static boolean isValidCoin(ItemStack item) {
		return item.getItem() == LOTRMod.silverCoin && !IPickpocketable.Helper.isPickpocketed(item);
	}

	public class InventoryCoinExchangeSlot extends InventoryBasic {
		public InventoryCoinExchangeSlot(int i) {
			super("coinExchange", true, i);
		}

		@Override
		public void markDirty() {
			super.markDirty();
			onCraftMatrixChanged(this);
		}
	}

}
