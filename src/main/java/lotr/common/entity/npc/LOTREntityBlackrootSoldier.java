package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityBlackrootSoldier extends LOTREntityGondorSoldier {
	public LOTREntityBlackrootSoldier(World world) {
		super(world);
		spawnRidingHorse = rand.nextInt(10) == 0;
		npcShield = LOTRShields.ALIGNMENT_BLACKROOT_VALE;
	}

	@Override
	public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
		if (mode == LOTREntityNPC.AttackMode.IDLE) {
			setCurrentItemOrArmor(0, npcItemsInv.getIdleItem());
		} else {
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeapon());
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordGondor));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsBlackroot));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsBlackroot));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyBlackroot));
		if (rand.nextInt(10) == 0) {
			setCurrentItemOrArmor(4, null);
		} else {
			setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetBlackroot));
		}
		return data;
	}
}
