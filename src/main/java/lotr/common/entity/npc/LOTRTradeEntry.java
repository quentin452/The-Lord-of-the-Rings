package lotr.common.entity.npc;

import lotr.common.item.LOTRItemMug;
import lotr.common.quest.IPickpocketable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

public class LOTRTradeEntry {
	public ItemStack tradeItem;
	public int tradeCost;
	public int recentTradeValue;
	public int lockedTicks;
	public LOTRTraderNPCInfo theTrader;

	public LOTRTradeEntry(ItemStack itemstack, int cost) {
		tradeItem = itemstack;
		tradeCost = cost;
	}

	public static LOTRTradeEntry readFromNBT(NBTTagCompound nbt) {
		ItemStack savedItem = ItemStack.loadItemStackFromNBT(nbt);
		if (savedItem != null) {
			int cost = nbt.getInteger("Cost");
			LOTRTradeEntry trade = new LOTRTradeEntry(savedItem, cost);
			if (nbt.hasKey("RecentTradeValue")) {
				trade.recentTradeValue = nbt.getInteger("RecentTradeValue");
			}
			trade.lockedTicks = nbt.getInteger("LockedTicks");
			return trade;
		}
		return null;
	}

	public ItemStack createTradeItem() {
		return tradeItem.copy();
	}

	public void doTransaction(int value) {
		recentTradeValue += value;
	}

	public int getCost() {
		return tradeCost;
	}

	public void setCost(int cost) {
		tradeCost = cost;
	}

	public float getLockedProgress() {
		if (theTrader != null && theTrader.shouldLockTrades()) {
			return (float) recentTradeValue / theTrader.getLockTradeAtValue();
		}
		return 0.0f;
	}

	public int getLockedProgressForSlot() {
		return getLockedProgressInt(16);
	}

	public int getLockedProgressInt(int i) {
		float f = getLockedProgress();
		return Math.round(f * i);
	}

	public boolean isAvailable() {
		if (theTrader != null && theTrader.shouldLockTrades()) {
			return recentTradeValue < theTrader.getLockTradeAtValue() && lockedTicks <= 0;
		}
		return true;
	}

	public boolean matches(ItemStack itemstack) {
		if (IPickpocketable.Helper.isPickpocketed(itemstack)) {
			return false;
		}
		ItemStack tradeCreated = createTradeItem();
		if (LOTRItemMug.isItemFullDrink(tradeCreated)) {
			ItemStack tradeDrink = LOTRItemMug.getEquivalentDrink(tradeCreated);
			ItemStack offerDrink = LOTRItemMug.getEquivalentDrink(itemstack);
			return tradeDrink.getItem() == offerDrink.getItem();
		}
		return OreDictionary.itemMatches(tradeCreated, itemstack, false);
	}

	public void setLockedForTicks(int ticks) {
		lockedTicks = ticks;
	}

	public void setOwningTrader(LOTRTraderNPCInfo trader) {
		if (theTrader != null) {
			throw new IllegalArgumentException("Cannot assign already-owned trade entry to a different trader!");
		}
		theTrader = trader;
	}

	public boolean updateAvailability(int tick) {
		boolean prevAvailable = isAvailable();
		int prevLockProgress = getLockedProgressForSlot();
		if (tick % theTrader.getValueDecayTicks() == 0 && recentTradeValue > 0) {
			--recentTradeValue;
		}
		if (lockedTicks > 0) {
			--lockedTicks;
		}
		if (isAvailable() != prevAvailable) {
			return true;
		}
		return getLockedProgressForSlot() != prevLockProgress;
	}

	public void writeToNBT(NBTTagCompound nbt) {
		tradeItem.writeToNBT(nbt);
		nbt.setInteger("Cost", tradeCost);
		nbt.setInteger("RecentTradeValue", recentTradeValue);
		nbt.setInteger("LockedTicks", lockedTicks);
	}
}
