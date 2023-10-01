package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class LOTRBlockRock extends Block {
	@SideOnly(Side.CLIENT)
	public IIcon[] rockIcons;
	@SideOnly(Side.CLIENT)
	public IIcon iconMordorSide;
	@SideOnly(Side.CLIENT)
	public IIcon iconMordorMoss;
	public String[] rockNames = {"mordor", "gondor", "rohan", "blue", "red", "chalk"};

	public LOTRBlockRock() {
		super(Material.rock);
		setCreativeTab(LOTRCreativeTabs.tabBlock);
		setHardness(1.5f);
		setResistance(10.0f);
		setStepSound(Block.soundTypeStone);
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int i, int j, int k, int side) {
		int meta = world.getBlockMetadata(i, j, k);
		if (meta == 0 && side != 1 && side != 0 && world.getBlock(i, j + 1, k) == LOTRMod.mordorMoss) {
			return iconMordorMoss;
		}
		return super.getIcon(world, i, j, k, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (meta >= rockNames.length) {
			meta = 0;
		}
		if (meta == 0 && side != 1 && side != 0) {
			return iconMordorSide;
		}
		return rockIcons[meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < rockNames.length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public boolean isReplaceableOreGen(World world, int i, int j, int k, Block target) {
		if (target == this) {
			return world.getBlockMetadata(i, j, k) == 0;
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if (world.getBlock(i, j, k) == this && world.getBlockMetadata(i, j, k) == 0 && random.nextInt(10) == 0) {
			world.spawnParticle("smoke", i + random.nextFloat(), j + 1.1f, k + random.nextFloat(), 0.0, 0.0, 0.0);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconregister) {
		rockIcons = new IIcon[rockNames.length];
		for (int i = 0; i < rockNames.length; ++i) {
			String subName = getTextureName() + "_" + rockNames[i];
			rockIcons[i] = iconregister.registerIcon(subName);
			if (i != 0) {
				continue;
			}
			iconMordorSide = iconregister.registerIcon(subName + "_side");
			iconMordorMoss = iconregister.registerIcon(subName + "_moss");
		}
	}
}
