package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class LOTRBlockSlabSand extends LOTRBlockSlabFalling {
	public LOTRBlockSlabSand(boolean flag) {
		super(flag, Material.sand, 3);
		setHardness(0.5f);
		setStepSound(Block.soundTypeSand);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		j &= 7;
		switch (j) {
			case 0:
				return Blocks.sand.getIcon(i, 0);
			case 1:
				return Blocks.sand.getIcon(i, 1);
			case 2:
				return LOTRMod.whiteSand.getIcon(i, 0);
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
