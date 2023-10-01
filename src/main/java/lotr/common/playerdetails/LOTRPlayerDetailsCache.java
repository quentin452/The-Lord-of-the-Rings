package lotr.common.playerdetails;

import net.minecraft.entity.player.EntityPlayer;

import java.util.*;

public class LOTRPlayerDetailsCache {
	public Map<UUID, PlayerDetails> detailsForPlayer = new HashMap<>();
	public PlayerDetailsApiClient apiClient = new PlayerDetailsApiClient();
	public Collection<UUID> apiRequestedPlayers = new HashSet<>();

	public void fireRequest(UUID playerId) {
		if (!apiRequestedPlayers.contains(playerId)) {
			apiRequestedPlayers.add(playerId);
			apiClient.getPlayerDetailsAsync(playerId, details -> detailsForPlayer.put(playerId, details));
		}
	}

	public PlayerDetails getPlayerDetails(EntityPlayer player) {
		return getPlayerDetails(player.getUniqueID());
	}

	public PlayerDetails getPlayerDetails(UUID playerId) {
		if (detailsForPlayer.containsKey(playerId)) {
			return detailsForPlayer.get(playerId);
		}
		fireRequest(playerId);
		return PlayerDetails.getPlaceholder(playerId);
	}

}
