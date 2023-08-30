package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class LOTREntityGaladhrimWarden extends LOTREntityGaladhrimElf {
	public int sneakCooldown;
	public EntityLivingBase prevElfTarget;

	public LOTREntityGaladhrimWarden(World world) {
		super(world);
		tasks.addTask(2, rangedAttackAI);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		boolean flag = super.attackEntityFrom(damagesource, f);
		if (flag && !worldObj.isRemote && isElfSneaking()) {
			setElfSneaking(false);
		}
		return flag;
	}

	@Override
	public EntityAIBase createElfRangedAttackAI() {
		return new LOTREntityAIRangedAttack(this, 1.25, 25, 35, 24.0f);
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(17, (byte) 0);
	}

	@Override
	public void func_145780_a(int i, int j, int k, Block block) {
		if (!isElfSneaking()) {
			super.func_145780_a(i, j, k, block);
		}
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "galadhrim/elf/hired";
			}
			return "galadhrim/warrior/friendly";
		}
		return "galadhrim/warrior/hostile";
	}

	public boolean isElfSneaking() {
		return dataWatcher.getWatchableObjectByte(17) == 1;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!worldObj.isRemote) {
			if (isElfSneaking()) {
				if (getAttackTarget() == null) {
					if (sneakCooldown > 0) {
						--sneakCooldown;
					} else {
						setElfSneaking(false);
					}
				} else {
					sneakCooldown = 20;
				}
			} else {
				sneakCooldown = 0;
			}
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerElven));
		npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.mallornBow));
		npcItemsInv.setIdleItem(npcItemsInv.getRangedWeapon());
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsHithlain));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsHithlain));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyHithlain));
		if (rand.nextInt(10) != 0) {
			setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetHithlain));
		}
		return data;
	}

	@Override
	public void setAttackTarget(EntityLivingBase target, boolean speak) {
		super.setAttackTarget(target, speak);
		if (target != null && target != prevElfTarget) {
			prevElfTarget = target;
			if (!worldObj.isRemote && !isElfSneaking()) {
				setElfSneaking(true);
			}
		}
	}

	public void setElfSneaking(boolean flag) {
		dataWatcher.updateObject(17, flag ? (byte) 1 : 0);
		if (flag) {
			sneakCooldown = 20;
		}
	}

	@Override
	public void swingItem() {
		super.swingItem();
		if (!worldObj.isRemote && isElfSneaking()) {
			setElfSneaking(false);
		}
	}
}
