package lotr.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class LOTRGuiButtonFsOption extends GuiButton {
	public int iconU;
	public int iconV;

	public LOTRGuiButtonFsOption(int i, int x, int y, int u, int v, String s) {
		super(i, x, y, 16, 16, s);
		iconU = u;
		iconV = v;
	}

	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		if (visible) {
			mc.getTextureManager().bindTexture(LOTRGuiFellowships.iconsTextures);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			field_146123_n = i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height;
			drawTexturedModalRect(xPosition, yPosition, iconU, iconV + (field_146123_n ? height : 0), width, height);
			mouseDragged(mc, i, j);
		}
	}

	public void setIconUV(int u, int v) {
		iconU = u;
		iconV = v;
	}
}
