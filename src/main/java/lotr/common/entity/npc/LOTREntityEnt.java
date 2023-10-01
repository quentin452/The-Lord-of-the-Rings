package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockCorruptMallorn;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIEntHealSapling;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemEntDraught;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class LOTREntityEnt extends LOTREntityTree {
	public Random branchRand = new Random();
	public int eyesClosed;
	public ChunkCoordinates saplingHealTarget;
	public boolean canHealSapling = true;

	public LOTREntityEnt(World world) {
		super(world);
		setSize(1.4f, 4.6f);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new LOTREntityAIEntHealSapling(this, 1.5));
		tasks.addTask(1, new LOTREntityAIAttackOnCollide(this, 2.0, false));
		tasks.addTask(2, new EntityAIWander(this, 1.0));
		tasks.addTask(3, new EntityAIWatchClosest2(this, EntityPlayer.class, 12.0f, 0.02f));
		tasks.addTask(3, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 8.0f, 0.02f));
		tasks.addTask(4, new EntityAIWatchClosest(this, EntityLiving.class, 10.0f, 0.02f));
		tasks.addTask(5, new EntityAILookIdle(this));
		addTargetTasks(true);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
		getEntityAttribute(npcAttackDamage).setBaseValue(7.0);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (super.attackEntityAsMob(entity)) {
			float knockbackModifier = 1.5f;
			entity.addVelocity(-MathHelper.sin(rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f, 0.15, MathHelper.cos(rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f);
			return true;
		}
		return false;
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		boolean flag = super.attackEntityFrom(damagesource, f);
		if (!worldObj.isRemote && flag) {
			if (damagesource.getEntity() != null) {
				setHealingSapling(false);
			}
			if (getAttackTarget() != null) {
				canHealSapling = false;
			}
		}
		return flag;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		if (flag) {
			int dropChance = 10 - i * 2;
			if (dropChance < 1) {
				dropChance = 1;
			}
			if (rand.nextInt(dropChance) == 0) {
				entityDropItem(new ItemStack(LOTRMod.entDraught, 1, rand.nextInt(LOTRItemEntDraught.draughtTypes.length)), 0.0f);
			}
		}
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(18, (byte) 0);
	}

	@Override
	public void func_145780_a(int i, int j, int k, Block block) {
		playSound("lotr:ent.step", 0.75f, getSoundPitch());
	}

	@Override
	public float getAlignmentBonus() {
		return 3.0f;
	}

	@Override
	public int getExperiencePoints(EntityPlayer entityplayer) {
		return 5 + rand.nextInt(6);
	}

	public int getExtraHeadBranches() {
		long l = getUniqueID().getLeastSignificantBits();
		l = l * 365620672396L ^ l * 12784892284L ^ l;
		l = l * l * 18569660L + l * 6639092L;
		branchRand.setSeed(l);
		if (branchRand.nextBoolean()) {
			return 0;
		}
		return MathHelper.getRandomIntegerInRange(branchRand, 2, 5);
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.FANGORN;
	}

	@Override
	public LOTRAchievement getKillAchievement() {
		return LOTRAchievement.killEnt;
	}

	@Override
	public String getNPCName() {
		return familyInfo.getName();
	}

	@Override
	public float getSoundVolume() {
		return 1.5f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			return "ent/ent/friendly";
		}
		return "ent/ent/hostile";
	}

	@Override
	public LOTRAchievement getTalkAchievement() {
		return LOTRAchievement.talkEnt;
	}

	public boolean isHealingSapling() {
		return dataWatcher.getWatchableObjectByte(18) == 1;
	}

	public void setHealingSapling(boolean flag) {
		dataWatcher.updateObject(18, flag ? (byte) 1 : 0);
	}

	@Override
	public void onDeath(DamageSource damagesource) {
		super.onDeath(damagesource);
		if (!worldObj.isRemote && damagesource.getEntity() instanceof EntityPlayer && saplingHealTarget != null) {
			int i = saplingHealTarget.posX;
			int j = saplingHealTarget.posY;
			int k = saplingHealTarget.posZ;
			Block block = worldObj.getBlock(i, j, k);
			int meta = worldObj.getBlockMetadata(i, j, k);
			if (block == LOTRMod.corruptMallorn) {
				if (++meta >= LOTRBlockCorruptMallorn.ENT_KILLS) {
					LOTRBlockCorruptMallorn.summonEntBoss(worldObj, i, j, k);
				} else {
					worldObj.setBlockMetadataWithNotify(i, j, k, meta, 3);
				}
			}
		}
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (worldObj.isRemote) {
			if (eyesClosed > 0) {
				--eyesClosed;
			} else if (rand.nextInt(400) == 0) {
				eyesClosed = 30;
			}
			if (isHealingSapling()) {
				for (int l = 0; l < 2; ++l) {
					float angle = rotationYawHead + 90.0f + MathHelper.randomFloatClamp(rand, -40.0f, 40.0f);
					angle = (float) Math.toRadians(angle);
					double d = posX + MathHelper.cos(angle) * 1.5;
					double d1 = boundingBox.minY + height * MathHelper.randomFloatClamp(rand, 0.3f, 0.6f);
					double d2 = posZ + MathHelper.sin(angle) * 1.5;
					double d3 = MathHelper.cos(angle) * 0.06;
					double d4 = -0.03;
					double d5 = MathHelper.sin(angle) * 0.06;
					LOTRMod.proxy.spawnParticle("leafGold_30", d, d1, d2, d3, d4, d5);
				}
			}
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		if (nbt.hasKey("EntName")) {
			familyInfo.setName(nbt.getString("EntName"));
		}
		if (nbt.hasKey("SaplingHealX")) {
			int x = nbt.getInteger("SaplingHealX");
			int y = nbt.getInteger("SaplingHealY");
			int z = nbt.getInteger("SaplingHealZ");
			saplingHealTarget = new ChunkCoordinates(x, y, z);
		}
	}

	@Override
	public void setAttackTarget(EntityLivingBase target, boolean speak) {
		super.setAttackTarget(target, speak);
		if (getAttackTarget() == null) {
			canHealSapling = true;
		}
	}

	@Override
	public void setupNPCName() {
		familyInfo.setName(LOTRNames.getEntName(rand));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		if (saplingHealTarget != null) {
			nbt.setInteger("SaplingHealX", saplingHealTarget.posX);
			nbt.setInteger("SaplingHealY", saplingHealTarget.posY);
			nbt.setInteger("SaplingHealZ", saplingHealTarget.posZ);
		}
	}
}
