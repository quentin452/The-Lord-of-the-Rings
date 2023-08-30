package lotr.common.world.biome;

import java.util.Random;

import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class LOTRBiomeGenNearHaradHills extends LOTRBiomeGenNearHarad {
	public static NoiseGeneratorPerlin noiseSandstone = new NoiseGeneratorPerlin(new Random(8906820602062L), 1);
	public static NoiseGeneratorPerlin noiseStone = new NoiseGeneratorPerlin(new Random(583062262026L), 1);

	public LOTRBiomeGenNearHaradHills(int i, boolean major) {
		super(i, major);
		enableRain = true;
		clearBiomeVariants();
		addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
		addBiomeVariant(LOTRBiomeVariant.SCRUBLAND_SAND);
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
		Block topBlock_pre = topBlock;
		int topBlockMeta_pre = topBlockMeta;
		Block fillerBlock_pre = fillerBlock;
		int fillerBlockMeta_pre = fillerBlockMeta;
		double d1 = noiseSandstone.func_151601_a(i * 0.09, k * 0.09);
		double d2 = noiseSandstone.func_151601_a(i * 0.4, k * 0.4);
		double d3 = noiseStone.func_151601_a(i * 0.09, k * 0.09);
		if (d3 + noiseStone.func_151601_a(i * 0.4, k * 0.4) > 0.6) {
			topBlock = Blocks.stone;
			topBlockMeta = 0;
			fillerBlock = topBlock;
			fillerBlockMeta = topBlockMeta;
		} else if (d1 + d2 > 0.2) {
			topBlock = Blocks.sandstone;
			topBlockMeta = 0;
			fillerBlock = topBlock;
			fillerBlockMeta = topBlockMeta;
		}
		super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		topBlock = topBlock_pre;
		topBlockMeta = topBlockMeta_pre;
		fillerBlock = fillerBlock_pre;
		fillerBlockMeta = fillerBlockMeta_pre;
	}

	@Override
	public float getTreeIncreaseChance() {
		return 0.01f;
	}
}
