package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.client.render.LOTRConnectedTextures;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class LOTRBlockCobblebrick extends LOTRBlockBrickBase implements LOTRConnectedBlock {
	public LOTRBlockCobblebrick() {
		setBrickNames("cob");
	}

	@Override
	public boolean areBlocksConnected(IBlockAccess world, int i, int j, int k, int i1, int j1, int k1) {
		return LOTRConnectedBlock.Checks.matchBlockAndMeta(this, world, i, j, k, i1, j1, k1);
	}

	@Override
	public String getConnectedName(int meta) {
		return textureName + "_" + brickNames[meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int i, int j, int k, int side) {
		return LOTRConnectedTextures.getConnectedIconBlock(this, world, i, j, k, side, false);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		boolean[][] adjacentFlags = i == 0 || i == 1 ? new boolean[][]{{false, false, false}, {false, true, false}, {false, false, false}} : new boolean[][]{{false, true, false}, {false, true, false}, {false, true, false}};
		return LOTRConnectedTextures.getConnectedIconItem(this, j, adjacentFlags);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		brickIcons = new IIcon[brickNames.length];
		for (int i = 0; i < brickNames.length; ++i) {
			LOTRConnectedTextures.registerConnectedIcons(iconregister, this, i, false);
		}
	}
}
