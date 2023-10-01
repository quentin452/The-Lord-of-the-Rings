package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketFTBounceServer implements IMessage {
	@Override
	public void fromBytes(ByteBuf data) {
	}

	@Override
	public void toBytes(ByteBuf data) {
	}

	public static class Handler implements IMessageHandler<LOTRPacketFTBounceServer, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketFTBounceServer packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			LOTRLevelData.getData(entityplayer).receiveFTBouncePacket();
			return null;
		}
	}

}
