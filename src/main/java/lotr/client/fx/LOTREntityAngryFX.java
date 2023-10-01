package lotr.client.fx;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.util.LOTRFunctions;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityAngryFX extends EntityFX {
	public float angryScale;

	public LOTREntityAngryFX(World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
		super(world, x, y, z, 0.0, 0.0, 0.0);
		motionX = xSpeed;
		motionY = ySpeed;
		motionZ = zSpeed;
		particleScale = 7.5f;
		angryScale = 7.5f;
		particleMaxAge = 40 + rand.nextInt(20);
		particleGravity = 0.0f;
		noClip = false;
	}

	@Override
	public float getBrightness(float f) {
		return 1.0f;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float f) {
		return 15728880;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		setParticleTextureIndex(12 + particleAge / 4 % 4);
		float fade = 0.8f;
		float ageF = (float) particleAge / particleMaxAge;
		if (ageF >= fade) {
			particleAlpha = 1.0f - (ageF - fade) / (1.0f - fade);
			if (particleAlpha <= 0.0f) {
				setDead();
			}
		}
	}

	@Override
	public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
		float modScale = LOTRFunctions.normalisedCos((particleAge + f) / 12.0f);
		modScale = MathHelper.clamp_float(modScale, 0.0f, 1.0f);
		particleScale = angryScale * (0.7f + modScale * 0.3f);
		float uMin = particleTextureIndexX / 4.0f;
		float uMax = uMin + 0.25f;
		float vMin = particleTextureIndexY / 4.0f;
		float vMax = vMin + 0.25f;
		float scale = 0.125f * particleScale;
		float x = (float) (prevPosX + (posX - prevPosX) * f - interpPosX);
		float y = (float) (prevPosY + (posY - prevPosY) * f - interpPosY);
		float z = (float) (prevPosZ + (posZ - prevPosZ) * f - interpPosZ);
		tessellator.setColorRGBA_F(particleRed, particleGreen, particleBlue, particleAlpha);
		tessellator.addVertexWithUV(x - f1 * scale - f4 * scale, y - f2 * scale, z - f3 * scale - f5 * scale, uMax, vMax);
		tessellator.addVertexWithUV(x - f1 * scale + f4 * scale, y + f2 * scale, z - f3 * scale + f5 * scale, uMax, vMin);
		tessellator.addVertexWithUV(x + f1 * scale + f4 * scale, y + f2 * scale, z + f3 * scale + f5 * scale, uMin, vMin);
		tessellator.addVertexWithUV(x + f1 * scale - f4 * scale, y - f2 * scale, z + f3 * scale - f5 * scale, uMin, vMax);
	}

	@Override
	public void setParticleTextureIndex(int i) {
		particleTextureIndexX = i % 4;
		particleTextureIndexY = i / 4;
	}
}
