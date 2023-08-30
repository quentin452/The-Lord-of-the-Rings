package lotr.common.block;

import java.util.*;

import lotr.common.LOTRMod;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class LOTRBlockLeaves6 extends LOTRBlockLeavesBase {
	public LOTRBlockLeaves6() {
		setLeafNames("mahogany", "willow", "cypress", "olive");
	}

	@Override
	public void addSpecialLeafDrops(List drops, World world, int i, int j, int k, int meta, int fortune) {
		if ((meta & 3) == 3 && world.rand.nextInt(calcFortuneModifiedDropChance(10, fortune)) == 0) {
			drops.add(new ItemStack(LOTRMod.olive));
		}
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return Item.getItemFromBlock(LOTRMod.sapling6);
	}
}
