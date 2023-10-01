package lotr.common.block;

import lotr.common.LOTRMod;
import net.minecraft.item.Item;

import java.util.Random;

public class LOTRBlockLeaves9 extends LOTRBlockLeavesBase {
	public LOTRBlockLeaves9() {
		setLeafNames("dragon", "kanuka");
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return Item.getItemFromBlock(LOTRMod.sapling9);
	}
}
