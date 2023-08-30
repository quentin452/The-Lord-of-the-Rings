package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityUtumnoOrc extends LOTREntityOrc {
	public LOTREntityUtumnoOrc(World world) {
		super(world);
		setSize(0.6f, 1.8f);
		isWeakOrc = false;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(24.0);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
	}

	@Override
	public boolean canOrcSkirmish() {
		return false;
	}

	@Override
	public EntityAIBase createOrcAttackAI() {
		return new LOTREntityAIAttackOnCollide(this, 1.5, true);
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.UTUMNO;
	}

	@Override
	public LOTRAchievement getKillAchievement() {
		return LOTRAchievement.killUtumnoOrc;
	}

	@Override
	public float getSoundPitch() {
		return super.getSoundPitch() * 0.65f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		return "utumno/orc/hostile";
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(6);
		switch (i) {
			case 0:
			case 1:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordUtumno));
				break;
			case 2:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeUtumno));
				break;
			case 3:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerUtumno));
				break;
			case 4:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerUtumnoPoisoned));
				break;
			case 5:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerUtumno));
				break;
			default:
				break;
		}
		if (rand.nextInt(6) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearUtumno));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsUtumno));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsUtumno));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyUtumno));
		if (rand.nextInt(10) != 0) {
			setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetUtumno));
		}
		return data;
	}

	@Override
	public void setupNPCName() {
		if (rand.nextInt(5) == 0) {
			familyInfo.setName(LOTRNames.getSindarinOrQuenyaName(rand, rand.nextBoolean()));
		} else {
			familyInfo.setName(LOTRNames.getOrcName(rand));
		}
	}
}
