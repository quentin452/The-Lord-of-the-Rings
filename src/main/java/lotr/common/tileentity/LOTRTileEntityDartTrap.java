package lotr.common.tileentity;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemDart;
import net.minecraft.block.*;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.*;

public class LOTRTileEntityDartTrap extends TileEntityDispenser {
	public int fireCooldown;

	@Override
	public String getInventoryName() {
		return hasCustomInventoryName() ? field_146020_a : "container.lotr.dartTrap";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return getTriggerRange();
	}

	public AxisAlignedBB getTriggerRange() {
		Vec3 vecTarget;
		new BlockSourceImpl(worldObj, xCoord, yCoord, zCoord);
		EnumFacing facing = BlockDispenser.func_149937_b(getBlockMetadata());
		float front = 0.55f;
		float range = 16.0f;
		Vec3 vecPos = Vec3.createVectorHelper(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5);
		Vec3 vecFront = vecPos.addVector(facing.getFrontOffsetX() * front, facing.getFrontOffsetY() * front, facing.getFrontOffsetZ() * front);
		MovingObjectPosition hitBlock = worldObj.func_147447_a(vecFront, vecTarget = vecPos.addVector(facing.getFrontOffsetX() * range, facing.getFrontOffsetY() * range, facing.getFrontOffsetZ() * range), true, true, false);
		if (hitBlock != null) {
			vecTarget = Vec3.createVectorHelper(hitBlock.blockX + 0.5 - facing.getFrontOffsetX(), hitBlock.blockY + 0.5 - facing.getFrontOffsetY(), hitBlock.blockZ + 0.5 - facing.getFrontOffsetZ());
		}
		float f = 0.0f;
		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(xCoord + f, yCoord + f, zCoord + f, xCoord + 1 - f, yCoord + 1 - f, zCoord + 1 - f);
		return bb.addCoord(vecTarget.xCoord - vecPos.xCoord, vecTarget.yCoord - vecPos.yCoord, vecTarget.zCoord - vecPos.zCoord);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (!worldObj.isRemote) {
			if (fireCooldown > 0) {
				--fireCooldown;
			} else {
				ItemStack itemstack;
				int slot = func_146017_i();
				if (slot >= 0 && (itemstack = getStackInSlot(slot)).getItem() instanceof LOTRItemDart && !worldObj.selectEntitiesWithinAABB(EntityLivingBase.class, getTriggerRange(), LOTRMod.selectLivingExceptCreativePlayers()).isEmpty()) {
					IBehaviorDispenseItem dispense = (IBehaviorDispenseItem) BlockDispenser.dispenseBehaviorRegistry.getObject(itemstack.getItem());
					ItemStack result = dispense.dispense(new BlockSourceImpl(worldObj, xCoord, yCoord, zCoord), itemstack);
					setInventorySlotContents(slot, result.stackSize == 0 ? null : result);
					fireCooldown = 20;
				}
			}
		}
	}
}
