package lotr.common.inventory;

import cpw.mods.fml.relauncher.*;
import lotr.common.item.LOTRItemMug;
import lotr.common.recipe.LOTRBrewingRecipes;
import lotr.common.tileentity.LOTRTileEntityBarrel;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class LOTRSlotBarrel extends Slot {
	public LOTRTileEntityBarrel theBarrel;
	public boolean isWater;

	public LOTRSlotBarrel(LOTRTileEntityBarrel inv, int i, int j, int k) {
		super(inv, i, j, k);
		theBarrel = inv;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getBackgroundIconIndex() {
		IIcon iIcon;
		if (getSlotIndex() > 5) {
			iIcon = LOTRItemMug.barrelGui_emptyBucketSlotIcon;
		} else {
			iIcon = null;
		}
		return iIcon;
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		if (theBarrel.barrelMode == 0) {
			if (isWater) {
				return LOTRBrewingRecipes.isWaterSource(itemstack);
			}
			return true;
		}
		return false;
	}

	public LOTRSlotBarrel setWaterSource() {
		isWater = true;
		return this;
	}
}
