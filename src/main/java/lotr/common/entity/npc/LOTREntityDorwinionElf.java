package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemMug;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.world.biome.LOTRBiomeGenDorwinion;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityDorwinionElf extends LOTREntityElf {
	public LOTREntityDorwinionElf(World world) {
		super(world);
	}

	@Override
	public boolean canElfSpawnHere() {
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(boundingBox.minY);
		int k = MathHelper.floor_double(posZ);
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
		return j > 62 && worldObj.getBlock(i, j - 1, k) == biome.topBlock;
	}

	@Override
	public EntityAIBase createElfRangedAttackAI() {
		return createElfMeleeAttackAI();
	}

	@Override
	public LOTRMiniQuest createMiniQuest() {
		return LOTRMiniQuestFactory.DORWINION_ELF.createQuest(this);
	}

	@Override
	public void dropElfItems(boolean flag, int i) {
		super.dropElfItems(flag, i);
		if (flag) {
			int dropChance = 20 - i * 4;
			if (rand.nextInt(Math.max(dropChance, 1)) == 0) {
				ItemStack drink = LOTRFoods.DORWINION_DRINK.getRandomBrewableDrink(rand);
				LOTRItemMug.setStrengthMeta(drink, 1 + rand.nextInt(3));
				entityDropItem(drink, 0.0f);
			}
		}
		if (rand.nextInt(6) == 0) {
			dropChestContents(LOTRChestContents.DORWINION_HOUSE, 1, 1 + i);
		}
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public float getBlockPathWeight(int i, int j, int k) {
		float f = 0.0f;
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
		if (biome instanceof LOTRBiomeGenDorwinion) {
			f += 20.0f;
		}
		return f;
	}

	@Override
	public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
		return LOTRMiniQuestFactory.DORWINION_ELF;
	}

	@Override
	public LOTRFoods getElfDrinks() {
		return LOTRFoods.DORWINION_DRINK;
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.DORWINION;
	}

	@Override
	public LOTRAchievement getKillAchievement() {
		return LOTRAchievement.killDorwinionElf;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			return "dorwinion/elf/friendly";
		}
		return "dorwinion/elf/hostile";
	}

	@Override
	public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
		if (mode == LOTREntityNPC.AttackMode.IDLE) {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			setCurrentItemOrArmor(0, npcItemsInv.getIdleItem());
		} else {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			tasks.addTask(2, meleeAttackAI);
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeapon());
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerDorwinionElf));
		npcItemsInv.setIdleItem(null);
		return data;
	}

	@Override
	public void setupNPCName() {
		familyInfo.setName(LOTRNames.getSindarinName(rand, familyInfo.isMale()));
	}
}
