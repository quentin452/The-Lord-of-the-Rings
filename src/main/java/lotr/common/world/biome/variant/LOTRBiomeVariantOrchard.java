package lotr.common.world.biome.variant;

import java.util.Random;

import com.google.common.math.IntMath;

import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.map.LOTRRoads;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRBiomeVariantOrchard extends LOTRBiomeVariant {
	public LOTRBiomeVariantOrchard(int i, String s) {
		super(i, s, LOTRBiomeVariant.VariantScale.SMALL);
		setTemperatureRainfall(0.1f, 0.2f);
		setHeight(0.0f, 0.4f);
		setTrees(0.0f);
		setGrass(0.5f);
		disableStructuresVillages();
	}

	@Override
	public void decorateVariant(World world, Random random, int i, int k, LOTRBiome biome) {
		for (int i1 : new int[]{i + 3, i + 11}) {
			int k1 = k + 8;
			int j1 = world.getHeightValue(i1, k1);
			WorldGenAbstractTree treeGen = getRandomTree(random).create(false, random);
			treeGen.generate(world, random, i1, j1, k1);
		}
	}

	@Override
	public void generateVariantTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int height, LOTRBiome biome) {
		int chunkX = i & 0xF;
		int chunkZ = k & 0xF;
		int xzIndex = chunkX * 16 + chunkZ;
		int ySize = blocks.length / 256;
		if (!LOTRRoads.isRoadAt(i, k)) {
			for (int j = 128; j >= 0; --j) {
				int index = xzIndex * ySize + j;
				Block above = blocks[index + 1];
				Block block = blocks[index];
				if (!block.isOpaqueCube() || above.getMaterial() != Material.air) {
					continue;
				}
				int i1 = IntMath.mod(i, 32);
				int k1 = IntMath.mod(k, 16);
				if (i1 == 6 || i1 == 7 || i1 == 8 || k1 != 0) {
					break;
				}
				blocks[index + 1] = Blocks.fence;
				meta[index + 1] = 0;
				break;
			}
		}
	}
}
