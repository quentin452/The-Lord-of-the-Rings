package lotr.common.entity.animal;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.LOTREntityUtils;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class LOTREntityAurochs extends EntityCow implements LOTRRandomSkinEntity {
	public EntityAIBase attackAI;
	public EntityAIBase panicAI;
	public boolean prevIsChild = true;
	public float aurochsWidth;
	public float aurochsHeight;

	public LOTREntityAurochs(World world) {
		super(world);
		aurochsWidth = 1.5f;
		aurochsHeight = 1.7f;
		setSize(aurochsWidth, aurochsHeight);
		EntityAITasks.EntityAITaskEntry panic = LOTREntityUtils.removeAITask(this, EntityAIPanic.class);
		tasks.addTask(panic.priority, panic.action);
		panicAI = panic.action;
		attackAI = createAurochsAttackAI();
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
		getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		float f = (float) getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
		boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), f);
		if (flag) {
			float kb = 0.75f;
			entity.addVelocity(-MathHelper.sin(rotationYaw * 3.1415927f / 180.0f) * kb * 0.5f, 0.0, MathHelper.cos(rotationYaw * 3.1415927f / 180.0f) * kb * 0.5f);
		}
		return flag;
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		Entity attacker;
		boolean flag = super.attackEntityFrom(damagesource, f);
		if (flag && isChild() && (attacker = damagesource.getEntity()) instanceof EntityLivingBase) {
			List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(12.0, 12.0, 12.0));
			for (Object element : list) {
				LOTREntityAurochs aurochs;
				Entity entity = (Entity) element;
				if (entity.getClass() != getClass() || (aurochs = (LOTREntityAurochs) entity).isChild()) {
					continue;
				}
				aurochs.setAttackTarget((EntityLivingBase) attacker);
			}
		}
		return flag;
	}

	public EntityAIBase createAurochsAttackAI() {
		return new LOTREntityAIAttackOnCollide(this, 1.7, true);
	}

	@Override
	public EntityCow createChild(EntityAgeable entity) {
		return new LOTREntityAurochs(worldObj);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		int hides = 2 + rand.nextInt(3) + rand.nextInt(1 + i);
		for (int l = 0; l < hides; ++l) {
			dropItem(Items.leather, 1);
		}
		int meats = 2 + rand.nextInt(3) + rand.nextInt(1 + i);
		for (int l = 0; l < meats; ++l) {
			if (isBurning()) {
				dropItem(Items.cooked_beef, 1);
				continue;
			}
			dropItem(Items.beef, 1);
		}
		dropHornItem(flag, i);
	}

	public void dropHornItem(boolean flag, int i) {
		dropItem(LOTRMod.horn, 1);
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(20, (byte) 0);
	}

	@Override
	public String getDeathSound() {
		return "lotr:aurochs.hurt";
	}

	@Override
	public String getHurtSound() {
		return "lotr:aurochs.hurt";
	}

	@Override
	public String getLivingSound() {
		return "lotr:aurochs.say";
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
	}

	@Override
	public float getSoundPitch() {
		return super.getSoundPitch() * 0.75f;
	}

	@Override
	public float getSoundVolume() {
		return 1.0f;
	}

	@Override
	public int getTalkInterval() {
		return 200;
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		if (isAurochsEnraged()) {
			return false;
		}
		return super.interact(entityplayer);
	}

	public boolean isAurochsEnraged() {
		return dataWatcher.getWatchableObjectByte(20) == 1;
	}

	public void setAurochsEnraged(boolean flag) {
		dataWatcher.updateObject(20, flag ? (byte) 1 : 0);
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!worldObj.isRemote) {
			EntityLivingBase target;
			boolean isChild = isChild();
			if (isChild != prevIsChild) {
				EntityAITasks.EntityAITaskEntry taskEntry;
				if (isChild) {
					taskEntry = LOTREntityUtils.removeAITask(this, attackAI.getClass());
					tasks.addTask(taskEntry.priority, panicAI);
				} else {
					taskEntry = LOTREntityUtils.removeAITask(this, panicAI.getClass());
					tasks.addTask(taskEntry.priority, attackAI);
				}
			}
			if (getAttackTarget() != null && (!(target = getAttackTarget()).isEntityAlive() || target instanceof EntityPlayer && ((EntityPlayer) target).capabilities.isCreativeMode)) {
				setAttackTarget(null);
			}
			if (riddenByEntity instanceof EntityLiving) {
				target = ((EntityLiving) riddenByEntity).getAttackTarget();
				setAttackTarget(target);
			} else if (riddenByEntity instanceof EntityPlayer) {
				setAttackTarget(null);
			}
			setAurochsEnraged(getAttackTarget() != null);
		}
		prevIsChild = isChild();
	}

	@Override
	public void setUniqueID(UUID uuid) {
		entityUniqueID = uuid;
	}
}
