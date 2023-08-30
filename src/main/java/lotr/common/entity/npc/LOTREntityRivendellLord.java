package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityRivendellLord extends LOTREntityRivendellWarrior implements LOTRUnitTradeable {
	public LOTREntityRivendellLord(World world) {
		super(world);
		addTargetTasks(false);
		npcCape = LOTRCapes.RIVENDELL;
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return LOTRLevelData.getData(entityplayer).getAlignment(getFaction()) >= 300.0f && isFriendly(entityplayer);
	}

	@Override
	public float getAlignmentBonus() {
		return 5.0f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (canTradeWith(entityplayer)) {
				return "rivendell/lord/friendly";
			}
			return "rivendell/lord/neutral";
		}
		return "rivendell/warrior/hostile";
	}

	@Override
	public LOTRUnitTradeEntries getUnits() {
		return LOTRUnitTradeEntries.RIVENDELL_LORD;
	}

	@Override
	public LOTRInvasions getWarhorn() {
		return LOTRInvasions.HIGH_ELF_RIVENDELL;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordRivendell));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsRivendell));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsRivendell));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyRivendell));
		setCurrentItemOrArmor(4, null);
		return data;
	}

	@Override
	public void onUnitTrade(EntityPlayer entityplayer) {
		LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeRivendellLord);
	}

	@Override
	public boolean shouldTraderRespawn() {
		return true;
	}
}
