package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityRohirrimMarshal extends LOTREntityRohirrimWarrior implements LOTRUnitTradeable {
	public LOTREntityRohirrimMarshal(World world) {
		super(world);
		addTargetTasks(false);
		npcCape = LOTRCapes.ROHAN;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0);
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return LOTRLevelData.getData(entityplayer).getAlignment(getFaction()) >= 150.0f && isFriendly(entityplayer);
	}

	@Override
	public float getAlignmentBonus() {
		return 5.0f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (canTradeWith(entityplayer)) {
				return "rohan/marshal/friendly";
			}
			return "rohan/marshal/neutral";
		}
		return "rohan/warrior/hostile";
	}

	@Override
	public LOTRUnitTradeEntries getUnits() {
		return LOTRUnitTradeEntries.ROHIRRIM_MARSHAL;
	}

	@Override
	public LOTRInvasions getWarhorn() {
		return LOTRInvasions.ROHAN;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordRohan));
		npcItemsInv.setMeleeWeaponMounted(npcItemsInv.getMeleeWeapon());
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		npcItemsInv.setIdleItemMounted(npcItemsInv.getMeleeWeaponMounted());
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsRohanMarshal));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsRohanMarshal));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyRohanMarshal));
		setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetRohanMarshal));
		return data;
	}

	@Override
	public void onUnitTrade(EntityPlayer entityplayer) {
		LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeRohirrimMarshal);
	}

	@Override
	public boolean shouldTraderRespawn() {
		return true;
	}
}
