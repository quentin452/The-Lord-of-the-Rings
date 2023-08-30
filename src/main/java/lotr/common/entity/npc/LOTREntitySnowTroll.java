package lotr.common.entity.npc;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.entity.projectile.LOTREntityTrollSnowball;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.potion.*;
import net.minecraft.world.World;

public class LOTREntitySnowTroll extends LOTREntityTroll {
	public EntityAIBase rangedAttackAI = getTrollRangedAttackAI();
	public EntityAIBase meleeAttackAI;

	public LOTREntitySnowTroll(World world) {
		super(world);
		isImmuneToFrost = true;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (super.attackEntityAsMob(entity)) {
			int difficulty;
			int duration;
			if (entity instanceof EntityLivingBase && (duration = (difficulty = worldObj.difficultySetting.getDifficultyId()) * (difficulty + 5) / 2) > 0) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, duration * 20, 0));
			}
			return true;
		}
		return false;
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float f) {
		EntityArrow template = new EntityArrow(worldObj, this, target, f * 1.6f, 0.5f);
		LOTREntityTrollSnowball snowball = new LOTREntityTrollSnowball(worldObj, this);
		snowball.setLocationAndAngles(template.posX, template.posY, template.posZ, template.rotationYaw, template.rotationPitch);
		snowball.motionX = template.motionX;
		snowball.motionY = template.motionY;
		snowball.motionZ = template.motionZ;
		worldObj.spawnEntityInWorld(snowball);
		playSound("random.bow", 1.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.8f));
		swingItem();
	}

	@Override
	public boolean canTrollBeTickled(EntityPlayer entityplayer) {
		return false;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int furs = 1 + rand.nextInt(3) + rand.nextInt(i + 1);
		for (int l = 0; l < furs; ++l) {
			dropItem(LOTRMod.fur, 1);
		}
		int snows = 2 + rand.nextInt(4) + rand.nextInt(i * 2 + 1);
		for (int l = 0; l < snows; ++l) {
			dropItem(Items.snowball, 1);
		}
	}

	@Override
	public void dropTrollItems(boolean flag, int i) {
		if (rand.nextBoolean()) {
			super.dropTrollItems(flag, i);
		}
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(21, (byte) 0);
	}

	@Override
	public LOTRAchievement getKillAchievement() {
		return LOTRAchievement.killSnowTroll;
	}

	@Override
	public double getMeleeRange() {
		return 12.0;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		return null;
	}

	@Override
	public EntityAIBase getTrollAttackAI() {
		meleeAttackAI = new LOTREntityAIAttackOnCollide(this, 1.6, false);
		return meleeAttackAI;
	}

	public EntityAIBase getTrollRangedAttackAI() {
		return new LOTREntityAIRangedAttack(this, 1.2, 20, 30, 24.0f);
	}

	@Override
	public float getTrollScale() {
		return 0.8f;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleHealthUpdate(byte b) {
		if (b == 15) {
			super.handleHealthUpdate(b);
			for (int l = 0; l < 64; ++l) {
				worldObj.spawnParticle("snowballpoof", posX + rand.nextGaussian() * width * 0.5, posY + rand.nextDouble() * height, posZ + rand.nextGaussian() * width * 0.5, 0.0, 0.0, 0.0);
			}
		} else {
			super.handleHealthUpdate(b);
		}
	}

	@Override
	public boolean hasTrollName() {
		return false;
	}

	public boolean isThrowingSnow() {
		return dataWatcher.getWatchableObjectByte(21) == 1;
	}

	@Override
	public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
		if (mode == LOTREntityNPC.AttackMode.IDLE) {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			setThrowingSnow(false);
		}
		if (mode == LOTREntityNPC.AttackMode.MELEE) {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			tasks.addTask(3, meleeAttackAI);
			setThrowingSnow(false);
		}
		if (mode == LOTREntityNPC.AttackMode.RANGED) {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			tasks.addTask(3, rangedAttackAI);
			setThrowingSnow(true);
		}
	}

	@Override
	public void onTrollDeathBySun() {
		worldObj.playSoundAtEntity(this, "lotr:troll.transform", getSoundVolume(), getSoundPitch());
		worldObj.setEntityState(this, (byte) 15);
		setDead();
	}

	public void setThrowingSnow(boolean flag) {
		dataWatcher.updateObject(21, flag ? (byte) 1 : 0);
	}
}
