package lotr.common.entity.ai;

import java.util.*;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;

public class LOTREntityAIFollowHiringPlayer extends EntityAIBase {
	public LOTREntityNPC theNPC;
	public boolean isBannerBearer;
	public EntityPlayer theHiringPlayer;
	public double moveSpeed;
	public int followTick;
	public float maxNearDist;
	public float minFollowDist;
	public boolean avoidsWater;
	public EntityLiving bannerBearerTarget;

	public LOTREntityAIFollowHiringPlayer(LOTREntityNPC entity) {
		theNPC = entity;
		isBannerBearer = entity instanceof LOTRBannerBearer;
		double entityMoveSpeed = entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
		moveSpeed = 1.0 / entityMoveSpeed * 0.37;
		minFollowDist = 8.0f;
		maxNearDist = 6.0f;
		setMutexBits(3);
	}

	@Override
	public boolean continueExecuting() {
		if (theNPC.hiredNPCInfo.isActive && theNPC.hiredNPCInfo.getHiringPlayer() != null && theNPC.hiredNPCInfo.shouldFollowPlayer() && !theNPC.getNavigator().noPath()) {
			EntityLivingBase target = bannerBearerTarget != null ? bannerBearerTarget : theHiringPlayer;
			return theNPC.getDistanceSqToEntity(target) > maxNearDist * maxNearDist;
		}
		return false;
	}

	@Override
	public void resetTask() {
		theHiringPlayer = null;
		bannerBearerTarget = null;
		theNPC.getNavigator().clearPathEntity();
		theNPC.getNavigator().setAvoidsWater(avoidsWater);
	}

	@Override
	public boolean shouldExecute() {
		if (!theNPC.hiredNPCInfo.isActive) {
			return false;
		}
		EntityPlayer entityplayer = theNPC.hiredNPCInfo.getHiringPlayer();
		if (entityplayer == null) {
			return false;
		}
		theHiringPlayer = entityplayer;
		if (!theNPC.hiredNPCInfo.shouldFollowPlayer()) {
			return false;
		}
		if (isBannerBearer) {
			Collection<EntityLiving> alliesToFollow = new ArrayList<>();
			List nearbyEntities = theNPC.worldObj.getEntitiesWithinAABB(EntityLiving.class, theNPC.boundingBox.expand(16.0, 16.0, 16.0));
			for (Object nearbyEntitie : nearbyEntities) {
				EntityLiving entity = (EntityLiving) nearbyEntitie;
				if (entity == theNPC || LOTRMod.getNPCFaction(entity) != theNPC.getFaction()) {
					continue;
				}
				if (entity instanceof LOTREntityNPC) {
					LOTREntityNPC npc = (LOTREntityNPC) entity;
					if (!npc.hiredNPCInfo.isActive || npc.hiredNPCInfo.getHiringPlayer() != entityplayer) {
						continue;
					}
				}
				alliesToFollow.add(entity);
			}
			EntityLiving entityToFollow = null;
			double d = Double.MAX_VALUE;
			for (EntityLiving entity : alliesToFollow) {
				double dist = theNPC.getDistanceSqToEntity(entity);
				if (dist >= d || dist <= minFollowDist * minFollowDist) {
					continue;
				}
				d = dist;
				entityToFollow = entity;
			}
			if (entityToFollow != null) {
				bannerBearerTarget = entityToFollow;
				return true;
			}
		}
		return theNPC.getDistanceSqToEntity(entityplayer) >= minFollowDist * minFollowDist;
	}

	@Override
	public void startExecuting() {
		followTick = 0;
		avoidsWater = theNPC.getNavigator().getAvoidsWater();
		theNPC.getNavigator().setAvoidsWater(false);
	}

	@Override
	public void updateTask() {
		EntityLivingBase target = bannerBearerTarget != null ? bannerBearerTarget : theHiringPlayer;
		theNPC.getLookHelper().setLookPositionWithEntity(target, 10.0f, theNPC.getVerticalFaceSpeed());
		if (theNPC.hiredNPCInfo.shouldFollowPlayer() && --followTick <= 0) {
			followTick = 10;
			if (!theNPC.getNavigator().tryMoveToEntityLiving(target, moveSpeed) && theNPC.hiredNPCInfo.teleportAutomatically) {
				double d = theNPC.getEntityAttribute(SharedMonsterAttributes.followRange).getAttributeValue();
				d = Math.max(d, 24.0);
				if (theNPC.getDistanceSqToEntity(theHiringPlayer) > d * d) {
					theNPC.hiredNPCInfo.tryTeleportToHiringPlayer(false);
				}
			}
		}
	}
}
