package lotr.common.entity.item;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityBannerWall extends EntityHanging {
	public NBTTagCompound protectData;
	public boolean updatedClientBB;

	public LOTREntityBannerWall(World world) {
		super(world);
		setSize(0.0f, 0.0f);
	}

	public LOTREntityBannerWall(World world, int i, int j, int k, int dir) {
		super(world, i, j, k, dir);
		setSize(0.0f, 0.0f);
		setDirection(dir);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		if (!worldObj.isRemote && damagesource.getEntity() instanceof EntityPlayer && LOTRBannerProtection.isProtected(worldObj, this, LOTRBannerProtection.forPlayer((EntityPlayer) damagesource.getEntity(), LOTRBannerProtection.Permission.FULL), true)) {
			return false;
		}
		return super.attackEntityFrom(damagesource, f);
	}

	@Override
	public void entityInit() {
		dataWatcher.addObject(10, 0);
		dataWatcher.addObject(11, 0);
		dataWatcher.addObject(12, 0);
		dataWatcher.addObject(13, (byte) 0);
		dataWatcher.addObject(18, (byte) 0);
	}

	public ItemStack getBannerItem() {
		ItemStack item = new ItemStack(LOTRMod.banner, 1, getBannerType().bannerID);
		if (protectData != null) {
			LOTRItemBanner.setProtectionData(item, protectData);
		}
		return item;
	}

	public LOTRItemBanner.BannerType getBannerType() {
		return LOTRItemBanner.BannerType.forID(getBannerTypeID());
	}

	public int getBannerTypeID() {
		return dataWatcher.getWatchableObjectByte(18);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float f) {
		int i;
		int k;
		if (!updatedClientBB) {
			getWatchedDirection();
			setDirection(hangingDirection);
			updatedClientBB = true;
		}
		if (worldObj.blockExists(i = MathHelper.floor_double(posX), 0, k = MathHelper.floor_double(posZ))) {
			int j = MathHelper.floor_double(posY);
			return worldObj.getLightBrightnessForSkyBlocks(i, j, k, 0);
		}
		return 0;
	}

	@Override
	public int getHeightPixels() {
		return 32;
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return getBannerItem();
	}

	public void getWatchedDirection() {
		field_146063_b = dataWatcher.getWatchableObjectInt(10);
		field_146064_c = dataWatcher.getWatchableObjectInt(11);
		field_146062_d = dataWatcher.getWatchableObjectInt(12);
		hangingDirection = dataWatcher.getWatchableObjectByte(13);
	}

	@Override
	public int getWidthPixels() {
		return 16;
	}

	@Override
	public void onBroken(Entity entity) {
		worldObj.playSoundAtEntity(this, Blocks.planks.stepSound.getBreakSound(), (Blocks.planks.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.planks.stepSound.getPitch() * 0.8f);
		boolean flag = !(entity instanceof EntityPlayer) || !((EntityPlayer) entity).capabilities.isCreativeMode;
		if (flag) {
			entityDropItem(getBannerItem(), 0.0f);
		}
	}

	@Override
	public void onUpdate() {
		if (worldObj.isRemote && !updatedClientBB) {
			getWatchedDirection();
			setDirection(hangingDirection);
			updatedClientBB = true;
		}
		super.onUpdate();
	}

	@Override
	public boolean onValidSurface() {
		if (!worldObj.getCollidingBoundingBoxes(this, boundingBox).isEmpty()) {
			return false;
		}
		int i = field_146063_b;
		int j = field_146064_c;
		int k = field_146062_d;
		Block block = worldObj.getBlock(i, j, k);
		if (!block.getMaterial().isSolid()) {
			return false;
		}
		List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox);
		for (Object obj : list) {
			if (!(obj instanceof EntityHanging)) {
				continue;
			}
			return false;
		}
		return true;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setBannerTypeID(nbt.getByte("BannerType"));
		if (nbt.hasKey("ProtectData")) {
			protectData = nbt.getCompoundTag("ProtectData");
		}
	}

	public void setBannerType(LOTRItemBanner.BannerType type) {
		setBannerTypeID(type.bannerID);
	}

	public void setBannerTypeID(int i) {
		dataWatcher.updateObject(18, (byte) i);
	}

	@Override
	public void setDirection(int dir) {
		float zEdge;
		float xSize;
		float zSize;
		float edge;
		float xEdge;
		if (dir < 0 || dir >= Direction.directions.length) {
			dir = 0;
		}
		hangingDirection = dir;
		prevRotationYaw = rotationYaw = Direction.rotateOpposite[dir] * 90.0f;
		float width = 1.0f;
		float thickness = 0.0625f;
		float yEdge = edge = 0.01f;
		if (dir == 0 || dir == 2) {
			xSize = width;
			zSize = thickness;
			xEdge = thickness + edge;
			zEdge = edge;
		} else {
			xSize = thickness;
			zSize = width;
			xEdge = edge;
			zEdge = thickness + edge;
		}
		float f = field_146063_b + 0.5f;
		float f1 = field_146064_c + 0.5f;
		float f2 = field_146062_d + 0.5f;
		float f3 = 0.5f + thickness / 2.0f;
		setPosition(f += Direction.offsetX[dir] * f3, f1, f2 += Direction.offsetZ[dir] * f3);
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		boundingBox.setBounds(f - xSize / 2.0f, f1 - 1.5f, f2 - zSize / 2.0f, f + xSize / 2.0f, f1 + 0.5f, f2 + zSize / 2.0f);
		boundingBox.setBB(boundingBox.contract(xEdge, yEdge, zEdge));
		if (!worldObj.isRemote) {
			updateWatchedDirection();
		}
	}

	public void setProtectData(NBTTagCompound nbt) {
		protectData = nbt;
	}

	public void updateWatchedDirection() {
		dataWatcher.updateObject(10, field_146063_b);
		dataWatcher.updateObject(11, field_146064_c);
		dataWatcher.updateObject(12, field_146062_d);
		dataWatcher.updateObject(13, (byte) hangingDirection);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setByte("BannerType", (byte) getBannerTypeID());
		if (protectData != null) {
			nbt.setTag("ProtectData", protectData);
		}
	}
}
