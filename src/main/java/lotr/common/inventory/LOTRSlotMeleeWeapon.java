package lotr.common.inventory;

import cpw.mods.fml.relauncher.*;
import lotr.common.item.LOTRWeaponStats;
import lotr.common.util.LOTRCommonIcons;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class LOTRSlotMeleeWeapon extends Slot {
	public LOTRSlotMeleeWeapon(IInventory inv, int i, int j, int k) {
		super(inv, i, j, k);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getBackgroundIconIndex() {
		return LOTRCommonIcons.iconMeleeWeapon;
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return LOTRWeaponStats.isMeleeWeapon(itemstack);
	}
}
