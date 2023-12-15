package lotr.common.entity.ai;

import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityBandit;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTREntityNPCRideable;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LOTREntityAINearestAttackableTargetBasic extends EntityAITarget {
	public Class targetClass;
	public int targetChance;
	public TargetSorter targetSorter;
	public IEntitySelector targetSelector;
	public EntityLivingBase targetEntity;

	public LOTREntityAINearestAttackableTargetBasic(EntityCreature entity, Class cls, int chance, boolean checkSight) {
		this(entity, cls, chance, checkSight, false, null);
	}

	@SuppressWarnings("Convert2Lambda")
	public LOTREntityAINearestAttackableTargetBasic(EntityCreature entity, Class cls, int chance, boolean checkSight, boolean nearby, IEntitySelector selector) {
		super(entity, checkSight, nearby);
		targetClass = cls;
		targetChance = chance;
		targetSorter = new TargetSorter(entity);
		setMutexBits(1);
		targetSelector = new IEntitySelector() {

			@Override
			public boolean isEntityApplicable(Entity testEntity) {
				if (testEntity instanceof EntityLivingBase) {
					EntityLivingBase testEntityLiving = (EntityLivingBase) testEntity;
					if (selector != null && !selector.isEntityApplicable(testEntityLiving)) {
						return false;
					}
					return isSuitableTarget(testEntityLiving, false);
				}
				return false;
			}
		};
	}

	public LOTREntityAINearestAttackableTargetBasic(EntityCreature entity, Class cls, int chance, boolean checkSight, IEntitySelector selector) {
		this(entity, cls, chance, checkSight, false, selector);
	}

	public boolean isPlayerSuitableAlignmentTarget(EntityPlayer entityplayer) {
		float alignment = LOTRLevelData.getData(entityplayer).getAlignment(LOTRMod.getNPCFaction(taskOwner));
		return alignment < 0.0f;
	}

	public boolean isPlayerSuitableTarget(EntityPlayer entityplayer) {
		return isPlayerSuitableAlignmentTarget(entityplayer);
	}

	@Override
	public boolean isSuitableTarget(EntityLivingBase entity, boolean flag) {
		if (entity == taskOwner.ridingEntity || entity == taskOwner.riddenByEntity) {
			return false;
		}
		if (super.isSuitableTarget(entity, flag)) {
			if (entity instanceof EntityPlayer) {
				return isPlayerSuitableTarget((EntityPlayer) entity);
			}
			if (entity instanceof LOTREntityBandit) {
				return taskOwner instanceof LOTREntityNPC && ((LOTREntityNPC) taskOwner).hiredNPCInfo.isActive;
			}
			return true;
		}
		return false;
	}

    @Override
    public boolean shouldExecute() {
        if (targetChance > 0 && taskOwner.getRNG().nextInt(targetChance) != 0) {
            return false;
        }

        if (isNPCInactiveOrChild()) {
            return false;
        }

        if (isRideableTamedOrRiddenByPlayer()) {
            return false;
        }

        double range = getTargetDistance();
        double rangeY = Math.min(range, 8.0);
        List<EntityLivingBase> entities = getEntitiesInRange(range, rangeY);

        if (entities.isEmpty()) {
            return false;
        }

        targetEntity = entities.get(0);
        return true;
    }

    private boolean isNPCInactiveOrChild() {
        return taskOwner instanceof LOTREntityNPC &&
            (((LOTREntityNPC) taskOwner).hiredNPCInfo.isActive && ((LOTREntityNPC) taskOwner).hiredNPCInfo.isHalted() ||
                ((LOTREntityNPC) taskOwner).isChild());
    }

    private boolean isRideableTamedOrRiddenByPlayer() {
        return taskOwner instanceof LOTREntityNPCRideable &&
            (((LOTREntityNPCRideable) taskOwner).isNPCTamed() || ((LOTREntityNPCRideable) taskOwner).riddenByEntity instanceof EntityPlayer);
    }


    private List<EntityLivingBase> getEntitiesInRange(double range, double rangeY) {
        AxisAlignedBB boundingBox = taskOwner.boundingBox.expand(range, rangeY, range);
        return (List<EntityLivingBase>) taskOwner.worldObj.selectEntitiesWithinAABB(targetClass, boundingBox, targetSelector).stream()
            .filter(EntityLivingBase.class::isInstance)
            .map(EntityLivingBase.class::cast)
            .sorted(targetSorter)
            .collect(Collectors.toList());
    }

	@Override
	public void startExecuting() {
		taskOwner.setAttackTarget(targetEntity);
		super.startExecuting();
	}

	public static class TargetSorter implements Comparator<Entity> {
		public EntityLivingBase theNPC;

		public TargetSorter(EntityLivingBase entity) {
			theNPC = entity;
		}

		@Override
		public int compare(Entity e1, Entity e2) {
			double d2;
			double d1 = distanceMetricSq(e1);
			d2 = distanceMetricSq(e2);
			return Double.compare(d1, d2);
		}

		public double distanceMetricSq(Entity target) {
			double dSq = theNPC.getDistanceSqToEntity(target);
			double avg = 12.0;
			double avgSq = avg * avg;
			dSq /= avgSq;
			int dupes = 0;
			double nearRange = 8.0;
			List nearbyEntities = theNPC.worldObj.getEntitiesWithinAABB(LOTREntityNPC.class, theNPC.boundingBox.expand(nearRange, nearRange, nearRange));
			for (Object obj : nearbyEntities) {
				LOTREntityNPC nearby = (LOTREntityNPC) obj;
				if (nearby == theNPC || !nearby.isEntityAlive() || nearby.getAttackTarget() != target) {
					continue;
				}
				++dupes;
			}
			int dupesSq = dupes * dupes;
			return dSq + dupesSq;
		}
	}

}
