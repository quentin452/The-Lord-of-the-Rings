package lotr.client.fx;

import cpw.mods.fml.relauncher.*;
import net.minecraft.client.particle.EntitySpellParticleFX;
import net.minecraft.world.World;

public class LOTREntityMorgulPortalFX extends EntitySpellParticleFX {
	public LOTREntityMorgulPortalFX(World world, double d, double d1, double d2, double d3, double d4, double d5) {
		super(world, d, d1, d2, d3, d4, d5);
		particleRed = 0.2f;
		particleGreen = 0.8f;
		particleBlue = 0.4f;
		particleScale = 0.5f + rand.nextFloat() * 0.5f;
		particleMaxAge = 20 + rand.nextInt(20);
		motionX = d3;
		motionY = d4;
		motionZ = d5;
	}

	@Override
	public float getBrightness(float f) {
		return 1.0f;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getBrightnessForRender(float f) {
		return 15728880;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		particleAlpha = 0.5f + 0.5f * ((float) particleAge / particleMaxAge);
		motionX *= 1.1;
		motionZ *= 1.1;
	}
}
