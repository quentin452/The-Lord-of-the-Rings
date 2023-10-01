package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityHighElfSmith extends LOTREntityHighElf implements LOTRTradeable.Smith {
	public LOTREntityHighElfSmith(World world) {
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
		return LOTRTradeEntries.HIGH_ELF_SMITH_BUY;
	}

	@Override
	public LOTRTradeEntries getSellPool() {
		return LOTRTradeEntries.HIGH_ELF_SMITH_SELL;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (canTradeWith(entityplayer)) {
				return "highElf/smith/friendly";
			}
			return "highElf/smith/neutral";
		}
		return "highElf/smith/hostile";
	}

	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
		if (type == LOTRTradeEntries.TradeType.BUY) {
			LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeHighElfSmith);
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
