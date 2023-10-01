package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityInvasionSpawner;

public class LOTRPacketInvasionWatch implements IMessage {
	public int invasionEntityID;
	public boolean overrideAlreadyWatched;

	public LOTRPacketInvasionWatch() {
	}

	public LOTRPacketInvasionWatch(LOTREntityInvasionSpawner invasion, boolean override) {
		invasionEntityID = invasion.getEntityId();
		overrideAlreadyWatched = override;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		invasionEntityID = data.readInt();
		overrideAlreadyWatched = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(invasionEntityID);
		data.writeBoolean(overrideAlreadyWatched);
	}

	public static class Handler implements IMessageHandler<LOTRPacketInvasionWatch, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketInvasionWatch packet, MessageContext context) {
			LOTRMod.proxy.handleInvasionWatch(packet.invasionEntityID, packet.overrideAlreadyWatched);
			return null;
		}
	}

}
