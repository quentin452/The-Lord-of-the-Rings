package lotr.common.entity.ai;

import lotr.common.LOTRConfig;
import lotr.common.entity.npc.LOTREntityOrc;
import net.minecraft.entity.EntityLivingBase;

import java.util.List;

public class LOTREntityAIOrcSkirmish extends LOTREntityAINearestAttackableTargetBasic {
	public LOTREntityOrc theOrc;

	public LOTREntityAIOrcSkirmish(LOTREntityOrc orc, boolean flag) {
		super(orc, LOTREntityOrc.class, 0, flag, null);
		theOrc = orc;
	}

	public boolean canOrcSkirmish(EntityLivingBase entity) {
		if (entity instanceof LOTREntityOrc) {
			LOTREntityOrc orc = (LOTREntityOrc) entity;
			return !orc.isTrader() && !orc.hiredNPCInfo.isActive && orc.ridingEntity == null && orc.canOrcSkirmish();
		}
		return false;
	}

	@Override
	public boolean isSuitableTarget(EntityLivingBase entity, boolean flag) {
		return canOrcSkirmish(entity) && super.isSuitableTarget(entity, flag);
	}

	@Override
	public boolean shouldExecute() {
		if (!LOTRConfig.enableOrcSkirmish || !canOrcSkirmish(theOrc)) {
			return false;
		}
		if (!theOrc.isOrcSkirmishing()) {
			int chance = 20000;
			List nearbyOrcs = theOrc.worldObj.getEntitiesWithinAABB(LOTREntityOrc.class, theOrc.boundingBox.expand(16.0, 8.0, 16.0));
			for (Object nearbyOrc : nearbyOrcs) {
				LOTREntityOrc orc = (LOTREntityOrc) nearbyOrc;
				if (!orc.isOrcSkirmishing()) {
					continue;
				}
				chance /= 10;
			}
			if (chance < 40) {
				chance = 40;
			}
			if (theOrc.getRNG().nextInt(chance) != 0) {
				return false;
			}
		}
		return super.shouldExecute();
	}

	@Override
	public void startExecuting() {
		super.startExecuting();
		theOrc.setOrcSkirmishing();
	}
}
