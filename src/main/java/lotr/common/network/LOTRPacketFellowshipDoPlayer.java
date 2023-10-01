package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.fellowship.LOTRFellowshipClient;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.UUID;

public class LOTRPacketFellowshipDoPlayer extends LOTRPacketFellowshipDo {
	public UUID subjectUuid;
	public PlayerFunction function;

	public LOTRPacketFellowshipDoPlayer() {
	}

	public LOTRPacketFellowshipDoPlayer(LOTRFellowshipClient fs, UUID subject, PlayerFunction f) {
		super(fs);
		subjectUuid = subject;
		function = f;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		super.fromBytes(data);
		subjectUuid = new UUID(data.readLong(), data.readLong());
		function = PlayerFunction.values()[data.readByte()];
	}

	@Override
	public void toBytes(ByteBuf data) {
		super.toBytes(data);
		data.writeLong(subjectUuid.getMostSignificantBits());
		data.writeLong(subjectUuid.getLeastSignificantBits());
		data.writeByte(function.ordinal());
	}

	public enum PlayerFunction {
		REMOVE, TRANSFER, OP, DEOP

	}

	public static class Handler implements IMessageHandler<LOTRPacketFellowshipDoPlayer, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketFellowshipDoPlayer packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			String playerName = entityplayer.getCommandSenderName();
			LOTRFellowship fellowship = packet.getActiveFellowship();
			UUID subjectPlayer = packet.subjectUuid;
			if (fellowship != null && subjectPlayer != null) {
				LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
				if (packet.function == PlayerFunction.REMOVE) {
					playerData.removePlayerFromFellowship(fellowship, subjectPlayer, playerName);
				} else if (packet.function == PlayerFunction.TRANSFER) {
					playerData.transferFellowship(fellowship, subjectPlayer, playerName);
				} else if (packet.function == PlayerFunction.OP) {
					playerData.setFellowshipAdmin(fellowship, subjectPlayer, true, playerName);
				} else if (packet.function == PlayerFunction.DEOP) {
					playerData.setFellowshipAdmin(fellowship, subjectPlayer, false, playerName);
				}
			}
			return null;
		}
	}

}
