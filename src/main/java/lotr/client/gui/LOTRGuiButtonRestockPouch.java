package lotr.client.gui;

import lotr.client.LOTRReflectionClient;
import lotr.common.LOTRMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRGuiButtonRestockPouch extends GuiButton {
	public static ResourceLocation texture = new ResourceLocation("lotr:gui/widgets.png");
	public GuiContainer parentGUI;

	public LOTRGuiButtonRestockPouch(GuiContainer parent, int i, int j, int k) {
		super(i, j, k, 10, 10, "");
		parentGUI = parent;
	}

	public void checkPouchRestockEnabled(Minecraft mc) {
		InventoryPlayer inv = mc.thePlayer.inventory;
		enabled = visible = inv.hasItem(LOTRMod.pouch);
		if (parentGUI instanceof GuiContainerCreative && LOTRReflectionClient.getCreativeTabIndex((GuiContainerCreative) parentGUI) != CreativeTabs.tabInventory.getTabIndex()) {
			visible = false;
			enabled = false;
		}
	}

	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		checkPouchRestockEnabled(mc);
		if (visible) {
			mc.getTextureManager().bindTexture(texture);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			field_146123_n = i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height;
			int k = getHoverState(field_146123_n);
			drawTexturedModalRect(xPosition, yPosition, 0, 128 + k * 10, width, height);
			mouseDragged(mc, i, j);
		}
	}
}
