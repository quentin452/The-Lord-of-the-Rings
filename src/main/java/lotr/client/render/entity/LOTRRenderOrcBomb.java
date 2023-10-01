package lotr.client.render.entity;

import lotr.common.LOTRMod;
import lotr.common.entity.item.LOTREntityOrcBomb;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderOrcBomb extends Render {
	public RenderBlocks blockRenderer = new RenderBlocks();

	public LOTRRenderOrcBomb() {
		shadowSize = 0.5f;
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		LOTREntityOrcBomb bomb = (LOTREntityOrcBomb) entity;
		renderBomb(bomb, d, d1, d2, f1, bomb.orcBombFuse, bomb.getBombStrengthLevel(), 1.0f, entity.getBrightness(f1));
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return TextureMap.locationBlocksTexture;
	}

	public void renderBomb(Entity entity, double d, double d1, double d2, float ticks, int fuse, int strengthLevel, float bombScale, float brightness) {
		float f2;
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		GL11.glScalef(bombScale, bombScale, bombScale);
		if (fuse - ticks + 1.0f < 10.0f) {
			f2 = 1.0f - (fuse - ticks + 1.0f) / 10.0f;
			if (f2 < 0.0f) {
				f2 = 0.0f;
			}
			if (f2 > 1.0f) {
				f2 = 1.0f;
			}
			f2 *= f2;
			f2 *= f2;
			float scale = 1.0f + f2 * 0.3f;
			GL11.glScalef(scale, scale, scale);
		}
		f2 = (1.0f - (fuse - ticks + 1.0f) / 100.0f) * 0.8f;
		bindEntityTexture(entity);
		blockRenderer.renderBlockAsItem(LOTRMod.orcBomb, strengthLevel, brightness);
		if (fuse / 5 % 2 == 0) {
			GL11.glDisable(3553);
			GL11.glDisable(2896);
			GL11.glEnable(3042);
			GL11.glBlendFunc(770, 772);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, f2);
			blockRenderer.renderBlockAsItem(LOTRMod.orcBomb, strengthLevel, 1.0f);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			GL11.glDisable(3042);
			GL11.glEnable(2896);
			GL11.glEnable(3553);
		}
		GL11.glPopMatrix();
	}
}
