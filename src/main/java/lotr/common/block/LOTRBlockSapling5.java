package lotr.common.block;

import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class LOTRBlockSapling5 extends LOTRBlockSaplingBase {
	public LOTRBlockSapling5() {
		setSaplingNames("pine", "lemon", "orange", "lime");
	}

	@Override
	public void growTree(World world, int i, int j, int k, Random random) {
		int meta = world.getBlockMetadata(i, j, k) & 7;
		WorldGenAbstractTree treeGen = null;
		if (meta == 0) {
			treeGen = LOTRTreeType.PINE.create(true, random);
		}
		if (meta == 1) {
			treeGen = LOTRTreeType.LEMON.create(true, random);
		}
		if (meta == 2) {
			treeGen = LOTRTreeType.ORANGE.create(true, random);
		}
		if (meta == 3) {
			treeGen = LOTRTreeType.LIME.create(true, random);
		}
		world.setBlock(i, j, k, Blocks.air, 0, 4);
		if (treeGen != null && !treeGen.generate(world, random, i, j, k)) {
			world.setBlock(i, j, k, this, meta, 4);
		}
	}
}
