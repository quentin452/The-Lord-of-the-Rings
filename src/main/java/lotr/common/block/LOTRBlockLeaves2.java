package lotr.common.block;

import lotr.common.LOTRMod;
import net.minecraft.item.Item;

import java.util.Random;

public class LOTRBlockLeaves2 extends LOTRBlockLeavesBase {
	public LOTRBlockLeaves2() {
		setLeafNames("lebethron", "beech", "holly", "banana");
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return Item.getItemFromBlock(LOTRMod.sapling2);
	}

	@Override
	public int getSaplingChance(int meta) {
		if (meta == 3) {
			return 9;
		}
		return super.getSaplingChance(meta);
	}
}
