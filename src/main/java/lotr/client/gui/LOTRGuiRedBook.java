package lotr.client.gui;

import java.util.*;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;

import lotr.client.LOTRTextBody;
import lotr.common.*;
import lotr.common.entity.npc.LOTRSpeech;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.network.*;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class LOTRGuiRedBook extends LOTRGuiScreenBase {
	public static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/quest/redBook.png");
	public static ResourceLocation guiTexture_miniquests = new ResourceLocation("lotr:gui/quest/redBook_miniquests.png");
	public static RenderItem renderItem = new RenderItem();
	public static boolean viewCompleted;
	public static int textColor = 8019267;
	public static int textColorDark = 5521198;
	public static int textColorFaded = 9666921;
	public static int textColorRed = 16711680;
	public static Page page;
	public int xSize = 420;
	public int ySize = 256;
	public int guiLeft;
	public int guiTop;
	public int pageWidth = 186;
	public int pageTop = 18;
	public int pageBorder = 10;
	public boolean wasMouseDown;
	public int lastMouseY;
	public int scrollBarWidth = 12;
	public int scrollBarHeight = 216;
	public int scrollBarX = xSize / 2 + pageWidth;
	public int scrollBarY = 18;
	public int scrollBarActiveHeight = 203;
	public int scrollBarActiveYOffset = 3;
	public int scrollBarActiveXBorder = 1;
	public int scrollWidgetWidth = 10;
	public int scrollWidgetHeight = 17;
	public boolean isScrolling;
	public float currentScroll;
	public Map<LOTRMiniQuest, Pair<Integer, Integer>> displayedMiniQuests = new HashMap<>();
	public int maxDisplayedMiniQuests = 4;
	public int qPanelWidth = 170;
	public int qPanelHeight = 45;
	public int qPanelBorder = 4;
	public int qDelX = 158;
	public int qDelY = 4;
	public int qTrackX = 148;
	public int qTrackY = 4;
	public int qWidgetSize = 8;
	public int diaryWidth = 170;
	public int diaryHeight = 218;
	public int diaryX = xSize / 2 - pageBorder - pageWidth / 2 - diaryWidth / 2;
	public int diaryY = ySize / 2 - diaryHeight / 2 - 1;
	public int diaryBorder = 6;
	public boolean mouseInDiary;
	public boolean isDiaryScrolling;
	public float diaryScroll;
	public LOTRMiniQuest selectedMiniquest;
	public LOTRMiniQuest deletingMiniquest;
	public int trackTicks;
	public GuiButton buttonViewActive;
	public GuiButton buttonViewCompleted;
	public GuiButton buttonQuestDelete;
	public GuiButton buttonQuestDeleteCancel;

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button == buttonViewActive) {
				viewCompleted = false;
			}
			if (button == buttonViewCompleted) {
				viewCompleted = true;
			}
			if (button == buttonQuestDelete && deletingMiniquest != null) {
				IMessage packet = new LOTRPacketDeleteMiniquest(deletingMiniquest);
				LOTRPacketHandler.networkWrapper.sendToServer(packet);
				deletingMiniquest = null;
				selectedMiniquest = null;
				diaryScroll = 0.0f;
			}
			if (button == buttonQuestDeleteCancel && deletingMiniquest != null) {
				deletingMiniquest = null;
			}
		}
	}

	public boolean canScroll() {
		return hasScrollBar() && getMiniQuests().size() > maxDisplayedMiniQuests;
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		boolean hasQuestViewButtons;
		boolean hasQuestDeleteButtons;
		displayedMiniQuests.clear();
		setupScrollBar(i, j);
		drawDefaultBackground();
		mc.getTextureManager().bindTexture(guiTexture);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize, 512);
		int x = guiLeft + xSize / 2 - pageBorder - pageWidth / 2;
		int y = guiTop + 30;
		if (page == Page.MINIQUESTS && selectedMiniquest == null) {
			float scale = 2.0f;
			float invScale = 1.0f / scale;
			x = (int) (x * invScale);
			y = (int) (y * invScale);
			GL11.glScalef(scale, scale, scale);
			drawCenteredString(page.getTitle(), x, y, 8019267);
			GL11.glScalef(invScale, invScale, invScale);
			x = guiLeft + xSize / 2 - pageBorder - pageWidth / 2;
			y = guiTop + 50;
			if (viewCompleted) {
				drawCenteredString(StatCollector.translateToLocal("lotr.gui.redBook.mq.viewComplete"), x, y, 8019267);
			} else {
				drawCenteredString(StatCollector.translateToLocal("lotr.gui.redBook.mq.viewActive"), x, y, 8019267);
			}
		}
		if (page == Page.MINIQUESTS) {
			if (selectedMiniquest == null) {
				drawCenteredString(LOTRDate.ShireReckoning.getShireDate().getDateName(false), guiLeft + xSize / 2 - pageBorder - pageWidth / 2, guiTop + ySize - 30, 8019267);
				drawCenteredString(StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.numActive", getPlayerData().getActiveMiniQuests().size()), x, guiTop + 120, 8019267);
				drawCenteredString(StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.numComplete", getPlayerData().getCompletedMiniQuestsTotal()), x, guiTop + 140, 8019267);
			} else {
				LOTRMiniQuest quest = selectedMiniquest;
				mc.getTextureManager().bindTexture(guiTexture);
				float[] questRGB = quest.getQuestColorComponents();
				GL11.glColor4f(questRGB[0], questRGB[1], questRGB[2], 1.0f);
				x = guiLeft + diaryX;
				y = guiTop + diaryY;
				drawTexturedModalRect(x, y, 0, 256, diaryWidth, diaryHeight, 512);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				int textW = diaryWidth - diaryBorder * 2;
				int textBottom = y + diaryHeight - diaryBorder;
				x += diaryBorder;
				y += diaryBorder;
				boolean completed = quest.isCompleted();
				boolean failed = !completed && quest.isFailed();
				String entityName = quest.entityName;
				quest.getFactionSubtitle();
				LOTRTextBody pageText = new LOTRTextBody(8019267);
				pageText.setTextWidth(textW);
				String[] dayYear = LOTRDate.ShireReckoning.getShireDate(quest.dateGiven).getDayAndYearNames(false);
				pageText.add(dayYear[0]);
				pageText.add(dayYear[1]);
				if (quest.biomeGiven != null) {
					pageText.add(quest.biomeGiven.getBiomeDisplayName());
				}
				pageText.add("");
				String startQuote = LOTRSpeech.formatSpeech(quest.quoteStart, mc.thePlayer, null, quest.getObjectiveInSpeech());
				startQuote = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.quote", startQuote);
				pageText.add(startQuote);
				pageText.add("");
				List<String> quotesStages = quest.quotesStages;
				if (!quotesStages.isEmpty()) {
					for (String s : quotesStages) {
						String stageQuote = LOTRSpeech.formatSpeech(s, mc.thePlayer, null, quest.getObjectiveInSpeech());
						stageQuote = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.quote", stageQuote);
						pageText.add(stageQuote);
						pageText.add("");
					}
				}
				String asked = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.asked", entityName, quest.getQuestObjective());
				pageText.add(asked);
				pageText.add("");
				String progress = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.progress", quest.getQuestProgress());
				pageText.add(progress);
				if (quest.willHire) {
					pageText.add("");
					String willHire = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.willHire", entityName);
					pageText.add(willHire);
				}
				if (failed) {
					for (int l = 0; l < pageText.size(); ++l) {
						String line = pageText.getText(l);
						line = EnumChatFormatting.STRIKETHROUGH + line;
						pageText.set(l, line);
					}
					String failureText = quest.getQuestFailure();
					pageText.add(failureText, 16711680);
				}
				if (completed) {
					pageText.add("");
					pageText.addLinebreak();
					pageText.add("");
					dayYear = LOTRDate.ShireReckoning.getShireDate(quest.dateCompleted).getDayAndYearNames(false);
					pageText.add(dayYear[0]);
					pageText.add(dayYear[1]);
					pageText.add("");
					String completeQuote = LOTRSpeech.formatSpeech(quest.quoteComplete, mc.thePlayer, null, quest.getObjectiveInSpeech());
					completeQuote = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.quote", completeQuote);
					pageText.add(completeQuote);
					pageText.add("");
					String completedText = StatCollector.translateToLocal("lotr.gui.redBook.mq.diary.complete");
					pageText.add(completedText);
					if (quest.anyRewardsGiven()) {
						pageText.add("");
						String rewardText = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.reward", entityName);
						pageText.add(rewardText);
						if (quest.alignmentRewarded != 0.0f) {
							String alignS = LOTRAlignmentValues.formatAlignForDisplay(quest.alignmentRewarded);
							String alignFacName = quest.getAlignmentRewardFaction().factionName();
							String rewardAlign = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.reward.align", alignS, alignFacName);
							pageText.add(rewardAlign);
						}
						if (quest.coinsRewarded != 0.0f) {
							String rewardCoins = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.reward.coins", quest.coinsRewarded);
							pageText.add(rewardCoins);
						}
						if (!quest.itemsRewarded.isEmpty()) {
							for (ItemStack item : quest.itemsRewarded) {
								String rewardItem = item.getItem() instanceof ItemEditableBook ? StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.reward.book", item.getDisplayName()) : StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.reward.item", item.getDisplayName(), item.stackSize);
								pageText.add(rewardItem);
							}
						}
					}
					if (quest.wasHired) {
						pageText.add("");
						String rewardHired = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.reward.hired", entityName);
						pageText.add(rewardHired);
					}
				}
				diaryScroll = pageText.renderAndReturnScroll(fontRendererObj, x, y, textBottom, diaryScroll);
			}
			if (deletingMiniquest == null) {
				List<LOTRMiniQuest> miniquests = getMiniQuests();
				if (!(miniquests = new ArrayList<>(miniquests)).isEmpty()) {
					if (viewCompleted) {
						miniquests = Lists.reverse(miniquests);
					} else {
						miniquests.sort(new LOTRMiniQuest.SorterAlphabetical());
					}
					int size = miniquests.size();
					int min = Math.round(currentScroll * (size - maxDisplayedMiniQuests));
					int max = maxDisplayedMiniQuests - 1 + Math.round(currentScroll * (size - maxDisplayedMiniQuests));
					min = Math.max(min, 0);
					max = Math.min(max, size - 1);
					for (int index = min; index <= max; ++index) {
						LOTRMiniQuest quest = miniquests.get(index);
						int displayIndex = index - min;
						int questX = guiLeft + xSize / 2 + pageBorder;
						int questY = guiTop + pageTop + displayIndex * (4 + qPanelHeight);
						renderMiniQuestPanel(quest, questX, questY, i, j);
						displayedMiniQuests.put(quest, Pair.of(questX, questY));
					}
				}
			} else {
				String deleteText = viewCompleted ? StatCollector.translateToLocal("lotr.gui.redBook.mq.deleteCmp") : StatCollector.translateToLocal("lotr.gui.redBook.mq.delete");
				List deleteTextLines = fontRendererObj.listFormattedStringToWidth(deleteText, pageWidth);
				int lineX = guiLeft + xSize / 2 + pageBorder + pageWidth / 2;
				int lineY = guiTop + 50;
				for (Object obj : deleteTextLines) {
					String line = (String) obj;
					drawCenteredString(line, lineX, lineY, 8019267);
					lineY += fontRendererObj.FONT_HEIGHT;
				}
				int questX = guiLeft + xSize / 2 + pageBorder + pageWidth / 2 - qPanelWidth / 2;
				int questY = guiTop + pageTop + 80;
				renderMiniQuestPanel(deletingMiniquest, questX, questY, i, j);
			}
		}
		if (hasScrollBar()) {
			mc.getTextureManager().bindTexture(guiTexture_miniquests);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			drawTexturedModalRect(guiLeft + scrollBarX, guiTop + scrollBarY, 244, 0, scrollBarWidth, scrollBarHeight);
			if (canScroll()) {
				int scroll = (int) (currentScroll * (scrollBarActiveHeight - scrollWidgetHeight));
				drawTexturedModalRect(guiLeft + scrollBarX + scrollBarActiveXBorder, guiTop + scrollBarY + scrollBarActiveYOffset + scroll, 224, 0, scrollWidgetWidth, scrollWidgetHeight);
			} else {
				drawTexturedModalRect(guiLeft + scrollBarX + scrollBarActiveXBorder, guiTop + scrollBarY + scrollBarActiveYOffset, 234, 0, scrollWidgetWidth, scrollWidgetHeight);
			}
		}
		buttonViewActive.enabled = buttonViewActive.visible = hasQuestViewButtons = page == Page.MINIQUESTS && selectedMiniquest == null;
		buttonViewCompleted.enabled = buttonViewCompleted.visible = hasQuestViewButtons;
		buttonQuestDelete.enabled = buttonQuestDelete.visible = hasQuestDeleteButtons = page == Page.MINIQUESTS && deletingMiniquest != null;
		buttonQuestDeleteCancel.enabled = buttonQuestDeleteCancel.visible = hasQuestDeleteButtons;
		if (viewCompleted) {
			buttonQuestDelete.displayString = StatCollector.translateToLocal("lotr.gui.redBook.mq.deleteCmpYes");
			buttonQuestDeleteCancel.displayString = StatCollector.translateToLocal("lotr.gui.redBook.mq.deleteCmpNo");
		} else {
			buttonQuestDelete.displayString = StatCollector.translateToLocal("lotr.gui.redBook.mq.deleteYes");
			buttonQuestDeleteCancel.displayString = StatCollector.translateToLocal("lotr.gui.redBook.mq.deleteNo");
		}
		super.drawScreen(i, j, f);
	}

	public List<LOTRMiniQuest> getMiniQuests() {
		if (viewCompleted) {
			return getPlayerData().getMiniQuestsCompleted();
		}
		return getPlayerData().getMiniQuests();
	}

	public LOTRPlayerData getPlayerData() {
		return LOTRLevelData.getData(mc.thePlayer);
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		int i = Mouse.getEventDWheel();
		if (i != 0 && (canScroll() || mouseInDiary)) {
			if (i > 0) {
				i = 1;
			}
			if (i < 0) {
				i = -1;
			}
			if (mouseInDiary) {
				diaryScroll += i;
			} else {
				int j = getMiniQuests().size() - maxDisplayedMiniQuests;
				currentScroll -= (float) i / j;
				currentScroll = Math.max(currentScroll, 0.0f);
				currentScroll = Math.min(currentScroll, 1.0f);
			}
		}
	}

	public boolean hasScrollBar() {
		return page == Page.MINIQUESTS && deletingMiniquest == null;
	}

	@Override
	public void initGui() {
		if (page == null) {
			page = Page.values()[0];
		}
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		int buttonX = guiLeft + xSize / 2 - pageBorder - pageWidth / 2;
		int buttonY = guiTop + 80;
		buttonViewActive = new LOTRGuiButtonRedBook(2, buttonX - 10 - 60, buttonY, 60, 20, StatCollector.translateToLocal("lotr.gui.redBook.mq.viewActive"));
		buttonList.add(buttonViewActive);
		buttonViewCompleted = new LOTRGuiButtonRedBook(3, buttonX + 10, buttonY, 60, 20, StatCollector.translateToLocal("lotr.gui.redBook.mq.viewComplete"));
		buttonList.add(buttonViewCompleted);
		buttonX = guiLeft + xSize / 2 + pageBorder + pageWidth / 2;
		buttonY = guiTop + ySize - 60;
		buttonQuestDelete = new LOTRGuiButtonRedBook(2, buttonX - 10 - 60, buttonY, 60, 20, "");
		buttonList.add(buttonQuestDelete);
		buttonQuestDeleteCancel = new LOTRGuiButtonRedBook(3, buttonX + 10, buttonY, 60, 20, "");
		buttonList.add(buttonQuestDeleteCancel);
	}

	@Override
	public void keyTyped(char c, int i) {
		if (i == 1 || i == mc.gameSettings.keyBindInventory.getKeyCode()) {
			if (deletingMiniquest != null) {
				deletingMiniquest = null;
				return;
			}
			if (selectedMiniquest != null) {
				selectedMiniquest = null;
				return;
			}
		}
		super.keyTyped(c, i);
	}

	@Override
	public void mouseClicked(int i, int j, int mouse) {
		if (mouse == 0) {
			int i1;
			int questY;
			LOTRMiniQuest quest;
			int j1;
			int i2;
			int questX;
			int j2;
			if (page == Page.MINIQUESTS && deletingMiniquest == null) {
				for (Map.Entry<LOTRMiniQuest, Pair<Integer, Integer>> entry : displayedMiniQuests.entrySet()) {
					quest = entry.getKey();
					questX = entry.getValue().getLeft();
					questY = entry.getValue().getRight();
					i1 = questX + qDelX;
					j1 = questY + qDelY;
					i2 = i1 + qWidgetSize;
					j2 = j1 + qWidgetSize;
					if (i >= i1 && j >= j1 && i < i2 && j < j2) {
						selectedMiniquest = deletingMiniquest = quest;
						diaryScroll = 0.0f;
						return;
					}
					if (viewCompleted) {
						continue;
					}
					i1 = questX + qTrackX;
					j1 = questY + qTrackY;
					i2 = i1 + qWidgetSize;
					j2 = j1 + qWidgetSize;
					if (i < i1 || j < j1 || i >= i2 || j >= j2) {
						continue;
					}
					trackOrUntrack(quest);
					return;
				}
			}
			if (page == Page.MINIQUESTS && deletingMiniquest == null) {
				for (Map.Entry<LOTRMiniQuest, Pair<Integer, Integer>> entry : displayedMiniQuests.entrySet()) {
					quest = entry.getKey();
					questX = entry.getValue().getLeft();
					questY = entry.getValue().getRight();
					i1 = questX;
					j1 = questY;
					i2 = i1 + qPanelWidth;
					j2 = j1 + qPanelHeight;
					if (i < i1 || j < j1 || i >= i2 || j >= j2) {
						continue;
					}
					selectedMiniquest = quest;
					diaryScroll = 0.0f;
					return;
				}
				if (!mouseInDiary && !isScrolling) {
					selectedMiniquest = null;
					diaryScroll = 0.0f;
				}
			}
		}
		super.mouseClicked(i, j, mouse);
	}

	public void renderMiniQuestPanel(LOTRMiniQuest quest, int questX, int questY, int mouseX, int mouseY) {
		GL11.glPushMatrix();
		boolean mouseInPanel = mouseX >= questX && mouseX < questX + qPanelWidth && mouseY >= questY && mouseY < questY + qPanelHeight;
		boolean mouseInDelete = mouseX >= questX + qDelX && mouseX < questX + qDelX + qWidgetSize && mouseY >= questY + qDelY && mouseY < questY + qDelY + qWidgetSize;
		boolean mouseInTrack = mouseX >= questX + qTrackX && mouseX < questX + qTrackX + qWidgetSize && mouseY >= questY + qTrackY && mouseY < questY + qTrackY + qWidgetSize;
		boolean isTracking = quest == getPlayerData().getTrackingMiniQuest();
		mc.getTextureManager().bindTexture(guiTexture_miniquests);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		if (mouseInPanel || quest == selectedMiniquest) {
			drawTexturedModalRect(questX, questY, 0, qPanelHeight, qPanelWidth, qPanelHeight);
		} else {
			drawTexturedModalRect(questX, questY, 0, 0, qPanelWidth, qPanelHeight);
		}
		float[] questRGB = quest.getQuestColorComponents();
		GL11.glColor4f(questRGB[0], questRGB[1], questRGB[2], 1.0f);
		GL11.glEnable(3008);
		drawTexturedModalRect(questX, questY, 0, qPanelHeight * 2, qPanelWidth, qPanelHeight);
		GL11.glDisable(3008);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		String questName = quest.entityName;
		String factionName = quest.getFactionSubtitle();
		if (quest.isFailed()) {
			questName = EnumChatFormatting.STRIKETHROUGH + questName;
			factionName = EnumChatFormatting.STRIKETHROUGH + factionName;
		}
		fontRendererObj.drawString(questName, questX + qPanelBorder, questY + qPanelBorder, 8019267);
		fontRendererObj.drawString(factionName, questX + qPanelBorder, questY + qPanelBorder + fontRendererObj.FONT_HEIGHT, 8019267);
		if (quest.isFailed()) {
			fontRendererObj.drawString(quest.getQuestFailureShorthand(), questX + qPanelBorder, questY + 25, 16711680);
		} else if (isTracking && trackTicks > 0) {
			fontRendererObj.drawString(StatCollector.translateToLocal("lotr.gui.redBook.mq.tracking"), questX + qPanelBorder, questY + 25, 8019267);
		} else {
			String objective = quest.getQuestObjective();
			int maxObjLength = qPanelWidth - qPanelBorder * 2 - 18;
			if (fontRendererObj.getStringWidth(objective) >= maxObjLength) {
				String ellipsis = "...";
				while (fontRendererObj.getStringWidth(objective + ellipsis) >= maxObjLength) {
					objective = objective.substring(0, objective.length() - 1);
					while (Character.isWhitespace(objective.charAt(objective.length() - 1))) {
						objective = objective.substring(0, objective.length() - 1);
					}
				}
				objective = objective + ellipsis;
			}
			fontRendererObj.drawString(objective, questX + qPanelBorder, questY + 25, 8019267);
			String progress = quest.getQuestProgress();
			if (quest.isCompleted()) {
				progress = StatCollector.translateToLocal("lotr.gui.redBook.mq.complete");
			}
			fontRendererObj.drawString(progress, questX + qPanelBorder, questY + 25 + fontRendererObj.FONT_HEIGHT, 8019267);
		}
		if (deletingMiniquest == null) {
			mc.getTextureManager().bindTexture(guiTexture_miniquests);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			int delU = qPanelWidth;
			int delV = 0;
			if (mouseInDelete) {
				delV += qWidgetSize;
			}
			drawTexturedModalRect(questX + qDelX, questY + qDelY, delU, delV, qWidgetSize, qWidgetSize);
			if (!viewCompleted) {
				int trackU = qPanelWidth + qWidgetSize;
				int trackV = 0;
				if (mouseInTrack) {
					trackV += qWidgetSize;
				}
				if (isTracking) {
					trackU += qWidgetSize;
				}
				drawTexturedModalRect(questX + qTrackX, questY + qTrackY, trackU, trackV, qWidgetSize, qWidgetSize);
			}
		}
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glDisable(2896);
		GL11.glEnable(32826);
		GL11.glEnable(2896);
		GL11.glEnable(2884);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		renderItem.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), quest.getQuestIcon(), questX + 149, questY + 24);
		GL11.glDisable(2896);
		GL11.glEnable(3008);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glPopMatrix();
	}

	public void setupScrollBar(int i, int j) {
		boolean isMouseDown = Mouse.isButtonDown(0);
		int i1 = i - guiLeft;
		int j1 = j - guiTop;
		mouseInDiary = selectedMiniquest != null && i1 >= diaryX && i1 < diaryX + diaryWidth && j1 >= diaryY && j1 < diaryY + diaryHeight;
		boolean mouseInScrollBar = i1 >= scrollBarX + scrollBarActiveXBorder && i1 < scrollBarX + scrollBarWidth - scrollBarActiveXBorder * 2 && j1 >= scrollBarY + scrollBarActiveYOffset && j1 < scrollBarY + scrollBarActiveYOffset + scrollBarActiveHeight;
		if (!wasMouseDown && isMouseDown) {
			if (mouseInScrollBar) {
				isScrolling = canScroll();
			} else if (mouseInDiary) {
				isDiaryScrolling = true;
			}
		}
		if (!isMouseDown) {
			isScrolling = false;
			isDiaryScrolling = false;
		}
		wasMouseDown = isMouseDown;
		if (isScrolling) {
			currentScroll = (j - (guiTop + scrollBarY + scrollBarActiveYOffset) - scrollWidgetHeight / 2.0f) / ((float) scrollBarActiveHeight - scrollWidgetHeight);
			currentScroll = Math.max(currentScroll, 0.0f);
			currentScroll = Math.min(currentScroll, 1.0f);
		} else if (isDiaryScrolling) {
			float d = (float) (lastMouseY - j) / fontRendererObj.FONT_HEIGHT;
			diaryScroll -= d;
		}
		lastMouseY = j;
	}

	public void trackOrUntrack(LOTRMiniQuest quest) {
		LOTRMiniQuest tracking = getPlayerData().getTrackingMiniQuest();
		LOTRMiniQuest newTracking;
		newTracking = quest == tracking ? null : quest;
		IMessage packet = new LOTRPacketMiniquestTrack(newTracking);
		LOTRPacketHandler.networkWrapper.sendToServer(packet);
		getPlayerData().setTrackingMiniQuest(newTracking);
		trackTicks = 40;
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		if (trackTicks > 0) {
			--trackTicks;
		}
	}

	public enum Page {
		MINIQUESTS("miniquests");

		public String name;

		Page(String s) {
			name = s;
		}

		public String getTitle() {
			return StatCollector.translateToLocal("lotr.gui.redBook.page." + name);
		}
	}

}
