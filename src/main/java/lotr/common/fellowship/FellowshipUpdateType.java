package lotr.common.fellowship;

import com.google.common.collect.ImmutableList;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.LOTRPlayerData;
import lotr.common.LOTRTitle;
import lotr.common.network.LOTRPacketFellowship;
import lotr.common.network.LOTRPacketFellowshipPartialUpdate;

import java.util.List;
import java.util.UUID;

public interface FellowshipUpdateType {
	IMessage createUpdatePacket(LOTRPlayerData var1, LOTRFellowship var2);

	List<UUID> getPlayersToCheckSharedWaypointsFrom(LOTRFellowship var1);

	class AddMember implements FellowshipUpdateType {
		public UUID memberID;

		public AddMember(UUID member) {
			memberID = member;
		}

		@Override
		public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
			return new LOTRPacketFellowshipPartialUpdate.AddMember(fs, memberID);
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
			return ImmutableList.of(memberID);
		}
	}

	class ChangeIcon implements FellowshipUpdateType {
		@Override
		public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
			return new LOTRPacketFellowshipPartialUpdate.ChangeIcon(fs);
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
			return null;
		}
	}

	class Full implements FellowshipUpdateType {
		@Override
		public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
			return new LOTRPacketFellowship(pd, fs, false);
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
			return fs.getAllPlayerUUIDs();
		}
	}

	class RemoveAdmin implements FellowshipUpdateType {
		public UUID adminID;

		public RemoveAdmin(UUID admin) {
			adminID = admin;
		}

		@Override
		public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
			return new LOTRPacketFellowshipPartialUpdate.RemoveAdmin(fs, adminID, fs.isAdmin(pd.getPlayerUUID()));
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
			return null;
		}
	}

	class RemoveMember implements FellowshipUpdateType {
		public UUID memberID;

		public RemoveMember(UUID member) {
			memberID = member;
		}

		@Override
		public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
			return new LOTRPacketFellowshipPartialUpdate.RemoveMember(fs, memberID);
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
			return ImmutableList.of(memberID);
		}
	}

	class Rename implements FellowshipUpdateType {
		@Override
		public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
			return new LOTRPacketFellowshipPartialUpdate.Rename(fs);
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
			return null;
		}
	}

	class SetAdmin implements FellowshipUpdateType {
		public UUID adminID;

		public SetAdmin(UUID admin) {
			adminID = admin;
		}

		@Override
		public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
			return new LOTRPacketFellowshipPartialUpdate.SetAdmin(fs, adminID, fs.isAdmin(pd.getPlayerUUID()));
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
			return null;
		}
	}

	class SetOwner implements FellowshipUpdateType {
		public UUID ownerID;

		public SetOwner(UUID owner) {
			ownerID = owner;
		}

		@Override
		public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
			return new LOTRPacketFellowshipPartialUpdate.SetOwner(fs, ownerID, fs.isOwner(pd.getPlayerUUID()));
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
			return ImmutableList.of(ownerID);
		}
	}

	class ToggleHiredFriendlyFire implements FellowshipUpdateType {
		@Override
		public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
			return new LOTRPacketFellowshipPartialUpdate.ToggleHiredFriendlyFire(fs);
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
			return null;
		}
	}

	class TogglePvp implements FellowshipUpdateType {
		@Override
		public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
			return new LOTRPacketFellowshipPartialUpdate.TogglePvp(fs);
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
			return null;
		}
	}

	class ToggleShowMapLocations implements FellowshipUpdateType {
		@Override
		public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
			return new LOTRPacketFellowshipPartialUpdate.ToggleShowMap(fs);
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
			return null;
		}
	}

	class UpdatePlayerTitle implements FellowshipUpdateType {
		public UUID playerID;
		public LOTRTitle.PlayerTitle playerTitle;

		public UpdatePlayerTitle(UUID player, LOTRTitle.PlayerTitle title) {
			playerID = player;
			playerTitle = title;
		}

		@Override
		public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
			return new LOTRPacketFellowshipPartialUpdate.UpdatePlayerTitle(fs, playerID, playerTitle);
		}

		@Override
		public List<UUID> getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
			return null;
		}
	}

}
