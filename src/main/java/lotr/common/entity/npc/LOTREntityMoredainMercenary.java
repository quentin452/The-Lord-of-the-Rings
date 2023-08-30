package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemHaradRobes;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityMoredainMercenary extends LOTREntityMoredain implements LOTRMercenary {
	public static ItemStack[] weaponsIron = {new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.daggerNearHarad), new ItemStack(LOTRMod.daggerNearHaradPoisoned), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.maceNearHarad), new ItemStack(LOTRMod.pikeNearHarad)};
	public static ItemStack[] weaponsBronze = {new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.daggerHaradPoisoned), new ItemStack(LOTRMod.pikeHarad)};
	public static int[] turbanColors = {10487808, 5976610, 14864579, 10852752, 11498561, 12361037};

	public LOTREntityMoredainMercenary(World world) {
		super(world);
		npcShield = LOTRShields.ALIGNMENT_MOREDAIN;
		spawnRidingHorse = false;
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return LOTRLevelData.getData(entityplayer).getAlignment(getFaction()) >= 0.0f && isFriendly(entityplayer);
	}

	@Override
	public EntityAIBase createHaradrimAttackAI() {
		return new LOTREntityAIAttackOnCollide(this, 1.7, true);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.NEAR_HARAD;
	}

	@Override
	public LOTRFaction getHiringFaction() {
		return LOTRFaction.NEAR_HARAD;
	}

	@Override
	public float getMercAlignmentRequired() {
		return 0.0f;
	}

	@Override
	public int getMercBaseCost() {
		return 20;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "nearHarad/mercenary/hired";
			}
			return "nearHarad/mercenary/friendly";
		}
		return "nearHarad/mercenary/hostile";
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
		if (rand.nextInt(8) == 0) {
			setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsGulfHarad));
			setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsGulfHarad));
			setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyGulfHarad));
		} else if (rand.nextInt(5) == 0) {
			setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsHarnedor));
			setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsHarnedor));
			setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyHarnedor));
		} else if (rand.nextInt(3) == 0) {
			setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsUmbar));
			setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsUmbar));
			setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyUmbar));
		} else {
			setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsNearHarad));
			setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsNearHarad));
			setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyNearHarad));
		}
		if (rand.nextInt(10) == 0) {
			setCurrentItemOrArmor(4, null);
		} else {
			ItemStack turban = new ItemStack(LOTRMod.helmetHaradRobes);
			int robeColor = turbanColors[rand.nextInt(turbanColors.length)];
			LOTRItemHaradRobes.setRobesColor(turban, robeColor);
			setCurrentItemOrArmor(4, turban);
		}
		return data;
	}

	@Override
	public void onUnitTrade(EntityPlayer entityplayer) {
		LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.hireMoredainMercenary);
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
