package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityUtils;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.item.LOTRItemLeatherHat;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityHobbitOrcharder extends LOTREntityHobbit implements LOTRTradeable {
	public LOTREntityHobbitOrcharder(World world) {
		super(world);
		LOTREntityUtils.removeAITask(this, EntityAIPanic.class);
		tasks.addTask(2, new LOTREntityAIAttackOnCollide(this, 1.2, false));
		addTargetTasks(false);
		isNPCPersistent = false;
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return isFriendly(entityplayer);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public LOTRTradeEntries getBuyPool() {
		return LOTRTradeEntries.HOBBIT_ORCHARDER_BUY;
	}

	@Override
	public LOTRTradeEntries getSellPool() {
		return LOTRTradeEntries.HOBBIT_ORCHARDER_SELL;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			return "hobbit/orcharder/friendly";
		}
		return "hobbit/hobbit/hostile";
	}

	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
		if (type == LOTRTradeEntries.TradeType.BUY && itemstack.getItem() instanceof ItemFood) {
			LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.buyOrcharderFood);
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		ItemStack hat = new ItemStack(LOTRMod.leatherHat);
		LOTRItemLeatherHat.setHatColor(hat, 4818735);
		setCurrentItemOrArmor(4, hat);
		int i = rand.nextInt(3);
		switch (i) {
			case 0:
				npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_axe));
				break;
			case 1:
				npcItemsInv.setMeleeWeapon(new ItemStack(Items.stone_axe));
				break;
			case 2:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.axeBronze));
				break;
			default:
				break;
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		return data;
	}

	@Override
	public boolean shouldTraderRespawn() {
		return false;
	}
}
