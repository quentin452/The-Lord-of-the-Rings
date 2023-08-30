package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.client.render.LOTRConnectedTextures;
import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntityGulduril;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockOreStorage extends LOTRBlockOreStorageBase implements LOTRConnectedBlock {
	@SideOnly(Side.CLIENT)
	public IIcon orcSteelSideIcon;
	@SideOnly(Side.CLIENT)
	public IIcon urukSteelSideIcon;
	@SideOnly(Side.CLIENT)
	public IIcon morgulSteelSideIcon;

	public LOTRBlockOreStorage() {
		setOreStorageNames("copper", "tin", "bronze", "silver", "mithril", "orcSteel", "quendite", "dwarfSteel", "galvorn", "urukSteel", "naurite", "gulduril", "morgulSteel", "sulfur", "saltpeter", "blueDwarfSteel");
	}

	@Override
	public boolean areBlocksConnected(IBlockAccess world, int i, int j, int k, int i1, int j1, int k1) {
		return LOTRConnectedBlock.Checks.matchBlockAndMeta(this, world, i, j, k, i1, j1, k1);
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		if (hasTileEntity(metadata)) {
			return new LOTRTileEntityGulduril();
		}
		return null;
	}

	@Override
	public String getConnectedName(int meta) {
		return textureName + "_" + oreStorageNames[meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int i, int j, int k, int side) {
		int meta = world.getBlockMetadata(i, j, k);
		if (meta == 4) {
			return LOTRConnectedTextures.getConnectedIconBlock(this, world, i, j, k, side, false);
		}
		return super.getIcon(world, i, j, k, side);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta) {
		if (meta == 4) {
			return LOTRConnectedTextures.getConnectedIconItem(this, 4);
		}
		if (meta == 5 && side > 1) {
			return orcSteelSideIcon;
		}
		if (meta == 9 && side > 1) {
			return urukSteelSideIcon;
		}
		if (meta == 12 && side > 1) {
			return morgulSteelSideIcon;
		}
		return super.getIcon(side, meta);
	}

	@Override
	public int getLightValue(IBlockAccess world, int i, int j, int k) {
		if (world.getBlockMetadata(i, j, k) == 6) {
			return LOTRMod.oreQuendite.getLightValue();
		}
		if (world.getBlockMetadata(i, j, k) == 10) {
			return LOTRMod.oreNaurite.getLightValue();
		}
		if (world.getBlockMetadata(i, j, k) == 11) {
			return LOTRMod.oreGulduril.getLightValue();
		}
		return 0;
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return metadata == 11;
	}

	@Override
	public boolean isFireSource(World world, int i, int j, int k, ForgeDirection side) {
		return world.getBlockMetadata(i, j, k) == 13 && side == ForgeDirection.UP;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		oreStorageIcons = new IIcon[oreStorageNames.length];
		for (int i = 0; i < oreStorageNames.length; ++i) {
			if (i == 4) {
				LOTRConnectedTextures.registerConnectedIcons(iconregister, this, 4, false);
				continue;
			}
			oreStorageIcons[i] = iconregister.registerIcon(getTextureName() + "_" + oreStorageNames[i]);
		}
		orcSteelSideIcon = iconregister.registerIcon(getTextureName() + "_orcSteel_side");
		urukSteelSideIcon = iconregister.registerIcon(getTextureName() + "_urukSteel_side");
		morgulSteelSideIcon = iconregister.registerIcon(getTextureName() + "_morgulSteel_side");
	}
}
