package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRCapes;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityHighElfLord extends LOTREntityHighElfWarrior implements LOTRUnitTradeable {
	public LOTREntityHighElfLord(World world) {
		super(world);
		addTargetTasks(false);
		npcCape = LOTRCapes.HIGH_ELF;
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
				return "highElf/lord/friendly";
			}
			return "highElf/lord/neutral";
		}
		return "highElf/warrior/hostile";
	}

	@Override
	public LOTRUnitTradeEntries getUnits() {
		return LOTRUnitTradeEntries.HIGH_ELF_LORD;
	}

	@Override
	public LOTRInvasions getWarhorn() {
		return LOTRInvasions.HIGH_ELF_LINDON;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordHighElven));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsHighElven));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsHighElven));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyHighElven));
		setCurrentItemOrArmor(4, null);
		return data;
	}

	@Override
	public void onUnitTrade(EntityPlayer entityplayer) {
		LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeHighElfLord);
	}

	@Override
	public boolean shouldTraderRespawn() {
		return true;
	}
}
