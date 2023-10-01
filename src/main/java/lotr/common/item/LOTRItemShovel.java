package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;

public class LOTRItemShovel extends ItemSpade {
	public LOTRItemShovel(Item.ToolMaterial material) {
		super(material);
		setCreativeTab(LOTRCreativeTabs.tabTools);
	}

	public LOTRItemShovel(LOTRMaterial material) {
		this(material.toToolMaterial());
	}
}
