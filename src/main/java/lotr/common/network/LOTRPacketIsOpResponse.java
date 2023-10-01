package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;

public class LOTRPacketIsOpResponse implements IMessage {
	public boolean isOp;

	public LOTRPacketIsOpResponse() {
	}

	public LOTRPacketIsOpResponse(boolean flag) {
		isOp = flag;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		isOp = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeBoolean(isOp);
	}

	public static class Handler implements IMessageHandler<LOTRPacketIsOpResponse, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketIsOpResponse packet, MessageContext context) {
			LOTRMod.proxy.setMapIsOp(packet.isOp);
			return null;
		}
	}

}
