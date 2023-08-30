package lotr.common.quest;

import java.util.*;

import lotr.common.LOTRPlayerData;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public abstract class LOTRMiniQuestCollectBase extends LOTRMiniQuest {
	public int collectTarget;
	public int amountGiven;

	protected LOTRMiniQuestCollectBase(LOTRPlayerData pd) {
		super(pd);
	}

	@Override
	public float getAlignmentBonus() {
		float f = collectTarget;
		return Math.max(f * rewardFactor, 1.0f);
	}

	@Override
	public int getCoinBonus() {
		return Math.round(getAlignmentBonus() * 2.0f);
	}

	@Override
	public float getCompletionFactor() {
		return (float) amountGiven / collectTarget;
	}

	@Override
	public String getQuestProgress() {
		return StatCollector.translateToLocalFormatted("lotr.miniquest.collect.progress", amountGiven, collectTarget);
	}

	@Override
	public String getQuestProgressShorthand() {
		return StatCollector.translateToLocalFormatted("lotr.miniquest.progressShort", amountGiven, collectTarget);
	}

	public abstract boolean isQuestItem(ItemStack var1);

	@Override
	public boolean isValidQuest() {
		return super.isValidQuest() && collectTarget > 0;
	}

	@Override
	public void onInteract(EntityPlayer entityplayer, LOTREntityNPC npc) {
		int prevAmountGiven = amountGiven;
		Collection<Integer> slotNumbers = new ArrayList<>();
		slotNumbers.add(entityplayer.inventory.currentItem);
		for (int slot = 0; slot < entityplayer.inventory.mainInventory.length; ++slot) {
			if (slotNumbers.contains(slot)) {
				continue;
			}
			slotNumbers.add(slot);
		}
		for (int slot2 : slotNumbers) {
			ItemStack itemstack = entityplayer.inventory.mainInventory[slot2];
			if (itemstack != null && isQuestItem(itemstack)) {
				int amountRemaining = collectTarget - amountGiven;
				if (itemstack.stackSize >= amountRemaining) {
					itemstack.stackSize -= amountRemaining;
					if (itemstack.stackSize <= 0) {
						itemstack = null;
					}
					entityplayer.inventory.setInventorySlotContents(slot2, itemstack);
					amountGiven += amountRemaining;
				} else {
					amountGiven += itemstack.stackSize;
					entityplayer.inventory.setInventorySlotContents(slot2, null);
				}
			}
			if (amountGiven < collectTarget) {
				continue;
			}
			complete(entityplayer, npc);
			break;
		}
		if (amountGiven > prevAmountGiven && !isCompleted()) {
			updateQuest();
		}
		if (!isCompleted()) {
			sendProgressSpeechbank(entityplayer, npc);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		collectTarget = nbt.getInteger("Target");
		amountGiven = nbt.getInteger("Given");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("Target", collectTarget);
		nbt.setInteger("Given", amountGiven);
	}
}
