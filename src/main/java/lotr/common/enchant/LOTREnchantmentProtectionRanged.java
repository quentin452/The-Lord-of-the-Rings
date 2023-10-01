package lotr.common.enchant;

import lotr.common.item.LOTRMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentProtectionRanged extends LOTREnchantmentProtectionSpecial {
	public LOTREnchantmentProtectionRanged(String s, int level) {
		super(s, level);
	}

	@Override
	public int calcIntProtection() {
		return protectLevel;
	}

	@Override
	public boolean canApply(ItemStack itemstack, boolean considering) {
		if (super.canApply(itemstack, considering)) {
			Item item = itemstack.getItem();
			return !(item instanceof ItemArmor) || ((ItemArmor) item).getArmorMaterial() != LOTRMaterial.GALVORN.toArmorMaterial();
		}
		return false;
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("lotr.enchant.protectRanged.desc", formatAdditiveInt(calcIntProtection()));
	}

	@Override
	public boolean protectsAgainst(DamageSource source) {
		return source.isProjectile();
	}
}
