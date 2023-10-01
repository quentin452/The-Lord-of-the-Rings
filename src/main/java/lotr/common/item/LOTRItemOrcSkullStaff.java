package lotr.common.item;

import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

public class LOTRItemOrcSkullStaff extends LOTRItemSword {
	public LOTRItemOrcSkullStaff() {
		super(LOTRMaterial.MORDOR);
	}

	@Override
	public boolean getIsRepairable(ItemStack itemstack, ItemStack repairItem) {
		return repairItem.getItem() == Items.skull;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return null;
	}
}
