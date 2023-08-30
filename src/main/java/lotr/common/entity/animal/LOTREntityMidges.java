package lotr.common.entity.animal;

import java.util.List;

import lotr.common.*;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.world.biome.LOTRBiomeGenMidgewater;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityMidges extends EntityLiving implements LOTRAmbientCreature {
	public ChunkCoordinates currentFlightTarget;
	public EntityPlayer playerTarget;
	public Midge[] midges;

	public LOTREntityMidges(World world) {
		super(world);
		setSize(2.0f, 2.0f);
		renderDistanceWeight = 0.5;
		midges = new Midge[3 + rand.nextInt(6)];
		for (int l = 0; l < midges.length; ++l) {
			midges[l] = new Midge();
		}
	}

	@Override
	public boolean allowLeashing() {
		return false;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(2.0);
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
	public void fall(float f) {
	}

	@Override
	public boolean getCanSpawnHere() {
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(posY);
		int k = MathHelper.floor_double(posZ);
		if (j < 62) {
			return false;
		}
		return worldObj.getBlock(i, j - 1, k) == worldObj.getBiomeGenForCoords(i, k).topBlock && super.getCanSpawnHere();
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		int id = LOTREntities.getEntityID(this);
		if (id > 0 && LOTREntities.spawnEggs.containsKey(id)) {
			return new ItemStack(LOTRMod.spawnEgg, 1, id);
		}
		return null;
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		return false;
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	@Override
	public void onDeath(DamageSource damagesource) {
		Entity attacker;
		super.onDeath(damagesource);
		if (!worldObj.isRemote && damagesource instanceof EntityDamageSourceIndirect && (attacker = damagesource.getEntity()) instanceof LOTREntityNPC) {
			LOTREntityNPC npc = (LOTREntityNPC) attacker;
			if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() != null) {
				EntityPlayer entityplayer = npc.hiredNPCInfo.getHiringPlayer();
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.shootDownMidges);
			}
		}
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		motionY *= 0.6;
		for (Midge midge : midges) {
			midge.update();
		}
		if (rand.nextInt(5) == 0) {
			playSound("lotr:midges.swarm", getSoundVolume(), getSoundPitch());
		}
		if (!worldObj.isRemote && isEntityAlive()) {
			int chance;
			boolean inMidgewater = worldObj.getBiomeGenForCoords(MathHelper.floor_double(posX), MathHelper.floor_double(posZ)) instanceof LOTRBiomeGenMidgewater;
			chance = inMidgewater ? 100 : 500;
			if (rand.nextInt(chance) == 0) {
				double range = inMidgewater ? 16.0 : 24.0;
				int threshold = inMidgewater ? 6 : 5;
				List list = worldObj.getEntitiesWithinAABB(LOTREntityMidges.class, boundingBox.expand(range, range, range));
				if (list.size() < threshold) {
					LOTREntityMidges moreMidges = new LOTREntityMidges(worldObj);
					moreMidges.setLocationAndAngles(posX, posY, posZ, rand.nextFloat() * 360.0f, 0.0f);
					moreMidges.onSpawnWithEgg(null);
					worldObj.spawnEntityInWorld(moreMidges);
				}
			}
		}
	}

	@Override
	public void updateAITasks() {
		super.updateAITasks();
		if (currentFlightTarget != null && !worldObj.isAirBlock(currentFlightTarget.posX, currentFlightTarget.posY, currentFlightTarget.posZ)) {
			currentFlightTarget = null;
		}
		if (playerTarget != null && (!playerTarget.isEntityAlive() || getDistanceSqToEntity(playerTarget) > 256.0)) {
			playerTarget = null;
		}
		if (playerTarget != null) {
			if (rand.nextInt(400) == 0) {
				playerTarget = null;
			} else {
				currentFlightTarget = new ChunkCoordinates((int) playerTarget.posX, (int) playerTarget.posY + 3, (int) playerTarget.posZ);
			}
		} else if (rand.nextInt(100) == 0) {
			EntityPlayer closestPlayer = worldObj.getClosestPlayerToEntity(this, 12.0);
			if (closestPlayer != null && rand.nextInt(7) == 0) {
				playerTarget = closestPlayer;
			} else {
				int height;
				int i = (int) posX + rand.nextInt(7) - rand.nextInt(7);
				int j = (int) posY + rand.nextInt(4) - rand.nextInt(3);
				int k = (int) posZ + rand.nextInt(7) - rand.nextInt(7);
				if (j < 1) {
					j = 1;
				}
				height = worldObj.getTopSolidOrLiquidBlock(i, k);
				if (j > height + 8) {
					j = height + 8;
				}
				currentFlightTarget = new ChunkCoordinates(i, j, k);
			}
		}
		if (currentFlightTarget != null) {
			double dx = currentFlightTarget.posX + 0.5 - posX;
			double dy = currentFlightTarget.posY + 0.5 - posY;
			double dz = currentFlightTarget.posZ + 0.5 - posZ;
			motionX += (Math.signum(dx) * 0.5 - motionX) * 0.1;
			motionY += (Math.signum(dy) * 0.7 - motionY) * 0.1;
			motionZ += (Math.signum(dz) * 0.5 - motionZ) * 0.1;
			moveForward = 0.2f;
		} else {
			motionZ = 0.0;
			motionY = 0.0;
			motionX = 0.0;
		}
	}

	@Override
	public void updateFallState(double d, boolean flag) {
	}

	public class Midge {
		public float midge_posX;
		public float midge_posY;
		public float midge_posZ;
		public float midge_prevPosX;
		public float midge_prevPosY;
		public float midge_prevPosZ;
		public float midge_initialPosY;
		public float midge_rotation;
		public int midgeTick;
		public int maxMidgeTick = 80;

		public Midge() {
			midge_posX = -1.0f + rand.nextFloat() * 2.0f;
			midge_initialPosY = midge_posY = rand.nextFloat() * 2.0f;
			midge_posZ = -1.0f + rand.nextFloat() * 2.0f;
			midge_rotation = rand.nextFloat() * 360.0f;
			midgeTick = rand.nextInt(maxMidgeTick);
		}

		public void update() {
			midge_prevPosX = midge_posX;
			midge_prevPosY = midge_posY;
			midge_prevPosZ = midge_posZ;
			++midgeTick;
			if (midgeTick > maxMidgeTick) {
				midgeTick = 0;
			}
			midge_posY = midge_initialPosY + 0.5f * MathHelper.sin(midgeTick / 6.2831855f);
		}
	}

}
