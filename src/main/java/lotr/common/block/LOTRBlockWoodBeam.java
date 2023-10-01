package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public abstract class LOTRBlockWoodBeam extends BlockRotatedPillar {
	@SideOnly(Side.CLIENT)
	public IIcon[] sideIcons;
	@SideOnly(Side.CLIENT)
	public IIcon[] topIcons;
	public String[] woodNames;

	protected LOTRBlockWoodBeam() {
		super(Material.wood);
		setCreativeTab(LOTRCreativeTabs.tabBlock);
		setHardness(2.0f);
		setStepSound(Block.soundTypeWood);
	}

	@Override
	public int getRenderType() {
		return LOTRMod.proxy.getBeamRenderID();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getSideIcon(int i) {
		if (i < 0 || i >= woodNames.length) {
			i = 0;
		}
		return sideIcons[i];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int j = 0; j < woodNames.length; ++j) {
			list.add(new ItemStack(item, 1, j));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getTopIcon(int i) {
		if (i < 0 || i >= woodNames.length) {
			i = 0;
		}
		return topIcons[i];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		sideIcons = new IIcon[woodNames.length];
		topIcons = new IIcon[woodNames.length];
		for (int i = 0; i < woodNames.length; ++i) {
			topIcons[i] = iconregister.registerIcon(getTextureName() + "_" + woodNames[i] + "_top");
			sideIcons[i] = iconregister.registerIcon(getTextureName() + "_" + woodNames[i] + "_side");
		}
	}

	public void setWoodNames(String... s) {
		woodNames = s;
	}
}
