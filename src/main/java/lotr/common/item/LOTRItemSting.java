package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class LOTRItemSting extends LOTRItemDagger implements LOTRStoryItem {
	public LOTRItemSting() {
		super(LOTRMaterial.HIGH_ELVEN);
		setMaxDamage(700);
		setCreativeTab(LOTRCreativeTabs.tabStory);
		lotrWeaponDamage += 1.0f;
	}

	@Override
	public float func_150893_a(ItemStack itemstack, Block block) {
		if (block == LOTRMod.webUngoliant) {
			return 15.0f;
		}
		return super.func_150893_a(itemstack, block);
	}
}
