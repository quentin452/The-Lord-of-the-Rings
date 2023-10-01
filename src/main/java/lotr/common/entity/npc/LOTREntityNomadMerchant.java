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

public class LOTREntityNomadMerchant extends LOTREntityNomad implements LOTRTravellingTrader {
	public static int[] robeColors = {15723226, 13551017, 6512465, 2499615, 11376219, 7825215};

	public LOTREntityNomadMerchant(World world) {
		super(world);
		addTargetTasks(false);
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return LOTRLevelData.getData(entityplayer).getAlignment(getFaction()) >= 0.0f && isFriendly(entityplayer);
	}

	@Override
	public LOTREntityNPC createTravellingEscort() {
		return new LOTREntityNomad(worldObj);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public LOTRTradeEntries getBuyPool() {
		return LOTRTradeEntries.NOMAD_MERCHANT_BUY;
	}

	@Override
	public String getDepartureSpeech() {
		return "nearHarad/nomad/merchant/departure";
	}

	@Override
	public LOTRTradeEntries getSellPool() {
		return LOTRTradeEntries.NOMAD_MERCHANT_SELL;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			return "nearHarad/nomad/merchant/friendly";
		}
		return "nearHarad/nomad/merchant/hostile";
	}

	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
		LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeNomadMerchant);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		ItemStack[] robe;
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setIdleItem(new ItemStack(LOTRMod.pouch, 1, 3));
		int robeColor = robeColors[rand.nextInt(robeColors.length)];
		for (ItemStack item : robe = new ItemStack[]{new ItemStack(LOTRMod.bootsHaradRobes), new ItemStack(LOTRMod.legsHaradRobes), new ItemStack(LOTRMod.bodyHaradRobes), new ItemStack(LOTRMod.helmetHaradRobes)}) {
			LOTRItemHaradRobes.setRobesColor(item, robeColor);
		}
		if (rand.nextBoolean()) {
			LOTRItemHaradTurban.setHasOrnament(robe[3], true);
		}
		setCurrentItemOrArmor(1, robe[0]);
		setCurrentItemOrArmor(2, robe[1]);
		setCurrentItemOrArmor(3, robe[2]);
		setCurrentItemOrArmor(4, robe[3]);
		return data;
	}

	@Override
	public boolean shouldTraderRespawn() {
		return false;
	}
}
