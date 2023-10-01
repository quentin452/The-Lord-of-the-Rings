package lotr.common.entity.animal;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.world.biome.LOTRBiomeGenFarHaradSwamp;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.List;

public class LOTREntityCrocodile extends EntityMob {
	public EntityAIBase targetAI;
	public boolean prevCanTarget = true;

	public LOTREntityCrocodile(World world) {
		super(world);
		setSize(2.1f, 0.7f);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new LOTREntityAIAttackOnCollide(this, 1.5, false));
		tasks.addTask(2, new EntityAIWander(this, 1.0));
		tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
		tasks.addTask(3, new EntityAIWatchClosest(this, EntityLiving.class, 12.0f));
		tasks.addTask(4, new EntityAILookIdle(this));
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
		targetAI = new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true);
		targetTasks.addTask(1, targetAI);
		targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, LOTREntityNPC.class, 400, true));
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		boolean flag = super.attackEntityAsMob(entity);
		if (flag) {
			if (!worldObj.isRemote) {
				setSnapTime(20);
			}
			worldObj.playSoundAtEntity(this, "lotr:crocodile.snap", getSoundVolume(), getSoundPitch());
		}
		return flag;
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int count = rand.nextInt(3) + rand.nextInt(i + 1);
		for (int j = 0; j < count; ++j) {
			int drop = rand.nextInt(5);
			switch (drop) {
				case 0: {
					dropItem(Items.bone, 1);
					continue;
				}
				case 1: {
					dropItem(Items.fish, 1);
					continue;
				}
				case 2: {
					dropItem(Items.leather, 1);
					continue;
				}
				case 3: {
					dropItem(LOTRMod.zebraRaw, 1);
					continue;
				}
				case 4: {
					dropItem(LOTRMod.gemsbokHide, 1);
				}
			}
		}
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(20, 0);
	}

	@Override
	public boolean getCanSpawnHere() {
		List nearbyCrocodiles = worldObj.getEntitiesWithinAABB(getClass(), boundingBox.expand(24.0, 12.0, 24.0));
		if (nearbyCrocodiles.size() > 3) {
			return false;
		}
		if (worldObj.checkNoEntityCollision(boundingBox) && isValidLightLevel() && worldObj.getCollidingBoundingBoxes(this, boundingBox).isEmpty()) {
			for (int i = -8; i <= 8; ++i) {
				for (int j = -8; j <= 8; ++j) {
					for (int k = -8; k <= 8; ++k) {
						int j1;
						int k1;
						int i1 = MathHelper.floor_double(posX) + i;
						if (!worldObj.blockExists(i1, j1 = MathHelper.floor_double(posY) + j, k1 = MathHelper.floor_double(posZ) + k) || worldObj.getBlock(i1, j1, k1).getMaterial() != Material.water) {
							continue;
						}
						if (posY > 60.0) {
							return true;
						}
						if (rand.nextInt(50) != 0) {
							continue;
						}
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public String getDeathSound() {
		return "lotr:crocodile.death";
	}

	@Override
	public Item getDropItem() {
		return Items.rotten_flesh;
	}

	@Override
	public String getLivingSound() {
		return "lotr:crocodile.say";
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
	}

	public int getSnapTime() {
		return dataWatcher.getWatchableObjectInt(20);
	}

	public void setSnapTime(int i) {
		dataWatcher.updateObject(20, i);
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	@Override
	public boolean isValidLightLevel() {
		int i = MathHelper.floor_double(posX);
		if (worldObj.getBiomeGenForCoords(i, MathHelper.floor_double(posZ)) instanceof LOTRBiomeGenFarHaradSwamp) {
			return true;
		}
		return super.isValidLightLevel();
	}

	@Override
	public void moveEntityWithHeading(float f, float f1) {
		if (!worldObj.isRemote && isInWater() && getAttackTarget() != null) {
			moveFlying(f, f1, 0.1f);
		}
		super.moveEntityWithHeading(f, f1);
	}

	@Override
	public void onLivingUpdate() {
		EntityAnimal entityanimal;
		int i;
		List list;
		EntityLivingBase entity;
		super.onLivingUpdate();
		if (!worldObj.isRemote && isInWater()) {
			motionY += 0.02;
		}
		if (!worldObj.isRemote && getAttackTarget() != null && (!(entity = getAttackTarget()).isEntityAlive() || entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode)) {
			setAttackTarget(null);
		}
		if (!worldObj.isRemote) {
			boolean canTarget;
			canTarget = getBrightness(1.0f) < 0.5f;
			if (canTarget != prevCanTarget) {
				if (canTarget) {
					targetTasks.addTask(1, targetAI);
				} else {
					targetTasks.removeTask(targetAI);
				}
			}
			prevCanTarget = canTarget;
		}
		if (!worldObj.isRemote && (i = getSnapTime()) > 0) {
			setSnapTime(i - 1);
		}
		if (getAttackTarget() == null && worldObj.rand.nextInt(1000) == 0 && !(list = worldObj.getEntitiesWithinAABB(EntityAnimal.class, boundingBox.expand(12.0, 6.0, 12.0))).isEmpty() && (entityanimal = (EntityAnimal) list.get(rand.nextInt(list.size()))).getAttributeMap().getAttributeInstance(SharedMonsterAttributes.attackDamage) == null) {
			setAttackTarget(entityanimal);
		}
	}
}
