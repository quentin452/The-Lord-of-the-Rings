package lotr.common.block;

import lotr.common.LOTRMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

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
