package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.world.map.LOTRCustomWaypoint;
import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;

public class LOTRPacketDeleteCWPClient implements IMessage {
	public int cwpID;
	public UUID sharingPlayer;

	public LOTRPacketDeleteCWPClient() {
	}

	public LOTRPacketDeleteCWPClient(int id) {
		cwpID = id;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		cwpID = data.readInt();
		boolean shared = data.readBoolean();
		if (shared) {
			sharingPlayer = new UUID(data.readLong(), data.readLong());
		}
	}

	public LOTRPacketDeleteCWPClient setSharingPlayer(UUID player) {
		sharingPlayer = player;
		return this;
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(cwpID);
		boolean shared = sharingPlayer != null;
		data.writeBoolean(shared);
		if (shared) {
			data.writeLong(sharingPlayer.getMostSignificantBits());
			data.writeLong(sharingPlayer.getLeastSignificantBits());
		}
	}

	public static class Handler implements IMessageHandler<LOTRPacketDeleteCWPClient, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketDeleteCWPClient packet, MessageContext context) {
			LOTRCustomWaypoint cwp;
			EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
			LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
			if (packet.sharingPlayer != null) {
				LOTRCustomWaypoint cwp2;
				if (!LOTRMod.proxy.isSingleplayer() && (cwp2 = pd.getSharedCustomWaypointByID(packet.sharingPlayer, packet.cwpID)) != null) {
					pd.removeSharedCustomWaypoint(cwp2);
				}
			} else if (!LOTRMod.proxy.isSingleplayer() && (cwp = pd.getCustomWaypointByID(packet.cwpID)) != null) {
				pd.removeCustomWaypointClient(cwp);
			}
			return null;
		}
	}

}
