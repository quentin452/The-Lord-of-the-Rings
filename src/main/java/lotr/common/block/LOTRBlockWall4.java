package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import net.minecraft.util.IIcon;

public class LOTRBlockWall4 extends LOTRBlockWallBase {
	public LOTRBlockWall4() {
		super(LOTRMod.brick, 16);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		switch (j) {
			case 0:
				return LOTRMod.brick4.getIcon(i, 0);
			case 1:
				return LOTRMod.brick4.getIcon(i, 1);
			case 2:
				return LOTRMod.brick4.getIcon(i, 2);
			case 3:
				return LOTRMod.brick4.getIcon(i, 3);
			case 4:
				return LOTRMod.brick4.getIcon(i, 4);
			case 5:
				return LOTRMod.brick4.getIcon(i, 5);
			case 6:
				return LOTRMod.brick4.getIcon(i, 14);
			case 7:
				return LOTRMod.brick5.getIcon(i, 8);
			case 8:
				return LOTRMod.brick5.getIcon(i, 9);
			case 9:
				return LOTRMod.brick5.getIcon(i, 10);
			case 10:
				return LOTRMod.brick5.getIcon(i, 13);
			case 11:
				return LOTRMod.brick5.getIcon(i, 14);
			case 12:
				return LOTRMod.brick5.getIcon(i, 15);
			case 13:
				return LOTRMod.brick6.getIcon(i, 1);
			case 14:
				return LOTRMod.brick6.getIcon(i, 3);
			case 15:
				return LOTRMod.brick6.getIcon(i, 4);
			default:
				break;
		}
		return super.getIcon(i, j);
	}
}
