package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import net.minecraft.util.IIcon;

public class LOTRBlockWall5 extends LOTRBlockWallBase {
	public LOTRBlockWall5() {
		super(LOTRMod.brick, 5);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int i, int j) {
		switch (j) {
			case 0:
				return LOTRMod.brick6.getIcon(i, 6);
			case 1:
				return LOTRMod.brick6.getIcon(i, 7);
			case 2:
				return LOTRMod.brick6.getIcon(i, 10);
			case 3:
				return LOTRMod.brick6.getIcon(i, 11);
			case 4:
				return LOTRMod.brick6.getIcon(i, 13);
			default:
				break;
		}
		return super.getIcon(i, j);
	}
}
