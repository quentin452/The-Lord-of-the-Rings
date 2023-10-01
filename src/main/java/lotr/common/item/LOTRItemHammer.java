package lotr.common.item;

import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class LOTRItemHammer extends LOTRItemSword {
	public LOTRItemHammer(Item.ToolMaterial material) {
		super(material);
	}

	public LOTRItemHammer(LOTRMaterial material) {
		this(material.toToolMaterial());
		lotrWeaponDamage += 2.0f;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.none;
	}
}
