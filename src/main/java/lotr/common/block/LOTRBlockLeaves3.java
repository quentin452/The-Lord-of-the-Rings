package lotr.common.block;

import lotr.common.LOTRMod;
import net.minecraft.item.Item;

import java.util.Random;

public class LOTRBlockLeaves3 extends LOTRBlockLeavesBase {
	public LOTRBlockLeaves3() {
		setLeafNames("maple", "larch", "datePalm", "mangrove");
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return Item.getItemFromBlock(LOTRMod.sapling3);
	}

	@Override
	public int getSaplingChance(int meta) {
		if (meta == 2) {
			return 15;
		}
		return super.getSaplingChance(meta);
	}
}
