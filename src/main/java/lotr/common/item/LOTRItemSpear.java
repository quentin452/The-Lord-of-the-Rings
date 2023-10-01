package lotr.common.item;

import lotr.common.dispenser.LOTRDispenseSpear;
import lotr.common.enchant.LOTREnchantment;
import lotr.common.enchant.LOTREnchantmentHelper;
import lotr.common.entity.projectile.LOTREntitySpear;
import net.minecraft.block.BlockDispenser;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRItemSpear extends LOTRItemSword {
	public LOTRItemSpear(Item.ToolMaterial material) {
		super(material);
		lotrWeaponDamage -= 1.0f;
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, new LOTRDispenseSpear());
	}

	public LOTRItemSpear(LOTRMaterial material) {
		this(material.toToolMaterial());
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.bow;
	}

	public int getMaxDrawTime() {
		return 20;
	}

	public float getRangedDamageMultiplier(ItemStack itemstack, Entity shooter, Entity hit) {
		float damage = getLOTRWeaponDamage();
		damage = shooter instanceof EntityLivingBase && hit instanceof EntityLivingBase ? damage + EnchantmentHelper.getEnchantmentModifierLiving((EntityLivingBase) shooter, (EntityLivingBase) hit) : damage + EnchantmentHelper.func_152377_a(itemstack, EnumCreatureAttribute.UNDEFINED);
		return damage * 0.7f;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int i) {
		if (entityplayer.getHeldItem() != itemstack) {
			return;
		}
		int useTick = getMaxItemUseDuration(itemstack) - i;
		float charge = (float) useTick / getMaxDrawTime();
		if (charge < 0.1f) {
			return;
		}
		charge = (charge * charge + charge * 2.0f) / 3.0f;
		charge = Math.min(charge, 1.0f);
		LOTREntitySpear spear = new LOTREntitySpear(world, entityplayer, itemstack.copy(), charge * 2.0f);
		if (charge >= 1.0f) {
			spear.setIsCritical(true);
		}
		if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemstack) + LOTREnchantmentHelper.calcFireAspect(itemstack) > 0) {
			spear.setFire(100);
		}
		for (LOTREnchantment ench : LOTREnchantment.allEnchantments) {
			if (!ench.applyToProjectile() || !LOTREnchantmentHelper.hasEnchant(itemstack, ench)) {
				continue;
			}
			LOTREnchantmentHelper.setProjectileEnchantment(spear, ench);
		}
		if (entityplayer.capabilities.isCreativeMode) {
			spear.canBePickedUp = 2;
		}
		world.playSoundAtEntity(entityplayer, "random.bow", 1.0f, 1.0f / (itemRand.nextFloat() * 0.4f + 1.2f) + charge * 0.5f);
		if (!world.isRemote) {
			world.spawnEntityInWorld(spear);
		}
		if (!entityplayer.capabilities.isCreativeMode) {
			--itemstack.stackSize;
			if (itemstack.stackSize <= 0) {
				entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
			}
		}
	}
}
