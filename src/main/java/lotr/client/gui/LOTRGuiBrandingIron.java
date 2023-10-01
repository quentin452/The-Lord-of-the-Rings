package lotr.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.item.LOTRItemBrandingIron;
import lotr.common.network.LOTRPacketBrandingIron;
import lotr.common.network.LOTRPacketHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;

public class LOTRGuiBrandingIron extends LOTRGuiScreenBase {
	public static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/brandingIron.png");
	public static RenderItem itemRenderer = new RenderItem();
	public int xSize = 200;
	public int ySize = 132;
	public int guiLeft;
	public int guiTop;
	public GuiButton buttonDone;
	public GuiTextField brandNameField;
	public ItemStack theItem;

	@Override
	public void actionPerformed(GuiButton button) {
		if (button == buttonDone) {
			mc.thePlayer.closeScreen();
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(guiTexture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		String s = StatCollector.translateToLocal("lotr.gui.brandingIron.title");
		fontRendererObj.drawString(s, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop + 11, 4210752);
		s = StatCollector.translateToLocal("lotr.gui.brandingIron.naming");
		fontRendererObj.drawString(s, brandNameField.xPosition, brandNameField.yPosition - fontRendererObj.FONT_HEIGHT - 3, 4210752);
		s = StatCollector.translateToLocal("lotr.gui.brandingIron.unnameHint");
		fontRendererObj.drawString(s, brandNameField.xPosition, brandNameField.yPosition + brandNameField.height + 3, 4210752);
		brandNameField.drawTextBox();
		buttonDone.enabled = !StringUtils.isBlank(brandNameField.getText());
		super.drawScreen(i, j, f);
		if (theItem != null) {
			itemRenderer.renderItemIntoGUI(fontRendererObj, mc.getTextureManager(), theItem, guiLeft + 8, guiTop + 8);
		}
	}

	@Override
	public void initGui() {
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		buttonDone = new GuiButton(1, guiLeft + xSize / 2 - 40, guiTop + 97, 80, 20, StatCollector.translateToLocal("lotr.gui.brandingIron.done"));
		buttonList.add(buttonDone);
		ItemStack itemstack = mc.thePlayer.inventory.getCurrentItem();
		if (itemstack != null && itemstack.getItem() instanceof LOTRItemBrandingIron) {
			theItem = itemstack;
			brandNameField = new GuiTextField(fontRendererObj, guiLeft + xSize / 2 - 80, guiTop + 50, 160, 20);
		}
		if (theItem == null) {
			mc.thePlayer.closeScreen();
		}
	}

	@Override
	public void keyTyped(char c, int i) {
		if (brandNameField.getVisible() && brandNameField.textboxKeyTyped(c, i)) {
			return;
		}
		super.keyTyped(c, i);
	}

	@Override
	public void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		brandNameField.mouseClicked(i, j, k);
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		String brandName = brandNameField.getText();
		if (!StringUtils.isBlank(brandName)) {
			IMessage packet = new LOTRPacketBrandingIron(brandName);
			LOTRPacketHandler.networkWrapper.sendToServer(packet);
		}
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		brandNameField.updateCursorCounter();
		ItemStack itemstack = mc.thePlayer.getCurrentEquippedItem();
		if (itemstack == null || !(itemstack.getItem() instanceof LOTRItemBrandingIron)) {
			mc.thePlayer.closeScreen();
		} else {
			theItem = itemstack;
		}
	}
}
