package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.world.IBlockAccess;

public class LOTRBlockFangornRiverweed extends BlockLilyPad {
	public LOTRBlockFangornRiverweed() {
		setCreativeTab(LOTRCreativeTabs.tabDeco);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockAccess world, int i, int j, int k) {
		return 16777215;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getBlockColor() {
		return 16777215;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getRenderColor(int meta) {
		return 16777215;
	}
}
