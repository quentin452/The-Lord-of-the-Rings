package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.util.IIcon;

public class LOTRBlockScorchedWall extends LOTRBlockWallBase {
	public LOTRBlockScorchedWall() {
		super(LOTRMod.scorchedStone, 1);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return LOTRMod.scorchedStone.getIcon(i, j);
	}
}
