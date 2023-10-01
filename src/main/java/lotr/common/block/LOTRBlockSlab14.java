package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockSlab14 extends LOTRBlockSlabBase {
	public LOTRBlockSlab14(boolean flag) {
		super(flag, Material.rock, 5);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int i, int j) {
		j &= 7;
		if (j == 0) {
			return LOTRMod.pillar2.getIcon(i, 14);
		}
		switch (j) {
			case 1:
				return LOTRMod.brick6.getIcon(i, 11);
			case 2:
				return LOTRMod.brick6.getIcon(i, 13);
			case 3:
				return LOTRMod.pillar2.getIcon(i, 15);
			case 4:
				return LOTRMod.pillar3.getIcon(i, 0);
			default:
				break;
		}
		return super.getIcon(i, j);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconregister) {
	}
}
