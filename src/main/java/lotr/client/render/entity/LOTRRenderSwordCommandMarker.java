package lotr.client.render.entity;

import lotr.common.LOTRMod;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class LOTRRenderSwordCommandMarker extends Render {
	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		GL11.glPushMatrix();
		GL11.glEnable(32826);
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		float rotation = -renderManager.livingPlayer.rotationYaw;
		GL11.glRotatef(rotation, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(135.0f, 0.0f, 0.0f, 1.0f);
		float scale = 1.2f;
		GL11.glTranslatef(-0.75f * scale, 0.0f, 0.03125f * scale);
		GL11.glScalef(scale, scale, scale);
		ItemStack item = new ItemStack(LOTRMod.commandSword);
		GL11.glTranslatef(0.9375f, 0.0625f, 0.0f);
		GL11.glRotatef(-335.0f, 0.0f, 0.0f, 1.0f);
		GL11.glRotatef(-50.0f, 0.0f, 1.0f, 0.0f);
		renderManager.itemRenderer.renderItem(renderManager.livingPlayer, item, 0, IItemRenderer.ItemRenderType.EQUIPPED);
		GL11.glDisable(32826);
		GL11.glPopMatrix();
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return TextureMap.locationItemsTexture;
	}
}
