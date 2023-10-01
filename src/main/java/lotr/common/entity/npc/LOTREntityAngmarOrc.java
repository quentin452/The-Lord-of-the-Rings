package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityAngmarOrc extends LOTREntityOrc {
	public LOTREntityAngmarOrc(World world) {
		super(world);
	}

	@Override
	public LOTRMiniQuest createMiniQuest() {
		return LOTRMiniQuestFactory.ANGMAR.createQuest(this);
	}

	@Override
	public EntityAIBase createOrcAttackAI() {
		return new LOTREntityAIAttackOnCollide(this, 1.4, false);
	}

	@Override
	public void dropOrcItems(boolean flag, int i) {
		if (rand.nextInt(6) == 0) {
			dropChestContents(LOTRChestContents.ANGMAR_TENT, 1, 2 + i);
		}
	}

	@Override
	public float getAlignmentBonus() {
		return 1.0f;
	}

	@Override
	public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
		return LOTRMiniQuestFactory.ANGMAR;
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.ANGMAR;
	}

	@Override
	public LOTRAchievement getKillAchievement() {
		return LOTRAchievement.killAngmarOrc;
	}

	@Override
	public String getOrcSkirmishSpeech() {
		return "angmar/orc/skirmish";
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "angmar/orc/hired";
			}
			if (LOTRLevelData.getData(entityplayer).getAlignment(getFaction()) >= 100.0f) {
				return "angmar/orc/friendly";
			}
			return "angmar/orc/neutral";
		}
		return "angmar/orc/hostile";
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(10);
		switch (i) {
			case 0:
			case 1:
			case 2:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordAngmar));
				break;
			case 3:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeAngmar));
				break;
			case 4:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerAngmar));
				break;
			case 5:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerAngmarPoisoned));
				break;
			case 6:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerAngmar));
				break;
			case 7:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pickaxeAngmar));
				break;
			case 8:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.axeAngmar));
				break;
			case 9:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.polearmAngmar));
				break;
			default:
				break;
		}
		if (rand.nextInt(6) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearAngmar));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsAngmar));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsAngmar));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyAngmar));
		if (rand.nextInt(5) != 0) {
			setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetAngmar));
		}
		return data;
	}
}
