package lotr.client.gui;

import java.util.*;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.math.IntMath;

import lotr.client.*;
import lotr.common.*;
import lotr.common.fac.*;
import lotr.common.network.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.*;

public class LOTRGuiFactions extends LOTRGuiMenuBase {
	public static ResourceLocation factionsTexture = new ResourceLocation("lotr:gui/factions.png");
	public static ResourceLocation factionsTextureFull = new ResourceLocation("lotr:gui/factions_full.png");
	public static LOTRDimension currentDimension;
	public static LOTRDimension prevDimension;
	public static LOTRDimension.DimensionRegion currentRegion;
	public static LOTRDimension.DimensionRegion prevRegion;
	public static List<LOTRFaction> currentFactionList;
	public static Page currentPage = Page.FRONT;

	public int currentFactionIndex;
	public int prevFactionIndex;
	public LOTRFaction currentFaction;
	public int pageY = 46;
	public int pageWidth = 256;
	public int pageHeight = 128;
	public int pageBorderLeft = 16;
	public int pageBorderTop = 12;
	public int pageMapX = 159;
	public int pageMapY = 22;
	public int pageMapSize = 80;
	public LOTRGuiMap mapDrawGui;
	public GuiButton buttonRegions;
	public GuiButton buttonPagePrev;
	public GuiButton buttonPageNext;
	public GuiButton buttonFactionMap;
	public LOTRGuiButtonPledge buttonPledge;
	public LOTRGuiButtonPledge buttonPledgeConfirm;
	public LOTRGuiButtonPledge buttonPledgeRevoke;
	public float currentScroll;
	public boolean isScrolling;
	public boolean wasMouseDown;
	public int scrollBarWidth;
	public int scrollBarHeight;
	public int scrollBarX;
	public int scrollBarY;
	public int scrollBarBorder;
	public int scrollWidgetWidth;
	public int scrollWidgetHeight;
	public LOTRGuiScrollPane scrollPaneAlliesEnemies;
	public int scrollAlliesEnemiesX;
	public int numDisplayedAlliesEnemies;
	public List currentAlliesEnemies;
	public boolean isOtherPlayer;
	public String otherPlayerName;
	public Map<LOTRFaction, Float> playerAlignmentMap;
	public boolean isPledging;

	public boolean isUnpledging;

	public LOTRGuiFactions() {
		xSize = pageWidth;
		currentScroll = 0.0f;
		isScrolling = false;
		scrollBarWidth = 240;
		scrollBarHeight = 14;
		scrollBarX = xSize / 2 - scrollBarWidth / 2;
		scrollBarY = 180;
		scrollBarBorder = 1;
		scrollWidgetWidth = 17;
		scrollWidgetHeight = 12;
		scrollPaneAlliesEnemies = new LOTRGuiScrollPane(7, 7).setColors(5521198, 8019267);
		scrollAlliesEnemiesX = 138;
		isOtherPlayer = false;
		isPledging = false;
		isUnpledging = false;
		mapDrawGui = new LOTRGuiMap();
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button == buttonRegions) {
				List<LOTRDimension.DimensionRegion> regionList = currentDimension.dimensionRegions;
				if (!regionList.isEmpty()) {
					int i = regionList.indexOf(currentRegion);
					++i;
					i = IntMath.mod(i, regionList.size());
					currentRegion = regionList.get(i);
					updateCurrentDimensionAndFaction();
					setCurrentScrollFromFaction();
					scrollPaneAlliesEnemies.resetScroll();
					isPledging = false;
					isUnpledging = false;
				}
			} else if (button == buttonPagePrev) {
				Page newPage = currentPage.prev();
				if (newPage != null) {
					currentPage = newPage;
					scrollPaneAlliesEnemies.resetScroll();
					isPledging = false;
					isUnpledging = false;
				}
			} else if (button == buttonPageNext) {
				Page newPage = currentPage.next();
				if (newPage != null) {
					currentPage = newPage;
					scrollPaneAlliesEnemies.resetScroll();
					isPledging = false;
					isUnpledging = false;
				}
			} else if (button == buttonFactionMap) {
				LOTRGuiMap factionGuiMap = new LOTRGuiMap();
				factionGuiMap.setControlZone(currentFaction);
				mc.displayGuiScreen(factionGuiMap);
			} else if (button == buttonPledge) {
				if (LOTRLevelData.getData(mc.thePlayer).isPledgedTo(currentFaction)) {
					isUnpledging = true;
				} else {
					isPledging = true;
				}
			} else if (button == buttonPledgeConfirm) {
				IMessage packet = new LOTRPacketPledgeSet(currentFaction);
				LOTRPacketHandler.networkWrapper.sendToServer(packet);
				isPledging = false;
			} else if (button == buttonPledgeRevoke) {
				IMessage packet = new LOTRPacketPledgeSet(null);
				LOTRPacketHandler.networkWrapper.sendToServer(packet);
				isUnpledging = false;
				mc.displayGuiScreen(null);
			} else {
				super.actionPerformed(button);
			}
		}
	}

	public boolean canScroll() {
		return true;
	}

	public void drawButtonHoveringText(List list, int i, int j) {
		func_146283_a(list, i, j);
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		List desc;
		int stringWidth;
		LOTRPlayerData clientPD = LOTRLevelData.getData(mc.thePlayer);
		boolean mouseOverAlignLock = false;
		boolean mouseOverWarCrimes = false;
		if (!isPledging && !isUnpledging) {
			buttonPagePrev.enabled = currentPage.prev() != null;
			buttonPageNext.enabled = currentPage.next() != null;
			buttonFactionMap.enabled = currentPage != Page.RANKS && currentFaction.isPlayableAlignmentFaction() && LOTRDimension.getCurrentDimensionWithFallback(mc.theWorld) == currentFaction.factionDimension;
			buttonFactionMap.visible = buttonFactionMap.enabled;
			if (!LOTRFaction.controlZonesEnabled(mc.theWorld)) {
				buttonFactionMap.enabled = false;
				buttonFactionMap.visible = false;
			}
			if (!isOtherPlayer && currentPage == Page.FRONT) {
				if (clientPD.isPledgedTo(currentFaction)) {
					buttonPledge.isBroken = buttonPledge.func_146115_a();
					buttonPledge.enabled = true;
					buttonPledge.visible = true;
					buttonPledge.setDisplayLines(StatCollector.translateToLocal("lotr.gui.factions.unpledge"));
				} else {
					buttonPledge.isBroken = false;
					buttonPledge.visible = clientPD.getPledgeFaction() == null && currentFaction.isPlayableAlignmentFaction() && clientPD.getAlignment(currentFaction) >= 0.0f;
					buttonPledge.enabled = buttonPledge.visible && clientPD.hasPledgeAlignment(currentFaction);
					String desc1 = StatCollector.translateToLocal("lotr.gui.factions.pledge");
					String desc2 = StatCollector.translateToLocalFormatted("lotr.gui.factions.pledgeReq", LOTRAlignmentValues.formatAlignForDisplay(currentFaction.getPledgeAlignment()));
					buttonPledge.setDisplayLines(desc1, desc2);
				}
			} else {
				buttonPledge.enabled = false;
				buttonPledge.visible = false;
			}
			buttonPledgeConfirm.enabled = false;
			buttonPledgeConfirm.visible = false;
			buttonPledgeRevoke.enabled = false;
			buttonPledgeRevoke.visible = false;
		} else {
			buttonPagePrev.enabled = false;
			buttonPageNext.enabled = false;
			buttonFactionMap.enabled = false;
			buttonFactionMap.visible = false;
			buttonPledge.enabled = false;
			buttonPledge.visible = false;
			if (isPledging) {
				buttonPledgeConfirm.visible = true;
				buttonPledgeConfirm.enabled = clientPD.canMakeNewPledge() && clientPD.canPledgeTo(currentFaction);
				buttonPledgeConfirm.setDisplayLines(StatCollector.translateToLocal("lotr.gui.factions.pledge"));
				buttonPledgeRevoke.enabled = false;
				buttonPledgeRevoke.visible = false;
			} else {
				buttonPledgeConfirm.enabled = false;
				buttonPledgeConfirm.visible = false;
				buttonPledgeRevoke.enabled = true;
				buttonPledgeRevoke.visible = true;
				buttonPledgeRevoke.setDisplayLines(StatCollector.translateToLocal("lotr.gui.factions.unpledge"));
			}
		}
		setupScrollBar(i, j);
		drawDefaultBackground();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		if (useFullPageTexture()) {
			mc.getTextureManager().bindTexture(factionsTextureFull);
		} else {
			mc.getTextureManager().bindTexture(factionsTexture);
		}
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		drawTexturedModalRect(guiLeft, guiTop + pageY, 0, 0, pageWidth, pageHeight);
		String title = StatCollector.translateToLocalFormatted("lotr.gui.factions.title", currentDimension.getDimensionName());
		if (isOtherPlayer) {
			title = StatCollector.translateToLocalFormatted("lotr.gui.factions.titleOther", otherPlayerName);
		}
		fontRendererObj.drawString(title, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(title) / 2, guiTop - 30, 16777215);
		if (currentRegion != null && currentDimension.dimensionRegions.size() > 1) {
			buttonRegions.displayString = currentRegion.getRegionName();
			buttonRegions.enabled = true;
			buttonRegions.visible = true;
		} else {
			buttonRegions.displayString = "";
			buttonRegions.enabled = false;
			buttonRegions.visible = false;
		}
		if (currentFaction != null) {
			float alignment = isOtherPlayer && playerAlignmentMap != null ? playerAlignmentMap.get(currentFaction) : clientPD.getAlignment(currentFaction);
			int x = guiLeft + xSize / 2;
			int y = guiTop;
			LOTRTickHandlerClient.renderAlignmentBar(alignment, isOtherPlayer, currentFaction, x, y, true, false, true, true);
			String s = currentFaction.factionSubtitle();
			drawCenteredString(s, x, y + (fontRendererObj.FONT_HEIGHT + 22), 16777215);
			if (!useFullPageTexture()) {
				if (currentFaction.factionMapInfo != null) {
					LOTRMapRegion mapInfo = currentFaction.factionMapInfo;
					int mapX = mapInfo.mapX;
					int mapY = mapInfo.mapY;
					int mapR = mapInfo.radius;
					int xMin = guiLeft + pageMapX;
					int xMax = xMin + pageMapSize;
					int yMin = guiTop + pageY + pageMapY;
					int yMax = yMin + pageMapSize;
					int mapBorder = 1;
					Gui.drawRect(xMin - mapBorder, yMin - mapBorder, xMax + mapBorder, yMax + mapBorder, -16777216);
					float zoom = (float) pageMapSize / (mapR * 2);
					float zoomExp = (float) Math.log(zoom) / (float) Math.log(2.0);
					mapDrawGui.setFakeMapProperties(mapX, mapY, zoom, zoomExp, zoom);
					int[] statics = LOTRGuiMap.setFakeStaticProperties(pageMapSize, pageMapSize, xMin, xMax, yMin, yMax);
					mapDrawGui.enableZoomOutWPFading = false;
					boolean sepia = LOTRConfig.enableSepiaMap;
					mapDrawGui.renderMapAndOverlay(sepia, 1.0f, true);
					LOTRGuiMap.setFakeStaticProperties(statics[0], statics[1], statics[2], statics[3], statics[4], statics[5]);
				}
				int wcX = guiLeft + pageMapX + 3;
				int wcY = guiTop + pageY + pageMapY + pageMapSize + 5;
				int wcWidth = 8;
				mc.getTextureManager().bindTexture(factionsTexture);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				if (currentFaction.approvesWarCrimes) {
					drawTexturedModalRect(wcX, wcY, 33, 142, wcWidth, wcWidth);
				} else {
					drawTexturedModalRect(wcX, wcY, 41, 142, wcWidth, wcWidth);
				}
				if (i >= wcX && i < wcX + wcWidth && j >= wcY && j < wcY + wcWidth) {
					mouseOverWarCrimes = true;
				}
			}
			x = guiLeft + pageBorderLeft;
			y = guiTop + pageY + pageBorderTop;
			if (!isPledging && !isUnpledging) {
				int index;
				if (currentPage == Page.FRONT) {
					if (isOtherPlayer) {
						s = StatCollector.translateToLocalFormatted("lotr.gui.factions.pageOther", otherPlayerName);
						fontRendererObj.drawString(s, x, y, 8019267);
						y += fontRendererObj.FONT_HEIGHT * 2;
					}
					String alignmentInfo = StatCollector.translateToLocal("lotr.gui.factions.alignment");
					fontRendererObj.drawString(alignmentInfo, x, y, 8019267);
					String alignmentString = LOTRAlignmentValues.formatAlignForDisplay(alignment);
					LOTRTickHandlerClient.drawAlignmentText(fontRendererObj, x += fontRendererObj.getStringWidth(alignmentInfo) + 5, y, alignmentString, 1.0f);
					if (clientPD.isPledgeEnemyAlignmentLimited(currentFaction)) {
						mc.getTextureManager().bindTexture(factionsTexture);
						GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
						int lockX = x + fontRendererObj.getStringWidth(alignmentString) + 5;
						int lockWidth = 16;
						drawTexturedModalRect(lockX, y, 0, 200, lockWidth, lockWidth);
						if (i >= lockX && i < lockX + lockWidth && j >= y && j < y + lockWidth) {
							mouseOverAlignLock = true;
						}
					}
					x = guiLeft + pageBorderLeft;
					LOTRFactionRank curRank = currentFaction.getRank(alignment);
					String rankName = curRank.getFullNameWithGender(clientPD);
					rankName = StatCollector.translateToLocalFormatted("lotr.gui.factions.alignment.state", rankName);
					fontRendererObj.drawString(rankName, x, y += fontRendererObj.FONT_HEIGHT, 8019267);
					y += fontRendererObj.FONT_HEIGHT * 2;
					if (!isOtherPlayer) {
						LOTRFactionData factionData = clientPD.getFactionData(currentFaction);
						if (alignment >= 0.0f) {
							float conq;
							s = StatCollector.translateToLocalFormatted("lotr.gui.factions.data.enemiesKilled", factionData.getEnemiesKilled());
							fontRendererObj.drawString(s, x, y, 8019267);
							s = StatCollector.translateToLocalFormatted("lotr.gui.factions.data.trades", factionData.getTradeCount());
							fontRendererObj.drawString(s, x, y += fontRendererObj.FONT_HEIGHT, 8019267);
							s = StatCollector.translateToLocalFormatted("lotr.gui.factions.data.hires", factionData.getHireCount());
							fontRendererObj.drawString(s, x, y += fontRendererObj.FONT_HEIGHT, 8019267);
							s = StatCollector.translateToLocalFormatted("lotr.gui.factions.data.miniquests", factionData.getMiniQuestsCompleted());
							fontRendererObj.drawString(s, x, y += fontRendererObj.FONT_HEIGHT, 8019267);
							y += fontRendererObj.FONT_HEIGHT;
							if (clientPD.isPledgedTo(currentFaction) && (conq = factionData.getConquestEarned()) != 0.0f) {
								int conqInt = Math.round(conq);
								s = StatCollector.translateToLocalFormatted("lotr.gui.factions.data.conquest", conqInt);
								fontRendererObj.drawString(s, x, y, 8019267);
								y += fontRendererObj.FONT_HEIGHT;
							}
						}
						if (alignment <= 0.0f) {
							s = StatCollector.translateToLocalFormatted("lotr.gui.factions.data.npcsKilled", factionData.getNPCsKilled());
							fontRendererObj.drawString(s, x, y, 8019267);
						}
						if (buttonPledge.visible && clientPD.isPledgedTo(currentFaction)) {
							s = StatCollector.translateToLocal("lotr.gui.factions.pledged");
							int px = buttonPledge.xPosition + buttonPledge.width + 8;
							int py = buttonPledge.yPosition + buttonPledge.height / 2 - fontRendererObj.FONT_HEIGHT / 2;
							fontRendererObj.drawString(s, px, py, 16711680);
						}
					}
				} else if (currentPage == Page.RANKS) {
					LOTRFactionRank curRank = currentFaction.getRank(clientPD);
					int[] minMax = scrollPaneAlliesEnemies.getMinMaxIndices(currentAlliesEnemies, numDisplayedAlliesEnemies);
					for (index = minMax[0]; index <= minMax[1]; ++index) {
						Object listObj = currentAlliesEnemies.get(index);
						if (listObj instanceof String) {
							s = (String) listObj;
							fontRendererObj.drawString(s, x, y, 8019267);
						} else if (listObj instanceof LOTRFactionRank) {
							LOTRFactionRank rank = (LOTRFactionRank) listObj;
							String rankName = rank.getShortNameWithGender(clientPD);
							String rankAlign = LOTRAlignmentValues.formatAlignForDisplay(rank.alignment);
							if (rank == LOTRFactionRank.RANK_ENEMY) {
								rankAlign = "-";
							}
							boolean hiddenRankName = !clientPD.isPledgedTo(currentFaction) && rank.alignment > currentFaction.getPledgeAlignment() && rank.alignment > currentFaction.getRankAbove(curRank).alignment;
							if (hiddenRankName) {
								rankName = StatCollector.translateToLocal("lotr.gui.factions.rank?");
							}
							s = StatCollector.translateToLocalFormatted("lotr.gui.factions.listRank", rankName, rankAlign);
							if (rank == curRank) {
								LOTRTickHandlerClient.drawAlignmentText(fontRendererObj, x, y, s, 1.0f);
							} else {
								fontRendererObj.drawString(s, x, y, 8019267);
							}
						}
						y += fontRendererObj.FONT_HEIGHT;
					}
				} else if (currentPage == Page.ALLIES || currentPage == Page.ENEMIES) {
					int avgBgColor = LOTRTextures.computeAverageFactionPageColor(factionsTexture, 20, 20, 120, 80);
					int[] minMax = scrollPaneAlliesEnemies.getMinMaxIndices(currentAlliesEnemies, numDisplayedAlliesEnemies);
					for (index = minMax[0]; index <= minMax[1]; ++index) {
						Object listObj = currentAlliesEnemies.get(index);
						if (listObj instanceof LOTRFactionRelations.Relation) {
							LOTRFactionRelations.Relation rel = (LOTRFactionRelations.Relation) listObj;
							s = StatCollector.translateToLocalFormatted("lotr.gui.factions.relationHeader", rel.getDisplayName());
							fontRendererObj.drawString(s, x, y, 8019267);
						} else if (listObj instanceof LOTRFaction) {
							LOTRFaction fac = (LOTRFaction) listObj;
							s = StatCollector.translateToLocalFormatted("lotr.gui.factions.list", fac.factionName());
							fontRendererObj.drawString(s, x, y, LOTRTextures.findContrastingColor(fac.getFactionColor(), avgBgColor));
						}
						y += fontRendererObj.FONT_HEIGHT;
					}
				}
				if (scrollPaneAlliesEnemies.hasScrollBar) {
					scrollPaneAlliesEnemies.drawScrollBar();
				}
			} else {
				int stringWidth2 = pageWidth - pageBorderLeft * 2;
				Collection<String> displayLines = new ArrayList<>();
				if (isPledging) {
					List<LOTRFaction> facsPreventingPledge = clientPD.getFactionsPreventingPledgeTo(currentFaction);
					if (facsPreventingPledge.isEmpty()) {
						if (clientPD.canMakeNewPledge()) {
							if (clientPD.canPledgeTo(currentFaction)) {
								String desc2 = StatCollector.translateToLocalFormatted("lotr.gui.factions.pledgeDesc1", currentFaction.factionName());
								displayLines.addAll(fontRendererObj.listFormattedStringToWidth(desc2, stringWidth2));
								displayLines.add("");
								desc2 = StatCollector.translateToLocalFormatted("lotr.gui.factions.pledgeDesc2");
								displayLines.addAll(fontRendererObj.listFormattedStringToWidth(desc2, stringWidth2));
							}
						} else {
							LOTRFaction brokenPledge = clientPD.getBrokenPledgeFaction();
							String brokenPledgeName = brokenPledge == null ? StatCollector.translateToLocal("lotr.gui.factions.pledgeUnknown") : brokenPledge.factionName();
							String desc3 = StatCollector.translateToLocalFormatted("lotr.gui.factions.pledgeBreakCooldown", currentFaction.factionName(), brokenPledgeName);
							displayLines.addAll(fontRendererObj.listFormattedStringToWidth(desc3, stringWidth2));
							displayLines.add("");
							GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
							mc.getTextureManager().bindTexture(factionsTexture);
							drawTexturedModalRect(guiLeft + pageWidth / 2 - 97, guiTop + pageY + 56, 0, 240, 194, 16);
							float cdFrac = (float) clientPD.getPledgeBreakCooldown() / clientPD.getPledgeBreakCooldownStart();
							drawTexturedModalRect(guiLeft + pageWidth / 2 - 75, guiTop + pageY + 60, 22, 232, MathHelper.ceiling_float_int(cdFrac * 150.0f), 8);
						}
					} else {
						facsPreventingPledge.sort((o1, o2) -> {
							float align1 = clientPD.getAlignment(o1);
							float align2 = clientPD.getAlignment(o2);
							return -Float.compare(align1, align2);
						});
						String facNames = "If you are reading this, something has gone hideously wrong.";
						if (facsPreventingPledge.size() == 1) {
							facNames = StatCollector.translateToLocalFormatted("lotr.gui.factions.enemies1", facsPreventingPledge.get(0).factionName());
						} else if (facsPreventingPledge.size() == 2) {
							facNames = StatCollector.translateToLocalFormatted("lotr.gui.factions.enemies2", facsPreventingPledge.get(0).factionName(), facsPreventingPledge.get(1).factionName());
						} else if (facsPreventingPledge.size() == 3) {
							facNames = StatCollector.translateToLocalFormatted("lotr.gui.factions.enemies3", facsPreventingPledge.get(0).factionName(), facsPreventingPledge.get(1).factionName(), facsPreventingPledge.get(2).factionName());
						} else if (facsPreventingPledge.size() > 3) {
							facNames = StatCollector.translateToLocalFormatted("lotr.gui.factions.enemies3+", facsPreventingPledge.get(0).factionName(), facsPreventingPledge.get(1).factionName(), facsPreventingPledge.get(2).factionName(), facsPreventingPledge.size() - 3);
						}
						String desc4 = StatCollector.translateToLocalFormatted("lotr.gui.factions.pledgeEnemies", currentFaction.factionName(), facNames);
						displayLines.addAll(fontRendererObj.listFormattedStringToWidth(desc4, stringWidth2));
						displayLines.add("");
					}
				} else {
					String desc5 = StatCollector.translateToLocalFormatted("lotr.gui.factions.unpledgeDesc1", currentFaction.factionName());
					displayLines.addAll(fontRendererObj.listFormattedStringToWidth(desc5, stringWidth2));
					displayLines.add("");
					desc5 = StatCollector.translateToLocalFormatted("lotr.gui.factions.unpledgeDesc2");
					displayLines.addAll(fontRendererObj.listFormattedStringToWidth(desc5, stringWidth2));
				}
				for (String line : displayLines) {
					fontRendererObj.drawString(line, x, y, 8019267);
					y += mc.fontRenderer.FONT_HEIGHT;
				}
			}
		}
		if (hasScrollBar()) {
			mc.getTextureManager().bindTexture(factionsTexture);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			drawTexturedModalRect(guiLeft + scrollBarX, guiTop + scrollBarY, 0, 128, scrollBarWidth, scrollBarHeight);
			int factions = currentFactionList.size();
			for (int index = 0; index < factions; ++index) {
				LOTRFaction faction = currentFactionList.get(index);
				float[] factionColors = faction.getFactionRGB();
				float shade = 0.6f;
				GL11.glColor4f(factionColors[0] * shade, factionColors[1] * shade, factionColors[2] * shade, 1.0f);
				float xMin = (float) index / factions;
				float xMax = (float) (index + 1) / factions;
				xMin = guiLeft + scrollBarX + scrollBarBorder + xMin * (scrollBarWidth - scrollBarBorder * 2);
				xMax = guiLeft + scrollBarX + scrollBarBorder + xMax * (scrollBarWidth - scrollBarBorder * 2);
				float yMin = guiTop + scrollBarY + scrollBarBorder;
				float yMax = guiTop + scrollBarY + scrollBarHeight - scrollBarBorder;
				float minU = (scrollBarBorder) / 256.0f;
				float maxU = (scrollBarWidth - scrollBarBorder) / 256.0f;
				float minV = (128 + scrollBarBorder) / 256.0f;
				float maxV = (128 + scrollBarHeight - scrollBarBorder) / 256.0f;
				Tessellator tessellator = Tessellator.instance;
				tessellator.startDrawingQuads();
				tessellator.addVertexWithUV(xMin, yMax, zLevel, minU, maxV);
				tessellator.addVertexWithUV(xMax, yMax, zLevel, maxU, maxV);
				tessellator.addVertexWithUV(xMax, yMin, zLevel, maxU, minV);
				tessellator.addVertexWithUV(xMin, yMin, zLevel, minU, minV);
				tessellator.draw();
			}
			mc.getTextureManager().bindTexture(factionsTexture);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			if (canScroll()) {
				int scroll = (int) (currentScroll * (scrollBarWidth - scrollBarBorder * 2 - scrollWidgetWidth));
				drawTexturedModalRect(guiLeft + scrollBarX + scrollBarBorder + scroll, guiTop + scrollBarY + scrollBarBorder, 0, 142, scrollWidgetWidth, scrollWidgetHeight);
			}
		}
		super.drawScreen(i, j, f);
		if (buttonFactionMap.enabled && buttonFactionMap.func_146115_a()) {
			float z = zLevel;
			String s = StatCollector.translateToLocal("lotr.gui.factions.viewMap");
			stringWidth = 200;
			desc = fontRendererObj.listFormattedStringToWidth(s, stringWidth);
			func_146283_a(desc, i, j);
			GL11.glDisable(2896);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			zLevel = z;
		}
		if (mouseOverAlignLock) {
			float z = zLevel;
			String alignLimit = LOTRAlignmentValues.formatAlignForDisplay(clientPD.getPledgeEnemyAlignmentLimit(currentFaction));
			String lockDesc = StatCollector.translateToLocalFormatted("lotr.gui.factions.pledgeLocked", alignLimit, clientPD.getPledgeFaction().factionName());
			int stringWidth3 = 200;
			List desc6 = fontRendererObj.listFormattedStringToWidth(lockDesc, stringWidth3);
			func_146283_a(desc6, i, j);
			GL11.glDisable(2896);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			zLevel = z;
		}
		if (mouseOverWarCrimes) {
			float z = zLevel;
			String warCrimes = currentFaction.approvesWarCrimes ? "lotr.gui.factions.warCrimesYes" : "lotr.gui.factions.warCrimesNo";
			warCrimes = StatCollector.translateToLocal(warCrimes);
			stringWidth = 200;
			desc = fontRendererObj.listFormattedStringToWidth(warCrimes, stringWidth);
			func_146283_a(desc, i, j);
			GL11.glDisable(2896);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			zLevel = z;
		}
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		int k = Mouse.getEventDWheel();
		if (k != 0) {
			k = Integer.signum(k);
			if (scrollPaneAlliesEnemies.hasScrollBar && scrollPaneAlliesEnemies.mouseOver) {
				int l = currentAlliesEnemies.size() - numDisplayedAlliesEnemies;
				scrollPaneAlliesEnemies.mouseWheelScroll(k, l);
			} else {
				if (k < 0) {
					currentFactionIndex = Math.min(currentFactionIndex + 1, Math.max(0, currentFactionList.size() - 1));
				}
				if (k > 0) {
					currentFactionIndex = Math.max(currentFactionIndex - 1, 0);
				}
				setCurrentScrollFromFaction();
				scrollPaneAlliesEnemies.resetScroll();
				isPledging = false;
				isUnpledging = false;
			}
		}
	}

	public boolean hasScrollBar() {
		return currentFactionList.size() > 1;
	}

	@Override
	public void initGui() {
		super.initGui();
		if (isOtherPlayer) {
			buttonList.remove(buttonMenuReturn);
		}
		buttonRegions = new LOTRGuiButtonRedBook(0, guiLeft + xSize / 2 - 60, guiTop + 200, 120, 20, "");
		buttonList.add(buttonRegions);
		buttonPagePrev = new LOTRGuiButtonFactionsPage(1, guiLeft + 8, guiTop + pageY + 104, false);
		buttonList.add(buttonPagePrev);
		buttonPageNext = new LOTRGuiButtonFactionsPage(2, guiLeft + 232, guiTop + pageY + 104, true);
		buttonList.add(buttonPageNext);
		buttonFactionMap = new LOTRGuiButtonFactionsMap(3, guiLeft + pageMapX + pageMapSize - 3 - 8, guiTop + pageY + pageMapY + 3);
		buttonList.add(buttonFactionMap);
		buttonPledge = new LOTRGuiButtonPledge(this, 4, guiLeft + 14, guiTop + pageY + pageHeight - 42, "");
		buttonList.add(buttonPledge);
		buttonPledgeConfirm = new LOTRGuiButtonPledge(this, 5, guiLeft + pageWidth / 2 - 16, guiTop + pageY + pageHeight - 44, "");
		buttonList.add(buttonPledgeConfirm);
		buttonPledgeRevoke = new LOTRGuiButtonPledge(this, 6, guiLeft + pageWidth / 2 - 16, guiTop + pageY + pageHeight - 44, "");
		buttonList.add(buttonPledgeRevoke);
		buttonPledgeRevoke.isBroken = true;
		prevDimension = currentDimension = LOTRDimension.getCurrentDimensionWithFallback(mc.theWorld);
		currentFaction = LOTRLevelData.getData(mc.thePlayer).getViewingFaction();
		prevRegion = currentRegion = currentFaction.factionRegion;
		currentFactionList = currentRegion.factionList;
		prevFactionIndex = currentFactionIndex = currentFactionList.indexOf(currentFaction);
		setCurrentScrollFromFaction();
		if (mc.currentScreen == this) {
			IMessage packet = new LOTRPacketClientMQEvent(LOTRPacketClientMQEvent.ClientMQEvent.FACTIONS);
			LOTRPacketHandler.networkWrapper.sendToServer(packet);
		}
	}

	@Override
	public void keyTyped(char c, int i) {
		if (i == 1 || i == mc.gameSettings.keyBindInventory.getKeyCode()) {
			if (isPledging) {
				isPledging = false;
				return;
			}
			if (isUnpledging) {
				isUnpledging = false;
				return;
			}
			if (isOtherPlayer) {
				mc.thePlayer.closeScreen();
				return;
			}
		}
		super.keyTyped(c, i);
	}

	public void setCurrentScrollFromFaction() {
		currentScroll = (float) currentFactionIndex / (currentFactionList.size() - 1);
	}

	public void setOtherPlayer(String name, Map<LOTRFaction, Float> alignments) {
		isOtherPlayer = true;
		otherPlayerName = name;
		playerAlignmentMap = alignments;
	}

	public void setupScrollBar(int i, int j) {
		boolean isMouseDown = Mouse.isButtonDown(0);
		int i1 = guiLeft + scrollBarX;
		int j1 = guiTop + scrollBarY;
		int i2 = i1 + scrollBarWidth;
		int j2 = j1 + scrollBarHeight;
		if (!wasMouseDown && isMouseDown && i >= i1 && j >= j1 && i < i2 && j < j2) {
			isScrolling = canScroll();
		}
		if (!isMouseDown) {
			isScrolling = false;
		}
		wasMouseDown = isMouseDown;
		if (isScrolling) {
			currentScroll = (i - i1 - scrollWidgetWidth / 2.0f) / ((float) (i2 - i1) - scrollWidgetWidth);
			currentScroll = MathHelper.clamp_float(currentScroll, 0.0f, 1.0f);
			currentFactionIndex = Math.round(currentScroll * (currentFactionList.size() - 1));
			scrollPaneAlliesEnemies.resetScroll();
		}
		if (currentPage == Page.ALLIES || currentPage == Page.ENEMIES || currentPage == Page.RANKS) {
			if (currentPage == Page.ALLIES) {
				List<LOTRFaction> friends;
				currentAlliesEnemies = new ArrayList();
				List<LOTRFaction> allies = currentFaction.getOthersOfRelation(LOTRFactionRelations.Relation.ALLY);
				if (!allies.isEmpty()) {
					currentAlliesEnemies.add(LOTRFactionRelations.Relation.ALLY);
					currentAlliesEnemies.addAll(allies);
				}
				if (!(friends = currentFaction.getOthersOfRelation(LOTRFactionRelations.Relation.FRIEND)).isEmpty()) {
					if (!currentAlliesEnemies.isEmpty()) {
						currentAlliesEnemies.add(null);
					}
					currentAlliesEnemies.add(LOTRFactionRelations.Relation.FRIEND);
					currentAlliesEnemies.addAll(friends);
				}
			} else if (currentPage == Page.ENEMIES) {
				List<LOTRFaction> enemies;
				currentAlliesEnemies = new ArrayList();
				List<LOTRFaction> mortals = currentFaction.getOthersOfRelation(LOTRFactionRelations.Relation.MORTAL_ENEMY);
				if (!mortals.isEmpty()) {
					currentAlliesEnemies.add(LOTRFactionRelations.Relation.MORTAL_ENEMY);
					currentAlliesEnemies.addAll(mortals);
				}
				if (!(enemies = currentFaction.getOthersOfRelation(LOTRFactionRelations.Relation.ENEMY)).isEmpty()) {
					if (!currentAlliesEnemies.isEmpty()) {
						currentAlliesEnemies.add(null);
					}
					currentAlliesEnemies.add(LOTRFactionRelations.Relation.ENEMY);
					currentAlliesEnemies.addAll(enemies);
				}
			} else {
				currentAlliesEnemies = new ArrayList();
				currentAlliesEnemies.add(StatCollector.translateToLocal("lotr.gui.factions.rankHeader"));
				if (LOTRLevelData.getData(mc.thePlayer).getAlignment(currentFaction) <= 0.0f) {
					currentAlliesEnemies.add(LOTRFactionRank.RANK_ENEMY);
				}
				LOTRFactionRank rank = LOTRFactionRank.RANK_NEUTRAL;
				do {
					currentAlliesEnemies.add(rank);
					LOTRFactionRank nextRank = currentFaction.getRankAbove(rank);
					if (nextRank == null || nextRank.isDummyRank() || currentAlliesEnemies.contains(nextRank)) {
						break;
					}
					rank = nextRank;
				} while (true);
			}
			scrollPaneAlliesEnemies.hasScrollBar = false;
			numDisplayedAlliesEnemies = currentAlliesEnemies.size();
			if (numDisplayedAlliesEnemies > 10) {
				numDisplayedAlliesEnemies = 10;
				scrollPaneAlliesEnemies.hasScrollBar = true;
			}
			scrollPaneAlliesEnemies.paneX0 = guiLeft;
			scrollPaneAlliesEnemies.scrollBarX0 = guiLeft + scrollAlliesEnemiesX;
			if (currentPage == Page.RANKS) {
				scrollPaneAlliesEnemies.scrollBarX0 += 50;
			}
			scrollPaneAlliesEnemies.paneY0 = guiTop + pageY + pageBorderTop;
			scrollPaneAlliesEnemies.paneY1 = scrollPaneAlliesEnemies.paneY0 + fontRendererObj.FONT_HEIGHT * numDisplayedAlliesEnemies;
		} else {
			scrollPaneAlliesEnemies.hasScrollBar = false;
		}
		scrollPaneAlliesEnemies.mouseDragScroll(i, j);
	}

	@Override
	public void setWorldAndResolution(Minecraft mc, int i, int j) {
		super.setWorldAndResolution(mc, i, j);
		mapDrawGui.setWorldAndResolution(mc, i, j);
	}

	public void updateCurrentDimensionAndFaction() {
		boolean changes;
		LOTRPlayerData pd = LOTRLevelData.getData(mc.thePlayer);
		Map<LOTRDimension.DimensionRegion, LOTRFaction> lastViewedRegions = new EnumMap<>(LOTRDimension.DimensionRegion.class);
		if (currentFactionIndex != prevFactionIndex) {
			currentFaction = currentFactionList.get(currentFactionIndex);
		}
		prevFactionIndex = currentFactionIndex;
		currentDimension = LOTRDimension.getCurrentDimensionWithFallback(mc.theWorld);
		if (currentDimension != prevDimension) {
			currentRegion = currentDimension.dimensionRegions.get(0);
		}
		if (currentRegion != prevRegion) {
			pd.setRegionLastViewedFaction(prevRegion, currentFaction);
			lastViewedRegions.put(prevRegion, currentFaction);
			currentFactionList = currentRegion.factionList;
			currentFaction = pd.getRegionLastViewedFaction(currentRegion);
			prevFactionIndex = currentFactionIndex = currentFactionList.indexOf(currentFaction);
		}
		prevDimension = currentDimension;
		prevRegion = currentRegion;
		LOTRFaction prevFaction = pd.getViewingFaction();
		changes = currentFaction != prevFaction;
		if (changes) {
			pd.setViewingFaction(currentFaction);
			LOTRClientProxy.sendClientInfoPacket(currentFaction, lastViewedRegions);
			isPledging = false;
			isUnpledging = false;
		}
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		updateCurrentDimensionAndFaction();
		LOTRPlayerData playerData = LOTRLevelData.getData(mc.thePlayer);
		if (isPledging && !playerData.hasPledgeAlignment(currentFaction)) {
			isPledging = false;
		}
		if (isUnpledging && !playerData.isPledgedTo(currentFaction)) {
			isUnpledging = false;
		}
	}

	public boolean useFullPageTexture() {
		return isPledging || isUnpledging || currentPage == Page.RANKS;
	}

	public enum Page {
		FRONT, RANKS, ALLIES, ENEMIES;

		public Page next() {
			int i = ordinal();
			if (i == values().length - 1) {
				return null;
			}
			i++;
			return values()[i];
		}

		public Page prev() {
			int i = ordinal();
			if (i == 0) {
				return null;
			}
			i--;
			return values()[i];
		}
	}

}
