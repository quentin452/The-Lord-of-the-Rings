package lotr.common.entity.npc;

import net.minecraft.entity.player.EntityPlayer;

public interface LOTRTravellingTrader extends LOTRTradeable {
	LOTREntityNPC createTravellingEscort();

	String getDepartureSpeech();

	void startTraderVisiting(EntityPlayer entityplayer);
}
