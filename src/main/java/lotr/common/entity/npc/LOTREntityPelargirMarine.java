package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityPelargirMarine extends LOTREntityGondorSoldier {
	public LOTREntityPelargirMarine(World world) {
		super(world);
		spawnRidingHorse = false;
		npcShield = LOTRShields.ALIGNMENT_PELARGIR;
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
		if (rand.nextBoolean()) {
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.tridentPelargir));
		} else {
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordPelargir));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsPelargir));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsPelargir));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyPelargir));
		setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetPelargir));
		return data;
	}
}
