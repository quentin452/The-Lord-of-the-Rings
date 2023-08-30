package lotr.common.entity.npc;

import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface LOTRTradeable {
	boolean canTradeWith(EntityPlayer var1);

	LOTRTradeEntries getBuyPool();

	LOTRFaction getFaction();

	String getNPCName();

	LOTRTradeEntries getSellPool();

	void onPlayerTrade(EntityPlayer var1, LOTRTradeEntries.TradeType var2, ItemStack var3);

	boolean shouldTraderRespawn();

	interface Bartender extends LOTRTradeable {
	}

	interface Smith extends LOTRTradeable {
	}

}
