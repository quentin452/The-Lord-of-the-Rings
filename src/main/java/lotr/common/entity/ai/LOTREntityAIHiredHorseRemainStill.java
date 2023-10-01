package lotr.common.entity.ai;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRNPCMount;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAIHiredHorseRemainStill extends EntityAIBase {
	public LOTRNPCMount theHorse;
	public EntityCreature livingHorse;

	public LOTREntityAIHiredHorseRemainStill(LOTRNPCMount entity) {
		theHorse = entity;
		livingHorse = (EntityCreature) theHorse;
		setMutexBits(5);
	}

	@Override
	public boolean shouldExecute() {
		if (!theHorse.getBelongsToNPC()) {
			return false;
		}
		Entity rider = livingHorse.riddenByEntity;
		if (rider == null || !rider.isEntityAlive() || !(rider instanceof LOTREntityNPC)) {
			return false;
		}
		LOTREntityNPC ridingNPC = (LOTREntityNPC) rider;
		if (!ridingNPC.hiredNPCInfo.isActive || livingHorse.isInWater() || !livingHorse.onGround) {
			return false;
		}
		return ridingNPC.hiredNPCInfo.isHalted() && (ridingNPC.getAttackTarget() == null || !ridingNPC.getAttackTarget().isEntityAlive());
	}

	@Override
	public void startExecuting() {
		livingHorse.getNavigator().clearPathEntity();
	}
}
