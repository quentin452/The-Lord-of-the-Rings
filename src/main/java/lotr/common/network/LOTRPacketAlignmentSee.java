package lotr.common.network;

import java.util.*;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.fac.LOTRFaction;

public class LOTRPacketAlignmentSee implements IMessage {
	public String username;
	public Map<LOTRFaction, Float> alignmentMap = new EnumMap<>(LOTRFaction.class);

	public LOTRPacketAlignmentSee() {
	}

	public LOTRPacketAlignmentSee(String name, LOTRPlayerData pd) {
		username = name;
		for (LOTRFaction f : LOTRFaction.getPlayableAlignmentFactions()) {
			float al = pd.getAlignment(f);
			alignmentMap.put(f, al);
		}
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte length = data.readByte();
		ByteBuf nameBytes = data.readBytes(length);
		username = nameBytes.toString(Charsets.UTF_8);
		byte factionID;
		while ((factionID = data.readByte()) >= 0) {
			LOTRFaction f = LOTRFaction.forID(factionID);
			float alignment = data.readFloat();
			alignmentMap.put(f, alignment);
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		byte[] nameBytes = username.getBytes(Charsets.UTF_8);
		data.writeByte(nameBytes.length);
		data.writeBytes(nameBytes);
		for (Map.Entry<LOTRFaction, Float> entry : alignmentMap.entrySet()) {
			LOTRFaction f = entry.getKey();
			float alignment = entry.getValue();
			data.writeByte(f.ordinal());
			data.writeFloat(alignment);
		}
		data.writeByte(-1);
	}

	public static class Handler implements IMessageHandler<LOTRPacketAlignmentSee, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketAlignmentSee packet, MessageContext context) {
			LOTRMod.proxy.displayAlignmentSee(packet.username, packet.alignmentMap);
			return null;
		}
	}

}
