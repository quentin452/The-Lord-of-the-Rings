package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.LOTRMountFunctions;
import lotr.common.entity.ai.*;
import lotr.common.entity.animal.*;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.item.LOTRItemMountArmor;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.Locale;

public abstract class LOTREntityWarg extends LOTREntityNPCRideable implements IInvBasic {
	public int eatingTick;
	public AnimalChest wargInventory;

	protected LOTREntityWarg(World world) {
		super(world);
		setSize(1.5f, 1.7f);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new LOTREntityAIHiredRemainStill(this));
		tasks.addTask(2, getWargAttackAI());
		tasks.addTask(3, new LOTREntityAIUntamedPanic(this, 1.2));
		tasks.addTask(4, new LOTREntityAIFollowHiringPlayer(this));
		tasks.addTask(5, new EntityAIWander(this, 1.0));
		tasks.addTask(6, new EntityAIWatchClosest2(this, EntityPlayer.class, 12.0f, 0.02f));
		tasks.addTask(6, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 8.0f, 0.02f));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityLiving.class, 12.0f, 0.02f));
		tasks.addTask(8, new EntityAILookIdle(this));
		int target = addTargetTasks(true);
		if (!(this instanceof LOTREntityWargBombardier)) {
			targetTasks.addTask(target + 1, new LOTREntityAINearestAttackableTargetBasic(this, LOTREntityRabbit.class, 500, false));
			targetTasks.addTask(target + 1, new LOTREntityAINearestAttackableTargetBasic(this, LOTREntityDeer.class, 1000, false));
		}
		isImmuneToFrost = true;
		spawnsInDarkness = true;
		setupWargInventory();
	}

	@Override
	public boolean allowLeashing() {
		return isNPCTamed();
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(MathHelper.getRandomIntegerInRange(rand, 20, 32));
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
		getEntityAttribute(npcAttackDamage).setBaseValue(MathHelper.getRandomIntegerInRange(rand, 3, 5));
	}

	@Override
	public boolean canDropRares() {
		return false;
	}

	@Override
	public boolean canReEquipHired(int slot, ItemStack itemstack) {
		return false;
	}

	public boolean canWargBeRidden() {
		return true;
	}

	public void checkWargInventory() {
		if (!worldObj.isRemote) {
			setWargSaddled(wargInventory.getStackInSlot(0) != null);
			setWargArmorWatched(getWargArmor());
		}
	}

	public abstract LOTREntityNPC createWargRider();

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int furs = 1 + rand.nextInt(3) + rand.nextInt(i + 1);
		for (int l = 0; l < furs; ++l) {
			dropItem(LOTRMod.fur, 1);
		}
		int bones = 2 + rand.nextInt(2) + rand.nextInt(i + 1);
		for (int l = 0; l < bones; ++l) {
			dropItem(LOTRMod.wargBone, 1);
		}
		if (flag) {
			int rugChance = 50 - i * 8;
			if (rand.nextInt(Math.max(rugChance, 1)) == 0) {
				entityDropItem(new ItemStack(LOTRMod.wargskinRug, 1, getWargType().wargID), 0.0f);
			}
		}
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(18, (byte) 0);
		dataWatcher.addObject(19, (byte) 0);
		dataWatcher.addObject(20, 0);
		if (rand.nextInt(500) == 0) {
			setWargType(WargType.WHITE);
		} else if (rand.nextInt(20) == 0) {
			setWargType(WargType.BLACK);
		} else if (rand.nextInt(3) == 0) {
			setWargType(WargType.GREY);
		} else {
			setWargType(WargType.BROWN);
		}
	}

	@Override
	public String getAttackSound() {
		return "lotr:warg.attack";
	}

	@Override
	public boolean getBelongsToNPC() {
		return false;
	}

	@Override
	public String getDeathSound() {
		return "lotr:warg.death";
	}

	@Override
	public String getHurtSound() {
		return "lotr:warg.hurt";
	}

	@Override
	public String getLivingSound() {
		return "lotr:warg.say";
	}

	@Override
	public String getMountArmorTexture() {
		ItemStack armor = getWargArmorWatched();
		if (armor != null && armor.getItem() instanceof LOTRItemMountArmor) {
			return ((LOTRItemMountArmor) armor.getItem()).getArmorTexture();
		}
		return null;
	}

	@Override
	public IInventory getMountInventory() {
		return wargInventory;
	}

	public float getTailRotation() {
		float f = (getMaxHealth() - getHealth()) / getMaxHealth();
		return f * -1.2f;
	}

	@Override
	public int getTotalArmorValue() {
		ItemStack itemstack = getWargArmor();
		if (itemstack != null && itemstack.getItem() instanceof LOTRItemMountArmor) {
			LOTRItemMountArmor armor = (LOTRItemMountArmor) itemstack.getItem();
			return armor.getDamageReduceAmount();
		}
		return 0;
	}

	public ItemStack getWargArmor() {
		return wargInventory.getStackInSlot(1);
	}

	public ItemStack getWargArmorWatched() {
		int ID = dataWatcher.getWatchableObjectInt(20);
		return new ItemStack(Item.getItemById(ID));
	}

	public EntityAIBase getWargAttackAI() {
		return new LOTREntityAIAttackOnCollide(this, 1.6, false);
	}

	public WargType getWargType() {
		byte i = dataWatcher.getWatchableObjectByte(19);
		return WargType.forID(i);
	}

	@Override
	public IEntityLivingData initCreatureForHire(IEntityLivingData data) {
		return super.onSpawnWithEgg(data);
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		if (worldObj.isRemote || hiredNPCInfo.isActive) {
			return false;
		}
		if (LOTRMountFunctions.interact(this, entityplayer)) {
			return true;
		}
		if (getAttackTarget() != entityplayer) {
			boolean hasRequiredAlignment = LOTRLevelData.getData(entityplayer).getAlignment(getFaction()) >= 50.0f;
			boolean notifyNotEnoughAlignment = false;
			ItemStack itemstack = entityplayer.inventory.getCurrentItem();
			if (isNPCTamed() && entityplayer.isSneaking()) {
				if (hasRequiredAlignment) {
					openGUI(entityplayer);
					return true;
				}
				notifyNotEnoughAlignment = true;
			}
			if (!notifyNotEnoughAlignment && isNPCTamed() && itemstack != null && itemstack.getItem() instanceof ItemFood && ((ItemFood) itemstack.getItem()).isWolfsFavoriteMeat() && getHealth() < getMaxHealth()) {
				if (hasRequiredAlignment) {
					if (!entityplayer.capabilities.isCreativeMode) {
						--itemstack.stackSize;
						if (itemstack.stackSize == 0) {
							entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
						}
					}
					heal(((ItemFood) itemstack.getItem()).func_150905_g(itemstack));
					eatingTick = 20;
					return true;
				}
				notifyNotEnoughAlignment = true;
			}
			if (!notifyNotEnoughAlignment && isNPCTamed() && !isMountSaddled() && canWargBeRidden() && riddenByEntity == null && itemstack != null && itemstack.getItem() == Items.saddle) {
				if (hasRequiredAlignment) {
					openGUI(entityplayer);
					return true;
				}
				notifyNotEnoughAlignment = true;
			}
			if (!notifyNotEnoughAlignment && !isChild() && canWargBeRidden() && riddenByEntity == null) {
				if (itemstack != null && itemstack.interactWithEntity(entityplayer, this)) {
					return true;
				}
				if (hasRequiredAlignment) {
					entityplayer.mountEntity(this);
					setAttackTarget(null);
					getNavigator().clearPathEntity();
					return true;
				}
				notifyNotEnoughAlignment = true;
			}
			if (notifyNotEnoughAlignment) {
				LOTRAlignmentValues.notifyAlignmentNotHighEnough(entityplayer, 50.0f, getFaction());
				return true;
			}
		}
		return super.interact(entityplayer);
	}

	@Override
	public boolean isMountSaddled() {
		return dataWatcher.getWatchableObjectByte(18) == 1;
	}

	@Override
	public void onDeath(DamageSource damagesource) {
		super.onDeath(damagesource);
		if (!worldObj.isRemote) {
			if (getBelongsToNPC()) {
				wargInventory.setInventorySlotContents(0, null);
				wargInventory.setInventorySlotContents(1, null);
			}
			if (isNPCTamed()) {
				setWargSaddled(false);
				dropItem(Items.saddle, 1);
				ItemStack wargArmor = getWargArmor();
				if (wargArmor != null) {
					entityDropItem(wargArmor, 0.0f);
					setWargArmor(null);
				}
			}
		}
	}

	@Override
	public void onInventoryChanged(InventoryBasic inv) {
		boolean prevSaddled = isMountSaddled();
		ItemStack prevArmor = getWargArmorWatched();
		checkWargInventory();
		ItemStack wargArmor = getWargArmorWatched();
		if (ticksExisted > 20) {
			if (!prevSaddled && isMountSaddled()) {
				playSound("mob.horse.leather", 0.5f, 1.0f);
			}
			if (!ItemStack.areItemStacksEqual(prevArmor, wargArmor)) {
				playSound("mob.horse.armor", 0.5f, 1.0f);
			}
		}
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!worldObj.isRemote && riddenByEntity instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) riddenByEntity;
			if (LOTRLevelData.getData(entityplayer).getAlignment(getFaction()) < 50.0f) {
				entityplayer.mountEntity(null);
			} else if (isNPCTamed() && isMountSaddled()) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.rideWarg);
			}
		}
		if (eatingTick > 0) {
			if (eatingTick % 4 == 0) {
				worldObj.playSoundAtEntity(this, "random.eat", 0.5f + 0.5f * rand.nextInt(2), 0.4f + rand.nextFloat() * 0.2f);
			}
			--eatingTick;
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		if (!worldObj.isRemote && canWargBeRidden() && rand.nextInt(3) == 0) {
			LOTREntityNPC rider = createWargRider();
			rider.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0f);
			rider.onSpawnWithEgg(null);
			rider.isNPCPersistent = isNPCPersistent;
			worldObj.spawnEntityInWorld(rider);
			rider.mountEntity(this);
		}
		return data;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		ItemStack wargArmor;
		super.readEntityFromNBT(nbt);
		setWargType(WargType.forID(nbt.getByte("WargType")));
		if (nbt.hasKey("WargSaddleItem")) {
			ItemStack saddle = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("WargSaddleItem"));
			if (saddle != null && saddle.getItem() == Items.saddle) {
				wargInventory.setInventorySlotContents(0, saddle);
			}
		} else if (nbt.getBoolean("Saddled")) {
			wargInventory.setInventorySlotContents(0, new ItemStack(Items.saddle));
		}
		if (nbt.hasKey("WargArmorItem") && (wargArmor = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("WargArmorItem"))) != null && isMountArmorValid(wargArmor)) {
			wargInventory.setInventorySlotContents(1, wargArmor);
		}
		checkWargInventory();
	}

	@Override
	public void setBelongsToNPC(boolean flag) {
	}

	public void setupWargInventory() {
		AnimalChest prevInv = wargInventory;
		wargInventory = new AnimalChest("WargInv", 2);
		wargInventory.func_110133_a(getCommandSenderName());
		if (prevInv != null) {
			prevInv.func_110132_b(this);
			int invSize = Math.min(prevInv.getSizeInventory(), wargInventory.getSizeInventory());
			for (int slot = 0; slot < invSize; ++slot) {
				ItemStack itemstack = prevInv.getStackInSlot(slot);
				if (itemstack == null) {
					continue;
				}
				wargInventory.setInventorySlotContents(slot, itemstack.copy());
			}
		}
		wargInventory.func_110134_a(this);
		checkWargInventory();
	}

	public void setWargArmor(ItemStack itemstack) {
		wargInventory.setInventorySlotContents(1, itemstack);
		setupWargInventory();
		setWargArmorWatched(getWargArmor());
	}

	public void setWargArmorWatched(ItemStack itemstack) {
		if (itemstack == null) {
			dataWatcher.updateObject(20, 0);
		} else {
			dataWatcher.updateObject(20, Item.getIdFromItem(itemstack.getItem()));
		}
	}

	public void setWargSaddled(boolean flag) {
		dataWatcher.updateObject(18, flag ? (byte) 1 : 0);
	}

	public void setWargType(WargType w) {
		dataWatcher.updateObject(19, (byte) w.wargID);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setByte("WargType", (byte) getWargType().wargID);
		if (wargInventory.getStackInSlot(0) != null) {
			nbt.setTag("WargSaddleItem", wargInventory.getStackInSlot(0).writeToNBT(new NBTTagCompound()));
		}
		if (getWargArmor() != null) {
			nbt.setTag("WargArmorItem", getWargArmor().writeToNBT(new NBTTagCompound()));
		}
	}

	public enum WargType {
		BROWN(0), GREY(1), BLACK(2), WHITE(3), ICE(4), OBSIDIAN(5), FIRE(6);

		public int wargID;

		WargType(int i) {
			wargID = i;
		}

		public String textureName() {
			return name().toLowerCase(Locale.ROOT);
		}

		public static WargType forID(int ID) {
			for (WargType w : values()) {
				if (w.wargID != ID) {
					continue;
				}
				return w;
			}
			return BROWN;
		}

		public static String[] wargTypeNames() {
			String[] names = new String[values().length];
			for (int i = 0; i < names.length; ++i) {
				names[i] = values()[i].textureName();
			}
			return names;
		}
	}

}
