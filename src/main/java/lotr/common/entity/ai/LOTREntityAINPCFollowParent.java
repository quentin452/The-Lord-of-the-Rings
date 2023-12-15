package lotr.common.entity.ai;

import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAINPCFollowParent extends EntityAIBase {
    private final LOTREntityNPC theNPC;
    private LOTREntityNPC parentNPC;
    private final double moveSpeed;
    private int followTick;

    public LOTREntityAINPCFollowParent(LOTREntityNPC npc, double moveSpeed) {
        this.theNPC = npc;
        this.moveSpeed = moveSpeed;
    }

    @Override
    public boolean continueExecuting() {
        return parentNPC != null && parentNPC.isEntityAlive() &&
            theNPC.getDistanceSqToEntity(parentNPC) >= 9.0 && theNPC.getDistanceSqToEntity(parentNPC) <= 256.0;
    }

    @Override
    public void resetTask() {
        parentNPC = null;
    }

    @Override
    public boolean shouldExecute() {
        if (theNPC.familyInfo.getAge() >= 0) {
            return false;
        }

        LOTREntityNPC parent = theNPC.familyInfo.getParentToFollow();
        if (parent == null || theNPC.getDistanceSqToEntity(parent) < 9.0 || theNPC.getDistanceSqToEntity(parent) >= 256.0) {
            return false;
        }

        parentNPC = parent;
        return true;
    }

    @Override
    public void startExecuting() {
        followTick = 0;
    }

    @Override
    public void updateTask() {
        if (--followTick <= 0) {
            followTick = 10;
            theNPC.getNavigator().tryMoveToEntityLiving(parentNPC, moveSpeed);
        }
    }
}
