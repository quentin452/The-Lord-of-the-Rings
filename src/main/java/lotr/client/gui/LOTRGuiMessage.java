package lotr.client.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import org.lwjgl.opengl.GL11;

import lotr.client.LOTRTickHandlerClient;
import lotr.common.LOTRGuiMessageTypes;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.*;

public class LOTRGuiMessage extends LOTRGuiScreenBase {
	public static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/message.png");
	public LOTRGuiMessageTypes type;
	public int xSize = 240;
	public int ySize = 160;
	public int border = 10;
	public int guiLeft;
	public int guiTop;
	public GuiButton buttonDismiss;
	public int buttonTimer = 60;

	public LOTRGuiMessage(LOTRGuiMessageTypes t) {
		type = t;
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled && button == buttonDismiss) {
			mc.thePlayer.closeScreen();
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		mc.getTextureManager().bindTexture(guiTexture);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		String msg = type.getMessage();
		int pageWidth = xSize - border * 2;
		String[] splitNewline = msg.split(Pattern.quote("\\n"));
		Collection<String> msgLines = new ArrayList<>();
		for (String line : splitNewline) {
			msgLines.addAll(fontRendererObj.listFormattedStringToWidth(line, pageWidth));
		}
		int x = guiLeft + border;
		int y = guiTop + border;
		for (String line : msgLines) {
			fontRendererObj.drawString(line, x, y, 8019267);
			y += fontRendererObj.FONT_HEIGHT;
		}
		String s = StatCollector.translateToLocal("lotr.gui.message.notDisplayedAgain");
		drawCenteredString(s, guiLeft + xSize / 2, guiTop + ySize - border / 2 - fontRendererObj.FONT_HEIGHT, 9666921);
		if (type == LOTRGuiMessageTypes.ALIGN_DRAIN) {
			int numIcons = 3;
			int iconGap = 40;
			for (int l = 0; l < numIcons; ++l) {
				int iconX = guiLeft + xSize / 2;
				iconX -= (numIcons - 1) * iconGap / 2;
				int iconY = guiTop + border + 14;
				int num = l + 1;
				LOTRTickHandlerClient.renderAlignmentDrain(mc, iconX + (l * iconGap - 8), iconY, num);
			}
		}
		if (buttonTimer > 0) {
			--buttonTimer;
		}
		buttonDismiss.enabled = buttonTimer == 0;
		super.drawScreen(i, j, f);
	}

	@Override
	public void initGui() {
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		buttonDismiss = new LOTRGuiButtonRedBook(0, guiLeft + xSize / 2 - 40, guiTop + ySize + 20, 80, 20, StatCollector.translateToLocal("lotr.gui.message.dismiss"));
		buttonList.add(buttonDismiss);
	}

	@Override
	public void keyTyped(char c, int i) {
	}
}
