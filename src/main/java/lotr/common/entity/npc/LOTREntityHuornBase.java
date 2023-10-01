package lotr.common.entity.npc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.client.LOTRTickHandlerClient;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import lotr.common.entity.ai.LOTREntityAIHiredRemainStill;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustrum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class LOTREntityHuornBase extends LOTREntityTree {
	public boolean ignoringFrustumForRender;

	protected LOTREntityHuornBase(World world) {
		super(world);
		setSize(1.5f, 4.0f);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new LOTREntityAIHiredRemainStill(this));
		tasks.addTask(2, new LOTREntityAIAttackOnCollide(this, 1.5, false));
		tasks.addTask(3, new LOTREntityAIFollowHiringPlayer(this));
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60.0);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3);
		getEntityAttribute(npcAttackDamage).setBaseValue(4.0);
	}

	@Override
	public void applyEntityCollision(Entity entity) {
		if (isHuornActive()) {
			super.applyEntityCollision(entity);
		} else {
			double x = motionX;
			double y = motionY;
			double z = motionZ;
			super.applyEntityCollision(entity);
			motionX = x;
			motionY = y;
			motionZ = z;
		}
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		boolean flag = super.attackEntityFrom(damagesource, f);
		if (flag && !worldObj.isRemote && !isHuornActive()) {
			setHuornActive(true);
		}
		return flag;
	}

	@Override
	public void collideWithEntity(Entity entity) {
		if (isHuornActive()) {
			super.collideWithEntity(entity);
		} else {
			double x = motionX;
			double y = motionY;
			double z = motionZ;
			super.collideWithEntity(entity);
			motionX = x;
			motionY = y;
			motionZ = z;
		}
	}

	@Override
	public int decreaseAirSupply(int i) {
		return i;
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(17, (byte) 0);
	}

	@Override
	public String getDeathSound() {
		return Blocks.log.stepSound.getBreakSound();
	}

	@Override
	public String getHurtSound() {
		return Blocks.log.stepSound.getBreakSound();
	}

	@Override
	public float getSoundPitch() {
		return super.getSoundPitch() * 0.75f;
	}

	public boolean isHuornActive() {
		return dataWatcher.getWatchableObjectByte(17) == 1;
	}

	public void setHuornActive(boolean flag) {
		dataWatcher.updateObject(17, flag ? (byte) 1 : 0);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean isInRangeToRender3d(double d, double d1, double d2) {
		EntityLivingBase viewer = Minecraft.getMinecraft().renderViewEntity;
		float f = LOTRTickHandlerClient.renderTick;
		double viewX = viewer.lastTickPosX + (viewer.posX - viewer.lastTickPosX) * f;
		double viewY = viewer.lastTickPosY + (viewer.posY - viewer.lastTickPosY) * f;
		double viewZ = viewer.lastTickPosZ + (viewer.posZ - viewer.lastTickPosZ) * f;
		ICamera camera = new Frustrum();
		camera.setPosition(viewX, viewY, viewZ);
		AxisAlignedBB expandedBB = boundingBox.expand(2.0, 3.0, 2.0);
		if (camera.isBoundingBoxInFrustum(expandedBB)) {
			ignoringFrustumForRender = true;
			ignoreFrustumCheck = true;
		}
		return super.isInRangeToRender3d(d, d1, d2);
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!worldObj.isRemote) {
			boolean active = !getNavigator().noPath() || getAttackTarget() != null && getAttackTarget().isEntityAlive();
			setHuornActive(active);
		}
	}
}
