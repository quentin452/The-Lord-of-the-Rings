package lotr.common.enchant;

import lotr.common.item.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class LOTREnchantmentProtectionMithril extends LOTREnchantmentProtectionSpecial {
	public LOTREnchantmentProtectionMithril(String s) {
		super(s, 1);
		setValueModifier(2.0f);
	}

	@Override
	public int calcIntProtection() {
		return 4;
	}

	@Override
	public boolean canApply(ItemStack itemstack, boolean considering) {
		if (super.canApply(itemstack, considering)) {
			Item item = itemstack.getItem();
			return item instanceof ItemArmor && ((ItemArmor) item).getArmorMaterial() == LOTRMaterial.MITHRIL.toArmorMaterial();
		}
		return false;
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("lotr.enchant.protectMithril.desc", formatAdditiveInt(calcIntProtection()));
	}

	@Override
	public boolean protectsAgainst(DamageSource source) {
		ItemStack weapon;
		Entity attacker = source.getEntity();
		Entity entity = source.getSourceOfDamage();
		if (attacker instanceof EntityLivingBase && attacker == entity && (weapon = ((EntityLivingBase) attacker).getHeldItem()) != null) {
			ItemStack weaponBase = weapon.copy();
			LOTREnchantmentHelper.clearEnchants(weaponBase);
			float range = LOTRWeaponStats.getMeleeReachFactor(weaponBase);
			return range >= 1.3f;
		}
		return false;
	}
}
