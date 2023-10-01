package lotr.common.entity.ai;

import lotr.common.entity.animal.LOTREntityLionBase;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.List;

public class LOTREntityAILionChase extends EntityAIBase {
	public LOTREntityLionBase theLion;
	public EntityCreature targetEntity;
	public double speed;
	public int chaseTimer;
	public int lionRePathDelay;

	public LOTREntityAILionChase(LOTREntityLionBase lion, double d) {
		theLion = lion;
		speed = d;
		setMutexBits(1);
	}

	@Override
	public boolean continueExecuting() {
		return targetEntity != null && targetEntity.isEntityAlive() && chaseTimer > 0 && theLion.getDistanceSqToEntity(targetEntity) < 256.0;
	}

	@Override
	public void resetTask() {
		chaseTimer = 0;
		lionRePathDelay = 0;
	}

	@Override
	public boolean shouldExecute() {
		if (theLion.isChild() || theLion.isInLove() || theLion.getRNG().nextInt(800) != 0) {
			return false;
		}
		List entities = theLion.worldObj.getEntitiesWithinAABB(EntityAnimal.class, theLion.boundingBox.expand(12.0, 12.0, 12.0));
		ArrayList<EntityAnimal> validTargets = new ArrayList<>();
		for (Object entitie : entities) {
			EntityAnimal entity = (EntityAnimal) entitie;
			if (entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.attackDamage) != null) {
				continue;
			}
			validTargets.add(entity);
		}
		if (validTargets.isEmpty()) {
			return false;
		}
		targetEntity = validTargets.get(theLion.getRNG().nextInt(validTargets.size()));
		return true;
	}

	@Override
	public void startExecuting() {
		chaseTimer = 300 + theLion.getRNG().nextInt(400);
	}

	@Override
	public void updateTask() {
		Vec3 vec3;
		--chaseTimer;
		theLion.getLookHelper().setLookPositionWithEntity(targetEntity, 30.0f, 30.0f);
		--lionRePathDelay;
		if (lionRePathDelay <= 0) {
			lionRePathDelay = 10;
			theLion.getNavigator().tryMoveToEntityLiving(targetEntity, speed);
		}
		if (targetEntity.getNavigator().noPath() && (vec3 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(targetEntity, 16, 7, Vec3.createVectorHelper(theLion.posX, theLion.posY, theLion.posZ))) != null) {
			targetEntity.getNavigator().tryMoveToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord, 2.0);
		}
	}
}
