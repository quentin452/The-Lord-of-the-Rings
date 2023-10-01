package lotr.common.entity.npc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class LOTRMercenaryTradeEntry extends LOTRUnitTradeEntry {
	public LOTRMercenary theMerc;

	public LOTRMercenaryTradeEntry(LOTRMercenary merc) {
		super(merc.getClass(), merc.getMercBaseCost(), merc.getMercAlignmentRequired());
		theMerc = merc;
	}

	public static LOTRMercenaryTradeEntry createFor(LOTRMercenary merc) {
		return new LOTRMercenaryTradeEntry(merc);
	}

	@Override
	public LOTREntityNPC getOrCreateHiredNPC(World world) {
		return (LOTREntityNPC) theMerc;
	}

	@Override
	public boolean hasRequiredCostAndAlignment(EntityPlayer entityplayer, LOTRHireableBase trader) {
		if (((LOTREntityNPC) theMerc).hiredNPCInfo.isActive) {
			return false;
		}
		return super.hasRequiredCostAndAlignment(entityplayer, trader);
	}
}
