package lotr.common.network;

import java.util.*;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.map.LOTRConquestZone;
import net.minecraft.world.World;

public class LOTRPacketConquestGrid implements IMessage {
	public LOTRFaction conqFac;
	public List<LOTRConquestZone> allZones;
	public long worldTime;

	public LOTRPacketConquestGrid() {
	}

	public LOTRPacketConquestGrid(LOTRFaction fac, Collection<LOTRConquestZone> grid, World world) {
		conqFac = fac;
		allZones = new ArrayList<>(grid);
		worldTime = world.getTotalWorldTime();
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte facID = data.readByte();
		conqFac = LOTRFaction.forID(facID);
		allZones = new ArrayList<>();
		short gridX;
		short gridZ;
		float str;
		while ((gridX = data.readShort()) != -15000) {
			gridZ = data.readShort();
			long time = data.readLong();
			str = data.readFloat();
			LOTRConquestZone zone = new LOTRConquestZone(gridX, gridZ);
			zone.setClientSide();
			zone.setLastChangeTime(time);
			zone.setConquestStrengthRaw(conqFac, str);
			allZones.add(zone);
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		int facID = conqFac.ordinal();
		data.writeByte(facID);
		for (LOTRConquestZone zone : allZones) {
			float str = zone.getConquestStrength(conqFac, worldTime);
			if (str <= 0.0f) {
				continue;
			}
			float strRaw = zone.getConquestStrengthRaw(conqFac);
			data.writeShort(zone.gridX);
			data.writeShort(zone.gridZ);
			data.writeLong(zone.getLastChangeTime());
			data.writeFloat(strRaw);
		}
		data.writeShort(-15000);
	}

	public static class Handler implements IMessageHandler<LOTRPacketConquestGrid, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketConquestGrid packet, MessageContext context) {
			LOTRMod.proxy.receiveConquestGrid(packet.conqFac, packet.allZones);
			return null;
		}
	}

}
