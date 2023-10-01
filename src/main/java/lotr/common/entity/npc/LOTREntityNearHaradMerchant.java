package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemHaradRobes;
import lotr.common.item.LOTRItemHaradTurban;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityNearHaradMerchant extends LOTREntityNearHaradrim implements LOTRTravellingTrader {
	public static int[] robeColors = {15723226, 14829087, 12653845, 8526876, 2625038};

	public LOTREntityNearHaradMerchant(World world) {
		super(world);
		addTargetTasks(false);
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return LOTRLevelData.getData(entityplayer).getAlignment(getFaction()) >= 0.0f && isFriendly(entityplayer);
	}

	@Override
	public LOTREntityNPC createTravellingEscort() {
		return new LOTREntityNearHaradrim(worldObj);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public LOTRTradeEntries getBuyPool() {
		return LOTRTradeEntries.NEAR_HARAD_MERCHANT_BUY;
	}

	@Override
	public String getDepartureSpeech() {
		return "nearHarad/merchant/departure";
	}

	@Override
	public LOTRTradeEntries getSellPool() {
		return LOTRTradeEntries.NEAR_HARAD_MERCHANT_SELL;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			return "nearHarad/merchant/friendly";
		}
		return "nearHarad/merchant/hostile";
	}

	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
		LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeNearHaradMerchant);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setIdleItem(new ItemStack(LOTRMod.pouch, 1, 3));
		int robeColor = robeColors[rand.nextInt(robeColors.length)];
		ItemStack turban = new ItemStack(LOTRMod.helmetHaradRobes);
		LOTRItemHaradRobes.setRobesColor(turban, robeColor);
		if (rand.nextBoolean()) {
			LOTRItemHaradTurban.setHasOrnament(turban, true);
		}
		setCurrentItemOrArmor(1, null);
		setCurrentItemOrArmor(2, null);
		setCurrentItemOrArmor(3, null);
		setCurrentItemOrArmor(4, turban);
		return data;
	}

	@Override
	public boolean shouldTraderRespawn() {
		return false;
	}
}
