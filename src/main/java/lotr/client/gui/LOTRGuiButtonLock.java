package lotr.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRGuiButtonLock extends GuiButton {
	public static ResourceLocation texture = new ResourceLocation("lotr:gui/widgets.png");

	public LOTRGuiButtonLock(int i, int j, int k) {
		super(i, j, k, 20, 20, "");
	}

	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		if (visible) {
			mc.getTextureManager().bindTexture(texture);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			field_146123_n = i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height;
			int k = getHoverState(field_146123_n);
			drawTexturedModalRect(xPosition, yPosition, 0, 196 + k * 20, width, height);
			mouseDragged(mc, i, j);
		}
	}
}
