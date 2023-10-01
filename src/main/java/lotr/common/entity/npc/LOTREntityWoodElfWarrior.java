package lotr.common.entity.npc;

import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import lotr.common.entity.animal.LOTREntityElk;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityWoodElfWarrior extends LOTREntityWoodElf {
	public LOTREntityWoodElfWarrior(World world) {
		super(world);
		tasks.removeTask(rangedAttackAI);
		tasks.addTask(2, meleeAttackAI);
		spawnRidingHorse = rand.nextInt(4) == 0;
		npcShield = LOTRShields.ALIGNMENT_WOOD_ELF;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
	}

	@Override
	public EntityAIBase createElfMeleeAttackAI() {
		return new LOTREntityAIAttackOnCollide(this, 1.4, false);
	}

	@Override
	public EntityAIBase createElfRangedAttackAI() {
		return new LOTREntityAIRangedAttack(this, 1.25, 25, 35, 24.0f);
	}

	@Override
	public LOTRNPCMount createMountToRide() {
		LOTREntityElk elk = new LOTREntityElk(worldObj);
		elk.setMountArmor(new ItemStack(LOTRMod.elkArmorWoodElven));
		return elk;
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "woodElf/elf/hired";
			}
			if (LOTRLevelData.getData(entityplayer).getAlignment(getFaction()) >= LOTREntityWoodElf.getWoodlandTrustLevel()) {
				return "woodElf/warrior/friendly";
			}
			return "woodElf/elf/neutral";
		}
		return "woodElf/warrior/hostile";
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(6);
		if (i == 0) {
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.polearmWoodElven));
		} else if (i == 1) {
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.longspearWoodElven));
		} else {
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordWoodElven));
		}
		npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.mirkwoodBow));
		if (rand.nextInt(5) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearWoodElven));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsWoodElven));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsWoodElven));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyWoodElven));
		if (rand.nextInt(10) == 0) {
			setCurrentItemOrArmor(4, null);
		} else {
			setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetWoodElven));
		}
		return data;
	}
}
