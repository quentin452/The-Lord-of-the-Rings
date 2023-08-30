package lotr.common.entity.ai;

import java.util.Random;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.*;

public class LOTREntityAIRangedAttack extends EntityAIBase {
	public EntityLiving theOwner;
	public IRangedAttackMob theOwnerRanged;
	public EntityLivingBase attackTarget;
	public int rangedAttackTime = -1;
	public double moveSpeed;
	public double moveSpeedFlee = 1.5;
	public int repathDelay;
	public int attackTimeMin;
	public int attackTimeMax;
	public float attackRange;
	public float attackRangeSq;

	public LOTREntityAIRangedAttack(IRangedAttackMob entity, double speed, int time, float range) {
		this(entity, speed, time, time, range);
	}

	public LOTREntityAIRangedAttack(IRangedAttackMob entity, double speed, int min, int max, float range) {
		theOwnerRanged = entity;
		theOwner = (EntityLiving) entity;
		moveSpeed = speed;
		attackTimeMin = min;
		attackTimeMax = max;
		attackRange = range;
		attackRangeSq = range * range;
		setMutexBits(3);
	}

	@Override
	public boolean continueExecuting() {
		if (!theOwner.isEntityAlive()) {
			return false;
		}
		attackTarget = theOwner.getAttackTarget();
		return attackTarget != null && attackTarget.isEntityAlive();
	}

	@Override
	public void resetTask() {
		attackTarget = null;
		repathDelay = 0;
		rangedAttackTime = -1;
	}

	@Override
	public boolean shouldExecute() {
		EntityLivingBase target = theOwner.getAttackTarget();
		if (target == null) {
			return false;
		}
		attackTarget = target;
		return true;
	}

	@Override
	public void updateTask() {
		double distanceSq = theOwner.getDistanceSq(attackTarget.posX, attackTarget.boundingBox.minY, attackTarget.posZ);
		boolean canSee = theOwner.getEntitySenses().canSee(attackTarget);
		repathDelay = canSee ? ++repathDelay : 0;
		if (distanceSq <= attackRangeSq) {
			if (theOwner.getDistanceSqToEntity(attackTarget) < 25.0) {
				Vec3 vec = findPositionAwayFrom(theOwner, attackTarget, 8, 16);
				if (vec != null) {
					theOwner.getNavigator().tryMoveToXYZ(vec.xCoord, vec.yCoord, vec.zCoord, moveSpeedFlee);
				}
			} else if (repathDelay >= 20) {
				theOwner.getNavigator().clearPathEntity();
				repathDelay = 0;
			}
		} else {
			theOwner.getNavigator().tryMoveToEntityLiving(attackTarget, moveSpeed);
		}
		theOwner.getLookHelper().setLookPositionWithEntity(attackTarget, 30.0f, 30.0f);
		--rangedAttackTime;
		if (rangedAttackTime == 0) {
			float distanceRatio;
			if (distanceSq > attackRangeSq || !canSee) {
				return;
			}
			float power = distanceRatio = MathHelper.sqrt_double(distanceSq) / attackRange;
			power = MathHelper.clamp_float(power, 0.1f, 1.0f);
			theOwnerRanged.attackEntityWithRangedAttack(attackTarget, power);
			rangedAttackTime = MathHelper.floor_float(distanceRatio * (attackTimeMax - attackTimeMin) + attackTimeMin);
		} else if (rangedAttackTime < 0) {
			float distanceRatio = MathHelper.sqrt_double(distanceSq) / attackRange;
			rangedAttackTime = MathHelper.floor_float(distanceRatio * (attackTimeMax - attackTimeMin) + attackTimeMin);
		}
	}

	public static Vec3 findPositionAwayFrom(EntityLivingBase entity, EntityLivingBase target, int min, int max) {
		Random random = entity.getRNG();
		for (int l = 0; l < 24; ++l) {
			int k;
			int j;
			int i = MathHelper.floor_double(entity.posX) - max + random.nextInt(max * 2 + 1);
			double d = target.getDistanceSq(i, j = MathHelper.floor_double(entity.boundingBox.minY) - 4 + random.nextInt(9), k = MathHelper.floor_double(entity.posZ) - max + random.nextInt(max * 2 + 1));
			if (d <= min * min || d >= max * max) {
				continue;
			}
			return Vec3.createVectorHelper(i, j, k);
		}
		return null;
	}
}
