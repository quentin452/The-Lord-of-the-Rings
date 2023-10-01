package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityRangerNorth;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenRangerWatchtower extends LOTRWorldGenStructureBase2 {
	public Block woodBlock;
	public int woodMeta;
	public Block plankBlock;
	public int plankMeta;
	public Block fenceBlock;
	public int fenceMeta;
	public Block stairBlock;
	public Block trapdoorBlock;

	public LOTRWorldGenRangerWatchtower(boolean flag) {
		super(flag);
	}

	public void generateSupportPillar(World world, int i, int j, int k) {
		int j1 = j;
		while (!isOpaque(world, i, j1, k) && getY(j1) >= 0) {
			setBlockAndMetadata(world, i, j1, k, woodBlock, woodMeta);
			setGrassToDirt(world, i, j1 - 1, i);
			--j1;
		}
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int i1;
		int k1;
		int randomWood;
		int i12;
		setOriginAndRotation(world, i, j, k, rotation, 0);
		if (restrictions) {
			for (int i13 = -4; i13 <= 4; ++i13) {
				for (int k12 = -4; k12 <= 4; ++k12) {
					int j12 = getTopBlock(world, i13, k12);
					Block block = getBlock(world, i13, j12 - 1, k12);
					if (block == Blocks.grass) {
						continue;
					}
					return false;
				}
			}
		}
		randomWood = random.nextInt(4);
		switch (randomWood) {
			case 0:
				woodBlock = Blocks.log;
				woodMeta = 0;
				plankBlock = Blocks.planks;
				plankMeta = 0;
				fenceBlock = Blocks.fence;
				fenceMeta = 0;
				stairBlock = Blocks.oak_stairs;
				trapdoorBlock = Blocks.trapdoor;
				break;
			case 1:
				woodBlock = Blocks.log;
				woodMeta = 1;
				plankBlock = Blocks.planks;
				plankMeta = 1;
				fenceBlock = Blocks.fence;
				fenceMeta = 0;
				stairBlock = Blocks.spruce_stairs;
				trapdoorBlock = LOTRMod.trapdoorSpruce;
				break;
			case 2:
				woodBlock = LOTRMod.wood2;
				woodMeta = 1;
				plankBlock = LOTRMod.planks;
				plankMeta = 9;
				fenceBlock = LOTRMod.fence;
				fenceMeta = 9;
				stairBlock = LOTRMod.stairsBeech;
				trapdoorBlock = LOTRMod.trapdoorBeech;
				break;
			case 3:
				woodBlock = LOTRMod.wood3;
				woodMeta = 0;
				plankBlock = LOTRMod.planks;
				plankMeta = 12;
				fenceBlock = LOTRMod.fence;
				fenceMeta = 12;
				stairBlock = LOTRMod.stairsMaple;
				trapdoorBlock = LOTRMod.trapdoorMaple;
				break;
			default:
				break;
		}
		generateSupportPillar(world, -3, 4, -3);
		generateSupportPillar(world, -3, 4, 3);
		generateSupportPillar(world, 3, 4, -3);
		generateSupportPillar(world, 3, 4, 3);
		for (i12 = -2; i12 <= 2; ++i12) {
			for (k1 = -2; k1 <= 2; ++k1) {
				for (int j13 = 5; j13 <= 19; ++j13) {
					setAir(world, i12, j13, k1);
				}
			}
		}
		for (j1 = 6; j1 <= 19; ++j1) {
			setBlockAndMetadata(world, -2, j1, -2, woodBlock, woodMeta);
			setBlockAndMetadata(world, -2, j1, 2, woodBlock, woodMeta);
			setBlockAndMetadata(world, 2, j1, -2, woodBlock, woodMeta);
			setBlockAndMetadata(world, 2, j1, 2, woodBlock, woodMeta);
		}
		for (j1 = 5; j1 <= 10; j1 += 5) {
			for (i1 = -3; i1 <= 3; ++i1) {
				for (int k13 = -3; k13 <= 3; ++k13) {
					setBlockAndMetadata(world, i1, j1, k13, plankBlock, plankMeta);
				}
			}
			for (i1 = -4; i1 <= 4; ++i1) {
				setBlockAndMetadata(world, i1, j1, -4, stairBlock, 2);
				setBlockAndMetadata(world, i1, j1, 4, stairBlock, 3);
			}
			for (k1 = -3; k1 <= 3; ++k1) {
				setBlockAndMetadata(world, -4, j1, k1, stairBlock, 1);
				setBlockAndMetadata(world, 4, j1, k1, stairBlock, 0);
			}
			for (i1 = -2; i1 <= 2; ++i1) {
				setBlockAndMetadata(world, i1, j1 + 1, -3, fenceBlock, fenceMeta);
				setBlockAndMetadata(world, i1, j1 + 1, 3, fenceBlock, fenceMeta);
			}
			for (k1 = -2; k1 <= 2; ++k1) {
				setBlockAndMetadata(world, -3, j1 + 1, k1, fenceBlock, fenceMeta);
				setBlockAndMetadata(world, 3, j1 + 1, k1, fenceBlock, fenceMeta);
			}
			setBlockAndMetadata(world, 0, j1 + 2, -3, Blocks.torch, 5);
			setBlockAndMetadata(world, 0, j1 + 2, 3, Blocks.torch, 5);
			setBlockAndMetadata(world, -3, j1 + 2, 0, Blocks.torch, 5);
			setBlockAndMetadata(world, 3, j1 + 2, 0, Blocks.torch, 5);
			spawnNPCAndSetHome(new LOTREntityRangerNorth(world), world, -1, j1 + 1, 0, 8);
		}
		for (i12 = -2; i12 <= 2; ++i12) {
			for (k1 = -2; k1 <= 2; ++k1) {
				int i2 = Math.abs(i12);
				int k2 = Math.abs(k1);
				if (i2 >= 2 && k2 >= 2) {
					continue;
				}
				setBlockAndMetadata(world, i12, 15, k1, plankBlock, plankMeta);
				if ((i2 >= 2 || k2 != 2) && i2 != 2) {
					continue;
				}
				setBlockAndMetadata(world, i12, 16, k1, fenceBlock, fenceMeta);
			}
		}
		setGrassToDirt(world, 0, 0, 0);
		for (j1 = 1; j1 <= 25; ++j1) {
			setBlockAndMetadata(world, 0, j1, 0, woodBlock, woodMeta);
			if (j1 > 15) {
				continue;
			}
			setBlockAndMetadata(world, 0, j1, -1, Blocks.ladder, 2);
		}
		setBlockAndMetadata(world, 0, 6, -1, trapdoorBlock, 0);
		setBlockAndMetadata(world, 0, 11, -1, trapdoorBlock, 0);
		setBlockAndMetadata(world, 0, 17, -2, Blocks.torch, 5);
		setBlockAndMetadata(world, 0, 17, 2, Blocks.torch, 5);
		setBlockAndMetadata(world, -2, 17, 0, Blocks.torch, 5);
		setBlockAndMetadata(world, 2, 17, 0, Blocks.torch, 5);
		placeChest(world, random, 0, 16, 1, 0, LOTRChestContents.RANGER_TENT);
		setBlockAndMetadata(world, 0, 11, 1, LOTRMod.rangerTable, 0);
		for (j1 = 17; j1 <= 18; ++j1) {
			setBlockAndMetadata(world, -2, j1, -2, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, -2, j1, 2, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, 2, j1, -2, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, 2, j1, 2, fenceBlock, fenceMeta);
		}
		for (int step = 0; step <= 1; ++step) {
			for (i1 = -2 + step; i1 <= 2 - step; ++i1) {
				setBlockAndMetadata(world, i1, 20 + step, -2 + step, stairBlock, 2);
				setBlockAndMetadata(world, i1, 20 + step, 2 - step, stairBlock, 3);
			}
			for (k1 = -1 + step; k1 <= 1 - step; ++k1) {
				setBlockAndMetadata(world, -2 + step, 20 + step, k1, stairBlock, 1);
				setBlockAndMetadata(world, 2 - step, 20 + step, k1, stairBlock, 0);
			}
		}
		placeWallBanner(world, -2, 15, 0, LOTRItemBanner.BannerType.RANGER_NORTH, 3);
		placeWallBanner(world, 2, 15, 0, LOTRItemBanner.BannerType.RANGER_NORTH, 1);
		placeWallBanner(world, 0, 15, -2, LOTRItemBanner.BannerType.RANGER_NORTH, 2);
		placeWallBanner(world, 0, 15, 2, LOTRItemBanner.BannerType.RANGER_NORTH, 0);
		for (j1 = 24; j1 <= 25; ++j1) {
			setBlockAndMetadata(world, 1, j1, 0, Blocks.wool, 13);
			setBlockAndMetadata(world, 2, j1, 1, Blocks.wool, 13);
			setBlockAndMetadata(world, 2, j1, 2, Blocks.wool, 13);
			setBlockAndMetadata(world, 3, j1, 3, Blocks.wool, 13);
		}
		LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
		respawner.setSpawnClass(LOTREntityRangerNorth.class);
		respawner.setCheckRanges(24, -12, 20, 8);
		respawner.setSpawnRanges(4, -4, 4, 16);
		placeNPCRespawner(respawner, world, 0, 0, 0);
		return true;
	}
}
