package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import net.minecraft.util.IIcon;

public class LOTRBlockWallBone extends LOTRBlockWallBase {
	public LOTRBlockWallBone() {
		super(LOTRMod.boneBlock, 1);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (j == 0) {
			return LOTRMod.boneBlock.getIcon(i, 0);
		}
		return super.getIcon(i, j);
	}
}
