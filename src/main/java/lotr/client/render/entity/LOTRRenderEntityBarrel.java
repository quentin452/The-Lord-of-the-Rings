package lotr.client.render.entity;

import lotr.common.LOTRMod;
import lotr.common.entity.item.LOTREntityBarrel;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderEntityBarrel extends Render {
	public ItemStack barrelItem = new ItemStack(LOTRMod.barrel);

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		LOTREntityBarrel barrel = (LOTREntityBarrel) entity;
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d, (float) d1 + 0.5f, (float) d2);
		GL11.glRotatef(180.0f - f, 0.0f, 1.0f, 0.0f);
		float f2 = barrel.getTimeSinceHit() - f1;
		float f3 = barrel.getDamageTaken() - f1;
		if (f3 < 0.0f) {
			f3 = 0.0f;
		}
		if (f2 > 0.0f) {
			GL11.glRotatef(MathHelper.sin(f2) * f2 * f3 / 10.0f * barrel.getForwardDirection(), 1.0f, 0.0f, 0.0f);
		}
		bindEntityTexture(barrel);
		GL11.glScalef(1.5f, 1.5f, 1.5f);
		renderManager.itemRenderer.renderItem(renderManager.livingPlayer, barrelItem, 0);
		GL11.glPopMatrix();
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return TextureMap.locationBlocksTexture;
	}
}
