package lotr.common.entity.npc;

import lotr.common.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGulfHaradWarrior extends LOTREntityGulfHaradrim {
	public LOTREntityGulfHaradWarrior(World world) {
		super(world);
		addTargetTasks(true);
		spawnRidingHorse = rand.nextInt(10) == 0;
		npcShield = LOTRShields.ALIGNMENT_GULF;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(npcRangedAccuracy).setBaseValue(0.75);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "nearHarad/gulf/warrior/hired";
			}
			return "nearHarad/gulf/warrior/friendly";
		}
		return "nearHarad/gulf/warrior/hostile";
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		if (rand.nextInt(3) == 0) {
			int i = rand.nextInt(5);
			switch (i) {
				case 0:
				case 1:
					npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordHarad));
					break;
				case 2:
					npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerHarad));
					break;
				case 3:
					npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerHaradPoisoned));
					break;
				case 4:
					npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeHarad));
					break;
				default:
					break;
			}
		} else {
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordGulfHarad));
		}
		if (rand.nextInt(5) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearHarad));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsGulfHarad));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsGulfHarad));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyGulfHarad));
		if (rand.nextInt(10) == 0) {
			setCurrentItemOrArmor(4, null);
		} else {
			setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetGulfHarad));
		}
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
