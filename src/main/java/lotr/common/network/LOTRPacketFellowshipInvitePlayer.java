package lotr.common.network;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRConfig;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.fellowship.LOTRFellowshipClient;
import lotr.common.util.LOTRLog;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import java.util.UUID;

public class LOTRPacketFellowshipInvitePlayer extends LOTRPacketFellowshipDo {
	public String invitedUsername;

	public LOTRPacketFellowshipInvitePlayer() {
	}

	public LOTRPacketFellowshipInvitePlayer(LOTRFellowshipClient fs, String username) {
		super(fs);
		invitedUsername = username;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		super.fromBytes(data);
		byte nameLength = data.readByte();
		ByteBuf nameBytes = data.readBytes(nameLength);
		invitedUsername = nameBytes.toString(Charsets.UTF_8);
	}

	@Override
	public void toBytes(ByteBuf data) {
		super.toBytes(data);
		byte[] nameBytes = invitedUsername.getBytes(Charsets.UTF_8);
		data.writeByte(nameBytes.length);
		data.writeBytes(nameBytes);
	}

	public static class Handler implements IMessageHandler<LOTRPacketFellowshipInvitePlayer, IMessage> {
		public UUID findInvitedPlayerUUID(String invitedUsername) {
			GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152655_a(invitedUsername);
			if (profile != null && profile.getId() != null) {
				return profile.getId();
			}
			return null;
		}

		@Override
		public IMessage onMessage(LOTRPacketFellowshipInvitePlayer packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			LOTRFellowship fellowship = packet.getActiveFellowship();
			if (fellowship != null) {
				int limit = LOTRConfig.getFellowshipMaxSize(entityplayer.worldObj);
				if (limit >= 0 && fellowship.getPlayerCount() >= limit) {
					LOTRLog.logger.warn(String.format("Player %s tried to invite a player with username %s to fellowship %s, but fellowship size %d is already >= the maximum of %d", entityplayer.getCommandSenderName(), packet.invitedUsername, fellowship.getName(), fellowship.getPlayerCount(), limit));
				} else {
					UUID invitedPlayer = findInvitedPlayerUUID(packet.invitedUsername);
					if (invitedPlayer != null) {
						LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
						playerData.invitePlayerToFellowship(fellowship, invitedPlayer, entityplayer.getCommandSenderName());
					} else {
						LOTRLog.logger.warn(String.format("Player %s tried to invite a player with username %s to fellowship %s, but couldn't find the invited player's UUID", entityplayer.getCommandSenderName(), packet.invitedUsername, fellowship.getName()));
					}
				}
			}
			return null;
		}
	}

}
