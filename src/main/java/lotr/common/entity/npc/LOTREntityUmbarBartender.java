package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFoods;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityUmbarBartender extends LOTREntityUmbarian implements LOTRTradeable.Bartender {
	public LOTREntityUmbarBartender(World world) {
		super(world);
		addTargetTasks(false);
		npcLocationName = "entity.lotr.UmbarBartender.locationName";
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return isFriendly(entityplayer);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int drinks = 1 + rand.nextInt(4) + i;
		for (int l = 0; l < drinks; ++l) {
			ItemStack drink = LOTRFoods.SOUTHRON_DRINK.getRandomFood(rand);
			entityDropItem(drink, 0.0f);
		}
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public LOTRTradeEntries getBuyPool() {
		return LOTRTradeEntries.SOUTHRON_BARTENDER_BUY;
	}

	@Override
	public LOTRTradeEntries getSellPool() {
		return LOTRTradeEntries.SOUTHRON_BARTENDER_SELL;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			return "nearHarad/umbar/bartender/friendly";
		}
		return "nearHarad/umbar/bartender/hostile";
	}

	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
		LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeHaradBartender);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setIdleItem(new ItemStack(LOTRMod.gobletGold));
		return data;
	}

	@Override
	public boolean shouldTraderRespawn() {
		return true;
	}
}
