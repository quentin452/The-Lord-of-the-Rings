package lotr.common.world.structure2;

import java.util.*;

import lotr.common.LOTRMod;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenHaradRuinedFort extends LOTRWorldGenStructureBase2 {
	public LOTRWorldGenHaradRuinedFort(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int k1;
		int i1;
		setOriginAndRotation(world, i, j, k, rotation, 4);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -7; i1 <= 12; ++i1) {
				for (k1 = -3; k1 <= 4; ++k1) {
					j1 = getTopBlock(world, i1, k1) - 1;
					if (j1 >= -4) {
						continue;
					}
					return false;
				}
			}
		}
		if (usingPlayer == null) {
			originY -= 4 + random.nextInt(5);
		}
		for (i1 = -7; i1 <= 12; ++i1) {
			for (k1 = -3; k1 <= 4; ++k1) {
				j1 = -2;
				while (!isOpaque(world, i1, j1, k1) && getY(j1) >= 0) {
					if (random.nextInt(4) == 0) {
						setBlockAndMetadata(world, i1, j1, k1, LOTRMod.brick3, 11);
					} else {
						setBlockAndMetadata(world, i1, j1, k1, LOTRMod.brick, 15);
					}
					setGrassToDirt(world, i1, j1 - 1, k1);
					--j1;
				}
			}
		}
		loadStrScan("harad_ruined_fort");
		addBlockMetaAliasOption("BRICK", 3, LOTRMod.brick, 15);
		addBlockMetaAliasOption("BRICK", 1, LOTRMod.brick3, 11);
		addBlockMetaAliasOption("BRICK_SLAB", 3, LOTRMod.slabSingle4, 0);
		addBlockMetaAliasOption("BRICK_SLAB", 1, LOTRMod.slabSingle7, 1);
		addBlockMetaAliasOption("BRICK_SLAB_INV", 3, LOTRMod.slabSingle4, 8);
		addBlockMetaAliasOption("BRICK_SLAB_INV", 1, LOTRMod.slabSingle7, 9);
		addBlockAliasOption("BRICK_STAIR", 3, LOTRMod.stairsNearHaradBrick);
		addBlockAliasOption("BRICK_STAIR", 1, LOTRMod.stairsNearHaradBrickCracked);
		addBlockMetaAliasOption("BRICK_WALL", 3, LOTRMod.wall, 15);
		addBlockMetaAliasOption("BRICK_WALL", 1, LOTRMod.wall3, 3);
		addBlockMetaAliasOption("PILLAR", 4, LOTRMod.pillar, 5);
		generateStrScan(world, random, 0, 1, 0);
		List<int[]> chestCoords = new ArrayList<>();
		chestCoords.add(new int[]{3, 1, -2});
		chestCoords.add(new int[]{0, 1, -2});
		chestCoords.add(new int[]{-3, 1, -2});
		chestCoords.add(new int[]{-6, 1, -2});
		chestCoords.add(new int[]{8, 1, 0});
		chestCoords.add(new int[]{10, 1, 1});
		chestCoords.add(new int[]{11, 1, 3});
		chestCoords.add(new int[]{8, 1, 3});
		chestCoords.add(new int[]{6, 1, 3});
		chestCoords.add(new int[]{3, 1, 3});
		chestCoords.add(new int[]{0, 1, 3});
		chestCoords.add(new int[]{-3, 1, 3});
		chestCoords.add(new int[]{-6, 1, 3});
		chestCoords.add(new int[]{6, 2, -2});
		chestCoords.add(new int[]{6, 2, 0});
		chestCoords.add(new int[]{6, 6, -2});
		chestCoords.add(new int[]{-6, 6, -2});
		chestCoords.add(new int[]{-1, 6, -1});
		chestCoords.add(new int[]{8, 6, 0});
		chestCoords.add(new int[]{10, 6, 1});
		chestCoords.add(new int[]{0, 6, 1});
		chestCoords.add(new int[]{-2, 6, 1});
		chestCoords.add(new int[]{-6, 6, 1});
		chestCoords.add(new int[]{8, 6, 3});
		chestCoords.add(new int[]{0, 6, 3});
		chestCoords.add(new int[]{-2, 6, 3});
		chestCoords.add(new int[]{-6, 6, 3});
		int chests = 2 + random.nextInt(4);
		while (chestCoords.size() > chests) {
			chestCoords.remove(random.nextInt(chestCoords.size()));
		}
		for (int[] coords : chestCoords) {
			placeChest(world, random, coords[0], coords[1], coords[2], LOTRMod.chestBasket, MathHelper.getRandomIntegerInRange(random, 2, 4), LOTRChestContents.NEAR_HARAD_PYRAMID);
		}
		return true;
	}
}
