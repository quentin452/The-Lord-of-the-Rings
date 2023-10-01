package lotr.common.entity.ai;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityEnt;
import lotr.common.tileentity.LOTRTileEntityCorruptMallorn;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class LOTREntityAIEntHealSapling extends EntityAIBase {
	public static int HEAL_TIME = 160;
	public LOTREntityEnt theEnt;
	public World theWorld;
	public double moveSpeed;
	public double xPos;
	public double yPos;
	public double zPos;
	public int healingTick;
	public int pathingTick;
	public int rePathDelay;

	public LOTREntityAIEntHealSapling(LOTREntityEnt ent, double d) {
		theEnt = ent;
		moveSpeed = d;
		theWorld = ent.worldObj;
		setMutexBits(3);
	}

	@Override
	public boolean continueExecuting() {
		if (!theEnt.canHealSapling) {
			return false;
		}
		if (pathingTick < 300 && healingTick < HEAL_TIME) {
			Block block = theWorld.getBlock(MathHelper.floor_double(xPos), MathHelper.floor_double(yPos), MathHelper.floor_double(zPos));
			return block == LOTRMod.corruptMallorn;
		}
		return false;
	}

	public Vec3 findSapling() {
		double leastDistSq = 576.0;
		LOTRTileEntityCorruptMallorn mallorn = null;
		for (Object obj : theWorld.loadedTileEntityList) {
			if (!(obj instanceof LOTRTileEntityCorruptMallorn)) {
				continue;
			}
			LOTRTileEntityCorruptMallorn te = (LOTRTileEntityCorruptMallorn) obj;
			double distSq = theEnt.getDistanceSq(te.xCoord + 0.5, te.yCoord, te.zCoord + 0.5);
			if (distSq >= leastDistSq) {
				continue;
			}
			mallorn = te;
			leastDistSq = distSq;
		}
		if (mallorn != null) {
			return Vec3.createVectorHelper(mallorn.xCoord + 0.5, mallorn.yCoord, mallorn.zCoord + 0.5);
		}
		return null;
	}

	@Override
	public void resetTask() {
		pathingTick = 0;
		healingTick = 0;
		rePathDelay = 0;
		theEnt.setHealingSapling(false);
	}

	@Override
	public boolean shouldExecute() {
		Vec3 vec3;
		if (theEnt.canHealSapling && (vec3 = findSapling()) != null) {
			xPos = vec3.xCoord;
			yPos = vec3.yCoord;
			zPos = vec3.zCoord;
			return true;
		}
		return false;
	}

	@Override
	public void updateTask() {
		if (theEnt.getDistanceSq(xPos, yPos, zPos) > 9.0) {
			theEnt.setHealingSapling(false);
			--rePathDelay;
			if (rePathDelay <= 0) {
				rePathDelay = 10;
				theEnt.getNavigator().tryMoveToXYZ(xPos, yPos, zPos, moveSpeed);
			}
			++pathingTick;
		} else {
			theEnt.getNavigator().clearPathEntity();
			theEnt.getLookHelper().setLookPosition(xPos, yPos + 0.5, zPos, 10.0f, theEnt.getVerticalFaceSpeed());
			theEnt.setHealingSapling(true);
			theEnt.saplingHealTarget = new ChunkCoordinates(MathHelper.floor_double(xPos), MathHelper.floor_double(yPos), MathHelper.floor_double(zPos));
			++healingTick;
			if (healingTick >= HEAL_TIME) {
				theWorld.setBlock(MathHelper.floor_double(xPos), MathHelper.floor_double(yPos), MathHelper.floor_double(zPos), LOTRMod.sapling, 1, 3);
				theEnt.setHealingSapling(false);
			}
		}
	}
}
