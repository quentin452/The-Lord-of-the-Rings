package lotr.common.entity.ai;

import lotr.common.entity.npc.LOTREntityGollum;
import lotr.common.entity.npc.LOTRSpeech;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.Random;

public class LOTREntityAIGollumFishing extends EntityAIBase {
	public LOTREntityGollum theGollum;
	public double moveSpeed;
	public boolean avoidsWater;
	public World theWorld;
	public double xPosition;
	public double yPosition;
	public double zPosition;
	public int moveTick;
	public int fishTick;
	public boolean finished;

	public LOTREntityAIGollumFishing(LOTREntityGollum entity, double d) {
		theGollum = entity;
		moveSpeed = d;
		theWorld = entity.worldObj;
		setMutexBits(3);
	}

	public boolean atFishingLocation() {
		if (theGollum.getDistanceSq(xPosition, yPosition, zPosition) < 4.0) {
			int j;
			int k;
			int i = MathHelper.floor_double(theGollum.posX);
			return theWorld.getBlock(i, j = MathHelper.floor_double(theGollum.boundingBox.minY), k = MathHelper.floor_double(theGollum.posZ)).getMaterial() == Material.water || theWorld.getBlock(i, j - 1, k).getMaterial() == Material.water;
		}
		return false;
	}

	@Override
	public boolean continueExecuting() {
		return theGollum.getGollumOwner() != null && !theGollum.isGollumSitting() && moveTick < 300 && !finished;
	}

	public Vec3 findPossibleFishingLocation() {
		Random random = theGollum.getRNG();
		for (int l = 0; l < 32; ++l) {
			int j;
			int k;
			int i = MathHelper.floor_double(theGollum.posX) - 16 + random.nextInt(33);
			if (theWorld.getBlock(i, (j = MathHelper.floor_double(theGollum.boundingBox.minY) - 8 + random.nextInt(17)) + 1, k = MathHelper.floor_double(theGollum.posZ) - 16 + random.nextInt(33)).isNormalCube() || theWorld.getBlock(i, j, k).isNormalCube() || theWorld.getBlock(i, j - 1, k).getMaterial() != Material.water) {
				continue;
			}
			return Vec3.createVectorHelper(i + 0.5, j + 0.5, k + 0.5);
		}
		return null;
	}

	@Override
	public void resetTask() {
		theGollum.getNavigator().clearPathEntity();
		theGollum.getNavigator().setAvoidsWater(avoidsWater);
		moveTick = 0;
		fishTick = 0;
		if (finished) {
			finished = false;
			theGollum.prevFishTime = 3000;
		} else {
			theGollum.prevFishTime = 600;
		}
		theGollum.isFishing = false;
	}

	@Override
	public boolean shouldExecute() {
		if (theGollum.getGollumOwner() == null || theGollum.isGollumSitting() || theGollum.prevFishTime > 0 || theGollum.isFishing) {
			return false;
		}
		if (theGollum.getEquipmentInSlot(0) != null) {
			return false;
		}
		if (theGollum.getRNG().nextInt(60) == 0) {
			Vec3 vec3 = findPossibleFishingLocation();
			if (vec3 == null) {
				return false;
			}
			xPosition = vec3.xCoord;
			yPosition = vec3.yCoord;
			zPosition = vec3.zCoord;
			return true;
		}
		return false;
	}

	@Override
	public void startExecuting() {
		avoidsWater = theGollum.getNavigator().getAvoidsWater();
		theGollum.getNavigator().setAvoidsWater(false);
		theGollum.isFishing = true;
	}

	@Override
	public void updateTask() {
		if (atFishingLocation()) {
			if (theGollum.isInWater()) {
				theWorld.setEntityState(theGollum, (byte) 15);
				if (theGollum.getRNG().nextInt(4) == 0) {
					theWorld.playSoundAtEntity(theGollum, theGollum.getSplashSound(), 1.0f, 1.0f + (theGollum.getRNG().nextFloat() - theGollum.getRNG().nextFloat()) * 0.4f);
				}
				theGollum.getJumpHelper().setJumping();
				if (theGollum.getRNG().nextInt(50) == 0) {
					LOTRSpeech.sendSpeech(theGollum.getGollumOwner(), theGollum, LOTRSpeech.getRandomSpeechForPlayer(theGollum, "char/gollum/fishing", theGollum.getGollumOwner()));
				}
			}
			++fishTick;
			if (fishTick > 100) {
				theGollum.setCurrentItemOrArmor(0, new ItemStack(Items.fish, 4 + theGollum.getRNG().nextInt(9)));
				finished = true;
				LOTRSpeech.sendSpeech(theGollum.getGollumOwner(), theGollum, LOTRSpeech.getRandomSpeechForPlayer(theGollum, "char/gollum/catchFish", theGollum.getGollumOwner()));
			}
		} else {
			theGollum.getNavigator().tryMoveToXYZ(xPosition, yPosition, zPosition, moveSpeed);
			++moveTick;
		}
	}
}
