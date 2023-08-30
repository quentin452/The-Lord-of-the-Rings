package lotr.client.gui;

import lotr.common.entity.npc.LOTREntityNPC;

public abstract class LOTRGuiNPCInteract extends LOTRGuiScreenBase {
	public LOTREntityNPC theEntity;

	protected LOTRGuiNPCInteract(LOTREntityNPC entity) {
		theEntity = entity;
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		String s = theEntity.getCommandSenderName();
		fontRendererObj.drawString(s, (width - fontRendererObj.getStringWidth(s)) / 2, height / 5 * 3 - 20, 16777215);
		super.drawScreen(i, j, f);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		if (theEntity == null || !theEntity.isEntityAlive() || theEntity.getDistanceSqToEntity(mc.thePlayer) > 100.0) {
			mc.thePlayer.closeScreen();
		}
	}
}
