package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.fellowship.LOTRFellowship;
import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;

public class LOTRPacketFellowshipRemove implements IMessage {
	public UUID fellowshipID;
	public boolean isInvite;

	public LOTRPacketFellowshipRemove() {
	}

	public LOTRPacketFellowshipRemove(LOTRFellowship fs, boolean invite) {
		fellowshipID = fs.getFellowshipID();
		isInvite = invite;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		fellowshipID = new UUID(data.readLong(), data.readLong());
		isInvite = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeLong(fellowshipID.getMostSignificantBits());
		data.writeLong(fellowshipID.getLeastSignificantBits());
		data.writeBoolean(isInvite);
	}

	public static class Handler implements IMessageHandler<LOTRPacketFellowshipRemove, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketFellowshipRemove packet, MessageContext context) {
			EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
			if (packet.isInvite) {
				LOTRLevelData.getData(entityplayer).removeClientFellowshipInvite(packet.fellowshipID);
			} else {
				LOTRLevelData.getData(entityplayer).removeClientFellowship(packet.fellowshipID);
			}
			return null;
		}
	}

}
