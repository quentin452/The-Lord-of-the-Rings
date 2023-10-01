package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;

public class LOTRItemPickaxe extends ItemPickaxe {
	public LOTRItemPickaxe(Item.ToolMaterial material) {
		super(material);
		setCreativeTab(LOTRCreativeTabs.tabTools);
	}

	public LOTRItemPickaxe(LOTRMaterial material) {
		this(material.toToolMaterial());
	}
}
