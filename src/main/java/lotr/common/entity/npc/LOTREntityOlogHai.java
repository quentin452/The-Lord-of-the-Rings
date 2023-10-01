package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import lotr.common.entity.ai.LOTREntityAIHiredRemainStill;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetOrc;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class LOTREntityOlogHai extends LOTREntityTroll {
	public LOTREntityOlogHai(World world) {
		super(world);
		tasks.taskEntries.clear();
		targetTasks.taskEntries.clear();
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new LOTREntityAIHiredRemainStill(this));
		tasks.addTask(2, new LOTREntityAIAttackOnCollide(this, 2.0, false));
		tasks.addTask(3, new LOTREntityAIFollowHiringPlayer(this));
		tasks.addTask(4, new EntityAIWander(this, 1.0));
		tasks.addTask(5, new EntityAIWatchClosest2(this, EntityPlayer.class, 12.0f, 0.02f));
		tasks.addTask(5, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 8.0f, 0.02f));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityLiving.class, 12.0f, 0.01f));
		tasks.addTask(7, new EntityAILookIdle(this));
		addTargetTasks(true, LOTREntityAINearestAttackableTargetOrc.class);
		trollImmuneToSun = true;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0);
		getEntityAttribute(npcAttackDamage).setBaseValue(7.0);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (super.attackEntityAsMob(entity)) {
			List entities;
			float attackDamage = (float) getEntityAttribute(LOTREntityNPC.npcAttackDamage).getAttributeValue();
			float knockbackModifier = 0.25f * attackDamage;
			entity.addVelocity(-MathHelper.sin(rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f, 0.0, MathHelper.cos(rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f);
			worldObj.playSoundAtEntity(entity, "lotr:troll.ologHai_hammer", 1.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f);
			if (!worldObj.isRemote && !(entities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, entity.boundingBox.expand(4.0, 4.0, 4.0))).isEmpty()) {
				for (Object entitie : entities) {
					EntityLivingBase hitEntity = (EntityLivingBase) entitie;
					if (hitEntity == this || hitEntity == entity || !LOTRMod.canNPCAttackEntity(this, hitEntity, false)) {
						continue;
					}
					float strength = 4.0f - entity.getDistanceToEntity(hitEntity);
					strength += 1.0f;
					if (strength > 4.0f) {
						strength = 4.0f;
					}
					if (!hitEntity.attackEntityFrom(DamageSource.causeMobDamage(this), strength / 4.0f * attackDamage)) {
						continue;
					}
					float knockback = strength * 0.25f;
					if (knockback < 0.75f) {
						knockback = 0.75f;
					}
					hitEntity.addVelocity(-MathHelper.sin(rotationYaw * 3.1415927f / 180.0f) * knockback * 0.5f, 0.2 + 0.12 * knockback, MathHelper.cos(rotationYaw * 3.1415927f / 180.0f) * knockback * 0.5f);
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean canTrollBeTickled(EntityPlayer entityplayer) {
		return false;
	}

	@Override
	public void dropTrollItems(boolean flag, int i) {
		if (flag) {
			int rareDropChance = 8 - i;
			if (rareDropChance < 1) {
				rareDropChance = 1;
			}
			if (rand.nextInt(rareDropChance) == 0) {
				int drops = 1 + rand.nextInt(2) + rand.nextInt(i + 1);
				for (int j = 0; j < drops; ++j) {
					dropItem(LOTRMod.orcSteel, 1);
				}
			}
		}
	}

	@Override
	public float getAlignmentBonus() {
		return 4.0f;
	}

	@Override
	public int getExperiencePoints(EntityPlayer entityplayer) {
		return 5 + rand.nextInt(8);
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.MORDOR;
	}

	@Override
	public LOTRAchievement getKillAchievement() {
		return LOTRAchievement.killOlogHai;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		return null;
	}

	@Override
	public int getTotalArmorValue() {
		return 15;
	}

	@Override
	public float getTrollScale() {
		return 1.25f;
	}

	@Override
	public boolean hasTrollName() {
		return false;
	}
}
