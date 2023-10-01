package lotr.common.entity.projectile;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class LOTREntityMallornLeafBomb extends EntityThrowable {
	public static int MAX_LEAVES_AGE = 200;
	public UUID throwerUUID;
	public int leavesAge;
	public float leavesDamage;

	public LOTREntityMallornLeafBomb(World world) {
		super(world);
		setSize(2.0f, 2.0f);
		setPosition(posX, posY, posZ);
	}

	public LOTREntityMallornLeafBomb(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
		setSize(2.0f, 2.0f);
		setPosition(posX, posY, posZ);
	}

	public LOTREntityMallornLeafBomb(World world, EntityLivingBase thrower) {
		super(world, thrower);
		setSize(2.0f, 2.0f);
		setPosition(posX, posY, posZ);
		throwerUUID = thrower.getUniqueID();
	}

	public LOTREntityMallornLeafBomb(World world, EntityLivingBase thrower, EntityLivingBase target) {
		super(world, thrower);
		setSize(2.0f, 2.0f);
		setPosition(posX, posY, posZ);
		throwerUUID = thrower.getUniqueID();
		posY = thrower.posY + thrower.getEyeHeight() - 0.1;
		double dx = target.posX - thrower.posX;
		double dy = target.boundingBox.minY + target.height / 3.0f - posY;
		double dz = target.posZ - thrower.posZ;
		double dxz = MathHelper.sqrt_double(dx * dx + dz * dz);
		if (dxz >= 1.0E-7) {
			float f2 = (float) (Math.atan2(dz, dx) * 180.0 / 3.141592653589793) - 90.0f;
			float f3 = (float) -(Math.atan2(dy, dxz) * 180.0 / 3.141592653589793);
			double d4 = dx / dxz;
			double d5 = dz / dxz;
			setLocationAndAngles(thrower.posX + d4, posY, thrower.posZ + d5, f2, f3);
			yOffset = 0.0f;
			setThrowableHeading(dx, dy, dz, func_70182_d(), 1.0f);
		}
	}

	public void explode(Entity target) {
		if (!worldObj.isRemote) {
			double range = 2.0;
			List entities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, boundingBox.expand(range, range, range));
			if (!entities.isEmpty()) {
				for (Object entitie : entities) {
					float damage;
					EntityLivingBase entity = (EntityLivingBase) entitie;
					if (!isEntityVulnerable(entity) || (damage = leavesDamage / Math.max(1.0f, getDistanceToEntity(entity))) <= 0.0f) {
						continue;
					}
					entity.attackEntityFrom(DamageSource.causeMobDamage(getThrower()), damage);
				}
			}
			setDead();
		}
	}

	@Override
	public float func_70182_d() {
		return 1.0f;
	}

	@Override
	public float getGravityVelocity() {
		return 0.0f;
	}

	@Override
	public EntityLivingBase getThrower() {
		if (throwerUUID != null) {
			for (Object obj : worldObj.loadedEntityList) {
				Entity entity = (Entity) obj;
				if (!(entity instanceof EntityLivingBase) || !entity.getUniqueID().equals(throwerUUID)) {
					continue;
				}
				return (EntityLivingBase) entity;
			}
		}
		return null;
	}

	public boolean isEntityVulnerable(Entity target) {
		if (target == getThrower()) {
			return false;
		}
		if (target instanceof EntityLivingBase) {
			EntityLivingBase livingTarget = (EntityLivingBase) target;
			EntityLivingBase thrower = getThrower();
			if (thrower instanceof LOTREntityNPC) {
				((EntityLiving) thrower).getJumpHelper().doJump();
				return LOTRMod.canNPCAttackEntity((EntityCreature) thrower, livingTarget, false);
			}
			return true;
		}
		return false;
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
		if (worldObj.isRemote) {
			Vec3 axis = Vec3.createVectorHelper(-motionX, -motionY, -motionZ);
			int leaves = 20;
			for (int l = 0; l < leaves; ++l) {
				float angle = (float) l / leaves * 2.0f * 3.1415927f;
				Vec3 rotate = Vec3.createVectorHelper(1.0, 1.0, 1.0);
				rotate.rotateAroundX(0.6981317007977318f);
				rotate.rotateAroundY(angle);
				float dot = (float) rotate.dotProduct(axis);
				Vec3 parallel = Vec3.createVectorHelper(axis.xCoord * dot, axis.yCoord * dot, axis.zCoord * dot);
				Vec3 perp = parallel.subtract(rotate);
				Vec3 cross = rotate.crossProduct(axis);
				float sin = MathHelper.sin(-angle);
				float cos = MathHelper.cos(-angle);
				Vec3 crossSin = Vec3.createVectorHelper(cross.xCoord * sin, cross.yCoord * sin, cross.zCoord * sin);
				Vec3 perpCos = Vec3.createVectorHelper(perp.xCoord * cos, perp.yCoord * cos, perp.zCoord * cos);
				Vec3 result = parallel.addVector(crossSin.xCoord + perpCos.xCoord, crossSin.yCoord + perpCos.yCoord, crossSin.zCoord + perpCos.zCoord);
				double d = posX;
				double d1 = posY;
				double d2 = posZ;
				double d3 = result.xCoord / 10.0;
				double d4 = result.yCoord / 10.0;
				double d5 = result.zCoord / 10.0;
				LOTRMod.proxy.spawnParticle("leafGold_30", d, d1, d2, d3, d4, d5);
				LOTRMod.proxy.spawnParticle("mEntHeal_" + Block.getIdFromBlock(LOTRMod.leaves) + "_" + 1, d, d1, d2, d3 * 0.5, d4 * 0.5, d5 * 0.5);
			}
		} else {
			++leavesAge;
			if (leavesAge >= MAX_LEAVES_AGE) {
				explode(null);
			}
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		leavesAge = nbt.getInteger("LeavesAge");
		leavesDamage = nbt.getFloat("LeavesDamage");
		if (nbt.hasKey("ThrowerUUID")) {
			throwerUUID = UUID.fromString(nbt.getString("ThrowerUUID"));
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("LeavesAge", leavesAge);
		nbt.setFloat("LeavesDamage", leavesDamage);
		if (throwerUUID != null) {
			nbt.setString("ThrowerUUID", throwerUUID.toString());
		}
	}
}
