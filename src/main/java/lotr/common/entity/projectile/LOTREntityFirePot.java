package lotr.common.entity.projectile;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockPlate;
import lotr.common.block.LOTRBlockRhunFireJar;
import lotr.common.entity.animal.LOTREntityBird;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.List;

public class LOTREntityFirePot extends EntityThrowable {
	public LOTREntityFirePot(World world) {
		super(world);
	}

	public LOTREntityFirePot(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
	}

	public LOTREntityFirePot(World world, EntityLivingBase entityliving) {
		super(world, entityliving);
	}

	@Override
	public float func_70182_d() {
		return 1.2f;
	}

	@Override
	public float getGravityVelocity() {
		return 0.04f;
	}

	@Override
	public void onImpact(MovingObjectPosition m) {
		if (!worldObj.isRemote) {
			Block block;
			EntityLivingBase thrower = getThrower();
			Entity hitEntity = m.entityHit;
			double range = 3.0;
			List entities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, boundingBox.expand(range, range, range));
			if (hitEntity instanceof EntityLivingBase && !entities.contains(hitEntity)) {
				entities.add(hitEntity);
			}
			for (Object obj : entities) {
				EntityLivingBase entity = (EntityLivingBase) obj;
				float damage = 1.0f;
				if (entity == hitEntity) {
					damage = 3.0f;
				}
				if (entity == hitEntity && thrower instanceof EntityPlayer && hitEntity instanceof LOTREntityBird && !((LOTREntityBird) hitEntity).isBirdStill()) {
					LOTRLevelData.getData((EntityPlayer) thrower).addAchievement(LOTRAchievement.hitBirdFirePot);
				}
				if (!entity.attackEntityFrom(DamageSource.causeThrownDamage(this, thrower), damage)) {
					continue;
				}
				int fire = 2 + rand.nextInt(3);
				if (entity == hitEntity) {
					fire += 2 + rand.nextInt(3);
				}
				entity.setFire(fire);
			}
			if (m.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && (block = worldObj.getBlock(m.blockX, m.blockY, m.blockZ)) instanceof LOTRBlockRhunFireJar) {
				((LOTRBlockRhunFireJar) block).explode(worldObj, m.blockX, m.blockY, m.blockZ);
			}
			worldObj.playSoundAtEntity(this, LOTRBlockPlate.soundTypePlate.getBreakSound(), 1.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f);
			setDead();
		}
		for (int i = 0; i < 8; ++i) {
			double d = posX + MathHelper.randomFloatClamp(rand, -0.25f, 0.25f);
			double d1 = posY + MathHelper.randomFloatClamp(rand, -0.25f, 0.25f);
			double d2 = posZ + MathHelper.randomFloatClamp(rand, -0.25f, 0.25f);
			worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(LOTRMod.rhunFireJar) + "_0", d, d1, d2, 0.0, 0.0, 0.0);
		}
		for (int l = 0; l < 16; ++l) {
			String s = rand.nextBoolean() ? "flame" : "smoke";
			double d = posX;
			double d1 = posY;
			double d2 = posZ;
			double d3 = MathHelper.randomFloatClamp(rand, -0.1f, 0.1f);
			double d4 = MathHelper.randomFloatClamp(rand, 0.2f, 0.3f);
			double d5 = MathHelper.randomFloatClamp(rand, -0.1f, 0.1f);
			worldObj.spawnParticle(s, d, d1, d2, d3, d4, d5);
		}
	}
}
