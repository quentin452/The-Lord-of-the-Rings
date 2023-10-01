package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import lotr.common.item.LOTRItemCrossbow;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGundabadOrcArcher extends LOTREntityGundabadOrc {
	public LOTREntityGundabadOrcArcher(World world) {
		super(world);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float f) {
		if (isCrossbowOrc()) {
			npcCrossbowAttack(target, f);
		} else {
			npcArrowAttack(target, f);
		}
	}

	@Override
	public EntityAIBase createOrcAttackAI() {
		return new LOTREntityAIRangedAttack(this, 1.25, 30, 60, 16.0f);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		if (isCrossbowOrc()) {
			dropNPCCrossbowBolts(i);
		} else {
			dropNPCArrows(i);
		}
	}

	public boolean isCrossbowOrc() {
		ItemStack itemstack = npcItemsInv.getRangedWeapon();
		return itemstack != null && itemstack.getItem() instanceof LOTRItemCrossbow;
	}

	@Override
	public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
		if (mode == LOTREntityNPC.AttackMode.IDLE) {
			setCurrentItemOrArmor(0, npcItemsInv.getIdleItem());
		} else {
			setCurrentItemOrArmor(0, npcItemsInv.getRangedWeapon());
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		if (rand.nextInt(4) == 0) {
			if (rand.nextBoolean()) {
				npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.ironCrossbow));
			} else {
				npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.bronzeCrossbow));
			}
		} else if (rand.nextBoolean()) {
			npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.orcBow));
		} else {
			npcItemsInv.setRangedWeapon(new ItemStack(Items.bow));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getRangedWeapon());
		return data;
	}
}
