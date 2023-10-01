package lotr.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentProtectionFire extends LOTREnchantmentProtectionSpecial {
	public LOTREnchantmentProtectionFire(String s, int level) {
		super(s, level);
	}

	@Override
	public int calcIntProtection() {
		return 1 + protectLevel;
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("lotr.enchant.protectFire.desc", formatAdditiveInt(calcIntProtection()));
	}

	@Override
	public boolean protectsAgainst(DamageSource source) {
		return source.isFireDamage();
	}
}
