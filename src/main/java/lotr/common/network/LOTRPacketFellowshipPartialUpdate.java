package lotr.common.network;

import java.io.IOException;
import java.util.UUID;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.fellowship.*;
import lotr.common.util.LOTRLog;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public abstract class LOTRPacketFellowshipPartialUpdate implements IMessage {
	public UUID fellowshipID;

	protected LOTRPacketFellowshipPartialUpdate() {
	}

	protected LOTRPacketFellowshipPartialUpdate(LOTRFellowship fs) {
		fellowshipID = fs.getFellowshipID();
	}

	@Override
	public void fromBytes(ByteBuf data) {
		fellowshipID = new UUID(data.readLong(), data.readLong());
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeLong(fellowshipID.getMostSignificantBits());
		data.writeLong(fellowshipID.getLeastSignificantBits());
	}

	public abstract void updateClient(LOTRFellowshipClient paramLOTRFellowshipClient);

	public static class AddMember extends OnePlayerUpdate {
		public LOTRTitle.PlayerTitle playerTitle;

		public AddMember() {
		}

		public AddMember(LOTRFellowship fs, UUID member) {
			super(fs, member);
			playerTitle = LOTRLevelData.getData(member).getPlayerTitle();
		}

		@Override
		public void fromBytes(ByteBuf data) {
			super.fromBytes(data);
			playerTitle = LOTRTitle.PlayerTitle.readNullableTitle(data);
		}

		@Override
		public void toBytes(ByteBuf data) {
			super.toBytes(data);
			LOTRTitle.PlayerTitle.writeNullableTitle(data, playerTitle);
		}

		@Override
		public void updateClient(LOTRFellowshipClient fellowship) {
			fellowship.addMember(playerProfile, playerTitle);
		}

		public static class Handler extends LOTRPacketFellowshipPartialUpdate.Handler<AddMember> {
		}
	}

	public static class ChangeIcon extends LOTRPacketFellowshipPartialUpdate {
		public ItemStack fellowshipIcon;

		public ChangeIcon() {
		}

		public ChangeIcon(LOTRFellowship fs) {
			super(fs);
			fellowshipIcon = fs.getIcon();
		}

		@Override
		public void fromBytes(ByteBuf data) {
			super.fromBytes(data);
			NBTTagCompound iconData = new NBTTagCompound();
			try {
				iconData = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
			} catch (IOException e) {
				FMLLog.severe("LOTR: Error reading fellowship icon data");
				e.printStackTrace();
			}
			fellowshipIcon = ItemStack.loadItemStackFromNBT(iconData);
		}

		@Override
		public void toBytes(ByteBuf data) {
			super.toBytes(data);
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
		}

		@Override
		public void updateClient(LOTRFellowshipClient fellowship) {
			fellowship.setIcon(fellowshipIcon);
		}

		public static class Handler extends LOTRPacketFellowshipPartialUpdate.Handler<ChangeIcon> {
		}
	}

	public abstract static class Handler<P extends LOTRPacketFellowshipPartialUpdate> implements IMessageHandler<P, IMessage> {
		@Override
		public IMessage onMessage(P packet, MessageContext context) {
			EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
			LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
			LOTRFellowshipClient fellowship = pd.getClientFellowshipByID(packet.fellowshipID);
			if (fellowship != null) {
				packet.updateClient(fellowship);
			} else {
				LOTRLog.logger.warn("Client couldn't find fellowship for ID " + packet.fellowshipID);
			}
			return null;
		}
	}

	public abstract static class OnePlayerUpdate extends LOTRPacketFellowshipPartialUpdate {
		public GameProfile playerProfile;

		protected OnePlayerUpdate() {
		}

		protected OnePlayerUpdate(LOTRFellowship fs, UUID player) {
			super(fs);
			playerProfile = LOTRPacketFellowship.getPlayerProfileWithUsername(player);
		}

		@Override
		public void fromBytes(ByteBuf data) {
			super.fromBytes(data);
			playerProfile = LOTRPacketFellowship.readPlayerUuidAndUsername(data);
		}

		@Override
		public void toBytes(ByteBuf data) {
			super.toBytes(data);
			LOTRPacketFellowship.writePlayerUuidAndUsername(data, playerProfile);
		}
	}

	public static class RemoveAdmin extends OnePlayerUpdate {
		public boolean isAdminned;

		public RemoveAdmin() {
		}

		public RemoveAdmin(LOTRFellowship fs, UUID admin, boolean adminned) {
			super(fs, admin);
			isAdminned = adminned;
		}

		@Override
		public void fromBytes(ByteBuf data) {
			super.fromBytes(data);
			isAdminned = data.readBoolean();
		}

		@Override
		public void toBytes(ByteBuf data) {
			super.toBytes(data);
			data.writeBoolean(isAdminned);
		}

		@Override
		public void updateClient(LOTRFellowshipClient fellowship) {
			fellowship.removeAdmin(playerProfile.getId(), isAdminned);
		}

		public static class Handler extends LOTRPacketFellowshipPartialUpdate.Handler<RemoveAdmin> {
		}
	}

	public static class RemoveMember extends OnePlayerUpdate {
		public RemoveMember() {
		}

		public RemoveMember(LOTRFellowship fs, UUID member) {
			super(fs, member);
		}

		@Override
		public void updateClient(LOTRFellowshipClient fellowship) {
			fellowship.removeMember(playerProfile);
		}

		public static class Handler extends LOTRPacketFellowshipPartialUpdate.Handler<RemoveMember> {
		}
	}

	public static class Rename extends LOTRPacketFellowshipPartialUpdate {
		public String fellowshipName;

		public Rename() {
		}

		public Rename(LOTRFellowship fs) {
			super(fs);
			fellowshipName = fs.getName();
		}

		@Override
		public void fromBytes(ByteBuf data) {
			super.fromBytes(data);
			int fsNameLength = data.readByte();
			ByteBuf fsNameBytes = data.readBytes(fsNameLength);
			fellowshipName = fsNameBytes.toString(Charsets.UTF_8);
		}

		@Override
		public void toBytes(ByteBuf data) {
			super.toBytes(data);
			byte[] fsNameBytes = fellowshipName.getBytes(Charsets.UTF_8);
			data.writeByte(fsNameBytes.length);
			data.writeBytes(fsNameBytes);
		}

		@Override
		public void updateClient(LOTRFellowshipClient fellowship) {
			fellowship.setName(fellowshipName);
		}

		public static class Handler extends LOTRPacketFellowshipPartialUpdate.Handler<Rename> {
		}
	}

	public static class SetAdmin extends OnePlayerUpdate {
		public boolean isAdminned;

		public SetAdmin() {
		}

		public SetAdmin(LOTRFellowship fs, UUID admin, boolean adminned) {
			super(fs, admin);
			isAdminned = adminned;
		}

		@Override
		public void fromBytes(ByteBuf data) {
			super.fromBytes(data);
			isAdminned = data.readBoolean();
		}

		@Override
		public void toBytes(ByteBuf data) {
			super.toBytes(data);
			data.writeBoolean(isAdminned);
		}

		@Override
		public void updateClient(LOTRFellowshipClient fellowship) {
			fellowship.setAdmin(playerProfile.getId(), isAdminned);
		}

		public static class Handler extends LOTRPacketFellowshipPartialUpdate.Handler<SetAdmin> {
		}
	}

	public static class SetOwner extends OnePlayerUpdate {
		public boolean isOwned;

		public SetOwner() {
		}

		public SetOwner(LOTRFellowship fs, UUID owner, boolean owned) {
			super(fs, owner);
			isOwned = owned;
		}

		@Override
		public void fromBytes(ByteBuf data) {
			super.fromBytes(data);
			isOwned = data.readBoolean();
		}

		@Override
		public void toBytes(ByteBuf data) {
			super.toBytes(data);
			data.writeBoolean(isOwned);
		}

		@Override
		public void updateClient(LOTRFellowshipClient fellowship) {
			fellowship.setOwner(playerProfile, isOwned);
		}

		public static class Handler extends LOTRPacketFellowshipPartialUpdate.Handler<SetOwner> {
		}
	}

	public static class ToggleHiredFriendlyFire extends LOTRPacketFellowshipPartialUpdate {
		public boolean preventHiredFF;

		public ToggleHiredFriendlyFire() {
		}

		public ToggleHiredFriendlyFire(LOTRFellowship fs) {
			super(fs);
			preventHiredFF = fs.getPreventHiredFriendlyFire();
		}

		@Override
		public void fromBytes(ByteBuf data) {
			super.fromBytes(data);
			preventHiredFF = data.readBoolean();
		}

		@Override
		public void toBytes(ByteBuf data) {
			super.toBytes(data);
			data.writeBoolean(preventHiredFF);
		}

		@Override
		public void updateClient(LOTRFellowshipClient fellowship) {
			fellowship.setPreventHiredFriendlyFire(preventHiredFF);
		}

		public static class Handler extends LOTRPacketFellowshipPartialUpdate.Handler<ToggleHiredFriendlyFire> {
		}
	}

	public static class TogglePvp extends LOTRPacketFellowshipPartialUpdate {
		public boolean preventPVP;

		public TogglePvp() {
		}

		public TogglePvp(LOTRFellowship fs) {
			super(fs);
			preventPVP = fs.getPreventPVP();
		}

		@Override
		public void fromBytes(ByteBuf data) {
			super.fromBytes(data);
			preventPVP = data.readBoolean();
		}

		@Override
		public void toBytes(ByteBuf data) {
			super.toBytes(data);
			data.writeBoolean(preventPVP);
		}

		@Override
		public void updateClient(LOTRFellowshipClient fellowship) {
			fellowship.setPreventPVP(preventPVP);
		}

		public static class Handler extends LOTRPacketFellowshipPartialUpdate.Handler<TogglePvp> {
		}
	}

	public static class ToggleShowMap extends LOTRPacketFellowshipPartialUpdate {
		public boolean showMapLocations;

		public ToggleShowMap() {
		}

		public ToggleShowMap(LOTRFellowship fs) {
			super(fs);
			showMapLocations = fs.getShowMapLocations();
		}

		@Override
		public void fromBytes(ByteBuf data) {
			super.fromBytes(data);
			showMapLocations = data.readBoolean();
		}

		@Override
		public void toBytes(ByteBuf data) {
			super.toBytes(data);
			data.writeBoolean(showMapLocations);
		}

		@Override
		public void updateClient(LOTRFellowshipClient fellowship) {
			fellowship.setShowMapLocations(showMapLocations);
		}

		public static class Handler extends LOTRPacketFellowshipPartialUpdate.Handler<ToggleShowMap> {
		}
	}

	public static class UpdatePlayerTitle extends OnePlayerUpdate {
		public LOTRTitle.PlayerTitle playerTitle;

		public UpdatePlayerTitle() {
		}

		public UpdatePlayerTitle(LOTRFellowship fs, UUID player, LOTRTitle.PlayerTitle title) {
			super(fs, player);
			playerTitle = title;
		}

		@Override
		public void fromBytes(ByteBuf data) {
			super.fromBytes(data);
			playerTitle = LOTRTitle.PlayerTitle.readNullableTitle(data);
		}

		@Override
		public void toBytes(ByteBuf data) {
			super.toBytes(data);
			LOTRTitle.PlayerTitle.writeNullableTitle(data, playerTitle);
		}

		@Override
		public void updateClient(LOTRFellowshipClient fellowship) {
			fellowship.updatePlayerTitle(playerProfile.getId(), playerTitle);
		}

		public static class Handler extends LOTRPacketFellowshipPartialUpdate.Handler<UpdatePlayerTitle> {
		}
	}
}
