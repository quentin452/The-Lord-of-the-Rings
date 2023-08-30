package lotr.client;

import org.lwjgl.opengl.GL11;

import lotr.common.item.LOTRWeaponStats;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

public class LOTRAttackTiming {
	public static Minecraft mc = Minecraft.getMinecraft();
	public static ResourceLocation meterTexture = new ResourceLocation("lotr:gui/attackMeter.png");
	public static RenderItem itemRenderer = new RenderItem();
	public static int attackTime;
	public static int prevAttackTime;
	public static int fullAttackTime;
	public static ItemStack attackItem;
	public static int lastCheckTick = -1;

	public static void doAttackTiming() {
		int currentTick = LOTRTickHandlerClient.clientTick;
		if (lastCheckTick == -1) {
			lastCheckTick = currentTick;
		} else if (lastCheckTick == currentTick) {
			return;
		}
		if (mc.thePlayer == null) {
			reset();
		} else {
			KeyBinding attackKey = mc.gameSettings.keyBindAttack;
			boolean pressed = attackKey.isPressed();
			if (pressed) {
				KeyBinding.onTick(attackKey.getKeyCode());
			}
			if (pressed && mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && mc.objectMouseOver.entityHit instanceof EntityLivingBase) {
				if (attackTime > 0) {
					//noinspection StatementWithEmptyBody
					while (attackKey.isPressed()) {
					}
				} else {
					ItemStack itemstack = mc.thePlayer.getHeldItem();
					attackTime = fullAttackTime = LOTRWeaponStats.getAttackTimePlayer(itemstack);
					attackItem = itemstack;
				}
				lastCheckTick = currentTick;
			}
		}
	}

	public static void renderAttackMeter(ScaledResolution resolution, float partialTicks) {
		if (fullAttackTime > 0) {
			float attackTimeF = prevAttackTime + (attackTime - prevAttackTime) * partialTicks;
			float meterAmount = 1.0f - attackTimeF / fullAttackTime;
			int minX = resolution.getScaledWidth() / 2 + 120;
			int maxX = resolution.getScaledWidth() - 20;
			int maxY = resolution.getScaledHeight() - 10;
			int minY = maxY - 10;
			double lerpX = minX + (maxX - minX) * meterAmount;
			Tessellator tessellator = Tessellator.instance;
			mc.getTextureManager().bindTexture(meterTexture);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			double minU = 0.0;
			double maxU = 1.0;
			double minV = 0.0;
			double maxV = 0.0625;
			double lerpU = minU + (maxU - minU) * meterAmount;
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(minX, minY, 0.0, minU, minV);
			tessellator.addVertexWithUV(minX, maxY, 0.0, minU, maxV);
			tessellator.addVertexWithUV(maxX, maxY, 0.0, maxU, maxV);
			tessellator.addVertexWithUV(maxX, minY, 0.0, maxU, minV);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(lerpX, minY, 0.0, lerpU, minV + maxV);
			tessellator.addVertexWithUV(lerpX, maxY, 0.0, lerpU, maxV + maxV);
			tessellator.addVertexWithUV(maxX, maxY, 0.0, maxU, maxV + maxV);
			tessellator.addVertexWithUV(maxX, minY, 0.0, maxU, minV + maxV);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(minX, minY, 0.0, minU, minV + maxV * 2.0);
			tessellator.addVertexWithUV(minX, maxY, 0.0, minU, maxV + maxV * 2.0);
			tessellator.addVertexWithUV(maxX, maxY, 0.0, maxU, maxV + maxV * 2.0);
			tessellator.addVertexWithUV(maxX, minY, 0.0, maxU, minV + maxV * 2.0);
			tessellator.draw();
			if (attackItem != null) {
				RenderHelper.enableGUIStandardItemLighting();
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				GL11.glEnable(32826);
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				int iconX = (minX + maxX) / 2 - 8;
				int iconY = (minY + maxY) / 2 - 8;
				itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), attackItem, iconX, iconY);
				RenderHelper.disableStandardItemLighting();
			}
		}
	}

	public static void reset() {
		attackTime = 0;
		prevAttackTime = 0;
		fullAttackTime = 0;
		attackItem = null;
	}

	public static void update() {
		prevAttackTime = attackTime;
		if (attackTime > 0) {
			--attackTime;
		} else {
			reset();
		}
	}
}
