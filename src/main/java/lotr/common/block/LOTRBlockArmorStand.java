package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntityArmorStand;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class LOTRBlockArmorStand extends Block {
	public LOTRBlockArmorStand() {
		super(Material.circuits);
		setHardness(0.5f);
		setResistance(1.0f);
		setStepSound(Block.soundTypeStone);
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, Block block, int meta) {
		IInventory stand = (IInventory) world.getTileEntity(i, j, k);
		if (stand != null) {
			LOTRMod.dropContainerItems(stand, world, i, j, k);
		}
		super.breakBlock(world, i, j, k, block, meta);
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		int meta = world.getBlockMetadata(i, j, k);
		if (hasTileEntity(meta)) {
			return world.getBlock(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP) && world.getBlock(i, j + 1, k) == this;
		}
		return world.getBlock(i, j - 1, k) == this;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		return world.getBlock(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP) && j < 255;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		if (hasTileEntity(metadata)) {
			return new LOTRTileEntityArmorStand();
		}
		return null;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		if (hasTileEntity(world.getBlockMetadata(i, j, k))) {
			return AxisAlignedBB.getBoundingBox(i, j, k, i + 1.0, j + 0.125, k + 1.0);
		}
		return null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return Blocks.planks.getIcon(i, 0);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Item getItem(World world, int i, int j, int k) {
		return LOTRMod.armorStandItem;
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return hasTileEntity(i) ? LOTRMod.armorStandItem : null;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return (metadata & 4) == 0;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
		if (!hasTileEntity(world.getBlockMetadata(i, j, k))) {
			--j;
		}
		if (hasTileEntity(world.getBlockMetadata(i, j, k))) {
			if (!world.isRemote) {
				entityplayer.openGui(LOTRMod.instance, 17, world, i, j, k);
			}
			return true;
		}
		return false;
	}

	@Override
	public void onBlockHarvested(World world, int i, int j, int k, int meta, EntityPlayer entityplayer) {
		if (entityplayer.capabilities.isCreativeMode && !hasTileEntity(meta) && world.getBlock(i, j - 1, k) == this) {
			world.setBlockToAir(i, j - 1, k);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
		int meta = world.getBlockMetadata(i, j, k);
		if (hasTileEntity(meta)) {
			if (!canBlockStay(world, i, j, k)) {
				world.setBlockToAir(i, j, k);
				if (!world.isRemote) {
					dropBlockAsItem(world, i, j, k, meta, 0);
				}
			}
		} else if (!canBlockStay(world, i, j, k)) {
			world.setBlockToAir(i, j, k);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
}
