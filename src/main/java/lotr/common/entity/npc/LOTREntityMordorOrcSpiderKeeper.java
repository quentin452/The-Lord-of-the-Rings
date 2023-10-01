package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityMordorOrcSpiderKeeper extends LOTREntityMordorOrc implements LOTRUnitTradeable {
	public LOTREntityMordorOrcSpiderKeeper(World world) {
		super(world);
		setSize(0.6f, 1.8f);
		addTargetTasks(false);
		isWeakOrc = false;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0);
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return LOTRLevelData.getData(entityplayer).getAlignment(getFaction()) >= 250.0f && isFriendly(entityplayer);
	}

	@Override
	public float getAlignmentBonus() {
		return 5.0f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (canTradeWith(entityplayer)) {
				return "mordor/chieftain/friendly";
			}
			return "mordor/chieftain/neutral";
		}
		return "mordor/orc/hostile";
	}

	@Override
	public LOTRUnitTradeEntries getUnits() {
		return LOTRUnitTradeEntries.MORDOR_ORC_SPIDER_KEEPER;
	}

	@Override
	public LOTRInvasions getWarhorn() {
		return LOTRInvasions.MORDOR_NAN_UNGOL;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.orcSkullStaff));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsOrc));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsOrc));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyOrc));
		setCurrentItemOrArmor(4, null);
		if (!worldObj.isRemote) {
			LOTREntityMordorSpider spider = new LOTREntityMordorSpider(worldObj);
			spider.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0f);
			spider.setSpiderScale(3);
			if (worldObj.func_147461_a(spider.boundingBox).isEmpty() || liftSpawnRestrictions) {
				spider.onSpawnWithEgg(null);
				worldObj.spawnEntityInWorld(spider);
				mountEntity(spider);
			}
		}
		return data;
	}

	@Override
	public void onUnitTrade(EntityPlayer entityplayer) {
		LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeOrcSpiderKeeper);
	}

	@Override
	public boolean shouldTraderRespawn() {
		return true;
	}
}
