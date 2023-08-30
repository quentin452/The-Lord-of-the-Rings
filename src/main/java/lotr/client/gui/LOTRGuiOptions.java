package lotr.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import org.lwjgl.opengl.GL11;

import lotr.common.*;
import lotr.common.network.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;

public class LOTRGuiOptions extends LOTRGuiMenuBase {
	public LOTRGuiButtonOptions buttonFriendlyFire;
	public LOTRGuiButtonOptions buttonHiredDeathMessages;
	public LOTRGuiButtonOptions buttonAlignment;
	public LOTRGuiButtonOptions buttonMapLocation;
	public LOTRGuiButtonOptions buttonConquest;
	public LOTRGuiButtonOptions buttonFeminineRank;

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button instanceof LOTRGuiButtonOptions) {
				IMessage packet = new LOTRPacketSetOption(button.id);
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
		String s = StatCollector.translateToLocal("lotr.gui.options.title");
		fontRendererObj.drawString(s, guiLeft + 100 - fontRendererObj.getStringWidth(s) / 2, guiTop - 30, 16777215);
		s = StatCollector.translateToLocal("lotr.gui.options.worldSettings");
		fontRendererObj.drawString(s, guiLeft + 100 - fontRendererObj.getStringWidth(s) / 2, guiTop + 10, 16777215);
		LOTRPlayerData pd = LOTRLevelData.getData(mc.thePlayer);
		buttonFriendlyFire.setState(pd.getFriendlyFire());
		buttonHiredDeathMessages.setState(pd.getEnableHiredDeathMessages());
		buttonAlignment.setState(!pd.getHideAlignment());
		buttonMapLocation.setState(!pd.getHideMapLocation());
		buttonConquest.setState(pd.getEnableConquestKills());
		buttonFeminineRank.setState(pd.getFemRankOverride());
		super.drawScreen(i, j, f);
		for (Object element : buttonList) {
			GuiButton button = (GuiButton) element;
			if (!(button instanceof LOTRGuiButtonOptions)) {
				continue;
			}
			((LOTRGuiButtonOptions) button).drawTooltip(mc, i, j);
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		int buttonX = guiLeft + xSize / 2 - 100;
		int buttonY = guiTop + 40;
		buttonFriendlyFire = new LOTRGuiButtonOptions(0, buttonX, buttonY, 200, 20, "lotr.gui.options.friendlyFire");
		buttonList.add(buttonFriendlyFire);
		buttonHiredDeathMessages = new LOTRGuiButtonOptions(1, buttonX, buttonY + 24, 200, 20, "lotr.gui.options.hiredDeathMessages");
		buttonList.add(buttonHiredDeathMessages);
		buttonAlignment = new LOTRGuiButtonOptions(2, buttonX, buttonY + 48, 200, 20, "lotr.gui.options.showAlignment");
		buttonList.add(buttonAlignment);
		buttonMapLocation = new LOTRGuiButtonOptions(3, buttonX, buttonY + 72, 200, 20, "lotr.gui.options.showMapLocation");
		buttonList.add(buttonMapLocation);
		buttonConquest = new LOTRGuiButtonOptions(5, buttonX, buttonY + 96, 200, 20, "lotr.gui.options.conquest");
		buttonList.add(buttonConquest);
		buttonFeminineRank = new LOTRGuiButtonOptions(4, buttonX, buttonY + 120, 200, 20, "lotr.gui.options.femRank");
		buttonList.add(buttonFeminineRank);
	}
}
