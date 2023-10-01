package lotr.common.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.block.LOTRBlockSignCarved;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class LOTRTileEntitySignCarved extends LOTRTileEntitySign {
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 1600.0;
	}

	@Override
	public int getNumLines() {
		return 8;
	}

	public IIcon getOnBlockIcon() {
		World world = getWorldObj();
		Block block = getBlockType();
		if (block instanceof LOTRBlockSignCarved) {
			LOTRBlockSignCarved signBlock = (LOTRBlockSignCarved) block;
			int meta = getBlockMetadata();
			int i = xCoord;
			int j = yCoord;
			int k = zCoord;
			return signBlock.getOnBlockIcon(world, i, j, k, meta);
		}
		return Blocks.stone.getIcon(0, 0);
	}
}
