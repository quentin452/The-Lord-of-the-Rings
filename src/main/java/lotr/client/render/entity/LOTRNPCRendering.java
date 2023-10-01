package lotr.client.render.entity;

import lotr.client.LOTRSpeechClient;
import lotr.client.LOTRTickHandlerClient;
import lotr.common.LOTRConfig;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRHiredNPCInfo;
import lotr.common.item.LOTRItemRedBook;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;

public class LOTRNPCRendering {
	public static RenderItem itemRenderer = new RenderItem();

	public static float calcSpeechDisplacement(LOTREntityNPC npc) {
		LOTRSpeechClient.TimedSpeech timedSpeech = LOTRSpeechClient.getSpeechFor(npc);
		if (timedSpeech != null) {
			FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
			int fontHeight = fr.FONT_HEIGHT;
			int numLines = fr.listFormattedStringToWidth(timedSpeech.getSpeech(), 150).size();
			float f = fontHeight * (3 + numLines);
			return f * 0.015f;
		}
		return 0.0f;
	}

	public static void renderAllNPCSpeeches(Minecraft mc, World world, float f) {
		GL11.glPushMatrix();
		RenderHelper.enableStandardItemLighting();
		GL11.glAlphaFunc(516, 0.01f);
		double d0 = RenderManager.renderPosX;
		double d1 = RenderManager.renderPosY;
		double d2 = RenderManager.renderPosZ;
		for (Object obj : world.loadedEntityList) {
			Entity entity = (Entity) obj;
			boolean inRange = entity.isInRangeToRender3d(d0, d1, d2);
			if (!(entity instanceof LOTREntityNPC) || !inRange) {
				continue;
			}
			LOTREntityNPC npc = (LOTREntityNPC) entity;
			if (!npc.isEntityAlive()) {
				LOTRSpeechClient.removeSpeech(npc);
				continue;
			}
			LOTRSpeechClient.TimedSpeech timedSpeech = LOTRSpeechClient.getSpeechFor(npc);
			if (timedSpeech == null) {
				continue;
			}
			double d3 = npc.lastTickPosX + (npc.posX - npc.lastTickPosX) * f;
			double d4 = npc.lastTickPosY + (npc.posY - npc.lastTickPosY) * f;
			double d5 = npc.lastTickPosZ + (npc.posZ - npc.lastTickPosZ) * f;
			renderSpeech(npc, timedSpeech.getSpeech(), timedSpeech.getAge(), d3 - d0, d4 - d1, d5 - d2);
		}
		GL11.glAlphaFunc(516, 0.1f);
		RenderHelper.disableStandardItemLighting();
		mc.entityRenderer.disableLightmap(f);
		GL11.glPopMatrix();
	}

	public static void renderHealthBar(EntityLivingBase entity, double d, double d1, double d2, int[] colors, int[] mountColors) {
		Minecraft mc = Minecraft.getMinecraft();
		WorldClient world = mc.theWorld;
		world.theProfiler.startSection("renderHealthBar");
		RenderManager renderManager = RenderManager.instance;
		double distance = RendererLivingEntity.NAME_TAG_RANGE;
		double distanceSq = entity.getDistanceSqToEntity(renderManager.livingPlayer);
		if (distanceSq <= distance * distance) {
			float f1 = 1.6f;
			float f2 = 0.016666666f * f1;
			GL11.glPushMatrix();
			GL11.glTranslatef((float) d, (float) d1 + entity.height + 0.7f, (float) d2);
			GL11.glNormal3f(0.0f, 1.0f, 0.0f);
			GL11.glRotatef(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
			GL11.glScalef(-f2, -f2, f2);
			GL11.glDisable(2896);
			GL11.glDepthMask(false);
			GL11.glDisable(2929);
			GL11.glEnable(3042);
			GL11.glBlendFunc(770, 771);
			Tessellator tessellator = Tessellator.instance;
			GL11.glDisable(3553);
			int colorHealth = colors[0];
			int colorBase = colors[1];
			tessellator.startDrawingQuads();
			tessellator.setColorOpaque_I(0);
			tessellator.addVertex(-19.5, 18.5, 0.0);
			tessellator.addVertex(-19.5, 21.0, 0.0);
			tessellator.addVertex(19.5, 21.0, 0.0);
			tessellator.addVertex(19.5, 18.5, 0.0);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setColorOpaque_I(colorBase);
			tessellator.addVertex(-19.0, 19.0, 0.0);
			tessellator.addVertex(-19.0, 20.5, 0.0);
			tessellator.addVertex(19.0, 20.5, 0.0);
			tessellator.addVertex(19.0, 19.0, 0.0);
			tessellator.draw();
			double healthRemaining = entity.getHealth() / entity.getMaxHealth();
			if (healthRemaining < 0.0) {
				healthRemaining = 0.0;
			}
			tessellator.startDrawingQuads();
			tessellator.setColorOpaque_I(colorHealth);
			tessellator.addVertex(-19.0, 19.0, 0.0);
			tessellator.addVertex(-19.0, 20.5, 0.0);
			tessellator.addVertex(-19.0 + 38.0 * healthRemaining, 20.5, 0.0);
			tessellator.addVertex(-19.0 + 38.0 * healthRemaining, 19.0, 0.0);
			tessellator.draw();
			if (mountColors != null && entity.ridingEntity instanceof EntityLivingBase) {
				EntityLivingBase mount = (EntityLivingBase) entity.ridingEntity;
				int mountColorHealth = mountColors[0];
				int mountColorBase = mountColors[1];
				tessellator.startDrawingQuads();
				tessellator.setColorOpaque_I(0);
				tessellator.addVertex(-19.5, 23.5, 0.0);
				tessellator.addVertex(-19.5, 26.0, 0.0);
				tessellator.addVertex(19.5, 26.0, 0.0);
				tessellator.addVertex(19.5, 23.5, 0.0);
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setColorOpaque_I(mountColorBase);
				tessellator.addVertex(-19.0, 24.0, 0.0);
				tessellator.addVertex(-19.0, 25.5, 0.0);
				tessellator.addVertex(19.0, 25.5, 0.0);
				tessellator.addVertex(19.0, 24.0, 0.0);
				tessellator.draw();
				double mountHealthRemaining = mount.getHealth() / mount.getMaxHealth();
				if (mountHealthRemaining < 0.0) {
					mountHealthRemaining = 0.0;
				}
				tessellator.startDrawingQuads();
				tessellator.setColorOpaque_I(mountColorHealth);
				tessellator.addVertex(-19.0, 24.0, 0.0);
				tessellator.addVertex(-19.0, 25.5, 0.0);
				tessellator.addVertex(-19.0 + 38.0 * mountHealthRemaining, 25.5, 0.0);
				tessellator.addVertex(-19.0 + 38.0 * mountHealthRemaining, 24.0, 0.0);
				tessellator.draw();
			}
			GL11.glEnable(3553);
			GL11.glEnable(2929);
			GL11.glDepthMask(true);
			GL11.glEnable(2896);
			GL11.glDisable(3042);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			GL11.glPopMatrix();
		}
		world.theProfiler.endSection();
	}

	public static void renderHiredIcon(EntityLivingBase entity, double d, double d1, double d2) {
		if (!LOTRConfig.hiredUnitIcons || entity.riddenByEntity instanceof LOTREntityNPC) {
			return;
		}
		if (entity instanceof LOTREntityNPC && LOTRSpeechClient.hasSpeech((LOTREntityNPC) entity)) {
			d1 += calcSpeechDisplacement((LOTREntityNPC) entity);
		}
		Minecraft mc = Minecraft.getMinecraft();
		TextureManager textureManager = mc.getTextureManager();
		FontRenderer fr = mc.fontRenderer;
		RenderManager renderManager = RenderManager.instance;
		EntityLivingBase viewer = renderManager.livingPlayer;
		WorldClient world = mc.theWorld;
		world.theProfiler.startSection("renderHiredIcon");
		double distance = RendererLivingEntity.NAME_TAG_RANGE;
		double distanceSq = entity.getDistanceSqToEntity(viewer);
		if (distanceSq <= distance * distance) {
			ItemStack hiredIcon = entity.getHeldItem();
			int displayLevel = -1;
			String squadron = null;
			if (entity instanceof LOTREntityNPC) {
				String sq;
				LOTREntityNPC npc = (LOTREntityNPC) entity;
				LOTRHiredNPCInfo hiredInfo = npc.hiredNPCInfo;
				if (hiredInfo.getTask().displayXpLevel) {
					displayLevel = hiredInfo.xpLevel;
				}
				if (!StringUtils.isNullOrEmpty(sq = hiredInfo.getSquadron())) {
					squadron = sq;
				}
			}
			GL11.glPushMatrix();
			GL11.glTranslatef((float) d, (float) d1 + entity.height, (float) d2);
			GL11.glNormal3f(0.0f, 1.0f, 0.0f);
			GL11.glRotatef(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
			GL11.glDisable(2896);
			GL11.glDepthMask(false);
			GL11.glDisable(2929);
			GL11.glEnable(3042);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			if (squadron != null) {
				GL11.glTranslatef(0.0f, 0.3f, 0.0f);
				GL11.glPushMatrix();
				Tessellator tessellator = Tessellator.instance;
				int halfWidth = fr.getStringWidth(squadron) / 2;
				float boxScale = 0.015f;
				GL11.glScalef(-boxScale, -boxScale, boxScale);
				GL11.glDisable(3553);
				tessellator.startDrawingQuads();
				tessellator.setColorRGBA_F(0.0f, 0.0f, 0.0f, 0.25f);
				tessellator.addVertex(-halfWidth - 1, -9.0, 0.0);
				tessellator.addVertex(-halfWidth - 1, 0.0, 0.0);
				tessellator.addVertex(halfWidth + 1, 0.0, 0.0);
				tessellator.addVertex(halfWidth + 1, -9.0, 0.0);
				tessellator.draw();
				GL11.glEnable(3553);
				fr.drawString(squadron, -halfWidth, -8, 553648127);
				GL11.glEnable(2929);
				GL11.glDepthMask(true);
				fr.drawString(squadron, -halfWidth, -8, -1);
				GL11.glDisable(2929);
				GL11.glDepthMask(false);
				GL11.glPopMatrix();
			}
			if (viewer.isSneaking() && displayLevel >= 0) {
				GL11.glPushMatrix();
				GL11.glTranslatef(0.0f, 0.5f, 0.0f);
				GL11.glScalef(-1.0f, -1.0f, 1.0f);
				float textScale = 0.03f;
				GL11.glScalef(textScale, textScale, textScale);
				String s = String.valueOf(displayLevel);
				LOTRTickHandlerClient.drawBorderedText(fr, -fr.getStringWidth(s) / 2, 0, s, 16733440, 1.0f);
				GL11.glPopMatrix();
			} else if (hiredIcon != null) {
				GL11.glPushMatrix();
				GL11.glTranslatef(0.0f, 0.5f, 0.0f);
				GL11.glScalef(-1.0f, -1.0f, 1.0f);
				float itemScale = 0.03f;
				GL11.glScalef(itemScale, itemScale, itemScale);
				textureManager.bindTexture(TextureMap.locationItemsTexture);
				itemRenderer.renderIcon(-8, -8, hiredIcon.getIconIndex(), 16, 16);
				GL11.glPopMatrix();
			}
			GL11.glDisable(3042);
			GL11.glEnable(2929);
			GL11.glDepthMask(true);
			GL11.glEnable(2896);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			GL11.glPopMatrix();
		}
		world.theProfiler.endSection();
	}

	public static void renderNPCHealthBar(EntityLivingBase entity, double d, double d1, double d2) {
		if (!LOTRConfig.hiredUnitHealthBars || entity.riddenByEntity instanceof LOTREntityNPC) {
			return;
		}
		if (entity instanceof LOTREntityNPC && LOTRSpeechClient.hasSpeech((LOTREntityNPC) entity)) {
			d1 += calcSpeechDisplacement((LOTREntityNPC) entity);
		}
		renderHealthBar(entity, d, d1, d2, new int[]{5888860, 12006707}, new int[]{6079225, 12006707});
	}

	public static void renderQuestBook(LOTREntityNPC npc, double d, double d1, double d2) {
		Minecraft mc = Minecraft.getMinecraft();
		WorldClient world = mc.theWorld;
		world.theProfiler.startSection("renderMiniquestBook");
		float distance = mc.renderViewEntity.getDistanceToEntity(npc);
		boolean aboveHead = distance <= LOTRMiniQuest.RENDER_HEAD_DISTANCE;
		TextureManager textureManager = mc.getTextureManager();
		RenderManager renderManager = RenderManager.instance;
		EntityClientPlayerMP entityplayer = mc.thePlayer;
		if (!LOTRLevelData.getData(entityplayer).getMiniQuestsForEntity(npc, true).isEmpty() && !LOTRSpeechClient.hasSpeech(npc)) {
			ItemStack questBook = new ItemStack(LOTRMod.redBook);
			IIcon icon = questBook.getIconIndex();
			if (icon == null) {
				icon = ((TextureMap) textureManager.getTexture(TextureMap.locationItemsTexture)).getAtlasSprite("missingno");
			}
			Tessellator tessellator = Tessellator.instance;
			float minU = icon.getMinU();
			float maxU = icon.getMaxU();
			float minV = icon.getMinV();
			float maxV = icon.getMaxV();
			if (aboveHead) {
				float age = npc.ticksExisted + LOTRTickHandlerClient.renderTick;
				float rotation = age % 360.0f;
				GL11.glPushMatrix();
				GL11.glEnable(32826);
				GL11.glDisable(2896);
				GL11.glTranslatef((float) d, (float) d1 + npc.height + 1.3f, (float) d2);
				float scale = 1.0f;
				GL11.glRotatef(rotation * 6.0f, 0.0f, 1.0f, 0.0f);
				GL11.glTranslatef(-0.5f * scale, -0.5f * scale, 0.03125f * scale);
				GL11.glScalef(scale, scale, scale);
				textureManager.bindTexture(TextureMap.locationItemsTexture);
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				ItemRenderer.renderItemIn2D(tessellator, maxU, minV, minU, maxV, icon.getIconWidth(), icon.getIconHeight(), 0.0625f);
				GL11.glEnable(2896);
				GL11.glDisable(32826);
			} else {
				float scale = distance / (float) LOTRMiniQuest.RENDER_HEAD_DISTANCE;
				scale = (float) Math.pow(scale, 1.1);
				float alpha = (float) Math.pow(scale, -0.4);
				GL11.glPushMatrix();
				GL11.glTranslatef((float) d, (float) d1 + npc.height + 1.3f, (float) d2);
				GL11.glNormal3f(0.0f, 1.0f, 0.0f);
				GL11.glRotatef(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
				GL11.glRotatef(renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
				GL11.glScalef(scale, scale, scale);
				GL11.glDisable(2896);
				GL11.glDepthMask(false);
				GL11.glDisable(2929);
				GL11.glEnable(3042);
				OpenGlHelper.glBlendFunc(770, 771, 1, 0);
				textureManager.bindTexture(TextureMap.locationItemsTexture);
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
				GL11.glScalef(-1.0f, -1.0f, 1.0f);
				float itemScale = 0.0625f;
				GL11.glScalef(itemScale, itemScale, itemScale);
				textureManager.bindTexture(TextureMap.locationItemsTexture);
				itemRenderer.renderIcon(-8, -8, icon, 16, 16);
				GL11.glDisable(3042);
				GL11.glEnable(2929);
				GL11.glDepthMask(true);
				GL11.glEnable(2896);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			}
			GL11.glPopMatrix();
		}
		world.theProfiler.endSection();
	}

	public static void renderQuestOffer(LOTREntityNPC npc, double d, double d1, double d2) {
		Minecraft mc = Minecraft.getMinecraft();
		WorldClient world = mc.theWorld;
		world.theProfiler.startSection("renderMiniquestoffer");
		if (npc.isEntityAlive() && npc.questInfo.clientIsOffering && !LOTRSpeechClient.hasSpeech(npc)) {
			EntityClientPlayerMP entityplayer = mc.thePlayer;
			float distance = mc.renderViewEntity.getDistanceToEntity(npc);
			if (distance <= 16.0 && LOTRLevelData.getData(entityplayer).getMiniQuestsForEntity(npc, true).isEmpty()) {
				TextureManager textureManager = mc.getTextureManager();
				RenderManager renderManager = RenderManager.instance;
				IIcon icon = LOTRItemRedBook.questOfferIcon;
				icon.getMinU();
				icon.getMaxU();
				icon.getMinV();
				icon.getMaxV();
				float scale = 0.75f;
				float alpha = 1.0f;
				int questColor = npc.questInfo.clientOfferColor;
				float[] questRGB = new Color(questColor).getColorComponents(null);
				GL11.glPushMatrix();
				GL11.glTranslatef((float) d, (float) d1 + npc.height + 1.0f, (float) d2);
				GL11.glNormal3f(0.0f, 1.0f, 0.0f);
				GL11.glRotatef(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
				GL11.glRotatef(renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
				GL11.glScalef(scale, scale, scale);
				GL11.glDisable(2896);
				GL11.glEnable(3042);
				OpenGlHelper.glBlendFunc(770, 771, 1, 0);
				textureManager.bindTexture(TextureMap.locationItemsTexture);
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
				GL11.glScalef(-1.0f, -1.0f, 1.0f);
				float itemScale = 0.0625f;
				GL11.glScalef(itemScale, itemScale, itemScale);
				textureManager.bindTexture(TextureMap.locationItemsTexture);
				GL11.glColor4f(questRGB[0], questRGB[1], questRGB[2], alpha);
				itemRenderer.renderIcon(-8, -8, icon, 16, 16);
				GL11.glDisable(3042);
				GL11.glEnable(2896);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				GL11.glPopMatrix();
			}
		}
		world.theProfiler.endSection();
	}

	public static void renderSpeech(EntityLivingBase entity, String speech, float speechAge, double d, double d1, double d2) {
		Minecraft mc = Minecraft.getMinecraft();
		WorldClient world = mc.theWorld;
		world.theProfiler.startSection("renderNPCSpeech");
		RenderManager renderManager = RenderManager.instance;
		FontRenderer fr = mc.fontRenderer;
		double distance = RendererLivingEntity.NAME_TAG_RANGE;
		double distanceSq = entity.getDistanceSqToEntity(renderManager.livingPlayer);
		if (distanceSq <= distance * distance) {
			String name = EnumChatFormatting.YELLOW + entity.getCommandSenderName();
			int fontHeight = fr.FONT_HEIGHT;
			List<String> speechLines = fr.listFormattedStringToWidth(speech, 150);
			float alpha = 0.8f;
			if (speechAge < 0.1f) {
				alpha *= speechAge / 0.1f;
			}
			int intAlpha = (int) (alpha * 255.0f);
			GL11.glPushMatrix();
			GL11.glTranslatef((float) d, (float) d1 + entity.height + 0.3f, (float) d2);
			GL11.glNormal3f(0.0f, 1.0f, 0.0f);
			GL11.glRotatef(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
			GL11.glDisable(2896);
			GL11.glDepthMask(false);
			GL11.glDisable(2929);
			GL11.glEnable(3042);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			Tessellator tessellator = Tessellator.instance;
			float scale = 0.015f;
			GL11.glScalef(-scale, -scale, scale);
			GL11.glTranslatef(0.0f, -fontHeight * (3 + speechLines.size()), 0.0f);
			GL11.glDisable(3553);
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_F(0.0f, 0.0f, 0.0f, 0.25f * alpha);
			int halfNameW = fr.getStringWidth(name) / 2;
			tessellator.addVertex(-halfNameW - 1, 0.0, 0.0);
			tessellator.addVertex(-halfNameW - 1, fontHeight, 0.0);
			tessellator.addVertex(halfNameW + 1, fontHeight, 0.0);
			tessellator.addVertex(halfNameW + 1, 0.0, 0.0);
			tessellator.draw();
			GL11.glEnable(3553);
			fr.drawString(name, -halfNameW, 0, intAlpha << 24 | 0xFFFFFF);
			GL11.glTranslatef(0.0f, fontHeight, 0.0f);
			for (String line : speechLines) {
				GL11.glTranslatef(0.0f, fontHeight, 0.0f);
				GL11.glDisable(3553);
				tessellator.startDrawingQuads();
				tessellator.setColorRGBA_F(0.0f, 0.0f, 0.0f, 0.25f * alpha);
				int halfLineW = fr.getStringWidth(line) / 2;
				tessellator.addVertex(-halfLineW - 1, 0.0, 0.0);
				tessellator.addVertex(-halfLineW - 1, fontHeight, 0.0);
				tessellator.addVertex(halfLineW + 1, fontHeight, 0.0);
				tessellator.addVertex(halfLineW + 1, 0.0, 0.0);
				tessellator.draw();
				GL11.glEnable(3553);
				fr.drawString(line, -halfLineW, 0, intAlpha << 24 | 0xFFFFFF);
			}
			GL11.glDisable(3042);
			GL11.glEnable(2929);
			GL11.glDepthMask(true);
			GL11.glEnable(2896);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			GL11.glPopMatrix();
		}
		world.theProfiler.endSection();
	}
}
