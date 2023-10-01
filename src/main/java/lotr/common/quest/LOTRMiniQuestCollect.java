package lotr.common.quest;

import lotr.common.LOTRPlayerData;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.item.LOTRItemMug;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;

import java.util.Random;

public class LOTRMiniQuestCollect extends LOTRMiniQuestCollectBase {
	public ItemStack collectItem;

	public LOTRMiniQuestCollect(LOTRPlayerData pd) {
		super(pd);
	}

	@Override
	public String getObjectiveInSpeech() {
		return collectTarget + " " + collectItem.getDisplayName();
	}

	@Override
	public String getProgressedObjectiveInSpeech() {
		return collectTarget - amountGiven + " " + collectItem.getDisplayName();
	}

	@Override
	public ItemStack getQuestIcon() {
		return collectItem;
	}

	@Override
	public String getQuestObjective() {
		return StatCollector.translateToLocalFormatted("lotr.miniquest.collect", collectTarget, collectItem.getDisplayName());
	}

	@Override
	public boolean isQuestItem(ItemStack itemstack) {
		if (IPickpocketable.Helper.isPickpocketed(itemstack)) {
			return false;
		}
		if (LOTRItemMug.isItemFullDrink(collectItem)) {
			ItemStack collectDrink = LOTRItemMug.getEquivalentDrink(collectItem);
			ItemStack offerDrink = LOTRItemMug.getEquivalentDrink(itemstack);
			return collectDrink.getItem() == offerDrink.getItem();
		}
		return itemstack.getItem() == collectItem.getItem() && (collectItem.getItemDamage() == 32767 || itemstack.getItemDamage() == collectItem.getItemDamage());
	}

	@Override
	public boolean isValidQuest() {
		return super.isValidQuest() && collectItem != null;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if (nbt.hasKey("Item")) {
			NBTTagCompound itemData = nbt.getCompoundTag("Item");
			collectItem = ItemStack.loadItemStackFromNBT(itemData);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if (collectItem != null) {
			NBTTagCompound itemData = new NBTTagCompound();
			collectItem.writeToNBT(itemData);
			nbt.setTag("Item", itemData);
		}
	}

	public static class QFCollect<Q extends LOTRMiniQuestCollect> extends LOTRMiniQuest.QuestFactoryBase<Q> {
		public ItemStack collectItem;
		public int minTarget;
		public int maxTarget;

		public QFCollect(String name) {
			super(name);
		}

		@Override
		public Q createQuest(LOTREntityNPC npc, Random rand) {
			Q quest = super.createQuest(npc, rand);
			quest.collectItem = collectItem.copy();
			quest.collectTarget = MathHelper.getRandomIntegerInRange(rand, minTarget, maxTarget);
			return quest;
		}

		@Override
		public Class getQuestClass() {
			return LOTRMiniQuestCollect.class;
		}

		public QFCollect setCollectItem(ItemStack itemstack, int min, int max) {
			collectItem = itemstack;
			if (collectItem.isItemStackDamageable()) {
				collectItem.setItemDamage(32767);
			}
			minTarget = min;
			maxTarget = max;
			return this;
		}
	}

}
