package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRBlockQuenditeGrass extends Block {
	@SideOnly(Side.CLIENT)
	public IIcon grassSideIcon;

	public LOTRBlockQuenditeGrass() {
		super(Material.grass);
		setCreativeTab(LOTRCreativeTabs.tabBlock);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (i == 0) {
			return Blocks.dirt.getIcon(0, j);
		}
		if (i == 1) {
			return blockIcon;
		}
		return grassSideIcon;
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return Item.getItemFromBlock(Blocks.dirt);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if (random.nextInt(8) == 0) {
			double d = i + random.nextFloat();
			double d1 = j + 1.0;
			double d2 = k + random.nextFloat();
			LOTRMod.proxy.spawnParticle("quenditeSmoke", d, d1, d2, 0.0, 0.0, 0.0);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		blockIcon = iconregister.registerIcon("lotr:quenditeGrass_top");
		grassSideIcon = iconregister.registerIcon("lotr:quenditeGrass_side");
	}
}
