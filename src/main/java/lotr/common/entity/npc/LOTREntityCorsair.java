package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.quest.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityCorsair extends LOTREntityUmbarian {
	public static ItemStack[] weaponsCorsair = {new ItemStack(LOTRMod.swordCorsair), new ItemStack(LOTRMod.swordCorsair), new ItemStack(LOTRMod.daggerCorsair), new ItemStack(LOTRMod.daggerCorsairPoisoned), new ItemStack(LOTRMod.spearCorsair), new ItemStack(LOTRMod.spearCorsair), new ItemStack(LOTRMod.battleaxeCorsair), new ItemStack(LOTRMod.battleaxeCorsair)};
	public EntityAIBase rangedAttackAI = createHaradrimRangedAttackAI();
	public EntityAIBase meleeAttackAI;

	public LOTREntityCorsair(World world) {
		super(world);
		addTargetTasks(true);
		spawnRidingHorse = false;
		npcShield = LOTRShields.ALIGNMENT_CORSAIR;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(npcRangedAccuracy).setBaseValue(0.5);
	}

	@Override
	public EntityAIBase createHaradrimAttackAI() {
		meleeAttackAI = new LOTREntityAIAttackOnCollide(this, 1.6, true);
		return meleeAttackAI;
	}

	public EntityAIBase createHaradrimRangedAttackAI() {
		return new LOTREntityAIRangedAttack(this, 1.5, 30, 40, 16.0f);
	}

	@Override
	public LOTRMiniQuest createMiniQuest() {
		return LOTRMiniQuestFactory.CORSAIR.createQuest(this);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		dropNPCArrows(i);
	}

	@Override
	public void dropHaradrimItems(boolean flag, int i) {
		if (rand.nextInt(3) == 0) {
			dropChestContents(LOTRChestContents.CORSAIR, 1, 2 + i);
		}
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
		return LOTRMiniQuestFactory.CORSAIR;
	}

	@Override
	public LOTRFoods getHaradrimDrinks() {
		return LOTRFoods.CORSAIR_DRINK;
	}

	@Override
	public LOTRFoods getHaradrimFoods() {
		return LOTRFoods.CORSAIR;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "nearHarad/umbar/corsair/hired";
			}
			return "nearHarad/umbar/corsair/friendly";
		}
		return "nearHarad/umbar/corsair/hostile";
	}

	@Override
	public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
		if (mode == LOTREntityNPC.AttackMode.IDLE) {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			setCurrentItemOrArmor(0, npcItemsInv.getIdleItem());
		}
		if (mode == LOTREntityNPC.AttackMode.MELEE) {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			tasks.addTask(2, meleeAttackAI);
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeapon());
		}
		if (mode == LOTREntityNPC.AttackMode.RANGED) {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			tasks.addTask(2, rangedAttackAI);
			setCurrentItemOrArmor(0, npcItemsInv.getRangedWeapon());
		}
	}

	@Override
	public void onKillEntity(EntityLivingBase entity) {
		super.onKillEntity(entity);
		if (entity instanceof LOTREntityNPC && ((LOTREntityNPC) entity).canDropRares() && rand.nextInt(2) == 0) {
			int coins = getRandomCoinDropAmount();
			coins = (int) (coins * MathHelper.randomFloatClamp(rand, 1.0f, 3.0f));
			if (coins > 0) {
				entity.dropItem(LOTRMod.silverCoin, coins);
			}
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(weaponsCorsair.length);
		npcItemsInv.setMeleeWeapon(weaponsCorsair[i].copy());
		if (rand.nextInt(5) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearCorsair));
		}
		npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.nearHaradBow));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsCorsair));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsCorsair));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyCorsair));
		if (rand.nextInt(2) == 0) {
			setCurrentItemOrArmor(4, null);
		} else {
			setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetCorsair));
		}
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
