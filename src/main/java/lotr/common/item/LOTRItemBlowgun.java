package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import lotr.common.enchant.LOTREnchantment;
import lotr.common.enchant.LOTREnchantmentHelper;
import lotr.common.entity.projectile.LOTREntityDart;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRItemBlowgun extends Item {
	public LOTRItemBlowgun(Item.ToolMaterial material) {
		setMaxStackSize(1);
		setMaxDamage(material.getMaxUses());
		setCreativeTab(LOTRCreativeTabs.tabCombat);
		setFull3D();
	}

	public LOTRItemBlowgun(LOTRMaterial material) {
		this(material.toToolMaterial());
	}

	public static void applyBlowgunModifiers(LOTREntityDart dart, ItemStack itemstack) {
		int punch = LOTREnchantmentHelper.calcRangedKnockback(itemstack);
		if (punch > 0) {
			dart.knockbackStrength = punch;
		}
		if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemstack) + LOTREnchantmentHelper.calcFireAspect(itemstack) > 0) {
			dart.setFire(100);
		}
		for (LOTREnchantment ench : LOTREnchantment.allEnchantments) {
			if (!ench.applyToProjectile() || !LOTREnchantmentHelper.hasEnchant(itemstack, ench)) {
				continue;
			}
			LOTREnchantmentHelper.setProjectileEnchantment(dart, ench);
		}
	}

	public static float getBlowgunLaunchSpeedFactor(ItemStack itemstack) {
		float f = 1.0f;
		if (itemstack != null) {
			f *= LOTREnchantmentHelper.calcRangedDamageFactor(itemstack);
		}
		return f;
	}

	@Override
	public boolean getIsRepairable(ItemStack itemstack, ItemStack repairItem) {
		return repairItem.getItem() == Item.getItemFromBlock(LOTRMod.reeds);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.bow;
	}

	public int getMaxDrawTime() {
		return 5;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 72000;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		boolean anyDart = false;
		for (ItemStack invItem : entityplayer.inventory.mainInventory) {
			if (invItem == null || !(invItem.getItem() instanceof LOTRItemDart)) {
				continue;
			}
			anyDart = true;
			break;
		}
		if (anyDart || entityplayer.capabilities.isCreativeMode) {
			entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		}
		return itemstack;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int i) {
		ItemStack dartItem = null;
		int dartSlot = -1;
		for (int l = 0; l < entityplayer.inventory.mainInventory.length; ++l) {
			ItemStack invItem = entityplayer.inventory.mainInventory[l];
			if (invItem == null || !(invItem.getItem() instanceof LOTRItemDart)) {
				continue;
			}
			dartItem = invItem;
			dartSlot = l;
			break;
		}
		if (dartItem == null && entityplayer.capabilities.isCreativeMode) {
			dartItem = new ItemStack(LOTRMod.tauredainDart);
		}
		if (dartItem != null) {
			int useTick = getMaxItemUseDuration(itemstack) - i;
			float charge = (float) useTick / getMaxDrawTime();
			if (charge < 0.65f) {
				return;
			}
			charge = (charge * charge + charge * 2.0f) / 3.0f;
			charge = Math.min(charge, 1.0f);
			itemstack.damageItem(1, entityplayer);
			if (!entityplayer.capabilities.isCreativeMode && dartSlot >= 0) {
				--dartItem.stackSize;
				if (dartItem.stackSize <= 0) {
					entityplayer.inventory.mainInventory[dartSlot] = null;
				}
			}
			world.playSoundAtEntity(entityplayer, "lotr:item.dart", 1.0f, 1.0f / (itemRand.nextFloat() * 0.4f + 1.2f) + charge * 0.5f);
			if (!world.isRemote) {
				ItemStack shotDart = dartItem.copy();
				shotDart.stackSize = 1;
				LOTREntityDart dart = ((LOTRItemDart) shotDart.getItem()).createDart(world, entityplayer, shotDart, charge * 2.0f * getBlowgunLaunchSpeedFactor(itemstack));
				if (dart.dartDamageFactor < 1.0f) {
					dart.dartDamageFactor = 1.0f;
				}
				if (charge >= 1.0f) {
					dart.setIsCritical(true);
				}
				applyBlowgunModifiers(dart, itemstack);
				if (entityplayer.capabilities.isCreativeMode) {
					dart.canBePickedUp = 2;
				}
				world.spawnEntityInWorld(dart);
			}
		}
	}
}
