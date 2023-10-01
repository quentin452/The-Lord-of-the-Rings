package lotr.common.block;

import lotr.common.LOTRMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class LOTRBlockLeaves5 extends LOTRBlockLeavesBase {
	public LOTRBlockLeaves5() {
		setLeafNames("pine", "lemon", "orange", "lime");
	}

	@Override
	public void addSpecialLeafDrops(List drops, World world, int i, int j, int k, int meta, int fortune) {
		if ((meta & 3) == 1 && world.rand.nextInt(calcFortuneModifiedDropChance(16, fortune)) == 0) {
			drops.add(new ItemStack(LOTRMod.lemon));
		}
		if ((meta & 3) == 2 && world.rand.nextInt(calcFortuneModifiedDropChance(16, fortune)) == 0) {
			drops.add(new ItemStack(LOTRMod.orange));
		}
		if ((meta & 3) == 3 && world.rand.nextInt(calcFortuneModifiedDropChance(16, fortune)) == 0) {
			drops.add(new ItemStack(LOTRMod.lime));
		}
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return Item.getItemFromBlock(LOTRMod.sapling5);
	}
}
