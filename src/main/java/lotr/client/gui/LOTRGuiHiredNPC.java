package lotr.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRUnitTradeEntry;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRFaction;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketHiredUnitCommand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collection;

public abstract class LOTRGuiHiredNPC extends LOTRGuiScreenBase {
	public static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/npc/hired.png");
	public int xSize = 200;
	public int ySize = 220;
	public int guiLeft;
	public int guiTop;
	public LOTREntityNPC theNPC;
	public int page;

	protected LOTRGuiHiredNPC(LOTREntityNPC npc) {
		theNPC = npc;
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(guiTexture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		String s = theNPC.getNPCName();
		fontRendererObj.drawString(s, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop + 11, 3618615);
		s = theNPC.getEntityClassName();
		fontRendererObj.drawString(s, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop + 26, 3618615);
		if (page == 0 && theNPC.hiredNPCInfo.hasHiringRequirements()) {
			int x = guiLeft + 6;
			int y = guiTop + 170;
			s = StatCollector.translateToLocal("lotr.hiredNPC.commandReq");
			fontRendererObj.drawString(s, x, y, 3618615);
			y += fontRendererObj.FONT_HEIGHT;
			x += 4;
			int maxWidth = xSize - 12 - 4;
			LOTRFaction fac = theNPC.getHiringFaction();
			String alignS = LOTRAlignmentValues.formatAlignForDisplay(theNPC.hiredNPCInfo.alignmentRequiredToCommand);
			String alignReq = StatCollector.translateToLocalFormatted("lotr.hiredNPC.commandReq.align", alignS, fac.factionName());
			Collection requirementLines = new ArrayList(fontRendererObj.listFormattedStringToWidth(alignReq, maxWidth));
			LOTRUnitTradeEntry.PledgeType pledge = theNPC.hiredNPCInfo.pledgeType;
			String pledgeReq = pledge.getCommandReqText(fac);
			if (pledgeReq != null) {
				requirementLines.addAll(fontRendererObj.listFormattedStringToWidth(pledgeReq, maxWidth));
			}
			for (Object obj : requirementLines) {
				String line = (String) obj;
				fontRendererObj.drawString(line, x, y, 3618615);
				y += fontRendererObj.FONT_HEIGHT;
			}
		}
		super.drawScreen(i, j, f);
	}

	@Override
	public void initGui() {
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		sendActionPacket(-1);
	}

	public void sendActionPacket(int action) {
		sendActionPacket(action, 0);
	}

	public void sendActionPacket(int action, int value) {
		IMessage packet = new LOTRPacketHiredUnitCommand(theNPC.getEntityId(), page, action, value);
		LOTRPacketHandler.networkWrapper.sendToServer(packet);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		if (!theNPC.isEntityAlive() || theNPC.hiredNPCInfo.getHiringPlayer() != mc.thePlayer || theNPC.getDistanceSqToEntity(mc.thePlayer) > 64.0) {
			mc.thePlayer.closeScreen();
		}
	}
}
