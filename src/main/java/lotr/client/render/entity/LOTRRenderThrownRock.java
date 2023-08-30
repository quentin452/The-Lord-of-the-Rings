package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;

import lotr.common.LOTRMod;
import lotr.common.entity.projectile.LOTREntityThrownRock;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderThrownRock extends Render {
	public RenderBlocks blockRenderer = new RenderBlocks();

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		GL11.glEnable(32826);
		GL11.glDisable(2884);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		GL11.glRotatef(f, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * f1, 1.0f, 0.0f, 0.0f);
		int i = entity.getBrightnessForRender(f1);
		int j = i % 65536;
		int k = i / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		bindEntityTexture(entity);
		if (((LOTREntityThrownRock) entity).getSpawnsTroll()) {
			blockRenderer.renderBlockAsItem(LOTRMod.trollTotem, 0, 1.0f);
		} else {
			blockRenderer.renderBlockAsItem(Blocks.stone, 0, 1.0f);
		}
		GL11.glPopMatrix();
		GL11.glEnable(2884);
		GL11.glDisable(32826);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return TextureMap.locationBlocksTexture;
	}
}
