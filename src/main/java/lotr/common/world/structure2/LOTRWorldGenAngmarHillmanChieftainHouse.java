package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityAngmarHillmanChieftain;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenAngmarHillmanChieftainHouse extends LOTRWorldGenStructureBase2 {
	public Block woodBlock;
	public Block plankBlock;
	public Block slabBlock;
	public Block stairBlock;
	public Block fenceBlock;
	public Block doorBlock;
	public Block floorBlock;

	public LOTRWorldGenAngmarHillmanChieftainHouse(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int j12;
		int j13;
		int i1;
		int k1;
		int i12;
		int k12;
		int j14;
		int j15;
		int j16;
		int k13;
		int k14;
		setOriginAndRotation(world, i, j, k, rotation, 5);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i13 = -5; i13 <= 5; ++i13) {
				for (int k15 = -6; k15 <= 6; ++k15) {
					j13 = getTopBlock(world, i13, k15);
					Block block = getBlock(world, i13, j13 - 1, k15);
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
		fenceBlock = Blocks.fence;
		int fenceMeta = 0;
		doorBlock = LOTRMod.doorSpruce;
		floorBlock = Blocks.stained_hardened_clay;
		int floorMeta = 15;
		for (i12 = -5; i12 <= 5; ++i12) {
			for (k1 = -6; k1 <= 6; ++k1) {
				for (j12 = 1; j12 <= 10; ++j12) {
					setAir(world, i12, j12, k1);
				}
				for (j12 = 0; (j12 == 0 || !isOpaque(world, i12, j12, k1)) && getY(j12) >= 0; --j12) {
					if (getBlock(world, i12, j12 + 1, k1).isOpaqueCube()) {
						setBlockAndMetadata(world, i12, j12, k1, Blocks.dirt, 0);
					} else {
						setBlockAndMetadata(world, i12, j12, k1, Blocks.grass, 0);
					}
					setGrassToDirt(world, i12, j12 - 1, k1);
				}
			}
		}
		for (i12 = -4; i12 <= 4; ++i12) {
			for (k1 = -5; k1 <= 5; ++k1) {
				setBlockAndMetadata(world, i12, 0, k1, floorBlock, floorMeta);
				if (random.nextInt(2) != 0) {
					continue;
				}
				setBlockAndMetadata(world, i12, 1, k1, LOTRMod.thatchFloor, 0);
			}
		}
		for (int i14 : new int[]{-4, 4}) {
			for (k12 = -4; k12 <= 4; ++k12) {
				setBlockAndMetadata(world, i14, 1, k12, woodBlock, woodMeta | 8);
				setBlockAndMetadata(world, i14, 4, k12, woodBlock, woodMeta | 8);
			}
			for (j13 = 1; j13 <= 4; ++j13) {
				setBlockAndMetadata(world, i14, j13, -5, woodBlock, woodMeta);
				setBlockAndMetadata(world, i14, j13, 0, woodBlock, woodMeta);
				setBlockAndMetadata(world, i14, j13, 5, woodBlock, woodMeta);
			}
		}
		for (int i14 : new int[]{-3, 3}) {
			for (k12 = -4; k12 <= 4; ++k12) {
				setBlockAndMetadata(world, i14, 1, k12, plankBlock, plankMeta);
			}
			for (j13 = 2; j13 <= 3; ++j13) {
				setBlockAndMetadata(world, i14, j13, -4, plankBlock, plankMeta);
				setBlockAndMetadata(world, i14, j13, -1, plankBlock, plankMeta);
				setBlockAndMetadata(world, i14, j13, 1, plankBlock, plankMeta);
				setBlockAndMetadata(world, i14, j13, 4, plankBlock, plankMeta);
			}
			setBlockAndMetadata(world, i14, 3, -3, stairBlock, 7);
			setBlockAndMetadata(world, i14, 3, -2, stairBlock, 6);
			setBlockAndMetadata(world, i14, 3, 2, stairBlock, 7);
			setBlockAndMetadata(world, i14, 3, 3, stairBlock, 6);
			for (j13 = 1; j13 <= 5; ++j13) {
				setBlockAndMetadata(world, i14, j13, 0, woodBlock, woodMeta);
			}
			setBlockAndMetadata(world, i14, 1, -5, woodBlock, woodMeta | 4);
			setBlockAndMetadata(world, i14, 2, -5, stairBlock, 2);
			setBlockAndMetadata(world, i14, 3, -5, stairBlock, 6);
			setBlockAndMetadata(world, i14, 4, -5, slabBlock, slabMeta);
		}
		int[] i15 = {-2, 2};
		k1 = i15.length;
		for (j12 = 0; j12 < k1; ++j12) {
			int i14;
			i14 = i15[j12];
			for (j13 = 1; j13 <= 3; ++j13) {
				setBlockAndMetadata(world, i14, j13, -4, plankBlock, plankMeta);
				setBlockAndMetadata(world, i14, j13, -5, woodBlock, woodMeta);
			}
			setBlockAndMetadata(world, i14, 4, -5, slabBlock, slabMeta);
			setBlockAndMetadata(world, i14, 2, -6, Blocks.torch, 4);
			setBlockAndMetadata(world, i14, 3, -6, Blocks.skull, 2);
		}
		for (j15 = 1; j15 <= 3; ++j15) {
			setBlockAndMetadata(world, -1, j15, -4, woodBlock, woodMeta);
			setBlockAndMetadata(world, 1, j15, -4, woodBlock, woodMeta);
		}
		setBlockAndMetadata(world, -1, 2, -5, Blocks.torch, 4);
		setBlockAndMetadata(world, 1, 2, -5, Blocks.torch, 4);
		setBlockAndMetadata(world, -1, 3, -5, stairBlock, 4);
		setBlockAndMetadata(world, -1, 4, -5, stairBlock, 1);
		setBlockAndMetadata(world, 1, 3, -5, stairBlock, 5);
		setBlockAndMetadata(world, 1, 4, -5, stairBlock, 0);
		setBlockAndMetadata(world, 0, 1, -4, doorBlock, 1);
		setBlockAndMetadata(world, 0, 2, -4, doorBlock, 8);
		setBlockAndMetadata(world, 0, 3, -4, plankBlock, plankMeta);
		setBlockAndMetadata(world, 0, 3, -5, slabBlock, slabMeta | 8);
		for (i1 = -3; i1 <= 3; ++i1) {
			setBlockAndMetadata(world, i1, 4, -4, woodBlock, woodMeta | 4);
			setBlockAndMetadata(world, i1, 5, -5, stairBlock, 6);
		}
		setBlockAndMetadata(world, -2, 5, -4, Blocks.skull, 3);
		setBlockAndMetadata(world, 2, 5, -4, Blocks.skull, 3);
		for (i1 = -2; i1 <= 2; ++i1) {
			setBlockAndMetadata(world, i1, 6, -5, woodBlock, woodMeta | 4);
		}
		for (i1 = -1; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 7, -5, woodBlock, woodMeta | 4);
		}
		for (j15 = 4; j15 <= 9; ++j15) {
			setBlockAndMetadata(world, 0, j15, -5, woodBlock, woodMeta);
		}
		setBlockAndMetadata(world, 0, 9, -4, stairBlock, 7);
		setBlockAndMetadata(world, 0, 6, -6, Blocks.torch, 4);
		setBlockAndMetadata(world, 0, 5, -4, Blocks.torch, 3);
		placeWallBanner(world, 0, 5, -5, LOTRItemBanner.BannerType.ANGMAR, 2);
		placeWallBanner(world, -2, 5, -5, LOTRItemBanner.BannerType.RHUDAUR, 2);
		placeWallBanner(world, 2, 5, -5, LOTRItemBanner.BannerType.RHUDAUR, 2);
		for (i1 = -3; i1 <= 3; ++i1) {
			setBlockAndMetadata(world, i1, 1, 5, woodBlock, woodMeta | 4);
			setBlockAndMetadata(world, i1, 2, 5, stairBlock, 3);
			setBlockAndMetadata(world, i1, 3, 5, stairBlock, 7);
			setBlockAndMetadata(world, i1, 4, 5, woodBlock, woodMeta | 4);
		}
		setBlockAndMetadata(world, -3, 5, 5, plankBlock, plankMeta);
		setBlockAndMetadata(world, -2, 5, 5, plankBlock, plankMeta);
		setBlockAndMetadata(world, -1, 5, 5, slabBlock, slabMeta | 8);
		setBlockAndMetadata(world, 0, 5, 5, plankBlock, plankMeta);
		setBlockAndMetadata(world, 1, 5, 5, slabBlock, slabMeta | 8);
		setBlockAndMetadata(world, 2, 5, 5, plankBlock, plankMeta);
		setBlockAndMetadata(world, 3, 5, 5, plankBlock, plankMeta);
		for (i1 = -2; i1 <= 2; ++i1) {
			for (j1 = 6; j1 <= 7; ++j1) {
				setBlockAndMetadata(world, i1, j1, 5, plankBlock, plankMeta);
			}
		}
		for (int i14 : new int[]{-2, 2}) {
			for (j13 = 1; j13 <= 4; ++j13) {
				setBlockAndMetadata(world, i14, j13, 4, plankBlock, plankMeta);
			}
			setBlockAndMetadata(world, i14, 5, 4, fenceBlock, fenceMeta);
		}
		for (j14 = 4; j14 <= 5; ++j14) {
			setBlockAndMetadata(world, -3, j14, 4, plankBlock, plankMeta);
			setBlockAndMetadata(world, 3, j14, 4, plankBlock, plankMeta);
		}
		for (j14 = 7; j14 <= 9; ++j14) {
			setBlockAndMetadata(world, 0, j14, 5, woodBlock, woodMeta);
		}
		setBlockAndMetadata(world, 0, 9, 4, stairBlock, 6);
		setBlockAndMetadata(world, 0, 5, 4, Blocks.torch, 4);
		placeWallBanner(world, 0, 4, 5, LOTRItemBanner.BannerType.ANGMAR, 2);
		setBlockAndMetadata(world, -1, 4, 4, Blocks.skull, 2);
		setBlockAndMetadata(world, 1, 4, 4, Blocks.skull, 2);
		setBlockAndMetadata(world, 0, 3, 5, plankBlock, plankMeta);
		setBlockAndMetadata(world, 0, 3, 6, stairBlock, 7);
		setBlockAndMetadata(world, 0, 4, 6, woodBlock, woodMeta);
		setBlockAndMetadata(world, 0, 5, 6, woodBlock, woodMeta);
		setBlockAndMetadata(world, 0, 6, 6, stairBlock, 3);
		setBlockAndMetadata(world, -2, 5, 0, Blocks.torch, 2);
		placeWallBanner(world, -3, 3, 0, LOTRItemBanner.BannerType.RHUDAUR, 1);
		setBlockAndMetadata(world, 2, 5, 0, Blocks.torch, 1);
		placeWallBanner(world, 3, 3, 0, LOTRItemBanner.BannerType.RHUDAUR, 3);
		for (k13 = -3; k13 <= -1; ++k13) {
			setBlockAndMetadata(world, -3, 4, k13, stairBlock, 0);
			setBlockAndMetadata(world, 3, 4, k13, stairBlock, 1);
		}
		for (k13 = -4; k13 <= -1; ++k13) {
			setBlockAndMetadata(world, -3, 5, k13, stairBlock, 4);
			setBlockAndMetadata(world, 3, 5, k13, stairBlock, 5);
		}
		for (k13 = 1; k13 <= 3; ++k13) {
			setBlockAndMetadata(world, -3, 4, k13, stairBlock, 0);
			setBlockAndMetadata(world, 3, 4, k13, stairBlock, 1);
			setBlockAndMetadata(world, -3, 5, k13, stairBlock, 4);
			setBlockAndMetadata(world, 3, 5, k13, stairBlock, 5);
		}
		for (k13 = -6; k13 <= 6; ++k13) {
			setBlockAndMetadata(world, -5, 4, k13, slabBlock, slabMeta | 8);
			setBlockAndMetadata(world, -4, 5, k13, stairBlock, 1);
			setBlockAndMetadata(world, -3, 6, k13, stairBlock, 1);
			setBlockAndMetadata(world, -2, 7, k13, plankBlock, plankMeta);
			setBlockAndMetadata(world, -2, 8, k13, stairBlock, 1);
			setBlockAndMetadata(world, -1, 9, k13, plankBlock, plankMeta);
			setBlockAndMetadata(world, -1, 10, k13, stairBlock, 1);
			setBlockAndMetadata(world, 0, 10, k13, woodBlock, woodMeta | 8);
			setBlockAndMetadata(world, 1, 10, k13, stairBlock, 0);
			setBlockAndMetadata(world, 1, 9, k13, plankBlock, plankMeta);
			setBlockAndMetadata(world, 2, 8, k13, stairBlock, 0);
			setBlockAndMetadata(world, 2, 7, k13, plankBlock, plankMeta);
			setBlockAndMetadata(world, 3, 6, k13, stairBlock, 0);
			setBlockAndMetadata(world, 4, 5, k13, stairBlock, 0);
			setBlockAndMetadata(world, 5, 4, k13, slabBlock, slabMeta | 8);
		}
		for (int k15 : new int[]{-6, 6}) {
			setBlockAndMetadata(world, -4, 4, k15, slabBlock, slabMeta | 8);
			setBlockAndMetadata(world, -3, 5, k15, stairBlock, 4);
			setBlockAndMetadata(world, -2, 6, k15, stairBlock, 4);
			setBlockAndMetadata(world, -1, 7, k15, stairBlock, 4);
			setBlockAndMetadata(world, -1, 8, k15, plankBlock, plankMeta);
			setBlockAndMetadata(world, 1, 8, k15, plankBlock, plankMeta);
			setBlockAndMetadata(world, 1, 7, k15, stairBlock, 5);
			setBlockAndMetadata(world, 2, 6, k15, stairBlock, 5);
			setBlockAndMetadata(world, 3, 5, k15, stairBlock, 5);
			setBlockAndMetadata(world, 4, 4, k15, slabBlock, slabMeta | 8);
		}
		setBlockAndMetadata(world, 0, 11, -6, stairBlock, 3);
		setBlockAndMetadata(world, 0, 11, -7, stairBlock, 6);
		setBlockAndMetadata(world, 0, 12, -7, stairBlock, 3);
		setBlockAndMetadata(world, 0, 11, 6, stairBlock, 2);
		setBlockAndMetadata(world, 0, 11, 7, stairBlock, 7);
		setBlockAndMetadata(world, 0, 12, 7, stairBlock, 2);
		for (k14 = -1; k14 <= 1; ++k14) {
			setBlockAndMetadata(world, -1, 10, k14, plankBlock, plankMeta);
			setBlockAndMetadata(world, 1, 10, k14, plankBlock, plankMeta);
			setBlockAndMetadata(world, -1, 11, k14, stairBlock, 1);
			setBlockAndMetadata(world, 1, 11, k14, stairBlock, 0);
		}
		setBlockAndMetadata(world, 0, 11, -1, stairBlock, 2);
		setBlockAndMetadata(world, 0, 11, 1, stairBlock, 3);
		setAir(world, 0, 10, 0);
		for (int l = 0; l <= 2; ++l) {
			j1 = 4 + l * 2;
			setBlockAndMetadata(world, -4 + l, j1, 0, woodBlock, woodMeta);
			setBlockAndMetadata(world, -4 + l, j1 + 1, 0, woodBlock, woodMeta);
			setBlockAndMetadata(world, -4 + l, j1 + 2, 0, stairBlock, 1);
			setBlockAndMetadata(world, 4 - l, j1, 0, woodBlock, woodMeta);
			setBlockAndMetadata(world, 4 - l, j1 + 1, 0, woodBlock, woodMeta);
			setBlockAndMetadata(world, 4 - l, j1 + 2, 0, stairBlock, 0);
		}
		for (k14 = -4; k14 <= 4; ++k14) {
			setBlockAndMetadata(world, -2, 6, k14, stairBlock, 4);
			setBlockAndMetadata(world, 2, 6, k14, stairBlock, 5);
		}
		for (k14 = -3; k14 <= 3; ++k14) {
			setBlockAndMetadata(world, -1, 8, k14, stairBlock, 4);
			setBlockAndMetadata(world, 1, 8, k14, stairBlock, 5);
		}
		for (int i14 : new int[]{-1, 1}) {
			setBlockAndMetadata(world, i14, 8, -5, plankBlock, plankMeta);
			setBlockAndMetadata(world, i14, 8, -4, plankBlock, plankMeta);
			setBlockAndMetadata(world, i14, 8, 4, plankBlock, plankMeta);
			setBlockAndMetadata(world, i14, 8, 5, plankBlock, plankMeta);
		}
		setBlockAndMetadata(world, -1, 7, -4, stairBlock, 4);
		setBlockAndMetadata(world, 1, 7, -4, stairBlock, 5);
		setBlockAndMetadata(world, -1, 7, 4, stairBlock, 4);
		setBlockAndMetadata(world, 1, 7, 4, stairBlock, 5);
		for (j16 = 0; j16 >= -5; --j16) {
			for (int i16 = -1; i16 <= 1; ++i16) {
				for (int k16 = -1; k16 <= 1; ++k16) {
					if (i16 == 0 && k16 == 0) {
						setAir(world, 0, j16, 0);
						continue;
					}
					setBlockAndMetadata(world, i16, j16, k16, Blocks.cobblestone, 0);
				}
			}
		}
		setBlockAndMetadata(world, 0, -6, 0, LOTRMod.hearth, 0);
		setBlockAndMetadata(world, 0, -5, 0, Blocks.fire, 0);
		setBlockAndMetadata(world, 0, 0, 0, LOTRMod.bronzeBars, 0);
		setAir(world, 0, 1, 0);
		setBlockAndMetadata(world, 0, 1, 3, LOTRMod.strawBed, 0);
		setBlockAndMetadata(world, 0, 1, 4, LOTRMod.strawBed, 8);
		for (j16 = 1; j16 <= 2; ++j16) {
			setBlockAndMetadata(world, -1, j16, 4, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, 1, j16, 4, fenceBlock, fenceMeta);
		}
		setBlockAndMetadata(world, -2, 1, 3, LOTRMod.angmarTable, 0);
		setBlockAndMetadata(world, -2, 1, 2, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, 2, 1, 3, Blocks.furnace, 5);
		placeChest(world, random, 2, 1, 2, 5, LOTRChestContents.ANGMAR_HILLMAN_HOUSE);
		LOTREntityAngmarHillmanChieftain chieftain = new LOTREntityAngmarHillmanChieftain(world);
		spawnNPCAndSetHome(chieftain, world, 0, 1, 0, 8);
		return true;
	}
}
