package lotr.common.network;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRBannerProtection;
import lotr.common.LOTRMod;
import lotr.common.entity.item.LOTRBannerWhitelistEntry;
import lotr.common.entity.item.LOTREntityBanner;
import lotr.common.fellowship.LOTRFellowshipProfile;
import net.minecraft.entity.Entity;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

import java.util.List;

public class LOTRPacketBannerData implements IMessage {
	public int entityID;
	public boolean openGui;
	public boolean playerSpecificProtection;
	public boolean selfProtection;
	public boolean structureProtection;
	public int customRange;
	public float alignmentProtection;
	public int whitelistLength;
	public String[] whitelistSlots;
	public int[] whitelistPerms;
	public int defaultPerms;
	public boolean thisPlayerHasPermission;

	public LOTRPacketBannerData() {
	}

	public LOTRPacketBannerData(int id, boolean flag) {
		entityID = id;
		openGui = flag;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		openGui = data.readBoolean();
		playerSpecificProtection = data.readBoolean();
		selfProtection = data.readBoolean();
		structureProtection = data.readBoolean();
		customRange = data.readShort();
		alignmentProtection = data.readFloat();
		whitelistLength = data.readShort();
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
		defaultPerms = data.readShort();
		thisPlayerHasPermission = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(entityID);
		data.writeBoolean(openGui);
		data.writeBoolean(playerSpecificProtection);
		data.writeBoolean(selfProtection);
		data.writeBoolean(structureProtection);
		data.writeShort(customRange);
		data.writeFloat(alignmentProtection);
		data.writeShort(whitelistLength);
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
		data.writeShort(defaultPerms);
		data.writeBoolean(thisPlayerHasPermission);
	}

	public static class Handler implements IMessageHandler<LOTRPacketBannerData, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketBannerData packet, MessageContext context) {
			World world = LOTRMod.proxy.getClientWorld();
			Entity entity = world.getEntityByID(packet.entityID);
			if (entity instanceof LOTREntityBanner) {
				LOTREntityBanner banner = (LOTREntityBanner) entity;
				banner.setPlayerSpecificProtection(packet.playerSpecificProtection);
				banner.setSelfProtection(packet.selfProtection);
				banner.setStructureProtection(packet.structureProtection);
				banner.setCustomRange(packet.customRange);
				banner.setAlignmentProtection(packet.alignmentProtection);
				banner.resizeWhitelist(packet.whitelistLength);
				for (int index = 0; index < packet.whitelistSlots.length; ++index) {
					String username = packet.whitelistSlots[index];
					if (StringUtils.isNullOrEmpty(username)) {
						banner.whitelistPlayer(index, null);
					} else if (LOTRFellowshipProfile.hasFellowshipCode(username)) {
						String fsName = LOTRFellowshipProfile.stripFellowshipCode(username);
						LOTRFellowshipProfile profile = new LOTRFellowshipProfile(banner, null, fsName);
						banner.whitelistPlayer(index, profile);
					} else {
						GameProfile profile = new GameProfile(null, username);
						banner.whitelistPlayer(index, profile);
					}
					LOTRBannerWhitelistEntry entry = banner.getWhitelistEntry(index);
					if (entry == null) {
						continue;
					}
					entry.decodePermBitFlags(packet.whitelistPerms[index]);
				}
				List<LOTRBannerProtection.Permission> defaultPerms = LOTRBannerWhitelistEntry.static_decodePermBitFlags(packet.defaultPerms);
				banner.setDefaultPermissions(defaultPerms);
				banner.setClientside_playerHasPermissionInSurvival(packet.thisPlayerHasPermission);
				if (packet.openGui) {
					LOTRMod.proxy.displayBannerGui(banner);
				}
			}
			return null;
		}
	}

}
