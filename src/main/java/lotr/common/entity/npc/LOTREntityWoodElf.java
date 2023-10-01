package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFoods;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetWoodElf;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemMug;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.world.biome.LOTRBiomeGenWoodlandRealm;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityWoodElf extends LOTREntityElf {
	public LOTREntityWoodElf(World world) {
		super(world);
		tasks.addTask(2, rangedAttackAI);
		addTargetTasks(true, LOTREntityAINearestAttackableTargetWoodElf.class);
	}

	public static float getWoodlandTrustLevel() {
		return LOTRFaction.WOOD_ELF.getFirstRank().alignment;
	}

	@Override
	public boolean canElfSpawnHere() {
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(boundingBox.minY);
		int k = MathHelper.floor_double(posZ);
		return j > 62 && worldObj.getBlock(i, j - 1, k) == Blocks.grass;
	}

	@Override
	public EntityAIBase createElfMeleeAttackAI() {
		return createElfRangedAttackAI();
	}

	@Override
	public EntityAIBase createElfRangedAttackAI() {
		return new LOTREntityAIRangedAttack(this, 1.25, 30, 50, 16.0f);
	}

	@Override
	public LOTRMiniQuest createMiniQuest() {
		return LOTRMiniQuestFactory.WOOD_ELF.createQuest(this);
	}

	@Override
	public void dropElfItems(boolean flag, int i) {
		super.dropElfItems(flag, i);
		if (flag) {
			int dropChance = 20 - i * 4;
			if (rand.nextInt(Math.max(dropChance, 1)) == 0) {
				ItemStack elfDrink = new ItemStack(LOTRMod.mugRedWine);
				elfDrink.setItemDamage(1 + rand.nextInt(3));
				LOTRItemMug.setVessel(elfDrink, LOTRFoods.ELF_DRINK.getRandomVessel(rand), true);
				entityDropItem(elfDrink, 0.0f);
			}
		}
		if (rand.nextInt(6) == 0) {
			dropChestContents(LOTRChestContents.WOOD_ELF_HOUSE, 1, 1 + i);
		}
	}

	@Override
	public float getAlignmentBonus() {
		return 1.0f;
	}

	@Override
	public float getBlockPathWeight(int i, int j, int k) {
		float f = 0.0f;
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
		if (biome instanceof LOTRBiomeGenWoodlandRealm) {
			f += 20.0f;
		}
		return f;
	}

	@Override
	public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
		return LOTRMiniQuestFactory.WOOD_ELF;
	}

	@Override
	public LOTRFoods getElfDrinks() {
		return LOTRFoods.WOOD_ELF_DRINK;
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.WOOD_ELF;
	}

	@Override
	public LOTRAchievement getKillAchievement() {
		return LOTRAchievement.killWoodElf;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "woodElf/elf/hired";
			}
			if (LOTRLevelData.getData(entityplayer).getAlignment(getFaction()) >= getWoodlandTrustLevel()) {
				return "woodElf/elf/friendly";
			}
			return "woodElf/elf/neutral";
		}
		return "woodElf/elf/hostile";
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.mirkwoodBow));
		npcItemsInv.setMeleeWeapon(npcItemsInv.getRangedWeapon());
		npcItemsInv.setIdleItem(null);
		return data;
	}

	@Override
	public void setupNPCName() {
		familyInfo.setName(LOTRNames.getSindarinName(rand, familyInfo.isMale()));
	}
}
