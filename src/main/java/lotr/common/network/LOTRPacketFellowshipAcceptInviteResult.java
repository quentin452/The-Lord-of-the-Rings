package lotr.common.network;

import java.util.UUID;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.fellowship.LOTRFellowship;

public class LOTRPacketFellowshipAcceptInviteResult implements IMessage {
	public UUID fellowshipID;
	public String fellowshipName;
	public AcceptInviteResult result;

	public LOTRPacketFellowshipAcceptInviteResult() {
	}

	public LOTRPacketFellowshipAcceptInviteResult(AcceptInviteResult result) {
		fellowshipID = null;
		fellowshipName = null;
		this.result = result;
	}

	public LOTRPacketFellowshipAcceptInviteResult(LOTRFellowship fs, AcceptInviteResult result) {
		fellowshipID = fs.getFellowshipID();
		fellowshipName = fs.getName();
		this.result = result;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		boolean hasFellowship = data.readBoolean();
		if (hasFellowship) {
			fellowshipID = new UUID(data.readLong(), data.readLong());
			byte fsNameLength = data.readByte();
			ByteBuf fsNameBytes = data.readBytes(fsNameLength);
			fellowshipName = fsNameBytes.toString(Charsets.UTF_8);
		}
		result = AcceptInviteResult.values()[data.readByte()];
	}

	@Override
	public void toBytes(ByteBuf data) {
		boolean hasFellowship = fellowshipID != null && fellowshipName != null;
		data.writeBoolean(hasFellowship);
		if (hasFellowship) {
			data.writeLong(fellowshipID.getMostSignificantBits());
			data.writeLong(fellowshipID.getLeastSignificantBits());
			byte[] fsNameBytes = fellowshipName.getBytes(Charsets.UTF_8);
			data.writeByte(fsNameBytes.length);
			data.writeBytes(fsNameBytes);
		}
		data.writeByte(result.ordinal());
	}

	public enum AcceptInviteResult {
		JOINED, DISBANDED, TOO_LARGE, NONEXISTENT

	}

	public static class Handler implements IMessageHandler<LOTRPacketFellowshipAcceptInviteResult, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketFellowshipAcceptInviteResult packet, MessageContext context) {
			LOTRMod.proxy.displayFellowshipAcceptInvitationResult(packet.fellowshipID, packet.fellowshipName, packet.result);
			return null;
		}
	}

}
