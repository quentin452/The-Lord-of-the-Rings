package lotr.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class LOTRGuiButtonCoinExchange extends GuiButton {
	public LOTRGuiButtonCoinExchange(int i, int j, int k) {
		super(i, j, k, 32, 17, "");
	}

	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		if (visible) {
			mc.getTextureManager().bindTexture(LOTRGuiCoinExchange.guiTexture);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			field_146123_n = i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height;
			int k = getHoverState(field_146123_n);
			int u = 176 + id * width;
			int v = k * height;
			drawTexturedModalRect(xPosition, yPosition, u, v, width, height);
			mouseDragged(mc, i, j);
		}
	}
}
