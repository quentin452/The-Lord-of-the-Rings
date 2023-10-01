package lotr.common.world.genlayer;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Arrays;

public class LOTRGenLayerBiome extends LOTRGenLayer {
	public BiomeGenBase theBiome;

	public LOTRGenLayerBiome(BiomeGenBase biome) {
		super(0L);
		theBiome = biome;
	}

	@Override
	public int[] getInts(World world, int i, int k, int xSize, int zSize) {
		int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);
		Arrays.fill(ints, theBiome.biomeID);
		return ints;
	}
}
