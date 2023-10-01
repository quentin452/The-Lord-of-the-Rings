package lotr.common.entity.npc;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIEat;
import lotr.common.entity.animal.LOTREntityRabbit;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class LOTREntitySaruman extends LOTREntityNPC {
	public LOTREntityRabbit targetingRabbit;
	public String randomNameTag;

	public LOTREntitySaruman(World world) {
		super(world);
		setSize(0.6f, 1.8f);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIOpenDoor(this, true));
		tasks.addTask(2, new EntityAIWander(this, 1.0));
		tasks.addTask(3, new LOTREntityAIEat(this, new LOTRFoods(new ItemStack[]{new ItemStack(Blocks.log)}), 200));
		tasks.addTask(4, new EntityAIWatchClosest(this, EntityLivingBase.class, 20.0f, 0.05f));
		tasks.addTask(5, new EntityAILookIdle(this));
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int j = rand.nextInt(2) + rand.nextInt(i + 1);
		for (int k = 0; k < j; ++k) {
			dropItem(Items.bone, 1);
		}
	}

	@Override
	public boolean getAlwaysRenderNameTag() {
		return true;
	}

	@Override
	public String getCustomNameTag() {
		if (randomNameTag == null) {
			StringBuilder tmp = new StringBuilder();
			for (int l = 0; l < 100; ++l) {
				tmp.append((char) rand.nextInt(1000));
			}
			randomNameTag = tmp.toString();
		}
		return randomNameTag;
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.ISENGARD;
	}

	@Override
	public String getLivingSound() {
		return "lotr:orc.say";
	}

	@Override
	public int getTalkInterval() {
		return 10;
	}

	@Override
	public boolean hasCustomNameTag() {
		return true;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!worldObj.isRemote) {
			List rabbits;
			if (rand.nextInt(10) == 0) {
				playSound(getLivingSound(), getSoundVolume(), getSoundPitch());
			}
			List allMobsExcludingRabbits = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(24.0, 24.0, 24.0));
			for (Object allMobsExcludingRabbit : allMobsExcludingRabbits) {
				Entity entity = (Entity) allMobsExcludingRabbit;
				if (entity instanceof LOTREntityRabbit || entity instanceof LOTREntityGandalf) {
					continue;
				}
				double dSq = getDistanceSqToEntity(entity);
				if (dSq <= 0.0) {
					dSq = 1.0E-5;
				}
				float strength = 1.0f;
				if (entity instanceof EntityPlayer) {
					strength /= 3.0f;
				}
				double force = strength / dSq;
				double x = entity.posX - posX;
				double y = entity.posY - posY;
				double z = entity.posZ - posZ;
				x *= force;
				y *= force;
				z *= force;
				if (entity instanceof EntityPlayerMP) {
					((EntityPlayerMP) entity).playerNetServerHandler.sendPacket(new S27PacketExplosion(posX, posY, posZ, 0.0f, new ArrayList(), Vec3.createVectorHelper(x, y, z)));
					continue;
				}
				entity.motionX += x;
				entity.motionY += y;
				entity.motionZ += z;
			}
			if (rand.nextInt(40) == 0) {
				LOTREntityRabbit rabbit = new LOTREntityRabbit(worldObj);
				int i = MathHelper.floor_double(posX) - rand.nextInt(16) + rand.nextInt(16);
				int j = MathHelper.floor_double(boundingBox.minY) - rand.nextInt(8) + rand.nextInt(8);
				int k = MathHelper.floor_double(posZ) - rand.nextInt(16) + rand.nextInt(16);
				rabbit.setLocationAndAngles(i, j, k, 0.0f, 0.0f);
				AxisAlignedBB aabb = rabbit.boundingBox;
				if (worldObj.checkNoEntityCollision(aabb) && worldObj.getCollidingBoundingBoxes(rabbit, aabb).isEmpty() && !worldObj.isAnyLiquid(aabb)) {
					worldObj.spawnEntityInWorld(rabbit);
				}
			}
			if (targetingRabbit == null && rand.nextInt(20) == 0 && !(rabbits = worldObj.getEntitiesWithinAABB(LOTREntityRabbit.class, boundingBox.expand(24.0, 24.0, 24.0))).isEmpty()) {
				LOTREntityRabbit rabbit = (LOTREntityRabbit) rabbits.get(rand.nextInt(rabbits.size()));
				if (rabbit.ridingEntity == null) {
					targetingRabbit = rabbit;
				}
			}
			if (targetingRabbit != null) {
				if (targetingRabbit.isEntityAlive()) {
					getNavigator().tryMoveToEntityLiving(targetingRabbit, 1.0);
					if (getDistanceToEntity(targetingRabbit) < 1.0) {
						Entity entityToMount = this;
						while (entityToMount.riddenByEntity != null) {
							entityToMount = entityToMount.riddenByEntity;
						}
						targetingRabbit.mountEntity(entityToMount);
						targetingRabbit = null;
					}
				} else {
					targetingRabbit = null;
				}
			}
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(0, new ItemStack(LOTRMod.gandalfStaffWhite));
		return data;
	}
}
