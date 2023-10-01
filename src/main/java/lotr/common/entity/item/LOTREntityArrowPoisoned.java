package lotr.common.entity.item;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class LOTREntityArrowPoisoned extends EntityArrow implements IEntityAdditionalSpawnData {
	public LOTREntityArrowPoisoned(World world) {
		super(world);
	}

	public LOTREntityArrowPoisoned(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
	}

	public LOTREntityArrowPoisoned(World world, EntityLivingBase shooter, EntityLivingBase target, float charge, float inaccuracy) {
		super(world, shooter, target, charge, inaccuracy);
	}

	public LOTREntityArrowPoisoned(World world, EntityLivingBase shooter, float charge) {
		super(world, shooter, charge);
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer entityplayer) {
		boolean isInGround;
		NBTTagCompound nbt = new NBTTagCompound();
		writeEntityToNBT(nbt);
		isInGround = nbt.getByte("inGround") == 1;
		if (!worldObj.isRemote && isInGround && arrowShake <= 0) {
			boolean pickup;
			pickup = canBePickedUp == 1 || canBePickedUp == 2 && entityplayer.capabilities.isCreativeMode;
			if (canBePickedUp == 1 && !entityplayer.inventory.addItemStackToInventory(new ItemStack(LOTRMod.arrowPoisoned, 1))) {
				pickup = false;
			}
			if (pickup) {
				playSound("random.pop", 0.2f, ((rand.nextFloat() - rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
				entityplayer.onItemPickup(this, 1);
				setDead();
			}
		}
	}

	@Override
	public void readSpawnData(ByteBuf data) {
		Entity entity;
		motionX = data.readDouble();
		motionY = data.readDouble();
		motionZ = data.readDouble();
		int id = data.readInt();
		if (id >= 0 && (entity = worldObj.getEntityByID(id)) != null) {
			shootingEntity = entity;
		}
	}

	@Override
	public void writeSpawnData(ByteBuf data) {
		data.writeDouble(motionX);
		data.writeDouble(motionY);
		data.writeDouble(motionZ);
		data.writeInt(shootingEntity == null ? -1 : shootingEntity.getEntityId());
	}
}
