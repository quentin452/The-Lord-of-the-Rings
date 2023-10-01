package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import lotr.common.entity.projectile.LOTREntityThrowingAxe;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityAngmarHillmanAxeThrower extends LOTREntityAngmarHillmanWarrior {
	public LOTREntityAngmarHillmanAxeThrower(World world) {
		super(world);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float f) {
		ItemStack axeItem = npcItemsInv.getRangedWeapon();
		if (axeItem == null) {
			axeItem = new ItemStack(LOTRMod.throwingAxeIron);
		}
		LOTREntityThrowingAxe axe = new LOTREntityThrowingAxe(worldObj, this, target, axeItem, 1.0f, (float) getEntityAttribute(npcRangedAccuracy).getAttributeValue());
		playSound("random.bow", 1.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.8f));
		worldObj.spawnEntityInWorld(axe);
		swingItem();
	}

	@Override
	public EntityAIBase getHillmanAttackAI() {
		return new LOTREntityAIRangedAttack(this, 1.4, 40, 60, 12.0f);
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
		if (rand.nextInt(3) == 0) {
			npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.throwingAxeIron));
		} else {
			npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.throwingAxeBronze));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getRangedWeapon());
		return data;
	}
}
