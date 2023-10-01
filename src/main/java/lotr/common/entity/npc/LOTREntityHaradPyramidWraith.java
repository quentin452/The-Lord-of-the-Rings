package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityHaradPyramidWraith extends LOTREntitySkeletalWraith {
	public LOTREntityHaradPyramidWraith(World world) {
		super(world);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		if (rand.nextBoolean()) {
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerNearHaradPoisoned));
			npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
			setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsNearHarad));
			setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsNearHarad));
			setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyNearHarad));
		} else {
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerHaradPoisoned));
			npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
			setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsGulfHarad));
			setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsGulfHarad));
			setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyGulfHarad));
		}
		return data;
	}
}
