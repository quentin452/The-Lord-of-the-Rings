package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenHobbitHouse extends LOTRWorldGenHobbitStructure {
	public LOTRWorldGenHobbitHouse(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int k1;
		int i1;
		int i12;
		int j12;
		int k12;
		int k13;
		setOriginAndRotation(world, i, j, k, rotation, 11, 1);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i13 = -9; i13 <= 8; ++i13) {
				for (int k14 = -10; k14 <= 6; ++k14) {
					int j13 = getTopBlock(world, i13, k14) - 1;
					if (!isSurface(world, i13, j13, k14)) {
						return false;
					}
					if (j13 < minHeight) {
						minHeight = j13;
					}
					if (j13 <= maxHeight) {
						continue;
					}
					maxHeight = j13;
				}
			}
			if (maxHeight - minHeight > 6) {
				return false;
			}
		}
		for (i12 = -4; i12 <= 3; ++i12) {
			for (k1 = -10; k1 <= -6; ++k1) {
				int j14;
				for (j14 = 0; (j14 >= 0 || !isOpaque(world, i12, j14, k1)) && getY(j14) >= 0; --j14) {
					if (j14 == 0) {
						setBlockAndMetadata(world, i12, 0, k1, Blocks.grass, 0);
					} else {
						setBlockAndMetadata(world, i12, j14, k1, Blocks.dirt, 0);
					}
					setGrassToDirt(world, i12, j14 - 1, k1);
				}
				for (j14 = 1; j14 <= 5; ++j14) {
					setAir(world, i12, j14, k1);
				}
				if (i12 != -4 && i12 != 3 && k1 != -10) {
					continue;
				}
				setBlockAndMetadata(world, i12, 1, k1, outFenceBlock, outFenceMeta);
			}
		}
		for (i12 = -9; i12 <= 8; ++i12) {
			for (k1 = -6; k1 <= 6; ++k1) {
				int j15;
				int k2 = Math.abs(k1);
				boolean beam = false;
				boolean wall = false;
				boolean indoors = false;
				if ((i12 == -7 || i12 == 6) && k2 == 4) {
					beam = true;
				} else if ((i12 == -9 || i12 == 8) && k2 == 0) {
					beam = true;
				} else if ((i12 == -3 || i12 == 2) && k2 == 6) {
					beam = true;
				} else if (i12 >= -6 && i12 <= 5 && k2 <= 5 || k2 <= 3 && i12 >= -8 && i12 <= 7) {
					if (i12 == -8 || i12 == 7 || k2 == 5) {
						wall = true;
					} else {
						indoors = true;
					}
				}
				if (!beam && !wall && !indoors) {
					continue;
				}
				for (j15 = 1; j15 <= 6; ++j15) {
					setAir(world, i12, j15, k1);
				}
				if (beam) {
					for (j15 = 2; (j15 >= 0 || !isOpaque(world, i12, j15, k1)) && getY(j15) >= 0; --j15) {
						setBlockAndMetadata(world, i12, j15, k1, beamBlock, beamMeta);
						setGrassToDirt(world, i12, j15 - 1, k1);
					}
					continue;
				}
				if (wall) {
					for (j15 = 0; (j15 >= 0 || !isOpaque(world, i12, j15, k1)) && getY(j15) >= 0; --j15) {
						setBlockAndMetadata(world, i12, j15, k1, plankBlock, plankMeta);
						setGrassToDirt(world, i12, j15 - 1, k1);
					}
					for (j15 = 1; j15 <= 2; ++j15) {
						setBlockAndMetadata(world, i12, j15, k1, wallBlock, wallMeta);
					}
					continue;
				}
				if (!indoors) {
					continue;
				}
				for (j15 = 0; (j15 >= 0 || !isOpaque(world, i12, j15, k1)) && getY(j15) >= 0; --j15) {
					setBlockAndMetadata(world, i12, j15, k1, floorBlock, floorMeta);
					setGrassToDirt(world, i12, j15 - 1, k1);
				}
				setBlockAndMetadata(world, i12, 3, k1, plankSlabBlock, plankSlabMeta | 8);
			}
		}
		for (i12 = -2; i12 <= 1; ++i12) {
			for (j1 = 1; j1 <= 3; ++j1) {
				setBlockAndMetadata(world, i12, j1, -5, Blocks.brick_block, 0);
			}
		}
		for (i12 = -1; i12 <= 0; ++i12) {
			setBlockAndMetadata(world, i12, 0, -5, floorBlock, floorMeta);
			for (j1 = 1; j1 <= 2; ++j1) {
				setBlockAndMetadata(world, i12, j1, -5, gateBlock, 2);
			}
		}
		setBlockAndMetadata(world, 1, 2, -4, Blocks.tripwire_hook, 0);
		for (j12 = 1; j12 <= 3; ++j12) {
			setBlockAndMetadata(world, -3, j12, -4, beamBlock, beamMeta);
			setBlockAndMetadata(world, -7, j12, 0, beamBlock, beamMeta);
			for (int i14 = -6; i14 <= -4; ++i14) {
				setBlockAndMetadata(world, i14, j12, 0, plankBlock, plankMeta);
			}
			setBlockAndMetadata(world, -3, j12, 0, beamBlock, beamMeta);
			setBlockAndMetadata(world, -3, j12, 4, beamBlock, beamMeta);
			setBlockAndMetadata(world, 2, j12, 4, beamBlock, beamMeta);
			setBlockAndMetadata(world, 6, j12, 0, beamBlock, beamMeta);
			setBlockAndMetadata(world, 2, j12, -4, beamBlock, beamMeta);
		}
		setBlockAndMetadata(world, -2, 3, -4, plankBlock, plankMeta);
		setBlockAndMetadata(world, -3, 3, -3, plankBlock, plankMeta);
		for (i12 = -6; i12 <= -4; ++i12) {
			setBlockAndMetadata(world, i12, 3, -4, plankBlock, plankMeta);
		}
		for (k12 = -3; k12 <= -1; ++k12) {
			setBlockAndMetadata(world, -7, 3, k12, plankBlock, plankMeta);
		}
		for (k12 = 1; k12 <= 3; ++k12) {
			setBlockAndMetadata(world, -7, 3, k12, plankBlock, plankMeta);
		}
		for (i12 = -6; i12 <= -4; ++i12) {
			setBlockAndMetadata(world, i12, 3, 4, plankBlock, plankMeta);
		}
		for (k12 = 1; k12 <= 3; ++k12) {
			setBlockAndMetadata(world, -3, 3, k12, plankBlock, plankMeta);
		}
		for (i12 = -2; i12 <= 1; ++i12) {
			setBlockAndMetadata(world, i12, 3, 4, plankBlock, plankMeta);
		}
		setBlockAndMetadata(world, 2, 3, 3, plankBlock, plankMeta);
		for (i12 = 3; i12 <= 5; ++i12) {
			setBlockAndMetadata(world, i12, 3, 4, Blocks.double_stone_slab, 0);
		}
		for (k12 = 1; k12 <= 3; ++k12) {
			setBlockAndMetadata(world, 6, 3, k12, Blocks.double_stone_slab, 0);
		}
		setBlockAndMetadata(world, 5, 3, 0, plankBlock, plankMeta);
		for (k12 = -3; k12 <= -1; ++k12) {
			setBlockAndMetadata(world, 6, 3, k12, plankBlock, plankMeta);
		}
		for (i12 = 3; i12 <= 5; ++i12) {
			setBlockAndMetadata(world, i12, 3, -4, plankBlock, plankMeta);
		}
		setBlockAndMetadata(world, 2, 3, -3, plankBlock, plankMeta);
		setBlockAndMetadata(world, 1, 3, -4, plankBlock, plankMeta);
		setBlockAndMetadata(world, -4, 1, -4, plank2Block, plank2Meta);
		setBlockAndMetadata(world, -4, 2, -4, Blocks.torch, 3);
		setBlockAndMetadata(world, -5, 2, -5, LOTRMod.glassPane, 0);
		placeChest(world, random, -6, 1, -4, 3, LOTRChestContents.HOBBIT_HOLE_STUDY);
		for (j12 = 1; j12 <= 2; ++j12) {
			setBlockAndMetadata(world, -7, j12, -3, Blocks.bookshelf, 0);
			setBlockAndMetadata(world, -7, j12, -1, Blocks.bookshelf, 0);
		}
		setBlockAndMetadata(world, -7, 1, -2, plank2SlabBlock, plank2SlabMeta | 8);
		setBlockAndMetadata(world, -5, 1, -2, Blocks.oak_stairs, 1);
		spawnItemFrame(world, -5, 2, 0, 2, new ItemStack(Items.clock));
		setBlockAndMetadata(world, -3, 1, 1, plankStairBlock, 3);
		setBlockAndMetadata(world, -3, 2, 1, plankStairBlock, 7);
		setBlockAndMetadata(world, -3, 1, 3, plankStairBlock, 2);
		setBlockAndMetadata(world, -3, 2, 3, plankStairBlock, 6);
		for (int i15 : new int[]{-6, -5}) {
			setBlockAndMetadata(world, i15, 1, 3, bedBlock, 0);
			setBlockAndMetadata(world, i15, 1, 4, bedBlock, 8);
		}
		setBlockAndMetadata(world, -4, 1, 4, plank2Block, plank2Meta);
		setBlockAndMetadata(world, -4, 2, 4, Blocks.torch, 4);
		setBlockAndMetadata(world, -7, 1, 1, beamBlock, beamMeta);
		placeBarrel(world, random, -7, 2, 1, 4, LOTRFoods.HOBBIT_DRINK);
		setBlockAndMetadata(world, -1, 2, 5, LOTRMod.glassPane, 0);
		setBlockAndMetadata(world, 0, 2, 5, LOTRMod.glassPane, 0);
		setBlockAndMetadata(world, 2, 2, 3, Blocks.torch, 4);
		setBlockAndMetadata(world, 3, 1, 4, LOTRMod.hobbitTable, 0);
		for (i1 = 4; i1 <= 5; ++i1) {
			placeChest(world, random, i1, 1, 4, 2, LOTRChestContents.HOBBIT_HOLE_LARDER);
		}
		setBlockAndMetadata(world, 6, 1, 3, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, 6, 1, 2, Blocks.cauldron, 3);
		setBlockAndMetadata(world, 6, 1, 1, LOTRMod.hobbitOven, 5);
		setBlockAndMetadata(world, 6, 2, -1, Blocks.torch, 1);
		setBlockAndMetadata(world, 4, 2, -5, LOTRMod.glassPane, 0);
		setBlockAndMetadata(world, 3, 2, -4, Blocks.torch, 3);
		setBlockAndMetadata(world, 6, 1, -1, plankStairBlock, 2);
		setBlockAndMetadata(world, 6, 1, -2, plankStairBlock, 1);
		setBlockAndMetadata(world, 6, 1, -3, plankStairBlock, 3);
		setBlockAndMetadata(world, 5, 1, -4, plankStairBlock, 1);
		setBlockAndMetadata(world, 4, 1, -4, plankStairBlock, 3);
		setBlockAndMetadata(world, 3, 1, -4, plankStairBlock, 0);
		for (i1 = 3; i1 <= 4; ++i1) {
			for (k1 = -2; k1 <= -1; ++k1) {
				setBlockAndMetadata(world, i1, 1, k1, Blocks.planks, 1);
				if (random.nextBoolean()) {
					placePlateWithCertainty(world, random, i1, 2, k1, plateBlock, LOTRFoods.HOBBIT);
					continue;
				}
				placeMug(world, random, i1, 2, k1, random.nextInt(4), LOTRFoods.HOBBIT_DRINK);
			}
		}
		for (i1 = -1; i1 <= 0; ++i1) {
			for (k1 = -2; k1 <= 1; ++k1) {
				setBlockAndMetadata(world, i1, 1, k1, carpetBlock, carpetMeta);
			}
		}
		for (i1 = -2; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 0, -6, pathBlock, pathMeta);
		}
		setBlockAndMetadata(world, -1, 0, -7, pathBlock, pathMeta);
		setBlockAndMetadata(world, 0, 0, -7, pathBlock, pathMeta);
		setBlockAndMetadata(world, 0, 0, -8, pathBlock, pathMeta);
		setBlockAndMetadata(world, 1, 0, -8, pathBlock, pathMeta);
		setBlockAndMetadata(world, 1, 0, -9, pathBlock, pathMeta);
		setBlockAndMetadata(world, 1, 0, -10, pathBlock, pathMeta);
		setAir(world, 1, 1, -10);
		for (i1 = -3; i1 <= 2; ++i1) {
			for (k1 = -9; k1 <= -7; ++k1) {
				if (getBlock(world, i1, 0, k1) != Blocks.grass || random.nextInt(4) != 0) {
					continue;
				}
				plantFlower(world, random, i1, 1, k1);
			}
		}
		placeHedge(world, -7, 1, -5);
		placeHedge(world, -8, 1, -4);
		for (k13 = -3; k13 <= -1; ++k13) {
			placeHedge(world, -9, 1, k13);
		}
		for (k13 = 1; k13 <= 3; ++k13) {
			placeHedge(world, -9, 1, k13);
		}
		placeHedge(world, -8, 1, 4);
		placeHedge(world, -7, 1, 5);
		for (i1 = -6; i1 <= -4; ++i1) {
			placeHedge(world, i1, 1, 6);
		}
		for (i1 = 3; i1 <= 5; ++i1) {
			placeHedge(world, i1, 1, 6);
		}
		placeHedge(world, 6, 1, 5);
		placeHedge(world, 7, 1, 4);
		for (k13 = 1; k13 <= 3; ++k13) {
			placeHedge(world, 8, 1, k13);
		}
		for (k13 = -3; k13 <= -1; ++k13) {
			placeHedge(world, 8, 1, k13);
		}
		placeHedge(world, 7, 1, -4);
		placeHedge(world, 6, 1, -5);
		for (i1 = -2; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 1, 6, Blocks.brick_block, 0);
			setGrassToDirt(world, i1, 0, 6);
			placeFlowerPot(world, i1, 2, 6, getRandomFlower(world, random));
		}
		for (i1 = -6; i1 <= 5; ++i1) {
			setBlockAndMetadata(world, i1, 3, -6, roofSlabBlock, roofSlabMeta);
			setBlockAndMetadata(world, i1, 3, 6, roofSlabBlock, roofSlabMeta);
		}
		for (k13 = -3; k13 <= 3; ++k13) {
			setBlockAndMetadata(world, -9, 3, k13, roofSlabBlock, roofSlabMeta);
			setBlockAndMetadata(world, 8, 3, k13, roofSlabBlock, roofSlabMeta);
		}
		setBlockAndMetadata(world, -7, 3, -5, roofSlabBlock, roofSlabMeta);
		setBlockAndMetadata(world, -8, 3, -4, roofSlabBlock, roofSlabMeta);
		setBlockAndMetadata(world, -8, 3, 4, roofSlabBlock, roofSlabMeta);
		setBlockAndMetadata(world, -7, 3, 5, roofSlabBlock, roofSlabMeta);
		setBlockAndMetadata(world, 6, 3, 5, roofSlabBlock, roofSlabMeta);
		setBlockAndMetadata(world, 7, 3, 4, roofSlabBlock, roofSlabMeta);
		setBlockAndMetadata(world, 7, 3, -4, roofSlabBlock, roofSlabMeta);
		setBlockAndMetadata(world, 6, 3, -5, roofSlabBlock, roofSlabMeta);
		for (i1 = -6; i1 <= 5; ++i1) {
			setBlockAndMetadata(world, i1, 3, -5, roofBlock, roofMeta);
			setBlockAndMetadata(world, i1, 3, 5, roofBlock, roofMeta);
		}
		for (k13 = -3; k13 <= 3; ++k13) {
			setBlockAndMetadata(world, -8, 3, k13, roofBlock, roofMeta);
			setBlockAndMetadata(world, 7, 3, k13, roofBlock, roofMeta);
		}
		setBlockAndMetadata(world, -7, 3, -4, roofBlock, roofMeta);
		setBlockAndMetadata(world, 6, 3, -4, roofBlock, roofMeta);
		setBlockAndMetadata(world, -7, 3, 4, roofBlock, roofMeta);
		setBlockAndMetadata(world, 6, 3, 4, roofBlock, roofMeta);
		for (i1 = -5; i1 <= 4; ++i1) {
			setBlockAndMetadata(world, i1, 4, -4, roofStairBlock, 2);
			setBlockAndMetadata(world, i1, 4, 4, roofStairBlock, 3);
		}
		for (k13 = -2; k13 <= 2; ++k13) {
			setBlockAndMetadata(world, -7, 4, k13, roofStairBlock, 1);
			setBlockAndMetadata(world, 6, 4, k13, roofStairBlock, 0);
		}
		setBlockAndMetadata(world, -6, 4, -4, roofStairBlock, 1);
		setBlockAndMetadata(world, -6, 4, -3, roofStairBlock, 2);
		setBlockAndMetadata(world, -7, 4, -3, roofStairBlock, 1);
		setBlockAndMetadata(world, 5, 4, -4, roofStairBlock, 0);
		setBlockAndMetadata(world, 5, 4, -3, roofStairBlock, 2);
		setBlockAndMetadata(world, 6, 4, -3, roofStairBlock, 0);
		setBlockAndMetadata(world, -6, 4, 4, roofStairBlock, 1);
		setBlockAndMetadata(world, -6, 4, 3, roofStairBlock, 3);
		setBlockAndMetadata(world, -7, 4, 3, roofStairBlock, 1);
		setBlockAndMetadata(world, 5, 4, 4, roofStairBlock, 0);
		setBlockAndMetadata(world, 5, 4, 3, roofStairBlock, 3);
		setBlockAndMetadata(world, 6, 4, 3, roofStairBlock, 0);
		for (i1 = -6; i1 <= 5; ++i1) {
			for (k1 = -3; k1 <= 3; ++k1) {
				if ((i1 < -5 || i1 > 4) && (k1 < -2 || k1 > 2)) {
					continue;
				}
				setBlockAndMetadata(world, i1, 4, k1, roofBlock, roofMeta);
				setBlockAndMetadata(world, i1, 5, k1, roofSlabBlock, roofSlabMeta);
			}
		}
		for (i1 = -5; i1 <= 4; ++i1) {
			setBlockAndMetadata(world, i1, 5, -2, roofStairBlock, 2);
			setBlockAndMetadata(world, i1, 5, 2, roofStairBlock, 3);
		}
		for (k13 = -1; k13 <= 1; ++k13) {
			setBlockAndMetadata(world, -5, 5, k13, roofStairBlock, 1);
			setBlockAndMetadata(world, 4, 5, k13, roofStairBlock, 0);
		}
		for (i1 = -4; i1 <= 3; ++i1) {
			for (k1 = -1; k1 <= 1; ++k1) {
				setBlockAndMetadata(world, i1, 5, k1, roofBlock, roofMeta);
			}
		}
		setBlockAndMetadata(world, 3, 5, 0, Blocks.brick_block, 0);
		setBlockAndMetadata(world, 3, 6, 0, Blocks.brick_block, 0);
		setBlockAndMetadata(world, 3, 7, 0, Blocks.flower_pot, 0);
		for (i1 = -2; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 3, -5, Blocks.brick_block, 0);
			setBlockAndMetadata(world, i1, 4, -5, roofSlabBlock, roofSlabMeta);
		}
		setBlockAndMetadata(world, -3, 3, -6, tileSlabBlock, tileSlabMeta);
		setBlockAndMetadata(world, -2, 3, -6, tileSlabBlock, tileSlabMeta | 8);
		setBlockAndMetadata(world, -1, 4, -6, tileSlabBlock, tileSlabMeta);
		setBlockAndMetadata(world, 0, 4, -6, tileSlabBlock, tileSlabMeta);
		setBlockAndMetadata(world, 1, 3, -6, tileSlabBlock, tileSlabMeta | 8);
		setBlockAndMetadata(world, 2, 3, -6, tileSlabBlock, tileSlabMeta);
		setBlockAndMetadata(world, -3, 2, -7, tileSlabBlock, tileSlabMeta | 8);
		setBlockAndMetadata(world, -2, 3, -7, tileSlabBlock, tileSlabMeta);
		setBlockAndMetadata(world, -1, 3, -7, tileSlabBlock, tileSlabMeta | 8);
		setBlockAndMetadata(world, 0, 3, -7, tileSlabBlock, tileSlabMeta | 8);
		setBlockAndMetadata(world, 1, 3, -7, tileSlabBlock, tileSlabMeta);
		setBlockAndMetadata(world, 2, 2, -7, tileSlabBlock, tileSlabMeta | 8);
		spawnHobbitCouple(world, 0, 1, 0, 16);
		return true;
	}

	@Override
	public boolean makeWealthy(Random random) {
		return false;
	}

	public void placeHedge(World world, int i, int j, int k) {
		int j1;
		//noinspection StatementWithEmptyBody
		for (j1 = j; !isOpaque(world, i, j1 - 1, k) && j1 >= j - 6; --j1) {
		}
		setBlockAndMetadata(world, i, j1, k, hedgeBlock, hedgeMeta);
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		bedBlock = random.nextBoolean() ? LOTRMod.strawBed : Blocks.bed;
	}
}
