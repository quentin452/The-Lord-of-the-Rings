package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;

public class LOTRPacketEnableAlignmentZones implements IMessage {
	public boolean enable;

	public LOTRPacketEnableAlignmentZones() {
	}

	public LOTRPacketEnableAlignmentZones(boolean flag) {
		enable = flag;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		enable = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeBoolean(enable);
	}

	public static class Handler implements IMessageHandler<LOTRPacketEnableAlignmentZones, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketEnableAlignmentZones packet, MessageContext context) {
			LOTRLevelData.setEnableAlignmentZones(packet.enable);
			return null;
		}
	}

}
