package lotr.common.entity.npc;

import lotr.common.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityRivendellSmith extends LOTREntityRivendellElf implements LOTRTradeable.Smith {
	public LOTREntityRivendellSmith(World world) {
		super(world);
		addTargetTasks(false);
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return LOTRLevelData.getData(entityplayer).getAlignment(getFaction()) >= 100.0f && isFriendly(entityplayer);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public LOTRTradeEntries getBuyPool() {
		return LOTRTradeEntries.RIVENDELL_SMITH_BUY;
	}

	@Override
	public LOTRTradeEntries getSellPool() {
		return LOTRTradeEntries.RIVENDELL_SMITH_SELL;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (canTradeWith(entityplayer)) {
				return "rivendell/smith/friendly";
			}
			return "rivendell/smith/neutral";
		}
		return "rivendell/smith/hostile";
	}

	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
		if (type == LOTRTradeEntries.TradeType.BUY) {
			LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeRivendellSmith);
		}
	}

	@Override
	public boolean shouldRenderNPCHair() {
		return false;
	}

	@Override
	public boolean shouldTraderRespawn() {
		return true;
	}
}
