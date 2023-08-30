package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.entity.npc.LOTREntityHalfTroll;
import net.minecraft.entity.Entity;
import net.minecraft.item.*;
import net.minecraft.util.StringUtils;

import java.util.Locale;

public class LOTRItemArmor extends ItemArmor {
	public LOTRMaterial lotrMaterial;
	public String extraName;

	public LOTRItemArmor(LOTRMaterial material, int slotType) {
		this(material, slotType, "");
	}

	public LOTRItemArmor(LOTRMaterial material, int slotType, String s) {
		super(material.toArmorMaterial(), 0, slotType);
		lotrMaterial = material;
		setCreativeTab(LOTRCreativeTabs.tabCombat);
		extraName = s;
	}

	public String getArmorName() {
		String suffix;
		String prefix = getArmorMaterial().name().substring("lotr".length() + 1).toLowerCase(Locale.ROOT);
		suffix = armorType == 2 ? "2" : "1";
		if (!StringUtils.isNullOrEmpty(extraName)) {
			suffix = extraName;
		}
		return prefix + "_" + suffix;
	}

	@Override
	public String getArmorTexture(ItemStack itemstack, Entity entity, int slot, String type) {
		String path = "lotr:armor/";
		if (entity instanceof LOTREntityHalfTroll) {
			path = "lotr:mob/halfTroll/";
		}
		String armorName = getArmorName();
		StringBuilder texture = new StringBuilder().append(path).append(armorName);
		if (type != null) {
			texture.append("_").append(type);
		}
		return texture.append(".png").toString();
	}

	public LOTRMaterial getLOTRArmorMaterial() {
		return lotrMaterial;
	}

	@Override
	public boolean isDamageable() {
		return lotrMaterial.isDamageable();
	}
}
