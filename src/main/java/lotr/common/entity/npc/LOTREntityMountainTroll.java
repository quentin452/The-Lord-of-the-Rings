package lotr.common.entity.npc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import lotr.common.entity.projectile.LOTREntityThrownRock;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class LOTREntityMountainTroll extends LOTREntityTroll {
	public static IAttribute thrownRockDamage = new RangedAttribute("lotr.thrownRockDamage", 5.0, 0.0, 100.0).setDescription("LOTR Thrown Rock Damage");
	public EntityAIBase rangedAttackAI = getTrollRangedAttackAI();
	public EntityAIBase meleeAttackAI;
	public boolean canDropTrollTotem = true;

	public LOTREntityMountainTroll(World world) {
		super(world);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(70.0);
		getEntityAttribute(npcAttackDamage).setBaseValue(7.0);
		getAttributeMap().registerAttribute(thrownRockDamage);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float f) {
		EntityArrow template = new EntityArrow(worldObj, this, target, f * 1.5f, 0.5f);
		LOTREntityThrownRock rock = getThrownRock();
		rock.setLocationAndAngles(template.posX, template.posY, template.posZ, template.rotationYaw, template.rotationPitch);
		rock.motionX = template.motionX;
		rock.motionY = template.motionY + 0.6;
		rock.motionZ = template.motionZ;
		worldObj.spawnEntityInWorld(rock);
		playSound(getLivingSound(), getSoundVolume(), getSoundPitch() * 0.75f);
		swingItem();
	}

	@Override
	public boolean canTrollBeTickled(EntityPlayer entityplayer) {
		return false;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		if (canDropTrollTotem) {
			dropTrollTotemPart(flag, i);
		}
	}

	public void dropTrollTotemPart(boolean flag, int i) {
		int totemChance = 15 - i * 3;
		if (rand.nextInt(Math.max(totemChance, 1)) == 0) {
			entityDropItem(new ItemStack(LOTRMod.trollTotem, 1, rand.nextInt(3)), 0.0f);
		}
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(21, (byte) 0);
	}

	@Override
	public float getAlignmentBonus() {
		return 4.0f;
	}

	@Override
	public int getExperiencePoints(EntityPlayer entityplayer) {
		return 7 + rand.nextInt(6);
	}

	@Override
	public LOTRAchievement getKillAchievement() {
		return LOTRAchievement.killMountainTroll;
	}

	@Override
	public double getMeleeRange() {
		return 12.0;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		return null;
	}

	public LOTREntityThrownRock getThrownRock() {
		LOTREntityThrownRock rock = new LOTREntityThrownRock(worldObj, this);
		rock.setDamage((float) getEntityAttribute(thrownRockDamage).getAttributeValue());
		return rock;
	}

	@Override
	public EntityAIBase getTrollAttackAI() {
		meleeAttackAI = new LOTREntityAIAttackOnCollide(this, 1.8, false);
		return meleeAttackAI;
	}

	public EntityAIBase getTrollRangedAttackAI() {
		return new LOTREntityAIRangedAttack(this, 1.2, 30, 60, 24.0f);
	}

	@Override
	public float getTrollScale() {
		return 1.6f;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleHealthUpdate(byte b) {
		if (b == 15) {
			super.handleHealthUpdate(b);
			for (int l = 0; l < 64; ++l) {
				LOTRMod.proxy.spawnParticle("largeStone", posX + rand.nextGaussian() * width * 0.5, posY + rand.nextDouble() * height, posZ + rand.nextGaussian() * width * 0.5, 0.0, 0.0, 0.0);
			}
		} else {
			super.handleHealthUpdate(b);
		}
	}

	@Override
	public boolean hasTrollName() {
		return false;
	}

	public boolean isThrowingRocks() {
		return dataWatcher.getWatchableObjectByte(21) == 1;
	}

	public void setThrowingRocks(boolean flag) {
		dataWatcher.updateObject(21, flag ? (byte) 1 : 0);
	}

	@Override
	public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
		if (mode == LOTREntityNPC.AttackMode.IDLE) {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			setThrowingRocks(false);
		}
		if (mode == LOTREntityNPC.AttackMode.MELEE) {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			tasks.addTask(3, meleeAttackAI);
			setThrowingRocks(false);
		}
		if (mode == LOTREntityNPC.AttackMode.RANGED) {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			tasks.addTask(3, rangedAttackAI);
			setThrowingRocks(true);
		}
	}

	@Override
	public void onTrollDeathBySun() {
		worldObj.playSoundAtEntity(this, "lotr:troll.transform", getSoundVolume(), getSoundPitch());
		worldObj.setEntityState(this, (byte) 15);
		setDead();
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		if (nbt.hasKey("CanDropTrollTotem")) {
			setCanDropTrollTotem(nbt.getBoolean("CanDropTrollTotem"));
		}
	}

	public LOTREntityMountainTroll setCanDropTrollTotem(boolean flag) {
		canDropTrollTotem = flag;
		return this;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setBoolean("CanDropTrollTotem", canDropTrollTotem);
	}
}
