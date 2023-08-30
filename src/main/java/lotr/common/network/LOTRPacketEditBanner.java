package lotr.common.network;

import java.util.List;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRBannerProtection;
import lotr.common.entity.item.*;
import lotr.common.fellowship.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

public class LOTRPacketEditBanner implements IMessage {
	public int bannerID;
	public boolean playerSpecificProtection;
	public boolean selfProtection;
	public float alignmentProtection;
	public int whitelistLength;
	public String[] whitelistSlots;
	public int[] whitelistPerms;
	public int defaultPerms;

	public LOTRPacketEditBanner() {
	}

	public LOTRPacketEditBanner(LOTREntityBanner banner) {
		bannerID = banner.getEntityId();
	}

	@Override
	public void fromBytes(ByteBuf data) {
		bannerID = data.readInt();
		playerSpecificProtection = data.readBoolean();
		selfProtection = data.readBoolean();
		alignmentProtection = data.readFloat();
		whitelistLength = data.readShort();
		boolean sendWhitelist = data.readBoolean();
		if (sendWhitelist) {
			whitelistSlots = new String[data.readShort()];
			whitelistPerms = new int[whitelistSlots.length];
			short index;
			while ((index = data.readShort()) >= 0) {
				byte length = data.readByte();
				if (length == -1) {
					whitelistSlots[index] = null;
					continue;
				}
				ByteBuf usernameBytes = data.readBytes(length);
				whitelistSlots[index] = usernameBytes.toString(Charsets.UTF_8);
				whitelistPerms[index] = data.readShort();
			}
		}
		defaultPerms = data.readShort();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(bannerID);
		data.writeBoolean(playerSpecificProtection);
		data.writeBoolean(selfProtection);
		data.writeFloat(alignmentProtection);
		data.writeShort(whitelistLength);
		boolean sendWhitelist = whitelistSlots != null;
		data.writeBoolean(sendWhitelist);
		if (sendWhitelist) {
			data.writeShort(whitelistSlots.length);
			for (int index = 0; index < whitelistSlots.length; ++index) {
				data.writeShort(index);
				String username = whitelistSlots[index];
				if (StringUtils.isNullOrEmpty(username)) {
					data.writeByte(-1);
					continue;
				}
				byte[] usernameBytes = username.getBytes(Charsets.UTF_8);
				data.writeByte(usernameBytes.length);
				data.writeBytes(usernameBytes);
				data.writeShort(whitelistPerms[index]);
			}
			data.writeShort(-1);
		}
		data.writeShort(defaultPerms);
	}

	public static class Handler implements IMessageHandler<LOTRPacketEditBanner, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketEditBanner packet, MessageContext context) {
			LOTREntityBanner banner;
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			World world = entityplayer.worldObj;
			Entity bEntity = world.getEntityByID(packet.bannerID);
			if (bEntity instanceof LOTREntityBanner && (banner = (LOTREntityBanner) bEntity).canPlayerEditBanner(entityplayer)) {
				banner.setPlayerSpecificProtection(packet.playerSpecificProtection);
				banner.setSelfProtection(packet.selfProtection);
				banner.setAlignmentProtection(packet.alignmentProtection);
				banner.resizeWhitelist(packet.whitelistLength);
				if (packet.whitelistSlots != null) {
					for (int index = 0; index < packet.whitelistSlots.length; ++index) {
						if (index == 0) {
							continue;
						}
						String username = packet.whitelistSlots[index];
						int perms = packet.whitelistPerms[index];
						if (StringUtils.isNullOrEmpty(username)) {
							banner.whitelistPlayer(index, null);
							continue;
						}
						List<LOTRBannerProtection.Permission> decodedPerms = LOTRBannerWhitelistEntry.static_decodePermBitFlags(perms);
						if (LOTRFellowshipProfile.hasFellowshipCode(username)) {
							String fsName = LOTRFellowshipProfile.stripFellowshipCode(username);
							LOTRFellowship fellowship = banner.getPlacersFellowshipByName(fsName);
							if (fellowship == null) {
								continue;
							}
							banner.whitelistFellowship(index, fellowship, decodedPerms);
							continue;
						}
						GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152655_a(username);
						if (profile == null) {
							continue;
						}
						banner.whitelistPlayer(index, profile, decodedPerms);
					}
				}
				List<LOTRBannerProtection.Permission> defaultPerms = LOTRBannerWhitelistEntry.static_decodePermBitFlags(packet.defaultPerms);
				banner.setDefaultPermissions(defaultPerms);
			}
			return null;
		}
	}

}
