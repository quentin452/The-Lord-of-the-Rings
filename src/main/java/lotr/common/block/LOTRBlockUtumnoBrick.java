package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import lotr.common.world.LOTRWorldProviderUtumno;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class LOTRBlockUtumnoBrick extends Block implements LOTRWorldProviderUtumno.UtumnoBlock {
	@SideOnly(Side.CLIENT)
	public IIcon[] brickIcons;
	@SideOnly(Side.CLIENT)
	public IIcon iceGlowingTop;
	@SideOnly(Side.CLIENT)
	public IIcon fireTileSide;
	public String[] brickNames = {"fire", "burning", "ice", "iceGlowing", "obsidian", "obsidianFire", "iceTile", "obsidianTile", "fireTile"};

	public LOTRBlockUtumnoBrick() {
		super(Material.rock);
		setCreativeTab(LOTRCreativeTabs.tabBlock);
		setHardness(1.5f);
		setResistance(Float.MAX_VALUE);
		setStepSound(Block.soundTypeStone);
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (meta >= brickNames.length) {
			meta = 0;
		}
		if (meta == 3 && side == 1) {
			return iceGlowingTop;
		}
		if (meta == 8 && side != 1 && side != 0) {
			return fireTileSide;
		}
		return brickIcons[meta];
	}

	@Override
	public int getLightValue(IBlockAccess world, int i, int j, int k) {
		int meta = world.getBlockMetadata(i, j, k);
		if (meta == 1 || meta == 3 || meta == 5) {
			return 12;
		}
		return super.getLightValue(world, i, j, k);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < brickNames.length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public boolean isFireSource(World world, int i, int j, int k, ForgeDirection side) {
		return isFlammable(world, i, j, k, side);
	}

	@Override
	public boolean isFlammable(IBlockAccess world, int i, int j, int k, ForgeDirection side) {
		int meta = world.getBlockMetadata(i, j, k);
		if (meta == 0 || meta == 1) {
			return true;
		}
		return super.isFlammable(world, i, j, k, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconregister) {
		brickIcons = new IIcon[brickNames.length];
		for (int i = 0; i < brickNames.length; ++i) {
			String subName = getTextureName() + "_" + brickNames[i];
			brickIcons[i] = iconregister.registerIcon(subName);
			if (i == 3) {
				iceGlowingTop = iconregister.registerIcon(subName + "_top");
			}
			if (i != 8) {
				continue;
			}
			fireTileSide = iconregister.registerIcon(subName + "_side");
		}
	}
}
