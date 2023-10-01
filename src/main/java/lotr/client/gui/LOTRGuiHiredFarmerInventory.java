package lotr.client.gui;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.inventory.LOTRContainerHiredFarmerInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class LOTRGuiHiredFarmerInventory extends GuiContainer {
	public static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/npc/hiredFarmer.png");
	public LOTREntityNPC theNPC;

	public LOTRGuiHiredFarmerInventory(InventoryPlayer inv, LOTREntityNPC entity) {
		super(new LOTRContainerHiredFarmerInventory(inv, entity));
		theNPC = entity;
		ySize = 161;
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(guiTexture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		ItemStack seeds = inventorySlots.getSlot(0).getStack();
		if (seeds == null) {
			drawTexturedModalRect(guiLeft + 80, guiTop + 21, 176, 0, 16, 16);
		}
		if (inventorySlots.getSlot(3).getStack() == null) {
			drawTexturedModalRect(guiLeft + 123, guiTop + 34, 176, 16, 16, 16);
		}
	}

	@Override
	public void drawGuiContainerForegroundLayer(int i, int j) {
		String s = theNPC.getNPCName();
		fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 67, 4210752);
		ItemStack seeds = inventorySlots.getSlot(0).getStack();
		if (seeds != null && seeds.stackSize == 1) {
			s = StatCollector.translateToLocal("lotr.gui.farmer.oneSeed");
			s = EnumChatFormatting.RED + s;
			fontRendererObj.drawSplitString(s, xSize + 10, 20, 120, 16777215);
		}
	}
}
