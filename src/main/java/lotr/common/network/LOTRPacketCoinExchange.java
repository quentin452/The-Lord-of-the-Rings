package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.inventory.LOTRContainerCoinExchange;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;

public class LOTRPacketCoinExchange implements IMessage {
	public int button;

	public LOTRPacketCoinExchange() {
	}

	public LOTRPacketCoinExchange(int i) {
		button = i;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		button = data.readByte();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(button);
	}

	public static class Handler implements IMessageHandler<LOTRPacketCoinExchange, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketCoinExchange packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			Container container = entityplayer.openContainer;
			if (container instanceof LOTRContainerCoinExchange) {
				LOTRContainerCoinExchange coinExchange = (LOTRContainerCoinExchange) container;
				coinExchange.handleExchangePacket(packet.button);
			}
			return null;
		}
	}

}
