package lotr.common.world.feature;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class LOTRWorldGenSeaBlock extends WorldGenerator {
	public Block theBlock;
	public int theMeta;
	public int tries;

	public LOTRWorldGenSeaBlock(Block block, int i, int t) {
		theBlock = block;
		theMeta = i;
		tries = t;
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		for (int l = 0; l < tries; ++l) {
			int i1 = i - random.nextInt(6) + random.nextInt(6);
			int j1 = j - random.nextInt(4) + random.nextInt(4);
			int k1 = k - random.nextInt(6) + random.nextInt(6);
			Block below = world.getBlock(i1, j1 - 1, k1);
			Block block = world.getBlock(i1, j1, k1);
			Material belowMaterial = below.getMaterial();
			if (belowMaterial != Material.sand && belowMaterial != Material.ground || block.getMaterial() != Material.water) {
				continue;
			}
			world.setBlock(i1, j1, k1, theBlock, theMeta, 2);
		}
		return true;
	}
}
