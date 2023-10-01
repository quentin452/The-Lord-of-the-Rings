package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.enchant.LOTREnchantment;
import lotr.common.enchant.LOTREnchantmentHelper;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import lotr.common.entity.projectile.LOTREntityMarshWraithBall;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.UUID;

public class LOTREntityMarshWraith extends LOTREntityNPC {
	public UUID attackTargetUUID;
	public boolean checkedForAttackTarget;
	public int timeUntilDespawn = -1;

	public LOTREntityMarshWraith(World world) {
		super(world);
		setSize(0.6f, 1.8f);
		tasks.addTask(0, new LOTREntityAIRangedAttack(this, 1.6, 40, 12.0f));
		tasks.addTask(1, new EntityAIWander(this, 1.0));
		tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(3, new EntityAILookIdle(this));
		ignoreFrustumCheck = true;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		ItemStack itemstack;
		boolean vulnerable = false;
		Entity entity = damagesource.getEntity();
		if (entity instanceof EntityLivingBase && entity == damagesource.getSourceOfDamage() && (itemstack = ((EntityLivingBase) entity).getHeldItem()) != null && LOTREnchantmentHelper.hasEnchant(itemstack, LOTREnchantment.baneWraith)) {
			vulnerable = true;
		}
		if (vulnerable && getDeathFadeTime() == 0) {
			boolean flag = super.attackEntityFrom(damagesource, f);
			if (flag) {
				timeUntilDespawn = 100;
			}
			return flag;
		}
		return false;
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float f) {
		if (getSpawnFadeTime() == 30 && getDeathFadeTime() == 0) {
			LOTREntityMarshWraithBall ball = new LOTREntityMarshWraithBall(worldObj, this, target);
			playSound("lotr:wraith.marshWraith_shoot", 1.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.8f));
			worldObj.spawnEntityInWorld(ball);
		}
	}

	@Override
	public boolean canReEquipHired(int slot, ItemStack itemstack) {
		return false;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int flesh = 1 + rand.nextInt(3) + rand.nextInt(i + 1);
		for (int l = 0; l < flesh; ++l) {
			dropItem(Items.rotten_flesh, 1);
		}
		dropChestContents(LOTRChestContents.MARSH_REMAINS, 1, 3 + i);
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, 0);
		dataWatcher.addObject(17, 0);
	}

	@Override
	public void func_145780_a(int i, int j, int k, Block block) {
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEAD;
	}

	public int getDeathFadeTime() {
		return dataWatcher.getWatchableObjectInt(17);
	}

	public void setDeathFadeTime(int i) {
		dataWatcher.updateObject(17, i);
	}

	@Override
	public String getDeathSound() {
		return "lotr:wight.death";
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.HOSTILE;
	}

	@Override
	public String getHurtSound() {
		return "lotr:wight.hurt";
	}

	@Override
	public LOTRAchievement getKillAchievement() {
		return LOTRAchievement.killMarshWraith;
	}

	public int getSpawnFadeTime() {
		return dataWatcher.getWatchableObjectInt(16);
	}

	public void setSpawnFadeTime(int i) {
		dataWatcher.updateObject(16, i);
	}

	@Override
	public boolean handleWaterMovement() {
		return false;
	}

	@Override
	public void onDeath(DamageSource damagesource) {
		super.onDeath(damagesource);
		if (!worldObj.isRemote) {
			setDeathFadeTime(30);
		}
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!worldObj.isRemote && !isDead) {
			int hover = 2;
			int i = MathHelper.floor_double(posX);
			int j = MathHelper.floor_double(posY);
			int k = MathHelper.floor_double(posZ);
			double newY = posY;
			for (int j1 = 0; j1 <= hover; ++j1) {
				int j2 = j - j1;
				Block block = worldObj.getBlock(i, j2, k);
				Material material = block.getMaterial();
				if (!material.isSolid() && !material.isLiquid()) {
					continue;
				}
				newY = Math.max(newY, j + j1 + 1);
			}
			motionY += (newY - posY) * 0.04;
		}
		if (rand.nextBoolean()) {
			worldObj.spawnParticle("smoke", posX + (rand.nextDouble() - 0.5) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5) * width, 0.0, 0.0, 0.0);
		}
		if (!worldObj.isRemote) {
			int i;
			if (getAttackTarget() == null && attackTargetUUID != null && !checkedForAttackTarget) {
				for (i = 0; i < worldObj.loadedEntityList.size(); ++i) {
					Entity entity = (Entity) worldObj.loadedEntityList.get(i);
					if (!(entity instanceof EntityLiving) || !entity.getUniqueID().equals(attackTargetUUID)) {
						continue;
					}
					setAttackTarget((EntityLivingBase) entity);
					break;
				}
				checkedForAttackTarget = true;
			}
			if (getSpawnFadeTime() < 30) {
				setSpawnFadeTime(getSpawnFadeTime() + 1);
			}
			if (getDeathFadeTime() > 0) {
				setDeathFadeTime(getDeathFadeTime() - 1);
			}
			if (getSpawnFadeTime() == 30 && getDeathFadeTime() == 0) {
				if (getAttackTarget() == null || getAttackTarget().isDead) {
					setDeathFadeTime(30);
				} else {
					int k;
					int j;
					if (timeUntilDespawn == -1) {
						timeUntilDespawn = 100;
					}
					if (worldObj.getBlock(i = MathHelper.floor_double(getAttackTarget().posX), j = MathHelper.floor_double(getAttackTarget().boundingBox.minY), k = MathHelper.floor_double(getAttackTarget().posZ)).getMaterial() == Material.water || worldObj.getBlock(i, j - 1, k).getMaterial() == Material.water) {
						timeUntilDespawn = 100;
					} else if (timeUntilDespawn > 0) {
						--timeUntilDespawn;
					} else {
						setDeathFadeTime(30);
						setAttackTarget(null);
					}
				}
			}
			if (getDeathFadeTime() == 1) {
				setDead();
			}
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setSpawnFadeTime(nbt.getInteger("SpawnFadeTime"));
		setDeathFadeTime(nbt.getInteger("DeathFadeTime"));
		if (nbt.hasKey("TargetUUIDMost") && nbt.hasKey("TargetUUIDLeast")) {
			attackTargetUUID = new UUID(nbt.getLong("TargetUUIDMost"), nbt.getLong("TargetUUIDLeast"));
		}
	}

	@Override
	public void setInWeb() {
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("SpawnFadeTime", getSpawnFadeTime());
		nbt.setInteger("DeathFadeTime", getDeathFadeTime());
		if (attackTargetUUID != null) {
			nbt.setLong("TargetUUIDMost", attackTargetUUID.getMostSignificantBits());
			nbt.setLong("TargetUUIDLeast", attackTargetUUID.getLeastSignificantBits());
		}
	}
}
