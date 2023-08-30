package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityRohanBlacksmith;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenRohanSmithy extends LOTRWorldGenRohanStructure {
	public LOTRWorldGenRohanSmithy(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int k1;
		int i2;
		int j12;
		int i1;
		int j13;
		int j14;
		setOriginAndRotation(world, i, j, k, rotation, 4);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i12 = -10; i12 <= 5; ++i12) {
				for (int k12 = -3; k12 <= 4; ++k12) {
					j14 = getTopBlock(world, i12, k12) - 1;
					if (!isSurface(world, i12, j14, k12)) {
						return false;
					}
					if (j14 < minHeight) {
						minHeight = j14;
					}
					if (j14 > maxHeight) {
						maxHeight = j14;
					}
					if (maxHeight - minHeight <= 5) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -10; i1 <= 5; ++i1) {
			for (int k13 = -3; k13 <= 4; ++k13) {
				boolean corner;
				for (j12 = 2; j12 <= 8; ++j12) {
					setAir(world, i1, j12, k13);
				}
				corner = (i1 == -10 || i1 == 5) && (k13 == -3 || k13 == 4);
				if (corner) {
					for (j1 = 1; (j1 >= 1 || !isOpaque(world, i1, j1, k13)) && getY(j1) >= 0; --j1) {
						setBlockAndMetadata(world, i1, j1, k13, rockSlabDoubleBlock, rockSlabDoubleMeta);
						setGrassToDirt(world, i1, j1 - 1, k13);
					}
					setBlockAndMetadata(world, i1, 2, k13, rockSlabBlock, rockSlabMeta);
					continue;
				}
				for (j1 = 1; (j1 >= 1 || !isOpaque(world, i1, j1, k13)) && getY(j1) >= 0; --j1) {
					setBlockAndMetadata(world, i1, j1, k13, brickBlock, brickMeta);
					setGrassToDirt(world, i1, j1 - 1, k13);
				}
			}
		}
		for (i1 = -1; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 1, -3, brickStairBlock, 2);
		}
		for (i1 = -4; i1 <= 4; ++i1) {
			i2 = Math.abs(i1);
			if (i2 == 2) {
				for (j12 = 1; j12 <= 2; ++j12) {
					setBlockAndMetadata(world, i1, j12, -3, logBlock, logMeta);
				}
				for (j12 = 3; j12 <= 4; ++j12) {
					setBlockAndMetadata(world, i1, j12, -3, fenceBlock, fenceMeta);
				}
			}
			if (i2 == 3) {
				for (j12 = 2; j12 <= 4; ++j12) {
					setBlockAndMetadata(world, i1, j12, -2, plankBlock, plankMeta);
				}
			}
			if (i2 == 4) {
				int[] j15 = {-2, 3};
				j1 = j15.length;
				for (j14 = 0; j14 < j1; ++j14) {
					int k14 = j15[j14];
					setBlockAndMetadata(world, i1, 2, k14, logBlock, logMeta);
					for (int j16 = 3; j16 <= 4; ++j16) {
						setBlockAndMetadata(world, i1, j16, k14, fenceBlock, fenceMeta);
					}
					setBlockAndMetadata(world, i1, 5, k14, plank2Block, plank2Meta);
				}
				for (k1 = -1; k1 <= 2; ++k1) {
					setBlockAndMetadata(world, i1, 5, k1, roofSlabBlock, roofSlabMeta);
				}
			}
			if (i1 != 4) {
				continue;
			}
			for (k1 = -1; k1 <= 2; ++k1) {
				for (j1 = 2; j1 <= 4; ++j1) {
					if (k1 >= 0 && k1 <= 1 && j1 >= 3) {
						setBlockAndMetadata(world, i1, j1, k1, barsBlock, 0);
						continue;
					}
					setBlockAndMetadata(world, i1, j1, k1, plankBlock, plankMeta);
				}
			}
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			i2 = Math.abs(i1);
			setBlockAndMetadata(world, i1, 5, 3, roofSlabBlock, roofSlabMeta);
			for (k1 = -2; k1 <= 2; ++k1) {
				if (i2 == 3 && k1 == -2) {
					setBlockAndMetadata(world, i1, 5, -2, roofSlabBlock, roofSlabMeta);
					continue;
				}
				setBlockAndMetadata(world, i1, 5, k1, roofBlock, roofMeta);
			}
			if (i2 > 2) {
				continue;
			}
			for (k1 = -2; k1 <= 2; ++k1) {
				boolean slab = i2 == 0 && k1 == -2;
				if (i2 == 1 && (k1 == -1 || k1 == 2)) {
					slab = true;
				}
				if (i2 == 2 && k1 >= 0 && k1 <= 1) {
					slab = true;
				}
				if (slab) {
					setBlockAndMetadata(world, i1, 6, k1, roofSlabBlock, roofSlabMeta);
				}
				boolean full = i2 == 0 && k1 >= -1;
				if (i2 == 1 && k1 >= 0 && k1 <= 1) {
					full = true;
				}
				if (full) {
					setBlockAndMetadata(world, i1, 6, k1, roofBlock, roofMeta);
				}
				slab = i2 == 0 && k1 >= 0 && k1 <= 1;
				if (!slab) {
					continue;
				}
				setBlockAndMetadata(world, i1, 7, k1, roofSlabBlock, roofSlabMeta);
			}
		}
		for (int k15 = -3; k15 <= 2; ++k15) {
			setBlockAndMetadata(world, 0, 5, k15, logBlock, logMeta | 8);
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			i2 = Math.abs(i1);
			if (i2 == 0) {
				setBlockAndMetadata(world, i1, 6, -3, plank2SlabBlock, plank2SlabMeta);
			}
			if (i2 == 1) {
				setBlockAndMetadata(world, i1, 5, -3, plank2SlabBlock, plank2SlabMeta | 8);
			}
			if (i2 == 2) {
				setBlockAndMetadata(world, i1, 5, -3, plank2SlabBlock, plank2SlabMeta);
			}
			if (i2 != 3) {
				continue;
			}
			setBlockAndMetadata(world, i1, 4, -3, plank2SlabBlock, plank2SlabMeta | 8);
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			i2 = Math.abs(i1);
			if (i2 <= 1) {
				for (j12 = 2; j12 <= 6; ++j12) {
					setBlockAndMetadata(world, i1, j12, 3, brickBlock, brickMeta);
				}
			}
			if (i2 == 2) {
				setBlockAndMetadata(world, i1, 2, 3, brickBlock, brickMeta);
				setBlockAndMetadata(world, i1, 4, 3, fenceBlock, fenceMeta);
			}
			if (i2 != 3) {
				continue;
			}
			for (j12 = 2; j12 <= 4; ++j12) {
				setBlockAndMetadata(world, i1, j12, 3, brickBlock, brickMeta);
			}
		}
		setBlockAndMetadata(world, -1, 7, 3, brickStairBlock, 1);
		setBlockAndMetadata(world, 1, 7, 3, brickStairBlock, 0);
		for (j13 = 7; j13 <= 9; ++j13) {
			setBlockAndMetadata(world, 0, j13, 3, brickBlock, brickMeta);
		}
		setBlockAndMetadata(world, 0, 10, 3, Blocks.flower_pot, 0);
		for (i1 = -1; i1 <= 1; ++i1) {
			for (int j17 = 2; j17 <= 6; ++j17) {
				setBlockAndMetadata(world, i1, j17, 4, brickBlock, brickMeta);
			}
		}
		setBlockAndMetadata(world, 0, 3, 4, brickCarvedBlock, brickCarvedMeta);
		setBlockAndMetadata(world, -1, 6, 4, brickStairBlock, 1);
		setBlockAndMetadata(world, 1, 6, 4, brickStairBlock, 0);
		setBlockAndMetadata(world, 0, 7, 4, brickStairBlock, 3);
		setBlockAndMetadata(world, 0, 1, 3, LOTRMod.hearth, 0);
		setBlockAndMetadata(world, 0, 2, 3, Blocks.fire, 0);
		setAir(world, 0, 3, 3);
		setBlockAndMetadata(world, 0, 4, 2, Blocks.torch, 4);
		for (i1 = -1; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 5, 2, brickBlock, brickMeta);
			setBlockAndMetadata(world, i1, 6, 2, brickBlock, brickMeta);
		}
		setBlockAndMetadata(world, 0, 7, 2, brickStairBlock, 2);
		setBlockAndMetadata(world, -1, 2, 2, LOTRMod.alloyForge, 2);
		setBlockAndMetadata(world, 0, 2, 2, LOTRMod.unsmeltery, 2);
		setBlockAndMetadata(world, 1, 2, 2, Blocks.furnace, 2);
		for (j13 = 3; j13 <= 4; ++j13) {
			setBlockAndMetadata(world, -1, j13, 2, brickWallBlock, brickWallMeta);
			setBlockAndMetadata(world, 1, j13, 2, brickWallBlock, brickWallMeta);
		}
		setBlockAndMetadata(world, 2, 2, 2, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, 3, 2, 2, logBlock, logMeta);
		setBlockAndMetadata(world, 3, 2, 1, tableBlock, 0);
		for (j13 = 3; j13 <= 4; ++j13) {
			ItemStack weapon = random.nextBoolean() ? getRandomRohanWeapon(random) : null;
			placeWeaponRack(world, 3, j13, 2, 6, weapon);
		}
		placeArmorStand(world, 3, 2, -1, 1, getRohanArmourItems());
		setBlockAndMetadata(world, -7, 2, 0, Blocks.anvil, 1);
		setBlockAndMetadata(world, -8, 2, 3, rockSlabDoubleBlock, rockSlabDoubleMeta);
		setBlockAndMetadata(world, -7, 2, 3, Blocks.cauldron, 3);
		setBlockAndMetadata(world, -6, 2, 3, rockSlabDoubleBlock, rockSlabDoubleMeta);
		LOTREntityRohanBlacksmith blacksmith = new LOTREntityRohanBlacksmith(world);
		spawnNPCAndSetHome(blacksmith, world, 0, 2, 0, 8);
		return true;
	}
}
