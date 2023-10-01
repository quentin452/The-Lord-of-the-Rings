package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDunlendingWarrior extends LOTREntityDunlending {
	public LOTREntityDunlendingWarrior(World world) {
		super(world);
		npcShield = LOTRShields.ALIGNMENT_DUNLAND;
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public EntityAIBase getDunlendingAttackAI() {
		return new LOTREntityAIAttackOnCollide(this, 1.6, false);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(7);
		switch (i) {
			case 0:
				npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_sword));
				break;
			case 1:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordBronze));
				break;
			case 2:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerIron));
				break;
			case 3:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerBronze));
				break;
			case 4:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeIron));
				break;
			case 5:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeBronze));
				break;
			case 6:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeIron));
				break;
			default:
				break;
		}
		if (rand.nextInt(5) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			if (rand.nextBoolean()) {
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearIron));
			} else {
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearBronze));
			}
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsDunlending));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsDunlending));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyDunlending));
		if (rand.nextInt(10) != 0) {
			setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetDunlending));
		}
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
