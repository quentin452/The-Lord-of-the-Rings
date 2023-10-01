package lotr.common.entity.ai;

import lotr.common.entity.npc.LOTREntityGollum;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTREntityAIGollumFollowOwner extends EntityAIBase {
	public LOTREntityGollum theGollum;
	public EntityPlayer theOwner;
	public double moveSpeed;
	public PathNavigate theGollumPathfinder;
	public int followTick;
	public float maxDist;
	public float minDist;
	public boolean avoidsWater;
	public World theWorld;

	public LOTREntityAIGollumFollowOwner(LOTREntityGollum entity, double d, float f, float f1) {
		theGollum = entity;
		moveSpeed = d;
		theGollumPathfinder = entity.getNavigator();
		minDist = f;
		maxDist = f1;
		theWorld = entity.worldObj;
		setMutexBits(3);
	}

	@Override
	public boolean continueExecuting() {
		return theGollum.getGollumOwner() != null && !theGollumPathfinder.noPath() && theGollum.getDistanceSqToEntity(theOwner) > maxDist * maxDist && !theGollum.isGollumSitting();
	}

	@Override
	public void resetTask() {
		theOwner = null;
		theGollumPathfinder.clearPathEntity();
		theGollum.getNavigator().setAvoidsWater(avoidsWater);
	}

	@Override
	public boolean shouldExecute() {
		EntityPlayer entityplayer = theGollum.getGollumOwner();
		if (entityplayer == null || theGollum.isGollumSitting() || theGollum.getDistanceSqToEntity(entityplayer) < minDist * minDist) {
			return false;
		}
		theOwner = entityplayer;
		return true;
	}

	@Override
	public void startExecuting() {
		followTick = 0;
		avoidsWater = theGollum.getNavigator().getAvoidsWater();
		theGollum.getNavigator().setAvoidsWater(false);
	}

	@Override
	public void updateTask() {
		theGollum.getLookHelper().setLookPositionWithEntity(theOwner, 10.0f, theGollum.getVerticalFaceSpeed());
		if (!theGollum.isGollumSitting() && --followTick <= 0) {
			followTick = 10;
			if (!theGollumPathfinder.tryMoveToEntityLiving(theOwner, moveSpeed) && theGollum.getDistanceSqToEntity(theOwner) >= 256.0) {
				int i = MathHelper.floor_double(theOwner.posX);
				int j = MathHelper.floor_double(theOwner.boundingBox.minY);
				int k = MathHelper.floor_double(theOwner.posZ);
				float f = theGollum.width / 2.0f;
				float f1 = theGollum.height;
				AxisAlignedBB theGollumBoundingBox = AxisAlignedBB.getBoundingBox(theOwner.posX - f, theOwner.posY - theGollum.yOffset + theGollum.ySize, theOwner.posZ - f, theOwner.posX + f, theOwner.posY - theGollum.yOffset + theGollum.ySize + f1, theOwner.posZ + f);
				if (theWorld.func_147461_a(theGollumBoundingBox).isEmpty() && theWorld.getBlock(i, j - 1, k).isSideSolid(theWorld, i, j - 1, k, ForgeDirection.UP)) {
					theGollum.setLocationAndAngles(theOwner.posX, theOwner.boundingBox.minY, theOwner.posZ, theGollum.rotationYaw, theGollum.rotationPitch);
					theGollum.fallDistance = 0.0f;
					theGollum.getNavigator().clearPathEntity();
				}
			}
		}
	}
}
