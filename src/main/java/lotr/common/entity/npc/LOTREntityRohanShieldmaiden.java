package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityRohanShieldmaiden extends LOTREntityRohirrimWarrior {
	public LOTREntityRohanShieldmaiden(World world) {
		super(world);
		spawnRidingHorse = false;
		questInfo.setOfferChance(4000);
		questInfo.setMinAlignment(150.0f);
	}

	@Override
	public LOTRMiniQuest createMiniQuest() {
		return LOTRMiniQuestFactory.ROHAN_SHIELDMAIDEN.createQuest(this);
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "rohan/warrior/hired";
			}
			return "rohan/shieldmaiden/friendly";
		}
		return "rohan/warrior/hostile";
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		if (rand.nextBoolean()) {
			setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetRohan));
		} else {
			setCurrentItemOrArmor(4, null);
		}
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(false);
	}
}
