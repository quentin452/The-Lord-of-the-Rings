package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityTormentedElf extends LOTREntityElf {
	public LOTREntityTormentedElf(World world) {
		super(world);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
	}

	@Override
	public boolean canElfSpawnHere() {
		return true;
	}

	@Override
	public void dropElfItems(boolean flag, int i) {
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
	}

	@Override
	public String getAttackSound() {
		return null;
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.UTUMNO;
	}

	@Override
	public LOTRAchievement getKillAchievement() {
		return LOTRAchievement.killTormentedElf;
	}

	@Override
	public String getLivingSound() {
		return null;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		return "utumno/elf/hostile";
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(5);
		switch (i) {
			case 0:
			case 1:
			case 2:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordUtumno));
				break;
			case 3:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerUtumno));
				break;
			case 4:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerUtumnoPoisoned));
				break;
			default:
				break;
		}
		npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.utumnoBow));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		return data;
	}
}
