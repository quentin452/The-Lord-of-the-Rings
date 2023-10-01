package lotr.common.entity.ai;

import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityWoodElf;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;

public class LOTREntityAINearestAttackableTargetWoodElf extends LOTREntityAINearestAttackableTargetBasic {
	public LOTREntityAINearestAttackableTargetWoodElf(EntityCreature entity, Class targetClass, int chance, boolean flag) {
		super(entity, targetClass, chance, flag);
	}

	public LOTREntityAINearestAttackableTargetWoodElf(EntityCreature entity, Class targetClass, int chance, boolean flag, IEntitySelector selector) {
		super(entity, targetClass, chance, flag, selector);
	}

	@Override
	public boolean isPlayerSuitableAlignmentTarget(EntityPlayer entityplayer) {
		float alignment = LOTRLevelData.getData(entityplayer).getAlignment(LOTRMod.getNPCFaction(taskOwner));
		if (alignment >= LOTREntityWoodElf.getWoodlandTrustLevel()) {
			return false;
		}
		if (alignment >= 0.0f) {
			int chance = Math.round(alignment * 20.0f);
			chance = Math.max(chance, 1);
			return taskOwner.getRNG().nextInt(chance) == 0;
		}
		return super.isPlayerSuitableAlignmentTarget(entityplayer);
	}
}
