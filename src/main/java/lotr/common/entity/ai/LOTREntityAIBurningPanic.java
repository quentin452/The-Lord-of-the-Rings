package lotr.common.entity.ai;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.Random;

public class LOTREntityAIBurningPanic extends EntityAIBase {
	public EntityCreature theEntity;
	public World theWorld;
	public double speed;
	public double randPosX;
	public double randPosY;
	public double randPosZ;
	public boolean avoidsWater;

	public LOTREntityAIBurningPanic(EntityCreature entity, double d) {
		theEntity = entity;
		theWorld = theEntity.worldObj;
		speed = d;
		setMutexBits(1);
	}

	@Override
	public boolean continueExecuting() {
		return theEntity.isBurning() && theEntity.getAttackTarget() == null && !theEntity.getNavigator().noPath();
	}

	public Vec3 findWaterLocation() {
		Random random = theEntity.getRNG();
		for (int l = 0; l < 32; ++l) {
			int j;
			int k;
			int i = MathHelper.floor_double(theEntity.posX) + MathHelper.getRandomIntegerInRange(random, -8, 8);
			if (theWorld.getBlock(i, (j = MathHelper.floor_double(theEntity.boundingBox.minY) + MathHelper.getRandomIntegerInRange(random, -8, 8)) + 1, k = MathHelper.floor_double(theEntity.posZ) + MathHelper.getRandomIntegerInRange(random, -8, 8)).isNormalCube() || theWorld.getBlock(i, j, k).isNormalCube() || theWorld.getBlock(i, j - 1, k).getMaterial() != Material.water) {
				continue;
			}
			return Vec3.createVectorHelper(i + 0.5, j + 0.5, k + 0.5);
		}
		return null;
	}

	@Override
	public void resetTask() {
		theEntity.getNavigator().setAvoidsWater(avoidsWater);
	}

	@Override
	public boolean shouldExecute() {
		if (theEntity.isBurning() && theEntity.getAttackTarget() == null) {
			Vec3 target = findWaterLocation();
			if (target == null) {
				target = RandomPositionGenerator.findRandomTarget(theEntity, 5, 4);
			}
			if (target != null) {
				randPosX = target.xCoord;
				randPosY = target.yCoord;
				randPosZ = target.zCoord;
				return true;
			}
		}
		return false;
	}

	@Override
	public void startExecuting() {
		avoidsWater = theEntity.getNavigator().getAvoidsWater();
		theEntity.getNavigator().setAvoidsWater(false);
		theEntity.getNavigator().tryMoveToXYZ(randPosX, randPosY, randPosZ, speed);
	}
}
