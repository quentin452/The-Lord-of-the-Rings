package lotr.client.gui;

import lotr.common.inventory.LOTRContainerChestWithPouch;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class LOTRGuiChestWithPouch extends GuiContainer {
	public static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/pouch_with_chest.png");
	public IInventory pouchInv;
	public IInventory chestInv;
	public int chestRows;
	public int pouchRows;

	public LOTRGuiChestWithPouch(EntityPlayer entityplayer, int slot, IInventory chest) {
		super(new LOTRContainerChestWithPouch(entityplayer, slot, chest));
		pouchInv = ((LOTRContainerChestWithPouch) inventorySlots).pouchContainer.pouchInventory;
		chestInv = chest;
		allowUserInput = false;
		chestRows = chest.getSizeInventory() / 9;
		pouchRows = pouchInv.getSizeInventory() / 9;
		ySize = 180 + chestRows * 18;
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		int l;
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(guiTexture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, 17 + chestRows * 18);
		drawTexturedModalRect(guiLeft, guiTop + 17 + chestRows * 18, 0, 125, xSize, 13);
		for (l = 0; l < 3; ++l) {
			drawTexturedModalRect(guiLeft, guiTop + 17 + chestRows * 18 + 13 + l * 18, 0, 138, xSize, 18);
		}
		drawTexturedModalRect(guiLeft, guiTop + 17 + chestRows * 18 + 67, 0, 156, xSize, 96);
		mc.getTextureManager().bindTexture(LOTRGuiPouch.texture);
		for (l = 0; l < pouchRows; ++l) {
			drawTexturedModalRect(guiLeft + 7, guiTop + 17 + chestRows * 18 + 13 + l * 18, 0, 180, 162, 18);
		}
	}

	@Override
	public void drawGuiContainerForegroundLayer(int i, int j) {
		fontRendererObj.drawString(chestInv.hasCustomInventoryName() ? chestInv.getInventoryName() : StatCollector.translateToLocal(chestInv.getInventoryName()), 8, 6, 4210752);
		fontRendererObj.drawString(pouchInv.hasCustomInventoryName() ? pouchInv.getInventoryName() : StatCollector.translateToLocal(pouchInv.getInventoryName()), 8, ySize - 160, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 93, 4210752);
	}
}
