package lotr.common.enchant;

import lotr.common.item.LOTRWeaponStats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentMeleeSpeed extends LOTREnchantment {
	public float speedFactor;

	public LOTREnchantmentMeleeSpeed(String s, float speed) {
		super(s, LOTREnchantmentType.MELEE);
		speedFactor = speed;
		setValueModifier(speedFactor);
	}

	@Override
	public boolean canApply(ItemStack itemstack, boolean considering) {
		if (super.canApply(itemstack, considering)) {
			float speed = LOTRWeaponStats.getMeleeSpeed(itemstack);
			return speed * speedFactor <= LOTRWeaponStats.MAX_MODIFIABLE_SPEED;
		}
		return false;
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("lotr.enchant.meleeSpeed.desc", formatMultiplicative(speedFactor));
	}

	@Override
	public boolean isBeneficial() {
		return speedFactor >= 1.0f;
	}
}
