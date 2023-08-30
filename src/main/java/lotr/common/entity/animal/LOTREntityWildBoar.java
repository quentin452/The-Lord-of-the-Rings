package lotr.common.entity.animal;

import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityWildBoar extends LOTREntityHorse {
	public LOTREntityWildBoar(World world) {
		super(world);
		setSize(0.9f, 0.8f);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0);
	}

	@Override
	public double clampChildHealth(double health) {
		return MathHelper.clamp_double(health, 10.0, 30.0);
	}

	@Override
	public double clampChildSpeed(double speed) {
		return MathHelper.clamp_double(speed, 0.08, 0.29);
	}

	@Override
	public EntityAIBase createMountAttackAI() {
		return new LOTREntityAIAttackOnCollide(this, 1.2, true);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		int meat = rand.nextInt(3) + 1 + rand.nextInt(1 + i);
		for (int l = 0; l < meat; ++l) {
			if (isBurning()) {
				dropItem(Items.cooked_porkchop, 1);
				continue;
			}
			dropItem(Items.porkchop, 1);
		}
	}

	@Override
	public void func_145780_a(int i, int j, int k, Block block) {
		playSound("mob.pig.step", 0.15f, 1.0f);
	}

	@Override
	public String getAngrySoundName() {
		super.getAngrySoundName();
		return "mob.pig.say";
	}

	@Override
	public String getDeathSound() {
		super.getDeathSound();
		return "mob.pig.death";
	}

	@Override
	public int getHorseType() {
		return 0;
	}

	@Override
	public String getHurtSound() {
		super.getHurtSound();
		return "mob.pig.say";
	}

	@Override
	public String getLivingSound() {
		super.getLivingSound();
		return "mob.pig.say";
	}

	@Override
	public boolean isBreedingItem(ItemStack itemstack) {
		return itemstack != null && itemstack.getItem() == Items.carrot;
	}

	@Override
	public boolean isMountHostile() {
		return true;
	}

	@Override
	public void onLOTRHorseSpawn() {
		double maxHealth = getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
		maxHealth = Math.min(maxHealth, 25.0);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth);
		double speed = getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
		speed *= 1.0;
		speed = clampChildSpeed(speed);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(speed);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		double speed;
		double clampedSpeed;
		super.readEntityFromNBT(nbt);
		boolean doBoarNerf = true;
		if ((clampedSpeed = clampChildSpeed(speed = getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue())) < speed) {
			System.out.println("Reducing boar movement speed from " + speed);
			speed = clampedSpeed;
			getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(speed);
			System.out.println("Movement speed now " + getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
		}
	}
}
