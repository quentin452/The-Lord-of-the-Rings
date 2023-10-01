package lotr.common.tileentity;

import lotr.common.LOTRMod;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTRTileEntityOrcForge extends LOTRTileEntityAlloyForgeBase {
	@Override
	public ItemStack getAlloySmeltingResult(ItemStack itemstack, ItemStack alloyItem) {
		if (isIron(itemstack) && isCoal(alloyItem)) {
			return new ItemStack(LOTRMod.urukSteel);
		}
		if (isOrcSteel(itemstack) && alloyItem.getItem() == LOTRMod.guldurilCrystal) {
			return new ItemStack(LOTRMod.morgulSteel);
		}
		if (isOrcSteel(itemstack) && alloyItem.getItem() == LOTRMod.nauriteGem) {
			return new ItemStack(LOTRMod.blackUrukSteel);
		}
		return super.getAlloySmeltingResult(itemstack, alloyItem);
	}

	@Override
	public String getForgeName() {
		return StatCollector.translateToLocal("container.lotr.orcForge");
	}

	@Override
	public ItemStack getSmeltingResult(ItemStack itemstack) {
		if (isWood(itemstack)) {
			boolean isCharred;
			isCharred = itemstack.getItem() == Item.getItemFromBlock(LOTRMod.wood) && itemstack.getItemDamage() == 3;
			if (!isCharred) {
				return new ItemStack(LOTRMod.wood, 1, 3);
			}
		}
		if (itemstack.getItem() == Item.getItemFromBlock(LOTRMod.oreMorgulIron)) {
			return new ItemStack(LOTRMod.orcSteel);
		}
		if (itemstack.getItem() instanceof ItemFood && ((ItemFood) itemstack.getItem()).isWolfsFavoriteMeat()) {
			return new ItemStack(Items.rotten_flesh);
		}
		return super.getSmeltingResult(itemstack);
	}
}
