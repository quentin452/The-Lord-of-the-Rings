package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityEasterling;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenEasterlingTownHouse extends LOTRWorldGenEasterlingStructureTown {
	public LOTRWorldGenEasterlingTownHouse(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int i1;
		int j1;
		int k13;
		int k12;
		int l;
		setOriginAndRotation(world, i, j, k, rotation, 7);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i12 = -5; i12 <= 5; ++i12) {
				for (int k14 = -8; k14 <= 8; ++k14) {
					int j14 = getTopBlock(world, i12, k14) - 1;
					if (!isSurface(world, i12, j14, k14)) {
						return false;
					}
					if (j14 < minHeight) {
						minHeight = j14;
					}
					if (j14 > maxHeight) {
						maxHeight = j14;
					}
					if (maxHeight - minHeight <= 8) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i13 = -4; i13 <= 4; ++i13) {
			for (int k122 = -6; k122 <= 6; ++k122) {
				int j12;
				int i2 = Math.abs(i13);
				int k2 = Math.abs(k122);
				if (i2 == 4 && (k2 == 2 || k2 == 6) || i2 == 0 && k122 == 6) {
					for (j12 = 4; (j12 >= 0 || !isOpaque(world, i13, j12, k122)) && getY(j12) >= 0; --j12) {
						setBlockAndMetadata(world, i13, j12, k122, woodBeamBlock, woodBeamMeta);
						setGrassToDirt(world, i13, j12 - 1, k122);
					}
					continue;
				}
				if (i2 == 4 || k2 == 6) {
					for (j12 = 3; (j12 >= 0 || !isOpaque(world, i13, j12, k122)) && getY(j12) >= 0; --j12) {
						setBlockAndMetadata(world, i13, j12, k122, brickBlock, brickMeta);
						setGrassToDirt(world, i13, j12 - 1, k122);
					}
					if (k2 == 6) {
						setBlockAndMetadata(world, i13, 4, k122, woodBeamBlock, woodBeamMeta | 4);
						continue;
					}
					setBlockAndMetadata(world, i13, 4, k122, woodBeamBlock, woodBeamMeta | 8);
					continue;
				}
				for (j12 = 0; (j12 >= 0 || !isOpaque(world, i13, j12, k122)) && getY(j12) >= 0; --j12) {
					if (IntMath.mod(i13, 2) == 1 && IntMath.mod(k122, 2) == 1) {
						setBlockAndMetadata(world, i13, j12, k122, pillarRedBlock, pillarRedMeta);
					} else {
						setBlockAndMetadata(world, i13, j12, k122, brickRedBlock, brickRedMeta);
					}
					setGrassToDirt(world, i13, j12 - 1, k122);
				}
				for (j12 = 1; j12 <= 8; ++j12) {
					setAir(world, i13, j12, k122);
				}
			}
		}
		for (int k131 : new int[]{-4, 4}) {
			for (int i14 : new int[]{-4, 4}) {
				setBlockAndMetadata(world, i14, 2, k131 - 1, brickStairBlock, 7);
				setAir(world, i14, 2, k131);
				setBlockAndMetadata(world, i14, 2, k131 + 1, brickStairBlock, 6);
			}
			setBlockAndMetadata(world, -4, 3, k131, brickStairBlock, 5);
			setBlockAndMetadata(world, 4, 3, k131, brickStairBlock, 4);
		}
		for (int i15 : new int[]{-2, 2}) {
			setBlockAndMetadata(world, i15, 2, -6, LOTRMod.reedBars, 0);
			setBlockAndMetadata(world, i15, 3, -6, brickStairBlock, 6);
		}
		for (int i12 : new int[]{-2, 2}) {
			setBlockAndMetadata(world, i12 - 1, 2, 6, brickStairBlock, 4);
			setAir(world, i12, 2, 6);
			setBlockAndMetadata(world, i12 + 1, 2, 6, brickStairBlock, 5);
			setBlockAndMetadata(world, i12, 3, 6, brickStairBlock, 7);
		}
		for (int k131 : new int[]{-7, 7}) {
			setBlockAndMetadata(world, -4, 3, k131, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, 4, 3, k131, fenceBlock, fenceMeta);
		}
		for (int i12 : new int[]{-5, 5}) {
			setBlockAndMetadata(world, i12, 3, -6, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, i12, 3, 6, fenceBlock, fenceMeta);
		}
		setBlockAndMetadata(world, -1, 3, -7, Blocks.torch, 4);
		setBlockAndMetadata(world, 1, 3, -7, Blocks.torch, 4);
		setBlockAndMetadata(world, -1, 3, 7, Blocks.torch, 3);
		setBlockAndMetadata(world, 1, 3, 7, Blocks.torch, 3);
		setBlockAndMetadata(world, -5, 3, -2, Blocks.torch, 1);
		setBlockAndMetadata(world, -5, 3, 2, Blocks.torch, 1);
		setBlockAndMetadata(world, 5, 3, -2, Blocks.torch, 2);
		setBlockAndMetadata(world, 5, 3, 2, Blocks.torch, 2);
		setBlockAndMetadata(world, 0, 0, -6, woodBeamBlock, woodBeamMeta | 4);
		setBlockAndMetadata(world, 0, 1, -6, doorBlock, 1);
		setBlockAndMetadata(world, 0, 2, -6, doorBlock, 8);
		for (int k15 = -5; k15 <= 5; ++k15) {
			setBlockAndMetadata(world, 0, 0, k15, woodBeamBlock, woodBeamMeta | 8);
		}
		for (int k131 : new int[]{-2, 2}) {
			for (int i16 = -3; i16 <= 3; ++i16) {
				setBlockAndMetadata(world, i16, 0, k131, woodBeamBlock, woodBeamMeta | 4);
			}
		}
		for (int k16 = -6; k16 <= 6; ++k16) {
			for (l = 0; l <= 3; ++l) {
				j1 = 5 + l;
				setBlockAndMetadata(world, -4 + l, j1, k16, roofStairBlock, 1);
				setBlockAndMetadata(world, 4 - l, j1, k16, roofStairBlock, 0);
				if (l <= 0) {
					continue;
				}
				setBlockAndMetadata(world, -4 + l, j1 - 1, k16, roofStairBlock, 4);
				setBlockAndMetadata(world, 4 - l, j1 - 1, k16, roofStairBlock, 5);
			}
			setBlockAndMetadata(world, 0, 8, k16, roofBlock, roofMeta);
			setBlockAndMetadata(world, 0, 9, k16, roofSlabBlock, roofSlabMeta);
		}
		for (int k131 : new int[]{-7, 7}) {
			for (int l2 = 0; l2 <= 2; ++l2) {
				int j12 = 5 + l2;
				setBlockAndMetadata(world, -3 + l2, j12, k131, roofStairBlock, 1);
				setBlockAndMetadata(world, 3 - l2, j12, k131, roofStairBlock, 0);
				if (l2 <= 0) {
					continue;
				}
				setBlockAndMetadata(world, -3 + l2, j12 - 1, k131, roofStairBlock, 4);
				setBlockAndMetadata(world, 3 - l2, j12 - 1, k131, roofStairBlock, 5);
			}
			setBlockAndMetadata(world, 0, 7, k131, roofBlock, roofMeta);
			setBlockAndMetadata(world, 0, 8, k131, roofBlock, roofMeta);
			setBlockAndMetadata(world, 0, 9, k131, roofSlabBlock, roofSlabMeta);
			setBlockAndMetadata(world, -4, 4, k131, roofStairBlock, 5);
			setBlockAndMetadata(world, -3, 4, k131, roofStairBlock, 4);
			setBlockAndMetadata(world, -2, 4, k131, roofSlabBlock, roofSlabMeta | 8);
			setBlockAndMetadata(world, -1, 4, k131, roofBlock, roofMeta);
			setBlockAndMetadata(world, 0, 4, k131, roofSlabBlock, roofSlabMeta | 8);
			setBlockAndMetadata(world, 1, 4, k131, roofBlock, roofMeta);
			setBlockAndMetadata(world, 2, 4, k131, roofSlabBlock, roofSlabMeta | 8);
			setBlockAndMetadata(world, 3, 4, k131, roofStairBlock, 5);
			setBlockAndMetadata(world, 4, 4, k131, roofStairBlock, 4);
			setBlockAndMetadata(world, -1, 5, k131, roofSlabBlock, roofSlabMeta);
			setBlockAndMetadata(world, 0, 5, k131, roofBlock, roofMeta);
			setBlockAndMetadata(world, 1, 5, k131, roofSlabBlock, roofSlabMeta);
		}
		setBlockAndMetadata(world, 0, 8, -8, roofStairBlock, 6);
		setBlockAndMetadata(world, 0, 9, -8, roofStairBlock, 3);
		setBlockAndMetadata(world, 0, 8, 8, roofStairBlock, 7);
		setBlockAndMetadata(world, 0, 9, 8, roofStairBlock, 2);
		for (int k131 : new int[]{-6, 6}) {
			for (int l3 = 0; l3 <= 2; ++l3) {
				int j13 = 5 + l3;
				setBlockAndMetadata(world, -3 + l3, j13, k131, roofBlock, roofMeta);
				setBlockAndMetadata(world, 3 - l3, j13, k131, roofBlock, roofMeta);
				for (int i17 = -2 + l3; i17 <= 2 - l3; ++i17) {
					setBlockAndMetadata(world, i17, j13, k131, plankBlock, plankMeta);
				}
			}
		}
		for (int i12 : new int[]{-5, 5}) {
			setBlockAndMetadata(world, i12, 5, -7, roofSlabBlock, roofSlabMeta);
			setBlockAndMetadata(world, i12, 4, -6, roofStairBlock, 6);
			setBlockAndMetadata(world, i12, 4, -5, roofStairBlock, 7);
			setBlockAndMetadata(world, i12, 5, -4, roofSlabBlock, roofSlabMeta);
			setBlockAndMetadata(world, i12, 4, -3, roofStairBlock, 6);
			setBlockAndMetadata(world, i12, 4, -1, roofStairBlock, 7);
			setBlockAndMetadata(world, i12, 4, 1, roofStairBlock, 6);
			setBlockAndMetadata(world, i12, 4, 3, roofStairBlock, 7);
			setBlockAndMetadata(world, i12, 5, 4, roofSlabBlock, roofSlabMeta);
			setBlockAndMetadata(world, i12, 4, 5, roofStairBlock, 6);
			setBlockAndMetadata(world, i12, 4, 6, roofStairBlock, 7);
			setBlockAndMetadata(world, i12, 5, 7, roofSlabBlock, roofSlabMeta);
		}
		int[] k16 = {-2, 2};
		l = k16.length;
		for (j1 = 0; j1 < l; ++j1) {
			k13 = k16[j1];
			setBlockAndMetadata(world, -5, 4, k13, roofStairBlock, 1);
			setBlockAndMetadata(world, 5, 4, k13, roofStairBlock, 0);
		}
		for (int i18 = -3; i18 <= 3; ++i18) {
			for (k12 = -2; k12 <= 5; ++k12) {
				setBlockAndMetadata(world, i18, 4, k12, plankSlabBlock, plankSlabMeta | 8);
				if (Math.abs(i18) > 2 || random.nextInt(3) != 0) {
					continue;
				}
				setBlockAndMetadata(world, i18, 5, k12, LOTRMod.thatchFloor, 0);
			}
		}
		int[] i18 = {-2, 2};
		k12 = i18.length;
		for (j1 = 0; j1 < k12; ++j1) {
			k13 = i18[j1];
			for (int i19 = -3; i19 <= 3; ++i19) {
				setBlockAndMetadata(world, i19, 4, k13, woodBeamBlock, woodBeamMeta | 4);
			}
		}
		for (int k131 = -5; k131 <= 5; ++k131) {
			setBlockAndMetadata(world, 0, 4, k131, woodBeamBlock, woodBeamMeta | 8);
		}
		for (int j13 = 1; j13 <= 5; ++j13) {
			setBlockAndMetadata(world, -3, j13, 0, woodBeamBlock, woodBeamMeta);
		}
		for (int j14 = 1; j14 <= 6; ++j14) {
			setBlockAndMetadata(world, -2, j14, 0, woodBeamBlock, woodBeamMeta);
			setBlockAndMetadata(world, -1, j14, 0, Blocks.ladder, 4);
		}
		for (int i110 = 2; i110 <= 3; ++i110) {
			setBlockAndMetadata(world, i110, 1, -1, brickStairBlock, 2);
			setBlockAndMetadata(world, i110, 2, -1, brickStairBlock, 6);
			setBlockAndMetadata(world, i110, 3, -1, brickStairBlock, 6);
			setBlockAndMetadata(world, i110, 1, 1, brickStairBlock, 3);
			setBlockAndMetadata(world, i110, 2, 1, brickStairBlock, 7);
			setBlockAndMetadata(world, i110, 3, 1, brickStairBlock, 7);
			setBlockAndMetadata(world, i110, 3, 0, brickBlock, brickMeta);
			for (k12 = -1; k12 <= 1; ++k12) {
				setBlockAndMetadata(world, i110, 4, k12, brickBlock, brickMeta);
			}
		}
		for (int k14 = -1; k14 <= 1; ++k14) {
			setBlockAndMetadata(world, 2, 5, k14, brickStairBlock, 1);
			setBlockAndMetadata(world, 2, 6, k14, brickStairBlock, 5);
			setBlockAndMetadata(world, 3, 5, k14, brickBlock, brickMeta);
		}
		setBlockAndMetadata(world, 3, 6, 0, brickBlock, brickMeta);
		setBlockAndMetadata(world, 3, 7, 0, brickBlock, brickMeta);
		setBlockAndMetadata(world, 3, 8, 0, Blocks.flower_pot, 0);
		setBlockAndMetadata(world, 2, 0, 0, LOTRMod.hearth, 0);
		setBlockAndMetadata(world, 2, 1, 0, barsBlock, 0);
		setBlockAndMetadata(world, 2, 2, 0, Blocks.furnace, 5);
		setBlockAndMetadata(world, 3, 0, 0, LOTRMod.hearth, 0);
		setBlockAndMetadata(world, 3, 1, 0, Blocks.fire, 0);
		spawnItemFrame(world, 2, 3, 0, 3, getEasterlingFramedItem(random));
		setBlockAndMetadata(world, -2, 1, -5, plankStairBlock, 7);
		setBlockAndMetadata(world, -3, 1, -5, plankStairBlock, 7);
		setBlockAndMetadata(world, -3, 1, -4, plankStairBlock, 4);
		placePlate(world, random, -2, 2, -5, plateBlock, LOTRFoods.RHUN);
		placePlate(world, random, -3, 2, -5, plateBlock, LOTRFoods.RHUN);
		placeMug(world, random, -3, 2, -4, 3, LOTRFoods.RHUN_DRINK);
		setBlockAndMetadata(world, 3, 1, -4, tableBlock, 0);
		for (int k131 : new int[]{-1, 1}) {
			setBlockAndMetadata(world, -3, 1, k131, plankSlabBlock, plankSlabMeta | 8);
			placeBarrel(world, random, -3, 2, k131, 4, LOTRFoods.RHUN_DRINK);
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			setBlockAndMetadata(world, i1, 1, 5, plankStairBlock, 6);
			if (Math.abs(i1) < 2) {
				continue;
			}
			if (random.nextBoolean()) {
				placePlate(world, random, i1, 2, 5, plateBlock, LOTRFoods.RHUN);
				continue;
			}
			placeMug(world, random, i1, 2, 5, 0, LOTRFoods.RHUN_DRINK);
		}
		setBlockAndMetadata(world, -1, 1, 5, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, 1, 1, 5, Blocks.cauldron, 3);
		setBlockAndMetadata(world, -2, 1, 3, plankStairBlock, 3);
		setBlockAndMetadata(world, 2, 1, 3, plankStairBlock, 3);
		setBlockAndMetadata(world, 0, 3, -2, LOTRMod.chandelier, 0);
		setBlockAndMetadata(world, 0, 3, 2, LOTRMod.chandelier, 0);
		setBlockAndMetadata(world, -3, 5, -2, woodBeamBlock, woodBeamMeta);
		setBlockAndMetadata(world, 3, 5, -2, woodBeamBlock, woodBeamMeta);
		for (i1 = -2; i1 <= 2; ++i1) {
			setBlockAndMetadata(world, i1, 5, -2, fenceBlock, fenceMeta);
		}
		for (int i12 : new int[]{-1, 1}) {
			setBlockAndMetadata(world, i12, 5, 4, bedBlock, 0);
			setBlockAndMetadata(world, i12, 5, 5, bedBlock, 8);
		}
		placeChest(world, random, 0, 5, 5, 2, chestContents);
		setBlockAndMetadata(world, 0, 7, 5, LOTRMod.chandelier, 3);
		int men = 1 + random.nextInt(2);
		for (l = 0; l < men; ++l) {
			LOTREntityEasterling easterling = new LOTREntityEasterling(world);
			spawnNPCAndSetHome(easterling, world, 0, 1, 0, 16);
		}
		return true;
	}
}
