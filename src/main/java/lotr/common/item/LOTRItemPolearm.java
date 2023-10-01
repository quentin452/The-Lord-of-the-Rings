package lotr.common.item;

import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class LOTRItemPolearm extends LOTRItemSword {
	public LOTRItemPolearm(Item.ToolMaterial material) {
		super(material);
	}

	public LOTRItemPolearm(LOTRMaterial material) {
		this(material.toToolMaterial());
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.none;
	}
}
