package lotr.common.entity.animal;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.ai.LOTREntityAIAvoidWithChance;
import lotr.common.entity.ai.LOTREntityAIFlee;
import lotr.common.entity.ai.LOTREntityAIRabbitEatCrops;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRFarmhand;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class LOTREntityRabbit extends EntityCreature implements LOTRAmbientCreature, LOTRRandomSkinEntity {
	public static String fleeSound = "lotr:rabbit.flee";

	public LOTREntityRabbit(World world) {
		super(world);
		setSize(0.5f, 0.5f);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new LOTREntityAIFlee(this, 2.0));
		tasks.addTask(2, new LOTREntityAIAvoidWithChance(this, EntityPlayer.class, 4.0f, 1.3, 1.5, 0.05f, fleeSound));
		tasks.addTask(2, new LOTREntityAIAvoidWithChance(this, LOTREntityNPC.class, 4.0f, 1.3, 1.5, 0.05f, fleeSound));
		tasks.addTask(3, new LOTREntityAIRabbitEatCrops(this, 1.2));
		tasks.addTask(4, new EntityAIWander(this, 1.0));
		tasks.addTask(5, new EntityAIWatchClosest(this, EntityLivingBase.class, 8.0f, 0.05f));
		tasks.addTask(6, new EntityAILookIdle(this));
	}

	public boolean anyFarmhandsNearby(int i, int j, int k) {
		int range = 16;
		List farmhands = worldObj.getEntitiesWithinAABB(LOTRFarmhand.class, AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 1, k + 1).expand(range, range, range));
		return !farmhands.isEmpty();
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		boolean flag = super.attackEntityFrom(damagesource, f);
		if (flag && !worldObj.isRemote && damagesource.getEntity() instanceof EntityPlayer && isRabbitEating()) {
			EntityPlayer entityplayer = (EntityPlayer) damagesource.getEntity();
			LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.attackRabbit);
		}
		return flag;
	}

	@Override
	public boolean canDespawn() {
		return true;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		int meat = rand.nextInt(3) + rand.nextInt(1 + i);
		for (int l = 0; l < meat; ++l) {
			if (isBurning()) {
				dropItem(LOTRMod.rabbitCooked, 1);
				continue;
			}
			dropItem(LOTRMod.rabbitRaw, 1);
		}
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(17, (byte) 0);
	}

	@Override
	public float getBlockPathWeight(int i, int j, int k) {
		Block block = worldObj.getBlock(i, j - 1, k);
		if (block == Blocks.grass) {
			return 10.0f;
		}
		return worldObj.getLightBrightness(i, j, k) - 0.5f;
	}

	@Override
	public boolean getCanSpawnHere() {
		if (super.getCanSpawnHere()) {
			boolean flag = LOTRAmbientSpawnChecks.canSpawn(this, 8, 4, 32, 4, Material.plants, Material.vine);
			if (flag) {
				int i = MathHelper.floor_double(posX);
				return !anyFarmhandsNearby(i, MathHelper.floor_double(posY), MathHelper.floor_double(posZ));
			}
		}
		return false;
	}

	@Override
	public String getDeathSound() {
		return "lotr:rabbit.death";
	}

	@Override
	public int getExperiencePoints(EntityPlayer entityplayer) {
		return 1 + rand.nextInt(2);
	}

	@Override
	public String getHurtSound() {
		return "lotr:rabbit.hurt";
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
	}

	@Override
	public int getTalkInterval() {
		return 200;
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	public boolean isRabbitEating() {
		return dataWatcher.getWatchableObjectByte(17) == 1;
	}

	public void setRabbitEating(boolean flag) {
		dataWatcher.updateObject(17, flag ? (byte) 1 : 0);
	}

	@Override
	public void setUniqueID(UUID uuid) {
		entityUniqueID = uuid;
	}
}
