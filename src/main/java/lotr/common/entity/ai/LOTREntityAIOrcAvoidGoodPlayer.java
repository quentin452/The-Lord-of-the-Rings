package lotr.common.entity.ai;

import java.util.List;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityOrc;
import lotr.common.fac.LOTRFaction;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.*;
import net.minecraft.util.Vec3;

public class LOTREntityAIOrcAvoidGoodPlayer extends EntityAIBase {
	public LOTREntityOrc theOrc;
	public double speed;
	public EntityLivingBase closestEnemyPlayer;
	public float distanceFromEntity;
	public PathEntity entityPathEntity;
	public PathNavigate entityPathNavigate;

	public LOTREntityAIOrcAvoidGoodPlayer(LOTREntityOrc orc, float f, double d) {
		theOrc = orc;
		distanceFromEntity = f;
		speed = d;
		entityPathNavigate = orc.getNavigator();
		setMutexBits(1);
	}

	@SuppressWarnings("Convert2Lambda")
	public boolean anyNearbyOrcsAttacked() {
		List nearbyAllies = theOrc.worldObj.selectEntitiesWithinAABB(EntityLiving.class, theOrc.boundingBox.expand(distanceFromEntity, distanceFromEntity / 2.0, distanceFromEntity), new IEntitySelector() {

			@Override
			public boolean isEntityApplicable(Entity entity) {
				if (entity != theOrc) {
					return LOTRMod.getNPCFaction(entity).isGoodRelation(theOrc.getFaction());
				}
				return false;
			}
		});
		for (Object obj : nearbyAllies) {
			EntityLiving ally = (EntityLiving) obj;
			if (ally instanceof LOTREntityOrc ? !(((LOTREntityOrc) ally).currentRevengeTarget instanceof EntityPlayer) : !(ally.getAttackTarget() instanceof EntityPlayer)) {
				continue;
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean continueExecuting() {
		return !entityPathNavigate.noPath() && theOrc.getAITarget() != closestEnemyPlayer && !anyNearbyOrcsAttacked();
	}

	@Override
	public void resetTask() {
		closestEnemyPlayer = null;
	}

	@SuppressWarnings("Convert2Lambda")
	@Override
	public boolean shouldExecute() {
		if (!theOrc.isWeakOrc || theOrc.hiredNPCInfo.isActive || theOrc.getFaction() == LOTRFaction.MORDOR) {
			return false;
		}
		if (theOrc.currentRevengeTarget != null || anyNearbyOrcsAttacked()) {
			return false;
		}
		List validPlayers = theOrc.worldObj.selectEntitiesWithinAABB(EntityPlayer.class, theOrc.boundingBox.expand(distanceFromEntity, distanceFromEntity / 2.0, distanceFromEntity), new IEntitySelector() {

			@Override
			public boolean isEntityApplicable(Entity entity) {
				EntityPlayer entityplayer = (EntityPlayer) entity;
				if (entityplayer.capabilities.isCreativeMode || theOrc.currentRevengeTarget == entityplayer) {
					return false;
				}
				float alignment = LOTRLevelData.getData(entityplayer).getAlignment(theOrc.getFaction());
				return alignment <= -500.0f;
			}
		});
		if (validPlayers.isEmpty()) {
			return false;
		}
		closestEnemyPlayer = (EntityLivingBase) validPlayers.get(0);
		Vec3 fleePath = RandomPositionGenerator.findRandomTargetBlockAwayFrom(theOrc, 16, 7, Vec3.createVectorHelper(closestEnemyPlayer.posX, closestEnemyPlayer.posY, closestEnemyPlayer.posZ));
		if (fleePath == null || closestEnemyPlayer.getDistanceSq(fleePath.xCoord, fleePath.yCoord, fleePath.zCoord) < closestEnemyPlayer.getDistanceSqToEntity(theOrc)) {
			return false;
		}
		entityPathEntity = entityPathNavigate.getPathToXYZ(fleePath.xCoord, fleePath.yCoord, fleePath.zCoord);
		return entityPathEntity != null && entityPathEntity.isDestinationSame(fleePath);
	}

	@Override
	public void startExecuting() {
		entityPathNavigate.setPath(entityPathEntity, speed);
	}

}
