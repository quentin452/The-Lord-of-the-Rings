package lotr.common.block;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;

public class LOTRBlockThatch extends Block {
	public static String[] thatchNames = {"thatch", "reed"};
	@SideOnly(Side.CLIENT)
	public IIcon[] thatchIcons;

	public LOTRBlockThatch() {
		super(Material.grass);
		setHardness(0.5f);
		setStepSound(Block.soundTypeGrass);
		setCreativeTab(LOTRCreativeTabs.tabBlock);
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (j >= thatchNames.length) {
			j = 0;
		}
		return thatchIcons[j];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < thatchNames.length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		thatchIcons = new IIcon[thatchNames.length];
		for (int i = 0; i < thatchNames.length; ++i) {
			thatchIcons[i] = iconregister.registerIcon(getTextureName() + "_" + thatchNames[i]);
		}
	}
}
