package lotr.common.entity.npc;

import lotr.common.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDorwinionElfWarrior extends LOTREntityDorwinionElf {
	public LOTREntityDorwinionElfWarrior(World world) {
		super(world);
		tasks.addTask(2, meleeAttackAI);
		npcShield = LOTRShields.ALIGNMENT_DORWINION_ELF;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
	}

	@Override
	public float getAlignmentBonus() {
		return 3.0f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "dorwinion/elfWarrior/hired";
			}
			return "dorwinion/elfWarrior/friendly";
		}
		return "dorwinion/elfWarrior/hostile";
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(2);
		if (i == 0) {
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordDorwinionElf));
			if (rand.nextInt(5) == 0) {
				npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearBladorthin));
			}
		} else {
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearBladorthin));
			npcItemsInv.setSpearBackup(null);
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsDorwinionElf));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsDorwinionElf));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyDorwinionElf));
		if (rand.nextInt(10) != 0) {
			setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetDorwinionElf));
		}
		return data;
	}
}
