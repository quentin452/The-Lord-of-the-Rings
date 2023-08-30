package lotr.common.network;

import java.util.UUID;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.world.map.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;

public class LOTRPacketFastTravel implements IMessage {
	public boolean isCustom;
	public int wpID;
	public UUID sharingPlayer;

	public LOTRPacketFastTravel() {
	}

	public LOTRPacketFastTravel(LOTRAbstractWaypoint wp) {
		isCustom = wp instanceof LOTRCustomWaypoint;
		wpID = wp.getID();
		if (wp instanceof LOTRCustomWaypoint) {
			sharingPlayer = ((LOTRCustomWaypoint) wp).getSharingPlayerID();
		}
	}

	@Override
	public void fromBytes(ByteBuf data) {
		isCustom = data.readBoolean();
		wpID = data.readInt();
		boolean shared = data.readBoolean();
		if (shared) {
			sharingPlayer = new UUID(data.readLong(), data.readLong());
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeBoolean(isCustom);
		data.writeInt(wpID);
		boolean shared = sharingPlayer != null;
		data.writeBoolean(shared);
		if (shared) {
			data.writeLong(sharingPlayer.getMostSignificantBits());
			data.writeLong(sharingPlayer.getLeastSignificantBits());
		}
	}

	public static class Handler implements IMessageHandler<LOTRPacketFastTravel, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketFastTravel packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			if (LOTRConfig.enableFastTravel) {
				LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
				boolean isCustom = packet.isCustom;
				int waypointID = packet.wpID;
				LOTRAbstractWaypoint waypoint = null;
				if (isCustom) {
					UUID sharingPlayer = packet.sharingPlayer;
					waypoint = sharingPlayer != null ? playerData.getSharedCustomWaypointByID(sharingPlayer, waypointID) : playerData.getCustomWaypointByID(waypointID);
				} else {
					if (waypointID >= 0 && waypointID < LOTRWaypoint.values().length) {
						waypoint = LOTRWaypoint.values()[waypointID];
					}
				}
				if (waypoint != null && waypoint.hasPlayerUnlocked(entityplayer)) {
					if (playerData.getTimeSinceFT() < playerData.getWaypointFTTime(waypoint, entityplayer)) {
						entityplayer.closeScreen();
						entityplayer.addChatMessage(new ChatComponentTranslation("lotr.fastTravel.moreTime", waypoint.getDisplayName()));
					} else {
						boolean canTravel = playerData.canFastTravel();
						if (!canTravel) {
							entityplayer.closeScreen();
							entityplayer.addChatMessage(new ChatComponentTranslation("lotr.fastTravel.underAttack"));
						} else if (entityplayer.isPlayerSleeping()) {
							entityplayer.closeScreen();
							entityplayer.addChatMessage(new ChatComponentTranslation("lotr.fastTravel.inBed"));
						} else {
							playerData.setTargetFTWaypoint(waypoint);
						}
					}
				}
			} else {
				entityplayer.addChatMessage(new ChatComponentTranslation("chat.lotr.ftDisabled"));
			}
			return null;
		}
	}

}
