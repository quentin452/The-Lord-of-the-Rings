package lotr.common.entity.ai;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityWargBombardier;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.world.World;

public class LOTREntityAIWargBombardierAttack extends EntityAIBase {
	public World worldObj;
	public LOTREntityWargBombardier theWarg;
	public EntityLivingBase entityTarget;
	public double moveSpeed;
	public PathEntity entityPathEntity;
	public int randomMoveTick;

	public LOTREntityAIWargBombardierAttack(LOTREntityWargBombardier entity, double speed) {
		theWarg = entity;
		worldObj = entity.worldObj;
		moveSpeed = speed;
		setMutexBits(3);
	}

	@Override
	public boolean continueExecuting() {
		EntityLivingBase entity = theWarg.getAttackTarget();
		return entity != null && entityTarget.isEntityAlive();
	}

	@Override
	public void resetTask() {
		entityTarget = null;
		theWarg.getNavigator().clearPathEntity();
		theWarg.setBombFuse(35);
	}

	@Override
	public boolean shouldExecute() {
		EntityLivingBase entity = theWarg.getAttackTarget();
		if (entity == null) {
			return false;
		}
		entityTarget = entity;
		entityPathEntity = theWarg.getNavigator().getPathToEntityLiving(entityTarget);
		return entityPathEntity != null;
	}

	@Override
	public void startExecuting() {
		theWarg.getNavigator().setPath(entityPathEntity, moveSpeed);
		randomMoveTick = 0;
	}

	@Override
	public void updateTask() {
		theWarg.getLookHelper().setLookPositionWithEntity(entityTarget, 30.0f, 30.0f);
		if (theWarg.getEntitySenses().canSee(entityTarget) && --randomMoveTick <= 0) {
			randomMoveTick = 4 + theWarg.getRNG().nextInt(7);
			theWarg.getNavigator().tryMoveToEntityLiving(entityTarget, moveSpeed);
		}
		if (theWarg.getDistanceSq(entityTarget.posX, entityTarget.boundingBox.minY, entityTarget.posZ) <= 16.0) {
			if (theWarg.getBombFuse() > 20) {
				int i;
				//noinspection StatementWithEmptyBody
				for (i = theWarg.getBombFuse(); i > 20; i -= 10) {
				}
				theWarg.setBombFuse(i);
			} else if (theWarg.getBombFuse() > 0) {
				theWarg.setBombFuse(theWarg.getBombFuse() - 1);
			} else {
				worldObj.createExplosion(theWarg, theWarg.posX, theWarg.posY, theWarg.posZ, (theWarg.getBombStrengthLevel() + 1) * 4.0f, LOTRMod.canGrief(worldObj));
				theWarg.setDead();
			}
		} else if (theWarg.getBombFuse() <= 20) {
			int i;
			//noinspection StatementWithEmptyBody
			for (i = theWarg.getBombFuse(); i <= 20; i += 10) {
			}
			theWarg.setBombFuse(i);
		} else {
			theWarg.setBombFuse(theWarg.getBombFuse() - 1);
		}
	}
}
