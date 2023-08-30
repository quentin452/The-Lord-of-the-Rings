package lotr.client.fx;

import net.minecraft.client.particle.EntityExplodeFX;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityWaveFX extends EntityExplodeFX {
	public float initScale;
	public float finalScale;
	public double origMotionX;
	public double origMotionZ;

	public LOTREntityWaveFX(World world, double d, double d1, double d2, double d3, double d4, double d5) {
		super(world, d, d1, d2, d3, d4, d5);
		origMotionX = motionX = d3;
		motionY = d4;
		origMotionZ = motionZ = d5;
		initScale = particleScale = 1.0f + rand.nextFloat() * 4.0f;
		finalScale = initScale * MathHelper.randomFloatClamp(rand, 1.2f, 2.0f);
		particleMaxAge = MathHelper.getRandomIntegerInRange(rand, 60, 80);
		particleAlpha = 0.0f;
	}

	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		if (particleAge++ >= particleMaxAge) {
			setDead();
		}
		if (particleAlpha < 1.0f) {
			particleAlpha += 0.02f;
			particleAlpha = Math.min(particleAlpha, 1.0f);
		}
		particleScale = initScale + (float) particleAge / particleMaxAge * (finalScale - initScale);
		setParticleTextureIndex((particleMaxAge - particleAge) % 8);
		handleWaterMovement();
		if (inWater) {
			motionY += MathHelper.randomFloatClamp(rand, 0.04f, 0.1f);
			motionX = origMotionX;
			motionZ = origMotionZ;
		} else {
			motionY -= 0.02;
			motionX *= 0.98;
			motionZ *= 0.98;
		}
		moveEntity(motionX, motionY, motionZ);
		motionY *= 0.995;
	}
}
