package lotr.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRGuiBannerButton extends GuiButton {
	public static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/banner_edit.png");

	public LOTRGuiBannerButton(int i, int j, int k) {
		super(i, j, k, 7, 7, "");
	}

	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		if (visible) {
			mc.getTextureManager().bindTexture(guiTexture);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			boolean over = i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height;
			int k = 226 + id * width;
			int l = getHoverState(over) * width;
			drawTexturedModalRect(xPosition, yPosition, k, l, width, height);
		}
	}
}
