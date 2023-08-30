package lotr.client.gui;

import java.util.*;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import org.lwjgl.opengl.GL11;

import lotr.client.LOTRClientProxy;
import lotr.common.*;
import lotr.common.fac.*;
import lotr.common.network.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.EnumChatFormatting;

public class LOTRGuiAlignmentChoices extends LOTRGuiScreenBase {
	public int xSize = 430;
	public int ySize = 250;
	public int guiLeft;
	public int guiTop;
	public int page;
	public GuiButton buttonConfirm;
	public Map<LOTRFaction, LOTRGuiButtonRedBook> facButtons = new EnumMap<>(LOTRFaction.class);
	public Map<LOTRGuiButtonRedBook, LOTRFaction> buttonFacs = new HashMap<>();
	public Set<LOTRFaction> setZeroFacs = EnumSet.noneOf(LOTRFaction.class);

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button == buttonConfirm) {
				if (page == 0) {
					page = 1;
				} else if (page == 1) {
					IMessage packet = new LOTRPacketAlignmentChoices(setZeroFacs);
					LOTRPacketHandler.networkWrapper.sendToServer(packet);
					mc.thePlayer.closeScreen();
				}
			} else if (buttonFacs.containsKey(button)) {
				LOTRFaction fac = buttonFacs.get(button);
				if (isFactionConflicting(LOTRLevelData.getData(mc.thePlayer), fac, false)) {
					if (setZeroFacs.contains(fac)) {
						setZeroFacs.remove(fac);
					} else {
						setZeroFacs.add(fac);
					}
				}
			}
		}
		super.actionPerformed(button);
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		Gui.drawRect(guiLeft, guiTop, guiLeft + xSize, guiTop + ySize, -5756117);
		Gui.drawRect(guiLeft + 2, guiTop + 2, guiLeft + xSize - 2, guiTop + ySize - 2, -1847889);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(LOTRClientProxy.alignmentTexture);
		int warnIconSize = 32;
		drawTexturedModalRect(guiLeft - warnIconSize, guiTop, 16, 128, warnIconSize, warnIconSize);
		drawTexturedModalRect(guiLeft + xSize, guiTop, 16, 128, warnIconSize, warnIconSize);
		LOTRPlayerData pd = LOTRLevelData.getData(mc.thePlayer);
		int textColor = 8019267;
		int border = 7;
		int lineWidth = xSize - border * 2;
		int x = guiLeft + border;
		int y = guiTop + border;
		if (page == 0) {
			String s = "Hello! You are reading this because you earned alignment before Update 35.";
			fontRendererObj.drawSplitString(s, x, y, lineWidth, textColor);
			y += fontRendererObj.FONT_HEIGHT * fontRendererObj.listFormattedStringToWidth(s, lineWidth).size();
			s = "This update introduces 'Enemy Alignment Draining'. If you have + alignment with two Mortal Enemy factions (more severe than Enemy), both alignments will slowly drain over time until one reaches 0.";
			fontRendererObj.drawSplitString(s, x, y += fontRendererObj.FONT_HEIGHT, lineWidth, textColor);
			y += fontRendererObj.FONT_HEIGHT * fontRendererObj.listFormattedStringToWidth(s, lineWidth).size();
			s = "You can still hold + alignment with Mortal Enemies in the short term if you work quickly. But long-term public friendship with Gondor and Mordor together is not in the spirit of Tolkien's Middle-earth.";
			fontRendererObj.drawSplitString(s, x, y += fontRendererObj.FONT_HEIGHT, lineWidth, textColor);
			y += fontRendererObj.FONT_HEIGHT * fontRendererObj.listFormattedStringToWidth(s, lineWidth).size();
			s = "Because you have played before, you have the option to set any unwanted alignments to zero immediately, to prevent draining high alignment from factions you care about. This will also help if you want to Pledge to a faction.";
			fontRendererObj.drawSplitString(s, x, y += fontRendererObj.FONT_HEIGHT, lineWidth, textColor);
			y += fontRendererObj.FONT_HEIGHT * fontRendererObj.listFormattedStringToWidth(s, lineWidth).size();
			s = "Note that if you are a server admin or playing in singleplayer you can toggle this feature in the LOTR mod config. However, players who wish to Pledge will still need to reduce Mortal Enemy alignments to zero.";
			fontRendererObj.drawSplitString(EnumChatFormatting.ITALIC + s, x, y + fontRendererObj.FONT_HEIGHT, lineWidth, textColor);
			buttonConfirm.displayString = "View your alignments";
		} else if (page == 1) {
			String s = "Choose which alignments to set to zero. You can choose as many or as few as you like, but you can only choose once. Alignments which will drain due to a conflict are in RED - this will update as you select unwanted factions.";
			fontRendererObj.drawSplitString(s, x, y, lineWidth, textColor);
			y += fontRendererObj.FONT_HEIGHT * fontRendererObj.listFormattedStringToWidth(s, lineWidth).size();
			s = "If you are hoping to Pledge to a faction, you will need to have 0 or - alignment with all of its Mortal Enemies.";
			fontRendererObj.drawSplitString(s, x, y += fontRendererObj.FONT_HEIGHT, lineWidth, textColor);
			y += fontRendererObj.FONT_HEIGHT * fontRendererObj.listFormattedStringToWidth(s, lineWidth).size();
			int buttonX = guiLeft + border;
			int buttonY = y + fontRendererObj.FONT_HEIGHT;
			for (LOTRFaction fac : LOTRFaction.getPlayableAlignmentFactions()) {
				LOTRGuiButtonRedBook button = facButtons.get(fac);
				button.visible = true;
				button.enabled = false;
				button.displayString = "";
				button.xPosition = buttonX;
				button.yPosition = buttonY;
				buttonX += button.width + 4;
				if (buttonX >= guiLeft + xSize - border) {
					buttonX = guiLeft + border;
					buttonY += 24;
				}
				float align = pd.getAlignment(fac);
				String facName = fac.factionName();
				String alignS = LOTRAlignmentValues.formatAlignForDisplay(align);
				String status = "Not draining";
				button.enabled = false;
				if (align > 0.0f) {
					boolean isDraining = isFactionConflicting(pd, fac, false);
					boolean willDrain = isFactionConflicting(pd, fac, true);
					if (isDraining) {
						if (setZeroFacs.contains(fac)) {
							status = "Setting to zero";
							button.enabled = true;
							Gui.drawRect(button.xPosition - 1, button.yPosition - 1, button.xPosition + button.width + 1, button.yPosition + button.height + 1, -1);
						} else if (willDrain) {
							status = "Draining";
							button.enabled = true;
							Gui.drawRect(button.xPosition - 1, button.yPosition - 1, button.xPosition + button.width + 1, button.yPosition + button.height + 1, -62464);
						} else {
							status = "Will not drain after CONFIRM";
							button.enabled = false;
						}
					}
				}
				float buttonTextScale = 0.5f;
				GL11.glPushMatrix();
				GL11.glTranslatef(0.0f, 0.0f, 100.0f);
				GL11.glScalef(buttonTextScale, buttonTextScale, 1.0f);
				int buttonTextX = (int) ((button.xPosition + button.width / 2) / buttonTextScale);
				int buttonTextY = (int) (button.yPosition / buttonTextScale) + 4;
				drawCenteredString(facName, buttonTextX, buttonTextY, textColor);
				drawCenteredString(alignS, buttonTextX, buttonTextY += fontRendererObj.FONT_HEIGHT, textColor);
				drawCenteredString(status, buttonTextX, buttonTextY + fontRendererObj.FONT_HEIGHT, textColor);
				GL11.glPopMatrix();
				if (!button.func_146115_a() || align <= 0.0f || setZeroFacs.contains(fac) || !isFactionConflicting(pd, fac, true)) {
					continue;
				}
				GL11.glPushMatrix();
				GL11.glTranslatef(0.0f, 0.0f, 100.0f);
				for (LOTRFaction otherFac : LOTRFaction.getPlayableAlignmentFactions()) {
					if (fac == otherFac || setZeroFacs.contains(otherFac) || !pd.doFactionsDrain(fac, otherFac) || pd.getAlignment(otherFac) <= 0.0f) {
						continue;
					}
					LOTRGuiButtonRedBook otherButton = facButtons.get(otherFac);
					int x1 = button.xPosition + button.width / 2;
					int x2 = otherButton.xPosition + otherButton.width / 2;
					int y1 = button.yPosition + button.height / 2;
					int y2 = otherButton.yPosition + otherButton.height / 2;
					GL11.glDisable(3553);
					Tessellator tess = Tessellator.instance;
					tess.startDrawing(1);
					GL11.glPushAttrib(2849);
					GL11.glLineWidth(4.0f);
					tess.setColorOpaque_I(-62464);
					tess.addVertex(x1, y1, 0.0);
					tess.addVertex(x2, y2, 0.0);
					tess.draw();
					GL11.glPopAttrib();
					GL11.glEnable(3553);
				}
				GL11.glPopMatrix();
			}
			s = "If you do not want to choose now you can close this screen with '" + GameSettings.getKeyDisplayString(mc.gameSettings.keyBindInventory.getKeyCode()) + "' and it will appear again when you log in. Remember - you can only choose once.";
			y = buttonConfirm.yPosition - fontRendererObj.FONT_HEIGHT * (fontRendererObj.listFormattedStringToWidth(s, lineWidth).size() + 1);
			fontRendererObj.drawSplitString(s, x, y, lineWidth, textColor);
			buttonConfirm.displayString = "CONFIRM - set " + setZeroFacs.size() + " alignments to zero";
		}
		super.drawScreen(i, j, f);
	}

	@Override
	public void initGui() {
		super.initGui();
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		buttonConfirm = new LOTRGuiButtonRedBook(0, guiLeft + xSize / 2 - 100, guiTop + ySize - 30, 200, 20, "BUTTON");
		buttonList.add(buttonConfirm);
		for (LOTRFaction fac : LOTRFaction.getPlayableAlignmentFactions()) {
			LOTRGuiButtonRedBook button = new LOTRGuiButtonRedBook(0, 0, 0, 80, 20, "");
			facButtons.put(fac, button);
			buttonFacs.put(button, fac);
			buttonList.add(button);
			button.enabled = false;
			button.visible = false;
		}
	}

	public boolean isFactionConflicting(LOTRPlayerData pd, LOTRFaction fac, boolean accountForSelection) {
		for (LOTRFaction otherFac : LOTRFaction.getPlayableAlignmentFactions()) {
			if (fac == otherFac || accountForSelection && setZeroFacs.contains(otherFac) || !pd.doFactionsDrain(fac, otherFac) || pd.getAlignment(otherFac) <= 0.0f) {
				continue;
			}
			return true;
		}
		return false;
	}
}
