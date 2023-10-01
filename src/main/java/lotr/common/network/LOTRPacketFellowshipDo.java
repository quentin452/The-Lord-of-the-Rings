package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.fellowship.LOTRFellowshipClient;
import lotr.common.fellowship.LOTRFellowshipData;

import java.util.UUID;

public abstract class LOTRPacketFellowshipDo implements IMessage {
	public UUID fellowshipID;

	protected LOTRPacketFellowshipDo() {
	}

	protected LOTRPacketFellowshipDo(LOTRFellowshipClient fsClient) {
		fellowshipID = fsClient.getFellowshipID();
	}

	@Override
	public void fromBytes(ByteBuf data) {
		fellowshipID = new UUID(data.readLong(), data.readLong());
	}

	public LOTRFellowship getActiveFellowship() {
		return LOTRFellowshipData.getActiveFellowship(fellowshipID);
	}

	public LOTRFellowship getActiveOrDisbandedFellowship() {
		return LOTRFellowshipData.getFellowship(fellowshipID);
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeLong(fellowshipID.getMostSignificantBits());
		data.writeLong(fellowshipID.getLeastSignificantBits());
	}
}
