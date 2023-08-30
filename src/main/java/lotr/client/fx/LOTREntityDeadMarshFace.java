package lotr.client.fx;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityDeadMarshFace extends EntityFX {
	public float faceAlpha;

	public LOTREntityDeadMarshFace(World world, double d, double d1, double d2) {
		super(world, d, d1, d2, 0.0, 0.0, 0.0);
		particleMaxAge = 120 + rand.nextInt(120);
		rotationYaw = world.rand.nextFloat() * 360.0f;
		rotationPitch = -60.0f + world.rand.nextFloat() * 120.0f;
	}

	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		++particleAge;
		faceAlpha = MathHelper.sin((float) particleAge / particleMaxAge * 3.1415927f);
		if (particleAge > particleMaxAge) {
			setDead();
		}
	}
}
