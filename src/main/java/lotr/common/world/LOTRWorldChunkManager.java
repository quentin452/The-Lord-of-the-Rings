package lotr.common.world;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRDimension;
import lotr.common.LOTRMod;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.LOTRBiomeGenFarHaradJungle;
import lotr.common.world.biome.LOTRBiomeGenFarHaradMangrove;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.biome.variant.LOTRBiomeVariantList;
import lotr.common.world.biome.variant.LOTRBiomeVariantStorage;
import lotr.common.world.genlayer.*;
import lotr.common.world.map.LOTRFixedStructures;
import lotr.common.world.village.LOTRVillageGen;
import lotr.common.world.village.LOTRVillagePositionCache;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.structure.MapGenStructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class LOTRWorldChunkManager extends WorldChunkManager {
	public static int LAYER_BIOME;
	public static int LAYER_VARIANTS_LARGE = 1;
	public static int LAYER_VARIANTS_SMALL = 2;
	public static int LAYER_VARIANTS_LAKES = 3;
	public static int LAYER_VARIANTS_RIVERS = 4;
	public World worldObj;
	public LOTRDimension lotrDimension;
	public LOTRGenLayer[] chunkGenLayers;
	public LOTRGenLayer[] worldLayers;
	public BiomeCache biomeCache;
	public Map<LOTRVillageGen, LOTRVillagePositionCache> villageCacheMap = new HashMap<>();
	public Map<MapGenStructure, LOTRVillagePositionCache> structureCacheMap = new HashMap<>();

	public LOTRWorldChunkManager(World world, LOTRDimension dim) {
		worldObj = world;
		biomeCache = new BiomeCache(this);
		lotrDimension = dim;
		setupGenLayers();
	}

	@Override
	public boolean areBiomesViable(int i, int k, int range, List list) {
		LOTRIntCache.get(worldObj).resetIntCache();
		int i1 = i - range >> 2;
		int k1 = k - range >> 2;
		int i2 = i + range >> 2;
		int k2 = k + range >> 2;
		int i3 = i2 - i1 + 1;
		int k3 = k2 - k1 + 1;
		int[] ints = chunkGenLayers[LAYER_BIOME].getInts(worldObj, i1, k1, i3, k3);
		for (int l = 0; l < i3 * k3; ++l) {
			LOTRBiome biome = lotrDimension.biomeList[ints[l]];
			if (list.contains(biome)) {
				continue;
			}
			return false;
		}
		return true;
	}

	public boolean areVariantsSuitableVillage(int i, int k, int range, boolean requireFlat) {
		int i1 = i - range >> 2;
		int k1 = k - range >> 2;
		int i2 = i + range >> 2;
		int k2 = k + range >> 2;
		int i3 = i2 - i1 + 1;
		int k3 = k2 - k1 + 1;
		BiomeGenBase[] biomes = getBiomesForGeneration(null, i1, k1, i3, k3);
		for (LOTRBiomeVariant v : getVariantsChunkGen(null, i1, k1, i3, k3, biomes)) {
			if (v.hillFactor > 1.6f || requireFlat && v.hillFactor > 1.0f || v.treeFactor > 1.0f) {
				return false;
			}
			if (v.disableVillages) {
				return false;
			}
			if (!v.absoluteHeight || v.absoluteHeightLevel >= 0.0f) {
				continue;
			}
			return false;
		}
		return true;
	}

	@Override
	public void cleanupCache() {
		biomeCache.cleanupCache();
	}

	@Override
	public ChunkPosition findBiomePosition(int i, int k, int range, List list, Random random) {
		LOTRIntCache.get(worldObj).resetIntCache();
		int i1 = i - range >> 2;
		int k1 = k - range >> 2;
		int i2 = i + range >> 2;
		int k2 = k + range >> 2;
		int i3 = i2 - i1 + 1;
		int k3 = k2 - k1 + 1;
		int[] ints = chunkGenLayers[LAYER_BIOME].getInts(worldObj, i1, k1, i3, k3);
		ChunkPosition chunkpos = null;
		int j = 0;
		for (int l = 0; l < i3 * k3; ++l) {
			int xPos = i1 + l % i3 << 2;
			int zPos = k1 + l / i3 << 2;
			LOTRBiome biome = lotrDimension.biomeList[ints[l]];
			if (!list.contains(biome) || chunkpos != null && random.nextInt(j + 1) != 0) {
				continue;
			}
			chunkpos = new ChunkPosition(xPos, 0, zPos);
			++j;
		}
		return chunkpos;
	}

	@Override
	public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] biomes, int i, int k, int xSize, int zSize, boolean useCache) {
		LOTRIntCache.get(worldObj).resetIntCache();
		if (biomes == null || biomes.length < xSize * zSize) {
			biomes = new BiomeGenBase[xSize * zSize];
		}
		if (useCache && xSize == 16 && zSize == 16 && (i & 0xF) == 0 && (k & 0xF) == 0) {
			BiomeGenBase[] cachedBiomes = biomeCache.getCachedBiomes(i, k);
			System.arraycopy(cachedBiomes, 0, biomes, 0, 16 * 16);
			return biomes;
		}
		int[] ints = worldLayers[LAYER_BIOME].getInts(worldObj, i, k, xSize, zSize);
		for (int l = 0; l < xSize * zSize; ++l) {
			int biomeID = ints[l];
			biomes[l] = lotrDimension.biomeList[biomeID];
		}
		return biomes;
	}

	@Override
	public BiomeGenBase getBiomeGenAt(int i, int k) {
		return biomeCache.getBiomeGenAt(i, k);
	}

	@Override
	public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] biomes, int i, int k, int xSize, int zSize) {
		LOTRIntCache.get(worldObj).resetIntCache();
		if (biomes == null || biomes.length < xSize * zSize) {
			biomes = new BiomeGenBase[xSize * zSize];
		}
		int[] ints = chunkGenLayers[LAYER_BIOME].getInts(worldObj, i, k, xSize, zSize);
		for (int l = 0; l < xSize * zSize; ++l) {
			int biomeID = ints[l];
			biomes[l] = lotrDimension.biomeList[biomeID];
		}
		return biomes;
	}

	public LOTRBiomeVariant getBiomeVariantAt(int i, int k) {
		byte[] variants;
		Chunk chunk = worldObj.getChunkFromBlockCoords(i, k);
		if (chunk != null && (variants = LOTRBiomeVariantStorage.getChunkBiomeVariants(worldObj, chunk)) != null) {
			if (variants.length == 256) {
				int chunkX = i & 0xF;
				int chunkZ = k & 0xF;
				byte variantID = variants[chunkX + chunkZ * 16];
				return LOTRBiomeVariant.getVariantForID(variantID);
			}
			FMLLog.severe("Found chunk biome variant array of unexpected length " + variants.length);
		}
		if (!worldObj.isRemote) {
			return getBiomeVariants(null, i, k, 1, 1)[0];
		}
		return LOTRBiomeVariant.STANDARD;
	}

	public LOTRBiomeVariant[] getBiomeVariants(LOTRBiomeVariant[] variants, int i, int k, int xSize, int zSize) {
		return getBiomeVariantsFromLayers(variants, i, k, xSize, zSize, null, false);
	}

	public LOTRBiomeVariant[] getBiomeVariantsFromLayers(LOTRBiomeVariant[] variants, int i, int k, int xSize, int zSize, BiomeGenBase[] biomeSource, boolean isChunkGeneration) {
		LOTRIntCache.get(worldObj).resetIntCache();
		BiomeGenBase[] biomes = new BiomeGenBase[xSize * zSize];
		if (biomeSource != null) {
			biomes = biomeSource;
		} else {
			for (int k1 = 0; k1 < zSize; ++k1) {
				for (int i1 = 0; i1 < xSize; ++i1) {
					int index = i1 + k1 * xSize;
					biomes[index] = worldObj.getBiomeGenForCoords(i + i1, k + k1);
				}
			}
		}
		if (variants == null || variants.length < xSize * zSize) {
			variants = new LOTRBiomeVariant[xSize * zSize];
		}
		LOTRGenLayer[] sourceGenLayers = isChunkGeneration ? chunkGenLayers : worldLayers;
		LOTRGenLayer variantsLarge = sourceGenLayers[LAYER_VARIANTS_LARGE];
		LOTRGenLayer variantsSmall = sourceGenLayers[LAYER_VARIANTS_SMALL];
		LOTRGenLayer variantsLakes = sourceGenLayers[LAYER_VARIANTS_LAKES];
		LOTRGenLayer variantsRivers = sourceGenLayers[LAYER_VARIANTS_RIVERS];
		int[] variantsLargeInts = variantsLarge.getInts(worldObj, i, k, xSize, zSize);
		int[] variantsSmallInts = variantsSmall.getInts(worldObj, i, k, xSize, zSize);
		int[] variantsLakesInts = variantsLakes.getInts(worldObj, i, k, xSize, zSize);
		int[] variantsRiversInts = variantsRivers.getInts(worldObj, i, k, xSize, zSize);
		for (int k1 = 0; k1 < zSize; ++k1) {
			for (int i1 = 0; i1 < xSize; ++i1) {
				int riverCode;
				int index = i1 + k1 * xSize;
				LOTRBiome biome = (LOTRBiome) biomes[index];
				LOTRBiomeVariant variant = LOTRBiomeVariant.STANDARD;
				int xPos = i + i1;
				int zPos = k + k1;
				if (isChunkGeneration) {
					xPos <<= 2;
					zPos <<= 2;
				}
				boolean[] flags = LOTRFixedStructures._mountainNear_structureNear(worldObj, xPos, zPos);
				boolean mountainNear = flags[0];
				boolean structureNear = flags[1];
				boolean fixedVillageNear = biome.decorator.anyFixedVillagesAt(worldObj, xPos, zPos);
				if (!mountainNear && !fixedVillageNear) {
					float variantChance = biome.variantChance;
					if (variantChance > 0.0f) {
						for (int pass = 0; pass <= 1; ++pass) {
							LOTRBiomeVariantList variantList;
							variantList = pass == 0 ? biome.getBiomeVariantsLarge() : biome.getBiomeVariantsSmall();
							if (variantList.isEmpty()) {
								continue;
							}
							int[] sourceInts = pass == 0 ? variantsLargeInts : variantsSmallInts;
							int variantCode = sourceInts[index];
							float variantF = (float) variantCode / LOTRGenLayerBiomeVariants.RANDOM_MAX;
							if (variantF < variantChance) {
								float variantFNormalised = variantF / variantChance;
								variant = variantList.get(variantFNormalised);
								break;
							}
							variant = LOTRBiomeVariant.STANDARD;
						}
					}
					if (!structureNear && biome.getEnableRiver()) {
						int lakeCode = variantsLakesInts[index];
						if (LOTRGenLayerBiomeVariantsLake.getFlag(lakeCode, 1)) {
							variant = LOTRBiomeVariant.LAKE;
						}
						if (LOTRGenLayerBiomeVariantsLake.getFlag(lakeCode, 2) && biome instanceof LOTRBiomeGenFarHaradJungle && ((LOTRBiomeGenFarHaradJungle) biome).hasJungleLakes()) {
							variant = LOTRBiomeVariant.LAKE;
						}
						if (LOTRGenLayerBiomeVariantsLake.getFlag(lakeCode, 4) && biome instanceof LOTRBiomeGenFarHaradMangrove) {
							variant = LOTRBiomeVariant.LAKE;
						}
					}
				}
				riverCode = variantsRiversInts[index];
				if (riverCode == 2) {
					variant = LOTRBiomeVariant.RIVER;
				} else if (riverCode == 1 && biome.getEnableRiver() && !structureNear && !mountainNear) {
					variant = LOTRBiomeVariant.RIVER;
				}
				variants[index] = variant;
			}
		}
		return variants;
	}

    @Override
    public float[] getRainfall(float[] rainfall, int i, int k, int xSize, int zSize) {
        LOTRIntCache.get(worldObj).resetIntCache();
        if (rainfall == null || rainfall.length < xSize * zSize) {
            rainfall = new float[xSize * zSize];
        }
        int[] ints = worldLayers[LAYER_BIOME].getInts(worldObj, i, k, xSize, zSize);
        for (int l = 0; l < xSize * zSize; ++l) {
            int biomeID = ints[l];
            if(biomeID >= 0 && biomeID <= 255 && lotrDimension.biomeList.length > biomeID) {
                float f = lotrDimension.biomeList[biomeID].getIntRainfall() / 65536.0f;
                if (f > 1.0f) {
                    f = 1.0f;
                }
                rainfall[l] = f;
            } else {
               return new float[0];
            }
        }
        return rainfall;
    }


	public LOTRVillagePositionCache getStructureCache(MapGenStructure structure) {
		LOTRVillagePositionCache cache = structureCacheMap.get(structure);
		if (cache == null) {
			cache = new LOTRVillagePositionCache();
			structureCacheMap.put(structure, cache);
		}
		return cache;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getTemperatureAtHeight(float f, int height) {
		if (worldObj.isRemote && LOTRMod.isChristmas()) {
			return 0.0f;
		}
		return f;
	}

	public LOTRBiomeVariant[] getVariantsChunkGen(LOTRBiomeVariant[] variants, int i, int k, int xSize, int zSize, BiomeGenBase[] biomeSource) {
		return getBiomeVariantsFromLayers(variants, i, k, xSize, zSize, biomeSource, true);
	}

	public LOTRVillagePositionCache getVillageCache(LOTRVillageGen village) {
		LOTRVillagePositionCache cache = villageCacheMap.get(village);
		if (cache == null) {
			cache = new LOTRVillagePositionCache();
			villageCacheMap.put(village, cache);
		}
		return cache;
	}

	@Override
	public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] biomes, int i, int k, int xSize, int zSize) {
		return getBiomeGenAt(biomes, i, k, xSize, zSize, true);
	}

	public void setupGenLayers() {
		int i;
		long seed = worldObj.getSeed() + 1954L;
		chunkGenLayers = LOTRGenLayerWorld.createWorld(lotrDimension, worldObj.getWorldInfo().getTerrainType());
		worldLayers = new LOTRGenLayer[chunkGenLayers.length];
		for (i = 0; i < worldLayers.length; ++i) {
			LOTRGenLayer layer = chunkGenLayers[i];
			worldLayers[i] = new LOTRGenLayerZoomVoronoi(10L, layer);
		}
		for (i = 0; i < worldLayers.length; ++i) {
			chunkGenLayers[i].initWorldGenSeed(seed);
			worldLayers[i].initWorldGenSeed(seed);
		}
	}
}
