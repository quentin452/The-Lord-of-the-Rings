package lotr.common.network;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.LOTRTitle;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.fellowship.LOTRFellowshipClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.UsernameCache;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.*;

public class LOTRPacketFellowship implements IMessage {
	public UUID fellowshipID;
	public boolean isInvite;
	public String fellowshipName;
	public ItemStack fellowshipIcon;
	public boolean isOwned;
	public boolean isAdminned;
	public GameProfile owner;
	public List<GameProfile> members = new ArrayList<>();
	public Map<UUID, LOTRTitle.PlayerTitle> titleMap = new HashMap<>();
	public Set<UUID> adminUuids = new HashSet<>();
	public boolean preventPVP;
	public boolean preventHiredFF;
	public boolean showMapLocations;

	public LOTRPacketFellowship() {
	}

	public LOTRPacketFellowship(LOTRPlayerData playerData, LOTRFellowship fs, boolean invite) {
		fellowshipID = fs.getFellowshipID();
		isInvite = invite;
		fellowshipName = fs.getName();
		fellowshipIcon = fs.getIcon();
		UUID thisPlayer = playerData.getPlayerUUID();
		isOwned = fs.isOwner(thisPlayer);
		isAdminned = fs.isAdmin(thisPlayer);
		List<UUID> playerIDs = fs.getAllPlayerUUIDs();
		for (UUID player : playerIDs) {
			GameProfile profile = getPlayerProfileWithUsername(player);
			if (fs.isOwner(player)) {
				owner = profile;
			} else {
				members.add(profile);
			}
			LOTRTitle.PlayerTitle title = LOTRLevelData.getPlayerTitleWithOfflineCache(player);
			if (title != null) {
				titleMap.put(player, title);
			}
			if (!fs.isAdmin(player)) {
				continue;
			}
			adminUuids.add(player);
		}
		preventPVP = fs.getPreventPVP();
		preventHiredFF = fs.getPreventHiredFriendlyFire();
		showMapLocations = fs.getShowMapLocations();
	}

	public static GameProfile getPlayerProfileWithUsername(UUID player) {
		GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152652_a(player);
		if (profile == null || StringUtils.isBlank(profile.getName())) {
			String name = UsernameCache.getLastKnownUsername(player);
			if (name != null) {
				profile = new GameProfile(player, name);
			} else {
				profile = new GameProfile(player, "");
				MinecraftServer.getServer().func_147130_as().fillProfileProperties(profile, true);
			}
		}
		return profile;
	}

	public static GameProfile readPlayerUuidAndUsername(ByteBuf data) {
		UUID uuid = new UUID(data.readLong(), data.readLong());
		byte nameLength = data.readByte();
		if (nameLength >= 0) {
			ByteBuf nameBytes = data.readBytes(nameLength);
			String username = nameBytes.toString(Charsets.UTF_8);
			return new GameProfile(uuid, username);
		}
		return null;
	}

	public static void writePlayerUuidAndUsername(ByteBuf data, GameProfile profile) {
		UUID uuid = profile.getId();
		String username = profile.getName();
		data.writeLong(uuid.getMostSignificantBits());
		data.writeLong(uuid.getLeastSignificantBits());
		byte[] usernameBytes = username.getBytes(Charsets.UTF_8);
		data.writeByte(usernameBytes.length);
		data.writeBytes(usernameBytes);
	}

	@Override
	public void fromBytes(ByteBuf data) {
		fellowshipID = new UUID(data.readLong(), data.readLong());
		isInvite = data.readBoolean();
		byte fsNameLength = data.readByte();
		ByteBuf fsNameBytes = data.readBytes(fsNameLength);
		fellowshipName = fsNameBytes.toString(Charsets.UTF_8);
		NBTTagCompound iconData = new NBTTagCompound();
		try {
			iconData = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
		} catch (IOException e) {
			FMLLog.severe("LOTR: Error reading fellowship icon data");
			e.printStackTrace();
		}
		fellowshipIcon = ItemStack.loadItemStackFromNBT(iconData);
		isOwned = data.readBoolean();
		isAdminned = data.readBoolean();
		owner = readPlayerUuidAndUsername(data);
		readTitleForPlayer(data, owner.getId());
		int numMembers = data.readInt();
		for (int i = 0; i < numMembers; ++i) {
			GameProfile member = readPlayerUuidAndUsername(data);
			if (member == null) {
				continue;
			}
			members.add(member);
			UUID memberUuid = member.getId();
			readTitleForPlayer(data, memberUuid);
			boolean admin = data.readBoolean();
			if (!admin) {
				continue;
			}
			adminUuids.add(memberUuid);
		}
		preventPVP = data.readBoolean();
		preventHiredFF = data.readBoolean();
		showMapLocations = data.readBoolean();
	}

	public void readTitleForPlayer(ByteBuf data, UUID playerUuid) {
		LOTRTitle.PlayerTitle playerTitle = LOTRTitle.PlayerTitle.readNullableTitle(data);
		if (playerTitle != null) {
			titleMap.put(playerUuid, playerTitle);
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeLong(fellowshipID.getMostSignificantBits());
		data.writeLong(fellowshipID.getLeastSignificantBits());
		data.writeBoolean(isInvite);
		byte[] fsNameBytes = fellowshipName.getBytes(Charsets.UTF_8);
		data.writeByte(fsNameBytes.length);
		data.writeBytes(fsNameBytes);
		NBTTagCompound iconData = new NBTTagCompound();
		if (fellowshipIcon != null) {
			fellowshipIcon.writeToNBT(iconData);
		}
		try {
			new PacketBuffer(data).writeNBTTagCompoundToBuffer(iconData);
		} catch (IOException e) {
			FMLLog.severe("LOTR: Error writing fellowship icon data");
			e.printStackTrace();
		}
		data.writeBoolean(isOwned);
		data.writeBoolean(isAdminned);
		writePlayerUuidAndUsername(data, owner);
		LOTRTitle.PlayerTitle.writeNullableTitle(data, titleMap.get(owner.getId()));
		data.writeInt(members.size());
		for (GameProfile member : members) {
			UUID memberUuid = member.getId();
			LOTRTitle.PlayerTitle title = titleMap.get(memberUuid);
			boolean admin = adminUuids.contains(memberUuid);
			writePlayerUuidAndUsername(data, member);
			LOTRTitle.PlayerTitle.writeNullableTitle(data, title);
			data.writeBoolean(admin);
		}
		data.writeBoolean(preventPVP);
		data.writeBoolean(preventHiredFF);
		data.writeBoolean(showMapLocations);
	}

	public static class Handler implements IMessageHandler<LOTRPacketFellowship, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketFellowship packet, MessageContext context) {
			LOTRFellowshipClient fellowship = new LOTRFellowshipClient(packet.fellowshipID, packet.fellowshipName, packet.isOwned, packet.isAdminned, packet.owner, packet.members);
			fellowship.setTitles(packet.titleMap);
			fellowship.setAdmins(packet.adminUuids);
			fellowship.setIcon(packet.fellowshipIcon);
			fellowship.setPreventPVP(packet.preventPVP);
			fellowship.setPreventHiredFriendlyFire(packet.preventHiredFF);
			fellowship.setShowMapLocations(packet.showMapLocations);
			EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
			if (packet.isInvite) {
				LOTRLevelData.getData(entityplayer).addOrUpdateClientFellowshipInvite(fellowship);
			} else {
				LOTRLevelData.getData(entityplayer).addOrUpdateClientFellowship(fellowship);
			}
			return null;
		}
	}

}
