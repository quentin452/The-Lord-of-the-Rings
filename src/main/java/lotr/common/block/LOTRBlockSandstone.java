package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockSandstone extends Block {
	@SideOnly(Side.CLIENT)
	public IIcon iconTop;
	@SideOnly(Side.CLIENT)
	public IIcon iconBottom;

	public LOTRBlockSandstone() {
		super(Material.rock);
		setCreativeTab(LOTRCreativeTabs.tabBlock);
		setStepSound(Block.soundTypeStone);
		setHardness(0.8f);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (i == 0) {
			return iconBottom;
		}
		if (i == 1) {
			return iconTop;
		}
		return blockIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		super.registerBlockIcons(iconregister);
		iconTop = iconregister.registerIcon(getTextureName() + "_top");
		iconBottom = iconregister.registerIcon(getTextureName() + "_bottom");
	}
}
