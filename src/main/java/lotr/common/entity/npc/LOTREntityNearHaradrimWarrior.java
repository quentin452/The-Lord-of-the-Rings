package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.item.LOTRItemHaradRobes;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityNearHaradrimWarrior extends LOTREntityNearHaradrim {
	public static ItemStack[] weaponsIron = {new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.daggerNearHarad), new ItemStack(LOTRMod.daggerNearHaradPoisoned), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.maceNearHarad), new ItemStack(LOTRMod.pikeNearHarad)};
	public static ItemStack[] weaponsBronze = {new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.daggerHaradPoisoned), new ItemStack(LOTRMod.pikeHarad)};
	public static int[] turbanColors = {1643539, 6309443, 7014914, 7809314, 5978155};

	public LOTREntityNearHaradrimWarrior(World world) {
		super(world);
		addTargetTasks(true);
		spawnRidingHorse = false;
		npcShield = LOTRShields.ALIGNMENT_NEAR_HARAD;
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
				return "nearHarad/coast/warrior/hired";
			}
			return "nearHarad/coast/warrior/friendly";
		}
		return "nearHarad/coast/warrior/hostile";
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		int i;
		data = super.onSpawnWithEgg(data);
		if (rand.nextInt(3) == 0) {
			i = rand.nextInt(weaponsBronze.length);
			npcItemsInv.setMeleeWeapon(weaponsBronze[i].copy());
			if (rand.nextInt(5) == 0) {
				npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearHarad));
			}
		} else {
			i = rand.nextInt(weaponsIron.length);
			npcItemsInv.setMeleeWeapon(weaponsIron[i].copy());
			if (rand.nextInt(5) == 0) {
				npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearNearHarad));
			}
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsNearHarad));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsNearHarad));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyNearHarad));
		if (rand.nextInt(10) == 0) {
			setCurrentItemOrArmor(4, null);
		} else if (rand.nextInt(5) == 0) {
			ItemStack turban = new ItemStack(LOTRMod.helmetHaradRobes);
			int robeColor = turbanColors[rand.nextInt(turbanColors.length)];
			LOTRItemHaradRobes.setRobesColor(turban, robeColor);
			setCurrentItemOrArmor(4, turban);
		} else {
			setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetNearHarad));
		}
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
