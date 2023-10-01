package lotr.client.fx;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFireworkOverlayFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class LOTREntityGandalfFireballExplodeFX extends EntityFireworkOverlayFX {
	public LOTREntityGandalfFireballExplodeFX(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
		particleRed = 0.33f;
		particleGreen = 1.0f;
		particleBlue = 1.0f;
		particleMaxAge = 32;
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
	public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
		float var8 = 0.25f;
		float var9 = var8 + 0.25f;
		float var10 = 0.125f;
		float var11 = var10 + 0.25f;
		float var12 = 16.0f - particleAge * 0.2f;
		particleAlpha = 0.9f - (particleAge + f - 1.0f) * 0.15f;
		float var13 = (float) (prevPosX + (posX - prevPosX) * f - interpPosX);
		float var14 = (float) (prevPosY + (posY - prevPosY) * f - interpPosY);
		float var15 = (float) (prevPosZ + (posZ - prevPosZ) * f - interpPosZ);
		tessellator.setColorRGBA_F(particleRed, particleGreen, particleBlue, particleAlpha);
		tessellator.addVertexWithUV(var13 - f1 * var12 - f4 * var12, var14 - f2 * var12, var15 - f3 * var12 - f5 * var12, var9, var11);
		tessellator.addVertexWithUV(var13 - f1 * var12 + f4 * var12, var14 + f2 * var12, var15 - f3 * var12 + f5 * var12, var9, var10);
		tessellator.addVertexWithUV(var13 + f1 * var12 + f4 * var12, var14 + f2 * var12, var15 + f3 * var12 + f5 * var12, var8, var10);
		tessellator.addVertexWithUV(var13 + f1 * var12 - f4 * var12, var14 - f2 * var12, var15 + f3 * var12 - f5 * var12, var8, var11);
	}
}
