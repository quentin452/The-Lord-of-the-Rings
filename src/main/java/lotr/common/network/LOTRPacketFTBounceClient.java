package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;

public class LOTRPacketFTBounceClient implements IMessage {
	@Override
	public void fromBytes(ByteBuf data) {
	}

	@Override
	public void toBytes(ByteBuf data) {
	}

	public static class Handler implements IMessageHandler<LOTRPacketFTBounceClient, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketFTBounceClient packet, MessageContext context) {
			LOTRMod.proxy.getClientPlayer();
			IMessage packetResponse = new LOTRPacketFTBounceServer();
			LOTRPacketHandler.networkWrapper.sendToServer(packetResponse);
			return null;
		}
	}

}
