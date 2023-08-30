package lotr.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentToolSpeed extends LOTREnchantment {
	public float speedFactor;

	public LOTREnchantmentToolSpeed(String s, float speed) {
		super(s, new LOTREnchantmentType[]{LOTREnchantmentType.TOOL, LOTREnchantmentType.SHEARS});
		speedFactor = speed;
		setValueModifier(speedFactor);
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("lotr.enchant.toolSpeed.desc", formatMultiplicative(speedFactor));
	}

	@Override
	public boolean isBeneficial() {
		return speedFactor >= 1.0f;
	}
}
