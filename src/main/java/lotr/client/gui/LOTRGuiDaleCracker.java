package lotr.client.gui;

import lotr.common.inventory.LOTRContainerDaleCracker;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class LOTRGuiDaleCracker extends GuiContainer {
	public static ResourceLocation texture = new ResourceLocation("lotr:gui/daleCracker.png");
	public LOTRContainerDaleCracker theCracker;
	public GuiButton buttonSeal;

	public LOTRGuiDaleCracker(EntityPlayer entityplayer) {
		super(new LOTRContainerDaleCracker(entityplayer));
		theCracker = (LOTRContainerDaleCracker) inventorySlots;
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled && button == buttonSeal && !theCracker.isCrackerInvEmpty()) {
			theCracker.sendSealingPacket(mc.thePlayer);
			mc.displayGuiScreen(null);
		}
	}

	@Override
	public boolean checkHotbarKeys(int i) {
		return false;
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

	@Override
	public void drawGuiContainerForegroundLayer(int i, int j) {
		String s = StatCollector.translateToLocal("lotr.gui.daleCracker");
		fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonSeal = new GuiButton(0, guiLeft + xSize / 2 - 40, guiTop + 48, 80, 20, StatCollector.translateToLocal("lotr.gui.daleCracker.seal"));
		buttonList.add(buttonSeal);
		buttonSeal.enabled = false;
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		buttonSeal.enabled = !theCracker.isCrackerInvEmpty();
	}
}
