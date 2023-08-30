package lotr.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.network.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.Entity;
import net.minecraft.util.StatCollector;

public class LOTRGuiHiredDismiss extends LOTRGuiNPCInteract {
	public LOTRGuiHiredDismiss(LOTREntityNPC entity) {
		super(entity);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button.id == 1) {
				mc.displayGuiScreen(new LOTRGuiHiredInteract(theEntity));
				return;
			}
			IMessage packet = new LOTRPacketHiredUnitDismiss(theEntity.getEntityId(), button.id);
			LOTRPacketHandler.networkWrapper.sendToServer(packet);
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		boolean hasRider;
		super.drawScreen(i, j, f);
		String s = StatCollector.translateToLocal("lotr.gui.dismiss.warning1");
		int y = height / 5 * 3;
		fontRendererObj.drawString(s, (width - fontRendererObj.getStringWidth(s)) / 2, y, 16777215);
		s = StatCollector.translateToLocal("lotr.gui.dismiss.warning2");
		fontRendererObj.drawString(s, (width - fontRendererObj.getStringWidth(s)) / 2, y += fontRendererObj.FONT_HEIGHT, 16777215);
		y += fontRendererObj.FONT_HEIGHT;
		Entity mount = theEntity.ridingEntity;
		Entity rider = theEntity.riddenByEntity;
		boolean hasMount = mount instanceof LOTREntityNPC && ((LOTREntityNPC) mount).hiredNPCInfo.getHiringPlayer() == mc.thePlayer;
		hasRider = rider instanceof LOTREntityNPC && ((LOTREntityNPC) rider).hiredNPCInfo.getHiringPlayer() == mc.thePlayer;
		if (hasMount) {
			s = StatCollector.translateToLocal("lotr.gui.dismiss.mount");
			fontRendererObj.drawString(s, (width - fontRendererObj.getStringWidth(s)) / 2, y, 11184810);
			y += fontRendererObj.FONT_HEIGHT;
		}
		if (hasRider) {
			s = StatCollector.translateToLocal("lotr.gui.dismiss.rider");
			fontRendererObj.drawString(s, (width - fontRendererObj.getStringWidth(s)) / 2, y, 11184810);
		}
	}

	@Override
	public void initGui() {
		buttonList.add(new GuiButton(0, width / 2 - 65, height / 5 * 3 + 40, 60, 20, StatCollector.translateToLocal("lotr.gui.dismiss.dismiss")));
		buttonList.add(new GuiButton(1, width / 2 + 5, height / 5 * 3 + 40, 60, 20, StatCollector.translateToLocal("lotr.gui.dismiss.cancel")));
	}
}
