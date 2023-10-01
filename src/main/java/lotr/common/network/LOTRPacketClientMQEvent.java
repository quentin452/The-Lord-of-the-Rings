package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.quest.LOTRMiniQuestEvent;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketClientMQEvent implements IMessage {
	public ClientMQEvent type;

	public LOTRPacketClientMQEvent() {
	}

	public LOTRPacketClientMQEvent(ClientMQEvent t) {
		type = t;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte typeID = data.readByte();
		if (typeID >= 0 && typeID < ClientMQEvent.values().length) {
			type = ClientMQEvent.values()[typeID];
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(type.ordinal());
	}

	public enum ClientMQEvent {
		MAP, FACTIONS

	}

	public static class Handler implements IMessageHandler<LOTRPacketClientMQEvent, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketClientMQEvent packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
			if (packet.type == ClientMQEvent.MAP) {
				pd.distributeMQEvent(new LOTRMiniQuestEvent.ViewMap());
			} else if (packet.type == ClientMQEvent.FACTIONS) {
				pd.distributeMQEvent(new LOTRMiniQuestEvent.ViewFactions());
			}
			return null;
		}
	}

}
