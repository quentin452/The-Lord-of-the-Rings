package lotr.common.enchant;

import lotr.common.item.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentDamage extends LOTREnchantment {
	public float baseDamageBoost;

	public LOTREnchantmentDamage(String s, float boost) {
		super(s, new LOTREnchantmentType[]{LOTREnchantmentType.MELEE, LOTREnchantmentType.THROWING_AXE});
		baseDamageBoost = boost;
		if (baseDamageBoost >= 0.0f) {
			setValueModifier((7.0f + baseDamageBoost * 5.0f) / 7.0f);
		} else {
			setValueModifier((7.0f + baseDamageBoost) / 7.0f);
		}
	}

	@Override
	public boolean canApply(ItemStack itemstack, boolean considering) {
		if (super.canApply(itemstack, considering)) {
			float dmg = LOTRWeaponStats.getMeleeDamageBonus(itemstack);
			return dmg + baseDamageBoost > 0.0f;
		}
		return false;
	}

	public float getBaseDamageBoost() {
		return baseDamageBoost;
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		if (itemstack != null && itemstack.getItem() instanceof LOTRItemThrowingAxe) {
			return StatCollector.translateToLocalFormatted("lotr.enchant.damage.desc.throw", formatAdditive(baseDamageBoost));
		}
		return StatCollector.translateToLocalFormatted("lotr.enchant.damage.desc", formatAdditive(baseDamageBoost));
	}

	public float getEntitySpecificDamage(EntityLivingBase entity) {
		return 0.0f;
	}

	@Override
	public boolean isBeneficial() {
		return baseDamageBoost >= 0.0f;
	}
}
