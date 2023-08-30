package lotr.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketHiredUnitInteract;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;

import java.util.List;

public class LOTRGuiHiredInteract extends LOTRGuiNPCInteract {
	public LOTRGuiHiredInteract(LOTREntityNPC entity) {
		super(entity);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button.id == 2) {
				mc.displayGuiScreen(new LOTRGuiHiredDismiss(theEntity));
				return;
			}
			IMessage packet = new LOTRPacketHiredUnitInteract(theEntity.getEntityId(), button.id);
			LOTRPacketHandler.networkWrapper.sendToServer(packet);
		}
	}

	@Override
	public void initGui() {
		buttonList.add(new GuiButton(0, width / 2 - 65, height / 5 * 3, 60, 20, StatCollector.translateToLocal("lotr.gui.npc.talk")));
		buttonList.add(new GuiButton(1, width / 2 + 5, height / 5 * 3, 60, 20, StatCollector.translateToLocal("lotr.gui.npc.command")));
		buttonList.add(new GuiButton(2, width / 2 - 65, height / 5 * 3 + 25, 130, 20, StatCollector.translateToLocal("lotr.gui.npc.dismiss")));
		((List<GuiButton>) buttonList).get(0).enabled = theEntity.getSpeechBank(mc.thePlayer) != null;
	}
}
