package lotr.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.tileentity.LOTRTileEntityMillstone;
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

public class LOTRBlockMillstone extends BlockContainer {
	@SideOnly(Side.CLIENT)
	public IIcon iconSide;
	@SideOnly(Side.CLIENT)
	public IIcon iconTop;
	@SideOnly(Side.CLIENT)
	public IIcon iconSideActive;
	@SideOnly(Side.CLIENT)
	public IIcon iconTopActive;

	public LOTRBlockMillstone() {
		super(Material.rock);
		setCreativeTab(LOTRCreativeTabs.tabUtil);
		setHardness(4.0f);
		setStepSound(Block.soundTypeStone);
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, Block block, int meta) {
		IInventory millstone = (IInventory) world.getTileEntity(i, j, k);
		if (millstone != null) {
			LOTRMod.dropContainerItems(millstone, world, i, j, k);
			world.func_147453_f(i, j, k, block);
		}
		super.breakBlock(world, i, j, k, block, meta);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new LOTRTileEntityMillstone();
	}

	@Override
	public int getComparatorInputOverride(World world, int i, int j, int k, int direction) {
		return Container.calcRedstoneFromInventory((IInventory) world.getTileEntity(i, j, k));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(IBlockAccess world, int i, int j, int k, int side) {
		boolean active = isMillstoneActive(world, i, j, k);
		if (side == 1 || side == 0) {
			return active ? iconTopActive : iconTop;
		}
		return active ? iconSideActive : iconSide;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return i == 1 || i == 0 ? iconTop : iconSide;
	}

	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
		if (!world.isRemote) {
			entityplayer.openGui(LOTRMod.instance, 52, world, i, j, k);
		}
		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entity, ItemStack itemstack) {
		if (itemstack.hasDisplayName()) {
			((LOTRTileEntityMillstone) world.getTileEntity(i, j, k)).setSpecialMillstoneName(itemstack.getDisplayName());
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if (isMillstoneActive(world, i, j, k)) {
			for (int l = 0; l < 6; ++l) {
				float f10 = 0.5f + MathHelper.randomFloatClamp(random, -0.2f, 0.2f);
				float f11 = 0.5f + MathHelper.randomFloatClamp(random, -0.2f, 0.2f);
				float f12 = 0.9f + random.nextFloat() * 0.2f;
				world.spawnParticle("smoke", i + f10, j + f12, k + f11, 0.0, 0.0, 0.0);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		iconSide = iconregister.registerIcon(getTextureName() + "_side");
		iconTop = iconregister.registerIcon(getTextureName() + "_top");
		iconSideActive = iconregister.registerIcon(getTextureName() + "_side_active");
		iconTopActive = iconregister.registerIcon(getTextureName() + "_top_active");
	}

	public static boolean isMillstoneActive(IBlockAccess world, int i, int j, int k) {
		int meta = world.getBlockMetadata(i, j, k);
		return (meta & 8) != 0;
	}

	public static void toggleMillstoneActive(World world, int i, int j, int k) {
		int meta = world.getBlockMetadata(i, j, k);
		world.setBlockMetadataWithNotify(i, j, k, meta ^ 8, 2);
	}
}
