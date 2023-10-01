package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.util.IIcon;

public class LOTRBlockWall2 extends LOTRBlockWallBase {
	public LOTRBlockWall2() {
		super(LOTRMod.brick, 16);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		switch (j) {
			case 0:
				return LOTRMod.brick2.getIcon(i, 0);
			case 1:
				return LOTRMod.brick2.getIcon(i, 1);
			case 2:
				return LOTRMod.rock.getIcon(i, 4);
			case 3:
				return LOTRMod.brick2.getIcon(i, 2);
			case 4:
				return LOTRMod.brick2.getIcon(i, 3);
			case 5:
				return LOTRMod.brick2.getIcon(i, 4);
			case 6:
				return LOTRMod.brick2.getIcon(i, 5);
			case 7:
				return LOTRMod.brick2.getIcon(i, 7);
			case 8:
				return LOTRMod.brick2.getIcon(i, 8);
			case 9:
				return LOTRMod.brick2.getIcon(i, 9);
			case 10:
				return LOTRMod.brick2.getIcon(i, 11);
			case 11:
				return LOTRMod.brick3.getIcon(i, 2);
			case 12:
				return LOTRMod.brick3.getIcon(i, 3);
			case 13:
				return LOTRMod.brick3.getIcon(i, 4);
			case 14:
				return LOTRMod.brick3.getIcon(i, 9);
			case 15:
				return LOTRMod.brick3.getIcon(i, 10);
			default:
				break;
		}
		return super.getIcon(i, j);
	}
}
