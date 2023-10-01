package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGondorRenegade extends LOTREntityGondorSoldier {
	public static ItemStack[] weaponsUmbar = {new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.maceNearHarad), new ItemStack(LOTRMod.pikeNearHarad)};

	public LOTREntityGondorRenegade(World world) {
		super(world);
		npcShield = null;
		spawnRidingHorse = false;
		questInfo.setOfferChance(4000);
		questInfo.setMinAlignment(50.0f);
	}

	@Override
	public LOTRMiniQuest createMiniQuest() {
		return LOTRMiniQuestFactory.GONDOR_RENEGADE.createQuest(this);
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.NEAR_HARAD;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendlyAndAligned(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "nearHarad/renegade/hired";
			}
			return "nearHarad/renegade/friendly";
		}
		return "nearHarad/renegade/hostile";
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(weaponsUmbar.length);
		npcItemsInv.setMeleeWeapon(weaponsUmbar[i].copy());
		npcItemsInv.setMeleeWeaponMounted(npcItemsInv.getMeleeWeapon());
		if (rand.nextInt(5) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearNearHarad));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		npcItemsInv.setIdleItemMounted(npcItemsInv.getMeleeWeaponMounted());
		if (rand.nextInt(5) == 0) {
			setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsPelargir));
			setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsPelargir));
			setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyPelargir));
		} else {
			setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsGondor));
			setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsGondor));
			setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyGondor));
		}
		setCurrentItemOrArmor(4, null);
		return data;
	}
}
