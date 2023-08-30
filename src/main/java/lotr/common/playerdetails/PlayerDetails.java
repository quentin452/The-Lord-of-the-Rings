package lotr.common.playerdetails;

import java.util.*;

public class PlayerDetails {
	public UUID uuid;
	public List<String> exclusiveGroups;
	public boolean isReceivedFromApi;

	public PlayerDetails(UUID uuid, List<String> exclusiveGroups, boolean isReceivedFromApi) {
		this.uuid = uuid;
		this.exclusiveGroups = exclusiveGroups;
		this.isReceivedFromApi = isReceivedFromApi;
	}

	public boolean hasAnyExclusiveGroup(ExclusiveGroup... groups) {
		for (ExclusiveGroup group : groups) {
			if (!hasExclusiveGroup(group)) {
				continue;
			}
			return true;
		}
		return false;
	}

	public boolean hasExclusiveGroup(ExclusiveGroup group) {
		return exclusiveGroups.contains(group.getName());
	}

	public boolean isReceivedFromApi() {
		return isReceivedFromApi;
	}

	@Override
	public String toString() {
		return String.format("PlayerDetails[uuid=%s,groups=%s,isReceivedFromApi=%b]", uuid, exclusiveGroups, isReceivedFromApi);
	}

	public static PlayerDetails fromResponse(UUID uuid, List<String> exclusiveGroups) {
		return new PlayerDetails(uuid, exclusiveGroups, true);
	}

	public static PlayerDetails getPlaceholder(UUID uuid) {
		return new PlayerDetails(uuid, Collections.emptyList(), false);
	}
}
