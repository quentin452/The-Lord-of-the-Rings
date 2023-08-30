package lotr.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class LOTRBlockHangingFruit extends Block {
	@SideOnly(Side.CLIENT)
	public IIcon[] fruitIcons;
	public String[] fruitSides = {"top", "side", "bottom"};

	protected LOTRBlockHangingFruit() {
		super(Material.plants);
		setTickRandomly(true);
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		int l = world.getBlockMetadata(i, j, k);
		ForgeDirection dir = ForgeDirection.getOrientation(l + 2);
		Block block = world.getBlock(i + dir.offsetX, j, k + dir.offsetZ);
		return block.isWood(world, i + dir.offsetX, j, k + dir.offsetZ);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		setBlockBoundsBasedOnState(world, i, j, k);
		return super.getCollisionBoundingBoxFromPool(world, i, j, k);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (i == 0) {
			return fruitIcons[2];
		}
		if (i == 1) {
			return fruitIcons[0];
		}
		return fruitIcons[1];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k) {
		setBlockBoundsBasedOnState(world, i, j, k);
		return super.getSelectedBoundingBoxFromPool(world, i, j, k);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
		if (!canBlockStay(world, i, j, k)) {
			dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			world.setBlockToAir(i, j, k);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		fruitIcons = new IIcon[3];
		for (int i = 0; i < 3; ++i) {
			fruitIcons[i] = iconregister.registerIcon(getTextureName() + "_" + fruitSides[i]);
		}
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		if (!canBlockStay(world, i, j, k)) {
			dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			world.setBlockToAir(i, j, k);
		}
	}
}
