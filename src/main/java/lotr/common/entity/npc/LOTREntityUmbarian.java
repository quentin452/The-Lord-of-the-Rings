package lotr.common.entity.npc;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class LOTREntityUmbarian extends LOTREntityNearHaradrimBase {
	public LOTREntityUmbarian(World world) {
		super(world);
		addTargetTasks(false);
	}

	@Override
	public LOTRMiniQuest createMiniQuest() {
		return LOTRMiniQuestFactory.UMBAR.createQuest(this);
	}

	@Override
	public LOTRNPCMount createMountToRide() {
		LOTREntityHorse horse = (LOTREntityHorse) super.createMountToRide();
		horse.setMountArmor(new ItemStack(LOTRMod.horseArmorUmbar));
		return horse;
	}

	@Override
	public void dropHaradrimItems(boolean flag, int i) {
		if (rand.nextInt(5) == 0) {
			dropChestContents(LOTRChestContents.NEAR_HARAD_HOUSE, 1, 2 + i);
		}
	}

	@Override
	public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
		return LOTRMiniQuestFactory.UMBAR;
	}

	@Override
	public LOTRFoods getHaradrimDrinks() {
		return LOTRFoods.SOUTHRON_DRINK;
	}

	@Override
	public LOTRFoods getHaradrimFoods() {
		return LOTRFoods.SOUTHRON;
	}

	@Override
	public String getNPCFormattedName(String npcName, String entityName) {
		if (getClass() == LOTREntityUmbarian.class) {
			return StatCollector.translateToLocalFormatted("entity.lotr.Umbarian.entityName", npcName);
		}
		return super.getNPCFormattedName(npcName, entityName);
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			return "nearHarad/umbar/haradrim/friendly";
		}
		return "nearHarad/umbar/haradrim/hostile";
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerNearHarad));
		npcItemsInv.setIdleItem(null);
		return data;
	}

	@Override
	public void setupNPCName() {
		familyInfo.setName(LOTRNames.getUmbarName(rand, familyInfo.isMale()));
	}
}
