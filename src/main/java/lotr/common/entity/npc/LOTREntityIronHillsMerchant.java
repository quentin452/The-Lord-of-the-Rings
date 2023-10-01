package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityIronHillsMerchant extends LOTREntityDwarf implements LOTRTravellingTrader {
	public LOTREntityIronHillsMerchant(World world) {
		super(world);
		addTargetTasks(false);
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return LOTRLevelData.getData(entityplayer).getAlignment(getFaction()) >= 0.0f && isFriendly(entityplayer);
	}

	@Override
	public LOTREntityNPC createTravellingEscort() {
		return new LOTREntityDwarf(worldObj);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public LOTRTradeEntries getBuyPool() {
		return LOTRTradeEntries.IRON_HILLS_MERCHANT_BUY;
	}

	@Override
	public String getDepartureSpeech() {
		return "dwarf/merchant/departure";
	}

	@Override
	public LOTRTradeEntries getSellPool() {
		return LOTRTradeEntries.IRON_HILLS_MERCHANT_SELL;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			return "dwarf/merchant/friendly";
		}
		return "dwarf/dwarf/hostile";
	}

	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
		LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeIronHillsMerchant);
	}

	@Override
	public boolean shouldTraderRespawn() {
		return false;
	}
}
