package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import lotr.common.world.LOTRWorldProviderUtumno;
import net.minecraft.util.IIcon;

public class LOTRBlockUtumnoWall extends LOTRBlockWallBase implements LOTRWorldProviderUtumno.UtumnoBlock {
	public LOTRBlockUtumnoWall() {
		super(LOTRMod.utumnoBrick, 6);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		switch (j) {
			case 0:
				return LOTRMod.utumnoBrick.getIcon(i, 0);
			case 1:
				return LOTRMod.utumnoBrick.getIcon(i, 2);
			case 2:
				return LOTRMod.utumnoBrick.getIcon(i, 4);
			case 3:
				return LOTRMod.utumnoBrick.getIcon(i, 6);
			case 4:
				return LOTRMod.utumnoBrick.getIcon(i, 7);
			case 5:
				return LOTRMod.utumnoBrick.getIcon(i, 8);
			default:
				break;
		}
		return super.getIcon(i, j);
	}
}
