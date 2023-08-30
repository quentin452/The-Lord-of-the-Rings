package lotr.common.entity.ai;

import java.util.List;

import lotr.common.entity.npc.LOTREntityBalrog;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class LOTREntityAIBalrogCharge extends LOTREntityAIAttackOnCollide {
	public LOTREntityBalrog theBalrog;
	public float chargeDist;
	public int frustrationTime;
	public boolean hitChargeTarget;
	public int chargingTick;

	public LOTREntityAIBalrogCharge(LOTREntityBalrog balrog, double speed, float dist, int fr) {
		super(balrog, speed, false);
		theBalrog = balrog;
		chargeDist = dist;
		frustrationTime = fr;
	}

	@Override
	public boolean continueExecuting() {
		if (!theBalrog.isEntityAlive()) {
			return false;
		}
		attackTarget = theBalrog.getAttackTarget();
		if (attackTarget == null || !attackTarget.isEntityAlive()) {
			return false;
		}
		return chargingTick > 0 && !hitChargeTarget;
	}

	@Override
	public void resetTask() {
		super.resetTask();
		theBalrog.setBalrogCharging(false);
		hitChargeTarget = false;
		chargingTick = 0;
	}

	@Override
	public boolean shouldExecute() {
		if (theBalrog.isBalrogCharging()) {
			return false;
		}
		boolean flag = super.shouldExecute();
		if (flag) {
			if (theBalrog.chargeFrustration >= frustrationTime) {
				return true;
			}
			double dist = theBalrog.getDistanceSqToEntity(attackTarget);
			return dist >= chargeDist * chargeDist;
		}
		return false;
	}

	@Override
	public void startExecuting() {
		super.startExecuting();
		theBalrog.setBalrogCharging(true);
		hitChargeTarget = false;
		chargingTick = 200;
	}

	@Override
	public void updateTask() {
		updateLookAndPathing();
		if (chargingTick > 0) {
			--chargingTick;
		}
		AxisAlignedBB targetBB = theBalrog.boundingBox.expand(0.5, 0.0, 0.5);
		double motionExtent = 2.0;
		float angleRad = (float) Math.atan2(theBalrog.motionZ, theBalrog.motionX);
		targetBB = targetBB.getOffsetBoundingBox(MathHelper.cos(angleRad) * motionExtent, 0.0, MathHelper.sin(angleRad) * motionExtent);
		List hitTargets = worldObj.getEntitiesWithinAABBExcludingEntity(theBalrog, targetBB);
		for (Object hitTarget : hitTargets) {
			EntityLivingBase hitEntity;
			Entity obj = (Entity) hitTarget;
			if (!(obj instanceof EntityLivingBase) || (hitEntity = (EntityLivingBase) obj) == theBalrog.riddenByEntity) {
				continue;
			}
			float attackStr = (float) theBalrog.getEntityAttribute(LOTREntityBalrog.balrogChargeDamage).getAttributeValue();
			boolean flag = hitEntity.attackEntityFrom(DamageSource.causeMobDamage(theBalrog), attackStr);
			if (!flag) {
				continue;
			}
			float knock = 2.5f;
			float knockY = 0.5f;
			hitEntity.addVelocity(-MathHelper.sin((float) Math.toRadians(theBalrog.rotationYaw)) * knock, knockY, MathHelper.cos((float) Math.toRadians(theBalrog.rotationYaw)) * knock);
			hitEntity.setFire(6);
			if (hitEntity != attackTarget) {
				continue;
			}
			hitChargeTarget = true;
		}
	}
}
