package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.map.LOTRConquestGrid;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketConquestGridRequest implements IMessage {
	public LOTRFaction conqFac;

	public LOTRPacketConquestGridRequest() {
	}

	public LOTRPacketConquestGridRequest(LOTRFaction fac) {
		conqFac = fac;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte facID = data.readByte();
		conqFac = LOTRFaction.forID(facID);
	}

	@Override
	public void toBytes(ByteBuf data) {
		int facID = conqFac.ordinal();
		data.writeByte(facID);
	}

	public static class Handler implements IMessageHandler<LOTRPacketConquestGridRequest, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketConquestGridRequest packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			LOTRFaction fac = packet.conqFac;
			if (fac != null) {
				LOTRConquestGrid.ConquestViewableQuery query = LOTRConquestGrid.canPlayerViewConquest(entityplayer, fac);
				if (query.result == LOTRConquestGrid.ConquestViewable.CAN_VIEW) {
					LOTRConquestGrid.sendConquestGridTo(entityplayer, fac);
				}
			}
			return null;
		}
	}

}
