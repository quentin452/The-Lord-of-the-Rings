package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityMoredainWarrior extends LOTREntityMoredain {
	public static ItemStack[] weaponsMoredain = {new ItemStack(LOTRMod.battleaxeMoredain), new ItemStack(LOTRMod.battleaxeMoredain), new ItemStack(LOTRMod.daggerMoredain), new ItemStack(LOTRMod.daggerMoredainPoisoned), new ItemStack(LOTRMod.clubMoredain), new ItemStack(LOTRMod.clubMoredain), new ItemStack(LOTRMod.spearMoredain), new ItemStack(LOTRMod.spearMoredain), new ItemStack(LOTRMod.swordMoredain), new ItemStack(LOTRMod.swordMoredain)};
	public static ItemStack[] weaponsIron = {new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.daggerNearHarad), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.maceNearHarad), new ItemStack(LOTRMod.spearNearHarad)};
	public static ItemStack[] weaponsBronze = {new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.spearHarad)};

	public LOTREntityMoredainWarrior(World world) {
		super(world);
		npcShield = LOTRShields.ALIGNMENT_MOREDAIN;
		spawnRidingHorse = rand.nextInt(10) == 0;
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
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		if (rand.nextInt(5) == 0) {
			if (rand.nextBoolean()) {
				int i = rand.nextInt(weaponsIron.length);
				npcItemsInv.setMeleeWeapon(weaponsIron[i].copy());
			} else {
				int i = rand.nextInt(weaponsBronze.length);
				npcItemsInv.setMeleeWeapon(weaponsBronze[i].copy());
			}
		} else {
			int i = rand.nextInt(weaponsMoredain.length);
			npcItemsInv.setMeleeWeapon(weaponsMoredain[i].copy());
		}
		if (rand.nextInt(3) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearMoredain));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsMoredain));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsMoredain));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyMoredain));
		if (rand.nextInt(3) == 0) {
			setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetMoredain));
		}
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
