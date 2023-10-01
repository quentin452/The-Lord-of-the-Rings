package lotr.client.gui;

import lotr.common.entity.npc.LOTRMercenary;
import lotr.common.entity.npc.LOTRMercenaryTradeEntry;
import lotr.common.entity.npc.LOTRUnitTradeEntries;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class LOTRGuiMercenaryHire extends LOTRGuiHireBase {
	public LOTRGuiMercenaryHire(EntityPlayer entityplayer, LOTRMercenary mercenary, World world) {
		super(entityplayer, mercenary, world);
		LOTRMercenaryTradeEntry e = LOTRMercenaryTradeEntry.createFor(mercenary);
		LOTRUnitTradeEntries trades = new LOTRUnitTradeEntries(0.0f, e);
		setTrades(trades);
	}
}
