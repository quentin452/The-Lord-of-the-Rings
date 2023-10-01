package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.fellowship.LOTRFellowshipClient;
import lotr.common.world.map.LOTRCustomWaypoint;

import java.util.UUID;

public class LOTRPacketShareCWPClient implements IMessage {
	public int cwpID;
	public UUID fellowshipID;
	public boolean adding;

	public LOTRPacketShareCWPClient() {
	}

	public LOTRPacketShareCWPClient(int id, UUID fsID, boolean add) {
		cwpID = id;
		fellowshipID = fsID;
		adding = add;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		cwpID = data.readInt();
		fellowshipID = new UUID(data.readLong(), data.readLong());
		adding = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(cwpID);
		data.writeLong(fellowshipID.getMostSignificantBits());
		data.writeLong(fellowshipID.getLeastSignificantBits());
		data.writeBoolean(adding);
	}

	public static class Handler implements IMessageHandler<LOTRPacketShareCWPClient, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketShareCWPClient packet, MessageContext context) {
			LOTRCustomWaypoint cwp;
			LOTRPlayerData pd;
			if (!LOTRMod.proxy.isSingleplayer() && (cwp = (pd = LOTRLevelData.getData(LOTRMod.proxy.getClientPlayer())).getCustomWaypointByID(packet.cwpID)) != null) {
				if (packet.adding) {
					LOTRFellowshipClient fsClient = pd.getClientFellowshipByID(packet.fellowshipID);
					if (fsClient != null) {
						pd.customWaypointAddSharedFellowshipClient(cwp, fsClient);
					}
				} else {
					pd.customWaypointRemoveSharedFellowshipClient(cwp, packet.fellowshipID);
				}
			}
			return null;
		}
	}

}
