package lotr.common.world.genlayer;

import java.util.*;
import java.util.Map.Entry;

import cpw.mods.fml.common.FMLLog;
import lotr.common.LOTRDimension;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.world.World;

public class LOTRGenLayerRemoveMapRivers extends LOTRGenLayer {
	public static int MAX_PIXEL_RANGE = 4;
	public LOTRDimension dimension;

	public LOTRGenLayerRemoveMapRivers(long l, LOTRGenLayer biomes, LOTRDimension dim) {
		super(l);
		lotrParent = biomes;
		dimension = dim;
	}

	@Override
	public int[] getInts(World world, int i, int k, int xSize, int zSize) {
		int maxRange = MAX_PIXEL_RANGE;
		int[] biomes = lotrParent.getInts(world, i - maxRange, k - maxRange, xSize + maxRange * 2, zSize + maxRange * 2);
		int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);
		for (int k1 = 0; k1 < zSize; ++k1) {
			for (int i1 = 0; i1 < xSize; ++i1) {
				initChunkSeed(i + i1, k + k1);
				int biomeID = biomes[i1 + maxRange + (k1 + maxRange) * (xSize + maxRange * 2)];
				if (biomeID == LOTRBiome.river.biomeID) {
					int replaceID = -1;
					for (int range = 1; range <= maxRange; ++range) {
						int id;
						int subBiomeID;
						int count;
						HashMap<Integer, Integer> viableBiomes = new HashMap<>();
						HashMap<Integer, Integer> viableBiomesWateryAdjacent = new HashMap<>();
						for (int k2 = k1 - range; k2 <= k1 + range; ++k2) {
							for (int i2 = i1 - range; i2 <= i1 + range; ++i2) {
								LOTRBiome subBiome;
								if (Math.abs(i2 - i1) != range && Math.abs(k2 - k1) != range || (subBiome = dimension.biomeList[subBiomeID = biomes[i2 + maxRange + (k2 + maxRange) * (xSize + maxRange * 2)]]) == LOTRBiome.river) {
									continue;
								}
								boolean wateryAdjacent = subBiome.isWateryBiome() && range == 1;
								HashMap<Integer, Integer> srcMap = wateryAdjacent ? viableBiomesWateryAdjacent : viableBiomes;
								int count2 = 0;
								if (srcMap.containsKey(subBiomeID)) {
									count2 = srcMap.get(subBiomeID);
								}
								count2++;
								srcMap.put(subBiomeID, count2);
							}
						}
						Map<Integer, Integer> priorityMap = viableBiomes;
						if (!viableBiomesWateryAdjacent.isEmpty()) {
							priorityMap = viableBiomesWateryAdjacent;
						}
						if (priorityMap.isEmpty()) {
							continue;
						}
						ArrayList<Integer> maxCountBiomes = new ArrayList<>();
						int maxCount = 0;
						for (Entry<Integer, Integer> e : priorityMap.entrySet()) {
							count = e.getValue();
							if (count <= maxCount) {
								continue;
							}
							maxCount = count;
						}
						for (Entry e : priorityMap.entrySet()) {
							id = (Integer) e.getKey();
							count = (Integer) e.getValue();
							if (count != maxCount) {
								continue;
							}
							maxCountBiomes.add(id);
						}
						replaceID = maxCountBiomes.get(nextInt(maxCountBiomes.size()));
						break;
					}
					if (replaceID == -1) {
						FMLLog.warning("WARNING! LOTR map generation failed to replace map river at %d, %d", i, k);
						ints[i1 + k1 * xSize] = 0;
						continue;
					}
					ints[i1 + k1 * xSize] = replaceID;
					continue;
				}
				ints[i1 + k1 * xSize] = biomeID;
			}
		}
		return ints;
	}
}
