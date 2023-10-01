package lotr.common.world.biome;

import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRBiomeGenWhiteMountainsFoothills extends LOTRBiomeGenWhiteMountains {
	public LOTRBiomeGenWhiteMountainsFoothills(int i, boolean major) {
		super(i, major);
		decorator.biomeGemFactor = 0.75f;
	}

	@Override
	public void generateMountainTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int xzIndex, int ySize, int height, int rockDepth, LOTRBiomeVariant variant) {
	}
}
