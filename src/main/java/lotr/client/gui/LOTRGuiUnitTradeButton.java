package lotr.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRGuiUnitTradeButton extends GuiButton {
	public static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/npc/unit_trade_buttons.png");

	public LOTRGuiUnitTradeButton(int i, int j, int k, int width, int height) {
		super(i, j, k, width, height, "");
	}

	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		if (visible) {
			mc.getTextureManager().bindTexture(guiTexture);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			boolean flag = i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height;
			int k = id * 19;
			int l = 0;
			if (!enabled) {
				l += width * 2;
			} else if (flag) {
				l += width;
			}
			drawTexturedModalRect(xPosition, yPosition, l, k, width, height);
		}
	}
}
