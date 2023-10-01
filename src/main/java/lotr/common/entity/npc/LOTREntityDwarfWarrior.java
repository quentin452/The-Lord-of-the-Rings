package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDwarfWarrior extends LOTREntityDwarf {
	public LOTREntityDwarfWarrior(World world) {
		super(world);
		npcShield = LOTRShields.ALIGNMENT_DWARF;
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(7);
		switch (i) {
			case 0:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordDwarven));
				break;
			case 1:
			case 2:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeDwarven));
				break;
			case 3:
			case 4:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerDwarven));
				break;
			case 5:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.mattockDwarven));
				break;
			case 6:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeDwarven));
				break;
			default:
				break;
		}
		if (rand.nextInt(6) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearDwarven));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsDwarven));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsDwarven));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyDwarven));
		if (rand.nextInt(10) != 0) {
			setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetDwarven));
		}
		return data;
	}
}
