package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityEasterlingWarrior extends LOTREntityEasterlingLevyman {
	public LOTREntityEasterlingWarrior(World world) {
		super(world);
		addTargetTasks(true);
		spawnRidingHorse = rand.nextInt(6) == 0;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(npcRangedAccuracy).setBaseValue(0.75);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(10);
		switch (i) {
			case 0:
			case 1:
			case 2:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordRhun));
				break;
			case 3:
			case 4:
			case 5:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeRhun));
				break;
			case 6:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.polearmRhun));
				break;
			case 7:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerRhun));
				break;
			case 8:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerRhunPoisoned));
				break;
			case 9:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeRhun));
				break;
			default:
				break;
		}
		if (rand.nextInt(5) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearRhun));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsRhun));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsRhun));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyRhun));
		setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetRhun));
		return data;
	}
}
