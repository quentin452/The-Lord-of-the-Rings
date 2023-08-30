package lotr.common.playerdetails;

import java.io.*;
import java.net.*;
import java.util.*;

import org.apache.commons.io.*;
import org.apache.logging.log4j.*;

import com.google.common.math.IntMath;
import com.google.gson.*;

public class PlayerDetailsApiClient {
	public static String API_URL = "https://sd58ui0e24.execute-api.eu-west-2.amazonaws.com/lotrmod/playerdetails/{playerId}";
	public static Gson GSON = new GsonBuilder().create();
	public static Logger LOGGER = LogManager.getLogger();

	public void getPlayerDetailsAsync(UUID playerId, Callback callback) {
		Thread thread = new Thread("Playerdetails fetcher") {

			public void backoffAndRetry(int attemptNumber) {
				if (attemptNumber > 4) {
					LOGGER.error("Reached max number of retries for playerdetails API (playerId = {}). No further requests will be made for this player while the game is running.", playerId);
					return;
				}
				try {
					int waitMs = IntMath.pow(3, attemptNumber) * 1000;
					LOGGER.debug("Scheduling retry #{} with delay {}ms for playerdetails API (playerId = {})", attemptNumber + 1, waitMs, playerId);
					Thread.sleep(waitMs);
					executeRequest(attemptNumber + 1);
				} catch (InterruptedException e) {
					LOGGER.error("Exception scheduling retry #{} for playerdetails API (playerId = {}). Will not retry.", attemptNumber + 1, playerId, e);
				}
			}

			public void executeRequest(int attemptNumber) {
				block6:
				{
					try {
						URL url = new URL(API_URL.replace("{playerId}", playerId.toString()));
						LOGGER.debug("Making call to fetch playerdetails at {} (attempt #{})", url, attemptNumber);
						try {
							HttpURLConnection connection = (HttpURLConnection) url.openConnection();
							int statusCode = connection.getResponseCode();
							LOGGER.debug("Got response with status {} from playerdetails API (playerId = {})", statusCode, playerId);
							if (statusCode == 200) {
								parseSuccessResponseWithCallback(playerId, callback, connection);
								break block6;
							}
							int statusFamily = statusCode / 100;
							if (statusFamily == 4 || statusFamily == 5) {
								LOGGER.error("Failure response code {} from playerdetails API (playerId = {}). Retrying...", statusCode, playerId);
								backoffAndRetry(attemptNumber);
								break block6;
							}
							LOGGER.error("Unsupported response code {} from playerdetails API (playerId = {}). Will not retry.", statusCode, playerId);
						} catch (IOException e) {
							LOGGER.error("Exception calling playerdetails API (playerId = {}). Retrying...", playerId, e);
							backoffAndRetry(attemptNumber);
						}
					} catch (MalformedURLException e) {
						LOGGER.error("Malformed URL for playerdetails API (playerId = {})", playerId, e);
					}
				}
			}

			@Override
			public void run() {
				executeRequest(0);
			}
		};
		thread.setDaemon(true);
		thread.start();
	}

	@SuppressWarnings("MalformedFormatString")
	public PlayerDetails parsePlayerDetailsFromResponse(UUID playerId, JsonObject json) {
		UUID responsePlayerId = UUID.fromString(json.get("uuid").getAsString());
		if (!responsePlayerId.equals(playerId)) {
			throw new IllegalArgumentException(String.format("Player ID in response ({}) did not match requested ID ({})", responsePlayerId, playerId));
		}
		List<String> groups = new ArrayList<>();
		if (json.has("groups")) {
			for (JsonElement elem : json.get("groups").getAsJsonArray()) {
				groups.add(elem.getAsString());
			}
		}
		return PlayerDetails.fromResponse(responsePlayerId, groups);
	}

	/*
	 * WARNING - Removed try catching itself - possible behaviour change.
	 */
	public void parseSuccessResponseWithCallback(UUID playerId, Callback callback, HttpURLConnection connection) {
		InputStream contentStream = null;
		try {
			contentStream = connection.getInputStream();
			String encoding = connection.getContentEncoding();
			if (encoding == null) {
				encoding = Charsets.UTF_8.name();
			}
			String responseBody = IOUtils.toString(contentStream, encoding);
			JsonObject json = GSON.fromJson(responseBody, JsonObject.class);
			LOGGER.debug("Got JSON in playerdetails success response: {}", json);
			PlayerDetails playerDetails = parsePlayerDetailsFromResponse(playerId, json);
			callback.accept(playerDetails);
			LOGGER.debug("Fetched playerdetails for {}, result = {}", playerId, playerDetails);
		} catch (Exception e) {
			LOGGER.error("Exception deserialising success response from playerdetails API (playerId = {}). Will not retry.", playerId, e);
		} finally {
			IOUtils.closeQuietly(contentStream);
		}
	}

	public interface Callback {
		void accept(PlayerDetails var1);
	}

}
