package lotr.common.block;

import net.minecraft.item.Item;

import java.util.Random;

public class LOTRBlockObsidianGravel extends LOTRBlockGravel {
	@Override
	public Item getItemDropped(int i, Random rand, int j) {
		return Item.getItemFromBlock(this);
	}
}
