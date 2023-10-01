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

public class LOTRPacketFTScreen implements IMessage {
	public boolean isCustom;
	public int wpID;
	public UUID sharingPlayer;
	public int startX;
	public int startZ;

	public LOTRPacketFTScreen() {
	}

	public LOTRPacketFTScreen(LOTRAbstractWaypoint wp, int x, int z) {
		isCustom = wp instanceof LOTRCustomWaypoint;
		wpID = wp.getID();
		if (wp instanceof LOTRCustomWaypoint) {
			sharingPlayer = ((LOTRCustomWaypoint) wp).getSharingPlayerID();
		}
		startX = x;
		startZ = z;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		isCustom = data.readBoolean();
		wpID = data.readInt();
		boolean shared = data.readBoolean();
		if (shared) {
			sharingPlayer = new UUID(data.readLong(), data.readLong());
		}
		startX = data.readInt();
		startZ = data.readInt();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeBoolean(isCustom);
		data.writeInt(wpID);
		boolean shared = sharingPlayer != null;
		data.writeBoolean(shared);
		if (shared) {
			data.writeLong(sharingPlayer.getMostSignificantBits());
			data.writeLong(sharingPlayer.getLeastSignificantBits());
		}
		data.writeInt(startX);
		data.writeInt(startZ);
	}

	public static class Handler implements IMessageHandler<LOTRPacketFTScreen, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketFTScreen packet, MessageContext context) {
			boolean custom = packet.isCustom;
			int wpID = packet.wpID;
			EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
			LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
			LOTRAbstractWaypoint waypoint = null;
			if (custom) {
				UUID sharingPlayerID = packet.sharingPlayer;
				waypoint = sharingPlayerID != null ? playerData.getSharedCustomWaypointByID(sharingPlayerID, wpID) : playerData.getCustomWaypointByID(wpID);
			} else {
				if (wpID >= 0 && wpID < LOTRWaypoint.values().length) {
					waypoint = LOTRWaypoint.values()[wpID];
				}
			}
			if (waypoint != null) {
				LOTRMod.proxy.displayFTScreen(waypoint, packet.startX, packet.startZ);
			}
			return null;
		}
	}

}
