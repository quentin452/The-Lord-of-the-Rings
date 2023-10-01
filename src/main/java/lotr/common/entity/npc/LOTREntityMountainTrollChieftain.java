package lotr.common.entity.npc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIBossJumpAttack;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import lotr.common.entity.projectile.LOTREntityThrownRock;
import lotr.common.item.LOTRItemBossTrophy;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class LOTREntityMountainTrollChieftain extends LOTREntityMountainTroll implements LOTRBoss {
	public int trollDeathTick;
	public int healAmount;

	public LOTREntityMountainTrollChieftain(World world) {
		super(world);
		tasks.addTask(2, new LOTREntityAIBossJumpAttack(this, 1.5, 0.03f));
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
		getEntityAttribute(npcAttackDamage).setBaseValue(8.0);
		getEntityAttribute(thrownRockDamage).setBaseValue(8.0);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		if (getTrollSpawnTick() < 100 || trollDeathTick > 0) {
			return false;
		}
		if (LOTRMod.getDamagingPlayerIncludingUnits(damagesource) == null && f > 1.0f) {
			f = 1.0f;
		}
		return super.attackEntityFrom(damagesource, f);
	}

	@Override
	public void damageEntity(DamageSource damagesource, float f) {
		super.damageEntity(damagesource, f);
		if (!worldObj.isRemote && getTrollArmorLevel() > 0 && getHealth() <= 0.0f) {
			setTrollArmorLevel(getTrollArmorLevel() - 1);
			if (getTrollArmorLevel() == 0) {
				double speed = getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
				getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(speed * 1.5);
			}
			double maxHealth = getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth * 2.0);
			setHealth(getMaxHealth());
			worldObj.setEntityState(this, (byte) 21);
		}
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		int dropped;
		super.dropFewItems(flag, i);
		int drops = 3 + rand.nextInt(4) + rand.nextInt(i * 2 + 1);
		for (int j = 0; j < drops; ++j) {
			dropTrollItems(flag, i);
		}
		int bones = MathHelper.getRandomIntegerInRange(rand, 4, 8) + rand.nextInt(i * 3 + 1);
		for (int j = 0; j < bones; ++j) {
			dropItem(LOTRMod.trollBone, 1);
		}
		for (int coins = MathHelper.getRandomIntegerInRange(rand, 50, 100 + i * 100); coins > 0; coins -= dropped) {
			dropped = Math.min(20, coins);
			dropItem(LOTRMod.silverCoin, dropped);
		}
		dropChestContents(LOTRChestContents.TROLL_HOARD, 5, 8 + i * 3);
		entityDropItem(new ItemStack(LOTRMod.bossTrophy, 1, LOTRItemBossTrophy.TrophyType.MOUNTAIN_TROLL_CHIEFTAIN.trophyID), 0.0f);
		float swordChance = 0.3f;
		swordChance += i * 0.1f;
		if (rand.nextFloat() < swordChance) {
			dropItem(LOTRMod.swordGondolin, 1);
		}
		float armorChance = 0.2f;
		armorChance += i * 0.05f;
		if (rand.nextFloat() < armorChance) {
			dropItem(LOTRMod.helmetGondolin, 1);
		}
		if (rand.nextFloat() < armorChance) {
			dropItem(LOTRMod.bodyGondolin, 1);
		}
		if (rand.nextFloat() < armorChance) {
			dropItem(LOTRMod.legsGondolin, 1);
		}
		if (rand.nextFloat() < armorChance) {
			dropItem(LOTRMod.bootsGondolin, 1);
		}
	}

	@Override
	public void dropTrollTotemPart(boolean flag, int i) {
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(22, (short) 0);
		dataWatcher.addObject(23, -1);
		dataWatcher.addObject(24, (byte) 2);
	}

	@Override
	public float getAlignmentBonus() {
		return 50.0f;
	}

	public float getArmorLevelChanceModifier() {
		int i = 3 - getTrollArmorLevel();
		if (i < 1) {
			i = 1;
		}
		return i;
	}

	@Override
	public float getBaseChanceModifier() {
		return bossInfo.getHealthChanceModifier() * getArmorLevelChanceModifier();
	}

	@Override
	public LOTRAchievement getBossKillAchievement() {
		return LOTRAchievement.killMountainTrollChieftain;
	}

	@Override
	public int getExperiencePoints(EntityPlayer entityplayer) {
		return 100;
	}

	public int getHealingEntityID() {
		return dataWatcher.getWatchableObjectInt(23);
	}

	public void setHealingEntityID(int i) {
		dataWatcher.updateObject(23, i);
	}

	public float getSpawningOffset(float f) {
		float f1 = (getTrollSpawnTick() + f) / 100.0f;
		f1 = Math.min(f1, 1.0f);
		return (1.0f - f1) * -5.0f;
	}

	@Override
	public LOTREntityThrownRock getThrownRock() {
		LOTREntityThrownRock rock = super.getThrownRock();
		float f = getBaseChanceModifier();
		f *= 0.4f;
		int maxNearbyTrolls = 5;
		List nearbyTrolls = worldObj.getEntitiesWithinAABB(LOTREntityTroll.class, boundingBox.expand(24.0, 8.0, 24.0));
		float nearbyModifier = (float) (maxNearbyTrolls - nearbyTrolls.size()) / maxNearbyTrolls;
		f *= nearbyModifier;
		if (rand.nextFloat() < f) {
			rock.setSpawnsTroll(true);
		}
		return rock;
	}

	@Override
	public int getTotalArmorValue() {
		return 12;
	}

	public int getTrollArmorLevel() {
		return dataWatcher.getWatchableObjectByte(24);
	}

	public void setTrollArmorLevel(int i) {
		dataWatcher.updateObject(24, (byte) i);
	}

	@Override
	public EntityAIBase getTrollRangedAttackAI() {
		return new LOTREntityAIRangedAttack(this, 1.2, 20, 50, 24.0f);
	}

	@Override
	public float getTrollScale() {
		return 2.0f;
	}

	public int getTrollSpawnTick() {
		return dataWatcher.getWatchableObjectShort(22);
	}

	public void setTrollSpawnTick(int i) {
		dataWatcher.updateObject(22, (short) i);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleHealthUpdate(byte b) {
		if (b == 20) {
			for (int i = 0; i < 360; i += 2) {
				float angle = (float) Math.toRadians(i);
				double distance = 2.0;
				double d = distance * MathHelper.sin(angle);
				double d1 = distance * MathHelper.cos(angle);
				LOTRMod.proxy.spawnParticle("largeStone", posX + d, boundingBox.minY + 0.1, posZ + d1, d * 0.2, 0.2, d1 * 0.2);
			}
		} else if (b == 21) {
			for (int i = 0; i < 64; ++i) {
				LOTRMod.proxy.spawnParticle("mtcArmor", posX + (rand.nextDouble() - 0.5) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5) * width, 0.0, 0.0, 0.0);
			}
		} else {
			super.handleHealthUpdate(b);
		}
	}

	@Override
	public boolean hasTwoHeads() {
		return true;
	}

	@Override
	public boolean isMovementBlocked() {
		if (getTrollSpawnTick() < 100 || trollDeathTick > 0) {
			return true;
		}
		return super.isMovementBlocked();
	}

	@Override
	public void onJumpAttackFall() {
		worldObj.setEntityState(this, (byte) 20);
		playSound("lotr:troll.rockSmash", 1.5f, 0.75f);
	}

	@Override
	public void onLivingUpdate() {
		double d1;
		double d2;
		LOTREntityThrownRock rock;
		super.onLivingUpdate();
		if (getTrollSpawnTick() < 100) {
			if (worldObj.isRemote) {
				for (int l = 0; l < 32; ++l) {
					double d = posX + rand.nextGaussian() * width * 0.5;
					d1 = posY + rand.nextDouble() * height + getSpawningOffset(0.0f);
					d2 = posZ + rand.nextGaussian() * width * 0.5;
					LOTRMod.proxy.spawnParticle("mtcSpawn", d, d1, d2, 0.0, 0.0, 0.0);
				}
			} else {
				setTrollSpawnTick(getTrollSpawnTick() + 1);
				if (getTrollSpawnTick() == 100) {
					bossInfo.doJumpAttack(1.5);
				}
			}
		}
		if (worldObj.isRemote && getTrollArmorLevel() == 0) {
			for (int i = 0; i < 4; ++i) {
				worldObj.spawnParticle("largesmoke", posX + (rand.nextDouble() - 0.5) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5) * width, 0.0, 0.0, 0.0);
			}
		}
		if (!worldObj.isRemote && (getTrollBurnTime() >= 0 || trollDeathTick > 0)) {
			if (trollDeathTick == 0) {
				worldObj.playSoundAtEntity(this, "lotr:troll.transform", getSoundVolume(), getSoundPitch());
			}
			if (trollDeathTick % 5 == 0) {
				worldObj.setEntityState(this, (byte) 15);
			}
			if (trollDeathTick % 10 == 0) {
				playSound(getLivingSound(), getSoundVolume() * 2.0f, 0.8f);
			}
			++trollDeathTick;
			rotationYaw += 60.0f * (rand.nextFloat() - 0.5f);
			rotationYawHead += 60.0f * (rand.nextFloat() - 0.5f);
			rotationPitch += 60.0f * (rand.nextFloat() - 0.5f);
			limbSwingAmount += 60.0f * (rand.nextFloat() - 0.5f);
			if (trollDeathTick >= 200) {
				setDead();
			}
		}
		if (!worldObj.isRemote && getHealth() < getMaxHealth()) {
			List nearbyTrolls;
			LOTREntityTroll troll;
			float f = getBaseChanceModifier();
			f *= 0.02f;
			if (rand.nextFloat() < f && !(nearbyTrolls = worldObj.getEntitiesWithinAABB(LOTREntityTroll.class, boundingBox.expand(24.0, 8.0, 24.0))).isEmpty() && !((troll = (LOTREntityTroll) nearbyTrolls.get(rand.nextInt(nearbyTrolls.size()))) instanceof LOTREntityMountainTrollChieftain) && troll.isEntityAlive()) {
				setHealingEntityID(troll.getEntityId());
				healAmount = 8 + rand.nextInt(3);
			}
		}
		if (getHealingEntityID() != -1) {
			Entity entity = worldObj.getEntityByID(getHealingEntityID());
			if (entity instanceof LOTREntityTroll && entity.isEntityAlive()) {
				if (worldObj.isRemote) {
					double d = entity.posX;
					d1 = entity.posY + entity.height / 2.0;
					d2 = entity.posZ;
					double d3 = posX - d;
					double d4 = posY + height / 2.0 - d1;
					double d5 = posZ - d2;
					LOTRMod.proxy.spawnParticle("mtcHeal", d, d1, d2, d3 / 30.0, d4 / 30.0, d5 / 30.0);
				} else {
					if (ticksExisted % 20 == 0) {
						heal(3.0f);
						entity.attackEntityFrom(DamageSource.generic, 3.0f);
						--healAmount;
						if (!entity.isEntityAlive() || getHealth() >= getMaxHealth() || healAmount <= 0) {
							setHealingEntityID(-1);
						}
					}
				}
			} else if (!worldObj.isRemote) {
				setHealingEntityID(-1);
			}
		}
		if (!worldObj.isRemote && getHealth() < getMaxHealth() && rand.nextInt(50) == 0 && !isThrowingRocks() && (rock = getThrownRock()).getSpawnsTroll()) {
			rock.setLocationAndAngles(posX, posY + height, posZ, 0.0f, 0.0f);
			rock.motionX = 0.0;
			rock.motionY = 1.5;
			rock.motionZ = 0.0;
			worldObj.spawnEntityInWorld(rock);
			swingItem();
		}
		if (!worldObj.isRemote) {
			float f = getBaseChanceModifier();
			f *= 0.05f;
			if (rand.nextFloat() < f) {
				bossInfo.doTargetedJumpAttack(1.5);
			}
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setTrollSpawnTick(nbt.getInteger("TrollSpawnTick"));
		trollDeathTick = nbt.getInteger("TrollDeathTick");
		if (nbt.hasKey("TrollArmorLevel")) {
			setTrollArmorLevel(nbt.getInteger("TrollArmorLevel"));
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("TrollSpawnTick", getTrollSpawnTick());
		nbt.setInteger("TrollDeathTick", trollDeathTick);
		nbt.setInteger("TrollArmorLevel", getTrollArmorLevel());
	}
}
