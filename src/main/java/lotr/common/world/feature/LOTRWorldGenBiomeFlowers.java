package lotr.common.world.feature;

import lotr.common.world.biome.LOTRBiome;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class LOTRWorldGenBiomeFlowers extends WorldGenerator {
	public Block specifiedBlock;
	public int specifiedMeta;

	public LOTRWorldGenBiomeFlowers() {
		this(null, -1);
	}

	public LOTRWorldGenBiomeFlowers(Block block, int meta) {
		specifiedBlock = block;
		specifiedMeta = meta;
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		Block block;
		int meta;
		if (specifiedBlock != null) {
			block = specifiedBlock;
			meta = specifiedMeta;
		} else {
			BiomeGenBase.FlowerEntry flower = ((LOTRBiome) world.getBiomeGenForCoords(i, k)).getRandomFlower(world, random, i, j, k);
			block = flower.block;
			meta = flower.metadata;
		}
		for (int l = 0; l < 64; ++l) {
			int k1;
			int j1;
			int i1 = i + random.nextInt(8) - random.nextInt(8);
			if (!world.isAirBlock(i1, j1 = j + random.nextInt(4) - random.nextInt(4), k1 = k + random.nextInt(8) - random.nextInt(8)) || world.provider.hasNoSky && j1 >= 255 || !block.canBlockStay(world, i1, j1, k1)) {
				continue;
			}
			world.setBlock(i1, j1, k1, block, meta, 2);
		}
		return true;
	}
}
