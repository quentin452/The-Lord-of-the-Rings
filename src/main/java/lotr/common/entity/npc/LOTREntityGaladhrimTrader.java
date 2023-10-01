package lotr.common.entity.npc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRCapes;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class LOTREntityGaladhrimTrader extends LOTREntityGaladhrimElf implements LOTRTravellingTrader {
	public LOTREntityGaladhrimTrader(World world) {
		super(world);
		tasks.addTask(2, new LOTREntityAIAttackOnCollide(this, 1.6, false));
		addTargetTasks(false);
		npcCape = LOTRCapes.GALADHRIM_TRADER;
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return LOTRLevelData.getData(entityplayer).getAlignment(getFaction()) >= 75.0f && isFriendly(entityplayer);
	}

	@Override
	public LOTREntityNPC createTravellingEscort() {
		return new LOTREntityGaladhrimElf(worldObj);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public LOTRTradeEntries getBuyPool() {
		return LOTRTradeEntries.GALADHRIM_TRADER_BUY;
	}

	@Override
	public String getDepartureSpeech() {
		return "galadhrim/trader/departure";
	}

	@Override
	public int getExperiencePoints(EntityPlayer entityplayer) {
		return 5 + rand.nextInt(3);
	}

	@Override
	public LOTRTradeEntries getSellPool() {
		return LOTRTradeEntries.GALADHRIM_TRADER_SELL;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (canTradeWith(entityplayer)) {
				return "galadhrim/trader/friendly";
			}
			return "galadhrim/trader/neutral";
		}
		return "galadhrim/trader/hostile";
	}

	@Override
	public int getTotalArmorValue() {
		return 10;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleHealthUpdate(byte b) {
		if (b == 15) {
			for (int i = 0; i < 16; ++i) {
				double d = posX + (rand.nextDouble() - 0.5) * width;
				double d1 = posY + rand.nextDouble() * height;
				double d2 = posZ + (rand.nextDouble() - 0.5) * width;
				double d3 = -0.2 + rand.nextFloat() * 0.4f;
				double d4 = -0.2 + rand.nextFloat() * 0.4f;
				double d5 = -0.2 + rand.nextFloat() * 0.4f;
				LOTRMod.proxy.spawnParticle("leafGold_" + (30 + rand.nextInt(30)), d, d1, d2, d3, d4, d5);
			}
		} else {
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
		LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeElvenTrader);
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
