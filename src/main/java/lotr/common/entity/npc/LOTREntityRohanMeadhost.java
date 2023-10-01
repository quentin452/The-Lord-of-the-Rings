package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFoods;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityRohanMeadhost extends LOTREntityRohanMan implements LOTRTradeable.Bartender {
	public LOTREntityRohanMeadhost(World world) {
		super(world);
		addTargetTasks(false);
		npcLocationName = "entity.lotr.RohanMeadhost.locationName";
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return isFriendly(entityplayer);
	}

	@Override
	public EntityAIBase createRohanAttackAI() {
		return new LOTREntityAIAttackOnCollide(this, 1.3, false);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int j = rand.nextInt(3) + rand.nextInt(i + 1);
		for (int k = 0; k < j; ++k) {
			int l = rand.nextInt(11);
			switch (l) {
				case 0:
				case 1:
				case 2: {
					Item food = LOTRFoods.ROHAN.getRandomFood(rand).getItem();
					entityDropItem(new ItemStack(food), 0.0f);
					continue;
				}
				case 3: {
					entityDropItem(new ItemStack(Items.gold_nugget, 2 + rand.nextInt(3)), 0.0f);
					continue;
				}
				case 4: {
					entityDropItem(new ItemStack(Items.wheat, 1 + rand.nextInt(4)), 0.0f);
					continue;
				}
				case 5: {
					entityDropItem(new ItemStack(Items.sugar, 1 + rand.nextInt(3)), 0.0f);
					continue;
				}
				case 6: {
					entityDropItem(new ItemStack(Items.paper, 1 + rand.nextInt(2)), 0.0f);
					continue;
				}
				case 7:
				case 8: {
					entityDropItem(new ItemStack(LOTRMod.mug), 0.0f);
					continue;
				}
				case 9:
				case 10: {
					Item drink = LOTRMod.mugMead;
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
		return LOTRTradeEntries.ROHAN_MEADHOST_BUY;
	}

	@Override
	public LOTRTradeEntries getSellPool() {
		return LOTRTradeEntries.ROHAN_MEADHOST_SELL;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			return "rohan/meadhost/friendly";
		}
		return "rohan/meadhost/hostile";
	}

	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
		if (type == LOTRTradeEntries.TradeType.BUY && itemstack.getItem() == LOTRMod.mugMead) {
			LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.buyRohanMead);
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.mugMead));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		return data;
	}

	@Override
	public boolean shouldTraderRespawn() {
		return true;
	}
}
