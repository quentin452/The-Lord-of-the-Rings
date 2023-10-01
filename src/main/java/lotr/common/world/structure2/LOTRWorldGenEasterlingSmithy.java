package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityEasterlingBlacksmith;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenEasterlingSmithy extends LOTRWorldGenEasterlingStructureTown {
	public LOTRWorldGenEasterlingSmithy(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int j1;
		int i2;
		int i1;
		int i12;
		int i13;
		int i14;
		int j12;
		int i22;
		int k2;
		setOriginAndRotation(world, i, j, k, rotation, 7);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (i13 = -7; i13 <= 7; ++i13) {
				for (int k12 = -7; k12 <= 7; ++k12) {
					j12 = getTopBlock(world, i13, k12) - 1;
					if (!isSurface(world, i13, j12, k12)) {
						return false;
					}
					if (j12 < minHeight) {
						minHeight = j12;
					}
					if (j12 > maxHeight) {
						maxHeight = j12;
					}
					if (maxHeight - minHeight <= 6) {
						continue;
					}
					return false;
				}
			}
		}
		for (i14 = -6; i14 <= 6; ++i14) {
			for (k1 = -6; k1 <= 6; ++k1) {
				i22 = Math.abs(i14);
				k2 = Math.abs(k1);
				if (i22 == 6 && k2 % 4 == 2 || k2 == 6 && i22 % 4 == 2) {
					for (j12 = 4; (j12 >= 0 || !isOpaque(world, i14, j12, k1)) && getY(j12) >= 0; --j12) {
						setBlockAndMetadata(world, i14, j12, k1, woodBeamBlock, woodBeamMeta);
						setGrassToDirt(world, i14, j12 - 1, k1);
					}
					continue;
				}
				if (i22 == 6 || k2 == 6) {
					for (j12 = 3; (j12 >= 0 || !isOpaque(world, i14, j12, k1)) && getY(j12) >= 0; --j12) {
						setBlockAndMetadata(world, i14, j12, k1, brickBlock, brickMeta);
						setGrassToDirt(world, i14, j12 - 1, k1);
					}
					if (k2 == 6) {
						setBlockAndMetadata(world, i14, 4, k1, woodBeamBlock, woodBeamMeta | 4);
						continue;
					}
					setBlockAndMetadata(world, i14, 4, k1, woodBeamBlock, woodBeamMeta | 8);
					continue;
				}
				for (j12 = 0; (j12 >= 0 || !isOpaque(world, i14, j12, k1)) && getY(j12) >= 0; --j12) {
					setBlockAndMetadata(world, i14, j12, k1, brickRedBlock, brickRedMeta);
					setGrassToDirt(world, i14, j12 - 1, k1);
				}
				for (j12 = 1; j12 <= 9; ++j12) {
					setAir(world, i14, j12, k1);
				}
				if (IntMath.mod(i14, 2) != 1 || IntMath.mod(k1, 2) != 1) {
					continue;
				}
				setBlockAndMetadata(world, i14, 0, k1, pillarRedBlock, pillarRedMeta);
			}
		}
		for (i14 = -5; i14 <= 5; ++i14) {
			setBlockAndMetadata(world, i14, 0, -2, woodBeamBlock, woodBeamMeta | 4);
			setBlockAndMetadata(world, i14, 0, 2, woodBeamBlock, woodBeamMeta | 4);
		}
		for (int k13 = -5; k13 <= 5; ++k13) {
			setBlockAndMetadata(world, -2, 0, k13, woodBeamBlock, woodBeamMeta | 8);
			setBlockAndMetadata(world, 2, 0, k13, woodBeamBlock, woodBeamMeta | 8);
		}
		setBlockAndMetadata(world, -4, 2, -6, LOTRMod.reedBars, 0);
		setBlockAndMetadata(world, 4, 2, -6, LOTRMod.reedBars, 0);
		for (int k12 : new int[]{-4, 0}) {
			setBlockAndMetadata(world, -6, 2, k12 - 1, brickStairBlock, 7);
			setAir(world, -6, 2, k12);
			setBlockAndMetadata(world, -6, 2, k12 + 1, brickStairBlock, 6);
			setBlockAndMetadata(world, -6, 3, k12, brickStairBlock, 5);
			setBlockAndMetadata(world, 6, 2, k12 - 1, brickStairBlock, 7);
			setAir(world, 6, 2, k12);
			setBlockAndMetadata(world, 6, 2, k12 + 1, brickStairBlock, 6);
			setBlockAndMetadata(world, 6, 3, k12, brickStairBlock, 4);
		}
		for (int k12 : new int[]{-7, 7}) {
			setBlockAndMetadata(world, -6, 3, k12, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, 6, 3, k12, fenceBlock, fenceMeta);
		}
		int[] k13 = {-7, 7};
		k1 = k13.length;
		for (i22 = 0; i22 < k1; ++i22) {
			int i15 = k13[i22];
			setBlockAndMetadata(world, i15, 3, -6, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, i15, 3, 6, fenceBlock, fenceMeta);
		}
		setBlockAndMetadata(world, -2, 3, -7, Blocks.torch, 4);
		setBlockAndMetadata(world, 2, 3, -7, Blocks.torch, 4);
		setBlockAndMetadata(world, -2, 3, 7, Blocks.torch, 3);
		setBlockAndMetadata(world, 2, 3, 7, Blocks.torch, 3);
		setBlockAndMetadata(world, -7, 3, -2, Blocks.torch, 1);
		setBlockAndMetadata(world, -7, 3, 2, Blocks.torch, 1);
		setBlockAndMetadata(world, 7, 3, -2, Blocks.torch, 2);
		setBlockAndMetadata(world, 7, 3, 2, Blocks.torch, 2);
		setBlockAndMetadata(world, 0, 0, -6, woodBeamBlock, woodBeamMeta | 4);
		setBlockAndMetadata(world, 0, 1, -6, doorBlock, 1);
		setBlockAndMetadata(world, 0, 2, -6, doorBlock, 8);
		for (i1 = -7; i1 <= 7; ++i1) {
			for (k1 = -7; k1 <= 7; ++k1) {
				i22 = Math.abs(i1);
				k2 = Math.abs(k1);
				if (i22 == 7 && (k2 == 7 || k2 == 0 || k2 == 4) || k2 == 7 && (i22 == 0 || i22 == 4)) {
					setBlockAndMetadata(world, i1, 5, k1, roofSlabBlock, roofSlabMeta);
				}
				if (k2 == 7) {
					if (i1 == -6 || i1 == -3 || i1 == 1 || i1 == 5) {
						setBlockAndMetadata(world, i1, 4, k1, roofStairBlock, 5);
					}
					if (i1 == -5 || i1 == -1 || i1 == 3 || i1 == 6) {
						setBlockAndMetadata(world, i1, 4, k1, roofStairBlock, 4);
					}
					if (i22 == 2) {
						setBlockAndMetadata(world, i1, 4, k1, roofStairBlock, k1 < 0 ? 2 : 3);
					}
				}
				if (i22 != 7) {
					continue;
				}
				if (k1 == -6 || k1 == -3 || k1 == 1 || k1 == 5) {
					setBlockAndMetadata(world, i1, 4, k1, roofStairBlock, 6);
				}
				if (k1 == -5 || k1 == -1 || k1 == 3 || k1 == 6) {
					setBlockAndMetadata(world, i1, 4, k1, roofStairBlock, 7);
				}
				if (k2 != 2) {
					continue;
				}
				setBlockAndMetadata(world, i1, 4, k1, roofStairBlock, i1 < 0 ? 1 : 0);
			}
		}
		for (int step = 0; step <= 1; ++step) {
			int k14;
			j1 = 5 + step;
			for (i13 = -6 + step; i13 <= 6 - step; ++i13) {
				setBlockAndMetadata(world, i13, j1, -6 + step, roofStairBlock, 2);
				setBlockAndMetadata(world, i13, j1, 6 - step, roofStairBlock, 3);
			}
			for (k14 = -6 + step; k14 <= 6 - step; ++k14) {
				setBlockAndMetadata(world, -6 + step, j1, k14, roofStairBlock, 1);
				setBlockAndMetadata(world, 6 - step, j1, k14, roofStairBlock, 0);
			}
			for (i13 = -5 + step; i13 <= 5 - step; ++i13) {
				setBlockAndMetadata(world, i13, j1, -5 + step, roofStairBlock, 7);
				setBlockAndMetadata(world, i13, j1, 5 - step, roofStairBlock, 6);
			}
			for (k14 = -5 + step; k14 <= 5 - step; ++k14) {
				setBlockAndMetadata(world, -5 + step, j1, k14, roofStairBlock, 4);
				setBlockAndMetadata(world, 5 - step, j1, k14, roofStairBlock, 5);
			}
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			for (k1 = -3; k1 <= 3; ++k1) {
				i22 = Math.abs(i1);
				k2 = Math.abs(k1);
				if (i22 <= 1 && k2 >= 3 || k2 <= 1 && i22 >= 3 || i22 >= 2 && k2 >= 2) {
					setBlockAndMetadata(world, i1, 6, k1, roofSlabBlock, roofSlabMeta | 8);
				}
				if (i22 != 2 || k2 != 2) {
					continue;
				}
				setBlockAndMetadata(world, i1, 6, k1, roofBlock, roofMeta);
				setBlockAndMetadata(world, i1, 5, k1, LOTRMod.chandelier, 0);
			}
		}
		for (i1 = -4; i1 <= 4; ++i1) {
			i2 = Math.abs(i1);
			if (i2 >= 2) {
				setBlockAndMetadata(world, i1, 7, -2, roofStairBlock, 2);
				setBlockAndMetadata(world, i1, 7, 2, roofStairBlock, 3);
			}
			if (i2 >= 1) {
				setBlockAndMetadata(world, i1, 8, -1, roofStairBlock, 7);
				setBlockAndMetadata(world, i1, 8, 1, roofStairBlock, 6);
			}
			if (i2 < 0) {
				continue;
			}
			setBlockAndMetadata(world, i1, 9, 0, roofSlabBlock, roofSlabMeta);
		}
		for (int k15 = -4; k15 <= 4; ++k15) {
			int k22 = Math.abs(k15);
			if (k22 >= 2) {
				setBlockAndMetadata(world, -2, 7, k15, roofStairBlock, 1);
				setBlockAndMetadata(world, 2, 7, k15, roofStairBlock, 0);
			}
			if (k22 >= 1) {
				setBlockAndMetadata(world, -1, 8, k15, roofStairBlock, 4);
				setBlockAndMetadata(world, 1, 8, k15, roofStairBlock, 5);
			}
			setBlockAndMetadata(world, 0, 9, k15, roofSlabBlock, roofSlabMeta);
		}
		setBlockAndMetadata(world, 0, 9, -4, roofStairBlock, 3);
		setBlockAndMetadata(world, 0, 9, 4, roofStairBlock, 2);
		setBlockAndMetadata(world, -4, 9, 0, roofStairBlock, 0);
		setBlockAndMetadata(world, 4, 9, 0, roofStairBlock, 1);
		for (int k12 : new int[]{-3, 3}) {
			setBlockAndMetadata(world, -1, 7, k12, plankBlock, plankMeta);
			setBlockAndMetadata(world, 0, 7, k12, LOTRMod.reedBars, 0);
			setBlockAndMetadata(world, 1, 7, k12, plankBlock, plankMeta);
			setBlockAndMetadata(world, -1, 8, k12, roofBlock, roofMeta);
			setBlockAndMetadata(world, 0, 8, k12, plankBlock, plankMeta);
			setBlockAndMetadata(world, 1, 8, k12, roofBlock, roofMeta);
		}
		for (int i15 : new int[]{-3, 3}) {
			setBlockAndMetadata(world, i15, 7, -1, plankBlock, plankMeta);
			setBlockAndMetadata(world, i15, 7, 0, LOTRMod.reedBars, 0);
			setBlockAndMetadata(world, i15, 7, 1, plankBlock, plankMeta);
			setBlockAndMetadata(world, i15, 8, -1, roofBlock, roofMeta);
			setBlockAndMetadata(world, i15, 8, 0, plankBlock, plankMeta);
			setBlockAndMetadata(world, i15, 8, 1, roofBlock, roofMeta);
		}
		setBlockAndMetadata(world, 0, 4, -7, plankBlock, plankMeta);
		spawnItemFrame(world, 0, 4, -7, 2, new ItemStack(LOTRMod.blacksmithHammer));
		setBlockAndMetadata(world, -2, 3, -5, Blocks.torch, 3);
		setBlockAndMetadata(world, 2, 3, -5, Blocks.torch, 3);
		setBlockAndMetadata(world, 2, 3, -5, Blocks.torch, 3);
		setBlockAndMetadata(world, 5, 3, -2, Blocks.torch, 1);
		for (i12 = -5; i12 <= 5; ++i12) {
			i2 = Math.abs(i12);
			if (i2 == 2) {
				setBlockAndMetadata(world, i12, 1, -2, woodBeamBlock, woodBeamMeta);
				setBlockAndMetadata(world, i12, 2, -2, plankSlabBlock, plankSlabMeta);
				continue;
			}
			if (i12 == 4) {
				setBlockAndMetadata(world, 4, 1, -2, fenceGateBlock, 0);
				continue;
			}
			setBlockAndMetadata(world, i12, 1, -2, fenceBlock, fenceMeta);
		}
		setBlockAndMetadata(world, 3, 1, 5, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, 4, 1, 5, tableBlock, 0);
		setBlockAndMetadata(world, 5, 1, 5, woodBeamBlock, woodBeamMeta);
		for (int k16 = 3; k16 <= 4; ++k16) {
			placeChest(world, random, 5, 1, k16, 5, LOTRChestContents.EASTERLING_SMITHY);
		}
		placeWeaponRack(world, 4, 3, 5, 6, random.nextBoolean() ? null : getEasterlingWeaponItem(random));
		placeWeaponRack(world, 5, 3, 4, 7, random.nextBoolean() ? null : getEasterlingWeaponItem(random));
		placeArmorStand(world, 5, 1, 0, 1, null);
		for (i12 = -1; i12 <= 1; ++i12) {
			for (k1 = 4; k1 <= 6; ++k1) {
				for (int j13 = 1; j13 <= 6; ++j13) {
					setBlockAndMetadata(world, i12, j13, k1, brickBlock, brickMeta);
				}
			}
		}
		for (int j14 = 1; j14 <= 5; ++j14) {
			setAir(world, 0, j14, 5);
		}
		setBlockAndMetadata(world, 0, 0, 5, LOTRMod.hearth, 0);
		setBlockAndMetadata(world, 0, 1, 5, Blocks.fire, 0);
		setBlockAndMetadata(world, 0, 0, 4, brickBlock, brickMeta);
		setBlockAndMetadata(world, 0, 1, 4, barsBlock, 0);
		setBlockAndMetadata(world, 0, 3, 4, barsBlock, 0);
		setBlockAndMetadata(world, 0, 5, 4, barsBlock, 0);
		setBlockAndMetadata(world, -1, 6, 6, brickStairBlock, 3);
		setBlockAndMetadata(world, 1, 6, 6, brickStairBlock, 3);
		setBlockAndMetadata(world, 0, 7, 6, brickBlock, brickMeta);
		setBlockAndMetadata(world, 0, 8, 6, brickBlock, brickMeta);
		setBlockAndMetadata(world, 0, 9, 6, Blocks.flower_pot, 0);
		for (i12 = -3; i12 <= -2; ++i12) {
			for (j1 = 1; j1 <= 2; ++j1) {
				setBlockAndMetadata(world, i12, j1, 5, brickBlock, brickMeta);
			}
		}
		setBlockAndMetadata(world, -3, 1, 4, brickBlock, brickMeta);
		setBlockAndMetadata(world, -3, 1, 3, brickBlock, brickMeta);
		setBlockAndMetadata(world, -3, 2, 3, brickBlock, brickMeta);
		setBlockAndMetadata(world, -5, 1, 3, Blocks.furnace, 2);
		setBlockAndMetadata(world, -4, 1, 3, LOTRMod.alloyForge, 2);
		setBlockAndMetadata(world, -5, 2, 3, barsBlock, 0);
		setBlockAndMetadata(world, -4, 2, 3, barsBlock, 0);
		setBlockAndMetadata(world, -3, 2, 4, barsBlock, 0);
		for (i12 = -5; i12 <= -4; ++i12) {
			for (k1 = 4; k1 <= 5; ++k1) {
				setBlockAndMetadata(world, i12, 3, k1, brickBlock, brickMeta);
				setBlockAndMetadata(world, i12, 1, k1, Blocks.lava, 0);
			}
		}
		setBlockAndMetadata(world, -5, 3, 3, brickStairBlock, 2);
		setBlockAndMetadata(world, -4, 3, 3, brickStairBlock, 2);
		setBlockAndMetadata(world, -3, 3, 3, brickStairBlock, 2);
		setBlockAndMetadata(world, -3, 3, 4, brickStairBlock, 0);
		setBlockAndMetadata(world, -3, 3, 5, brickStairBlock, 0);
		setBlockAndMetadata(world, -3, 1, 0, Blocks.anvil, 1);
		setBlockAndMetadata(world, -5, 1, 0, Blocks.cauldron, 3);
		LOTREntityEasterlingBlacksmith blacksmith = new LOTREntityEasterlingBlacksmith(world);
		spawnNPCAndSetHome(blacksmith, world, 0, 1, 0, 16);
		return true;
	}
}
