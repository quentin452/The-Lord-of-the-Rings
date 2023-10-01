package lotr.common.entity.ai;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRTradeable;
import lotr.common.item.LOTRItemMug;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

import java.util.List;

public class LOTREntityAIDrink extends LOTREntityAIConsumeBase {
	public LOTREntityAIDrink(LOTREntityNPC entity, LOTRFoods foods, int chance) {
		super(entity, foods, chance);
	}

	@SuppressWarnings("Convert2Lambda")
	@Override
	public void consume() {
		ItemStack itemstack = theEntity.getHeldItem();
		Item item = itemstack.getItem();
		if (item instanceof LOTRItemMug) {
			LOTRItemMug drink = (LOTRItemMug) item;
			drink.applyToNPC(theEntity, itemstack);
			if (drink.alcoholicity > 0.0f && theEntity.canGetDrunk() && !theEntity.isDrunkard() && rand.nextInt(3) == 0) {
				double range = 12.0;
				IEntitySelector selectNonEnemyBartenders = new IEntitySelector() {

					@Override
					public boolean isEntityApplicable(Entity entity) {
						return entity.isEntityAlive() && !LOTRMod.getNPCFaction(entity).isBadRelation(theEntity.getFaction());
					}
				};
				List nearbyBartenders = theEntity.worldObj.selectEntitiesWithinAABB(LOTRTradeable.Bartender.class, theEntity.boundingBox.expand(range, range, range), selectNonEnemyBartenders);
				if (!nearbyBartenders.isEmpty()) {
					int drunkTime = MathHelper.getRandomIntegerInRange(rand, 30, 1500);
					theEntity.familyInfo.setDrunkTime(drunkTime * 20);
				}
			}
		}
	}

	@Override
	public ItemStack createConsumable() {
		ItemStack drink = foodPool.getRandomFood(rand);
		Item item = drink.getItem();
		if (item instanceof LOTRItemMug && ((LOTRItemMug) item).isBrewable) {
			LOTRItemMug.setStrengthMeta(drink, 1 + rand.nextInt(3));
		}
		return drink;
	}

	@Override
	public int getConsumeTime() {
		int time = super.getConsumeTime();
		if (theEntity.isDrunkard()) {
			time *= 1 + rand.nextInt(4);
		}
		return time;
	}

	@Override
	public boolean shouldConsume() {
		if (theEntity.isDrunkard() && rand.nextInt(100) == 0) {
			return true;
		}
		return super.shouldConsume();
	}

	@Override
	public void updateConsumeTick(int tick) {
		if (tick % 4 == 0) {
			theEntity.playSound("random.drink", 0.5f, rand.nextFloat() * 0.1f + 0.9f);
		}
	}

}
