package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;

public class LOTRGuiButtonRedBook extends GuiButton {
	public LOTRGuiButtonRedBook(int i, int x, int y, int w, int h, String s) {
		super(i, x, y, w, h, s);
	}

	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		if (visible) {
			FontRenderer fontrenderer = mc.fontRenderer;
			mc.getTextureManager().bindTexture(LOTRGuiRedBook.guiTexture);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			field_146123_n = i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height;
			int k = getHoverState(field_146123_n);
			Gui.func_146110_a(xPosition, yPosition, 170.0f, 256 + k * 20, width, height, 512.0f, 512.0f);
			GL11.glEnable(3042);
			GL11.glBlendFunc(770, 771);
			Gui.func_146110_a(xPosition, yPosition, 170.0f, 316.0f, width / 2, height, 512.0f, 512.0f);
			Gui.func_146110_a(xPosition + width / 2, yPosition, 370 - (float) width / 2, 316.0f, width / 2, height, 512.0f, 512.0f);
			mouseDragged(mc, i, j);
			int color = 8019267;
			if (!enabled) {
				color = 5521198;
			} else if (field_146123_n) {
				color = 8019267;
			}
			fontrenderer.drawString(displayString, xPosition + width / 2 - fontrenderer.getStringWidth(displayString) / 2, yPosition + (height - 8) / 2, color);
		}
	}
}
