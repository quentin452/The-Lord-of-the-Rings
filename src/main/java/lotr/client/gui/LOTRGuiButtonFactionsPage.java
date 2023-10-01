package lotr.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class LOTRGuiButtonFactionsPage extends GuiButton {
	public boolean leftOrRight;

	public LOTRGuiButtonFactionsPage(int i, int x, int y, boolean flag) {
		super(i, x, y, 16, 16, "");
		leftOrRight = flag;
	}

	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		if (visible) {
			mc.getTextureManager().bindTexture(LOTRGuiFactions.factionsTexture);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			field_146123_n = i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height;
			int k = getHoverState(field_146123_n);
			drawTexturedModalRect(xPosition, yPosition, k * 16, leftOrRight ? 176 : 160, width, height);
			mouseDragged(mc, i, j);
			if (enabled) {
				FontRenderer fr = mc.fontRenderer;
				int stringBorder = 0;
				int stringY = yPosition + height / 2 - fr.FONT_HEIGHT / 2;
				if (leftOrRight) {
					String s = "->";
					fr.drawString(s, xPosition - stringBorder - fr.getStringWidth(s), stringY, 0);
				} else {
					String s = "<-";
					fr.drawString(s, xPosition + width + stringBorder, stringY, 0);
				}
			}
		}
	}
}
