package lotr.common.entity.npc;

import lotr.common.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class LOTREntityBreeHobbitInnkeeper extends LOTREntityBreeHobbit implements LOTRTradeable.Bartender {
	public LOTREntityBreeHobbitInnkeeper(World world) {
		super(world);
		addTargetTasks(false);
		npcLocationName = "entity.lotr.BreeHobbitBartender.locationName";
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return isFriendlyAndAligned(entityplayer);
	}

	@Override
	public void dropHobbitItems(boolean flag, int i) {
		int j = rand.nextInt(3) + rand.nextInt(i + 1);
		for (int k = 0; k < j; ++k) {
			int l = rand.nextInt(7);
			switch (l) {
				case 0:
				case 1:
				case 2: {
					Item food = LOTRFoods.BREE.getRandomFood(rand).getItem();
					entityDropItem(new ItemStack(food), 0.0f);
					continue;
				}
				case 3: {
					entityDropItem(new ItemStack(Items.gold_nugget, 2 + rand.nextInt(3)), 0.0f);
					continue;
				}
				case 4:
				case 5: {
					entityDropItem(new ItemStack(LOTRMod.mug), 0.0f);
					continue;
				}
				case 6: {
					Item drink = LOTRFoods.BREE.getRandomFood(rand).getItem();
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
		return LOTRTradeEntries.BREE_INNKEEPER_BUY;
	}

	@Override
	public LOTRTradeEntries getSellPool() {
		return LOTRTradeEntries.BREE_INNKEEPER_SELL;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendlyAndAligned(entityplayer)) {
			return "bree/innkeeper/hobbit/friendly";
		}
		return "bree/innkeeper/hobbit/hostile";
	}

	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
		LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeBreeInnkeeper);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setIdleItem(new ItemStack(LOTRMod.mug));
		return data;
	}
}
