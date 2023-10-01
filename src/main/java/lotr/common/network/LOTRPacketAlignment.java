package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.fac.LOTRFaction;

import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

public class LOTRPacketAlignment implements IMessage {
	public UUID player;
	public Map<LOTRFaction, Float> alignmentMap = new EnumMap<>(LOTRFaction.class);
	public boolean hideAlignment;

	public LOTRPacketAlignment() {
	}

	public LOTRPacketAlignment(UUID uuid) {
		player = uuid;
		LOTRPlayerData pd = LOTRLevelData.getData(player);
		for (LOTRFaction f : LOTRFaction.values()) {
			float al = pd.getAlignment(f);
			alignmentMap.put(f, al);
		}
		hideAlignment = pd.getHideAlignment();
	}

	@Override
	public void fromBytes(ByteBuf data) {
		player = new UUID(data.readLong(), data.readLong());
		byte factionID;
		while ((factionID = data.readByte()) >= 0) {
			LOTRFaction f = LOTRFaction.forID(factionID);
			float alignment = data.readFloat();
			alignmentMap.put(f, alignment);
		}
		hideAlignment = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeLong(player.getMostSignificantBits());
		data.writeLong(player.getLeastSignificantBits());
		for (Map.Entry<LOTRFaction, Float> entry : alignmentMap.entrySet()) {
			LOTRFaction f = entry.getKey();
			float alignment = entry.getValue();
			data.writeByte(f.ordinal());
			data.writeFloat(alignment);
		}
		data.writeByte(-1);
		data.writeBoolean(hideAlignment);
	}

	public static class Handler implements IMessageHandler<LOTRPacketAlignment, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketAlignment packet, MessageContext context) {
			if (!LOTRMod.proxy.isSingleplayer()) {
				LOTRPlayerData pd = LOTRLevelData.getData(packet.player);
				for (Map.Entry entry : packet.alignmentMap.entrySet()) {
					LOTRFaction f = (LOTRFaction) entry.getKey();
					float alignment = (Float) entry.getValue();
					pd.setAlignment(f, alignment);
				}
				pd.setHideAlignment(packet.hideAlignment);
			}
			return null;
		}
	}

}
