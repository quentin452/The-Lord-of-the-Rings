package lotr.common.inventory;

import lotr.common.entity.npc.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRContainerTrade extends Container {
	public IInventory tradeInvBuy = new InventoryBasic("trade", false, 9);
	public IInventory tradeInvSell = new InventoryBasic("trade", false, 9);
	public IInventory tradeInvSellOffer = new InventoryBasic("trade", false, 9);
	public LOTRTradeable theTrader;
	public LOTREntityNPC theTraderNPC;
	public World theWorld;

	public LOTRContainerTrade(InventoryPlayer inv, LOTRTradeable trader, World world) {
		int i;
		theTrader = trader;
		theTraderNPC = (LOTREntityNPC) trader;
		theWorld = world;
		if (!world.isRemote) {
			updateAllTradeSlots();
		}
		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new LOTRSlotTrade(this, tradeInvBuy, i, 8 + i * 18, 40, theTraderNPC, LOTRTradeEntries.TradeType.BUY));
		}
		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new LOTRSlotTrade(this, tradeInvSell, i, 8 + i * 18, 92, theTraderNPC, LOTRTradeEntries.TradeType.SELL));
		}
		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(tradeInvSellOffer, i, 8 + i * 18, 141));
		}
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 188 + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(inv, i, 8 + i * 18, 246));
		}
	}

	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		theTraderNPC.traderNPCInfo.sendClientPacket((EntityPlayer) crafting);
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return theTraderNPC != null && entityplayer.getDistanceToEntity(theTraderNPC) <= 12.0 && theTraderNPC.isEntityAlive() && theTraderNPC.getAttackTarget() == null && theTrader.canTradeWith(entityplayer);
	}

	@Override
	public void onContainerClosed(EntityPlayer entityplayer) {
		super.onContainerClosed(entityplayer);
		if (!theWorld.isRemote) {
			for (int i = 0; i < tradeInvSellOffer.getSizeInventory(); ++i) {
				ItemStack itemstack = tradeInvSellOffer.getStackInSlotOnClosing(i);
				if (itemstack == null) {
					continue;
				}
				entityplayer.dropPlayerItemWithRandomChoice(itemstack, false);
			}
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			boolean sellable;
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			LOTRTradeSellResult sellResult = LOTRTradeEntries.getItemSellResult(itemstack1, theTraderNPC);
			sellable = sellResult != null && sellResult.tradesMade > 0;
			if (i < 9) {
				if (!mergeItemStack(itemstack1, 27, 63, true)) {
					return null;
				}
			} else if (i < 18 || (i < 27 ? !mergeItemStack(itemstack1, 27, 63, true) : sellable ? !mergeItemStack(itemstack1, 18, 27, false) : i < 54 ? !mergeItemStack(itemstack1, 54, 63, false) : i < 63 && !mergeItemStack(itemstack1, 27, 54, false))) {
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

	public void updateAllTradeSlots() {
		LOTRTradeEntry trade;
		int i;
		LOTRTradeEntry[] buyTrades = theTraderNPC.traderNPCInfo.getBuyTrades();
		LOTRTradeEntry[] sellTrades = theTraderNPC.traderNPCInfo.getSellTrades();
		if (buyTrades != null) {
			for (i = 0; i < tradeInvBuy.getSizeInventory(); ++i) {
				trade = null;
				if (i < buyTrades.length) {
					trade = buyTrades[i];
				}
				if (trade != null) {
					tradeInvBuy.setInventorySlotContents(i, trade.createTradeItem());
					continue;
				}
				tradeInvBuy.setInventorySlotContents(i, null);
			}
		}
		if (sellTrades != null) {
			for (i = 0; i < tradeInvSell.getSizeInventory(); ++i) {
				trade = null;
				if (i < sellTrades.length) {
					trade = sellTrades[i];
				}
				if (trade != null) {
					tradeInvSell.setInventorySlotContents(i, trade.createTradeItem());
					continue;
				}
				tradeInvSell.setInventorySlotContents(i, null);
			}
		}
	}
}
