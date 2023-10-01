package lotr.common.entity.projectile;

import lotr.common.item.LOTRItemThrowingAxe;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityThrowingAxe extends LOTREntityProjectileBase {
	public int axeRotation;

	public LOTREntityThrowingAxe(World world) {
		super(world);
	}

	public LOTREntityThrowingAxe(World world, EntityLivingBase entityliving, EntityLivingBase target, ItemStack item, float charge, float inaccuracy) {
		super(world, entityliving, target, item, charge, inaccuracy);
	}

	public LOTREntityThrowingAxe(World world, EntityLivingBase entityliving, ItemStack item, float charge) {
		super(world, entityliving, item, charge);
	}

	public LOTREntityThrowingAxe(World world, ItemStack item, double d, double d1, double d2) {
		super(world, item, d, d1, d2);
	}

	@Override
	public float getBaseImpactDamage(Entity entity, ItemStack itemstack) {
		if (!isThrowingAxe()) {
			return 0.0f;
		}
		float speed = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
		float damage = ((LOTRItemThrowingAxe) itemstack.getItem()).getRangedDamageMultiplier(itemstack, shootingEntity, entity);
		return speed * damage;
	}

	public boolean isThrowingAxe() {
		Item item = getProjectileItem().getItem();
		return item instanceof LOTRItemThrowingAxe;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (!inGround) {
			++axeRotation;
			if (axeRotation > 9) {
				axeRotation = 0;
			}
			rotationPitch = axeRotation / 9.0f * 360.0f;
		}
		if (!isThrowingAxe()) {
			setDead();
		}
	}
}
