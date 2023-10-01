package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class LOTRBlockRhunFlower extends LOTRBlockFlower {
	public static String[] flowerNames = {"chrysBlue", "chrysOrange", "chrysPink", "chrysYellow", "chrysWhite"};
	@SideOnly(Side.CLIENT)
	public IIcon[] flowerIcons;

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (j >= flowerNames.length) {
			j = 0;
		}
		return flowerIcons[j];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < flowerNames.length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		flowerIcons = new IIcon[flowerNames.length];
		for (int i = 0; i < flowerNames.length; ++i) {
			flowerIcons[i] = iconregister.registerIcon(getTextureName() + "_" + flowerNames[i]);
		}
	}
}
