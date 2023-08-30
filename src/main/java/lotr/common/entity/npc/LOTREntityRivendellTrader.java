package lotr.common.entity.npc;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class LOTREntityRivendellTrader extends LOTREntityRivendellElf implements LOTRTravellingTrader {
	public LOTREntityRivendellTrader(World world) {
		super(world);
		tasks.addTask(2, new LOTREntityAIAttackOnCollide(this, 1.6, false));
		addTargetTasks(false);
		npcCape = LOTRCapes.RIVENDELL_TRADER;
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return LOTRLevelData.getData(entityplayer).getAlignment(getFaction()) >= 75.0f && isFriendly(entityplayer);
	}

	@Override
	public LOTREntityNPC createTravellingEscort() {
		return new LOTREntityRivendellElf(worldObj);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public LOTRTradeEntries getBuyPool() {
		return LOTRTradeEntries.RIVENDELL_TRADER_BUY;
	}

	@Override
	public String getDepartureSpeech() {
		return "rivendell/trader/departure";
	}

	@Override
	public int getExperiencePoints(EntityPlayer entityplayer) {
		return 5 + rand.nextInt(3);
	}

	@Override
	public LOTRTradeEntries getSellPool() {
		return LOTRTradeEntries.RIVENDELL_TRADER_SELL;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (canTradeWith(entityplayer)) {
				return "rivendell/trader/friendly";
			}
			return "rivendell/trader/neutral";
		}
		return "rivendell/trader/hostile";
	}

	@Override
	public int getTotalArmorValue() {
		return 10;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleHealthUpdate(byte b) {
		if (b != 15) {
			super.handleHealthUpdate(b);
		}
	}

	@Override
	public void onDeath(DamageSource damagesource) {
		super.onDeath(damagesource);
		if (!worldObj.isRemote) {
			worldObj.setEntityState(this, (byte) 15);
		}
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!worldObj.isRemote && isEntityAlive() && travellingTraderInfo.timeUntilDespawn == 0) {
			worldObj.setEntityState(this, (byte) 15);
		}
	}

	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
		LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeRivendellTrader);
	}

	@Override
	public boolean shouldRenderNPCHair() {
		return false;
	}

	@Override
	public boolean shouldTraderRespawn() {
		return false;
	}
}
