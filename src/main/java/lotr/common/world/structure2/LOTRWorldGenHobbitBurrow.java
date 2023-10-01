package lotr.common.world.structure2;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenHobbitBurrow extends LOTRWorldGenHobbitStructure {
	public LOTRChestContents burrowLoot;
	public LOTRFoods foodPool;

	public LOTRWorldGenHobbitBurrow(boolean flag) {
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
			for (i1 = -9; i1 <= 9; ++i1) {
				for (k1 = -7; k1 <= 8; ++k1) {
					j1 = getTopBlock(world, i1, k1) - 1;
					if (isSurface(world, i1, j1, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			for (k1 = -7; k1 <= 3; ++k1) {
				for (j1 = 1; j1 <= 3; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("hobbit_burrow");
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("FLOOR", floorBlock, floorMeta);
		associateBlockMetaAlias("COBBLE_WALL", Blocks.cobblestone_wall, 0);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		associateBlockAlias("DOOR", doorBlock);
		associateBlockMetaAlias("BEAM", beamBlock, beamMeta);
		associateBlockMetaAlias("BEAM|4", beamBlock, beamMeta | 4);
		associateBlockMetaAlias("BEAM|8", beamBlock, beamMeta | 8);
		associateBlockMetaAlias("TABLE", tableBlock, 0);
		associateBlockMetaAlias("CARPET", carpetBlock, carpetMeta);
		addBlockMetaAliasOption("THATCH_FLOOR", 1, LOTRMod.thatchFloor, 0);
		setBlockAliasChance("THATCH_FLOOR", 0.33f);
		associateBlockMetaAlias("LEAF", Blocks.leaves, 4);
		generateStrScan(world, random, 0, 0, 0);
		setBlockAndMetadata(world, -2, 1, -2, bedBlock, 3);
		setBlockAndMetadata(world, -3, 1, -2, bedBlock, 11);
		setBlockAndMetadata(world, -2, 1, -1, bedBlock, 3);
		setBlockAndMetadata(world, -3, 1, -1, bedBlock, 11);
		placeChest(world, random, -3, 1, 0, 4, burrowLoot, MathHelper.getRandomIntegerInRange(random, 1, 3));
		placeChest(world, random, 1, 1, 2, 2, burrowLoot, MathHelper.getRandomIntegerInRange(random, 1, 3));
		placeChest(world, random, 0, 1, 2, 2, burrowLoot, MathHelper.getRandomIntegerInRange(random, 1, 3));
		placePlateWithCertainty(world, random, 3, 2, -1, plateBlock, foodPool);
		placeRandomFlowerPot(world, random, -3, 2, -5);
		placeRandomFlowerPot(world, random, -1, 4, -4);
		placeSign(world, 0, 2, -4, Blocks.wall_sign, 2, new String[]{"", homeName1, homeName2, ""});
		for (i1 = -8; i1 <= 8; ++i1) {
			for (k1 = -6; k1 <= 8; ++k1) {
				j1 = getTopBlock(world, i1, k1);
				if (j1 < 1 || !isAir(world, i1, j1, k1) || getBlock(world, i1, j1 - 1, k1) != Blocks.grass) {
					continue;
				}
				if (random.nextInt(20) == 0) {
					plantFlower(world, random, i1, j1, k1);
					continue;
				}
				if (random.nextInt(7) != 0) {
					continue;
				}
				plantTallGrass(world, random, i1, j1, k1);
			}
		}
		spawnHobbitCouple(world, 0, 1, 0, 16);
		spawnItemFrame(world, -4, 2, 0, 1, getRandomHobbitDecoration(world, random));
		return true;
	}

	@Override
	public boolean makeWealthy(Random random) {
		return false;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		bedBlock = LOTRMod.strawBed;
		burrowLoot = LOTRChestContents.HOBBIT_HOLE_LARDER;
		foodPool = LOTRFoods.HOBBIT;
	}
}
