package lotr.common.network;

import java.util.*;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.entity.npc.*;
import lotr.common.inventory.LOTRContainerTrade;
import lotr.common.item.LOTRItemCoin;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class LOTRPacketSell implements IMessage {
	@Override
	public void fromBytes(ByteBuf data) {
	}

	@Override
	public void toBytes(ByteBuf data) {
	}

	public static class Handler implements IMessageHandler<LOTRPacketSell, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketSell packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			Container container = entityplayer.openContainer;
			if (container instanceof LOTRContainerTrade) {
				LOTRContainerTrade tradeContainer = (LOTRContainerTrade) container;
				LOTREntityNPC trader = tradeContainer.theTraderNPC;
				IInventory invSellOffer = tradeContainer.tradeInvSellOffer;
				Map<LOTRTradeEntry, Integer> tradesUsed = new HashMap<>();
				int totalCoins = 0;
				for (int i = 0; i < invSellOffer.getSizeInventory(); ++i) {
					LOTRTradeSellResult sellResult;
					ItemStack itemstack = invSellOffer.getStackInSlot(i);
					if (itemstack == null || (sellResult = LOTRTradeEntries.getItemSellResult(itemstack, trader)) == null) {
						continue;
					}
					int tradeIndex = sellResult.tradeIndex;
					int value = sellResult.totalSellValue;
					int itemsSold = sellResult.itemsSold;
					LOTRTradeEntry[] sellTrades = trader.traderNPCInfo.getSellTrades();
					LOTRTradeEntry trade = null;
					if (sellTrades != null) {
						trade = sellTrades[tradeIndex];
					}
					totalCoins += value;
					if (trade != null) {
						tradesUsed.merge(trade, value, Integer::sum);
					}
					itemstack.stackSize -= itemsSold;
					if (itemstack.stackSize > 0) {
						continue;
					}
					invSellOffer.setInventorySlotContents(i, null);
				}
				if (totalCoins > 0) {
					for (Map.Entry e : tradesUsed.entrySet()) {
						LOTRTradeEntry trade = (LOTRTradeEntry) e.getKey();
						int value = (Integer) e.getValue();
						trader.traderNPCInfo.onTrade(entityplayer, trade, LOTRTradeEntries.TradeType.SELL, value);
					}
					LOTRItemCoin.giveCoins(totalCoins, entityplayer);
					if (totalCoins >= 1000) {
						LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.earnManyCoins);
					}
					trader.playTradeSound();
				}
			}
			return null;
		}
	}

}
