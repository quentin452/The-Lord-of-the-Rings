package lotr.common.enchant;

import net.minecraft.util.DamageSource;

public abstract class LOTREnchantmentProtectionSpecial extends LOTREnchantment {
	public int protectLevel;

	protected LOTREnchantmentProtectionSpecial(String s, int level) {
		this(s, LOTREnchantmentType.ARMOR, level);
	}

	protected LOTREnchantmentProtectionSpecial(String s, LOTREnchantmentType type, int level) {
		super(s, type);
		protectLevel = level;
		setValueModifier((2.0f + protectLevel) / 2.0f);
	}

	public abstract int calcIntProtection();

	public int calcSpecialProtection(DamageSource source) {
		if (source.canHarmInCreative()) {
			return 0;
		}
		if (protectsAgainst(source)) {
			return calcIntProtection();
		}
		return 0;
	}

	@Override
	public boolean isBeneficial() {
		return true;
	}

	@Override
	public boolean isCompatibleWith(LOTREnchantment other) {
		if (super.isCompatibleWith(other)) {
			if (other instanceof LOTREnchantmentProtectionSpecial) {
				return isCompatibleWithOtherProtection() || ((LOTREnchantmentProtectionSpecial) other).isCompatibleWithOtherProtection();
			}
			return true;
		}
		return false;
	}

	public boolean isCompatibleWithOtherProtection() {
		return false;
	}

	public abstract boolean protectsAgainst(DamageSource var1);
}
