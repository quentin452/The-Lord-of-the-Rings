package lotr.common.entity.npc;

import java.util.List;

import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.playerdetails.ExclusiveGroup;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.*;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class LOTREntityElf extends LOTREntityNPC {
	public EntityAIBase rangedAttackAI = createElfRangedAttackAI();
	public EntityAIBase meleeAttackAI = createElfMeleeAttackAI();
	public int soloTick;
	public float soloSpinSpeed;
	public float soloSpin;
	public float prevSoloSpin;
	public float bowAmount;
	public float prevBowAmount;

	protected LOTREntityElf(World world) {
		super(world);
		setSize(0.6f, 1.8f);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new LOTREntityAIHiredRemainStill(this));
		tasks.addTask(3, new LOTREntityAIFollowHiringPlayer(this));
		tasks.addTask(4, new EntityAIOpenDoor(this, true));
		tasks.addTask(5, new EntityAIWander(this, 1.0));
		tasks.addTask(6, new LOTREntityAIEat(this, LOTRFoods.ELF, 12000));
		tasks.addTask(6, new LOTREntityAIDrink(this, getElfDrinks(), 8000));
		tasks.addTask(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(7, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0f, 0.02f));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		tasks.addTask(9, new EntityAILookIdle(this));
		addTargetTasks(true);
	}

	@Override
	public void addPotionEffect(PotionEffect effect) {
		if (effect.getPotionID() == Potion.poison.id) {
			return;
		}
		super.addPotionEffect(effect);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
		getEntityAttribute(npcRangedAccuracy).setBaseValue(0.5);
	}

	public abstract boolean canElfSpawnHere();

	public EntityAIBase createElfMeleeAttackAI() {
		return new LOTREntityAIAttackOnCollide(this, 1.5, false);
	}

	public EntityAIBase createElfRangedAttackAI() {
		return new LOTREntityAIRangedAttack(this, 1.25, 40, 60, 16.0f);
	}

	public void dropElfItems(boolean flag, int i) {
		if (flag) {
			int dropChance = 40 - i * 8;
			if (rand.nextInt(Math.max(dropChance, 1)) == 0) {
				dropItem(LOTRMod.lembas, 1);
			}
		}
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int bones = rand.nextInt(2) + rand.nextInt(i + 1);
		for (int l = 0; l < bones; ++l) {
			dropItem(LOTRMod.elfBone, 1);
		}
		dropNPCArrows(i);
		dropElfItems(flag, i);
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(22, (byte) 0);
		dataWatcher.addObject(23, (short) 0);
	}

	@Override
	public String getAttackSound() {
		return familyInfo.isMale() ? "lotr:elf.male.attack" : super.getAttackSound();
	}

	public float getBowingAmount(float f) {
		return prevBowAmount + (bowAmount - prevBowAmount) * f;
	}

	public int getBowingTick() {
		return dataWatcher.getWatchableObjectShort(23);
	}

	@Override
	public boolean getCanSpawnHere() {
		if (super.getCanSpawnHere()) {
			return liftSpawnRestrictions || canElfSpawnHere();
		}
		return false;
	}

	public LOTRFoods getElfDrinks() {
		return LOTRFoods.ELF_DRINK;
	}

	@Override
	public String getEntityClassName() {
		if (isJazz()) {
			return "Jazz-elf";
		}
		return super.getEntityClassName();
	}

	@Override
	public ItemStack getHeldItem() {
		if (worldObj.isRemote && isJazz() && isSolo()) {
			return null;
		}
		return super.getHeldItem();
	}

	public boolean getJazzFlag(int i) {
		byte b = dataWatcher.getWatchableObjectByte(22);
		return (b & 1 << i) != 0;
	}

	@Override
	public String getLivingSound() {
		if (getAttackTarget() == null && rand.nextInt(10) == 0 && familyInfo.isMale()) {
			return "lotr:elf.male.say";
		}
		return super.getLivingSound();
	}

	@Override
	public String getNPCName() {
		return familyInfo.getName();
	}

	public float getSoloSpin(float f) {
		return prevSoloSpin + (soloSpin - prevSoloSpin) * f;
	}

	public boolean isJazz() {
		return getJazzFlag(0);
	}

	public boolean isSolo() {
		return getJazzFlag(1);
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

	@SuppressWarnings("Convert2Lambda")
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (isJazz()) {
			if (!worldObj.isRemote) {
				if (soloTick > 0) {
					--soloTick;
					rotationPitch = -10.0f + (MathHelper.sin(soloTick * 0.3f) + 1.0f) / 2.0f * -30.0f;
				} else if (rand.nextInt(200) == 0) {
					soloTick = 60 + rand.nextInt(300);
				}
				setSolo(soloTick > 0);
			} else if (isSolo()) {
				if (rand.nextInt(3) == 0) {
					double d = posX;
					double d1 = boundingBox.minY + getEyeHeight();
					double d2 = posZ;
					double d3 = MathHelper.getRandomDoubleInRange(rand, -0.1, 0.1);
					double d4 = MathHelper.getRandomDoubleInRange(rand, -0.1, 0.1);
					double d5 = MathHelper.getRandomDoubleInRange(rand, -0.1, 0.1);
					LOTRMod.proxy.spawnParticle("music", d, d1, d2, d3, d4, d5);
				}
				if (soloSpinSpeed == 0.0f || rand.nextInt(30) == 0) {
					soloSpinSpeed = MathHelper.randomFloatClamp(rand, -25.0f, 25.0f);
				}
				prevSoloSpin = soloSpin;
				soloSpin += soloSpinSpeed;
			} else {
				soloSpin = 0.0f;
				prevSoloSpin = 0.0f;
				soloSpinSpeed = 0.0f;
			}
		}
		if (worldObj.isRemote) {
			prevBowAmount = bowAmount;
			int tick = getBowingTick();
			if (tick <= 0 && bowAmount > 0.0f) {
				bowAmount -= 0.2f;
				bowAmount = Math.max(bowAmount, 0.0f);
			} else if (tick > 0 && bowAmount < 1.0f) {
				bowAmount += 0.2f;
				bowAmount = Math.min(bowAmount, 1.0f);
			}
		} else {
			double range = 8.0;
			double rangeSq = range * range;
			EntityPlayer bowingPlayer;
			List players = worldObj.selectEntitiesWithinAABB(EntityPlayer.class, boundingBox.expand(range, range, range), new IEntitySelector() {

				@Override
				public boolean isEntityApplicable(Entity entity) {
					EntityPlayer entityplayer = (EntityPlayer) entity;
					if (entityplayer.isEntityAlive() && isFriendly(entityplayer) && getDistanceSqToEntity(entityplayer) <= rangeSq) {
						return LOTRMod.playerDetailsCache.getPlayerDetails(entityplayer).hasExclusiveGroup(ExclusiveGroup.BOWING_ELVES);
					}
					return false;
				}
			});
			if (players.isEmpty() || getAttackTarget() != null) {
				setBowingTick(0);
			} else {
				int tick = getBowingTick();
				if (tick >= 0) {
					++tick;
				}
				if (tick > 40) {
					tick = -1;
				}
				setBowingTick(tick);
				if (tick >= 0) {
					getNavigator().clearPathEntity();
					bowingPlayer = (EntityPlayer) players.get(0);
					float bowLook = (float) Math.toDegrees(Math.atan2(bowingPlayer.posZ - posZ, bowingPlayer.posX - posX));
					rotationYaw = rotationYawHead = bowLook - 90.0f;
				}
			}
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setJazz(nbt.getBoolean("BoopBoopBaDoop"));
	}

	public void setBowingTick(int i) {
		dataWatcher.updateObject(23, (short) i);
	}

	public void setJazz(boolean flag) {
		setJazzFlag(0, flag);
	}

	public void setJazzFlag(int i, boolean flag) {
		byte b = dataWatcher.getWatchableObjectByte(22);
		int pow2 = 1 << i;
		b = flag ? (byte) (b | pow2) : (byte) (b & ~pow2);
		dataWatcher.updateObject(22, b);
	}

	public void setSolo(boolean flag) {
		setJazzFlag(1, flag);
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(rand.nextBoolean());
	}

	@Override
	public void setupNPCName() {
		familyInfo.setName(LOTRNames.getSindarinOrQuenyaName(rand, familyInfo.isMale()));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setBoolean("BoopBoopBaDoop", isJazz());
	}

}
