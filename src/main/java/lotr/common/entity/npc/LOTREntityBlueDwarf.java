package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityBlueDwarf extends LOTREntityDwarf {
	public LOTREntityBlueDwarf(World world) {
		super(world);
		familyInfo.marriageEntityClass = LOTREntityBlueDwarf.class;
		familyInfo.marriageAchievement = LOTRAchievement.marryBlueDwarf;
	}

	@Override
	public LOTRMiniQuest createMiniQuest() {
		return LOTRMiniQuestFactory.BLUE_MOUNTAINS.createQuest(this);
	}

	@Override
	public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
		return LOTRMiniQuestFactory.BLUE_MOUNTAINS;
	}

	@Override
	public LOTRFoods getDwarfFoods() {
		return LOTRFoods.BLUE_DWARF;
	}

	@Override
	public Item getDwarfSteelDrop() {
		return LOTRMod.blueDwarfSteel;
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.BLUE_MOUNTAINS;
	}

	@Override
	public LOTRChestContents getGenericDrops() {
		return LOTRChestContents.BLUE_MOUNTAINS_STRONGHOLD;
	}

	@Override
	public LOTRAchievement getKillAchievement() {
		return LOTRAchievement.killBlueDwarf;
	}

	@Override
	public LOTRChestContents getLarderDrops() {
		return LOTRChestContents.BLUE_DWARF_HOUSE_LARDER;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "blueDwarf/dwarf/hired";
			}
			return isChild() ? "blueDwarf/child/friendly" : "blueDwarf/dwarf/friendly";
		}
		return isChild() ? "blueDwarf/child/hostile" : "blueDwarf/dwarf/hostile";
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerBlueDwarven));
		npcItemsInv.setIdleItem(null);
		return data;
	}
}
