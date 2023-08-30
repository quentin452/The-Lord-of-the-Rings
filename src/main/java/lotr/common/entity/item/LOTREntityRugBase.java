package lotr.common.entity.item;

import lotr.common.entity.LOTRBannerProtectable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

public abstract class LOTREntityRugBase extends Entity implements LOTRBannerProtectable {
	public int timeSinceLastGrowl = getTimeUntilGrowl();

	protected LOTREntityRugBase(World world) {
		super(world);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		if (!worldObj.isRemote && !isDead) {
			boolean creative;
			Block.SoundType blockSound = Blocks.wool.stepSound;
			worldObj.playSoundAtEntity(this, blockSound.getBreakSound(), (blockSound.getVolume() + 1.0f) / 2.0f, blockSound.getPitch() * 0.8f);
			Entity attacker = damagesource.getEntity();
			creative = attacker instanceof EntityPlayer && ((EntityPlayer) attacker).capabilities.isCreativeMode;
			if (!creative) {
				entityDropItem(getRugItem(), 0.0f);
			}
			setDead();
		}
		return true;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public void entityInit() {
	}

	@Override
	public AxisAlignedBB getBoundingBox() {
		return boundingBox;
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return getRugItem();
	}

	public abstract ItemStack getRugItem();

	public abstract String getRugNoise();

	public int getTimeUntilGrowl() {
		return (60 + rand.nextInt(150)) * 20;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		motionY -= 0.04;
		func_145771_j(posX, (boundingBox.minY + boundingBox.maxY) / 2.0, posZ);
		moveEntity(motionX, motionY, motionZ);
		float f = 0.98f;
		if (onGround) {
			f = 0.588f;
			Block i = worldObj.getBlock(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
			if (i.getMaterial() != Material.air) {
				f = i.slipperiness * 0.98f;
			}
		}
		motionX *= f;
		motionY *= 0.98;
		motionZ *= f;
		if (onGround) {
			motionY *= -0.5;
		}
		if (!worldObj.isRemote) {
			if (timeSinceLastGrowl > 0) {
				--timeSinceLastGrowl;
			} else if (rand.nextInt(5000) == 0) {
				worldObj.playSoundAtEntity(this, getRugNoise(), 1.0f, (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2f + 1.0f);
				timeSinceLastGrowl = getTimeUntilGrowl();
			}
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
	}
}
