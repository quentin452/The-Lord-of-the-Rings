package lotr.common.entity.ai;

import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntitySauron;
import lotr.common.item.LOTRItemSauronMace;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.List;

public class LOTREntityAISauronUseMace extends EntityAIBase {
	public LOTREntitySauron theSauron;
	public int attackTick;
	public World theWorld;

	public LOTREntityAISauronUseMace(LOTREntitySauron sauron) {
		theSauron = sauron;
		theWorld = theSauron.worldObj;
		setMutexBits(3);
	}

	@Override
	public boolean continueExecuting() {
		return theSauron.getIsUsingMace();
	}

	@Override
	public void resetTask() {
		attackTick = 40;
		theSauron.setIsUsingMace(false);
	}

	@Override
	public boolean shouldExecute() {
		int targets = 0;
		List nearbyEntities = theWorld.getEntitiesWithinAABB(EntityLivingBase.class, theSauron.boundingBox.expand(6.0, 6.0, 6.0));
		for (Object nearbyEntitie : nearbyEntities) {
			EntityLivingBase entity = (EntityLivingBase) nearbyEntitie;
			if (!entity.isEntityAlive()) {
				continue;
			}
			if (entity instanceof EntityPlayer) {
				EntityPlayer entityplayer = (EntityPlayer) entity;
				if (entityplayer.capabilities.isCreativeMode || LOTRLevelData.getData(entityplayer).getAlignment(theSauron.getFaction()) >= 0.0f && theSauron.getAttackTarget() != entityplayer) {
					continue;
				}
				++targets;
				continue;
			}
			if (theSauron.getFaction().isBadRelation(LOTRMod.getNPCFaction(entity))) {
				++targets;
				continue;
			}
			if (theSauron.getAttackTarget() != entity && (!(entity instanceof EntityLiving) || ((EntityLiving) entity).getAttackTarget() != theSauron)) {
				continue;
			}
			++targets;
		}
		if (targets >= 4) {
			return true;
		}
		return targets > 0 && theSauron.getRNG().nextInt(100) == 0;
	}

	@Override
	public void startExecuting() {
		attackTick = 40;
		theSauron.setIsUsingMace(true);
	}

	@Override
	public void updateTask() {
		attackTick = Math.max(attackTick - 1, 0);
		if (attackTick == 0) {
			attackTick = 40;
			LOTRItemSauronMace.useSauronMace(theSauron.getEquipmentInSlot(0), theWorld, theSauron);
			theSauron.setIsUsingMace(false);
		}
	}
}
