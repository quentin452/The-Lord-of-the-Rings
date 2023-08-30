package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class LOTRPacketIsOpRequest implements IMessage {
	@Override
	public void fromBytes(ByteBuf data) {
	}

	@Override
	public void toBytes(ByteBuf data) {
	}

	public static class Handler implements IMessageHandler<LOTRPacketIsOpRequest, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketIsOpRequest packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			boolean isOp = MinecraftServer.getServer().getConfigurationManager().func_152596_g(entityplayer.getGameProfile());
			IMessage packetResponse = new LOTRPacketIsOpResponse(isOp);
			LOTRPacketHandler.networkWrapper.sendTo(packetResponse, entityplayer);
			return null;
		}
	}

}
