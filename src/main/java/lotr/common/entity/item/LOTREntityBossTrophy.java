package lotr.common.entity.item;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTRBannerProtectable;
import lotr.common.item.LOTRItemBossTrophy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTREntityBossTrophy extends Entity implements LOTRBannerProtectable {
	public LOTREntityBossTrophy(World world) {
		super(world);
		setSize(1.0f, 1.0f);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		if (!worldObj.isRemote && !isDead && damagesource.getSourceOfDamage() instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) damagesource.getSourceOfDamage();
			dropAsItem(!entityplayer.capabilities.isCreativeMode);
			return true;
		}
		return false;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	public void dropAsItem(boolean dropItem) {
		worldObj.playSoundAtEntity(this, Blocks.stone.stepSound.getBreakSound(), (Blocks.stone.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.stone.stepSound.getPitch() * 0.8f);
		if (dropItem) {
			entityDropItem(new ItemStack(LOTRMod.bossTrophy, 1, getTrophyType().trophyID), 0.0f);
		}
		setDead();
	}

	@Override
	public void entityInit() {
		dataWatcher.addObject(18, (byte) 0);
		dataWatcher.addObject(19, (byte) 0);
		dataWatcher.addObject(20, (byte) 0);
	}

	@Override
	public AxisAlignedBB getBoundingBox() {
		return boundingBox;
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(LOTRMod.bossTrophy, 1, getTrophyType().trophyID);
	}

	public int getTrophyFacing() {
		byte i = dataWatcher.getWatchableObjectByte(20);
		if (i < 0 || i >= Direction.directions.length) {
			i = 0;
		}
		return i;
	}

	public void setTrophyFacing(int i) {
		dataWatcher.updateObject(20, (byte) i);
	}

	public LOTRItemBossTrophy.TrophyType getTrophyType() {
		return LOTRItemBossTrophy.TrophyType.forID(getTrophyTypeID());
	}

	public void setTrophyType(LOTRItemBossTrophy.TrophyType type) {
		setTrophyTypeID(type.trophyID);
	}

	public int getTrophyTypeID() {
		return dataWatcher.getWatchableObjectByte(18);
	}

	public void setTrophyTypeID(int i) {
		dataWatcher.updateObject(18, (byte) i);
	}

	public boolean hangingOnValidSurface() {
		if (isTrophyHanging()) {
			int direction = getTrophyFacing();
			int opposite = Direction.rotateOpposite[direction];
			int dx = Direction.offsetX[opposite];
			int dz = Direction.offsetZ[opposite];
			int blockX = MathHelper.floor_double(posX);
			int blockY = MathHelper.floor_double(boundingBox.minY);
			int blockZ = MathHelper.floor_double(posZ);
			Block block = worldObj.getBlock(blockX += dx, blockY, blockZ += dz);
			int blockSide = Direction.directionToFacing[direction];
			return block.isSideSolid(worldObj, blockX, blockY, blockZ, ForgeDirection.getOrientation(blockSide));
		}
		return false;
	}

	public boolean isTrophyHanging() {
		return dataWatcher.getWatchableObjectByte(19) == 1;
	}

	public void setTrophyHanging(boolean flag) {
		dataWatcher.updateObject(19, flag ? (byte) 1 : 0);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		if (isTrophyHanging()) {
			if (!hangingOnValidSurface() && !worldObj.isRemote && !isDead) {
				dropAsItem(true);
			}
		} else {
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
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		setTrophyTypeID(nbt.getByte("TrophyType"));
		setTrophyHanging(nbt.getBoolean("TrophyHanging"));
		setTrophyFacing(nbt.getByte("TrophyFacing"));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setByte("TrophyType", (byte) getTrophyTypeID());
		nbt.setBoolean("TrophyHanging", isTrophyHanging());
		nbt.setByte("TrophyFacing", (byte) getTrophyFacing());
	}
}
