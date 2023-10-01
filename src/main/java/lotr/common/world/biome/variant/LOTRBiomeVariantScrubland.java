package lotr.common.world.biome.variant;

import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRBiomeVariantScrubland extends LOTRBiomeVariant {
	public Block stoneBlock;

	public LOTRBiomeVariantScrubland(int i, String s, Block block) {
		super(i, s, LOTRBiomeVariant.VariantScale.LARGE);
		setTemperatureRainfall(0.0f, -0.2f);
		setTrees(0.8f);
		setGrass(0.5f);
		setFlowers(0.5f);
		addTreeTypes(0.6f, LOTRTreeType.OAK_SHRUB, 100);
		stoneBlock = block;
		disableVillages();
	}

	@Override
	public void generateVariantTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int height, LOTRBiome biome) {
		int index;
		int j;
		double d2;
		int chunkX = i & 0xF;
		int chunkZ = k & 0xF;
		int xzIndex = chunkX * 16 + chunkZ;
		int ySize = blocks.length / 256;
		double d1 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.005, k * 0.005);
		d2 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.07, k * 0.07);
		if (d1 + d2 + LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.3, k * 0.3) > 0.6) {
			j = height;
			index = xzIndex * ySize + j;
			if (d1 + d2 > 0.7 && random.nextInt(3) != 0) {
				blocks[index] = Blocks.sand;
			} else if (random.nextInt(5) == 0) {
				blocks[index] = Blocks.gravel;
			} else {
				blocks[index] = stoneBlock;
			}
			meta[index] = 0;
			if (random.nextInt(30) == 0) {
				if (random.nextInt(3) == 0) {
					blocks[index + 1] = stoneBlock;
				} else {
					blocks[index + 1] = Blocks.gravel;
				}
				meta[index + 1] = 0;
			}
		}
		d1 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.008, k * 0.008);
		d2 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.05, k * 0.05);
		if (d1 + d2 + LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.6, k * 0.6) > 0.8 && random.nextInt(3) == 0) {
			j = height;
			index = xzIndex * ySize + j;
			Block above = blocks[index + 1];
			Block block = blocks[index];
			if (block.isOpaqueCube() && above.getMaterial() == Material.air) {
				blocks[index + 1] = Blocks.leaves;
				meta[index + 1] = 4;
				if (random.nextInt(5) == 0) {
					blocks[index + 2] = Blocks.leaves;
					meta[index + 2] = 4;
				}
			}
		}
		if (random.nextInt(3000) == 0) {
			j = height;
			index = xzIndex * ySize + j;
			blocks[index] = biome.fillerBlock;
			meta[index] = (byte) biome.fillerBlockMeta;
			int logHeight = 1 + random.nextInt(4);
			for (int j1 = 1; j1 <= logHeight; ++j1) {
				blocks[index + j1] = Blocks.log;
				meta[index + j1] = 0;
			}
		}
	}
}
