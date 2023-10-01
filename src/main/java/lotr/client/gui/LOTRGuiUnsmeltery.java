package lotr.client.gui;

import lotr.common.inventory.LOTRContainerUnsmeltery;
import lotr.common.tileentity.LOTRTileEntityUnsmeltery;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class LOTRGuiUnsmeltery extends GuiContainer {
	public static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/unsmelter.png");
	public LOTRTileEntityUnsmeltery theUnsmeltery;

	public LOTRGuiUnsmeltery(InventoryPlayer inv, LOTRTileEntityUnsmeltery unsmeltery) {
		super(new LOTRContainerUnsmeltery(inv, unsmeltery));
		theUnsmeltery = unsmeltery;
		ySize = 176;
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(guiTexture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		if (theUnsmeltery.isSmelting()) {
			int k = theUnsmeltery.getSmeltTimeRemainingScaled(13);
			drawTexturedModalRect(guiLeft + 56, guiTop + 36 + 12 - k, 176, 12 - k, 14, k + 1);
		}
		int l = theUnsmeltery.getSmeltProgressScaled(24);
		drawTexturedModalRect(guiLeft + 79, guiTop + 34, 176, 14, l + 1, 16);
	}

	@Override
	public void drawGuiContainerForegroundLayer(int i, int j) {
		String s = theUnsmeltery.getInventoryName();
		fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 72, 4210752);
	}
}
