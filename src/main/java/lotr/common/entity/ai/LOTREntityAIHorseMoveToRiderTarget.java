package lotr.common.entity.ai;

import lotr.common.entity.npc.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.Vec3;

public class LOTREntityAIHorseMoveToRiderTarget extends EntityAIBase {
	public LOTRNPCMount theHorse;
	public EntityCreature livingHorse;
	public double speed;
	public PathEntity entityPathEntity;
	public int pathCheckTimer;

	public LOTREntityAIHorseMoveToRiderTarget(LOTRNPCMount horse) {
		theHorse = horse;
		livingHorse = (EntityCreature) theHorse;
		setMutexBits(3);
	}

	@Override
	public boolean continueExecuting() {
		if (livingHorse.riddenByEntity == null || !livingHorse.riddenByEntity.isEntityAlive() || !(livingHorse.riddenByEntity instanceof LOTREntityNPC)) {
			return false;
		}
		LOTREntityNPC rider = (LOTREntityNPC) livingHorse.riddenByEntity;
		EntityLivingBase riderTarget = rider.getAttackTarget();
		return riderTarget != null && riderTarget.isEntityAlive() && !livingHorse.getNavigator().noPath();
	}

	@Override
	public void resetTask() {
		livingHorse.getNavigator().clearPathEntity();
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
		EntityLivingBase riderTarget = ((EntityLiving) rider).getAttackTarget();
		if (riderTarget == null || !riderTarget.isEntityAlive()) {
			return false;
		}
		entityPathEntity = livingHorse.getNavigator().getPathToEntityLiving(riderTarget);
		return entityPathEntity != null;
	}

	@Override
	public void startExecuting() {
		speed = ((EntityLivingBase) livingHorse.riddenByEntity).getEntityAttribute(LOTREntityNPC.horseAttackSpeed).getAttributeValue();
		livingHorse.getNavigator().setPath(entityPathEntity, speed);
		pathCheckTimer = 0;
	}

	@Override
	public void updateTask() {
		boolean aimingBow;
		LOTREntityNPC rider = (LOTREntityNPC) livingHorse.riddenByEntity;
		EntityLivingBase riderTarget = rider.getAttackTarget();
		aimingBow = rider.isAimingRanged() && livingHorse.getEntitySenses().canSee(riderTarget);
		if (!aimingBow) {
			livingHorse.getLookHelper().setLookPositionWithEntity(riderTarget, 30.0f, 30.0f);
			rider.rotationYaw = livingHorse.rotationYaw;
			rider.rotationYawHead = livingHorse.rotationYawHead;
		}
		if (--pathCheckTimer <= 0) {
			pathCheckTimer = 4 + livingHorse.getRNG().nextInt(7);
			livingHorse.getNavigator().tryMoveToEntityLiving(riderTarget, speed);
		}
		if (aimingBow) {
			if (rider.getDistanceSqToEntity(riderTarget) < 25.0) {
				Vec3 vec = LOTREntityAIRangedAttack.findPositionAwayFrom(rider, riderTarget, 8, 16);
				if (vec != null) {
					livingHorse.getNavigator().tryMoveToXYZ(vec.xCoord, vec.yCoord, vec.zCoord, speed);
				}
			} else {
				livingHorse.getNavigator().clearPathEntity();
			}
		}
	}
}
