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

public class LOTREntityMoredainChieftain extends LOTREntityMoredainWarrior implements LOTRUnitTradeable {
	public LOTREntityMoredainChieftain(World world) {
		super(world);
		addTargetTasks(false);
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
				return "moredain/chieftain/friendly";
			}
			return "moredain/chieftain/neutral";
		}
		return "moredain/moredain/hostile";
	}

	@Override
	public LOTRUnitTradeEntries getUnits() {
		return LOTRUnitTradeEntries.MOREDAIN_CHIEFTAIN;
	}

	@Override
	public LOTRInvasions getWarhorn() {
		return LOTRInvasions.MOREDAIN;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeMoredain));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsMoredainLion));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsMoredainLion));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyMoredainLion));
		setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetMoredainLion));
		return data;
	}

	@Override
	public void onUnitTrade(EntityPlayer entityplayer) {
		LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeMoredainChieftain);
	}

	@Override
	public boolean shouldTraderRespawn() {
		return true;
	}
}
