package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.fellowship.LOTRFellowshipClient;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketFellowshipDisband extends LOTRPacketFellowshipDo {
	public LOTRPacketFellowshipDisband() {
	}

	public LOTRPacketFellowshipDisband(LOTRFellowshipClient fs) {
		super(fs);
	}

	public static class Handler implements IMessageHandler<LOTRPacketFellowshipDisband, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketFellowshipDisband packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			LOTRFellowship fellowship = packet.getActiveFellowship();
			if (fellowship != null) {
				LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
				playerData.disbandFellowship(fellowship, entityplayer.getCommandSenderName());
			}
			return null;
		}
	}

}
