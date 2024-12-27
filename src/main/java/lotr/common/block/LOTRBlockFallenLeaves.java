package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class LOTRBlockFallenLeaves extends Block implements IShearable {
    public static List<LOTRBlockFallenLeaves> allFallenLeaves = new ArrayList<>();
	public Block[] leafBlocks;

	public LOTRBlockFallenLeaves() {
		super(Material.vine);
		allFallenLeaves.add(this);
		setCreativeTab(LOTRCreativeTabs.tabDeco);
		setHardness(0.2f);
		setStepSound(Block.soundTypeGrass);
		useNeighborBrightness = true;
		setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
	}

	public static void assignLeaves(Block fallenLeaves, Block... leaves) {
		((LOTRBlockFallenLeaves) fallenLeaves).leafBlocks = leaves;
	}

	public static Object[] fallenBlockMetaFromLeafBlockMeta(Block block, int meta) {
		meta &= 3;
		for (LOTRBlockFallenLeaves fallenLeaves : allFallenLeaves) {
			for (int i = 0; i < fallenLeaves.leafBlocks.length; ++i) {
				Block leafBlock = fallenLeaves.leafBlocks[i];
				if (leafBlock != block) {
					continue;
				}
				return new Object[]{fallenLeaves, i * 4 + meta};
			}
		}
		return null;
	}

	@Override
	public void addCollisionBoxesToList(World world, int i, int j, int k, AxisAlignedBB bb, List boxes, Entity entity) {
    }

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		Block below = world.getBlock(i, j - 1, k);
		int belowMeta = world.getBlockMetadata(i, j - 1, k);
		if (below.getMaterial() == Material.water && belowMeta == 0) {
			return true;
		}
		return below.isSideSolid(world, i, j - 1, k, ForgeDirection.UP);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		Block block = world.getBlock(i, j, k);
		if (block.getMaterial().isLiquid()) {
			return false;
		}
		return canBlockStay(world, i, j, k);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockAccess world, int i, int j, int k) {
		int meta = world.getBlockMetadata(i, j, k);
		Object[] obj = leafBlockMetaFromFallenMeta(meta);
		return ((Block) obj[0]).colorMultiplier(world, i, j, k);
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		Object[] obj = leafBlockMetaFromFallenMeta(j);
		return ((Block) obj[0]).getIcon(i, (Integer) obj[1]);
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return null;
	}

	public Block[] getLeafBlocks() {
		return leafBlocks;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getRenderColor(int i) {
		Object[] obj = leafBlockMetaFromFallenMeta(i);
		return ((Block) obj[0]).getRenderColor((Integer) obj[1]);
	}

	@Override
	public int getRenderType() {
		return LOTRMod.proxy.getFallenLeavesRenderID();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < leafBlocks.length; ++i) {
			Block leaf = leafBlocks[i];
			List<ItemStack> leafTypes = new ArrayList<>();
			leaf.getSubBlocks(Item.getItemFromBlock(leaf), leaf.getCreativeTabToDisplayOn(), leafTypes);
			for (ItemStack leafItem : leafTypes) {
				int meta = leafItem.getItemDamage();
				list.add(new ItemStack(item, 1, i * 4 + meta));
			}
		}
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, int i, int j, int k) {
		return true;
	}

	public Object[] leafBlockMetaFromFallenMeta(int meta) {
		Block leaf = leafBlocks[meta / 4];
		int leafMeta = meta & 3;
		return new Object[]{leaf, leafMeta};
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
		if (!canBlockStay(world, i, j, k)) {
			dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			world.setBlockToAir(i, j, k);
		}
	}

	@Override
	public ArrayList onSheared(ItemStack item, IBlockAccess world, int i, int j, int k, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<>();
		drops.add(new ItemStack(this, 1, world.getBlockMetadata(i, j, k)));
		return drops;
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
