package lotr.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRGuiMessageTypes;
import lotr.common.LOTRMod;

public class LOTRPacketMessage implements IMessage {
	public LOTRGuiMessageTypes message;

	public LOTRPacketMessage() {
	}

	public LOTRPacketMessage(LOTRGuiMessageTypes m) {
		message = m;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte messageID = data.readByte();
		if (messageID < 0 || messageID >= LOTRGuiMessageTypes.values().length) {
			FMLLog.severe("Failed to display LOTR message: There is no message with ID " + messageID);
			message = null;
		} else {
			message = LOTRGuiMessageTypes.values()[messageID];
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(message.ordinal());
	}

	public static class Handler implements IMessageHandler<LOTRPacketMessage, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketMessage packet, MessageContext context) {
			if (packet.message != null) {
				LOTRMod.proxy.displayMessage(packet.message);
			}
			return null;
		}
	}

}
