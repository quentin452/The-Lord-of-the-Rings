package lotr.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class LOTRGuiButtonBanner extends GuiButton {
	public boolean activated;
	public int iconU;
	public int iconV;

	public LOTRGuiButtonBanner(int i, int x, int y, int u, int v) {
		super(i, x, y, 16, 16, "");
		iconU = u;
		iconV = v;
	}

	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		if (visible) {
			mc.getTextureManager().bindTexture(LOTRGuiBanner.bannerTexture);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			field_146123_n = i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height;
			int state = getHoverState(field_146123_n);
			drawTexturedModalRect(xPosition, yPosition, iconU + state % 2 * width, iconV + state / 2 * height, width, height);
			mouseDragged(mc, i, j);
		}
	}

	@Override
	public int getHoverState(boolean mouseover) {
		return (activated ? 0 : 2) + (mouseover ? 1 : 0);
	}
}
