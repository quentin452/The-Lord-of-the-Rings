package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockSlab11 extends LOTRBlockSlabBase {
	public LOTRBlockSlab11(boolean flag) {
		super(flag, Material.rock, 8);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		j &= 7;
		if (j == 0) {
			return LOTRMod.brick5.getIcon(i, 8);
		}
		switch (j) {
			case 1:
				return LOTRMod.brick5.getIcon(i, 9);
			case 2:
				return LOTRMod.brick5.getIcon(i, 10);
			case 3:
				return LOTRMod.rock.getIcon(i, 1);
			case 4:
				return LOTRMod.rock.getIcon(i, 2);
			case 5:
				return LOTRMod.rock.getIcon(i, 3);
			case 6:
				return LOTRMod.rock.getIcon(i, 4);
			case 7:
				return LOTRMod.rock.getIcon(i, 5);
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
