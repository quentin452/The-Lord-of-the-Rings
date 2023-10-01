package lotr.common.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTREntityMarshWraithBall extends EntityThrowable {
	public int animationTick;
	public Entity attackTarget;

	public LOTREntityMarshWraithBall(World world) {
		super(world);
		setSize(0.75f, 0.75f);
	}

	public LOTREntityMarshWraithBall(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
		setSize(0.75f, 0.75f);
	}

	public LOTREntityMarshWraithBall(World world, EntityLivingBase entityliving) {
		super(world, entityliving);
		setSize(0.75f, 0.75f);
	}

	public LOTREntityMarshWraithBall(World world, EntityLivingBase entityliving, EntityLivingBase target) {
		super(world, entityliving);
		setSize(0.75f, 0.75f);
		attackTarget = target;
		posX = entityliving.posX;
		posY = entityliving.boundingBox.minY + entityliving.getEyeHeight() - 0.1;
		posZ = entityliving.posZ;
		setPosition(posX, posY, posZ);
		double d = target.posX - posX;
		double d1 = target.boundingBox.minY + target.height / 2.0f - posY;
		double d2 = target.posZ - posZ;
		double d3 = MathHelper.sqrt_double(d * d + d2 * d2);
		if (d3 >= 1.0E-7) {
			float f2 = (float) (Math.atan2(d2, d) * 180.0 / 3.141592653589793) - 90.0f;
			float f3 = (float) -(Math.atan2(d1, d3) * 180.0 / 3.141592653589793);
			setLocationAndAngles(posX, posY, posZ, f2, f3);
			setThrowableHeading(d, d1, d2, func_70182_d(), 1.0f);
		}
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, (short) 0);
	}

	@Override
	public float func_70182_d() {
		return 0.5f;
	}

	public int getBallAge() {
		return dataWatcher.getWatchableObjectShort(16);
	}

	public void setBallAge(int age) {
		dataWatcher.updateObject(16, (short) age);
	}

	@Override
	public float getGravityVelocity() {
		return 0.0f;
	}

	@Override
	public void onImpact(MovingObjectPosition m) {
		if (m.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
			if (!worldObj.isRemote) {
				setDead();
			}
		} else if (m.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
			Entity entity = m.entityHit;
			if (attackTarget != null && entity == attackTarget) {
				if (entity.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 5.0f) && entity instanceof EntityLivingBase) {
					int duration = 5;
					((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, duration * 20, 0));
				}
				if (!worldObj.isRemote) {
					setDead();
				}
			}
		}
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (ticksExisted % 2 == 0) {
			++animationTick;
			if (animationTick >= 16) {
				animationTick = 0;
			}
		}
		if (!worldObj.isRemote) {
			setBallAge(getBallAge() + 1);
			if (getBallAge() >= 200) {
				setDead();
			}
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setBallAge(nbt.getInteger("BallAge"));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("BallAge", getBallAge());
	}
}
