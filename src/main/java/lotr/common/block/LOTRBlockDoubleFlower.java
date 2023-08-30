package lotr.common.block;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class LOTRBlockDoubleFlower extends BlockDoublePlant {
	public static String[] flowerNames = {"blackIris", "yellowIris", "pink", "red"};
	@SideOnly(Side.CLIENT)
	public IIcon[] doublePlantBottomIcons;
	@SideOnly(Side.CLIENT)
	public IIcon[] doublePlantTopIcons;

	public LOTRBlockDoubleFlower() {
		setCreativeTab(LOTRCreativeTabs.tabDeco);
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		if (world.getBlock(i, j, k) != this) {
			return super.canBlockStay(world, i, j, k);
		}
		int l = world.getBlockMetadata(i, j, k);
		return isTop(l) ? world.getBlock(i, j - 1, k) == this : world.getBlock(i, j + 1, k) == this && super.canBlockStay(world, i, j, k);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		return super.canPlaceBlockAt(world, i, j, k) && world.isAirBlock(i, j + 1, k);
	}

	@Override
	public void checkAndDropBlock(World world, int i, int j, int k) {
		if (!canBlockStay(world, i, j, k)) {
			int l = world.getBlockMetadata(i, j, k);
			if (!isTop(l)) {
				dropBlockAsItem(world, i, j, k, l, 0);
				if (world.getBlock(i, j + 1, k) == this) {
					world.setBlock(i, j + 1, k, Blocks.air, 0, 2);
				}
			}
			world.setBlock(i, j, k, Blocks.air, 0, 2);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockAccess world, int i, int j, int k) {
		return 16777215;
	}

	@Override
	public int damageDropped(int i) {
		return isTop(i) ? 0 : i & 7;
	}

	@Override
	public boolean func_149851_a(World world, int i, int j, int k, boolean flag) {
		return true;
	}

	@Override
	public void func_149853_b(World world, Random random, int i, int j, int k) {
		int meta = func_149885_e(world, i, j, k);
		dropBlockAsItem(world, i, j, k, new ItemStack(this, 1, meta));
	}

	@Override
	public int func_149885_e(IBlockAccess world, int i, int j, int k) {
		int l = world.getBlockMetadata(i, j, k);
		return !isTop(l) ? l & 7 : world.getBlockMetadata(i, j - 1, k) & 7;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon func_149888_a(boolean isTop, int i) {
		if (isTop) {
			if (i >= doublePlantTopIcons.length) {
				i = 0;
			}
			return doublePlantTopIcons[i];
		}
		if (i >= doublePlantBottomIcons.length) {
			i = 0;
		}
		return doublePlantBottomIcons[i];
	}

	@Override
	public int getDamageValue(World world, int i, int j, int k) {
		int l = world.getBlockMetadata(i, j, k);
		return isTop(l) ? getFlowerMeta(world.getBlockMetadata(i, j - 1, k)) : getFlowerMeta(l);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (isTop(j)) {
			return doublePlantBottomIcons[1];
		}
		int k = j & 7;
		if (k >= doublePlantBottomIcons.length) {
			k = 0;
		}
		return doublePlantBottomIcons[k];
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		if (isTop(i)) {
			return null;
		}
		return Item.getItemFromBlock(this);
	}

	@Override
	public int getRenderType() {
		return LOTRMod.proxy.getDoublePlantRenderID();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < doublePlantBottomIcons.length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public void onBlockHarvested(World world, int i, int j, int k, int meta, EntityPlayer entityplayer) {
		if (isTop(meta)) {
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
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entity, ItemStack itemstack) {
		int l = ((MathHelper.floor_double(entity.rotationYaw * 4.0f / 360.0f + 0.5) & 3) + 2) % 4;
		world.setBlock(i, j + 1, k, this, 8 | l, 2);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		doublePlantBottomIcons = new IIcon[flowerNames.length];
		doublePlantTopIcons = new IIcon[flowerNames.length];
		for (int i = 0; i < doublePlantBottomIcons.length; ++i) {
			doublePlantBottomIcons[i] = iconregister.registerIcon(getTextureName() + "_" + flowerNames[i] + "_bottom");
			doublePlantTopIcons[i] = iconregister.registerIcon(getTextureName() + "_" + flowerNames[i] + "_top");
		}
	}

	public static int getFlowerMeta(int i) {
		return i & 7;
	}

	public static boolean isTop(int i) {
		return (i & 8) != 0;
	}
}
