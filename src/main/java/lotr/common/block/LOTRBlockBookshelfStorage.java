package lotr.common.block;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntityBookshelf;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class LOTRBlockBookshelfStorage extends BlockContainer {
	public LOTRBlockBookshelfStorage() {
		super(Material.wood);
		setHardness(1.5f);
		setStepSound(Block.soundTypeWood);
		setCreativeTab(null);
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, Block block, int meta) {
		IInventory bookshelf = (IInventory) world.getTileEntity(i, j, k);
		if (bookshelf != null) {
			LOTRMod.dropContainerItems(bookshelf, world, i, j, k);
			world.func_147453_f(i, j, k, block);
		}
		super.breakBlock(world, i, j, k, block, meta);
	}

	@Override
	public boolean canSilkHarvest() {
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new LOTRTileEntityBookshelf();
	}

	@Override
	public ItemStack createStackedBlock(int i) {
		return new ItemStack(Blocks.bookshelf);
	}

	@Override
	public int getComparatorInputOverride(World world, int i, int j, int k, int direction) {
		return Container.calcRedstoneFromInventory((IInventory) world.getTileEntity(i, j, k));
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int i, int j, int k, int meta, int fortune) {
		return Blocks.bookshelf.getDrops(world, i, j, k, meta, fortune);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return Blocks.bookshelf.getIcon(i, j);
	}

	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
		if (!canOpenBookshelf(world, i, j, k, entityplayer)) {
			return false;
		}
		if (!world.isRemote) {
			entityplayer.openGui(LOTRMod.instance, 55, world, i, j, k);
		}
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}

	public static boolean canOpenBookshelf(World world, int i, int j, int k, EntityPlayer entityplayer) {
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		return itemstack == null || itemstack.getItem() != Item.getItemFromBlock(Blocks.bookshelf);
	}
}
