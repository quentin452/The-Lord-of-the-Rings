package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.client.render.LOTRConnectedTextures;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class LOTRBlockBrick extends LOTRBlockBrickBase implements LOTRConnectedBlock {
	@SideOnly(Side.CLIENT)
	public IIcon iconRohanSide;

	public LOTRBlockBrick() {
		setBrickNames("mordor", "gondor", "gondorMossy", "gondorCracked", "rohan", "gondorCarved", "dwarven", "mordorCracked", "dwarvenSilver", "dwarvenGold", "dwarvenMithril", "galadhrim", "galadhrimMossy", "galadhrimCracked", "blueRock", "nearHarad");
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
		int meta = world.getBlockMetadata(i, j, k);
		if (meta == 8 || meta == 9 || meta == 10) {
			return LOTRConnectedTextures.getConnectedIconBlock(this, world, i, j, k, side, false);
		}
		return super.getIcon(world, i, j, k, side);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (j == 8 || j == 9 || j == 10) {
			return LOTRConnectedTextures.getConnectedIconItem(this, j);
		}
		if (j == 4 && i != 0 && i != 1) {
			return iconRohanSide;
		}
		return super.getIcon(i, j);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		brickIcons = new IIcon[brickNames.length];
		for (int i = 0; i < brickNames.length; ++i) {
			if (i == 8 || i == 9 || i == 10) {
				LOTRConnectedTextures.registerConnectedIcons(iconregister, this, i, false);
				continue;
			}
			brickIcons[i] = iconregister.registerIcon(getTextureName() + "_" + brickNames[i]);
			if (i != 4) {
				continue;
			}
			iconRohanSide = iconregister.registerIcon(getTextureName() + "_" + brickNames[i] + "_side");
		}
	}
}
