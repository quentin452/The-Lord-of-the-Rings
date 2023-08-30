package lotr.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.util.StatCollector;

public class LOTRGuiButtonOptions extends GuiButton {
	public String baseDisplayString;

	public LOTRGuiButtonOptions(int i, int j, int k, int l, int i1, String s) {
		super(i, j, k, l, i1, s);
		baseDisplayString = s;
	}

	public void drawTooltip(Minecraft mc, int i, int j) {
		if (enabled && func_146115_a()) {
			String s = getDescription();
			int border = 3;
			int stringWidth = 200;
			int stringHeight = mc.fontRenderer.listFormattedStringToWidth(s, stringWidth).size() * mc.fontRenderer.FONT_HEIGHT;
			int offset = 10;
			Gui.drawRect(i += offset, j += offset, i + stringWidth + border * 2, j + stringHeight + border * 2, -1073741824);
			mc.fontRenderer.drawSplitString(s, i + border, j + border, stringWidth, 16777215);
		}
	}

	public String getDescription() {
		return StatCollector.translateToLocal(baseDisplayString + ".desc.on") + "\n\n" + StatCollector.translateToLocal(baseDisplayString + ".desc.off");
	}

	public void setState(boolean flag) {
		setState(flag ? StatCollector.translateToLocal("lotr.gui.button.on") : StatCollector.translateToLocal("lotr.gui.button.off"));
	}

	public void setState(String s) {
		displayString = StatCollector.translateToLocal(baseDisplayString) + ": " + s;
	}
}
