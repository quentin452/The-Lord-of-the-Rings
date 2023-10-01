package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;

public class LOTRPacketFTCooldown implements IMessage {
	public int cooldownMax;
	public int cooldownMin;

	public LOTRPacketFTCooldown() {
	}

	public LOTRPacketFTCooldown(int max, int min) {
		cooldownMax = max;
		cooldownMin = min;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		cooldownMax = data.readInt();
		cooldownMin = data.readInt();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(cooldownMax);
		data.writeInt(cooldownMin);
	}

	public static class Handler implements IMessageHandler<LOTRPacketFTCooldown, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketFTCooldown packet, MessageContext context) {
			LOTRLevelData.setWaypointCooldown(packet.cooldownMax, packet.cooldownMin);
			return null;
		}
	}

}
