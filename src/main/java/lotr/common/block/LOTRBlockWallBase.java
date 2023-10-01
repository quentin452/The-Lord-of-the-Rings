package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public abstract class LOTRBlockWallBase extends BlockWall {
	public int subtypes;

	protected LOTRBlockWallBase(Block block, int i) {
		super(block);
		setCreativeTab(LOTRCreativeTabs.tabBlock);
		subtypes = i;
	}

	@Override
	public boolean canPlaceTorchOnTop(World world, int i, int j, int k) {
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int j = 0; j < subtypes; ++j) {
			list.add(new ItemStack(item, 1, j));
		}
	}
}
