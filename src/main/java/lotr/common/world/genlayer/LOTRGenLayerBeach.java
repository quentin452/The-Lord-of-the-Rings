package lotr.common.world.genlayer;

import lotr.common.LOTRDimension;
import lotr.common.world.biome.*;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTRGenLayerBeach extends LOTRGenLayer {
	public LOTRDimension dimension;
	public BiomeGenBase targetBiome;

	public LOTRGenLayerBeach(long l, LOTRGenLayer layer, LOTRDimension dim, BiomeGenBase target) {
		super(l);
		lotrParent = layer;
		dimension = dim;
		targetBiome = target;
	}

    @Override
    public int[] getInts(World world, int i, int k, int xSize, int zSize) {
        int[] biomes = lotrParent.getInts(world, i - 1, k - 1, xSize + 2, zSize + 2);
        int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);
        for (int k1 = 0; k1 < zSize; ++k1) {
            for (int i1 = 0; i1 < xSize; ++i1) {
                initChunkSeed(i + i1, k + k1);
                int biomeID = biomes[i1 + 1 + (k1 + 1) * (xSize + 2)];

                biomeID = Math.max(0, Math.min(255, biomeID));

                LOTRBiome biome = dimension.biomeList[biomeID];
                int newBiomeID = biomeID;

                if (biomeID != targetBiome.biomeID && !biome.isWateryBiome()) {
                    int biome1 = biomes[i1 + 1 + (k1 + 1 - 1) * (xSize + 2)];
                    int biome2 = biomes[i1 + 1 + 1 + (k1 + 1) * (xSize + 2)];
                    int biome3 = biomes[i1 + 1 - 1 + (k1 + 1) * (xSize + 2)];
                    int biome4 = biomes[i1 + 1 + (k1 + 1 + 1) * (xSize + 2)];
                    if (biome1 == targetBiome.biomeID || biome2 == targetBiome.biomeID || biome3 == targetBiome.biomeID || biome4 == targetBiome.biomeID) {
                        if (biome instanceof LOTRBiomeGenLindon) {
                            newBiomeID = nextInt(3) == 0 ? LOTRBiome.lindonCoast.biomeID : LOTRBiome.beachWhite.biomeID;
                        } else if (biome instanceof LOTRBiomeGenForodwaith) {
                            newBiomeID = LOTRBiome.forodwaithCoast.biomeID;
                        } else if (biome instanceof LOTRBiomeGenFarHaradCoast || biome instanceof LOTRBiomeGenFarHaradVolcano) {
                            // Keep the same biomeID if it matches certain conditions
                            newBiomeID = biomeID;
                        } else if (!(biome instanceof LOTRBiomeGenBeach)) {
                            newBiomeID = biome.decorator.whiteSand ? LOTRBiome.beachWhite.biomeID : nextInt(20) == 0 ? LOTRBiome.beachGravel.biomeID : LOTRBiome.beach.biomeID;
                        }
                    }
                }

                newBiomeID = Math.max(0, Math.min(255, newBiomeID));

                ints[i1 + k1 * xSize] = newBiomeID;
            }
        }
        return ints;
    }
}
