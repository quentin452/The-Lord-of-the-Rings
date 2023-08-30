package lotr.common.entity.npc;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.inventory.LOTRInventoryNPC;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityGollum extends LOTREntityNPC implements LOTRCharacter {
	public static int INV_ROWS = 3;
	public int eatingTick;
	public int prevFishTime = 400;
	public boolean isFishing;
	public LOTRInventoryNPC inventory = new LOTRInventoryNPC("gollum", this, INV_ROWS * 9);
	public int prevFishRequired;
	public int fishRequired = prevFishRequired = 20;

	public LOTREntityGollum(World world) {
		super(world);
		setSize(0.6f, 1.2f);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new LOTREntityAIGollumRemainStill(this));
		tasks.addTask(2, new LOTREntityAIGollumPanic(this, 1.4));
		tasks.addTask(3, new LOTREntityAIGollumAvoidEntity(this, LOTREntityOrc.class, 8.0f, 1.2, 1.4));
		tasks.addTask(3, new LOTREntityAIGollumAvoidEntity(this, LOTREntityElf.class, 8.0f, 1.2, 1.4));
		tasks.addTask(4, new LOTREntityAIGollumFishing(this, 1.5));
		tasks.addTask(5, new LOTREntityAIGollumFollowOwner(this, 1.2, 6.0f, 4.0f));
		tasks.addTask(6, new EntityAIWander(this, 1.0));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f, 0.1f));
		tasks.addTask(8, new EntityAILookIdle(this));
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		EntityPlayer owner = getGollumOwner();
		if (owner != null && damagesource.getEntity() == owner) {
			f = 0.0f;
			if (!worldObj.isRemote) {
				LOTRSpeech.sendSpeech(owner, this, LOTRSpeech.getRandomSpeechForPlayer(this, "char/gollum/hurt", owner));
			}
		}
		if (super.attackEntityFrom(damagesource, f)) {
			setGollumSitting(false);
			return true;
		}
		return false;
	}

	@Override
	public boolean canDropRares() {
		return false;
	}

	public boolean canGollumEat(ItemStack itemstack) {
		if (itemstack.getItem() == Items.fish || itemstack.getItem() == Items.cooked_fished) {
			return true;
		}
		ItemFood food = (ItemFood) itemstack.getItem();
		return food.isWolfsFavoriteMeat();
	}

	@Override
	public boolean canReEquipHired(int slot, ItemStack itemstack) {
		return false;
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(17, "");
		dataWatcher.addObject(18, (byte) 0);
		dataWatcher.addObject(19, (byte) 0);
	}

	@Override
	public String getDeathSound() {
		return "lotr:gollum.death";
	}

	public EntityPlayer getGollumOwner() {
		try {
			UUID uuid = UUID.fromString(getGollumOwnerUUID());
			return worldObj.func_152378_a(uuid);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	public String getGollumOwnerUUID() {
		return dataWatcher.getWatchableObjectString(17);
	}

	@Override
	public String getHurtSound() {
		return "lotr:gollum.hurt";
	}

	@Override
	public String getLivingSound() {
		return "lotr:gollum.say";
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (!isGollumFleeing()) {
			return "char/gollum/say";
		}
		return super.getSpeechBank(entityplayer);
	}

	@Override
	public String getSplashSound() {
		return super.getSplashSound();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleHealthUpdate(byte b) {
		if (b == 15) {
			for (int i = 0; i < 4; ++i) {
				double d = rand.nextGaussian() * 0.02;
				double d1 = rand.nextGaussian() * 0.02;
				double d2 = rand.nextGaussian() * 0.02;
				worldObj.spawnParticle(rand.nextBoolean() ? "bubble" : "splash", posX + rand.nextFloat() * width * 2.0f - width, posY + 0.5 + rand.nextFloat() * height, posZ + rand.nextFloat() * width * 2.0f - width, d, d1, d2);
			}
		} else {
			super.handleHealthUpdate(b);
		}
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		if (!worldObj.isRemote) {
			if (getGollumOwner() != null && entityplayer == getGollumOwner()) {
				ItemStack itemstack = entityplayer.inventory.getCurrentItem();
				if (itemstack != null && itemstack.getItem() instanceof ItemFood && canGollumEat(itemstack) && getHealth() < getMaxHealth()) {
					if (!entityplayer.capabilities.isCreativeMode) {
						--itemstack.stackSize;
						if (itemstack.stackSize <= 0) {
							entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
						}
					}
					heal(((ItemFood) itemstack.getItem()).func_150905_g(itemstack));
					eatingTick = 20;
					return true;
				}
				if (entityplayer.isSneaking()) {
					entityplayer.openGui(LOTRMod.instance, 10, worldObj, getEntityId(), 0, 0);
					return true;
				}
				setGollumSitting(!isGollumSitting());
				if (isGollumSitting()) {
					LOTRSpeech.sendSpeech(getGollumOwner(), this, LOTRSpeech.getRandomSpeechForPlayer(this, "char/gollum/stay", getGollumOwner()));
				} else {
					LOTRSpeech.sendSpeech(getGollumOwner(), this, LOTRSpeech.getRandomSpeechForPlayer(this, "char/gollum/follow", getGollumOwner()));
				}
				return true;
			}
			ItemStack itemstack = entityplayer.inventory.getCurrentItem();
			if (itemstack != null && itemstack.getItem() == Items.fish) {
				if (itemstack.stackSize >= fishRequired) {
					if (!entityplayer.capabilities.isCreativeMode) {
						itemstack.stackSize -= fishRequired;
						if (itemstack.stackSize <= 0) {
							entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
						}
					}
					fishRequired = 0;
				} else {
					fishRequired -= itemstack.stackSize;
					if (!entityplayer.capabilities.isCreativeMode) {
						entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
					}
				}
				eatingTick = 20;
				if (fishRequired <= 0) {
					setGollumOwnerUUID(entityplayer.getUniqueID().toString());
					LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tameGollum);
					LOTRSpeech.sendSpeech(entityplayer, this, LOTRSpeech.getRandomSpeechForPlayer(this, "char/gollum/tame", entityplayer));
					LOTRSpeech.messageAllPlayers(new ChatComponentTranslation("chat.lotr.tameGollum", entityplayer.getCommandSenderName(), getCommandSenderName()));
					spawnHearts();
					prevFishRequired = fishRequired = Math.round(prevFishRequired * (1.5f + rand.nextFloat() * 0.25f));
				} else {
					LOTRSpeech.sendSpeech(entityplayer, this, LOTRSpeech.getRandomSpeechForPlayer(this, "char/gollum/tameProgress", entityplayer));
				}
				return true;
			}
		}
		return super.interact(entityplayer);
	}

	public boolean isGollumFleeing() {
		return dataWatcher.getWatchableObjectByte(18) == 1;
	}

	public boolean isGollumSitting() {
		return dataWatcher.getWatchableObjectByte(19) == 1;
	}

	@Override
	public void onDeath(DamageSource damagesource) {
		if (!worldObj.isRemote && !StringUtils.isNullOrEmpty(getGollumOwnerUUID())) {
			LOTRSpeech.messageAllPlayers(func_110142_aN().func_151521_b());
		}
		super.onDeath(damagesource);
		if (!worldObj.isRemote) {
			inventory.dropAllItems();
			LOTRLevelData.setGollumSpawned(false);
		}
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!worldObj.isRemote && rand.nextInt(500) == 0) {
			heal(1.0f);
		}
		if (eatingTick > 0) {
			if (eatingTick % 4 == 0) {
				worldObj.playSoundAtEntity(this, "random.eat", 0.5f + 0.5f * rand.nextInt(2), (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f);
			}
			--eatingTick;
		}
		if (prevFishTime > 0) {
			--prevFishTime;
		}
		if (isGollumSitting() && !worldObj.isRemote && onGround) {
			getJumpHelper().setJumping();
		}
		if (!worldObj.isRemote && getEquipmentInSlot(0) != null && getGollumOwner() != null && getDistanceSqToEntity(getGollumOwner()) < 4.0) {
			getLookHelper().setLookPositionWithEntity(getGollumOwner(), 100.0f, 100.0f);
			getLookHelper().onUpdateLook();
			EntityItem entityitem = new EntityItem(worldObj, posX, posY + getEyeHeight(), posZ, getEquipmentInSlot(0));
			entityitem.delayBeforeCanPickup = 40;
			float f = 0.3f;
			entityitem.motionX = -MathHelper.sin(rotationYawHead / 180.0f * 3.1415927f) * MathHelper.cos(rotationPitch / 180.0f * 3.1415927f) * f;
			entityitem.motionZ = MathHelper.cos(rotationYawHead / 180.0f * 3.1415927f) * MathHelper.cos(rotationPitch / 180.0f * 3.1415927f) * f;
			entityitem.motionY = -MathHelper.sin(rotationPitch / 180.0f * 3.1415927f) * f + 0.1f;
			f = 0.02f;
			float f1 = rand.nextFloat() * 3.1415927f * 2.0f;
			entityitem.motionX += Math.cos(f1) * (f *= rand.nextFloat());
			entityitem.motionY += (rand.nextFloat() - rand.nextFloat()) * 0.1f;
			entityitem.motionZ += Math.sin(f1) * f;
			worldObj.spawnEntityInWorld(entityitem);
			setCurrentItemOrArmor(0, null);
		}
		if (!worldObj.isRemote && StringUtils.isNullOrEmpty(getGollumOwnerUUID()) && rand.nextInt(40) == 0) {
			List<EntityPlayer> nearbyPlayers = worldObj.getEntitiesWithinAABB(EntityPlayer.class, boundingBox.expand(80.0, 80.0, 80.0));
			for (EntityPlayer entityplayer : nearbyPlayers) {
				double d2 = getDistanceToEntity(entityplayer);
				int chance = (int) (d2 / 8.0);
				if (rand.nextInt(Math.max(2, chance)) != 0) {
					continue;
				}
				worldObj.playSoundAtEntity(entityplayer, getLivingSound(), getSoundVolume(), getSoundPitch());
			}
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		inventory.readFromNBT(nbt);
		if (nbt.hasKey("GollumOwnerUUID")) {
			setGollumOwnerUUID(nbt.getString("GollumOwnerUUID"));
		}
		setGollumSitting(nbt.getBoolean("GollumSitting"));
		prevFishTime = nbt.getInteger("GollumFishTime");
		if (nbt.hasKey("FishReq")) {
			fishRequired = nbt.getInteger("FishReq");
			prevFishRequired = nbt.getInteger("FishReqPrev");
		}
	}

	public void setGollumFleeing(boolean flag) {
		dataWatcher.updateObject(18, flag ? (byte) 1 : 0);
	}

	public void setGollumOwnerUUID(String s) {
		dataWatcher.updateObject(17, s);
	}

	public void setGollumSitting(boolean flag) {
		dataWatcher.updateObject(19, flag ? (byte) 1 : 0);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		inventory.writeToNBT(nbt);
		nbt.setString("GollumOwnerUUID", getGollumOwnerUUID());
		nbt.setBoolean("GollumSitting", isGollumSitting());
		nbt.setInteger("GollumFishTime", prevFishTime);
		nbt.setInteger("FishReq", fishRequired);
		nbt.setInteger("FishReqPrev", prevFishRequired);
	}
}
