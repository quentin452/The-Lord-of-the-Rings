package lotr.common.entity.npc;

import lotr.common.LOTRCapes;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class LOTREntityRanger extends LOTREntityDunedain {
	public EntityAIBase rangedAttackAI = createDunedainRangedAI();
	public EntityAIBase meleeAttackAI;
	public int sneakCooldown;
	public EntityLivingBase prevRangerTarget;

	protected LOTREntityRanger(World world) {
		super(world);
		addTargetTasks(true);
		npcCape = LOTRCapes.RANGER;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
		getEntityAttribute(npcRangedAccuracy).setBaseValue(0.5);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		boolean flag = super.attackEntityFrom(damagesource, f);
		if (flag && !worldObj.isRemote && isRangerSneaking()) {
			setRangerSneaking(false);
		}
		return flag;
	}

	@Override
	public EntityAIBase createDunedainAttackAI() {
		meleeAttackAI = new LOTREntityAIAttackOnCollide(this, 1.5, true);
		return meleeAttackAI;
	}

	public EntityAIBase createDunedainRangedAI() {
		return new LOTREntityAIRangedAttack(this, 1.25, 20, 40, 20.0f);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		dropNPCArrows(i);
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(17, (byte) 0);
	}

	@Override
	public void func_145780_a(int i, int j, int k, Block block) {
		if (!isRangerSneaking()) {
			super.func_145780_a(i, j, k, block);
		}
	}

	public boolean isRangerSneaking() {
		return dataWatcher.getWatchableObjectByte(17) == 1;
	}

	public void setRangerSneaking(boolean flag) {
		dataWatcher.updateObject(17, flag ? (byte) 1 : 0);
		if (flag) {
			sneakCooldown = 20;
		}
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
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!worldObj.isRemote) {
			if (ridingEntity == null) {
				if (isRangerSneaking()) {
					if (getAttackTarget() == null) {
						if (sneakCooldown > 0) {
							--sneakCooldown;
						} else {
							setRangerSneaking(false);
						}
					} else {
						sneakCooldown = 20;
					}
				} else {
					sneakCooldown = 0;
				}
			} else if (isRangerSneaking()) {
				setRangerSneaking(false);
			}
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerIron));
		npcItemsInv.setRangedWeapon(new ItemStack(Items.bow));
		npcItemsInv.setIdleItem(null);
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsRanger));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsRanger));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyRanger));
		setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetRanger));
		return data;
	}

	@Override
	public void setAttackTarget(EntityLivingBase target, boolean speak) {
		super.setAttackTarget(target, speak);
		if (target != null && target != prevRangerTarget) {
			prevRangerTarget = target;
			if (!worldObj.isRemote && !isRangerSneaking() && ridingEntity == null) {
				setRangerSneaking(true);
			}
		}
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}

	@Override
	public void swingItem() {
		super.swingItem();
		if (!worldObj.isRemote && isRangerSneaking()) {
			setRangerSneaking(false);
		}
	}
}
