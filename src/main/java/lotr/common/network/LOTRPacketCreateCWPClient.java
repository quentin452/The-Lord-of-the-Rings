package lotr.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.world.map.LOTRCustomWaypoint;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LOTRPacketCreateCWPClient implements IMessage {
	public double mapX;
	public double mapY;
	public int xCoord;
	public int yCoord;
	public int zCoord;
	public int cwpID;
	public String name;
	public List<UUID> sharedFellowshipIDs;
	public UUID sharingPlayer;
	public String sharingPlayerName;
	public boolean sharedUnlocked;
	public boolean sharedHidden;

	public LOTRPacketCreateCWPClient() {
	}

	public LOTRPacketCreateCWPClient(double xm, double ym, int xc, int yc, int zc, int id, String s, List<UUID> fsIDs) {
		mapX = xm;
		mapY = ym;
		xCoord = xc;
		yCoord = yc;
		zCoord = zc;
		cwpID = id;
		name = s;
		sharedFellowshipIDs = fsIDs;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		boolean shared;
		mapX = data.readDouble();
		mapY = data.readDouble();
		xCoord = data.readInt();
		yCoord = data.readInt();
		zCoord = data.readInt();
		cwpID = data.readInt();
		short nameLength = data.readShort();
		name = data.readBytes(nameLength).toString(Charsets.UTF_8);
		sharedFellowshipIDs = new ArrayList<>();
		boolean sharedFellowships = data.readBoolean();
		if (sharedFellowships) {
			int sharedLength = data.readShort();
			for (int l = 0; l < sharedLength; ++l) {
				UUID fsID = new UUID(data.readLong(), data.readLong());
				sharedFellowshipIDs.add(fsID);
			}
		}
		shared = data.readBoolean();
		if (shared) {
			sharingPlayer = new UUID(data.readLong(), data.readLong());
			byte usernameLength = data.readByte();
			sharingPlayerName = data.readBytes(usernameLength).toString(Charsets.UTF_8);
			sharedUnlocked = data.readBoolean();
			sharedHidden = data.readBoolean();
		}
	}

	public LOTRPacketCreateCWPClient setSharingPlayer(UUID player, String name, boolean unlocked, boolean hidden) {
		sharingPlayer = player;
		sharingPlayerName = name;
		sharedUnlocked = unlocked;
		sharedHidden = hidden;
		return this;
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeDouble(mapX);
		data.writeDouble(mapY);
		data.writeInt(xCoord);
		data.writeInt(yCoord);
		data.writeInt(zCoord);
		data.writeInt(cwpID);
		byte[] nameBytes = name.getBytes(Charsets.UTF_8);
		data.writeShort(nameBytes.length);
		data.writeBytes(nameBytes);
		boolean sharedFellowships = sharedFellowshipIDs != null;
		data.writeBoolean(sharedFellowships);
		if (sharedFellowships) {
			data.writeShort(sharedFellowshipIDs.size());
			for (UUID fsID : sharedFellowshipIDs) {
				data.writeLong(fsID.getMostSignificantBits());
				data.writeLong(fsID.getLeastSignificantBits());
			}
		}
		boolean shared = sharingPlayer != null;
		data.writeBoolean(shared);
		if (shared) {
			data.writeLong(sharingPlayer.getMostSignificantBits());
			data.writeLong(sharingPlayer.getLeastSignificantBits());
			byte[] usernameBytes = sharingPlayerName.getBytes(Charsets.UTF_8);
			data.writeByte(usernameBytes.length);
			data.writeBytes(usernameBytes);
			data.writeBoolean(sharedUnlocked);
			data.writeBoolean(sharedHidden);
		}
	}

	public static class Handler implements IMessageHandler<LOTRPacketCreateCWPClient, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketCreateCWPClient packet, MessageContext context) {
			EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
			LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
			LOTRCustomWaypoint cwp = new LOTRCustomWaypoint(packet.name, packet.mapX, packet.mapY, packet.xCoord, packet.yCoord, packet.zCoord, packet.cwpID);
			if (packet.sharedFellowshipIDs != null) {
				cwp.setSharedFellowshipIDs(packet.sharedFellowshipIDs);
			}
			if (packet.sharingPlayer != null) {
				if (!LOTRMod.proxy.isSingleplayer()) {
					cwp.setSharingPlayerID(packet.sharingPlayer);
					cwp.setSharingPlayerName(packet.sharingPlayerName);
					if (packet.sharedUnlocked) {
						cwp.setSharedUnlocked();
					}
					cwp.setSharedHidden(packet.sharedHidden);
					pd.addOrUpdateSharedCustomWaypoint(cwp);
				}
			} else if (!LOTRMod.proxy.isSingleplayer()) {
				pd.addCustomWaypointClient(cwp);
			}
			return null;
		}
	}

}
