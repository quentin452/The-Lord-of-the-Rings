package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;

import lotr.client.LOTRClientProxy;
import lotr.client.model.LOTRModelSmokeShip;
import lotr.common.entity.projectile.LOTREntitySmokeRing;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderSmokeRing extends Render {
	public ModelBase magicShipModel = new LOTRModelSmokeShip();

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		Tessellator tessellator = Tessellator.instance;
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		GL11.glEnable(32826);
		GL11.glEnable(2977);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		LOTREntitySmokeRing smokeRing = (LOTREntitySmokeRing) entity;
		float age = smokeRing.getRenderSmokeAge(f1);
		float opacity = 1.0f - age;
		int colour = smokeRing.getSmokeColour();
		if (colour == 16) {
			GL11.glDisable(3553);
			GL11.glDisable(2884);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, opacity * 0.75f);
			GL11.glScalef(0.3f, -0.3f, 0.3f);
			GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * f1, 0.0f, 0.0f, -1.0f);
			GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
			magicShipModel.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
			GL11.glEnable(2884);
			GL11.glEnable(3553);
		} else {
			float[] colours = EntitySheep.fleeceColorTable[colour];
			GL11.glColor4f(colours[0], colours[1], colours[2], opacity);
			bindEntityTexture(entity);
			float smokeSize = 0.1f + 0.9f * age;
			GL11.glScalef(smokeSize, smokeSize, smokeSize);
			drawSprite(tessellator, 0);
		}
		GL11.glDisable(3042);
		GL11.glDisable(32826);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glPopMatrix();
	}

	public void drawSprite(Tessellator tessellator, int index) {
		float var3 = (index % 16 * 16) / 128.0f;
		float var4 = (index % 16 * 16 + 16) / 128.0f;
		float var5 = ((float) index / 16 * 16) / 128.0f;
		float var6 = ((float) index / 16 * 16 + 16) / 128.0f;
		float var7 = 1.0f;
		float var8 = 0.5f;
		float var9 = 0.25f;
		GL11.glRotatef(180.0f - renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(-renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0f, 1.0f, 0.0f);
		tessellator.addVertexWithUV(0.0f - var8, 0.0f - var9, 0.0, var3, var6);
		tessellator.addVertexWithUV(var7 - var8, 0.0f - var9, 0.0, var4, var6);
		tessellator.addVertexWithUV(var7 - var8, var7 - var9, 0.0, var4, var5);
		tessellator.addVertexWithUV(0.0f - var8, var7 - var9, 0.0, var3, var5);
		tessellator.draw();
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return LOTRClientProxy.particlesTexture;
	}
}
