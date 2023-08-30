package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.item.LOTRItemLeatherHat;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDorwinionMerchantElf extends LOTREntityDorwinionElf implements LOTRTravellingTrader {
	public LOTREntityDorwinionMerchantElf(World world) {
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
	public LOTRTradeEntries getBuyPool() {
		return LOTRTradeEntries.DORWINION_MERCHANT_BUY;
	}

	@Override
	public String getDepartureSpeech() {
		return "dorwinion/merchantElf/departure";
	}

	@Override
	public LOTRTradeEntries getSellPool() {
		return LOTRTradeEntries.DORWINION_MERCHANT_SELL;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			return "dorwinion/merchantElf/friendly";
		}
		return "dorwinion/merchantElf/hostile";
	}

	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
		LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeDorwinionMerchant);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		ItemStack hat = new ItemStack(LOTRMod.leatherHat);
		int colorHat = LOTREntityDorwinionMerchantMan.hatColors[rand.nextInt(LOTREntityDorwinionMerchantMan.hatColors.length)];
		int colorFeather = LOTREntityDorwinionMerchantMan.featherColors[rand.nextInt(LOTREntityDorwinionMerchantMan.featherColors.length)];
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
