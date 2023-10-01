package lotr.client.render.entity;

import lotr.common.entity.projectile.LOTREntityCrossbowBolt;
import lotr.common.item.LOTRItemCrossbowBolt;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderCrossbowBolt extends Render {
	public static ResourceLocation boltTexture = new ResourceLocation("lotr:item/crossbowBolt.png");

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		LOTREntityCrossbowBolt bolt = (LOTREntityCrossbowBolt) entity;
		ItemStack boltItem = bolt.getProjectileItem();
		bindEntityTexture(bolt);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		GL11.glRotatef(bolt.prevRotationYaw + (bolt.rotationYaw - bolt.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(bolt.prevRotationPitch + (bolt.rotationPitch - bolt.prevRotationPitch) * f1, 0.0f, 0.0f, 1.0f);
		Tessellator tessellator = Tessellator.instance;
		int yOffset = 0;
		if (boltItem != null && boltItem.getItem() instanceof LOTRItemCrossbowBolt && ((LOTRItemCrossbowBolt) boltItem.getItem()).isPoisoned) {
			yOffset = 1;
		}
		float f2 = 0.0f;
		float f3 = 0.5f;
		float f4 = (yOffset * 10) / 32.0f;
		float f5 = (5 + yOffset * 10) / 32.0f;
		float f6 = 0.0f;
		float f7 = 0.15625f;
		float f8 = (5 + yOffset * 10) / 32.0f;
		float f9 = (10 + yOffset * 10) / 32.0f;
		float f10 = 0.05625f;
		GL11.glEnable(32826);
		float f11 = bolt.shake - f1;
		if (f11 > 0.0f) {
			float f12 = -MathHelper.sin(f11 * 3.0f) * f11;
			GL11.glRotatef(f12, 0.0f, 0.0f, 1.0f);
		}
		GL11.glRotatef(45.0f, 1.0f, 0.0f, 0.0f);
		GL11.glScalef(f10, f10, f10);
		GL11.glTranslatef(-4.0f, 0.0f, 0.0f);
		GL11.glNormal3f(f10, 0.0f, 0.0f);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-7.0, -2.0, -2.0, f6, f8);
		tessellator.addVertexWithUV(-7.0, -2.0, 2.0, f7, f8);
		tessellator.addVertexWithUV(-7.0, 2.0, 2.0, f7, f9);
		tessellator.addVertexWithUV(-7.0, 2.0, -2.0, f6, f9);
		tessellator.draw();
		GL11.glNormal3f(-f10, 0.0f, 0.0f);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-7.0, 2.0, -2.0, f6, f8);
		tessellator.addVertexWithUV(-7.0, 2.0, 2.0, f7, f8);
		tessellator.addVertexWithUV(-7.0, -2.0, 2.0, f7, f9);
		tessellator.addVertexWithUV(-7.0, -2.0, -2.0, f6, f9);
		tessellator.draw();
		for (int i = 0; i < 4; ++i) {
			GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
			GL11.glNormal3f(0.0f, 0.0f, f10);
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(-8.0, -2.0, 0.0, f2, f4);
			tessellator.addVertexWithUV(8.0, -2.0, 0.0, f3, f4);
			tessellator.addVertexWithUV(8.0, 2.0, 0.0, f3, f5);
			tessellator.addVertexWithUV(-8.0, 2.0, 0.0, f2, f5);
			tessellator.draw();
		}
		GL11.glDisable(32826);
		GL11.glPopMatrix();
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return boltTexture;
	}
}
