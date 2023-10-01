package lotr.common.entity.ai;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRNPCMount;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;

public class LOTREntityAIHorseFollowHiringPlayer extends EntityAIBase {
	public LOTRNPCMount theHorse;
	public EntityCreature livingHorse;
	public EntityPlayer theHiringPlayer;
	public double moveSpeed;
	public int followTick;
	public float maxNearDist;
	public float minFollowDist;
	public boolean avoidsWater;
	public boolean initSpeed;

	public LOTREntityAIHorseFollowHiringPlayer(LOTRNPCMount entity) {
		theHorse = entity;
		livingHorse = (EntityCreature) theHorse;
		minFollowDist = 8.0f;
		maxNearDist = 6.0f;
		setMutexBits(3);
	}

	@Override
	public boolean continueExecuting() {
		if (livingHorse.riddenByEntity == null || !livingHorse.riddenByEntity.isEntityAlive() || !(livingHorse.riddenByEntity instanceof LOTREntityNPC)) {
			return false;
		}
		LOTREntityNPC ridingNPC = (LOTREntityNPC) livingHorse.riddenByEntity;
		return ridingNPC.hiredNPCInfo.isActive && ridingNPC.hiredNPCInfo.getHiringPlayer() != null && ridingNPC.hiredNPCInfo.shouldFollowPlayer() && !livingHorse.getNavigator().noPath() && livingHorse.getDistanceSqToEntity(theHiringPlayer) > maxNearDist * maxNearDist;
	}

	@Override
	public void resetTask() {
		theHiringPlayer = null;
		livingHorse.getNavigator().clearPathEntity();
		livingHorse.getNavigator().setAvoidsWater(avoidsWater);
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
		if (!ridingNPC.hiredNPCInfo.isActive) {
			return false;
		}
		EntityPlayer entityplayer = ridingNPC.hiredNPCInfo.getHiringPlayer();
		if (entityplayer == null || !ridingNPC.hiredNPCInfo.shouldFollowPlayer() || livingHorse.getDistanceSqToEntity(entityplayer) < minFollowDist * minFollowDist) {
			return false;
		}
		theHiringPlayer = entityplayer;
		return true;
	}

	@Override
	public void startExecuting() {
		followTick = 0;
		avoidsWater = livingHorse.getNavigator().getAvoidsWater();
		livingHorse.getNavigator().setAvoidsWater(false);
		if (!initSpeed) {
			double d = livingHorse.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
			moveSpeed = 1.0 / d * 0.37;
			initSpeed = true;
		}
	}

	@Override
	public void updateTask() {
		LOTREntityNPC ridingNPC = (LOTREntityNPC) livingHorse.riddenByEntity;
		livingHorse.getLookHelper().setLookPositionWithEntity(theHiringPlayer, 10.0f, livingHorse.getVerticalFaceSpeed());
		ridingNPC.rotationYaw = livingHorse.rotationYaw;
		ridingNPC.rotationYawHead = livingHorse.rotationYawHead;
		if (ridingNPC.hiredNPCInfo.shouldFollowPlayer() && --followTick <= 0) {
			followTick = 10;
			if (!livingHorse.getNavigator().tryMoveToEntityLiving(theHiringPlayer, moveSpeed) && ridingNPC.hiredNPCInfo.teleportAutomatically) {
				double d = ridingNPC.getEntityAttribute(SharedMonsterAttributes.followRange).getAttributeValue();
				d = Math.max(d, 24.0);
				if (ridingNPC.getDistanceSqToEntity(theHiringPlayer) > d * d) {
					ridingNPC.hiredNPCInfo.tryTeleportToHiringPlayer(false);
				}
			}
		}
	}
}
