package lotr.common.world;

import lotr.common.LOTRDimension;
import lotr.common.LOTRLevelData;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.chunk.IChunkProvider;

public class LOTRWorldProviderMiddleEarth extends LOTRWorldProvider {
	@Override
	public IChunkProvider createChunkGenerator() {
		return new LOTRChunkProvider(worldObj, worldObj.getSeed());
	}

	@Override
	public LOTRDimension getLOTRDimension() {
		return LOTRDimension.MIDDLE_EARTH;
	}

	@Override
	public ChunkCoordinates getSpawnPoint() {
		return new ChunkCoordinates(LOTRLevelData.middleEarthPortalX, LOTRLevelData.middleEarthPortalY, LOTRLevelData.middleEarthPortalZ);
	}

	public void setRingPortalLocation(int i, int j, int k) {
		LOTRLevelData.markMiddleEarthPortalLocation(i, j, k);
	}

	@Override
	public void setSpawnPoint(int i, int j, int k) {
	}
}
