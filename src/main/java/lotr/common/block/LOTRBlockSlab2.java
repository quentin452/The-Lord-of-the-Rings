package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockSlab2 extends LOTRBlockSlabBase {
	public LOTRBlockSlab2(boolean flag) {
		super(flag, Material.rock, 8);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		j &= 7;
		if (j == 0) {
			return LOTRMod.pillar.getIcon(i, 0);
		}
		switch (j) {
			case 1:
				return LOTRMod.smoothStone.getIcon(i, 2);
			case 2:
				return LOTRMod.brick.getIcon(i, 7);
			case 3:
				return LOTRMod.brick.getIcon(i, 11);
			case 4:
				return LOTRMod.brick.getIcon(i, 12);
			case 5:
				return LOTRMod.brick.getIcon(i, 13);
			case 6:
				return LOTRMod.pillar.getIcon(i, 1);
			case 7:
				return LOTRMod.pillar.getIcon(i, 2);
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
