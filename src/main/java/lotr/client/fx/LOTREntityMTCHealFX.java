package lotr.client.fx;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntitySpellParticleFX;
import net.minecraft.world.World;

public class LOTREntityMTCHealFX extends EntitySpellParticleFX {
	public int baseTextureIndex;

	public LOTREntityMTCHealFX(World world, double d, double d1, double d2, double d3, double d4, double d5) {
		super(world, d, d1, d2, d3, d4, d5);
		particleRed = 1.0f;
		particleGreen = 0.3f;
		particleBlue = 0.3f;
		particleScale *= 3.0f;
		particleMaxAge = 30;
		motionX = d3;
		motionY = d4;
		motionZ = d5;
		renderDistanceWeight = 10.0;
		noClip = true;
		setBaseSpellTextureIndex(128);
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
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		if (particleAge++ >= particleMaxAge) {
			setDead();
		}
		setParticleTextureIndex(baseTextureIndex + 7 - particleAge * 8 / particleMaxAge);
		moveEntity(motionX, motionY, motionZ);
		particleAlpha = 0.5f + 0.5f * ((float) particleAge / particleMaxAge);
	}

	@Override
	public void setBaseSpellTextureIndex(int i) {
		super.setBaseSpellTextureIndex(i);
		baseTextureIndex = i;
	}
}
