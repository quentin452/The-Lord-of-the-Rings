package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.entity.projectile.LOTREntityThrowingAxe;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityLossarnachAxeman extends LOTREntityGondorSoldier {
	public EntityAIBase rangedAttackAI = createGondorRangedAI();
	public EntityAIBase meleeAttackAI;

	public LOTREntityLossarnachAxeman(World world) {
		super(world);
		spawnRidingHorse = false;
		npcShield = LOTRShields.ALIGNMENT_LOSSARNACH;
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float f) {
		ItemStack axeItem = npcItemsInv.getRangedWeapon();
		if (axeItem == null) {
			axeItem = new ItemStack(LOTRMod.throwingAxeLossarnach);
		}
		LOTREntityThrowingAxe axe = new LOTREntityThrowingAxe(worldObj, this, target, axeItem, 1.0f, (float) getEntityAttribute(npcRangedAccuracy).getAttributeValue());
		playSound("random.bow", 1.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.8f));
		worldObj.spawnEntityInWorld(axe);
		swingItem();
	}

	@Override
	public EntityAIBase createGondorAttackAI() {
		meleeAttackAI = new LOTREntityAIAttackOnCollide(this, 1.6, false);
		return meleeAttackAI;
	}

	public EntityAIBase createGondorRangedAI() {
		return new LOTREntityAIRangedAttack(this, 1.3, 30, 50, 16.0f);
	}

	@Override
	public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
		if (mode == LOTREntityNPC.AttackMode.IDLE) {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			setCurrentItemOrArmor(0, npcItemsInv.getIdleItem());
		}
		if (mode == LOTREntityNPC.AttackMode.MELEE) {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			tasks.addTask(2, meleeAttackAI);
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeapon());
		}
		if (mode == LOTREntityNPC.AttackMode.RANGED) {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			tasks.addTask(2, rangedAttackAI);
			setCurrentItemOrArmor(0, npcItemsInv.getRangedWeapon());
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeLossarnach));
		npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.throwingAxeLossarnach));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsLossarnach));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsLossarnach));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyLossarnach));
		if (rand.nextInt(3) == 0) {
			setCurrentItemOrArmor(4, null);
		} else {
			setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetLossarnach));
		}
		return data;
	}
}
