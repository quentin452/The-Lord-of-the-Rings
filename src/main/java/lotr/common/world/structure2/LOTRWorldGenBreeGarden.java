package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenBreeGarden extends LOTRWorldGenBreeStructure {
	public LOTRWorldGenBreeGarden(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int i1;
		int j1;
		int k1;
		setOriginAndRotation(world, i, j, k, rotation, 8);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -6; i1 <= 6; ++i1) {
				for (k1 = -3; k1 <= 3; ++k1) {
					j1 = getTopBlock(world, i1, k1) - 1;
					if (isSurface(world, i1, j1, k1)) {
						continue;
					}
					return false;
				}
			}
			for (i1 = -3; i1 <= 3; ++i1) {
				for (k1 = -8; k1 <= 4; ++k1) {
					j1 = getTopBlock(world, i1, k1) - 1;
					if (isSurface(world, i1, j1, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -6; i1 <= 6; ++i1) {
			for (k1 = -3; k1 <= 3; ++k1) {
				for (j1 = 1; j1 <= 5; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			k1 = -4;
			for (j1 = 1; j1 <= 5; ++j1) {
				setAir(world, i1, j1, k1);
			}
		}
		for (i1 = -2; i1 <= 2; ++i1) {
			for (k1 = -8; k1 <= -5; ++k1) {
				for (j1 = 1; j1 <= 5; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("bree_garden");
		addBlockMetaAliasOption("COBBLE", 3, Blocks.cobblestone, 0);
		addBlockMetaAliasOption("COBBLE", 1, Blocks.mossy_cobblestone, 0);
		addBlockAliasOption("COBBLE_STAIR", 3, Blocks.stone_stairs);
		addBlockAliasOption("COBBLE_STAIR", 1, LOTRMod.stairsCobblestoneMossy);
		addBlockMetaAliasOption("COBBLE_WALL", 3, Blocks.cobblestone_wall, 0);
		addBlockMetaAliasOption("COBBLE_WALL", 1, Blocks.cobblestone_wall, 1);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		addBlockMetaAliasOption("LEAF", 1, Blocks.leaves, 4);
		addBlockMetaAliasOption("LEAF", 1, LOTRMod.leaves4, 4);
		addBlockMetaAliasOption("LEAF", 1, LOTRMod.leaves2, 5);
		addBlockMetaAliasOption("LEAF", 1, LOTRMod.leaves7, 4);
		addBlockMetaAliasOption("PATH", 14, Blocks.grass, 0);
		addBlockMetaAliasOption("PATH", 3, Blocks.cobblestone, 0);
		addBlockMetaAliasOption("PATH", 3, Blocks.cobblestone, 1);
		generateStrScan(world, random, 0, 0, 0);
		for (i1 = -5; i1 <= 5; ++i1) {
			for (k1 = -3; k1 <= 2; ++k1) {
				j1 = 1;
				Block below = getBlock(world, i1, 0, k1);
				if (below != Blocks.grass || !isAir(world, i1, j1, k1) || random.nextInt(5) != 0) {
					continue;
				}
				plantFlower(world, random, i1, j1, k1);
			}
		}
		return true;
	}
}
