package lotr.common.inventory;

import lotr.common.LOTRLevelData;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRHireableBase;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemCoin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class LOTRSlotAlignmentReward extends LOTRSlotProtected {
	public static int REWARD_COST = 2000;
	public LOTRContainerUnitTrade theContainer;
	public LOTRHireableBase theTrader;
	public LOTREntityNPC theLivingTrader;
	public ItemStack alignmentReward;

	public LOTRSlotAlignmentReward(LOTRContainerUnitTrade container, IInventory inv, int i, int j, int k, LOTRHireableBase entity, ItemStack item) {
		super(inv, i, j, k);
		theContainer = container;
		theTrader = entity;
		theLivingTrader = (LOTREntityNPC) theTrader;
		alignmentReward = item.copy();
	}

	@Override
	public boolean canTakeStack(EntityPlayer entityplayer) {
		if (LOTRLevelData.getData(entityplayer).getAlignment(theTrader.getFaction()) < 1500.0f) {
			return false;
		}
		int coins = LOTRItemCoin.getInventoryValue(entityplayer, false);
		if (coins < REWARD_COST) {
			return false;
		}
		return super.canTakeStack(entityplayer);
	}

	@Override
	public void onPickupFromSlot(EntityPlayer entityplayer, ItemStack itemstack) {
		LOTRFaction faction = theLivingTrader.getFaction();
		if (!entityplayer.worldObj.isRemote) {
			LOTRItemCoin.takeCoins(REWARD_COST, entityplayer);
			LOTRLevelData.getData(entityplayer).getFactionData(faction).takeConquestHorn();
			theLivingTrader.playTradeSound();
		}
		super.onPickupFromSlot(entityplayer, itemstack);
		if (!entityplayer.worldObj.isRemote) {
			ItemStack reward = alignmentReward.copy();
			putStack(reward);
			((EntityPlayerMP) entityplayer).sendContainerToPlayer(theContainer);
		}
	}
}
