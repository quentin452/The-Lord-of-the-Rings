package lotr.common.entity.projectile;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRBannerProtection;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTREntityPlate extends EntityThrowable implements IEntityAdditionalSpawnData {
	public int plateSpin;
	public Block plateBlock;

	public LOTREntityPlate(World world) {
		super(world);
		setSize(0.5f, 0.5f);
	}

	public LOTREntityPlate(World world, Block block, double d, double d1, double d2) {
		super(world, d, d1, d2);
		setSize(0.5f, 0.5f);
		plateBlock = block;
	}

	public LOTREntityPlate(World world, Block block, EntityLivingBase entityliving) {
		super(world, entityliving);
		setSize(0.5f, 0.5f);
		plateBlock = block;
	}

	public boolean breakGlass(int i, int j, int k) {
		Block block = worldObj.getBlock(i, j, k);
		if (block.getMaterial() == Material.glass && !LOTRBannerProtection.isProtected(worldObj, i, j, k, LOTRBannerProtection.forThrown(this), true)) {
			worldObj.playAuxSFX(2001, i, j, k, Block.getIdFromBlock(block) + (worldObj.getBlockMetadata(i, j, k) << 12));
			worldObj.setBlockToAir(i, j, k);
			return true;
		}
		return false;
	}

	@Override
	public float func_70182_d() {
		return 1.5f;
	}

	@Override
	public float getGravityVelocity() {
		return 0.02f;
	}

	public Block getPlateBlock() {
		return plateBlock;
	}

	@Override
	public void onImpact(MovingObjectPosition m) {
		int i;
		int k;
		int j;
		if (m.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
			if (m.entityHit == getThrower()) {
				return;
			}
			m.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 1.0f);
		} else if (m.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && !worldObj.isRemote && breakGlass(i = m.blockX, j = m.blockY, k = m.blockZ)) {
			int range = 2;
			for (int i1 = i - range; i1 <= i + range; ++i1) {
				for (int j1 = j - range; j1 <= j + range; ++j1) {
					for (int k1 = k - range; k1 <= k + range; ++k1) {
						if (rand.nextInt(4) == 0) {
							continue;
						}
						breakGlass(i1, j1, k1);
					}
				}
			}
			return;
		}
		for (i = 0; i < 8; ++i) {
			double d = posX + MathHelper.randomFloatClamp(rand, -0.25f, 0.25f);
			double d1 = posY + MathHelper.randomFloatClamp(rand, -0.25f, 0.25f);
			double d2 = posZ + MathHelper.randomFloatClamp(rand, -0.25f, 0.25f);
			worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(plateBlock) + "_0", d, d1, d2, 0.0, 0.0, 0.0);
		}
		if (!worldObj.isRemote) {
			worldObj.playSoundAtEntity(this, plateBlock.stepSound.getBreakSound(), 1.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f);
			setDead();
		}
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		++plateSpin;
		rotationYaw = plateSpin % 12 / 12.0f * 360.0f;
		float speed = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
		if (speed > 0.1f && motionY < 0.0 && isInWater()) {
			float factor = MathHelper.randomFloatClamp(rand, 0.4f, 0.8f);
			motionX *= factor;
			motionZ *= factor;
			motionY += factor;
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		if (nbt.hasKey("PlateBlockID")) {
			plateBlock = Block.getBlockById(nbt.getShort("PlateBlockID"));
		}
		if (plateBlock == null) {
			plateBlock = LOTRMod.plateBlock;
		}
	}

	@Override
	public void readSpawnData(ByteBuf data) {
		Block block = Block.getBlockById(data.readShort());
		if (block == null) {
			block = LOTRMod.plateBlock;
		}
		plateBlock = block;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		if (plateBlock != null) {
			nbt.setShort("PlateBlockID", (short) Block.getIdFromBlock(plateBlock));
		}
	}

	@Override
	public void writeSpawnData(ByteBuf data) {
		data.writeShort(Block.getIdFromBlock(plateBlock));
	}
}
