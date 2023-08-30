package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.*;
import lotr.common.world.biome.LOTRBiomeGenIthilien;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityRangerIthilien extends LOTREntityRanger {
	public LOTREntityRangerIthilien(World world) {
		super(world);
		npcCape = LOTRCapes.RANGER_ITHILIEN;
	}

	@Override
	public LOTRMiniQuest createMiniQuest() {
		return LOTRMiniQuestFactory.GONDOR.createQuest(this);
	}

	@Override
	public void dropDunedainItems(boolean flag, int i) {
		if (rand.nextInt(6) == 0) {
			dropChestContents(LOTRChestContents.GONDOR_HOUSE, 1, 2 + i);
		}
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public float getBlockPathWeight(int i, int j, int k) {
		float f = super.getBlockPathWeight(i, j, k);
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
		if (biome instanceof LOTRBiomeGenIthilien) {
			f += 20.0f;
		}
		return f;
	}

	@Override
	public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
		return LOTRMiniQuestFactory.GONDOR;
	}

	@Override
	public LOTRFoods getDunedainDrinks() {
		return LOTRFoods.GONDOR_DRINK;
	}

	@Override
	public LOTRFoods getDunedainFoods() {
		return LOTRFoods.GONDOR;
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.GONDOR;
	}

	@Override
	public LOTRAchievement getKillAchievement() {
		return LOTRAchievement.killRangerIthilien;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "gondor/ranger/hired";
			}
			return "gondor/ranger/friendly";
		}
		return "gondor/ranger/hostile";
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(4);
		if (i == 0 || i == 1 || i == 2) {
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerGondor));
		} else {
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordGondor));
		}
		npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.gondorBow));
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsRangerIthilien));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsRangerIthilien));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyRangerIthilien));
		setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetRangerIthilien));
		return data;
	}
}
