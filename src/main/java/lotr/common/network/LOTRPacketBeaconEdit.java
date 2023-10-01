package lotr.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.fellowship.LOTRFellowshipData;
import lotr.common.tileentity.LOTRTileEntityBeacon;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;

import java.util.UUID;

public class LOTRPacketBeaconEdit implements IMessage {
	public int beaconX;
	public int beaconY;
	public int beaconZ;
	public UUID fellowshipID;
	public String beaconName;
	public boolean releasePlayer;

	public LOTRPacketBeaconEdit() {
	}

	public LOTRPacketBeaconEdit(int i, int j, int k, UUID fsID, String name, boolean release) {
		beaconX = i;
		beaconY = j;
		beaconZ = k;
		fellowshipID = fsID;
		beaconName = name;
		releasePlayer = release;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		beaconX = data.readInt();
		beaconY = data.readInt();
		beaconZ = data.readInt();
		if (data.readBoolean()) {
			fellowshipID = new UUID(data.readLong(), data.readLong());
		}
		if (data.readBoolean()) {
			short length = data.readShort();
			ByteBuf nameBytes = data.readBytes(length);
			beaconName = nameBytes.toString(Charsets.UTF_8);
		}
		releasePlayer = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(beaconX);
		data.writeInt(beaconY);
		data.writeInt(beaconZ);
		boolean hasFs = fellowshipID != null;
		data.writeBoolean(hasFs);
		if (hasFs) {
			data.writeLong(fellowshipID.getMostSignificantBits());
			data.writeLong(fellowshipID.getLeastSignificantBits());
		}
		boolean hasName = beaconName != null;
		data.writeBoolean(hasName);
		if (hasName) {
			byte[] nameBytes = beaconName.getBytes(Charsets.UTF_8);
			data.writeShort(nameBytes.length);
			data.writeBytes(nameBytes);
		}
		data.writeBoolean(releasePlayer);
	}

	public static class Handler implements IMessageHandler<LOTRPacketBeaconEdit, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketBeaconEdit packet, MessageContext context) {
			LOTRTileEntityBeacon beacon;
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			TileEntity te = entityplayer.worldObj.getTileEntity(packet.beaconX, packet.beaconY, packet.beaconZ);
			if (te instanceof LOTRTileEntityBeacon && (beacon = (LOTRTileEntityBeacon) te).isPlayerEditing(entityplayer)) {
				LOTRFellowship fellowship = null;
				if (packet.fellowshipID != null) {
					fellowship = LOTRFellowshipData.getActiveFellowship(packet.fellowshipID);
				}
				if (fellowship != null && fellowship.containsPlayer(entityplayer.getUniqueID())) {
					beacon.setFellowship(fellowship);
				} else {
					beacon.setFellowship(null);
				}
				if (packet.beaconName != null) {
					beacon.setBeaconName(packet.beaconName);
				} else {
					beacon.setBeaconName(null);
				}
				if (packet.releasePlayer) {
					beacon.releaseEditingPlayer(entityplayer);
				}
			}
			return null;
		}
	}

}
