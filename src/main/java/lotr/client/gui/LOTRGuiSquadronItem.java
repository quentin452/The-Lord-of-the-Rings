package lotr.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import org.lwjgl.opengl.GL11;

import lotr.common.LOTRSquadrons;
import lotr.common.network.*;
import net.minecraft.client.gui.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

public class LOTRGuiSquadronItem extends LOTRGuiScreenBase {
	public static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/squadronItem.png");
	public int xSize = 200;
	public int ySize = 120;
	public int guiLeft;
	public int guiTop;
	public GuiButton buttonDone;
	public GuiTextField squadronNameField;
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
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(guiTexture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		String s = theItem.getDisplayName();
		fontRendererObj.drawString(s, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop + 11, 4210752);
		s = StatCollector.translateToLocal("lotr.gui.squadronItem.squadron");
		fontRendererObj.drawString(s, squadronNameField.xPosition, squadronNameField.yPosition - fontRendererObj.FONT_HEIGHT - 3, 4210752);
		boolean noSquadron = StringUtils.isNullOrEmpty(squadronNameField.getText()) && !squadronNameField.isFocused();
		if (noSquadron) {
			String squadronMessage = StatCollector.translateToLocal("lotr.gui.squadronItem.none");
			squadronNameField.setText(EnumChatFormatting.DARK_GRAY + squadronMessage);
		}
		squadronNameField.drawTextBox();
		if (noSquadron) {
			squadronNameField.setText("");
		}
		super.drawScreen(i, j, f);
	}

	@Override
	public void initGui() {
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		buttonList.add(buttonDone = new GuiButton(1, guiLeft + xSize / 2 - 40, guiTop + 85, 80, 20, StatCollector.translateToLocal("lotr.gui.squadronItem.done")));
		ItemStack itemstack = mc.thePlayer.inventory.getCurrentItem();
		if (itemstack != null && itemstack.getItem() instanceof LOTRSquadrons.SquadronItem) {
			theItem = itemstack;
			squadronNameField = new GuiTextField(fontRendererObj, guiLeft + xSize / 2 - 80, guiTop + 50, 160, 20);
			squadronNameField.setMaxStringLength(LOTRSquadrons.SQUADRON_LENGTH_MAX);
			String squadron = LOTRSquadrons.getSquadron(theItem);
			if (!StringUtils.isNullOrEmpty(squadron)) {
				squadronNameField.setText(squadron);
			}
		}
		if (theItem == null) {
			mc.thePlayer.closeScreen();
		}
	}

	@Override
	public void keyTyped(char c, int i) {
		if (squadronNameField.getVisible() && squadronNameField.textboxKeyTyped(c, i)) {
			return;
		}
		super.keyTyped(c, i);
	}

	@Override
	public void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		squadronNameField.mouseClicked(i, j, k);
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		String squadron = squadronNameField.getText();
		IMessage packet = new LOTRPacketItemSquadron(squadron);
		LOTRPacketHandler.networkWrapper.sendToServer(packet);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		squadronNameField.updateCursorCounter();
		ItemStack itemstack = mc.thePlayer.getCurrentEquippedItem();
		if (itemstack == null || !(itemstack.getItem() instanceof LOTRSquadrons.SquadronItem)) {
			mc.thePlayer.closeScreen();
		} else {
			theItem = itemstack;
		}
	}
}
