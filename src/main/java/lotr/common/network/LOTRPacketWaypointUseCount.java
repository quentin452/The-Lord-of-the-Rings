package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.world.map.LOTRAbstractWaypoint;
import lotr.common.world.map.LOTRCustomWaypoint;
import lotr.common.world.map.LOTRWaypoint;
import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;

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
