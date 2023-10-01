package lotr.common.entity.ai;

import lotr.common.entity.npc.IBandit;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class LOTREntityAINearestAttackableTargetBandit extends LOTREntityAINearestAttackableTargetBasic {
	public IBandit taskOwnerAsBandit;

	public LOTREntityAINearestAttackableTargetBandit(EntityCreature entity, Class targetClass, int chance, boolean flag) {
		super(entity, targetClass, chance, flag);
		taskOwnerAsBandit = (IBandit) entity;
	}

	public LOTREntityAINearestAttackableTargetBandit(EntityCreature entity, Class targetClass, int chance, boolean flag, IEntitySelector selector) {
		super(entity, targetClass, chance, flag, selector);
		taskOwnerAsBandit = (IBandit) entity;
	}

	@Override
	public boolean isPlayerSuitableTarget(EntityPlayer entityplayer) {
		if (IBandit.Helper.canStealFromPlayerInv(taskOwnerAsBandit, entityplayer)) {
			return false;
		}
		return super.isPlayerSuitableTarget(entityplayer);
	}

	@Override
	public boolean isSuitableTarget(EntityLivingBase entity, boolean flag) {
		return entity instanceof EntityPlayer && super.isSuitableTarget(entity, flag);
	}

	@Override
	public boolean shouldExecute() {
		if (!taskOwnerAsBandit.getBanditInventory().isEmpty()) {
			return false;
		}
		return super.shouldExecute();
	}
}
