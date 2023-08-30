package lotr.client.gui;

import java.util.UUID;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;

import lotr.common.LOTRLevelData;
import lotr.common.fellowship.LOTRFellowshipClient;
import lotr.common.network.*;
import lotr.common.tileentity.LOTRTileEntityBeacon;
import net.minecraft.client.gui.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;

public class LOTRGuiBeacon extends LOTRGuiScreenBase {
	public static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/beacon.png");
	public int xSize = 200;
	public int ySize = 160;
	public int guiLeft;
	public int guiTop;
	public int beaconX;
	public int beaconY;
	public int beaconZ;
	public UUID initFellowshipID;
	public LOTRFellowshipClient initFellowship;
	public String initBeaconName;
	public String currentBeaconName;
	public GuiButton buttonDone;
	public GuiTextField fellowshipNameField;
	public GuiTextField beaconNameField;

	public LOTRGuiBeacon(LOTRTileEntityBeacon beacon) {
		beaconX = beacon.xCoord;
		beaconY = beacon.yCoord;
		beaconZ = beacon.zCoord;
		initFellowshipID = beacon.getFellowshipID();
		initBeaconName = beacon.getBeaconName();
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled && button == buttonDone) {
			mc.thePlayer.closeScreen();
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(guiTexture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		TileEntity te = mc.theWorld.getTileEntity(beaconX, beaconY, beaconZ);
		String s = new ItemStack(te.getBlockType(), 1, te.getBlockMetadata()).getDisplayName();
		fontRendererObj.drawString(s, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop + 11, 4210752);
		fellowshipNameField.drawTextBox();
		s = StatCollector.translateToLocal("container.lotr.beacon.nameFellowship");
		fontRendererObj.drawString(s, fellowshipNameField.xPosition + 4, fellowshipNameField.yPosition - 4 - fontRendererObj.FONT_HEIGHT, 4210752);
		currentBeaconName = beaconNameField.getText();
		beaconNameField.setEnabled(true);
		if (beaconNameField.isFocused()) {
			beaconNameField.drawTextBox();
		} else {
			String beaconNameEff = currentBeaconName;
			if (StringUtils.isBlank(beaconNameEff)) {
				beaconNameEff = fellowshipNameField.getText();
				beaconNameField.setEnabled(false);
			}
			beaconNameField.setText(beaconNameEff);
			beaconNameField.drawTextBox();
			beaconNameField.setText(currentBeaconName);
		}
		s = StatCollector.translateToLocal("container.lotr.beacon.nameBeacon");
		fontRendererObj.drawString(s, beaconNameField.xPosition + 4, beaconNameField.yPosition - 4 - fontRendererObj.FONT_HEIGHT * 2, 4210752);
		s = StatCollector.translateToLocal("container.lotr.beacon.namePrefix");
		fontRendererObj.drawString(s, beaconNameField.xPosition + 4, beaconNameField.yPosition - 4 - fontRendererObj.FONT_HEIGHT, 4210752);
		super.drawScreen(i, j, f);
	}

	@Override
	public void initGui() {
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		initFellowship = LOTRLevelData.getData(mc.thePlayer).getClientFellowshipByID(initFellowshipID);
		buttonDone = new GuiButton(0, guiLeft + xSize / 2 - 40, guiTop + 130, 80, 20, StatCollector.translateToLocal("container.lotr.beacon.done"));
		buttonList.add(buttonDone);
		fellowshipNameField = new GuiTextField(fontRendererObj, guiLeft + xSize / 2 - 80, guiTop + 45, 160, 20);
		fellowshipNameField.setMaxStringLength(40);
		if (initFellowship != null) {
			fellowshipNameField.setText(initFellowship.getName());
		}
		beaconNameField = new GuiTextField(fontRendererObj, guiLeft + xSize / 2 - 80, guiTop + 100, 160, 20);
		beaconNameField.setMaxStringLength(40);
		if (!StringUtils.isBlank(initBeaconName)) {
			beaconNameField.setText(initBeaconName);
		}
	}

	@Override
	public void keyTyped(char c, int i) {
		if (fellowshipNameField.getVisible() && fellowshipNameField.textboxKeyTyped(c, i) || beaconNameField.getVisible() && beaconNameField.textboxKeyTyped(c, i)) {
			return;
		}
		super.keyTyped(c, i);
	}

	@Override
	public void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		fellowshipNameField.mouseClicked(i, j, k);
		beaconNameField.mouseClicked(i, j, k);
	}

	@Override
	public void onGuiClosed() {
		sendBeaconEditPacket(true);
	}

	public void sendBeaconEditPacket(boolean closed) {
		LOTRFellowshipClient fs;
		UUID fsID = null;
		String fsName = fellowshipNameField.getText();
		if (!StringUtils.isBlank(fsName) && (fs = LOTRLevelData.getData(mc.thePlayer).getClientFellowshipByName(fsName)) != null) {
			fsID = fs.getFellowshipID();
		}
		String beaconName = currentBeaconName;
		IMessage packet = new LOTRPacketBeaconEdit(beaconX, beaconY, beaconZ, fsID, beaconName, true);
		LOTRPacketHandler.networkWrapper.sendToServer(packet);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		fellowshipNameField.updateCursorCounter();
		beaconNameField.updateCursorCounter();
		double dSq = mc.thePlayer.getDistanceSq(beaconX + 0.5, beaconY + 0.5, beaconZ + 0.5);
		if (dSq > 64.0) {
			mc.thePlayer.closeScreen();
		} else {
			TileEntity tileentity = mc.theWorld.getTileEntity(beaconX, beaconY, beaconZ);
			if (!(tileentity instanceof LOTRTileEntityBeacon)) {
				mc.thePlayer.closeScreen();
			}
		}
	}
}
