package lotr.common.enchant;

import lotr.common.item.LOTRWeaponStats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentKnockback extends LOTREnchantment {
	public int knockback;

	public LOTREnchantmentKnockback(String s, int i) {
		super(s, new LOTREnchantmentType[]{LOTREnchantmentType.MELEE, LOTREnchantmentType.THROWING_AXE});
		knockback = i;
		setValueModifier((knockback + 2) / 2.0f);
	}

	@Override
	public boolean canApply(ItemStack itemstack, boolean considering) {
		return super.canApply(itemstack, considering) && LOTRWeaponStats.getBaseExtraKnockback(itemstack) + knockback <= LOTRWeaponStats.MAX_MODIFIABLE_KNOCKBACK;
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("lotr.enchant.knockback.desc", formatAdditiveInt(knockback));
	}

	@Override
	public boolean isBeneficial() {
		return knockback >= 0;
	}
}
