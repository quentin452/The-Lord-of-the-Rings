package lotr.common.entity.ai;

import java.util.*;

import lotr.common.LOTRLevelData;
import lotr.common.entity.npc.*;
import lotr.common.item.*;
import lotr.common.recipe.LOTRRecipes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.*;
import net.minecraft.util.MathHelper;

public class LOTREntityAIBanditSteal extends EntityAIBase {
	public IBandit theBandit;
	public LOTREntityNPC theBanditAsNPC;
	public EntityPlayer targetPlayer;
	public EntityPlayer prevTargetPlayer;
	public double speed;
	public int chaseTimer;
	public int rePathDelay;

	public LOTREntityAIBanditSteal(IBandit bandit, double d) {
		theBandit = bandit;
		theBanditAsNPC = theBandit.getBanditAsNPC();
		speed = d;
		setMutexBits(3);
	}

	@Override
	public boolean continueExecuting() {
		if (targetPlayer == null || !targetPlayer.isEntityAlive() || targetPlayer.capabilities.isCreativeMode || !IBandit.Helper.canStealFromPlayerInv(theBandit, targetPlayer)) {
			return false;
		}
		return chaseTimer > 0 && theBanditAsNPC.getDistanceSqToEntity(targetPlayer) < 256.0;
	}

	public int getRandomTheftAmount(ItemStack itemstack) {
		return MathHelper.getRandomIntegerInRange(theBanditAsNPC.getRNG(), 1, 8);
	}

	@Override
	public void resetTask() {
		chaseTimer = 0;
		rePathDelay = 0;
		if (targetPlayer != null) {
			prevTargetPlayer = targetPlayer;
		}
		targetPlayer = null;
	}

	@Override
	public boolean shouldExecute() {
		if (!theBandit.getBanditInventory().isEmpty()) {
			return false;
		}
		double range = 32.0;
		List players = theBanditAsNPC.worldObj.getEntitiesWithinAABB(EntityPlayer.class, theBanditAsNPC.boundingBox.expand(range, range, range));
		ArrayList<EntityPlayer> validTargets = new ArrayList<>();
		for (Object player : players) {
			EntityPlayer entityplayer = (EntityPlayer) player;
			if (entityplayer.capabilities.isCreativeMode || !theBandit.canTargetPlayerForTheft(entityplayer) || !IBandit.Helper.canStealFromPlayerInv(theBandit, entityplayer)) {
				continue;
			}
			validTargets.add(entityplayer);
		}
		if (validTargets.isEmpty()) {
			return false;
		}
		targetPlayer = validTargets.get(theBanditAsNPC.getRNG().nextInt(validTargets.size()));
		if (targetPlayer != prevTargetPlayer) {
			theBanditAsNPC.sendSpeechBank(targetPlayer, theBandit.getTheftSpeechBank(targetPlayer));
		}
		return true;
	}

	@Override
	public void startExecuting() {
		chaseTimer = 600;
	}

	public void steal() {
		InventoryPlayer inv = targetPlayer.inventory;
		int thefts = MathHelper.getRandomIntegerInRange(theBanditAsNPC.getRNG(), 1, theBandit.getMaxThefts());
		boolean stolenSomething = false;
		for (int i = 0; i < thefts; ++i) {
			if (tryStealItem(inv, LOTRItemCoin.class)) {
				stolenSomething = true;
			}
			if (tryStealItem(inv, LOTRItemGem.class) || tryStealItem(inv, LOTRValuableItems.getToolMaterials()) || tryStealItem(inv, LOTRItemRing.class) || tryStealItem(inv, ItemArmor.class)) {
				stolenSomething = true;
				continue;
			}
			if (tryStealItem(inv, ItemSword.class) || tryStealItem(inv, ItemTool.class) || tryStealItem(inv, LOTRItemPouch.class)) {
				stolenSomething = true;
				continue;
			}
			if (!tryStealItem(inv)) {
				continue;
			}
			stolenSomething = true;
		}
		if (stolenSomething) {
			targetPlayer.addChatMessage(theBandit.getTheftChatMsg(targetPlayer));
			theBanditAsNPC.playSound("mob.horse.leather", 0.5f, 1.0f);
			if (theBanditAsNPC.getAttackTarget() != null) {
				theBanditAsNPC.setAttackTarget(null);
			}
			LOTRLevelData.getData(targetPlayer).cancelFastTravel();
		}
	}

	public boolean stealItem(IInventory inv, int slot) {
		ItemStack playerItem = inv.getStackInSlot(slot);
		int theft = getRandomTheftAmount(playerItem);
		if (theft > playerItem.stackSize) {
			theft = playerItem.stackSize;
		}
		int banditSlot = 0;
		while (theBandit.getBanditInventory().getStackInSlot(banditSlot) != null) {
			banditSlot++;
			if (banditSlot < theBandit.getBanditInventory().getSizeInventory()) {
				continue;
			}
			return false;
		}
		ItemStack stolenItem = playerItem.copy();
		stolenItem.stackSize = theft;
		theBandit.getBanditInventory().setInventorySlotContents(banditSlot, stolenItem);
		playerItem.stackSize -= theft;
		if (playerItem.stackSize <= 0) {
			inv.setInventorySlotContents(slot, null);
		}
		theBanditAsNPC.isNPCPersistent = true;
		return true;
	}

	public boolean tryStealItem(InventoryPlayer inv) {
		return tryStealItem_do(inv, new BanditItemFilter() {

			@Override
			public boolean isApplicable(ItemStack itemstack) {
				return true;
			}
		});
	}

	public boolean tryStealItem(InventoryPlayer inv, Class itemclass) {
		return tryStealItem_do(inv, new BanditItemFilter() {

			@Override
			public boolean isApplicable(ItemStack itemstack) {
				return itemclass.isAssignableFrom(itemstack.getItem().getClass());
			}
		});
	}

	public boolean tryStealItem(InventoryPlayer inv, Iterable<ItemStack> itemList) {
		return tryStealItem_do(inv, new BanditItemFilter() {

			@Override
			public boolean isApplicable(ItemStack itemstack) {
				for (ItemStack listItem : itemList) {
					if (!LOTRRecipes.checkItemEquals(listItem, itemstack)) {
						continue;
					}
					return true;
				}
				return false;
			}
		});
	}

	public boolean tryStealItem_do(InventoryPlayer inv, BanditItemFilter filter) {
		Integer[] inventorySlots = new Integer[inv.mainInventory.length];
		for (int l = 0; l < inventorySlots.length; ++l) {
			inventorySlots[l] = l;
		}
		List<Integer> slotsAsList = Arrays.asList(inventorySlots);
		Collections.shuffle(slotsAsList);
		Integer[] arrinteger = slotsAsList.toArray(inventorySlots);
		int n = arrinteger.length;
		for (Integer integer : arrinteger) {
			ItemStack itemstack;
			int slot = integer;
			if (slot == inv.currentItem || (itemstack = inv.getStackInSlot(slot)) == null || !filter.isApplicable(itemstack) || !stealItem(inv, slot)) {
				continue;
			}
			return true;
		}
		return false;
	}

	@Override
	public void updateTask() {
		--chaseTimer;
		theBanditAsNPC.getLookHelper().setLookPositionWithEntity(targetPlayer, 30.0f, 30.0f);
		--rePathDelay;
		if (rePathDelay <= 0) {
			rePathDelay = 10;
			theBanditAsNPC.getNavigator().tryMoveToEntityLiving(targetPlayer, speed);
		}
		if (theBanditAsNPC.getDistanceSqToEntity(targetPlayer) <= 2.0) {
			chaseTimer = 0;
			steal();
		}
	}

	public abstract static class BanditItemFilter {

		public abstract boolean isApplicable(ItemStack var1);
	}

}
