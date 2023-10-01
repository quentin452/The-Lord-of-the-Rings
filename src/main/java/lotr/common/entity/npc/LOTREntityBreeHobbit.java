package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFoods;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.IPickpocketable;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.world.biome.LOTRBiomeGenBreeland;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityBreeHobbit extends LOTREntityHobbit implements IPickpocketable {
	public LOTREntityBreeHobbit(World world) {
		super(world);
		familyInfo.marriageEntityClass = LOTREntityBreeHobbit.class;
	}

	@Override
	public boolean canPickpocket() {
		return true;
	}

	@Override
	public void changeNPCNameForMarriage(LOTREntityNPC spouse) {
		super.changeNPCNameForMarriage(spouse);
	}

	@Override
	public LOTRMiniQuest createMiniQuest() {
		return LOTRMiniQuestFactory.BREE.createQuest(this);
	}

	@Override
	public void createNPCChildName(LOTREntityNPC maleParent, LOTREntityNPC femaleParent) {
		familyInfo.setName(LOTRNames.getBreeHobbitChildNameForParent(rand, familyInfo.isMale(), (LOTREntityHobbit) maleParent));
	}

	@Override
	public ItemStack createPickpocketItem() {
		return LOTRChestContents.BREE_PICKPOCKET.getOneItem(rand, true);
	}

	@Override
	public void dropHobbitItems(boolean flag, int i) {
		if (rand.nextInt(6) == 0) {
			dropChestContents(LOTRChestContents.BREE_HOUSE, 1, 2 + i);
		}
	}

	@Override
	public float getBlockPathWeight(int i, int j, int k) {
		float f = 0.0f;
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
		if (biome instanceof LOTRBiomeGenBreeland) {
			f += 20.0f;
		}
		return f;
	}

	@Override
	public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
		return LOTRMiniQuestFactory.BREE;
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.BREE;
	}

	@Override
	public LOTRFoods getHobbitDrinks() {
		return LOTRFoods.BREE_DRINK;
	}

	@Override
	public LOTRFoods getHobbitFoods() {
		return LOTRFoods.BREE;
	}

	@Override
	public LOTRAchievement getKillAchievement() {
		return LOTRAchievement.killBreeHobbit;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isDrunkard()) {
			return "bree/hobbit/drunkard/neutral";
		}
		if (isFriendlyAndAligned(entityplayer)) {
			return isChild() ? "bree/hobbit/child/friendly" : "bree/hobbit/friendly";
		}
		return isChild() ? "bree/hobbit/child/hostile" : "bree/hobbit/hostile";
	}

	@Override
	public void setupNPCName() {
		familyInfo.setName(LOTRNames.getBreeHobbitName(rand, familyInfo.isMale()));
	}
}
