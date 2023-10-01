package lotr.common.entity.animal;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.UUID;

public class LOTREntityElk extends LOTREntityHorse implements LOTRRandomSkinEntity {
	public LOTREntityElk(World world) {
		super(world);
		setSize(1.6f, 1.8f);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0);
	}

	@Override
	public double clampChildHealth(double health) {
		return MathHelper.clamp_double(health, 16.0, 50.0);
	}

	@Override
	public double clampChildSpeed(double speed) {
		return MathHelper.clamp_double(speed, 0.08, 0.34);
	}

	@Override
	public EntityAIBase createMountAttackAI() {
		return new LOTREntityAIAttackOnCollide(this, 1.25, true);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		int hide = rand.nextInt(3) + rand.nextInt(1 + i);
		for (int l = 0; l < hide; ++l) {
			dropItem(Items.leather, 1);
		}
		int meat = rand.nextInt(3) + rand.nextInt(1 + i);
		for (int l = 0; l < meat; ++l) {
			if (isBurning()) {
				dropItem(LOTRMod.deerCooked, 1);
				continue;
			}
			dropItem(LOTRMod.deerRaw, 1);
		}
	}

	@Override
	public String getDeathSound() {
		super.getDeathSound();
		return "lotr:elk.death";
	}

	@Override
	public int getHorseType() {
		return 0;
	}

	@Override
	public String getHurtSound() {
		super.getHurtSound();
		return "lotr:elk.hurt";
	}

	@Override
	public String getLivingSound() {
		super.getLivingSound();
		return "lotr:elk.say";
	}

	@Override
	public boolean isBreedingItem(ItemStack itemstack) {
		return itemstack != null && itemstack.getItem() == Items.wheat;
	}

	@Override
	public boolean isMountHostile() {
		return true;
	}

	@Override
	public void onLOTRHorseSpawn() {
		double maxHealth = getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth * (1.0f + rand.nextFloat() * 0.5f));
	}

	@Override
	public void setUniqueID(UUID uuid) {
		entityUniqueID = uuid;
	}
}
