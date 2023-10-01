package lotr.common.item;

import lotr.common.LOTRBannerProtection;
import lotr.common.LOTRMod;
import lotr.common.enchant.LOTREnchantment;
import lotr.common.enchant.LOTREnchantmentHelper;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LOTRItemBalrogWhip extends LOTRItemSword {
	public LOTRItemBalrogWhip() {
		super(LOTRMaterial.UTUMNO);
		lotrWeaponDamage = 7.0f;
		setMaxDamage(1000);
	}

	public void checkIncompatibleModifiers(ItemStack itemstack) {
		for (LOTREnchantment ench : new LOTREnchantment[]{LOTREnchantment.fire, LOTREnchantment.chill}) {
			if (!LOTREnchantmentHelper.hasEnchant(itemstack, ench)) {
				continue;
			}
			LOTREnchantmentHelper.removeEnchant(itemstack, ench);
		}
	}

	@Override
	public boolean getIsRepairable(ItemStack itemstack, ItemStack repairItem) {
		return repairItem.getItem() == LOTRMod.balrogFire;
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.bow;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 20;
	}

	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLivingBase hitEntity, EntityLivingBase user) {
		if (super.hitEntity(itemstack, hitEntity, user)) {
			checkIncompatibleModifiers(itemstack);
			if (!user.worldObj.isRemote && hitEntity.hurtTime == hitEntity.maxHurtTime) {
				launchWhip(user, hitEntity);
			}
			return true;
		}
		return false;
	}

	public void launchWhip(EntityLivingBase user, EntityLivingBase hitEntity) {
		World world = user.worldObj;
		world.playSoundAtEntity(user, "lotr:item.balrogWhip", 2.0f, 0.7f + itemRand.nextFloat() * 0.6f);
		double range = 16.0;
		Vec3 position = Vec3.createVectorHelper(user.posX, user.posY, user.posZ);
		Vec3 look = user.getLookVec();
		Vec3 sight = position.addVector(look.xCoord * range, look.yCoord * range, look.zCoord * range);
		float sightWidth = 1.0f;
		List list = world.getEntitiesWithinAABBExcludingEntity(user, user.boundingBox.addCoord(look.xCoord * range, look.yCoord * range, look.zCoord * range).expand(sightWidth, sightWidth, sightWidth));
		Collection<EntityLivingBase> whipTargets = new ArrayList<>();
		for (Object element : list) {
			EntityLivingBase entity;
			Entity obj = (Entity) element;
			if (!(obj instanceof EntityLivingBase) || (entity = (EntityLivingBase) obj) == user.ridingEntity && !entity.canRiderInteract() || !entity.canBeCollidedWith()) {
				continue;
			}
			float width = 1.0f;
			AxisAlignedBB axisalignedbb = entity.boundingBox.expand(width, width, width);
			MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(position, sight);
			if (axisalignedbb.isVecInside(position)) {
				whipTargets.add(entity);
				continue;
			}
			if (movingobjectposition == null) {
				continue;
			}
			whipTargets.add(entity);
		}
		for (EntityLivingBase entity : whipTargets) {
			if (entity != hitEntity && !entity.attackEntityFrom(DamageSource.causeMobDamage(user), 1.0f)) {
				continue;
			}
			entity.setFire(5);
		}
		Vec3 eyeHeight = position.addVector(0.0, user.getEyeHeight(), 0.0);
		for (int l = 4; l < (int) range; ++l) {
			double d = l / range;
			double dx = sight.xCoord - eyeHeight.xCoord;
			double dy = sight.yCoord - eyeHeight.yCoord;
			double dz = sight.zCoord - eyeHeight.zCoord;
			double x = eyeHeight.xCoord + dx * d;
			double y = eyeHeight.yCoord + dy * d;
			double z = eyeHeight.zCoord + dz * d;
			int i = MathHelper.floor_double(x);
			int j = MathHelper.floor_double(y);
			int k = MathHelper.floor_double(z);
			boolean placedFire = false;
			for (int j1 = j - 3; j1 <= j + 3; ++j1) {
				if (!World.doesBlockHaveSolidTopSurface(world, i, j1 - 1, k) && !(world.getBlock(i, j1 - 1, k) instanceof BlockLeavesBase) || !world.getBlock(i, j1, k).isReplaceable(world, i, j1, k)) {
					continue;
				}
				boolean protection = false;
				if (user instanceof EntityPlayer) {
					protection = LOTRBannerProtection.isProtected(world, i, j1, k, LOTRBannerProtection.forPlayer((EntityPlayer) user, LOTRBannerProtection.Permission.FULL), false);
				} else if (user instanceof EntityLiving) {
					protection = LOTRBannerProtection.isProtected(world, i, j1, k, LOTRBannerProtection.forNPC((EntityLiving) user), false);
				}
				if (protection) {
					continue;
				}
				world.setBlock(i, j1, k, Blocks.fire, 0, 3);
				placedFire = true;
				break;
			}
			if (!placedFire) {
				break;
			}
		}
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		checkIncompatibleModifiers(itemstack);
		entityplayer.swingItem();
		if (!world.isRemote) {
			launchWhip(entityplayer, null);
		}
		itemstack.damageItem(1, entityplayer);
		return itemstack;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		return itemstack;
	}
}
