package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockColored;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class LOTRBlockClayTileDyed extends LOTRBlockClayTile {
	@SideOnly(Side.CLIENT)
	public IIcon[] clayIcons;

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (j >= 16) {
			j = 0;
		}
		return clayIcons[j];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < 16; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		clayIcons = new IIcon[16];
		for (int i = 0; i < clayIcons.length; ++i) {
			int dyeMeta = BlockColored.func_150031_c(i);
			clayIcons[i] = iconregister.registerIcon(getTextureName() + "_" + ItemDye.field_150923_a[dyeMeta]);
		}
	}
}
