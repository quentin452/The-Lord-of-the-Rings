package lotr.common.entity.animal;

import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockBerryBush;
import lotr.common.entity.AnimalJarUpdater;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.LOTRScarecrows;
import lotr.common.inventory.LOTREntityInventory;
import lotr.common.item.LOTRValuableItems;
import lotr.common.world.biome.LOTRBiomeGenFarHarad;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LOTREntityBird extends EntityLiving implements LOTRAmbientCreature, LOTRRandomSkinEntity, AnimalJarUpdater {
	public ChunkCoordinates currentFlightTarget;
	public int flightTargetTime;
	public int flapTime;
	public LOTREntityInventory birdInv = new LOTREntityInventory("BirdItems", this, 9);
	public EntityItem stealTargetItem;
	public EntityPlayer stealTargetPlayer;
	public int stolenTime;
	public boolean stealingCrops;

	public LOTREntityBird(World world) {
		super(world);
		setSize(0.5f, 0.5f);
		tasks.addTask(0, new EntityAIWatchClosest(this, EntityPlayer.class, 12.0f, 0.05f));
		tasks.addTask(1, new EntityAIWatchClosest(this, EntityLiving.class, 12.0f, 0.1f));
		tasks.addTask(2, new EntityAILookIdle(this));
	}

	@Override
	public boolean allowLeashing() {
		return false;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(MathHelper.getRandomDoubleInRange(rand, 0.08, 0.13));
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		boolean flag = super.attackEntityFrom(damagesource, f);
		if (flag && !worldObj.isRemote && isBirdStill()) {
			setBirdStill(false);
		}
		return flag;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	public boolean canBirdSit() {
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(posY);
		int k = MathHelper.floor_double(posZ);
		Block block = worldObj.getBlock(i, j, k);
		Block below = worldObj.getBlock(i, j - 1, k);
		return block.getBlocksMovement(worldObj, i, j, k) && below.isSideSolid(worldObj, i, j - 1, k, ForgeDirection.UP);
	}

	public boolean canBirdSpawnHere() {
		return LOTRAmbientSpawnChecks.canSpawn(this, 8, 12, 40, 4, Material.leaves);
	}

	public void cancelFlight() {
		currentFlightTarget = null;
		flightTargetTime = 0;
		stealTargetItem = null;
		stealTargetPlayer = null;
		stealingCrops = false;
	}

	@Override
	public boolean canDespawn() {
		return super.canDespawn();
	}

	public boolean canStealCrops(int i, int j, int k) {
		Block block = worldObj.getBlock(i, j, k);
		if (block instanceof BlockCrops) {
			return true;
		}
		if (block instanceof LOTRBlockBerryBush) {
			int meta = worldObj.getBlockMetadata(i, j, k);
			return LOTRBlockBerryBush.hasBerries(meta);
		}
		return false;
	}

	public boolean canStealItem(EntityItem entity) {
		return entity.isEntityAlive() && isStealable(entity.getEntityItem());
	}

	public boolean canStealItems() {
		return getBirdType().canSteal;
	}

	public boolean canStealPlayer(EntityPlayer entityplayer) {
		if (entityplayer.capabilities.isCreativeMode || !entityplayer.isEntityAlive()) {
			return false;
		}
		List<Integer> slots = getStealablePlayerSlots(entityplayer);
		return !slots.isEmpty();
	}

	@Override
	public boolean canTriggerWalking() {
		return false;
	}

	@Override
	public void collideWithEntity(Entity entity) {
	}

	@Override
	public void collideWithNearbyEntities() {
	}

	@Override
	public boolean doesEntityNotTriggerPressurePlate() {
		return true;
	}

	@Override
	public void dropEquipment(boolean flag, int i) {
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		int feathers = rand.nextInt(3) + rand.nextInt(i + 1);
		for (int l = 0; l < feathers; ++l) {
			dropItem(Items.feather, 1);
		}
	}

	public void eatCropBlock(int i, int j, int k) {
		Block block = worldObj.getBlock(i, j, k);
		if (block instanceof LOTRBlockBerryBush) {
			int meta = worldObj.getBlockMetadata(i, j, k);
			meta = LOTRBlockBerryBush.setHasBerries(meta, false);
			worldObj.setBlockMetadataWithNotify(i, j, k, meta, 3);
		} else {
			worldObj.setBlockToAir(i, j, k);
		}
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, (byte) 0);
		dataWatcher.addObject(17, (byte) 1);
	}

	@Override
	public void fall(float f) {
	}

	public String getBirdTextureDir() {
		return getBirdType().textureDir;
	}

	public BirdType getBirdType() {
		byte i = dataWatcher.getWatchableObjectByte(16);
		if (i < 0 || i >= BirdType.values().length) {
			i = 0;
		}
		return BirdType.values()[i];
	}

	public void setBirdType(BirdType type) {
		setBirdType(type.ordinal());
	}

	public void setBirdType(int i) {
		dataWatcher.updateObject(16, (byte) i);
	}

	@Override
	public boolean getCanSpawnHere() {
		if (super.getCanSpawnHere()) {
			return canBirdSpawnHere();
		}
		return false;
	}

	@Override
	public String getDeathSound() {
		BirdType type = getBirdType();
		if (type == BirdType.CROW) {
			return "lotr:bird.crow.hurt";
		}
		return "lotr:bird.hurt";
	}

	public double getDistanceSqToFlightTarget() {
		double d = currentFlightTarget.posX + 0.5;
		double d1 = currentFlightTarget.posY + 0.5;
		double d2 = currentFlightTarget.posZ + 0.5;
		return getDistanceSq(d, d1, d2);
	}

	@Override
	public String getHurtSound() {
		BirdType type = getBirdType();
		if (type == BirdType.CROW) {
			return "lotr:bird.crow.hurt";
		}
		return "lotr:bird.hurt";
	}

	public ChunkCoordinates getItemFlightTarget(EntityItem entity) {
		int i = MathHelper.floor_double(entity.posX);
		int j = MathHelper.floor_double(entity.boundingBox.minY);
		int k = MathHelper.floor_double(entity.posZ);
		return new ChunkCoordinates(i, j, k);
	}

	@Override
	public String getLivingSound() {
		BirdType type = getBirdType();
		if (type == BirdType.CROW) {
			return "lotr:bird.crow.say";
		}
		return "lotr:bird.say";
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
	}

	public ChunkCoordinates getPlayerFlightTarget(EntityPlayer entityplayer) {
		int i = MathHelper.floor_double(entityplayer.posX);
		int j = MathHelper.floor_double(entityplayer.boundingBox.minY + 1.0);
		int k = MathHelper.floor_double(entityplayer.posZ);
		return new ChunkCoordinates(i, j, k);
	}

	@Override
	public float getSoundVolume() {
		return 1.0f;
	}

	public List<Integer> getStealablePlayerSlots(EntityPlayer entityplayer) {
		List<Integer> slots = new ArrayList<>();
		for (int i = 0; i <= 8; ++i) {
			ItemStack itemstack;
			if (i != entityplayer.inventory.currentItem || (itemstack = entityplayer.inventory.getStackInSlot(i)) == null || !isStealable(itemstack)) {
				continue;
			}
			slots.add(i);
		}
		return slots;
	}

	public ItemStack getStolenItem() {
		return getEquipmentInSlot(4);
	}

	public void setStolenItem(ItemStack itemstack) {
		setCurrentItemOrArmor(4, itemstack);
	}

	@Override
	public int getTalkInterval() {
		return 60;
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		return false;
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	public boolean isBirdStill() {
		return dataWatcher.getWatchableObjectByte(17) == 1;
	}

	public void setBirdStill(boolean flag) {
		dataWatcher.updateObject(17, flag ? (byte) 1 : 0);
	}

	public boolean isStealable(ItemStack itemstack) {
		BirdType type = getBirdType();
		Item item = itemstack.getItem();
		if (type == BirdType.COMMON) {
			return item instanceof IPlantable && ((IPlantable) item).getPlantType(worldObj, -1, -1, -1) == EnumPlantType.Crop;
		}
		if (type == BirdType.CROW) {
			return item instanceof ItemFood || LOTRMod.isOreNameEqual(itemstack, "bone");
		}
		if (type == BirdType.MAGPIE) {
			return LOTRValuableItems.canMagpieSteal(itemstack);
		}
		return false;
	}

	public boolean isValidFlightTarget(ChunkCoordinates coords) {
		int i = coords.posX;
		int j = coords.posY;
		int k = coords.posZ;
		if (j >= 1) {
			Block block = worldObj.getBlock(i, j, k);
			return block.getBlocksMovement(worldObj, i, j, k);
		}
		return false;
	}

	public void newFlight() {
		flightTargetTime = 0;
	}

	@Override
	public void onDeath(DamageSource damagesource) {
		super.onDeath(damagesource);
		if (!worldObj.isRemote) {
			setStolenItem(null);
			birdInv.dropAllItems();
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = MathHelper.floor_double(posX);
		int k = MathHelper.floor_double(posZ);
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
		if (biome instanceof LOTRBiomeGenFarHarad) {
			if (rand.nextInt(8) == 0) {
				setBirdType(BirdType.CROW);
			} else {
				setBirdType(BirdType.FAR_HARAD);
			}
		} else if (rand.nextInt(6) == 0) {
			setBirdType(BirdType.CROW);
		} else if (rand.nextInt(10) == 0) {
			setBirdType(BirdType.MAGPIE);
		} else {
			setBirdType(BirdType.COMMON);
		}
		return data;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (isBirdStill()) {
			motionZ = 0.0;
			motionY = 0.0;
			motionX = 0.0;
			posY = MathHelper.floor_double(posY);
			if (worldObj.isRemote) {
				if (rand.nextInt(200) == 0) {
					flapTime = 40;
				}
				if (flapTime > 0) {
					--flapTime;
				}
			}
		} else {
			motionY *= 0.6;
			if (worldObj.isRemote) {
				flapTime = 0;
			}
		}
	}

	@Override
	public void playLivingSound() {
		boolean sound = true;
		if (!worldObj.isDaytime()) {
			sound = rand.nextInt(20) == 0;
		}
		if (sound) {
			super.playLivingSound();
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setBirdType(nbt.getInteger("BirdType"));
		setBirdStill(nbt.getBoolean("BirdStill"));
		birdInv.writeToNBT(nbt);
		nbt.setShort("StealTime", (short) stolenTime);
	}

	@Override
	public void setUniqueID(UUID uuid) {
		entityUniqueID = uuid;
	}

	@SuppressWarnings("Convert2Lambda")
	@Override
	public void updateAITasks() {
		super.updateAITasks();
		if (getStolenItem() != null) {
			++stolenTime;
			if (stolenTime >= 200) {
				setStolenItem(null);
				stolenTime = 0;
			}
		}
		if (isBirdStill()) {
			if (!canBirdSit() || rand.nextInt(400) == 0 || worldObj.getClosestPlayerToEntity(this, 6.0) != null) {
				setBirdStill(false);
			}
		} else {
			if (canStealItems() && !stealingCrops && stealTargetItem == null && stealTargetPlayer == null && !birdInv.isFull() && rand.nextInt(100) == 0) {
				double range = 16.0;
				List players = worldObj.selectEntitiesWithinAABB(EntityPlayer.class, boundingBox.expand(range, range, range), new IEntitySelector() {

					@Override
					public boolean isEntityApplicable(Entity e) {
						EntityPlayer entityplayer;
						if (e instanceof EntityPlayer && canStealPlayer(entityplayer = (EntityPlayer) e)) {
							ChunkCoordinates coords = getPlayerFlightTarget(entityplayer);
							return isValidFlightTarget(coords);
						}
						return false;
					}
				});
				if (players.isEmpty()) {
					List entityItems = worldObj.selectEntitiesWithinAABB(EntityItem.class, boundingBox.expand(range, range, range), new IEntitySelector() {

						@Override
						public boolean isEntityApplicable(Entity e) {
							EntityItem eItem;
							if (e instanceof EntityItem && canStealItem(eItem = (EntityItem) e)) {
								ChunkCoordinates coords = getItemFlightTarget(eItem);
								return isValidFlightTarget(coords);
							}
							return false;
						}
					});
					if (!entityItems.isEmpty()) {
						stealTargetItem = (EntityItem) entityItems.get(rand.nextInt(entityItems.size()));
						currentFlightTarget = getItemFlightTarget(stealTargetItem);
						newFlight();
					}
				} else {
					stealTargetPlayer = (EntityPlayer) players.get(rand.nextInt(players.size()));
					currentFlightTarget = getPlayerFlightTarget(stealTargetPlayer);
					newFlight();
				}
			}
			if (stealTargetItem != null || stealTargetPlayer != null) {
				if (birdInv.isFull() || currentFlightTarget == null || !isValidFlightTarget(currentFlightTarget)) {
					cancelFlight();
				} else if (stealTargetItem != null && !canStealItem(stealTargetItem) || stealTargetPlayer != null && !canStealPlayer(stealTargetPlayer)) {
					cancelFlight();
				} else {
					if (stealTargetItem != null) {
						currentFlightTarget = getItemFlightTarget(stealTargetItem);
					} else if (stealTargetPlayer != null) {
						currentFlightTarget = getPlayerFlightTarget(stealTargetPlayer);
					}
					if (getDistanceSqToFlightTarget() < 1.0) {
						ItemStack stolenItem = null;
						if (stealTargetItem != null) {
							ItemStack itemstack = stealTargetItem.getEntityItem();
							ItemStack stealCopy = itemstack.copy();
							stealCopy.stackSize = MathHelper.getRandomIntegerInRange(rand, 1, Math.min(stealCopy.stackSize, 4));
							ItemStack safeCopy = stealCopy.copy();
							if (birdInv.addItemToInventory(stealCopy)) {
								itemstack.stackSize -= safeCopy.stackSize - stealCopy.stackSize;
								if (itemstack.stackSize <= 0) {
									stealTargetItem.setDead();
								}
								stolenItem = safeCopy;
							}
						} else if (stealTargetPlayer != null) {
							List<Integer> slots = getStealablePlayerSlots(stealTargetPlayer);
							int randSlot = slots.get(rand.nextInt(slots.size()));
							ItemStack itemstack = stealTargetPlayer.inventory.getStackInSlot(randSlot);
							ItemStack stealCopy = itemstack.copy();
							stealCopy.stackSize = MathHelper.getRandomIntegerInRange(rand, 1, Math.min(stealCopy.stackSize, 4));
							ItemStack safeCopy = stealCopy.copy();
							if (birdInv.addItemToInventory(stealCopy)) {
								itemstack.stackSize -= safeCopy.stackSize - stealCopy.stackSize;
								if (itemstack.stackSize <= 0) {
									itemstack = null;
								}
								stealTargetPlayer.inventory.setInventorySlotContents(randSlot, itemstack);
								stolenItem = safeCopy;
							}
						}
						if (stolenItem != null) {
							stolenTime = 0;
							setStolenItem(stolenItem);
							playSound("random.pop", 0.5f, ((rand.nextFloat() - rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
						}
						cancelFlight();
					}
				}
			} else if (stealingCrops) {
				if (!LOTRMod.canGrief(worldObj)) {
					stealingCrops = false;
				} else if (currentFlightTarget == null || !isValidFlightTarget(currentFlightTarget)) {
					cancelFlight();
				} else {
					int i = currentFlightTarget.posX;
					int j = currentFlightTarget.posY;
					int k = currentFlightTarget.posZ;
					if (getDistanceSqToFlightTarget() < 1.0) {
						if (canStealCrops(i, j, k)) {
							eatCropBlock(i, j, k);
							playSound("random.eat", 1.0f, (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2f + 1.0f);
						}
						cancelFlight();
					} else if (!canStealCrops(i, j, k) || flightTargetTime % 100 == 0 && LOTRScarecrows.anyScarecrowsNearby(worldObj, i, j, k)) {
						cancelFlight();
					}
				}
			} else {
				int j;
				if (LOTRMod.canGrief(worldObj) && !stealingCrops && rand.nextInt(100) == 0) {
					int i = MathHelper.floor_double(posX);
					j = MathHelper.floor_double(posY);
					int k = MathHelper.floor_double(posZ);
					int range = 16;
					int yRange = 8;
					int attempts = 32;
					for (int l = 0; l < attempts; ++l) {
						int k1;
						int j1;
						int i1 = i + MathHelper.getRandomIntegerInRange(rand, -range, range);
						if (!canStealCrops(i1, j1 = j + MathHelper.getRandomIntegerInRange(rand, -yRange, yRange), k1 = k + MathHelper.getRandomIntegerInRange(rand, -range, range)) || LOTRScarecrows.anyScarecrowsNearby(worldObj, i1, j1, k1)) {
							continue;
						}
						stealingCrops = true;
						currentFlightTarget = new ChunkCoordinates(i1, j1, k1);
						newFlight();
						break;
					}
				}
				if (!stealingCrops) {
					if (currentFlightTarget != null && !isValidFlightTarget(currentFlightTarget)) {
						cancelFlight();
					}
					if (currentFlightTarget == null || rand.nextInt(50) == 0 || getDistanceSqToFlightTarget() < 4.0) {
						int i = MathHelper.floor_double(posX);
						j = MathHelper.floor_double(posY);
						int k = MathHelper.floor_double(posZ);
						currentFlightTarget = new ChunkCoordinates(i + (rand.nextInt(16) - rand.nextInt(16)), j + MathHelper.getRandomIntegerInRange(rand, -2, 3), k + (rand.nextInt(16) - rand.nextInt(16)));
						newFlight();
					}
				}
			}
			if (currentFlightTarget != null) {
				double speed = getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
				double d0 = currentFlightTarget.posX + 0.5 - posX;
				double d1 = currentFlightTarget.posY + 0.5 - posY;
				double d2 = currentFlightTarget.posZ + 0.5 - posZ;
				motionX += (Math.signum(d0) * 0.5 - motionX) * speed;
				motionY += (Math.signum(d1) * 0.8 - motionY) * speed;
				motionZ += (Math.signum(d2) * 0.5 - motionZ) * speed;
				float f = (float) (Math.atan2(motionZ, motionX) * 180.0 / 3.141592653589793) - 90.0f;
				float f1 = MathHelper.wrapAngleTo180_float(f - rotationYaw);
				moveForward = 0.5f;
				rotationYaw += f1;
				++flightTargetTime;
				if (flightTargetTime >= 400) {
					cancelFlight();
				}
			}
			if (rand.nextInt(200) == 0 && canBirdSit()) {
				setBirdStill(true);
				cancelFlight();
			}
		}
	}

	@Override
	public void updateFallState(double d, boolean flag) {
	}

	@Override
	public void updateInAnimalJar() {
		setBirdStill(false);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("BirdType", getBirdType().ordinal());
		nbt.setBoolean("BirdStill", isBirdStill());
		birdInv.readFromNBT(nbt);
		stolenTime = nbt.getShort("StealTime");
	}

	public enum BirdType {
		COMMON("common", true), CROW("crow", true), MAGPIE("magpie", true), FAR_HARAD("farHarad", true);

		public String textureDir;
		public boolean canSteal;

		BirdType(String s, boolean flag) {
			textureDir = s;
			canSteal = flag;
		}
	}

}
