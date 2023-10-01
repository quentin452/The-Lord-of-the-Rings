package lotr.client.gui;

import lotr.common.entity.npc.LOTREntityGollum;
import lotr.common.inventory.LOTRContainerGollum;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class LOTRGuiGollum extends GuiContainer {
	public static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/npc/gollum.png");
	public LOTREntityGollum theGollum;

	public LOTRGuiGollum(InventoryPlayer inv, LOTREntityGollum gollum) {
		super(new LOTRContainerGollum(inv, gollum));
		theGollum = gollum;
		ySize = 168;
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(guiTexture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

	@Override
	public void drawGuiContainerForegroundLayer(int i, int j) {
		String s = theGollum.getCommandSenderName();
		fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 74, 4210752);
	}
}
