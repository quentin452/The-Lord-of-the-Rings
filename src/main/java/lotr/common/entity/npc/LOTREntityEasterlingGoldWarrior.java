package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityEasterlingGoldWarrior extends LOTREntityEasterlingWarrior {
	public LOTREntityEasterlingGoldWarrior(World world) {
		super(world);
		npcShield = LOTRShields.ALIGNMENT_RHUN;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0);
	}

	@Override
	public LOTRNPCMount createMountToRide() {
		LOTREntityHorse horse = (LOTREntityHorse) super.createMountToRide();
		horse.setMountArmor(new ItemStack(LOTRMod.horseArmorRhunGold));
		return horse;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsRhunGold));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsRhunGold));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyRhunGold));
		setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetRhunGold));
		return data;
	}
}
