package lotr.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

public abstract class LOTRGuiScreenBase extends GuiScreen {
	public static void drawFloatRect(float x0, float y0, float x1, float y1, int color) {
		float temp;
		if (x0 < x1) {
			temp = x0;
			x0 = x1;
			x1 = temp;
		}
		if (y0 < y1) {
			temp = y0;
			y0 = y1;
			y1 = temp;
		}
		float alpha = (color >> 24 & 0xFF) / 255.0f;
		float r = (color >> 16 & 0xFF) / 255.0f;
		float g = (color >> 8 & 0xFF) / 255.0f;
		float b = (color & 0xFF) / 255.0f;
		Tessellator tessellator = Tessellator.instance;
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glColor4f(r, g, b, alpha);
		tessellator.startDrawingQuads();
		tessellator.addVertex(x0, y1, 0.0);
		tessellator.addVertex(x1, y1, 0.0);
		tessellator.addVertex(x1, y0, 0.0);
		tessellator.addVertex(x0, y0, 0.0);
		tessellator.draw();
		GL11.glEnable(3553);
		GL11.glDisable(3042);
	}

	public static void drawTexturedModalRectFloat(double x, double y, double u, double v, double width, double height, int imageWidth, float z) {
		float f = 1.0f / imageWidth;
		float f1 = 1.0f / imageWidth;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x + 0.0, y + height, z, (u + 0.0) * f, (v + height) * f1);
		tessellator.addVertexWithUV(x + width, y + height, z, (u + width) * f, (v + height) * f1);
		tessellator.addVertexWithUV(x + width, y + 0.0, z, (u + width) * f, (v + 0.0) * f1);
		tessellator.addVertexWithUV(x + 0.0, y + 0.0, z, (u + 0.0) * f, (v + 0.0) * f1);
		tessellator.draw();
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	public void drawCenteredString(String s, int x, int y, int c) {
		fontRendererObj.drawString(s, x - fontRendererObj.getStringWidth(s) / 2, y, c);
	}

	@Override
	public void drawTexturedModalRect(int x, int y, int u, int v, int width, int height) {
		drawTexturedModalRect(x, y, u, v, width, height, 256);
	}

	public void drawTexturedModalRect(int x, int y, int u, int v, int width, int height, int imageWidth) {
		drawTexturedModalRectFloat(x, y, u, v, width, height, imageWidth, zLevel);
	}

	public void drawTexturedModalRectFloat(float x, float y, int u, int v, float width, float height) {
		drawTexturedModalRectFloat(x, y, u, v, width, height, 256);
	}

	public void drawTexturedModalRectFloat(float x, float y, int u, int v, float width, float height, int imageWidth) {
		drawTexturedModalRectFloat(x, y, u, v, width, height, imageWidth, zLevel);
	}

	@Override
	public void keyTyped(char c, int i) {
		if (i == 1 || i == mc.gameSettings.keyBindInventory.getKeyCode()) {
			mc.thePlayer.closeScreen();
		}
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		if (!mc.thePlayer.isEntityAlive() || mc.thePlayer.isDead) {
			mc.thePlayer.closeScreen();
		}
	}
}
