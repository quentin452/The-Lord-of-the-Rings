package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDaleSoldier extends LOTREntityDaleLevyman {
	public LOTREntityDaleSoldier(World world) {
		super(world);
		npcShield = LOTRShields.ALIGNMENT_DALE;
		spawnRidingHorse = rand.nextInt(8) == 0;
	}

	@Override
	public EntityAIBase createDaleAttackAI() {
		return new LOTREntityAIAttackOnCollide(this, 1.5, true);
	}

	@Override
	public LOTRNPCMount createMountToRide() {
		LOTREntityHorse horse = (LOTREntityHorse) super.createMountToRide();
		horse.setMountArmor(new ItemStack(LOTRMod.horseArmorDale));
		return horse;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(5);
		switch (i) {
			case 0:
			case 1:
			case 2:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordDale));
				break;
			case 3:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeDale));
				break;
			case 4:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeDale));
				break;
			default:
				break;
		}
		if (rand.nextInt(6) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearDale));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsDale));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsDale));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyDale));
		if (rand.nextInt(10) == 0) {
			setCurrentItemOrArmor(4, null);
		} else {
			setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetDale));
		}
		return data;
	}
}
