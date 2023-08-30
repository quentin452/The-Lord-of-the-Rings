package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

public class LOTRRenderNPCRespawner extends Render {
	public ItemStack renderIcon;

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		IIcon icon;
		if (!Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode) {
			return;
		}
		LOTREntityNPCRespawner spawner = (LOTREntityNPCRespawner) entity;
		GL11.glPushMatrix();
		GL11.glEnable(32826);
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		float rotation = interpolateRotation(spawner.prevSpawnerSpin, spawner.spawnerSpin, f1);
		float scale = 2.0f;
		GL11.glRotatef(rotation, 0.0f, 1.0f, 0.0f);
		GL11.glTranslatef(-0.5f * scale, -spawner.height / 2.0f, 0.03125f * scale);
		GL11.glScalef(scale, scale, scale);
		if (renderIcon == null) {
			renderIcon = new ItemStack(LOTRMod.npcRespawner);
		}
		icon = renderIcon.getIconIndex();
		if (icon == null) {
			icon = ((TextureMap) Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationItemsTexture)).getAtlasSprite("missingno");
		}
		Tessellator tessellator = Tessellator.instance;
		float f2 = icon.getMinU();
		float f3 = icon.getMaxU();
		float f4 = icon.getMinV();
		float f5 = icon.getMaxV();
		bindEntityTexture(spawner);
		ItemRenderer.renderItemIn2D(tessellator, f3, f4, f2, f5, icon.getIconWidth(), icon.getIconHeight(), 0.0625f);
		GL11.glDisable(32826);
		GL11.glPopMatrix();
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return TextureMap.locationItemsTexture;
	}

	public float interpolateRotation(float prevRotation, float newRotation, float tick) {
		float interval;
		//noinspection StatementWithEmptyBody
		for (interval = newRotation - prevRotation; interval < -180.0f; interval += 360.0f) {
		}
		while (interval >= 180.0f) {
			interval -= 360.0f;
		}
		return prevRotation + tick * interval;
	}
}
