package lotr.client.gui;

import lotr.common.entity.npc.LOTREntityNPCRideable;
import lotr.common.inventory.LOTRContainerNPCMountInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRGuiNPCMountInventory extends GuiContainer {
	public static ResourceLocation guiTexture = new ResourceLocation("textures/gui/container/horse.png");
	public IInventory thePlayerInv;
	public IInventory theMountInv;
	public LOTREntityNPCRideable theMount;
	public float mouseX;
	public float mouseY;

	public LOTRGuiNPCMountInventory(IInventory playerInv, IInventory mountInv, LOTREntityNPCRideable mount) {
		super(new LOTRContainerNPCMountInventory(playerInv, mountInv, mount));
		thePlayerInv = playerInv;
		theMountInv = mountInv;
		theMount = mount;
		allowUserInput = false;
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(guiTexture);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
		drawTexturedModalRect(k + 7, l + 35, 0, ySize + 54, 18, 18);
		GuiInventory.func_147046_a(k + 51, l + 60, 17, k + 51 - mouseX, l + 75 - 50 - mouseY, theMount);
	}

	@Override
	public void drawGuiContainerForegroundLayer(int i, int j) {
		fontRendererObj.drawString(theMountInv.hasCustomInventoryName() ? theMountInv.getInventoryName() : I18n.format(theMountInv.getInventoryName()), 8, 6, 4210752);
		fontRendererObj.drawString(thePlayerInv.hasCustomInventoryName() ? thePlayerInv.getInventoryName() : I18n.format(thePlayerInv.getInventoryName()), 8, ySize - 96 + 2, 4210752);
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		mouseX = i;
		mouseY = j;
		super.drawScreen(i, j, f);
	}
}
