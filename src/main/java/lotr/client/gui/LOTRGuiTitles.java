package lotr.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.client.LOTRReflectionClient;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRTitle;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketSelectTitle;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;
import java.util.*;

public class LOTRGuiTitles extends LOTRGuiMenuBase {
	public LOTRTitle.PlayerTitle currentTitle;
	public List<LOTRTitle> displayedTitles = new ArrayList<>();
	public Map<LOTRTitle, Pair<Boolean, Pair<Integer, Integer>>> displayedTitleInfo = new HashMap<>();
	public LOTRTitle selectedTitle;
	public EnumChatFormatting selectedColor = EnumChatFormatting.WHITE;
	public int colorBoxWidth = 8;
	public int colorBoxGap = 4;
	public Map<EnumChatFormatting, Pair<Integer, Integer>> displayedColorBoxes = new EnumMap<>(EnumChatFormatting.class);
	public GuiButton selectButton;
	public GuiButton removeButton;
	public float currentScroll;
	public boolean isScrolling;
	public boolean wasMouseDown;
	public int scrollBarWidth = 11;
	public int scrollBarHeight = 144;
	public int scrollBarX = 197 - (scrollBarWidth - 1) / 2;
	public int scrollBarY = 30;
	public int scrollWidgetWidth = 11;
	public int scrollWidgetHeight = 8;

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button == selectButton && (currentTitle == null || selectedTitle != currentTitle.getTitle() || selectedColor != currentTitle.getColor())) {
				IMessage packet = new LOTRPacketSelectTitle(new LOTRTitle.PlayerTitle(selectedTitle, selectedColor));
				LOTRPacketHandler.networkWrapper.sendToServer(packet);
			} else if (button == removeButton) {
				IMessage packet = new LOTRPacketSelectTitle(null);
				LOTRPacketHandler.networkWrapper.sendToServer(packet);
			} else {
				super.actionPerformed(button);
			}
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		setupScrollBar(i, j);
		String s = StatCollector.translateToLocal("lotr.gui.titles.title");
		drawCenteredString(s, guiLeft + xSize / 2, guiTop - 30, 16777215);
		String titleName = currentTitle == null ? StatCollector.translateToLocal("lotr.gui.titles.currentTitle.none") : currentTitle.getTitle().getDisplayName(mc.thePlayer);
		EnumChatFormatting currentColor = currentTitle == null ? EnumChatFormatting.WHITE : currentTitle.getColor();
		titleName = currentColor + titleName + EnumChatFormatting.RESET;
		drawCenteredString(StatCollector.translateToLocalFormatted("lotr.gui.titles.currentTitle", titleName), guiLeft + xSize / 2, guiTop, 16777215);
		displayedTitleInfo.clear();
		int titleX = guiLeft + xSize / 2;
		int titleY = guiTop + 30;
		int yIncrement = 12;
		drawVerticalLine(titleX - 70, titleY - 1, titleY + yIncrement * 12, -1711276033);
		drawVerticalLine(titleX + 70 - 1, titleY - 1, titleY + yIncrement * 12, -1711276033);
		int size = displayedTitles.size();
		int min = Math.round(currentScroll * (size - 12));
		int max = 11 + Math.round(currentScroll * (size - 12));
		min = Math.max(min, 0);
		max = Math.min(max, size - 1);
		for (int index = min; index <= max; ++index) {
			String name;
			boolean isCurrentTitle;
			boolean mouseOver;
			LOTRTitle title = displayedTitles.get(index);
			isCurrentTitle = currentTitle != null && currentTitle.getTitle() == title;
			if (title != null) {
				name = title.getDisplayName(mc.thePlayer);
				if (isCurrentTitle) {
					name = "[" + name + "]";
					name = currentTitle.getColor() + name;
				}
			} else {
				name = "---";
			}
			int nameWidth = fontRendererObj.getStringWidth(name);
			int nameHeight = mc.fontRenderer.FONT_HEIGHT;
			int nameXMin = titleX - nameWidth / 2;
			int nameXMax = titleX + nameWidth / 2;
			int nameYMin = titleY;
			int nameYMax = titleY + nameHeight;
			mouseOver = i >= nameXMin && i < nameXMax && j >= nameYMin && j < nameYMax;
			if (title != null) {
				displayedTitleInfo.put(title, Pair.of(mouseOver, Pair.of(titleX, titleY)));
			}
			int textColor = title != null ? title.canPlayerUse(mc.thePlayer) ? mouseOver ? 16777120 : 16777215 : mouseOver ? 12303291 : 7829367 : 7829367;
			drawCenteredString(name, titleX, titleY, textColor);
			titleY += yIncrement;
		}
		displayedColorBoxes.clear();
		if (selectedTitle != null) {
			String title = selectedColor + selectedTitle.getDisplayName(mc.thePlayer);
			drawCenteredString(title, guiLeft + xSize / 2, guiTop + 185, 16777215);
			Collection<EnumChatFormatting> colorCodes = new ArrayList<>();
			for (EnumChatFormatting ecf : EnumChatFormatting.values()) {
				if (!ecf.isColor()) {
					continue;
				}
				colorCodes.add(ecf);
			}
			int colorX = guiLeft + xSize / 2 - (colorBoxWidth * colorCodes.size() + colorBoxGap * (colorCodes.size() - 1)) / 2;
			int colorY = guiTop + 200;
			for (EnumChatFormatting code : colorCodes) {
				int color = LOTRReflectionClient.getFormattingColor(code);
				float[] rgb = new Color(color).getColorComponents(null);
				GL11.glColor4f(rgb[0], rgb[1], rgb[2], 1.0f);
				boolean mouseOver = i >= colorX && i < colorX + colorBoxWidth && j >= colorY && j < colorY + colorBoxWidth;
				GL11.glDisable(3553);
				drawTexturedModalRect(colorX, colorY + (mouseOver ? -1 : 0), 0, 0, colorBoxWidth, colorBoxWidth);
				GL11.glEnable(3553);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				displayedColorBoxes.put(code, Pair.of(colorX, colorY));
				colorX += colorBoxWidth + colorBoxGap;
			}
		}
		if (displayedTitles.size() > 12) {
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			int scroll = (int) (currentScroll * (scrollBarHeight - scrollWidgetHeight));
			int x1 = guiLeft + scrollBarX;
			int y1 = guiTop + scrollBarY + scroll;
			int x2 = x1 + scrollWidgetWidth;
			int y2 = y1 + scrollWidgetHeight;
			Gui.drawRect(x1, y1, x2, y2, -1426063361);
		}
		selectButton.enabled = selectedTitle != null;
		removeButton.enabled = currentTitle != null;
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		super.drawScreen(i, j, f);
		for (Map.Entry<LOTRTitle, Pair<Boolean, Pair<Integer, Integer>>> entry : displayedTitleInfo.entrySet()) {
			LOTRTitle title = entry.getKey();
			String desc = title.getDescription(mc.thePlayer);
			boolean mouseOver = entry.getValue().getLeft();
			if (!mouseOver) {
				continue;
			}
			int stringWidth = 200;
			List titleLines = fontRendererObj.listFormattedStringToWidth(desc, stringWidth);
			int offset = 10;
			int x = i + offset;
			int y = j + offset;
			func_146283_a(titleLines, x, y);
			GL11.glDisable(2896);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		}
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		int i = Mouse.getEventDWheel();
		if (i != 0) {
			i = Integer.signum(i);
			int j = displayedTitles.size() - 12;
			currentScroll -= (float) i / j;
			currentScroll = MathHelper.clamp_float(currentScroll, 0.0f, 1.0f);
		}
	}

	@Override
	public void initGui() {
		xSize = 256;
		super.initGui();
		selectButton = new GuiButton(0, guiLeft + xSize / 2 - 10 - 80, guiTop + 220, 80, 20, StatCollector.translateToLocal("lotr.gui.titles.select"));
		buttonList.add(selectButton);
		removeButton = new GuiButton(1, guiLeft + xSize / 2 + 10, guiTop + 220, 80, 20, StatCollector.translateToLocal("lotr.gui.titles.remove"));
		buttonList.add(removeButton);
		updateScreen();
	}

	@Override
	public void mouseClicked(int i, int j, int mouse) {
		if (mouse == 0) {
			for (Map.Entry<LOTRTitle, Pair<Boolean, Pair<Integer, Integer>>> entry : displayedTitleInfo.entrySet()) {
				LOTRTitle title = entry.getKey();
				boolean mouseOver = entry.getValue().getLeft();
				if (!mouseOver || !title.canPlayerUse(mc.thePlayer)) {
					continue;
				}
				selectedTitle = title;
				selectedColor = EnumChatFormatting.WHITE;
				return;
			}
			if (!displayedColorBoxes.isEmpty()) {
				for (Map.Entry<EnumChatFormatting, Pair<Integer, Integer>> entry : displayedColorBoxes.entrySet()) {
					EnumChatFormatting color = entry.getKey();
					int colorX = (Integer) ((Pair<?, ?>) entry.getValue()).getLeft();
					int colorY = (Integer) ((Pair<?, ?>) entry.getValue()).getRight();
					if (i >= colorX && i < colorX + colorBoxWidth && j >= colorY && j < colorY + colorBoxWidth) {
						selectedColor = color;
						break;
					}
				}
			}
		}
		super.mouseClicked(i, j, mouse);
	}

	public void setupScrollBar(int i, int j) {
		boolean isMouseDown = Mouse.isButtonDown(0);
		int i1 = guiLeft + scrollBarX;
		int j1 = guiTop + scrollBarY;
		int i2 = i1 + scrollBarWidth;
		int j2 = j1 + scrollBarHeight;
		if (!wasMouseDown && isMouseDown && i >= i1 && j >= j1 && i < i2 && j < j2) {
			isScrolling = true;
		}
		if (!isMouseDown) {
			isScrolling = false;
		}
		wasMouseDown = isMouseDown;
		if (isScrolling) {
			currentScroll = (j - j1 - scrollWidgetHeight / 2.0f) / ((float) (j2 - j1) - scrollWidgetHeight);
			currentScroll = MathHelper.clamp_float(currentScroll, 0.0f, 1.0f);
		}
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		currentTitle = LOTRLevelData.getData(mc.thePlayer).getPlayerTitle();
		displayedTitles.clear();
		List<LOTRTitle> availableTitles = new ArrayList<>();
		List<LOTRTitle> unavailableTitles = new ArrayList<>();
		for (LOTRTitle title : LOTRTitle.allTitles) {
			if (title.canPlayerUse(mc.thePlayer)) {
				availableTitles.add(title);
				continue;
			}
			if (!title.canDisplay(mc.thePlayer)) {
				continue;
			}
			unavailableTitles.add(title);
		}
		Comparator<LOTRTitle> sorter = LOTRTitle.createTitleSorter(mc.thePlayer);
		availableTitles.sort(sorter);
		unavailableTitles.sort(sorter);
		displayedTitles.addAll(availableTitles);
		displayedTitles.add(null);
		displayedTitles.addAll(unavailableTitles);
	}
}
