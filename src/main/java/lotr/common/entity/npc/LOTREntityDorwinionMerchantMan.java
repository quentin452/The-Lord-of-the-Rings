package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.item.LOTRItemLeatherHat;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDorwinionMerchantMan extends LOTREntityDorwinionMan implements LOTRTravellingTrader {
	public static int[] hatColors = {15387062, 12361599, 7422850, 12677797, 13401212, 11350064, 9523548, 12502137, 11718290, 8817612, 6316484};
	public static int[] featherColors = {16777215, 12887724, 15061504, 0, 7475245, 4402118, 8311657};

	public LOTREntityDorwinionMerchantMan(World world) {
		super(world);
		addTargetTasks(false);
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return LOTRLevelData.getData(entityplayer).getAlignment(getFaction()) >= 0.0f && isFriendly(entityplayer);
	}

	@Override
	public LOTREntityNPC createTravellingEscort() {
		return new LOTREntityDorwinionGuard(worldObj);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public LOTRTradeEntries getBuyPool() {
		return LOTRTradeEntries.DORWINION_MERCHANT_BUY;
	}

	@Override
	public String getDepartureSpeech() {
		return "dorwinion/merchant/departure";
	}

	@Override
	public LOTRTradeEntries getSellPool() {
		return LOTRTradeEntries.DORWINION_MERCHANT_SELL;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			return "dorwinion/merchant/friendly";
		}
		return "dorwinion/merchant/hostile";
	}

	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
		LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeDorwinionMerchant);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		ItemStack hat = new ItemStack(LOTRMod.leatherHat);
		int colorHat = hatColors[rand.nextInt(hatColors.length)];
		int colorFeather = featherColors[rand.nextInt(featherColors.length)];
		LOTRItemLeatherHat.setHatColor(hat, colorHat);
		LOTRItemLeatherHat.setFeatherColor(hat, colorFeather);
		setCurrentItemOrArmor(4, hat);
		return data;
	}

	@Override
	public boolean shouldTraderRespawn() {
		return false;
	}
}
