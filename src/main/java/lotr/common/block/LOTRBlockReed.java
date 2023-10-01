package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

public class LOTRBlockReed extends Block implements IPlantable {
	public static int MAX_GROW_HEIGHT = 3;
	public static int META_GROW_END = 15;
	@SideOnly(Side.CLIENT)
	public IIcon iconUpper;
	@SideOnly(Side.CLIENT)
	public IIcon iconLower;

	public LOTRBlockReed() {
		super(Material.plants);
		float f = 0.375f;
		setBlockBounds(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, 1.0f, 0.5f + f);
		setTickRandomly(true);
		setHardness(0.0f);
		setStepSound(soundTypeGrass);
		setCreativeTab(LOTRCreativeTabs.tabDeco);
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		return canPlaceBlockAt(world, i, j, k);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		Block below = world.getBlock(i, j - 1, k);
		int belowMeta = world.getBlockMetadata(i, j - 1, k);
		if (below == this) {
			return true;
		}
		return below.getMaterial() == Material.water && belowMeta == 0;
	}

	public boolean canReedGrow(World world, int i, int j, int k) {
		return true;
	}

	public boolean checkCanStay(World world, int i, int j, int k) {
		if (!canBlockStay(world, i, j, k)) {
			int meta = world.getBlockMetadata(i, j, k);
			dropBlockAsItem(world, i, j, k, meta, 0);
			world.setBlockToAir(i, j, k);
			return false;
		}
		return true;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		return null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(IBlockAccess world, int i, int j, int k, int side) {
		if (side == -2) {
			return iconLower;
		}
		if (side == -1) {
			return blockIcon;
		}
		world.getBlock(i, j - 1, k);
		Block above = world.getBlock(i, j + 1, k);
		if (above != this) {
			return iconUpper;
		}
		return blockIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (i == -2) {
			return iconLower;
		}
		return blockIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public String getItemIconName() {
		return getTextureName();
	}

	@Override
	public Block getPlant(IBlockAccess world, int i, int j, int k) {
		return this;
	}

	@Override
	public int getPlantMetadata(IBlockAccess world, int i, int j, int k) {
		return world.getBlockMetadata(i, j, k);
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int i, int j, int k) {
		return EnumPlantType.Water;
	}

	@Override
	public int getRenderType() {
		return LOTRMod.proxy.getReedsRenderID();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
		checkCanStay(world, i, j, k);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		blockIcon = iconregister.registerIcon(getTextureName() + "_mid");
		iconUpper = iconregister.registerIcon(getTextureName() + "_upper");
		iconLower = iconregister.registerIcon(getTextureName() + "_lower");
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		if (checkCanStay(world, i, j, k) && canReedGrow(world, i, j, k) && world.isAirBlock(i, j + 1, k)) {
			int belowReeds = 1;
			while (world.getBlock(i, j - belowReeds, k) == this) {
				++belowReeds;
			}
			if (belowReeds < MAX_GROW_HEIGHT) {
				int meta = world.getBlockMetadata(i, j, k);
				if (meta == META_GROW_END) {
					world.setBlock(i, j + 1, k, this, 0, 3);
					world.setBlockMetadataWithNotify(i, j, k, 0, 4);
				} else {
					world.setBlockMetadataWithNotify(i, j, k, meta + 1, 4);
				}
			}
		}
	}
}
