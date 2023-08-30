package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGondorGreengrocer extends LOTREntityGondorMarketTrader {
	public LOTREntityGondorGreengrocer(World world) {
		super(world);
	}

	@Override
	public LOTRTradeEntries getBuyPool() {
		return LOTRTradeEntries.GONDOR_GREENGROCER_BUY;
	}

	@Override
	public LOTRTradeEntries getSellPool() {
		return LOTRTradeEntries.GONDOR_GREENGROCER_SELL;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(3);
		switch (i) {
			case 0:
				npcItemsInv.setIdleItem(new ItemStack(Items.apple));
				break;
			case 1:
				npcItemsInv.setIdleItem(new ItemStack(LOTRMod.appleGreen));
				break;
			case 2:
				npcItemsInv.setIdleItem(new ItemStack(LOTRMod.pear));
				break;
			default:
				break;
		}
		return data;
	}
}
