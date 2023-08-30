package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockSlab5 extends LOTRBlockSlabBase {
	public LOTRBlockSlab5(boolean flag) {
		super(flag, Material.rock, 8);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		j &= 7;
		if (j == 0) {
			return LOTRMod.pillar.getIcon(i, 6);
		}
		switch (j) {
			case 1:
				return LOTRMod.pillar.getIcon(i, 7);
			case 2:
				return LOTRMod.pillar.getIcon(i, 8);
			case 3:
				return LOTRMod.brick2.getIcon(i, 11);
			case 4:
				return LOTRMod.pillar.getIcon(i, 9);
			case 5:
				return LOTRMod.brick3.getIcon(i, 2);
			case 6:
				return LOTRMod.brick3.getIcon(i, 3);
			case 7:
				return LOTRMod.brick3.getIcon(i, 4);
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
