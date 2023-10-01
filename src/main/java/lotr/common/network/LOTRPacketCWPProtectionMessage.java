package lotr.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import net.minecraft.util.IChatComponent;

public class LOTRPacketCWPProtectionMessage implements IMessage {
	public IChatComponent message;

	public LOTRPacketCWPProtectionMessage() {
	}

	public LOTRPacketCWPProtectionMessage(IChatComponent c) {
		message = c;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		int length = data.readInt();
		ByteBuf srlBytes = data.readBytes(length);
		String serialised = srlBytes.toString(Charsets.UTF_8);
		message = IChatComponent.Serializer.func_150699_a(serialised);
	}

	@Override
	public void toBytes(ByteBuf data) {
		String serialised = IChatComponent.Serializer.func_150696_a(message);
		byte[] srlBytes = serialised.getBytes(Charsets.UTF_8);
		data.writeInt(srlBytes.length);
		data.writeBytes(srlBytes);
	}

	public static class Handler implements IMessageHandler<LOTRPacketCWPProtectionMessage, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketCWPProtectionMessage packet, MessageContext context) {
			LOTRMod.proxy.setMapCWPProtectionMessage(packet.message);
			return null;
		}
	}

}
