package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockSlab12 extends LOTRBlockSlabBase {
	public LOTRBlockSlab12(boolean flag) {
		super(flag, Material.rock, 8);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		j &= 7;
		if (j == 0) {
			return LOTRMod.brick5.getIcon(i, 11);
		}
		switch (j) {
			case 1:
				return LOTRMod.brick5.getIcon(i, 13);
			case 2:
				return LOTRMod.brick5.getIcon(i, 14);
			case 3:
				return LOTRMod.brick5.getIcon(i, 15);
			case 4:
				return LOTRMod.pillar2.getIcon(i, 8);
			case 5:
				return LOTRMod.brick6.getIcon(i, 1);
			case 6:
				return LOTRMod.pillar2.getIcon(i, 9);
			case 7:
				return LOTRMod.brick6.getIcon(i, 10);
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
