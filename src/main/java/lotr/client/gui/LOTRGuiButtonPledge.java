package lotr.client.gui;

import lotr.client.LOTRClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;
import java.util.List;

public class LOTRGuiButtonPledge extends GuiButton {
	public LOTRGuiFactions parentGUI;
	public boolean isBroken;
	public List<String> displayLines;

	public LOTRGuiButtonPledge(LOTRGuiFactions gui, int i, int x, int y, String s) {
		super(i, x, y, 32, 32, s);
		parentGUI = gui;
	}

	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		if (visible) {
			mc.getTextureManager().bindTexture(LOTRClientProxy.alignmentTexture);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			field_146123_n = i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height;
			int state = getHoverState(field_146123_n);
			drawTexturedModalRect(xPosition, yPosition, state * width, 180, width, height);
			mouseDragged(mc, i, j);
			if (func_146115_a() && displayLines != null) {
				float z = zLevel;
				parentGUI.drawButtonHoveringText(displayLines, i, j);
				GL11.glDisable(2896);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				zLevel = z;
			}
		}
	}

	@Override
	public int getHoverState(boolean flag) {
		if (isBroken) {
			return flag ? 4 : 3;
		}
		if (!enabled) {
			return 0;
		}
		return flag ? 2 : 1;
	}

	public void setDisplayLines(String... s) {
		displayLines = s == null ? null : Arrays.asList(s);
	}
}
