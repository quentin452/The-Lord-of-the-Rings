package lotr.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.network.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;

public class LOTRGuiUnitTradeInteract extends LOTRGuiNPCInteract {
	public GuiButton buttonTalk;
	public GuiButton buttonHire;

	public LOTRGuiUnitTradeInteract(LOTREntityNPC entity) {
		super(entity);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			IMessage packet = new LOTRPacketUnitTraderInteract(theEntity.getEntityId(), button.id);
			LOTRPacketHandler.networkWrapper.sendToServer(packet);
		}
	}

	@Override
	public void initGui() {
		buttonTalk = new GuiButton(0, width / 2 - 65, height / 5 * 3, 60, 20, StatCollector.translateToLocal("lotr.gui.npc.talk"));
		buttonHire = new GuiButton(1, width / 2 + 5, height / 5 * 3, 60, 20, StatCollector.translateToLocal("lotr.gui.npc.hire"));
		buttonList.add(buttonTalk);
		buttonList.add(buttonHire);
	}
}
