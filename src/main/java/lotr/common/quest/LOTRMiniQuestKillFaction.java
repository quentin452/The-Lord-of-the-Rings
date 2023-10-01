package lotr.common.quest;

import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Random;

public class LOTRMiniQuestKillFaction extends LOTRMiniQuestKill {
	public LOTRFaction killFaction;

	public LOTRMiniQuestKillFaction(LOTRPlayerData pd) {
		super(pd);
	}

	@Override
	public String getKillTargetName() {
		return killFaction.factionEntityName();
	}

	@Override
	public boolean isValidQuest() {
		return super.isValidQuest() && killFaction != null;
	}

	@Override
	public void onKill(EntityPlayer entityplayer, EntityLivingBase entity) {
		if (killCount < killTarget && LOTRMod.getNPCFaction(entity) == killFaction) {
			++killCount;
			updateQuest();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		killFaction = LOTRFaction.forName(nbt.getString("KillFaction"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setString("KillFaction", killFaction.codeName());
	}

	public static class QFKillFaction extends LOTRMiniQuestKill.QFKill<LOTRMiniQuestKillFaction> {
		public LOTRFaction killFaction;

		public QFKillFaction(String name) {
			super(name);
		}

		@Override
		public LOTRMiniQuestKillFaction createQuest(LOTREntityNPC npc, Random rand) {
			LOTRMiniQuestKillFaction quest = super.createQuest(npc, rand);
			quest.killFaction = killFaction;
			return quest;
		}

		@Override
		public Class getQuestClass() {
			return LOTRMiniQuestKillFaction.class;
		}

		public QFKillFaction setKillFaction(LOTRFaction faction, int min, int max) {
			killFaction = faction;
			setKillTarget(min, max);
			return this;
		}
	}

}
