package lotr.client.render.entity;

import lotr.client.LOTRClientProxy;
import lotr.common.entity.projectile.LOTREntityGandalfFireball;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderGandalfFireball extends Render {
	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		GL11.glEnable(32826);
		bindEntityTexture(entity);
		Tessellator tessellator = Tessellator.instance;
		drawSprite(tessellator, 24 + ((LOTREntityGandalfFireball) entity).animationTick);
		GL11.glDisable(32826);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glPopMatrix();
	}

	public void drawSprite(Tessellator tessellator, int index) {
		float f = (index % 8 * 16) / 128.0f;
		float f1 = (index % 8 * 16 + 16) / 128.0f;
		float f2 = ((float) index / 8 * 16) / 128.0f;
		float f3 = ((float) index / 8 * 16 + 16) / 128.0f;
		float f4 = 1.0f;
		float f5 = 0.5f;
		float f6 = 0.25f;
		GL11.glRotatef(180.0f - renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(-renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0f, 1.0f, 0.0f);
		tessellator.setBrightness(15728880);
		tessellator.addVertexWithUV(0.0f - f5, 0.0f - f6, 0.0, f, f3);
		tessellator.addVertexWithUV(f4 - f5, 0.0f - f6, 0.0, f1, f3);
		tessellator.addVertexWithUV(f4 - f5, f4 - f6, 0.0, f1, f2);
		tessellator.addVertexWithUV(0.0f - f5, f4 - f6, 0.0, f, f2);
		tessellator.draw();
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return LOTRClientProxy.particlesTexture;
	}
}
