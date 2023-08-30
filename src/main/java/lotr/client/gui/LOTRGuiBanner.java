package lotr.client.gui;

import java.util.Arrays;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.base.Function;
import com.mojang.authlib.GameProfile;

import lotr.common.LOTRBannerProtection;
import lotr.common.entity.item.*;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fellowship.LOTRFellowshipProfile;
import lotr.common.network.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;

public class LOTRGuiBanner extends LOTRGuiScreenBase {
	public static ResourceLocation bannerTexture = new ResourceLocation("lotr:gui/banner_edit.png");
	public LOTREntityBanner theBanner;
	public int xSize = 200;
	public int ySize = 250;
	public int guiLeft;
	public int guiTop;
	public GuiButton buttonMode;
	public LOTRGuiButtonBanner buttonSelfProtection;
	public GuiButton buttonAddSlot;
	public GuiButton buttonRemoveSlot;
	public LOTRGuiButtonBanner buttonDefaultPermissions;
	public GuiTextField alignmentField;
	public GuiTextField[] allowedPlayers = {};
	public boolean[] invalidUsernames = {};
	public boolean[] validatedUsernames = {};
	public boolean[] checkUsernames = {};
	public float currentScroll;
	public boolean isScrolling;
	public boolean wasMouseDown;
	public int scrollBarWidth = 12;
	public int scrollBarHeight = 132;
	public int scrollBarX = 181;
	public int scrollBarY = 68;
	public int scrollBarBorder = 1;
	public int scrollWidgetWidth = 10;
	public int scrollWidgetHeight = 17;
	public int permIconX = 3;
	public int permIconY;
	public int permIconWidth = 10;
	public int permissionsMouseoverIndex = -1;
	public int permissionsMouseoverY = -1;
	public int permWindowBorder = 4;
	public int permWindowWidth = 150;
	public int permWindowHeight = 70;
	public int permissionsOpenIndex = -1;
	public int permissionsOpenY = -1;
	public LOTRBannerProtection.Permission mouseOverPermission;
	public boolean defaultPermissionsOpen;

	public LOTRGuiBanner(LOTREntityBanner banner) {
		theBanner = banner;
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button == buttonMode) {
				theBanner.setPlayerSpecificProtection(!theBanner.isPlayerSpecificProtection());
			}
			if (button == buttonSelfProtection) {
				theBanner.setSelfProtection(!theBanner.isSelfProtection());
			}
			if (button == buttonAddSlot) {
				theBanner.resizeWhitelist(theBanner.getWhitelistLength() + 1);
				refreshWhitelist();
			}
			if (button == buttonRemoveSlot) {
				theBanner.resizeWhitelist(theBanner.getWhitelistLength() - 1);
				refreshWhitelist();
			}
			if (button == buttonDefaultPermissions) {
				defaultPermissionsOpen = true;
			}
		}
	}

	public void buttonSound() {
		buttonMode.func_146113_a(mc.getSoundHandler());
	}

	public boolean canScroll() {
		return true;
	}

	public void checkUsernameValid(int index) {
		GuiTextField textBox = allowedPlayers[index];
		String username = textBox.getText();
		if (!StringUtils.isBlank(username) && !invalidUsernames[index]) {
			IMessage packet = new LOTRPacketBannerRequestInvalidName(theBanner, index, username);
			LOTRPacketHandler.networkWrapper.sendToServer(packet);
		}
	}

	public void drawPermissionsWindow(int i, int j, int windowX, int windowY, String boxTitle, String boxSubtitle, Function<LOTRBannerProtection.Permission, Boolean> getEnabled, boolean includeFull) {
		Gui.drawRect(windowX, windowY, windowX + permWindowWidth, windowY + permWindowHeight, -1442840576);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		fontRendererObj.drawString(boxTitle, windowX + 4, windowY + 4, 16777215);
		fontRendererObj.drawString(boxSubtitle, windowX + 4, windowY + 14, 11184810);
		mc.getTextureManager().bindTexture(bannerTexture);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		int x = windowX + 4;
		int y = windowY + 32;
		mouseOverPermission = null;
		for (LOTRBannerProtection.Permission p : LOTRBannerProtection.Permission.values()) {
			if (!includeFull && p == LOTRBannerProtection.Permission.FULL) {
				continue;
			}
			if (i >= x && i < x + 10 && j >= y && j < y + 10) {
				mouseOverPermission = p;
			}
			drawTexturedModalRect(x, y, 200 + (getEnabled.apply(p) ? 0 : 20) + (mouseOverPermission == p ? 10 : 0), 160 + p.ordinal() * 10, 10, 10);
			x += 14;
			if (p != LOTRBannerProtection.Permission.FULL) {
				continue;
			}
			x += 4;
		}
		if (mouseOverPermission != null) {
			String permName = StatCollector.translateToLocal("lotr.gui.bannerEdit.perm." + mouseOverPermission.codeName);
			fontRendererObj.drawSplitString(permName, windowX + 4, windowY + 47, permWindowWidth - permWindowBorder * 2, 16777215);
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		int windowY;
		String s;
		permissionsMouseoverIndex = -1;
		permissionsMouseoverY = -1;
		mouseOverPermission = null;
		setupScrollBar(i, j);
		alignmentField.setVisible(false);
		alignmentField.setEnabled(false);
		for (GuiTextField textBox : allowedPlayers) {
			textBox.setVisible(false);
			textBox.setEnabled(false);
		}
		drawDefaultBackground();
		mc.getTextureManager().bindTexture(bannerTexture);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		String title = StatCollector.translateToLocal("lotr.gui.bannerEdit.title");
		fontRendererObj.drawString(title, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(title) / 2, guiTop + 6, 4210752);
		if (theBanner.isPlayerSpecificProtection()) {
			buttonMode.displayString = StatCollector.translateToLocal("lotr.gui.bannerEdit.protectionMode.playerSpecific");
			s = StatCollector.translateToLocal("lotr.gui.bannerEdit.protectionMode.playerSpecific.desc.1");
			fontRendererObj.drawString(s, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop + 46, 4210752);
			s = StatCollector.translateToLocal("lotr.gui.bannerEdit.protectionMode.playerSpecific.desc.2");
			fontRendererObj.drawString(s, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop + 46 + fontRendererObj.FONT_HEIGHT, 4210752);
			s = LOTRFellowshipProfile.getFellowshipCodeHint();
			fontRendererObj.drawString(s, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop + 206, 4210752);
			int start = Math.round(currentScroll * (allowedPlayers.length - 6));
			int end = start + 6 - 1;
			start = Math.max(start, 0);
			end = Math.min(end, allowedPlayers.length - 1);
			for (int index = start; index <= end; ++index) {
				int displayIndex = index - start;
				GuiTextField textBox = allowedPlayers[index];
				textBox.setVisible(true);
				textBox.setEnabled(index != 0);
				textBox.xPosition = guiLeft + xSize / 2 - 70;
				textBox.yPosition = guiTop + 70 + displayIndex * (textBox.height + 4);
				textBox.drawTextBox();
				String number = index + 1 + ".";
				fontRendererObj.drawString(number, guiLeft + 24 - fontRendererObj.getStringWidth(number), textBox.yPosition + 6, 4210752);
				if (index == 0 || !validatedUsernames[index]) {
					continue;
				}
				mc.getTextureManager().bindTexture(bannerTexture);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				int permX = textBox.xPosition + textBox.width + permIconX;
				int permY = textBox.yPosition + permIconY;
				boolean mouseOver = i >= permX && i < permX + permIconWidth && j >= permY && j < permY + permIconWidth;
				drawTexturedModalRect(permX, permY, 200 + (mouseOver ? permIconWidth : 0), 150, permIconWidth, permIconWidth);
				if (!mouseOver) {
					continue;
				}
				permissionsMouseoverIndex = index;
				permissionsMouseoverY = textBox.yPosition;
			}
			if (hasScrollBar()) {
				mc.getTextureManager().bindTexture(bannerTexture);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				drawTexturedModalRect(guiLeft + scrollBarX, guiTop + scrollBarY, 200, 0, scrollBarWidth, scrollBarHeight);
				if (canScroll()) {
					int scroll = (int) (currentScroll * (scrollBarHeight - scrollBarBorder * 2 - scrollWidgetHeight));
					drawTexturedModalRect(guiLeft + scrollBarX + scrollBarBorder, guiTop + scrollBarY + scrollBarBorder + scroll, 212, 0, scrollWidgetWidth, scrollWidgetHeight);
				}
			}
		} else {
			permissionsOpenY = -1;
			permissionsOpenIndex = -1;
			buttonMode.displayString = StatCollector.translateToLocal("lotr.gui.bannerEdit.protectionMode.faction");
			s = StatCollector.translateToLocalFormatted("lotr.gui.bannerEdit.protectionMode.faction.desc.1");
			fontRendererObj.drawString(s, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop + 46, 4210752);
			s = StatCollector.translateToLocalFormatted("lotr.gui.bannerEdit.protectionMode.faction.desc.2", theBanner.getAlignmentProtection(), theBanner.getBannerType().faction.factionName());
			fontRendererObj.drawString(s, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop + 46 + fontRendererObj.FONT_HEIGHT, 4210752);
			s = StatCollector.translateToLocal("lotr.gui.bannerEdit.protectionMode.faction.desc.3");
			fontRendererObj.drawString(s, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop + 46 + fontRendererObj.FONT_HEIGHT * 2, 4210752);
			s = StatCollector.translateToLocal("lotr.gui.bannerEdit.protectionMode.faction.alignment");
			fontRendererObj.drawString(s, alignmentField.xPosition, alignmentField.yPosition - fontRendererObj.FONT_HEIGHT - 3, 4210752);
			alignmentField.setVisible(true);
			alignmentField.setEnabled(true);
			alignmentField.drawTextBox();
		}
		if (permissionsOpenIndex >= 0) {
			int windowX = guiLeft + xSize + permWindowBorder;
			windowY = permissionsOpenY;
			String username = allowedPlayers[permissionsOpenIndex].getText();
			boolean isFellowship = LOTRFellowshipProfile.hasFellowshipCode(username);
			if (isFellowship) {
				username = LOTRFellowshipProfile.stripFellowshipCode(username);
			}
			String boxTitle = StatCollector.translateToLocal(isFellowship ? "lotr.gui.bannerEdit.perms.fellowship" : "lotr.gui.bannerEdit.perms.player");
			String boxSubtitle = StatCollector.translateToLocalFormatted("lotr.gui.bannerEdit.perms.name", username);
			Function<LOTRBannerProtection.Permission, Boolean> getEnabled = p -> theBanner.getWhitelistEntry(permissionsOpenIndex).isPermissionEnabled(p);
			drawPermissionsWindow(i, j, windowX, windowY, boxTitle, boxSubtitle, getEnabled, true);
		}
		if (defaultPermissionsOpen) {
			int windowX = guiLeft + xSize + permWindowBorder;
			windowY = guiTop + ySize - permWindowHeight;
			String boxTitle = StatCollector.translateToLocal("lotr.gui.bannerEdit.perms.default");
			String boxSubtitle = StatCollector.translateToLocalFormatted("lotr.gui.bannerEdit.perms.allPlayers");
			Function<LOTRBannerProtection.Permission, Boolean> getEnabled = p -> theBanner.hasDefaultPermission(p);
			drawPermissionsWindow(i, j, windowX, windowY, boxTitle, boxSubtitle, getEnabled, false);
		}
		super.drawScreen(i, j, f);
		if (buttonSelfProtection.func_146115_a()) {
			float z = zLevel;
			String tooltip = StatCollector.translateToLocal("lotr.gui.bannerEdit.selfProtection." + (buttonSelfProtection.activated ? "on" : "off"));
			drawCreativeTabHoveringText(tooltip, i, j);
			GL11.glDisable(2896);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			zLevel = z;
		}
		if (buttonDefaultPermissions.func_146115_a()) {
			float z = zLevel;
			String tooltip = StatCollector.translateToLocal("lotr.gui.bannerEdit.perms.default");
			drawCreativeTabHoveringText(tooltip, i, j);
			GL11.glDisable(2896);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			zLevel = z;
		}
		if (permissionsMouseoverIndex >= 0) {
			float z = zLevel;
			String username = allowedPlayers[permissionsMouseoverIndex].getText();
			boolean isFellowship = LOTRFellowshipProfile.hasFellowshipCode(username);
			String tooltip = StatCollector.translateToLocal(isFellowship ? "lotr.gui.bannerEdit.perms.fellowship" : "lotr.gui.bannerEdit.perms.player");
			drawCreativeTabHoveringText(tooltip, i, j);
			GL11.glDisable(2896);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			zLevel = z;
		}
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		int i = Mouse.getEventDWheel();
		if (i != 0 && canScroll()) {
			int j = allowedPlayers.length - 6;
			if (i > 0) {
				i = 1;
			}
			if (i < 0) {
				i = -1;
			}
			currentScroll = (float) (currentScroll - (double) i / j);
			if (currentScroll < 0.0f) {
				currentScroll = 0.0f;
			}
			if (currentScroll > 1.0f) {
				currentScroll = 1.0f;
			}
		}
	}

	public boolean hasScrollBar() {
		return theBanner.isPlayerSpecificProtection();
	}

	@Override
	public void initGui() {
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		buttonMode = new GuiButton(0, guiLeft + xSize / 2 - 80, guiTop + 20, 160, 20, "");
		buttonList.add(buttonMode);
		buttonSelfProtection = new LOTRGuiButtonBanner(1, guiLeft + xSize / 2 - 24, guiTop + 224, 212, 100);
		buttonList.add(buttonSelfProtection);
		buttonAddSlot = new LOTRGuiButtonBannerWhitelistSlots(0, guiLeft + 179, guiTop + 202);
		buttonList.add(buttonAddSlot);
		buttonRemoveSlot = new LOTRGuiButtonBannerWhitelistSlots(1, guiLeft + 187, guiTop + 202);
		buttonList.add(buttonRemoveSlot);
		buttonDefaultPermissions = new LOTRGuiButtonBanner(2, guiLeft + xSize / 2 + 8, guiTop + 224, 200, 134);
		buttonList.add(buttonDefaultPermissions);
		buttonDefaultPermissions.activated = true;
		alignmentField = new GuiTextField(fontRendererObj, guiLeft + xSize / 2 - 70, guiTop + 100, 130, 18);
		alignmentField.setText(String.valueOf(theBanner.getAlignmentProtection()));
		alignmentField.setEnabled(false);
		refreshWhitelist();
		for (int i = 0; i < allowedPlayers.length; ++i) {
			String name;
			GuiTextField textBox = allowedPlayers[i];
			textBox.setTextColor(16777215);
			GameProfile profile = theBanner.getWhitelistedPlayer(i);
			if (profile != null && !StringUtils.isBlank(name = profile.getName())) {
				textBox.setText(name);
				textBox.setTextColor(65280);
				validatedUsernames[i] = true;
			}
			allowedPlayers[i] = textBox;
		}
		allowedPlayers[0].setEnabled(false);
		Arrays.fill(checkUsernames, false);
	}

	@Override
	public void keyTyped(char c, int i) {
		if (alignmentField.getVisible() && alignmentField.textboxKeyTyped(c, i)) {
			return;
		}
		for (int l = 1; l < allowedPlayers.length; ++l) {
			GuiTextField textBox = allowedPlayers[l];
			if (!textBox.getVisible() || !textBox.textboxKeyTyped(c, i)) {
				continue;
			}
			validatedUsernames[l] = false;
			checkUsernames[l] = true;
			textBox.setTextColor(16777215);
			updateWhitelistedPlayer(l, null);
			return;
		}
		if (permissionsOpenIndex >= 0 && (i == 1 || i == mc.gameSettings.keyBindInventory.getKeyCode())) {
			permissionsOpenY = -1;
			permissionsOpenIndex = -1;
			return;
		}
		if (defaultPermissionsOpen && (i == 1 || i == mc.gameSettings.keyBindInventory.getKeyCode())) {
			defaultPermissionsOpen = false;
			return;
		}
		super.keyTyped(c, i);
	}

	@Override
	public void mouseClicked(int i, int j, int k) {
		int dx;
		super.mouseClicked(i, j, k);
		if (alignmentField.getVisible()) {
			alignmentField.mouseClicked(i, j, k);
		}
		for (int l = 1; l < allowedPlayers.length; ++l) {
			GuiTextField textBox = allowedPlayers[l];
			if (!textBox.getVisible()) {
				continue;
			}
			textBox.mouseClicked(i, j, k);
			if (!textBox.isFocused() && checkUsernames[l]) {
				checkUsernameValid(l);
				checkUsernames[l] = false;
			}
			if (!textBox.isFocused() || !invalidUsernames[l]) {
				continue;
			}
			invalidUsernames[l] = false;
			textBox.setTextColor(16777215);
			textBox.setText("");
		}
		if (permissionsMouseoverIndex >= 0) {
			permissionsOpenIndex = permissionsMouseoverIndex;
			permissionsOpenY = permissionsMouseoverY;
			permissionsMouseoverIndex = -1;
			permissionsMouseoverY = -1;
			defaultPermissionsOpen = false;
			buttonSound();
			return;
		}
		if (permissionsOpenIndex >= 0) {
			dx = i - (guiLeft + xSize + permWindowBorder);
			int dy = j - permissionsOpenY;
			if (dx < 0 || dx >= permWindowWidth || dy < 0 || dy >= permWindowHeight) {
				permissionsOpenY = -1;
				permissionsOpenIndex = -1;
				return;
			}
			if (mouseOverPermission != null) {
				LOTRBannerWhitelistEntry entry = theBanner.getWhitelistEntry(permissionsOpenIndex);
				if (mouseOverPermission == LOTRBannerProtection.Permission.FULL) {
					if (entry.isPermissionEnabled(mouseOverPermission)) {
						entry.clearPermissions();
					} else {
						entry.clearPermissions();
						entry.addPermission(mouseOverPermission);
					}
				} else if (entry.isPermissionEnabled(mouseOverPermission)) {
					entry.removePermission(mouseOverPermission);
				} else {
					if (entry.isPermissionEnabled(LOTRBannerProtection.Permission.FULL)) {
						entry.removePermission(LOTRBannerProtection.Permission.FULL);
					}
					entry.addPermission(mouseOverPermission);
				}
				buttonSound();
				return;
			}
		}
		if (defaultPermissionsOpen) {
			dx = i - (guiLeft + xSize + permWindowBorder);
			int dy = j - (guiTop + ySize - permWindowHeight);
			if ((dx < 0 || dx >= permWindowWidth || dy < 0 || dy >= permWindowHeight) && !buttonDefaultPermissions.mousePressed(mc, i, j)) {
				defaultPermissionsOpen = false;
				return;
			}
			if (mouseOverPermission != null) {
				if (theBanner.hasDefaultPermission(mouseOverPermission)) {
					theBanner.removeDefaultPermission(mouseOverPermission);
				} else {
					theBanner.addDefaultPermission(mouseOverPermission);
				}
				sendBannerData(false);
				buttonSound();
			}
		}
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		sendBannerData(true);
	}

	public void refreshWhitelist() {
		int length = theBanner.getWhitelistLength();
		GuiTextField[] allowedPlayers_new = new GuiTextField[length];
		boolean[] invalidUsernames_new = new boolean[length];
		boolean[] validatedUsernames_new = new boolean[length];
		boolean[] checkUsernames_new = new boolean[length];
		for (int i = 0; i < length; ++i) {
			allowedPlayers_new[i] = i < allowedPlayers.length ? allowedPlayers[i] : new GuiTextField(fontRendererObj, 0, 0, 130, 18);
			if (i < invalidUsernames.length) {
				invalidUsernames_new[i] = invalidUsernames[i];
			}
			if (i < validatedUsernames.length) {
				validatedUsernames_new[i] = validatedUsernames[i];
			}
			if (i >= checkUsernames.length) {
				continue;
			}
			checkUsernames_new[i] = checkUsernames[i];
		}
		allowedPlayers = allowedPlayers_new;
		invalidUsernames = invalidUsernames_new;
		validatedUsernames = validatedUsernames_new;
		checkUsernames = checkUsernames_new;
	}

	public void sendBannerData(boolean sendWhitelist) {
		LOTRPacketEditBanner packet = new LOTRPacketEditBanner(theBanner);
		packet.playerSpecificProtection = theBanner.isPlayerSpecificProtection();
		packet.selfProtection = theBanner.isSelfProtection();
		packet.alignmentProtection = theBanner.getAlignmentProtection();
		packet.whitelistLength = theBanner.getWhitelistLength();
		if (sendWhitelist) {
			String[] whitelistSlots = new String[allowedPlayers.length];
			int[] whitelistPerms = new int[allowedPlayers.length];
			for (int index = 1; index < allowedPlayers.length; ++index) {
				String text = allowedPlayers[index].getText();
				updateWhitelistedPlayer(index, text);
				LOTRBannerWhitelistEntry entry = theBanner.getWhitelistEntry(index);
				if (entry == null) {
					whitelistSlots[index] = null;
					continue;
				}
				GameProfile profile = entry.profile;
				if (profile == null) {
					whitelistSlots[index] = null;
					continue;
				}
				String username = profile.getName();
				if (StringUtils.isBlank(username)) {
					whitelistSlots[index] = null;
					continue;
				}
				whitelistSlots[index] = username;
				whitelistPerms[index] = entry.encodePermBitFlags();
			}
			packet.whitelistSlots = whitelistSlots;
			packet.whitelistPerms = whitelistPerms;
		}
		packet.defaultPerms = theBanner.getDefaultPermBitFlags();
		LOTRPacketHandler.networkWrapper.sendToServer(packet);
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
			currentScroll = (j - j1 - scrollWidgetHeight / 2.0f) / ((float) (j2 - j1) - scrollWidgetHeight);
			if (currentScroll < 0.0f) {
				currentScroll = 0.0f;
			}
			if (currentScroll > 1.0f) {
				currentScroll = 1.0f;
			}
		}
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		buttonSelfProtection.activated = theBanner.isSelfProtection();
		buttonAddSlot.visible = buttonRemoveSlot.visible = theBanner.isPlayerSpecificProtection();
		buttonAddSlot.enabled = theBanner.getWhitelistLength() < LOTREntityBanner.WHITELIST_MAX;
		buttonRemoveSlot.enabled = theBanner.getWhitelistLength() > LOTREntityBanner.WHITELIST_MIN;
		alignmentField.updateCursorCounter();
		alignmentField.setVisible(!theBanner.isPlayerSpecificProtection());
		alignmentField.setEnabled(alignmentField.getVisible());
		if (alignmentField.getVisible() && !alignmentField.isFocused()) {
			float prevAlignment = theBanner.getAlignmentProtection();
			float alignment = LOTRAlignmentValues.parseDisplayedAlign(alignmentField.getText());
			alignment = MathHelper.clamp_float(alignment, LOTREntityBanner.ALIGNMENT_PROTECTION_MIN, LOTREntityBanner.ALIGNMENT_PROTECTION_MAX);
			theBanner.setAlignmentProtection(alignment);
			alignmentField.setText(LOTRAlignmentValues.formatAlignForDisplay(alignment));
			if (alignment != prevAlignment) {
				sendBannerData(false);
			}
		}
		for (GuiTextField textBox : allowedPlayers) {
			textBox.updateCursorCounter();
		}
	}

	public void updateWhitelistedPlayer(int index, String username) {
		LOTRBannerWhitelistEntry prevEntry = theBanner.getWhitelistEntry(index);
		int prevPerms = -1;
		if (prevEntry != null) {
			prevPerms = prevEntry.encodePermBitFlags();
		}
		if (StringUtils.isBlank(username)) {
			theBanner.whitelistPlayer(index, null);
		} else {
			if (LOTRFellowshipProfile.hasFellowshipCode(username)) {
				String fsName = LOTRFellowshipProfile.stripFellowshipCode(username);
				if (StringUtils.isBlank(fsName)) {
					theBanner.whitelistPlayer(index, null);
				} else {
					theBanner.whitelistPlayer(index, new LOTRFellowshipProfile(theBanner, null, fsName));
				}
			} else {
				theBanner.whitelistPlayer(index, new GameProfile(null, username));
			}
			if (prevPerms >= 0) {
				theBanner.getWhitelistEntry(index).decodePermBitFlags(prevPerms);
			}
		}
	}

	public void validateUsername(int index, String prevText, boolean valid) {
		GuiTextField textBox = allowedPlayers[index];
		String username = textBox.getText();
		if (username.equals(prevText)) {
			if (valid) {
				validatedUsernames[index] = true;
				invalidUsernames[index] = false;
				textBox.setTextColor(65280);
				updateWhitelistedPlayer(index, username);
			} else {
				invalidUsernames[index] = true;
				validatedUsernames[index] = false;
				textBox.setTextColor(16711680);
				textBox.setText(StatCollector.translateToLocal("lotr.gui.bannerEdit.invalidUsername"));
				updateWhitelistedPlayer(index, null);
			}
		}
	}

}
