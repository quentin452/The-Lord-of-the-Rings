package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import lotr.common.entity.projectile.LOTREntityDart;
import lotr.common.item.LOTRItemBlowgun;
import lotr.common.item.LOTRItemDart;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class LOTREntityTauredainBlowgunner extends LOTREntityTauredain {
	public LOTREntityTauredainBlowgunner(World world) {
		super(world);
		addTargetTasks(true);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float f) {
		ItemStack heldItem = getHeldItem();
		float str = 1.0f + getDistanceToEntity(target) / 16.0f * 0.015f;
		LOTREntityDart dart = ((LOTRItemDart) LOTRMod.tauredainDart).createDart(worldObj, this, target, new ItemStack(LOTRMod.tauredainDart), str * LOTRItemBlowgun.getBlowgunLaunchSpeedFactor(heldItem), 1.0f);
		if (heldItem != null) {
			LOTRItemBlowgun.applyBlowgunModifiers(dart, heldItem);
		}
		playSound("lotr:item.dart", 1.0f, 1.0f / (rand.nextFloat() * 0.4f + 1.2f) + 0.5f);
		worldObj.spawnEntityInWorld(dart);
	}

	@Override
	public EntityAIBase createHaradrimAttackAI() {
		return new LOTREntityAIRangedAttack(this, 1.5, 10, 30, 16.0f);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		dropNPCAmmo(LOTRMod.tauredainDart, i);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendlyAndAligned(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "tauredain/warrior/hired";
			}
			return "tauredain/warrior/friendly";
		}
		return "tauredain/warrior/hostile";
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
		npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.tauredainBlowgun));
		npcItemsInv.setIdleItem(npcItemsInv.getRangedWeapon());
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsTauredain));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsTauredain));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyTauredain));
		return data;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		if (npcItemsInv.getRangedWeapon() == null) {
			npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.tauredainBlowgun));
			npcItemsInv.setIdleItem(npcItemsInv.getRangedWeapon());
		}
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
