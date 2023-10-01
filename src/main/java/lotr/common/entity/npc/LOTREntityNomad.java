package lotr.common.entity.npc;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityCamel;
import lotr.common.item.LOTRItemHaradRobes;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.world.biome.LOTRBiomeGenNearHarad;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityNomad extends LOTREntityNearHaradrimBase implements LOTRBiomeGenNearHarad.ImmuneToHeat {
	public static int[] nomadTurbanColors = {15392448, 13550476, 10063441, 8354400, 8343622};

	public LOTREntityNomad(World world) {
		super(world);
		addTargetTasks(false);
	}

	@Override
	public LOTRMiniQuest createMiniQuest() {
		return LOTRMiniQuestFactory.NOMAD.createQuest(this);
	}

	@Override
	public LOTRNPCMount createMountToRide() {
		LOTREntityCamel camel = new LOTREntityCamel(worldObj);
		camel.setNomadChestAndCarpet();
		return camel;
	}

	@Override
	public void dropHaradrimItems(boolean flag, int i) {
		if (rand.nextInt(5) == 0) {
			dropChestContents(LOTRChestContents.NOMAD_TENT, 1, 2 + i);
		}
	}

	@Override
	public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
		return LOTRMiniQuestFactory.NOMAD;
	}

	@Override
	public LOTRFoods getHaradrimDrinks() {
		return LOTRFoods.NOMAD_DRINK;
	}

	@Override
	public LOTRFoods getHaradrimFoods() {
		return LOTRFoods.NOMAD;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			return "nearHarad/nomad/nomad/friendly";
		}
		return "nearHarad/nomad/nomad/hostile";
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerHarad));
		npcItemsInv.setIdleItem(null);
		if (rand.nextInt(4) == 0) {
			ItemStack turban = new ItemStack(LOTRMod.helmetHaradRobes);
			int robeColor = nomadTurbanColors[rand.nextInt(nomadTurbanColors.length)];
			LOTRItemHaradRobes.setRobesColor(turban, robeColor);
			setCurrentItemOrArmor(4, turban);
		} else {
			setCurrentItemOrArmor(4, null);
		}
		return data;
	}

	@Override
	public void setupNPCName() {
		familyInfo.setName(LOTRNames.getNomadName(rand, familyInfo.isMale()));
	}
}
