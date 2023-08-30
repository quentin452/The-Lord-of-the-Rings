package lotr.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentLooting extends LOTREnchantment {
	public int lootLevel;

	public LOTREnchantmentLooting(String s, int level) {
		super(s, new LOTREnchantmentType[]{LOTREnchantmentType.TOOL, LOTREnchantmentType.MELEE});
		lootLevel = level;
		setValueModifier(1.0f + lootLevel);
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("lotr.enchant.looting.desc", formatAdditiveInt(lootLevel));
	}

	@Override
	public boolean isBeneficial() {
		return true;
	}

	@Override
	public boolean isCompatibleWith(LOTREnchantment other) {
		return super.isCompatibleWith(other) && !(other instanceof LOTREnchantmentSilkTouch);
	}
}
