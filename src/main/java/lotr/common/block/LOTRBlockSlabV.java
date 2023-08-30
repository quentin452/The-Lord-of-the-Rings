package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class LOTRBlockSlabV extends LOTRBlockSlabBase {
	public LOTRBlockSlabV(boolean flag) {
		super(flag, Material.rock, 6);
		setCreativeTab(CreativeTabs.tabBlock);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		j &= 7;
		if (j == 0) {
			return Blocks.stonebrick.getIcon(i, 1);
		}
		switch (j) {
			case 1:
				return Blocks.stonebrick.getIcon(i, 2);
			case 2:
				return LOTRMod.redBrick.getIcon(i, 0);
			case 3:
				return LOTRMod.redBrick.getIcon(i, 1);
			case 4:
				return Blocks.mossy_cobblestone.getIcon(i, 0);
			case 5:
				return Blocks.stone.getIcon(i, 0);
			default:
				break;
		}
		return super.getIcon(i, j);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}
}
