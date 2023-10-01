package lotr.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class LOTRGuiButtonFactionsMap extends GuiButton {
	public LOTRGuiButtonFactionsMap(int i, int x, int y) {
		super(i, x, y, 8, 8, "");
	}

	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		if (visible) {
			mc.getTextureManager().bindTexture(LOTRGuiFactions.factionsTexture);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			field_146123_n = i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height;
			drawTexturedModalRect(xPosition, yPosition, 17 + (field_146123_n ? width : 0), 142, width, height);
			mouseDragged(mc, i, j);
		}
	}
}
