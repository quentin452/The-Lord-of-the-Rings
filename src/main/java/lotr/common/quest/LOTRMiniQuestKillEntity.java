package lotr.common.quest;

import lotr.common.LOTRPlayerData;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

import java.util.Random;

public class LOTRMiniQuestKillEntity extends LOTRMiniQuestKill {
	public Class entityType;

	public LOTRMiniQuestKillEntity(LOTRPlayerData pd) {
		super(pd);
	}

	@Override
	public String getKillTargetName() {
		String entityName = LOTREntities.getStringFromClass(entityType);
		return StatCollector.translateToLocal("entity." + entityName + ".name");
	}

	@Override
	public boolean isValidQuest() {
		return super.isValidQuest() && entityType != null && EntityLivingBase.class.isAssignableFrom(entityType);
	}

	@Override
	public void onKill(EntityPlayer entityplayer, EntityLivingBase entity) {
		if (killCount < killTarget && entityType.isAssignableFrom(entity.getClass())) {
			++killCount;
			updateQuest();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		entityType = LOTREntities.getClassFromString(nbt.getString("KillClass"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setString("KillClass", LOTREntities.getStringFromClass(entityType));
	}

	public static class QFKillEntity extends LOTRMiniQuestKill.QFKill<LOTRMiniQuestKillEntity> {
		public Class entityType;

		public QFKillEntity(String name) {
			super(name);
		}

		@Override
		public LOTRMiniQuestKillEntity createQuest(LOTREntityNPC npc, Random rand) {
			LOTRMiniQuestKillEntity quest = super.createQuest(npc, rand);
			quest.entityType = entityType;
			return quest;
		}

		@Override
		public Class getQuestClass() {
			return LOTRMiniQuestKillEntity.class;
		}

		public QFKillEntity setKillEntity(Class entityClass, int min, int max) {
			entityType = entityClass;
			setKillTarget(min, max);
			return this;
		}
	}

}
