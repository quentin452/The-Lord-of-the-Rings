package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import net.minecraft.util.IIcon;

public class LOTRBlockWallClayTileDyed extends LOTRBlockWallBase {
	public LOTRBlockWallClayTileDyed() {
		super(LOTRMod.clayTileDyed, 16);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return LOTRMod.clayTileDyed.getIcon(i, j);
	}
}
