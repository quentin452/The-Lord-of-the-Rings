package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIBalrogCharge;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.LOTRWorldProviderUtumno;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityBalrog extends LOTREntityNPC {
	public static IAttribute balrogChargeDamage = new RangedAttribute("lotr.balrogChargeDamage", 2.0, 0.0, Double.MAX_VALUE).setDescription("Balrog Charge Damage");
	public int chargeLean;
	public int prevChargeLean;
	public int chargeFrustration;

	public LOTREntityBalrog(World world) {
		super(world);
		setSize(2.4f, 5.0f);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new LOTREntityAIBalrogCharge(this, 3.0, 20.0f, 200));
		tasks.addTask(2, new LOTREntityAIAttackOnCollide(this, 1.6, false));
		tasks.addTask(3, new LOTREntityAIFollowHiringPlayer(this));
		tasks.addTask(4, new EntityAIWander(this, 1.0));
		tasks.addTask(5, new EntityAIWatchClosest2(this, EntityPlayer.class, 24.0f, 0.02f));
		tasks.addTask(5, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 16.0f, 0.02f));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityLiving.class, 12.0f, 0.02f));
		tasks.addTask(7, new EntityAILookIdle(this));
		addTargetTasks(true);
		spawnsInDarkness = true;
		isImmuneToFire = true;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
		getEntityAttribute(npcAttackDamage).setBaseValue(10.0);
		getAttributeMap().registerAttribute(balrogChargeDamage).setBaseValue(15.0);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (super.attackEntityAsMob(entity)) {
			EntityLivingBase curTarget = getAttackTarget();
			if (curTarget != null && entity == curTarget) {
				chargeFrustration = 0;
			}
			if (getHeldItem() == null) {
				entity.setFire(5);
			}
			float attackDamage = (float) getEntityAttribute(LOTREntityNPC.npcAttackDamage).getAttributeValue();
			float knockbackModifier = 0.25f * attackDamage;
			entity.addVelocity(-MathHelper.sin(rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f, knockbackModifier * 0.1, MathHelper.cos(rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f);
			return true;
		}
		return false;
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		if (damagesource == DamageSource.fall) {
			return false;
		}
		boolean flag = super.attackEntityFrom(damagesource, f);
		if (flag) {
			EntityLivingBase curTarget = getAttackTarget();
			if (curTarget != null && damagesource.getEntity() == curTarget && damagesource.getSourceOfDamage() == curTarget) {
				chargeFrustration = 0;
			}
			return true;
		}
		return false;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int coals = MathHelper.getRandomIntegerInRange(rand, 4, 16 + i * 8);
		for (int l = 0; l < coals; ++l) {
			dropItem(Items.coal, 1);
		}
		int fires = 1;
		if (i > 0) {
			float x = MathHelper.randomFloatClamp(rand, 0.0f, i * 0.667f);
			while (x > 1.0f) {
				x -= 1.0f;
				++fires;
			}
			if (rand.nextFloat() < x) {
				++fires;
			}
		}
		for (int l = 0; l < fires; ++l) {
			dropItem(LOTRMod.balrogFire, 1);
		}
	}

	@Override
	public void dropNPCEquipment(boolean flag, int i) {
		ItemStack heldItem;
		if (flag && rand.nextInt(3) == 0 && (heldItem = getHeldItem()) != null) {
			entityDropItem(heldItem, 0.0f);
			setCurrentItemOrArmor(0, null);
		}
		super.dropNPCEquipment(flag, i);
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(20, (byte) 0);
	}

	@Override
	public void func_145780_a(int i, int j, int k, Block block) {
		playSound("lotr:troll.step", 0.75f, getSoundPitch());
	}

	@Override
	public int getExperiencePoints(EntityPlayer entityplayer) {
		return 15 + rand.nextInt(10);
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.UTUMNO;
	}

	@Override
	public String getHurtSound() {
		return "lotr:troll.say";
	}

	public float getInterpolatedChargeLean(float f) {
		return (prevChargeLean + (chargeLean - prevChargeLean) * f) / 10.0f;
	}

	@Override
	public LOTRAchievement getKillAchievement() {
		return LOTRAchievement.killBalrog;
	}

	@Override
	public String getLivingSound() {
		return "lotr:troll.say";
	}

	@Override
	public float getSoundVolume() {
		return 1.5f;
	}

	public boolean isBalrogCharging() {
		return dataWatcher.getWatchableObjectByte(20) == 1;
	}

	public void setBalrogCharging(boolean flag) {
		dataWatcher.updateObject(20, (byte) (flag ? 1 : 0));
	}

	public boolean isWreathedInFlame() {
		return isEntityAlive() && !isWet();
	}

	@Override
	public void knockBack(Entity entity, float f, double d, double d1) {
		super.knockBack(entity, f, d, d1);
		motionX /= 4.0;
		motionY /= 4.0;
		motionZ /= 4.0;
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
	public void onDeath(DamageSource damagesource) {
		if (worldObj.isRemote) {
			for (int l = 0; l < 100; ++l) {
				double d = posX + width * MathHelper.randomFloatClamp(rand, -1.0f, 1.0f);
				double d1 = posY + height * MathHelper.randomFloatClamp(rand, 0.0f, 1.0f);
				double d2 = posZ + width * MathHelper.randomFloatClamp(rand, -1.0f, 1.0f);
				double d3 = rand.nextGaussian() * 0.03;
				double d4 = rand.nextGaussian() * 0.03;
				double d5 = rand.nextGaussian() * 0.03;
				if (rand.nextInt(3) == 0) {
					worldObj.spawnParticle("explode", d, d1, d2, d3, d4, d5);
					continue;
				}
				worldObj.spawnParticle("flame", d, d1, d2, d3, d4, d5);
			}
		} else {
			int exRange = 8;
			int i = MathHelper.floor_double(posX);
			int j = MathHelper.floor_double(posY);
			int k = MathHelper.floor_double(posZ);
			for (int i1 = i - exRange; i1 <= i + exRange; ++i1) {
				for (int j1 = j - exRange; j1 <= j + exRange; ++j1) {
					for (int k1 = k - exRange; k1 <= k + exRange; ++k1) {
						Block block = worldObj.getBlock(i1, j1, k1);
						if (block.getMaterial() != Material.fire) {
							continue;
						}
						worldObj.setBlockToAir(i1, j1, k1);
					}
				}
			}
		}
		super.onDeath(damagesource);
		playSound(getHurtSound(), getSoundVolume() * 2.0f, getSoundPitch() * 0.75f);
	}

	@Override
	public void onLivingUpdate() {
		block12:
		{
			int l;
			super.onLivingUpdate();
			if (getHealth() < getMaxHealth() && ticksExisted % 10 == 0) {
				heal(1.0f);
			}
			if (!worldObj.isRemote) {
				chargeFrustration = getAttackTarget() == null ? 0 : isBalrogCharging() ? 0 : ++chargeFrustration;
			}
			prevChargeLean = chargeLean;
			if (isBalrogCharging()) {
				if (chargeLean < 10) {
					++chargeLean;
				}
			} else if (chargeLean > 0) {
				--chargeLean;
			}
			if (!isWreathedInFlame()) {
				break block12;
			}
			if (!worldObj.isRemote && rand.nextInt(80) == 0) {
				boolean isUtumno = worldObj.provider instanceof LOTRWorldProviderUtumno;
				for (int l2 = 0; l2 < 24; ++l2) {
					int i = MathHelper.floor_double(posX);
					int j = MathHelper.floor_double(boundingBox.minY);
					int k = MathHelper.floor_double(posZ);
					Block block = worldObj.getBlock(i += MathHelper.getRandomIntegerInRange(rand, -8, 8), j += MathHelper.getRandomIntegerInRange(rand, -4, 8), k += MathHelper.getRandomIntegerInRange(rand, -8, 8));
					float maxResistance = Blocks.obsidian.getExplosionResistance(this);
					if (!block.isReplaceable(worldObj, i, j, k) && (!isUtumno || block.getExplosionResistance(this) > maxResistance) || !Blocks.fire.canPlaceBlockAt(worldObj, i, j, k)) {
						continue;
					}
					worldObj.setBlock(i, j, k, Blocks.fire, 0, 3);
				}
			}
			if (isBalrogCharging()) {
				for (l = 0; l < 10; ++l) {
					String s = rand.nextInt(3) == 0 ? "flame" : "largesmoke";
					double d0 = posX + (rand.nextDouble() - 0.5) * width * 1.5;
					double d1 = posY + height * MathHelper.getRandomDoubleInRange(rand, 0.5, 1.5);
					double d2 = posZ + (rand.nextDouble() - 0.5) * width * 1.5;
					double d3 = motionX * -2.0;
					double d4 = motionY * -0.5;
					double d5 = motionZ * -2.0;
					worldObj.spawnParticle(s, d0, d1, d2, d3, d4, d5);
				}
			} else {
				for (l = 0; l < 3; ++l) {
					String s = rand.nextInt(3) == 0 ? "flame" : "largesmoke";
					double d0 = posX + (rand.nextDouble() - 0.5) * width * 2.0;
					double d1 = posY + 0.5 + rand.nextDouble() * height * 1.5;
					double d2 = posZ + (rand.nextDouble() - 0.5) * width * 2.0;
					worldObj.spawnParticle(s, d0, d1, d2, 0.0, 0.0, 0.0);
				}
			}
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		if (rand.nextBoolean()) {
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.balrogWhip));
		} else {
			int i = rand.nextInt(3);
			switch (i) {
				case 0:
					npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordUtumno));
					break;
				case 1:
					npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeUtumno));
					break;
				case 2:
					npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerUtumno));
					break;
				default:
					break;
			}
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		return data;
	}
}
