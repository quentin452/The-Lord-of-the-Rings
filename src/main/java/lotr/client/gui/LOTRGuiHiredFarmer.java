package lotr.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.LOTRSquadrons;
import lotr.common.entity.npc.*;
import lotr.common.network.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;

public class LOTRGuiHiredFarmer extends LOTRGuiHiredNPC {
	public LOTRGuiButtonOptions buttonGuardMode;
	public LOTRGuiSlider sliderGuardRange;
	public GuiTextField squadronNameField;
	public boolean sendSquadronUpdate;

	public LOTRGuiHiredFarmer(LOTREntityNPC npc) {
		super(npc);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button instanceof LOTRGuiSlider) {
			return;
		}
		if (button.enabled) {
			sendActionPacket(button.id);
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		super.drawScreen(i, j, f);
		String s = theNPC.hiredNPCInfo.getStatusString();
		fontRendererObj.drawString(s, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop + 48, 4210752);
		s = StatCollector.translateToLocal("lotr.gui.farmer.squadron");
		fontRendererObj.drawString(s, squadronNameField.xPosition, squadronNameField.yPosition - fontRendererObj.FONT_HEIGHT - 3, 4210752);
		squadronNameField.drawTextBox();
	}

	@Override
	public void initGui() {
		super.initGui();
		int midX = guiLeft + xSize / 2;
		buttonGuardMode = new LOTRGuiButtonOptions(0, midX - 80, guiTop + 60, 160, 20, StatCollector.translateToLocal("lotr.gui.farmer.mode"));
		buttonList.add(buttonGuardMode);
		buttonGuardMode.setState(theNPC.hiredNPCInfo.isGuardMode());
		sliderGuardRange = new LOTRGuiSlider(1, midX - 80, guiTop + 84, 160, 20, StatCollector.translateToLocal("lotr.gui.farmer.range"));
		buttonList.add(sliderGuardRange);
		sliderGuardRange.setMinMaxValues(LOTRHiredNPCInfo.GUARD_RANGE_MIN, LOTRHiredNPCInfo.GUARD_RANGE_MAX);
		sliderGuardRange.setSliderValue(theNPC.hiredNPCInfo.getGuardRange());
		sliderGuardRange.visible = theNPC.hiredNPCInfo.isGuardMode();
		squadronNameField = new GuiTextField(fontRendererObj, midX - 80, guiTop + 120, 160, 20);
		squadronNameField.setMaxStringLength(LOTRSquadrons.SQUADRON_LENGTH_MAX);
		String squadron = theNPC.hiredNPCInfo.getSquadron();
		if (!StringUtils.isNullOrEmpty(squadron)) {
			squadronNameField.setText(squadron);
		}
		buttonList.add(new LOTRGuiButtonOptions(2, midX - 80, guiTop + 144, 160, 20, StatCollector.translateToLocal("lotr.gui.farmer.openInv")));
	}

	@Override
	public void keyTyped(char c, int i) {
		if (squadronNameField != null && squadronNameField.getVisible() && squadronNameField.textboxKeyTyped(c, i)) {
			theNPC.hiredNPCInfo.setSquadron(squadronNameField.getText());
			sendSquadronUpdate = true;
			return;
		}
		super.keyTyped(c, i);
	}

	@Override
	public void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		if (squadronNameField != null) {
			squadronNameField.mouseClicked(i, j, k);
		}
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		if (sendSquadronUpdate) {
			String squadron = theNPC.hiredNPCInfo.getSquadron();
			IMessage packet = new LOTRPacketNPCSquadron(theNPC, squadron);
			LOTRPacketHandler.networkWrapper.sendToServer(packet);
		}
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		buttonGuardMode.setState(theNPC.hiredNPCInfo.isGuardMode());
		sliderGuardRange.visible = theNPC.hiredNPCInfo.isGuardMode();
		if (sliderGuardRange.dragging) {
			int i = sliderGuardRange.getSliderValue();
			theNPC.hiredNPCInfo.setGuardRange(i);
			sendActionPacket(sliderGuardRange.id, i);
		}
		squadronNameField.updateCursorCounter();
	}
}
