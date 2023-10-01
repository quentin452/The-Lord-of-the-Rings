package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityAngmarHillman;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenAngmarHillmanHouse extends LOTRWorldGenStructureBase2 {
	public Block woodBlock;
	public Block plankBlock;
	public Block slabBlock;
	public Block stairBlock;
	public Block doorBlock;
	public Block floorBlock;

	public LOTRWorldGenAngmarHillmanHouse(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int i1;
		int j1;
		int i12;
		int j12;
		setOriginAndRotation(world, i, j, k, rotation, 5);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i13 = -4; i13 <= 4; ++i13) {
				for (int k12 = -6; k12 <= 6; ++k12) {
					int j13 = getTopBlock(world, i13, k12);
					Block block = getBlock(world, i13, j13 - 1, k12);
					if (block != Blocks.grass && block != Blocks.stone) {
						return false;
					}
					if (j13 < minHeight) {
						minHeight = j13;
					}
					if (j13 > maxHeight) {
						maxHeight = j13;
					}
					if (maxHeight - minHeight <= 4) {
						continue;
					}
					return false;
				}
			}
		}
		woodBlock = Blocks.log;
		int woodMeta = 1;
		plankBlock = Blocks.planks;
		int plankMeta = 1;
		slabBlock = Blocks.wooden_slab;
		int slabMeta = 1;
		stairBlock = Blocks.spruce_stairs;
		doorBlock = LOTRMod.doorSpruce;
		floorBlock = Blocks.stained_hardened_clay;
		int floorMeta = 15;
		for (i12 = -4; i12 <= 4; ++i12) {
			for (k1 = -6; k1 <= 6; ++k1) {
				for (j1 = 1; j1 <= 7; ++j1) {
					setAir(world, i12, j1, k1);
				}
				for (j1 = 0; (j1 == 0 || !isOpaque(world, i12, j1, k1)) && getY(j1) >= 0; --j1) {
					if (getBlock(world, i12, j1 + 1, k1).isOpaqueCube()) {
						setBlockAndMetadata(world, i12, j1, k1, Blocks.dirt, 0);
					} else {
						setBlockAndMetadata(world, i12, j1, k1, Blocks.grass, 0);
					}
					setGrassToDirt(world, i12, j1 - 1, k1);
				}
			}
		}
		for (i12 = -3; i12 <= 3; ++i12) {
			for (k1 = -5; k1 <= 5; ++k1) {
				setBlockAndMetadata(world, i12, 0, k1, floorBlock, floorMeta);
				if (random.nextInt(2) != 0) {
					continue;
				}
				setBlockAndMetadata(world, i12, 1, k1, LOTRMod.thatchFloor, 0);
			}
		}
		for (int j14 = 1; j14 <= 4; ++j14) {
			setBlockAndMetadata(world, -3, j14, -5, woodBlock, woodMeta);
			setBlockAndMetadata(world, 3, j14, -5, woodBlock, woodMeta);
			setBlockAndMetadata(world, -3, j14, 5, woodBlock, woodMeta);
			setBlockAndMetadata(world, 3, j14, 5, woodBlock, woodMeta);
		}
		for (int j15 : new int[]{1, 4}) {
			for (i1 = -2; i1 <= 2; ++i1) {
				setBlockAndMetadata(world, i1, j15, -5, woodBlock, woodMeta | 4);
				setBlockAndMetadata(world, i1, j15, 5, woodBlock, woodMeta | 4);
			}
			for (int k13 = -4; k13 <= 4; ++k13) {
				setBlockAndMetadata(world, -3, j15, k13, woodBlock, woodMeta | 8);
				setBlockAndMetadata(world, 3, j15, k13, woodBlock, woodMeta | 8);
			}
		}
		for (int k14 = -4; k14 <= 4; ++k14) {
			setBlockAndMetadata(world, -3, 2, k14, stairBlock, 1);
			setBlockAndMetadata(world, 3, 2, k14, stairBlock, 0);
			setBlockAndMetadata(world, -3, 3, k14, stairBlock, 5);
			setBlockAndMetadata(world, 3, 3, k14, stairBlock, 4);
		}
		int[] k14 = {-3, 3};
		k1 = k14.length;
		for (j1 = 0; j1 < k1; ++j1) {
			int i14 = k14[j1];
			setBlockAndMetadata(world, i14, 2, 0, slabBlock, slabMeta);
			setBlockAndMetadata(world, i14, 3, 0, slabBlock, slabMeta | 8);
		}
		for (int k15 = -5; k15 <= 5; ++k15) {
			for (int l = 0; l <= 3; ++l) {
				setBlockAndMetadata(world, -4 + l, 4 + l, k15, stairBlock, 1);
				setBlockAndMetadata(world, 4 - l, 4 + l, k15, stairBlock, 0);
			}
			setBlockAndMetadata(world, 0, 7, k15, plankBlock, plankMeta);
		}
		for (int k12 : new int[]{-5, 5}) {
			for (i1 = -2; i1 <= 2; ++i1) {
				setBlockAndMetadata(world, i1, 4, k12, woodBlock, woodMeta | 4);
				setBlockAndMetadata(world, i1, 5, k12, woodBlock, woodMeta | 4);
			}
			for (i1 = -1; i1 <= 1; ++i1) {
				setBlockAndMetadata(world, i1, 6, k12, woodBlock, woodMeta | 4);
			}
		}
		for (int i15 = -2; i15 <= 2; ++i15) {
			setBlockAndMetadata(world, i15, 2, 5, stairBlock, 3);
			setBlockAndMetadata(world, i15, 3, 5, stairBlock, 7);
		}
		setBlockAndMetadata(world, 0, 3, 5, plankBlock, plankMeta);
		setBlockAndMetadata(world, 0, 3, 6, stairBlock, 7);
		for (j12 = 4; j12 <= 8; ++j12) {
			setBlockAndMetadata(world, 0, j12, 6, woodBlock, woodMeta);
		}
		setBlockAndMetadata(world, 0, 8, 5, stairBlock, 2);
		setBlockAndMetadata(world, 0, 9, 6, stairBlock, 2);
		setBlockAndMetadata(world, 0, 1, -5, doorBlock, 1);
		setBlockAndMetadata(world, 0, 2, -5, doorBlock, 8);
		setBlockAndMetadata(world, 0, 3, -5, plankBlock, plankMeta);
		setBlockAndMetadata(world, -2, 2, -5, stairBlock, 2);
		setBlockAndMetadata(world, -2, 3, -5, stairBlock, 6);
		setBlockAndMetadata(world, -1, 2, -5, stairBlock, 1);
		setBlockAndMetadata(world, -1, 3, -5, stairBlock, 5);
		setBlockAndMetadata(world, 1, 2, -5, stairBlock, 0);
		setBlockAndMetadata(world, 1, 3, -5, stairBlock, 4);
		setBlockAndMetadata(world, 2, 2, -5, stairBlock, 2);
		setBlockAndMetadata(world, 2, 3, -5, stairBlock, 6);
		setBlockAndMetadata(world, 0, 3, -6, stairBlock, 6);
		for (j12 = 4; j12 <= 8; ++j12) {
			setBlockAndMetadata(world, 0, j12, -6, woodBlock, woodMeta);
		}
		setBlockAndMetadata(world, 0, 8, -5, stairBlock, 3);
		setBlockAndMetadata(world, 0, 9, -6, stairBlock, 3);
		setBlockAndMetadata(world, -1, 5, -6, Blocks.torch, 1);
		setBlockAndMetadata(world, 1, 5, -6, Blocks.torch, 2);
		for (int k16 = -4; k16 <= 4; ++k16) {
			setBlockAndMetadata(world, -2, 1, k16, slabBlock, slabMeta | 8);
			setBlockAndMetadata(world, 2, 1, k16, slabBlock, slabMeta | 8);
		}
		setBlockAndMetadata(world, -2, 3, -4, Blocks.torch, 3);
		setBlockAndMetadata(world, 2, 3, -4, Blocks.torch, 3);
		setBlockAndMetadata(world, -2, 3, 4, Blocks.torch, 4);
		setBlockAndMetadata(world, 2, 3, 4, Blocks.torch, 4);
		setBlockAndMetadata(world, 0, 1, 3, LOTRMod.strawBed, 0);
		setBlockAndMetadata(world, 0, 1, 4, LOTRMod.strawBed, 8);
		setBlockAndMetadata(world, -1, 1, 4, Blocks.crafting_table, 0);
		placeChest(world, random, 1, 1, 4, 2, LOTRChestContents.ANGMAR_HILLMAN_HOUSE);
		placeWallBanner(world, 0, 4, 5, LOTRItemBanner.BannerType.RHUDAUR, 2);
		setBlockAndMetadata(world, -1, 3, 4, Blocks.skull, 2);
		setBlockAndMetadata(world, 1, 3, 4, Blocks.skull, 2);
		LOTREntityAngmarHillman hillman = new LOTREntityAngmarHillman(world);
		spawnNPCAndSetHome(hillman, world, 0, 1, 0, 8);
		return true;
	}
}
