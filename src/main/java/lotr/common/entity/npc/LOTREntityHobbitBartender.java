package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFoods;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityHobbitBartender extends LOTREntityHobbit implements LOTRTradeable.Bartender {
	public LOTREntityHobbitBartender(World world) {
		super(world);
		npcLocationName = "entity.lotr.HobbitBartender.locationName";
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return isFriendly(entityplayer);
	}

	@Override
	public void dropHobbitItems(boolean flag, int i) {
		int count = rand.nextInt(3) + rand.nextInt(i + 1);
		for (int k = 0; k < count; ++k) {
			int j = rand.nextInt(10);
			switch (j) {
				case 0:
				case 1: {
					entityDropItem(LOTRFoods.HOBBIT.getRandomFood(rand), 0.0f);
					continue;
				}
				case 2: {
					entityDropItem(new ItemStack(Items.gold_nugget, 2 + rand.nextInt(3)), 0.0f);
					continue;
				}
				case 3: {
					entityDropItem(new ItemStack(Items.bowl, 1 + rand.nextInt(4)), 0.0f);
					continue;
				}
				case 4: {
					entityDropItem(new ItemStack(LOTRMod.hobbitPipe, 1, rand.nextInt(100)), 0.0f);
					continue;
				}
				case 5: {
					entityDropItem(new ItemStack(LOTRMod.pipeweed, 1 + rand.nextInt(2)), 0.0f);
					continue;
				}
				case 6:
				case 7:
				case 8: {
					entityDropItem(new ItemStack(LOTRMod.mug), 0.0f);
					continue;
				}
				case 9: {
					Item drink = LOTRFoods.HOBBIT_DRINK.getRandomFood(rand).getItem();
					entityDropItem(new ItemStack(drink, 1, 1 + rand.nextInt(3)), 0.0f);
				}
			}
		}
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public LOTRTradeEntries getBuyPool() {
		return LOTRTradeEntries.HOBBIT_BARTENDER_BUY;
	}

	@Override
	public LOTRTradeEntries getSellPool() {
		return LOTRTradeEntries.HOBBIT_BARTENDER_SELL;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			return "hobbit/bartender/friendly";
		}
		return "hobbit/bartender/hostile";
	}

	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
		LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeBartender);
		if (type == LOTRTradeEntries.TradeType.SELL && itemstack.getItem() == LOTRMod.pipeweedLeaf) {
			LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.sellPipeweedLeaf);
		}
	}

	@Override
	public boolean shouldTraderRespawn() {
		return true;
	}
}
