package lotr.common.item;

import cpw.mods.fml.relauncher.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class LOTRItemKaftan extends LOTRItemHaradRobes {
	@SideOnly(Side.CLIENT)
	public IIcon overlayIcon;

	public LOTRItemKaftan(int slot) {
		super(LOTRMaterial.KAFTAN, slot);
	}

	@Override
	public int getColor(ItemStack itemstack) {
		return LOTRItemHaradRobes.getRobesColor(itemstack);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getColorFromItemStack(ItemStack itemstack, int pass) {
		if (pass >= 1) {
			return 16777215;
		}
		return super.getColorFromItemStack(itemstack, pass);
	}

	@Override
	public IIcon getIcon(ItemStack itemstack, int pass) {
		if (pass >= 1) {
			return overlayIcon;
		}
		return super.getIcon(itemstack, pass);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconregister) {
		super.registerIcons(iconregister);
		overlayIcon = iconregister.registerIcon(getIconString() + "_overlay");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
}
