package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;

public class LOTRPacketConquestNotification implements IMessage {
	public LOTRFaction conqFac;
	public float conqVal;
	public boolean isCleansing;

	public LOTRPacketConquestNotification() {
	}

	public LOTRPacketConquestNotification(LOTRFaction fac, float f, boolean clean) {
		conqFac = fac;
		conqVal = f;
		isCleansing = clean;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte facID = data.readByte();
		conqFac = LOTRFaction.forID(facID);
		conqVal = data.readFloat();
		isCleansing = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(conqFac.ordinal());
		data.writeFloat(conqVal);
		data.writeBoolean(isCleansing);
	}

	public static class Handler implements IMessageHandler<LOTRPacketConquestNotification, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketConquestNotification packet, MessageContext context) {
			if (packet.conqFac != null) {
				LOTRMod.proxy.queueConquestNotification(packet.conqFac, packet.conqVal, packet.isCleansing);
			}
			return null;
		}
	}

}
