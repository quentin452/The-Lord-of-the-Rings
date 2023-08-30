package lotr.common.entity.ai;

import lotr.common.*;
import lotr.common.fac.LOTRFaction;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;

public class LOTREntityAINearestAttackableTargetOrc extends LOTREntityAINearestAttackableTargetBasic {
	public LOTREntityAINearestAttackableTargetOrc(EntityCreature entity, Class targetClass, int chance, boolean flag) {
		super(entity, targetClass, chance, flag);
	}

	public LOTREntityAINearestAttackableTargetOrc(EntityCreature entity, Class targetClass, int chance, boolean flag, IEntitySelector selector) {
		super(entity, targetClass, chance, flag, selector);
	}

	@Override
	public boolean isPlayerSuitableAlignmentTarget(EntityPlayer entityplayer) {
		LOTRFaction faction = LOTRMod.getNPCFaction(taskOwner);
		if (faction == LOTRFaction.MORDOR) {
			float alignment = LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.MORDOR);
			if (alignment >= 100.0f) {
				return false;
			}
			if (alignment >= 0.0f) {
				int chance = Math.round(alignment * 5.0f);
				chance = Math.max(chance, 1);
				return taskOwner.getRNG().nextInt(chance) == 0;
			}
		}
		return super.isPlayerSuitableAlignmentTarget(entityplayer);
	}
}
