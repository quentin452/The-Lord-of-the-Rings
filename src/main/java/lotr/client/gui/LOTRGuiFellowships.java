package lotr.client.gui;

import com.google.common.math.IntMath;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.LOTRConfig;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.LOTRTitle;
import lotr.common.fellowship.LOTRFellowshipClient;
import lotr.common.network.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.*;

public class LOTRGuiFellowships extends LOTRGuiMenuBase {
	public static ResourceLocation iconsTextures = new ResourceLocation("lotr:gui/fellowships.png");
	public static int entrySplit = 5;
	public static int entryBorder = 10;
	public static int selectBorder = 2;
	public Page page = Page.LIST;
	public List<LOTRFellowshipClient> allFellowshipsLeading = new ArrayList<>();
	public List<LOTRFellowshipClient> allFellowshipsOther = new ArrayList<>();
	public List<LOTRFellowshipClient> allFellowshipInvites = new ArrayList<>();
	public LOTRFellowshipClient mouseOverFellowship;
	public LOTRFellowshipClient viewingFellowship;
	public UUID mouseOverPlayer;
	public boolean mouseOverPlayerRemove;
	public boolean mouseOverPlayerOp;
	public boolean mouseOverPlayerDeop;
	public boolean mouseOverPlayerTransfer;
	public UUID removingPlayer;
	public UUID oppingPlayer;
	public UUID deoppingPlayer;
	public UUID transferringPlayer;
	public boolean mouseOverInviteAccept;
	public boolean mouseOverInviteReject;
	public LOTRPacketFellowshipAcceptInviteResult.AcceptInviteResult acceptInviteResult;
	public String acceptInviteResultFellowshipName;
	public GuiButton buttonCreate;
	public GuiButton buttonCreateThis;
	public LOTRGuiButtonFsOption buttonInvitePlayer;
	public GuiButton buttonInviteThis;
	public LOTRGuiButtonFsOption buttonDisband;
	public GuiButton buttonDisbandThis;
	public GuiButton buttonLeave;
	public GuiButton buttonLeaveThis;
	public LOTRGuiButtonFsOption buttonSetIcon;
	public GuiButton buttonRemove;
	public GuiButton buttonTransfer;
	public LOTRGuiButtonFsOption buttonRename;
	public GuiButton buttonRenameThis;
	public GuiButton buttonBack;
	public GuiButton buttonInvites;
	public LOTRGuiButtonFsOption buttonPVP;
	public LOTRGuiButtonFsOption buttonHiredFF;
	public LOTRGuiButtonFsOption buttonMapShow;
	public GuiButton buttonOp;
	public GuiButton buttonDeop;
	public Collection<LOTRGuiButtonFsOption> orderedFsOptionButtons = new ArrayList<>();
	public GuiTextField textFieldName;
	public GuiTextField textFieldPlayer;
	public GuiTextField textFieldRename;
	public int scrollBarX;
	public LOTRGuiScrollPane scrollPaneLeading;
	public LOTRGuiScrollPane scrollPaneOther;
	public LOTRGuiScrollPane scrollPaneMembers;
	public LOTRGuiScrollPane scrollPaneInvites;
	public int displayedFellowshipsLeading;
	public int displayedFellowshipsOther;
	public int displayedMembers;
	public int displayedInvites;
	public int tickCounter;

	public LOTRGuiFellowships() {
		xSize = 256;
		int scrollWidgetWidth = 9;
		int scrollWidgetHeight = 8;
		scrollBarX = xSize + 2 + 1;
		scrollPaneLeading = new LOTRGuiScrollPane(scrollWidgetWidth, scrollWidgetHeight);
		scrollPaneOther = new LOTRGuiScrollPane(scrollWidgetWidth, scrollWidgetHeight);
		scrollPaneMembers = new LOTRGuiScrollPane(scrollWidgetWidth, scrollWidgetHeight);
		scrollPaneInvites = new LOTRGuiScrollPane(scrollWidgetWidth, scrollWidgetHeight);
	}

	public static boolean isPlayerOnline(GameProfile player) {
		EntityClientPlayerMP mcPlayer = Minecraft.getMinecraft().thePlayer;
		List list = mcPlayer.sendQueue.playerInfoList;
		for (Object obj : list) {
			GuiPlayerInfo info = (GuiPlayerInfo) obj;
			if (!info.name.equalsIgnoreCase(player.getName())) {
				continue;
			}
			return true;
		}
		return false;
	}

	public void acceptInvitation(LOTRFellowshipClient invite) {
		IMessage packet = new LOTRPacketFellowshipRespondInvite(invite, true);
		LOTRPacketHandler.networkWrapper.sendToServer(packet);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button == buttonCreate) {
				page = Page.CREATE;
			} else if (button == buttonCreateThis) {
				String name = textFieldName.getText();
				if (checkValidFellowshipName(name) == null) {
					name = StringUtils.trim(name);
					IMessage packet = new LOTRPacketFellowshipCreate(name);
					LOTRPacketHandler.networkWrapper.sendToServer(packet);
				}
				page = Page.LIST;
			} else if (button == buttonInvitePlayer) {
				page = Page.INVITE;
			} else if (button == buttonInviteThis) {
				String name = textFieldPlayer.getText();
				if (checkValidPlayerName(name) == null) {
					name = StringUtils.trim(name);
					IMessage packet = new LOTRPacketFellowshipInvitePlayer(viewingFellowship, name);
					LOTRPacketHandler.networkWrapper.sendToServer(packet);
				}
				page = Page.FELLOWSHIP;
			} else if (button == buttonDisband) {
				page = Page.DISBAND;
			} else if (button == buttonDisbandThis) {
				IMessage packet = new LOTRPacketFellowshipDisband(viewingFellowship);
				LOTRPacketHandler.networkWrapper.sendToServer(packet);
				page = Page.LIST;
			} else if (button == buttonLeave) {
				page = Page.LEAVE;
			} else if (button == buttonLeaveThis) {
				IMessage packet = new LOTRPacketFellowshipLeave(viewingFellowship);
				LOTRPacketHandler.networkWrapper.sendToServer(packet);
				page = Page.LIST;
			} else if (button == buttonSetIcon) {
				IMessage packet = new LOTRPacketFellowshipSetIcon(viewingFellowship);
				LOTRPacketHandler.networkWrapper.sendToServer(packet);
			} else if (button == buttonRemove) {
				IMessage packet = new LOTRPacketFellowshipDoPlayer(viewingFellowship, removingPlayer, LOTRPacketFellowshipDoPlayer.PlayerFunction.REMOVE);
				LOTRPacketHandler.networkWrapper.sendToServer(packet);
				page = Page.FELLOWSHIP;
			} else if (button == buttonOp) {
				IMessage packet = new LOTRPacketFellowshipDoPlayer(viewingFellowship, oppingPlayer, LOTRPacketFellowshipDoPlayer.PlayerFunction.OP);
				LOTRPacketHandler.networkWrapper.sendToServer(packet);
				page = Page.FELLOWSHIP;
			} else if (button == buttonDeop) {
				IMessage packet = new LOTRPacketFellowshipDoPlayer(viewingFellowship, deoppingPlayer, LOTRPacketFellowshipDoPlayer.PlayerFunction.DEOP);
				LOTRPacketHandler.networkWrapper.sendToServer(packet);
				page = Page.FELLOWSHIP;
			} else if (button == buttonTransfer) {
				IMessage packet = new LOTRPacketFellowshipDoPlayer(viewingFellowship, transferringPlayer, LOTRPacketFellowshipDoPlayer.PlayerFunction.TRANSFER);
				LOTRPacketHandler.networkWrapper.sendToServer(packet);
				page = Page.FELLOWSHIP;
			} else if (button == buttonRename) {
				page = Page.RENAME;
			} else if (button == buttonRenameThis) {
				String name = textFieldRename.getText();
				if (checkValidFellowshipName(name) == null) {
					name = StringUtils.trim(name);
					IMessage packet = new LOTRPacketFellowshipRename(viewingFellowship, name);
					LOTRPacketHandler.networkWrapper.sendToServer(packet);
				}
				page = Page.FELLOWSHIP;
			} else if (button == buttonBack) {
				keyTyped('E', 1);
			} else if (button == buttonInvites) {
				page = Page.INVITATIONS;
			} else if (button == buttonPVP) {
				IMessage packet = new LOTRPacketFellowshipToggle(viewingFellowship, LOTRPacketFellowshipToggle.ToggleFunction.PVP);
				LOTRPacketHandler.networkWrapper.sendToServer(packet);
			} else if (button == buttonHiredFF) {
				IMessage packet = new LOTRPacketFellowshipToggle(viewingFellowship, LOTRPacketFellowshipToggle.ToggleFunction.HIRED_FF);
				LOTRPacketHandler.networkWrapper.sendToServer(packet);
			} else if (button == buttonMapShow) {
				IMessage packet = new LOTRPacketFellowshipToggle(viewingFellowship, LOTRPacketFellowshipToggle.ToggleFunction.MAP_SHOW);
				LOTRPacketHandler.networkWrapper.sendToServer(packet);
			} else {
				super.actionPerformed(button);
			}
		}
	}

	public void alignOptionButtons() {
		Collection<GuiButton> activeOptionButtons = new ArrayList<>();
		for (GuiButton button : orderedFsOptionButtons) {
			if (!button.visible) {
				continue;
			}
			activeOptionButtons.add(button);
		}
		if (buttonLeave.visible) {
			activeOptionButtons.add(buttonLeave);
		}
		int midX = guiLeft + xSize / 2;
		int numActive = activeOptionButtons.size();
		if (numActive > 0) {
			int gap = 8;
			int allWidth = 0;
			for (GuiButton button : activeOptionButtons) {
				if (allWidth > 0) {
					allWidth += gap;
				}
				allWidth += button.width;
			}
			int x = midX - allWidth / 2;
			for (GuiButton activeOptionButton : activeOptionButtons) {
				activeOptionButton.xPosition = x;
				x += activeOptionButton.width;
				x += gap;
			}
		}
	}

	public void buttonSound() {
		buttonBack.func_146113_a(mc.getSoundHandler());
	}

	public String checkValidFellowshipName(String name) {
		if (!StringUtils.isWhitespace(name)) {
			if (LOTRLevelData.getData(mc.thePlayer).anyMatchingFellowshipNames(name, true)) {
				return StatCollector.translateToLocal("lotr.gui.fellowships.nameExists");
			}
			return null;
		}
		return "";
	}

	public String checkValidPlayerName(String name) {
		if (!StringUtils.isWhitespace(name)) {
			if (viewingFellowship.containsPlayerUsername(name)) {
				return StatCollector.translateToLocalFormatted("lotr.gui.fellowships.playerExists", name);
			}
			return null;
		}
		return "";
	}

	public void clearMouseOverFellowship() {
		mouseOverFellowship = null;
	}

	public int countOnlineMembers(LOTRFellowshipClient fs) {
		int i = 0;
		List<GameProfile> allPlayers = fs.getAllPlayerProfiles();
		for (GameProfile player : allPlayers) {
			if (!isPlayerOnline(player)) {
				continue;
			}
			++i;
		}
		return i;
	}

	public void displayAcceptInvitationResult(UUID fellowshipID, String name, LOTRPacketFellowshipAcceptInviteResult.AcceptInviteResult result) {
		if (page == Page.ACCEPT_INVITE_RESULT) {
			if (result == LOTRPacketFellowshipAcceptInviteResult.AcceptInviteResult.JOINED) {
				page = Page.FELLOWSHIP;
				viewingFellowship = LOTRLevelData.getData(mc.thePlayer).getClientFellowshipByID(fellowshipID);
			} else {
				acceptInviteResult = result;
				acceptInviteResultFellowshipName = name;
			}
		}
	}

	public void drawFellowshipEntry(LOTRFellowshipClient fs, int x, int y, int mouseX, int mouseY, boolean isInvite) {
		drawFellowshipEntry(fs, x, y, mouseX, mouseY, isInvite, xSize);
	}

	public void drawFellowshipEntry(LOTRFellowshipClient fs, int x, int y, int mouseX, int mouseY, boolean isInvite, int selectWidth) {
		int selectX0 = x - 2;
		int selectX1 = x + selectWidth + 2;
		int selectY0 = y - 2;
		int selectY1 = y + fontRendererObj.FONT_HEIGHT + 2;
		if (mouseX >= selectX0 && mouseX <= selectX1 && mouseY >= selectY0 && mouseY <= selectY1) {
			Gui.drawRect(selectX0, selectY0, selectX1, selectY1, 1442840575);
			mouseOverFellowship = fs;
		}
		boolean isMouseOver = mouseOverFellowship == fs;
		drawFellowshipIcon(fs, x, y, 0.5f);
		String fsName = fs.getName();
		int maxLength = 110;
		if (fontRendererObj.getStringWidth(fsName) > maxLength) {
			String ellipsis = "...";
			while (fontRendererObj.getStringWidth(fsName + ellipsis) > maxLength) {
				fsName = fsName.substring(0, fsName.length() - 1);
			}
			fsName = fsName + ellipsis;
		}
		GameProfile owner = fs.getOwnerProfile();
		boolean ownerOnline = isPlayerOnline(owner);
		fontRendererObj.drawString(fsName, x + 15, y, 16777215);
		fontRendererObj.drawString(owner.getName(), x + 130, y, ownerOnline ? 16777215 : isMouseOver ? 12303291 : 7829367);
		if (isInvite) {
			int iconWidth = 8;
			int iconAcceptX = x + xSize - 18;
			int iconRejectX = x + xSize - 8;
			boolean accept = false;
			boolean reject = false;
			if (isMouseOver) {
				accept = mouseOverInviteAccept = mouseX >= iconAcceptX && mouseX <= iconAcceptX + iconWidth && mouseY >= y && mouseY <= y + iconWidth;
				reject = mouseOverInviteReject = mouseX >= iconRejectX && mouseX <= iconRejectX + iconWidth && mouseY >= y && mouseY <= y + iconWidth;
			}
			mc.getTextureManager().bindTexture(iconsTextures);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			drawTexturedModalRect(iconAcceptX, y, 16, 16 + (accept ? 0 : iconWidth), iconWidth, iconWidth);
			drawTexturedModalRect(iconRejectX, y, 8, 16 + (reject ? 0 : iconWidth), iconWidth, iconWidth);
		} else {
			String memberCount = String.valueOf(fs.getPlayerCount());
			String onlineMemberCount = countOnlineMembers(fs) + " | ";
			fontRendererObj.drawString(memberCount, x + xSize - fontRendererObj.getStringWidth(memberCount), y, isMouseOver ? 12303291 : 7829367);
			fontRendererObj.drawString(onlineMemberCount, x + xSize - fontRendererObj.getStringWidth(memberCount) - fontRendererObj.getStringWidth(onlineMemberCount), y, 16777215);
		}
	}

	public void drawFellowshipIcon(LOTRFellowshipClient fsClient, int x, int y, float scale) {
		ItemStack fsIcon = fsClient.getIcon();
		if (fsIcon != null) {
			GL11.glDisable(3042);
			GL11.glDisable(3008);
			RenderHelper.enableGUIStandardItemLighting();
			GL11.glDisable(2896);
			GL11.glEnable(32826);
			GL11.glEnable(2896);
			GL11.glEnable(2884);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			GL11.glPushMatrix();
			GL11.glScalef(scale, scale, 1.0f);
			renderItem.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), fsIcon, Math.round(x / scale), Math.round(y / scale));
			GL11.glPopMatrix();
			GL11.glDisable(2896);
		}
	}

	public void drawPlayerEntry(GameProfile player, int x, int y, int titleOffset, int mouseX, int mouseY) {
		UUID playerUuid = player.getId();
		String playerUsername = player.getName();
		int selectX0 = x - 2;
		int selectX1 = x + xSize + 2;
		int selectY0 = y - 2;
		int selectY1 = y + fontRendererObj.FONT_HEIGHT + 2;
		if (mouseX >= selectX0 && mouseX <= selectX1 && mouseY >= selectY0 && mouseY <= selectY1) {
			Gui.drawRect(selectX0, selectY0, selectX1, selectY1, 1442840575);
			mouseOverPlayer = playerUuid;
		}
		boolean isMouseOver = playerUuid.equals(mouseOverPlayer);
		String titleName = null;
		LOTRTitle.PlayerTitle title = viewingFellowship.getTitleFor(playerUuid);
		if (title != null) {
			titleName = title.getFormattedTitle(mc.thePlayer);
		}
		if (titleName != null) {
			fontRendererObj.drawString(titleName, x, y, 16777215);
		}
		fontRendererObj.drawString(playerUsername, x + titleOffset, y, isPlayerOnline(player) ? 16777215 : isMouseOver ? 12303291 : 7829367);
		boolean isOwner = viewingFellowship.getOwnerUuid().equals(playerUuid);
		boolean isAdmin = viewingFellowship.isAdmin(playerUuid);
		if (isOwner) {
			mc.getTextureManager().bindTexture(iconsTextures);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			drawTexturedModalRect(x + titleOffset + fontRendererObj.getStringWidth(playerUsername + " "), y, 0, 0, 8, 8);
		} else if (isAdmin) {
			mc.getTextureManager().bindTexture(iconsTextures);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			drawTexturedModalRect(x + titleOffset + fontRendererObj.getStringWidth(playerUsername + " "), y, 8, 0, 8, 8);
		}
		boolean owned = viewingFellowship.isOwned();
		boolean adminned = viewingFellowship.isAdminned();
		if (!isOwner && (owned || adminned)) {
			int iconWidth = 8;
			int iconRemoveX = x + xSize - 28;
			int iconOpDeopX = x + xSize - 18;
			int iconTransferX = x + xSize - 8;
			if (adminned) {
				iconRemoveX = x + xSize - 8;
			}
			boolean remove = false;
			boolean opDeop = false;
			boolean transfer = false;
			if (isMouseOver) {
				remove = mouseOverPlayerRemove = mouseX >= iconRemoveX && mouseX <= iconRemoveX + iconWidth && mouseY >= y && mouseY <= y + iconWidth;
				if (owned) {
					opDeop = isAdmin ? (mouseOverPlayerDeop = mouseX >= iconOpDeopX && mouseX <= iconOpDeopX + iconWidth && mouseY >= y && mouseY <= y + iconWidth) : (mouseOverPlayerOp = mouseX >= iconOpDeopX && mouseX <= iconOpDeopX + iconWidth && mouseY >= y && mouseY <= y + iconWidth);
					transfer = mouseOverPlayerTransfer = mouseX >= iconTransferX && mouseX <= iconTransferX + iconWidth && mouseY >= y && mouseY <= y + iconWidth;
				}
			}
			mc.getTextureManager().bindTexture(iconsTextures);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			drawTexturedModalRect(iconRemoveX, y, 8, 16 + (remove ? 0 : iconWidth), iconWidth, iconWidth);
			if (owned) {
				if (isAdmin) {
					drawTexturedModalRect(iconOpDeopX, y, 32, 16 + (opDeop ? 0 : iconWidth), iconWidth, iconWidth);
				} else {
					drawTexturedModalRect(iconOpDeopX, y, 24, 16 + (opDeop ? 0 : iconWidth), iconWidth, iconWidth);
				}
				drawTexturedModalRect(iconTransferX, y, 0, 16 + (transfer ? 0 : iconWidth), iconWidth, iconWidth);
			}
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		LOTRPlayerData playerData = LOTRLevelData.getData(mc.thePlayer);
		boolean viewingOwned = (viewingFellowship != null && viewingFellowship.isOwned());
		boolean viewingAdminned = (viewingFellowship != null && viewingFellowship.isAdminned());
		mouseOverFellowship = null;
		mouseOverPlayer = null;
		mouseOverPlayerRemove = false;
		mouseOverPlayerOp = false;
		mouseOverPlayerDeop = false;
		mouseOverPlayerTransfer = false;
		if (page != Page.REMOVE) removingPlayer = null;
		if (page != Page.OP) oppingPlayer = null;
		if (page != Page.DEOP) deoppingPlayer = null;
		if (page != Page.TRANSFER) transferringPlayer = null;
		mouseOverInviteAccept = false;
		mouseOverInviteReject = false;
		if (page != Page.ACCEPT_INVITE_RESULT) {
			acceptInviteResult = null;
			acceptInviteResultFellowshipName = null;
		}
		boolean creationEnabled = LOTRConfig.isFellowshipCreationEnabled(mc.theWorld);
		boolean canPlayerCreateNew = playerData.canCreateFellowships(true);
		buttonCreate.visible = (page == Page.LIST);
		buttonCreate.enabled = (buttonCreate.visible && creationEnabled && canPlayerCreateNew);
		buttonCreateThis.visible = (page == Page.CREATE);
		String checkValidName = checkValidFellowshipName(textFieldName.getText());
		buttonCreateThis.enabled = (buttonCreateThis.visible && checkValidName == null);
		buttonInvitePlayer.visible = buttonInvitePlayer.enabled = (page == Page.FELLOWSHIP && (viewingOwned || viewingAdminned));
		boolean canInvite = (page == Page.INVITE && !isFellowshipMaxSize(viewingFellowship));
		buttonInviteThis.visible = canInvite;
		String checkValidPlayer = "";
		if (canInvite) {
			checkValidPlayer = checkValidPlayerName(textFieldPlayer.getText());
			buttonInviteThis.enabled = (buttonInviteThis.visible && checkValidPlayer == null);
		}
		buttonDisband.visible = buttonDisband.enabled = (page == Page.FELLOWSHIP && viewingOwned);
		buttonDisbandThis.visible = buttonDisbandThis.enabled = (page == Page.DISBAND);
		buttonLeave.visible = buttonLeave.enabled = (page == Page.FELLOWSHIP && !viewingOwned);
		buttonLeaveThis.visible = buttonLeaveThis.enabled = (page == Page.LEAVE);
		buttonSetIcon.visible = buttonSetIcon.enabled = (page == Page.FELLOWSHIP && (viewingOwned || viewingAdminned));
		buttonRemove.visible = buttonRemove.enabled = (page == Page.REMOVE);
		buttonTransfer.visible = buttonTransfer.enabled = (page == Page.TRANSFER);
		buttonRename.visible = buttonRename.enabled = (page == Page.FELLOWSHIP && viewingOwned);
		buttonRenameThis.visible = (page == Page.RENAME);
		String checkValidRename = checkValidFellowshipName(textFieldRename.getText());
		buttonRenameThis.enabled = (buttonRenameThis.visible && checkValidRename == null);
		buttonBack.visible = buttonBack.enabled = (page != Page.LIST);
		buttonInvites.visible = buttonInvites.enabled = (page == Page.LIST);
		buttonPVP.visible = buttonPVP.enabled = (page == Page.FELLOWSHIP && (viewingOwned || viewingAdminned));
		if (buttonPVP.enabled) buttonPVP.setIconUV(64, viewingFellowship.getPreventPVP() ? 80 : 48);
		buttonHiredFF.visible = buttonHiredFF.enabled = (page == Page.FELLOWSHIP && (viewingOwned || viewingAdminned));
		if (buttonHiredFF.enabled)
			buttonHiredFF.setIconUV(80, viewingFellowship.getPreventHiredFriendlyFire() ? 80 : 48);
		buttonMapShow.visible = buttonMapShow.enabled = (page == Page.FELLOWSHIP && viewingOwned);
		if (buttonMapShow.enabled)
			buttonMapShow.setIconUV(96, viewingFellowship.getShowMapLocations() ? 48 : 80);
		buttonOp.visible = buttonOp.enabled = (page == Page.OP);
		buttonDeop.visible = buttonDeop.enabled = (page == Page.DEOP);
		alignOptionButtons();
		setupScrollBars(i, j);
		drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		super.drawScreen(i, j, f);
		StringBuilder s = new StringBuilder(StatCollector.translateToLocal("lotr.gui.fellowships.title"));
		drawCenteredString(s.toString(), guiLeft + xSize / 2, guiTop - 30, 16777215);
		if (page == Page.LIST) {
			int x = guiLeft;
			int y = scrollPaneLeading.paneY0;
			s = new StringBuilder(StatCollector.translateToLocal("lotr.gui.fellowships.leading"));
			drawCenteredString(s.toString(), guiLeft + xSize / 2, y, 16777215);
			y += fontRendererObj.FONT_HEIGHT + 10;
			List<LOTRFellowshipClient> sortedLeading = sortFellowshipsForDisplay(allFellowshipsLeading);
			int[] leadingMinMax = scrollPaneLeading.getMinMaxIndices(sortedLeading, displayedFellowshipsLeading);
			for (int index = leadingMinMax[0]; index <= leadingMinMax[1]; index++) {
				LOTRFellowshipClient fs = sortedLeading.get(index);
				drawFellowshipEntry(fs, x, y, i, j, false);
				y += fontRendererObj.FONT_HEIGHT + 5;
			}
			y = scrollPaneOther.paneY0;
			s = new StringBuilder(StatCollector.translateToLocal("lotr.gui.fellowships.member"));
			drawCenteredString(s.toString(), guiLeft + xSize / 2, y, 16777215);
			y += fontRendererObj.FONT_HEIGHT + 10;
			List<LOTRFellowshipClient> sortedOther = sortFellowshipsForDisplay(allFellowshipsOther);
			int[] otherMinMax = scrollPaneOther.getMinMaxIndices(sortedOther, displayedFellowshipsOther);
			for (int k = otherMinMax[0]; k <= otherMinMax[1]; k++) {
				LOTRFellowshipClient fs = sortedOther.get(k);
				drawFellowshipEntry(fs, x, y, i, j, false);
				y += fontRendererObj.FONT_HEIGHT + 5;
			}
			String invites = String.valueOf(playerData.getClientFellowshipInvites().size());
			int invitesX = buttonInvites.xPosition - 2 - fontRendererObj.getStringWidth(invites);
			int invitesY = buttonInvites.yPosition + buttonInvites.height / 2 - fontRendererObj.FONT_HEIGHT / 2;
			fontRendererObj.drawString(invites, invitesX, invitesY, 16777215);
			if (buttonInvites.func_146115_a())
				renderIconTooltip(i, j, StatCollector.translateToLocal("lotr.gui.fellowships.invitesTooltip"));
			if (buttonCreate.func_146115_a()) if (!creationEnabled) {
				s = new StringBuilder(StatCollector.translateToLocal("lotr.gui.fellowships.creationDisabled"));
				drawCenteredString(s.toString(), guiLeft + xSize / 2, buttonCreate.yPosition + buttonCreate.height + 4, 16777215);
			} else if (!canPlayerCreateNew) {
				s = new StringBuilder(StatCollector.translateToLocal("lotr.gui.fellowships.createLimit"));
				drawCenteredString(s.toString(), guiLeft + xSize / 2, buttonCreate.yPosition + buttonCreate.height + 4, 16777215);
			}
			if (scrollPaneLeading.hasScrollBar) scrollPaneLeading.drawScrollBar();
			if (scrollPaneOther.hasScrollBar) scrollPaneOther.drawScrollBar();
		} else if (page == Page.CREATE) {
			s = new StringBuilder(StatCollector.translateToLocal("lotr.gui.fellowships.createName"));
			drawCenteredString(s.toString(), guiLeft + xSize / 2, textFieldName.yPosition - 4 - fontRendererObj.FONT_HEIGHT, 16777215);
			textFieldName.drawTextBox();
			if (checkValidName != null)
				drawCenteredString(checkValidName, guiLeft + xSize / 2, textFieldName.yPosition + textFieldName.height + fontRendererObj.FONT_HEIGHT, 16711680);
		} else if (page == Page.FELLOWSHIP) {
			int x = guiLeft;
			int y = guiTop + 10;
			s = new StringBuilder(StatCollector.translateToLocalFormatted("lotr.gui.fellowships.nameAndPlayers", viewingFellowship.getName(), viewingFellowship.getPlayerCount()));
			drawCenteredString(s.toString(), guiLeft + xSize / 2, y, 16777215);
			y += fontRendererObj.FONT_HEIGHT;
			y += 5;
			if (viewingFellowship.getIcon() != null)
				drawFellowshipIcon(viewingFellowship, guiLeft + xSize / 2 - 8, y, 1.0F);
			boolean preventPVP = viewingFellowship.getPreventPVP();
			boolean preventHiredFF = viewingFellowship.getPreventHiredFriendlyFire();
			boolean mapShow = viewingFellowship.getShowMapLocations();
			int iconPVPX = guiLeft + xSize - 36;
			int iconHFFX = guiLeft + xSize - 16;
			int iconMapX = guiLeft + xSize - 56;
			int iconY = y;
			int iconSize = 16;
			mc.getTextureManager().bindTexture(iconsTextures);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			drawTexturedModalRect(iconPVPX, iconY, 64, preventPVP ? 80 : 48, iconSize, iconSize);
			drawTexturedModalRect(iconHFFX, iconY, 80, preventHiredFF ? 80 : 48, iconSize, iconSize);
			drawTexturedModalRect(iconMapX, iconY, 96, mapShow ? 48 : 80, iconSize, iconSize);
			if (i >= iconPVPX && i < iconPVPX + iconSize && j >= iconY && j < iconY + iconSize)
				renderIconTooltip(i, j, StatCollector.translateToLocal(preventPVP ? "lotr.gui.fellowships.pvp.prevent" : "lotr.gui.fellowships.pvp.allow"));
			if (i >= iconHFFX && i < iconHFFX + iconSize && j >= iconY && j < iconY + iconSize)
				renderIconTooltip(i, j, StatCollector.translateToLocal(preventHiredFF ? "lotr.gui.fellowships.hiredFF.prevent" : "lotr.gui.fellowships.hiredFF.allow"));
			if (i >= iconMapX && i < iconMapX + iconSize && j >= iconY && j < iconY + iconSize)
				renderIconTooltip(i, j, StatCollector.translateToLocal(mapShow ? "lotr.gui.fellowships.mapShow.on" : "lotr.gui.fellowships.mapShow.off"));
			y += iconSize;
			y += 10;
			int titleOffset = 0;
			for (UUID playerUuid : viewingFellowship.getAllPlayerUuids()) {
				LOTRTitle.PlayerTitle title = viewingFellowship.getTitleFor(playerUuid);
				if (title != null) {
					String titleName = title.getFormattedTitle(mc.thePlayer);
					int thisTitleWidth = fontRendererObj.getStringWidth(titleName + " ");
					titleOffset = Math.max(titleOffset, thisTitleWidth);
				}
			}
			drawPlayerEntry(viewingFellowship.getOwnerProfile(), x, y, titleOffset, i, j);
			y += fontRendererObj.FONT_HEIGHT + 10;
			List<GameProfile> membersSorted = sortMembersForDisplay(viewingFellowship);
			int[] membersMinMax = scrollPaneMembers.getMinMaxIndices(membersSorted, displayedMembers);
			for (int index = membersMinMax[0]; index <= membersMinMax[1]; index++) {
				GameProfile member = membersSorted.get(index);
				drawPlayerEntry(member, x, y, titleOffset, i, j);
				y += fontRendererObj.FONT_HEIGHT + 5;
			}
			for (Object bObj : buttonList) {
				GuiButton button = (GuiButton) bObj;
				if (button instanceof LOTRGuiButtonFsOption && button.visible && button.func_146115_a()) {
					s = new StringBuilder(button.displayString);
					drawCenteredString(s.toString(), guiLeft + xSize / 2, button.yPosition + button.height + 4, 16777215);
				}
			}
			if (scrollPaneMembers.hasScrollBar) scrollPaneMembers.drawScrollBar();
		} else if (page == Page.INVITE) {
			if (isFellowshipMaxSize(viewingFellowship)) {
				int x = guiLeft + xSize / 2;
				int y = guiTop + 30;
				s = new StringBuilder(StatCollector.translateToLocalFormatted("lotr.gui.fellowships.invite.maxSize", viewingFellowship.getName(), LOTRConfig.getFellowshipMaxSize(mc.theWorld)));
				List<String> lines = fontRendererObj.listFormattedStringToWidth(s.toString(), xSize);
				for (String line : lines) {
					drawCenteredString(line, x, y, 16777215);
					y += fontRendererObj.FONT_HEIGHT;
				}
			} else {
				s = new StringBuilder(StatCollector.translateToLocalFormatted("lotr.gui.fellowships.inviteName", viewingFellowship.getName()));
				drawCenteredString(s.toString(), guiLeft + xSize / 2, textFieldPlayer.yPosition - 4 - fontRendererObj.FONT_HEIGHT, 16777215);
				textFieldPlayer.drawTextBox();
				if (checkValidPlayer != null)
					drawCenteredString(checkValidPlayer, guiLeft + xSize / 2, textFieldPlayer.yPosition + textFieldPlayer.height + fontRendererObj.FONT_HEIGHT, 16711680);
			}
		} else if (page == Page.DISBAND) {
			int x = guiLeft + xSize / 2;
			int y = guiTop + 30;
			s = new StringBuilder(StatCollector.translateToLocalFormatted("lotr.gui.fellowships.disbandCheck1", viewingFellowship.getName()));
			drawCenteredString(s.toString(), x, y, 16777215);
			y += fontRendererObj.FONT_HEIGHT;
			s = new StringBuilder(StatCollector.translateToLocal("lotr.gui.fellowships.disbandCheck2"));
			drawCenteredString(s.toString(), x, y, 16777215);
			y += fontRendererObj.FONT_HEIGHT * 2;
			s = new StringBuilder(StatCollector.translateToLocal("lotr.gui.fellowships.disbandCheck3"));
			drawCenteredString(s.toString(), x, y, 16777215);
		} else if (page == Page.LEAVE) {
			int x = guiLeft + xSize / 2;
			int y = guiTop + 30;
			s = new StringBuilder(StatCollector.translateToLocalFormatted("lotr.gui.fellowships.leaveCheck1", viewingFellowship.getName()));
			drawCenteredString(s.toString(), x, y, 16777215);
			y += fontRendererObj.FONT_HEIGHT;
			s = new StringBuilder(StatCollector.translateToLocal("lotr.gui.fellowships.leaveCheck2"));
			drawCenteredString(s.toString(), x, y, 16777215);
		} else if (page == Page.REMOVE) {
			int x = guiLeft + xSize / 2;
			int y = guiTop + 30;
			s = new StringBuilder(StatCollector.translateToLocalFormatted("lotr.gui.fellowships.removeCheck", viewingFellowship.getName(), viewingFellowship.getUsernameFor(removingPlayer)));
			List<String> lines = fontRendererObj.listFormattedStringToWidth(s.toString(), xSize);
			for (String line : lines) {
				drawCenteredString(line, x, y, 16777215);
				y += fontRendererObj.FONT_HEIGHT;
			}
		} else if (page == Page.OP) {
			int x = guiLeft + xSize / 2;
			int y = guiTop + 30;
			s = new StringBuilder(StatCollector.translateToLocalFormatted("lotr.gui.fellowships.opCheck1", viewingFellowship.getName(), viewingFellowship.getUsernameFor(oppingPlayer)));
			List<String> lines = fontRendererObj.listFormattedStringToWidth(s.toString(), xSize);
			for (String line : lines) {
				drawCenteredString(line, x, y, 16777215);
				y += fontRendererObj.FONT_HEIGHT;
			}
			y += fontRendererObj.FONT_HEIGHT;
			s = new StringBuilder(StatCollector.translateToLocalFormatted("lotr.gui.fellowships.opCheck2", viewingFellowship.getName(), viewingFellowship.getUsernameFor(oppingPlayer)));
			lines = fontRendererObj.listFormattedStringToWidth(s.toString(), xSize);
			for (String line : lines) {
				drawCenteredString(line, x, y, 16777215);
				y += fontRendererObj.FONT_HEIGHT;
			}
		} else if (page == Page.DEOP) {
			int x = guiLeft + xSize / 2;
			int y = guiTop + 30;
			s = new StringBuilder(StatCollector.translateToLocalFormatted("lotr.gui.fellowships.deopCheck", viewingFellowship.getName(), viewingFellowship.getUsernameFor(deoppingPlayer)));
			List<String> lines = fontRendererObj.listFormattedStringToWidth(s.toString(), xSize);
			for (String line : lines) {
				drawCenteredString(line, x, y, 16777215);
				y += fontRendererObj.FONT_HEIGHT;
			}
		} else if (page == Page.TRANSFER) {
			int x = guiLeft + xSize / 2;
			int y = guiTop + 30;
			s = new StringBuilder(StatCollector.translateToLocalFormatted("lotr.gui.fellowships.transferCheck1", viewingFellowship.getName(), viewingFellowship.getUsernameFor(transferringPlayer)));
			List<String> lines = fontRendererObj.listFormattedStringToWidth(s.toString(), xSize);
			for (String line : lines) {
				drawCenteredString(line, x, y, 16777215);
				y += fontRendererObj.FONT_HEIGHT;
			}
			y += fontRendererObj.FONT_HEIGHT;
			s = new StringBuilder(StatCollector.translateToLocal("lotr.gui.fellowships.transferCheck2"));
			drawCenteredString(s.toString(), x, y, 16777215);
		} else if (page == Page.RENAME) {
			s = new StringBuilder(StatCollector.translateToLocalFormatted("lotr.gui.fellowships.renameName", viewingFellowship.getName()));
			drawCenteredString(s.toString(), guiLeft + xSize / 2, textFieldRename.yPosition - 4 - fontRendererObj.FONT_HEIGHT, 16777215);
			textFieldRename.drawTextBox();
			if (checkValidRename != null)
				drawCenteredString(checkValidRename, guiLeft + xSize / 2, textFieldRename.yPosition + textFieldRename.height + fontRendererObj.FONT_HEIGHT, 16711680);
		} else if (page == Page.INVITATIONS) {
			int x = guiLeft;
			int y = guiTop + 10;
			s = new StringBuilder(StatCollector.translateToLocal("lotr.gui.fellowships.invites"));
			drawCenteredString(s.toString(), guiLeft + xSize / 2, y, 16777215);
			y += fontRendererObj.FONT_HEIGHT + 10;
			if (allFellowshipInvites.isEmpty()) {
				y += fontRendererObj.FONT_HEIGHT;
				s = new StringBuilder(StatCollector.translateToLocal("lotr.gui.fellowships.invitesNone"));
				drawCenteredString(s.toString(), guiLeft + xSize / 2, y, 16777215);
			} else {
				int[] invitesMinMax = scrollPaneInvites.getMinMaxIndices(allFellowshipInvites, displayedInvites);
				for (int index = invitesMinMax[0]; index <= invitesMinMax[1]; index++) {
					LOTRFellowshipClient fs = allFellowshipInvites.get(index);
					drawFellowshipEntry(fs, x, y, i, j, true);
					y += fontRendererObj.FONT_HEIGHT + 5;
				}
			}
			if (scrollPaneInvites.hasScrollBar) scrollPaneInvites.drawScrollBar();
		} else if (page == Page.ACCEPT_INVITE_RESULT) {
			int x = guiLeft + xSize / 2;
			int y = guiTop + 30;
			if (acceptInviteResult == null) {
				int waitingDots = IntMath.mod(tickCounter / 10, 3);
				s = new StringBuilder();
				for (int l = 0; l < waitingDots; l++)
					s.append(".");
				drawCenteredString(s.toString(), guiLeft + xSize / 2, y, 16777215);
			} else if (acceptInviteResult == LOTRPacketFellowshipAcceptInviteResult.AcceptInviteResult.JOINED) {
				s = new StringBuilder("Joining... (you shouldn't be able to see this message)");
				drawCenteredString(s.toString(), guiLeft + xSize / 2, y, 16777215);
			} else {
				if (acceptInviteResult == LOTRPacketFellowshipAcceptInviteResult.AcceptInviteResult.DISBANDED) {
					s = new StringBuilder(StatCollector.translateToLocalFormatted("lotr.gui.fellowships.invited.disbanded", acceptInviteResultFellowshipName));
				} else if (acceptInviteResult == LOTRPacketFellowshipAcceptInviteResult.AcceptInviteResult.TOO_LARGE) {
					s = new StringBuilder(StatCollector.translateToLocalFormatted("lotr.gui.fellowships.invited.maxSize", acceptInviteResultFellowshipName, LOTRConfig.getFellowshipMaxSize(mc.theWorld)));
				} else if (acceptInviteResult == LOTRPacketFellowshipAcceptInviteResult.AcceptInviteResult.NONEXISTENT) {
					s = new StringBuilder(StatCollector.translateToLocalFormatted("lotr.gui.fellowships.invited.notFound"));
				} else {
					s = new StringBuilder("If you can see this message, something has gone wrong!");
				}
				List<String> lines = fontRendererObj.listFormattedStringToWidth(s.toString(), xSize);
				for (String line : lines) {
					drawCenteredString(line, x, y, 16777215);
					y += fontRendererObj.FONT_HEIGHT;
				}
			}
		}
	}

	public LOTRFellowshipClient getMouseOverFellowship() {
		return mouseOverFellowship;
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		int k = Mouse.getEventDWheel();
		if (k != 0) {
			int l;
			k = Integer.signum(k);
			if (page == Page.LIST) {
				if (scrollPaneLeading.hasScrollBar && scrollPaneLeading.mouseOver) {
					l = allFellowshipsLeading.size() - displayedFellowshipsLeading;
					scrollPaneLeading.mouseWheelScroll(k, l);
				}
				if (scrollPaneOther.hasScrollBar && scrollPaneOther.mouseOver) {
					l = allFellowshipsOther.size() - displayedFellowshipsOther;
					scrollPaneOther.mouseWheelScroll(k, l);
				}
			}
			if (page == Page.FELLOWSHIP && scrollPaneMembers.hasScrollBar && scrollPaneMembers.mouseOver) {
				l = viewingFellowship.getMemberUuids().size() - displayedMembers;
				scrollPaneMembers.mouseWheelScroll(k, l);
			}
			if (page == Page.INVITATIONS && scrollPaneInvites.hasScrollBar && scrollPaneInvites.mouseOver) {
				l = allFellowshipInvites.size() - displayedInvites;
				scrollPaneInvites.mouseWheelScroll(k, l);
			}
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		if (mc.thePlayer != null) {
			refreshFellowshipList();
		}
		int midX = guiLeft + xSize / 2;
		buttonCreate = new GuiButton(0, midX - 100, guiTop + 230, 200, 20, StatCollector.translateToLocal("lotr.gui.fellowships.create"));
		buttonList.add(buttonCreate);
		buttonCreateThis = new GuiButton(1, midX - 100, guiTop + 170, 200, 20, StatCollector.translateToLocal("lotr.gui.fellowships.createThis"));
		buttonList.add(buttonCreateThis);
		buttonInvitePlayer = new LOTRGuiButtonFsOption(2, midX, guiTop + 232, 0, 48, StatCollector.translateToLocal("lotr.gui.fellowships.invite"));
		buttonList.add(buttonInvitePlayer);
		buttonInviteThis = new GuiButton(3, midX - 100, guiTop + 170, 200, 20, StatCollector.translateToLocal("lotr.gui.fellowships.inviteThis"));
		buttonList.add(buttonInviteThis);
		buttonDisband = new LOTRGuiButtonFsOption(4, midX, guiTop + 232, 16, 48, StatCollector.translateToLocal("lotr.gui.fellowships.disband"));
		buttonList.add(buttonDisband);
		buttonDisbandThis = new GuiButton(5, midX - 100, guiTop + 170, 200, 20, StatCollector.translateToLocal("lotr.gui.fellowships.disbandThis"));
		buttonList.add(buttonDisbandThis);
		buttonLeave = new GuiButton(6, midX - 60, guiTop + 230, 120, 20, StatCollector.translateToLocal("lotr.gui.fellowships.leave"));
		buttonList.add(buttonLeave);
		buttonLeaveThis = new GuiButton(7, midX - 100, guiTop + 170, 200, 20, StatCollector.translateToLocal("lotr.gui.fellowships.leaveThis"));
		buttonList.add(buttonLeaveThis);
		buttonSetIcon = new LOTRGuiButtonFsOption(8, midX, guiTop + 232, 48, 48, StatCollector.translateToLocal("lotr.gui.fellowships.setIcon"));
		buttonList.add(buttonSetIcon);
		buttonRemove = new GuiButton(9, midX - 100, guiTop + 170, 200, 20, StatCollector.translateToLocal("lotr.gui.fellowships.remove"));
		buttonList.add(buttonRemove);
		buttonTransfer = new GuiButton(10, midX - 100, guiTop + 170, 200, 20, StatCollector.translateToLocal("lotr.gui.fellowships.transfer"));
		buttonList.add(buttonTransfer);
		buttonRename = new LOTRGuiButtonFsOption(11, midX, guiTop + 232, 32, 48, StatCollector.translateToLocal("lotr.gui.fellowships.rename"));
		buttonList.add(buttonRename);
		buttonRenameThis = new GuiButton(12, midX - 100, guiTop + 170, 200, 20, StatCollector.translateToLocal("lotr.gui.fellowships.renameThis"));
		buttonList.add(buttonRenameThis);
		buttonBack = new GuiButton(13, guiLeft - 10, guiTop, 20, 20, "<");
		buttonList.add(buttonBack);
		buttonInvites = new LOTRGuiButtonFsInvites(14, guiLeft + xSize - 16, guiTop, "");
		buttonList.add(buttonInvites);
		buttonPVP = new LOTRGuiButtonFsOption(15, midX, guiTop + 232, 64, 48, StatCollector.translateToLocal("lotr.gui.fellowships.togglePVP"));
		buttonList.add(buttonPVP);
		buttonHiredFF = new LOTRGuiButtonFsOption(16, midX, guiTop + 232, 80, 48, StatCollector.translateToLocal("lotr.gui.fellowships.toggleHiredFF"));
		buttonList.add(buttonHiredFF);
		buttonMapShow = new LOTRGuiButtonFsOption(17, midX, guiTop + 232, 96, 48, StatCollector.translateToLocal("lotr.gui.fellowships.toggleMapShow"));
		buttonList.add(buttonMapShow);
		buttonOp = new GuiButton(18, midX - 100, guiTop + 170, 200, 20, StatCollector.translateToLocal("lotr.gui.fellowships.op"));
		buttonList.add(buttonOp);
		buttonDeop = new GuiButton(19, midX - 100, guiTop + 170, 200, 20, StatCollector.translateToLocal("lotr.gui.fellowships.deop"));
		buttonList.add(buttonDeop);
		orderedFsOptionButtons.clear();
		orderedFsOptionButtons.add(buttonInvitePlayer);
		orderedFsOptionButtons.add(buttonDisband);
		orderedFsOptionButtons.add(buttonRename);
		orderedFsOptionButtons.add(buttonSetIcon);
		orderedFsOptionButtons.add(buttonMapShow);
		orderedFsOptionButtons.add(buttonPVP);
		orderedFsOptionButtons.add(buttonHiredFF);
		textFieldName = new GuiTextField(fontRendererObj, midX - 80, guiTop + 40, 160, 20);
		textFieldName.setMaxStringLength(40);
		textFieldPlayer = new GuiTextField(fontRendererObj, midX - 80, guiTop + 40, 160, 20);
		textFieldRename = new GuiTextField(fontRendererObj, midX - 80, guiTop + 40, 160, 20);
		textFieldRename.setMaxStringLength(40);
	}

	public boolean isFellowshipMaxSize(LOTRFellowshipClient fellowship) {
		if (fellowship != null) {
			int limit = LOTRConfig.getFellowshipMaxSize(mc.theWorld);
			return limit >= 0 && fellowship.getPlayerCount() >= limit;
		}
		return false;
	}

	@Override
	public void keyTyped(char c, int i) {
		if (page == Page.CREATE && textFieldName.textboxKeyTyped(c, i) || page == Page.INVITE && textFieldPlayer.textboxKeyTyped(c, i)) {
			return;
		}
		if (page == Page.RENAME && textFieldRename.textboxKeyTyped(c, i)) {
			return;
		}
		if (page == Page.LIST) {
			super.keyTyped(c, i);
		} else {
			if (i == 1 || i == mc.gameSettings.keyBindInventory.getKeyCode()) {
				if (page == Page.INVITE || page == Page.DISBAND || page == Page.LEAVE || page == Page.REMOVE || page == Page.OP || page == Page.DEOP || page == Page.TRANSFER || page == Page.RENAME) {
					page = Page.FELLOWSHIP;
				} else if (page == Page.ACCEPT_INVITE_RESULT) {
					if (acceptInviteResult != null) {
						page = Page.INVITATIONS;
					}
				} else {
					page = Page.LIST;
				}
			}
		}
	}

	@Override
	public void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		if (page == Page.LIST && mouseOverFellowship != null) {
			buttonSound();
			page = Page.FELLOWSHIP;
			viewingFellowship = mouseOverFellowship;
		}
		if (page == Page.CREATE) {
			textFieldName.mouseClicked(i, j, k);
		}
		if (page == Page.INVITE) {
			textFieldPlayer.mouseClicked(i, j, k);
		}
		if (page == Page.RENAME) {
			textFieldRename.mouseClicked(i, j, k);
		}
		if (page == Page.FELLOWSHIP && mouseOverPlayer != null && mouseOverPlayerRemove) {
			buttonSound();
			page = Page.REMOVE;
			removingPlayer = mouseOverPlayer;
		}
		if (page == Page.FELLOWSHIP && mouseOverPlayer != null && mouseOverPlayerOp) {
			buttonSound();
			page = Page.OP;
			oppingPlayer = mouseOverPlayer;
		}
		if (page == Page.FELLOWSHIP && mouseOverPlayer != null && mouseOverPlayerDeop) {
			buttonSound();
			page = Page.DEOP;
			deoppingPlayer = mouseOverPlayer;
		}
		if (page == Page.FELLOWSHIP && mouseOverPlayer != null && mouseOverPlayerTransfer) {
			buttonSound();
			page = Page.TRANSFER;
			transferringPlayer = mouseOverPlayer;
		}
		if (page == Page.INVITATIONS && mouseOverFellowship != null && mouseOverInviteAccept) {
			buttonSound();
			acceptInvitation(mouseOverFellowship);
			mouseOverFellowship = null;
			page = Page.ACCEPT_INVITE_RESULT;
		}
		if (page == Page.INVITATIONS && mouseOverFellowship != null && mouseOverInviteReject) {
			buttonSound();
			rejectInvitation(mouseOverFellowship);
			mouseOverFellowship = null;
		}
	}

	public void refreshFellowshipList() {
		allFellowshipsLeading.clear();
		allFellowshipsOther.clear();
		Iterable<LOTRFellowshipClient> fellowships = new ArrayList<>(LOTRLevelData.getData(mc.thePlayer).getClientFellowships());
		for (LOTRFellowshipClient fs : fellowships) {
			if (fs.isOwned()) {
				allFellowshipsLeading.add(fs);
				continue;
			}
			allFellowshipsOther.add(fs);
		}
		allFellowshipInvites.clear();
		allFellowshipInvites.addAll(LOTRLevelData.getData(mc.thePlayer).getClientFellowshipInvites());
	}

	public void rejectInvitation(LOTRFellowshipClient invite) {
		IMessage packet = new LOTRPacketFellowshipRespondInvite(invite, false);
		LOTRPacketHandler.networkWrapper.sendToServer(packet);
	}

	public void renderIconTooltip(int x, int y, String s) {
		float z = zLevel;
		int stringWidth = 200;
		List desc = fontRendererObj.listFormattedStringToWidth(s, stringWidth);
		func_146283_a(desc, x, y);
		GL11.glDisable(2896);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		zLevel = z;
	}

	public void setupScrollBars(int i, int j) {
		if (page == Page.LIST) {
			displayedFellowshipsLeading = allFellowshipsLeading.size();
			displayedFellowshipsOther = allFellowshipsOther.size();
			scrollPaneLeading.hasScrollBar = false;
			scrollPaneOther.hasScrollBar = false;
			while (displayedFellowshipsLeading + displayedFellowshipsOther > 12) {
				if (displayedFellowshipsOther >= displayedFellowshipsLeading) {
					--displayedFellowshipsOther;
					scrollPaneOther.hasScrollBar = true;
					continue;
				}
				--displayedFellowshipsLeading;
				scrollPaneLeading.hasScrollBar = true;
			}
			scrollPaneLeading.paneX0 = guiLeft;
			scrollPaneLeading.scrollBarX0 = guiLeft + scrollBarX;
			scrollPaneLeading.paneY0 = guiTop + 10;
			scrollPaneLeading.paneY1 = scrollPaneLeading.paneY0 + fontRendererObj.FONT_HEIGHT + 10 + (fontRendererObj.FONT_HEIGHT + 5) * displayedFellowshipsLeading;
			scrollPaneLeading.mouseDragScroll(i, j);
			scrollPaneOther.paneX0 = guiLeft;
			scrollPaneOther.scrollBarX0 = guiLeft + scrollBarX;
			scrollPaneOther.paneY0 = scrollPaneLeading.paneY1 + 5;
			scrollPaneOther.paneY1 = scrollPaneOther.paneY0 + fontRendererObj.FONT_HEIGHT + 10 + (fontRendererObj.FONT_HEIGHT + 5) * displayedFellowshipsOther;
			scrollPaneOther.mouseDragScroll(i, j);
		}
		if (page == Page.FELLOWSHIP) {
			displayedMembers = viewingFellowship.getMemberUuids().size();
			scrollPaneMembers.hasScrollBar = false;
			if (displayedMembers > 11) {
				displayedMembers = 11;
				scrollPaneMembers.hasScrollBar = true;
			}
			scrollPaneMembers.paneX0 = guiLeft;
			scrollPaneMembers.scrollBarX0 = guiLeft + scrollBarX;
			scrollPaneMembers.paneY0 = guiTop + 10 + fontRendererObj.FONT_HEIGHT + 5 + 16 + 10 + fontRendererObj.FONT_HEIGHT + 10;
			scrollPaneMembers.paneY1 = scrollPaneMembers.paneY0 + (fontRendererObj.FONT_HEIGHT + 5) * displayedMembers;
		} else {
			scrollPaneMembers.hasScrollBar = false;
		}
		scrollPaneMembers.mouseDragScroll(i, j);
		if (page == Page.INVITATIONS) {
			displayedInvites = allFellowshipInvites.size();
			scrollPaneInvites.hasScrollBar = false;
			if (displayedInvites > 15) {
				displayedInvites = 15;
				scrollPaneInvites.hasScrollBar = true;
			}
			scrollPaneInvites.paneX0 = guiLeft;
			scrollPaneInvites.scrollBarX0 = guiLeft + scrollBarX;
			scrollPaneInvites.paneY0 = guiTop + 10 + fontRendererObj.FONT_HEIGHT + 10;
			scrollPaneInvites.paneY1 = scrollPaneInvites.paneY0 + (fontRendererObj.FONT_HEIGHT + 5) * displayedInvites;
			scrollPaneInvites.mouseDragScroll(i, j);
		}
	}

	public List<LOTRFellowshipClient> sortFellowshipsForDisplay(List<LOTRFellowshipClient> list) {
		List<LOTRFellowshipClient> sorted = new ArrayList<>(list);
		sorted.sort((fs1, fs2) -> {
			int count2;
			int count1 = fs1.getPlayerCount();
			count2 = fs2.getPlayerCount();
			if (count1 == count2) {
				return fs1.getName().toLowerCase(Locale.ROOT).compareTo(fs2.getName().toLowerCase(Locale.ROOT));
			}
			return -Integer.compare(count1, count2);
		});
		return sorted;
	}

	public List<GameProfile> sortMembersForDisplay(LOTRFellowshipClient fs) {
		List<GameProfile> members = new ArrayList<>(fs.getMemberProfiles());
		members.sort(Comparator.comparing(LOTRGuiFellowships::isPlayerOnline).reversed().thenComparing(player -> fs.isAdmin(player.getId())).reversed().thenComparing(player -> player.getName().toLowerCase(Locale.ROOT)));
		return members;
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		++tickCounter;
		refreshFellowshipList();
		textFieldName.updateCursorCounter();
		if (page != Page.CREATE) {
			textFieldName.setText("");
		}
		textFieldPlayer.updateCursorCounter();
		if (page != Page.INVITE || isFellowshipMaxSize(viewingFellowship)) {
			textFieldPlayer.setText("");
		}
		textFieldRename.updateCursorCounter();
		if (page != Page.RENAME) {
			textFieldRename.setText("");
		}
	}

	public enum Page {
		LIST, CREATE, FELLOWSHIP, INVITE, DISBAND, LEAVE, REMOVE, OP, DEOP, TRANSFER, RENAME, INVITATIONS, ACCEPT_INVITE_RESULT

	}

}
