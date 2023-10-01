package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityRohirrimWarrior extends LOTREntityRohanMan {
	public LOTREntityRohirrimWarrior(World world) {
		super(world);
		addTargetTasks(true);
		spawnRidingHorse = rand.nextInt(3) == 0;
		npcShield = LOTRShields.ALIGNMENT_ROHAN;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
		getEntityAttribute(npcRangedAccuracy).setBaseValue(0.75);
		getEntityAttribute(horseAttackSpeed).setBaseValue(2.0);
	}

	@Override
	public EntityAIBase createRohanAttackAI() {
		return new LOTREntityAIAttackOnCollide(this, 1.45, false);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "rohan/warrior/hired";
			}
			return "rohan/warrior/friendly";
		}
		return "rohan/warrior/hostile";
	}

	@Override
	public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
		if (mode == LOTREntityNPC.AttackMode.IDLE) {
			if (mounted) {
				setCurrentItemOrArmor(0, npcItemsInv.getIdleItemMounted());
			} else {
				setCurrentItemOrArmor(0, npcItemsInv.getIdleItem());
			}
		} else if (mounted) {
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeaponMounted());
		} else {
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeapon());
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		if (rand.nextInt(3) == 0) {
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeRohan));
		} else {
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordRohan));
		}
		if (rand.nextInt(4) == 0) {
			npcItemsInv.setMeleeWeaponMounted(new ItemStack(LOTRMod.lanceRohan));
		} else {
			npcItemsInv.setMeleeWeaponMounted(npcItemsInv.getMeleeWeapon());
		}
		if (rand.nextInt(4) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearRohan));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		npcItemsInv.setIdleItemMounted(npcItemsInv.getMeleeWeaponMounted());
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsRohan));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsRohan));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyRohan));
		setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetRohan));
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
