package lotr.common.network;

import java.util.Map;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.fac.*;
import net.minecraft.util.StatCollector;

public class LOTRPacketAlignmentBonus implements IMessage {
	public LOTRFaction mainFaction;
	public float prevMainAlignment;
	public LOTRAlignmentBonusMap factionBonusMap = new LOTRAlignmentBonusMap();
	public float conquestBonus;
	public double posX;
	public double posY;
	public double posZ;
	public String name;
	public boolean needsTranslation;
	public boolean isKill;
	public boolean isHiredKill;

	public LOTRPacketAlignmentBonus() {
	}

	public LOTRPacketAlignmentBonus(LOTRFaction f, float pre, LOTRAlignmentBonusMap fMap, float conqBonus, double x, double y, double z, LOTRAlignmentValues.AlignmentBonus source) {
		mainFaction = f;
		prevMainAlignment = pre;
		factionBonusMap = fMap;
		conquestBonus = conqBonus;
		posX = x;
		posY = y;
		posZ = z;
		name = source.name;
		needsTranslation = source.needsTranslation;
		isKill = source.isKill;
		isHiredKill = source.killByHiredUnit;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		mainFaction = LOTRFaction.forID(data.readByte());
		prevMainAlignment = data.readFloat();
		byte factionID;
		while ((factionID = data.readByte()) >= 0) {
			LOTRFaction faction = LOTRFaction.forID(factionID);
			float bonus = data.readFloat();
			factionBonusMap.put(faction, bonus);
		}
		conquestBonus = data.readFloat();
		posX = data.readDouble();
		posY = data.readDouble();
		posZ = data.readDouble();
		short length = data.readShort();
		name = data.readBytes(length).toString(Charsets.UTF_8);
		needsTranslation = data.readBoolean();
		isKill = data.readBoolean();
		isHiredKill = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(mainFaction.ordinal());
		data.writeFloat(prevMainAlignment);
		if (!factionBonusMap.isEmpty()) {
			for (Map.Entry e : factionBonusMap.entrySet()) {
				LOTRFaction faction = (LOTRFaction) e.getKey();
				float bonus = (Float) e.getValue();
				data.writeByte(faction.ordinal());
				data.writeFloat(bonus);
			}
		}
		data.writeByte(-1);
		data.writeFloat(conquestBonus);
		data.writeDouble(posX);
		data.writeDouble(posY);
		data.writeDouble(posZ);
		byte[] nameData = name.getBytes(Charsets.UTF_8);
		data.writeShort(nameData.length);
		data.writeBytes(nameData);
		data.writeBoolean(needsTranslation);
		data.writeBoolean(isKill);
		data.writeBoolean(isHiredKill);
	}

	public static class Handler implements IMessageHandler<LOTRPacketAlignmentBonus, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketAlignmentBonus packet, MessageContext context) {
			String name = packet.name;
			if (packet.needsTranslation) {
				name = StatCollector.translateToLocal(name);
			}
			LOTRMod.proxy.spawnAlignmentBonus(packet.mainFaction, packet.prevMainAlignment, packet.factionBonusMap, name, packet.isKill, packet.isHiredKill, packet.conquestBonus, packet.posX, packet.posY, packet.posZ);
			return null;
		}
	}

}
