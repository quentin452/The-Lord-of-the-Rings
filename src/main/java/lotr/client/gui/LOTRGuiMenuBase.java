package lotr.client.gui;

import lotr.client.LOTRKeyHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.StatCollector;

public abstract class LOTRGuiMenuBase extends LOTRGuiScreenBase {
	public static RenderItem renderItem = new RenderItem();
	public int xSize = 200;
	public int ySize = 256;
	public int guiLeft;
	public int guiTop;
	public GuiButton buttonMenuReturn;

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled && button == buttonMenuReturn) {
			mc.displayGuiScreen(new LOTRGuiMenu());
		}
		super.actionPerformed(button);
	}

	@Override
	public void initGui() {
		super.initGui();
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		int buttonH = 20;
		int buttonGap = 35;
		int minGap = 10;
		buttonMenuReturn = new LOTRGuiButtonLeftRight(1000, true, 0, guiTop + (ySize + buttonH) / 4, StatCollector.translateToLocal("lotr.gui.menuButton"));
		buttonList.add(buttonMenuReturn);
		buttonMenuReturn.xPosition = Math.min(buttonGap, guiLeft - minGap - buttonMenuReturn.width);
	}

	@Override
	public void keyTyped(char c, int i) {
		if (i == LOTRKeyHandler.keyBindingMenu.getKeyCode()) {
			mc.displayGuiScreen(new LOTRGuiMenu());
			return;
		}
		super.keyTyped(c, i);
	}
}
