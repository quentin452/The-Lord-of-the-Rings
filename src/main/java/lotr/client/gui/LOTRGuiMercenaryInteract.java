package lotr.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketMercenaryInteract;
import net.minecraft.client.gui.GuiButton;

public class LOTRGuiMercenaryInteract extends LOTRGuiUnitTradeInteract {
	public LOTRGuiMercenaryInteract(LOTREntityNPC entity) {
		super(entity);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			IMessage packet = new LOTRPacketMercenaryInteract(theEntity.getEntityId(), button.id);
			LOTRPacketHandler.networkWrapper.sendToServer(packet);
		}
	}
}
