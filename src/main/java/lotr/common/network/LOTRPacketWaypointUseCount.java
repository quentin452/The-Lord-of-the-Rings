package lotr.common.network;

import java.util.UUID;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.world.map.*;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRPacketWaypointUseCount implements IMessage {
	public boolean isCustom;
	public int wpID;
	public int useCount;
	public UUID sharingPlayer;

	public LOTRPacketWaypointUseCount() {
	}

	public LOTRPacketWaypointUseCount(LOTRAbstractWaypoint wp, int count) {
		isCustom = wp instanceof LOTRCustomWaypoint;
		wpID = wp.getID();
		useCount = count;
		if (wp instanceof LOTRCustomWaypoint) {
			sharingPlayer = ((LOTRCustomWaypoint) wp).getSharingPlayerID();
		}
	}

	@Override
	public void fromBytes(ByteBuf data) {
		isCustom = data.readBoolean();
		wpID = data.readInt();
		useCount = data.readInt();
		boolean shared = data.readBoolean();
		if (shared) {
			sharingPlayer = new UUID(data.readLong(), data.readLong());
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeBoolean(isCustom);
		data.writeInt(wpID);
		data.writeInt(useCount);
		boolean shared = sharingPlayer != null;
		data.writeBoolean(shared);
		if (shared) {
			data.writeLong(sharingPlayer.getMostSignificantBits());
			data.writeLong(sharingPlayer.getLeastSignificantBits());
		}
	}

	public static class Handler implements IMessageHandler<LOTRPacketWaypointUseCount, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketWaypointUseCount packet, MessageContext context) {
			boolean custom = packet.isCustom;
			int wpID = packet.wpID;
			EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
			LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
			LOTRAbstractWaypoint waypoint = null;
			if (custom) {
				UUID sharingPlayerID = packet.sharingPlayer;
				waypoint = sharingPlayerID != null ? pd.getSharedCustomWaypointByID(sharingPlayerID, wpID) : pd.getCustomWaypointByID(wpID);
			} else {
				if (wpID >= 0 && wpID < LOTRWaypoint.values().length) {
					waypoint = LOTRWaypoint.values()[wpID];
				}
			}
			if (waypoint != null) {
				pd.setWPUseCount(waypoint, packet.useCount);
			}
			return null;
		}
	}

}
