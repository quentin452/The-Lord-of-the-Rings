package lotr.common.entity.projectile;

import lotr.common.LOTRMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityPebble extends EntityThrowable {
	public boolean isSling;

	public LOTREntityPebble(World world) {
		super(world);
	}

	public LOTREntityPebble(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
	}

	public LOTREntityPebble(World world, EntityLivingBase entityliving) {
		super(world, entityliving);
	}

	@Override
	public float func_70182_d() {
		return 1.0f;
	}

	@Override
	public float getGravityVelocity() {
		return 0.04f;
	}

	public boolean isSling() {
		return isSling;
	}

	@Override
	public void onImpact(MovingObjectPosition m) {
		if (m.entityHit != null) {
			float damage = isSling ? 2.0f : 1.0f;
			m.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), damage);
		}
		if (!worldObj.isRemote) {
			entityDropItem(new ItemStack(LOTRMod.pebble), 0.0f);
			setDead();
		}
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		float speed = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
		if (speed > 0.1f && motionY < 0.0 && isInWater()) {
			float factor = MathHelper.randomFloatClamp(rand, 0.4f, 0.8f);
			motionX *= factor;
			motionZ *= factor;
			motionY += factor;
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		isSling = nbt.getBoolean("Sling");
	}

	public LOTREntityPebble setSling() {
		isSling = true;
		return this;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setBoolean("Sling", isSling);
	}
}
