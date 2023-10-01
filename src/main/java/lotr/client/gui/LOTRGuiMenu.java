package lotr.client.gui;

import lotr.common.LOTRDimension;
import lotr.common.quest.LOTRMiniQuestWelcome;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class LOTRGuiMenu extends LOTRGuiScreenBase {
	public static ResourceLocation menuIconsTexture = new ResourceLocation("lotr:gui/menu_icons.png");
	public static Class<? extends LOTRGuiMenuBase> lastMenuScreen;

	public static GuiScreen openMenu(EntityPlayer entityplayer) {
		boolean[] map_factions = LOTRMiniQuestWelcome.forceMenu_Map_Factions(entityplayer);
		if (map_factions[0]) {
			return new LOTRGuiMap();
		}
		if (map_factions[1]) {
			return new LOTRGuiFactions();
		}
		if (lastMenuScreen != null) {
			try {
				return lastMenuScreen.getConstructor().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new LOTRGuiMenu();
	}

	public static void resetLastMenuScreen() {
		lastMenuScreen = null;
	}

	@Override
	public void actionPerformed(GuiButton button) {
		LOTRGuiMenuBase screen;
		if (button.enabled && button instanceof LOTRGuiButtonMenu && (screen = ((LOTRGuiButtonMenu) button).openMenu()) != null) {
			mc.displayGuiScreen(screen);
			lastMenuScreen = screen.getClass();
			return;
		}
		super.actionPerformed(button);
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		String title = StatCollector.translateToLocalFormatted("lotr.gui.menu", LOTRDimension.getCurrentDimensionWithFallback(mc.theWorld).getDimensionName());
		fontRendererObj.drawStringWithShadow(title, width / 2 - fontRendererObj.getStringWidth(title) / 2, height / 2 - 80, 16777215);
		super.drawScreen(i, j, f);
		for (Object obj : buttonList) {
			LOTRGuiButtonMenu button;
			if (!(obj instanceof LOTRGuiButtonMenu) || !(button = (LOTRGuiButtonMenu) obj).func_146115_a() || button.displayString == null) {
				continue;
			}
			float z = zLevel;
			drawCreativeTabHoveringText(button.displayString, i, j);
			GL11.glDisable(2896);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			zLevel = z;
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		resetLastMenuScreen();
		int midX = width / 2;
		int midY = height / 2;
		int buttonGap = 10;
		int buttonSize = 32;
		buttonList.add(new LOTRGuiButtonMenu(this, 2, 0, 0, LOTRGuiAchievements.class, StatCollector.translateToLocal("lotr.gui.achievements"), 30));
		buttonList.add(new LOTRGuiButtonMenu(this, 3, 0, 0, LOTRGuiMap.class, StatCollector.translateToLocal("lotr.gui.map"), 50));
		buttonList.add(new LOTRGuiButtonMenu(this, 4, 0, 0, LOTRGuiFactions.class, StatCollector.translateToLocal("lotr.gui.factions"), 33));
		buttonList.add(new LOTRGuiButtonMenu(this, 0, 0, 0, null, "?", -1));
		buttonList.add(new LOTRGuiButtonMenu(this, 6, 0, 0, LOTRGuiFellowships.class, StatCollector.translateToLocal("lotr.gui.fellowships"), 25));
		buttonList.add(new LOTRGuiButtonMenu(this, 7, 0, 0, LOTRGuiTitles.class, StatCollector.translateToLocal("lotr.gui.titles"), 20));
		buttonList.add(new LOTRGuiButtonMenu(this, 5, 0, 0, LOTRGuiShields.class, StatCollector.translateToLocal("lotr.gui.shields"), 31));
		buttonList.add(new LOTRGuiButtonMenu(this, 1, 0, 0, LOTRGuiOptions.class, StatCollector.translateToLocal("lotr.gui.options"), 24));
		ArrayList<LOTRGuiButtonMenu> menuButtons = new ArrayList<>();
		for (Object obj : buttonList) {
			if (!(obj instanceof LOTRGuiButtonMenu)) {
				continue;
			}
			LOTRGuiButtonMenu button = (LOTRGuiButtonMenu) obj;
			button.enabled = button.canDisplayMenu();
			menuButtons.add(button);
		}
		int numButtons = menuButtons.size();
		int numTopRowButtons = (numButtons - 1) / 2 + 1;
		int numBtmRowButtons = numButtons - numTopRowButtons;
		int topRowLeft = midX - (numTopRowButtons * buttonSize + (numTopRowButtons - 1) * buttonGap) / 2;
		int btmRowLeft = midX - (numBtmRowButtons * buttonSize + (numBtmRowButtons - 1) * buttonGap) / 2;
		for (int l = 0; l < numButtons; ++l) {
			LOTRGuiButtonMenu button = menuButtons.get(l);
			if (l < numTopRowButtons) {
				button.xPosition = topRowLeft + l * (buttonSize + buttonGap);
				button.yPosition = midY - buttonGap / 2 - buttonSize;
				continue;
			}
			button.xPosition = btmRowLeft + (l - numTopRowButtons) * (buttonSize + buttonGap);
			button.yPosition = midY + buttonGap / 2;
		}
	}

	@Override
	public void keyTyped(char c, int i) {
		for (Object obj : buttonList) {
			if (!(obj instanceof LOTRGuiButtonMenu)) {
				continue;
			}
			LOTRGuiButtonMenu button = (LOTRGuiButtonMenu) obj;
			if (!button.visible || !button.enabled || button.menuKeyCode < 0 || i != button.menuKeyCode) {
				continue;
			}
			actionPerformed(button);
			return;
		}
		super.keyTyped(c, i);
	}
}
