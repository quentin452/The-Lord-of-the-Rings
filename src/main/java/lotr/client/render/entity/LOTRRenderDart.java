package lotr.client.render.entity;

import lotr.common.entity.projectile.LOTREntityDart;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderDart extends Render {
	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		LOTREntityDart dart = (LOTREntityDart) entity;
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		GL11.glRotatef(dart.prevRotationYaw + (dart.rotationYaw - dart.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(dart.prevRotationPitch + (dart.rotationPitch - dart.prevRotationPitch) * f1, 0.0f, 0.0f, 1.0f);
		GL11.glEnable(32826);
		float f2 = dart.shake - f1;
		if (f2 > 0.0f) {
			float f3 = -MathHelper.sin(f2 * 3.0f) * f2;
			GL11.glRotatef(f3, 0.0f, 0.0f, 1.0f);
		}
		GL11.glRotatef(-135.0f, 0.0f, 0.0f, 1.0f);
		GL11.glTranslatef(0.0f, -1.0f, 0.0f);
		float scale = 0.6f;
		GL11.glScalef(scale, scale, scale);
		GL11.glTranslatef(0.0f, 0.8f, 0.0f);
		ItemStack itemstack = dart.getProjectileItem();
		IIcon icon = itemstack.getIconIndex();
		Tessellator tessellator = Tessellator.instance;
		float minU = icon.getMinU();
		float maxU = icon.getMaxU();
		float minV = icon.getMinV();
		float maxV = icon.getMaxV();
		int width = icon.getIconWidth();
		int height = icon.getIconWidth();
		bindTexture(getEntityTexture(dart));
		ItemRenderer.renderItemIn2D(tessellator, maxU, minV, minU, maxV, width, height, 0.0625f);
		GL11.glDisable(32826);
		GL11.glPopMatrix();
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return TextureMap.locationItemsTexture;
	}
}
