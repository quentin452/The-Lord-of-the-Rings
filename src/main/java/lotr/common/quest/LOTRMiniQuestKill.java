package lotr.common.quest;

import lotr.common.LOTRPlayerData;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;

import java.util.Random;

public abstract class LOTRMiniQuestKill extends LOTRMiniQuest {
	public int killTarget;
	public int killCount;

	protected LOTRMiniQuestKill(LOTRPlayerData pd) {
		super(pd);
	}

	@Override
	public float getAlignmentBonus() {
		return killTarget * rewardFactor;
	}

	@Override
	public int getCoinBonus() {
		return Math.round(killTarget * 2.0f * rewardFactor);
	}

	@Override
	public float getCompletionFactor() {
		return (float) killCount / killTarget;
	}

	public abstract String getKillTargetName();

	@Override
	public String getObjectiveInSpeech() {
		return killTarget + " " + getKillTargetName();
	}

	@Override
	public String getProgressedObjectiveInSpeech() {
		return killTarget - killCount + " " + getKillTargetName();
	}

	@Override
	public ItemStack getQuestIcon() {
		return new ItemStack(Items.iron_sword);
	}

	@Override
	public String getQuestObjective() {
		return StatCollector.translateToLocalFormatted("lotr.miniquest.kill", killTarget, getKillTargetName());
	}

	@Override
	public String getQuestProgress() {
		return StatCollector.translateToLocalFormatted("lotr.miniquest.kill.progress", killCount, killTarget);
	}

	@Override
	public String getQuestProgressShorthand() {
		return StatCollector.translateToLocalFormatted("lotr.miniquest.progressShort", killCount, killTarget);
	}

	@Override
	public boolean isValidQuest() {
		return super.isValidQuest() && killTarget > 0;
	}

	@Override
	public void onInteract(EntityPlayer entityplayer, LOTREntityNPC npc) {
		if (killCount >= killTarget) {
			complete(entityplayer, npc);
		} else {
			sendProgressSpeechbank(entityplayer, npc);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		killTarget = nbt.getInteger("Target");
		killCount = nbt.getInteger("Count");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("Target", killTarget);
		nbt.setInteger("Count", killCount);
	}

	public abstract static class QFKill<Q extends LOTRMiniQuestKill> extends LOTRMiniQuest.QuestFactoryBase<Q> {
		public int minTarget;
		public int maxTarget;

		protected QFKill(String name) {
			super(name);
		}

		@Override
		public Q createQuest(LOTREntityNPC npc, Random rand) {
			Q quest = super.createQuest(npc, rand);
			quest.killTarget = MathHelper.getRandomIntegerInRange(rand, minTarget, maxTarget);
			return quest;
		}

		public QFKill setKillTarget(int min, int max) {
			minTarget = min;
			maxTarget = max;
			return this;
		}
	}

}
