package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRBlockDoubleTorch extends Block {
	@SideOnly(Side.CLIENT)
	public IIcon[] torchIcons;
	public Item torchItem;

	public LOTRBlockDoubleTorch() {
		super(Material.circuits);
		setHardness(0.0f);
		setStepSound(Block.soundTypeWood);
	}

	public static boolean canPlaceTorchOn(World world, int i, int j, int k) {
		return world.getBlock(i, j, k).canPlaceTorchOnTop(world, i, j, k);
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		if (world.getBlock(i, j, k) != this) {
			return super.canBlockStay(world, i, j, k);
		}
		int l = world.getBlockMetadata(i, j, k);
		return l == 1 ? world.getBlock(i, j - 1, k) == this : world.getBlock(i, j + 1, k) == this && canPlaceTorchOn(world, i, j - 1, k);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		return null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return j == 1 ? torchIcons[1] : torchIcons[0];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Item getItem(World world, int i, int j, int k) {
		return torchItem;
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		if (i == 0) {
			return torchItem;
		}
		return null;
	}

	@Override
	public int getLightValue(IBlockAccess world, int i, int j, int k) {
		return world.getBlockMetadata(i, j, k) == 1 ? 14 : 0;
	}

	@Override
	public int getRenderType() {
		return LOTRMod.proxy.getDoubleTorchRenderID();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k) {
		int meta = world.getBlockMetadata(i, j, k);
		if (meta == 0) {
			setBlockBounds(0.4f, 0.0f, 0.4f, 0.6f, 1.0f, 0.6f);
		} else if (meta == 1) {
			setBlockBounds(0.4f, 0.0f, 0.4f, 0.6f, 0.5375f, 0.6f);
		}
		return super.getSelectedBoundingBoxFromPool(world, i, j, k);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void onBlockHarvested(World world, int i, int j, int k, int meta, EntityPlayer entityplayer) {
		if (meta == 1) {
			if (world.getBlock(i, j - 1, k) == this) {
				if (entityplayer.capabilities.isCreativeMode) {
					world.setBlockToAir(i, j - 1, k);
				} else {
					world.func_147480_a(i, j - 1, k, true);
				}
			}
		} else if (entityplayer.capabilities.isCreativeMode && world.getBlock(i, j + 1, k) == this) {
			world.setBlock(i, j + 1, k, Blocks.air, 0, 2);
		}
		super.onBlockHarvested(world, i, j, k, meta, entityplayer);
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
		if (!canBlockStay(world, i, j, k)) {
			int meta = world.getBlockMetadata(i, j, k);
			if (meta == 0) {
				dropBlockAsItem(world, i, j, k, 0, 0);
				if (world.getBlock(i, j + 1, k) == this) {
					world.setBlock(i, j + 1, k, Blocks.air, 0, 2);
				}
			}
			world.setBlock(i, j, k, Blocks.air, 0, 2);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if (world.getBlockMetadata(i, j, k) == 1) {
			double d = i + 0.5;
			double d1 = j + 0.6;
			double d2 = k + 0.5;
			world.spawnParticle("smoke", d, d1, d2, 0.0, 0.0, 0.0);
			world.spawnParticle("flame", d, d1, d2, 0.0, 0.0, 0.0);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		torchIcons = new IIcon[2];
		torchIcons[0] = iconregister.registerIcon(getTextureName() + "_bottom");
		torchIcons[1] = iconregister.registerIcon(getTextureName() + "_top");
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int i, int j, int k) {
		int meta = world.getBlockMetadata(i, j, k);
		if (meta == 0) {
			setBlockBounds(0.4375f, 0.0f, 0.4375f, 0.5625f, 1.0f, 0.5625f);
		} else if (meta == 1) {
			setBlockBounds(0.4375f, 0.0f, 0.4375f, 0.5625f, 0.5f, 0.5625f);
		}
	}
}
