package lotr.common.entity.ai;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.fac.LOTRFaction;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRNPCTargetSelector implements IEntitySelector {
	public EntityLiving owner;
	public LOTRFaction ownerFaction;

	public LOTRNPCTargetSelector(EntityLiving entity) {
		owner = entity;
		ownerFaction = LOTRMod.getNPCFaction(entity);
	}

	@Override
	public boolean isEntityApplicable(Entity target) {
		if (ownerFaction == LOTRFaction.HOSTILE && (target.getClass().isAssignableFrom(owner.getClass()) || owner.getClass().isAssignableFrom(target.getClass()))) {
			return false;
		}
		if (target.isEntityAlive()) {
			if (target instanceof LOTREntityNPC && !((LOTREntityNPC) target).canBeFreelyTargetedBy(owner)) {
				return false;
			}
			if (!ownerFaction.approvesWarCrimes && target instanceof LOTREntityNPC && ((LOTREntityNPC) target).isCivilianNPC()) {
				return false;
			}
			LOTRFaction targetFaction = LOTRMod.getNPCFaction(target);
			if (ownerFaction.isBadRelation(targetFaction)) {
				return true;
			}
			if (ownerFaction.isNeutral(targetFaction)) {
				EntityPlayer hiringPlayer = null;
				if (owner instanceof LOTREntityNPC) {
					LOTREntityNPC npc = (LOTREntityNPC) owner;
					if (npc.hiredNPCInfo.isActive) {
						hiringPlayer = npc.hiredNPCInfo.getHiringPlayer();
					}
				}
				return hiringPlayer != null && LOTRLevelData.getData(hiringPlayer).getAlignment(targetFaction) < 0.0f;
			}
		}
		return false;
	}
}
