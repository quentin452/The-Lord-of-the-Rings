package lotr.common.block;

import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class LOTRBlockFruitSapling extends LOTRBlockSaplingBase {
	public LOTRBlockFruitSapling() {
		setSaplingNames("apple", "pear", "cherry", "mango");
	}

	@Override
	public void growTree(World world, int i, int j, int k, Random random) {
		int meta = world.getBlockMetadata(i, j, k) & 7;
		WorldGenAbstractTree treeGen = null;
		switch (meta) {
			case 0:
				treeGen = LOTRTreeType.APPLE.create(true, random);
				break;
			case 1:
				treeGen = LOTRTreeType.PEAR.create(true, random);
				break;
			case 2:
				treeGen = LOTRTreeType.CHERRY.create(true, random);
				break;
			case 3:
				treeGen = LOTRTreeType.MANGO.create(true, random);
				break;
			default:
				break;
		}
		world.setBlock(i, j, k, Blocks.air, 0, 4);
		if (treeGen != null && !treeGen.generate(world, random, i, j, k)) {
			world.setBlock(i, j, k, this, meta, 4);
		}
	}
}
