package lotr.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.world.map.LOTRAbstractWaypoint;
import lotr.common.world.map.LOTRCustomWaypoint;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketShareCWP implements IMessage {
	public int wpID;
	public String fsName;
	public boolean adding;

	public LOTRPacketShareCWP() {
	}

	public LOTRPacketShareCWP(LOTRAbstractWaypoint wp, String s, boolean add) {
		wpID = wp.getID();
		fsName = s;
		adding = add;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		wpID = data.readInt();
		short length = data.readShort();
		fsName = data.readBytes(length).toString(Charsets.UTF_8);
		adding = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(wpID);
		byte[] nameBytes = fsName.getBytes(Charsets.UTF_8);
		data.writeShort(nameBytes.length);
		data.writeBytes(nameBytes);
		data.writeBoolean(adding);
	}

	public static class Handler implements IMessageHandler<LOTRPacketShareCWP, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketShareCWP packet, MessageContext context) {
			LOTRFellowship fellowship;
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
			LOTRCustomWaypoint cwp = pd.getCustomWaypointByID(packet.wpID);
			if (cwp != null && (fellowship = pd.getFellowshipByName(packet.fsName)) != null) {
				if (packet.adding) {
					pd.customWaypointAddSharedFellowship(cwp, fellowship);
				} else {
					pd.customWaypointRemoveSharedFellowship(cwp, fellowship);
				}
			}
			return null;
		}
	}

}
