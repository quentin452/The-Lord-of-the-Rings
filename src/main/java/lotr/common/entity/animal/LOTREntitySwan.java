package lotr.common.entity.animal;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.npc.LOTREntityBalrog;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntitySwan extends EntityCreature implements LOTRAmbientCreature {
	public static Random violenceRand = new Random();
	public static boolean wreckBalrogs;
	public float flapPhase;
	public float flapPower;
	public float prevFlapPower;
	public float prevFlapPhase;
	public float flapAccel = 1.0f;
	public int peckTime;
	public int peckLength;
	public int timeUntilHiss;
	public boolean assignedAttackOrFlee;
	public EntityAIBase attackAI = new LOTREntityAIAttackOnCollide(this, 1.4, true);
	public EntityAIBase fleeAI = new EntityAIPanic(this, 1.8);
	@SuppressWarnings("Convert2Lambda")
	public IEntitySelector swanAttackRange = new IEntitySelector() {

		@Override
		public boolean isEntityApplicable(Entity entity) {
			return entity instanceof EntityLivingBase && entity.isEntityAlive() && getDistanceSqToEntity(entity) < 16.0;
		}
	};

	public LOTREntitySwan(World world) {
		super(world);
		setSize(0.5f, 0.7f);
		getNavigator().setAvoidsWater(false);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, attackAI);
		tasks.addTask(2, new EntityAIWander(this, 1.0));
		tasks.addTask(3, new EntityAIWatchClosest(this, EntityLivingBase.class, 10.0f, 0.05f));
		tasks.addTask(4, new EntityAILookIdle(this));
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
		if (wreckBalrogs) {
			targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, LOTREntityBalrog.class, 0, true));
		}
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true, false, swanAttackRange));
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
		getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		float f = (float) getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
		if (wreckBalrogs && entity instanceof LOTREntityBalrog) {
			f *= 50.0f;
		}
		if (entity.attackEntityFrom(DamageSource.causeMobDamage(this), f)) {
			worldObj.setEntityState(this, (byte) 20);
			if (wreckBalrogs && entity instanceof LOTREntityBalrog) {
				entity.addVelocity(-MathHelper.sin(rotationYaw * 3.1415927f / 180.0f) * 2.0f, 0.2, MathHelper.cos(rotationYaw * 3.1415927f / 180.0f) * 2.0f);
				setFire(0);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		Entity entity = damagesource.getEntity();
		if (wreckBalrogs && entity instanceof LOTREntityBalrog) {
			f /= 20.0f;
		}
		if (super.attackEntityFrom(damagesource, f)) {
			if (wreckBalrogs && entity instanceof LOTREntityBalrog) {
				setFire(0);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean canDespawn() {
		return true;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		int feathers = rand.nextInt(3) + rand.nextInt(1 + i);
		for (int l = 0; l < feathers; ++l) {
			dropItem(LOTRMod.swanFeather, 1);
		}
	}

	@Override
	public void fall(float f) {
	}

	@Override
	public float getBlockPathWeight(int i, int j, int k) {
		return worldObj.getBlock(i, j - 1, k) == worldObj.getBiomeGenForCoords(i, k).topBlock ? 10.0f : worldObj.getLightBrightness(i, j, k) - 0.5f;
	}

	@Override
	public boolean getCanSpawnHere() {
		if (super.getCanSpawnHere()) {
			return LOTRAmbientSpawnChecks.canSpawn(this, 16, 8, 40, 2, Material.water);
		}
		return false;
	}

	@Override
	public int getExperiencePoints(EntityPlayer entityplayer) {
		return 1 + worldObj.rand.nextInt(2);
	}

	public float getPeckAngle(float tick) {
		if (peckLength == 0) {
			return 0.0f;
		}
		float peck = (peckTime + tick) / peckLength;
		float cutoff = 0.2f;
		if (peck < cutoff) {
			return peck / cutoff;
		}
		if (peck < 1.0f - cutoff) {
			return 1.0f;
		}
		return (1.0f - peck) / cutoff;
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleHealthUpdate(byte b) {
		if (b == 20) {
			peckLength = 10;
		} else if (b == 21) {
			peckLength = 40;
		} else {
			super.handleHealthUpdate(b);
		}
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	public boolean isViolentSwan() {
		long seed = getUniqueID().getLeastSignificantBits();
		violenceRand.setSeed(seed);
		return violenceRand.nextBoolean();
	}

	@Override
	public void onLivingUpdate() {
		EntityLivingBase target;
		super.onLivingUpdate();
		prevFlapPhase = flapPhase;
		prevFlapPower = flapPower;
		flapPower += onGround ? -0.02f : 0.05f;
		flapPower = Math.max(0.0f, Math.min(flapPower, 1.0f));
		if (!onGround) {
			flapAccel = 0.6f;
		}
		flapPhase += flapAccel;
		flapAccel *= 0.95f;
		if (!onGround && motionY < 0.0) {
			motionY *= 0.6;
		}
		if (inWater && motionY < 0.0) {
			motionY *= 0.01;
		}
		if (!worldObj.isRemote && getAttackTarget() != null && (!(target = getAttackTarget()).isEntityAlive() || target instanceof EntityPlayer && ((EntityPlayer) target).capabilities.isCreativeMode)) {
			setAttackTarget(null);
		}
		if (peckLength > 0) {
			++peckTime;
			if (peckTime >= peckLength) {
				peckTime = 0;
				peckLength = 0;
			}
		} else {
			peckTime = 0;
		}
	}

	@Override
	public void updateAITasks() {
		if (!assignedAttackOrFlee) {
			tasks.removeTask(attackAI);
			tasks.removeTask(fleeAI);
			boolean violent = isViolentSwan();
			if (violent) {
				tasks.addTask(1, attackAI);
			} else {
				tasks.addTask(1, fleeAI);
			}
			assignedAttackOrFlee = true;
		}
		super.updateAITasks();
		if (timeUntilHiss <= 0) {
			List nearbyPlayers;
			double range;
			if (getAttackTarget() == null && rand.nextInt(3) == 0 && !(nearbyPlayers = worldObj.selectEntitiesWithinAABB(EntityPlayer.class, boundingBox.expand(range = 8.0, range, range), LOTRMod.selectNonCreativePlayers())).isEmpty()) {
				EntityPlayer entityplayer = (EntityPlayer) nearbyPlayers.get(rand.nextInt(nearbyPlayers.size()));
				getNavigator().clearPathEntity();
				float hissLook = (float) Math.toDegrees(Math.atan2(entityplayer.posZ - posZ, entityplayer.posX - posX));
				rotationYaw = rotationYawHead = hissLook - 90.0f;
				worldObj.setEntityState(this, (byte) 21);
				playSound("lotr:swan.hiss", getSoundVolume(), getSoundPitch());
				timeUntilHiss = 80 + rand.nextInt(80);
			}
		} else {
			--timeUntilHiss;
		}
	}

}
