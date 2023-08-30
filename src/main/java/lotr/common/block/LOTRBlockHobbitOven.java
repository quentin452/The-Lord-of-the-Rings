package lotr.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.tileentity.LOTRTileEntityHobbitOven;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class LOTRBlockHobbitOven extends BlockContainer {
	@SideOnly(Side.CLIENT)
	public IIcon[] ovenIcons;

	public LOTRBlockHobbitOven() {
		super(Material.rock);
		setCreativeTab(LOTRCreativeTabs.tabUtil);
		setHardness(3.5f);
		setStepSound(Block.soundTypeStone);
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, Block block, int meta) {
		IInventory oven = (IInventory) world.getTileEntity(i, j, k);
		if (oven != null) {
			LOTRMod.dropContainerItems(oven, world, i, j, k);
			world.func_147453_f(i, j, k, block);
		}
		super.breakBlock(world, i, j, k, block, meta);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new LOTRTileEntityHobbitOven();
	}

	@Override
	public int getComparatorInputOverride(World world, int i, int j, int k, int direction) {
		return Container.calcRedstoneFromInventory((IInventory) world.getTileEntity(i, j, k));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(IBlockAccess world, int i, int j, int k, int side) {
		if (side == 1 || side == 0) {
			return ovenIcons[1];
		}
		int meta = world.getBlockMetadata(i, j, k) & 7;
		return side != meta ? ovenIcons[0] : isOvenActive(world, i, j, k) ? ovenIcons[3] : ovenIcons[2];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return i == 1 || i == 0 ? ovenIcons[1] : i == 3 ? ovenIcons[2] : ovenIcons[0];
	}

	@Override
	public int getLightValue(IBlockAccess world, int i, int j, int k) {
		return isOvenActive(world, i, j, k) ? 13 : 0;
	}

	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
		if (!world.isRemote) {
			entityplayer.openGui(LOTRMod.instance, 0, world, i, j, k);
		}
		return true;
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
		super.onBlockAdded(world, i, j, k);
		setDefaultDirection(world, i, j, k);
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entity, ItemStack itemstack) {
		int rotation = MathHelper.floor_double(entity.rotationYaw * 4.0f / 360.0f + 0.5) & 3;
		if (rotation == 0) {
			world.setBlockMetadataWithNotify(i, j, k, 2, 2);
		}
		if (rotation == 1) {
			world.setBlockMetadataWithNotify(i, j, k, 5, 2);
		}
		if (rotation == 2) {
			world.setBlockMetadataWithNotify(i, j, k, 3, 2);
		}
		if (rotation == 3) {
			world.setBlockMetadataWithNotify(i, j, k, 4, 2);
		}
		if (itemstack.hasDisplayName()) {
			((LOTRTileEntityHobbitOven) world.getTileEntity(i, j, k)).setOvenName(itemstack.getDisplayName());
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if (isOvenActive(world, i, j, k)) {
			int meta = world.getBlockMetadata(i, j, k) & 7;
			float f = i + 0.5f;
			float f1 = j + 0.0f + random.nextFloat() * 6.0f / 16.0f;
			float f2 = k + 0.5f;
			float f3 = 0.52f;
			float f4 = random.nextFloat() * 0.6f - 0.3f;
			switch (meta) {
				case 4:
					world.spawnParticle("smoke", f - f3, f1, f2 + f4, 0.0, 0.0, 0.0);
					world.spawnParticle("flame", f - f3, f1, f2 + f4, 0.0, 0.0, 0.0);
					break;
				case 5:
					world.spawnParticle("smoke", f + f3, f1, f2 + f4, 0.0, 0.0, 0.0);
					world.spawnParticle("flame", f + f3, f1, f2 + f4, 0.0, 0.0, 0.0);
					break;
				case 2:
					world.spawnParticle("smoke", f + f4, f1, f2 - f3, 0.0, 0.0, 0.0);
					world.spawnParticle("flame", f + f4, f1, f2 - f3, 0.0, 0.0, 0.0);
					break;
				case 3:
					world.spawnParticle("smoke", f + f4, f1, f2 + f3, 0.0, 0.0, 0.0);
					world.spawnParticle("flame", f + f4, f1, f2 + f3, 0.0, 0.0, 0.0);
					break;
				default:
					break;
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		ovenIcons = new IIcon[4];
		ovenIcons[0] = iconregister.registerIcon(getTextureName() + "_side");
		ovenIcons[1] = iconregister.registerIcon(getTextureName() + "_top");
		ovenIcons[2] = iconregister.registerIcon(getTextureName() + "_front");
		ovenIcons[3] = iconregister.registerIcon(getTextureName() + "_active");
	}

	public void setDefaultDirection(World world, int i, int j, int k) {
		if (!world.isRemote) {
			Block i1 = world.getBlock(i, j, k - 1);
			Block j1 = world.getBlock(i, j, k + 1);
			Block k1 = world.getBlock(i - 1, j, k);
			Block l1 = world.getBlock(i + 1, j, k);
			int meta = 3;
			if (i1.isOpaqueCube() && !j1.isOpaqueCube()) {
				meta = 3;
			}
			if (j1.isOpaqueCube() && !i1.isOpaqueCube()) {
				meta = 2;
			}
			if (k1.isOpaqueCube() && !l1.isOpaqueCube()) {
				meta = 5;
			}
			if (l1.isOpaqueCube() && !k1.isOpaqueCube()) {
				meta = 4;
			}
			world.setBlockMetadataWithNotify(i, j, k, meta, 2);
		}
	}

	public static boolean isOvenActive(IBlockAccess world, int i, int j, int k) {
		int meta = world.getBlockMetadata(i, j, k);
		return meta > 7;
	}

	public static void setOvenActive(World world, int i, int j, int k) {
		int meta = world.getBlockMetadata(i, j, k);
		world.setBlockMetadataWithNotify(i, j, k, meta ^ 8, 2);
		world.updateLightByType(EnumSkyBlock.Block, i, j, k);
	}
}
