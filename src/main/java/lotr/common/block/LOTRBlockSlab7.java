package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockSlab7 extends LOTRBlockSlabBase {
	public LOTRBlockSlab7(boolean flag) {
		super(flag, Material.rock, 8);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		j &= 7;
		if (j == 0) {
			return LOTRMod.brick3.getIcon(i, 10);
		}
		switch (j) {
			case 1:
				return LOTRMod.brick3.getIcon(i, 11);
			case 2:
				return LOTRMod.brick3.getIcon(i, 13);
			case 3:
				return LOTRMod.brick3.getIcon(i, 14);
			case 4:
				return LOTRMod.pillar.getIcon(i, 15);
			case 5:
				return LOTRMod.redSandstone.getIcon(i, 0);
			case 6:
				return LOTRMod.brick4.getIcon(i, 5);
			case 7:
				return LOTRMod.pillar2.getIcon(i, 0);
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
