package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockSlab extends LOTRBlockSlabBase {
	public LOTRBlockSlab(boolean flag) {
		super(flag, Material.rock, 8);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		j &= 7;
		if (j == 0) {
			return LOTRMod.smoothStone.getIcon(i, 0);
		}
		switch (j) {
			case 1:
				return LOTRMod.brick.getIcon(i, 0);
			case 2:
				return LOTRMod.smoothStone.getIcon(i, 1);
			case 3:
				return LOTRMod.brick.getIcon(i, 1);
			case 4:
				return LOTRMod.brick.getIcon(i, 2);
			case 5:
				return LOTRMod.brick.getIcon(i, 3);
			case 6:
				return LOTRMod.brick.getIcon(i, 4);
			case 7:
				return LOTRMod.brick.getIcon(i, 6);
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
