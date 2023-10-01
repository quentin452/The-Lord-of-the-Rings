package lotr.common.block;

import lotr.common.LOTRMod;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class LOTRBlockSapling extends LOTRBlockSaplingBase {
	public LOTRBlockSapling() {
		setSaplingNames("shirePine", "mallorn", "mirkOak", "mirkOakRed");
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		if (super.canBlockStay(world, i, j, k)) {
			return true;
		}
		int meta = world.getBlockMetadata(i, j, k) & 7;
		return meta == 1 && world.getBlock(i, j - 1, k) == LOTRMod.quenditeGrass;
	}

	@Override
	public boolean canReplace(World world, int i, int j, int k, int side, ItemStack item) {
		if (super.canReplace(world, i, j, k, side, item)) {
			return true;
		}
		return item.getItemDamage() == 1 && world.getBlock(i, j - 1, k) == LOTRMod.quenditeGrass;
	}

	@Override
	public void growTree(World world, int i, int j, int k, Random random) {
		int k1;
		int i1;
		int meta = world.getBlockMetadata(i, j, k) & 7;
		WorldGenAbstractTree treeGen = null;
		int trunkNeg = 0;
		int trunkPos = 0;
		int xOffset = 0;
		int zOffset = 0;
		boolean cross = false;
		if (meta == 0) {
			treeGen = LOTRTreeType.SHIRE_PINE.create(true, random);
		}
		if (meta == 1) {
			int[] partyTree;
			int[] boughTree;
			if (world.getBlock(i, j - 1, k) == LOTRMod.quenditeGrass) {
				block0:
				for (i1 = -4; i1 <= 4; ++i1) {
					for (k1 = -4; k1 <= 4; ++k1) {
						boolean canGenerate = true;
						block2:
						for (int i2 = -2; i2 <= 2; ++i2) {
							for (int k2 = -2; k2 <= 2; ++k2) {
								int i3 = i + i1 + i2;
								int k3 = k + k1 + k2;
								if (isSameSapling(world, i3, j, k3, 1) && world.getBlock(i3, j - 1, k3) == LOTRMod.quenditeGrass) {
									continue;
								}
								canGenerate = false;
								break block2;
							}
						}
						if (!canGenerate) {
							continue;
						}
						treeGen = LOTRTreeType.MALLORN_EXTREME_SAPLING.create(true, random);
						trunkPos = 2;
						trunkNeg = 2;
						xOffset = i1;
						zOffset = k1;
						break block0;
					}
				}
			}
			if (treeGen == null && (partyTree = LOTRBlockSaplingBase.findPartyTree(world, i, j, k, this, 1)) != null) {
				treeGen = LOTRTreeType.MALLORN_PARTY.create(true, random);
				trunkPos = 1;
				trunkNeg = 1;
				xOffset = partyTree[0];
				zOffset = partyTree[1];
			}
			if (treeGen == null && (boughTree = LOTRBlockSaplingBase.findCrossShape(world, i, j, k, this, 1)) != null) {
				treeGen = LOTRTreeType.MALLORN_BOUGHS.create(true, random);
				trunkPos = 1;
				trunkNeg = 1;
				xOffset = boughTree[0];
				zOffset = boughTree[1];
				cross = true;
			}
			if (treeGen == null) {
				trunkPos = 0;
				trunkNeg = 0;
				xOffset = 0;
				zOffset = 0;
				treeGen = LOTRTreeType.MALLORN.create(true, random);
			}
		}
		if (meta == 2) {
			int[] partyTree = LOTRBlockSaplingBase.findPartyTree(world, i, j, k, this, 2);
			if (partyTree != null) {
				treeGen = LOTRTreeType.MIRK_OAK_LARGE.create(true, random);
				trunkPos = 1;
				trunkNeg = 1;
				xOffset = partyTree[0];
				zOffset = partyTree[1];
			}
			if (treeGen == null) {
				treeGen = LOTRTreeType.MIRK_OAK.create(true, random);
				trunkPos = 0;
				trunkNeg = 0;
				xOffset = 0;
				zOffset = 0;
			}
		}
		if (meta == 3) {
			int[] partyTree = LOTRBlockSaplingBase.findPartyTree(world, i, j, k, this, 3);
			if (partyTree != null) {
				treeGen = LOTRTreeType.RED_OAK_LARGE.create(true, random);
				trunkPos = 1;
				trunkNeg = 1;
				xOffset = partyTree[0];
				zOffset = partyTree[1];
			}
			if (treeGen == null) {
				treeGen = LOTRTreeType.RED_OAK.create(true, random);
				trunkPos = 0;
				trunkNeg = 0;
				xOffset = 0;
				zOffset = 0;
			}
		}
		for (i1 = -trunkNeg; i1 <= trunkPos; ++i1) {
			for (k1 = -trunkNeg; k1 <= trunkPos; ++k1) {
				if (cross && Math.abs(i1) != 0 && Math.abs(k1) != 0) {
					continue;
				}
				world.setBlock(i + xOffset + i1, j, k + zOffset + k1, Blocks.air, 0, 4);
			}
		}
		if (treeGen != null && !treeGen.generate(world, random, i + xOffset, j, k + zOffset)) {
			for (i1 = -trunkNeg; i1 <= trunkPos; ++i1) {
				for (k1 = -trunkNeg; k1 <= trunkPos; ++k1) {
					if (cross && Math.abs(i1) != 0 && Math.abs(k1) != 0) {
						continue;
					}
					world.setBlock(i + xOffset + i1, j, k + zOffset + k1, this, meta, 4);
				}
			}
		}
	}
}
