package lotr.common.network;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.world.map.LOTRAbstractWaypoint;
import lotr.common.world.map.LOTRCustomWaypoint;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketRenameCWP implements IMessage {
	public int wpID;
	public String name;

	public LOTRPacketRenameCWP() {
	}

	public LOTRPacketRenameCWP(LOTRAbstractWaypoint wp, String s) {
		wpID = wp.getID();
		name = s;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		wpID = data.readInt();
		short length = data.readShort();
		name = data.readBytes(length).toString(Charsets.UTF_8);
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(wpID);
		byte[] nameBytes = name.getBytes(Charsets.UTF_8);
		data.writeShort(nameBytes.length);
		data.writeBytes(nameBytes);
	}

	public static class Handler implements IMessageHandler<LOTRPacketRenameCWP, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketRenameCWP packet, MessageContext context) {
			String wpName;
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
			LOTRCustomWaypoint cwp = pd.getCustomWaypointByID(packet.wpID);
			if (cwp != null && (wpName = LOTRCustomWaypoint.validateCustomName(packet.name)) != null) {
				pd.renameCustomWaypoint(cwp, wpName);
			}
			return null;
		}
	}

}
