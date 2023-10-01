package lotr.common.enchant;

import lotr.common.item.LOTRMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentProtection extends LOTREnchantment {
	public int protectLevel;

	public LOTREnchantmentProtection(String s, int level) {
		this(s, LOTREnchantmentType.ARMOR, level);
	}

	public LOTREnchantmentProtection(String s, LOTREnchantmentType type, int level) {
		super(s, type);
		protectLevel = level;
		if (protectLevel >= 0) {
			setValueModifier((2.0f + protectLevel) / 2.0f);
		} else {
			setValueModifier((4.0f + protectLevel) / 4.0f);
		}
	}

	@Override
	public boolean canApply(ItemStack itemstack, boolean considering) {
		if (super.canApply(itemstack, considering)) {
			Item item = itemstack.getItem();
			if (item instanceof ItemArmor) {
				ItemArmor armor = (ItemArmor) item;
				if (armor.getArmorMaterial() == LOTRMaterial.GALVORN.toArmorMaterial()) {
					return false;
				}
				int prot = armor.damageReduceAmount;
				int total = prot + protectLevel;
				if (total > 0) {
					if (considering) {
						return true;
					}
					return total <= LOTRMaterial.MITHRIL.toArmorMaterial().getDamageReductionAmount(armor.armorType);
				}
				return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("lotr.enchant.protect.desc", formatAdditiveInt(protectLevel));
	}

	@Override
	public boolean isBeneficial() {
		return protectLevel >= 0;
	}
}
