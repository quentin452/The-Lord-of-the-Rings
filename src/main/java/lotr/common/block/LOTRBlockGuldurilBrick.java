package lotr.common.block;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.tileentity.LOTRTileEntityGulduril;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTRBlockGuldurilBrick extends Block {
	public LOTRBlockGuldurilBrick() {
		super(Material.rock);
		setCreativeTab(LOTRCreativeTabs.tabBlock);
		setHardness(3.0f);
		setResistance(10.0f);
		setStepSound(Block.soundTypeStone);
		setLightLevel(0.75f);
	}

	@Override
	public boolean canSilkHarvest() {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new LOTRTileEntityGulduril();
	}

	@Override
	public ArrayList getDrops(World world, int i, int j, int k, int metadata, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<>();
		ItemStack drop = blockForGuldurilMeta(metadata);
		if (drop != null) {
			drops.add(drop);
		}
		return drops;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		Item item;
		ItemStack itemstack = blockForGuldurilMeta(j);
		if (itemstack != null && (item = itemstack.getItem()) instanceof ItemBlock) {
			Block block = ((ItemBlock) item).field_150939_a;
			int meta = itemstack.getItemDamage();
			return block.getIcon(i, meta);
		}
		return LOTRMod.brick.getIcon(i, 0);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int i, int j, int k) {
		return new ItemStack(this, 1, world.getBlockMetadata(i, j, k));
	}

	@Override
	public int getRenderType() {
		return LOTRMod.proxy.getGuldurilRenderID();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i <= 9; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}

	public static ItemStack blockForGuldurilMeta(int i) {
		switch (i) {
			case 0:
				return new ItemStack(LOTRMod.brick, 1, 0);
			case 1:
				return new ItemStack(LOTRMod.brick, 1, 7);
			case 2:
				return new ItemStack(LOTRMod.brick2, 1, 0);
			case 3:
				return new ItemStack(LOTRMod.brick2, 1, 1);
			case 4:
				return new ItemStack(LOTRMod.brick2, 1, 8);
			case 5:
				return new ItemStack(LOTRMod.brick2, 1, 9);
			case 6:
				return new ItemStack(LOTRMod.brick, 1, 1);
			case 7:
				return new ItemStack(LOTRMod.brick, 1, 2);
			case 8:
				return new ItemStack(LOTRMod.brick, 1, 3);
			case 9:
				return new ItemStack(LOTRMod.brick2, 1, 11);
			default:
				break;
		}
		return null;
	}

	public static int guldurilMetaForBlock(Block block, int i) {
		if (block == null) {
			return -1;
		}
		if (block == LOTRMod.brick && i == 0) {
			return 0;
		}
		if (block == LOTRMod.brick && i == 7) {
			return 1;
		}
		if (block == LOTRMod.brick2 && i == 0) {
			return 2;
		}
		if (block == LOTRMod.brick2 && i == 1) {
			return 3;
		}
		if (block == LOTRMod.brick2 && i == 8) {
			return 4;
		}
		if (block == LOTRMod.brick2 && i == 9) {
			return 5;
		}
		if (block == LOTRMod.brick && i == 1) {
			return 6;
		}
		if (block == LOTRMod.brick && i == 2) {
			return 7;
		}
		if (block == LOTRMod.brick && i == 3) {
			return 8;
		}
		if (block == LOTRMod.brick2 && i == 11) {
			return 9;
		}
		return -1;
	}
}
