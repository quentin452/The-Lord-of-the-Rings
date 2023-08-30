package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.quest.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGulfHaradrim extends LOTREntityNearHaradrimBase {
	public LOTREntityGulfHaradrim(World world) {
		super(world);
		addTargetTasks(true);
	}

	@Override
	public LOTRMiniQuest createMiniQuest() {
		return LOTRMiniQuestFactory.GULF_HARAD.createQuest(this);
	}

	@Override
	public void dropHaradrimItems(boolean flag, int i) {
		if (rand.nextInt(5) == 0) {
			dropChestContents(LOTRChestContents.GULF_HOUSE, 1, 2 + i);
		}
	}

	@Override
	public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
		return LOTRMiniQuestFactory.GULF_HARAD;
	}

	@Override
	public LOTRFoods getHaradrimDrinks() {
		return LOTRFoods.GULF_HARAD_DRINK;
	}

	@Override
	public LOTRFoods getHaradrimFoods() {
		return LOTRFoods.GULF_HARAD;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			return "nearHarad/gulf/haradrim/friendly";
		}
		return "nearHarad/gulf/haradrim/hostile";
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerHarad));
		npcItemsInv.setIdleItem(null);
		return data;
	}

	@Override
	public void setupNPCName() {
		familyInfo.setName(LOTRNames.getGulfHaradName(rand, familyInfo.isMale()));
	}
}
