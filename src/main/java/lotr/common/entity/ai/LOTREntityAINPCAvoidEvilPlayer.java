package lotr.common.entity.ai;

import lotr.common.LOTRLevelData;
import lotr.common.entity.npc.LOTREntityHobbit;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LOTREntityAINPCAvoidEvilPlayer extends EntityAIBase {
	public LOTREntityNPC theNPC;
	public double farSpeed;
	public double nearSpeed;
	public Entity closestLivingEntity;
	public float distanceFromEntity;
	public PathEntity entityPathEntity;
	public PathNavigate entityPathNavigate;

	public LOTREntityAINPCAvoidEvilPlayer(LOTREntityNPC npc, float f, double d, double d1) {
		theNPC = npc;
		distanceFromEntity = f;
		farSpeed = d;
		nearSpeed = d1;
		entityPathNavigate = npc.getNavigator();
		setMutexBits(1);
	}

	@Override
	public boolean continueExecuting() {
		return !entityPathNavigate.noPath();
	}

	@Override
	public void resetTask() {
		closestLivingEntity = null;
	}

    private List<EntityPlayer> validPlayers;

    @Override
    public boolean shouldExecute() {
        validPlayers = getValidPlayers();

        if (validPlayers.isEmpty()) {
            return false;
        }

        closestLivingEntity = validPlayers.get(0);
        Vec3 fleePath = findFleePath();

        if (fleePath == null || closestLivingEntity.getDistanceSq(fleePath.xCoord, fleePath.yCoord, fleePath.zCoord) < closestLivingEntity.getDistanceSqToEntity(theNPC)) {
            return false;
        }

        entityPathEntity = entityPathNavigate.getPathToXYZ(fleePath.xCoord, fleePath.yCoord, fleePath.zCoord);
        return entityPathEntity != null && entityPathEntity.isDestinationSame(fleePath);
    }


    private List<EntityPlayer> getValidPlayers() {
        return (List<EntityPlayer>) theNPC.worldObj.getEntitiesWithinAABB(EntityPlayer.class, theNPC.boundingBox.expand(distanceFromEntity, distanceFromEntity / 2.0, distanceFromEntity)).stream()
            .filter(entity -> !((EntityPlayer) entity).capabilities.isCreativeMode)
            .map(entity -> (EntityPlayer) entity)
            .filter(entityplayer -> {
                float alignment = LOTRLevelData.getData((EntityPlayer) entityplayer).getAlignment(theNPC.getFaction());
                return (theNPC.familyInfo.getAge() >= 0 || alignment >= 0.0f) &&
                    (!(theNPC instanceof LOTREntityHobbit) || alignment > -100.0f);
            })
            .collect(Collectors.toList());
    }

    private Vec3 findFleePath() {
        EntityPlayer closestPlayer = validPlayers.get(0);
        return RandomPositionGenerator.findRandomTargetBlockAwayFrom(theNPC, 16, 7, Vec3.createVectorHelper(closestPlayer.posX, closestPlayer.posY, closestPlayer.posZ));
    }

	@Override
	public void startExecuting() {
		entityPathNavigate.setPath(entityPathEntity, farSpeed);
	}

	@Override
	public void updateTask() {
		if (theNPC.getDistanceSqToEntity(closestLivingEntity) < 49.0) {
			theNPC.getNavigator().setSpeed(nearSpeed);
		} else {
			theNPC.getNavigator().setSpeed(farSpeed);
		}
	}
}
