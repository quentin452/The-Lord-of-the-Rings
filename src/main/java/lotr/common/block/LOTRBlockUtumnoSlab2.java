package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockUtumnoSlab2 extends LOTRBlockUtumnoSlabBase {
	public LOTRBlockUtumnoSlab2(boolean flag) {
		super(flag, 3);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		j &= 7;
		switch (j) {
			case 0:
				return LOTRMod.utumnoBrick.getIcon(i, 6);
			case 1:
				return LOTRMod.utumnoBrick.getIcon(i, 7);
			case 2:
				return LOTRMod.utumnoBrick.getIcon(i, 8);
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
