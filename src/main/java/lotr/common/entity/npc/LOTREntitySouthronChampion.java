package lotr.common.entity.npc;

import lotr.common.*;
import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntitySouthronChampion extends LOTREntityNearHaradrimWarrior {
	public static ItemStack[] weaponsChampion = {new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.maceNearHarad)};

	public LOTREntitySouthronChampion(World world) {
		super(world);
		spawnRidingHorse = true;
		npcCape = LOTRCapes.SOUTHRON_CHAMPION;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
		getEntityAttribute(npcRangedAccuracy).setBaseValue(0.5);
		getEntityAttribute(horseAttackSpeed).setBaseValue(1.9);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(weaponsChampion.length);
		npcItemsInv.setMeleeWeapon(weaponsChampion[i].copy());
		npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
		npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearNearHarad));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsNearHarad));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsNearHarad));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyNearHarad));
		setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetNearHaradWarlord));
		return data;
	}
}
