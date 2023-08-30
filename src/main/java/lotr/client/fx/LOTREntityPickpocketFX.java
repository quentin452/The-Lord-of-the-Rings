package lotr.client.fx;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class LOTREntityPickpocketFX extends EntityFX {
	public float bounciness;
	public double motionBeforeGround;

	public LOTREntityPickpocketFX(World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
		super(world, x, y, z, 0.0, 0.0, 0.0);
		motionX = xSpeed;
		motionY = ySpeed;
		motionZ = zSpeed;
		particleGravity = 1.0f;
		particleMaxAge = 30 + rand.nextInt(40);
		noClip = false;
		bounciness = 1.0f;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		updatePickpocketIcon();
		if (onGround) {
			motionY = motionBeforeGround * -bounciness;
		} else {
			motionBeforeGround = motionY;
		}
	}

	public void updatePickpocketIcon() {
		setParticleTextureIndex(160 + particleAge / 2 % 8);
	}
}
