package lotr.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRGuiButtonShieldsArrows extends GuiButton {
	public static ResourceLocation texture = new ResourceLocation("lotr:gui/widgets.png");
	public boolean leftOrRight;

	public LOTRGuiButtonShieldsArrows(int i, boolean flag, int j, int k) {
		super(i, j, k, 20, 20, "");
		leftOrRight = flag;
	}

	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		if (visible) {
			mc.getTextureManager().bindTexture(texture);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			field_146123_n = i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height;
			int k = getHoverState(field_146123_n);
			drawTexturedModalRect(xPosition, yPosition, leftOrRight ? 0 : 20, 60 + k * 20, width, height);
		}
	}
}
