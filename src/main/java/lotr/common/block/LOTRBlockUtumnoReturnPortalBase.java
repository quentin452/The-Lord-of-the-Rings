package lotr.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class LOTRBlockUtumnoReturnPortalBase extends Block {
	public static int MAX_SACRIFICE = 15;
	public static int RANGE = 5;
	@SideOnly(Side.CLIENT)
	public IIcon topIcon;

	public LOTRBlockUtumnoReturnPortalBase() {
		super(Material.circuits);
		setHardness(-1.0f);
		setResistance(Float.MAX_VALUE);
		setStepSound(Block.soundTypeStone);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		setBlockBoundsBasedOnState(world, i, j, k);
		return super.getCollisionBoundingBoxFromPool(world, i, j, k);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (i == 1) {
			return topIcon;
		}
		return super.getIcon(i, j);
	}

	@Override
	public int getLightValue(IBlockAccess world, int i, int j, int k) {
		int meta = world.getBlockMetadata(i, j, k);
		float f = (float) meta / MAX_SACRIFICE;
		float f1 = 0.5f;
		float f2 = f1 + (1.0f - f1) * f;
		return (int) (f2 * 16.0f);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		super.registerBlockIcons(iconregister);
		topIcon = iconregister.registerIcon(getTextureName() + "_top");
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int i, int j, int k) {
		int meta = world.getBlockMetadata(i, j, k);
		setBlockBoundsMeta(meta);
	}

	@Override
	public void setBlockBoundsForItemRender() {
		setBlockBoundsMeta(0);
	}

	public void setBlockBoundsMeta(int meta) {
		float f = (float) meta / MAX_SACRIFICE;
		float f1 = 0.0625f;
		float f2 = f1 + (1.0f - f1) * f;
		setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, f2, 1.0f);
	}
}
