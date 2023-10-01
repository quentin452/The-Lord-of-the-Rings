package lotr.common.entity.projectile;

import lotr.common.item.LOTRItemCrossbowBolt;
import lotr.common.item.LOTRItemDagger;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityCrossbowBolt extends LOTREntityProjectileBase {
	public static float BOLT_RELATIVE_TO_ARROW = 2.0f;
	public double boltDamageFactor = 2.0;

	public LOTREntityCrossbowBolt(World world) {
		super(world);
	}

	public LOTREntityCrossbowBolt(World world, EntityLivingBase entityliving, EntityLivingBase target, ItemStack item, float charge, float inaccuracy) {
		super(world, entityliving, target, item, charge, inaccuracy);
	}

	public LOTREntityCrossbowBolt(World world, EntityLivingBase entityliving, ItemStack item, float charge) {
		super(world, entityliving, item, charge);
	}

	public LOTREntityCrossbowBolt(World world, ItemStack item, double d, double d1, double d2) {
		super(world, item, d, d1, d2);
	}

	@Override
	public float getBaseImpactDamage(Entity entity, ItemStack itemstack) {
		float speed = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
		return speed * 2.0f * (float) boltDamageFactor;
	}

	@Override
	public int maxTicksInGround() {
		return 1200;
	}

	@Override
	public void onCollideWithTarget(Entity entity) {
		Item item;
		ItemStack itemstack;
		if (!worldObj.isRemote && entity instanceof EntityLivingBase && (itemstack = getProjectileItem()) != null && (item = itemstack.getItem()) instanceof LOTRItemCrossbowBolt && ((LOTRItemCrossbowBolt) item).isPoisoned) {
			LOTRItemDagger.applyStandardPoison((EntityLivingBase) entity);
		}
		super.onCollideWithTarget(entity);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		if (nbt.hasKey("boltDamageFactor")) {
			boltDamageFactor = nbt.getDouble("boltDamageFactor");
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setDouble("boltDamageFactor", boltDamageFactor);
	}
}
