package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.dispenser.LOTRDispenseThrowingAxe;
import lotr.common.enchant.LOTREnchantment;
import lotr.common.enchant.LOTREnchantmentHelper;
import lotr.common.entity.projectile.LOTREntityThrowingAxe;
import lotr.common.recipe.LOTRRecipes;
import net.minecraft.block.BlockDispenser;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRItemThrowingAxe extends Item {
	public Item.ToolMaterial axeMaterial;

	public LOTRItemThrowingAxe(Item.ToolMaterial material) {
		axeMaterial = material;
		setMaxStackSize(1);
		setMaxDamage(material.getMaxUses());
		setFull3D();
		setCreativeTab(LOTRCreativeTabs.tabCombat);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, new LOTRDispenseThrowingAxe());
	}

	public LOTRItemThrowingAxe(LOTRMaterial material) {
		this(material.toToolMaterial());
	}

	public Item.ToolMaterial getAxeMaterial() {
		return axeMaterial;
	}

	@Override
	public boolean getIsRepairable(ItemStack itemstack, ItemStack repairItem) {
		if (LOTRRecipes.checkItemEquals(axeMaterial.getRepairItemStack(), repairItem)) {
			return true;
		}
		return super.getIsRepairable(itemstack, repairItem);
	}

	public float getRangedDamageMultiplier(ItemStack itemstack, Entity shooter, Entity hit) {
		float damage = axeMaterial.getDamageVsEntity() + 4.0f;
		damage = shooter instanceof EntityLivingBase && hit instanceof EntityLivingBase ? damage + EnchantmentHelper.getEnchantmentModifierLiving((EntityLivingBase) shooter, (EntityLivingBase) hit) : damage + EnchantmentHelper.func_152377_a(itemstack, EnumCreatureAttribute.UNDEFINED);
		return damage * 0.5f;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		LOTREntityThrowingAxe axe = new LOTREntityThrowingAxe(world, entityplayer, itemstack.copy(), 2.0f);
		axe.setIsCritical(true);
		int fireAspect = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemstack) + LOTREnchantmentHelper.calcFireAspect(itemstack);
		if (fireAspect > 0) {
			axe.setFire(100);
		}
		for (LOTREnchantment ench : LOTREnchantment.allEnchantments) {
			if (!ench.applyToProjectile() || !LOTREnchantmentHelper.hasEnchant(itemstack, ench)) {
				continue;
			}
			LOTREnchantmentHelper.setProjectileEnchantment(axe, ench);
		}
		if (entityplayer.capabilities.isCreativeMode) {
			axe.canBePickedUp = 2;
		}
		world.playSoundAtEntity(entityplayer, "random.bow", 1.0f, 1.0f / (itemRand.nextFloat() * 0.4f + 1.2f) + 0.25f);
		if (!world.isRemote) {
			world.spawnEntityInWorld(axe);
		}
		if (!entityplayer.capabilities.isCreativeMode) {
			--itemstack.stackSize;
		}
		return itemstack;
	}
}
