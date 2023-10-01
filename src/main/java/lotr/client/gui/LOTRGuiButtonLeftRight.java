package lotr.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRGuiButtonLeftRight extends GuiButton {
	public static ResourceLocation texture = new ResourceLocation("lotr:gui/widgets.png");
	public boolean leftOrRight;

	public LOTRGuiButtonLeftRight(int i, boolean flag, int j, int k, String s) {
		super(i, j, k, 120, 20, s);
		leftOrRight = flag;
	}

	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		if (visible) {
			FontRenderer fontrenderer = mc.fontRenderer;
			mc.getTextureManager().bindTexture(texture);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			field_146123_n = i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height;
			int k = getHoverState(field_146123_n);
			drawTexturedModalRect(xPosition, yPosition, leftOrRight ? 0 : 136, k * 20, width, height);
			mouseDragged(mc, i, j);
			int l = 14737632;
			if (!enabled) {
				l = -6250336;
			} else if (field_146123_n) {
				l = 16777120;
			}
			if (leftOrRight) {
				drawCenteredString(fontrenderer, displayString, xPosition + 67, yPosition + (height - 8) / 2, l);
			} else {
				drawCenteredString(fontrenderer, displayString, xPosition + width - 67, yPosition + (height - 8) / 2, l);
			}
		}
	}
}
