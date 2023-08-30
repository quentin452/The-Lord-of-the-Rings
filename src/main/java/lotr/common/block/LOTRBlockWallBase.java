package lotr.common.block;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.world.World;

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
