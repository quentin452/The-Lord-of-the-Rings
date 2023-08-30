package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.FMLLog;
import lotr.common.entity.projectile.LOTREntityThrowingAxe;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

public class LOTRRenderThrowingAxe extends Render {
	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		LOTREntityThrowingAxe axe = (LOTREntityThrowingAxe) entity;
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		if (!axe.inGround) {
			GL11.glTranslatef(0.0f, 0.5f, 0.0f);
		}
		GL11.glRotatef(axe.prevRotationYaw + (axe.rotationYaw - axe.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
		if (axe.inGround) {
			GL11.glRotatef(-90.0f, 0.0f, 0.0f, 1.0f);
			GL11.glTranslatef(0.0f, 0.75f, 0.0f);
		} else {
			GL11.glRotatef(axe.rotationPitch + 45.0f * f1, 0.0f, 0.0f, -1.0f);
		}
		GL11.glEnable(32826);
		float f2 = axe.shake - f1;
		if (f2 > 0.0f) {
			float f3 = -MathHelper.sin(f2 * 3.0f) * f2;
			GL11.glRotatef(f3, 0.0f, 0.0f, 1.0f);
		}
		GL11.glRotatef(-135.0f, 0.0f, 0.0f, 1.0f);
		ItemStack axeItem = axe.getProjectileItem();
		IIcon icon = axeItem.getIconIndex();
		if (icon == null) {
			FMLLog.severe("Error rendering throwing axe: no icon for " + axeItem);
			GL11.glPopMatrix();
			return;
		}
		bindEntityTexture(entity);
		Tessellator tessellator = Tessellator.instance;
		ItemRenderer.renderItemIn2D(tessellator, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625f);
		GL11.glDisable(32826);
		GL11.glPopMatrix();
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return TextureMap.locationItemsTexture;
	}
}
