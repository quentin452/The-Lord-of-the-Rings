package lotr.common.entity.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRTradeable;
import lotr.common.entity.npc.LOTRTraderNPCInfo;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTREntityTraderRespawn extends Entity {
	public static int MAX_SCALE = 40;
	public int timeUntilSpawn;
	public int prevBobbingTime;
	public int bobbingTime;
	public String traderClassID;
	public boolean traderHasHome;
	public int traderHomeX;
	public int traderHomeY;
	public int traderHomeZ;
	public float traderHomeRadius;
	public String traderLocationName;
	public boolean shouldTraderRespawn;
	public NBTTagCompound traderData;
	public float spawnerSpin;
	public float prevSpawnerSpin;

	public LOTREntityTraderRespawn(World world) {
		super(world);
		setSize(0.75f, 0.75f);
		spawnerSpin = rand.nextFloat() * 360.0f;
	}

	@Override
	public void applyEntityCollision(Entity entity) {
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		Entity entity = damagesource.getEntity();
		if (entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode) {
			if (!worldObj.isRemote) {
				Block.SoundType sound = Blocks.glass.stepSound;
				worldObj.playSoundAtEntity(this, sound.getBreakSound(), (sound.getVolume() + 1.0f) / 2.0f, sound.getPitch() * 0.8f);
				worldObj.setEntityState(this, (byte) 16);
				setDead();
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	public void copyTraderDataFrom(LOTREntityNPC entity) {
		traderClassID = LOTREntities.getStringFromClass(entity.getClass());
		traderHasHome = entity.hasHome();
		if (traderHasHome) {
			ChunkCoordinates home = entity.getHomePosition();
			traderHomeX = home.posX;
			traderHomeY = home.posY;
			traderHomeZ = home.posZ;
			traderHomeRadius = entity.func_110174_bM();
		}
		shouldTraderRespawn = entity.shouldTraderRespawn();
		if (entity.getHasSpecificLocationName()) {
			traderLocationName = entity.npcLocationName;
		}
		if (entity instanceof LOTRTradeable) {
			LOTRTraderNPCInfo traderInfo = entity.traderNPCInfo;
			traderData = new NBTTagCompound();
			traderInfo.writeToNBT(traderData);
		}
	}

	@Override
	public void entityInit() {
		dataWatcher.addObject(16, 0);
		dataWatcher.addObject(17, (byte) 0);
		dataWatcher.addObject(18, "");
	}

	public float getBobbingOffset(float tick) {
		float f = bobbingTime - prevBobbingTime;
		return MathHelper.sin((prevBobbingTime + f * tick) / 5.0f) * 0.25f;
	}

	public String getClientTraderString() {
		return dataWatcher.getWatchableObjectString(18);
	}

	public void setClientTraderString(String s) {
		dataWatcher.updateObject(18, s);
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		int entityID = LOTREntities.getIDFromString(getClientTraderString());
		if (entityID > 0) {
			return new ItemStack(LOTRMod.spawnEgg, 1, entityID);
		}
		return null;
	}

	public int getScale() {
		return dataWatcher.getWatchableObjectInt(16);
	}

	public void setScale(int i) {
		dataWatcher.updateObject(16, i);
	}

	public float getScaleFloat(float tick) {
		float scale = getScale();
		if (scale < MAX_SCALE) {
			scale += tick;
		}
		return scale / MAX_SCALE;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleHealthUpdate(byte b) {
		if (b == 16) {
			for (int l = 0; l < 16; ++l) {
				worldObj.spawnParticle("iconcrack_" + Item.getIdFromItem(LOTRMod.silverCoin), posX + (rand.nextDouble() - 0.5) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5) * width, 0.0, 0.0, 0.0);
			}
		} else {
			super.handleHealthUpdate(b);
		}
	}

	@Override
	public boolean hitByEntity(Entity entity) {
		if (entity instanceof EntityPlayer) {
			return attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) entity), 0.0f);
		}
		return false;
	}

	public boolean isSpawnImminent() {
		return dataWatcher.getWatchableObjectByte(17) == 1;
	}

	public void onSpawn() {
		motionY = 0.25;
		timeUntilSpawn = MathHelper.getRandomIntegerInRange(rand, 10, 30) * 1200;
	}

	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		prevSpawnerSpin = spawnerSpin;
		spawnerSpin = isSpawnImminent() ? spawnerSpin + 24.0f : spawnerSpin + 6.0f;
		prevSpawnerSpin = MathHelper.wrapAngleTo180_float(prevSpawnerSpin);
		spawnerSpin = MathHelper.wrapAngleTo180_float(spawnerSpin);
		if (getScale() < MAX_SCALE) {
			if (!worldObj.isRemote) {
				setScale(getScale() + 1);
			}
			motionX = 0.0;
			motionY *= 0.9;
		} else {
			motionX = 0.0;
			motionY = 0.0;
		}
		motionZ = 0.0;
		moveEntity(motionX, motionY, motionZ);
		if (!worldObj.isRemote) {
			setClientTraderString(traderClassID);
			if (!isSpawnImminent() && timeUntilSpawn <= 1200) {
				setSpawnImminent();
			}
			if (timeUntilSpawn > 0) {
				--timeUntilSpawn;
			} else {
				boolean flag = false;
				Entity entity = EntityList.createEntityByName(traderClassID, worldObj);
				if (entity instanceof LOTREntityNPC) {
					LOTREntityNPC trader = (LOTREntityNPC) entity;
					trader.setLocationAndAngles(posX, posY, posZ, rand.nextFloat() * 360.0f, 0.0f);
					trader.spawnRidingHorse = false;
					trader.liftSpawnRestrictions = true;
					boundingBox.offset(0.0, 100.0, 0.0);
					if (trader.getCanSpawnHere()) {
						trader.liftSpawnRestrictions = false;
						trader.onSpawnWithEgg(null);
						if (traderHasHome) {
							trader.setHomeArea(traderHomeX, traderHomeY, traderHomeZ, Math.round(traderHomeRadius));
						}
						if (traderLocationName != null) {
							trader.setSpecificLocationName(traderLocationName);
						}
						trader.setShouldTraderRespawn(shouldTraderRespawn);
						flag = worldObj.spawnEntityInWorld(trader);
						if (trader instanceof LOTRTradeable && traderData != null) {
							trader.traderNPCInfo.readFromNBT(traderData);
						}
					}
					boundingBox.offset(0.0, -100.0, 0.0);
				}
				if (flag) {
					playSound("random.pop", 1.0f, 0.5f + rand.nextFloat() * 0.5f);
					setDead();
				} else {
					timeUntilSpawn = 60;
					setLocationAndAngles(posX, posY + 1.0, posZ, rotationYaw, rotationPitch);
				}
			}
		} else if (isSpawnImminent()) {
			prevBobbingTime = bobbingTime++;
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		setScale(nbt.getInteger("Scale"));
		timeUntilSpawn = nbt.getInteger("TimeUntilSpawn");
		if (timeUntilSpawn <= 1200) {
			setSpawnImminent();
		}
		traderClassID = nbt.getString("TraderClassID");
		traderHasHome = nbt.getBoolean("TraderHasHome");
		traderHomeX = nbt.getInteger("TraderHomeX");
		traderHomeY = nbt.getInteger("TraderHomeY");
		traderHomeZ = nbt.getInteger("TraderHomeZ");
		traderHomeRadius = nbt.getFloat("TraderHomeRadius");
		shouldTraderRespawn = !nbt.hasKey("TraderShouldRespawn") || nbt.getBoolean("TraderShouldRespawn");
		if (nbt.hasKey("TraderLocationName")) {
			traderLocationName = nbt.getString("TraderLocationName");
		}
		if (nbt.hasKey("TraderData")) {
			traderData = nbt.getCompoundTag("TraderData");
		}
	}

	public void setSpawnImminent() {
		dataWatcher.updateObject(17, (byte) 1);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("Scale", getScale());
		nbt.setInteger("TimeUntilSpawn", timeUntilSpawn);
		nbt.setString("TraderClassID", traderClassID);
		nbt.setBoolean("TraderHasHome", traderHasHome);
		nbt.setInteger("TraderHomeX", traderHomeX);
		nbt.setInteger("TraderHomeY", traderHomeY);
		nbt.setInteger("TraderHomeZ", traderHomeZ);
		nbt.setFloat("TraderHomeRadius", traderHomeRadius);
		nbt.setBoolean("TraderShouldRespawn", shouldTraderRespawn);
		if (traderLocationName != null) {
			nbt.setString("TraderLocationName", traderLocationName);
		}
		if (traderData != null) {
			nbt.setTag("TraderData", traderData);
		}
	}
}
