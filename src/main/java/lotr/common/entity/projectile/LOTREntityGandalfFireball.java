package lotr.common.entity.projectile;

import java.util.List;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.*;
import lotr.common.fac.LOTRFaction;
import lotr.common.network.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityGandalfFireball extends EntityThrowable {
	public int animationTick;

	public LOTREntityGandalfFireball(World world) {
		super(world);
	}

	public LOTREntityGandalfFireball(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
	}

	public LOTREntityGandalfFireball(World world, EntityLivingBase entityliving) {
		super(world, entityliving);
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, (short) 0);
	}

	public void explode(Entity target) {
		List entities;
		if (worldObj.isRemote) {
			return;
		}
		worldObj.playSoundAtEntity(this, "lotr:item.gandalfFireball", 4.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f);
		IMessage packet = new LOTRPacketWeaponFX(LOTRPacketWeaponFX.Type.FIREBALL_GANDALF_WHITE, this);
		LOTRPacketHandler.networkWrapper.sendToAllAround(packet, LOTRPacketHandler.nearEntity(this, 64.0));
		if (target != null && isEntityVulnerable(target)) {
			target.attackEntityFrom(DamageSource.causeMobDamage(getThrower()), 10.0f);
		}
		if (!(entities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, boundingBox.expand(6.0, 6.0, 6.0))).isEmpty()) {
			for (Object entitie : entities) {
				float damage;
				EntityLivingBase entity = (EntityLivingBase) entitie;
				if (entity == target || !isEntityVulnerable(entity) || (damage = 10.0f - getDistanceToEntity(entity) * 0.5f) <= 0.0f) {
					continue;
				}
				entity.attackEntityFrom(DamageSource.causeMobDamage(getThrower()), damage);
			}
		}
		setDead();
	}

	@Override
	public float func_70182_d() {
		return 1.5f;
	}

	public int getFireballAge() {
		return dataWatcher.getWatchableObjectShort(16);
	}

	@Override
	public float getGravityVelocity() {
		return 0.0f;
	}

	public boolean isEntityVulnerable(Entity entity) {
		if (entity == getThrower() || !(entity instanceof EntityLivingBase)) {
			return false;
		}
		if (entity instanceof EntityPlayer) {
			return LOTRLevelData.getData((EntityPlayer) entity).getAlignment(LOTRFaction.HIGH_ELF) < 0.0f;
		}
		return !LOTRFaction.HIGH_ELF.isGoodRelation(LOTRMod.getNPCFaction(entity));
	}

	@Override
	public void onImpact(MovingObjectPosition m) {
		if (!worldObj.isRemote) {
			Entity entity;
			if (m.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
				explode(null);
			} else if (m.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && isEntityVulnerable(entity = m.entityHit)) {
				explode(entity);
			}
		}
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (ticksExisted % 5 == 0) {
			++animationTick;
			if (animationTick >= 4) {
				animationTick = 0;
			}
		}
		if (!worldObj.isRemote) {
			setFireballAge(getFireballAge() + 1);
			if (getFireballAge() >= 200) {
				explode(null);
			}
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setFireballAge(nbt.getInteger("FireballAge"));
	}

	public void setFireballAge(int age) {
		dataWatcher.updateObject(16, (short) age);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("FireballAge", getFireballAge());
	}
}
