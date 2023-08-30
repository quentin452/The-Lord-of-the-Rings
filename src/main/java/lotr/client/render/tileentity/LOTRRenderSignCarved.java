package lotr.client.render.tileentity;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.nio.IntBuffer;
import java.util.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import lotr.common.tileentity.LOTRTileEntitySignCarved;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.resources.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;

public class LOTRRenderSignCarved extends TileEntitySpecialRenderer implements IResourceManagerReloadListener {
	public BufferedImage cachedBlockAtlasImage;
	public Map<IIcon, Integer> iconAverageColors = new HashMap<>();
	public Map<IIcon, Integer> iconContrastColors = new HashMap<>();

	public int averageIconColor(IIcon icon) {
		if (iconAverageColors.containsKey(icon)) {
			return iconAverageColors.get(icon);
		}
		if (cachedBlockAtlasImage == null) {
			cachedBlockAtlasImage = loadAndCacheBlockTextureAtlas();
		}
		int width = cachedBlockAtlasImage.getWidth();
		int height = cachedBlockAtlasImage.getHeight();
		int u0 = (int) Math.round((double) icon.getMinU() * width);
		int u1 = (int) Math.round((double) icon.getMaxU() * width);
		int v0 = (int) Math.round((double) icon.getMinV() * height);
		int v1 = (int) Math.round((double) icon.getMaxV() * height);
		int totalR = 0;
		int totalG = 0;
		int totalB = 0;
		int count = 0;
		for (int y = v0; y < v1; ++y) {
			for (int x = u0; x < u1; ++x) {
				int rgb = cachedBlockAtlasImage.getRGB(x, y);
				int r = rgb >> 16 & 0xFF;
				int g = rgb >> 8 & 0xFF;
				int b = rgb & 0xFF;
				totalR += r;
				totalG += g;
				totalB += b;
				++count;
			}
		}
		int avgR = totalR / count & 0xFF;
		int avgG = totalG / count & 0xFF;
		int avgB = totalB / count & 0xFF;
		int avgColor = 0xFF000000 | avgR << 16 | avgG << 8 | avgB;
		iconAverageColors.put(icon, avgColor);
		return avgColor;
	}

	public int calculateContrast(IIcon icon, int color) {
		Color c = new Color(color);
		float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
		float h = hsb[0];
		float s = hsb[1];
		float b = hsb[2];
		b = b > 0.6f ? b - 0.6f : b + 0.4f;
		b = MathHelper.clamp_float(b, 0.0f, 1.0f);
		return Color.HSBtoRGB(h, s * 0.5f, b);
	}

	public int getContrastingColor(IIcon icon) {
		if (iconContrastColors.containsKey(icon)) {
			return iconContrastColors.get(icon);
		}
		int color = averageIconColor(icon);
		color = calculateContrast(icon, color);
		iconContrastColors.put(icon, color);
		return color;
	}

	public int getTextColor(LOTRTileEntitySignCarved sign, float f) {
		return getContrastingColor(sign.getOnBlockIcon());
	}

	public BufferedImage loadAndCacheBlockTextureAtlas() {
		Minecraft mc = Minecraft.getMinecraft();
		mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		int mipmapLvl = 0;
		int width = GL11.glGetTexLevelParameteri(3553, mipmapLvl, 4096);
		int height = GL11.glGetTexLevelParameteri(3553, mipmapLvl, 4097);
		int pixelSize = width * height;
		BufferedImage atlasImage = new BufferedImage(width, height, 2);
		IntBuffer buffer = BufferUtils.createIntBuffer(pixelSize);
		int[] imgData = new int[pixelSize];
		GL11.glGetTexImage(3553, 0, 32993, 33639, buffer);
		buffer.get(imgData);
		atlasImage.setRGB(0, 0, width, height, imgData, 0, width);
		return atlasImage;
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		cachedBlockAtlasImage = null;
		iconAverageColors.clear();
		iconContrastColors.clear();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		LOTRTileEntitySignCarved sign = (LOTRTileEntitySignCarved) tileentity;
		int meta = tileentity.getBlockMetadata();
		float rotation = Direction.facingToDirection[meta] * 90.0f;
		float f1 = 0.6666667f;
		float f3 = 0.016666668f * f1;
		GL11.glDisable(32826);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d + 0.5f, (float) d1 + 0.75f * f1, (float) d2 + 0.5f);
		GL11.glRotatef(-rotation, 0.0f, 1.0f, 0.0f);
		GL11.glTranslatef(0.0f, -0.3125f, -0.4375f);
		GL11.glTranslatef(0.0f, 0.5f * f1, -0.09f * f1);
		GL11.glScalef(f3, -f3, f3);
		GL11.glNormal3f(0.0f, 0.0f, -1.0f * f3);
		GL11.glDepthMask(false);
		FontRenderer fontrenderer = func_147498_b();
		int color = getTextColor(sign, f);
		int signLines = sign.signText.length;
		int lineHeight = fontrenderer.FONT_HEIGHT + 1;
		int lineBase = -signLines * 5;
		if (signLines > 4) {
			lineBase = -((signLines - 1) * lineHeight) / 2;
		}
		for (int l = 0; l < signLines; ++l) {
			String s = sign.signText[l];
			if (l == sign.lineBeingEdited) {
				s = "> " + s + " <";
			}
			int lineX = -fontrenderer.getStringWidth(s) / 2;
			int lineY = lineBase + l * lineHeight;
			fontrenderer.drawString(s, lineX, lineY, color);
		}
		GL11.glDepthMask(true);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glPopMatrix();
	}
}
