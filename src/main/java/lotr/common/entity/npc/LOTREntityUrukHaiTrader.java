package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityUrukHaiTrader extends LOTREntityUrukHai implements LOTRTradeable.Smith {
	public LOTREntityUrukHaiTrader(World world) {
		super(world);
		addTargetTasks(false);
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return LOTRLevelData.getData(entityplayer).getAlignment(getFaction()) >= 100.0f && isFriendly(entityplayer);
	}

	@Override
	public LOTRTradeEntries getBuyPool() {
		return LOTRTradeEntries.URUK_HAI_TRADER_BUY;
	}

	@Override
	public LOTRTradeEntries getSellPool() {
		return LOTRTradeEntries.URUK_HAI_TRADER_SELL;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (canTradeWith(entityplayer)) {
				return "isengard/trader/friendly";
			}
			return "isengard/trader/neutral";
		}
		return "isengard/orc/hostile";
	}

	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
		LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeUrukTrader);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerUrukPoisoned));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsFur));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsFur));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyFur));
		setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetFur));
		return data;
	}

	@Override
	public boolean shouldTraderRespawn() {
		return true;
	}
}
