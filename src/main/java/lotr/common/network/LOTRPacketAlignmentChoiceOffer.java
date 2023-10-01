package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;

public class LOTRPacketAlignmentChoiceOffer implements IMessage {
	@Override
	public void fromBytes(ByteBuf data) {
	}

	@Override
	public void toBytes(ByteBuf data) {
	}

	public static class Handler implements IMessageHandler<LOTRPacketAlignmentChoiceOffer, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketAlignmentChoiceOffer packet, MessageContext context) {
			LOTRMod.proxy.displayAlignmentChoice();
			return null;
		}
	}

}
