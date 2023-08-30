package lotr.client;

import java.util.List;

import lotr.common.item.LOTRWeaponStats;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.*;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.*;

public class LOTREntityRenderer extends EntityRenderer {
	public Minecraft theMC;
	public Entity thePointedEntity;

	public LOTREntityRenderer(Minecraft mc, IResourceManager irm) {
		super(mc, irm);
		theMC = mc;
	}

	@Override
	public void getMouseOver(float partialTick) {
		if (theMC.renderViewEntity != null && theMC.theWorld != null) {
			double reach;
			theMC.pointedEntity = null;
			thePointedEntity = null;
			double blockReach = theMC.playerController.getBlockReachDistance();
			float meleeReachFactor = LOTRWeaponStats.getMeleeReachFactor(theMC.thePlayer.getHeldItem());
			theMC.objectMouseOver = theMC.renderViewEntity.rayTrace(blockReach * meleeReachFactor, partialTick);
			double maxDist = reach = LOTRWeaponStats.getMeleeReachDistance(theMC.thePlayer);
			Vec3 posVec = theMC.renderViewEntity.getPosition(partialTick);
			if (theMC.objectMouseOver != null) {
				maxDist = theMC.objectMouseOver.hitVec.distanceTo(posVec);
			}
			Vec3 lookVec = theMC.renderViewEntity.getLook(partialTick);
			Vec3 sightVec = posVec.addVector(lookVec.xCoord * reach, lookVec.yCoord * reach, lookVec.zCoord * reach);
			Vec3 targetVec = null;
			float lookWidth = LOTRWeaponStats.getMeleeExtraLookWidth();
			List entities = theMC.theWorld.getEntitiesWithinAABBExcludingEntity(theMC.renderViewEntity, theMC.renderViewEntity.boundingBox.addCoord(lookVec.xCoord * reach, lookVec.yCoord * reach, lookVec.zCoord * reach).expand(lookWidth, lookWidth, lookWidth));
			double leastDist = maxDist;
			for (Object entitie : entities) {
				double entityDist;
				Entity entity = (Entity) entitie;
				if (!entity.canBeCollidedWith()) {
					continue;
				}
				float f = entity.getCollisionBorderSize();
				AxisAlignedBB entityBB = entity.boundingBox.expand(f, f, f);
				MovingObjectPosition movingobjectposition = entityBB.calculateIntercept(posVec, sightVec);
				if (entityBB.isVecInside(posVec)) {
					if (leastDist <= 0.0 && leastDist != 0.0) {
						continue;
					}
					thePointedEntity = entity;
					targetVec = movingobjectposition == null ? posVec : movingobjectposition.hitVec;
					leastDist = 0.0;
					continue;
				}
				if (movingobjectposition == null || (entityDist = posVec.distanceTo(movingobjectposition.hitVec)) >= leastDist && leastDist != 0.0) {
					continue;
				}
				if (entity == theMC.renderViewEntity.ridingEntity && !entity.canRiderInteract()) {
					if (leastDist != 0.0) {
						continue;
					}
					thePointedEntity = entity;
					targetVec = movingobjectposition.hitVec;
					continue;
				}
				thePointedEntity = entity;
				targetVec = movingobjectposition.hitVec;
				leastDist = entityDist;
			}
			if (thePointedEntity != null && (leastDist < maxDist || theMC.objectMouseOver == null)) {
				theMC.objectMouseOver = new MovingObjectPosition(thePointedEntity, targetVec);
				if (thePointedEntity instanceof EntityLivingBase || thePointedEntity instanceof EntityItemFrame) {
					theMC.pointedEntity = thePointedEntity;
				}
			}
		}
	}

	@Override
	public void updateRenderer() {
		super.updateRenderer();
		if (Minecraft.isGuiEnabled()) {
			float wight = LOTRClientProxy.tickHandler.getWightLookFactor();
			float hand = LOTRReflectionClient.getHandFOV(this);
			LOTRReflectionClient.setHandFOV(this, hand + wight * 0.3f);
		}
	}
}
