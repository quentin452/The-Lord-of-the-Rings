package lotr.common.entity.animal;

import java.util.*;

import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockTorch;
import lotr.common.entity.*;
import lotr.common.world.biome.*;
import lotr.common.world.structure2.LOTRWorldGenElfHouse;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTREntityButterfly extends EntityLiving implements LOTRAmbientCreature, LOTRRandomSkinEntity {
	public LOTRBlockTorch elfTorchBlock;
	public ChunkCoordinates currentFlightTarget;
	public int flapTime;

	public LOTREntityButterfly(World world) {
		super(world);
		setSize(0.5f, 0.5f);
	}

	@Override
	public boolean allowLeashing() {
		return false;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(2.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(MathHelper.getRandomDoubleInRange(rand, 0.08, 0.12));
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		boolean flag = super.attackEntityFrom(damagesource, f);
		if (flag && !worldObj.isRemote && isButterflyStill()) {
			setButterflyStill(false);
		}
		return flag;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	public boolean canDespawn() {
		return true;
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
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, (byte) 0);
		dataWatcher.addObject(17, (byte) 0);
	}

	@Override
	public void fall(float f) {
	}

	public ButterflyType getButterflyType() {
		byte i = dataWatcher.getWatchableObjectByte(16);
		if (i < 0 || i >= ButterflyType.values().length) {
			i = 0;
		}
		return ButterflyType.values()[i];
	}

	@Override
	public boolean getCanSpawnHere() {
		if (super.getCanSpawnHere()) {
			return LOTRAmbientSpawnChecks.canSpawn(this, 8, 4, 32, 4, Material.plants, Material.vine);
		}
		return false;
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	public boolean isButterflyStill() {
		return dataWatcher.getWatchableObjectByte(17) == 1;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = MathHelper.floor_double(posX);
		int k = MathHelper.floor_double(posZ);
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
		if (biome instanceof LOTRBiomeGenMirkwood || biome instanceof LOTRBiomeGenWoodlandRealm) {
			setButterflyType(ButterflyType.MIRKWOOD);
		} else if (biome instanceof LOTRBiomeGenLothlorien) {
			setButterflyType(ButterflyType.LORIEN);
		} else if (biome instanceof LOTRBiomeGenFarHaradJungle) {
			setButterflyType(ButterflyType.JUNGLE);
		} else {
			setButterflyType(ButterflyType.COMMON);
		}
		return data;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (isButterflyStill()) {
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
			if (getButterflyType() == ButterflyType.LORIEN) {
				LOTRBlockTorch.TorchParticle particle;
				double d = posX;
				double d1 = posY;
				double d2 = posZ;
				if (elfTorchBlock == null) {
					Random torchRand = new Random();
					torchRand.setSeed(entityUniqueID.getLeastSignificantBits());
					elfTorchBlock = (LOTRBlockTorch) LOTRWorldGenElfHouse.getRandomTorch(torchRand);
				}
				particle = elfTorchBlock.createTorchParticle(rand);
				if (particle != null) {
					particle.spawn(d, d1, d2);
				}
			}
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setButterflyType(nbt.getInteger("ButterflyType"));
		setButterflyStill(nbt.getBoolean("ButterflyStill"));
	}

	public void setButterflyStill(boolean flag) {
		dataWatcher.updateObject(17, flag ? (byte) 1 : 0);
	}

	public void setButterflyType(ButterflyType type) {
		setButterflyType(type.ordinal());
	}

	public void setButterflyType(int i) {
		dataWatcher.updateObject(16, (byte) i);
	}

	@Override
	public void setUniqueID(UUID uuid) {
		entityUniqueID = uuid;
	}

	@Override
	public void updateAITasks() {
		super.updateAITasks();
		if (isButterflyStill()) {
			int k;
			int j;
			int i = MathHelper.floor_double(posX);
			if (!worldObj.getBlock(i, j = (int) posY - 1, k = MathHelper.floor_double(posZ)).isSideSolid(worldObj, i, j, k, ForgeDirection.UP) || rand.nextInt(400) == 0 || worldObj.getClosestPlayerToEntity(this, 3.0) != null) {
				setButterflyStill(false);
			}
		} else {
			if (currentFlightTarget != null && (!worldObj.isAirBlock(currentFlightTarget.posX, currentFlightTarget.posY, currentFlightTarget.posZ) || currentFlightTarget.posY < 1)) {
				currentFlightTarget = null;
			}
			if (currentFlightTarget == null || rand.nextInt(30) == 0 || currentFlightTarget.getDistanceSquared((int) posX, (int) posY, (int) posZ) < 4.0f) {
				currentFlightTarget = new ChunkCoordinates((int) posX + rand.nextInt(7) - rand.nextInt(7), (int) posY + rand.nextInt(6) - 2, (int) posZ + rand.nextInt(7) - rand.nextInt(7));
			}
			double speed = getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
			double d0 = currentFlightTarget.posX + 0.5 - posX;
			double d1 = currentFlightTarget.posY + 0.5 - posY;
			double d2 = currentFlightTarget.posZ + 0.5 - posZ;
			motionX += (Math.signum(d0) * 0.5 - motionX) * speed;
			motionY += (Math.signum(d1) * 0.7 - motionY) * speed;
			motionZ += (Math.signum(d2) * 0.5 - motionZ) * speed;
			float f = (float) (Math.atan2(motionZ, motionX) * 180.0 / 3.141592653589793) - 90.0f;
			float f1 = MathHelper.wrapAngleTo180_float(f - rotationYaw);
			moveForward = 0.5f;
			rotationYaw += f1;
			if (rand.nextInt(150) == 0 && worldObj.getBlock(MathHelper.floor_double(posX), (int) posY - 1, MathHelper.floor_double(posZ)).isNormalCube()) {
				setButterflyStill(true);
			}
		}
	}

	@Override
	public void updateFallState(double d, boolean flag) {
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("ButterflyType", getButterflyType().ordinal());
		nbt.setBoolean("ButterflyStill", isButterflyStill());
	}

	public enum ButterflyType {
		MIRKWOOD("mirkwood"), LORIEN("lorien"), COMMON("common"), JUNGLE("jungle");

		public String textureDir;

		ButterflyType(String s) {
			textureDir = s;
		}
	}

}
