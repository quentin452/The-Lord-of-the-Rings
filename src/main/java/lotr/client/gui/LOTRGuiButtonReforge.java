package lotr.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class LOTRGuiButtonReforge extends GuiButton {
	public int minU;
	public int minV;

	public LOTRGuiButtonReforge(int i, int x, int y, int u, int v) {
		super(i, x, y, 20, 20, "");
		minU = u;
		minV = v;
	}

	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		if (visible) {
			mc.getTextureManager().bindTexture(LOTRGuiAnvil.anvilTexture);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			field_146123_n = i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height;
			drawTexturedModalRect(xPosition, yPosition, minU + (field_146123_n ? width : 0), minV, width, height);
			mouseDragged(mc, i, j);
		}
	}
}
