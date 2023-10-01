package lotr.client.gui;

import lotr.common.inventory.LOTRContainerAlloyForge;
import lotr.common.tileentity.LOTRTileEntityAlloyForgeBase;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class LOTRGuiAlloyForge extends GuiContainer {
	public static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/forge.png");
	public LOTRTileEntityAlloyForgeBase theForge;

	public LOTRGuiAlloyForge(InventoryPlayer inv, LOTRTileEntityAlloyForgeBase forge) {
		super(new LOTRContainerAlloyForge(inv, forge));
		theForge = forge;
		ySize = 233;
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(guiTexture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		if (theForge.isSmelting()) {
			int k = theForge.getSmeltTimeRemainingScaled(12);
			drawTexturedModalRect(guiLeft + 80, guiTop + 112 + 12 - k, 176, 12 - k, 14, k + 2);
		}
		int l = theForge.getSmeltProgressScaled(24);
		drawTexturedModalRect(guiLeft + 80, guiTop + 58, 176, 14, 16, l + 1);
	}

	@Override
	public void drawGuiContainerForegroundLayer(int i, int j) {
		String s = theForge.getInventoryName();
		fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 139, 4210752);
	}
}
