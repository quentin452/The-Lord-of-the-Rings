package lotr.common.entity.projectile;

import cpw.mods.fml.common.registry.IThrowableEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.item.LOTRWeaponStats;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S0DPacketCollectItem;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.List;

public abstract class LOTREntityProjectileBase extends Entity implements IThrowableEntity, IProjectile {
	public int xTile = -1;
	public int yTile = -1;
	public int zTile = -1;
	public Block inTile;
	public int inData;
	public boolean inGround;
	public int shake;
	public Entity shootingEntity;
	public int ticksInGround;
	public int ticksInAir;
	public int canBePickedUp;
	public int knockbackStrength;

	protected LOTREntityProjectileBase(World world) {
		super(world);
		setSize(0.5f, 0.5f);
	}

	protected LOTREntityProjectileBase(World world, EntityLivingBase entityliving, EntityLivingBase target, ItemStack item, float charge, float inaccuracy) {
		super(world);
		setProjectileItem(item);
		shootingEntity = entityliving;
		if (entityliving instanceof EntityPlayer) {
			canBePickedUp = 1;
		}
		setSize(0.5f, 0.5f);
		posY = entityliving.posY + entityliving.getEyeHeight() - 0.1;
		double d = target.posX - entityliving.posX;
		double d1 = target.posY + target.getEyeHeight() - 0.7 - posY;
		double d2 = target.posZ - entityliving.posZ;
		double d3 = MathHelper.sqrt_double(d * d + d2 * d2);
		if (d3 >= 1.0E-7) {
			float f = (float) (Math.atan2(d2, d) * 180.0 / 3.141592653589793) - 90.0f;
			float f1 = (float) -(Math.atan2(d1, d3) * 180.0 / 3.141592653589793);
			double d4 = d / d3;
			double d5 = d2 / d3;
			setLocationAndAngles(entityliving.posX + d4, posY, entityliving.posZ + d5, f, f1);
			yOffset = 0.0f;
			float d6 = (float) d3 * 0.2f;
			setThrowableHeading(d, d1 + d6, d2, charge * 1.5f, inaccuracy);
		}
	}

	protected LOTREntityProjectileBase(World world, EntityLivingBase entityliving, ItemStack item, float charge) {
		super(world);
		setProjectileItem(item);
		shootingEntity = entityliving;
		if (entityliving instanceof EntityPlayer) {
			canBePickedUp = 1;
		}
		setSize(0.5f, 0.5f);
		setLocationAndAngles(entityliving.posX, entityliving.posY + entityliving.getEyeHeight(), entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
		posX -= MathHelper.cos(rotationYaw / 180.0f * 3.1415927f) * 0.16f;
		posY -= 0.1;
		posZ -= MathHelper.sin(rotationYaw / 180.0f * 3.1415927f) * 0.16f;
		setPosition(posX, posY, posZ);
		yOffset = 0.0f;
		motionX = -MathHelper.sin(rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(rotationPitch / 180.0f * 3.1415927f);
		motionZ = MathHelper.cos(rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(rotationPitch / 180.0f * 3.1415927f);
		motionY = -MathHelper.sin(rotationPitch / 180.0f * 3.1415927f);
		setThrowableHeading(motionX, motionY, motionZ, charge * 1.5f, 1.0f);
	}

	protected LOTREntityProjectileBase(World world, ItemStack item, double d, double d1, double d2) {
		super(world);
		setProjectileItem(item);
		setSize(0.5f, 0.5f);
		setPosition(d, d1, d2);
		yOffset = 0.0f;
	}

	@Override
	public boolean canAttackWithItem() {
		return false;
	}

	@Override
	public boolean canTriggerWalking() {
		return false;
	}

	public ItemStack createPickupDrop(EntityPlayer entityplayer) {
		ItemStack itemstack = getProjectileItem();
		if (itemstack != null) {
			ItemStack itemPickup = itemstack.copy();
			if (itemPickup.isItemStackDamageable()) {
				itemPickup.damageItem(1, entityplayer);
				if (itemPickup.getItemDamage() >= itemPickup.getMaxDamage()) {
					return null;
				}
			}
			return itemPickup;
		}
		return null;
	}

	@Override
	public void entityInit() {
		dataWatcher.addObject(17, (byte) 0);
		dataWatcher.addObjectByDataType(18, 5);
	}

	public abstract float getBaseImpactDamage(Entity var1, ItemStack var2);

	public DamageSource getDamageSource() {
		if (shootingEntity == null) {
			return DamageSource.causeThrownDamage(this, this);
		}
		return DamageSource.causeThrownDamage(this, shootingEntity);
	}

	public String getImpactSound() {
		return "random.bowhit";
	}

	public boolean getIsCritical() {
		return dataWatcher.getWatchableObjectByte(17) == 1;
	}

	public void setIsCritical(boolean flag) {
		dataWatcher.updateObject(17, (byte) (flag ? 1 : 0));
	}

	public float getKnockbackFactor() {
		return 1.0f;
	}

	public ItemStack getProjectileItem() {
		return dataWatcher.getWatchableObjectItemStack(18);
	}

	public void setProjectileItem(ItemStack item) {
		dataWatcher.updateObject(18, item);
	}

	@Override
	public float getShadowSize() {
		return 0.0f;
	}

	public float getSpeedReduction() {
		return 0.99f;
	}

	@Override
	public Entity getThrower() {
		return shootingEntity;
	}

	@Override
	public void setThrower(Entity entity) {
		shootingEntity = entity;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean isInRangeToRenderDist(double d) {
		double d1 = boundingBox.getAverageEdgeLength() * 4.0;
		return d < (d1 *= 64.0) * d1;
	}

	public int maxTicksInGround() {
		return canBePickedUp == 1 ? 6000 : 1200;
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer entityplayer) {
		if (!worldObj.isRemote && inGround && shake <= 0) {
			ItemStack itemstack = createPickupDrop(entityplayer);
			if (itemstack != null) {
				boolean pickup;
				pickup = canBePickedUp == 1 || canBePickedUp == 2 && entityplayer.capabilities.isCreativeMode;
				if (canBePickedUp == 1 && !entityplayer.inventory.addItemStackToInventory(itemstack.copy())) {
					pickup = false;
					EntityItem entityitem = new EntityItem(worldObj, posX, posY, posZ, itemstack);
					entityitem.delayBeforeCanPickup = 0;
					worldObj.spawnEntityInWorld(entityitem);
					setDead();
				}
				if (pickup) {
					if (!isDead) {
						EntityTracker entitytracker = ((WorldServer) worldObj).getEntityTracker();
						entitytracker.func_151247_a(this, new S0DPacketCollectItem(getEntityId(), entityplayer.getEntityId()));
					}
					playSound("random.pop", 0.2f, ((rand.nextFloat() - rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
					setDead();
				}
			} else {
				setDead();
			}
		}
	}

	public void onCollideWithTarget(Entity entity) {
		ItemStack itemstack;
		if (!worldObj.isRemote && ((itemstack = getProjectileItem()) == null || !itemstack.isItemStackDamageable())) {
			setDead();
		}
	}

	@Override
	public void onUpdate() {
		Block block;
		super.onUpdate();
		if (prevRotationPitch == 0.0f && prevRotationYaw == 0.0f) {
			float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
			prevRotationYaw = rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0 / 3.141592653589793);
			prevRotationPitch = rotationPitch = (float) (Math.atan2(motionY, f) * 180.0 / 3.141592653589793);
		}
		block = worldObj.getBlock(xTile, yTile, zTile);
		if (block != Blocks.air) {
			block.setBlockBoundsBasedOnState(worldObj, xTile, yTile, zTile);
			AxisAlignedBB axisalignedbb = block.getCollisionBoundingBoxFromPool(worldObj, xTile, yTile, zTile);
			if (axisalignedbb != null && axisalignedbb.isVecInside(Vec3.createVectorHelper(posX, posY, posZ))) {
				inGround = true;
			}
		}
		if (shake > 0) {
			--shake;
		}
		if (inGround) {
			Block j = worldObj.getBlock(xTile, yTile, zTile);
			int k = worldObj.getBlockMetadata(xTile, yTile, zTile);
			if (j == inTile && k == inData) {
				++ticksInGround;
				if (ticksInGround >= maxTicksInGround()) {
					setDead();
				}
			} else {
				inGround = false;
				motionX *= rand.nextFloat() * 0.2f;
				motionY *= rand.nextFloat() * 0.2f;
				motionZ *= rand.nextFloat() * 0.2f;
				ticksInGround = 0;
				ticksInAir = 0;
			}
		} else {
			int l;
			++ticksInAir;
			Vec3 vec3d = Vec3.createVectorHelper(posX, posY, posZ);
			Vec3 vec3d1 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
			MovingObjectPosition movingobjectposition = worldObj.func_147447_a(vec3d, vec3d1, false, true, false);
			vec3d = Vec3.createVectorHelper(posX, posY, posZ);
			vec3d1 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
			if (movingobjectposition != null) {
				vec3d1 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
			}
			Entity entity = null;
			List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0, 1.0, 1.0));
			double d = 0.0;
			for (l = 0; l < list.size(); ++l) {
				float f5;
				double d1;
				MovingObjectPosition movingobjectposition1;
				Entity entity1 = (Entity) list.get(l);
				if (!entity1.canBeCollidedWith() || entity1 == shootingEntity && ticksInAir < 5 || (movingobjectposition1 = entity1.boundingBox.expand(f5 = 0.3f, f5, f5).calculateIntercept(vec3d, vec3d1)) == null || (d1 = vec3d.distanceTo(movingobjectposition1.hitVec)) >= d && d != 0.0) {
					continue;
				}
				entity = entity1;
				d = d1;
			}
			if (entity != null) {
				movingobjectposition = new MovingObjectPosition(entity);
			}
			if (movingobjectposition != null && movingobjectposition.entityHit instanceof EntityPlayer) {
				EntityPlayer entityplayer = (EntityPlayer) movingobjectposition.entityHit;
				if (entityplayer.capabilities.disableDamage || shootingEntity instanceof EntityPlayer && !((EntityPlayer) shootingEntity).canAttackPlayer(entityplayer)) {
					movingobjectposition = null;
				}
			}
			if (movingobjectposition != null) {
				Entity hitEntity = movingobjectposition.entityHit;
				if (hitEntity != null) {
					ItemStack itemstack = getProjectileItem();
					int damageInt = MathHelper.ceiling_double_int(getBaseImpactDamage(hitEntity, itemstack));
					int fireAspect = 0;
					if (itemstack != null) {
						knockbackStrength = shootingEntity instanceof EntityLivingBase && hitEntity instanceof EntityLivingBase ? knockbackStrength + EnchantmentHelper.getKnockbackModifier((EntityLivingBase) shootingEntity, (EntityLivingBase) hitEntity) : knockbackStrength + LOTRWeaponStats.getTotalKnockback(itemstack);
					}
					if (getIsCritical()) {
						damageInt += rand.nextInt(damageInt / 2 + 2);
					}
					double[] prevMotion = {hitEntity.motionX, hitEntity.motionY, hitEntity.motionZ};
					DamageSource damagesource = getDamageSource();
					if (hitEntity.attackEntityFrom(damagesource, damageInt)) {
						double[] newMotion = {hitEntity.motionX, hitEntity.motionY, hitEntity.motionZ};
						float kbf = getKnockbackFactor();
						hitEntity.motionX = prevMotion[0] + (newMotion[0] - prevMotion[0]) * kbf;
						hitEntity.motionY = prevMotion[1] + (newMotion[1] - prevMotion[1]) * kbf;
						hitEntity.motionZ = prevMotion[2] + (newMotion[2] - prevMotion[2]) * kbf;
						if (isBurning()) {
							hitEntity.setFire(5);
						}
						if (hitEntity instanceof EntityLivingBase) {
							float knockback;
							EntityLivingBase hitEntityLiving = (EntityLivingBase) hitEntity;
							if (knockbackStrength > 0 && (knockback = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ)) > 0.0f) {
								hitEntityLiving.addVelocity(motionX * knockbackStrength * 0.6 / knockback, 0.1, motionZ * knockbackStrength * 0.6 / knockback);
							}
							if (shootingEntity instanceof EntityLivingBase) {
								EnchantmentHelper.func_151384_a(hitEntityLiving, shootingEntity);
								EnchantmentHelper.func_151385_b((EntityLivingBase) shootingEntity, hitEntityLiving);
							}
							if (shootingEntity instanceof EntityPlayerMP && hitEntityLiving instanceof EntityPlayer) {
								((EntityPlayerMP) shootingEntity).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0.0f));
							}
						}
						worldObj.playSoundAtEntity(this, getImpactSound(), 1.0f, 1.2f / (rand.nextFloat() * 0.2f + 0.9f));
						onCollideWithTarget(hitEntity);
					} else {
						motionX *= -0.1;
						motionY *= -0.1;
						motionZ *= -0.1;
						rotationYaw += 180.0f;
						prevRotationYaw += 180.0f;
						ticksInAir = 0;
					}
				} else {
					xTile = movingobjectposition.blockX;
					yTile = movingobjectposition.blockY;
					zTile = movingobjectposition.blockZ;
					inTile = worldObj.getBlock(xTile, yTile, zTile);
					inData = worldObj.getBlockMetadata(xTile, yTile, zTile);
					motionX = (float) (movingobjectposition.hitVec.xCoord - posX);
					motionY = (float) (movingobjectposition.hitVec.yCoord - posY);
					motionZ = (float) (movingobjectposition.hitVec.zCoord - posZ);
					float f2 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
					posX -= motionX / f2 * 0.05;
					posY -= motionY / f2 * 0.05;
					posZ -= motionZ / f2 * 0.05;
					worldObj.playSoundAtEntity(this, getImpactSound(), 1.0f, 1.2f / (rand.nextFloat() * 0.2f + 0.9f));
					inGround = true;
					shake = 7;
					setIsCritical(false);
					if (inTile.getMaterial() != Material.air) {
						inTile.onEntityCollidedWithBlock(worldObj, xTile, yTile, zTile, this);
					}
				}
			}
			if (getIsCritical()) {
				for (l = 0; l < 4; ++l) {
					worldObj.spawnParticle("crit", posX + motionX * l / 4.0, posY + motionY * l / 4.0, posZ + motionZ * l / 4.0, -motionX, -motionY + 0.2, -motionZ);
				}
			}
			posX += motionX;
			posY += motionY;
			posZ += motionZ;
			float f3 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
			rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0 / 3.141592653589793);
			rotationPitch = (float) (Math.atan2(motionY, f3) * 180.0 / 3.141592653589793);
			while (rotationPitch - prevRotationPitch < -180.0f) {
				prevRotationPitch -= 360.0f;
			}
			while (rotationPitch - prevRotationPitch >= 180.0f) {
				prevRotationPitch += 360.0f;
			}
			while (rotationYaw - prevRotationYaw < -180.0f) {
				prevRotationYaw -= 360.0f;
			}
			while (rotationYaw - prevRotationYaw >= 180.0f) {
				prevRotationYaw += 360.0f;
			}
			rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2f;
			rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2f;
			float f4 = getSpeedReduction();
			if (isInWater()) {
				for (int k1 = 0; k1 < 4; ++k1) {
					float f7 = 0.25f;
					worldObj.spawnParticle("bubble", posX - motionX * f7, posY - motionY * f7, posZ - motionZ * f7, motionX, motionY, motionZ);
				}
				f4 = 0.8f;
			}
			motionX *= f4;
			motionY *= f4;
			motionZ *= f4;
			motionY -= 0.05000000074505806;
			setPosition(posX, posY, posZ);
			func_145775_I();
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		xTile = nbt.getInteger("xTile");
		yTile = nbt.getInteger("yTile");
		zTile = nbt.getInteger("zTile");
		inTile = Block.getBlockById(nbt.getInteger("inTile"));
		inData = nbt.getByte("inData");
		shake = nbt.getByte("shake");
		inGround = nbt.getByte("inGround") == 1;
		canBePickedUp = nbt.getByte("pickup");
		knockbackStrength = nbt.getByte("Knockback");
		if (nbt.hasKey("itemID")) {
			ItemStack item = new ItemStack(Item.getItemById(nbt.getInteger("itemID")), 1, nbt.getInteger("itemDamage"));
			if (nbt.hasKey("ItemTagCompound")) {
				item.setTagCompound(nbt.getCompoundTag("ItemTagCompound"));
			}
			setProjectileItem(item);
		} else {
			setProjectileItem(ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("ProjectileItem")));
		}
	}

	@Override
	public void setThrowableHeading(double d, double d1, double d2, float f, float f1) {
		float f2 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
		d /= f2;
		d1 /= f2;
		d2 /= f2;
		d += rand.nextGaussian() * 0.0075 * f1;
		d1 += rand.nextGaussian() * 0.0075 * f1;
		d2 += rand.nextGaussian() * 0.0075 * f1;
		motionX = d *= f;
		motionY = d1 *= f;
		motionZ = d2 *= f;
		float f3 = MathHelper.sqrt_double(d * d + d2 * d2);
		prevRotationYaw = rotationYaw = (float) (Math.atan2(d, d2) * 180.0 / 3.141592653589793);
		prevRotationPitch = rotationPitch = (float) (Math.atan2(d1, f3) * 180.0 / 3.141592653589793);
		ticksInGround = 0;
	}

	@Override
	public void setVelocity(double d, double d1, double d2) {
		motionX = d;
		motionY = d1;
		motionZ = d2;
		if (prevRotationPitch == 0.0f && prevRotationYaw == 0.0f) {
			float f = MathHelper.sqrt_double(d * d + d2 * d2);
			prevRotationYaw = rotationYaw = (float) (Math.atan2(d, d2) * 180.0 / 3.141592653589793);
			prevRotationPitch = rotationPitch = (float) (Math.atan2(d1, f) * 180.0 / 3.141592653589793);
			setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
			ticksInGround = 0;
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("xTile", xTile);
		nbt.setInteger("yTile", yTile);
		nbt.setInteger("zTile", zTile);
		nbt.setInteger("inTile", Block.getIdFromBlock(inTile));
		nbt.setByte("inData", (byte) inData);
		nbt.setByte("shake", (byte) shake);
		nbt.setByte("inGround", (byte) (inGround ? 1 : 0));
		nbt.setByte("pickup", (byte) canBePickedUp);
		nbt.setByte("Knockback", (byte) knockbackStrength);
		if (getProjectileItem() != null) {
			nbt.setTag("ProjectileItem", getProjectileItem().writeToNBT(new NBTTagCompound()));
		}
	}
}
