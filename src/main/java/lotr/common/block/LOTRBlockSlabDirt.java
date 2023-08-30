package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class LOTRBlockSlabDirt extends LOTRBlockSlabBase {
	public LOTRBlockSlabDirt(boolean flag) {
		super(flag, Material.ground, 5);
		setHardness(0.5f);
		setStepSound(Block.soundTypeGravel);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		j &= 7;
		if (j == 0) {
			return Blocks.dirt.getIcon(i, 0);
		}
		switch (j) {
			case 1:
				return LOTRMod.dirtPath.getIcon(i, 0);
			case 2:
				return LOTRMod.mud.getIcon(i, 0);
			case 3:
				return LOTRMod.mordorDirt.getIcon(i, 0);
			case 4:
				return LOTRMod.dirtPath.getIcon(i, 1);
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
