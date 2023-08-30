package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockSlab10 extends LOTRBlockSlabBase {
	public LOTRBlockSlab10(boolean flag) {
		super(flag, Material.rock, 8);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		j &= 7;
		if (j == 0) {
			return LOTRMod.pillar2.getIcon(i, 5);
		}
		switch (j) {
			case 1:
				return LOTRMod.brick5.getIcon(i, 4);
			case 2:
				return LOTRMod.brick5.getIcon(i, 5);
			case 3:
				return LOTRMod.brick5.getIcon(i, 7);
			case 4:
				return LOTRMod.pillar2.getIcon(i, 6);
			case 5:
				return LOTRMod.pillar2.getIcon(i, 7);
			case 6:
				return LOTRMod.whiteSandstone.getIcon(i, 0);
			case 7:
				return LOTRMod.rock.getIcon(i, 0);
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
