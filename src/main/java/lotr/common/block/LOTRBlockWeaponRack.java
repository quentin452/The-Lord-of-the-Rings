package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRCreativeTabs;
import lotr.common.tileentity.LOTRTileEntityWeaponRack;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockWeaponRack extends BlockContainer {
	public LOTRBlockWeaponRack() {
		super(Material.circuits);
		setHardness(0.5f);
		setResistance(1.0f);
		setStepSound(Block.soundTypeWood);
		setCreativeTab(LOTRCreativeTabs.tabDeco);
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, Block block, int meta) {
		ItemStack weaponItem;
		LOTRTileEntityWeaponRack rack = (LOTRTileEntityWeaponRack) world.getTileEntity(i, j, k);
		if (rack != null && (weaponItem = rack.getWeaponItem()) != null) {
			dropBlockAsItem(world, i, j, k, weaponItem);
		}
		super.breakBlock(world, i, j, k, block, meta);
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		int meta = world.getBlockMetadata(i, j, k);
		if ((meta & 4) == 0) {
			return canPlaceBlockOnSide(world, i, j, k, 1);
		}
		int l = meta & 3;
		int dir = Direction.directionToFacing[l];
		return canPlaceBlockOnSide(world, i, j, k, dir);
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, int i, int j, int k, int side) {
		if (side == 1) {
			return world.getBlock(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP);
		}
		if (side != 0) {
			ForgeDirection dir = ForgeDirection.getOrientation(side);
			int i1 = i - dir.offsetX;
			int k1 = k - dir.offsetZ;
			return world.getBlock(i1, j, k1).isSideSolid(world, i1, j, k1, dir);
		}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new LOTRTileEntityWeaponRack();
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		return null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return Blocks.planks.getIcon(i, 0);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public String getItemIconName() {
		return getTextureName();
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int i, int j, int k, EntityPlayer entityplayer) {
		ItemStack weaponItem;
		LOTRTileEntityWeaponRack rack = (LOTRTileEntityWeaponRack) world.getTileEntity(i, j, k);
		if (rack != null && (weaponItem = rack.getWeaponItem()) != null) {
			return weaponItem.copy();
		}
		return super.getPickBlock(target, world, i, j, k, entityplayer);
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
		TileEntity tileentity = world.getTileEntity(i, j, k);
		if (tileentity instanceof LOTRTileEntityWeaponRack) {
			LOTRTileEntityWeaponRack rack = (LOTRTileEntityWeaponRack) tileentity;
			ItemStack heldItem = entityplayer.getHeldItem();
			ItemStack rackItem = rack.getWeaponItem();
			if (rackItem != null) {
				if (!world.isRemote) {
					if (entityplayer.getHeldItem() == null) {
						entityplayer.setCurrentItemOrArmor(0, rackItem);
						world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, "random.pop", 0.2f, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
					} else {
						dropBlockAsItem(world, i, j, k, rackItem);
					}
					rack.setWeaponItem(null);
				}
				return true;
			}
			if (rack.canAcceptItem(heldItem)) {
				if (!world.isRemote) {
					rack.setWeaponItem(heldItem.copy());
				}
				if (!entityplayer.capabilities.isCreativeMode) {
					--heldItem.stackSize;
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public int onBlockPlaced(World world, int i, int j, int k, int side, float f, float f1, float f2, int meta) {
		if (side == 1) {
			return 0;
		}
		if (side != 0) {
			return Direction.facingToDirection[side] | 4;
		}
		return 0;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entity, ItemStack itemstack) {
		int meta = world.getBlockMetadata(i, j, k);
		if ((meta & 4) == 0) {
			int rotation = MathHelper.floor_double(entity.rotationYaw * 4.0f / 360.0f + 2.5) & 3;
			world.setBlockMetadataWithNotify(i, j, k, meta | rotation, 2);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
		if (!canBlockStay(world, i, j, k)) {
			int meta = world.getBlockMetadata(i, j, k);
			dropBlockAsItem(world, i, j, k, meta, 0);
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

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int i, int j, int k) {
		float f = 0.2f;
		float h = 0.9f;
		int meta = world.getBlockMetadata(i, j, k);
		switch (meta) {
			case 0:
			case 2:
				setBlockBounds(0.0f, 0.0f, f, 1.0f, h, 1.0f - f);
				break;
			case 1:
			case 3:
				setBlockBounds(f, 0.0f, 0.0f, 1.0f - f, h, 1.0f);
				break;
			case 4:
				setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, h, 1.0f - f * 2.0f);
				break;
			case 6:
				setBlockBounds(0.0f, 0.0f, f * 2.0f, 1.0f, h, 1.0f);
				break;
			case 5:
				setBlockBounds(f * 2.0f, 0.0f, 0.0f, 1.0f, h, 1.0f);
				break;
			case 7:
				setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f - f * 2.0f, h, 1.0f);
				break;
			default:
				break;
		}
	}
}
