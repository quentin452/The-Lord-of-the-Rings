package lotr.common.entity.animal;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRReflection;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityShirePony extends LOTREntityHorse {
	public static float PONY_SCALE = 0.8f;
	public boolean breedingFlag;

	public LOTREntityShirePony(World world) {
		super(world);
		setSize(width * PONY_SCALE, height * PONY_SCALE);
	}

	@Override
	public double clampChildHealth(double health) {
		return MathHelper.clamp_double(health, 10.0, 28.0);
	}

	@Override
	public double clampChildJump(double jump) {
		return MathHelper.clamp_double(jump, 0.2, 1.0);
	}

	@Override
	public double clampChildSpeed(double speed) {
		return MathHelper.clamp_double(speed, 0.08, 0.3);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable other) {
		LOTREntityShirePony otherPony = (LOTREntityShirePony) other;
		breedingFlag = true;
		otherPony.breedingFlag = true;
		EntityAgeable child = super.createChild(otherPony);
		breedingFlag = false;
		otherPony.breedingFlag = false;
		return child;
	}

	@Override
	public boolean func_110259_cr() {
		return false;
	}

	@Override
	public String getAngrySoundName() {
		super.getAngrySoundName();
		return "mob.horse.angry";
	}

	@Override
	public String getDeathSound() {
		super.getDeathSound();
		return "mob.horse.death";
	}

	@Override
	public int getHorseType() {
		if (breedingFlag) {
			return 0;
		}
		return worldObj.isRemote ? 0 : 1;
	}

	@Override
	public String getHurtSound() {
		super.getHurtSound();
		return "mob.horse.hit";
	}

	@Override
	public String getLivingSound() {
		super.getLivingSound();
		return "mob.horse.idle";
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!worldObj.isRemote && riddenByEntity instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) riddenByEntity;
			if (isHorseSaddled() && isChested()) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.rideShirePony);
			}
		}
	}

	@Override
	public void onLOTRHorseSpawn() {
		double maxHealth = getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth * 0.75);
		double jumpStrength = getEntityAttribute(LOTRReflection.getHorseJumpStrength()).getAttributeValue();
		getEntityAttribute(LOTRReflection.getHorseJumpStrength()).setBaseValue(jumpStrength * 0.5);
		double moveSpeed = getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(moveSpeed * 0.8);
	}
}
