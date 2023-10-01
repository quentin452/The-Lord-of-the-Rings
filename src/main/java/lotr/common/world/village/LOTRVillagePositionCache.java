package lotr.common.world.village;

import net.minecraft.world.ChunkCoordIntPair;

import java.util.HashMap;
import java.util.Map;

public class LOTRVillagePositionCache {
	public Map<ChunkCoordIntPair, LocationInfo> cacheMap = new HashMap<>();

	public void clearCache() {
		cacheMap.clear();
	}

	public ChunkCoordIntPair getChunkKey(int chunkX, int chunkZ) {
		return new ChunkCoordIntPair(chunkX, chunkZ);
	}

	public LocationInfo getLocationAt(int chunkX, int chunkZ) {
		return cacheMap.get(getChunkKey(chunkX, chunkZ));
	}

	public LocationInfo markResult(int chunkX, int chunkZ, LocationInfo result) {
		if (cacheMap.size() >= 20000) {
			clearCache();
		}
		cacheMap.put(getChunkKey(chunkX, chunkZ), result);
		return result;
	}
}
