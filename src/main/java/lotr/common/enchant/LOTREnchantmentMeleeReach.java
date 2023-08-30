package lotr.common.enchant;

import lotr.common.item.LOTRWeaponStats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentMeleeReach extends LOTREnchantment {
	public float reachFactor;

	public LOTREnchantmentMeleeReach(String s, float reach) {
		super(s, LOTREnchantmentType.MELEE);
		reachFactor = reach;
		setValueModifier(reachFactor);
	}

	@Override
	public boolean canApply(ItemStack itemstack, boolean considering) {
		if (super.canApply(itemstack, considering)) {
			float reach = LOTRWeaponStats.getMeleeReachFactor(itemstack);
			return reach * reachFactor <= LOTRWeaponStats.MAX_MODIFIABLE_REACH;
		}
		return false;
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("lotr.enchant.meleeReach.desc", formatMultiplicative(reachFactor));
	}

	@Override
	public boolean isBeneficial() {
		return reachFactor >= 1.0f;
	}
}
