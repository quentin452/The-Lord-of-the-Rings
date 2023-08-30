package lotr.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.entity.item.LOTREntityBarrel;
import lotr.common.tileentity.LOTRTileEntityBarrel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTRItemBarrel extends ItemBlock {
	public LOTRItemBarrel(Block block) {
		super(block);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		NBTTagCompound barrelData = getBarrelData(itemstack);
		if (barrelData != null) {
			LOTRTileEntityBarrel tileEntity = new LOTRTileEntityBarrel();
			tileEntity.readBarrelFromNBT(barrelData);
			list.add(tileEntity.getInvSubtitle());
		}
	}

	@Override
	public int getItemStackLimit(ItemStack itemstack) {
		NBTTagCompound nbt = getBarrelData(itemstack);
		if (nbt != null) {
			return 1;
		}
		return super.getItemStackLimit(itemstack);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		MovingObjectPosition m = getMovingObjectPositionFromPlayer(world, entityplayer, true);
		if (m == null) {
			return itemstack;
		}
		if (m.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
			int i = m.blockX;
			int j = m.blockY;
			int k = m.blockZ;
			if (world.getBlock(i, j, k).getMaterial() != Material.water || world.getBlockMetadata(i, j, k) != 0) {
				return itemstack;
			}
			LOTREntityBarrel barrel = new LOTREntityBarrel(world, i + 0.5f, j + 1.0f, k + 0.5f);
			barrel.rotationYaw = ((MathHelper.floor_double(entityplayer.rotationYaw * 4.0f / 360.0f + 0.5) & 3) - 1) * 90.0f;
			if (!world.getCollidingBoundingBoxes(barrel, barrel.boundingBox.expand(-0.1, -0.1, -0.1)).isEmpty()) {
				return itemstack;
			}
			if (!world.isRemote) {
				barrel.barrelItemData = getBarrelData(itemstack);
				world.spawnEntityInWorld(barrel);
			}
			if (!entityplayer.capabilities.isCreativeMode) {
				--itemstack.stackSize;
			}
		}
		return itemstack;
	}

	@Override
	public boolean placeBlockAt(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2, int metadata) {
		if (super.placeBlockAt(itemstack, entityplayer, world, i, j, k, side, f, f1, f2, metadata)) {
			TileEntity tileentity = world.getTileEntity(i, j, k);
			if (tileentity instanceof LOTRTileEntityBarrel) {
				LOTRTileEntityBarrel barrel = (LOTRTileEntityBarrel) tileentity;
				loadBarrelDataToTE(itemstack, barrel);
			}
			return true;
		}
		return false;
	}

	public static NBTTagCompound getBarrelData(ItemStack itemstack) {
		if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("LOTRBarrelData")) {
			return itemstack.getTagCompound().getCompoundTag("LOTRBarrelData");
		}
		return null;
	}

	public static void loadBarrelDataToTE(ItemStack itemstack, LOTRTileEntityBarrel barrel) {
		NBTTagCompound nbt = getBarrelData(itemstack);
		if (nbt != null) {
			barrel.readBarrelFromNBT(nbt);
		}
	}

	public static void setBarrelData(ItemStack itemstack, NBTTagCompound nbt) {
		itemstack.setTagCompound(new NBTTagCompound());
		itemstack.getTagCompound().setTag("LOTRBarrelData", nbt);
	}

	public static void setBarrelDataFromTE(ItemStack itemstack, LOTRTileEntityBarrel barrel) {
		NBTTagCompound nbt = new NBTTagCompound();
		barrel.writeBarrelToNBT(nbt);
		setBarrelData(itemstack, nbt);
	}
}
