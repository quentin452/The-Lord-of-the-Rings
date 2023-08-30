package lotr.client.fx;

import java.awt.Color;

import net.minecraft.client.particle.EntitySpellParticleFX;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityRiverWaterFX extends EntitySpellParticleFX {
	public LOTREntityRiverWaterFX(World world, double d, double d1, double d2, double d3, double d4, double d5, int color) {
		super(world, d, d1, d2, d3, d4, d5);
		Color c = new Color(color);
		float[] rgb = c.getColorComponents(null);
		particleRed = MathHelper.randomFloatClamp(rand, rgb[0] - 0.3f, rgb[0] + 0.3f);
		particleGreen = MathHelper.randomFloatClamp(rand, rgb[1] - 0.3f, rgb[1] + 0.3f);
		particleBlue = MathHelper.randomFloatClamp(rand, rgb[2] - 0.3f, rgb[2] + 0.3f);
		particleRed = MathHelper.clamp_float(particleRed, 0.0f, 1.0f);
		particleGreen = MathHelper.clamp_float(particleGreen, 0.0f, 1.0f);
		particleBlue = MathHelper.clamp_float(particleBlue, 0.0f, 1.0f);
		particleScale = 0.5f + rand.nextFloat() * 0.5f;
		particleMaxAge = 20 + rand.nextInt(20);
		motionX = d3;
		motionY = d4;
		motionZ = d5;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		particleAlpha = 0.5f + 0.5f * ((float) particleAge / particleMaxAge);
	}
}
