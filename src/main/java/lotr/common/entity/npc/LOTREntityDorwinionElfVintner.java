package lotr.common.entity.npc;

import lotr.common.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDorwinionElfVintner extends LOTREntityDorwinionElf implements LOTRTradeable {
	public LOTREntityDorwinionElfVintner(World world) {
		super(world);
		addTargetTasks(false);
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return LOTRLevelData.getData(entityplayer).getAlignment(getFaction()) >= 50.0f && isFriendly(entityplayer);
	}

	@Override
	public LOTRTradeEntries getBuyPool() {
		return LOTRTradeEntries.DORWINION_VINTNER_BUY;
	}

	@Override
	public LOTRTradeEntries getSellPool() {
		return LOTRTradeEntries.DORWINION_VINTNER_SELL;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (canTradeWith(entityplayer)) {
				return "dorwinion/elfVintner/friendly";
			}
			return "dorwinion/elfVintner/neutral";
		}
		return "dorwinion/elf/hostile";
	}

	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
		if (type == LOTRTradeEntries.TradeType.BUY) {
			LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.buyWineVintner);
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		ItemStack drink = getBuyPool().getRandomTrades(rand)[0].createTradeItem();
		npcItemsInv.setIdleItem(drink);
		return data;
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
