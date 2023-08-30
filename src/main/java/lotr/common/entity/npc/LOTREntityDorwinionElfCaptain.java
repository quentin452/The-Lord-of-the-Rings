package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDorwinionElfCaptain extends LOTREntityDorwinionElfWarrior implements LOTRUnitTradeable {
	public LOTREntityDorwinionElfCaptain(World world) {
		super(world);
		addTargetTasks(false);
		npcCape = LOTRCapes.DORWINION_ELF_CAPTAIN;
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
				return "dorwinion/elfCaptain/friendly";
			}
			return "dorwinion/elfCaptain/neutral";
		}
		return "dorwinion/elfWarrior/hostile";
	}

	@Override
	public LOTRUnitTradeEntries getUnits() {
		return LOTRUnitTradeEntries.DORWINION_ELF_CAPTAIN;
	}

	@Override
	public LOTRInvasions getWarhorn() {
		return LOTRInvasions.DORWINION_ELF;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearBladorthin));
		npcItemsInv.setSpearBackup(null);
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsDorwinionElf));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsDorwinionElf));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyDorwinionElf));
		setCurrentItemOrArmor(4, null);
		return data;
	}

	@Override
	public void onUnitTrade(EntityPlayer entityplayer) {
		LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeDorwinionElfCaptain);
	}

	@Override
	public boolean shouldTraderRespawn() {
		return true;
	}
}
