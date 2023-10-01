package lotr.client.fx;

import net.minecraft.client.particle.EntityFlameFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.awt.*;

public class LOTREntityElvenGlowFX extends EntityFlameFX {
	public LOTREntityElvenGlowFX(World world, double d, double d1, double d2, double d3, double d4, double d5) {
		super(world, d, d1, d2, d3, d4, d5);
		particleRed = 0.15f;
		particleGreen = 0.9f;
		particleBlue = 1.0f;
		particleMaxAge *= 3;
	}

	@Override
	public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
		float minU = 0.25f;
		float maxU = minU + 0.25f;
		float minV = 0.125f;
		float maxV = minV + 0.25f;
		float var12 = 0.25f + 0.002f * MathHelper.sin((particleAge + f - 1.0f) * 0.25f * 3.1415927f);
		particleAlpha = 0.9f - (particleAge + f - 1.0f) * 0.02f;
		float var13 = (float) (prevPosX + (posX - prevPosX) * f - interpPosX);
		float var14 = (float) (prevPosY + (posY - prevPosY) * f - interpPosY);
		float var15 = (float) (prevPosZ + (posZ - prevPosZ) * f - interpPosZ);
		tessellator.setColorRGBA_F(particleRed, particleGreen, particleBlue, particleAlpha);
		tessellator.addVertexWithUV(var13 - f1 * var12 - f4 * var12, var14 - f2 * var12, var15 - f3 * var12 - f5 * var12, maxU, maxV);
		tessellator.addVertexWithUV(var13 - f1 * var12 + f4 * var12, var14 + f2 * var12, var15 - f3 * var12 + f5 * var12, maxU, minV);
		tessellator.addVertexWithUV(var13 + f1 * var12 + f4 * var12, var14 + f2 * var12, var15 + f3 * var12 + f5 * var12, minU, minV);
		tessellator.addVertexWithUV(var13 + f1 * var12 - f4 * var12, var14 - f2 * var12, var15 + f3 * var12 - f5 * var12, minU, maxV);
	}

	public LOTREntityElvenGlowFX setElvenGlowColor(int color) {
		float[] rgb = new Color(color).getColorComponents(null);
		particleRed = rgb[0];
		particleGreen = rgb[1];
		particleBlue = rgb[2];
		return this;
	}
}
