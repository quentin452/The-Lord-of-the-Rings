package lotr.client.render.entity;

import lotr.client.model.LOTRModelTroll;
import lotr.common.entity.item.LOTREntityStoneTroll;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderStoneTroll extends Render {
	public static ResourceLocation texture = new ResourceLocation("lotr:mob/troll/stone.png");
	public static LOTRModelTroll model = new LOTRModelTroll();
	public static LOTRModelTroll shirtModel = new LOTRModelTroll(1.0f, 0);
	public static LOTRModelTroll trousersModel = new LOTRModelTroll(0.75f, 1);

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		GL11.glPushMatrix();
		GL11.glDisable(2884);
		GL11.glTranslatef((float) d, (float) d1 + 1.5f, (float) d2);
		bindEntityTexture(entity);
		GL11.glScalef(-1.0f, -1.0f, 1.0f);
		GL11.glRotatef(180.0f - entity.rotationYaw, 0.0f, 1.0f, 0.0f);
		model.render(entity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
		bindTexture(LOTRRenderTroll.trollOutfits[((LOTREntityStoneTroll) entity).getTrollOutfit()]);
		shirtModel.render(entity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
		trousersModel.render(entity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
		GL11.glEnable(2884);
		GL11.glPopMatrix();
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}
}
