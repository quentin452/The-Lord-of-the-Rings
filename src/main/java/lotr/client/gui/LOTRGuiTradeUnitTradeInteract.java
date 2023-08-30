package lotr.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.network.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;

public class LOTRGuiTradeUnitTradeInteract extends LOTRGuiTradeInteract {
	public GuiButton buttonHire;

	public LOTRGuiTradeUnitTradeInteract(LOTREntityNPC entity) {
		super(entity);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button == buttonHire) {
				IMessage packet = new LOTRPacketUnitTraderInteract(theEntity.getEntityId(), 1);
				LOTRPacketHandler.networkWrapper.sendToServer(packet);
			} else {
				super.actionPerformed(button);
			}
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonHire = new GuiButton(-1, width / 2 - 65, height / 5 * 3 + 50, 130, 20, StatCollector.translateToLocal("lotr.gui.npc.hire"));
		buttonList.add(buttonHire);
	}
}
